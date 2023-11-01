package com.android.systemui.privacy.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/logging/PrivacyLogger_Factory.class */
public final class PrivacyLogger_Factory implements Factory<PrivacyLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public PrivacyLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static PrivacyLogger_Factory create(Provider<LogBuffer> provider) {
        return new PrivacyLogger_Factory(provider);
    }

    public static PrivacyLogger newInstance(LogBuffer logBuffer) {
        return new PrivacyLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PrivacyLogger m3693get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}