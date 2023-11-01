package com.android.systemui.appops;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecordingConfiguration;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.permission.PermissionManager;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.Dumpable;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/appops/AppOpsControllerImpl.class */
public class AppOpsControllerImpl extends BroadcastReceiver implements AppOpsController, AppOpsManager.OnOpActiveChangedListener, AppOpsManager.OnOpNotedListener, IndividualSensorPrivacyController.Callback, Dumpable {
    public static final int[] OPS = {42, 26, 101, 24, 27, 120, 100, 0, 1};
    public final AppOpsManager mAppOps;
    public final AudioManager mAudioManager;
    public H mBGHandler;
    public boolean mCameraDisabled;
    public final SystemClock mClock;
    public final Context mContext;
    public final BroadcastDispatcher mDispatcher;
    public boolean mListening;
    public boolean mMicMuted;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public final List<AppOpsController.Callback> mCallbacks = new ArrayList();
    public final SparseArray<Set<AppOpsController.Callback>> mCallbacksByCode = new SparseArray<>();
    @GuardedBy({"mActiveItems"})
    public final List<AppOpItem> mActiveItems = new ArrayList();
    @GuardedBy({"mNotedItems"})
    public final List<AppOpItem> mNotedItems = new ArrayList();
    @GuardedBy({"mActiveItems"})
    public final SparseArray<ArrayList<AudioRecordingConfiguration>> mRecordingsByUid = new SparseArray<>();
    public AudioManager.AudioRecordingCallback mAudioRecordingCallback = new AudioManager.AudioRecordingCallback() { // from class: com.android.systemui.appops.AppOpsControllerImpl.1
        {
            AppOpsControllerImpl.this = this;
        }

        @Override // android.media.AudioManager.AudioRecordingCallback
        public void onRecordingConfigChanged(List<AudioRecordingConfiguration> list) {
            synchronized (AppOpsControllerImpl.this.mActiveItems) {
                AppOpsControllerImpl.this.mRecordingsByUid.clear();
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    AudioRecordingConfiguration audioRecordingConfiguration = list.get(i);
                    ArrayList arrayList = (ArrayList) AppOpsControllerImpl.this.mRecordingsByUid.get(audioRecordingConfiguration.getClientUid());
                    ArrayList arrayList2 = arrayList;
                    if (arrayList == null) {
                        arrayList2 = new ArrayList();
                        AppOpsControllerImpl.this.mRecordingsByUid.put(audioRecordingConfiguration.getClientUid(), arrayList2);
                    }
                    arrayList2.add(audioRecordingConfiguration);
                }
            }
            AppOpsControllerImpl.this.updateSensorDisabledStatus();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/appops/AppOpsControllerImpl$H.class */
    public class H extends Handler {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public H(Looper looper) {
            super(looper);
            AppOpsControllerImpl.this = r4;
        }

        public void scheduleRemoval(final AppOpItem appOpItem, long j) {
            removeCallbacksAndMessages(appOpItem);
            postDelayed(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl.H.1
                {
                    H.this = this;
                }

                @Override // java.lang.Runnable
                public void run() {
                    AppOpsControllerImpl.this.removeNoted(appOpItem.getCode(), appOpItem.getUid(), appOpItem.getPackageName());
                }
            }, appOpItem, j);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$D_3b3F8lH_XnAWBGg8J56QkIqyo(AppOpsControllerImpl appOpsControllerImpl) {
        appOpsControllerImpl.lambda$setListening$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$GiO16G02VhvraMuIa07FO-1FDlk */
    public static /* synthetic */ void m1458$r8$lambda$GiO16G02VhvraMuIa07FO1FDlk(AppOpsControllerImpl appOpsControllerImpl, int i, boolean z) {
        appOpsControllerImpl.lambda$onSensorBlockedChanged$2(i, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$wxZ9nVuJ6nZ2-M8FJ3w80hwdnE4 */
    public static /* synthetic */ void m1459$r8$lambda$wxZ9nVuJ6nZ2M8FJ3w80hwdnE4(AppOpsControllerImpl appOpsControllerImpl, int i, int i2, String str, boolean z) {
        appOpsControllerImpl.lambda$notifySuscribers$1(i, i2, str, z);
    }

    public AppOpsControllerImpl(Context context, Looper looper, DumpManager dumpManager, AudioManager audioManager, IndividualSensorPrivacyController individualSensorPrivacyController, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        this.mDispatcher = broadcastDispatcher;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mBGHandler = new H(looper);
        int length = OPS.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            this.mCallbacksByCode.put(OPS[i], new ArraySet());
        }
        this.mAudioManager = audioManager;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mMicMuted = (audioManager.isMicrophoneMute() || individualSensorPrivacyController.isSensorBlocked(1)) ? true : z;
        this.mCameraDisabled = individualSensorPrivacyController.isSensorBlocked(2);
        this.mContext = context;
        this.mClock = systemClock;
        dumpManager.registerDumpable("AppOpsControllerImpl", this);
    }

    public /* synthetic */ void lambda$onSensorBlockedChanged$2(int i, boolean z) {
        if (i == 2) {
            this.mCameraDisabled = z;
        } else if (i == 1) {
            boolean z2 = true;
            if (!this.mAudioManager.isMicrophoneMute()) {
                z2 = z;
            }
            this.mMicMuted = z2;
        }
        updateSensorDisabledStatus();
    }

    public /* synthetic */ void lambda$setListening$0() {
        this.mAudioRecordingCallback.onRecordingConfigChanged(this.mAudioManager.getActiveRecordingConfigurations());
    }

    @Override // com.android.systemui.appops.AppOpsController
    public void addCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).add(callback);
                z = true;
            }
        }
        if (z) {
            this.mCallbacks.add(callback);
        }
        if (this.mCallbacks.isEmpty()) {
            return;
        }
        setListening(true);
    }

