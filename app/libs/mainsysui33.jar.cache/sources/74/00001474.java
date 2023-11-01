package com.android.systemui.controls.ui;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskViewFactory;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlActionCoordinatorImpl_Factory.class */
public final class ControlActionCoordinatorImpl_Factory implements Factory<ControlActionCoordinatorImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<DelayableExecutor> bgExecutorProvider;
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ControlsMetricsLogger> controlsMetricsLoggerProvider;
    public final Provider<ControlsSettingsDialogManager> controlsSettingsDialogManagerProvider;
    public final Provider<ControlsSettingsRepository> controlsSettingsRepositoryProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Optional<TaskViewFactory>> taskViewFactoryProvider;
    public final Provider<DelayableExecutor> uiExecutorProvider;
    public final Provider<VibratorHelper> vibratorProvider;

    public ControlActionCoordinatorImpl_Factory(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<DelayableExecutor> provider3, Provider<ActivityStarter> provider4, Provider<BroadcastSender> provider5, Provider<KeyguardStateController> provider6, Provider<Optional<TaskViewFactory>> provider7, Provider<ControlsMetricsLogger> provider8, Provider<VibratorHelper> provider9, Provider<ControlsSettingsRepository> provider10, Provider<ControlsSettingsDialogManager> provider11, Provider<FeatureFlags> provider12) {
        this.contextProvider = provider;
        this.bgExecutorProvider = provider2;
        this.uiExecutorProvider = provider3;
        this.activityStarterProvider = provider4;
        this.broadcastSenderProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
        this.taskViewFactoryProvider = provider7;
        this.controlsMetricsLoggerProvider = provider8;
        this.vibratorProvider = provider9;
        this.controlsSettingsRepositoryProvider = provider10;
        this.controlsSettingsDialogManagerProvider = provider11;
        this.featureFlagsProvider = provider12;
    }

    public static ControlActionCoordinatorImpl_Factory create(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<DelayableExecutor> provider3, Provider<ActivityStarter> provider4, Provider<BroadcastSender> provider5, Provider<KeyguardStateController> provider6, Provider<Optional<TaskViewFactory>> provider7, Provider<ControlsMetricsLogger> provider8, Provider<VibratorHelper> provider9, Provider<ControlsSettingsRepository> provider10, Provider<ControlsSettingsDialogManager> provider11, Provider<FeatureFlags> provider12) {
        return new ControlActionCoordinatorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static ControlActionCoordinatorImpl newInstance(Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, ActivityStarter activityStarter, BroadcastSender broadcastSender, KeyguardStateController keyguardStateController, Optional<TaskViewFactory> optional, ControlsMetricsLogger controlsMetricsLogger, VibratorHelper vibratorHelper, ControlsSettingsRepository controlsSettingsRepository, ControlsSettingsDialogManager controlsSettingsDialogManager, FeatureFlags featureFlags) {
        return new ControlActionCoordinatorImpl(context, delayableExecutor, delayableExecutor2, activityStarter, broadcastSender, keyguardStateController, optional, controlsMetricsLogger, vibratorHelper, controlsSettingsRepository, controlsSettingsDialogManager, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlActionCoordinatorImpl m1855get() {
        return newInstance((Context) this.contextProvider.get(), (DelayableExecutor) this.bgExecutorProvider.get(), (DelayableExecutor) this.uiExecutorProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (Optional) this.taskViewFactoryProvider.get(), (ControlsMetricsLogger) this.controlsMetricsLoggerProvider.get(), (VibratorHelper) this.vibratorProvider.get(), (ControlsSettingsRepository) this.controlsSettingsRepositoryProvider.get(), (ControlsSettingsDialogManager) this.controlsSettingsDialogManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}