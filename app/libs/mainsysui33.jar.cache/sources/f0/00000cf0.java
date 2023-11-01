package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/BiometricUnlockLogger_Factory.class */
public final class BiometricUnlockLogger_Factory implements Factory<BiometricUnlockLogger> {
    public final Provider<LogBuffer> logBufferProvider;

    public BiometricUnlockLogger_Factory(Provider<LogBuffer> provider) {
        this.logBufferProvider = provider;
    }

    public static BiometricUnlockLogger_Factory create(Provider<LogBuffer> provider) {
        return new BiometricUnlockLogger_Factory(provider);
    }

    public static BiometricUnlockLogger newInstance(LogBuffer logBuffer) {
        return new BiometricUnlockLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BiometricUnlockLogger m853get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}