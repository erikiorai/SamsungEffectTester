package com.android.systemui.flags;

import android.content.Context;
import android.os.Handler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagsModule_ProvideFlagManagerFactory.class */
public final class FlagsModule_ProvideFlagManagerFactory implements Factory<FlagManager> {
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;

    public FlagsModule_ProvideFlagManagerFactory(Provider<Context> provider, Provider<Handler> provider2) {
        this.contextProvider = provider;
        this.handlerProvider = provider2;
    }

    public static FlagsModule_ProvideFlagManagerFactory create(Provider<Context> provider, Provider<Handler> provider2) {
        return new FlagsModule_ProvideFlagManagerFactory(provider, provider2);
    }

    public static FlagManager provideFlagManager(Context context, Handler handler) {
        return (FlagManager) Preconditions.checkNotNullFromProvides(FlagsModule.provideFlagManager(context, handler));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FlagManager m2696get() {
        return provideFlagManager((Context) this.contextProvider.get(), (Handler) this.handlerProvider.get());
    }
}