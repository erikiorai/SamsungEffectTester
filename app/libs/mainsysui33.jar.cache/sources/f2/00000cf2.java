package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/FaceMessageDeferralLogger_Factory.class */
public final class FaceMessageDeferralLogger_Factory implements Factory<FaceMessageDeferralLogger> {
    public final Provider<LogBuffer> logBufferProvider;

    public FaceMessageDeferralLogger_Factory(Provider<LogBuffer> provider) {
        this.logBufferProvider = provider;
    }

    public static FaceMessageDeferralLogger_Factory create(Provider<LogBuffer> provider) {
        return new FaceMessageDeferralLogger_Factory(provider);
    }

    public static FaceMessageDeferralLogger newInstance(LogBuffer logBuffer) {
        return new FaceMessageDeferralLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FaceMessageDeferralLogger m854get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}