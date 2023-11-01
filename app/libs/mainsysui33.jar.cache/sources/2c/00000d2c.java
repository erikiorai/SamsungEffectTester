package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/KeyguardUpdateMonitorLogger_Factory.class */
public final class KeyguardUpdateMonitorLogger_Factory implements Factory<KeyguardUpdateMonitorLogger> {
    public final Provider<LogBuffer> logBufferProvider;

    public KeyguardUpdateMonitorLogger_Factory(Provider<LogBuffer> provider) {
        this.logBufferProvider = provider;
    }

    public static KeyguardUpdateMonitorLogger_Factory create(Provider<LogBuffer> provider) {
        return new KeyguardUpdateMonitorLogger_Factory(provider);
    }

    public static KeyguardUpdateMonitorLogger newInstance(LogBuffer logBuffer) {
        return new KeyguardUpdateMonitorLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardUpdateMonitorLogger m906get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}