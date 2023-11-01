package com.android.systemui.qs.tileimpl;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tileimpl/IgnorableChildLinearLayout.class */
public final class IgnorableChildLinearLayout extends LinearLayout {
    public boolean forceUnspecifiedMeasure;
    public boolean ignoreLastView;

    public IgnorableChildLinearLayout(Context context) {
        this(context, null, 0, 0, 14, null);
    }

    public IgnorableChildLinearLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0, 0, 12, null);
    }

    public IgnorableChildLinearLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0, 8, null);
    }

    public IgnorableChildLinearLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public /* synthetic */ IgnorableChildLinearLayout(Context context, AttributeSet attributeSet, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i3 & 2) != 0 ? null : attributeSet, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 0 : i2);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int i3 = i;
        if (this.forceUnspecifiedMeasure) {
            i3 = i;
            if (getOrientation() == 0) {
                i3 = View.MeasureSpec.makeMeasureSpec(i, 0);
            }
        }
        int i4 = i2;
        if (this.forceUnspecifiedMeasure) {
            i4 = i2;
            if (getOrientation() == 1) {
                i4 = View.MeasureSpec.makeMeasureSpec(i2, 0);
            }
        }
        super.onMeasure(i3, i4);
        if (!this.ignoreLastView || getChildCount() <= 0) {
            return;
        }
        View childAt = getChildAt(getChildCount() - 1);
        if (childAt.getVisibility() != 8) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
            if (getOrientation() == 1) {
                int measuredHeight = childAt.getMeasuredHeight();
                int i5 = marginLayoutParams.bottomMargin;
                setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() - ((measuredHeight + i5) + marginLayoutParams.topMargin));
                return;
            }
            int measuredWidth = childAt.getMeasuredWidth();
            int i6 = marginLayoutParams.leftMargin;
            setMeasuredDimension(getMeasuredWidth() - ((measuredWidth + i6) + marginLayoutParams.rightMargin), getMeasuredHeight());
        }
    }

    public final void setForceUnspecifiedMeasure(boolean z) {
        this.forceUnspecifiedMeasure = z;
    }

    public final void setIgnoreLastView(boolean z) {
        this.ignoreLastView = z;
    }
}