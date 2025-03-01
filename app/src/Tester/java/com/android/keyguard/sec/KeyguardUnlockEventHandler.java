package com.android.keyguard.sec;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.aj.effect.MainActivity;
import com.aj.effect.R;

public class KeyguardUnlockEventHandler {
    private static final boolean DEBUG = false; // todo debug logs
    private static final String TAG = "KeyguardUnlockEventHandler";
    private UnlockCallback mCallback = null;
    private final int mFirstBorder;
    private final int mSecondBorder;
    private float mStartX;
    private float mStartY;
    private KeyguardEffectViewBase mUnlockView;
    private double mDistance = 0.0d;
    private boolean mIsKeyguardDismissing = false;
    private boolean mIsMultiTouch = false;
    private boolean mIsUnlockStarted = false;
    private boolean mIsIgnoreTouch = false;
    private final Context mContext;

    /* loaded from: classes.dex */
    public interface UnlockCallback {
        void onUnlockExecuted();

        void onUnlockViewPressed();

        void onUnlockViewReleased();

        void onUnlockViewSwiped(boolean z);

        void userActivity();
    }

    public KeyguardUnlockEventHandler(KeyguardEffectViewBase unlockView, Context context) {
        this.mUnlockView = unlockView;
        //this.mCallback = callback;
        mContext = context;
        Resources res = context.getResources();
        this.mFirstBorder = (int) res.getDimension(R.dimen.keyguard_lockscreen_first_border);
        this.mSecondBorder = (int) res.getDimension(R.dimen.keyguard_lockscreen_second_border);
    }

    @SuppressLint("LongLogTag")
    public boolean handleTouchEvent(View view, MotionEvent event) {
        Handler handler = new Handler();
        if (DEBUG)
            Log.d(TAG, "mIsUnlockStarted - " + this.mIsUnlockStarted);
        int action = event.getAction();
        int maskedAction = action & 255;
        if (mIsUnlockStarted) {
            return true;
        }
        if (mIsIgnoreTouch) {
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                mIsIgnoreTouch = false;
                mIsMultiTouch = false;
                if (mCallback != null) {
                    mCallback.onUnlockViewReleased();
                }
            }
            if (mUnlockView == null) {
                return false;
            }
            return mUnlockView.handleTouchEvent(view, event);
        }
        float touchedEventX = event.getX();
        float touchedEventY = event.getY();
        long delay = 0;
        switch (maskedAction) {
            case MotionEvent.ACTION_DOWN:
                mStartX = touchedEventX;
                mStartY = touchedEventY;
                mDistance = 0.0d;
                if (mCallback != null) {
                    mCallback.onUnlockViewPressed();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (event.getPointerCount() <= 1) {
                    mIsMultiTouch = false;
                    if (DEBUG)
                        Log.d(TAG, "mIsMultiTouch false");
                }
                if (mCallback != null) {
                    mCallback.onUnlockViewReleased();
                }
                if (DEBUG)
                    Log.d(TAG, "ACTION_UP mDistance: " + mDistance);
                //TODO : unlock switch
                if (mFirstBorder < mDistance && mDistance < mSecondBorder && !mIsMultiTouch && MainActivity.unlockBool) {
                    mIsUnlockStarted = true;
                    if (mUnlockView != null) {
                        mUnlockView.handleUnlock(view, event);
                        delay = mUnlockView.getUnlockDelay();

                        handler.postDelayed(() -> {
                            MainActivity.switchActivity(mContext);
                        }, delay+250L);
                    }
                    if (mCallback != null) {
                        ((View) mCallback).postDelayed(() -> {
                            if (mCallback != null) {
                                mCallback.userActivity();
                                launch();
                            }
                        }, delay);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCallback != null) {
                    mCallback.userActivity();
                }
                int diffX = (int) (touchedEventX - mStartX);
                int diffY = (int) (touchedEventY - mStartY);
                mDistance = Math.sqrt(Math.pow(diffX, 2.0d) + Math.pow(diffY, 2.0d));
                if (view != null) {
                    if (view.getHeight() / 2.0 < mDistance) {
                        if (mCallback != null) {
                            mCallback.onUnlockViewSwiped(true);
                        }
                    } else if (mCallback != null) {
                        mCallback.onUnlockViewSwiped(false);
                    }
                }
                if (mDistance >= mSecondBorder && !mIsMultiTouch && MainActivity.unlockBool) {
                    mIsUnlockStarted = true;
                    if (mUnlockView != null) {
                        mUnlockView.handleUnlock(view, event);
                        delay = mUnlockView.getUnlockDelay();

                        handler.postDelayed(() -> {
                            MainActivity.switchActivity(mContext);
                        }, delay+250L);
                    }
                    if (mCallback != null) {
                        ((View) mCallback).postDelayed(() -> {
                            if (mCallback != null) {
                                mCallback.userActivity();
                                launch();
                                mCallback.onUnlockViewReleased();
                            }
                        }, delay);
                        break;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (event.getPointerCount() <= 1) {
                    mIsMultiTouch = false;
                    if (DEBUG)
                        Log.d(TAG, "mIsMultiTouch false");
                }
                if (mCallback != null) {
                    mCallback.onUnlockViewReleased();
                }
                mDistance = 0.0d;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() >= 2) {
                    mIsMultiTouch = true;
                    if (DEBUG)
                        Log.d(TAG, "mIsMultiTouch true");
                } else {
                    mIsMultiTouch = false;
                    if (DEBUG)
                        Log.d(TAG, "mIsMultiTouch false");
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                int pointer_index = (MotionEvent.ACTION_POINTER_INDEX_MASK & action) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                int pointer_id = event.getPointerId(pointer_index);
                if (pointer_id == 0) {
                    mIsIgnoreTouch = true;
                }
                if (event.getPointerCount() <= 1) {
                }
                if (mCallback != null) {
                    mCallback.onUnlockViewReleased();
                    break;
                }
                mDistance = 0.0d;
                break;
        }
        if (mUnlockView == null) {
            return false;
        }
        return mUnlockView.handleTouchEvent(view, event);
    }

    public boolean handleHoverEvent(View view, MotionEvent event) {
        return mUnlockView.handleHoverEvent(view, event);
    }

    @SuppressLint("LongLogTag")
    private void launch() {
        Log.d(TAG, "launch() - mIsKeyguardDismissing=" + mIsKeyguardDismissing);
        if (!mIsKeyguardDismissing) {
            mIsKeyguardDismissing = true;
            if (mCallback != null) {
                mCallback.onUnlockExecuted();
                /*if (mCallback instanceof SecKeyguardCircleView) {
                    mUnlockView.reset();
                }*/
            }
        }
    }

    @SuppressLint("LongLogTag")
    public void reset() {
        Log.d(TAG, "reset()");
        mIsUnlockStarted = false;
        mIsIgnoreTouch = false;
        mDistance = 0.0d;
        mIsKeyguardDismissing = false;
    }

    @SuppressLint("LongLogTag")
    public void cleanUp() {
        Log.d(TAG, "cleanUp()");
        mUnlockView = null;
        mCallback = null;
    }
}