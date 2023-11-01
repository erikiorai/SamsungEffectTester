package com.android.systemui.qs.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/logging/QSLogger_Factory.class */
public final class QSLogger_Factory implements Factory<QSLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public QSLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static QSLogger_Factory create(Provider<LogBuffer> provider) {
        return new QSLogger_Factory(provider);
    }

    public static QSLogger newInstance(LogBuffer logBuffer) {
        return new QSLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSLogger m3957get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}