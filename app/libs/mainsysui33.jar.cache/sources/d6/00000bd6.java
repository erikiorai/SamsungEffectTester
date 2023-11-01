package com.android.keyguard;

import android.content.Context;
import android.graphics.Rect;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.widget.LockPatternView;
import com.android.settingslib.animation.AppearAnimationCreator;
import com.android.settingslib.animation.AppearAnimationUtils;
import com.android.settingslib.animation.DisappearAnimationUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPatternView.class */
public class KeyguardPatternView extends KeyguardInputView implements AppearAnimationCreator<LockPatternView.CellState> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final AppearAnimationUtils mAppearAnimationUtils;
    public ConstraintLayout mContainer;
    public final DisappearAnimationUtils mDisappearAnimationUtils;
    public final DisappearAnimationUtils mDisappearAnimationUtilsLocked;
    public View mEcaView;
    public long mLastPokeTime;
    public final Rect mLockPatternScreenBounds;
    public LockPatternView mLockPatternView;
    public BouncerKeyguardMessageArea mSecurityMessageDisplay;
    public final Rect mTempRect;
    public final int[] mTmpPosition;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$1cDVOSyY7kk25K7QUf6Oa8c8Pwg(KeyguardPatternView keyguardPatternView) {
        keyguardPatternView.lambda$startAppearAnimation$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$Sz_3IRlQouOrbmx4V9rQ2s0DkzU(KeyguardPatternView keyguardPatternView, Runnable runnable) {
        keyguardPatternView.lambda$startDisappearAnimation$1(runnable);
    }

    public KeyguardPatternView(Context context) {
        this(context, null);
    }

    public KeyguardPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTmpPosition = new int[2];
        this.mTempRect = new Rect();
        this.mLockPatternScreenBounds = new Rect();
        this.mLastPokeTime = -7000L;
        this.mAppearAnimationUtils = new AppearAnimationUtils(context, 220L, 1.5f, 2.0f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, 17563662));
        this.mDisappearAnimationUtils = new DisappearAnimationUtils(context, 125L, 1.2f, 0.6f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, 17563663));
        this.mDisappearAnimationUtilsLocked = new DisappearAnimationUtils(context, 187L, 1.2f, 0.6f, AnimationUtils.loadInterpolator(((LinearLayout) this).mContext, 17563663));
    }

    public /* synthetic */ void lambda$startAppearAnimation$0() {
        enableClipping(true);
    }

    public /* synthetic */ void lambda$startDisappearAnimation$1(Runnable runnable) {
        enableClipping(true);
        if (runnable != null) {
            runnable.run();
        }
    }

    @Override // com.android.settingslib.animation.AppearAnimationCreator
    public void createAnimation(LockPatternView.CellState cellState, long j, long j2, float f, boolean z, Interpolator interpolator, Runnable runnable) {
        this.mLockPatternView.startCellStateAnimation(cellState, 1.0f, z ? 1.0f : 0.0f, z ? f : 0.0f, z ? 0.0f : f, z ? 0.0f : 1.0f, 1.0f, j, j2, interpolator, runnable);
        if (runnable != null) {
            this.mAppearAnimationUtils.createAnimation(this.mEcaView, j, j2, f, z, interpolator, (Runnable) null);
        }
    }

    @Override // com.android.keyguard.KeyguardInputView
    public boolean disallowInterceptTouch(MotionEvent motionEvent) {
        return !this.mLockPatternView.isEmpty() || this.mLockPatternScreenBounds.contains((int) motionEvent.getRawX(), (int) motionEvent.getRawY());
    }

    public final void enableClipping(boolean z) {
        setClipChildren(z);
        this.mContainer.setClipToPadding(z);
        this.mContainer.setClipChildren(z);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getResources().getString(17040539);
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSecurityMessageDisplay = (BouncerKeyguardMessageArea) findViewById(R$id.bouncer_message_area);
    }

    public void onDevicePostureChanged(int i) {
        float f = ((LinearLayout) this).mContext.getResources().getFloat(R$dimen.half_opened_bouncer_height_ratio);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mContainer);
        int i2 = R$id.pattern_top_guideline;
        if (i != 2) {
            f = 0.0f;
        }
        constraintSet.setGuidelinePercent(i2, f);
        constraintSet.applyTo(this.mContainer);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLockPatternView = findViewById(R$id.lockPatternView);
        this.mEcaView = findViewById(R$id.keyguard_selector_fade_container);
        this.mContainer = (ConstraintLayout) findViewById(R$id.pattern_container);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mLockPatternView.getLocationOnScreen(this.mTmpPosition);
        Rect rect = this.mLockPatternScreenBounds;
        int[] iArr = this.mTmpPosition;
        int i5 = iArr[0];
        rect.set(i5 - 40, iArr[1] - 40, i5 + this.mLockPatternView.getWidth() + 40, this.mTmpPosition[1] + this.mLockPatternView.getHeight() + 40);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean onTouchEvent = super.onTouchEvent(motionEvent);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j = this.mLastPokeTime;
        if (onTouchEvent && elapsedRealtime - j > 6900) {
            this.mLastPokeTime = SystemClock.elapsedRealtime();
        }
        boolean z = false;
        this.mTempRect.set(0, 0, 0, 0);
        offsetRectIntoDescendantCoords(this.mLockPatternView, this.mTempRect);
        Rect rect = this.mTempRect;
        motionEvent.offsetLocation(rect.left, rect.top);
        if (this.mLockPatternView.dispatchTouchEvent(motionEvent) || onTouchEvent) {
            z = true;
        }
        Rect rect2 = this.mTempRect;
        motionEvent.offsetLocation(-rect2.left, -rect2.top);
        return z;
    }

    @Override // com.android.keyguard.KeyguardInputView
    public void startAppearAnimation() {
        enableClipping(false);
        setAlpha(1.0f);
        setTranslationY(this.mAppearAnimationUtils.getStartTranslation());
        AppearAnimationUtils.startTranslationYAnimation(this, 0L, 500L, ActionBarShadowController.ELEVATION_LOW, this.mAppearAnimationUtils.getInterpolator(), getAnimationListener(18));
        this.mAppearAnimationUtils.startAnimation2d(this.mLockPatternView.getCellStates(), new Runnable() { // from class: com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPatternView.$r8$lambda$1cDVOSyY7kk25K7QUf6Oa8c8Pwg(KeyguardPatternView.this);
            }
        }, this);
        if (TextUtils.isEmpty(this.mSecurityMessageDisplay.getText())) {
            return;
        }
        AppearAnimationUtils appearAnimationUtils = this.mAppearAnimationUtils;
        appearAnimationUtils.createAnimation((View) this.mSecurityMessageDisplay, 0L, 220L, appearAnimationUtils.getStartTranslation(), true, this.mAppearAnimationUtils.getInterpolator(), (Runnable) null);
    }

    public boolean startDisappearAnimation(boolean z, final Runnable runnable) {
        float f = z ? 1.5f : 1.0f;
        this.mLockPatternView.clearPattern();
        enableClipping(false);
        setTranslationY(ActionBarShadowController.ELEVATION_LOW);
        AppearAnimationUtils.startTranslationYAnimation(this, 0L, 300.0f * f, -this.mDisappearAnimationUtils.getStartTranslation(), this.mDisappearAnimationUtils.getInterpolator(), getAnimationListener(21));
        (z ? this.mDisappearAnimationUtilsLocked : this.mDisappearAnimationUtils).startAnimation2d(this.mLockPatternView.getCellStates(), new Runnable() { // from class: com.android.keyguard.KeyguardPatternView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPatternView.$r8$lambda$Sz_3IRlQouOrbmx4V9rQ2s0DkzU(KeyguardPatternView.this, runnable);
            }
        }, this);
        if (TextUtils.isEmpty(this.mSecurityMessageDisplay.getText())) {
            return true;
        }
        DisappearAnimationUtils disappearAnimationUtils = this.mDisappearAnimationUtils;
        disappearAnimationUtils.createAnimation((View) this.mSecurityMessageDisplay, 0L, f * 200.0f, (-disappearAnimationUtils.getStartTranslation()) * 3.0f, false, this.mDisappearAnimationUtils.getInterpolator(), (Runnable) null);
        return true;
    }
}