    public final boolean addNoted(int i, int i2, String str) {
        boolean z;
        AppOpItem appOpItem;
        synchronized (this.mNotedItems) {
            AppOpItem appOpItemLocked = getAppOpItemLocked(this.mNotedItems, i, i2, str);
            if (appOpItemLocked == null) {
                AppOpItem appOpItem2 = new AppOpItem(i, i2, str, this.mClock.elapsedRealtime());
                this.mNotedItems.add(appOpItem2);
                z = true;
                appOpItem = appOpItem2;
            } else {
                z = false;
                appOpItem = appOpItemLocked;
            }
        }
        this.mBGHandler.removeCallbacksAndMessages(appOpItem);
        this.mBGHandler.scheduleRemoval(appOpItem, 5000L);
        return z;
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("AppOpsController state:");
        printWriter.println("  Listening: " + this.mListening);
        printWriter.println("  Active Items:");
        for (int i = 0; i < this.mActiveItems.size(); i++) {
            printWriter.print("    ");
            printWriter.println(this.mActiveItems.get(i).toString());
        }
        printWriter.println("  Noted Items:");
        for (int i2 = 0; i2 < this.mNotedItems.size(); i2++) {
            printWriter.print("    ");
            printWriter.println(this.mNotedItems.get(i2).toString());
        }
    }

    @Override // com.android.systemui.appops.AppOpsController
    public List<AppOpItem> getActiveAppOps() {
        return getActiveAppOps(false);
    }

    @Override // com.android.systemui.appops.AppOpsController
    public List<AppOpItem> getActiveAppOps(boolean z) {
        return getActiveAppOpsForUser(-1, z);
    }

    public List<AppOpItem> getActiveAppOpsForUser(int i, boolean z) {
        Assert.isNotMainThread();
        ArrayList arrayList = new ArrayList();
        synchronized (this.mActiveItems) {
            int size = this.mActiveItems.size();
            for (int i2 = 0; i2 < size; i2++) {
                AppOpItem appOpItem = this.mActiveItems.get(i2);
                if ((i == -1 || UserHandle.getUserId(appOpItem.getUid()) == i) && isUserVisible(appOpItem.getPackageName()) && (z || !appOpItem.isDisabled())) {
                    arrayList.add(appOpItem);
                }
            }
        }
        synchronized (this.mNotedItems) {
            int size2 = this.mNotedItems.size();
            for (int i3 = 0; i3 < size2; i3++) {
                AppOpItem appOpItem2 = this.mNotedItems.get(i3);
                if ((i == -1 || UserHandle.getUserId(appOpItem2.getUid()) == i) && isUserVisible(appOpItem2.getPackageName())) {
                    arrayList.add(appOpItem2);
                }
            }
        }
        return arrayList;
    }

