package com.android.systemui.qs;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.SecurityController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSSecurityFooterUtils_Factory.class */
public final class QSSecurityFooterUtils_Factory implements Factory<QSSecurityFooterUtils> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> bgLooperProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DevicePolicyManager> devicePolicyManagerProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<SecurityController> securityControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public QSSecurityFooterUtils_Factory(Provider<Context> provider, Provider<DevicePolicyManager> provider2, Provider<UserTracker> provider3, Provider<Handler> provider4, Provider<ActivityStarter> provider5, Provider<SecurityController> provider6, Provider<Looper> provider7, Provider<DialogLaunchAnimator> provider8) {
        this.contextProvider = provider;
        this.devicePolicyManagerProvider = provider2;
        this.userTrackerProvider = provider3;
        this.mainHandlerProvider = provider4;
        this.activityStarterProvider = provider5;
        this.securityControllerProvider = provider6;
        this.bgLooperProvider = provider7;
        this.dialogLaunchAnimatorProvider = provider8;
    }

    public static QSSecurityFooterUtils_Factory create(Provider<Context> provider, Provider<DevicePolicyManager> provider2, Provider<UserTracker> provider3, Provider<Handler> provider4, Provider<ActivityStarter> provider5, Provider<SecurityController> provider6, Provider<Looper> provider7, Provider<DialogLaunchAnimator> provider8) {
        return new QSSecurityFooterUtils_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static QSSecurityFooterUtils newInstance(Context context, DevicePolicyManager devicePolicyManager, UserTracker userTracker, Handler handler, ActivityStarter activityStarter, SecurityController securityController, Looper looper, DialogLaunchAnimator dialogLaunchAnimator) {
        return new QSSecurityFooterUtils(context, devicePolicyManager, userTracker, handler, activityStarter, securityController, looper, dialogLaunchAnimator);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSSecurityFooterUtils m3790get() {
        return newInstance((Context) this.contextProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SecurityController) this.securityControllerProvider.get(), (Looper) this.bgLooperProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get());
    }
}