package com.android.systemui.qs.external;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTileStatePersister_Factory.class */
public final class CustomTileStatePersister_Factory implements Factory<CustomTileStatePersister> {
    public final Provider<Context> contextProvider;

    public CustomTileStatePersister_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static CustomTileStatePersister_Factory create(Provider<Context> provider) {
        return new CustomTileStatePersister_Factory(provider);
    }

    public static CustomTileStatePersister newInstance(Context context) {
        return new CustomTileStatePersister(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CustomTileStatePersister m3893get() {
        return newInstance((Context) this.contextProvider.get());
    }
}