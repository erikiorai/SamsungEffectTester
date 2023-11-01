package com.android.systemui.dagger;

import android.view.Choreographer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidesChoreographerFactory.class */
public final class FrameworkServicesModule_ProvidesChoreographerFactory implements Factory<Choreographer> {
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvidesChoreographerFactory(FrameworkServicesModule frameworkServicesModule) {
        this.module = frameworkServicesModule;
    }

    public static FrameworkServicesModule_ProvidesChoreographerFactory create(FrameworkServicesModule frameworkServicesModule) {
        return new FrameworkServicesModule_ProvidesChoreographerFactory(frameworkServicesModule);
    }

    public static Choreographer providesChoreographer(FrameworkServicesModule frameworkServicesModule) {
        return (Choreographer) Preconditions.checkNotNullFromProvides(frameworkServicesModule.providesChoreographer());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Choreographer m2366get() {
        return providesChoreographer(this.module);
    }
}