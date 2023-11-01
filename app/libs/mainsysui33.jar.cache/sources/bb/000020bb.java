package com.android.systemui.qs;

import android.graphics.Path;
import android.view.animation.BaseInterpolator;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/PathInterpolatorBuilder.class */
public class PathInterpolatorBuilder {
    public float[] mDist;
    public float[] mX;
    public float[] mY;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/PathInterpolatorBuilder$PathInterpolator.class */
    public static class PathInterpolator extends BaseInterpolator {
        public final float[] mX;
        public final float[] mY;

        public PathInterpolator(float[] fArr, float[] fArr2) {
            this.mX = fArr;
            this.mY = fArr2;
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            if (f <= ActionBarShadowController.ELEVATION_LOW) {
                return ActionBarShadowController.ELEVATION_LOW;
            }
            if (f >= 1.0f) {
                return 1.0f;
            }
            int i = 0;
            int length = this.mX.length - 1;
            while (length - i > 1) {
                int i2 = (i + length) / 2;
                if (f < this.mX[i2]) {
                    length = i2;
                } else {
                    i = i2;
                }
            }
            float[] fArr = this.mX;
            float f2 = fArr[length];
            float f3 = fArr[i];
            float f4 = f2 - f3;
            if (f4 == ActionBarShadowController.ELEVATION_LOW) {
                return this.mY[i];
            }
            float f5 = (f - f3) / f4;
            float[] fArr2 = this.mY;
            float f6 = fArr2[i];
            return f6 + (f5 * (fArr2[length] - f6));
        }
    }

    public PathInterpolatorBuilder(float f, float f2, float f3, float f4) {
        initCubic(f, f2, f3, f4);
    }

    public Interpolator getXInterpolator() {
        return new PathInterpolator(this.mDist, this.mX);
    }

    public Interpolator getYInterpolator() {
        return new PathInterpolator(this.mDist, this.mY);
    }

    public final void initCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        initPath(path);
    }

    public final void initPath(Path path) {
        float[] approximate = path.approximate(0.002f);
        int length = approximate.length / 3;
        float f = 0.0f;
        if (approximate[1] != ActionBarShadowController.ELEVATION_LOW || approximate[2] != ActionBarShadowController.ELEVATION_LOW || approximate[approximate.length - 2] != 1.0f || approximate[approximate.length - 1] != 1.0f) {
            throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
        }
        this.mX = new float[length];
        this.mY = new float[length];
        this.mDist = new float[length];
        int i = 0;
        int i2 = 0;
        float f2 = 0.0f;
        while (i < length) {
            int i3 = i2 + 1;
            float f3 = approximate[i2];
            int i4 = i3 + 1;
            float f4 = approximate[i3];
            float f5 = approximate[i4];
            if (f3 == f && f4 != f2) {
                throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
            }
            if (f4 < f2) {
                throw new IllegalArgumentException("The Path cannot loop back on itself.");
            }
            float[] fArr = this.mX;
            fArr[i] = f4;
            float[] fArr2 = this.mY;
            fArr2[i] = f5;
            if (i > 0) {
                float f6 = fArr[i];
                int i5 = i - 1;
                float f7 = f6 - fArr[i5];
                float f8 = f5 - fArr2[i5];
                float sqrt = (float) Math.sqrt((f7 * f7) + (f8 * f8));
                float[] fArr3 = this.mDist;
                fArr3[i] = fArr3[i5] + sqrt;
            }
            i++;
            f = f3;
            f2 = f4;
            i2 = i4 + 1;
        }
        float[] fArr4 = this.mDist;
        float f9 = fArr4[fArr4.length - 1];
        for (int i6 = 0; i6 < length; i6++) {
            float[] fArr5 = this.mDist;
            fArr5[i6] = fArr5[i6] / f9;
        }
    }
}