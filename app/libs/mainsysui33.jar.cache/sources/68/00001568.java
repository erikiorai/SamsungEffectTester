package com.android.systemui.dagger;

import android.content.ClipboardManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideClipboardManagerFactory.class */
public final class FrameworkServicesModule_ProvideClipboardManagerFactory implements Factory<ClipboardManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideClipboardManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideClipboardManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideClipboardManagerFactory(provider);
    }

    public static ClipboardManager provideClipboardManager(Context context) {
        return (ClipboardManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideClipboardManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardManager m2277get() {
        return provideClipboardManager((Context) this.contextProvider.get());
    }
}