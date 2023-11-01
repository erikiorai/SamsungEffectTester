package com.android.systemui.navigationbar;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavBarHelper_Factory.class */
public final class NavBarHelper_Factory implements Factory<NavBarHelper> {
    public final Provider<AccessibilityButtonModeObserver> accessibilityButtonModeObserverProvider;
    public final Provider<AccessibilityButtonTargetsObserver> accessibilityButtonTargetsObserverProvider;
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<AssistManager> assistManagerLazyProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<SystemActions> systemActionsProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public NavBarHelper_Factory(Provider<Context> provider, Provider<AccessibilityManager> provider2, Provider<AccessibilityButtonModeObserver> provider3, Provider<AccessibilityButtonTargetsObserver> provider4, Provider<SystemActions> provider5, Provider<OverviewProxyService> provider6, Provider<AssistManager> provider7, Provider<Optional<CentralSurfaces>> provider8, Provider<KeyguardStateController> provider9, Provider<NavigationModeController> provider10, Provider<UserTracker> provider11, Provider<DumpManager> provider12, Provider<CommandQueue> provider13) {
        this.contextProvider = provider;
        this.accessibilityManagerProvider = provider2;
        this.accessibilityButtonModeObserverProvider = provider3;
        this.accessibilityButtonTargetsObserverProvider = provider4;
        this.systemActionsProvider = provider5;
        this.overviewProxyServiceProvider = provider6;
        this.assistManagerLazyProvider = provider7;
        this.centralSurfacesOptionalLazyProvider = provider8;
        this.keyguardStateControllerProvider = provider9;
        this.navigationModeControllerProvider = provider10;
        this.userTrackerProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.commandQueueProvider = provider13;
    }

    public static NavBarHelper_Factory create(Provider<Context> provider, Provider<AccessibilityManager> provider2, Provider<AccessibilityButtonModeObserver> provider3, Provider<AccessibilityButtonTargetsObserver> provider4, Provider<SystemActions> provider5, Provider<OverviewProxyService> provider6, Provider<AssistManager> provider7, Provider<Optional<CentralSurfaces>> provider8, Provider<KeyguardStateController> provider9, Provider<NavigationModeController> provider10, Provider<UserTracker> provider11, Provider<DumpManager> provider12, Provider<CommandQueue> provider13) {
        return new NavBarHelper_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13);
    }

    public static NavBarHelper newInstance(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, SystemActions systemActions, OverviewProxyService overviewProxyService, Lazy<AssistManager> lazy, Lazy<Optional<CentralSurfaces>> lazy2, KeyguardStateController keyguardStateController, NavigationModeController navigationModeController, UserTracker userTracker, DumpManager dumpManager, CommandQueue commandQueue) {
        return new NavBarHelper(context, accessibilityManager, accessibilityButtonModeObserver, accessibilityButtonTargetsObserver, systemActions, overviewProxyService, lazy, lazy2, keyguardStateController, navigationModeController, userTracker, dumpManager, commandQueue);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavBarHelper m3397get() {
        return newInstance((Context) this.contextProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (AccessibilityButtonModeObserver) this.accessibilityButtonModeObserverProvider.get(), (AccessibilityButtonTargetsObserver) this.accessibilityButtonTargetsObserverProvider.get(), (SystemActions) this.systemActionsProvider.get(), (OverviewProxyService) this.overviewProxyServiceProvider.get(), DoubleCheck.lazy(this.assistManagerLazyProvider), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (CommandQueue) this.commandQueueProvider.get());
    }
}