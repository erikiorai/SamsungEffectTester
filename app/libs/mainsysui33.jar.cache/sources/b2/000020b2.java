package com.android.systemui.qs;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/PageIndicator.class */
public class PageIndicator extends ViewGroup {
    public boolean mAnimating;
    public final Animatable2.AnimationCallback mAnimationCallback;
    public final int mPageDotWidth;
    public final int mPageIndicatorHeight;
    public final int mPageIndicatorWidth;
    public int mPosition;
    public final ArrayList<Integer> mQueuedPositions;
    public ColorStateList mTint;

    public PageIndicator(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mQueuedPositions = new ArrayList<>();
        this.mPosition = -1;
        this.mAnimationCallback = new Animatable2.AnimationCallback() { // from class: com.android.systemui.qs.PageIndicator.1
            @Override // android.graphics.drawable.Animatable2.AnimationCallback
            public void onAnimationEnd(Drawable drawable) {
                super.onAnimationEnd(drawable);
                if (drawable instanceof AnimatedVectorDrawable) {
                    ((AnimatedVectorDrawable) drawable).unregisterAnimationCallback(PageIndicator.this.mAnimationCallback);
                }
                PageIndicator.this.mAnimating = false;
                if (PageIndicator.this.mQueuedPositions.size() != 0) {
                    PageIndicator pageIndicator = PageIndicator.this;
                    pageIndicator.setPosition(((Integer) pageIndicator.mQueuedPositions.remove(0)).intValue());
                }
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, new int[]{16843041});
        if (obtainStyledAttributes.hasValue(0)) {
            this.mTint = obtainStyledAttributes.getColorStateList(0);
        } else {
            this.mTint = Utils.getColorAccent(context);
        }
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        this.mPageIndicatorWidth = resources.getDimensionPixelSize(R$dimen.qs_page_indicator_width);
        this.mPageIndicatorHeight = resources.getDimensionPixelSize(R$dimen.qs_page_indicator_height);
        this.mPageDotWidth = resources.getDimensionPixelSize(R$dimen.qs_page_indicator_dot_width);
    }

    public final void animate(int i, int i2) {
        int i3 = i >> 1;
        int i4 = i2 >> 1;
        setIndex(i3);
        boolean z = (i & 1) != 0;
        boolean z2 = !z ? i >= i2 : i <= i2;
        int min = Math.min(i3, i4);
        int max = Math.max(i3, i4);
        int i5 = max;
        if (max == min) {
            i5 = max + 1;
        }
        ImageView imageView = (ImageView) getChildAt(min);
        ImageView imageView2 = (ImageView) getChildAt(i5);
        if (imageView == null || imageView2 == null) {
            return;
        }
        imageView2.setTranslationX(imageView.getX() - imageView2.getX());
        playAnimation(imageView, getTransition(z, z2, false));
        imageView.setAlpha(getAlpha(false));
        playAnimation(imageView2, getTransition(z, z2, true));
        imageView2.setAlpha(getAlpha(true));
        this.mAnimating = true;
    }

    public final float getAlpha(boolean z) {
        return z ? 1.0f : 0.42f;
    }

    public final int getTransition(boolean z, boolean z2, boolean z3) {
        return z3 ? z ? z2 ? R$drawable.major_b_a_animation : R$drawable.major_b_c_animation : z2 ? R$drawable.major_a_b_animation : R$drawable.major_c_b_animation : z ? z2 ? R$drawable.minor_b_c_animation : R$drawable.minor_b_a_animation : z2 ? R$drawable.minor_c_b_animation : R$drawable.minor_a_b_animation;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            int i6 = (this.mPageIndicatorWidth - this.mPageDotWidth) * i5;
            getChildAt(i5).layout(i6, 0, this.mPageIndicatorWidth + i6, this.mPageIndicatorHeight);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        if (childCount == 0) {
            super.onMeasure(i, i2);
            return;
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorWidth, 1073741824);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mPageIndicatorHeight, 1073741824);
        for (int i3 = 0; i3 < childCount; i3++) {
            getChildAt(i3).measure(makeMeasureSpec, makeMeasureSpec2);
        }
        int i4 = this.mPageIndicatorWidth;
        int i5 = this.mPageDotWidth;
        setMeasuredDimension(((i4 - i5) * (childCount - 1)) + i5, this.mPageIndicatorHeight);
    }

    public final void playAnimation(ImageView imageView, int i) {
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) getContext().getDrawable(i);
        imageView.setImageDrawable(animatedVectorDrawable);
        animatedVectorDrawable.forceAnimationOnUI();
        animatedVectorDrawable.registerAnimationCallback(this.mAnimationCallback);
        animatedVectorDrawable.start();
    }

    public final void setIndex(int i) {
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            ImageView imageView = (ImageView) getChildAt(i2);
            imageView.setTranslationX(ActionBarShadowController.ELEVATION_LOW);
            imageView.setImageResource(R$drawable.major_a_b);
            imageView.setAlpha(getAlpha(i2 == i));
            i2++;
        }
    }

    public void setLocation(float f) {
        int i = (int) f;
        int i2 = 0;
        setContentDescription(getContext().getString(R$string.accessibility_quick_settings_page, Integer.valueOf(i + 1), Integer.valueOf(getChildCount())));
        if (f != i) {
            i2 = 1;
        }
        int i3 = (i << 1) | i2;
        int i4 = this.mPosition;
        if (this.mQueuedPositions.size() != 0) {
            ArrayList<Integer> arrayList = this.mQueuedPositions;
            i4 = arrayList.get(arrayList.size() - 1).intValue();
        }
        if (i3 == i4) {
            return;
        }
        if (this.mAnimating) {
            this.mQueuedPositions.add(Integer.valueOf(i3));
        } else {
            setPosition(i3);
        }
    }

    public void setNumPages(int i) {
        setVisibility(i > 1 ? 0 : 8);
        if (i == getChildCount()) {
            return;
        }
        if (this.mAnimating) {
            Log.w("PageIndicator", "setNumPages during animation");
        }
        while (i < getChildCount()) {
            removeViewAt(getChildCount() - 1);
        }
        while (i > getChildCount()) {
            ImageView imageView = new ImageView(((ViewGroup) this).mContext);
            imageView.setImageResource(R$drawable.minor_a_b);
            imageView.setImageTintList(this.mTint);
            addView(imageView, new ViewGroup.LayoutParams(this.mPageIndicatorWidth, this.mPageIndicatorHeight));
        }
        setIndex(this.mPosition >> 1);
        requestLayout();
    }

    public final void setPosition(int i) {
        if (isVisibleToUser() && Math.abs(this.mPosition - i) == 1) {
            animate(this.mPosition, i);
        } else {
            setIndex(i >> 1);
        }
        this.mPosition = i;
    }

    public void setTintList(ColorStateList colorStateList) {
        if (colorStateList.equals(this.mTint)) {
            return;
        }
        this.mTint = colorStateList;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt instanceof ImageView) {
                ((ImageView) childAt).setImageTintList(this.mTint);
            }
        }
    }
}