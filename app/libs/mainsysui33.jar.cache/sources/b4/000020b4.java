package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Scroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$layout;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.logging.QSLogger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/PagedTileLayout.class */
public class PagedTileLayout extends ViewPager implements QSPanel.QSTileLayout {
    public static final Interpolator SCROLL_CUBIC = new Interpolator() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda0
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            return PagedTileLayout.$r8$lambda$OFYC2qFDgXh9TeNUYqUduxn3PHM(f);
        }
    };
    public final PagerAdapter mAdapter;
    public AnimatorSet mBounceAnimatorSet;
    public boolean mDistributeTiles;
    public int mExcessHeight;
    public int mLastExcessHeight;
    public float mLastExpansion;
    public int mLastMaxHeight;
    public int mLayoutDirection;
    public int mLayoutOrientation;
    public boolean mListening;
    public QSLogger mLogger;
    public int mMaxColumns;
    public int mMinRows;
    public final ViewPager.OnPageChangeListener mOnPageChangeListener;
    public PageIndicator mPageIndicator;
    public float mPageIndicatorPosition;
    public PageListener mPageListener;
    public int mPageToRestore;
    public final ArrayList<TileLayout> mPages;
    public Scroller mScroller;
    public final ArrayList<QSPanelControllerBase.TileRecord> mTiles;
    public final UiEventLogger mUiEventLogger;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/PagedTileLayout$PageListener.class */
    public interface PageListener {
        void onPageChanged(boolean z, int i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$EW4Xw4i-LCGdSRjqpDll-HChOF4 */
    public static /* synthetic */ void m3725$r8$lambda$EW4Xw4iLCGdSRjqpDllHChOF4(PagedTileLayout pagedTileLayout, int i) {
        pagedTileLayout.lambda$fakeDragBy$1(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda0.getInterpolation(float):float] */
    public static /* synthetic */ float $r8$lambda$OFYC2qFDgXh9TeNUYqUduxn3PHM(float f) {
        return lambda$static$0(f);
    }

    public PagedTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTiles = new ArrayList<>();
        this.mPages = new ArrayList<>();
        this.mDistributeTiles = false;
        this.mPageToRestore = -1;
        this.mUiEventLogger = QSEvents.INSTANCE.getQsUiEventsLogger();
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        this.mLastMaxHeight = -1;
        ViewPager.SimpleOnPageChangeListener simpleOnPageChangeListener = new ViewPager.SimpleOnPageChangeListener() { // from class: com.android.systemui.qs.PagedTileLayout.2
            public int mCurrentScrollState = 0;
            public boolean mIsScrollJankTraceBegin = false;

            {
                PagedTileLayout.this = this;
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
                if (i != this.mCurrentScrollState && i == 0) {
                    InteractionJankMonitor.getInstance().end(6);
                    this.mIsScrollJankTraceBegin = false;
                }
                this.mCurrentScrollState = i;
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                boolean z = true;
                if (!this.mIsScrollJankTraceBegin && this.mCurrentScrollState == 1) {
                    InteractionJankMonitor.getInstance().begin(PagedTileLayout.this, 6);
                    this.mIsScrollJankTraceBegin = true;
                }
                if (PagedTileLayout.this.mPageIndicator == null) {
                    return;
                }
                PagedTileLayout.this.mPageIndicatorPosition = i + f;
                PagedTileLayout.this.mPageIndicator.setLocation(PagedTileLayout.this.mPageIndicatorPosition);
                if (PagedTileLayout.this.mPageListener != null) {
                    int i3 = i;
                    if (PagedTileLayout.this.isLayoutRtl()) {
                        i3 = (PagedTileLayout.this.mPages.size() - 1) - i;
                    }
                    PageListener pageListener = PagedTileLayout.this.mPageListener;
                    if (i2 != 0 || i3 != 0) {
                        z = false;
                    }
                    if (i2 != 0) {
                        i3 = -1;
                    }
                    pageListener.onPageChanged(z, i3);
                }
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                PagedTileLayout.this.updateSelected();
                if (PagedTileLayout.this.mPageIndicator == null || PagedTileLayout.this.mPageListener == null) {
                    return;
                }
                boolean z = true;
                int i2 = i;
                if (PagedTileLayout.this.isLayoutRtl()) {
                    i2 = (PagedTileLayout.this.mPages.size() - 1) - i;
                }
                PageListener pageListener = PagedTileLayout.this.mPageListener;
                if (i2 != 0) {
                    z = false;
                }
                pageListener.onPageChanged(z, i2);
            }
        };
        this.mOnPageChangeListener = simpleOnPageChangeListener;
        PagerAdapter pagerAdapter = new PagerAdapter() { // from class: com.android.systemui.qs.PagedTileLayout.3
            {
                PagedTileLayout.this = this;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
                PagedTileLayout.this.mLogger.d("Destantiating page at", Integer.valueOf(i));
                viewGroup.removeView((View) obj);
                PagedTileLayout.this.updateListening();
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public int getCount() {
                return PagedTileLayout.this.mPages.size();
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public Object instantiateItem(ViewGroup viewGroup, int i) {
                PagedTileLayout.this.mLogger.d("Instantiating page at", Integer.valueOf(i));
                int i2 = i;
                if (PagedTileLayout.this.isLayoutRtl()) {
                    i2 = (PagedTileLayout.this.mPages.size() - 1) - i;
                }
                ViewGroup viewGroup2 = (ViewGroup) PagedTileLayout.this.mPages.get(i2);
                if (viewGroup2.getParent() != null) {
                    viewGroup.removeView(viewGroup2);
                }
                viewGroup.addView(viewGroup2);
                PagedTileLayout.this.updateListening();
                return viewGroup2;
            }

            @Override // androidx.viewpager.widget.PagerAdapter
            public boolean isViewFromObject(View view, Object obj) {
                return view == obj;
            }
        };
        this.mAdapter = pagerAdapter;
        this.mScroller = new Scroller(context, SCROLL_CUBIC);
        setAdapter(pagerAdapter);
        setOnPageChangeListener(simpleOnPageChangeListener);
        setCurrentItem(0, false);
        this.mLayoutOrientation = getResources().getConfiguration().orientation;
        this.mLayoutDirection = getLayoutDirection();
    }

    public /* synthetic */ void lambda$fakeDragBy$1(int i) {
        setCurrentItem(i, true);
        AnimatorSet animatorSet = this.mBounceAnimatorSet;
        if (animatorSet != null) {
            animatorSet.start();
        }
        setOffscreenPageLimit(1);
    }

    public static /* synthetic */ float lambda$static$0(float f) {
        float f2 = f - 1.0f;
        return (f2 * f2 * f2) + 1.0f;
    }

    public static Animator setupBounceAnimator(View view, int i) {
        view.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        view.setScaleX(ActionBarShadowController.ELEVATION_LOW);
        view.setScaleY(ActionBarShadowController.ELEVATION_LOW);
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat(View.ALPHA, 1.0f), PropertyValuesHolder.ofFloat(View.SCALE_X, 1.0f), PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.0f));
        ofPropertyValuesHolder.setDuration(450L);
        ofPropertyValuesHolder.setStartDelay(i * 85);
        ofPropertyValuesHolder.setInterpolator(new OvershootInterpolator(1.3f));
        return ofPropertyValuesHolder;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void addTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        forceTilesRedistribution("adding new tile");
        requestLayout();
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void computeScroll() {
        if (!this.mScroller.isFinished() && this.mScroller.computeScrollOffset()) {
            if (!isFakeDragging()) {
                beginFakeDrag();
            }
            fakeDragBy(getScrollX() - this.mScroller.getCurrX());
        } else if (isFakeDragging()) {
            endFakeDrag();
            AnimatorSet animatorSet = this.mBounceAnimatorSet;
            if (animatorSet != null) {
                animatorSet.start();
            }
            setOffscreenPageLimit(1);
        }
        super.computeScroll();
    }

    public final TileLayout createTileLayout() {
        TileLayout tileLayout = (TileLayout) LayoutInflater.from(getContext()).inflate(R$layout.qs_paged_page, (ViewGroup) this, false);
        tileLayout.setMinRows(this.mMinRows);
        tileLayout.setMaxColumns(this.mMaxColumns);
        tileLayout.setSelected(false);
        return tileLayout;
    }

    public final void distributeTiles() {
        emptyAndInflateOrRemovePages();
        int i = 0;
        int maxTiles = this.mPages.get(0).maxTiles();
        int size = this.mTiles.size();
        this.mLogger.logTileDistributionInProgress(maxTiles, size);
        int i2 = 0;
        while (true) {
            int i3 = i2;
            if (i >= size) {
                return;
            }
            QSPanelControllerBase.TileRecord tileRecord = this.mTiles.get(i);
            int i4 = i3;
            if (this.mPages.get(i3).mRecords.size() == maxTiles) {
                i4 = i3 + 1;
            }
            this.mLogger.logTileDistributed(tileRecord.tile.getClass().getSimpleName(), i4);
            this.mPages.get(i4).addTile(tileRecord);
            i++;
            i2 = i4;
        }
    }

    public final void emptyAndInflateOrRemovePages() {
        int numPages = getNumPages();
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            this.mPages.get(i).removeAllViews();
        }
        if (size == numPages) {
            return;
        }
        while (this.mPages.size() < numPages) {
            this.mLogger.d("Adding new page");
            this.mPages.add(createTileLayout());
        }
        while (this.mPages.size() > numPages) {
            this.mLogger.d("Removing page");
            ArrayList<TileLayout> arrayList = this.mPages;
            arrayList.remove(arrayList.size() - 1);
        }
        this.mPageIndicator.setNumPages(this.mPages.size());
        setAdapter(this.mAdapter);
        this.mAdapter.notifyDataSetChanged();
        int i2 = this.mPageToRestore;
        if (i2 != -1) {
            setCurrentItem(i2, false);
            this.mPageToRestore = -1;
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void endFakeDrag() {
        try {
            super.endFakeDrag();
        } catch (NullPointerException e) {
            this.mLogger.logException("endFakeDrag called without velocityTracker", e);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void fakeDragBy(float f) {
        try {
            super.fakeDragBy(f);
            postInvalidateOnAnimation();
        } catch (NullPointerException e) {
            this.mLogger.logException("FakeDragBy called before begin", e);
            final int size = this.mPages.size() - 1;
            post(new Runnable() { // from class: com.android.systemui.qs.PagedTileLayout$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    PagedTileLayout.m3725$r8$lambda$EW4Xw4iLCGdSRjqpDllHChOF4(PagedTileLayout.this, size);
                }
            });
        }
    }

    public void forceTilesRedistribution(String str) {
        this.mLogger.d("forcing tile redistribution across pages, reason", str);
        this.mDistributeTiles = true;
    }

    public int getColumnCount() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return this.mPages.get(0).mColumns;
    }

    public final int getCurrentPageNumber() {
        return getPageNumberForDirection(isLayoutRtl());
    }

    public int getNumPages() {
        int size = this.mTiles.size();
        int max = Math.max(size / this.mPages.get(0).maxTiles(), 1);
        int i = max;
        if (size > this.mPages.get(0).maxTiles() * max) {
            i = max + 1;
        }
        return i;
    }

    public int getNumTilesFirstPage() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return this.mPages.get(0).mRecords.size();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getNumVisibleTiles() {
        if (this.mPages.size() == 0) {
            return 0;
        }
        return this.mPages.get(getCurrentPageNumber()).mRecords.size();
    }

    public final int getPageNumberForDirection(boolean z) {
        int currentItem = getCurrentItem();
        int i = currentItem;
        if (z) {
            i = (this.mPages.size() - 1) - currentItem;
        }
        return i;
    }

    public List<String> getSpecsForPage(int i) {
        ArrayList arrayList = new ArrayList();
        if (i < 0) {
            return arrayList;
        }
        int maxTiles = this.mPages.get(0).maxTiles();
        for (int i2 = i * maxTiles; i2 < (i + 1) * maxTiles && i2 < this.mTiles.size(); i2++) {
            arrayList.add(this.mTiles.get(i2).tile.getTileSpec());
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getTilesHeight() {
        TileLayout tileLayout = this.mPages.get(0);
        if (tileLayout == null) {
            return 0;
        }
        return tileLayout.getTilesHeight();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void logVisibleTiles(TileLayout tileLayout) {
        for (int i = 0; i < tileLayout.mRecords.size(); i++) {
            QSTile qSTile = tileLayout.mRecords.get(i).tile;
            this.mUiEventLogger.logWithInstanceId(QSEvent.QS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            TileLayout tileLayout = this.mPages.get(i);
            if (tileLayout.getParent() == null) {
                tileLayout.dispatchConfigurationChanged(configuration);
            }
        }
        int i2 = this.mLayoutOrientation;
        int i3 = configuration.orientation;
        if (i2 == i3) {
            this.mLogger.d("Orientation didn't change, tiles might be not redistributed, new config", configuration);
            return;
        }
        this.mLayoutOrientation = i3;
        forceTilesRedistribution("orientation changed to " + this.mLayoutOrientation);
        setCurrentItem(0, false);
        this.mPageToRestore = 0;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPages.add(createTileLayout());
        this.mAdapter.notifyDataSetChanged();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getCount() <= 0) {
            return;
        }
        accessibilityEvent.setItemCount(this.mAdapter.getCount());
        accessibilityEvent.setFromIndex(getCurrentPageNumber());
        accessibilityEvent.setToIndex(getCurrentPageNumber());
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (getCurrentItem() != 0) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT);
        }
        if (getCurrentItem() != this.mPages.size() - 1) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mPages.get(0).getParent() == null) {
            this.mPages.get(0).layout(i, i2, i3, i4);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager, android.view.View
    public void onMeasure(int i, int i2) {
        int i3;
        int size = this.mTiles.size();
        if (this.mDistributeTiles || this.mLastMaxHeight != View.MeasureSpec.getSize(i2) || this.mLastExcessHeight != this.mExcessHeight) {
            int size2 = View.MeasureSpec.getSize(i2);
            this.mLastMaxHeight = size2;
            int i4 = this.mExcessHeight;
            this.mLastExcessHeight = i4;
            if (this.mPages.get(0).updateMaxRows(size2 - i4, size) || this.mDistributeTiles) {
                this.mDistributeTiles = false;
                distributeTiles();
            }
            int i5 = this.mPages.get(0).mRows;
            for (int i6 = 0; i6 < this.mPages.size(); i6++) {
                this.mPages.get(i6).mRows = i5;
            }
        }
        super.onMeasure(i, i2);
        int childCount = getChildCount();
        int i7 = 0;
        int i8 = 0;
        while (true) {
            i3 = i8;
            if (i7 >= childCount) {
                break;
            }
            int measuredHeight = getChildAt(i7).getMeasuredHeight();
            int i9 = i3;
            if (measuredHeight > i3) {
                i9 = measuredHeight;
            }
            i7++;
            i8 = i9;
        }
        int i10 = i3;
        if (this.mPages.get(0).getParent() == null) {
            this.mPages.get(0).measure(i, i2);
            int measuredHeight2 = this.mPages.get(0).getMeasuredHeight();
            i10 = i3;
            if (measuredHeight2 > i3) {
                i10 = measuredHeight2;
            }
        }
        setMeasuredDimension(getMeasuredWidth(), i10 + getPaddingBottom());
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        boolean z = true;
        if (this.mLayoutDirection != 1) {
            z = false;
        }
        int pageNumberForDirection = getPageNumberForDirection(z);
        super.onRtlPropertiesChanged(i);
        if (this.mLayoutDirection != i) {
            this.mLayoutDirection = i;
            setAdapter(this.mAdapter);
            setCurrentItem(pageNumberForDirection, false);
        }
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        int sanitizePageAction = sanitizePageAction(i);
        boolean performAccessibilityAction = super.performAccessibilityAction(sanitizePageAction, bundle);
        if (performAccessibilityAction && (sanitizePageAction == 8192 || sanitizePageAction == 4096)) {
            requestAccessibilityFocus();
        }
        return performAccessibilityAction;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        if (this.mTiles.remove(tileRecord)) {
            forceTilesRedistribution("removing tile");
            requestLayout();
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void restoreInstanceState(Bundle bundle) {
        this.mPageToRestore = bundle.getInt("current_page", -1);
    }

    public final int sanitizePageAction(int i) {
        int id = AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_LEFT.getId();
        return (i == id || i == AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_RIGHT.getId()) ? !isLayoutRtl() ? i == id ? RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST : RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT : i == id ? RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT : RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST : i;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void saveInstanceState(Bundle bundle) {
        int i = this.mPageToRestore;
        if (i == -1) {
            i = getCurrentPageNumber();
        }
        bundle.putInt("current_page", i);
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setCurrentItem(int i, boolean z) {
        int i2 = i;
        if (isLayoutRtl()) {
            i2 = (this.mPages.size() - 1) - i;
        }
        super.setCurrentItem(i2, z);
    }

    public void setExcessHeight(int i) {
        this.mExcessHeight = i;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setExpansion(float f, float f2) {
        this.mLastExpansion = f;
        updateSelected();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        updateListening();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setLogger(QSLogger qSLogger) {
        this.mLogger = qSLogger;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMaxColumns(int i) {
        this.mMaxColumns = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (this.mPages.get(i2).setMaxColumns(i)) {
                forceTilesRedistribution("maxColumns in pages changed");
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMinRows(int i) {
        this.mMinRows = i;
        boolean z = false;
        for (int i2 = 0; i2 < this.mPages.size(); i2++) {
            if (this.mPages.get(i2).setMinRows(i)) {
                forceTilesRedistribution("minRows changed in page");
                z = true;
            }
        }
        return z;
    }

    public void setPageIndicator(PageIndicator pageIndicator) {
        this.mPageIndicator = pageIndicator;
        pageIndicator.setNumPages(this.mPages.size());
        this.mPageIndicator.setLocation(this.mPageIndicatorPosition);
    }

    public void setPageListener(PageListener pageListener) {
        this.mPageListener = pageListener;
    }

    @Override // androidx.viewpager.widget.ViewPager
    public void setPageMargin(int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
        int i2 = -i;
        marginLayoutParams.setMarginStart(i2);
        marginLayoutParams.setMarginEnd(i2);
        setLayoutParams(marginLayoutParams);
        int size = this.mPages.size();
        for (int i3 = 0; i3 < size; i3++) {
            TileLayout tileLayout = this.mPages.get(i3);
            tileLayout.setPadding(i, tileLayout.getPaddingTop(), i, tileLayout.getPaddingBottom());
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setSquishinessFraction(float f) {
        int size = this.mPages.size();
        for (int i = 0; i < size; i++) {
            this.mPages.get(i).setSquishinessFraction(f);
        }
    }

    public void startTileReveal(Set<String> set, final Runnable runnable) {
        if (set.isEmpty() || this.mPages.size() < 2 || getScrollX() != 0 || !beginFakeDrag()) {
            return;
        }
        int size = this.mPages.size() - 1;
        TileLayout tileLayout = this.mPages.get(size);
        ArrayList arrayList = new ArrayList();
        Iterator<QSPanelControllerBase.TileRecord> it = tileLayout.mRecords.iterator();
        while (it.hasNext()) {
            QSPanelControllerBase.TileRecord next = it.next();
            if (set.contains(next.tile.getTileSpec())) {
                arrayList.add(setupBounceAnimator(next.tileView, arrayList.size()));
            }
        }
        if (arrayList.isEmpty()) {
            endFakeDrag();
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        this.mBounceAnimatorSet = animatorSet;
        animatorSet.playTogether(arrayList);
        this.mBounceAnimatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.PagedTileLayout.1
            {
                PagedTileLayout.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                PagedTileLayout.this.mBounceAnimatorSet = null;
                runnable.run();
            }
        });
        setOffscreenPageLimit(size);
        int width = getWidth() * size;
        Scroller scroller = this.mScroller;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i = width;
        if (isLayoutRtl()) {
            i = -width;
        }
        scroller.startScroll(scrollX, scrollY, i, 0, 750);
        postInvalidateOnAnimation();
    }

    public final void updateListening() {
        Iterator<TileLayout> it = this.mPages.iterator();
        while (it.hasNext()) {
            TileLayout next = it.next();
            next.setListening(next.getParent() != null && this.mListening);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean updateResources() {
        boolean z = false;
        for (int i = 0; i < this.mPages.size(); i++) {
            z |= this.mPages.get(i).updateResources();
        }
        if (z) {
            forceTilesRedistribution("resources in pages changed");
            requestLayout();
        } else {
            this.mLogger.d("resource in pages didn't change, tiles might be not redistributed");
        }
        return z;
    }

    public final void updateSelected() {
        float f = this.mLastExpansion;
        if (f <= ActionBarShadowController.ELEVATION_LOW || f >= 1.0f) {
            boolean z = f == 1.0f;
            setImportantForAccessibility(4);
            int currentPageNumber = getCurrentPageNumber();
            int i = 0;
            while (i < this.mPages.size()) {
                TileLayout tileLayout = this.mPages.get(i);
                tileLayout.setSelected(i == currentPageNumber ? z : false);
                if (tileLayout.isSelected()) {
                    logVisibleTiles(tileLayout);
                }
                i++;
            }
            setImportantForAccessibility(0);
        }
    }
}