package androidx.core.animation;

/* loaded from: mainsysui33.jar:androidx/core/animation/AccelerateDecelerateInterpolator.class */
public class AccelerateDecelerateInterpolator implements Interpolator {
    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        return ((float) (Math.cos((f + 1.0f) * 3.141592653589793d) / 2.0d)) + 0.5f;
    }
}