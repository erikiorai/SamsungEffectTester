package com.android.systemui.dreams.touch.dagger;

import com.android.wm.shell.animation.FlingAnimationUtils;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory.class */
public final class BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory implements Factory<FlingAnimationUtils> {
    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsClosing(Provider<FlingAnimationUtils.Builder> provider) {
        return (FlingAnimationUtils) Preconditions.checkNotNullFromProvides(BouncerSwipeModule.providesSwipeToBouncerFlingAnimationUtilsClosing(provider));
    }
}