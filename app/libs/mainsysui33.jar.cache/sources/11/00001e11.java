package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import com.android.app.motiontool.MotionToolManager;
import com.android.app.viewcapture.ViewCapture;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideMotionToolManagerFactory.class */
public final class MotionToolModule_Companion_ProvideMotionToolManagerFactory implements Factory<MotionToolManager> {
    public final Provider<ViewCapture> viewCaptureProvider;
    public final Provider<WindowManagerGlobal> windowManagerGlobalProvider;

    public MotionToolModule_Companion_ProvideMotionToolManagerFactory(Provider<ViewCapture> provider, Provider<WindowManagerGlobal> provider2) {
        this.viewCaptureProvider = provider;
        this.windowManagerGlobalProvider = provider2;
    }

    public static MotionToolModule_Companion_ProvideMotionToolManagerFactory create(Provider<ViewCapture> provider, Provider<WindowManagerGlobal> provider2) {
        return new MotionToolModule_Companion_ProvideMotionToolManagerFactory(provider, provider2);
    }

    public static MotionToolManager provideMotionToolManager(ViewCapture viewCapture, WindowManagerGlobal windowManagerGlobal) {
        return (MotionToolManager) Preconditions.checkNotNullFromProvides(MotionToolModule.Companion.provideMotionToolManager(viewCapture, windowManagerGlobal));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MotionToolManager m3386get() {
        return provideMotionToolManager((ViewCapture) this.viewCaptureProvider.get(), (WindowManagerGlobal) this.windowManagerGlobalProvider.get());
    }
}