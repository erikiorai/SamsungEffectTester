package com.android.systemui.navigationbar.gestural;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.os.SystemClock;
import android.util.MathUtils;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.core.graphics.ColorUtils;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.util.LatencyTracker;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.statusbar.VibratorHelper;
import java.io.PrintWriter;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/NavigationBarEdgePanel.class */
public class NavigationBarEdgePanel extends View implements NavigationEdgeBackPlugin {
    public final SpringAnimation mAngleAnimation;
    public final SpringForce mAngleAppearForce;
    public final SpringForce mAngleDisappearForce;
    public float mAngleOffset;
    public int mArrowColor;
    public final ValueAnimator mArrowColorAnimator;
    public int mArrowColorDark;
    public int mArrowColorLight;
    public final ValueAnimator mArrowDisappearAnimation;
    public final float mArrowLength;
    public int mArrowPaddingEnd;
    public final Path mArrowPath;
    public int mArrowStartColor;
    public final float mArrowThickness;
    public boolean mArrowsPointLeft;
    public NavigationEdgeBackPlugin.BackCallback mBackCallback;
    public final float mBaseTranslation;
    public float mCurrentAngle;
    public int mCurrentArrowColor;
    public float mCurrentTranslation;
    public final float mDensity;
    public float mDesiredAngle;
    public float mDesiredTranslation;
    public float mDesiredVerticalTranslation;
    public float mDisappearAmount;
    public final Point mDisplaySize;
    public boolean mDragSlopPassed;
    public final Runnable mFailsafeRunnable;
    public int mFingerOffset;
    public final Handler mHandler;
    public boolean mIsDark;
    public boolean mIsLeftPanel;
    public boolean mIsLongSwipeEnabled;
    public final LatencyTracker mLatencyTracker;
    public WindowManager.LayoutParams mLayoutParams;
    public int mLeftInset;
    public float mLongSwipeThreshold;
    public float mMaxTranslation;
    public int mMinArrowPosition;
    public final float mMinDeltaForSwitch;
    public final Paint mPaint;
    public float mPreviousTouchTranslation;
    public int mProtectionColor;
    public int mProtectionColorDark;
    public int mProtectionColorLight;
    public final Paint mProtectionPaint;
    public RegionSamplingHelper mRegionSamplingHelper;
    public final SpringForce mRegularTranslationSpring;
    public int mRightInset;
    public final Rect mSamplingRect;
    public int mScreenSize;
    public DynamicAnimation.OnAnimationEndListener mSetGoneEndListener;
    public boolean mShowProtection;
    public float mStartX;
    public float mStartY;
    public final float mSwipeProgressThreshold;
    public final float mSwipeTriggerThreshold;
    public float mTotalTouchDelta;
    public boolean mTrackingBackArrowLatency;
    public final SpringAnimation mTranslationAnimation;
    public boolean mTriggerBack;
    public final SpringForce mTriggerBackSpring;
    public boolean mTriggerLongSwipe;
    public VelocityTracker mVelocityTracker;
    public float mVerticalTranslation;
    public final SpringAnimation mVerticalTranslationAnimation;
    public long mVibrationTime;
    public final VibratorHelper mVibratorHelper;
    public final WindowManager mWindowManager;
    public static final Interpolator RUBBER_BAND_INTERPOLATOR = new PathInterpolator(0.2f, 1.0f, 1.0f, 1.0f);
    public static final Interpolator RUBBER_BAND_INTERPOLATOR_APPEAR = new PathInterpolator(0.25f, 1.0f, 1.0f, 1.0f);
    public static final FloatPropertyCompat<NavigationBarEdgePanel> CURRENT_ANGLE = new FloatPropertyCompat<NavigationBarEdgePanel>("currentAngle") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.2
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(NavigationBarEdgePanel navigationBarEdgePanel) {
            return navigationBarEdgePanel.getCurrentAngle();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(NavigationBarEdgePanel navigationBarEdgePanel, float f) {
            navigationBarEdgePanel.setCurrentAngle(f);
        }
    };
    public static final FloatPropertyCompat<NavigationBarEdgePanel> CURRENT_TRANSLATION = new FloatPropertyCompat<NavigationBarEdgePanel>("currentTranslation") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.3
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(NavigationBarEdgePanel navigationBarEdgePanel) {
            return navigationBarEdgePanel.getCurrentTranslation();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(NavigationBarEdgePanel navigationBarEdgePanel, float f) {
            navigationBarEdgePanel.setCurrentTranslation(f);
        }
    };
    public static final FloatPropertyCompat<NavigationBarEdgePanel> CURRENT_VERTICAL_TRANSLATION = new FloatPropertyCompat<NavigationBarEdgePanel>("verticalTranslation") { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.4
        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(NavigationBarEdgePanel navigationBarEdgePanel) {
            return navigationBarEdgePanel.getVerticalTranslation();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(NavigationBarEdgePanel navigationBarEdgePanel, float f) {
            navigationBarEdgePanel.setVerticalTranslation(f);
        }
    };

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$SVz6dzFfkFR_8P_S0X63yQEcHvE(NavigationBarEdgePanel navigationBarEdgePanel) {
        navigationBarEdgePanel.onFailsafe();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$aq10G-tcKdVPx-k3ZBloEi5A3mg */
    public static /* synthetic */ void m3502$r8$lambda$aq10GtcKdVPxk3ZBloEi5A3mg(NavigationBarEdgePanel navigationBarEdgePanel, ValueAnimator valueAnimator) {
        navigationBarEdgePanel.lambda$new$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$kTvcq9sXrew5EDL5lph2S5dZP_A(NavigationBarEdgePanel navigationBarEdgePanel) {
        navigationBarEdgePanel.lambda$triggerBack$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$wNbO9O8VfymmWe7_8zOnZr0uaVo(NavigationBarEdgePanel navigationBarEdgePanel, ValueAnimator valueAnimator) {
        navigationBarEdgePanel.lambda$new$1(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$zK50LyiR1rGmlp95uQShg0o1Ik8(NavigationBarEdgePanel navigationBarEdgePanel) {
        navigationBarEdgePanel.lambda$triggerBack$2();
    }

    public NavigationBarEdgePanel(Context context, LatencyTracker latencyTracker, VibratorHelper vibratorHelper, Executor executor) {
        super(context);
        Paint paint = new Paint();
        this.mPaint = paint;
        this.mArrowPath = new Path();
        this.mDisplaySize = new Point();
        boolean z = false;
        this.mIsDark = false;
        this.mShowProtection = false;
        this.mSamplingRect = new Rect();
        this.mTrackingBackArrowLatency = false;
        this.mHandler = new Handler();
        this.mFailsafeRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarEdgePanel.$r8$lambda$SVz6dzFfkFR_8P_S0X63yQEcHvE(NavigationBarEdgePanel.this);
            }
        };
        this.mSetGoneEndListener = new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.1
            {
                NavigationBarEdgePanel.this = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f, float f2) {
                dynamicAnimation.removeEndListener(this);
                if (z2) {
                    return;
                }
                NavigationBarEdgePanel.this.setVisibility(8);
            }
        };
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.mVibratorHelper = vibratorHelper;
        this.mDensity = context.getResources().getDisplayMetrics().density;
        this.mBaseTranslation = dp(32.0f);
        this.mArrowLength = dp(18.0f);
        float dp = dp(2.5f);
        this.mArrowThickness = dp;
        this.mMinDeltaForSwitch = dp(32.0f);
        paint.setStrokeWidth(dp);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.mArrowColorAnimator = ofFloat;
        ofFloat.setDuration(120L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NavigationBarEdgePanel.m3502$r8$lambda$aq10GtcKdVPxk3ZBloEi5A3mg(NavigationBarEdgePanel.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.mArrowDisappearAnimation = ofFloat2;
        ofFloat2.setDuration(100L);
        ofFloat2.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                NavigationBarEdgePanel.$r8$lambda$wNbO9O8VfymmWe7_8zOnZr0uaVo(NavigationBarEdgePanel.this, valueAnimator);
            }
        });
        SpringAnimation springAnimation = new SpringAnimation(this, CURRENT_ANGLE);
        this.mAngleAnimation = springAnimation;
        SpringForce dampingRatio = new SpringForce().setStiffness(500.0f).setDampingRatio(0.5f);
        this.mAngleAppearForce = dampingRatio;
        this.mAngleDisappearForce = new SpringForce().setStiffness(1500.0f).setDampingRatio(0.5f).setFinalPosition(90.0f);
        springAnimation.setSpring(dampingRatio).setMaxValue(90.0f);
        SpringAnimation springAnimation2 = new SpringAnimation(this, CURRENT_TRANSLATION);
        this.mTranslationAnimation = springAnimation2;
        SpringForce dampingRatio2 = new SpringForce().setStiffness(1500.0f).setDampingRatio(0.75f);
        this.mRegularTranslationSpring = dampingRatio2;
        this.mTriggerBackSpring = new SpringForce().setStiffness(450.0f).setDampingRatio(0.75f);
        springAnimation2.setSpring(dampingRatio2);
        SpringAnimation springAnimation3 = new SpringAnimation(this, CURRENT_VERTICAL_TRANSLATION);
        this.mVerticalTranslationAnimation = springAnimation3;
        springAnimation3.setSpring(new SpringForce().setStiffness(1500.0f).setDampingRatio(0.75f));
        Paint paint2 = new Paint(paint);
        this.mProtectionPaint = paint2;
        paint2.setStrokeWidth(dp + 2.0f);
        loadDimens();
        loadColors(context);
        updateArrowDirection();
        this.mSwipeTriggerThreshold = context.getResources().getDimension(R$dimen.navigation_edge_action_drag_threshold);
        this.mSwipeProgressThreshold = context.getResources().getDimension(R$dimen.navigation_edge_action_progress_threshold);
        setVisibility(8);
        z = ((View) this).mContext.getDisplayId() == 0 ? true : z;
        final boolean z2 = z;
        RegionSamplingHelper regionSamplingHelper = new RegionSamplingHelper(this, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.5
            {
                NavigationBarEdgePanel.this = this;
            }

            public Rect getSampledRegion(View view) {
                return NavigationBarEdgePanel.this.mSamplingRect;
            }

            public boolean isSamplingEnabled() {
                return z2;
            }

            public void onRegionDarknessChanged(boolean z3) {
                NavigationBarEdgePanel.this.setIsDark(!z3, true);
            }
        }, executor);
        this.mRegionSamplingHelper = regionSamplingHelper;
        regionSamplingHelper.setWindowVisible(true);
        this.mShowProtection = !z;
        this.mLatencyTracker = latencyTracker;
    }

    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        setCurrentArrowColor(ColorUtils.blendARGB(this.mArrowStartColor, this.mArrowColor, valueAnimator.getAnimatedFraction()));
    }

