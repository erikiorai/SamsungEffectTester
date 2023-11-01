package com.android.systemui.dreams.complication.dagger;

import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationModule_ProvidesVisibilityControllerFactory.class */
public final class ComplicationModule_ProvidesVisibilityControllerFactory implements Factory<Complication.VisibilityController> {
    public static Complication.VisibilityController providesVisibilityController(ComplicationLayoutEngine complicationLayoutEngine) {
        return (Complication.VisibilityController) Preconditions.checkNotNullFromProvides(ComplicationModule.providesVisibilityController(complicationLayoutEngine));
    }
}