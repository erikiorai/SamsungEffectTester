package com.android.systemui.dagger;

import android.content.ContentResolver;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideContentResolverFactory.class */
public final class FrameworkServicesModule_ProvideContentResolverFactory implements Factory<ContentResolver> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideContentResolverFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideContentResolverFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideContentResolverFactory(provider);
    }

    public static ContentResolver provideContentResolver(Context context) {
        return (ContentResolver) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideContentResolver(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ContentResolver m2280get() {
        return provideContentResolver((Context) this.contextProvider.get());
    }
}