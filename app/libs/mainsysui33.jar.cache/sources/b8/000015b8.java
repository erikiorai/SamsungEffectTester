package com.android.systemui.dagger;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/GlobalModule_ProvideApplicationContextFactory.class */
public final class GlobalModule_ProvideApplicationContextFactory implements Factory<Context> {
    public final Provider<Context> contextProvider;
    public final GlobalModule module;

    public GlobalModule_ProvideApplicationContextFactory(GlobalModule globalModule, Provider<Context> provider) {
        this.module = globalModule;
        this.contextProvider = provider;
    }

    public static GlobalModule_ProvideApplicationContextFactory create(GlobalModule globalModule, Provider<Context> provider) {
        return new GlobalModule_ProvideApplicationContextFactory(globalModule, provider);
    }

    public static Context provideApplicationContext(GlobalModule globalModule, Context context) {
        return (Context) Preconditions.checkNotNullFromProvides(globalModule.provideApplicationContext(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Context m2369get() {
        return provideApplicationContext(this.module, (Context) this.contextProvider.get());
    }
}