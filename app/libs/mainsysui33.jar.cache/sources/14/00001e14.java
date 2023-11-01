package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideWindowManagerGlobalFactory.class */
public final class MotionToolModule_Companion_ProvideWindowManagerGlobalFactory implements Factory<WindowManagerGlobal> {

    /* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideWindowManagerGlobalFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final MotionToolModule_Companion_ProvideWindowManagerGlobalFactory INSTANCE = new MotionToolModule_Companion_ProvideWindowManagerGlobalFactory();
    }

    public static MotionToolModule_Companion_ProvideWindowManagerGlobalFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static WindowManagerGlobal provideWindowManagerGlobal() {
        return (WindowManagerGlobal) Preconditions.checkNotNullFromProvides(MotionToolModule.Companion.provideWindowManagerGlobal());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WindowManagerGlobal m3390get() {
        return provideWindowManagerGlobal();
    }
}