package com.android.systemui.media.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import androidx.annotation.Keep;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.Interpolators;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import org.xmlpull.v1.XmlPullParser;

@Keep
/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/IlluminationDrawable.class */
public final class IlluminationDrawable extends Drawable {
    private ValueAnimator backgroundAnimation;
    private int backgroundColor;
    private float cornerRadius;
    private float highlight;
    private int highlightColor;
    private int[] themeAttrs;
    private float cornerRadiusOverride = -1.0f;
    private float[] tmpHsl = {ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW};
    private Paint paint = new Paint();
    private final ArrayList<LightSourceDrawable> lightSources = new ArrayList<>();

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ int access$getBackgroundColor$p(IlluminationDrawable illuminationDrawable) {
        return illuminationDrawable.backgroundColor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ int access$getHighlightColor$p(IlluminationDrawable illuminationDrawable) {
        return illuminationDrawable.highlightColor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ ArrayList access$getLightSources$p(IlluminationDrawable illuminationDrawable) {
        return illuminationDrawable.lightSources;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ Paint access$getPaint$p(IlluminationDrawable illuminationDrawable) {
        return illuminationDrawable.paint;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setBackgroundAnimation$p(IlluminationDrawable illuminationDrawable, ValueAnimator valueAnimator) {
        illuminationDrawable.backgroundAnimation = valueAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setHighlightColor$p(IlluminationDrawable illuminationDrawable, int i) {
        illuminationDrawable.highlightColor = i;
    }

    private final void animateBackground() {
        ColorUtils.colorToHSL(this.backgroundColor, this.tmpHsl);
        float[] fArr = this.tmpHsl;
        float f = fArr[2];
        float f2 = this.highlight;
        fArr[2] = MathUtils.constrain(f < 1.0f - f2 ? f + f2 : f - f2, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
        final int color = this.paint.getColor();
        final int i = this.highlightColor;
        final int HSLToColor = ColorUtils.HSLToColor(this.tmpHsl);
        ValueAnimator valueAnimator = this.backgroundAnimation;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(370L);
        ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                IlluminationDrawable.access$getPaint$p(IlluminationDrawable.this).setColor(ColorUtils.blendARGB(color, IlluminationDrawable.access$getBackgroundColor$p(IlluminationDrawable.this), floatValue));
                IlluminationDrawable.access$setHighlightColor$p(IlluminationDrawable.this, ColorUtils.blendARGB(i, HSLToColor, floatValue));
                ArrayList<LightSourceDrawable> access$getLightSources$p = IlluminationDrawable.access$getLightSources$p(IlluminationDrawable.this);
                IlluminationDrawable illuminationDrawable = IlluminationDrawable.this;
                for (LightSourceDrawable lightSourceDrawable : access$getLightSources$p) {
                    lightSourceDrawable.setHighlightColor(IlluminationDrawable.access$getHighlightColor$p(illuminationDrawable));
                }
                IlluminationDrawable.this.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.IlluminationDrawable$animateBackground$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                IlluminationDrawable.access$setBackgroundAnimation$p(IlluminationDrawable.this, null);
            }
        });
        ofFloat.start();
        this.backgroundAnimation = ofFloat;
    }

    private final void registerLightSource(LightSourceDrawable lightSourceDrawable) {
        lightSourceDrawable.setAlpha(this.paint.getAlpha());
        this.lightSources.add(lightSourceDrawable);
    }

    private final void setBackgroundColor(int i) {
        if (i == this.backgroundColor) {
            return;
        }
        this.backgroundColor = i;
        animateBackground();
    }

    private final void updateStateFromTypedArray(TypedArray typedArray) {
        int i = R$styleable.IlluminationDrawable_cornerRadius;
        if (typedArray.hasValue(i)) {
            this.cornerRadius = typedArray.getDimension(i, getCornerRadius());
        }
        int i2 = R$styleable.IlluminationDrawable_highlight;
        if (typedArray.hasValue(i2)) {
            this.highlight = typedArray.getInteger(i2, 0) / 100.0f;
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
        canvas.drawRoundRect(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, getBounds().width(), getBounds().height(), getCornerRadius(), getCornerRadius(), this.paint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.paint.getAlpha();
    }

    public final float getCornerRadius() {
        float f = this.cornerRadiusOverride;
        if (f < ActionBarShadowController.ELEVATION_LOW) {
            f = this.cornerRadius;
        }
        return f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -2;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(getBounds(), getCornerRadius());
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        TypedArray obtainAttributes = Drawable.obtainAttributes(resources, theme, attributeSet, R$styleable.IlluminationDrawable);
        this.themeAttrs = obtainAttributes.extractThemeAttrs();
        updateStateFromTypedArray(obtainAttributes);
        obtainAttributes.recycle();
    }

    public final void registerLightSource(View view) {
        if (view.getBackground() instanceof LightSourceDrawable) {
            registerLightSource((LightSourceDrawable) view.getBackground());
        } else if (view.getForeground() instanceof LightSourceDrawable) {
            registerLightSource((LightSourceDrawable) view.getForeground());
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i == this.paint.getAlpha()) {
            return;
        }
        this.paint.setAlpha(i);
        invalidateSelf();
        for (LightSourceDrawable lightSourceDrawable : this.lightSources) {
            lightSourceDrawable.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        throw new UnsupportedOperationException("Color filters are not supported");
    }

    public final void setCornerRadius(float f) {
        this.cornerRadius = f;
    }

    public final void setCornerRadiusOverride(Float f) {
        this.cornerRadiusOverride = f != null ? f.floatValue() : -1.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        super.setTintList(colorStateList);
        Intrinsics.checkNotNull(colorStateList);
        setBackgroundColor(colorStateList.getDefaultColor());
    }

    public void setXfermode(Xfermode xfermode) {
        if (Intrinsics.areEqual(xfermode, this.paint.getXfermode())) {
            return;
        }
        this.paint.setXfermode(xfermode);
        invalidateSelf();
    }
}