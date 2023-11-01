package com.android.systemui.dagger;

import android.content.Context;
import android.util.DisplayMetrics;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/GlobalModule_ProvideDisplayMetricsFactory.class */
public final class GlobalModule_ProvideDisplayMetricsFactory implements Factory<DisplayMetrics> {
    public final Provider<Context> contextProvider;
    public final GlobalModule module;

    public GlobalModule_ProvideDisplayMetricsFactory(GlobalModule globalModule, Provider<Context> provider) {
        this.module = globalModule;
        this.contextProvider = provider;
    }

    public static GlobalModule_ProvideDisplayMetricsFactory create(GlobalModule globalModule, Provider<Context> provider) {
        return new GlobalModule_ProvideDisplayMetricsFactory(globalModule, provider);
    }

    public static DisplayMetrics provideDisplayMetrics(GlobalModule globalModule, Context context) {
        return (DisplayMetrics) Preconditions.checkNotNullFromProvides(globalModule.provideDisplayMetrics(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DisplayMetrics m2370get() {
        return provideDisplayMetrics(this.module, (Context) this.contextProvider.get());
    }
}