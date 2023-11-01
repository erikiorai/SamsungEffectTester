package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.util.TypedValue;
import android.view.accessibility.AccessibilityManager;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollHelper.class */
public class UdfpsEnrollHelper {
    public final boolean mAccessibilityEnabled;
    public final Context mContext;
    public final int mEnrollReason;
    public final FingerprintManager mFingerprintManager;
    public final List<PointF> mGuidedEnrollmentPoints;
    public Listener mListener;
    public int mTotalSteps = -1;
    public int mRemainingSteps = -1;
    public int mLocationsEnrolled = 0;
    public int mCenterTouchCount = 0;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollHelper$Listener.class */
    public interface Listener {
        void onEnrollmentHelp(int i, int i2);

        void onEnrollmentProgress(int i, int i2);

        void onLastStepAcquired();
    }

    public UdfpsEnrollHelper(Context context, FingerprintManager fingerprintManager, int i) {
        this.mContext = context;
        this.mFingerprintManager = fingerprintManager;
        this.mEnrollReason = i;
        this.mAccessibilityEnabled = ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).isEnabled();
        ArrayList arrayList = new ArrayList();
        this.mGuidedEnrollmentPoints = arrayList;
        float applyDimension = TypedValue.applyDimension(5, 1.0f, context.getResources().getDisplayMetrics());
        if (!(Settings.Secure.getIntForUser(context.getContentResolver(), "com.android.systemui.biometrics.UdfpsNewCoords", 0, -2) != 0) || (!Build.IS_ENG && !Build.IS_USERDEBUG)) {
            Log.v("UdfpsEnrollHelper", "Using old coordinates");
            arrayList.add(new PointF(2.0f * applyDimension, ActionBarShadowController.ELEVATION_LOW * applyDimension));
            arrayList.add(new PointF(0.87f * applyDimension, (-2.7f) * applyDimension));
            float f = (-1.8f) * applyDimension;
            arrayList.add(new PointF(f, (-1.31f) * applyDimension));
            arrayList.add(new PointF(f, 1.31f * applyDimension));
            arrayList.add(new PointF(0.88f * applyDimension, 2.7f * applyDimension));
            arrayList.add(new PointF(3.94f * applyDimension, (-1.06f) * applyDimension));
            arrayList.add(new PointF(2.9f * applyDimension, (-4.14f) * applyDimension));
            arrayList.add(new PointF((-0.52f) * applyDimension, (-5.95f) * applyDimension));
            float f2 = (-3.33f) * applyDimension;
            arrayList.add(new PointF(f2, f2));
            arrayList.add(new PointF((-3.99f) * applyDimension, (-0.35f) * applyDimension));
            arrayList.add(new PointF((-3.62f) * applyDimension, 2.54f * applyDimension));
            arrayList.add(new PointF((-1.49f) * applyDimension, 5.57f * applyDimension));
            arrayList.add(new PointF(2.29f * applyDimension, 4.92f * applyDimension));
            arrayList.add(new PointF(3.82f * applyDimension, applyDimension * 1.78f));
            return;
        }
        Log.v("UdfpsEnrollHelper", "Using new coordinates");
        float f3 = (-0.15f) * applyDimension;
        arrayList.add(new PointF(f3, (-1.02f) * applyDimension));
        arrayList.add(new PointF(f3, 1.02f * applyDimension));
        float f4 = ActionBarShadowController.ELEVATION_LOW * applyDimension;
        arrayList.add(new PointF(0.29f * applyDimension, f4));
        float f5 = 2.17f * applyDimension;
        arrayList.add(new PointF(f5, (-2.35f) * applyDimension));
        float f6 = 1.07f * applyDimension;
        arrayList.add(new PointF(f6, (-3.96f) * applyDimension));
        float f7 = (-0.37f) * applyDimension;
        arrayList.add(new PointF(f7, (-4.31f) * applyDimension));
        float f8 = (-1.69f) * applyDimension;
        arrayList.add(new PointF(f8, (-3.29f) * applyDimension));
        float f9 = (-2.48f) * applyDimension;
        arrayList.add(new PointF(f9, (-1.23f) * applyDimension));
        arrayList.add(new PointF(f9, 1.23f * applyDimension));
        arrayList.add(new PointF(f8, 3.29f * applyDimension));
        arrayList.add(new PointF(f7, 4.31f * applyDimension));
        arrayList.add(new PointF(f6, 3.96f * applyDimension));
        arrayList.add(new PointF(f5, 2.35f * applyDimension));
        arrayList.add(new PointF(applyDimension * 2.58f, f4));
    }

    public void animateIfLastStep() {
        Listener listener = this.mListener;
        if (listener == null) {
            Log.e("UdfpsEnrollHelper", "animateIfLastStep, null listener");
            return;
        }
        int i = this.mRemainingSteps;
        if (i > 2 || i < 0) {
            return;
        }
        listener.onLastStepAcquired();
    }

    public PointF getNextGuidedEnrollmentPoint() {
        if (this.mAccessibilityEnabled || !isGuidedEnrollmentStage()) {
            return new PointF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        }
        float f = 0.5f;
        if (Build.IS_ENG || Build.IS_USERDEBUG) {
            f = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "com.android.systemui.biometrics.UdfpsEnrollHelper.scale", 0.5f, -2);
        }
        int i = this.mLocationsEnrolled;
        int i2 = this.mCenterTouchCount;
        List<PointF> list = this.mGuidedEnrollmentPoints;
        PointF pointF = list.get((i - i2) % list.size());
        return new PointF(pointF.x * f, pointF.y * f);
    }

    public int getStageThresholdSteps(int i, int i2) {
        return Math.round(i * this.mFingerprintManager.getEnrollStageThreshold(i2));
    }

    public boolean isCenterEnrollmentStage() {
        int i = this.mTotalSteps;
        boolean z = true;
        if (i != -1) {
            int i2 = this.mRemainingSteps;
            z = i2 == -1 ? true : i - i2 < getStageThresholdSteps(i, 0);
        }
        return z;
    }

    public boolean isEdgeEnrollmentStage() {
        int i = this.mTotalSteps;
        boolean z = false;
        if (i != -1) {
            int i2 = this.mRemainingSteps;
            if (i2 == -1) {
                z = false;
            } else {
                z = false;
                if (i - i2 >= getStageThresholdSteps(i, 2)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public boolean isGuidedEnrollmentStage() {
        boolean z = false;
        if (!this.mAccessibilityEnabled) {
            int i = this.mTotalSteps;
            z = false;
            if (i != -1) {
                int i2 = this.mRemainingSteps;
                if (i2 == -1) {
                    z = false;
                } else {
                    int i3 = i - i2;
                    z = false;
                    if (i3 >= getStageThresholdSteps(i, 0)) {
                        z = false;
                        if (i3 < getStageThresholdSteps(this.mTotalSteps, 1)) {
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public boolean isTipEnrollmentStage() {
        int i = this.mTotalSteps;
        boolean z = false;
        if (i != -1) {
            int i2 = this.mRemainingSteps;
            if (i2 == -1) {
                z = false;
            } else {
                int i3 = i - i2;
                z = false;
                if (i3 >= getStageThresholdSteps(i, 1)) {
                    z = false;
                    if (i3 < getStageThresholdSteps(this.mTotalSteps, 2)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }

    public void onEnrollmentHelp() {
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentHelp(this.mRemainingSteps, this.mTotalSteps);
        }
    }

    public void onEnrollmentProgress(int i) {
        if (this.mTotalSteps == -1) {
            this.mTotalSteps = i;
        }
        if (i != this.mRemainingSteps) {
            this.mLocationsEnrolled++;
            if (isCenterEnrollmentStage()) {
                this.mCenterTouchCount++;
            }
        }
        this.mRemainingSteps = i;
        Listener listener = this.mListener;
        if (listener != null) {
            listener.onEnrollmentProgress(i, this.mTotalSteps);
        }
    }

    public void setListener(Listener listener) {
        int i;
        this.mListener = listener;
        if (listener == null || (i = this.mTotalSteps) == -1) {
            return;
        }
        listener.onEnrollmentProgress(this.mRemainingSteps, i);
    }

    public boolean shouldShowProgressBar() {
        return this.mEnrollReason == 2;
    }
}