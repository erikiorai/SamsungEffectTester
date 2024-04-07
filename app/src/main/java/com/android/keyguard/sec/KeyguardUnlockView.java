package com.android.keyguard.sec;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class KeyguardUnlockView extends FrameLayout { //KeyguardUnlockEventHandler.UnlockCallback {
    private static final String TAG = "KeyguardUnlockView";
    private final int FADE_IN_OUT_ANIMATION_DURATION = 300;
    private Context mContext;
    private AlphaAnimation mFadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
    private AlphaAnimation mFadeOutAnimation = new AlphaAnimation(1.0f, 0.0f);
    private KeyguardUnlockEventHandler mKeyguardUnlockEventHandler;
    private long mResumedTimeMillis;
    private KeyguardEffectViewBase mUnlockView;
    private Handler mHandler = new Handler();

    public KeyguardUnlockView(Context context) {
        this(context, null);
    }

    public KeyguardUnlockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mResumedTimeMillis = System.currentTimeMillis();
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.mFadeInAnimation.setFillAfter(true);
        this.mFadeInAnimation.setDuration(300L);
        this.mFadeOutAnimation.setFillAfter(true);
        this.mFadeOutAnimation.setDuration(100L);
        this.mKeyguardUnlockEventHandler = new KeyguardUnlockEventHandler(KeyguardEffectViewController.getInstance(context), context);
        this.mContext = context;

    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mUnlockView = KeyguardEffectViewController.getInstance(this.mContext);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mKeyguardUnlockEventHandler != null) {
            this.mKeyguardUnlockEventHandler.cleanUp();
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return this.mKeyguardUnlockEventHandler.handleTouchEvent(null, event);
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        return mKeyguardUnlockEventHandler.handleHoverEvent(null, event);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (!hasWindowFocus) {
            this.mKeyguardUnlockEventHandler.reset();
        }
    }


    public void reset() { // todo avoid softlock
        mKeyguardUnlockEventHandler.reset();
    }
    // idk even what is this.
    /*private void pokeWakelockWithTimeCheck() {
        long now = System.currentTimeMillis();
        long diff = now - this.mResumedTimeMillis;
        if (diff <= 20000) {
            this.mCallback.userActivity();
        } else if (diff > 20000 && diff < 30000) {
            this.mCallback.userActivity();
        }
    }*/

    void doTransition(float to, View... views) {
        if (views != null) {
            for (View view : views) {
                if (view != null) {
                    view.animate().alpha(to);
                }
            }
        }
    }

    public void showUnlockAffordance() {
        Rect outRect = new Rect(0, 0, 0, 0);
        boolean isValidRect = getGlobalVisibleRect(outRect);
        Log.d(TAG, "outRect: " + outRect);
        Log.d(TAG, "isValidRect: " + isValidRect);
        if (isValidRect) {
            this.mUnlockView.showUnlockAffordance(500L, outRect);
        } else {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.sec.KeyguardUnlockView.7
                @Override // java.lang.Runnable
                public void run() {
                    Rect outRect2 = new Rect(0, 0, 0, 0);
                    boolean isValidRect2 = KeyguardUnlockView.this.getGlobalVisibleRect(outRect2);
                    Log.d(KeyguardUnlockView.TAG, "Retry isValidRect: " + isValidRect2);
                    if (isValidRect2) {
                        KeyguardUnlockView.this.mUnlockView.showUnlockAffordance(500L, outRect2);
                    }
                }
            }, 30L);
        }
    }


    // TODO: com.android.keyguard.KeyguardSecurityView important
    /*@Override // com.android.keyguard.KeyguardSecurityView
    public void startAppearAnimation() {
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean startDisappearAnimation(Runnable finishRunnable) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onResume(int reason) {
        if (reason == 1) {
            this.mResumedTimeMillis = System.currentTimeMillis();
            showUnlockAffordance();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView // todo most important
    public void showBouncer(int duration) {
        this.mIsBouncing = true;
        Rect outRect = new Rect();
        getGlobalVisibleRect(outRect);
        this.mUnlockView.showUnlockAffordance(0L, outRect);
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void hideBouncer(int duration) {
        this.mIsBouncing = false;
    }*/

    /*@Override // com.android.keyguard.sec.KeyguardUnlockEventHandler.UnlockCallback
    public void userActivity() {

    }

    @Override // com.android.keyguard.sec.KeyguardUnlockEventHandler.UnlockCallback
    public void onUnlockExecuted() {

    }

    @Override // com.android.keyguard.sec.KeyguardUnlockEventHandler.UnlockCallback
    public void onUnlockViewPressed() {
        //doTransition(1.0f, this.mFadeView, this.mAttributionView);
    }

    @Override // com.android.keyguard.sec.KeyguardUnlockEventHandler.UnlockCallback
    public void onUnlockViewReleased() {
        //doTransition(0.0f, this.mFadeView, this.mAttributionView);
    }

    @Override // com.android.keyguard.sec.KeyguardUnlockEventHandler.UnlockCallback
    public void onUnlockViewSwiped(boolean swiped) {
    } */
}