package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:androidx/core/animation/CycleInterpolator.class */
public class CycleInterpolator implements Interpolator {
    public float mCycles;

    public CycleInterpolator(float f) {
        this.mCycles = f;
    }

    public CycleInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public CycleInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, AndroidResources.STYLEABLE_CYCLE_INTERPOLATOR, 0, 0) : resources.obtainAttributes(attributeSet, AndroidResources.STYLEABLE_CYCLE_INTERPOLATOR);
        this.mCycles = obtainStyledAttributes.getFloat(0, 1.0f);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        return (float) Math.sin(this.mCycles * 2.0f * 3.141592653589793d * f);
    }
}