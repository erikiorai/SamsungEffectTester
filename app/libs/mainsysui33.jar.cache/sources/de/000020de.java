package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Trace;
import android.util.IndentingPrintWriter;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.animation.ShadeInterpolation;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QS;
import com.android.systemui.plugins.qs.QSContainerController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.dagger.QSFragmentComponent;
import com.android.systemui.qs.footer.ui.binder.FooterActionsViewBinder;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.DisableFlagsLogger;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.util.LifecycleFragment;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFragment.class */
public class QSFragment extends LifecycleFragment implements QS, CommandQueue.Callbacks, StatusBarStateController.StateListener, Dumpable {
    public final KeyguardBypassController mBypassController;
    public QSContainerImpl mContainer;
    public long mDelay;
    public final DumpManager mDumpManager;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public QSFooter mFooter;
    public final FooterActionsController mFooterActionsController;
    public final FooterActionsViewModel.Factory mFooterActionsViewModelFactory;
    public QuickStatusBarHeader mHeader;
    public boolean mHeaderAnimating;
    public final QSTileHost mHost;
    public boolean mInSplitShade;
    public boolean mIsNotificationPanelFullWidth;
    public float mLastHeaderTranslation;
    public boolean mLastKeyguardAndExpanded;
    public float mLastPanelFraction;
    public int mLastViewHeight;
    public int mLayoutDirection;
    public boolean mListening;
    public final ListeningAndVisibilityLifecycleOwner mListeningAndVisibilityLifecycleOwner;
    public float mLockscreenToShadeProgress;
    public boolean mOverScrolling;
    public QS.HeightListener mPanelView;
    public QSAnimator mQSAnimator;
    public QSContainerImplController mQSContainerImplController;
    public QSCustomizerController mQSCustomizerController;
    public FooterActionsViewModel mQSFooterActionsViewModel;
    public QSPanelController mQSPanelController;
    public NonInterceptingScrollView mQSPanelScrollView;
    public QSSquishinessController mQSSquishinessController;
    public final MediaHost mQqsMediaHost;
    public final QSFragmentComponent.Factory mQsComponentFactory;
    public boolean mQsDisabled;
    public boolean mQsExpanded;
    public final QSFragmentDisableFlagsLogger mQsFragmentDisableFlagsLogger;
    public final MediaHost mQsMediaHost;
    public boolean mQsVisible;
    public QuickQSPanelController mQuickQSPanelController;
    public final RemoteInputQuickSettingsDisabler mRemoteInputQuickSettingsDisabler;
    public QS.ScrollListener mScrollListener;
    public boolean mShowCollapsedOnKeyguard;
    public boolean mStackScrollerOverscrolling;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public boolean mTransitioningToFullShade;
    public final Rect mQsBounds = new Rect();
    public float mLastQSExpansion = -1.0f;
    public float mSquishinessFraction = 1.0f;
    public int[] mLocationTemp = new int[2];
    public int mStatusBarState = -1;
    public int[] mTmpLocation = new int[2];
    public final ViewTreeObserver.OnPreDrawListener mStartHeaderSlidingIn = new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.systemui.qs.QSFragment.2
        {
            QSFragment.this = this;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            QSFragment.this.getView().getViewTreeObserver().removeOnPreDrawListener(this);
            QSFragment.this.getView().animate().translationY(ActionBarShadowController.ELEVATION_LOW).setStartDelay(QSFragment.this.mDelay).setDuration(448L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(QSFragment.this.mAnimateHeaderSlidingInListener).start();
            return true;
        }
    };
    public final Animator.AnimatorListener mAnimateHeaderSlidingInListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.3
        {
            QSFragment.this = this;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            QSFragment.this.mHeaderAnimating = false;
            QSFragment.this.updateQsState();
            QSFragment.this.getView().animate().setListener(null);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QSFragment$ListeningAndVisibilityLifecycleOwner.class */
    public class ListeningAndVisibilityLifecycleOwner implements LifecycleOwner {
        public final LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);
        public boolean mDestroyed = false;

        public ListeningAndVisibilityLifecycleOwner() {
            QSFragment.this = r6;
            updateState();
        }

        public void destroy() {
            this.mDestroyed = true;
            updateState();
        }

        @Override // androidx.lifecycle.LifecycleOwner
        public Lifecycle getLifecycle() {
            return this.mLifecycleRegistry;
        }

        public void updateState() {
            if (this.mDestroyed) {
                this.mLifecycleRegistry.setCurrentState(Lifecycle.State.DESTROYED);
            } else if (!QSFragment.this.mListening) {
                this.mLifecycleRegistry.setCurrentState(Lifecycle.State.CREATED);
            } else if (QSFragment.this.mQsVisible) {
                this.mLifecycleRegistry.setCurrentState(Lifecycle.State.RESUMED);
            } else {
                this.mLifecycleRegistry.setCurrentState(Lifecycle.State.STARTED);
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda1.onScrollChange(android.view.View, int, int, int, int):void] */
    /* renamed from: $r8$lambda$3zvw7bVBDhwh3GV3GSYsq-B3ULI */
    public static /* synthetic */ void m3758$r8$lambda$3zvw7bVBDhwh3GV3GSYsqB3ULI(QSFragment qSFragment, View view, int i, int i2, int i3, int i4) {
        qSFragment.lambda$onViewCreated$1(view, i, i2, i3, i4);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda0.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$SU_vwOjIqGCZqnW_U4x6TV7pczk(QSFragment qSFragment, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        qSFragment.lambda$onViewCreated$0(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda2.onLayoutChange(android.view.View, int, int, int, int, int, int, int, int):void] */
    public static /* synthetic */ void $r8$lambda$lTvt7moPVBPdCeAzjAAkQwspBis(QSFragment qSFragment, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        qSFragment.lambda$onViewCreated$2(view, i, i2, i3, i4, i5, i6, i7, i8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$pUchIneYDy4TM4RTrUBsxshgISw(QSFragment qSFragment) {
        qSFragment.lambda$onViewCreated$3();
    }

    public QSFragment(RemoteInputQuickSettingsDisabler remoteInputQuickSettingsDisabler, QSTileHost qSTileHost, SysuiStatusBarStateController sysuiStatusBarStateController, CommandQueue commandQueue, MediaHost mediaHost, MediaHost mediaHost2, KeyguardBypassController keyguardBypassController, QSFragmentComponent.Factory factory, QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger, FalsingManager falsingManager, DumpManager dumpManager, FeatureFlags featureFlags, FooterActionsController footerActionsController, FooterActionsViewModel.Factory factory2) {
        this.mRemoteInputQuickSettingsDisabler = remoteInputQuickSettingsDisabler;
        this.mQsMediaHost = mediaHost;
        this.mQqsMediaHost = mediaHost2;
        this.mQsComponentFactory = factory;
        this.mQsFragmentDisableFlagsLogger = qSFragmentDisableFlagsLogger;
        commandQueue.observe(getLifecycle(), this);
        this.mHost = qSTileHost;
        this.mFalsingManager = falsingManager;
        this.mBypassController = keyguardBypassController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDumpManager = dumpManager;
        this.mFeatureFlags = featureFlags;
        this.mFooterActionsController = footerActionsController;
        this.mFooterActionsViewModelFactory = factory2;
        this.mListeningAndVisibilityLifecycleOwner = new ListeningAndVisibilityLifecycleOwner();
    }

    public /* synthetic */ void lambda$onViewCreated$0(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        updateQsBounds();
    }

    public /* synthetic */ void lambda$onViewCreated$1(View view, int i, int i2, int i3, int i4) {
        this.mQSAnimator.requestAnimatorUpdate();
        this.mHeader.setExpandedScrollAmount(i2);
        QS.ScrollListener scrollListener = this.mScrollListener;
        if (scrollListener != null) {
            scrollListener.onQsPanelScrollChanged(i2);
        }
    }

    public /* synthetic */ void lambda$onViewCreated$2(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (i6 - i8 != i2 - i4) {
            setQsExpansion(this.mLastQSExpansion, this.mLastPanelFraction, this.mLastHeaderTranslation, this.mSquishinessFraction);
        }
    }

    public /* synthetic */ void lambda$onViewCreated$3() {
        this.mQSPanelController.getMediaHost().getHostView().setAlpha(1.0f);
        this.mQSAnimator.requestAnimatorUpdate();
    }

    public static String visibilityToString(int i) {
        return i == 0 ? "VISIBLE" : i == 4 ? "INVISIBLE" : "GONE";
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public void animateHeaderSlidingOut() {
        if (getView().getY() == (-this.mHeader.getHeight())) {
            return;
        }
        this.mHeaderAnimating = true;
        getView().animate().y(-this.mHeader.getHeight()).setStartDelay(0L).setDuration(360L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSFragment.1
            {
                QSFragment.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (QSFragment.this.getView() != null) {
                    QSFragment.this.getView().animate().setListener(null);
                }
                QSFragment.this.mHeaderAnimating = false;
                QSFragment.this.updateQsState();
            }
        }).start();
    }

    public final float calculateAlphaProgress(float f) {
        if (this.mIsNotificationPanelFullWidth) {
            return 1.0f;
        }
        return this.mInSplitShade ? (this.mTransitioningToFullShade || this.mStatusBarStateController.getCurrentOrUpcomingState() == 1) ? this.mLockscreenToShadeProgress : f : this.mTransitioningToFullShade ? this.mLockscreenToShadeProgress : f;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void closeCustomizer() {
        this.mQSCustomizerController.hide();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void closeDetail() {
        this.mQSPanelController.closeDetail();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void disable(int i, int i2, int i3, boolean z) {
        if (i != getContext().getDisplayId()) {
            return;
        }
        int adjustDisableFlags = this.mRemoteInputQuickSettingsDisabler.adjustDisableFlags(i3);
        this.mQsFragmentDisableFlagsLogger.logDisableFlagChange(new DisableFlagsLogger.DisableState(i2, i3), new DisableFlagsLogger.DisableState(i2, adjustDisableFlags));
        boolean z2 = (adjustDisableFlags & 1) != 0;
        if (z2 == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = z2;
        this.mContainer.disable(i2, adjustDisableFlags, z);
        this.mHeader.disable(i2, adjustDisableFlags, z);
        this.mFooter.disable(i2, adjustDisableFlags, z);
        updateQsState();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter, "  ");
        indentingPrintWriter.println("QSFragment:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("mQsBounds: " + this.mQsBounds);
        indentingPrintWriter.println("mQsExpanded: " + this.mQsExpanded);
        indentingPrintWriter.println("mHeaderAnimating: " + this.mHeaderAnimating);
        indentingPrintWriter.println("mStackScrollerOverscrolling: " + this.mStackScrollerOverscrolling);
        indentingPrintWriter.println("mListening: " + this.mListening);
        indentingPrintWriter.println("mQsVisible: " + this.mQsVisible);
        indentingPrintWriter.println("mLayoutDirection: " + this.mLayoutDirection);
        indentingPrintWriter.println("mLastQSExpansion: " + this.mLastQSExpansion);
        indentingPrintWriter.println("mLastPanelFraction: " + this.mLastPanelFraction);
        indentingPrintWriter.println("mSquishinessFraction: " + this.mSquishinessFraction);
        indentingPrintWriter.println("mQsDisabled: " + this.mQsDisabled);
        indentingPrintWriter.println("mTemp: " + Arrays.toString(this.mLocationTemp));
        indentingPrintWriter.println("mShowCollapsedOnKeyguard: " + this.mShowCollapsedOnKeyguard);
        indentingPrintWriter.println("mLastKeyguardAndExpanded: " + this.mLastKeyguardAndExpanded);
        indentingPrintWriter.println("mStatusBarState: " + StatusBarState.toString(this.mStatusBarState));
        indentingPrintWriter.println("mTmpLocation: " + Arrays.toString(this.mTmpLocation));
        indentingPrintWriter.println("mLastViewHeight: " + this.mLastViewHeight);
        indentingPrintWriter.println("mLastHeaderTranslation: " + this.mLastHeaderTranslation);
        indentingPrintWriter.println("mInSplitShade: " + this.mInSplitShade);
        indentingPrintWriter.println("mTransitioningToFullShade: " + this.mTransitioningToFullShade);
        indentingPrintWriter.println("mLockscreenToShadeProgress: " + this.mLockscreenToShadeProgress);
        indentingPrintWriter.println("mOverScrolling: " + this.mOverScrolling);
        indentingPrintWriter.println("isCustomizing: " + this.mQSCustomizerController.isCustomizing());
        View view = getView();
        if (view != null) {
            indentingPrintWriter.println("top: " + view.getTop());
            indentingPrintWriter.println("y: " + view.getY());
            indentingPrintWriter.println("translationY: " + view.getTranslationY());
            indentingPrintWriter.println("alpha: " + view.getAlpha());
            indentingPrintWriter.println("height: " + view.getHeight());
            indentingPrintWriter.println("measuredHeight: " + view.getMeasuredHeight());
            indentingPrintWriter.println("clipBounds: " + view.getClipBounds());
        } else {
            indentingPrintWriter.println("getView(): null");
        }
        QuickStatusBarHeader quickStatusBarHeader = this.mHeader;
        if (quickStatusBarHeader == null) {
            indentingPrintWriter.println("mHeader: null");
            return;
        }
        indentingPrintWriter.println("headerHeight: " + quickStatusBarHeader.getHeight());
        indentingPrintWriter.println("Header visibility: " + visibilityToString(quickStatusBarHeader.getVisibility()));
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public int getDesiredHeight() {
        return this.mQSCustomizerController.isCustomizing() ? getView().getHeight() : getView().getMeasuredHeight();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public View getHeader() {
        return this.mHeader;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getHeightDiff() {
        return (this.mQSPanelScrollView.getBottom() - this.mHeader.getBottom()) + this.mHeader.getPaddingBottom();
    }

    public ListeningAndVisibilityLifecycleOwner getListeningAndVisibilityLifecycleOwner() {
        return this.mListeningAndVisibilityLifecycleOwner;
    }

    public QSPanelController getQSPanelController() {
        return this.mQSPanelController;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public int getQsMinExpansionHeight() {
        return this.mInSplitShade ? getQsMinExpansionHeightForSplitShade() : this.mHeader.getHeight();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public final int getQsMinExpansionHeightForSplitShade() {
        getView().getLocationOnScreen(this.mLocationTemp);
        return ((int) (this.mLocationTemp[1] - getView().getTranslationY())) + getView().getHeight();
    }

    public final boolean headerWillBeAnimating() {
        boolean z = true;
        if (this.mStatusBarState != 1 || !this.mShowCollapsedOnKeyguard || isKeyguardState()) {
            z = false;
        }
        return z;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public void hideImmediately() {
        getView().animate().cancel();
        getView().setY(-getQsMinExpansionHeight());
    }

    public final float interpolateAlphaAnimationProgress(float f) {
        return this.mQSPanelController.isBouncerInTransit() ? BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f) : isKeyguardState() ? f : ShadeInterpolation.getContentAlpha(f);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isCustomizing() {
        return this.mQSCustomizerController.isCustomizing();
    }

    public boolean isExpanded() {
        return this.mQsExpanded;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isFullyCollapsed() {
        float f = this.mLastQSExpansion;
        return f == ActionBarShadowController.ELEVATION_LOW || f == -1.0f;
    }

    public final boolean isKeyguardState() {
        boolean z = true;
        if (this.mStatusBarStateController.getCurrentOrUpcomingState() != 1) {
            z = false;
        }
        return z;
    }

    public boolean isListening() {
        return this.mListening;
    }

    public boolean isQsVisible() {
        return this.mQsVisible;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public boolean isShowingDetail() {
        return this.mQSCustomizerController.isCustomizing();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void notifyCustomizeChanged() {
        this.mContainer.updateExpansion();
        boolean isCustomizing = isCustomizing();
        this.mQSPanelScrollView.setVisibility(!isCustomizing ? 0 : 4);
        this.mFooter.setVisibility(!isCustomizing ? 0 : 4);
        this.mQSFooterActionsViewModel.onVisibilityChangeRequested(!isCustomizing);
        this.mHeader.setVisibility(!isCustomizing ? 0 : 4);
        this.mPanelView.onQsHeightChanged();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void onConfigurationChanged(Configuration configuration) {
        super/*android.app.Fragment*/.onConfigurationChanged(configuration);
        setEditLocation(getView());
        if (configuration.getLayoutDirection() != this.mLayoutDirection) {
            this.mLayoutDirection = configuration.getLayoutDirection();
            QSAnimator qSAnimator = this.mQSAnimator;
            if (qSAnimator != null) {
                qSAnimator.onRtlChanged();
            }
        }
        updateQsState();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mDumpManager.registerDumpable(getClass().getName(), this);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        try {
            Trace.beginSection("QSFragment#onCreateView");
            View inflate = layoutInflater.cloneInContext(new ContextThemeWrapper(getContext(), R$style.Theme_SystemUI_QuickSettings)).inflate(R$layout.qs_panel, viewGroup, false);
            Trace.endSection();
            return inflate;
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mStatusBarStateController.removeCallback(this);
        if (this.mListening) {
            setListening(false);
        }
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        if (qSCustomizerController != null) {
            qSCustomizerController.setQs(null);
        }
        this.mScrollListener = null;
        QSContainerImpl qSContainerImpl = this.mContainer;
        if (qSContainerImpl != null) {
            this.mDumpManager.unregisterDumpable(qSContainerImpl.getClass().getName());
        }
        this.mDumpManager.unregisterDumpable(getClass().getName());
        this.mListeningAndVisibilityLifecycleOwner.destroy();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void onSaveInstanceState(Bundle bundle) {
        super/*android.app.Fragment*/.onSaveInstanceState(bundle);
        bundle.putBoolean("expanded", this.mQsExpanded);
        bundle.putBoolean("listening", this.mListening);
        bundle.putBoolean("visible", this.mQsVisible);
        QSCustomizerController qSCustomizerController = this.mQSCustomizerController;
        if (qSCustomizerController != null) {
            qSCustomizerController.saveInstanceState(bundle);
        }
        if (this.mQsExpanded) {
            this.mQSPanelController.getTileLayout().saveInstanceState(bundle);
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onStateChanged(int i) {
        if (i == this.mStatusBarState) {
            return;
        }
        this.mStatusBarState = i;
        boolean z = true;
        if (i != 1) {
            z = false;
        }
        setKeyguardShowing(z);
        updateShowCollapsedOnKeyguard();
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onUpcomingStateChanged(int i) {
        if (i == 1) {
            onStateChanged(i);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void onViewCreated(View view, Bundle bundle) {
        QSFragmentComponent create = this.mQsComponentFactory.create(this);
        this.mQSPanelController = create.getQSPanelController();
        this.mQuickQSPanelController = create.getQuickQSPanelController();
        this.mQSPanelController.init();
        this.mQuickQSPanelController.init();
        this.mQSFooterActionsViewModel = this.mFooterActionsViewModelFactory.create(this);
        FooterActionsViewBinder.bind((LinearLayout) view.findViewById(R$id.qs_footer_actions), this.mQSFooterActionsViewModel, this.mListeningAndVisibilityLifecycleOwner);
        this.mFooterActionsController.init();
        NonInterceptingScrollView nonInterceptingScrollView = (NonInterceptingScrollView) view.findViewById(R$id.expanded_qs_scroll_view);
        this.mQSPanelScrollView = nonInterceptingScrollView;
        nonInterceptingScrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view2, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                QSFragment.$r8$lambda$SU_vwOjIqGCZqnW_U4x6TV7pczk(QSFragment.this, view2, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        this.mQSPanelScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnScrollChangeListener
            public final void onScrollChange(View view2, int i, int i2, int i3, int i4) {
                QSFragment.m3758$r8$lambda$3zvw7bVBDhwh3GV3GSYsqB3ULI(QSFragment.this, view2, i, i2, i3, i4);
            }
        });
        this.mHeader = (QuickStatusBarHeader) view.findViewById(R$id.header);
        this.mFooter = create.getQSFooter();
        QSContainerImplController qSContainerImplController = create.getQSContainerImplController();
        this.mQSContainerImplController = qSContainerImplController;
        qSContainerImplController.init();
        QSContainerImpl view2 = this.mQSContainerImplController.getView();
        this.mContainer = view2;
        this.mDumpManager.registerDumpable(view2.getClass().getName(), this.mContainer);
        this.mQSAnimator = create.getQSAnimator();
        this.mQSSquishinessController = create.getQSSquishinessController();
        QSCustomizerController qSCustomizerController = create.getQSCustomizerController();
        this.mQSCustomizerController = qSCustomizerController;
        qSCustomizerController.init();
        this.mQSCustomizerController.setQs(this);
        if (bundle != null) {
            setQsVisible(bundle.getBoolean("visible"));
            setExpanded(bundle.getBoolean("expanded"));
            setListening(bundle.getBoolean("listening"));
            setEditLocation(view);
            this.mQSCustomizerController.restoreInstanceState(bundle);
            if (this.mQsExpanded) {
                this.mQSPanelController.getTileLayout().restoreInstanceState(bundle);
            }
        }
        this.mStatusBarStateController.addCallback(this);
        onStateChanged(this.mStatusBarStateController.getState());
        view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                QSFragment.$r8$lambda$lTvt7moPVBPdCeAzjAAkQwspBis(QSFragment.this, view3, i, i2, i3, i4, i5, i6, i7, i8);
            }
        });
        this.mQSPanelController.setUsingHorizontalLayoutChangeListener(new Runnable() { // from class: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                QSFragment.$r8$lambda$pUchIneYDy4TM4RTrUBsxshgISw(QSFragment.this);
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setAlphaAnimationProgress(float f) {
        View view = getView();
        int i = (f > ActionBarShadowController.ELEVATION_LOW ? 1 : (f == ActionBarShadowController.ELEVATION_LOW ? 0 : -1));
        if (i == 0 && view.getVisibility() != 4) {
            view.setVisibility(4);
        } else if (i > 0 && view.getVisibility() != 0) {
            view.setVisibility(0);
        }
        view.setAlpha(interpolateAlphaAnimationProgress(f));
    }

    public void setBrightnessMirrorController(BrightnessMirrorController brightnessMirrorController) {
        this.mQSPanelController.setBrightnessMirror(brightnessMirrorController);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setCollapseExpandAction(Runnable runnable) {
        this.mQSPanelController.setCollapseExpandAction(runnable);
        this.mQuickQSPanelController.setCollapseExpandAction(runnable);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setCollapsedMediaVisibilityChangedListener(Consumer<Boolean> consumer) {
        this.mQuickQSPanelController.setMediaVisibilityChangedListener(consumer);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setContainerController(QSContainerController qSContainerController) {
        this.mQSCustomizerController.setContainerController(qSContainerController);
    }

    public final void setEditLocation(View view) {
        View findViewById = view.findViewById(16908291);
        int[] locationOnScreen = findViewById.getLocationOnScreen();
        this.mQSCustomizerController.setEditLocation(locationOnScreen[0] + (findViewById.getWidth() / 2), locationOnScreen[1] + (findViewById.getHeight() / 2));
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setExpanded(boolean z) {
        this.mQsExpanded = z;
        if (this.mInSplitShade && z) {
            setListening(true);
        } else {
            updateQsPanelControllerListening();
        }
        updateQsState();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public void setFancyClipping(int i, int i2, int i3, boolean z) {
        if (getView() instanceof QSContainerImpl) {
            ((QSContainerImpl) getView()).setFancyClipping(i, i2, i3, z);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHasNotifications(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderClickable(boolean z) {
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeaderListening(boolean z) {
        this.mQSContainerImplController.setListening(z);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setHeightOverride(int i) {
        this.mContainer.setHeightOverride(i);
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setInSplitShade(boolean z) {
        this.mInSplitShade = z;
        updateShowCollapsedOnKeyguard();
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setIsNotificationPanelFullWidth(boolean z) {
        this.mIsNotificationPanelFullWidth = z;
    }

    public final void setKeyguardShowing(boolean z) {
        this.mLastQSExpansion = -1.0f;
        QSAnimator qSAnimator = this.mQSAnimator;
        if (qSAnimator != null) {
            qSAnimator.setOnKeyguard(z);
        }
        this.mFooter.setKeyguardShowing(z);
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setListening(boolean z) {
        this.mListening = z;
        this.mQSContainerImplController.setListening(z && this.mQsVisible);
        this.mListeningAndVisibilityLifecycleOwner.updateState();
        updateQsPanelControllerListening();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public void setOverScrollAmount(int i) {
        this.mOverScrolling = i != 0;
        View view = getView();
        if (view != null) {
            view.setTranslationY(i);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setOverscrolling(boolean z) {
        this.mStackScrollerOverscrolling = z;
        updateQsState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setPanelView(QS.HeightListener heightListener) {
        this.mPanelView = heightListener;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.plugins.qs.QS
    public void setQsExpansion(float f, float f2, float f3, float f4) {
        float f5 = this.mTransitioningToFullShade ? 0.0f : f3;
        float calculateAlphaProgress = calculateAlphaProgress(f2);
        setAlphaAnimationProgress(calculateAlphaProgress);
        this.mContainer.setExpansion(f);
        float f6 = (this.mInSplitShade ? 1.0f : 0.1f) * (f - 1.0f);
        boolean isKeyguardState = isKeyguardState();
        boolean z = isKeyguardState && !this.mShowCollapsedOnKeyguard;
        if (!this.mHeaderAnimating && !headerWillBeAnimating() && !this.mOverScrolling) {
            getView().setTranslationY(z ? this.mHeader.getHeight() * f6 : f5);
        }
        int height = getView().getHeight();
        if (f == this.mLastQSExpansion && this.mLastKeyguardAndExpanded == z && this.mLastViewHeight == height && this.mLastHeaderTranslation == f5 && this.mSquishinessFraction == f4) {
            return;
        }
        this.mLastHeaderTranslation = f5;
        this.mLastPanelFraction = f2;
        this.mSquishinessFraction = f4;
        this.mLastQSExpansion = f;
        this.mLastKeyguardAndExpanded = z;
        this.mLastViewHeight = height;
        boolean z2 = f == 1.0f;
        boolean z3 = f == ActionBarShadowController.ELEVATION_LOW;
        float heightDiff = f6 * getHeightDiff();
        this.mHeader.setExpansion(z, f, heightDiff);
        if (f < 1.0f && f > 0.99d && this.mQuickQSPanelController.switchTileLayout(false)) {
            this.mHeader.updateResources();
        }
        this.mQSPanelController.setIsOnKeyguard(isKeyguardState);
        this.mFooter.setExpansion(z ? 1.0f : f);
        this.mQSFooterActionsViewModel.onQuickSettingsExpansionChanged(z ? 1.0f : this.mInSplitShade ? calculateAlphaProgress : f, this.mInSplitShade);
        this.mQSPanelController.setRevealExpansion(f);
        this.mQSPanelController.getTileLayout().setExpansion(f, f3);
        this.mQuickQSPanelController.getTileLayout().setExpansion(f, f3);
        float f7 = 0.0f;
        if (isKeyguardState) {
            f7 = 0.0f;
            if (!this.mShowCollapsedOnKeyguard) {
                f7 = heightDiff;
            }
        }
        this.mQSPanelScrollView.setTranslationY(f7);
        if (z3) {
            this.mQSPanelScrollView.setScrollY(0);
        }
        if (!z2) {
            this.mQsBounds.top = (int) (-this.mQSPanelScrollView.getTranslationY());
            this.mQsBounds.right = this.mQSPanelScrollView.getWidth();
            this.mQsBounds.bottom = this.mQSPanelScrollView.getHeight();
        }
        updateQsBounds();
        QSSquishinessController qSSquishinessController = this.mQSSquishinessController;
        if (qSSquishinessController != null) {
            qSSquishinessController.setSquishiness(this.mSquishinessFraction);
        }
        QSAnimator qSAnimator = this.mQSAnimator;
        if (qSAnimator != null) {
            qSAnimator.setPosition(f);
        }
        if (!this.mInSplitShade || this.mStatusBarStateController.getState() == 1 || this.mStatusBarStateController.getState() == 2) {
            this.mQsMediaHost.setSquishFraction(1.0f);
        } else {
            this.mQsMediaHost.setSquishFraction(this.mSquishinessFraction);
        }
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setQsVisible(boolean z) {
        this.mQsVisible = z;
        setListening(this.mListening);
        this.mListeningAndVisibilityLifecycleOwner.updateState();
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setScrollListener(QS.ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    @Override // com.android.systemui.plugins.qs.QS
    public void setTransitionToFullShadeProgress(boolean z, float f, float f2) {
        if (z != this.mTransitioningToFullShade) {
            this.mTransitioningToFullShade = z;
            updateShowCollapsedOnKeyguard();
        }
        this.mLockscreenToShadeProgress = f;
        float f3 = this.mLastQSExpansion;
        float f4 = this.mLastPanelFraction;
        float f5 = this.mLastHeaderTranslation;
        if (!z) {
            f2 = this.mSquishinessFraction;
        }
        setQsExpansion(f3, f4, f5, f2);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.qs.QSFragment */
    /* JADX WARN: Multi-variable type inference failed */
    public void updateQsBounds() {
        if (this.mLastQSExpansion == 1.0f) {
            int dimensionPixelSize = getResources().getDimensionPixelSize(R$dimen.qs_tiles_page_horizontal_margin) * 2;
            this.mQsBounds.set(-dimensionPixelSize, 0, this.mQSPanelScrollView.getWidth() + dimensionPixelSize, this.mQSPanelScrollView.getHeight());
        }
        this.mQSPanelScrollView.setClipBounds(this.mQsBounds);
        this.mQSPanelScrollView.getLocationOnScreen(this.mLocationTemp);
        int[] iArr = this.mLocationTemp;
        int i = iArr[0];
        int i2 = iArr[1];
        this.mQsMediaHost.getCurrentClipping().set(i, i2, getView().getMeasuredWidth() + i, (this.mQSPanelScrollView.getMeasuredHeight() + i2) - this.mQSPanelController.getPaddingBottom());
    }

    public final void updateQsPanelControllerListening() {
        this.mQSPanelController.setListening(this.mListening && this.mQsVisible, this.mQsExpanded);
    }

    /* JADX WARN: Code restructure failed: missing block: B:135:0x0117, code lost:
        if (r4.mShowCollapsedOnKeyguard != false) goto L56;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateQsState() {
        boolean z;
        boolean z2 = this.mQsExpanded;
        boolean z3 = z2 || this.mStackScrollerOverscrolling || this.mHeaderAnimating;
        this.mQSPanelController.setExpanded(z2);
        boolean isKeyguardState = isKeyguardState();
        this.mHeader.setVisibility((this.mQsExpanded || !isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) ? 0 : 4);
        this.mHeader.setExpanded(!(!isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard) || (this.mQsExpanded && !this.mStackScrollerOverscrolling), this.mQuickQSPanelController);
        boolean z4 = !this.mQsDisabled && z3;
        boolean z5 = z4 && (this.mQsExpanded || !isKeyguardState || this.mHeaderAnimating || this.mShowCollapsedOnKeyguard);
        this.mFooter.setVisibility(z5 ? 0 : 4);
        this.mQSFooterActionsViewModel.onVisibilityChangeRequested(z5);
        QSFooter qSFooter = this.mFooter;
        if (isKeyguardState && !this.mHeaderAnimating) {
            z = true;
        }
        z = this.mQsExpanded && !this.mStackScrollerOverscrolling;
        qSFooter.setExpanded(z);
        this.mQSPanelController.setVisibility(z4 ? 0 : 4);
    }

    public final void updateShowCollapsedOnKeyguard() {
        boolean z = this.mBypassController.getBypassEnabled() || (this.mTransitioningToFullShade && !this.mInSplitShade);
        if (z != this.mShowCollapsedOnKeyguard) {
            this.mShowCollapsedOnKeyguard = z;
            updateQsState();
            QSAnimator qSAnimator = this.mQSAnimator;
            if (qSAnimator != null) {
                qSAnimator.setShowCollapsedOnKeyguard(z);
            }
            if (z || !isKeyguardState()) {
                return;
            }
            setQsExpansion(this.mLastQSExpansion, this.mLastPanelFraction, ActionBarShadowController.ELEVATION_LOW, this.mSquishinessFraction);
        }
    }
}