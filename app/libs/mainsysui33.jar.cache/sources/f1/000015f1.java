package com.android.systemui.decor;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/PrivacyDotDecorProviderFactory_Factory.class */
public final class PrivacyDotDecorProviderFactory_Factory implements Factory<PrivacyDotDecorProviderFactory> {
    public final Provider<Resources> resProvider;

    public PrivacyDotDecorProviderFactory_Factory(Provider<Resources> provider) {
        this.resProvider = provider;
    }

    public static PrivacyDotDecorProviderFactory_Factory create(Provider<Resources> provider) {
        return new PrivacyDotDecorProviderFactory_Factory(provider);
    }

    public static PrivacyDotDecorProviderFactory newInstance(Resources resources) {
        return new PrivacyDotDecorProviderFactory(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PrivacyDotDecorProviderFactory m2394get() {
        return newInstance((Resources) this.resProvider.get());
    }
}