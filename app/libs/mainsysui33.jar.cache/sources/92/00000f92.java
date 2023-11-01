package com.android.systemui;

import android.os.Handler;
import android.os.UserHandle;
import android.service.notification.StatusBarNotification;
import android.util.SparseArray;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.util.Assert;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServiceController.class */
public class ForegroundServiceController {
    public static final int[] APP_OPS = {24};
    public final Handler mMainHandler;
    public final SparseArray<ForegroundServicesUserState> mUserServices = new SparseArray<>();
    public final Object mMutex = new Object();

    /* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServiceController$UserStateUpdateCallback.class */
    public interface UserStateUpdateCallback {
        boolean updateUserState(ForegroundServicesUserState foregroundServicesUserState);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ForegroundServiceController$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$JbXgeBv7gG-YTsnd5X1tVHhMbzM */
    public static /* synthetic */ void m1256$r8$lambda$JbXgeBv7gGYTsnd5X1tVHhMbzM(ForegroundServiceController foregroundServiceController, int i, int i2, String str, boolean z) {
        foregroundServiceController.lambda$new$0(i, i2, str, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.ForegroundServiceController$$ExternalSyntheticLambda0.onActiveStateChanged(int, int, java.lang.String, boolean):void] */
    public static /* synthetic */ void $r8$lambda$_HNBh6jVNcmyyKLdCrrJ5FgWPlY(ForegroundServiceController foregroundServiceController, int i, int i2, String str, boolean z) {
        foregroundServiceController.lambda$new$1(i, i2, str, z);
    }

    public ForegroundServiceController(AppOpsController appOpsController, Handler handler) {
        this.mMainHandler = handler;
        appOpsController.addCallback(APP_OPS, new AppOpsController.Callback() { // from class: com.android.systemui.ForegroundServiceController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.appops.AppOpsController.Callback
            public final void onActiveStateChanged(int i, int i2, String str, boolean z) {
                ForegroundServiceController.$r8$lambda$_HNBh6jVNcmyyKLdCrrJ5FgWPlY(ForegroundServiceController.this, i, i2, str, z);
            }
        });
    }

    public /* synthetic */ void lambda$new$1(final int i, final int i2, final String str, final boolean z) {
        this.mMainHandler.post(new Runnable() { // from class: com.android.systemui.ForegroundServiceController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ForegroundServiceController.m1256$r8$lambda$JbXgeBv7gGYTsnd5X1tVHhMbzM(ForegroundServiceController.this, i, i2, str, z);
            }
        });
    }

    public boolean isDisclosureNeededForUser(int i) {
        synchronized (this.mMutex) {
            ForegroundServicesUserState foregroundServicesUserState = this.mUserServices.get(i);
            if (foregroundServicesUserState == null) {
                return false;
            }
            return foregroundServicesUserState.isDisclosureNeeded();
        }
    }

    public boolean isDisclosureNotification(StatusBarNotification statusBarNotification) {
        return statusBarNotification.getId() == 40 && statusBarNotification.getTag() == null && statusBarNotification.getPackageName().equals("android");
    }

    /* renamed from: onAppOpChanged */
    public void lambda$new$0(int i, int i2, String str, boolean z) {
        Assert.isMainThread();
        int userId = UserHandle.getUserId(i2);
        synchronized (this.mMutex) {
            ForegroundServicesUserState foregroundServicesUserState = this.mUserServices.get(userId);
            ForegroundServicesUserState foregroundServicesUserState2 = foregroundServicesUserState;
            if (foregroundServicesUserState == null) {
                foregroundServicesUserState2 = new ForegroundServicesUserState();
                this.mUserServices.put(userId, foregroundServicesUserState2);
            }
            if (z) {
                foregroundServicesUserState2.addOp(str, i);
            } else {
                foregroundServicesUserState2.removeOp(str, i);
            }
        }
    }

    public boolean updateUserState(int i, UserStateUpdateCallback userStateUpdateCallback, boolean z) {
        synchronized (this.mMutex) {
            ForegroundServicesUserState foregroundServicesUserState = this.mUserServices.get(i);
            ForegroundServicesUserState foregroundServicesUserState2 = foregroundServicesUserState;
            if (foregroundServicesUserState == null) {
                if (!z) {
                    return false;
                }
                foregroundServicesUserState2 = new ForegroundServicesUserState();
                this.mUserServices.put(i, foregroundServicesUserState2);
            }
            return userStateUpdateCallback.updateUserState(foregroundServicesUserState2);
        }
    }
}