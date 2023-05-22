package com.aj.effect.lock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.aj.effect.R;

public class KeyguardUnlockView extends FrameLayout {
    protected KeyguardEffectViewBase mUnlockView;

    private static final String TAG = "KeyguardUnlockEventHandler";
    private float mStartX;
    private float mStartY;
    private double mDistance = 0.0d;
    private boolean mIsMultiTouch = false;
    private boolean mIsIgnoreTouch = false;

    //protected ImageView imageView;
    protected Context mContext;
    //private View fadeView;
    //private ObjectAnimator objectAnimator;
    private boolean mIsUnlockStarted;
    private int mFirstBorder;
    private int mSecondBorder;

    Button reset;

    public KeyguardUnlockView(Context context) {
        this(context, null);
    }

    public KeyguardUnlockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KeyguardUnlockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mContext = context;
        Resources res = mContext.getResources();
        this.mFirstBorder = (int) res.getDimension(R.dimen.keyguard_lockscreen_first_border);
        this.mSecondBorder = (int) res.getDimension(R.dimen.keyguard_lockscreen_second_border);

        mUnlockView = KeyguardEffectViewBrilliantRing.getInstance(mContext);
        if (mUnlockView != null) {
            mUnlockView.update();
            mUnlockView.show();
        }
        this.addView((View) mUnlockView);
    }

    /* private void animateTo(View view, float f) {
        if (view != null) {
            if (this.objectAnimator != null) {
                this.objectAnimator.cancel();
            }
            this.objectAnimator = ObjectAnimator.ofFloat(view, "alpha", f);
            this.objectAnimator.start();
        }
    } */

    private void delay(long delay) {
        postDelayed(new Runnable() { // from class: com.galaxytheme.common.KeyguardUnlockView.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i("KeyguardUnlockView", "finish, this = " + KeyguardUnlockView.this);
                //KeyguardUnlockView.this.mGalaxyLockscreen.authenticate(true, KeyguardUnlockView.this);
            }
        }, delay);
    }

    /* renamed from: a */
    @SuppressLint("LongLogTag")
    public boolean handleTouchEvent(View view, MotionEvent event) {
        //Log.d(TAG, "mIsUnlockStarted - " + this.mIsUnlockStarted);
        int action = event.getAction();
        int maskedAction = action & 255;
        float touchedEventX = event.getRawX();
        float touchedEventY = event.getRawY();
        //Log.d(TAG, "handleTouchEvent: action is " + action + ", masked action is " + maskedAction);
        if (this.mIsUnlockStarted) {
            return true;
        }
        switch (maskedAction) {
            case 0:
                this.mStartX = touchedEventX;
                this.mStartY = touchedEventY;
                this.mDistance = 0.0d;
                break;
            case 1:
                if (event.getPointerCount() <= 1) {
                    mIsMultiTouch = false;
                    Log.d(TAG, "mIsMultiTouch false");
                }
                Log.d(TAG, "ACTION_UP mDistance: " + this.mDistance);
                if (mFirstBorder < this.mDistance && this.mDistance < this.mSecondBorder && !this.mIsMultiTouch) {
                    mIsUnlockStarted = true;
                    long delay = 0;
                    if (mUnlockView != null) {
                        mUnlockView.handleUnlock(view, event);
                        delay = mUnlockView.getUnlockDelay();
                    }
                    visible();
                    delay(delay);
                }
                break;
            case 2:
                int diffX = (int) (touchedEventX - this.mStartX);
                int diffY = (int) (touchedEventY - this.mStartY);
                this.mDistance = Math.sqrt(Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d));
                if (view != null) {
                    if (view.getHeight() / 2 < this.mDistance) {
                    } else {
                    }
                }
                if (this.mDistance >= this.mSecondBorder && !this.mIsMultiTouch) {
                    mIsUnlockStarted = true;
                    long delay2 = 0;
                    if (mUnlockView != null) {
                        mUnlockView.handleUnlock(view, event);
                        delay2 = mUnlockView.getUnlockDelay();
                    }
                    visible();
                    delay(delay2);
                }
                break;
            case 3:
                if (event.getPointerCount() <= 1) {
                    this.mIsMultiTouch = false;
                    Log.d(TAG, "mIsMultiTouch false");
                }
                this.mDistance = 0.0d;
                break;
            case 5:
                if (event.getPointerCount() >= 2) {
                    this.mIsMultiTouch = true;
                    Log.d(TAG, "mIsMultiTouch true");
                    break;
                } else {
                    this.mIsMultiTouch = false;
                    Log.d(TAG, "mIsMultiTouch false");
                    break;
                }
            case 6:
                int pointer_index = (65280 & action) >> 8;
                int pointer_id = event.getPointerId(pointer_index);
                if (pointer_id == 0) {
                    this.mIsIgnoreTouch = true;
                }
                if (event.getPointerCount() <= 1) {
                }
                this.mDistance = 0.0d;
                break;
        }
        if (this.mUnlockView == null) {
            return false;
        }
        return mUnlockView.handleTouchEvent(view, event);
    }

    private void visible() {
        if (reset == null) {
            reset = findViewById(R.id.button);
            if (reset == null) {
                Log.w(TAG, "handleTouchEvent: reset button is still null", new NullPointerException());
            }
        } else {
            reset.setVisibility(VISIBLE);
        }
    }

    public void setReset(Button reset) {
        this.reset = reset;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return handleTouchEvent((View) this.mUnlockView, motionEvent);
    }


    /* public void setFadeView(View view) {
        this.fadeView = view;
    }

    public void setUnlockView(KeyguardEffectViewBase eVar) {
        if (!(eVar == null || ((View) eVar).getParent() == null)) {
            View view = (View) eVar;
            ((ViewGroup) view.getParent()).removeView(view);
        }
        this.mUnlockView = eVar;
    }

    public void setWindowInsets(Rect rect) {
        Log.i("draw", "KeyguardUnlockView, setWindowInsets, mWallpaperSurface = " + this.mWallpaperSurfaceView);
        if (this.mWallpaperSurfaceView != null) {
            this.mWallpaperSurfaceView.setWindowInsets(rect);
        }
    } */
}
