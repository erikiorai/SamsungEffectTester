package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.RemeasuringLinearLayout;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanel.class */
public class QSPanel extends LinearLayout implements TunerService.Tunable {
    public View mAutoBrightnessView;
    public View mBrightnessView;
    public final ArrayMap<View, Integer> mChildrenLayoutTop;
    public final Rect mClippingRect;
    public Runnable mCollapseExpandAction;
    public int mContentMarginEnd;
    public int mContentMarginStart;
    public final Context mContext;
    public boolean mExpanded;
    public View mFooter;
    public PageIndicator mFooterPageIndicator;
    public LinearLayout mHorizontalContentContainer;
    public LinearLayout mHorizontalLinearLayout;
    public boolean mIsAutomaticBrightnessAvailable;
    public boolean mListening;
    public ViewGroup mMediaHostView;
    public final int mMediaTopMargin;
    public final int mMediaTotalBottomMargin;
    public int mMovableContentStartIndex;
    public final List<OnConfigurationChangedListener> mOnConfigurationChangedListeners;
    public QSLogger mQsLogger;
    public boolean mShouldMoveMediaOnExpansion;
    public float mSquishinessFraction;
    public QSTileLayout mTileLayout;
    public boolean mUsingCombinedHeaders;
    public boolean mUsingHorizontalLayout;
    public boolean mUsingMediaPlayer;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanel$OnConfigurationChangedListener.class */
    public interface OnConfigurationChangedListener {
        void onConfigurationChange(Configuration configuration);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanel$QSTileLayout.class */
    public interface QSTileLayout {
        void addTile(QSPanelControllerBase.TileRecord tileRecord);

        int getHeight();

        int getNumVisibleTiles();

        int getTilesHeight();

        void removeTile(QSPanelControllerBase.TileRecord tileRecord);

        default void restoreInstanceState(Bundle bundle) {
        }

        default void saveInstanceState(Bundle bundle) {
        }

        default void setExpansion(float f, float f2) {
        }

        void setListening(boolean z, UiEventLogger uiEventLogger);

        default void setLogger(QSLogger qSLogger) {
        }

        default boolean setMaxColumns(int i) {
            return false;
        }

        default boolean setMinRows(int i) {
            return false;
        }

        void setSquishinessFraction(float f);

        boolean updateResources();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda0.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$3kx7WXfHRZkybJwv5sociXu0uXI(QSPanel qSPanel, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        qSPanel.lambda$setHorizontalContentContainerClipping$0(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$Lhko3SbScxQmJmzV9JEqtYPAMhk(Configuration configuration, OnConfigurationChangedListener onConfigurationChangedListener) {
        onConfigurationChangedListener.onConfigurationChange(configuration);
    }

    public QSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsAutomaticBrightnessAvailable = false;
        this.mOnConfigurationChangedListeners = new ArrayList();
        this.mSquishinessFraction = 1.0f;
        this.mChildrenLayoutTop = new ArrayMap<>();
        this.mClippingRect = new Rect();
        this.mShouldMoveMediaOnExpansion = true;
        this.mUsingCombinedHeaders = false;
        this.mUsingMediaPlayer = Utils.useQsMediaPlayer(context);
        this.mMediaTotalBottomMargin = getResources().getDimensionPixelSize(R$dimen.quick_settings_bottom_margin_media);
        this.mMediaTopMargin = getResources().getDimensionPixelSize(R$dimen.qs_tile_margin_vertical);
        this.mContext = context;
        setOrientation(1);
        this.mMovableContentStartIndex = getChildCount();
        this.mIsAutomaticBrightnessAvailable = getResources().getBoolean(17891380);
    }

    public /* synthetic */ void lambda$setHorizontalContentContainerClipping$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        int i9 = i3 - i;
        if (i9 == i7 - i5 && i4 - i2 == i8 - i6) {
            return;
        }
        Rect rect = this.mClippingRect;
        rect.right = i9;
        rect.bottom = i4 - i2;
        this.mHorizontalContentContainer.setClipBounds(rect);
    }

