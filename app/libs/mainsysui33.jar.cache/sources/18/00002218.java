package com.android.systemui.qs.footer.domain.interactor;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.FgsManagerController;
import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepository;
import com.android.systemui.qs.footer.data.repository.UserSwitcherRepository;
import com.android.systemui.security.data.repository.SecurityRepository;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.user.domain.interactor.UserInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/interactor/FooterActionsInteractorImpl_Factory.class */
public final class FooterActionsInteractorImpl_Factory implements Factory<FooterActionsInteractorImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<CoroutineDispatcher> bgDispatcherProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<FgsManagerController> fgsManagerControllerProvider;
    public final Provider<ForegroundServicesRepository> foregroundServicesRepositoryProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<QSSecurityFooterUtils> qsSecurityFooterUtilsProvider;
    public final Provider<SecurityRepository> securityRepositoryProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserInteractor> userInteractorProvider;
    public final Provider<UserSwitcherRepository> userSwitcherRepositoryProvider;

    public FooterActionsInteractorImpl_Factory(Provider<ActivityStarter> provider, Provider<MetricsLogger> provider2, Provider<UiEventLogger> provider3, Provider<DeviceProvisionedController> provider4, Provider<QSSecurityFooterUtils> provider5, Provider<FgsManagerController> provider6, Provider<UserInteractor> provider7, Provider<SecurityRepository> provider8, Provider<ForegroundServicesRepository> provider9, Provider<UserSwitcherRepository> provider10, Provider<BroadcastDispatcher> provider11, Provider<CoroutineDispatcher> provider12) {
        this.activityStarterProvider = provider;
        this.metricsLoggerProvider = provider2;
        this.uiEventLoggerProvider = provider3;
        this.deviceProvisionedControllerProvider = provider4;
        this.qsSecurityFooterUtilsProvider = provider5;
        this.fgsManagerControllerProvider = provider6;
        this.userInteractorProvider = provider7;
        this.securityRepositoryProvider = provider8;
        this.foregroundServicesRepositoryProvider = provider9;
        this.userSwitcherRepositoryProvider = provider10;
        this.broadcastDispatcherProvider = provider11;
        this.bgDispatcherProvider = provider12;
    }

    public static FooterActionsInteractorImpl_Factory create(Provider<ActivityStarter> provider, Provider<MetricsLogger> provider2, Provider<UiEventLogger> provider3, Provider<DeviceProvisionedController> provider4, Provider<QSSecurityFooterUtils> provider5, Provider<FgsManagerController> provider6, Provider<UserInteractor> provider7, Provider<SecurityRepository> provider8, Provider<ForegroundServicesRepository> provider9, Provider<UserSwitcherRepository> provider10, Provider<BroadcastDispatcher> provider11, Provider<CoroutineDispatcher> provider12) {
        return new FooterActionsInteractorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static FooterActionsInteractorImpl newInstance(ActivityStarter activityStarter, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, DeviceProvisionedController deviceProvisionedController, QSSecurityFooterUtils qSSecurityFooterUtils, FgsManagerController fgsManagerController, UserInteractor userInteractor, SecurityRepository securityRepository, ForegroundServicesRepository foregroundServicesRepository, UserSwitcherRepository userSwitcherRepository, BroadcastDispatcher broadcastDispatcher, CoroutineDispatcher coroutineDispatcher) {
        return new FooterActionsInteractorImpl(activityStarter, metricsLogger, uiEventLogger, deviceProvisionedController, qSSecurityFooterUtils, fgsManagerController, userInteractor, securityRepository, foregroundServicesRepository, userSwitcherRepository, broadcastDispatcher, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FooterActionsInteractorImpl m3930get() {
        return newInstance((ActivityStarter) this.activityStarterProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (QSSecurityFooterUtils) this.qsSecurityFooterUtilsProvider.get(), (FgsManagerController) this.fgsManagerControllerProvider.get(), (UserInteractor) this.userInteractorProvider.get(), (SecurityRepository) this.securityRepositoryProvider.get(), (ForegroundServicesRepository) this.foregroundServicesRepositoryProvider.get(), (UserSwitcherRepository) this.userSwitcherRepositoryProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get());
    }
}