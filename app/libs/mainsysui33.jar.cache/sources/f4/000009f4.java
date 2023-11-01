package androidx.viewpager2.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.R$styleable;
import androidx.viewpager2.adapter.StatefulAdapter;

/* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2.class */
public final class ViewPager2 extends ViewGroup {
    public static boolean sFeatureEnhancedA11yEnabled = true;
    public AccessibilityProvider mAccessibilityProvider;
    public int mCurrentItem;
    public RecyclerView.AdapterDataObserver mCurrentItemDataSetChangeObserver;
    public boolean mCurrentItemDirty;
    public CompositeOnPageChangeCallback mExternalPageChangeCallbacks;
    public FakeDrag mFakeDragger;
    public LinearLayoutManager mLayoutManager;
    public int mOffscreenPageLimit;
    public CompositeOnPageChangeCallback mPageChangeEventDispatcher;
    public PageTransformerAdapter mPageTransformerAdapter;
    public PagerSnapHelper mPagerSnapHelper;
    public Parcelable mPendingAdapterState;
    public int mPendingCurrentItem;
    public RecyclerView mRecyclerView;
    public RecyclerView.ItemAnimator mSavedItemAnimator;
    public boolean mSavedItemAnimatorPresent;
    public ScrollEventAdapter mScrollEventAdapter;
    public final Rect mTmpChildRect;
    public final Rect mTmpContainerRect;
    public boolean mUserInputEnabled;

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$AccessibilityProvider.class */
    public abstract class AccessibilityProvider {
        public AccessibilityProvider() {
        }

        public boolean handlesGetAccessibilityClassName() {
            return false;
        }

        public boolean handlesLmPerformAccessibilityAction(int i) {
            return false;
        }

        public boolean handlesPerformAccessibilityAction(int i, Bundle bundle) {
            return false;
        }

        public boolean handlesRvGetAccessibilityClassName() {
            return false;
        }

        public void onAttachAdapter(RecyclerView.Adapter<?> adapter) {
        }

        public void onDetachAdapter(RecyclerView.Adapter<?> adapter) {
        }

        public String onGetAccessibilityClassName() {
            throw new IllegalStateException("Not implemented.");
        }

        public void onInitialize(CompositeOnPageChangeCallback compositeOnPageChangeCallback, RecyclerView recyclerView) {
        }

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        }

        public void onLmInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public void onLmInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public boolean onLmPerformAccessibilityAction(int i) {
            throw new IllegalStateException("Not implemented.");
        }

        public boolean onPerformAccessibilityAction(int i, Bundle bundle) {
            throw new IllegalStateException("Not implemented.");
        }

        public void onRestorePendingState() {
        }

        public CharSequence onRvGetAccessibilityClassName() {
            throw new IllegalStateException("Not implemented.");
        }