    public static void switchToParent(View view, ViewGroup viewGroup, int i, String str) {
        if (viewGroup == null) {
            Log.w(str, "Trying to move view to null parent", new IllegalStateException());
            return;
        }
        ViewGroup viewGroup2 = (ViewGroup) view.getParent();
        if (viewGroup2 != viewGroup) {
            if (viewGroup2 != null) {
                viewGroup2.removeView(view);
            }
            viewGroup.addView(view, i);
        } else if (viewGroup.indexOfChild(view) == i) {
        } else {
            viewGroup.removeView(view);
            viewGroup.addView(view, i);
        }
    }

    public void addOnConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mOnConfigurationChangedListeners.add(onConfigurationChangedListener);
    }

    public void addTile(final QSPanelControllerBase.TileRecord tileRecord) {
        QSTile.Callback callback = new QSTile.Callback() { // from class: com.android.systemui.qs.QSPanel.1
            {
                QSPanel.this = this;
            }

            @Override // com.android.systemui.plugins.qs.QSTile.Callback
            public void onStateChanged(QSTile.State state) {
                QSPanel.this.drawTile(tileRecord, state);
            }
        };
        tileRecord.tile.addCallback(callback);
        tileRecord.callback = callback;
        tileRecord.tileView.init(tileRecord.tile);
        tileRecord.tile.refreshState();
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.addTile(tileRecord);
        }
    }

    public QSEvent closePanelEvent() {
        return QSEvent.QS_PANEL_COLLAPSED;
    }

    public boolean displayMediaMarginsOnMedia() {
        return true;
    }

    public void drawTile(QSPanelControllerBase.TileRecord tileRecord, QSTile.State state) {
        tileRecord.tileView.onStateChanged(state);
    }

    public View getBrightnessView() {
        return this.mBrightnessView;
    }

    public String getDumpableTag() {
        return "QSPanel";
    }

    public QSTileLayout getOrCreateTileLayout() {
        if (this.mTileLayout == null) {
            QSTileLayout qSTileLayout = (QSTileLayout) LayoutInflater.from(this.mContext).inflate(R$layout.qs_paged_tile_layout, (ViewGroup) this, false);
            this.mTileLayout = qSTileLayout;
            qSTileLayout.setLogger(this.mQsLogger);
            this.mTileLayout.setSquishinessFraction(this.mSquishinessFraction);
        }
        return this.mTileLayout;
    }

    public QSTileLayout getTileLayout() {
        return this.mTileLayout;
    }

