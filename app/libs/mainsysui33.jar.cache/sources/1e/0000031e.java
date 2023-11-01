package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:androidx/core/animation/AccelerateInterpolator.class */
public class AccelerateInterpolator implements Interpolator {
    public final double mDoubleFactor;
    public final float mFactor;

    public AccelerateInterpolator() {
        this.mFactor = 1.0f;
        this.mDoubleFactor = 2.0d;
    }

    public AccelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public AccelerateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, AndroidResources.STYLEABLE_ACCELERATE_INTERPOLATOR, 0, 0) : resources.obtainAttributes(attributeSet, AndroidResources.STYLEABLE_ACCELERATE_INTERPOLATOR);
        float f = obtainStyledAttributes.getFloat(0, 1.0f);
        this.mFactor = f;
        this.mDoubleFactor = f * 2.0f;
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        return this.mFactor == 1.0f ? f * f : (float) Math.pow(f, this.mDoubleFactor);
    }
}