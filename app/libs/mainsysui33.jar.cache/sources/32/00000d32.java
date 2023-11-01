package com.android.keyguard.mediator;

import android.os.Handler;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/mediator/ScreenOnCoordinator_Factory.class */
public final class ScreenOnCoordinator_Factory implements Factory<ScreenOnCoordinator> {
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<Optional<SysUIUnfoldComponent>> unfoldComponentProvider;

    public ScreenOnCoordinator_Factory(Provider<Optional<SysUIUnfoldComponent>> provider, Provider<Handler> provider2) {
        this.unfoldComponentProvider = provider;
        this.mainHandlerProvider = provider2;
    }

    public static ScreenOnCoordinator_Factory create(Provider<Optional<SysUIUnfoldComponent>> provider, Provider<Handler> provider2) {
        return new ScreenOnCoordinator_Factory(provider, provider2);
    }

    public static ScreenOnCoordinator newInstance(Optional<SysUIUnfoldComponent> optional, Handler handler) {
        return new ScreenOnCoordinator(optional, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenOnCoordinator m909get() {
        return newInstance((Optional) this.unfoldComponentProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}