package com.android.systemui.animation;

import android.text.Layout;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/TextInterpolatorKt.class */
public final class TextInterpolatorKt {
    public static final float getDrawOrigin(Layout layout, int i) {
        return layout.getParagraphDirection(i) == 1 ? layout.getLineLeft(i) : layout.getLineRight(i);
    }
}