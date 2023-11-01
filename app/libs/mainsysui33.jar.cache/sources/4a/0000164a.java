package com.android.systemui.doze;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeLogger_Factory.class */
public final class DozeLogger_Factory implements Factory<DozeLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public DozeLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static DozeLogger_Factory create(Provider<LogBuffer> provider) {
        return new DozeLogger_Factory(provider);
    }

    public static DozeLogger newInstance(LogBuffer logBuffer) {
        return new DozeLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeLogger m2460get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}