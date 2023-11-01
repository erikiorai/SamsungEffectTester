package com.android.systemui.motiontool;

import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory.class */
public final class MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory implements Factory<DdmHandleMotionTool> {
    public final Provider<MotionToolManager> motionToolManagerProvider;

    public MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory(Provider<MotionToolManager> provider) {
        this.motionToolManagerProvider = provider;
    }

    public static MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory create(Provider<MotionToolManager> provider) {
        return new MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory(provider);
    }

    public static DdmHandleMotionTool provideDdmHandleMotionTool(MotionToolManager motionToolManager) {
        return (DdmHandleMotionTool) Preconditions.checkNotNullFromProvides(MotionToolModule.Companion.provideDdmHandleMotionTool(motionToolManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DdmHandleMotionTool m3385get() {
        return provideDdmHandleMotionTool((MotionToolManager) this.motionToolManagerProvider.get());
    }
}