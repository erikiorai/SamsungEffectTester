package com.android.systemui.biometrics;

import android.content.res.Resources;
import com.android.keyguard.logging.FaceMessageDeferralLogger;
import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/FaceHelpMessageDeferral_Factory.class */
public final class FaceHelpMessageDeferral_Factory implements Factory<FaceHelpMessageDeferral> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FaceMessageDeferralLogger> logBufferProvider;
    public final Provider<Resources> resourcesProvider;

    public FaceHelpMessageDeferral_Factory(Provider<Resources> provider, Provider<FaceMessageDeferralLogger> provider2, Provider<DumpManager> provider3) {
        this.resourcesProvider = provider;
        this.logBufferProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static FaceHelpMessageDeferral_Factory create(Provider<Resources> provider, Provider<FaceMessageDeferralLogger> provider2, Provider<DumpManager> provider3) {
        return new FaceHelpMessageDeferral_Factory(provider, provider2, provider3);
    }

    public static FaceHelpMessageDeferral newInstance(Resources resources, FaceMessageDeferralLogger faceMessageDeferralLogger, DumpManager dumpManager) {
        return new FaceHelpMessageDeferral(resources, faceMessageDeferralLogger, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FaceHelpMessageDeferral m1547get() {
        return newInstance((Resources) this.resourcesProvider.get(), (FaceMessageDeferralLogger) this.logBufferProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}