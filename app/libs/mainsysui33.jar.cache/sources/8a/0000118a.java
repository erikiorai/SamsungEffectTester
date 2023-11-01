package com.android.systemui.biometrics;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.UserManager;
import android.view.WindowManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController_Factory.class */
public final class AuthController_Factory implements Factory<AuthController> {
    public final Provider<ActivityTaskManager> activityTaskManagerProvider;
    public final Provider<DelayableExecutor> bgExecutorProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<Execution> executionProvider;
    public final Provider<FaceManager> faceManagerProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<InteractionJankMonitor> jankMonitorProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<SideFpsController> sidefpsControllerFactoryProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<UdfpsController> udfpsControllerFactoryProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<VibratorHelper> vibratorProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public AuthController_Factory(Provider<Context> provider, Provider<Execution> provider2, Provider<CommandQueue> provider3, Provider<ActivityTaskManager> provider4, Provider<WindowManager> provider5, Provider<FingerprintManager> provider6, Provider<FaceManager> provider7, Provider<UdfpsController> provider8, Provider<SideFpsController> provider9, Provider<DisplayManager> provider10, Provider<WakefulnessLifecycle> provider11, Provider<UserManager> provider12, Provider<LockPatternUtils> provider13, Provider<StatusBarStateController> provider14, Provider<InteractionJankMonitor> provider15, Provider<Handler> provider16, Provider<DelayableExecutor> provider17, Provider<VibratorHelper> provider18) {
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.commandQueueProvider = provider3;
        this.activityTaskManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.fingerprintManagerProvider = provider6;
        this.faceManagerProvider = provider7;
        this.udfpsControllerFactoryProvider = provider8;
        this.sidefpsControllerFactoryProvider = provider9;
        this.displayManagerProvider = provider10;
        this.wakefulnessLifecycleProvider = provider11;
        this.userManagerProvider = provider12;
        this.lockPatternUtilsProvider = provider13;
        this.statusBarStateControllerProvider = provider14;
        this.jankMonitorProvider = provider15;
        this.handlerProvider = provider16;
        this.bgExecutorProvider = provider17;
        this.vibratorProvider = provider18;
    }

    public static AuthController_Factory create(Provider<Context> provider, Provider<Execution> provider2, Provider<CommandQueue> provider3, Provider<ActivityTaskManager> provider4, Provider<WindowManager> provider5, Provider<FingerprintManager> provider6, Provider<FaceManager> provider7, Provider<UdfpsController> provider8, Provider<SideFpsController> provider9, Provider<DisplayManager> provider10, Provider<WakefulnessLifecycle> provider11, Provider<UserManager> provider12, Provider<LockPatternUtils> provider13, Provider<StatusBarStateController> provider14, Provider<InteractionJankMonitor> provider15, Provider<Handler> provider16, Provider<DelayableExecutor> provider17, Provider<VibratorHelper> provider18) {
        return new AuthController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18);
    }

    public static AuthController newInstance(Context context, Execution execution, CommandQueue commandQueue, ActivityTaskManager activityTaskManager, WindowManager windowManager, FingerprintManager fingerprintManager, FaceManager faceManager, Provider<UdfpsController> provider, Provider<SideFpsController> provider2, DisplayManager displayManager, WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils, StatusBarStateController statusBarStateController, InteractionJankMonitor interactionJankMonitor, Handler handler, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper) {
        return new AuthController(context, execution, commandQueue, activityTaskManager, windowManager, fingerprintManager, faceManager, provider, provider2, displayManager, wakefulnessLifecycle, userManager, lockPatternUtils, statusBarStateController, interactionJankMonitor, handler, delayableExecutor, vibratorHelper);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AuthController m1535get() {
        return newInstance((Context) this.contextProvider.get(), (Execution) this.executionProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (ActivityTaskManager) this.activityTaskManagerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (FaceManager) this.faceManagerProvider.get(), this.udfpsControllerFactoryProvider, this.sidefpsControllerFactoryProvider, (DisplayManager) this.displayManagerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (UserManager) this.userManagerProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (InteractionJankMonitor) this.jankMonitorProvider.get(), (Handler) this.handlerProvider.get(), (DelayableExecutor) this.bgExecutorProvider.get(), (VibratorHelper) this.vibratorProvider.get());
    }
}