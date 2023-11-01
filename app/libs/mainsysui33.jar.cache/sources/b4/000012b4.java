package com.android.systemui.classifier;

import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/DistanceClassifier.class */
public class DistanceClassifier extends FalsingClassifier {
    public DistanceVectors mCachedDistance;
    public boolean mDistanceDirty;
    public final float mHorizontalFlingThresholdPx;
    public final float mHorizontalSwipeThresholdPx;
    public final float mVelocityToDistanceMultiplier;
    public final float mVerticalFlingThresholdPx;
    public final float mVerticalSwipeThresholdPx;

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/DistanceClassifier$DistanceVectors.class */
    public class DistanceVectors {
        public final float mDx;
        public final float mDy;
        public final float mVx;
        public final float mVy;

        public DistanceVectors(float f, float f2, float f3, float f4) {
            this.mDx = f;
            this.mDy = f2;
            this.mVx = f3;
            this.mVy = f4;
        }

        public String toString() {
            return String.format(null, "{dx=%f, vx=%f, dy=%f, vy=%f}", Float.valueOf(this.mDx), Float.valueOf(this.mVx), Float.valueOf(this.mDy), Float.valueOf(this.mVy));
        }
    }

    public DistanceClassifier(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        this.mVelocityToDistanceMultiplier = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_velcoity_to_distance", 30.0f);
        float f = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_fling_threshold_in", 1.0f);
        float f2 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_vertical_fling_threshold_in", 1.5f);
        float f3 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f4 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_horizontal_swipe_threshold_in", 3.0f);
        float f5 = deviceConfigProxy.getFloat("systemui", "brightline_falsing_distance_screen_fraction_max_distance", 0.8f);
        this.mHorizontalFlingThresholdPx = Math.min(getWidthPixels() * f5, f * getXdpi());
        this.mVerticalFlingThresholdPx = Math.min(getHeightPixels() * f5, f2 * getYdpi());
        this.mHorizontalSwipeThresholdPx = Math.min(getWidthPixels() * f5, f3 * getXdpi());
        this.mVerticalSwipeThresholdPx = Math.min(getHeightPixels() * f5, f4 * getYdpi());
        this.mDistanceDirty = true;
    }

    public final DistanceVectors calculateDistances() {
        List<MotionEvent> recentMotionEvents = getRecentMotionEvents();
        if (recentMotionEvents.size() < 3) {
            FalsingClassifier.logDebug("Only " + recentMotionEvents.size() + " motion events recorded.");
            return new DistanceVectors(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        }
        VelocityTracker obtain = VelocityTracker.obtain();
        for (MotionEvent motionEvent : recentMotionEvents) {
            obtain.addMovement(motionEvent);
        }
        obtain.computeCurrentVelocity(1);
        float xVelocity = obtain.getXVelocity();
        float yVelocity = obtain.getYVelocity();
        obtain.recycle();
        return new DistanceVectors(getLastMotionEvent().getX() - getFirstMotionEvent().getX(), getLastMotionEvent().getY() - getFirstMotionEvent().getY(), xVelocity, yVelocity);
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public FalsingClassifier.Result calculateFalsingResult(int i, double d, double d2) {
        if (i == 10 || i == 18 || i == 11 || i == 12 || i == 13 || i == 14 || i == 15 || i == 17) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        return !getPassedFlingThreshold() ? falsed(0.5d, getReason()) : FalsingClassifier.Result.passed(0.5d);
    }

    public final DistanceVectors getDistances() {
        if (this.mDistanceDirty) {
            this.mCachedDistance = calculateDistances();
            this.mDistanceDirty = false;
        }
        return this.mCachedDistance;
    }

    public final boolean getPassedDistanceThreshold() {
        DistanceVectors distances = getDistances();
        boolean z = true;
        if (!isHorizontal()) {
            FalsingClassifier.logDebug("Vertical swipe distance: " + Math.abs(distances.mDy));
            FalsingClassifier.logDebug("Threshold: " + this.mVerticalSwipeThresholdPx);
            return Math.abs(distances.mDy) >= this.mVerticalSwipeThresholdPx;
        }
        FalsingClassifier.logDebug("Horizontal swipe distance: " + Math.abs(distances.mDx));
        FalsingClassifier.logDebug("Threshold: " + this.mHorizontalSwipeThresholdPx);
        if (Math.abs(distances.mDx) < this.mHorizontalSwipeThresholdPx) {
            z = false;
        }
        return z;
    }

    public final boolean getPassedFlingThreshold() {
        DistanceVectors distances = getDistances();
        float f = distances.mDx;
        float f2 = distances.mVx;
        float f3 = this.mVelocityToDistanceMultiplier;
        float f4 = distances.mDy;
        float f5 = distances.mVy;
        float f6 = this.mVelocityToDistanceMultiplier;
        boolean z = true;
        if (!isHorizontal()) {
            FalsingClassifier.logDebug("Vertical swipe and fling distance: " + distances.mDy + ", " + (distances.mVy * this.mVelocityToDistanceMultiplier));
            StringBuilder sb = new StringBuilder();
            sb.append("Threshold: ");
            sb.append(this.mVerticalFlingThresholdPx);
            FalsingClassifier.logDebug(sb.toString());
            return Math.abs(f4 + (f5 * f6)) >= this.mVerticalFlingThresholdPx;
        }
        FalsingClassifier.logDebug("Horizontal swipe and fling distance: " + distances.mDx + ", " + (distances.mVx * this.mVelocityToDistanceMultiplier));
        StringBuilder sb2 = new StringBuilder();
        sb2.append("Threshold: ");
        sb2.append(this.mHorizontalFlingThresholdPx);
        FalsingClassifier.logDebug(sb2.toString());
        if (Math.abs(f + (f2 * f3)) < this.mHorizontalFlingThresholdPx) {
            z = false;
        }
        return z;
    }

    public String getReason() {
        return String.format(null, "{distanceVectors=%s, isHorizontal=%s, velocityToDistanceMultiplier=%f, horizontalFlingThreshold=%f, verticalFlingThreshold=%f, horizontalSwipeThreshold=%f, verticalSwipeThreshold=%s}", getDistances(), Boolean.valueOf(isHorizontal()), Float.valueOf(this.mVelocityToDistanceMultiplier), Float.valueOf(this.mHorizontalFlingThresholdPx), Float.valueOf(this.mVerticalFlingThresholdPx), Float.valueOf(this.mHorizontalSwipeThresholdPx), Float.valueOf(this.mVerticalSwipeThresholdPx));
    }

    public FalsingClassifier.Result isLongSwipe() {
        boolean passedDistanceThreshold = getPassedDistanceThreshold();
        FalsingClassifier.logDebug("Is longSwipe? " + passedDistanceThreshold);
        return passedDistanceThreshold ? FalsingClassifier.Result.passed(0.5d) : falsed(0.5d, getReason());
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public void onTouchEvent(MotionEvent motionEvent) {
        this.mDistanceDirty = true;
    }
}