package com.android.systemui.dagger;

import android.content.Context;
import android.os.storage.StorageManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideStorageManagerFactory.class */
public final class FrameworkServicesModule_ProvideStorageManagerFactory implements Factory<StorageManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideStorageManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideStorageManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideStorageManagerFactory(provider);
    }

    public static StorageManager provideStorageManager(Context context) {
        return (StorageManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideStorageManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public StorageManager m2352get() {
        return provideStorageManager((Context) this.contextProvider.get());
    }
}