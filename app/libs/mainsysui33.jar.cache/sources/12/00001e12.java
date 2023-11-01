package com.android.systemui.motiontool;

import com.android.app.viewcapture.ViewCapture;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideViewCaptureFactory.class */
public final class MotionToolModule_Companion_ProvideViewCaptureFactory implements Factory<ViewCapture> {

    /* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideViewCaptureFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final MotionToolModule_Companion_ProvideViewCaptureFactory INSTANCE = new MotionToolModule_Companion_ProvideViewCaptureFactory();
    }

    public static MotionToolModule_Companion_ProvideViewCaptureFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ViewCapture provideViewCapture() {
        return (ViewCapture) Preconditions.checkNotNullFromProvides(MotionToolModule.Companion.provideViewCapture());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ViewCapture m3387get() {
        return provideViewCapture();
    }
}