package androidx.core.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:androidx/core/animation/DecelerateInterpolator.class */
public class DecelerateInterpolator implements Interpolator {
    public float mFactor;

    public DecelerateInterpolator() {
        this.mFactor = 1.0f;
    }

    public DecelerateInterpolator(float f) {
        this.mFactor = f;
    }

    public DecelerateInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public DecelerateInterpolator(Resources resources, Resources.Theme theme, AttributeSet attributeSet) {
        this.mFactor = 1.0f;
        TypedArray obtainStyledAttributes = theme != null ? theme.obtainStyledAttributes(attributeSet, AndroidResources.STYLEABLE_DECELERATE_INTERPOLATOR, 0, 0) : resources.obtainAttributes(attributeSet, AndroidResources.STYLEABLE_DECELERATE_INTERPOLATOR);
        this.mFactor = obtainStyledAttributes.getFloat(0, 1.0f);
        obtainStyledAttributes.recycle();
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        float pow;
        float f2 = this.mFactor;
        if (f2 == 1.0f) {
            float f3 = 1.0f - f;
            pow = 1.0f - (f3 * f3);
        } else {
            pow = (float) (1.0d - Math.pow(1.0f - f, f2 * 2.0f));
        }
        return pow;
    }
}