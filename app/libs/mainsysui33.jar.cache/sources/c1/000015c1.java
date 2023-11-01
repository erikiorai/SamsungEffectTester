package com.android.systemui.dagger;

import com.android.systemui.ActivityStarterDelegate;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.PluginDependencyProvider;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/PluginModule_ProvideActivityStarterFactory.class */
public final class PluginModule_ProvideActivityStarterFactory implements Factory<ActivityStarter> {
    public final Provider<ActivityStarterDelegate> delegateProvider;
    public final Provider<PluginDependencyProvider> dependencyProvider;

    public PluginModule_ProvideActivityStarterFactory(Provider<ActivityStarterDelegate> provider, Provider<PluginDependencyProvider> provider2) {
        this.delegateProvider = provider;
        this.dependencyProvider = provider2;
    }

    public static PluginModule_ProvideActivityStarterFactory create(Provider<ActivityStarterDelegate> provider, Provider<PluginDependencyProvider> provider2) {
        return new PluginModule_ProvideActivityStarterFactory(provider, provider2);
    }

    public static ActivityStarter provideActivityStarter(ActivityStarterDelegate activityStarterDelegate, PluginDependencyProvider pluginDependencyProvider) {
        return (ActivityStarter) Preconditions.checkNotNullFromProvides(PluginModule.provideActivityStarter(activityStarterDelegate, pluginDependencyProvider));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityStarter m2373get() {
        return provideActivityStarter((ActivityStarterDelegate) this.delegateProvider.get(), (PluginDependencyProvider) this.dependencyProvider.get());
    }
}