    public void initialize(QSLogger qSLogger) {
        this.mQsLogger = qSLogger;
        this.mTileLayout = getOrCreateTileLayout();
        if (this.mUsingMediaPlayer) {
            RemeasuringLinearLayout remeasuringLinearLayout = new RemeasuringLinearLayout(this.mContext);
            this.mHorizontalLinearLayout = remeasuringLinearLayout;
            remeasuringLinearLayout.setOrientation(0);
            this.mHorizontalLinearLayout.setVisibility(this.mUsingHorizontalLayout ? 0 : 8);
            this.mHorizontalLinearLayout.setClipChildren(false);
            this.mHorizontalLinearLayout.setClipToPadding(false);
            RemeasuringLinearLayout remeasuringLinearLayout2 = new RemeasuringLinearLayout(this.mContext);
            this.mHorizontalContentContainer = remeasuringLinearLayout2;
            remeasuringLinearLayout2.setOrientation(1);
            setHorizontalContentContainerClipping();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
            layoutParams.setMarginStart(0);
            layoutParams.setMarginEnd((int) this.mContext.getResources().getDimension(R$dimen.qs_media_padding));
            layoutParams.gravity = 16;
            this.mHorizontalLinearLayout.addView(this.mHorizontalContentContainer, layoutParams);
            addView(this.mHorizontalLinearLayout, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    public boolean isExpanded() {
        return this.mExpanded;
    }

    public boolean isListening() {
        return this.mListening;
    }

    public boolean mediaNeedsTopMargin() {
        return false;
    }

    public final boolean needsDynamicRowsAndColumns() {
        return true;
    }

    @Override // android.view.View
    public void onConfigurationChanged(final Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mOnConfigurationChangedListeners.forEach(new Consumer() { // from class: com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSPanel.$r8$lambda$Lhko3SbScxQmJmzV9JEqtYPAMhk(configuration, (QSPanel.OnConfigurationChangedListener) obj);
            }
        });
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mFooter = findViewById(R$id.qs_footer);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        for (int i5 = 0; i5 < getChildCount(); i5++) {
            View childAt = getChildAt(i5);
            this.mChildrenLayoutTop.put(childAt, Integer.valueOf(childAt.getTop()));
        }
        updateViewPositions();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        QSTileLayout qSTileLayout = this.mTileLayout;
        int i3 = i2;
        if (qSTileLayout instanceof PagedTileLayout) {
            PageIndicator pageIndicator = this.mFooterPageIndicator;
            if (pageIndicator != null) {
                pageIndicator.setNumPages(((PagedTileLayout) qSTileLayout).getNumPages());
            }
            i3 = i2;
            if (((View) this.mTileLayout).getParent() == this) {
                int size = View.MeasureSpec.getSize(i2);
                i3 = View.MeasureSpec.makeMeasureSpec(10000, 1073741824);
                ((PagedTileLayout) this.mTileLayout).setExcessHeight(10000 - size);
            }
        }
        super.onMeasure(i, i3);
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        int childCount = getChildCount();
        int i4 = 0;
        while (i4 < childCount) {
            View childAt = getChildAt(i4);
            int i5 = paddingBottom;
            if (childAt.getVisibility() != 8) {
                int measuredHeight = childAt.getMeasuredHeight();
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) childAt.getLayoutParams();
                i5 = paddingBottom + measuredHeight + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin;
            }
            i4++;
            paddingBottom = i5;
        }
        setMeasuredDimension(getMeasuredWidth(), paddingBottom);
    }

    public void onTuningChanged(String str, String str2) {
        View view;
        if ("customsecure:qs_show_auto_brightness".equals(str) && this.mIsAutomaticBrightnessAvailable) {
            updateViewVisibilityForTuningValue(this.mAutoBrightnessView, str2);
        } else if (!"customsecure:qs_show_brightness_slider".equals(str) || (view = this.mBrightnessView) == null) {
        } else {
            updateViewVisibilityForTuningValue(view, str2);
        }
    }

    public QSEvent openPanelEvent() {
        return QSEvent.QS_PANEL_EXPANDED;
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        Runnable runnable;
        if ((i == 262144 || i == 524288) && (runnable = this.mCollapseExpandAction) != null) {
            runnable.run();
            return true;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v61, types: [android.widget.LinearLayout] */
    /* JADX WARN: Type inference failed for: r4v0, types: [android.view.View, android.view.ViewGroup] */
    public final void reAttachMediaHost(ViewGroup viewGroup, boolean z) {
        if (this.mUsingMediaPlayer) {
            this.mMediaHostView = viewGroup;
            QSPanel qSPanel = z ? this.mHorizontalLinearLayout : this;
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            Log.d(getDumpableTag(), "Reattaching media host: " + z + ", current " + viewGroup2 + ", new " + qSPanel);
            if (viewGroup2 != qSPanel) {
                if (viewGroup2 != null) {
                    viewGroup2.removeView(viewGroup);
                }
                qSPanel.addView(viewGroup);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams.height = -2;
                layoutParams.width = z ? 0 : -1;
                layoutParams.weight = z ? 1.0f : 0.0f;
                layoutParams.bottomMargin = (!z || displayMediaMarginsOnMedia()) ? Math.max(this.mMediaTotalBottomMargin - getPaddingBottom(), 0) : 0;
                int i = 0;
                if (mediaNeedsTopMargin()) {
                    i = 0;
                    if (!z) {
                        i = this.mMediaTopMargin;
                    }
                }
                layoutParams.topMargin = i;
                viewGroup.setLayoutParams(layoutParams);
            }
        }
    }

    public void removeOnConfigurationChangedListener(OnConfigurationChangedListener onConfigurationChangedListener) {
        this.mOnConfigurationChangedListeners.remove(onConfigurationChangedListener);
    }

    public void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mTileLayout.removeTile(tileRecord);
    }

    public void setBrightnessView(View view) {
        View view2 = this.mBrightnessView;
        if (view2 != null) {
            removeView(view2);
            this.mMovableContentStartIndex--;
        }
        addView(view, 0);
        this.mBrightnessView = view;
        this.mAutoBrightnessView = view.findViewById(R$id.brightness_icon);
        setBrightnessViewMargin();
        this.mMovableContentStartIndex++;
    }

    public final void setBrightnessViewMargin() {
        View view = this.mBrightnessView;
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R$dimen.qs_brightness_margin_top);
            marginLayoutParams.bottomMargin = this.mContext.getResources().getDimensionPixelSize(R$dimen.qs_brightness_margin_bottom);
            this.mBrightnessView.setLayoutParams(marginLayoutParams);
        }
    }

