package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory.class */
public final class KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory implements Factory<KeyguardSecurityViewFlipper> {
    public final Provider<KeyguardSecurityContainer> containerViewProvider;

    public KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory(Provider<KeyguardSecurityContainer> provider) {
        this.containerViewProvider = provider;
    }

    public static KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory create(Provider<KeyguardSecurityContainer> provider) {
        return new KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory(provider);
    }

    public static KeyguardSecurityViewFlipper providesKeyguardSecurityViewFlipper(KeyguardSecurityContainer keyguardSecurityContainer) {
        return (KeyguardSecurityViewFlipper) Preconditions.checkNotNullFromProvides(KeyguardBouncerModule.providesKeyguardSecurityViewFlipper(keyguardSecurityContainer));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardSecurityViewFlipper m839get() {
        return providesKeyguardSecurityViewFlipper((KeyguardSecurityContainer) this.containerViewProvider.get());
    }
}