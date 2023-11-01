package com.android.systemui.plugins;

import android.graphics.Rect;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockAnimations.class */
public interface ClockAnimations {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockAnimations$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void charge(ClockAnimations clockAnimations) {
        }

        public static void doze(ClockAnimations clockAnimations, float f) {
        }

        public static void enter(ClockAnimations clockAnimations) {
        }

        public static void fold(ClockAnimations clockAnimations, float f) {
        }

        public static boolean getHasCustomPositionUpdatedAnimation(ClockAnimations clockAnimations) {
            return false;
        }

        public static void onPositionUpdated(ClockAnimations clockAnimations, Rect rect, Rect rect2, float f) {
        }
    }

    void charge();

    void doze(float f);

    void enter();

    void fold(float f);

    boolean getHasCustomPositionUpdatedAnimation();

    void onPositionUpdated(Rect rect, Rect rect2, float f);
}