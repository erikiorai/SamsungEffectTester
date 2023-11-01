package com.android.systemui.dagger;

import android.view.IWindowManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIWindowManagerFactory.class */
public final class FrameworkServicesModule_ProvideIWindowManagerFactory implements Factory<IWindowManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIWindowManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIWindowManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWindowManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIWindowManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IWindowManager provideIWindowManager() {
        return (IWindowManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIWindowManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IWindowManager m2314get() {
        return provideIWindowManager();
    }
}