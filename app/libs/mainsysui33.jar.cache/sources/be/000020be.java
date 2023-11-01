package com.android.systemui.qs;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.android.systemui.R$styleable;
import java.lang.ref.WeakReference;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/PseudoGridView.class */
public class PseudoGridView extends ViewGroup {
    public int mFixedChildWidth;
    public int mHorizontalSpacing;
    public int mNumColumns;
    public int mVerticalSpacing;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/PseudoGridView$ViewGroupAdapterBridge.class */
    public static class ViewGroupAdapterBridge extends DataSetObserver {
        public final BaseAdapter mAdapter;
        public boolean mReleased = false;
        public final WeakReference<ViewGroup> mViewGroup;

        public ViewGroupAdapterBridge(ViewGroup viewGroup, BaseAdapter baseAdapter) {
            this.mViewGroup = new WeakReference<>(viewGroup);
            this.mAdapter = baseAdapter;
            baseAdapter.registerDataSetObserver(this);
            refresh();
        }

        public static void link(ViewGroup viewGroup, BaseAdapter baseAdapter) {
            new ViewGroupAdapterBridge(viewGroup, baseAdapter);
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            refresh();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            release();
        }

        public final void refresh() {
            if (this.mReleased) {
                return;
            }
            ViewGroup viewGroup = this.mViewGroup.get();
            if (viewGroup == null) {
                release();
                return;
            }
            int childCount = viewGroup.getChildCount();
            int count = this.mAdapter.getCount();
            int max = Math.max(childCount, count);
            for (int i = 0; i < max; i++) {
                if (i < count) {
                    View childAt = i < childCount ? viewGroup.getChildAt(i) : null;
                    View view = this.mAdapter.getView(i, childAt, viewGroup);
                    if (childAt == null) {
                        viewGroup.addView(view);
                    } else if (childAt != view) {
                        viewGroup.removeViewAt(i);
                        viewGroup.addView(view, i);
                    }
                } else {
                    viewGroup.removeViewAt(viewGroup.getChildCount() - 1);
                }
            }
        }

        public final void release() {
            if (this.mReleased) {
                return;
            }
            this.mReleased = true;
            this.mAdapter.unregisterDataSetObserver(this);
        }
    }

    public PseudoGridView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mNumColumns = 3;
        this.mFixedChildWidth = -1;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.PseudoGridView);
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            if (index == R$styleable.PseudoGridView_numColumns) {
                this.mNumColumns = obtainStyledAttributes.getInt(index, 3);
            } else if (index == R$styleable.PseudoGridView_verticalSpacing) {
                this.mVerticalSpacing = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R$styleable.PseudoGridView_horizontalSpacing) {
                this.mHorizontalSpacing = obtainStyledAttributes.getDimensionPixelSize(index, 0);
            } else if (index == R$styleable.PseudoGridView_fixedChildWidth) {
                this.mFixedChildWidth = obtainStyledAttributes.getDimensionPixelSize(index, -1);
            }
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean isLayoutRtl = isLayoutRtl();
        int childCount = getChildCount();
        int i5 = this.mNumColumns;
        int i6 = ((childCount + i5) - 1) / i5;
        int i7 = 0;
        for (int i8 = 0; i8 < i6; i8++) {
            int width = isLayoutRtl ? getWidth() : 0;
            int i9 = this.mNumColumns;
            int i10 = i8 * i9;
            int min = Math.min(i9 + i10, childCount);
            int i11 = 0;
            while (i10 < min) {
                View childAt = getChildAt(i10);
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                int i12 = width;
                if (isLayoutRtl) {
                    i12 = width - measuredWidth;
                }
                childAt.layout(i12, i7, i12 + measuredWidth, i7 + measuredHeight);
                i11 = Math.max(i11, measuredHeight);
                width = isLayoutRtl ? i12 - this.mHorizontalSpacing : i12 + measuredWidth + this.mHorizontalSpacing;
                i10++;
            }
            i7 += i11 + this.mVerticalSpacing;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        if (View.MeasureSpec.getMode(i) == 0) {
            throw new UnsupportedOperationException("Needs a maximum width");
        }
        int size = View.MeasureSpec.getSize(i);
        int i3 = this.mFixedChildWidth;
        int i4 = this.mNumColumns;
        int i5 = this.mHorizontalSpacing;
        if (i3 == -1 || (i3 * i4) + ((i4 - 1) * i5) > size) {
            i3 = (size - ((i4 - 1) * i5)) / i4;
        } else {
            size = (i3 * i4) + (i5 * (i4 - 1));
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        int childCount = getChildCount();
        int i6 = this.mNumColumns;
        int i7 = ((childCount + i6) - 1) / i6;
        int i8 = 0;
        for (int i9 = 0; i9 < i7; i9++) {
            int i10 = this.mNumColumns;
            int i11 = i9 * i10;
            int min = Math.min(i10 + i11, childCount);
            int i12 = 0;
            for (int i13 = i11; i13 < min; i13++) {
                View childAt = getChildAt(i13);
                childAt.measure(makeMeasureSpec, 0);
                i12 = Math.max(i12, childAt.getMeasuredHeight());
            }
            int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i12, 1073741824);
            while (i11 < min) {
                View childAt2 = getChildAt(i11);
                if (childAt2.getMeasuredHeight() != i12) {
                    childAt2.measure(makeMeasureSpec, makeMeasureSpec2);
                }
                i11++;
            }
            int i14 = i8 + i12;
            i8 = i14;
            if (i9 > 0) {
                i8 = i14 + this.mVerticalSpacing;
            }
        }
        setMeasuredDimension(size, ViewGroup.resolveSizeAndState(i8, i2, 0));
    }
}