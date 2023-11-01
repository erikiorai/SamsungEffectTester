package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$styleable;
import com.android.systemui.screenshot.CropView;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/MagnifierView.class */
public class MagnifierView extends View implements CropView.CropInteractionListener {
    public final int mBorderColor;
    public final float mBorderPx;
    public Path mCheckerboard;
    public float mCheckerboardBoxSize;
    public Paint mCheckerboardPaint;
    public CropView.CropBoundary mCropBoundary;
    public Drawable mDrawable;
    public final Paint mHandlePaint;
    public Path mInnerCircle;
    public float mLastCenter;
    public float mLastCropPosition;
    public Path mOuterCircle;
    public final Paint mShadePaint;
    public ViewPropertyAnimator mTranslationAnimator;
    public final Animator.AnimatorListener mTranslationAnimatorListener;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.MagnifierView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$qY99Xs39zRrVcu_EaL4gIHV3g_8(MagnifierView magnifierView) {
        magnifierView.lambda$onCropDragComplete$0();
    }

    public MagnifierView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MagnifierView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCheckerboardBoxSize = 40.0f;
        this.mLastCenter = 0.5f;
        this.mTranslationAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.screenshot.MagnifierView.1
            {
                MagnifierView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                MagnifierView.this.mTranslationAnimator = null;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                MagnifierView.this.mTranslationAnimator = null;
            }
        };
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.MagnifierView, 0, 0);
        Paint paint = new Paint();
        this.mShadePaint = paint;
        paint.setColor(ColorUtils.setAlphaComponent(obtainStyledAttributes.getColor(R$styleable.MagnifierView_scrimColor, 0), obtainStyledAttributes.getInteger(R$styleable.MagnifierView_scrimAlpha, 255)));
        Paint paint2 = new Paint();
        this.mHandlePaint = paint2;
        paint2.setColor(obtainStyledAttributes.getColor(R$styleable.MagnifierView_handleColor, -16777216));
        paint2.setStrokeWidth(obtainStyledAttributes.getDimensionPixelSize(R$styleable.MagnifierView_handleThickness, 20));
        this.mBorderPx = obtainStyledAttributes.getDimensionPixelSize(R$styleable.MagnifierView_borderThickness, 0);
        this.mBorderColor = obtainStyledAttributes.getColor(R$styleable.MagnifierView_borderColor, -1);
        obtainStyledAttributes.recycle();
        Paint paint3 = new Paint();
        this.mCheckerboardPaint = paint3;
        paint3.setColor(-7829368);
    }

    public /* synthetic */ void lambda$onCropDragComplete$0() {
        setVisibility(4);
    }

    public final Path generateCheckerboard() {
        Path path = new Path();
        int ceil = (int) Math.ceil(getWidth() / this.mCheckerboardBoxSize);
        int ceil2 = (int) Math.ceil(getHeight() / this.mCheckerboardBoxSize);
        for (int i = 0; i < ceil2; i++) {
            for (int i2 = i % 2 == 0 ? 0 : 1; i2 < ceil; i2 += 2) {
                float f = this.mCheckerboardBoxSize;
                path.addRect(i2 * f, i * f, (i2 + 1) * f, (i + 1) * f, Path.Direction.CW);
            }
        }
        return path;
    }

    public final int getParentWidth() {
        return ((View) getParent()).getWidth();
    }

    @Override // com.android.systemui.screenshot.CropView.CropInteractionListener
    public void onCropDragComplete() {
        animate().alpha(ActionBarShadowController.ELEVATION_LOW).translationX((getParentWidth() - getWidth()) / 2).scaleX(0.2f).scaleY(0.2f).withEndAction(new Runnable() { // from class: com.android.systemui.screenshot.MagnifierView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MagnifierView.$r8$lambda$qY99Xs39zRrVcu_EaL4gIHV3g_8(MagnifierView.this);
            }
        }).start();
    }

    @Override // com.android.systemui.screenshot.CropView.CropInteractionListener
    public void onCropDragMoved(CropView.CropBoundary cropBoundary, float f, int i, float f2, float f3) {
        boolean z = true;
        boolean z2 = f3 > ((float) (getParentWidth() / 2));
        float parentWidth = z2 ? 0.0f : getParentWidth() - getWidth();
        boolean z3 = Math.abs(f3 - ((float) (getParentWidth() / 2))) < ((float) getParentWidth()) / 10.0f;
        if (getTranslationX() >= (getParentWidth() - getWidth()) / 2) {
            z = false;
        }
        if (!z3 && z != z2 && this.mTranslationAnimator == null) {
            ViewPropertyAnimator translationX = animate().translationX(parentWidth);
            this.mTranslationAnimator = translationX;
            translationX.setListener(this.mTranslationAnimatorListener);
            this.mTranslationAnimator.start();
        }
        this.mLastCropPosition = f;
        setTranslationY(i - (getHeight() / 2));
        invalidate();
    }

    @Override // com.android.systemui.screenshot.CropView.CropInteractionListener
    public void onCropDragStarted(CropView.CropBoundary cropBoundary, float f, int i, float f2, float f3) {
        this.mCropBoundary = cropBoundary;
        this.mLastCenter = f2;
        float parentWidth = (f3 > ((float) (getParentWidth() / 2)) ? 1 : (f3 == ((float) (getParentWidth() / 2)) ? 0 : -1)) > 0 ? 0.0f : getParentWidth() - getWidth();
        this.mLastCropPosition = f;
        setTranslationY(i - (getHeight() / 2));
        setPivotX(getWidth() / 2);
        setPivotY(getHeight() / 2);
        setScaleX(0.2f);
        setScaleY(0.2f);
        setAlpha(ActionBarShadowController.ELEVATION_LOW);
        setTranslationX((getParentWidth() - getWidth()) / 2);
        setVisibility(0);
        ViewPropertyAnimator scaleY = animate().alpha(1.0f).translationX(parentWidth).scaleX(1.0f).scaleY(1.0f);
        this.mTranslationAnimator = scaleY;
        scaleY.setListener(this.mTranslationAnimatorListener);
        this.mTranslationAnimator.start();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipPath(this.mOuterCircle);
        canvas.drawColor(this.mBorderColor);
        canvas.clipPath(this.mInnerCircle);
        canvas.drawPath(this.mCheckerboard, this.mCheckerboardPaint);
        if (this.mDrawable != null) {
            canvas.save();
            canvas.translate(((-this.mDrawable.getBounds().width()) * this.mLastCenter) + (getWidth() / 2), ((-this.mDrawable.getBounds().height()) * this.mLastCropPosition) + (getHeight() / 2));
            this.mDrawable.draw(canvas);
            canvas.restore();
        }
        Rect rect = new Rect(0, 0, getWidth(), getHeight() / 2);
        if (this.mCropBoundary == CropView.CropBoundary.BOTTOM) {
            rect.offset(0, getHeight() / 2);
        }
        canvas.drawRect(rect, this.mShadePaint);
        canvas.drawLine(ActionBarShadowController.ELEVATION_LOW, getHeight() / 2, getWidth(), getHeight() / 2, this.mHandlePaint);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int width = getWidth() / 2;
        Path path = new Path();
        this.mOuterCircle = path;
        float f = width;
        path.addCircle(f, f, f, Path.Direction.CW);
        Path path2 = new Path();
        this.mInnerCircle = path2;
        path2.addCircle(f, f, f - this.mBorderPx, Path.Direction.CW);
        this.mCheckerboard = generateCheckerboard();
    }

    public void setDrawable(Drawable drawable, int i, int i2) {
        this.mDrawable = drawable;
        drawable.setBounds(0, 0, i, i2);
        invalidate();
    }
}