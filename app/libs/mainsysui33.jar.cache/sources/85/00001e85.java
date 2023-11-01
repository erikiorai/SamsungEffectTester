package com.android.systemui.navigationbar.buttons;

import com.android.systemui.navigationbar.NavigationBarView;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/DeadZone_Factory.class */
public final class DeadZone_Factory implements Factory<DeadZone> {
    public final Provider<NavigationBarView> viewProvider;

    public DeadZone_Factory(Provider<NavigationBarView> provider) {
        this.viewProvider = provider;
    }

    public static DeadZone_Factory create(Provider<NavigationBarView> provider) {
        return new DeadZone_Factory(provider);
    }

    public static DeadZone newInstance(NavigationBarView navigationBarView) {
        return new DeadZone(navigationBarView);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DeadZone m3456get() {
        return newInstance((NavigationBarView) this.viewProvider.get());
    }
}