package com.android.systemui.dreams.touch.dagger;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.VelocityTracker;
import com.android.systemui.R$dimen;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.wm.shell.animation.FlingAnimationUtils;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/dagger/BouncerSwipeModule.class */
public class BouncerSwipeModule {
    public static /* synthetic */ ValueAnimator lambda$providesValueAnimatorCreator$0(float f, float f2) {
        return ValueAnimator.ofFloat(f, f2);
    }

    public static /* synthetic */ VelocityTracker lambda$providesVelocityTrackerFactory$1() {
        return VelocityTracker.obtain();
    }

    public static DreamTouchHandler providesBouncerSwipeTouchHandler(BouncerSwipeTouchHandler bouncerSwipeTouchHandler) {
        return bouncerSwipeTouchHandler;
    }

    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsClosing(Provider<FlingAnimationUtils.Builder> provider) {
        return ((FlingAnimationUtils.Builder) provider.get()).reset().setMaxLengthSeconds(0.6f).setSpeedUpFactor(0.6f).build();
    }

    public static FlingAnimationUtils providesSwipeToBouncerFlingAnimationUtilsOpening(Provider<FlingAnimationUtils.Builder> provider) {
        return ((FlingAnimationUtils.Builder) provider.get()).reset().setMaxLengthSeconds(0.6f).setSpeedUpFactor(0.6f).build();
    }

    public static float providesSwipeToBouncerStartRegion(Resources resources) {
        TypedValue typedValue = new TypedValue();
        resources.getValue(R$dimen.dream_overlay_bouncer_start_region_screen_percentage, typedValue, true);
        return typedValue.getFloat();
    }

    public static BouncerSwipeTouchHandler.ValueAnimatorCreator providesValueAnimatorCreator() {
        return new BouncerSwipeTouchHandler.ValueAnimatorCreator() { // from class: com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda1
            @Override // com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.ValueAnimatorCreator
            public final ValueAnimator create(float f, float f2) {
                ValueAnimator lambda$providesValueAnimatorCreator$0;
                lambda$providesValueAnimatorCreator$0 = BouncerSwipeModule.lambda$providesValueAnimatorCreator$0(f, f2);
                return lambda$providesValueAnimatorCreator$0;
            }
        };
    }

    public static BouncerSwipeTouchHandler.VelocityTrackerFactory providesVelocityTrackerFactory() {
        return new BouncerSwipeTouchHandler.VelocityTrackerFactory() { // from class: com.android.systemui.dreams.touch.dagger.BouncerSwipeModule$$ExternalSyntheticLambda0
            @Override // com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.VelocityTrackerFactory
            public final VelocityTracker obtain() {
                VelocityTracker lambda$providesVelocityTrackerFactory$1;
                lambda$providesVelocityTrackerFactory$1 = BouncerSwipeModule.lambda$providesVelocityTrackerFactory$1();
                return lambda$providesVelocityTrackerFactory$1;
            }
        };
    }
}