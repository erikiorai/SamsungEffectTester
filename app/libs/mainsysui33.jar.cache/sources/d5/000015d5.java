package com.android.systemui.dagger;

import com.android.systemui.shared.system.TaskStackChangeListeners;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SharedLibraryModule_ProvideTaskStackChangeListenersFactory.class */
public final class SharedLibraryModule_ProvideTaskStackChangeListenersFactory implements Factory<TaskStackChangeListeners> {
    public final SharedLibraryModule module;

    public SharedLibraryModule_ProvideTaskStackChangeListenersFactory(SharedLibraryModule sharedLibraryModule) {
        this.module = sharedLibraryModule;
    }

    public static SharedLibraryModule_ProvideTaskStackChangeListenersFactory create(SharedLibraryModule sharedLibraryModule) {
        return new SharedLibraryModule_ProvideTaskStackChangeListenersFactory(sharedLibraryModule);
    }

    public static TaskStackChangeListeners provideTaskStackChangeListeners(SharedLibraryModule sharedLibraryModule) {
        return (TaskStackChangeListeners) Preconditions.checkNotNullFromProvides(sharedLibraryModule.provideTaskStackChangeListeners());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TaskStackChangeListeners m2388get() {
        return provideTaskStackChangeListeners(this.module);
    }
}