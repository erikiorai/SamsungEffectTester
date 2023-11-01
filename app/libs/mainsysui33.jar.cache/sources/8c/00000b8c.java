package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.ClockController;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardClockSwitch.class */
public class KeyguardClockSwitch extends RelativeLayout {
    public boolean mAnimateOnLayout;
    public boolean mChildrenAreLaidOut;
    public ClockController mClock;
    public AnimatorSet mClockInAnim;
    public AnimatorSet mClockOutAnim;
    public int mClockSwitchYAmount;
    public Integer mDisplayedClockSize;
    public FrameLayout mLargeClockFrame;
    public FrameLayout mSmallClockFrame;
    public int mSmartspaceTopOffset;
    public View mStatusArea;
    public ObjectAnimator mStatusAreaAnim;

    public KeyguardClockSwitch(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisplayedClockSize = null;
        this.mClockInAnim = null;
        this.mClockOutAnim = null;
        this.mStatusAreaAnim = null;
        this.mChildrenAreLaidOut = false;
        this.mAnimateOnLayout = true;
    }

    public static Rect getLargeClockRegion(ViewGroup viewGroup) {
        int dimensionPixelSize = viewGroup.getResources().getDimensionPixelSize(R$dimen.keyguard_large_clock_top_margin);
        int dimensionPixelSize2 = viewGroup.getResources().getDimensionPixelSize(R$dimen.large_clock_text_size) * 2;
        int height = ((viewGroup.getHeight() / 2) - (dimensionPixelSize2 / 2)) + (dimensionPixelSize / 2);
        return new Rect(viewGroup.getLeft(), height, viewGroup.getRight(), dimensionPixelSize2 + height);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onLayout$1() {
        updateClockViews(this.mDisplayedClockSize.intValue() == 0, this.mAnimateOnLayout);
    }

    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("KeyguardClockSwitch:");
        printWriter.println("  mSmallClockFrame: " + this.mSmallClockFrame);
        printWriter.println("  mLargeClockFrame: " + this.mLargeClockFrame);
        printWriter.println("  mStatusArea: " + this.mStatusArea);
        printWriter.println("  mDisplayedClockSize: " + this.mDisplayedClockSize);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x003c, code lost:
        if (com.android.systemui.shared.recents.utilities.Utilities.isTablet(((android.widget.RelativeLayout) r4).mContext) != false) goto L12;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Integer num = this.mDisplayedClockSize;
        if (num != null) {
            boolean z = configuration.orientation == 2;
            boolean z2 = false;
            if (num.intValue() == 0) {
                if (z) {
                    z2 = false;
                }
                z2 = true;
            }
            updateClockViews(z2, true);
        }
    }

    public void onDensityOrFontScaleChanged() {
        this.mClockSwitchYAmount = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.keyguard_clock_switch_y_shift);
        this.mSmartspaceTopOffset = ((RelativeLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.keyguard_smartspace_top_offset);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mSmallClockFrame = (FrameLayout) findViewById(R$id.lockscreen_clock_view);
        this.mLargeClockFrame = (FrameLayout) findViewById(R$id.lockscreen_clock_view_large);
        this.mStatusArea = findViewById(R$id.keyguard_status_area);
        onDensityOrFontScaleChanged();
    }

