package com.android.systemui.qs;

import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import android.view.View;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSAnimator.class */
public class QSAnimator implements QSHost.Callback, PagedTileLayout.PageListener, TouchAnimator.Listener, View.OnLayoutChangeListener, View.OnAttachStateChangeListener {
    public TouchAnimator mAllPagesDelayedAnimator;
    public TouchAnimator mBrightnessOpacityAnimator;
    public TouchAnimator mBrightnessTranslationAnimator;
    public final Executor mExecutor;
    public TouchAnimator mFirstPageAnimator;
    public final QSTileHost mHost;
    public float mLastPosition;
    public int mLastQQSTileHeight;
    public TouchAnimator mNonfirstPageAlphaAnimator;
    public int mNumQuickTiles;
    public boolean mOnKeyguard;
    public HeightExpansionAnimator mOtherFirstPageTilesHeightAnimator;
    public PagedTileLayout mPagedLayout;
    public TouchAnimator mQQSFooterActionsAnimator;
    public HeightExpansionAnimator mQQSTileHeightAnimator;
    public int mQQSTop;
    public TouchAnimator mQQSTranslationYAnimator;
    public final QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    public final QS mQs;
    public final QSPanelController mQsPanelController;
    public final QuickQSPanelController mQuickQSPanelController;
    public final QuickQSPanel mQuickQsPanel;
    public final QuickStatusBarHeader mQuickStatusBarHeader;
    public boolean mShowCollapsedOnKeyguard;
    public TouchAnimator mTranslationXAnimator;
    public TouchAnimator mTranslationYAnimator;
    public final ArrayList<View> mAllViews = new ArrayList<>();
    public final ArrayList<View> mAnimatedQsViews = new ArrayList<>();
    public boolean mOnFirstPage = true;
    public int mCurrentPage = 0;
    public final SparseArray<Pair<HeightExpansionAnimator, TouchAnimator>> mNonFirstPageQSAnimators = new SparseArray<>();
    public boolean mNeedsAnimatorUpdate = false;
    public int[] mTmpLoc1 = new int[2];
    public int[] mTmpLoc2 = new int[2];
    public final TouchAnimator.Listener mNonFirstPageListener = new TouchAnimator.ListenerAdapter() { // from class: com.android.systemui.qs.QSAnimator.1
        {
            QSAnimator.this = this;
        }

        @Override // com.android.systemui.qs.TouchAnimator.ListenerAdapter, com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationAtEnd() {
            QSAnimator.this.mQuickQsPanel.setVisibility(4);
        }

