package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/KeyguardLogger_Factory.class */
public final class KeyguardLogger_Factory implements Factory<KeyguardLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public KeyguardLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static KeyguardLogger_Factory create(Provider<LogBuffer> provider) {
        return new KeyguardLogger_Factory(provider);
    }

    public static KeyguardLogger newInstance(LogBuffer logBuffer) {
        return new KeyguardLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardLogger m856get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}