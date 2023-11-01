package com.android.systemui.dagger;

import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/AndroidInternalsModule_ProvideUiEventLoggerFactory.class */
public final class AndroidInternalsModule_ProvideUiEventLoggerFactory implements Factory<UiEventLogger> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/AndroidInternalsModule_ProvideUiEventLoggerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final AndroidInternalsModule_ProvideUiEventLoggerFactory INSTANCE = new AndroidInternalsModule_ProvideUiEventLoggerFactory();
    }

    public static AndroidInternalsModule_ProvideUiEventLoggerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static UiEventLogger provideUiEventLogger() {
        return (UiEventLogger) Preconditions.checkNotNullFromProvides(AndroidInternalsModule.provideUiEventLogger());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UiEventLogger m1891get() {
        return provideUiEventLogger();
    }
}