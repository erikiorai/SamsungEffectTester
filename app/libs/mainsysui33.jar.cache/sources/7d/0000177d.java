package com.android.systemui.dreams.touch;

import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/HideComplicationTouchHandler_Factory.class */
public final class HideComplicationTouchHandler_Factory implements Factory<HideComplicationTouchHandler> {
    public static HideComplicationTouchHandler newInstance(Complication.VisibilityController visibilityController, int i, int i2, TouchInsetManager touchInsetManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DelayableExecutor delayableExecutor, DreamOverlayStateController dreamOverlayStateController) {
        return new HideComplicationTouchHandler(visibilityController, i, i2, touchInsetManager, statusBarKeyguardViewManager, delayableExecutor, dreamOverlayStateController);
    }
}