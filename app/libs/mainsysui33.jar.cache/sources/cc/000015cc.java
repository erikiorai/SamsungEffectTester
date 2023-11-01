package com.android.systemui.dagger;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideLeakReportEmailFactory.class */
public final class ReferenceSystemUIModule_ProvideLeakReportEmailFactory implements Factory<String> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideLeakReportEmailFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final ReferenceSystemUIModule_ProvideLeakReportEmailFactory INSTANCE = new ReferenceSystemUIModule_ProvideLeakReportEmailFactory();
    }

    public static ReferenceSystemUIModule_ProvideLeakReportEmailFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static String provideLeakReportEmail() {
        return ReferenceSystemUIModule.provideLeakReportEmail();
    }

    /* JADX DEBUG: Method merged with bridge method */
    public String get() {
        return provideLeakReportEmail();
    }
}