package com.android.systemui.motiontool;

import android.view.WindowManagerGlobal;
import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import com.android.app.viewcapture.ViewCapture;

/* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule.class */
public interface MotionToolModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/motiontool/MotionToolModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final DdmHandleMotionTool provideDdmHandleMotionTool(MotionToolManager motionToolManager) {
            return DdmHandleMotionTool.Companion.getInstance(motionToolManager);
        }

        public final MotionToolManager provideMotionToolManager(ViewCapture viewCapture, WindowManagerGlobal windowManagerGlobal) {
            return MotionToolManager.Companion.getInstance(viewCapture, windowManagerGlobal);
        }

        public final ViewCapture provideViewCapture() {
            return ViewCapture.getInstance();
        }

        public final WindowManagerGlobal provideWindowManagerGlobal() {
            return WindowManagerGlobal.getInstance();
        }
    }
}