    @Override // android.widget.RelativeLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (z) {
            post(new Runnable() { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardClockSwitch.this.lambda$onLayout$0();
                }
            });
        }
        if (this.mDisplayedClockSize != null && !this.mChildrenAreLaidOut) {
            post(new Runnable() { // from class: com.android.keyguard.KeyguardClockSwitch$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardClockSwitch.this.lambda$onLayout$1();
                }
            });
        }
        this.mChildrenAreLaidOut = true;
    }

    public void setClock(ClockController clockController, int i) {
        this.mClock = clockController;
        this.mSmallClockFrame.removeAllViews();
        this.mLargeClockFrame.removeAllViews();
        if (clockController == null) {
            Log.e("KeyguardClockSwitch", "No clock being shown");
            return;
        }
        Log.i("KeyguardClockSwitch", "Attached new clock views to switch");
        this.mSmallClockFrame.addView(clockController.getSmallClock().getView());
        this.mLargeClockFrame.addView(clockController.getLargeClock().getView());
        lambda$onLayout$0();
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0045, code lost:
        if (com.android.systemui.shared.recents.utilities.Utilities.isTablet(((android.widget.RelativeLayout) r4).mContext) != false) goto L16;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean switchToClock(int i, boolean z) {
        Integer num = this.mDisplayedClockSize;
        if (num == null || i != num.intValue()) {
            boolean z2 = getResources().getConfiguration().orientation == 2;
            boolean z3 = false;
            if (i == 0) {
                if (z2) {
                    z3 = false;
                }
                z3 = true;
            }
            if (this.mChildrenAreLaidOut) {
                updateClockViews(z3, z);
            }
            this.mDisplayedClockSize = Integer.valueOf(i);
            return true;
        }
        return false;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: updateClockTargetRegions */
    public void lambda$onLayout$0() {
        if (this.mClock != null) {
            if (this.mSmallClockFrame.isLaidOut()) {
                this.mClock.getSmallClock().getEvents().onTargetRegionChanged(new Rect(this.mSmallClockFrame.getLeft(), this.mSmallClockFrame.getTop(), this.mSmallClockFrame.getRight(), this.mSmallClockFrame.getTop() + getResources().getDimensionPixelSize(R$dimen.small_clock_text_size)));
            }
            if (this.mLargeClockFrame.isLaidOut()) {
                this.mClock.getLargeClock().getEvents().onTargetRegionChanged(getLargeClockRegion(this.mLargeClockFrame));
            }
        }
    }

    public final void updateClockViews(boolean z, boolean z2) {
        FrameLayout frameLayout;
        FrameLayout frameLayout2;
        float f;
        Log.i("KeyguardClockSwitch", "updateClockViews; useLargeClock=" + z + "; animate=" + z2 + "; mChildrenAreLaidOut=" + this.mChildrenAreLaidOut);
        AnimatorSet animatorSet = this.mClockInAnim;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimatorSet animatorSet2 = this.mClockOutAnim;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        ObjectAnimator objectAnimator = this.mStatusAreaAnim;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        this.mClockInAnim = null;
        this.mClockOutAnim = null;
        this.mStatusAreaAnim = null;
        int i = -1;
        if (z) {
            frameLayout2 = this.mSmallClockFrame;
            frameLayout = this.mLargeClockFrame;
            if (indexOfChild(frameLayout) == -1) {
                addView(frameLayout, 0);
            }
            f = (this.mSmallClockFrame.getTop() - this.mStatusArea.getTop()) + this.mSmartspaceTopOffset;
        } else {
            frameLayout = this.mSmallClockFrame;
            frameLayout2 = this.mLargeClockFrame;
            removeView(frameLayout2);
            f = 0.0f;
            i = 1;
        }
        if (!z2) {
            frameLayout2.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            frameLayout.setAlpha(1.0f);
            frameLayout.setVisibility(0);
            this.mStatusArea.setTranslationY(f);
            return;
        }
        AnimatorSet animatorSet3 = new AnimatorSet();
        this.mClockOutAnim = animatorSet3;
        animatorSet3.setDuration(150L);
        this.mClockOutAnim.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
        this.mClockOutAnim.playTogether(ObjectAnimator.ofFloat(frameLayout2, View.ALPHA, ActionBarShadowController.ELEVATION_LOW), ObjectAnimator.ofFloat(frameLayout2, View.TRANSLATION_Y, ActionBarShadowController.ELEVATION_LOW, (-this.mClockSwitchYAmount) * i));
        this.mClockOutAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockOutAnim = null;
            }
        });
        frameLayout.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        frameLayout.setVisibility(0);
        AnimatorSet animatorSet4 = new AnimatorSet();
        this.mClockInAnim = animatorSet4;
        animatorSet4.setDuration(200L);
        this.mClockInAnim.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        this.mClockInAnim.playTogether(ObjectAnimator.ofFloat(frameLayout, View.ALPHA, 1.0f), ObjectAnimator.ofFloat(frameLayout, View.TRANSLATION_Y, i * this.mClockSwitchYAmount, ActionBarShadowController.ELEVATION_LOW));
        this.mClockInAnim.setStartDelay(75L);
        this.mClockInAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mClockInAnim = null;
            }
        });
        this.mClockInAnim.start();
        this.mClockOutAnim.start();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mStatusArea, View.TRANSLATION_Y, f);
        this.mStatusAreaAnim = ofFloat;
        ofFloat.setDuration(350L);
        this.mStatusAreaAnim.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mStatusAreaAnim.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardClockSwitch.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                KeyguardClockSwitch.this.mStatusAreaAnim = null;
            }
        });
        this.mStatusAreaAnim.start();
    }
}