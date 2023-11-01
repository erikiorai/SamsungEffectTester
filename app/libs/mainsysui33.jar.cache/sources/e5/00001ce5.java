package com.android.systemui.media.controls.ui;

import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHostState.class */
public interface MediaHostState {
    MediaHostState copy();

    DisappearParameters getDisappearParameters();

    float getExpansion();

    boolean getFalsingProtectionNeeded();

    MeasurementInput getMeasurementInput();

    boolean getShowsOnlyActiveMedia();

    float getSquishFraction();

    boolean getVisible();

    void setExpansion(float f);
}