        @Override // com.android.systemui.qs.TouchAnimator.Listener
        public void onAnimationStarted() {
            QSAnimator.this.mQuickQsPanel.setVisibility(0);
        }
    };
    public final Runnable mUpdateAnimators = new Runnable() { // from class: com.android.systemui.qs.QSAnimator$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            QSAnimator.$r8$lambda$X3NBs2olMRBkDKYFVdJQOpLFabM(QSAnimator.this);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSAnimator$HeightExpansionAnimator.class */
    public static class HeightExpansionAnimator {
        public final ValueAnimator mAnimator;
        public final TouchAnimator.Listener mListener;
        public final ValueAnimator.AnimatorUpdateListener mUpdateListener;
        public final List<View> mViews = new ArrayList();

        public HeightExpansionAnimator(TouchAnimator.Listener listener, int i, int i2) {
            ValueAnimator.AnimatorUpdateListener animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.QSAnimator.HeightExpansionAnimator.1
                public float mLastT = -1.0f;

                {
                    HeightExpansionAnimator.this = this;
                }

                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    int size = HeightExpansionAnimator.this.mViews.size();
                    int intValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    for (int i3 = 0; i3 < size; i3++) {
                        View view = (View) HeightExpansionAnimator.this.mViews.get(i3);
                        if (view instanceof HeightOverrideable) {
                            ((HeightOverrideable) view).setHeightOverride(intValue);
                        } else {
                            view.setBottom(view.getTop() + intValue);
                        }
                    }
                    if (animatedFraction == ActionBarShadowController.ELEVATION_LOW) {
                        HeightExpansionAnimator.this.mListener.onAnimationAtStart();
                    } else if (animatedFraction == 1.0f) {
                        HeightExpansionAnimator.this.mListener.onAnimationAtEnd();
                    } else {
                        float f = this.mLastT;
                        if (f <= ActionBarShadowController.ELEVATION_LOW || f == 1.0f) {
                            HeightExpansionAnimator.this.mListener.onAnimationStarted();
                        }
                    }
                    this.mLastT = animatedFraction;
                }
            };
            this.mUpdateListener = animatorUpdateListener;
            this.mListener = listener;
            ValueAnimator ofInt = ValueAnimator.ofInt(i, i2);
            this.mAnimator = ofInt;
            ofInt.setRepeatCount(-1);
            ofInt.setRepeatMode(2);
            ofInt.addUpdateListener(animatorUpdateListener);
        }

        public void addView(View view) {
            this.mViews.add(view);
        }

        public void resetViewsHeights() {
            int size = this.mViews.size();
            for (int i = 0; i < size; i++) {
                View view = this.mViews.get(i);
                if (view instanceof HeightOverrideable) {
                    ((HeightOverrideable) view).resetOverride();
                } else {
                    view.setBottom(view.getTop() + view.getMeasuredHeight());
                }
            }
        }

        public void setInterpolator(TimeInterpolator timeInterpolator) {
            this.mAnimator.setInterpolator(timeInterpolator);
        }

        public void setPosition(float f) {
            this.mAnimator.setCurrentFraction(f);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSAnimator$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$X3NBs2olMRBkDKYFVdJQOpLFabM(QSAnimator qSAnimator) {
        qSAnimator.lambda$new$0();
    }

    public QSAnimator(QS qs, QuickQSPanel quickQSPanel, QuickStatusBarHeader quickStatusBarHeader, QSPanelController qSPanelController, QuickQSPanelController quickQSPanelController, QSTileHost qSTileHost, Executor executor, TunerService tunerService, QSExpansionPathInterpolator qSExpansionPathInterpolator) {
        this.mQs = qs;
        this.mQuickQsPanel = quickQSPanel;
        this.mQsPanelController = qSPanelController;
        this.mQuickQSPanelController = quickQSPanelController;
        this.mQuickStatusBarHeader = quickStatusBarHeader;
        this.mHost = qSTileHost;
        this.mExecutor = executor;
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        qSTileHost.addCallback(this);
        qSPanelController.addOnAttachStateChangeListener(this);
        qs.getView().addOnLayoutChangeListener(this);
        if (qSPanelController.isAttachedToWindow()) {
            onViewAttachedToWindow(null);
        }
        QSPanel.QSTileLayout tileLayout = qSPanelController.getTileLayout();
        if (tileLayout instanceof PagedTileLayout) {
            this.mPagedLayout = (PagedTileLayout) tileLayout;
        } else {
            Log.w("QSAnimator", "QS Not using page layout");
        }
        qSPanelController.setPageListener(this);
    }

    public /* synthetic */ void lambda$new$0() {
        updateAnimators();
        setCurrentPosition();
    }

    public final void addNonFirstPageAnimators(int i) {
        Pair<HeightExpansionAnimator, TouchAnimator> createSecondaryPageAnimators = createSecondaryPageAnimators(i);
        if (createSecondaryPageAnimators != null) {
            this.mNonFirstPageQSAnimators.put(i, createSecondaryPageAnimators);
        }
    }

    public final void animateBrightnessSlider() {
        this.mBrightnessTranslationAnimator = null;
        this.mBrightnessOpacityAnimator = null;
        View brightnessView = this.mQsPanelController.getBrightnessView();
        View brightnessView2 = this.mQuickQSPanelController.getBrightnessView();
        if (brightnessView2 != null && brightnessView2.getVisibility() == 0) {
            this.mAnimatedQsViews.add(brightnessView);
            this.mAllViews.add(brightnessView2);
            this.mBrightnessTranslationAnimator = new TouchAnimator.Builder().addFloat(brightnessView, "sliderScaleY", 0.3f, 1.0f).addFloat(brightnessView2, "translationY", ActionBarShadowController.ELEVATION_LOW, getRelativeTranslationY(brightnessView, brightnessView2)).setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator()).build();
        } else if (brightnessView != null) {
            View view = this.mQs.getView();
            getRelativePosition(this.mTmpLoc1, (View) this.mQsPanelController.getTileLayout(), view);
            getRelativePosition(this.mTmpLoc2, (View) this.mQuickQSPanelController.getTileLayout(), view);
            this.mBrightnessTranslationAnimator = new TouchAnimator.Builder().addFloat(brightnessView, "translationY", (brightnessView.getMeasuredHeight() * 0.5f) + (this.mTmpLoc2[1] - this.mTmpLoc1[1]), ActionBarShadowController.ELEVATION_LOW).addFloat(brightnessView, "sliderScaleY", ActionBarShadowController.ELEVATION_LOW, 1.0f).setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator()).build();
            this.mBrightnessOpacityAnimator = new TouchAnimator.Builder().addFloat(brightnessView, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).setStartDelay(0.2f).setEndDelay(0.5f).build();
            this.mAllViews.add(brightnessView);
        }
    }

    public final void clearAnimationState() {
        int size = this.mAllViews.size();
        this.mQuickQsPanel.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        for (int i = 0; i < size; i++) {
            View view = this.mAllViews.get(i);
            view.setAlpha(1.0f);
            view.setTranslationX(ActionBarShadowController.ELEVATION_LOW);
            view.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
            view.setScaleY(1.0f);
            if (view instanceof SideLabelTileLayout) {
                SideLabelTileLayout sideLabelTileLayout = (SideLabelTileLayout) view;
                sideLabelTileLayout.setClipChildren(false);
                sideLabelTileLayout.setClipToPadding(false);
            }
        }
        HeightExpansionAnimator heightExpansionAnimator = this.mQQSTileHeightAnimator;
        if (heightExpansionAnimator != null) {
            heightExpansionAnimator.resetViewsHeights();
        }
        HeightExpansionAnimator heightExpansionAnimator2 = this.mOtherFirstPageTilesHeightAnimator;
        if (heightExpansionAnimator2 != null) {
            heightExpansionAnimator2.resetViewsHeights();
        }
        for (int i2 = 0; i2 < this.mNonFirstPageQSAnimators.size(); i2++) {
            ((HeightExpansionAnimator) this.mNonFirstPageQSAnimators.valueAt(i2).first).resetViewsHeights();
        }
        int size2 = this.mAnimatedQsViews.size();
        for (int i3 = 0; i3 < size2; i3++) {
            this.mAnimatedQsViews.get(i3).setVisibility(0);
        }
    }

    public final Pair<HeightExpansionAnimator, TouchAnimator> createSecondaryPageAnimators(int i) {
        if (this.mPagedLayout == null) {
            return null;
        }
        TouchAnimator.Builder interpolator = new TouchAnimator.Builder().setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
        TouchAnimator.Builder endDelay = new TouchAnimator.Builder().setStartDelay(0.15f).setEndDelay(0.7f);
        SideLabelTileLayout sideLabelTileLayout = (SideLabelTileLayout) this.mQuickQsPanel.getTileLayout();
        View view = this.mQs.getView();
        List<String> specsForPage = this.mPagedLayout.getSpecsForPage(i);
        ArrayList<String> arrayList = specsForPage;
        if (specsForPage.isEmpty()) {
            ArrayList<String> arrayList2 = this.mQsPanelController.getHost().mTileSpecs;
            Log.e("QSAnimator", "Trying to create animators for empty page " + i + ". Tiles: " + arrayList2);
            arrayList = arrayList2;
        }
        int i2 = -1;
        HeightExpansionAnimator heightExpansionAnimator = null;
        int i3 = 0;
        int i4 = -1;
        while (i3 < arrayList.size()) {
            QSTileView tileView = this.mQsPanelController.getTileView(arrayList.get(i3));
            getRelativePosition(this.mTmpLoc2, tileView, view);
            interpolator.addFloat(tileView, "translationY", -(this.mTmpLoc2[1] - (this.mQQSTop + sideLabelTileLayout.getPhantomTopPosition(i3))), ActionBarShadowController.ELEVATION_LOW);
            int measuredHeight = (tileView.getMeasuredHeight() - this.mLastQQSTileHeight) / 2;
            QSIconView icon = tileView.getIcon();
            float f = -measuredHeight;
            interpolator.addFloat(icon, "translationY", f, ActionBarShadowController.ELEVATION_LOW);
            interpolator.addFloat(tileView.getSecondaryIcon(), "translationY", f, ActionBarShadowController.ELEVATION_LOW);
            interpolator.addFloat(tileView.getLabelContainer(), "translationY", -(measuredHeight - (tileView.getSecondaryLabel().getVisibility() == 0 ? tileView.getSecondaryLabel().getMeasuredHeight() / 2 : 0)), ActionBarShadowController.ELEVATION_LOW);
            interpolator.addFloat(tileView.getSecondaryLabel(), "alpha", ActionBarShadowController.ELEVATION_LOW, 0.3f, 1.0f);
            endDelay.addFloat(tileView.getLabelContainer(), "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
            endDelay.addFloat(tileView.getIcon(), "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
            endDelay.addFloat(tileView.getSecondaryIcon(), "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
            int top = tileView.getTop();
            int i5 = i2;
            int i6 = i4;
            if (top != i2) {
                i6 = i4 + 1;
                i5 = top;
            }
            if (i3 < this.mQuickQsPanel.getTileLayout().getNumVisibleTiles() || i6 < 2) {
                interpolator.addFloat(tileView, "alpha", 0.6f, 1.0f);
            } else {
                float[] fArr = new float[i6];
                fArr[i6 - 1] = 1.0f;
                interpolator.addFloat(tileView, "alpha", fArr);
            }
            HeightExpansionAnimator heightExpansionAnimator2 = heightExpansionAnimator;
            if (heightExpansionAnimator == null) {
                heightExpansionAnimator2 = new HeightExpansionAnimator(this, this.mLastQQSTileHeight, tileView.getMeasuredHeight());
                heightExpansionAnimator2.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
            }
            heightExpansionAnimator2.addView(tileView);
            tileView.setClipChildren(true);
            tileView.setClipToPadding(true);
            this.mAllViews.add(tileView);
            this.mAllViews.add(tileView.getSecondaryLabel());
            this.mAllViews.add(tileView.getIcon());
            this.mAllViews.add(tileView.getSecondaryIcon());
            this.mAllViews.add(tileView.getLabelContainer());
            i3++;
            i2 = i5;
            i4 = i6;
            heightExpansionAnimator = heightExpansionAnimator2;
        }
        interpolator.addFloat(endDelay.build(), "position", ActionBarShadowController.ELEVATION_LOW, 1.0f);
        return new Pair<>(heightExpansionAnimator, interpolator.build());
    }

    public final void getRelativePosition(int[] iArr, View view, View view2) {
        iArr[0] = (view.getWidth() / 2) + 0;
        iArr[1] = 0;
        getRelativePositionInt(iArr, view, view2);
    }

    public final void getRelativePositionInt(int[] iArr, View view, View view2) {
        if (view == view2 || view == null) {
            return;
        }
        if (!isAPage(view)) {
            iArr[0] = iArr[0] + view.getLeft();
            iArr[1] = iArr[1] + view.getTop();
        }
        if (!(view instanceof PagedTileLayout)) {
            iArr[0] = iArr[0] - view.getScrollX();
            iArr[1] = iArr[1] - view.getScrollY();
        }
        getRelativePositionInt(iArr, (View) view.getParent(), view2);
    }

    public final int getRelativeTranslationY(View view, View view2) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        View view3 = this.mQs.getView();
        getRelativePositionInt(iArr, view, view3);
        getRelativePositionInt(iArr2, view2, view3);
        return (iArr[1] - iArr2[1]) - this.mQuickStatusBarHeader.getOffsetTranslation();
    }

    public final boolean isAPage(View view) {
        return view.getClass().equals(SideLabelTileLayout.class);
    }

    public final boolean isIconInAnimatedRow(int i) {
        PagedTileLayout pagedTileLayout = this.mPagedLayout;
        boolean z = false;
        if (pagedTileLayout == null) {
            return false;
        }
        int columnCount = pagedTileLayout.getColumnCount();
        if (i < (((this.mNumQuickTiles + columnCount) - 1) / columnCount) * columnCount) {
            z = true;
        }
        return z;
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationAtEnd() {
        this.mQuickQsPanel.setVisibility(4);
        int size = this.mAnimatedQsViews.size();
        for (int i = 0; i < size; i++) {
            this.mAnimatedQsViews.get(i).setVisibility(0);
        }
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationAtStart() {
        this.mQuickQsPanel.setVisibility(0);
    }

    @Override // com.android.systemui.qs.TouchAnimator.Listener
    public void onAnimationStarted() {
        updateQQSVisibility();
        if (this.mOnFirstPage) {
            int size = this.mAnimatedQsViews.size();
            for (int i = 0; i < size; i++) {
                this.mAnimatedQsViews.get(i).setVisibility(4);
            }
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if ((i == i5 && i2 == i6 && i3 == i7 && i4 == i8) ? false : true) {
            this.mExecutor.execute(this.mUpdateAnimators);
        }
    }

    @Override // com.android.systemui.qs.PagedTileLayout.PageListener
    public void onPageChanged(boolean z, int i) {
        if (i != -1 && this.mCurrentPage != i) {
            this.mCurrentPage = i;
            if (!z && !this.mNonFirstPageQSAnimators.contains(i)) {
                addNonFirstPageAnimators(i);
            }
        }
        if (this.mOnFirstPage == z) {
            return;
        }
        if (!z) {
            clearAnimationState();
        }
        this.mOnFirstPage = z;
    }

    public void onRtlChanged() {
        updateAnimators();
    }

    @Override // com.android.systemui.qs.QSHost.Callback
    public void onTilesChanged() {
        this.mExecutor.execute(this.mUpdateAnimators);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view) {
        updateAnimators();
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view) {
        this.mHost.removeCallback(this);
    }

    public void requestAnimatorUpdate() {
        this.mNeedsAnimatorUpdate = true;
    }

    public final void setCurrentPosition() {
        setPosition(this.mLastPosition);
    }

    public void setOnKeyguard(boolean z) {
        this.mOnKeyguard = z;
        updateQQSVisibility();
        if (this.mOnKeyguard) {
            clearAnimationState();
        }
    }

    public void setPosition(float f) {
        if (this.mNeedsAnimatorUpdate) {
            updateAnimators();
        }
        if (this.mFirstPageAnimator == null) {
            return;
        }
        if (this.mOnKeyguard) {
            f = this.mShowCollapsedOnKeyguard ? 0.0f : 1.0f;
        }
        this.mLastPosition = f;
        if (this.mOnFirstPage) {
            this.mQuickQsPanel.setAlpha(1.0f);
            this.mFirstPageAnimator.setPosition(f);
            this.mTranslationYAnimator.setPosition(f);
            this.mTranslationXAnimator.setPosition(f);
            HeightExpansionAnimator heightExpansionAnimator = this.mOtherFirstPageTilesHeightAnimator;
            if (heightExpansionAnimator != null) {
                heightExpansionAnimator.setPosition(f);
            }
        } else {
            this.mNonfirstPageAlphaAnimator.setPosition(f);
        }
        for (int i = 0; i < this.mNonFirstPageQSAnimators.size(); i++) {
            Pair<HeightExpansionAnimator, TouchAnimator> valueAt = this.mNonFirstPageQSAnimators.valueAt(i);
            if (valueAt != null) {
                ((HeightExpansionAnimator) valueAt.first).setPosition(f);
                ((TouchAnimator) valueAt.second).setPosition(f);
            }
        }
        HeightExpansionAnimator heightExpansionAnimator2 = this.mQQSTileHeightAnimator;
        if (heightExpansionAnimator2 != null) {
            heightExpansionAnimator2.setPosition(f);
        }
        this.mQQSTranslationYAnimator.setPosition(f);
        this.mAllPagesDelayedAnimator.setPosition(f);
        TouchAnimator touchAnimator = this.mBrightnessOpacityAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
        TouchAnimator touchAnimator2 = this.mBrightnessTranslationAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f);
        }
        TouchAnimator touchAnimator3 = this.mQQSFooterActionsAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f);
        }
    }

    public void setShowCollapsedOnKeyguard(boolean z) {
        this.mShowCollapsedOnKeyguard = z;
        updateQQSVisibility();
        setCurrentPosition();
    }

    public final void translateContent(View view, View view2, View view3, int i, int i2, int[] iArr, TouchAnimator.Builder builder, TouchAnimator.Builder builder2, TouchAnimator.Builder builder3) {
        getRelativePosition(iArr, view, view3);
        int i3 = iArr[0];
        int i4 = iArr[1];
        getRelativePosition(iArr, view2, view3);
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = (i5 - i3) - i;
        builder.addFloat(view, "translationX", ActionBarShadowController.ELEVATION_LOW, i7);
        builder.addFloat(view2, "translationX", -i7, ActionBarShadowController.ELEVATION_LOW);
        int i8 = (i6 - i4) - i2;
        builder3.addFloat(view, "translationY", ActionBarShadowController.ELEVATION_LOW, i8);
        builder2.addFloat(view2, "translationY", -i8, ActionBarShadowController.ELEVATION_LOW);
        this.mAllViews.add(view);
        this.mAllViews.add(view2);
    }

    public final void updateAnimators() {
        TouchAnimator.Builder builder;
        QSPanel.QSTileLayout qSTileLayout;
        TouchAnimator.Builder builder2;
        String str;
        TouchAnimator.Builder builder3;
        UniqueObjectHostView uniqueObjectHostView;
        int i;
        SideLabelTileLayout sideLabelTileLayout;
        int offsetTranslation;
        this.mNeedsAnimatorUpdate = false;
        TouchAnimator.Builder builder4 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder5 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder6 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder7 = new TouchAnimator.Builder();
        TouchAnimator.Builder builder8 = new TouchAnimator.Builder();
        TouchAnimator.Builder interpolator = new TouchAnimator.Builder().setInterpolator(Interpolators.ACCELERATE);
        Collection<QSTile> tiles = this.mHost.getTiles();
        clearAnimationState();
        this.mNonFirstPageQSAnimators.clear();
        this.mAllViews.clear();
        this.mAnimatedQsViews.clear();
        this.mQQSTileHeightAnimator = null;
        this.mOtherFirstPageTilesHeightAnimator = null;
        this.mNumQuickTiles = this.mQuickQsPanel.getNumQuickTiles();
        QSPanel.QSTileLayout tileLayout = this.mQsPanelController.getTileLayout();
        this.mAllViews.add((View) tileLayout);
        this.mLastQQSTileHeight = 0;
        if (this.mQsPanelController.areThereTiles()) {
            int i2 = 0;
            for (QSTile qSTile : tiles) {
                QSTileView tileView = this.mQsPanelController.getTileView(qSTile);
                if (tileView != null) {
                    PagedTileLayout pagedTileLayout = this.mPagedLayout;
                    if (pagedTileLayout != null && i2 >= pagedTileLayout.getNumTilesFirstPage()) {
                        break;
                    }
                    tileView.getIcon().getIconView();
                    View view = this.mQs.getView();
                    if (i2 < this.mQuickQSPanelController.getTileLayout().getNumVisibleTiles()) {
                        QSTileView tileView2 = this.mQuickQSPanelController.getTileView(qSTile);
                        if (tileView2 != null) {
                            getRelativePosition(this.mTmpLoc1, tileView2, view);
                            getRelativePosition(this.mTmpLoc2, tileView, view);
                            int[] iArr = this.mTmpLoc2;
                            int i3 = iArr[1];
                            int[] iArr2 = this.mTmpLoc1;
                            int i4 = i3 - iArr2[1];
                            int i5 = iArr[0] - iArr2[0];
                            builder6.addFloat(tileView2, "translationY", ActionBarShadowController.ELEVATION_LOW, i4 - this.mQuickStatusBarHeader.getOffsetTranslation());
                            builder5.addFloat(tileView, "translationY", -offsetTranslation, ActionBarShadowController.ELEVATION_LOW);
                            builder7.addFloat(tileView2, "translationX", ActionBarShadowController.ELEVATION_LOW, i5);
                            builder7.addFloat(tileView, "translationX", -i5, ActionBarShadowController.ELEVATION_LOW);
                            if (this.mQQSTileHeightAnimator == null) {
                                this.mQQSTileHeightAnimator = new HeightExpansionAnimator(this, tileView2.getMeasuredHeight(), tileView.getMeasuredHeight());
                                this.mLastQQSTileHeight = tileView2.getMeasuredHeight();
                            }
                            this.mQQSTileHeightAnimator.addView(tileView2);
                            translateContent(tileView2.getIcon(), tileView.getIcon(), view, i5, i4, this.mTmpLoc1, builder7, builder5, builder6);
                            translateContent(tileView2.getLabelContainer(), tileView.getLabelContainer(), view, i5, i4, this.mTmpLoc1, builder7, builder5, builder6);
                            translateContent(tileView2.getSecondaryIcon(), tileView.getSecondaryIcon(), view, i5, i4, this.mTmpLoc1, builder7, builder5, builder6);
                            interpolator.addFloat(tileView2.getSecondaryLabel(), "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
                            builder8.addFloat(tileView2.getSecondaryLabel(), "alpha", ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
                            this.mAnimatedQsViews.add(tileView);
                            this.mAllViews.add(tileView2);
                            this.mAllViews.add(tileView2.getSecondaryLabel());
                        }
                    } else {
                        if (!isIconInAnimatedRow(i2)) {
                            getRelativePosition(this.mTmpLoc1, (SideLabelTileLayout) this.mQuickQsPanel.getTileLayout(), view);
                            this.mQQSTop = this.mTmpLoc1[1];
                            getRelativePosition(this.mTmpLoc2, tileView, view);
                            builder5.addFloat(tileView, "translationY", -(this.mTmpLoc2[1] - (this.mTmpLoc1[1] + sideLabelTileLayout.getPhantomTopPosition(i))), ActionBarShadowController.ELEVATION_LOW);
                            if (this.mOtherFirstPageTilesHeightAnimator == null) {
                                this.mOtherFirstPageTilesHeightAnimator = new HeightExpansionAnimator(this, this.mLastQQSTileHeight, tileView.getMeasuredHeight());
                            }
                            this.mOtherFirstPageTilesHeightAnimator.addView(tileView);
                            tileView.setClipChildren(true);
                            tileView.setClipToPadding(true);
                            builder4.addFloat(tileView.getSecondaryLabel(), "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
                            this.mAllViews.add(tileView.getSecondaryLabel());
                        }
                    }
                    this.mAllViews.add(tileView);
                    i2++;
                } else {
                    Log.e("QSAnimator", "tileView is null " + qSTile.getTileSpec());
                }
            }
            int i6 = this.mCurrentPage;
            builder3 = interpolator;
            str = "alpha";
            builder2 = builder8;
            builder = builder4;
            qSTileLayout = tileLayout;
            if (i6 != 0) {
                addNonFirstPageAnimators(i6);
                builder3 = interpolator;
                str = "alpha";
                builder2 = builder8;
                builder = builder4;
                qSTileLayout = tileLayout;
            }
        } else {
            builder = builder4;
            qSTileLayout = tileLayout;
            builder2 = builder8;
            str = "alpha";
            builder3 = interpolator;
        }
        animateBrightnessSlider();
        this.mFirstPageAnimator = builder.addFloat(qSTileLayout, str, ActionBarShadowController.ELEVATION_LOW, 1.0f).addFloat(builder3.build(), "position", ActionBarShadowController.ELEVATION_LOW, 1.0f).setListener(this).build();
        TouchAnimator.Builder startDelay = new TouchAnimator.Builder().setStartDelay(0.86f);
        if (!this.mQsPanelController.shouldUseHorizontalLayout() || (uniqueObjectHostView = this.mQsPanelController.mMediaHost.hostView) == null) {
            this.mQsPanelController.mMediaHost.hostView.setAlpha(1.0f);
        } else {
            startDelay.addFloat(uniqueObjectHostView, str, ActionBarShadowController.ELEVATION_LOW, 1.0f);
        }
        this.mAllPagesDelayedAnimator = startDelay.build();
        builder5.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
        builder6.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
        builder7.setInterpolator(this.mQSExpansionPathInterpolator.getXInterpolator());
        if (this.mOnFirstPage) {
            this.mQQSTranslationYAnimator = builder6.build();
        }
        this.mTranslationYAnimator = builder5.build();
        this.mTranslationXAnimator = builder7.build();
        HeightExpansionAnimator heightExpansionAnimator = this.mQQSTileHeightAnimator;
        if (heightExpansionAnimator != null) {
            heightExpansionAnimator.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
        }
        HeightExpansionAnimator heightExpansionAnimator2 = this.mOtherFirstPageTilesHeightAnimator;
        if (heightExpansionAnimator2 != null) {
            heightExpansionAnimator2.setInterpolator(this.mQSExpansionPathInterpolator.getYInterpolator());
        }
        this.mNonfirstPageAlphaAnimator = builder2.addFloat(this.mQuickQsPanel, str, 1.0f, ActionBarShadowController.ELEVATION_LOW).addFloat(qSTileLayout, str, ActionBarShadowController.ELEVATION_LOW, 1.0f).setListener(this.mNonFirstPageListener).setEndDelay(0.9f).build();
    }

    public final void updateQQSVisibility() {
        this.mQuickQsPanel.setVisibility((!this.mOnKeyguard || this.mShowCollapsedOnKeyguard) ? 0 : 4);
    }
}