package com.android.systemui.controls.ui;

import android.os.VibrationEffect;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/Vibrations.class */
public final class Vibrations {
    public static final Vibrations INSTANCE;
    public static final VibrationEffect rangeEdgeEffect;
    public static final VibrationEffect rangeMiddleEffect;

    static {
        Vibrations vibrations = new Vibrations();
        INSTANCE = vibrations;
        rangeEdgeEffect = vibrations.initRangeEdgeEffect();
        rangeMiddleEffect = vibrations.initRangeMiddleEffect();
    }

    public final VibrationEffect getRangeEdgeEffect() {
        return rangeEdgeEffect;
    }

    public final VibrationEffect getRangeMiddleEffect() {
        return rangeMiddleEffect;
    }

    public final VibrationEffect initRangeEdgeEffect() {
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        startComposition.addPrimitive(7, 0.5f);
        return startComposition.compose();
    }

    public final VibrationEffect initRangeMiddleEffect() {
        VibrationEffect.Composition startComposition = VibrationEffect.startComposition();
        startComposition.addPrimitive(7, 0.1f);
        return startComposition.compose();
    }
}