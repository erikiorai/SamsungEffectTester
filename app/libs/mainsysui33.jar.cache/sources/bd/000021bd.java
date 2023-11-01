package com.android.systemui.qs.external;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/PackageManagerAdapter_Factory.class */
public final class PackageManagerAdapter_Factory implements Factory<PackageManagerAdapter> {
    public final Provider<Context> contextProvider;

    public PackageManagerAdapter_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static PackageManagerAdapter_Factory create(Provider<Context> provider) {
        return new PackageManagerAdapter_Factory(provider);
    }

    public static PackageManagerAdapter newInstance(Context context) {
        return new PackageManagerAdapter(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PackageManagerAdapter m3895get() {
        return newInstance((Context) this.contextProvider.get());
    }
}