    public final AppOpItem getAppOpItemLocked(List<AppOpItem> list, int i, int i2, String str) {
        int size = list.size();
        for (int i3 = 0; i3 < size; i3++) {
            AppOpItem appOpItem = list.get(i3);
            if (appOpItem.getCode() == i && appOpItem.getUid() == i2 && appOpItem.getPackageName().equals(str)) {
                return appOpItem;
            }
        }
        return null;
    }

    public final boolean isAnyRecordingPausedLocked(int i) {
        if (this.mMicMuted) {
            return true;
        }
        ArrayList<AudioRecordingConfiguration> arrayList = this.mRecordingsByUid.get(i);
        if (arrayList == null) {
            return false;
        }
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (arrayList.get(i2).isClientSilenced()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.systemui.appops.AppOpsController
    public boolean isMicMuted() {
        return this.mMicMuted;
    }

    public final boolean isOpCamera(int i) {
        return i == 26 || i == 101;
    }

    public final boolean isOpMicrophone(int i) {
        return i == 27 || i == 100 || i == 120;
    }

    public final boolean isUserVisible(String str) {
        return PermissionManager.shouldShowPackageForIndicatorCached(this.mContext, str);
    }

    public final void notifySuscribers(final int i, final int i2, final String str, final boolean z) {
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl.m1459$r8$lambda$wxZ9nVuJ6nZ2M8FJ3w80hwdnE4(AppOpsControllerImpl.this, i, i2, str, z);
            }
        });
    }

    /* renamed from: notifySuscribersWorker */
    public final void lambda$notifySuscribers$1(int i, int i2, String str, boolean z) {
        if (this.mCallbacksByCode.contains(i) && isUserVisible(str)) {
            for (AppOpsController.Callback callback : this.mCallbacksByCode.get(i)) {
                callback.onActiveStateChanged(i, i2, str, z);
            }
        }
    }

    public void onOpActiveChanged(String str, int i, String str2, String str3, boolean z, int i2, int i3) {
        boolean z2;
        int strOpToOp = AppOpsManager.strOpToOp(str);
        if (!(z && i3 != -1 && i2 != 0 && (i2 & 1) == 0 && (i2 & 8) == 0) && updateActives(strOpToOp, i, str2, z)) {
            synchronized (this.mNotedItems) {
                z2 = getAppOpItemLocked(this.mNotedItems, strOpToOp, i, str2) != null;
            }
            if (z2) {
                return;
            }
            notifySuscribers(strOpToOp, i, str2, z);
        }
    }

    @Override // android.app.AppOpsManager.OnOpActiveChangedListener
    public void onOpActiveChanged(String str, int i, String str2, boolean z) {
        onOpActiveChanged(str, i, str2, null, z, 0, -1);
    }

    public void onOpNoted(int i, int i2, String str, String str2, int i3, int i4) {
        boolean z;
        if (i4 == 0 && addNoted(i, i2, str)) {
            synchronized (this.mActiveItems) {
                z = getAppOpItemLocked(this.mActiveItems, i, i2, str) != null;
            }
            if (z) {
                return;
            }
            notifySuscribers(i, i2, str, true);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean z = true;
        if (!this.mAudioManager.isMicrophoneMute()) {
            z = this.mSensorPrivacyController.isSensorBlocked(1);
        }
        this.mMicMuted = z;
        updateSensorDisabledStatus();
    }

    public void onSensorBlockedChanged(final int i, final boolean z) {
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl.m1458$r8$lambda$GiO16G02VhvraMuIa07FO1FDlk(AppOpsControllerImpl.this, i, z);
            }
        });
    }

    @Override // com.android.systemui.appops.AppOpsController
    public void removeCallback(int[] iArr, AppOpsController.Callback callback) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            if (this.mCallbacksByCode.contains(iArr[i])) {
                this.mCallbacksByCode.get(iArr[i]).remove(callback);
            }
        }
        this.mCallbacks.remove(callback);
        if (this.mCallbacks.isEmpty()) {
            setListening(false);
        }
    }

    public final void removeNoted(int i, int i2, String str) {
        boolean z;
        synchronized (this.mNotedItems) {
            AppOpItem appOpItemLocked = getAppOpItemLocked(this.mNotedItems, i, i2, str);
            if (appOpItemLocked == null) {
                return;
            }
            this.mNotedItems.remove(appOpItemLocked);
            synchronized (this.mActiveItems) {
                z = getAppOpItemLocked(this.mActiveItems, i, i2, str) != null;
            }
            if (z) {
                return;
            }
            lambda$notifySuscribers$1(i, i2, str, false);
        }
    }

    @VisibleForTesting
    public void setBGHandler(H h) {
        this.mBGHandler = h;
    }

    @VisibleForTesting
    public void setListening(boolean z) {
        this.mListening = z;
        if (!z) {
            this.mAppOps.stopWatchingActive(this);
            this.mAppOps.stopWatchingNoted(this);
            this.mAudioManager.unregisterAudioRecordingCallback(this.mAudioRecordingCallback);
            this.mSensorPrivacyController.removeCallback(this);
            this.mBGHandler.removeCallbacksAndMessages(null);
            this.mDispatcher.unregisterReceiver(this);
            synchronized (this.mActiveItems) {
                this.mActiveItems.clear();
                this.mRecordingsByUid.clear();
            }
            synchronized (this.mNotedItems) {
                this.mNotedItems.clear();
            }
            return;
        }
        AppOpsManager appOpsManager = this.mAppOps;
        int[] iArr = OPS;
        appOpsManager.startWatchingActive(iArr, this);
        this.mAppOps.startWatchingNoted(iArr, this);
        this.mAudioManager.registerAudioRecordingCallback(this.mAudioRecordingCallback, this.mBGHandler);
        this.mSensorPrivacyController.addCallback(this);
        boolean z2 = true;
        if (!this.mAudioManager.isMicrophoneMute()) {
            z2 = this.mSensorPrivacyController.isSensorBlocked(1);
        }
        this.mMicMuted = z2;
        this.mCameraDisabled = this.mSensorPrivacyController.isSensorBlocked(2);
        this.mBGHandler.post(new Runnable() { // from class: com.android.systemui.appops.AppOpsControllerImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AppOpsControllerImpl.$r8$lambda$D_3b3F8lH_XnAWBGg8J56QkIqyo(AppOpsControllerImpl.this);
            }
        });
        this.mDispatcher.registerReceiverWithHandler(this, new IntentFilter("android.media.action.MICROPHONE_MUTE_CHANGED"), this.mBGHandler);
    }

    public final boolean updateActives(int i, int i2, String str, boolean z) {
        synchronized (this.mActiveItems) {
            AppOpItem appOpItemLocked = getAppOpItemLocked(this.mActiveItems, i, i2, str);
            if (appOpItemLocked != null || !z) {
                if (appOpItemLocked == null || z) {
                    return false;
                }
                this.mActiveItems.remove(appOpItemLocked);
                return true;
            }
            AppOpItem appOpItem = new AppOpItem(i, i2, str, this.mClock.elapsedRealtime());
            if (isOpMicrophone(i)) {
                appOpItem.setDisabled(isAnyRecordingPausedLocked(i2));
            } else if (isOpCamera(i)) {
                appOpItem.setDisabled(this.mCameraDisabled);
            }
            this.mActiveItems.add(appOpItem);
            return !appOpItem.isDisabled();
        }
    }

    public final void updateSensorDisabledStatus() {
        synchronized (this.mActiveItems) {
            int size = this.mActiveItems.size();
            for (int i = 0; i < size; i++) {
                AppOpItem appOpItem = this.mActiveItems.get(i);
                boolean isAnyRecordingPausedLocked = isOpMicrophone(appOpItem.getCode()) ? isAnyRecordingPausedLocked(appOpItem.getUid()) : isOpCamera(appOpItem.getCode()) ? this.mCameraDisabled : false;
                if (appOpItem.isDisabled() != isAnyRecordingPausedLocked) {
                    appOpItem.setDisabled(isAnyRecordingPausedLocked);
                    notifySuscribers(appOpItem.getCode(), appOpItem.getUid(), appOpItem.getPackageName(), !appOpItem.isDisabled());
                }
            }
        }
    }
}