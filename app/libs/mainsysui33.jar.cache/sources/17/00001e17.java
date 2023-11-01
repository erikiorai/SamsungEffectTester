package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolStartable_Factory.class */
public final class MotionToolStartable_Factory implements Factory<MotionToolStartable> {
    public final Provider<DdmHandleMotionTool> ddmHandleMotionToolProvider;

    public MotionToolStartable_Factory(Provider<DdmHandleMotionTool> provider) {
        this.ddmHandleMotionToolProvider = provider;
    }

    public static MotionToolStartable_Factory create(Provider<DdmHandleMotionTool> provider) {
        return new MotionToolStartable_Factory(provider);
    }

    public static MotionToolStartable newInstance(DdmHandleMotionTool ddmHandleMotionTool) {
        return new MotionToolStartable(ddmHandleMotionTool);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MotionToolStartable m3393get() {
        return newInstance((DdmHandleMotionTool) this.ddmHandleMotionToolProvider.get());
    }
}