package com.android.systemui.accessibility;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/MagnificationGestureDetector.class */
public class MagnificationGestureDetector {
    public final Runnable mCancelTapGestureRunnable;
    public final Handler mHandler;
    public final OnGestureListener mOnGestureListener;
    public int mTouchSlopSquare;
    public final PointF mPointerDown = new PointF();
    public final PointF mPointerLocation = new PointF(Float.NaN, Float.NaN);
    public boolean mDetectSingleTap = true;
    public boolean mDraggingDetected = false;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/MagnificationGestureDetector$OnGestureListener.class */
    public interface OnGestureListener {
        boolean onDrag(float f, float f2);

        boolean onFinish(float f, float f2);

        boolean onSingleTap();

        boolean onStart(float f, float f2);
    }

    public MagnificationGestureDetector(Context context, Handler handler, OnGestureListener onGestureListener) {
        int scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mTouchSlopSquare = scaledTouchSlop * scaledTouchSlop;
        this.mHandler = handler;
        this.mOnGestureListener = onGestureListener;
        this.mCancelTapGestureRunnable = new Runnable() { // from class: com.android.systemui.accessibility.MagnificationGestureDetector$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MagnificationGestureDetector.this.lambda$new$0();
            }
        };
    }

    public static boolean isLocationValid(PointF pointF) {
        return (Float.isNaN(pointF.x) || Float.isNaN(pointF.y)) ? false : true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        this.mDetectSingleTap = false;
    }

    public static void resetPointF(PointF pointF) {
        pointF.x = Float.NaN;
        pointF.y = Float.NaN;
    }

    public final boolean notifyDraggingGestureIfNeeded(float f, float f2) {
        if (this.mDraggingDetected) {
            if (!isLocationValid(this.mPointerLocation)) {
                this.mPointerLocation.set(this.mPointerDown);
            }
            PointF pointF = this.mPointerLocation;
            float f3 = pointF.x;
            float f4 = pointF.y;
            pointF.set(f, f2);
            return this.mOnGestureListener.onDrag(f - f3, f2 - f4);
        }
        return false;
    }

    public boolean onTouch(MotionEvent motionEvent) {
        boolean onStart;
        float rawX = motionEvent.getRawX();
        float rawY = motionEvent.getRawY();
        int actionMasked = motionEvent.getActionMasked();
        boolean z = false;
        boolean z2 = false;
        if (actionMasked != 0) {
            if (actionMasked == 1) {
                stopSingleTapDetectionIfNeeded(rawX, rawY);
                if (this.mDetectSingleTap) {
                    z2 = false | this.mOnGestureListener.onSingleTap();
                }
            } else if (actionMasked == 2) {
                stopSingleTapDetectionIfNeeded(rawX, rawY);
                onStart = notifyDraggingGestureIfNeeded(rawX, rawY);
            } else if (actionMasked != 3) {
                if (actionMasked == 5) {
                    stopSingleTapDetection();
                }
                return z;
            }
            z = z2 | this.mOnGestureListener.onFinish(rawX, rawY);
            reset();
            return z;
        }
        this.mPointerDown.set(rawX, rawY);
        this.mHandler.postAtTime(this.mCancelTapGestureRunnable, motionEvent.getDownTime() + ViewConfiguration.getLongPressTimeout());
        onStart = this.mOnGestureListener.onStart(rawX, rawY);
        z = false | onStart;
        return z;
    }

    public final void reset() {
        resetPointF(this.mPointerDown);
        resetPointF(this.mPointerLocation);
        this.mHandler.removeCallbacks(this.mCancelTapGestureRunnable);
        this.mDetectSingleTap = true;
        this.mDraggingDetected = false;
    }

    public final void stopSingleTapDetection() {
        this.mHandler.removeCallbacks(this.mCancelTapGestureRunnable);
        this.mDetectSingleTap = false;
    }

    public final void stopSingleTapDetectionIfNeeded(float f, float f2) {
        if (!this.mDraggingDetected && isLocationValid(this.mPointerDown)) {
            PointF pointF = this.mPointerDown;
            int i = (int) (pointF.x - f);
            int i2 = (int) (pointF.y - f2);
            if ((i * i) + (i2 * i2) > this.mTouchSlopSquare) {
                this.mDraggingDetected = true;
                stopSingleTapDetection();
            }
        }
    }
}