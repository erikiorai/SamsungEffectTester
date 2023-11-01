package com.android.systemui.keyguard;

import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.wm.shell.transition.ShellTransitions;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardService_Factory.class */
public final class KeyguardService_Factory implements Factory<KeyguardService> {
    public final Provider<KeyguardLifecyclesDispatcher> keyguardLifecyclesDispatcherProvider;
    public final Provider<KeyguardViewMediator> keyguardViewMediatorProvider;
    public final Provider<ScreenOnCoordinator> screenOnCoordinatorProvider;
    public final Provider<ShellTransitions> shellTransitionsProvider;

    public KeyguardService_Factory(Provider<KeyguardViewMediator> provider, Provider<KeyguardLifecyclesDispatcher> provider2, Provider<ScreenOnCoordinator> provider3, Provider<ShellTransitions> provider4) {
        this.keyguardViewMediatorProvider = provider;
        this.keyguardLifecyclesDispatcherProvider = provider2;
        this.screenOnCoordinatorProvider = provider3;
        this.shellTransitionsProvider = provider4;
    }

    public static KeyguardService_Factory create(Provider<KeyguardViewMediator> provider, Provider<KeyguardLifecyclesDispatcher> provider2, Provider<ScreenOnCoordinator> provider3, Provider<ShellTransitions> provider4) {
        return new KeyguardService_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardService newInstance(KeyguardViewMediator keyguardViewMediator, KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher, ScreenOnCoordinator screenOnCoordinator, ShellTransitions shellTransitions) {
        return new KeyguardService(keyguardViewMediator, keyguardLifecyclesDispatcher, screenOnCoordinator, shellTransitions);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardService m2821get() {
        return newInstance((KeyguardViewMediator) this.keyguardViewMediatorProvider.get(), (KeyguardLifecyclesDispatcher) this.keyguardLifecyclesDispatcherProvider.get(), (ScreenOnCoordinator) this.screenOnCoordinatorProvider.get(), (ShellTransitions) this.shellTransitionsProvider.get());
    }
}