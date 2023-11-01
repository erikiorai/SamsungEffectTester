package com.android.systemui.dagger;

import android.app.INotificationManager;
import android.content.Context;
import android.service.dreams.IDreamManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubbles;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SystemUIModule_ProvideBubblesManagerFactory.class */
public final class SystemUIModule_ProvideBubblesManagerFactory implements Factory<Optional<BubblesManager>> {
    public final Provider<Optional<Bubbles>> bubblesOptionalProvider;
    public final Provider<Context> contextProvider;
    public final Provider<IDreamManager> dreamManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<NotificationInterruptStateProvider> interruptionStateProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<CommonNotifCollection> notifCollectionProvider;
    public final Provider<NotifPipeline> notifPipelineProvider;
    public final Provider<NotificationLockscreenUserManager> notifUserManagerProvider;
    public final Provider<INotificationManager> notificationManagerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<IStatusBarService> statusBarServiceProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<Executor> sysuiMainExecutorProvider;
    public final Provider<NotificationVisibilityProvider> visibilityProvider;
    public final Provider<ZenModeController> zenModeControllerProvider;

    public SystemUIModule_ProvideBubblesManagerFactory(Provider<Context> provider, Provider<Optional<Bubbles>> provider2, Provider<NotificationShadeWindowController> provider3, Provider<KeyguardStateController> provider4, Provider<ShadeController> provider5, Provider<IStatusBarService> provider6, Provider<INotificationManager> provider7, Provider<IDreamManager> provider8, Provider<NotificationVisibilityProvider> provider9, Provider<NotificationInterruptStateProvider> provider10, Provider<ZenModeController> provider11, Provider<NotificationLockscreenUserManager> provider12, Provider<CommonNotifCollection> provider13, Provider<NotifPipeline> provider14, Provider<SysUiState> provider15, Provider<FeatureFlags> provider16, Provider<Executor> provider17) {
        this.contextProvider = provider;
        this.bubblesOptionalProvider = provider2;
        this.notificationShadeWindowControllerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.shadeControllerProvider = provider5;
        this.statusBarServiceProvider = provider6;
        this.notificationManagerProvider = provider7;
        this.dreamManagerProvider = provider8;
        this.visibilityProvider = provider9;
        this.interruptionStateProvider = provider10;
        this.zenModeControllerProvider = provider11;
        this.notifUserManagerProvider = provider12;
        this.notifCollectionProvider = provider13;
        this.notifPipelineProvider = provider14;
        this.sysUiStateProvider = provider15;
        this.featureFlagsProvider = provider16;
        this.sysuiMainExecutorProvider = provider17;
    }

    public static SystemUIModule_ProvideBubblesManagerFactory create(Provider<Context> provider, Provider<Optional<Bubbles>> provider2, Provider<NotificationShadeWindowController> provider3, Provider<KeyguardStateController> provider4, Provider<ShadeController> provider5, Provider<IStatusBarService> provider6, Provider<INotificationManager> provider7, Provider<IDreamManager> provider8, Provider<NotificationVisibilityProvider> provider9, Provider<NotificationInterruptStateProvider> provider10, Provider<ZenModeController> provider11, Provider<NotificationLockscreenUserManager> provider12, Provider<CommonNotifCollection> provider13, Provider<NotifPipeline> provider14, Provider<SysUiState> provider15, Provider<FeatureFlags> provider16, Provider<Executor> provider17) {
        return new SystemUIModule_ProvideBubblesManagerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static Optional<BubblesManager> provideBubblesManager(Context context, Optional<Bubbles> optional, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, ShadeController shadeController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, IDreamManager iDreamManager, NotificationVisibilityProvider notificationVisibilityProvider, NotificationInterruptStateProvider notificationInterruptStateProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, FeatureFlags featureFlags, Executor executor) {
        return (Optional) Preconditions.checkNotNullFromProvides(SystemUIModule.provideBubblesManager(context, optional, notificationShadeWindowController, keyguardStateController, shadeController, iStatusBarService, iNotificationManager, iDreamManager, notificationVisibilityProvider, notificationInterruptStateProvider, zenModeController, notificationLockscreenUserManager, commonNotifCollection, notifPipeline, sysUiState, featureFlags, executor));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<BubblesManager> get() {
        return provideBubblesManager((Context) this.contextProvider.get(), (Optional) this.bubblesOptionalProvider.get(), (NotificationShadeWindowController) this.notificationShadeWindowControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ShadeController) this.shadeControllerProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (INotificationManager) this.notificationManagerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (NotificationVisibilityProvider) this.visibilityProvider.get(), (NotificationInterruptStateProvider) this.interruptionStateProvider.get(), (ZenModeController) this.zenModeControllerProvider.get(), (NotificationLockscreenUserManager) this.notifUserManagerProvider.get(), (CommonNotifCollection) this.notifCollectionProvider.get(), (NotifPipeline) this.notifPipelineProvider.get(), (SysUiState) this.sysUiStateProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Executor) this.sysuiMainExecutorProvider.get());
    }
}