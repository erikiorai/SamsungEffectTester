package com.android.systemui.dagger;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SharedLibraryModule_ProvideActivityManagerWrapperFactory.class */
public final class SharedLibraryModule_ProvideActivityManagerWrapperFactory implements Factory<ActivityManagerWrapper> {
    public final SharedLibraryModule module;

    public SharedLibraryModule_ProvideActivityManagerWrapperFactory(SharedLibraryModule sharedLibraryModule) {
        this.module = sharedLibraryModule;
    }

    public static SharedLibraryModule_ProvideActivityManagerWrapperFactory create(SharedLibraryModule sharedLibraryModule) {
        return new SharedLibraryModule_ProvideActivityManagerWrapperFactory(sharedLibraryModule);
    }

    public static ActivityManagerWrapper provideActivityManagerWrapper(SharedLibraryModule sharedLibraryModule) {
        return (ActivityManagerWrapper) Preconditions.checkNotNullFromProvides(sharedLibraryModule.provideActivityManagerWrapper());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityManagerWrapper m2386get() {
        return provideActivityManagerWrapper(this.module);
    }
}