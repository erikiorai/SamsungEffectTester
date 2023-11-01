package com.android.systemui.dreams.touch.dagger;

import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.HideComplicationTouchHandler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/HideComplicationModule_ProvidesHideComplicationTouchHandlerFactory.class */
public final class HideComplicationModule_ProvidesHideComplicationTouchHandlerFactory implements Factory<DreamTouchHandler> {
    public static DreamTouchHandler providesHideComplicationTouchHandler(HideComplicationTouchHandler hideComplicationTouchHandler) {
        return (DreamTouchHandler) Preconditions.checkNotNullFromProvides(HideComplicationModule.providesHideComplicationTouchHandler(hideComplicationTouchHandler));
    }
}