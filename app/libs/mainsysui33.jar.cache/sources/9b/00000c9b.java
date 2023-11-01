package com.android.keyguard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: mainsysui33.jar:com/android/keyguard/NumPadButton.class */
public class NumPadButton extends AlphaOptimizedImageButton implements NumPadAnimationListener {
    public NumPadAnimator mAnimator;
    public int mOrientation;

    public NumPadButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Drawable background = getBackground();
        if (background instanceof GradientDrawable) {
            this.mAnimator = new NumPadAnimator(context, background.mutate(), attributeSet.getStyleAttribute(), getDrawable());
        } else {
            this.mAnimator = null;
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        this.mOrientation = configuration.orientation;
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.onLayout(i4 - i2);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int i3 = measuredWidth;
        if (this.mAnimator == null || this.mOrientation == 2) {
            i3 = (int) (measuredWidth * 0.66f);
        }
        setMeasuredDimension(getMeasuredWidth(), i3);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        NumPadAnimator numPadAnimator;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            NumPadAnimator numPadAnimator2 = this.mAnimator;
            if (numPadAnimator2 != null) {
                numPadAnimator2.expand();
            }
        } else if ((actionMasked == 1 || actionMasked == 3) && (numPadAnimator = this.mAnimator) != null) {
            numPadAnimator.contract();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void reloadColors() {
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.reloadColors(getContext());
        }
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16842809});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        ((VectorDrawable) getDrawable()).setTintList(ColorStateList.valueOf(color));
    }

    @Override // com.android.keyguard.NumPadAnimationListener
    public void setProgress(float f) {
        NumPadAnimator numPadAnimator = this.mAnimator;
        if (numPadAnimator != null) {
            numPadAnimator.setProgress(f);
        }
    }
}