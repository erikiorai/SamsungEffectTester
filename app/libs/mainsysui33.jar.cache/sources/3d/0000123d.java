package com.android.systemui.biometrics.dagger;

import com.android.systemui.util.concurrency.ThreadFactory;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/dagger/BiometricsModule.class */
public final class BiometricsModule {
    public static final BiometricsModule INSTANCE = new BiometricsModule();

    public static final Executor providesPluginExecutor(ThreadFactory threadFactory) {
        return threadFactory.buildExecutorOnNewThread("biometrics");
    }
}