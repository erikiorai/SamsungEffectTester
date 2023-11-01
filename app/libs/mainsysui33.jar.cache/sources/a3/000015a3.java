package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.ShortcutManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideShortcutManagerFactory.class */
public final class FrameworkServicesModule_ProvideShortcutManagerFactory implements Factory<ShortcutManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideShortcutManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideShortcutManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideShortcutManagerFactory(provider);
    }

    public static ShortcutManager provideShortcutManager(Context context) {
        return (ShortcutManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideShortcutManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ShortcutManager m2349get() {
        return provideShortcutManager((Context) this.contextProvider.get());
    }
}