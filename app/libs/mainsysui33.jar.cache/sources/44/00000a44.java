package com.airbnb.lottie.animation.content;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/content/DrawingContent.class */
public interface DrawingContent extends Content {
    void draw(Canvas canvas, Matrix matrix, int i);

    void getBounds(RectF rectF, Matrix matrix, boolean z);
}