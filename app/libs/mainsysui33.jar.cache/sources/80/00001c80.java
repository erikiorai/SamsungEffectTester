package com.android.systemui.media.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.animation.Interpolator;
import androidx.annotation.Keep;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.Interpolators;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

@Keep
/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/LightSourceDrawable.class */
public final class LightSourceDrawable extends Drawable {
    private boolean active;
    private boolean pressed;
    private Animator rippleAnimation;
    private int[] themeAttrs;
    private final RippleData rippleData = new RippleData(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
    private Paint paint = new Paint();
    private int highlightColor = -1;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.LightSourceDrawable$active$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.media.controls.ui.LightSourceDrawable$active$1$2.onAnimationEnd(android.animation.Animator):void, com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$2$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$3.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ RippleData access$getRippleData$p(LightSourceDrawable lightSourceDrawable) {
        return lightSourceDrawable.rippleData;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.LightSourceDrawable$active$1$2.onAnimationEnd(android.animation.Animator):void, com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$3.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setRippleAnimation$p(LightSourceDrawable lightSourceDrawable, Animator animator) {
        lightSourceDrawable.rippleAnimation = animator;
    }

    private final void illuminate() {
        this.rippleData.setAlpha(1.0f);
        invalidateSelf();
        Animator animator = this.rippleAnimation;
        if (animator != null) {
            animator.cancel();
        }
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat.setStartDelay(133L);
        ofFloat.setDuration(800 - ofFloat.getStartDelay());
        Interpolator interpolator = Interpolators.LINEAR_OUT_SLOW_IN;
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightSourceDrawable.this.invalidateSelf();
            }
        });
        Unit unit = Unit.INSTANCE;
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.rippleData.getProgress(), 1.0f);
        ofFloat2.setDuration(800L);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                LightSourceDrawable.this.invalidateSelf();
            }
        });
        animatorSet.playTogether(ofFloat, ofFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.LightSourceDrawable$illuminate$1$3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setProgress(ActionBarShadowController.ELEVATION_LOW);
                LightSourceDrawable.access$setRippleAnimation$p(LightSourceDrawable.this, null);
                LightSourceDrawable.this.invalidateSelf();
            }
        });
        animatorSet.start();
        this.rippleAnimation = animatorSet;
    }

    private final void setActive(boolean z) {
        if (z == this.active) {
            return;
        }
        this.active = z;
        if (z) {
            Animator animator = this.rippleAnimation;
            if (animator != null) {
                animator.cancel();
            }
            this.rippleData.setAlpha(1.0f);
            this.rippleData.setProgress(0.05f);
        } else {
            Animator animator2 = this.rippleAnimation;
            if (animator2 != null) {
                animator2.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.rippleData.getAlpha(), ActionBarShadowController.ELEVATION_LOW);
            ofFloat.setDuration(200L);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.LightSourceDrawable$active$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    LightSourceDrawable.this.invalidateSelf();
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.LightSourceDrawable$active$1$2
                public boolean cancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator3) {
                    this.cancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator3) {
                    if (this.cancelled) {
                        return;
                    }
                    LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setProgress(ActionBarShadowController.ELEVATION_LOW);
                    LightSourceDrawable.access$getRippleData$p(LightSourceDrawable.this).setAlpha(ActionBarShadowController.ELEVATION_LOW);
                    LightSourceDrawable.access$setRippleAnimation$p(LightSourceDrawable.this, null);
                    LightSourceDrawable.this.invalidateSelf();
                }
            });
            ofFloat.start();
            this.rippleAnimation = ofFloat;
        }
        invalidateSelf();
    }

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        int i = R$styleable.IlluminationDrawable_rippleMinSize;
        if (typedArray.hasValue(i)) {
            this.rippleData.setMinSize(typedArray.getDimension(i, ActionBarShadowController.ELEVATION_LOW));
        }
        int i2 = R$styleable.IlluminationDrawable_rippleMaxSize;
        if (typedArray.hasValue(i2)) {
            this.rippleData.setMaxSize(typedArray.getDimension(i2, ActionBarShadowController.ELEVATION_LOW));
        }
        int i3 = R$styleable.IlluminationDrawable_highlight;
        if (typedArray.hasValue(i3)) {
            this.rippleData.setHighlight(typedArray.getInteger(i3, 0) / 100.0f);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            TypedArray resolveAttributes = theme.resolveAttributes(iArr, R$styleable.IlluminationDrawable);
            updateStateFromTypedArray(resolveAttributes);
            resolveAttributes.recycle();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x000f, code lost:
        if (r0.length <= 0) goto L9;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean canApplyTheme() {
        boolean z;
        int[] iArr = this.themeAttrs;
        if (iArr != null) {
            Intrinsics.checkNotNull(iArr);
        }
        if (!super.canApplyTheme()) {
            z = false;
            return z;
        }
        z = true;
        return z;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float[] fArr;
        float lerp = MathUtils.lerp(this.rippleData.getMinSize(), this.rippleData.getMaxSize(), this.rippleData.getProgress());
        int alphaComponent = ColorUtils.setAlphaComponent(this.highlightColor, (int) (this.rippleData.getAlpha() * 255));
        Paint paint = this.paint;
        float x = this.rippleData.getX();
        float y = this.rippleData.getY();
        fArr = LightSourceDrawableKt.GRADIENT_STOPS;
        paint.setShader(new RadialGradient(x, y, lerp, new int[]{alphaComponent, 0}, fArr, Shader.TileMode.CLAMP));
        canvas.drawCircle(this.rippleData.getX(), this.rippleData.getY(), lerp, this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        float lerp = MathUtils.lerp(this.rippleData.getMinSize(), this.rippleData.getMaxSize(), this.rippleData.getProgress());
        Rect rect = new Rect((int) (this.rippleData.getX() - lerp), (int) (this.rippleData.getY() - lerp), (int) (this.rippleData.getX() + lerp), (int) (this.rippleData.getY() + lerp));
        rect.union(super.getDirtyBounds());
        return rect;
    }

    public final int getHighlightColor() {
        return this.highlightColor;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isProjected() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a3, code lost:
        if (r12 != false) goto L30;
     */
    @Override // android.graphics.drawable.Drawable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        if (iArr == null) {
            return onStateChange;
        }
        boolean z = this.pressed;
        this.pressed = false;
        boolean z2 = false;
        boolean z3 = false;
        boolean z4 = false;
        for (int i : iArr) {
            switch (i) {
                case 16842908:
                    z2 = true;
                    break;
                case 16842910:
                    z4 = true;
                    break;
                case 16842919:
                    this.pressed = true;
                    break;
                case 16843623:
                    z3 = true;
                    break;
            }
        }
        boolean z5 = false;
        if (z4) {
            if (!this.pressed && !z2) {
                z5 = false;
            }
            z5 = true;
        }
        setActive(z5);
        if (z && !this.pressed) {
            illuminate();
        }
        return onStateChange;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i == this.paint.getAlpha()) {
            return;
        }
        this.paint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setHighlightColor(int i) {
        if (this.highlightColor == i) {
            return;
        }
        this.highlightColor = i;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setHotspot(float f, float f2) {
        this.rippleData.setX(f);
        this.rippleData.setY(f2);
        if (this.active) {
            invalidateSelf();
        }
    }
}