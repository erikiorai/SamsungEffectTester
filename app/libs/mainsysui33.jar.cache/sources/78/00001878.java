package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/ListGridLayout.class */
public class ListGridLayout extends LinearLayout {
    public final int[][] mConfigs;
    public int mCurrentCount;
    public int mExpectedCount;
    public boolean mReverseItems;
    public boolean mReverseSublists;
    public boolean mSwapRowsAndColumns;

    /* JADX WARN: Type inference failed for: r1v19, types: [int[], int[][]] */
    public ListGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentCount = 0;
        this.mConfigs = new int[]{new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 2}, new int[]{1, 3}, new int[]{2, 2}, new int[]{2, 3}, new int[]{2, 3}, new int[]{3, 3}, new int[]{3, 3}, new int[]{3, 3}};
    }

    public void addItem(View view) {
        ViewGroup parentView = getParentView(this.mCurrentCount, this.mReverseSublists, this.mSwapRowsAndColumns);
        if (this.mReverseItems) {
            parentView.addView(view, 0);
        } else {
            parentView.addView(view);
        }
        parentView.setVisibility(0);
        this.mCurrentCount++;
    }

    public final int[] getConfig() {
        if (this.mExpectedCount < 0) {
            return this.mConfigs[0];
        }
        return this.mConfigs[Math.min(getMaxElementCount(), this.mExpectedCount)];
    }

    public final int getMaxElementCount() {
        return this.mConfigs.length - 1;
    }

    @VisibleForTesting
    public ViewGroup getParentView(int i, boolean z, boolean z2) {
        if (getRowCount() == 0 || i < 0) {
            return null;
        }
        return getSublist(getParentViewIndex(Math.min(i, getMaxElementCount() - 1), z, z2));
    }

    public final int getParentViewIndex(int i, boolean z, boolean z2) {
        int rowCount = getRowCount();
        int floor = z2 ? (int) Math.floor(i / rowCount) : i % rowCount;
        int i2 = floor;
        if (z) {
            i2 = reverseSublistIndex(floor);
        }
        return i2;
    }

    public int getRowCount() {
        return getConfig()[0];
    }

    @VisibleForTesting
    public ViewGroup getSublist(int i) {
        return (ViewGroup) getChildAt(i);
    }

    public void removeAllItems() {
        for (int i = 0; i < getChildCount(); i++) {
            ViewGroup sublist = getSublist(i);
            if (sublist != null) {
                sublist.removeAllViews();
                sublist.setVisibility(8);
            }
        }
        this.mCurrentCount = 0;
    }

    public final int reverseSublistIndex(int i) {
        return getChildCount() - (i + 1);
    }

    public void setExpectedCount(int i) {
        this.mExpectedCount = i;
    }

    public void setReverseItems(boolean z) {
        this.mReverseItems = z;
    }

    public void setReverseSublists(boolean z) {
        this.mReverseSublists = z;
    }

    public void setSwapRowsAndColumns(boolean z) {
        this.mSwapRowsAndColumns = z;
    }
}