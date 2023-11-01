package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.customview.view.AbsSavedState;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.widget.ActionBarShadowController;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager.class */
public class ViewPager extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.viewpager.widget.ViewPager";
    private static final int CLOSE_ENOUGH = 2;
    private static final boolean DEBUG = false;
    private static final int DEFAULT_GUTTER_SIZE = 16;
    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    private static final int MIN_FLING_VELOCITY = 400;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "ViewPager";
    private int mActivePointerId;
    public PagerAdapter mAdapter;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private int mBottomPageBounds;
    private boolean mCalledSuper;
    private int mCloseEnough;
    public int mCurItem;
    private int mDecorChildCount;
    private int mDefaultGutterSize;
    private boolean mDragInGutterEnabled;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private final Runnable mEndScrollRunnable;
    private int mExpectedAdapterCount;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout;
    private float mFirstOffset;
    private int mFlingDistance;
    private int mGutterSize;
    private boolean mInLayout;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private OnPageChangeListener mInternalPageChangeListener;
    private boolean mIsBeingDragged;
    private boolean mIsScrollStarted;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private float mLastOffset;
    public EdgeEffect mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private OnPageChangeListener mOnPageChangeListener;
    private List<OnPageChangeListener> mOnPageChangeListeners;
    private int mPageMargin;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    public EdgeEffect mRightEdge;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrollingCacheEnabled;
    private final ItemInfo mTempItem;
    private final Rect mTempRect;
    private int mTopPageBounds;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    public static final int[] LAYOUT_ATTRS = {16842931};
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() { // from class: androidx.viewpager.widget.ViewPager.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // java.util.Comparator
        public int compare(ItemInfo itemInfo, ItemInfo itemInfo2) {
            return itemInfo.position - itemInfo2.position;
        }
    };
    private static final Interpolator sInterpolator = new Interpolator() { // from class: androidx.viewpager.widget.ViewPager.2
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();

    @Inherited
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$DecorView.class */
    public @interface DecorView {
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$ItemInfo.class */
    public static class ItemInfo {
        public Object object;
        public float offset;
        public int position;
        public boolean scrolling;
        public float widthFactor;
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$LayoutParams.class */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        public int childIndex;
        public int gravity;
        public boolean isDecor;
        public boolean needsMeasure;
        public int position;
        public float widthFactor;

        public LayoutParams() {
            super(-1, -1);
            this.widthFactor = ActionBarShadowController.ELEVATION_LOW;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.widthFactor = ActionBarShadowController.ELEVATION_LOW;
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, ViewPager.LAYOUT_ATTRS);
            this.gravity = obtainStyledAttributes.getInteger(0, 48);
            obtainStyledAttributes.recycle();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$MyAccessibilityDelegate.class */
    public class MyAccessibilityDelegate extends AccessibilityDelegateCompat {
        public MyAccessibilityDelegate() {
        }

        public final boolean canScroll() {
            PagerAdapter pagerAdapter = ViewPager.this.mAdapter;
            boolean z = true;
            if (pagerAdapter == null || pagerAdapter.getCount() <= 1) {
                z = false;
            }
            return z;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            PagerAdapter pagerAdapter;
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(ViewPager.ACCESSIBILITY_CLASS_NAME);
            accessibilityEvent.setScrollable(canScroll());
            if (accessibilityEvent.getEventType() != 4096 || (pagerAdapter = ViewPager.this.mAdapter) == null) {
                return;
            }
            accessibilityEvent.setItemCount(pagerAdapter.getCount());
            accessibilityEvent.setFromIndex(ViewPager.this.mCurItem);
            accessibilityEvent.setToIndex(ViewPager.this.mCurItem);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(ViewPager.ACCESSIBILITY_CLASS_NAME);
            accessibilityNodeInfoCompat.setScrollable(canScroll());
            if (ViewPager.this.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT);
            }
            if (ViewPager.this.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST);
            }
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
            if (super.performAccessibilityAction(view, i, bundle)) {
                return true;
            }
            if (i == 4096) {
                if (ViewPager.this.canScrollHorizontally(1)) {
                    ViewPager viewPager = ViewPager.this;
                    viewPager.setCurrentItem(viewPager.mCurItem + 1);
                    return true;
                }
                return false;
            } else if (i == 8192 && ViewPager.this.canScrollHorizontally(-1)) {
                ViewPager viewPager2 = ViewPager.this;
                viewPager2.setCurrentItem(viewPager2.mCurItem - 1);
                return true;
            } else {
                return false;
            }
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$OnAdapterChangeListener.class */
    public interface OnAdapterChangeListener {
        void onAdapterChanged(ViewPager viewPager, PagerAdapter pagerAdapter, PagerAdapter pagerAdapter2);
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$OnPageChangeListener.class */
    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int i);

        void onPageScrolled(int i, float f, int i2);

        void onPageSelected(int i);
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$PageTransformer.class */
    public interface PageTransformer {
        void transformPage(View view, float f);
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$PagerObserver.class */
    public class PagerObserver extends DataSetObserver {
        public PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            ViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ViewPager.this.dataSetChanged();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$SavedState.class */
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: androidx.viewpager.widget.ViewPager.SavedState.1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
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
        public Parcelable adapterState;
        public ClassLoader loader;
        public int position;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            ClassLoader classLoader2 = classLoader == null ? getClass().getClassLoader() : classLoader;
            this.position = parcel.readInt();
            this.adapterState = parcel.readParcelable(classLoader2);
            this.loader = classLoader2;
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.position);
            parcel.writeParcelable(this.adapterState, i);
        }
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$SimpleOnPageChangeListener.class */
    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
    }

    /* loaded from: mainsysui33.jar:androidx/viewpager/widget/ViewPager$ViewPositionComparator.class */
    public static class ViewPositionComparator implements Comparator<View> {
        /* JADX DEBUG: Method merged with bridge method */
        @Override // java.util.Comparator
        public int compare(View view, View view2) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            boolean z = layoutParams.isDecor;
            if (z != layoutParams2.isDecor) {
                return z ? 1 : -1;
            }
            return layoutParams.position - layoutParams2.position;
        }
    }

    public ViewPager(Context context) {
        super(context);
        this.mItems = new ArrayList<>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new Runnable() { // from class: androidx.viewpager.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        initViewPager(context, null);
    }

    public ViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mItems = new ArrayList<>();
        this.mTempItem = new ItemInfo();
        this.mTempRect = new Rect();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mFirstOffset = -3.4028235E38f;
        this.mLastOffset = Float.MAX_VALUE;
        this.mOffscreenPageLimit = 1;
        this.mDragInGutterEnabled = true;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mEndScrollRunnable = new Runnable() { // from class: androidx.viewpager.widget.ViewPager.3
            @Override // java.lang.Runnable
            public void run() {
                ViewPager.this.setScrollState(0);
                ViewPager.this.populate();
            }
        };
        this.mScrollState = 0;
        initViewPager(context, attributeSet);
    }

    private void calculatePageOffsets(ItemInfo itemInfo, int i, ItemInfo itemInfo2) {
        int i2;
        int i3;
        ItemInfo itemInfo3;
        int i4;
        float f;
        ItemInfo itemInfo4;
        float f2;
        int count = this.mAdapter.getCount();
        int clientWidth = getClientWidth();
        float f3 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        if (itemInfo2 != null) {
            int i5 = itemInfo2.position;
            int i6 = itemInfo.position;
            if (i5 < i6) {
                int i7 = 0;
                float f4 = itemInfo2.offset + itemInfo2.widthFactor;
                float f5 = f3;
                while (true) {
                    float f6 = f4 + f5;
                    int i8 = i5 + 1;
                    if (i8 > itemInfo.position || i7 >= this.mItems.size()) {
                        break;
                    }
                    ItemInfo itemInfo5 = this.mItems.get(i7);
                    while (true) {
                        itemInfo4 = itemInfo5;
                        i5 = i8;
                        f2 = f6;
                        if (i8 <= itemInfo4.position) {
                            break;
                        }
                        i5 = i8;
                        f2 = f6;
                        if (i7 >= this.mItems.size() - 1) {
                            break;
                        }
                        i7++;
                        itemInfo5 = this.mItems.get(i7);
                    }
                    while (i5 < itemInfo4.position) {
                        f2 += this.mAdapter.getPageWidth(i5) + f3;
                        i5++;
                    }
                    itemInfo4.offset = f2;
                    f4 = f2;
                    f5 = itemInfo4.widthFactor + f3;
                }
            } else if (i5 > i6) {
                int size = this.mItems.size() - 1;
                float f7 = itemInfo2.offset;
                for (int i9 = i5 - 1; i9 >= itemInfo.position && size >= 0; i9 = i4 - 1) {
                    ItemInfo itemInfo6 = this.mItems.get(size);
                    while (true) {
                        itemInfo3 = itemInfo6;
                        i4 = i9;
                        f = f7;
                        if (i9 >= itemInfo3.position) {
                            break;
                        }
                        i4 = i9;
                        f = f7;
                        if (size <= 0) {
                            break;
                        }
                        size--;
                        itemInfo6 = this.mItems.get(size);
                    }
                    while (i4 > itemInfo3.position) {
                        f -= this.mAdapter.getPageWidth(i4) + f3;
                        i4--;
                    }
                    f7 = f - (itemInfo3.widthFactor + f3);
                    itemInfo3.offset = f7;
                }
            }
        }
        int size2 = this.mItems.size();
        float f8 = itemInfo.offset;
        int i10 = itemInfo.position;
        int i11 = i10 - 1;
        this.mFirstOffset = i10 == 0 ? f8 : -3.4028235E38f;
        int i12 = count - 1;
        this.mLastOffset = i10 == i12 ? (itemInfo.widthFactor + f8) - 1.0f : Float.MAX_VALUE;
        int i13 = i - 1;
        while (i13 >= 0) {
            ItemInfo itemInfo7 = this.mItems.get(i13);
            while (true) {
                i3 = itemInfo7.position;
                if (i11 <= i3) {
                    break;
                }
                f8 -= this.mAdapter.getPageWidth(i11) + f3;
                i11--;
            }
            f8 -= itemInfo7.widthFactor + f3;
            itemInfo7.offset = f8;
            if (i3 == 0) {
                this.mFirstOffset = f8;
            }
            i13--;
            i11--;
        }
        float f9 = itemInfo.offset + itemInfo.widthFactor + f3;
        int i14 = i + 1;
        int i15 = itemInfo.position + 1;
        while (i14 < size2) {
            ItemInfo itemInfo8 = this.mItems.get(i14);
            while (true) {
                i2 = itemInfo8.position;
                if (i15 >= i2) {
                    break;
                }
                f9 += this.mAdapter.getPageWidth(i15) + f3;
                i15++;
            }
            if (i2 == i12) {
                this.mLastOffset = (itemInfo8.widthFactor + f9) - 1.0f;
            }
            itemInfo8.offset = f9;
            f9 += itemInfo8.widthFactor + f3;
            i14++;
            i15++;
        }
    }

    private void completeScroll(boolean z) {
        boolean z2 = this.mScrollState == 2;
        if (z2) {
            setScrollingCacheEnabled(false);
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                int scrollX = getScrollX();
                int scrollY = getScrollY();
                int currX = this.mScroller.getCurrX();
                int currY = this.mScroller.getCurrY();
                if (scrollX != currX || scrollY != currY) {
                    scrollTo(currX, currY);
                    if (currX != scrollX) {
                        pageScrolled(currX);
                    }
                }
            }
        }
        this.mPopulatePending = false;
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (z2) {
            if (z) {
                ViewCompat.postOnAnimation(this, this.mEndScrollRunnable);
            } else {
                this.mEndScrollRunnable.run();
            }
        }
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity || EdgeEffectCompat.getDistance(this.mLeftEdge) != ActionBarShadowController.ELEVATION_LOW || EdgeEffectCompat.getDistance(this.mRightEdge) != ActionBarShadowController.ELEVATION_LOW) {
            i += (int) (f + (i >= this.mCurItem ? 0.4f : 0.6f));
        } else if (i2 <= 0) {
            i++;
        }
        int i4 = i;
        if (this.mItems.size() > 0) {
            ItemInfo itemInfo = this.mItems.get(0);
            ArrayList<ItemInfo> arrayList = this.mItems;
            i4 = Math.max(itemInfo.position, Math.min(i, arrayList.get(arrayList.size() - 1).position));
        }
        return i4;
    }

    private void dispatchOnPageScrolled(int i, float f, int i2) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i, f, i2);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i3 = 0; i3 < size; i3++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i3);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageScrolled(i, f, i2);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrolled(i, f, i2);
        }
    }

    private void dispatchOnPageSelected(int i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i2);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageSelected(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageSelected(i);
        }
    }

    private void dispatchOnScrollStateChanged(int i) {
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnPageChangeListener onPageChangeListener2 = this.mOnPageChangeListeners.get(i2);
                if (onPageChangeListener2 != null) {
                    onPageChangeListener2.onPageScrollStateChanged(i);
                }
            }
        }
        OnPageChangeListener onPageChangeListener3 = this.mInternalPageChangeListener;
        if (onPageChangeListener3 != null) {
            onPageChangeListener3.onPageScrollStateChanged(i);
        }
    }

    private void enableLayers(boolean z) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setLayerType(z ? this.mPageTransformerLayerType : 0, null);
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private Rect getChildRectInPagerCoordinates(Rect rect, View view) {
        Rect rect2 = rect;
        if (rect == null) {
            rect2 = new Rect();
        }
        if (view == null) {
            rect2.set(0, 0, 0, 0);
            return rect2;
        }
        rect2.left = view.getLeft();
        rect2.right = view.getRight();
        rect2.top = view.getTop();
        rect2.bottom = view.getBottom();
        ViewParent parent = view.getParent();
        while (true) {
            ViewParent viewParent = parent;
            if (!(viewParent instanceof ViewGroup) || viewParent == this) {
                break;
            }
            ViewGroup viewGroup = (ViewGroup) viewParent;
            rect2.left += viewGroup.getLeft();
            rect2.right += viewGroup.getRight();
            rect2.top += viewGroup.getTop();
            rect2.bottom += viewGroup.getBottom();
            parent = viewGroup.getParent();
        }
        return rect2;
    }

    private int getClientWidth() {
        return (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
    }

    private ItemInfo infoForCurrentScrollPosition() {
        int clientWidth = getClientWidth();
        float f = 0.0f;
        float scrollX = clientWidth > 0 ? getScrollX() / clientWidth : 0.0f;
        float f2 = clientWidth > 0 ? this.mPageMargin / clientWidth : 0.0f;
        ItemInfo itemInfo = null;
        int i = 0;
        int i2 = -1;
        boolean z = true;
        float f3 = 0.0f;
        while (i < this.mItems.size()) {
            ItemInfo itemInfo2 = this.mItems.get(i);
            int i3 = i;
            ItemInfo itemInfo3 = itemInfo2;
            if (!z) {
                int i4 = i2 + 1;
                i3 = i;
                itemInfo3 = itemInfo2;
                if (itemInfo2.position != i4) {
                    itemInfo3 = this.mTempItem;
                    itemInfo3.offset = f + f3 + f2;
                    itemInfo3.position = i4;
                    itemInfo3.widthFactor = this.mAdapter.getPageWidth(i4);
                    i3 = i - 1;
                }
            }
            f = itemInfo3.offset;
            float f4 = itemInfo3.widthFactor;
            if (!z && scrollX < f) {
                return itemInfo;
            }
            if (scrollX < f4 + f + f2 || i3 == this.mItems.size() - 1) {
                return itemInfo3;
            }
            i2 = itemInfo3.position;
            f3 = itemInfo3.widthFactor;
            i = i3 + 1;
            z = false;
            itemInfo = itemInfo3;
        }
        return itemInfo;
    }

    private static boolean isDecorView(View view) {
        return view.getClass().getAnnotation(DecorView.class) != null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0036, code lost:
        if (r6 < com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean isGutterDrag(float f, float f2) {
        boolean z;
        if (this.mDragInGutterEnabled) {
            return false;
        }
        if (f >= this.mGutterSize || f2 <= ActionBarShadowController.ELEVATION_LOW) {
            z = false;
            if (f > getWidth() - this.mGutterSize) {
                z = false;
            }
            return z;
        }
        z = true;
        return z;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private boolean pageScrolled(int i) {
        int i2;
        if (this.mItems.size() == 0) {
            if (this.mFirstLayout) {
                return false;
            }
            this.mCalledSuper = false;
            onPageScrolled(0, ActionBarShadowController.ELEVATION_LOW, 0);
            if (this.mCalledSuper) {
                return false;
            }
            throw new IllegalStateException("onPageScrolled did not call superclass implementation");
        }
        ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
        int clientWidth = getClientWidth();
        float f = this.mPageMargin;
        float f2 = clientWidth;
        float f3 = f / f2;
        int i3 = infoForCurrentScrollPosition.position;
        float f4 = ((i / f2) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + f3);
        this.mCalledSuper = false;
        onPageScrolled(i3, f4, (int) ((clientWidth + i2) * f4));
        if (this.mCalledSuper) {
            return true;
        }
        throw new IllegalStateException("onPageScrolled did not call superclass implementation");
    }

    private boolean performDrag(float f, float f2) {
        boolean z;
        boolean z2;
        float f3 = this.mLastMotionX - f;
        this.mLastMotionX = f;
        float releaseHorizontalGlow = releaseHorizontalGlow(f3, f2);
        float f4 = f3 - releaseHorizontalGlow;
        boolean z3 = false;
        boolean z4 = releaseHorizontalGlow != ActionBarShadowController.ELEVATION_LOW;
        if (Math.abs(f4) < 1.0E-4f) {
            return z4;
        }
        float scrollX = getScrollX() + f4;
        float clientWidth = getClientWidth();
        float f5 = this.mFirstOffset * clientWidth;
        float f6 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        if (itemInfo.position != 0) {
            f5 = itemInfo.offset * clientWidth;
            z = false;
        } else {
            z = true;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f6 = itemInfo2.offset * clientWidth;
        } else {
            z3 = true;
        }
        if (scrollX < f5) {
            if (z) {
                EdgeEffectCompat.onPullDistance(this.mLeftEdge, (f5 - scrollX) / clientWidth, 1.0f - (f2 / getHeight()));
                z4 = true;
            }
            z2 = z4;
        } else {
            f5 = scrollX;
            z2 = z4;
            if (scrollX > f6) {
                if (z3) {
                    EdgeEffectCompat.onPullDistance(this.mRightEdge, (scrollX - f6) / clientWidth, f2 / getHeight());
                    z4 = true;
                }
                f5 = f6;
                z2 = z4;
            }
        }
        int i = (int) f5;
        this.mLastMotionX += f5 - i;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        return z2;
    }

    private void recomputeScrollPosition(int i, int i2, int i3, int i4) {
        if (i2 <= 0 || this.mItems.isEmpty()) {
            ItemInfo infoForPosition = infoForPosition(this.mCurItem);
            int min = (int) ((infoForPosition != null ? Math.min(infoForPosition.offset, this.mLastOffset) : 0.0f) * ((i - getPaddingLeft()) - getPaddingRight()));
            if (min != getScrollX()) {
                completeScroll(false);
                scrollTo(min, getScrollY());
            }
        } else if (!this.mScroller.isFinished()) {
            this.mScroller.setFinalX(getCurrentItem() * getClientWidth());
        } else {
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            scrollTo((int) ((getScrollX() / (((i2 - getPaddingLeft()) - getPaddingRight()) + i4)) * (((i - paddingLeft) - paddingRight) + i3)), getScrollY());
        }
    }

    private float releaseHorizontalGlow(float f, float f2) {
        float height = f2 / getHeight();
        float width = f / getWidth();
        float f3 = 0.0f;
        if (EdgeEffectCompat.getDistance(this.mLeftEdge) != ActionBarShadowController.ELEVATION_LOW) {
            f3 = -EdgeEffectCompat.onPullDistance(this.mLeftEdge, -width, 1.0f - height);
        } else if (EdgeEffectCompat.getDistance(this.mRightEdge) != ActionBarShadowController.ELEVATION_LOW) {
            f3 = EdgeEffectCompat.onPullDistance(this.mRightEdge, width, height);
        }
        return f3 * getWidth();
    }

    private void removeNonDecorViews() {
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= getChildCount()) {
                return;
            }
            int i3 = i2;
            if (!((LayoutParams) getChildAt(i2).getLayoutParams()).isDecor) {
                removeViewAt(i2);
                i3 = i2 - 1;
            }
            i = i3 + 1;
        }
    }

    private void requestParentDisallowInterceptTouchEvent(boolean z) {
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(z);
        }
    }

    private boolean resetTouch() {
        this.mActivePointerId = -1;
        endDrag();
        this.mLeftEdge.onRelease();
        this.mRightEdge.onRelease();
        return (this.mLeftEdge.isFinished() && this.mRightEdge.isFinished()) ? false : true;
    }

    private void scrollToItem(int i, boolean z, int i2, boolean z2) {
        ItemInfo infoForPosition = infoForPosition(i);
        int clientWidth = infoForPosition != null ? (int) (getClientWidth() * Math.max(this.mFirstOffset, Math.min(infoForPosition.offset, this.mLastOffset))) : 0;
        if (z) {
            smoothScrollTo(clientWidth, 0, i2);
            if (z2) {
                dispatchOnPageSelected(i);
                return;
            }
            return;
        }
        if (z2) {
            dispatchOnPageSelected(i);
        }
        completeScroll(false);
        scrollTo(clientWidth, 0);
        pageScrolled(clientWidth);
    }

    private void setScrollingCacheEnabled(boolean z) {
        if (this.mScrollingCacheEnabled != z) {
            this.mScrollingCacheEnabled = z;
        }
    }

    private void sortChildDrawingOrder() {
        if (this.mDrawingOrder != 0) {
            ArrayList<View> arrayList = this.mDrawingOrderedChildren;
            if (arrayList == null) {
                this.mDrawingOrderedChildren = new ArrayList<>();
            } else {
                arrayList.clear();
            }
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                this.mDrawingOrderedChildren.add(getChildAt(i));
            }
            Collections.sort(this.mDrawingOrderedChildren, sPositionComparator);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        ItemInfo infoForChild;
        int size = arrayList.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i3 = 0; i3 < getChildCount(); i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                    childAt.addFocusables(arrayList, i, i2);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == arrayList.size()) && isFocusable()) {
            if ((i2 & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            arrayList.add(this);
        }
    }

    public ItemInfo addNewItem(int i, int i2) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = i;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, i);
        itemInfo.widthFactor = this.mAdapter.getPageWidth(i);
        if (i2 < 0 || i2 >= this.mItems.size()) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(i2, itemInfo);
        }
        return itemInfo;
    }

    public void addOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        if (this.mAdapterChangeListeners == null) {
            this.mAdapterChangeListeners = new ArrayList();
        }
        this.mAdapterChangeListeners.add(onAdapterChangeListener);
    }

    public void addOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        if (this.mOnPageChangeListeners == null) {
            this.mOnPageChangeListeners = new ArrayList();
        }
        this.mOnPageChangeListeners.add(onPageChangeListener);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> arrayList) {
        ItemInfo infoForChild;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem) {
                childAt.addTouchables(arrayList);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        ViewGroup.LayoutParams layoutParams2 = layoutParams;
        if (!checkLayoutParams(layoutParams)) {
            layoutParams2 = generateLayoutParams(layoutParams);
        }
        LayoutParams layoutParams3 = (LayoutParams) layoutParams2;
        boolean isDecorView = layoutParams3.isDecor | isDecorView(view);
        layoutParams3.isDecor = isDecorView;
        if (!this.mInLayout) {
            super.addView(view, i, layoutParams2);
        } else if (isDecorView) {
            throw new IllegalStateException("Cannot add pager decor view during layout");
        } else {
            layoutParams3.needsMeasure = true;
            addViewInLayout(view, i, layoutParams2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x0179  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean arrowScroll(int i) {
        View view;
        boolean z;
        View findNextFocus;
        View findFocus = findFocus();
        boolean z2 = false;
        if (findFocus != this) {
            view = findFocus;
            if (findFocus != null) {
                ViewParent parent = findFocus.getParent();
                while (true) {
                    ViewParent viewParent = parent;
                    if (!(viewParent instanceof ViewGroup)) {
                        z = false;
                        break;
                    } else if (viewParent == this) {
                        z = true;
                        break;
                    } else {
                        parent = viewParent.getParent();
                    }
                }
                view = findFocus;
                if (!z) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(findFocus.getClass().getSimpleName());
                    ViewParent parent2 = findFocus.getParent();
                    while (true) {
                        ViewParent viewParent2 = parent2;
                        if (!(viewParent2 instanceof ViewGroup)) {
                            break;
                        }
                        sb.append(" => ");
                        sb.append(viewParent2.getClass().getSimpleName());
                        parent2 = viewParent2.getParent();
                    }
                    Log.e(TAG, "arrowScroll tried to find focus based on non-child current focused view " + sb.toString());
                }
            }
            findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i);
            if (findNextFocus != null || findNextFocus == view) {
                if (i != 17 || i == 1) {
                    z2 = pageLeft();
                } else if (i == 66 || i == 2) {
                    z2 = pageRight();
                }
            } else if (i == 17) {
                z2 = (view == null || getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left < getChildRectInPagerCoordinates(this.mTempRect, view).left) ? findNextFocus.requestFocus() : pageLeft();
            } else if (i == 66) {
                z2 = (view == null || getChildRectInPagerCoordinates(this.mTempRect, findNextFocus).left > getChildRectInPagerCoordinates(this.mTempRect, view).left) ? findNextFocus.requestFocus() : pageRight();
            }
            if (z2) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            }
            return z2;
        }
        view = null;
        findNextFocus = FocusFinder.getInstance().findNextFocus(this, view, i);
        if (findNextFocus != null) {
        }
        if (i != 17) {
        }
        z2 = pageLeft();
        if (z2) {
        }
        return z2;
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = ActionBarShadowController.ELEVATION_LOW;
        this.mInitialMotionX = ActionBarShadowController.ELEVATION_LOW;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
        this.mFakeDragBeginTime = uptimeMillis;
        return true;
    }

    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        int i4;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i5 = i2 + scrollX;
                if (i5 >= childAt.getLeft() && i5 < childAt.getRight() && (i4 = i3 + scrollY) >= childAt.getTop() && i4 < childAt.getBottom() && canScroll(childAt, true, i, i5 - childAt.getLeft(), i4 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z && view.canScrollHorizontally(-i);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        boolean z = false;
        if (this.mAdapter == null) {
            return false;
        }
        int clientWidth = getClientWidth();
        int scrollX = getScrollX();
        if (i < 0) {
            if (scrollX > ((int) (clientWidth * this.mFirstOffset))) {
                z = true;
            }
            return z;
        }
        boolean z2 = false;
        if (i > 0) {
            z2 = false;
            if (scrollX < ((int) (clientWidth * this.mLastOffset))) {
                z2 = true;
            }
        }
        return z2;
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && super.checkLayoutParams(layoutParams);
    }

    public void clearOnPageChangeListeners() {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.View
    public void computeScroll() {
        this.mIsScrollStarted = true;
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll(true);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
            if (!pageScrolled(currX)) {
                this.mScroller.abortAnimation();
                scrollTo(0, currY);
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }

    public void dataSetChanged() {
        boolean z;
        int i;
        int i2;
        boolean z2;
        int count = this.mAdapter.getCount();
        this.mExpectedAdapterCount = count;
        boolean z3 = this.mItems.size() < (this.mOffscreenPageLimit * 2) + 1 && this.mItems.size() < count;
        int i3 = this.mCurItem;
        int i4 = 0;
        boolean z4 = false;
        while (true) {
            z = z4;
            if (i4 >= this.mItems.size()) {
                break;
            }
            ItemInfo itemInfo = this.mItems.get(i4);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition == -1) {
                i = i3;
                i2 = i4;
                z2 = z;
            } else {
                if (itemPosition == -2) {
                    this.mItems.remove(i4);
                    int i5 = i4 - 1;
                    boolean z5 = z;
                    if (!z) {
                        this.mAdapter.startUpdate((ViewGroup) this);
                        z5 = true;
                    }
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    int i6 = this.mCurItem;
                    i4 = i5;
                    z = z5;
                    if (i6 == itemInfo.position) {
                        i3 = Math.max(0, Math.min(i6, count - 1));
                        z = z5;
                        i4 = i5;
                    }
                } else {
                    int i7 = itemInfo.position;
                    i = i3;
                    i2 = i4;
                    z2 = z;
                    if (i7 != itemPosition) {
                        if (i7 == this.mCurItem) {
                            i3 = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z3 = true;
                i = i3;
                i2 = i4;
                z2 = z;
            }
            i4 = i2 + 1;
            i3 = i;
            z4 = z2;
        }
        if (z) {
            this.mAdapter.finishUpdate((ViewGroup) this);
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (z3) {
            int childCount = getChildCount();
            for (int i8 = 0; i8 < childCount; i8++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i8).getLayoutParams();
                if (!layoutParams.isDecor) {
                    layoutParams.widthFactor = ActionBarShadowController.ELEVATION_LOW;
                }
            }
            setCurrentItemInternal(i3, false, true);
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        ItemInfo infoForChild;
        if (accessibilityEvent.getEventType() == 4096) {
            return super.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(accessibilityEvent)) {
                return true;
            }
        }
        return false;
    }

    public float distanceInfluenceForSnapDuration(float f) {
        return (float) Math.sin((f - 0.5f) * 0.47123894f);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = getOverScrollMode();
        boolean z = false;
        boolean z2 = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.mAdapter) != null && pagerAdapter.getCount() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int save = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                int width = getWidth();
                canvas.rotate(270.0f);
                canvas.translate((-height) + getPaddingTop(), this.mFirstOffset * width);
                this.mLeftEdge.setSize(height, width);
                z2 = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(save);
            }
            z = z2;
            if (!this.mRightEdge.isFinished()) {
                int save2 = canvas.save();
                int width2 = getWidth();
                int height2 = getHeight();
                int paddingTop = getPaddingTop();
                int paddingBottom = getPaddingBottom();
                canvas.rotate(90.0f);
                canvas.translate(-getPaddingTop(), (-(this.mLastOffset + 1.0f)) * width2);
                this.mRightEdge.setSize((height2 - paddingTop) - paddingBottom, width2);
                z = z2 | this.mRightEdge.draw(canvas);
                canvas.restoreToCount(save2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter != null) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            setCurrentItemInternal(determineTargetPage(infoForCurrentScrollPosition.position, ((scrollX / clientWidth) - infoForCurrentScrollPosition.offset) / infoForCurrentScrollPosition.widthFactor, xVelocity, (int) (this.mLastMotionX - this.mInitialMotionX)), true, true, xVelocity);
        }
        endDrag();
        this.mFakeDragging = false;
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean z;
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 21) {
                z = keyEvent.hasModifiers(2) ? pageLeft() : arrowScroll(17);
            } else if (keyCode == 22) {
                z = keyEvent.hasModifiers(2) ? pageRight() : arrowScroll(66);
            } else if (keyCode == 61) {
                if (keyEvent.hasNoModifiers()) {
                    z = arrowScroll(2);
                } else if (keyEvent.hasModifiers(1)) {
                    z = arrowScroll(1);
                }
            }
            return z;
        }
        z = false;
        return z;
    }

    public void fakeDragBy(float f) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        if (this.mAdapter == null) {
            return;
        }
        this.mLastMotionX += f;
        float scrollX = getScrollX() - f;
        float clientWidth = getClientWidth();
        float f2 = this.mFirstOffset * clientWidth;
        float f3 = this.mLastOffset * clientWidth;
        ItemInfo itemInfo = this.mItems.get(0);
        ArrayList<ItemInfo> arrayList = this.mItems;
        ItemInfo itemInfo2 = arrayList.get(arrayList.size() - 1);
        if (itemInfo.position != 0) {
            f2 = itemInfo.offset * clientWidth;
        }
        if (itemInfo2.position != this.mAdapter.getCount() - 1) {
            f3 = itemInfo2.offset * clientWidth;
        }
        if (scrollX >= f2) {
            f2 = scrollX;
            if (scrollX > f3) {
                f2 = f3;
            }
        }
        int i = (int) f2;
        this.mLastMotionX += f2 - i;
        scrollTo(i, getScrollY());
        pageScrolled(i);
        MotionEvent obtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, ActionBarShadowController.ELEVATION_LOW, 0);
        this.mVelocityTracker.addMovement(obtain);
        obtain.recycle();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return generateDefaultLayoutParams();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        int i3 = i2;
        if (this.mDrawingOrder == 2) {
            i3 = (i - 1) - i2;
        }
        return ((LayoutParams) this.mDrawingOrderedChildren.get(i3).getLayoutParams()).childIndex;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public ItemInfo infoForAnyChild(View view) {
        while (true) {
            ViewParent parent = view.getParent();
            if (parent == this) {
                return infoForChild(view);
            }
            if (!(parent instanceof View)) {
                return null;
            }
            view = (View) parent;
        }
    }

    public ItemInfo infoForChild(View view) {
        for (int i = 0; i < this.mItems.size(); i++) {
            ItemInfo itemInfo = this.mItems.get(i);
            if (this.mAdapter.isViewFromObject(view, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    public ItemInfo infoForPosition(int i) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.position == i) {
                return itemInfo;
            }
        }
        return null;
    }

    public void initViewPager(Context context, AttributeSet attributeSet) {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        float f = context.getResources().getDisplayMetrics().density;
        this.mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
        this.mMinimumVelocity = (int) (400.0f * f);
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffect(context);
        this.mRightEdge = new EdgeEffect(context);
        this.mFlingDistance = (int) (25.0f * f);
        this.mCloseEnough = (int) (2.0f * f);
        this.mDefaultGutterSize = (int) (f * 16.0f);
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setOnApplyWindowInsetsListener(this, new OnApplyWindowInsetsListener() { // from class: androidx.viewpager.widget.ViewPager.4
            public final Rect mTempRect = new Rect();

            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                WindowInsetsCompat onApplyWindowInsets = ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                if (onApplyWindowInsets.isConsumed()) {
                    return onApplyWindowInsets;
                }
                Rect rect = this.mTempRect;
                rect.left = onApplyWindowInsets.getSystemWindowInsetLeft();
                rect.top = onApplyWindowInsets.getSystemWindowInsetTop();
                rect.right = onApplyWindowInsets.getSystemWindowInsetRight();
                rect.bottom = onApplyWindowInsets.getSystemWindowInsetBottom();
                int childCount = ViewPager.this.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    WindowInsetsCompat dispatchApplyWindowInsets = ViewCompat.dispatchApplyWindowInsets(ViewPager.this.getChildAt(i), onApplyWindowInsets);
                    rect.left = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetLeft(), rect.left);
                    rect.top = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetTop(), rect.top);
                    rect.right = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetRight(), rect.right);
                    rect.bottom = Math.min(dispatchApplyWindowInsets.getSystemWindowInsetBottom(), rect.bottom);
                }
                return new WindowInsetsCompat.Builder(onApplyWindowInsets).setSystemWindowInsets(Insets.of(rect)).build();
            }
        });
    }

    public boolean isDragInGutterEnabled() {
        return this.mDragInGutterEnabled;
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        removeCallbacks(this.mEndScrollRunnable);
        Scroller scroller = this.mScroller;
        if (scroller != null && !scroller.isFinished()) {
            this.mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        int width;
        int i;
        float f;
        super.onDraw(canvas);
        if (this.mPageMargin <= 0 || this.mMarginDrawable == null || this.mItems.size() <= 0 || this.mAdapter == null) {
            return;
        }
        int scrollX = getScrollX();
        float width2 = getWidth();
        float f2 = this.mPageMargin / width2;
        int i2 = 0;
        ItemInfo itemInfo = this.mItems.get(0);
        float f3 = itemInfo.offset;
        int size = this.mItems.size();
        int i3 = this.mItems.get(size - 1).position;
        for (int i4 = itemInfo.position; i4 < i3; i4++) {
            while (true) {
                i = itemInfo.position;
                if (i4 <= i || i2 >= size) {
                    break;
                }
                i2++;
                itemInfo = this.mItems.get(i2);
            }
            if (i4 == i) {
                float f4 = itemInfo.offset;
                float f5 = itemInfo.widthFactor;
                f = (f4 + f5) * width2;
                f3 = f4 + f5 + f2;
            } else {
                float pageWidth = this.mAdapter.getPageWidth(i4);
                f = (f3 + pageWidth) * width2;
                f3 += pageWidth + f2;
            }
            if (this.mPageMargin + f > scrollX) {
                this.mMarginDrawable.setBounds(Math.round(f), this.mTopPageBounds, Math.round(this.mPageMargin + f), this.mBottomPageBounds);
                this.mMarginDrawable.draw(canvas);
            }
            if (f > scrollX + width) {
                return;
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 3 || action == 1) {
            resetTouch();
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        if (action == 0) {
            float x = motionEvent.getX();
            this.mInitialMotionX = x;
            this.mLastMotionX = x;
            float y = motionEvent.getY();
            this.mInitialMotionY = y;
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
            this.mIsUnableToDrag = false;
            this.mIsScrollStarted = true;
            this.mScroller.computeScrollOffset();
            if (this.mScrollState == 2 && Math.abs(this.mScroller.getFinalX() - this.mScroller.getCurrX()) > this.mCloseEnough) {
                this.mScroller.abortAnimation();
                this.mPopulatePending = false;
                populate();
                this.mIsBeingDragged = true;
                requestParentDisallowInterceptTouchEvent(true);
                setScrollState(1);
            } else if (EdgeEffectCompat.getDistance(this.mLeftEdge) == ActionBarShadowController.ELEVATION_LOW && EdgeEffectCompat.getDistance(this.mRightEdge) == ActionBarShadowController.ELEVATION_LOW) {
                completeScroll(false);
                this.mIsBeingDragged = false;
            } else {
                this.mIsBeingDragged = true;
                setScrollState(1);
                if (EdgeEffectCompat.getDistance(this.mLeftEdge) != ActionBarShadowController.ELEVATION_LOW) {
                    EdgeEffectCompat.onPullDistance(this.mLeftEdge, ActionBarShadowController.ELEVATION_LOW, 1.0f - (this.mLastMotionY / getHeight()));
                }
                if (EdgeEffectCompat.getDistance(this.mRightEdge) != ActionBarShadowController.ELEVATION_LOW) {
                    EdgeEffectCompat.onPullDistance(this.mRightEdge, ActionBarShadowController.ELEVATION_LOW, this.mLastMotionY / getHeight());
                }
            }
        } else if (action == 2) {
            int i = this.mActivePointerId;
            if (i != -1) {
                int findPointerIndex = motionEvent.findPointerIndex(i);
                float x2 = motionEvent.getX(findPointerIndex);
                float f = x2 - this.mLastMotionX;
                float abs = Math.abs(f);
                float y2 = motionEvent.getY(findPointerIndex);
                float abs2 = Math.abs(y2 - this.mInitialMotionY);
                int i2 = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
                if (i2 != 0 && !isGutterDrag(this.mLastMotionX, f) && canScroll(this, false, (int) f, (int) x2, (int) y2)) {
                    this.mLastMotionX = x2;
                    this.mLastMotionY = y2;
                    this.mIsUnableToDrag = true;
                    return false;
                }
                int i3 = this.mTouchSlop;
                if (abs > i3 && abs * 0.5f > abs2) {
                    this.mIsBeingDragged = true;
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(1);
                    this.mLastMotionX = i2 > 0 ? this.mInitialMotionX + this.mTouchSlop : this.mInitialMotionX - this.mTouchSlop;
                    this.mLastMotionY = y2;
                    setScrollingCacheEnabled(true);
                } else if (abs2 > i3) {
                    this.mIsUnableToDrag = true;
                }
                if (this.mIsBeingDragged && performDrag(x2, y2)) {
                    ViewCompat.postInvalidateOnAnimation(this);
                }
            }
        } else if (action == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        return this.mIsBeingDragged;
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x00fd  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0143  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        ItemInfo infoForChild;
        int max;
        int i6;
        int max2;
        int childCount = getChildCount();
        int i7 = i3 - i;
        int i8 = i4 - i2;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int scrollX = getScrollX();
        int i9 = 0;
        int i10 = 0;
        while (true) {
            i5 = i10;
            if (i9 >= childCount) {
                break;
            }
            View childAt = getChildAt(i9);
            int i11 = paddingLeft;
            int i12 = paddingTop;
            int i13 = paddingRight;
            int i14 = paddingBottom;
            int i15 = i5;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                i11 = paddingLeft;
                i12 = paddingTop;
                i13 = paddingRight;
                i14 = paddingBottom;
                i15 = i5;
                if (layoutParams.isDecor) {
                    int i16 = layoutParams.gravity;
                    int i17 = i16 & 7;
                    int i18 = i16 & 112;
                    if (i17 != 1) {
                        if (i17 == 3) {
                            i6 = paddingLeft;
                            paddingLeft = childAt.getMeasuredWidth() + paddingLeft;
                        } else if (i17 != 5) {
                            i6 = paddingLeft;
                            paddingLeft = paddingLeft;
                        } else {
                            max = (i7 - paddingRight) - childAt.getMeasuredWidth();
                            paddingRight += childAt.getMeasuredWidth();
                        }
                        if (i18 != 16) {
                            max2 = Math.max((i8 - childAt.getMeasuredHeight()) / 2, paddingTop);
                        } else if (i18 == 48) {
                            max2 = paddingTop;
                            paddingTop = childAt.getMeasuredHeight() + paddingTop;
                        } else if (i18 != 80) {
                            max2 = paddingTop;
                            paddingTop = paddingTop;
                        } else {
                            max2 = (i8 - paddingBottom) - childAt.getMeasuredHeight();
                            paddingBottom += childAt.getMeasuredHeight();
                        }
                        int i19 = i6 + scrollX;
                        childAt.layout(i19, max2, childAt.getMeasuredWidth() + i19, max2 + childAt.getMeasuredHeight());
                        i15 = i5 + 1;
                        i14 = paddingBottom;
                        i13 = paddingRight;
                        i12 = paddingTop;
                        i11 = paddingLeft;
                    } else {
                        max = Math.max((i7 - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    }
                    i6 = max;
                    if (i18 != 16) {
                    }
                    int i192 = i6 + scrollX;
                    childAt.layout(i192, max2, childAt.getMeasuredWidth() + i192, max2 + childAt.getMeasuredHeight());
                    i15 = i5 + 1;
                    i14 = paddingBottom;
                    i13 = paddingRight;
                    i12 = paddingTop;
                    i11 = paddingLeft;
                }
            }
            i9++;
            paddingLeft = i11;
            paddingTop = i12;
            paddingRight = i13;
            paddingBottom = i14;
            i10 = i15;
        }
        for (int i20 = 0; i20 < childCount; i20++) {
            View childAt2 = getChildAt(i20);
            if (childAt2.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt2.getLayoutParams();
                if (!layoutParams2.isDecor && (infoForChild = infoForChild(childAt2)) != null) {
                    float f = (i7 - paddingLeft) - paddingRight;
                    int i21 = ((int) (infoForChild.offset * f)) + paddingLeft;
                    if (layoutParams2.needsMeasure) {
                        layoutParams2.needsMeasure = false;
                        childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (f * layoutParams2.widthFactor), 1073741824), View.MeasureSpec.makeMeasureSpec((i8 - paddingTop) - paddingBottom, 1073741824));
                    }
                    childAt2.layout(i21, paddingTop, childAt2.getMeasuredWidth() + i21, childAt2.getMeasuredHeight() + paddingTop);
                }
            }
        }
        this.mTopPageBounds = paddingTop;
        this.mBottomPageBounds = i8 - paddingBottom;
        this.mDecorChildCount = i5;
        if (this.mFirstLayout) {
            scrollToItem(this.mCurItem, false, 0, false);
        }
        this.mFirstLayout = false;
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        LayoutParams layoutParams;
        int i3;
        int i4;
        int i5;
        int i6;
        setMeasuredDimension(ViewGroup.getDefaultSize(0, i), ViewGroup.getDefaultSize(0, i2));
        int measuredWidth = getMeasuredWidth();
        this.mGutterSize = Math.min(measuredWidth / 10, this.mDefaultGutterSize);
        int paddingLeft = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int childCount = getChildCount();
        int i7 = 0;
        while (true) {
            int i8 = 1073741824;
            if (i7 >= childCount) {
                break;
            }
            View childAt = getChildAt(i7);
            int i9 = paddingLeft;
            int i10 = measuredHeight;
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams2 = (LayoutParams) childAt.getLayoutParams();
                i9 = paddingLeft;
                i10 = measuredHeight;
                if (layoutParams2 != null) {
                    i9 = paddingLeft;
                    i10 = measuredHeight;
                    if (layoutParams2.isDecor) {
                        int i11 = layoutParams2.gravity;
                        int i12 = i11 & 7;
                        int i13 = i11 & 112;
                        boolean z = i13 == 48 || i13 == 80;
                        boolean z2 = i12 != 3 ? i12 == 5 : true;
                        int i14 = Integer.MIN_VALUE;
                        if (z) {
                            i3 = Integer.MIN_VALUE;
                            i14 = 1073741824;
                        } else {
                            i3 = z2 ? 1073741824 : Integer.MIN_VALUE;
                        }
                        int i15 = ((ViewGroup.LayoutParams) layoutParams2).width;
                        if (i15 != -2) {
                            i5 = 1073741824;
                            i4 = i15 != -1 ? i15 : paddingLeft;
                        } else {
                            i4 = paddingLeft;
                            i5 = i14;
                        }
                        int i16 = ((ViewGroup.LayoutParams) layoutParams2).height;
                        if (i16 != -2) {
                            i6 = i16 != -1 ? i16 : measuredHeight;
                        } else {
                            i8 = i3;
                            i6 = measuredHeight;
                        }
                        childAt.measure(View.MeasureSpec.makeMeasureSpec(i4, i5), View.MeasureSpec.makeMeasureSpec(i6, i8));
                        if (z) {
                            i10 = measuredHeight - childAt.getMeasuredHeight();
                            i9 = paddingLeft;
                        } else {
                            i9 = paddingLeft;
                            i10 = measuredHeight;
                            if (z2) {
                                i9 = paddingLeft - childAt.getMeasuredWidth();
                                i10 = measuredHeight;
                            }
                        }
                    }
                }
            }
            i7++;
            paddingLeft = i9;
            measuredHeight = i10;
        }
        View.MeasureSpec.makeMeasureSpec(paddingLeft, 1073741824);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount2 = getChildCount();
        for (int i17 = 0; i17 < childCount2; i17++) {
            View childAt2 = getChildAt(i17);
            if (childAt2.getVisibility() != 8 && ((layoutParams = (LayoutParams) childAt2.getLayoutParams()) == null || !layoutParams.isDecor)) {
                childAt2.measure(View.MeasureSpec.makeMeasureSpec((int) (paddingLeft * layoutParams.widthFactor), 1073741824), makeMeasureSpec);
            }
        }
    }

    public void onPageScrolled(int i, float f, int i2) {
        int max;
        if (this.mDecorChildCount > 0) {
            int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            int width = getWidth();
            int childCount = getChildCount();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.isDecor) {
                    int i4 = layoutParams.gravity & 7;
                    if (i4 == 1) {
                        max = Math.max((width - childAt.getMeasuredWidth()) / 2, paddingLeft);
                    } else if (i4 == 3) {
                        max = paddingLeft;
                        paddingLeft = childAt.getWidth() + paddingLeft;
                    } else if (i4 != 5) {
                        max = paddingLeft;
                        paddingLeft = paddingLeft;
                    } else {
                        max = (width - paddingRight) - childAt.getMeasuredWidth();
                        paddingRight += childAt.getMeasuredWidth();
                    }
                    int left = (max + scrollX) - childAt.getLeft();
                    if (left != 0) {
                        childAt.offsetLeftAndRight(left);
                    }
                }
            }
        }
        dispatchOnPageScrolled(i, f, i2);
        if (this.mPageTransformer != null) {
            int scrollX2 = getScrollX();
            int childCount2 = getChildCount();
            for (int i5 = 0; i5 < childCount2; i5++) {
                View childAt2 = getChildAt(i5);
                if (!((LayoutParams) childAt2.getLayoutParams()).isDecor) {
                    this.mPageTransformer.transformPage(childAt2, (childAt2.getLeft() - scrollX2) / getClientWidth());
                }
            }
        }
        this.mCalledSuper = true;
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        int i2;
        int i3;
        ItemInfo infoForChild;
        int childCount = getChildCount();
        int i4 = -1;
        if ((i & 2) != 0) {
            i4 = childCount;
            i2 = 0;
            i3 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
        }
        while (i2 != i4) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (infoForChild = infoForChild(childAt)) != null && infoForChild.position == this.mCurItem && childAt.requestFocus(i, rect)) {
                return true;
            }
            i2 += i3;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
            return;
        }
        this.mRestoredCurItem = savedState.position;
        this.mRestoredAdapterState = savedState.adapterState;
        this.mRestoredClassLoader = savedState.loader;
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            savedState.adapterState = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3) {
            int i5 = this.mPageMargin;
            recomputeScrollPosition(i, i3, i5, i5);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        PagerAdapter pagerAdapter;
        if (this.mFakeDragging) {
            return true;
        }
        boolean z = false;
        if ((motionEvent.getAction() == 0 && motionEvent.getEdgeFlags() != 0) || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getCount() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mScroller.abortAnimation();
            this.mPopulatePending = false;
            populate();
            float x = motionEvent.getX();
            this.mInitialMotionX = x;
            this.mLastMotionX = x;
            float y = motionEvent.getY();
            this.mInitialMotionY = y;
            this.mLastMotionY = y;
            this.mActivePointerId = motionEvent.getPointerId(0);
        } else if (action != 1) {
            if (action == 2) {
                if (!this.mIsBeingDragged) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        z = resetTouch();
                    } else {
                        float x2 = motionEvent.getX(findPointerIndex);
                        float abs = Math.abs(x2 - this.mLastMotionX);
                        float y2 = motionEvent.getY(findPointerIndex);
                        float abs2 = Math.abs(y2 - this.mLastMotionY);
                        if (abs > this.mTouchSlop && abs > abs2) {
                            this.mIsBeingDragged = true;
                            requestParentDisallowInterceptTouchEvent(true);
                            float f = this.mInitialMotionX;
                            this.mLastMotionX = x2 - f > ActionBarShadowController.ELEVATION_LOW ? f + this.mTouchSlop : f - this.mTouchSlop;
                            this.mLastMotionY = y2;
                            setScrollState(1);
                            setScrollingCacheEnabled(true);
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                    }
                }
                if (this.mIsBeingDragged) {
                    int findPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                    z = false | performDrag(motionEvent.getX(findPointerIndex2), motionEvent.getY(findPointerIndex2));
                }
            } else if (action != 3) {
                if (action == 5) {
                    int actionIndex = motionEvent.getActionIndex();
                    this.mLastMotionX = motionEvent.getX(actionIndex);
                    this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                } else if (action == 6) {
                    onSecondaryPointerUp(motionEvent);
                    this.mLastMotionX = motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                }
            } else if (this.mIsBeingDragged) {
                scrollToItem(this.mCurItem, true, 0, false);
                z = resetTouch();
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
            this.mPopulatePending = true;
            int clientWidth = getClientWidth();
            int scrollX = getScrollX();
            ItemInfo infoForCurrentScrollPosition = infoForCurrentScrollPosition();
            float f2 = clientWidth;
            float f3 = this.mPageMargin / f2;
            int i = infoForCurrentScrollPosition.position;
            int determineTargetPage = determineTargetPage(i, ((scrollX / f2) - infoForCurrentScrollPosition.offset) / (infoForCurrentScrollPosition.widthFactor + f3), xVelocity, (int) (motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialMotionX));
            setCurrentItemInternal(determineTargetPage, true, true, xVelocity);
            boolean resetTouch = resetTouch();
            z = resetTouch;
            if (determineTargetPage == i) {
                z = resetTouch;
                if (resetTouch) {
                    if (EdgeEffectCompat.getDistance(this.mRightEdge) != ActionBarShadowController.ELEVATION_LOW) {
                        this.mRightEdge.onAbsorb(-xVelocity);
                        z = resetTouch;
                    } else {
                        z = resetTouch;
                        if (EdgeEffectCompat.getDistance(this.mLeftEdge) != ActionBarShadowController.ELEVATION_LOW) {
                            this.mLeftEdge.onAbsorb(xVelocity);
                            z = resetTouch;
                        }
                    }
                }
            }
        }
        if (z) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return true;
    }

    public boolean pageLeft() {
        int i = this.mCurItem;
        if (i > 0) {
            setCurrentItem(i - 1, true);
            return true;
        }
        return false;
    }

    public boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || this.mCurItem >= pagerAdapter.getCount() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    public void populate() {
        populate(this.mCurItem);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x00a3, code lost:
        if (r0 == r0) goto L28;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void populate(int i) {
        ItemInfo itemInfo;
        String hexString;
        ItemInfo itemInfo2;
        ItemInfo infoForChild;
        float f;
        int i2;
        ItemInfo itemInfo3;
        int i3;
        int i4;
        float f2;
        ItemInfo itemInfo4;
        int i5;
        int i6;
        int i7 = this.mCurItem;
        if (i7 != i) {
            itemInfo = infoForPosition(i7);
            this.mCurItem = i;
        } else {
            itemInfo = null;
        }
        if (this.mAdapter == null) {
            sortChildDrawingOrder();
        } else if (this.mPopulatePending) {
            sortChildDrawingOrder();
        } else if (getWindowToken() != null) {
            this.mAdapter.startUpdate((ViewGroup) this);
            int i8 = this.mOffscreenPageLimit;
            int max = Math.max(0, this.mCurItem - i8);
            int count = this.mAdapter.getCount();
            int min = Math.min(count - 1, this.mCurItem + i8);
            if (count != this.mExpectedAdapterCount) {
                try {
                    hexString = getResources().getResourceName(getId());
                } catch (Resources.NotFoundException e) {
                    hexString = Integer.toHexString(getId());
                }
                throw new IllegalStateException("The application's PagerAdapter changed the adapter's contents without calling PagerAdapter#notifyDataSetChanged! Expected adapter item count: " + this.mExpectedAdapterCount + ", found: " + count + " Pager id: " + hexString + " Pager class: " + getClass() + " Problematic adapter: " + this.mAdapter.getClass());
            }
            int i9 = 0;
            while (true) {
                if (i9 >= this.mItems.size()) {
                    break;
                }
                itemInfo2 = this.mItems.get(i9);
                int i10 = itemInfo2.position;
                int i11 = this.mCurItem;
                if (i10 < i11) {
                    i9++;
                }
            }
            itemInfo2 = null;
            ItemInfo itemInfo5 = itemInfo2;
            if (itemInfo2 == null) {
                itemInfo5 = itemInfo2;
                if (count > 0) {
                    itemInfo5 = addNewItem(this.mCurItem, i9);
                }
            }
            if (itemInfo5 != null) {
                int i12 = i9 - 1;
                ItemInfo itemInfo6 = i12 >= 0 ? this.mItems.get(i12) : null;
                int clientWidth = getClientWidth();
                float paddingLeft = clientWidth <= 0 ? 0.0f : (2.0f - itemInfo5.widthFactor) + (getPaddingLeft() / clientWidth);
                int i13 = this.mCurItem - 1;
                float f3 = 0.0f;
                ItemInfo itemInfo7 = itemInfo6;
                while (i13 >= 0) {
                    if (f3 < paddingLeft || i13 >= max) {
                        if (itemInfo7 == null || i13 != itemInfo7.position) {
                            float f4 = f3 + addNewItem(i13, i12 + 1).widthFactor;
                            i9++;
                            i3 = i9;
                            i4 = i12;
                            f2 = f4;
                            if (i12 >= 0) {
                                itemInfo4 = this.mItems.get(i12);
                                f2 = f4;
                                i6 = i12;
                                i5 = i9;
                                i13--;
                                i9 = i5;
                                i12 = i6;
                                itemInfo7 = itemInfo4;
                                f3 = f2;
                            }
                            itemInfo4 = null;
                            i12 = i4;
                            i9 = i3;
                            i6 = i12;
                            i5 = i9;
                            i13--;
                            i9 = i5;
                            i12 = i6;
                            itemInfo7 = itemInfo4;
                            f3 = f2;
                        } else {
                            float f5 = f3 + itemInfo7.widthFactor;
                            i12--;
                            i3 = i9;
                            i4 = i12;
                            f2 = f5;
                            if (i12 >= 0) {
                                itemInfo4 = this.mItems.get(i12);
                                f2 = f5;
                                i6 = i12;
                                i5 = i9;
                                i13--;
                                i9 = i5;
                                i12 = i6;
                                itemInfo7 = itemInfo4;
                                f3 = f2;
                            }
                            itemInfo4 = null;
                            i12 = i4;
                            i9 = i3;
                            i6 = i12;
                            i5 = i9;
                            i13--;
                            i9 = i5;
                            i12 = i6;
                            itemInfo7 = itemInfo4;
                            f3 = f2;
                        }
                    } else if (itemInfo7 == null) {
                        break;
                    } else {
                        i5 = i9;
                        i6 = i12;
                        itemInfo4 = itemInfo7;
                        f2 = f3;
                        if (i13 == itemInfo7.position) {
                            i5 = i9;
                            i6 = i12;
                            itemInfo4 = itemInfo7;
                            f2 = f3;
                            if (!itemInfo7.scrolling) {
                                this.mItems.remove(i12);
                                this.mAdapter.destroyItem((ViewGroup) this, i13, itemInfo7.object);
                                i12--;
                                i9--;
                                i3 = i9;
                                i4 = i12;
                                f2 = f3;
                                if (i12 >= 0) {
                                    itemInfo4 = this.mItems.get(i12);
                                    f2 = f3;
                                    i6 = i12;
                                    i5 = i9;
                                }
                                itemInfo4 = null;
                                i12 = i4;
                                i9 = i3;
                                i6 = i12;
                                i5 = i9;
                            }
                        }
                        i13--;
                        i9 = i5;
                        i12 = i6;
                        itemInfo7 = itemInfo4;
                        f3 = f2;
                    }
                }
                float f6 = itemInfo5.widthFactor;
                int i14 = i9 + 1;
                if (f6 < 2.0f) {
                    ItemInfo itemInfo8 = i14 < this.mItems.size() ? this.mItems.get(i14) : null;
                    float paddingRight = clientWidth <= 0 ? 0.0f : (getPaddingRight() / clientWidth) + 2.0f;
                    int i15 = this.mCurItem;
                    ItemInfo itemInfo9 = itemInfo8;
                    while (true) {
                        int i16 = i15 + 1;
                        if (i16 >= count) {
                            break;
                        }
                        if (f6 < paddingRight || i16 <= min) {
                            if (itemInfo9 == null || i16 != itemInfo9.position) {
                                ItemInfo addNewItem = addNewItem(i16, i14);
                                int i17 = i14 + 1;
                                float f7 = f6 + addNewItem.widthFactor;
                                f = f7;
                                i2 = i17;
                                if (i17 < this.mItems.size()) {
                                    itemInfo3 = this.mItems.get(i17);
                                    i2 = i17;
                                    f = f7;
                                }
                                itemInfo3 = null;
                            } else {
                                float f8 = f6 + itemInfo9.widthFactor;
                                int i18 = i14 + 1;
                                f = f8;
                                i2 = i18;
                                if (i18 < this.mItems.size()) {
                                    itemInfo3 = this.mItems.get(i18);
                                    f = f8;
                                    i2 = i18;
                                }
                                itemInfo3 = null;
                            }
                        } else if (itemInfo9 == null) {
                            break;
                        } else {
                            f = f6;
                            i2 = i14;
                            itemInfo3 = itemInfo9;
                            if (i16 == itemInfo9.position) {
                                f = f6;
                                i2 = i14;
                                itemInfo3 = itemInfo9;
                                if (!itemInfo9.scrolling) {
                                    this.mItems.remove(i14);
                                    this.mAdapter.destroyItem((ViewGroup) this, i16, itemInfo9.object);
                                    f = f6;
                                    i2 = i14;
                                    if (i14 < this.mItems.size()) {
                                        itemInfo3 = this.mItems.get(i14);
                                        f = f6;
                                        i2 = i14;
                                    }
                                    itemInfo3 = null;
                                }
                            }
                        }
                        f6 = f;
                        i14 = i2;
                        itemInfo9 = itemInfo3;
                        i15 = i16;
                    }
                }
                calculatePageOffsets(itemInfo5, i9, itemInfo);
                this.mAdapter.setPrimaryItem((ViewGroup) this, this.mCurItem, itemInfo5.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            int childCount = getChildCount();
            for (int i19 = 0; i19 < childCount; i19++) {
                View childAt = getChildAt(i19);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                layoutParams.childIndex = i19;
                if (!layoutParams.isDecor && layoutParams.widthFactor == ActionBarShadowController.ELEVATION_LOW && (infoForChild = infoForChild(childAt)) != null) {
                    layoutParams.widthFactor = infoForChild.widthFactor;
                    layoutParams.position = infoForChild.position;
                }
            }
            sortChildDrawingOrder();
            if (hasFocus()) {
                View findFocus = findFocus();
                ItemInfo infoForAnyChild = findFocus != null ? infoForAnyChild(findFocus) : null;
                if (infoForAnyChild == null || infoForAnyChild.position != this.mCurItem) {
                    for (int i20 = 0; i20 < getChildCount(); i20++) {
                        View childAt2 = getChildAt(i20);
                        ItemInfo infoForChild2 = infoForChild(childAt2);
                        if (infoForChild2 != null && infoForChild2.position == this.mCurItem && childAt2.requestFocus(2)) {
                            return;
                        }
                    }
                }
            }
        }
    }

    public void removeOnAdapterChangeListener(OnAdapterChangeListener onAdapterChangeListener) {
        List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
        if (list != null) {
            list.remove(onAdapterChangeListener);
        }
    }

    public void removeOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        List<OnPageChangeListener> list = this.mOnPageChangeListeners;
        if (list != null) {
            list.remove(onPageChangeListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void removeView(View view) {
        if (this.mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        PagerAdapter pagerAdapter2 = this.mAdapter;
        if (pagerAdapter2 != null) {
            pagerAdapter2.setViewPagerObserver(null);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i = 0; i < this.mItems.size(); i++) {
                ItemInfo itemInfo = this.mItems.get(i);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeNonDecorViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        PagerAdapter pagerAdapter3 = this.mAdapter;
        this.mAdapter = pagerAdapter;
        this.mExpectedAdapterCount = 0;
        if (pagerAdapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.setViewPagerObserver(this.mObserver);
            this.mPopulatePending = false;
            boolean z = this.mFirstLayout;
            this.mFirstLayout = true;
            this.mExpectedAdapterCount = this.mAdapter.getCount();
            if (this.mRestoredCurItem >= 0) {
                this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
                setCurrentItemInternal(this.mRestoredCurItem, false, true);
                this.mRestoredCurItem = -1;
                this.mRestoredAdapterState = null;
                this.mRestoredClassLoader = null;
            } else if (z) {
                requestLayout();
            } else {
                populate();
            }
        }
        List<OnAdapterChangeListener> list = this.mAdapterChangeListeners;
        if (list == null || list.isEmpty()) {
            return;
        }
        int size = this.mAdapterChangeListeners.size();
        for (int i2 = 0; i2 < size; i2++) {
            this.mAdapterChangeListeners.get(i2).onAdapterChanged(this, pagerAdapter3, pagerAdapter);
        }
    }

    public void setCurrentItem(int i) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        this.mPopulatePending = false;
        setCurrentItemInternal(i, z, false);
    }

    public void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    public void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        int i3;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
        } else if (z2 || this.mCurItem != i || this.mItems.size() == 0) {
            if (i < 0) {
                i3 = 0;
            } else {
                i3 = i;
                if (i >= this.mAdapter.getCount()) {
                    i3 = this.mAdapter.getCount() - 1;
                }
            }
            int i4 = this.mOffscreenPageLimit;
            int i5 = this.mCurItem;
            if (i3 > i5 + i4 || i3 < i5 - i4) {
                for (int i6 = 0; i6 < this.mItems.size(); i6++) {
                    this.mItems.get(i6).scrolling = true;
                }
            }
            boolean z3 = this.mCurItem != i3;
            if (!this.mFirstLayout) {
                populate(i3);
                scrollToItem(i3, z, i2, z3);
                return;
            }
            this.mCurItem = i3;
            if (z3) {
                dispatchOnPageSelected(i3);
            }
            requestLayout();
        } else {
            setScrollingCacheEnabled(false);
        }
    }

    public void setDragInGutterEnabled(boolean z) {
        this.mDragInGutterEnabled = z;
    }

    public OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener onPageChangeListener) {
        OnPageChangeListener onPageChangeListener2 = this.mInternalPageChangeListener;
        this.mInternalPageChangeListener = onPageChangeListener;
        return onPageChangeListener2;
    }

    public void setOffscreenPageLimit(int i) {
        int i2 = i;
        if (i < 1) {
            Log.w(TAG, "Requested offscreen page limit " + i + " too small; defaulting to 1");
            i2 = 1;
        }
        if (i2 != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = i2;
            populate();
        }
    }

    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void setPageMargin(int i) {
        int i2 = this.mPageMargin;
        this.mPageMargin = i;
        int width = getWidth();
        recomputeScrollPosition(width, width, i, i2);
        requestLayout();
    }

    public void setPageMarginDrawable(int i) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setPageMarginDrawable(Drawable drawable) {
        this.mMarginDrawable = drawable;
        if (drawable != null) {
            refreshDrawableState();
        }
        setWillNotDraw(drawable == null);
        invalidate();
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer) {
        setPageTransformer(z, pageTransformer, 2);
    }

    public void setPageTransformer(boolean z, PageTransformer pageTransformer, int i) {
        int i2 = 1;
        boolean z2 = pageTransformer != null;
        boolean z3 = z2 != (this.mPageTransformer != null);
        this.mPageTransformer = pageTransformer;
        setChildrenDrawingOrderEnabled(z2);
        if (z2) {
            if (z) {
                i2 = 2;
            }
            this.mDrawingOrder = i2;
            this.mPageTransformerLayerType = i;
        } else {
            this.mDrawingOrder = 0;
        }
        if (z3) {
            populate();
        }
    }

    public void setScrollState(int i) {
        if (this.mScrollState == i) {
            return;
        }
        this.mScrollState = i;
        if (this.mPageTransformer != null) {
            enableLayers(i != 0);
        }
        dispatchOnScrollStateChanged(i);
    }

    public void smoothScrollTo(int i, int i2, int i3) {
        int scrollX;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        Scroller scroller = this.mScroller;
        if ((scroller == null || scroller.isFinished()) ? false : true) {
            scrollX = this.mIsScrollStarted ? this.mScroller.getCurrX() : this.mScroller.getStartX();
            this.mScroller.abortAnimation();
            setScrollingCacheEnabled(false);
        } else {
            scrollX = getScrollX();
        }
        int scrollY = getScrollY();
        int i4 = i - scrollX;
        int i5 = i2 - scrollY;
        if (i4 == 0 && i5 == 0) {
            completeScroll(false);
            populate();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        setScrollState(2);
        int clientWidth = getClientWidth();
        int i6 = clientWidth / 2;
        float abs = Math.abs(i4);
        float f = clientWidth;
        float f2 = i6;
        float distanceInfluenceForSnapDuration = distanceInfluenceForSnapDuration(Math.min(1.0f, (abs * 1.0f) / f));
        int abs2 = Math.abs(i3);
        int min = Math.min(abs2 > 0 ? Math.round(Math.abs((f2 + (distanceInfluenceForSnapDuration * f2)) / abs2) * 1000.0f) * 4 : (int) (((Math.abs(i4) / ((f * this.mAdapter.getPageWidth(this.mCurItem)) + this.mPageMargin)) + 1.0f) * 100.0f), (int) MAX_SETTLE_DURATION);
        this.mIsScrollStarted = false;
        this.mScroller.startScroll(scrollX, scrollY, i4, i5, min);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mMarginDrawable;
    }
}