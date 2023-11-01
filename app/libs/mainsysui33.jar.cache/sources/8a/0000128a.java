package com.android.systemui.camera;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.ProfilerInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraGestureHelper.class */
public final class CameraGestureHelper {
    public final ActivityIntentHelper activityIntentHelper;
    public final ActivityManager activityManager;
    public final ActivityStarter activityStarter;
    public final IActivityTaskManager activityTaskManager;
    public final CameraIntentsWrapper cameraIntents;
    public final CentralSurfaces centralSurfaces;
    public final ContentResolver contentResolver;
    public final Context context;
    public final KeyguardStateController keyguardStateController;
    public final PackageManager packageManager;
    public final Executor uiExecutor;

    public CameraGestureHelper(Context context, CentralSurfaces centralSurfaces, KeyguardStateController keyguardStateController, PackageManager packageManager, ActivityManager activityManager, ActivityStarter activityStarter, ActivityIntentHelper activityIntentHelper, IActivityTaskManager iActivityTaskManager, CameraIntentsWrapper cameraIntentsWrapper, ContentResolver contentResolver, Executor executor) {
        this.context = context;
        this.centralSurfaces = centralSurfaces;
        this.keyguardStateController = keyguardStateController;
        this.packageManager = packageManager;
        this.activityManager = activityManager;
        this.activityStarter = activityStarter;
        this.activityIntentHelper = activityIntentHelper;
        this.activityTaskManager = iActivityTaskManager;
        this.cameraIntents = cameraIntentsWrapper;
        this.contentResolver = contentResolver;
        this.uiExecutor = executor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.camera.CameraGestureHelper$launchCamera$1.run():void] */
    public static final /* synthetic */ IActivityTaskManager access$getActivityTaskManager$p(CameraGestureHelper cameraGestureHelper) {
        return cameraGestureHelper.activityTaskManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.camera.CameraGestureHelper$launchCamera$1.run():void] */
    public static final /* synthetic */ ContentResolver access$getContentResolver$p(CameraGestureHelper cameraGestureHelper) {
        return cameraGestureHelper.contentResolver;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.camera.CameraGestureHelper$launchCamera$1.run():void] */
    public static final /* synthetic */ Context access$getContext$p(CameraGestureHelper cameraGestureHelper) {
        return cameraGestureHelper.context;
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x005b, code lost:
        if (com.android.systemui.shared.system.ActivityManagerKt.INSTANCE.isInForeground(r5.activityManager, r9) == false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canCameraGestureBeLaunched(int i) {
        ActivityInfo activityInfo;
        if (this.centralSurfaces.isCameraAllowedByAdmin()) {
            ResolveInfo resolveActivityAsUser = this.packageManager.resolveActivityAsUser(getStartCameraIntent(), 65536, KeyguardUpdateMonitor.getCurrentUser());
            String str = (resolveActivityAsUser == null || (activityInfo = resolveActivityAsUser.activityInfo) == null) ? null : activityInfo.packageName;
            boolean z = false;
            if (str != null) {
                if (i == 0) {
                    z = false;
                }
                z = true;
            }
            return z;
        }
        return false;
    }

    public final Intent getStartCameraIntent() {
        return (!this.keyguardStateController.isMethodSecure() || this.keyguardStateController.canDismissLockScreen()) ? this.cameraIntents.getInsecureCameraIntent() : this.cameraIntents.getSecureCameraIntent();
    }

    public final void launchCamera(int i) {
        final Intent startCameraIntent = getStartCameraIntent();
        startCameraIntent.putExtra("com.android.systemui.camera_launch_source", i);
        boolean wouldLaunchResolverActivity = this.activityIntentHelper.wouldLaunchResolverActivity(startCameraIntent, KeyguardUpdateMonitor.getCurrentUser());
        if (!CameraIntents.Companion.isSecureCameraIntent(startCameraIntent) || wouldLaunchResolverActivity) {
            this.activityStarter.startActivity(startCameraIntent, false);
        } else {
            this.uiExecutor.execute(new Runnable() { // from class: com.android.systemui.camera.CameraGestureHelper$launchCamera$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityOptions makeBasic = ActivityOptions.makeBasic();
                    makeBasic.setDisallowEnterPictureInPictureWhileLaunching(true);
                    makeBasic.setRotationAnimationHint(3);
                    try {
                        IActivityTaskManager access$getActivityTaskManager$p = CameraGestureHelper.access$getActivityTaskManager$p(CameraGestureHelper.this);
                        String basePackageName = CameraGestureHelper.access$getContext$p(CameraGestureHelper.this).getBasePackageName();
                        String attributionTag = CameraGestureHelper.access$getContext$p(CameraGestureHelper.this).getAttributionTag();
                        Intent intent = startCameraIntent;
                        access$getActivityTaskManager$p.startActivityAsUser((IApplicationThread) null, basePackageName, attributionTag, intent, intent.resolveTypeIfNeeded(CameraGestureHelper.access$getContentResolver$p(CameraGestureHelper.this)), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, makeBasic.toBundle(), UserHandle.CURRENT.getIdentifier());
                    } catch (RemoteException e) {
                        Log.w("CameraGestureHelper", "Unable to start camera activity", e);
                    }
                }
            });
        }
        this.centralSurfaces.startLaunchTransitionTimeout();
        this.centralSurfaces.readyForKeyguardDone();
    }
}