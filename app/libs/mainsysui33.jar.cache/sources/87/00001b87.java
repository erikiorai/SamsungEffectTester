package com.android.systemui.log.dagger;

import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideBouncerLogBufferFactory.class */
public final class LogModule_ProvideBouncerLogBufferFactory implements Factory<TableLogBuffer> {
    public final Provider<TableLogBufferFactory> factoryProvider;

    public LogModule_ProvideBouncerLogBufferFactory(Provider<TableLogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideBouncerLogBufferFactory create(Provider<TableLogBufferFactory> provider) {
        return new LogModule_ProvideBouncerLogBufferFactory(provider);
    }

    public static TableLogBuffer provideBouncerLogBuffer(TableLogBufferFactory tableLogBufferFactory) {
        return (TableLogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideBouncerLogBuffer(tableLogBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TableLogBuffer m3104get() {
        return provideBouncerLogBuffer((TableLogBufferFactory) this.factoryProvider.get());
    }
}