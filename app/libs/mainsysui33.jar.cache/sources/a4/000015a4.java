package com.android.systemui.dagger;

import android.app.smartspace.SmartspaceManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideSmartspaceManagerFactory.class */
public final class FrameworkServicesModule_ProvideSmartspaceManagerFactory implements Factory<SmartspaceManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideSmartspaceManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideSmartspaceManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideSmartspaceManagerFactory(provider);
    }

    public static SmartspaceManager provideSmartspaceManager(Context context) {
        return (SmartspaceManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideSmartspaceManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SmartspaceManager m2350get() {
        return provideSmartspaceManager((Context) this.contextProvider.get());
    }
}