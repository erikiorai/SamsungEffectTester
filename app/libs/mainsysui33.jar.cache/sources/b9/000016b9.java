package com.android.systemui.dreams;

import android.content.res.Resources;
import android.os.Handler;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Arrays;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayContainerViewController.class */
public class DreamOverlayContainerViewController extends ViewController<DreamOverlayContainerView> {
    public final BlurUtils mBlurUtils;
    public boolean mBouncerAnimating;
    public final KeyguardBouncer.PrimaryBouncerExpansionCallback mBouncerExpansionCallback;
    public final long mBurnInProtectionUpdateInterval;
    public final ComplicationHostViewController mComplicationHostViewController;
    public final DreamOverlayAnimationsController mDreamOverlayAnimationsController;
    public final ViewGroup mDreamOverlayContentView;
    public final int mDreamOverlayMaxTranslationY;
    public final Handler mHandler;
    public long mJitterStartTimeMillis;
    public final int mMaxBurnInOffset;
    public final long mMillisUntilFullJitter;
    public final PrimaryBouncerCallbackInteractor mPrimaryBouncerCallbackInteractor;
    public final DreamOverlayStateController mStateController;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final DreamOverlayStatusBarViewController mStatusBarViewController;

    public DreamOverlayContainerViewController(DreamOverlayContainerView dreamOverlayContainerView, ComplicationHostViewController complicationHostViewController, ViewGroup viewGroup, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, BlurUtils blurUtils, Handler handler, Resources resources, int i, long j, long j2, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, DreamOverlayAnimationsController dreamOverlayAnimationsController, DreamOverlayStateController dreamOverlayStateController) {
        super(dreamOverlayContainerView);
        this.mBouncerExpansionCallback = new KeyguardBouncer.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController.1
            {
                DreamOverlayContainerViewController.this = this;
            }

            public void onExpansionChanged(float f) {
                if (DreamOverlayContainerViewController.this.mBouncerAnimating) {
                    DreamOverlayContainerViewController.this.updateTransitionState(f);
                }
            }

            public void onFullyHidden() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = false;
            }

            public void onFullyShown() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = false;
            }

            public void onStartingToHide() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            public void onStartingToShow() {
                DreamOverlayContainerViewController.this.mBouncerAnimating = true;
            }

            public void onVisibilityChanged(boolean z) {
                if (z) {
                    return;
                }
                DreamOverlayContainerViewController.this.updateTransitionState(1.0f);
            }
        };
        this.mDreamOverlayContentView = viewGroup;
        this.mStatusBarViewController = dreamOverlayStatusBarViewController;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mBlurUtils = blurUtils;
        this.mDreamOverlayAnimationsController = dreamOverlayAnimationsController;
        this.mStateController = dreamOverlayStateController;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mDreamOverlayMaxTranslationY = resources.getDimensionPixelSize(R$dimen.dream_overlay_y_offset);
        viewGroup.addView(complicationHostViewController.getView(), new ViewGroup.LayoutParams(-1, -1));
        this.mHandler = handler;
        this.mMaxBurnInOffset = i;
        this.mBurnInProtectionUpdateInterval = j;
        this.mMillisUntilFullJitter = j2;
        this.mPrimaryBouncerCallbackInteractor = primaryBouncerCallbackInteractor;
    }

    public static float getAlpha(int i, float f) {
        return Interpolators.LINEAR_OUT_SLOW_IN.getInterpolation(i == 1 ? BouncerPanelExpansionCalculator.getDreamAlphaScaledExpansion(f) : BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f + 0.03f));
    }

    public static /* synthetic */ void lambda$updateTransitionState$0(float f, float f2, View view) {
        view.setAlpha(f);
        view.setTranslationY(f2);
    }

    public View getContainerView() {
        return ((ViewController) this).mView;
    }

    public final float getTranslationY(int i, float f) {
        return MathUtils.lerp(-this.mDreamOverlayMaxTranslationY, 0, Interpolators.LINEAR_OUT_SLOW_IN.getInterpolation(i == 1 ? BouncerPanelExpansionCalculator.getDreamYPositionScaledExpansion(f) : BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f + 0.03f)));
    }

    public void onInit() {
        this.mStatusBarViewController.init();
        this.mComplicationHostViewController.init();
        this.mDreamOverlayAnimationsController.init(((ViewController) this).mView);
    }

    public void onViewAttached() {
        this.mJitterStartTimeMillis = System.currentTimeMillis();
        this.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this), this.mBurnInProtectionUpdateInterval);
        KeyguardBouncer primaryBouncer = this.mStatusBarKeyguardViewManager.getPrimaryBouncer();
        if (primaryBouncer != null) {
            primaryBouncer.addBouncerExpansionCallback(this.mBouncerExpansionCallback);
        }
        this.mPrimaryBouncerCallbackInteractor.addBouncerExpansionCallback(this.mBouncerExpansionCallback);
        if (this.mStateController.isLowLightActive()) {
            return;
        }
        this.mDreamOverlayAnimationsController.startEntryAnimations();
    }

    public void onViewDetached() {
        this.mHandler.removeCallbacks(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this));
        KeyguardBouncer primaryBouncer = this.mStatusBarKeyguardViewManager.getPrimaryBouncer();
        if (primaryBouncer != null) {
            primaryBouncer.removeBouncerExpansionCallback(this.mBouncerExpansionCallback);
        }
        this.mPrimaryBouncerCallbackInteractor.removeBouncerExpansionCallback(this.mBouncerExpansionCallback);
        this.mDreamOverlayAnimationsController.cancelAnimations();
    }

    public final void updateBurnInOffsets() {
        int i;
        long currentTimeMillis = System.currentTimeMillis() - this.mJitterStartTimeMillis;
        long j = this.mMillisUntilFullJitter;
        if (currentTimeMillis < j) {
            i = Math.round(MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, this.mMaxBurnInOffset, ((float) currentTimeMillis) / ((float) j)));
        } else {
            i = this.mMaxBurnInOffset;
        }
        int i2 = i / 2;
        int burnInOffset = BurnInHelperKt.getBurnInOffset(i, true);
        int burnInOffset2 = BurnInHelperKt.getBurnInOffset(i, false);
        ((DreamOverlayContainerView) ((ViewController) this).mView).setTranslationX(burnInOffset - i2);
        ((DreamOverlayContainerView) ((ViewController) this).mView).setTranslationY(burnInOffset2 - i2);
        this.mHandler.postDelayed(new DreamOverlayContainerViewController$$ExternalSyntheticLambda0(this), this.mBurnInProtectionUpdateInterval);
    }

    public final void updateTransitionState(float f) {
        for (Integer num : Arrays.asList(1, 2)) {
            int intValue = num.intValue();
            final float alpha = getAlpha(intValue, f);
            final float translationY = getTranslationY(intValue, f);
            this.mComplicationHostViewController.getViewsAtPosition(intValue).forEach(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayContainerViewController$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DreamOverlayContainerViewController.lambda$updateTransitionState$0(alpha, translationY, (View) obj);
                }
            });
        }
        this.mBlurUtils.applyBlur(((DreamOverlayContainerView) ((ViewController) this).mView).getViewRootImpl(), (int) this.mBlurUtils.blurRadiusOfRatio(1.0f - BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(f)), false);
    }

    public void wakeUp(Runnable runnable, DelayableExecutor delayableExecutor) {
        this.mDreamOverlayAnimationsController.wakeUp(runnable, delayableExecutor);
    }
}