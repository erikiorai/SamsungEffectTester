package com.android.systemui.dump;

import android.content.Context;
import com.android.systemui.util.io.Files;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/LogBufferEulogizer_Factory.class */
public final class LogBufferEulogizer_Factory implements Factory<LogBufferEulogizer> {
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Files> filesProvider;
    public final Provider<SystemClock> systemClockProvider;

    public LogBufferEulogizer_Factory(Provider<Context> provider, Provider<DumpManager> provider2, Provider<SystemClock> provider3, Provider<Files> provider4) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
        this.systemClockProvider = provider3;
        this.filesProvider = provider4;
    }

    public static LogBufferEulogizer_Factory create(Provider<Context> provider, Provider<DumpManager> provider2, Provider<SystemClock> provider3, Provider<Files> provider4) {
        return new LogBufferEulogizer_Factory(provider, provider2, provider3, provider4);
    }

    public static LogBufferEulogizer newInstance(Context context, DumpManager dumpManager, SystemClock systemClock, Files files) {
        return new LogBufferEulogizer(context, dumpManager, systemClock, files);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBufferEulogizer m2669get() {
        return newInstance((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (Files) this.filesProvider.get());
    }
}