package com.android.systemui.dreams.touch;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.assist.PhoneStateMonitor$$ExternalSyntheticLambda1;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.wm.shell.animation.FlingAnimationUtils;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/BouncerSwipeTouchHandler.class */
public class BouncerSwipeTouchHandler implements DreamTouchHandler {
    public boolean mBouncerInitiallyShowing;
    public final float mBouncerZoneScreenPercentage;
    public Boolean mCapture;
    public final Optional<CentralSurfaces> mCentralSurfaces;
    public float mCurrentExpansion;
    public final DisplayMetrics mDisplayMetrics;
    public final FlingAnimationUtils mFlingAnimationUtils;
    public final FlingAnimationUtils mFlingAnimationUtilsClosing;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.1
        {
            BouncerSwipeTouchHandler.this = this;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (BouncerSwipeTouchHandler.this.mCapture == null) {
                BouncerSwipeTouchHandler.this.mCapture = Boolean.valueOf(Math.abs(f2) > Math.abs(f));
                BouncerSwipeTouchHandler bouncerSwipeTouchHandler = BouncerSwipeTouchHandler.this;
                bouncerSwipeTouchHandler.mBouncerInitiallyShowing = ((Boolean) bouncerSwipeTouchHandler.mCentralSurfaces.map(new PhoneStateMonitor$$ExternalSyntheticLambda1()).orElse(Boolean.FALSE)).booleanValue();
                if (BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                    BouncerSwipeTouchHandler.this.mStatusBarKeyguardViewManager.showPrimaryBouncer(false);
                }
            }
            if (BouncerSwipeTouchHandler.this.mCapture.booleanValue()) {
                if (BouncerSwipeTouchHandler.this.mBouncerInitiallyShowing || motionEvent.getY() >= motionEvent2.getY()) {
                    if ((!BouncerSwipeTouchHandler.this.mBouncerInitiallyShowing || motionEvent.getY() <= motionEvent2.getY()) && BouncerSwipeTouchHandler.this.mCentralSurfaces.isPresent()) {
                        float y = motionEvent2.getY();
                        float y2 = motionEvent.getY();
                        float abs = Math.abs(motionEvent.getY() - motionEvent2.getY()) / ((CentralSurfaces) BouncerSwipeTouchHandler.this.mCentralSurfaces.get()).getDisplayHeight();
                        BouncerSwipeTouchHandler bouncerSwipeTouchHandler2 = BouncerSwipeTouchHandler.this;
                        if (!bouncerSwipeTouchHandler2.mBouncerInitiallyShowing) {
                            abs = 1.0f - abs;
                        }
                        bouncerSwipeTouchHandler2.setPanelExpansion(abs, y - y2);
                        return true;
                    }
                    return true;
                }
                return true;
            }
            return false;
        }
    };
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public DreamTouchHandler.TouchSession mTouchSession;
    public final UiEventLogger mUiEventLogger;
    public ValueAnimatorCreator mValueAnimatorCreator;
    public VelocityTracker mVelocityTracker;
    public VelocityTrackerFactory mVelocityTrackerFactory;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/BouncerSwipeTouchHandler$DreamEvent.class */
    public enum DreamEvent implements UiEventLogger.UiEventEnum {
        DREAM_SWIPED(988),
        DREAM_BOUNCER_FULLY_VISIBLE(1056);
        
        private final int mId;

        DreamEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/BouncerSwipeTouchHandler$ValueAnimatorCreator.class */
    public interface ValueAnimatorCreator {
        ValueAnimator create(float f, float f2);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/touch/BouncerSwipeTouchHandler$VelocityTrackerFactory.class */
    public interface VelocityTrackerFactory {
        VelocityTracker obtain();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda0.onRemoved():void] */
    public static /* synthetic */ void $r8$lambda$1UFO0cLyASHb0LxeuKC6yIIhGUU(BouncerSwipeTouchHandler bouncerSwipeTouchHandler) {
        bouncerSwipeTouchHandler.lambda$onSessionStart$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$46l8uaPoh_CVPh3_ATgR4xHo19c(BouncerSwipeTouchHandler bouncerSwipeTouchHandler, float f, ValueAnimator valueAnimator) {
        bouncerSwipeTouchHandler.lambda$createExpansionAnimator$2(f, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda1.onInputEvent(android.view.InputEvent):void] */
    public static /* synthetic */ void $r8$lambda$m8Xiir1cJIqFocL2l5eMHZ1FpHw(BouncerSwipeTouchHandler bouncerSwipeTouchHandler, InputEvent inputEvent) {
        bouncerSwipeTouchHandler.lambda$onSessionStart$1(inputEvent);
    }

    public BouncerSwipeTouchHandler(DisplayMetrics displayMetrics, StatusBarKeyguardViewManager statusBarKeyguardViewManager, Optional<CentralSurfaces> optional, NotificationShadeWindowController notificationShadeWindowController, ValueAnimatorCreator valueAnimatorCreator, VelocityTrackerFactory velocityTrackerFactory, FlingAnimationUtils flingAnimationUtils, FlingAnimationUtils flingAnimationUtils2, float f, UiEventLogger uiEventLogger) {
        this.mDisplayMetrics = displayMetrics;
        this.mCentralSurfaces = optional;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mBouncerZoneScreenPercentage = f;
        this.mFlingAnimationUtils = flingAnimationUtils;
        this.mFlingAnimationUtilsClosing = flingAnimationUtils2;
        this.mValueAnimatorCreator = valueAnimatorCreator;
        this.mVelocityTrackerFactory = velocityTrackerFactory;
        this.mUiEventLogger = uiEventLogger;
    }

    public /* synthetic */ void lambda$createExpansionAnimator$2(float f, ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        setPanelExpansion(floatValue, f * floatValue);
    }

    public /* synthetic */ void lambda$onSessionStart$0() {
        this.mVelocityTracker.recycle();
        this.mCapture = null;
        this.mNotificationShadeWindowController.setForcePluginOpen(false, this);
    }

    public final ValueAnimator createExpansionAnimator(float f, final float f2) {
        ValueAnimator create = this.mValueAnimatorCreator.create(this.mCurrentExpansion, f);
        create.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                BouncerSwipeTouchHandler.$r8$lambda$46l8uaPoh_CVPh3_ATgR4xHo19c(BouncerSwipeTouchHandler.this, f2, valueAnimator);
            }
        });
        if (!this.mBouncerInitiallyShowing && f == ActionBarShadowController.ELEVATION_LOW) {
            create.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler.2
                {
                    BouncerSwipeTouchHandler.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BouncerSwipeTouchHandler.this.mUiEventLogger.log(DreamEvent.DREAM_BOUNCER_FULLY_VISIBLE);
                }
            });
        }
        return create;
    }

    public boolean flingRevealsOverlay(float f, float f2) {
        boolean z = true;
        if (Math.abs(f2) >= this.mFlingAnimationUtils.getMinVelocityPxPerSecond()) {
            return f > ActionBarShadowController.ELEVATION_LOW;
        }
        if (this.mCurrentExpansion <= 0.5f) {
            z = false;
        }
        return z;
    }

    public void flingToExpansion(float f, float f2) {
        if (this.mCentralSurfaces.isPresent()) {
            float displayHeight = this.mCentralSurfaces.get().getDisplayHeight();
            float f3 = displayHeight * this.mCurrentExpansion;
            float f4 = displayHeight * f2;
            ValueAnimator createExpansionAnimator = createExpansionAnimator(f2, f4 - f3);
            if (f2 == 1.0f) {
                this.mFlingAnimationUtilsClosing.apply(createExpansionAnimator, f3, f4, f, displayHeight);
            } else {
                this.mFlingAnimationUtils.apply(createExpansionAnimator, f3, f4, f, displayHeight);
            }
            createExpansionAnimator.start();
        }
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public void getTouchInitiationRegion(Region region) {
        if (((Boolean) this.mCentralSurfaces.map(new PhoneStateMonitor$$ExternalSyntheticLambda1()).orElse(Boolean.FALSE)).booleanValue()) {
            DisplayMetrics displayMetrics = this.mDisplayMetrics;
            region.op(new Rect(0, 0, displayMetrics.widthPixels, Math.round(displayMetrics.heightPixels * this.mBouncerZoneScreenPercentage)), Region.Op.UNION);
            return;
        }
        int round = Math.round(this.mDisplayMetrics.heightPixels * (1.0f - this.mBouncerZoneScreenPercentage));
        DisplayMetrics displayMetrics2 = this.mDisplayMetrics;
        region.op(new Rect(0, round, displayMetrics2.widthPixels, displayMetrics2.heightPixels), Region.Op.UNION);
    }

    /* renamed from: onMotionEvent */
    public final void lambda$onSessionStart$1(InputEvent inputEvent) {
        if (!(inputEvent instanceof MotionEvent)) {
            Log.e("BouncerSwipeTouchHandler", "non MotionEvent received:" + inputEvent);
            return;
        }
        MotionEvent motionEvent = (MotionEvent) inputEvent;
        int action = motionEvent.getAction();
        if (action != 1 && action != 3) {
            this.mVelocityTracker.addMovement(motionEvent);
            return;
        }
        this.mTouchSession.pop();
        Boolean bool = this.mCapture;
        if (bool == null || !bool.booleanValue()) {
            return;
        }
        this.mVelocityTracker.computeCurrentVelocity(1000);
        float yVelocity = this.mVelocityTracker.getYVelocity();
        float f = flingRevealsOverlay(yVelocity, (float) Math.hypot((double) this.mVelocityTracker.getXVelocity(), (double) yVelocity)) ? 1.0f : 0.0f;
        if (!this.mBouncerInitiallyShowing && f == ActionBarShadowController.ELEVATION_LOW) {
            this.mUiEventLogger.log(DreamEvent.DREAM_SWIPED);
        }
        flingToExpansion(yVelocity, f);
        if (f == 1.0f) {
            this.mStatusBarKeyguardViewManager.reset(false);
        }
    }

    @Override // com.android.systemui.dreams.touch.DreamTouchHandler
    public void onSessionStart(DreamTouchHandler.TouchSession touchSession) {
        VelocityTracker obtain = this.mVelocityTrackerFactory.obtain();
        this.mVelocityTracker = obtain;
        this.mTouchSession = touchSession;
        obtain.clear();
        this.mNotificationShadeWindowController.setForcePluginOpen(true, this);
        touchSession.registerCallback(new DreamTouchHandler.TouchSession.Callback() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda0
            @Override // com.android.systemui.dreams.touch.DreamTouchHandler.TouchSession.Callback
            public final void onRemoved() {
                BouncerSwipeTouchHandler.$r8$lambda$1UFO0cLyASHb0LxeuKC6yIIhGUU(BouncerSwipeTouchHandler.this);
            }
        });
        touchSession.registerGestureListener(this.mOnGestureListener);
        touchSession.registerInputListener(new InputChannelCompat.InputEventListener() { // from class: com.android.systemui.dreams.touch.BouncerSwipeTouchHandler$$ExternalSyntheticLambda1
            public final void onInputEvent(InputEvent inputEvent) {
                BouncerSwipeTouchHandler.$r8$lambda$m8Xiir1cJIqFocL2l5eMHZ1FpHw(BouncerSwipeTouchHandler.this, inputEvent);
            }
        });
    }

    public final void setPanelExpansion(float f, float f2) {
        this.mCurrentExpansion = f;
        this.mStatusBarKeyguardViewManager.onPanelExpansionChanged(new ShadeExpansionChangeEvent(f, false, true, f2));
    }
}