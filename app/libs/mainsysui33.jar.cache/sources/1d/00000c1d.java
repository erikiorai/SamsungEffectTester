package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.widget.FrameLayout;
import android.widget.ViewFlipper;
import com.android.systemui.R$styleable;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipper.class */
public class KeyguardSecurityViewFlipper extends ViewFlipper {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public Rect mTempRect;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipper$LayoutParams.class */
    public static class LayoutParams extends FrameLayout.LayoutParams {
        @ViewDebug.ExportedProperty(category = "layout")
        public int maxHeight;
        @ViewDebug.ExportedProperty(category = "layout")
        public int maxWidth;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyguardSecurityViewFlipper_Layout, 0, 0);
            this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(R$styleable.KeyguardSecurityViewFlipper_Layout_layout_maxWidth, 0);
            this.maxHeight = obtainStyledAttributes.getDimensionPixelSize(R$styleable.KeyguardSecurityViewFlipper_Layout_layout_maxHeight, 0);
            obtainStyledAttributes.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((FrameLayout.LayoutParams) layoutParams);
            this.maxWidth = layoutParams.maxWidth;
            this.maxHeight = layoutParams.maxHeight;
        }

        public void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
            super.encodeProperties(viewHierarchyEncoder);
            viewHierarchyEncoder.addProperty("layout:maxWidth", this.maxWidth);
            viewHierarchyEncoder.addProperty("layout:maxHeight", this.maxHeight);
        }
    }

    public KeyguardSecurityViewFlipper(Context context) {
        this(context, null);
    }

    public KeyguardSecurityViewFlipper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTempRect = new Rect();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public KeyguardInputView getSecurityView() {
        View childAt = getChildAt(getDisplayedChild());
        if (childAt instanceof KeyguardInputView) {
            return (KeyguardInputView) childAt;
        }
        return null;
    }

    public CharSequence getTitle() {
        KeyguardInputView securityView = getSecurityView();
        return securityView != null ? securityView.getTitle() : "";
    }

    public final int makeChildMeasureSpec(int i, int i2) {
        int i3;
        int i4;
        if (i2 != -2) {
            i3 = 1073741824;
            i4 = i;
            if (i2 != -1) {
                i4 = Math.min(i, i2);
                i3 = 1073741824;
            }
        } else {
            i3 = Integer.MIN_VALUE;
            i4 = i;
        }
        return View.MeasureSpec.makeMeasureSpec(i4, i3);
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z = DEBUG;
        if (z && mode != Integer.MIN_VALUE) {
            Log.w("KeyguardSecurityViewFlipper", "onMeasure: widthSpec " + View.MeasureSpec.toString(i) + " should be AT_MOST");
        }
        if (z && mode2 != Integer.MIN_VALUE) {
            Log.w("KeyguardSecurityViewFlipper", "onMeasure: heightSpec " + View.MeasureSpec.toString(i2) + " should be AT_MOST");
        }
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int childCount = getChildCount();
        int i6 = size;
        int i7 = size2;
        int i8 = 0;
        while (i8 < childCount) {
            View childAt = getChildAt(i8);
            if (childAt.getVisibility() != 0) {
                i5 = i7;
            } else {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                int i9 = layoutParams.maxWidth;
                int i10 = i6;
                if (i9 > 0) {
                    i10 = i6;
                    if (i9 < i6) {
                        i10 = i9;
                    }
                }
                int i11 = layoutParams.maxHeight;
                i6 = i10;
                i5 = i7;
                if (i11 > 0) {
                    i6 = i10;
                    i5 = i7;
                    if (i11 < i7) {
                        i5 = i11;
                        i6 = i10;
                    }
                }
            }
            i8++;
            i7 = i5;
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        int max = Math.max(0, i6 - paddingLeft);
        int max2 = Math.max(0, i7 - paddingTop);
        int i12 = mode == 1073741824 ? size : 0;
        if (mode2 == 1073741824) {
            i3 = size2;
            i4 = 0;
        } else {
            i3 = 0;
            i4 = 0;
        }
        while (i4 < childCount) {
            View childAt2 = getChildAt(i4);
            LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
            childAt2.measure(makeChildMeasureSpec(max, ((FrameLayout.LayoutParams) layoutParams2).width), makeChildMeasureSpec(max2, ((FrameLayout.LayoutParams) layoutParams2).height));
            i12 = Math.max(i12, Math.min(childAt2.getMeasuredWidth(), size - paddingLeft));
            i3 = Math.max(i3, Math.min(childAt2.getMeasuredHeight(), size2 - paddingTop));
            i4++;
        }
        setMeasuredDimension(i12 + paddingLeft, i3 + paddingTop);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        this.mTempRect.set(0, 0, 0, 0);
        int i = 0;
        while (i < getChildCount()) {
            View childAt = getChildAt(i);
            boolean z = onTouchEvent;
            if (childAt.getVisibility() == 0) {
                offsetRectIntoDescendantCoords(childAt, this.mTempRect);
                Rect rect = this.mTempRect;
                motionEvent.offsetLocation(rect.left, rect.top);
                z = childAt.dispatchTouchEvent(motionEvent) || onTouchEvent;
                Rect rect2 = this.mTempRect;
                motionEvent.offsetLocation(-rect2.left, -rect2.top);
            }
            i++;
            onTouchEvent = z;
        }
        return onTouchEvent;
    }
}