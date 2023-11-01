package com.android.systemui.classifier;

import android.view.MotionEvent;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.plugins.FalsingManager;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingClassifier.class */
public abstract class FalsingClassifier {
    public final FalsingDataProvider mDataProvider;
    public final FalsingDataProvider.MotionEventListener mMotionEventListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingClassifier$Result.class */
    public static class Result {
        public final double mConfidence;
        public final String mContext;
        public final boolean mFalsed;
        public final String mReason;

        public Result(boolean z, double d, String str, String str2) {
            this.mFalsed = z;
            this.mConfidence = d;
            this.mContext = str;
            this.mReason = str2;
        }

        public static Result falsed(double d, String str, String str2) {
            return new Result(true, d, str, str2);
        }

        public static Result passed(double d) {
            return new Result(false, d, null, null);
        }

        public double getConfidence() {
            return this.mConfidence;
        }

        public String getReason() {
            return String.format("{context=%s reason=%s}", this.mContext, this.mReason);
        }

        public boolean isFalse() {
            return this.mFalsed;
        }
    }

    public FalsingClassifier(FalsingDataProvider falsingDataProvider) {
        FalsingDataProvider.MotionEventListener motionEventListener = new FalsingDataProvider.MotionEventListener() { // from class: com.android.systemui.classifier.FalsingClassifier$$ExternalSyntheticLambda0
            @Override // com.android.systemui.classifier.FalsingDataProvider.MotionEventListener
            public final void onMotionEvent(MotionEvent motionEvent) {
                FalsingClassifier.this.onTouchEvent(motionEvent);
            }
        };
        this.mMotionEventListener = motionEventListener;
        this.mDataProvider = falsingDataProvider;
        falsingDataProvider.addMotionEventListener(motionEventListener);
    }

    public static void logDebug(String str) {
        BrightLineFalsingManager.logDebug(str);
    }

    public static void logInfo(String str) {
        BrightLineFalsingManager.logInfo(str);
    }

    public static void logVerbose(String str) {
        BrightLineFalsingManager.logVerbose(str);
    }

    public abstract Result calculateFalsingResult(int i, double d, double d2);

    public Result classifyGesture(int i, double d, double d2) {
        return calculateFalsingResult(i, d, d2);
    }

    public void cleanup() {
        this.mDataProvider.removeMotionEventListener(this.mMotionEventListener);
    }

    public Result falsed(double d, String str) {
        return Result.falsed(d, getFalsingContext(), str);
    }

    public float getAngle() {
        return this.mDataProvider.getAngle();
    }

    public String getFalsingContext() {
        return getClass().getSimpleName();
    }

    public MotionEvent getFirstMotionEvent() {
        return this.mDataProvider.getFirstRecentMotionEvent();
    }

    public int getHeightPixels() {
        return this.mDataProvider.getHeightPixels();
    }

    public MotionEvent getLastMotionEvent() {
        return this.mDataProvider.getLastMotionEvent();
    }

    public List<MotionEvent> getPriorMotionEvents() {
        return this.mDataProvider.getPriorMotionEvents();
    }

    public List<MotionEvent> getRecentMotionEvents() {
        return this.mDataProvider.getRecentMotionEvents();
    }

    public int getWidthPixels() {
        return this.mDataProvider.getWidthPixels();
    }

    public float getXdpi() {
        return this.mDataProvider.getXdpi();
    }

    public float getYdpi() {
        return this.mDataProvider.getYdpi();
    }

    public boolean isHorizontal() {
        return this.mDataProvider.isHorizontal();
    }

    public boolean isRight() {
        return this.mDataProvider.isRight();
    }

    public boolean isUp() {
        return this.mDataProvider.isUp();
    }

    public boolean isVertical() {
        return this.mDataProvider.isVertical();
    }

    public void onProximityEvent(FalsingManager.ProximityEvent proximityEvent) {
    }

    public void onSessionEnded() {
    }

    public void onSessionStarted() {
    }

    public void onTouchEvent(MotionEvent motionEvent) {
    }
}