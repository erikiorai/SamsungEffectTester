package com.android.systemui.privacy;

import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.util.IndentingPrintWriter;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.appops.AppOpItem;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.privacy.AppOpsPrivacyItemMonitor;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysJvmKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/AppOpsPrivacyItemMonitor.class */
public final class AppOpsPrivacyItemMonitor implements PrivacyItemMonitor {
    public static final int[] OPS;
    public static final int[] OPS_LOCATION;
    public static final int[] OPS_MIC_CAMERA;
    public final AppOpsController appOpsController;
    public final DelayableExecutor bgExecutor;
    @GuardedBy({"lock"})
    public PrivacyItemMonitor.Callback callback;
    public final AppOpsPrivacyItemMonitor$configCallback$1 configCallback;
    @GuardedBy({"lock"})
    public boolean listening;
    @GuardedBy({"lock"})
    public boolean locationAvailable;
    public final PrivacyLogger logger;
    @GuardedBy({"lock"})
    public boolean micCameraAvailable;
    public final PrivacyConfig privacyConfig;
    public final UserTracker userTracker;
    public static final Companion Companion = new Companion(null);
    public static final String[] CAMERA_WHITELIST_PKG = {"org.pixelexperience.faceunlock"};
    public static final int[] USER_INDEPENDENT_OPS = {101, 100};
    public final Object lock = new Object();
    public final AppOpsPrivacyItemMonitor$appOpsCallback$1 appOpsCallback = new AppOpsController.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1
        @Override // com.android.systemui.appops.AppOpsController.Callback
        public void onActiveStateChanged(int i, int i2, String str, boolean z) {
            boolean z2;
            Object access$getLock$p = AppOpsPrivacyItemMonitor.access$getLock$p(AppOpsPrivacyItemMonitor.this);
            AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = AppOpsPrivacyItemMonitor.this;
            synchronized (access$getLock$p) {
                AppOpsPrivacyItemMonitor.Companion companion = AppOpsPrivacyItemMonitor.Companion;
                if ((!ArraysKt___ArraysKt.contains(companion.getOPS_MIC_CAMERA(), i) || AppOpsPrivacyItemMonitor.access$getMicCameraAvailable$p(appOpsPrivacyItemMonitor)) && !ArraysKt___ArraysKt.contains(companion.getCAMERA_WHITELIST_PKG(), str)) {
                    if (!ArraysKt___ArraysKt.contains(companion.getOPS_LOCATION(), i) || AppOpsPrivacyItemMonitor.access$getLocationAvailable$p(appOpsPrivacyItemMonitor)) {
                        List userProfiles = AppOpsPrivacyItemMonitor.access$getUserTracker$p(appOpsPrivacyItemMonitor).getUserProfiles();
                        if (!(userProfiles instanceof Collection) || !userProfiles.isEmpty()) {
                            Iterator it = userProfiles.iterator();
                            do {
                                if (it.hasNext()) {
                                }
                            } while (!(((UserInfo) it.next()).id == UserHandle.getUserId(i2)));
                            z2 = true;
                            if (!z2 || ArraysKt___ArraysKt.contains(AppOpsPrivacyItemMonitor.Companion.getUSER_INDEPENDENT_OPS(), i)) {
                                AppOpsPrivacyItemMonitor.access$getLogger$p(appOpsPrivacyItemMonitor).logUpdatedItemFromAppOps(i, i2, str, z);
                                AppOpsPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(appOpsPrivacyItemMonitor);
                            }
                            Unit unit = Unit.INSTANCE;
                        }
                        z2 = false;
                        if (!z2) {
                        }
                        AppOpsPrivacyItemMonitor.access$getLogger$p(appOpsPrivacyItemMonitor).logUpdatedItemFromAppOps(i, i2, str, z);
                        AppOpsPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(appOpsPrivacyItemMonitor);
                        Unit unit2 = Unit.INSTANCE;
                    }
                }
            }
        }
    };
    public final UserTracker.Callback userTrackerCallback = new UserTracker.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$userTrackerCallback$1
        public void onProfilesChanged(List<? extends UserInfo> list) {
            AppOpsPrivacyItemMonitor.access$onCurrentProfilesChanged(AppOpsPrivacyItemMonitor.this);
        }

        public void onUserChanged(int i, Context context) {
            AppOpsPrivacyItemMonitor.access$onCurrentProfilesChanged(AppOpsPrivacyItemMonitor.this);
        }
    };

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/AppOpsPrivacyItemMonitor$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String[] getCAMERA_WHITELIST_PKG() {
            return AppOpsPrivacyItemMonitor.CAMERA_WHITELIST_PKG;
        }

        public final int[] getOPS_LOCATION() {
            return AppOpsPrivacyItemMonitor.OPS_LOCATION;
        }

        public final int[] getOPS_MIC_CAMERA() {
            return AppOpsPrivacyItemMonitor.OPS_MIC_CAMERA;
        }

        public final int[] getUSER_INDEPENDENT_OPS() {
            return AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS;
        }
    }

    static {
        int[] iArr = {26, 101, 27, 100, 120};
        OPS_MIC_CAMERA = iArr;
        int[] iArr2 = {0, 1};
        OPS_LOCATION = iArr2;
        OPS = ArraysKt___ArraysJvmKt.plus(iArr, iArr2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: com.android.systemui.privacy.PrivacyConfig */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v11, types: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1, com.android.systemui.privacy.PrivacyConfig$Callback] */
    /* JADX WARN: Type inference failed for: r1v10, types: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1] */
    public AppOpsPrivacyItemMonitor(AppOpsController appOpsController, UserTracker userTracker, PrivacyConfig privacyConfig, DelayableExecutor delayableExecutor, PrivacyLogger privacyLogger) {
        this.appOpsController = appOpsController;
        this.userTracker = userTracker;
        this.privacyConfig = privacyConfig;
        this.bgExecutor = delayableExecutor;
        this.logger = privacyLogger;
        this.micCameraAvailable = privacyConfig.getMicCameraAvailable();
        this.locationAvailable = privacyConfig.getLocationAvailable();
        ?? r0 = new PrivacyConfig.Callback() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1
            public final void onFlagChanged() {
                Object access$getLock$p = AppOpsPrivacyItemMonitor.access$getLock$p(AppOpsPrivacyItemMonitor.this);
                AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor = AppOpsPrivacyItemMonitor.this;
                synchronized (access$getLock$p) {
                    AppOpsPrivacyItemMonitor.access$setMicCameraAvailable$p(appOpsPrivacyItemMonitor, AppOpsPrivacyItemMonitor.access$getPrivacyConfig$p(appOpsPrivacyItemMonitor).getMicCameraAvailable());
                    AppOpsPrivacyItemMonitor.access$setLocationAvailable$p(appOpsPrivacyItemMonitor, AppOpsPrivacyItemMonitor.access$getPrivacyConfig$p(appOpsPrivacyItemMonitor).getLocationAvailable());
                    AppOpsPrivacyItemMonitor.access$setListeningStateLocked(appOpsPrivacyItemMonitor);
                    Unit unit = Unit.INSTANCE;
                }
                AppOpsPrivacyItemMonitor.access$dispatchOnPrivacyItemsChanged(AppOpsPrivacyItemMonitor.this);
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagLocationChanged(boolean z) {
                onFlagChanged();
            }

            @Override // com.android.systemui.privacy.PrivacyConfig.Callback
            public void onFlagMicCameraChanged(boolean z) {
                onFlagChanged();
            }
        };
        this.configCallback = r0;
        privacyConfig.addCallback((PrivacyConfig.Callback) r0);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void, com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ void access$dispatchOnPrivacyItemsChanged(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        appOpsPrivacyItemMonitor.dispatchOnPrivacyItemsChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void] */
    public static final /* synthetic */ boolean access$getLocationAvailable$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.locationAvailable;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void, com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ Object access$getLock$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.lock;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void] */
    public static final /* synthetic */ PrivacyLogger access$getLogger$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.logger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void] */
    public static final /* synthetic */ boolean access$getMicCameraAvailable$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.micCameraAvailable;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ PrivacyConfig access$getPrivacyConfig$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.privacyConfig;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$appOpsCallback$1.onActiveStateChanged(int, int, java.lang.String, boolean):void] */
    public static final /* synthetic */ UserTracker access$getUserTracker$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        return appOpsPrivacyItemMonitor.userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$userTrackerCallback$1.onProfilesChanged(java.util.List<? extends android.content.pm.UserInfo>):void, com.android.systemui.privacy.AppOpsPrivacyItemMonitor$userTrackerCallback$1.onUserChanged(int, android.content.Context):void] */
    public static final /* synthetic */ void access$onCurrentProfilesChanged(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        appOpsPrivacyItemMonitor.onCurrentProfilesChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ void access$setListeningStateLocked(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor) {
        appOpsPrivacyItemMonitor.setListeningStateLocked();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ void access$setLocationAvailable$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor, boolean z) {
        appOpsPrivacyItemMonitor.locationAvailable = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.privacy.AppOpsPrivacyItemMonitor$configCallback$1.onFlagChanged():void] */
    public static final /* synthetic */ void access$setMicCameraAvailable$p(AppOpsPrivacyItemMonitor appOpsPrivacyItemMonitor, boolean z) {
        appOpsPrivacyItemMonitor.micCameraAvailable = z;
    }

    @VisibleForTesting
    public static /* synthetic */ void getUserTrackerCallback$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public final void dispatchOnPrivacyItemsChanged() {
        final PrivacyItemMonitor.Callback callback;
        synchronized (this.lock) {
            callback = this.callback;
        }
        if (callback != null) {
            this.bgExecutor.execute(new Runnable() { // from class: com.android.systemui.privacy.AppOpsPrivacyItemMonitor$dispatchOnPrivacyItemsChanged$1
                @Override // java.lang.Runnable
                public final void run() {
                    PrivacyItemMonitor.Callback.this.onPrivacyItemsChanged();
                }
            });
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.println("AppOpsPrivacyItemMonitor:");
        asIndenting.increaseIndent();
        try {
            synchronized (this.lock) {
                boolean z = this.listening;
                asIndenting.println("Listening: " + z);
                boolean z2 = this.micCameraAvailable;
                asIndenting.println("micCameraAvailable: " + z2);
                boolean z3 = this.locationAvailable;
                asIndenting.println("locationAvailable: " + z3);
                PrivacyItemMonitor.Callback callback = this.callback;
                asIndenting.println("Callback: " + callback);
                Unit unit = Unit.INSTANCE;
            }
            List<UserInfo> userProfiles = this.userTracker.getUserProfiles();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
            for (UserInfo userInfo : userProfiles) {
                arrayList.add(Integer.valueOf(userInfo.id));
            }
            asIndenting.println("Current user ids: " + arrayList);
            asIndenting.decreaseIndent();
            asIndenting.flush();
        } catch (Throwable th) {
            asIndenting.decreaseIndent();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:98:0x00c5, code lost:
        if (kotlin.collections.ArraysKt___ArraysKt.contains(com.android.systemui.privacy.AppOpsPrivacyItemMonitor.USER_INDEPENDENT_OPS, r0.getCode()) != false) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:122:0x00d0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0033 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00b6  */
    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<PrivacyItem> getActivePrivacyItems() {
        ArrayList arrayList;
        boolean z;
        boolean z2;
        List<AppOpItem> activeAppOps = this.appOpsController.getActiveAppOps(true);
        List userProfiles = this.userTracker.getUserProfiles();
        synchronized (this.lock) {
            List<AppOpItem> list = activeAppOps;
            ArrayList<AppOpItem> arrayList2 = new ArrayList();
            for (Object obj : list) {
                AppOpItem appOpItem = (AppOpItem) obj;
                List list2 = userProfiles;
                if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                    Iterator it = list2.iterator();
                    do {
                        if (it.hasNext()) {
                        }
                    } while (!(((UserInfo) it.next()).id == UserHandle.getUserId(appOpItem.getUid())));
                    z = true;
                    z2 = z;
                    if (!z2) {
                        arrayList2.add(obj);
                    }
                }
                z = false;
                if (z) {
                }
                if (!z2) {
                }
            }
            arrayList = new ArrayList();
            for (AppOpItem appOpItem2 : arrayList2) {
                PrivacyItem privacyItemLocked = toPrivacyItemLocked(appOpItem2);
                if (privacyItemLocked != null) {
                    arrayList.add(privacyItemLocked);
                }
            }
        }
        return CollectionsKt___CollectionsKt.distinct(arrayList);
    }

    public final void onCurrentProfilesChanged() {
        List<UserInfo> userProfiles = this.userTracker.getUserProfiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(userProfiles, 10));
        for (UserInfo userInfo : userProfiles) {
            arrayList.add(Integer.valueOf(userInfo.id));
        }
        this.logger.logCurrentProfilesChanged(arrayList);
        dispatchOnPrivacyItemsChanged();
    }

    @GuardedBy({"lock"})
    public final boolean privacyItemForAppOpEnabledLocked(int i) {
        if (ArraysKt___ArraysKt.contains(OPS_LOCATION, i)) {
            return this.locationAvailable;
        }
        if (ArraysKt___ArraysKt.contains(OPS_MIC_CAMERA, i)) {
            return this.micCameraAvailable;
        }
        return false;
    }

    @GuardedBy({"lock"})
    public final void setListeningStateLocked() {
        boolean z = this.callback != null && (this.micCameraAvailable || this.locationAvailable);
        if (this.listening == z) {
            return;
        }
        this.listening = z;
        if (!z) {
            this.appOpsController.removeCallback(OPS, this.appOpsCallback);
            this.userTracker.removeCallback(this.userTrackerCallback);
            return;
        }
        this.appOpsController.addCallback(OPS, this.appOpsCallback);
        this.userTracker.addCallback(this.userTrackerCallback, this.bgExecutor);
        onCurrentProfilesChanged();
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public void startListening(PrivacyItemMonitor.Callback callback) {
        synchronized (this.lock) {
            this.callback = callback;
            setListeningStateLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    @Override // com.android.systemui.privacy.PrivacyItemMonitor
    public void stopListening() {
        synchronized (this.lock) {
            this.callback = null;
            setListeningStateLocked();
            Unit unit = Unit.INSTANCE;
        }
    }

    @GuardedBy({"lock"})
    public final PrivacyItem toPrivacyItemLocked(AppOpItem appOpItem) {
        PrivacyType privacyType;
        if (privacyItemForAppOpEnabledLocked(appOpItem.getCode())) {
            int code = appOpItem.getCode();
            if (code == 0 || code == 1) {
                privacyType = PrivacyType.TYPE_LOCATION;
            } else {
                if (code != 26) {
                    if (code != 27 && code != 100) {
                        if (code != 101) {
                            if (code != 120) {
                                return null;
                            }
                        }
                    }
                    privacyType = PrivacyType.TYPE_MICROPHONE;
                }
                privacyType = PrivacyType.TYPE_CAMERA;
            }
            if ((privacyType != PrivacyType.TYPE_CAMERA || this.micCameraAvailable) && !ArraysKt___ArraysKt.contains(CAMERA_WHITELIST_PKG, appOpItem.getPackageName())) {
                return new PrivacyItem(privacyType, new PrivacyApplication(appOpItem.getPackageName(), appOpItem.getUid()), appOpItem.getTimeStartedElapsed(), appOpItem.isDisabled());
            }
            return null;
        }
        return null;
    }
}