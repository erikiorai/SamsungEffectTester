package com.android.systemui.dreams;

import android.app.AlarmManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.time.DateFormatUtil;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarViewController_Factory.class */
public final class DreamOverlayStatusBarViewController_Factory implements Factory<DreamOverlayStatusBarViewController> {
    public final Provider<AlarmManager> alarmManagerProvider;
    public final Provider<ConnectivityManager> connectivityManagerProvider;
    public final Provider<DateFormatUtil> dateFormatUtilProvider;
    public final Provider<Optional<DreamOverlayNotificationCountProvider>> dreamOverlayNotificationCountProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<NextAlarmController> nextAlarmControllerProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<IndividualSensorPrivacyController> sensorPrivacyControllerProvider;
    public final Provider<DreamOverlayStatusBarItemsProvider> statusBarItemsProvider;
    public final Provider<StatusBarWindowStateController> statusBarWindowStateControllerProvider;
    public final Provider<TouchInsetManager.TouchInsetSession> touchInsetSessionProvider;
    public final Provider<DreamOverlayStatusBarView> viewProvider;
    public final Provider<ZenModeController> zenModeControllerProvider;

    public DreamOverlayStatusBarViewController_Factory(Provider<DreamOverlayStatusBarView> provider, Provider<Resources> provider2, Provider<Executor> provider3, Provider<ConnectivityManager> provider4, Provider<TouchInsetManager.TouchInsetSession> provider5, Provider<AlarmManager> provider6, Provider<NextAlarmController> provider7, Provider<DateFormatUtil> provider8, Provider<IndividualSensorPrivacyController> provider9, Provider<Optional<DreamOverlayNotificationCountProvider>> provider10, Provider<ZenModeController> provider11, Provider<StatusBarWindowStateController> provider12, Provider<DreamOverlayStatusBarItemsProvider> provider13, Provider<DreamOverlayStateController> provider14) {
        this.viewProvider = provider;
        this.resourcesProvider = provider2;
        this.mainExecutorProvider = provider3;
        this.connectivityManagerProvider = provider4;
        this.touchInsetSessionProvider = provider5;
        this.alarmManagerProvider = provider6;
        this.nextAlarmControllerProvider = provider7;
        this.dateFormatUtilProvider = provider8;
        this.sensorPrivacyControllerProvider = provider9;
        this.dreamOverlayNotificationCountProvider = provider10;
        this.zenModeControllerProvider = provider11;
        this.statusBarWindowStateControllerProvider = provider12;
        this.statusBarItemsProvider = provider13;
        this.dreamOverlayStateControllerProvider = provider14;
    }

    public static DreamOverlayStatusBarViewController_Factory create(Provider<DreamOverlayStatusBarView> provider, Provider<Resources> provider2, Provider<Executor> provider3, Provider<ConnectivityManager> provider4, Provider<TouchInsetManager.TouchInsetSession> provider5, Provider<AlarmManager> provider6, Provider<NextAlarmController> provider7, Provider<DateFormatUtil> provider8, Provider<IndividualSensorPrivacyController> provider9, Provider<Optional<DreamOverlayNotificationCountProvider>> provider10, Provider<ZenModeController> provider11, Provider<StatusBarWindowStateController> provider12, Provider<DreamOverlayStatusBarItemsProvider> provider13, Provider<DreamOverlayStateController> provider14) {
        return new DreamOverlayStatusBarViewController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static DreamOverlayStatusBarViewController newInstance(DreamOverlayStatusBarView dreamOverlayStatusBarView, Resources resources, Executor executor, ConnectivityManager connectivityManager, TouchInsetManager.TouchInsetSession touchInsetSession, AlarmManager alarmManager, NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil, IndividualSensorPrivacyController individualSensorPrivacyController, Optional<DreamOverlayNotificationCountProvider> optional, ZenModeController zenModeController, StatusBarWindowStateController statusBarWindowStateController, DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, DreamOverlayStateController dreamOverlayStateController) {
        return new DreamOverlayStatusBarViewController(dreamOverlayStatusBarView, resources, executor, connectivityManager, touchInsetSession, alarmManager, nextAlarmController, dateFormatUtil, individualSensorPrivacyController, optional, zenModeController, statusBarWindowStateController, dreamOverlayStatusBarItemsProvider, dreamOverlayStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayStatusBarViewController m2574get() {
        return newInstance((DreamOverlayStatusBarView) this.viewProvider.get(), (Resources) this.resourcesProvider.get(), (Executor) this.mainExecutorProvider.get(), (ConnectivityManager) this.connectivityManagerProvider.get(), (TouchInsetManager.TouchInsetSession) this.touchInsetSessionProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (NextAlarmController) this.nextAlarmControllerProvider.get(), (DateFormatUtil) this.dateFormatUtilProvider.get(), (IndividualSensorPrivacyController) this.sensorPrivacyControllerProvider.get(), (Optional) this.dreamOverlayNotificationCountProvider.get(), (ZenModeController) this.zenModeControllerProvider.get(), (StatusBarWindowStateController) this.statusBarWindowStateControllerProvider.get(), (DreamOverlayStatusBarItemsProvider) this.statusBarItemsProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get());
    }
}