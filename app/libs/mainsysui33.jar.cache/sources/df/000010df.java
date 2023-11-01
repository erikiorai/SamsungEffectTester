package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.TextPaint;
import android.util.SparseArray;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/TextAnimator.class */
public final class TextAnimator {
    public ValueAnimator animator;
    public final Function0<Unit> invalidateCallback;
    public TextInterpolator textInterpolator;
    public final SparseArray<Typeface> typefaceCache;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/TextAnimator$PositionedGlyph.class */
    public static abstract class PositionedGlyph {
        public int color;
        public int lineNo;
        public float textSize;
        public float x;
        public float y;

        public PositionedGlyph() {
        }

        public /* synthetic */ PositionedGlyph(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getColor() {
            return this.color;
        }

        public abstract int getGlyphIndex();

        public final int getLineNo() {
            return this.lineNo;
        }

        public final float getTextSize() {
            return this.textSize;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        public final void setColor(int i) {
            this.color = i;
        }

        public final void setLineNo(int i) {
            this.lineNo = i;
        }

        public final void setTextSize(float f) {
            this.textSize = f;
        }

        public final void setX(float f) {
            this.x = f;
        }

        public final void setY(float f) {
            this.y = f;
        }
    }

    public TextAnimator(Layout layout, Function0<Unit> function0) {
        this.invalidateCallback = function0;
        this.textInterpolator = new TextInterpolator(layout);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f);
        ofFloat.setDuration(300L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.TextAnimator$animator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                Function0 function02;
                TextAnimator.this.getTextInterpolator().setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                function02 = TextAnimator.this.invalidateCallback;
                function02.invoke();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$animator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                TextAnimator.this.getTextInterpolator().rebase();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                TextAnimator.this.getTextInterpolator().rebase();
            }
        });
        this.animator = ofFloat;
        this.typefaceCache = new SparseArray<>();
    }

    public final void draw(Canvas canvas) {
        this.textInterpolator.draw(canvas);
    }

    public final ValueAnimator getAnimator() {
        return this.animator;
    }

    public final TextInterpolator getTextInterpolator() {
        return this.textInterpolator;
    }

    public final boolean isRunning() {
        return this.animator.isRunning();
    }

    public final void setGlyphFilter(Function2<? super PositionedGlyph, ? super Float, Unit> function2) {
        this.textInterpolator.setGlyphFilter(function2);
    }

    public final void setTextStyle(final int i, float f, Integer num, boolean z, long j, TimeInterpolator timeInterpolator, long j2, final Runnable runnable) {
        Object orElse;
        if (z) {
            this.animator.cancel();
            this.textInterpolator.rebase();
        }
        if (f >= ActionBarShadowController.ELEVATION_LOW) {
            this.textInterpolator.getTargetPaint().setTextSize(f);
        }
        if (i >= 0) {
            TextPaint targetPaint = this.textInterpolator.getTargetPaint();
            orElse = TextAnimatorKt.getOrElse(this.typefaceCache, i, new Function0<Typeface>() { // from class: com.android.systemui.animation.TextAnimator$setTextStyle$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: invoke */
                public final Typeface m1448invoke() {
                    TextPaint targetPaint2 = TextAnimator.this.getTextInterpolator().getTargetPaint();
                    int i2 = i;
                    targetPaint2.setFontVariationSettings("'wght' " + i2);
                    return TextAnimator.this.getTextInterpolator().getTargetPaint().getTypeface();
                }
            });
            targetPaint.setTypeface((Typeface) orElse);
        }
        if (num != null) {
            this.textInterpolator.getTargetPaint().setColor(num.intValue());
        }
        this.textInterpolator.onTargetPaintModified();
        if (!z) {
            this.textInterpolator.setProgress(1.0f);
            this.textInterpolator.rebase();
            this.invalidateCallback.invoke();
            return;
        }
        this.animator.setStartDelay(j2);
        ValueAnimator valueAnimator = this.animator;
        long j3 = j;
        if (j == -1) {
            j3 = 300;
        }
        valueAnimator.setDuration(j3);
        if (timeInterpolator != null) {
            this.animator.setInterpolator(timeInterpolator);
        }
        if (runnable != null) {
            this.animator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.TextAnimator$setTextStyle$listener$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.getAnimator().removeListener(this);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    runnable.run();
                    this.getAnimator().removeListener(this);
                }
            });
        }
        this.animator.start();
    }

    public final void updateLayout(Layout layout) {
        this.textInterpolator.setLayout(layout);
    }
}