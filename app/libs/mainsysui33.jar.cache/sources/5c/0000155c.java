package com.android.systemui.dagger;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideAccessibilityManagerFactory.class */
public final class FrameworkServicesModule_ProvideAccessibilityManagerFactory implements Factory<AccessibilityManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideAccessibilityManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAccessibilityManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideAccessibilityManagerFactory(provider);
    }

    public static AccessibilityManager provideAccessibilityManager(Context context) {
        return (AccessibilityManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideAccessibilityManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AccessibilityManager m2264get() {
        return provideAccessibilityManager((Context) this.contextProvider.get());
    }
}