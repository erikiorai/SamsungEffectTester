package com.android.systemui;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ActivityStarterDelegate_Factory.class */
public final class ActivityStarterDelegate_Factory implements Factory<ActivityStarterDelegate> {
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;

    public ActivityStarterDelegate_Factory(Provider<Optional<CentralSurfaces>> provider) {
        this.centralSurfacesOptionalLazyProvider = provider;
    }

    public static ActivityStarterDelegate_Factory create(Provider<Optional<CentralSurfaces>> provider) {
        return new ActivityStarterDelegate_Factory(provider);
    }

    public static ActivityStarterDelegate newInstance(Lazy<Optional<CentralSurfaces>> lazy) {
        return new ActivityStarterDelegate(lazy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityStarterDelegate m1228get() {
        return newInstance(DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider));
    }
}