        public void onRvInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        }

        public void onSetLayoutDirection() {
        }

        public void onSetNewCurrentItem() {
        }

        public void onSetOrientation() {
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$BasicAccessibilityProvider.class */
    public class BasicAccessibilityProvider extends AccessibilityProvider {
        public BasicAccessibilityProvider() {
            super();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean handlesLmPerformAccessibilityAction(int i) {
            return (i == 8192 || i == 4096) && !ViewPager2.this.isUserInputEnabled();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean handlesRvGetAccessibilityClassName() {
            return true;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onLmInitializeAccessibilityNodeInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (ViewPager2.this.isUserInputEnabled()) {
                return;
            }
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
            accessibilityNodeInfoCompat.removeAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfoCompat.setScrollable(false);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean onLmPerformAccessibilityAction(int i) {
            if (handlesLmPerformAccessibilityAction(i)) {
                return false;
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public CharSequence onRvGetAccessibilityClassName() {
            if (handlesRvGetAccessibilityClassName()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$DataSetChangeObserver.class */
    public static abstract class DataSetChangeObserver extends RecyclerView.AdapterDataObserver {
        public DataSetChangeObserver() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public abstract void onChanged();

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeChanged(int i, int i2, Object obj) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeInserted(int i, int i2) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeMoved(int i, int i2, int i3) {
            onChanged();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public final void onItemRangeRemoved(int i, int i2) {
            onChanged();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$LinearLayoutManagerImpl.class */
    public class LinearLayoutManagerImpl extends LinearLayoutManager {
        public LinearLayoutManagerImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.LinearLayoutManager
        public void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
            int offscreenPageLimit = ViewPager2.this.getOffscreenPageLimit();
            if (offscreenPageLimit == -1) {
                super.calculateExtraLayoutSpace(state, iArr);
                return;
            }
            int pageSize = ViewPager2.this.getPageSize() * offscreenPageLimit;
            iArr[0] = pageSize;
            iArr[1] = pageSize;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onInitializeAccessibilityNodeInfo(RecyclerView.Recycler recycler, RecyclerView.State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(recycler, state, accessibilityNodeInfoCompat);
            ViewPager2.this.mAccessibilityProvider.onLmInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewPager2.this.mAccessibilityProvider.onLmInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean performAccessibilityAction(RecyclerView.Recycler recycler, RecyclerView.State state, int i, Bundle bundle) {
            return ViewPager2.this.mAccessibilityProvider.handlesLmPerformAccessibilityAction(i) ? ViewPager2.this.mAccessibilityProvider.onLmPerformAccessibilityAction(i) : super.performAccessibilityAction(recycler, state, i, bundle);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view, Rect rect, boolean z, boolean z2) {
            return false;
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$OnPageChangeCallback.class */
    public static abstract class OnPageChangeCallback {
        public void onPageScrollStateChanged(int i) {
        }

        public void onPageScrolled(int i, float f, int i2) {
        }

        public void onPageSelected(int i) {
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$PageAwareAccessibilityProvider.class */
    public class PageAwareAccessibilityProvider extends AccessibilityProvider {
        public final AccessibilityViewCommand mActionPageBackward;
        public final AccessibilityViewCommand mActionPageForward;
        public RecyclerView.AdapterDataObserver mAdapterDataObserver;

        public PageAwareAccessibilityProvider() {
            super();
            this.mActionPageForward = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.1
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                    PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(((ViewPager2) view).getCurrentItem() + 1);
                    return true;
                }
            };
            this.mActionPageBackward = new AccessibilityViewCommand() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.2
                @Override // androidx.core.view.accessibility.AccessibilityViewCommand
                public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                    PageAwareAccessibilityProvider.this.setCurrentItemFromAccessibilityCommand(((ViewPager2) view).getCurrentItem() - 1);
                    return true;
                }
            };
        }

        public final void addCollectionInfo(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i;
            int i2 = 1;
            if (ViewPager2.this.getAdapter() == null) {
                i = 0;
                i2 = 0;
            } else if (ViewPager2.this.getOrientation() == 1) {
                i2 = ViewPager2.this.getAdapter().getItemCount();
                i = 1;
            } else {
                i = ViewPager2.this.getAdapter().getItemCount();
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(i2, i, false, 0));
        }

        public final void addCollectionItemInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int i = 0;
            int position = ViewPager2.this.getOrientation() == 1 ? ViewPager2.this.mLayoutManager.getPosition(view) : 0;
            if (ViewPager2.this.getOrientation() == 0) {
                i = ViewPager2.this.mLayoutManager.getPosition(view);
            }
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(position, 1, i, 1, false, false));
        }

        public final void addScrollActions(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            int itemCount;
            RecyclerView.Adapter adapter = ViewPager2.this.getAdapter();
            if (adapter == null || (itemCount = adapter.getItemCount()) == 0 || !ViewPager2.this.isUserInputEnabled()) {
                return;
            }
            if (ViewPager2.this.mCurrentItem > 0) {
                accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST);
            }
            if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT);
            }
            accessibilityNodeInfoCompat.setScrollable(true);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean handlesGetAccessibilityClassName() {
            return true;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean handlesPerformAccessibilityAction(int i, Bundle bundle) {
            return i == 8192 || i == 4096;
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onAttachAdapter(RecyclerView.Adapter<?> adapter) {
            updatePageAccessibilityActions();
            if (adapter != null) {
                adapter.registerAdapterDataObserver(this.mAdapterDataObserver);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onDetachAdapter(RecyclerView.Adapter<?> adapter) {
            if (adapter != null) {
                adapter.unregisterAdapterDataObserver(this.mAdapterDataObserver);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public String onGetAccessibilityClassName() {
            if (handlesGetAccessibilityClassName()) {
                return "androidx.viewpager.widget.ViewPager";
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onInitialize(CompositeOnPageChangeCallback compositeOnPageChangeCallback, RecyclerView recyclerView) {
            ViewCompat.setImportantForAccessibility(recyclerView, 2);
            this.mAdapterDataObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.PageAwareAccessibilityProvider.3
                @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
                public void onChanged() {
                    PageAwareAccessibilityProvider.this.updatePageAccessibilityActions();
                }
            };
            if (ViewCompat.getImportantForAccessibility(ViewPager2.this) == 0) {
                ViewCompat.setImportantForAccessibility(ViewPager2.this, 1);
            }
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
            AccessibilityNodeInfoCompat wrap = AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo);
            addCollectionInfo(wrap);
            addScrollActions(wrap);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onLmInitializeAccessibilityNodeInfoForItem(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            addCollectionItemInfo(view, accessibilityNodeInfoCompat);
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public boolean onPerformAccessibilityAction(int i, Bundle bundle) {
            if (handlesPerformAccessibilityAction(i, bundle)) {
                setCurrentItemFromAccessibilityCommand(i == 8192 ? ViewPager2.this.getCurrentItem() - 1 : ViewPager2.this.getCurrentItem() + 1);
                return true;
            }
            throw new IllegalStateException();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onRestorePendingState() {
            updatePageAccessibilityActions();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onRvInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            accessibilityEvent.setSource(ViewPager2.this);
            accessibilityEvent.setClassName(onGetAccessibilityClassName());
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onSetLayoutDirection() {
            updatePageAccessibilityActions();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onSetNewCurrentItem() {
            updatePageAccessibilityActions();
        }

        @Override // androidx.viewpager2.widget.ViewPager2.AccessibilityProvider
        public void onSetOrientation() {
            updatePageAccessibilityActions();
        }

        public void setCurrentItemFromAccessibilityCommand(int i) {
            if (ViewPager2.this.isUserInputEnabled()) {
                ViewPager2.this.setCurrentItemInternal(i, true);
            }
        }

        public void updatePageAccessibilityActions() {
            int itemCount;
            ViewPager2 viewPager2 = ViewPager2.this;
            int i = 16908360;
            ViewCompat.removeAccessibilityAction(viewPager2, 16908360);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908361);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908358);
            ViewCompat.removeAccessibilityAction(viewPager2, 16908359);
            if (ViewPager2.this.getAdapter() == null || (itemCount = ViewPager2.this.getAdapter().getItemCount()) == 0 || !ViewPager2.this.isUserInputEnabled()) {
                return;
            }
            if (ViewPager2.this.getOrientation() != 0) {
                if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908359, null), null, this.mActionPageForward);
                }
                if (ViewPager2.this.mCurrentItem > 0) {
                    ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(16908358, null), null, this.mActionPageBackward);
                    return;
                }
                return;
            }
            boolean isRtl = ViewPager2.this.isRtl();
            int i2 = isRtl ? 16908360 : 16908361;
            if (isRtl) {
                i = 16908361;
            }
            if (ViewPager2.this.mCurrentItem < itemCount - 1) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i2, null), null, this.mActionPageForward);
            }
            if (ViewPager2.this.mCurrentItem > 0) {
                ViewCompat.replaceAccessibilityAction(viewPager2, new AccessibilityNodeInfoCompat.AccessibilityActionCompat(i, null), null, this.mActionPageBackward);
            }
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$PagerSnapHelperImpl.class */
    public class PagerSnapHelperImpl extends PagerSnapHelper {
        public PagerSnapHelperImpl() {
        }

        @Override // androidx.recyclerview.widget.PagerSnapHelper, androidx.recyclerview.widget.SnapHelper
        public View findSnapView(RecyclerView.LayoutManager layoutManager) {
            return ViewPager2.this.isFakeDragging() ? null : super.findSnapView(layoutManager);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$RecyclerViewImpl.class */
    public class RecyclerViewImpl extends RecyclerView {
        public RecyclerViewImpl(Context context) {
            super(context);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup, android.view.View
        public CharSequence getAccessibilityClassName() {
            return ViewPager2.this.mAccessibilityProvider.handlesRvGetAccessibilityClassName() ? ViewPager2.this.mAccessibilityProvider.onRvGetAccessibilityClassName() : super.getAccessibilityClassName();
        }

        @Override // android.view.View
        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.setFromIndex(ViewPager2.this.mCurrentItem);
            accessibilityEvent.setToIndex(ViewPager2.this.mCurrentItem);
            ViewPager2.this.mAccessibilityProvider.onRvInitializeAccessibilityEvent(accessibilityEvent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.isUserInputEnabled() && super.onInterceptTouchEvent(motionEvent);
        }

        @Override // androidx.recyclerview.widget.RecyclerView, android.view.View
        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return ViewPager2.this.isUserInputEnabled() && super.onTouchEvent(motionEvent);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$SavedState.class */
    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.viewpager2.widget.ViewPager2.SavedState.1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return createFromParcel(parcel, (ClassLoader) null);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            /* JADX DEBUG: Method merged with bridge method */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public Parcelable mAdapterState;
        public int mCurrentItem;
        public int mRecyclerViewId;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            readValues(parcel, classLoader);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final void readValues(Parcel parcel, ClassLoader classLoader) {
            this.mRecyclerViewId = parcel.readInt();
            this.mCurrentItem = parcel.readInt();
            this.mAdapterState = parcel.readParcelable(classLoader);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.mRecyclerViewId);
            parcel.writeInt(this.mCurrentItem);
            parcel.writeParcelable(this.mAdapterState, i);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager2/widget/ViewPager2$SmoothScrollToPosition.class */
    public static class SmoothScrollToPosition implements Runnable {
        public final int mPosition;
        public final RecyclerView mRecyclerView;

        public SmoothScrollToPosition(int i, RecyclerView recyclerView) {
            this.mPosition = i;
            this.mRecyclerView = recyclerView;
        }

        @Override // java.lang.Runnable
        public void run() {
            this.mRecyclerView.smoothScrollToPosition(this.mPosition);
        }
    }

    public ViewPager2(Context context) {
        super(context);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.notifyDataSetChangeHappened();
            }
        };
        this.mPendingCurrentItem = -1;
        this.mSavedItemAnimator = null;
        this.mSavedItemAnimatorPresent = false;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, null);
    }

    public ViewPager2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.notifyDataSetChangeHappened();
            }
        };
        this.mPendingCurrentItem = -1;
        this.mSavedItemAnimator = null;
        this.mSavedItemAnimatorPresent = false;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.notifyDataSetChangeHappened();
            }
        };
        this.mPendingCurrentItem = -1;
        this.mSavedItemAnimator = null;
        this.mSavedItemAnimatorPresent = false;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }

    public ViewPager2(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTmpContainerRect = new Rect();
        this.mTmpChildRect = new Rect();
        this.mExternalPageChangeCallbacks = new CompositeOnPageChangeCallback(3);
        this.mCurrentItemDirty = false;
        this.mCurrentItemDataSetChangeObserver = new DataSetChangeObserver() { // from class: androidx.viewpager2.widget.ViewPager2.1
            @Override // androidx.viewpager2.widget.ViewPager2.DataSetChangeObserver, androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
            public void onChanged() {
                ViewPager2 viewPager2 = ViewPager2.this;
                viewPager2.mCurrentItemDirty = true;
                viewPager2.mScrollEventAdapter.notifyDataSetChangeHappened();
            }
        };
        this.mPendingCurrentItem = -1;
        this.mSavedItemAnimator = null;
        this.mSavedItemAnimatorPresent = false;
        this.mUserInputEnabled = true;
        this.mOffscreenPageLimit = -1;
        initialize(context, attributeSet);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        return this.mRecyclerView.canScrollHorizontally(i);
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i) {
        return this.mRecyclerView.canScrollVertically(i);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        Parcelable parcelable = sparseArray.get(getId());
        if (parcelable instanceof SavedState) {
            int i = ((SavedState) parcelable).mRecyclerViewId;
            sparseArray.put(this.mRecyclerView.getId(), sparseArray.get(i));
            sparseArray.remove(i);
        }
        super.dispatchRestoreInstanceState(sparseArray);
        restorePendingState();
    }

    public final RecyclerView.OnChildAttachStateChangeListener enforceChildFillListener() {
        return new RecyclerView.OnChildAttachStateChangeListener() { // from class: androidx.viewpager2.widget.ViewPager2.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewAttachedToWindow(View view) {
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                if (((ViewGroup.MarginLayoutParams) layoutParams).width != -1 || ((ViewGroup.MarginLayoutParams) layoutParams).height != -1) {
                    throw new IllegalStateException("Pages must fill the whole ViewPager2 (use match_parent)");
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnChildAttachStateChangeListener
            public void onChildViewDetachedFromWindow(View view) {
            }
        };
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return this.mAccessibilityProvider.handlesGetAccessibilityClassName() ? this.mAccessibilityProvider.onGetAccessibilityClassName() : super.getAccessibilityClassName();
    }

    public RecyclerView.Adapter getAdapter() {
        return this.mRecyclerView.getAdapter();
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getOrientation() {
        return this.mLayoutManager.getOrientation();
    }

    public int getPageSize() {
        int height;
        int paddingBottom;
        RecyclerView recyclerView = this.mRecyclerView;
        if (getOrientation() == 0) {
            height = recyclerView.getWidth() - recyclerView.getPaddingLeft();
            paddingBottom = recyclerView.getPaddingRight();
        } else {
            height = recyclerView.getHeight() - recyclerView.getPaddingTop();
            paddingBottom = recyclerView.getPaddingBottom();
        }
        return height - paddingBottom;
    }

    public int getScrollState() {
        return this.mScrollEventAdapter.getScrollState();
    }

    public final void initialize(Context context, AttributeSet attributeSet) {
        this.mAccessibilityProvider = sFeatureEnhancedA11yEnabled ? new PageAwareAccessibilityProvider() : new BasicAccessibilityProvider();
        RecyclerViewImpl recyclerViewImpl = new RecyclerViewImpl(context);
        this.mRecyclerView = recyclerViewImpl;
        recyclerViewImpl.setId(ViewCompat.generateViewId());
        this.mRecyclerView.setDescendantFocusability(131072);
        LinearLayoutManagerImpl linearLayoutManagerImpl = new LinearLayoutManagerImpl(context);
        this.mLayoutManager = linearLayoutManagerImpl;
        this.mRecyclerView.setLayoutManager(linearLayoutManagerImpl);
        this.mRecyclerView.setScrollingTouchSlop(1);
        setOrientation(context, attributeSet);
        this.mRecyclerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        this.mRecyclerView.addOnChildAttachStateChangeListener(enforceChildFillListener());
        ScrollEventAdapter scrollEventAdapter = new ScrollEventAdapter(this);
        this.mScrollEventAdapter = scrollEventAdapter;
        this.mFakeDragger = new FakeDrag(this, scrollEventAdapter, this.mRecyclerView);
        PagerSnapHelperImpl pagerSnapHelperImpl = new PagerSnapHelperImpl();
        this.mPagerSnapHelper = pagerSnapHelperImpl;
        pagerSnapHelperImpl.attachToRecyclerView(this.mRecyclerView);
        this.mRecyclerView.addOnScrollListener(this.mScrollEventAdapter);
        CompositeOnPageChangeCallback compositeOnPageChangeCallback = new CompositeOnPageChangeCallback(3);
        this.mPageChangeEventDispatcher = compositeOnPageChangeCallback;
        this.mScrollEventAdapter.setOnPageChangeCallback(compositeOnPageChangeCallback);
        OnPageChangeCallback onPageChangeCallback = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.2
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageScrollStateChanged(int i) {
                if (i == 0) {
                    ViewPager2.this.updateCurrentItem();
                }
            }

            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                ViewPager2 viewPager2 = ViewPager2.this;
                if (viewPager2.mCurrentItem != i) {
                    viewPager2.mCurrentItem = i;
                    viewPager2.mAccessibilityProvider.onSetNewCurrentItem();
                }
            }
        };
        OnPageChangeCallback onPageChangeCallback2 = new OnPageChangeCallback() { // from class: androidx.viewpager2.widget.ViewPager2.3
            @Override // androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
            public void onPageSelected(int i) {
                ViewPager2.this.clearFocus();
                if (ViewPager2.this.hasFocus()) {
                    ViewPager2.this.mRecyclerView.requestFocus(2);
                }
            }
        };
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(onPageChangeCallback);
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(onPageChangeCallback2);
        this.mAccessibilityProvider.onInitialize(this.mPageChangeEventDispatcher, this.mRecyclerView);
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(this.mExternalPageChangeCallbacks);
        PageTransformerAdapter pageTransformerAdapter = new PageTransformerAdapter(this.mLayoutManager);
        this.mPageTransformerAdapter = pageTransformerAdapter;
        this.mPageChangeEventDispatcher.addOnPageChangeCallback(pageTransformerAdapter);
        RecyclerView recyclerView = this.mRecyclerView;
        attachViewToParent(recyclerView, 0, recyclerView.getLayoutParams());
    }

    public boolean isFakeDragging() {
        return this.mFakeDragger.isFakeDragging();
    }

    public boolean isRtl() {
        boolean z = true;
        if (this.mLayoutManager.getLayoutDirection() != 1) {
            z = false;
        }
        return z;
    }

    public boolean isUserInputEnabled() {
        return this.mUserInputEnabled;
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        this.mAccessibilityProvider.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth = this.mRecyclerView.getMeasuredWidth();
        int measuredHeight = this.mRecyclerView.getMeasuredHeight();
        this.mTmpContainerRect.left = getPaddingLeft();
        this.mTmpContainerRect.right = (i3 - i) - getPaddingRight();
        this.mTmpContainerRect.top = getPaddingTop();
        this.mTmpContainerRect.bottom = (i4 - i2) - getPaddingBottom();
        Gravity.apply(8388659, measuredWidth, measuredHeight, this.mTmpContainerRect, this.mTmpChildRect);
        RecyclerView recyclerView = this.mRecyclerView;
        Rect rect = this.mTmpChildRect;
        recyclerView.layout(rect.left, rect.top, rect.right, rect.bottom);
        if (this.mCurrentItemDirty) {
            updateCurrentItem();
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        measureChild(this.mRecyclerView, i, i2);
        int measuredWidth = this.mRecyclerView.getMeasuredWidth();
        int measuredHeight = this.mRecyclerView.getMeasuredHeight();
        int measuredState = this.mRecyclerView.getMeasuredState();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        setMeasuredDimension(ViewGroup.resolveSizeAndState(Math.max(measuredWidth + paddingLeft + paddingRight, getSuggestedMinimumWidth()), i, measuredState), ViewGroup.resolveSizeAndState(Math.max(measuredHeight + paddingTop + paddingBottom, getSuggestedMinimumHeight()), i2, measuredState << 16));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mPendingCurrentItem = savedState.mCurrentItem;
        this.mPendingAdapterState = savedState.mAdapterState;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.mRecyclerViewId = this.mRecyclerView.getId();
        int i = this.mPendingCurrentItem;
        int i2 = i;
        if (i == -1) {
            i2 = this.mCurrentItem;
        }
        savedState.mCurrentItem = i2;
        Parcelable parcelable = this.mPendingAdapterState;
        if (parcelable != null) {
            savedState.mAdapterState = parcelable;
        } else {
            RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
            if (adapter instanceof StatefulAdapter) {
                savedState.mAdapterState = ((StatefulAdapter) adapter).saveState();
            }
        }
        return savedState;
    }

    @Override // android.view.ViewGroup
    public void onViewAdded(View view) {
        throw new IllegalStateException(ViewPager2.class.getSimpleName() + " does not support direct child views");
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        return this.mAccessibilityProvider.handlesPerformAccessibilityAction(i, bundle) ? this.mAccessibilityProvider.onPerformAccessibilityAction(i, bundle) : super.performAccessibilityAction(i, bundle);
    }

    public final void registerCurrentItemDataSetTracker(RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.registerAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
        }
    }

    public void registerOnPageChangeCallback(OnPageChangeCallback onPageChangeCallback) {
        this.mExternalPageChangeCallbacks.addOnPageChangeCallback(onPageChangeCallback);
    }

    public final void restorePendingState() {
        RecyclerView.Adapter adapter;
        if (this.mPendingCurrentItem == -1 || (adapter = getAdapter()) == null) {
            return;
        }
        Parcelable parcelable = this.mPendingAdapterState;
        if (parcelable != null) {
            if (adapter instanceof StatefulAdapter) {
                ((StatefulAdapter) adapter).restoreState(parcelable);
            }
            this.mPendingAdapterState = null;
        }
        int max = Math.max(0, Math.min(this.mPendingCurrentItem, adapter.getItemCount() - 1));
        this.mCurrentItem = max;
        this.mPendingCurrentItem = -1;
        this.mRecyclerView.scrollToPosition(max);
        this.mAccessibilityProvider.onRestorePendingState();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        RecyclerView.Adapter adapter2 = this.mRecyclerView.getAdapter();
        this.mAccessibilityProvider.onDetachAdapter(adapter2);
        unregisterCurrentItemDataSetTracker(adapter2);
        this.mRecyclerView.setAdapter(adapter);
        this.mCurrentItem = 0;
        restorePendingState();
        this.mAccessibilityProvider.onAttachAdapter(adapter);
        registerCurrentItemDataSetTracker(adapter);
    }

    public void setCurrentItem(int i) {
        setCurrentItem(i, true);
    }

    public void setCurrentItem(int i, boolean z) {
        if (isFakeDragging()) {
            throw new IllegalStateException("Cannot change current item when ViewPager2 is fake dragging");
        }
        setCurrentItemInternal(i, z);
    }

    public void setCurrentItemInternal(int i, boolean z) {
        RecyclerView.Adapter adapter = getAdapter();
        if (adapter == null) {
            if (this.mPendingCurrentItem != -1) {
                this.mPendingCurrentItem = Math.max(i, 0);
            }
        } else if (adapter.getItemCount() <= 0) {
        } else {
            int min = Math.min(Math.max(i, 0), adapter.getItemCount() - 1);
            if (min == this.mCurrentItem && this.mScrollEventAdapter.isIdle()) {
                return;
            }
            int i2 = this.mCurrentItem;
            if (min == i2 && z) {
                return;
            }
            double d = i2;
            this.mCurrentItem = min;
            this.mAccessibilityProvider.onSetNewCurrentItem();
            if (!this.mScrollEventAdapter.isIdle()) {
                d = this.mScrollEventAdapter.getRelativeScrollPosition();
            }
            this.mScrollEventAdapter.notifyProgrammaticScroll(min, z);
            if (!z) {
                this.mRecyclerView.scrollToPosition(min);
                return;
            }
            double d2 = min;
            if (Math.abs(d2 - d) <= 3.0d) {
                this.mRecyclerView.smoothScrollToPosition(min);
                return;
            }
            this.mRecyclerView.scrollToPosition(d2 > d ? min - 3 : min + 3);
            RecyclerView recyclerView = this.mRecyclerView;
            recyclerView.post(new SmoothScrollToPosition(min, recyclerView));
        }
    }

    @Override // android.view.View
    public void setLayoutDirection(int i) {
        super.setLayoutDirection(i);
        this.mAccessibilityProvider.onSetLayoutDirection();
    }

    public void setOrientation(int i) {
        this.mLayoutManager.setOrientation(i);
        this.mAccessibilityProvider.onSetOrientation();
    }

    public final void setOrientation(Context context, AttributeSet attributeSet) {
        int[] iArr = R$styleable.ViewPager2;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr);
        ViewCompat.saveAttributeDataForStyleable(this, context, iArr, attributeSet, obtainStyledAttributes, 0, 0);
        try {
            setOrientation(obtainStyledAttributes.getInt(R$styleable.ViewPager2_android_orientation, 0));
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public final void unregisterCurrentItemDataSetTracker(RecyclerView.Adapter<?> adapter) {
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(this.mCurrentItemDataSetChangeObserver);
        }
    }

    public void unregisterOnPageChangeCallback(OnPageChangeCallback onPageChangeCallback) {
        this.mExternalPageChangeCallbacks.removeOnPageChangeCallback(onPageChangeCallback);
    }

    public void updateCurrentItem() {
        PagerSnapHelper pagerSnapHelper = this.mPagerSnapHelper;
        if (pagerSnapHelper == null) {
            throw new IllegalStateException("Design assumption violated.");
        }
        View findSnapView = pagerSnapHelper.findSnapView(this.mLayoutManager);
        if (findSnapView == null) {
            return;
        }
        int position = this.mLayoutManager.getPosition(findSnapView);
        if (position != this.mCurrentItem && getScrollState() == 0) {
            this.mPageChangeEventDispatcher.onPageSelected(position);
        }
        this.mCurrentItemDirty = false;
    }
}