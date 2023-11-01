package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardSecurityContainer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory.class */
public final class KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory implements Factory<KeyguardSecurityContainer> {
    public final Provider<KeyguardHostView> hostViewProvider;

    public KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory(Provider<KeyguardHostView> provider) {
        this.hostViewProvider = provider;
    }

    public static KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory create(Provider<KeyguardHostView> provider) {
        return new KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory(provider);
    }

    public static KeyguardSecurityContainer providesKeyguardSecurityContainer(KeyguardHostView keyguardHostView) {
        return (KeyguardSecurityContainer) Preconditions.checkNotNullFromProvides(KeyguardBouncerModule.providesKeyguardSecurityContainer(keyguardHostView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardSecurityContainer m838get() {
        return providesKeyguardSecurityContainer((KeyguardHostView) this.hostViewProvider.get());
    }
}