package com.android.systemui.flags;

import com.android.internal.statusbar.IStatusBarService;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/SystemExitRestarter_Factory.class */
public final class SystemExitRestarter_Factory implements Factory<SystemExitRestarter> {
    public final Provider<IStatusBarService> barServiceProvider;

    public SystemExitRestarter_Factory(Provider<IStatusBarService> provider) {
        this.barServiceProvider = provider;
    }

    public static SystemExitRestarter_Factory create(Provider<IStatusBarService> provider) {
        return new SystemExitRestarter_Factory(provider);
    }

    public static SystemExitRestarter newInstance(IStatusBarService iStatusBarService) {
        return new SystemExitRestarter(iStatusBarService);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SystemExitRestarter m2710get() {
        return newInstance((IStatusBarService) this.barServiceProvider.get());
    }
}