    public void setCollapseExpandAction(Runnable runnable) {
        this.mCollapseExpandAction = runnable;
    }

    public void setContentMargins(int i, int i2, ViewGroup viewGroup) {
        this.mContentMarginStart = i;
        this.mContentMarginEnd = i2;
        updateMediaHostContentMargins(viewGroup);
    }

    public void setExpanded(boolean z) {
        if (this.mExpanded == z) {
            return;
        }
        this.mExpanded = z;
        if (z) {
            return;
        }
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout instanceof PagedTileLayout) {
            ((PagedTileLayout) qSTileLayout).setCurrentItem(0, false);
        }
    }

    public void setFooterPageIndicator(PageIndicator pageIndicator) {
        if (this.mTileLayout instanceof PagedTileLayout) {
            this.mFooterPageIndicator = pageIndicator;
            updatePageIndicator();
        }
    }

    public void setHorizontalContentContainerClipping() {
        this.mHorizontalContentContainer.setClipChildren(true);
        this.mHorizontalContentContainer.setClipToPadding(false);
        this.mHorizontalContentContainer.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.QSPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                QSPanel.$r8$lambda$3kx7WXfHRZkybJwv5sociXu0uXI(QSPanel.this, view, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        Rect rect = this.mClippingRect;
        rect.left = 0;
        rect.top = -1000;
        this.mHorizontalContentContainer.setClipBounds(rect);
    }

    public void setListening(boolean z) {
        this.mListening = z;
    }

    public void setPageListener(PagedTileLayout.PageListener pageListener) {
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout instanceof PagedTileLayout) {
            ((PagedTileLayout) qSTileLayout).setPageListener(pageListener);
        }
    }

    public void setPageMargin(int i) {
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout instanceof PagedTileLayout) {
            ((PagedTileLayout) qSTileLayout).setPageMargin(i);
        }
    }

    public void setShouldMoveMediaOnExpansion(boolean z) {
        this.mShouldMoveMediaOnExpansion = z;
    }

    public void setSquishinessFraction(float f) {
        if (Float.compare(f, this.mSquishinessFraction) == 0) {
            return;
        }
        this.mSquishinessFraction = f;
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout == null) {
            return;
        }
        qSTileLayout.setSquishinessFraction(f);
        if (getMeasuredWidth() == 0) {
            return;
        }
        updateViewPositions();
    }

    public void setUsingCombinedHeaders(boolean z) {
        this.mUsingCombinedHeaders = z;
    }

    public void setUsingHorizontalLayout(boolean z, ViewGroup viewGroup, boolean z2) {
        if (z != this.mUsingHorizontalLayout || z2) {
            String dumpableTag = getDumpableTag();
            Log.d(dumpableTag, "setUsingHorizontalLayout: " + z + ", " + z2);
            this.mUsingHorizontalLayout = z;
            switchAllContentToParent(z ? this.mHorizontalContentContainer : this, this.mTileLayout);
            reAttachMediaHost(viewGroup, z);
            if (needsDynamicRowsAndColumns()) {
                this.mTileLayout.setMinRows(z ? 2 : 1);
                this.mTileLayout.setMaxColumns(z ? 2 : 4);
            }
            updateMargins(viewGroup);
            this.mHorizontalLinearLayout.setVisibility(z ? 0 : 8);
        }
    }

    public final void switchAllContentToParent(ViewGroup viewGroup, QSTileLayout qSTileLayout) {
        int i = viewGroup == this ? this.mMovableContentStartIndex : 0;
        switchToParent((View) qSTileLayout, viewGroup, i);
        View view = this.mFooter;
        if (view != null) {
            switchToParent(view, viewGroup, i + 1);
        }
    }

    public final void switchToParent(View view, ViewGroup viewGroup, int i) {
        switchToParent(view, viewGroup, i, getDumpableTag());
    }

    public final void updateHorizontalLinearLayoutMargins() {
        if (this.mHorizontalLinearLayout == null || displayMediaMarginsOnMedia()) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mHorizontalLinearLayout.getLayoutParams();
        layoutParams.bottomMargin = Math.max(this.mMediaTotalBottomMargin - getPaddingBottom(), 0);
        this.mHorizontalLinearLayout.setLayoutParams(layoutParams);
    }

    public void updateMargins(View view, int i, int i2) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        if (layoutParams != null) {
            layoutParams.setMarginStart(i);
            layoutParams.setMarginEnd(i2);
            view.setLayoutParams(layoutParams);
        }
    }

    public final void updateMargins(ViewGroup viewGroup) {
        updateMediaHostContentMargins(viewGroup);
        updateHorizontalLinearLayoutMargins();
        updatePadding();
    }

    public void updateMediaHostContentMargins(ViewGroup viewGroup) {
        if (this.mUsingMediaPlayer) {
            updateMargins(viewGroup, 0, this.mUsingHorizontalLayout ? this.mContentMarginEnd : 0);
        }
    }

    public void updatePadding() {
        Resources resources = this.mContext.getResources();
        setPaddingRelative(getPaddingStart(), resources.getDimensionPixelSize(this.mUsingCombinedHeaders ? R$dimen.qs_panel_padding_top_combined_headers : R$dimen.qs_panel_padding_top), getPaddingEnd(), resources.getDimensionPixelSize(R$dimen.qs_panel_padding_bottom));
    }

    public final void updatePageIndicator() {
        PageIndicator pageIndicator;
        if (!(this.mTileLayout instanceof PagedTileLayout) || (pageIndicator = this.mFooterPageIndicator) == null) {
            return;
        }
        pageIndicator.setVisibility(8);
        ((PagedTileLayout) this.mTileLayout).setPageIndicator(this.mFooterPageIndicator);
    }

    public void updateResources() {
        updatePadding();
        updatePageIndicator();
        setBrightnessViewMargin();
        QSTileLayout qSTileLayout = this.mTileLayout;
        if (qSTileLayout != null) {
            qSTileLayout.updateResources();
        }
    }

    public final void updateViewPositions() {
        int tilesHeight = this.mTileLayout.getTilesHeight();
        int height = this.mTileLayout.getHeight();
        boolean z = false;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (z) {
                int i2 = (childAt != this.mMediaHostView || this.mShouldMoveMediaOnExpansion) ? tilesHeight - height : 0;
                Integer num = this.mChildrenLayoutTop.get(childAt);
                if (num != null) {
                    int intValue = num.intValue();
                    int left = childAt.getLeft();
                    int i3 = intValue + i2;
                    childAt.setLeftTopRightBottom(left, i3, childAt.getRight(), childAt.getHeight() + i3);
                }
            }
            if (childAt == this.mTileLayout) {
                z = true;
            }
        }
    }

    public final void updateViewVisibilityForTuningValue(View view, String str) {
        view.setVisibility(TunerService.parseIntegerSwitch(str, true) ? 0 : 8);
    }
}