    public /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.mDisappearAmount = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    public /* synthetic */ void lambda$triggerBack$2() {
        setVisibility(8);
    }

    public /* synthetic */ void lambda$triggerBack$3() {
        this.mAngleOffset = Math.max((float) ActionBarShadowController.ELEVATION_LOW, this.mAngleOffset + 8.0f);
        updateAngle(true);
        this.mTranslationAnimation.setSpring(this.mTriggerBackSpring);
        setDesiredTranslation(this.mDesiredTranslation - dp(32.0f), true);
        animate().alpha(ActionBarShadowController.ELEVATION_LOW).setDuration(80L).withEndAction(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarEdgePanel.$r8$lambda$zK50LyiR1rGmlp95uQShg0o1Ik8(NavigationBarEdgePanel.this);
            }
        });
        this.mArrowDisappearAnimation.start();
        scheduleFailsafe();
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x002d, code lost:
        if (r7.mArrowsPointLeft == false) goto L7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void adjustSamplingRectToBoundingBox() {
        float f = this.mDesiredTranslation;
        if (!this.mTriggerBack) {
            float f2 = this.mBaseTranslation;
            boolean z = this.mIsLeftPanel;
            if (!z || !this.mArrowsPointLeft) {
                f = f2;
                if (!z) {
                    f = f2;
                }
            }
            f = f2 - getStaticArrowWidth();
        }
        float f3 = f - (this.mArrowThickness / 2.0f);
        if (!this.mIsLeftPanel) {
            f3 = this.mSamplingRect.width() - f3;
        }
        float staticArrowWidth = getStaticArrowWidth();
        float polarToCartY = polarToCartY(56.0f) * this.mArrowLength * 2.0f;
        float f4 = f3;
        if (!this.mArrowsPointLeft) {
            f4 = f3 - staticArrowWidth;
        }
        float f5 = polarToCartY / 2.0f;
        this.mSamplingRect.offset((int) f4, (int) (((getHeight() * 0.5f) + this.mDesiredVerticalTranslation) - f5));
        Rect rect = this.mSamplingRect;
        int i = rect.left;
        int i2 = rect.top;
        rect.set(i, i2, (int) (i + staticArrowWidth), (int) (i2 + polarToCartY));
        this.mRegionSamplingHelper.updateSamplingRect();
    }

    public final Path calculatePath(float f, float f2) {
        float f3 = f;
        if (!this.mArrowsPointLeft) {
            f3 = -f;
        }
        float lerp = MathUtils.lerp(1.0f, 0.75f, this.mDisappearAmount);
        float f4 = f3 * lerp;
        float f5 = f2 * lerp;
        this.mArrowPath.reset();
        this.mArrowPath.moveTo(f4, f5);
        this.mArrowPath.lineTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        this.mArrowPath.lineTo(f4, -f5);
        return this.mArrowPath;
    }

    public final void cancelBack() {
        this.mBackCallback.cancelBack();
        if (!this.mTranslationAnimation.isRunning()) {
            setVisibility(8);
            return;
        }
        this.mTranslationAnimation.addEndListener(this.mSetGoneEndListener);
        scheduleFailsafe();
    }

    public final void cancelFailsafe() {
        this.mHandler.removeCallbacks(this.mFailsafeRunnable);
    }

    public final float dp(float f) {
        return this.mDensity * f;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void dump(PrintWriter printWriter) {
        printWriter.println("NavigationBarEdgePanel:");
        printWriter.println("  mIsLeftPanel=" + this.mIsLeftPanel);
        printWriter.println("  mTriggerBack=" + this.mTriggerBack);
        printWriter.println("  mDragSlopPassed=" + this.mDragSlopPassed);
        printWriter.println("  mCurrentAngle=" + this.mCurrentAngle);
        printWriter.println("  mDesiredAngle=" + this.mDesiredAngle);
        printWriter.println("  mCurrentTranslation=" + this.mCurrentTranslation);
        printWriter.println("  mDesiredTranslation=" + this.mDesiredTranslation);
        printWriter.println("  mTranslationAnimation running=" + this.mTranslationAnimation.isRunning());
        this.mRegionSamplingHelper.dump(printWriter);
    }

    public final float getCurrentAngle() {
        return this.mCurrentAngle;
    }

    public final float getCurrentTranslation() {
        return this.mCurrentTranslation;
    }

    public final float getStaticArrowWidth() {
        return polarToCartX(56.0f) * this.mArrowLength;
    }

    public final float getVerticalTranslation() {
        return this.mVerticalTranslation;
    }

    /* JADX WARN: Code restructure failed: missing block: B:118:0x0215, code lost:
        if (r5.mArrowsPointLeft == false) goto L50;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleMoveEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        float abs = MathUtils.abs(x - this.mStartX);
        float f3 = y - this.mStartY;
        float f4 = abs - this.mPreviousTouchTranslation;
        if (Math.abs(f4) > ActionBarShadowController.ELEVATION_LOW) {
            if (Math.signum(f4) == Math.signum(this.mTotalTouchDelta)) {
                this.mTotalTouchDelta += f4;
            } else {
                this.mTotalTouchDelta = f4;
            }
        }
        this.mPreviousTouchTranslation = abs;
        boolean z = abs > this.mLongSwipeThreshold;
        if (!this.mDragSlopPassed && abs > this.mSwipeTriggerThreshold) {
            this.mDragSlopPassed = true;
            this.mVibratorHelper.vibrate(2);
            this.mVibrationTime = SystemClock.uptimeMillis();
            this.mDisappearAmount = ActionBarShadowController.ELEVATION_LOW;
            setAlpha(1.0f);
            setTriggerBack(true, true);
        }
        float f5 = this.mBaseTranslation;
        if (abs > f5) {
            float interpolation = RUBBER_BAND_INTERPOLATOR.getInterpolation(MathUtils.saturate((abs - f5) / (this.mScreenSize - f5)));
            float f6 = this.mMaxTranslation;
            float f7 = this.mBaseTranslation;
            f = f7 + (interpolation * (f6 - f7));
        } else {
            float interpolation2 = RUBBER_BAND_INTERPOLATOR_APPEAR.getInterpolation(MathUtils.saturate((f5 - abs) / f5));
            float f8 = this.mBaseTranslation;
            f = f8 - (interpolation2 * (f8 / 4.0f));
        }
        boolean z2 = this.mTriggerBack;
        if (Math.abs(this.mTotalTouchDelta) > this.mMinDeltaForSwitch) {
            z2 = this.mTotalTouchDelta > ActionBarShadowController.ELEVATION_LOW;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float xVelocity = this.mVelocityTracker.getXVelocity();
        float min = Math.min((MathUtils.mag(xVelocity, this.mVelocityTracker.getYVelocity()) / 1000.0f) * 4.0f, 4.0f) * Math.signum(xVelocity);
        this.mAngleOffset = min;
        boolean z3 = this.mIsLeftPanel;
        if ((z3 && this.mArrowsPointLeft) || (!z3 && !this.mArrowsPointLeft)) {
            this.mAngleOffset = min * (-1.0f);
        }
        if (Math.abs(f3) > Math.abs(x - this.mStartX) * 2.0f) {
            z2 = false;
        }
        if (this.mIsLongSwipeEnabled) {
            boolean z4 = false;
            if (z2) {
                z4 = false;
                if (z) {
                    z4 = true;
                }
            }
            setTriggerLongSwipe(z4, true);
        }
        setTriggerBack(z2, true);
        if (this.mTriggerBack) {
            boolean z5 = this.mIsLeftPanel;
            if (!z5 || !this.mArrowsPointLeft) {
                f2 = f;
                if (!z5) {
                    f2 = f;
                }
            }
            f2 = f - getStaticArrowWidth();
        } else {
            f2 = 0.0f;
        }
        setDesiredTranslation(f2, true);
        updateAngle(true);
        float height = (getHeight() / 2.0f) - this.mArrowLength;
        setDesiredVerticalTransition(RUBBER_BAND_INTERPOLATOR.getInterpolation(MathUtils.constrain(Math.abs(f3) / (15.0f * height), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f)) * height * Math.signum(f3), true);
        updateSamplingRect();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void loadColors(Context context) {
        int themeAttr = Utils.getThemeAttr(context, R$attr.darkIconTheme);
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, Utils.getThemeAttr(context, R$attr.lightIconTheme));
        ContextThemeWrapper contextThemeWrapper2 = new ContextThemeWrapper(context, themeAttr);
        int i = R$attr.singleToneColor;
        this.mArrowColorLight = Utils.getColorAttrDefaultColor(contextThemeWrapper, i);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(contextThemeWrapper2, i);
        this.mArrowColorDark = colorAttrDefaultColor;
        this.mProtectionColorDark = this.mArrowColorLight;
        this.mProtectionColorLight = colorAttrDefaultColor;
        updateIsDark(false);
    }

    public final void loadDimens() {
        Resources resources = getResources();
        this.mArrowPaddingEnd = resources.getDimensionPixelSize(R$dimen.navigation_edge_panel_padding);
        this.mMinArrowPosition = resources.getDimensionPixelSize(R$dimen.navigation_edge_arrow_min_y);
        this.mFingerOffset = resources.getDimensionPixelSize(R$dimen.navigation_edge_finger_offset);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateArrowDirection();
        loadDimens();
    }

    @Override // com.android.systemui.plugins.Plugin
    public void onDestroy() {
        cancelFailsafe();
        this.mWindowManager.removeView(this);
        this.mRegionSamplingHelper.stop();
        this.mRegionSamplingHelper = null;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f = this.mCurrentTranslation - (this.mArrowThickness / 2.0f);
        canvas.save();
        if (!this.mIsLeftPanel) {
            f = getWidth() - f;
        }
        canvas.translate(f, (getHeight() * 0.5f) + this.mVerticalTranslation);
        float polarToCartX = polarToCartX(this.mCurrentAngle) * this.mArrowLength;
        float polarToCartY = polarToCartY(this.mCurrentAngle) * this.mArrowLength;
        Path calculatePath = calculatePath(polarToCartX, polarToCartY);
        if (this.mTriggerLongSwipe) {
            calculatePath.addPath(calculatePath(polarToCartX, polarToCartY), this.mArrowThickness * 2.0f * (this.mIsLeftPanel ? 1 : -1), ActionBarShadowController.ELEVATION_LOW);
        }
        if (this.mShowProtection) {
            canvas.drawPath(calculatePath, this.mProtectionPaint);
        }
        canvas.drawPath(calculatePath, this.mPaint);
        canvas.restore();
        if (this.mTrackingBackArrowLatency) {
            this.mLatencyTracker.onActionEnd(15);
            this.mTrackingBackArrowLatency = false;
        }
    }

    public final void onFailsafe() {
        setVisibility(8);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mMaxTranslation = getWidth() - this.mArrowPaddingEnd;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void onMotionEvent(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDragSlopPassed = false;
            resetOnDown();
            this.mStartX = motionEvent.getX();
            this.mStartY = motionEvent.getY();
            setVisibility(0);
            updatePosition(motionEvent.getY());
            this.mRegionSamplingHelper.start(this.mSamplingRect);
            this.mWindowManager.updateViewLayout(this, this.mLayoutParams);
            this.mLatencyTracker.onActionStart(15);
            this.mTrackingBackArrowLatency = true;
        } else if (actionMasked == 1) {
            if (this.mTriggerLongSwipe) {
                triggerBack(true);
            } else if (this.mTriggerBack) {
                triggerBack(false);
            } else {
                cancelBack();
            }
            this.mRegionSamplingHelper.stop();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        } else if (actionMasked == 2) {
            handleMoveEvent(motionEvent);
        } else if (actionMasked != 3) {
        } else {
            cancelBack();
            this.mRegionSamplingHelper.stop();
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public final float polarToCartX(float f) {
        return (float) Math.cos(Math.toRadians(f));
    }

    public final float polarToCartY(float f) {
        return (float) Math.sin(Math.toRadians(f));
    }

    public final void resetOnDown() {
        animate().cancel();
        this.mAngleAnimation.cancel();
        this.mTranslationAnimation.cancel();
        this.mVerticalTranslationAnimation.cancel();
        this.mArrowDisappearAnimation.cancel();
        this.mAngleOffset = ActionBarShadowController.ELEVATION_LOW;
        this.mTranslationAnimation.setSpring(this.mRegularTranslationSpring);
        setTriggerBack(false, false);
        setTriggerLongSwipe(false, false);
        setDesiredTranslation(ActionBarShadowController.ELEVATION_LOW, false);
        setCurrentTranslation(ActionBarShadowController.ELEVATION_LOW);
        updateAngle(false);
        this.mPreviousTouchTranslation = ActionBarShadowController.ELEVATION_LOW;
        this.mTotalTouchDelta = ActionBarShadowController.ELEVATION_LOW;
        this.mVibrationTime = 0L;
        setDesiredVerticalTransition(ActionBarShadowController.ELEVATION_LOW, false);
        cancelFailsafe();
    }

    public final void scheduleFailsafe() {
        cancelFailsafe();
        this.mHandler.postDelayed(this.mFailsafeRunnable, 200L);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setBackCallback(NavigationEdgeBackPlugin.BackCallback backCallback) {
        this.mBackCallback = backCallback;
    }

    public final void setCurrentAngle(float f) {
        this.mCurrentAngle = f;
        invalidate();
    }

    public final void setCurrentArrowColor(int i) {
        this.mCurrentArrowColor = i;
        this.mPaint.setColor(i);
        invalidate();
    }

    public final void setCurrentTranslation(float f) {
        this.mCurrentTranslation = f;
        invalidate();
    }

    public final void setDesiredTranslation(float f, boolean z) {
        if (this.mDesiredTranslation != f) {
            this.mDesiredTranslation = f;
            if (z) {
                this.mTranslationAnimation.animateToFinalPosition(f);
            } else {
                setCurrentTranslation(f);
            }
        }
    }

    public final void setDesiredVerticalTransition(float f, boolean z) {
        if (this.mDesiredVerticalTranslation != f) {
            this.mDesiredVerticalTranslation = f;
            if (z) {
                this.mVerticalTranslationAnimation.animateToFinalPosition(f);
            } else {
                setVerticalTranslation(f);
            }
            invalidate();
        }
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setDisplaySize(Point point) {
        this.mDisplaySize.set(point.x, point.y);
        Point point2 = this.mDisplaySize;
        this.mScreenSize = Math.min(point2.x, point2.y);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setInsets(int i, int i2) {
        this.mLeftInset = i;
        this.mRightInset = i2;
    }

    public final void setIsDark(boolean z, boolean z2) {
        this.mIsDark = z;
        updateIsDark(z2);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setIsLeftPanel(boolean z) {
        this.mIsLeftPanel = z;
        this.mLayoutParams.gravity = z ? 51 : 53;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.mLayoutParams = layoutParams;
        this.mWindowManager.addView(this, layoutParams);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setLongSwipeEnabled(boolean z) {
        float min = z ? MathUtils.min(this.mDisplaySize.x * 0.5f, this.mLayoutParams.width * 2.5f) : 0.0f;
        this.mLongSwipeThreshold = min;
        boolean z2 = min > ActionBarShadowController.ELEVATION_LOW;
        this.mIsLongSwipeEnabled = z2;
        setTriggerLongSwipe(z2 && this.mTriggerLongSwipe, false);
    }

    public final void setTriggerBack(boolean z, boolean z2) {
        if (this.mTriggerBack != z) {
            this.mTriggerBack = z;
            this.mAngleAnimation.cancel();
            updateAngle(z2);
            this.mTranslationAnimation.cancel();
            this.mBackCallback.setTriggerBack(this.mTriggerBack);
        }
    }

    public final void setTriggerLongSwipe(boolean z, boolean z2) {
        if (this.mTriggerLongSwipe != z) {
            this.mTriggerLongSwipe = z;
            this.mVibratorHelper.vibrate(0);
            this.mAngleAnimation.cancel();
            updateAngle(z2);
            this.mTranslationAnimation.cancel();
            this.mBackCallback.setTriggerLongSwipe(this.mTriggerLongSwipe);
        }
    }

    public final void setVerticalTranslation(float f) {
        this.mVerticalTranslation = f;
        invalidate();
    }

    public final void triggerBack(boolean z) {
        this.mBackCallback.triggerBack(z);
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        if ((Math.abs(this.mVelocityTracker.getXVelocity()) < 500.0f) || SystemClock.uptimeMillis() - this.mVibrationTime >= 400) {
            this.mVibratorHelper.vibrate(0);
        }
        float f = this.mAngleOffset;
        if (f > -4.0f) {
            this.mAngleOffset = Math.max(-8.0f, f - 8.0f);
            updateAngle(true);
        }
        final Runnable runnable = new Runnable() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarEdgePanel.$r8$lambda$kTvcq9sXrew5EDL5lph2S5dZP_A(NavigationBarEdgePanel.this);
            }
        };
        if (!this.mTranslationAnimation.isRunning()) {
            runnable.run();
            return;
        }
        this.mTranslationAnimation.addEndListener(new DynamicAnimation.OnAnimationEndListener() { // from class: com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel.6
            {
                NavigationBarEdgePanel.this = this;
            }

            @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
            public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z2, float f2, float f3) {
                dynamicAnimation.removeEndListener(this);
                if (z2) {
                    return;
                }
                runnable.run();
            }
        });
        scheduleFailsafe();
    }

    public final void updateAngle(boolean z) {
        boolean z2 = this.mTriggerBack;
        float f = z2 ? this.mAngleOffset + 56.0f : 90.0f;
        if (f != this.mDesiredAngle) {
            if (z) {
                this.mAngleAnimation.setSpring(z2 ? this.mAngleAppearForce : this.mAngleDisappearForce);
                this.mAngleAnimation.animateToFinalPosition(f);
            } else {
                setCurrentAngle(f);
            }
            this.mDesiredAngle = f;
        }
    }

    public final void updateArrowDirection() {
        this.mArrowsPointLeft = getLayoutDirection() == 0;
        invalidate();
    }

    public final void updateIsDark(boolean z) {
        int i = this.mIsDark ? this.mProtectionColorDark : this.mProtectionColorLight;
        this.mProtectionColor = i;
        this.mProtectionPaint.setColor(i);
        this.mArrowColor = this.mIsDark ? this.mArrowColorDark : this.mArrowColorLight;
        this.mArrowColorAnimator.cancel();
        if (!z) {
            setCurrentArrowColor(this.mArrowColor);
            return;
        }
        this.mArrowStartColor = this.mCurrentArrowColor;
        this.mArrowColorAnimator.start();
    }

    public final void updatePosition(float f) {
        float max = Math.max(f - this.mFingerOffset, this.mMinArrowPosition);
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        layoutParams.y = MathUtils.constrain((int) (max - (layoutParams.height / 2.0f)), 0, this.mDisplaySize.y);
        updateSamplingRect();
    }

    public final void updateSamplingRect() {
        WindowManager.LayoutParams layoutParams = this.mLayoutParams;
        int i = layoutParams.y;
        int i2 = this.mIsLeftPanel ? this.mLeftInset : (this.mDisplaySize.x - this.mRightInset) - layoutParams.width;
        this.mSamplingRect.set(i2, i, layoutParams.width + i2, layoutParams.height + i);
        adjustSamplingRectToBoundingBox();
    }
}