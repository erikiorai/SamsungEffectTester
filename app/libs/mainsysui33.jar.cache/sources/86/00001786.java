package com.android.systemui.dreams.touch.dagger;

import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/BouncerSwipeModule_ProvidesValueAnimatorCreatorFactory.class */
public final class BouncerSwipeModule_ProvidesValueAnimatorCreatorFactory implements Factory<BouncerSwipeTouchHandler.ValueAnimatorCreator> {
    public static BouncerSwipeTouchHandler.ValueAnimatorCreator providesValueAnimatorCreator() {
        return (BouncerSwipeTouchHandler.ValueAnimatorCreator) Preconditions.checkNotNullFromProvides(BouncerSwipeModule.providesValueAnimatorCreator());
    }
}