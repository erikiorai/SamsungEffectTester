package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;

/* loaded from: mainsysui33.jar:androidx/recyclerview/widget/GridLayoutManager.class */
public class GridLayoutManager extends LinearLayoutManager {
    public int[] mCachedBorders;
    public final Rect mDecorInsets;
    public boolean mPendingSpanCountChange;
    public final SparseIntArray mPreLayoutSpanIndexCache;
    public final SparseIntArray mPreLayoutSpanSizeCache;
    public View[] mSet;
    public int mSpanCount;
    public SpanSizeLookup mSpanSizeLookup;
    public boolean mUsingSpansToEstimateScrollBarDimensions;

    /* loaded from: mainsysui33.jar:androidx/recyclerview/widget/GridLayoutManager$DefaultSpanSizeLookup.class */
    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanIndex(int i, int i2) {
            return i % i2;
        }

        @Override // androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
        public int getSpanSize(int i) {
            return 1;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/recyclerview/widget/GridLayoutManager$LayoutParams.class */
    public static class LayoutParams extends RecyclerView.LayoutParams {
        public int mSpanIndex;
        public int mSpanSize;

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.mSpanIndex = -1;
            this.mSpanSize = 0;
        }

        public int getSpanIndex() {
            return this.mSpanIndex;
        }

        public int getSpanSize() {
            return this.mSpanSize;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/recyclerview/widget/GridLayoutManager$SpanSizeLookup.class */
    public static abstract class SpanSizeLookup {
        public final SparseIntArray mSpanIndexCache = new SparseIntArray();
        public final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
        public boolean mCacheSpanIndices = false;
        public boolean mCacheSpanGroupIndices = false;

        public static int findFirstKeyLessThan(SparseIntArray sparseIntArray, int i) {
            int size = sparseIntArray.size() - 1;
            int i2 = 0;
            while (i2 <= size) {
                int i3 = (i2 + size) >>> 1;
                if (sparseIntArray.keyAt(i3) < i) {
                    i2 = i3 + 1;
                } else {
                    size = i3 - 1;
                }
            }
            int i4 = i2 - 1;
            if (i4 < 0 || i4 >= sparseIntArray.size()) {
                return -1;
            }
            return sparseIntArray.keyAt(i4);
        }

        public int getCachedSpanGroupIndex(int i, int i2) {
            if (this.mCacheSpanGroupIndices) {
                int i3 = this.mSpanGroupIndexCache.get(i, -1);
                if (i3 != -1) {
                    return i3;
                }
                int spanGroupIndex = getSpanGroupIndex(i, i2);
                this.mSpanGroupIndexCache.put(i, spanGroupIndex);
                return spanGroupIndex;
            }
            return getSpanGroupIndex(i, i2);
        }

        public int getCachedSpanIndex(int i, int i2) {
            if (this.mCacheSpanIndices) {
                int i3 = this.mSpanIndexCache.get(i, -1);
                if (i3 != -1) {
                    return i3;
                }
                int spanIndex = getSpanIndex(i, i2);
                this.mSpanIndexCache.put(i, spanIndex);
                return spanIndex;
            }
            return getSpanIndex(i, i2);
        }

        public int getSpanGroupIndex(int i, int i2) {
            int i3;
            int i4;
            int i5;
            int i6;
            int i7;
            int findFirstKeyLessThan;
            if (!this.mCacheSpanGroupIndices || (findFirstKeyLessThan = findFirstKeyLessThan(this.mSpanGroupIndexCache, i)) == -1) {
                i3 = 0;
                i4 = 0;
                i5 = 0;
            } else {
                int i8 = this.mSpanGroupIndexCache.get(findFirstKeyLessThan);
                int i9 = findFirstKeyLessThan + 1;
                int cachedSpanIndex = getCachedSpanIndex(findFirstKeyLessThan, i2) + getSpanSize(findFirstKeyLessThan);
                i3 = i8;
                i4 = i9;
                i5 = cachedSpanIndex;
                if (cachedSpanIndex == i2) {
                    i3 = i8 + 1;
                    i5 = 0;
                    i4 = i9;
                }
            }
            int spanSize = getSpanSize(i);
            int i10 = i5;
            int i11 = i4;
            int i12 = i3;
            while (i11 < i) {
                int spanSize2 = getSpanSize(i11);
                int i13 = i10 + spanSize2;
                if (i13 == i2) {
                    i6 = i12 + 1;
                    i7 = 0;
                } else {
                    i6 = i12;
                    i7 = i13;
                    if (i13 > i2) {
                        i6 = i12 + 1;
                        i7 = spanSize2;
                    }
                }
                i11++;
                i12 = i6;
                i10 = i7;
            }
            int i14 = i12;
            if (i10 + spanSize > i2) {
                i14 = i12 + 1;
            }
            return i14;
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0083  */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x005a -> B:20:0x007a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x006f -> B:20:0x007a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0072 -> B:20:0x007a). Please submit an issue!!! */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public int getSpanIndex(int i, int i2) {
            int i3;
            int i4;
            int spanSize = getSpanSize(i);
            if (spanSize == i2) {
                return 0;
            }
            if (this.mCacheSpanIndices) {
                int i5 = findFirstKeyLessThan(this.mSpanIndexCache, i);
                if (i5 >= 0) {
                    i4 = this.mSpanIndexCache.get(i5) + getSpanSize(i5);
                    i3 = i5 + 1;
                    if (i3 >= i) {
                        int spanSize2 = getSpanSize(i3);
                        int i6 = i4 + spanSize2;
                        if (i6 == i2) {
                            i4 = 0;
                            i5 = i3;
                        } else {
                            i5 = i3;
                            i4 = i6;
                            if (i6 > i2) {
                                i4 = spanSize2;
                                i5 = i3;
                            }
                        }
                        i3 = i5 + 1;
                        if (i3 >= i) {
                            if (spanSize + i4 <= i2) {
                                return i4;
                            }
                            return 0;
                        }
                    }
                }
            }
            i3 = 0;
            i4 = 0;
            if (i3 >= i) {
            }
        }

        public abstract int getSpanSize(int i);

        public void invalidateSpanGroupIndexCache() {
            this.mSpanGroupIndexCache.clear();
        }

        public void invalidateSpanIndexCache() {
            this.mSpanIndexCache.clear();
        }

        public void setSpanIndexCacheEnabled(boolean z) {
            if (!z) {
                this.mSpanGroupIndexCache.clear();
            }
            this.mCacheSpanIndices = z;
        }
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(i);
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPendingSpanCountChange = false;
        this.mSpanCount = -1;
        this.mPreLayoutSpanSizeCache = new SparseIntArray();
        this.mPreLayoutSpanIndexCache = new SparseIntArray();
        this.mSpanSizeLookup = new DefaultSpanSizeLookup();
        this.mDecorInsets = new Rect();
        setSpanCount(RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2).spanCount);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0018, code lost:
        if (r4[r4.length - 1] != r6) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int[] calculateItemBorders(int[] iArr, int i, int i2) {
        int[] iArr2;
        int i3;
        if (iArr != null && iArr.length == i + 1) {
            iArr2 = iArr;
        }
        iArr2 = new int[i + 1];
        iArr2[0] = 0;
        int i4 = i2 / i;
        int i5 = i2 % i;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i7 += i5;
            if (i7 <= 0 || i - i7 >= i5) {
                i3 = i4;
            } else {
                i3 = i4 + 1;
                i7 -= i;
            }
            i6 += i3;
            iArr2[i8] = i6;
        }
        return iArr2;
    }

    public final void assignSpans(RecyclerView.Recycler recycler, RecyclerView.State state, int i, boolean z) {
        int i2;
        int i3;
        int i4 = 0;
        int i5 = -1;
        if (z) {
            i3 = 1;
            i5 = i;
            i2 = 0;
        } else {
            i2 = i - 1;
            i3 = -1;
        }
        while (i2 != i5) {
            View view = this.mSet[i2];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            int spanSize = getSpanSize(recycler, state, getPosition(view));
            layoutParams.mSpanSize = spanSize;
            layoutParams.mSpanIndex = i4;
            i4 += spanSize;
            i2 += i3;
        }
    }

    public final void cachePreLayoutSpanMapping() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put(viewLayoutPosition, layoutParams.getSpanSize());
            this.mPreLayoutSpanIndexCache.put(viewLayoutPosition, layoutParams.getSpanIndex());
        }
    }

    public final void calculateItemBorders(int i) {
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, i);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public final void clearPreLayoutSpanMappingCache() {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = this.mSpanCount;
        for (int i2 = 0; i2 < this.mSpanCount && layoutState.hasMore(state) && i > 0; i2++) {
            int i3 = layoutState.mCurrentPosition;
            layoutPrefetchRegistry.addPosition(i3, Math.max(0, layoutState.mScrollingOffset));
            i -= this.mSpanSizeLookup.getSpanSize(i3);
            layoutState.mCurrentPosition += layoutState.mItemDirection;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollOffsetWithSpanInfo(state) : super.computeHorizontalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollRangeWithSpanInfo(state) : super.computeHorizontalScrollRange(state);
    }

    public final int computeScrollOffsetWithSpanInfo(RecyclerView.State state) {
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        boolean isSmoothScrollbarEnabled = isSmoothScrollbarEnabled();
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!isSmoothScrollbarEnabled, true);
        View findFirstVisibleChildClosestToEnd = findFirstVisibleChildClosestToEnd(!isSmoothScrollbarEnabled, true);
        if (findFirstVisibleChildClosestToStart == null || findFirstVisibleChildClosestToEnd == null) {
            return 0;
        }
        int cachedSpanGroupIndex = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.mSpanCount);
        int cachedSpanGroupIndex2 = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.mSpanCount);
        int max = this.mShouldReverseLayout ? Math.max(0, ((this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount) + 1) - Math.max(cachedSpanGroupIndex, cachedSpanGroupIndex2)) - 1) : Math.max(0, Math.min(cachedSpanGroupIndex, cachedSpanGroupIndex2));
        if (isSmoothScrollbarEnabled) {
            return Math.round((max * (Math.abs(this.mOrientationHelper.getDecoratedEnd(findFirstVisibleChildClosestToEnd) - this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart)) / ((this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.mSpanCount) - this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.mSpanCount)) + 1))) + (this.mOrientationHelper.getStartAfterPadding() - this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart)));
        }
        return max;
    }

    public final int computeScrollRangeWithSpanInfo(RecyclerView.State state) {
        if (getChildCount() == 0 || state.getItemCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!isSmoothScrollbarEnabled(), true);
        View findFirstVisibleChildClosestToEnd = findFirstVisibleChildClosestToEnd(!isSmoothScrollbarEnabled(), true);
        if (findFirstVisibleChildClosestToStart == null || findFirstVisibleChildClosestToEnd == null) {
            return 0;
        }
        if (isSmoothScrollbarEnabled()) {
            int decoratedEnd = this.mOrientationHelper.getDecoratedEnd(findFirstVisibleChildClosestToEnd);
            int decoratedStart = this.mOrientationHelper.getDecoratedStart(findFirstVisibleChildClosestToStart);
            int cachedSpanGroupIndex = this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToStart), this.mSpanCount);
            return (int) (((decoratedEnd - decoratedStart) / ((this.mSpanSizeLookup.getCachedSpanGroupIndex(getPosition(findFirstVisibleChildClosestToEnd), this.mSpanCount) - cachedSpanGroupIndex) + 1)) * (this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount) + 1));
        }
        return this.mSpanSizeLookup.getCachedSpanGroupIndex(state.getItemCount() - 1, this.mSpanCount) + 1;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollOffsetWithSpanInfo(state) : super.computeVerticalScrollOffset(state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.mUsingSpansToEstimateScrollBarDimensions ? computeScrollRangeWithSpanInfo(state) : super.computeVerticalScrollRange(state);
    }

    public final void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        int i2;
        int spanIndex;
        boolean z = i == 1;
        int spanIndex2 = getSpanIndex(recycler, state, anchorInfo.mPosition);
        if (z) {
            while (spanIndex2 > 0) {
                int i3 = anchorInfo.mPosition;
                if (i3 <= 0) {
                    return;
                }
                int i4 = i3 - 1;
                anchorInfo.mPosition = i4;
                spanIndex2 = getSpanIndex(recycler, state, i4);
            }
            return;
        }
        int itemCount = state.getItemCount();
        int i5 = anchorInfo.mPosition;
        while (i5 < itemCount - 1 && (spanIndex = getSpanIndex(recycler, state, (i2 = i5 + 1))) > spanIndex2) {
            i5 = i2;
            spanIndex2 = spanIndex;
        }
        anchorInfo.mPosition = i5;
    }

    public final void ensureViewSet() {
        View[] viewArr = this.mSet;
        if (viewArr == null || viewArr.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z, boolean z2) {
        int i;
        int childCount = getChildCount();
        int i2 = -1;
        int i3 = 1;
        if (z2) {
            i = getChildCount() - 1;
            i3 = -1;
        } else {
            i2 = childCount;
            i = 0;
        }
        int itemCount = state.getItemCount();
        ensureLayoutState();
        int startAfterPadding = this.mOrientationHelper.getStartAfterPadding();
        int endAfterPadding = this.mOrientationHelper.getEndAfterPadding();
        View view = null;
        View view2 = null;
        while (true) {
            View view3 = view2;
            if (i == i2) {
                if (view == null) {
                    view = view3;
                }
                return view;
            }
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            View view4 = view;
            View view5 = view3;
            if (position >= 0) {
                view4 = view;
                view5 = view3;
                if (position >= itemCount) {
                    continue;
                } else if (getSpanIndex(recycler, state, position) != 0) {
                    view4 = view;
                    view5 = view3;
                } else if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    view4 = view;
                    view5 = view3;
                    if (view3 == null) {
                        view5 = childAt;
                        view4 = view;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart(childAt) < endAfterPadding && this.mOrientationHelper.getDecoratedEnd(childAt) >= startAfterPadding) {
                    return childAt;
                } else {
                    view4 = view;
                    view5 = view3;
                    if (view == null) {
                        view4 = childAt;
                        view5 = view3;
                    }
                }
            }
            i += i3;
            view = view4;
            view2 = view5;
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.mOrientation == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if (state.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex(recycler, state, state.getItemCount() - 1) + 1;
    }

    public int getSpaceForSpanRange(int i, int i2) {
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            int[] iArr = this.mCachedBorders;
            return iArr[i2 + i] - iArr[i];
        }
        int[] iArr2 = this.mCachedBorders;
        int i3 = this.mSpanCount;
        return iArr2[i3 - i] - iArr2[(i3 - i) - i2];
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public final int getSpanGroupIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (state.isPreLayout()) {
            int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
            if (convertPreLayoutPositionToPostLayout == -1) {
                Log.w("GridLayoutManager", "Cannot find span size for pre layout position. " + i);
                return 0;
            }
            return this.mSpanSizeLookup.getCachedSpanGroupIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        return this.mSpanSizeLookup.getCachedSpanGroupIndex(i, this.mSpanCount);
    }

    public final int getSpanIndex(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (state.isPreLayout()) {
            int i2 = this.mPreLayoutSpanIndexCache.get(i, -1);
            if (i2 != -1) {
                return i2;
            }
            int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
            if (convertPreLayoutPositionToPostLayout == -1) {
                Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
                return 0;
            }
            return this.mSpanSizeLookup.getCachedSpanIndex(convertPreLayoutPositionToPostLayout, this.mSpanCount);
        }
        return this.mSpanSizeLookup.getCachedSpanIndex(i, this.mSpanCount);
    }

    public final int getSpanSize(RecyclerView.Recycler recycler, RecyclerView.State state, int i) {
        if (state.isPreLayout()) {
            int i2 = this.mPreLayoutSpanSizeCache.get(i, -1);
            if (i2 != -1) {
                return i2;
            }
            int convertPreLayoutPositionToPostLayout = recycler.convertPreLayoutPositionToPostLayout(i);
            if (convertPreLayoutPositionToPostLayout == -1) {
                Log.w("GridLayoutManager", "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + i);
                return 1;
            }
            return this.mSpanSizeLookup.getSpanSize(convertPreLayoutPositionToPostLayout);
        }
        return this.mSpanSizeLookup.getSpanSize(i);
    }

    public final void guessMeasurement(float f, int i) {
        calculateItemBorders(Math.max(Math.round(f * this.mSpanCount), i));
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.LayoutState layoutState, LinearLayoutManager.LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int decoratedMeasurementInOther;
        int i7;
        int makeMeasureSpec;
        int childMeasureSpec;
        View next;
        int modeInOther = this.mOrientationHelper.getModeInOther();
        boolean z = modeInOther != 1073741824;
        int i8 = getChildCount() > 0 ? this.mCachedBorders[this.mSpanCount] : 0;
        if (z) {
            updateMeasurements();
        }
        boolean z2 = layoutState.mItemDirection == 1;
        int i9 = this.mSpanCount;
        if (!z2) {
            i9 = getSpanIndex(recycler, state, layoutState.mCurrentPosition) + getSpanSize(recycler, state, layoutState.mCurrentPosition);
        }
        int i10 = 0;
        while (i10 < this.mSpanCount && layoutState.hasMore(state) && i9 > 0) {
            int i11 = layoutState.mCurrentPosition;
            int spanSize = getSpanSize(recycler, state, i11);
            if (spanSize > this.mSpanCount) {
                throw new IllegalArgumentException("Item at position " + i11 + " requires " + spanSize + " spans but GridLayoutManager has only " + this.mSpanCount + " spans.");
            }
            i9 -= spanSize;
            if (i9 < 0 || (next = layoutState.next(recycler)) == null) {
                break;
            }
            this.mSet[i10] = next;
            i10++;
        }
        if (i10 == 0) {
            layoutChunkResult.mFinished = true;
            return;
        }
        float f = 0.0f;
        assignSpans(recycler, state, i10, z2);
        int i12 = 0;
        int i13 = 0;
        while (i12 < i10) {
            View view = this.mSet[i12];
            if (layoutState.mScrapList == null) {
                if (z2) {
                    addView(view);
                } else {
                    addView(view, 0);
                }
            } else if (z2) {
                addDisappearingView(view);
            } else {
                addDisappearingView(view, 0);
            }
            calculateItemDecorationsForChild(view, this.mDecorInsets);
            measureChild(view, modeInOther, false);
            int decoratedMeasurement = this.mOrientationHelper.getDecoratedMeasurement(view);
            int i14 = i13;
            if (decoratedMeasurement > i13) {
                i14 = decoratedMeasurement;
            }
            float decoratedMeasurementInOther2 = (this.mOrientationHelper.getDecoratedMeasurementInOther(view) * 1.0f) / ((LayoutParams) view.getLayoutParams()).mSpanSize;
            float f2 = f;
            if (decoratedMeasurementInOther2 > f) {
                f2 = decoratedMeasurementInOther2;
            }
            i12++;
            i13 = i14;
            f = f2;
        }
        int i15 = i13;
        if (z) {
            guessMeasurement(f, i8);
            int i16 = 0;
            int i17 = 0;
            while (true) {
                int i18 = i17;
                i15 = i18;
                if (i16 >= i10) {
                    break;
                }
                View view2 = this.mSet[i16];
                measureChild(view2, 1073741824, true);
                int decoratedMeasurement2 = this.mOrientationHelper.getDecoratedMeasurement(view2);
                int i19 = i18;
                if (decoratedMeasurement2 > i18) {
                    i19 = decoratedMeasurement2;
                }
                i16++;
                i17 = i19;
            }
        }
        for (int i20 = 0; i20 < i10; i20++) {
            View view3 = this.mSet[i20];
            if (this.mOrientationHelper.getDecoratedMeasurement(view3) != i15) {
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                Rect rect = layoutParams.mDecorInsets;
                int i21 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
                int i22 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
                int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
                if (this.mOrientation == 1) {
                    makeMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(spaceForSpanRange, 1073741824, i22, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
                    childMeasureSpec = View.MeasureSpec.makeMeasureSpec(i15 - i21, 1073741824);
                } else {
                    makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i15 - i22, 1073741824);
                    childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(spaceForSpanRange, 1073741824, i21, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
                }
                measureChildWithDecorationsAndMargin(view3, makeMeasureSpec, childMeasureSpec, true);
            }
        }
        int i23 = 0;
        layoutChunkResult.mConsumed = i15;
        if (this.mOrientation == 1) {
            if (layoutState.mLayoutDirection == -1) {
                i3 = layoutState.mOffset;
                i4 = i3 - i15;
            } else {
                int i24 = layoutState.mOffset;
                i4 = i24;
                i3 = i15 + i24;
            }
            i2 = 0;
            i = 0;
        } else if (layoutState.mLayoutDirection == -1) {
            i2 = layoutState.mOffset;
            i = i2 - i15;
            i4 = 0;
            i3 = 0;
        } else {
            i = layoutState.mOffset;
            i2 = i15 + i;
            i3 = 0;
            i4 = 0;
        }
        while (i23 < i10) {
            View view4 = this.mSet[i23];
            LayoutParams layoutParams2 = (LayoutParams) view4.getLayoutParams();
            if (this.mOrientation == 1) {
                if (isLayoutRTL()) {
                    int paddingLeft = getPaddingLeft() + this.mCachedBorders[this.mSpanCount - layoutParams2.mSpanIndex];
                    i2 = paddingLeft;
                    i7 = paddingLeft - this.mOrientationHelper.getDecoratedMeasurementInOther(view4);
                } else {
                    int paddingLeft2 = getPaddingLeft() + this.mCachedBorders[layoutParams2.mSpanIndex];
                    i7 = paddingLeft2;
                    i2 = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + paddingLeft2;
                }
                decoratedMeasurementInOther = i3;
                i5 = i4;
                i6 = i7;
            } else {
                int paddingTop = getPaddingTop() + this.mCachedBorders[layoutParams2.mSpanIndex];
                i5 = paddingTop;
                i6 = i;
                decoratedMeasurementInOther = this.mOrientationHelper.getDecoratedMeasurementInOther(view4) + paddingTop;
            }
            layoutDecoratedWithMargins(view4, i6, i5, i2, decoratedMeasurementInOther);
            if (layoutParams2.isItemRemoved() || layoutParams2.isItemChanged()) {
                layoutChunkResult.mIgnoreConsumed = true;
            }
            layoutChunkResult.mFocusable |= view4.hasFocusable();
            i23++;
            i = i6;
            i4 = i5;
            i3 = decoratedMeasurementInOther;
        }
        Arrays.fill(this.mSet, (Object) null);
    }

    public final void measureChild(View view, int i, boolean z) {
        int childMeasureSpec;
        int childMeasureSpec2;
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        Rect rect = layoutParams.mDecorInsets;
        int i2 = rect.top + rect.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        int i3 = rect.left + rect.right + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        int spaceForSpanRange = getSpaceForSpanRange(layoutParams.mSpanIndex, layoutParams.mSpanSize);
        if (this.mOrientation == 1) {
            childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(spaceForSpanRange, i, i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, false);
            childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getHeightMode(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height, true);
        } else {
            childMeasureSpec = RecyclerView.LayoutManager.getChildMeasureSpec(spaceForSpanRange, i, i2, ((ViewGroup.MarginLayoutParams) layoutParams).height, false);
            childMeasureSpec2 = RecyclerView.LayoutManager.getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), getWidthMode(), i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, true);
        }
        measureChildWithDecorationsAndMargin(view, childMeasureSpec2, childMeasureSpec, z);
    }

    public final void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        if (z ? shouldReMeasureChild(view, i, i2, layoutParams) : shouldMeasureChild(view, i, i2, layoutParams)) {
            view.measure(i, i2);
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, LinearLayoutManager.AnchorInfo anchorInfo, int i) {
        super.onAnchorReady(recycler, state, anchorInfo, i);
        updateMeasurements();
        if (state.getItemCount() > 0 && !state.isPreLayout()) {
            ensureAnchorIsInCorrectSpan(recycler, state, anchorInfo, i);
        }
        ensureViewSet();
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x018e, code lost:
        if (r19 == (r0 > r21)) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01d7, code lost:
        if (r19 == (r0 > r22)) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:108:0x0234 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x01e8  */
    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public View onFocusSearchFailed(View view, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int childCount;
        int i2;
        int i3;
        boolean z;
        View findContainingItemView = findContainingItemView(view);
        View view2 = null;
        if (findContainingItemView == null) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        int i4 = layoutParams.mSpanIndex;
        int i5 = layoutParams.mSpanSize + i4;
        if (super.onFocusSearchFailed(view, i, recycler, state) == null) {
            return null;
        }
        if ((convertFocusDirectionToLayoutDirection(i) == 1) != this.mShouldReverseLayout) {
            i3 = getChildCount() - 1;
            childCount = -1;
            i2 = -1;
        } else {
            childCount = getChildCount();
            i2 = 1;
            i3 = 0;
        }
        boolean z2 = this.mOrientation == 1 && isLayoutRTL();
        int spanGroupIndex = getSpanGroupIndex(recycler, state, i3);
        int i6 = -1;
        int i7 = -1;
        View view3 = null;
        int i8 = 0;
        int i9 = childCount;
        int i10 = 0;
        for (int i11 = i3; i11 != i9; i11 += i2) {
            int spanGroupIndex2 = getSpanGroupIndex(recycler, state, i11);
            View childAt = getChildAt(i11);
            if (childAt == findContainingItemView) {
                break;
            }
            if (!childAt.hasFocusable() || spanGroupIndex2 == spanGroupIndex) {
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                int i12 = layoutParams2.mSpanIndex;
                int i13 = layoutParams2.mSpanSize + i12;
                if (childAt.hasFocusable() && i12 == i4 && i13 == i5) {
                    return childAt;
                }
                if ((!childAt.hasFocusable() || view2 != null) && (childAt.hasFocusable() || view3 != null)) {
                    int min = Math.min(i13, i5) - Math.max(i12, i4);
                    if (!childAt.hasFocusable()) {
                        if (view2 == null && isViewPartiallyVisible(childAt, false, true)) {
                            int i14 = i8;
                            if (min > i14) {
                                z = true;
                            } else if (min == i14) {
                            }
                        }
                        z = false;
                    } else if (min <= i10) {
                        if (min == i10) {
                        }
                        z = false;
                    }
                    if (z) {
                        if (childAt.hasFocusable()) {
                            i6 = layoutParams2.mSpanIndex;
                            view2 = childAt;
                            i10 = Math.min(i13, i5) - Math.max(i12, i4);
                        } else {
                            i7 = layoutParams2.mSpanIndex;
                            i8 = Math.min(i13, i5) - Math.max(i12, i4);
                            view3 = childAt;
                        }
                    }
                }
                z = true;
                if (z) {
                }
            } else if (view2 != null) {
                break;
            }
        }
        if (view2 == null) {
            view2 = view3;
        }
        return view2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int spanGroupIndex = getSpanGroupIndex(recycler, state, layoutParams2.getViewLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), spanGroupIndex, 1, false, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(spanGroupIndex, 1, layoutParams2.getSpanIndex(), layoutParams2.getSpanSize(), false, false));
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
        this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren(recycler, state);
        clearPreLayoutSpanMappingCache();
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSpanCountChange = false;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            int chooseSize = RecyclerView.LayoutManager.chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            int[] iArr = this.mCachedBorders;
            int chooseSize2 = RecyclerView.LayoutManager.chooseSize(i, iArr[iArr.length - 1] + paddingLeft, getMinimumWidth());
            i4 = chooseSize;
            i3 = chooseSize2;
        } else {
            int chooseSize3 = RecyclerView.LayoutManager.chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            int[] iArr2 = this.mCachedBorders;
            int chooseSize4 = RecyclerView.LayoutManager.chooseSize(i2, iArr2[iArr2.length - 1] + paddingTop, getMinimumHeight());
            i3 = chooseSize3;
            i4 = chooseSize4;
        }
        setMeasuredDimension(i3, i4);
    }

    public void setSpanCount(int i) {
        if (i == this.mSpanCount) {
            return;
        }
        this.mPendingSpanCountChange = true;
        if (i >= 1) {
            this.mSpanCount = i;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
            requestLayout();
            return;
        }
        throw new IllegalArgumentException("Span count should be at least 1. Provided " + i);
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager
    public void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
    }

    public final void updateMeasurements() {
        int height;
        int paddingTop;
        if (getOrientation() == 1) {
            height = getWidth() - getPaddingRight();
            paddingTop = getPaddingLeft();
        } else {
            height = getHeight() - getPaddingBottom();
            paddingTop = getPaddingTop();
        }
        calculateItemBorders(height - paddingTop);
    }
}