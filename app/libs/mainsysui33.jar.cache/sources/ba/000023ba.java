package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.dagger.ContextComponentHelper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/RecentsModule_ProvideRecentsImplFactory.class */
public final class RecentsModule_ProvideRecentsImplFactory implements Factory<RecentsImplementation> {
    public final Provider<ContextComponentHelper> componentHelperProvider;
    public final Provider<Context> contextProvider;

    public RecentsModule_ProvideRecentsImplFactory(Provider<Context> provider, Provider<ContextComponentHelper> provider2) {
        this.contextProvider = provider;
        this.componentHelperProvider = provider2;
    }

    public static RecentsModule_ProvideRecentsImplFactory create(Provider<Context> provider, Provider<ContextComponentHelper> provider2) {
        return new RecentsModule_ProvideRecentsImplFactory(provider, provider2);
    }

    public static RecentsImplementation provideRecentsImpl(Context context, ContextComponentHelper contextComponentHelper) {
        return (RecentsImplementation) Preconditions.checkNotNullFromProvides(RecentsModule.provideRecentsImpl(context, contextComponentHelper));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RecentsImplementation m4155get() {
        return provideRecentsImpl((Context) this.contextProvider.get(), (ContextComponentHelper) this.componentHelperProvider.get());
    }
}