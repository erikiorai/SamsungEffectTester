package com.android.systemui.classifier;

import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/DiagonalClassifier.class */
public class DiagonalClassifier extends FalsingClassifier {
    public final float mHorizontalAngleRange;
    public final float mVerticalAngleRange;

    public DiagonalClassifier(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        this.mHorizontalAngleRange = deviceConfigProxy.getFloat("systemui", "brightline_falsing_diagonal_horizontal_angle_range", 0.08726646f);
        this.mVerticalAngleRange = deviceConfigProxy.getFloat("systemui", "brightline_falsing_diagonal_horizontal_angle_range", 0.08726646f);
    }

    public final boolean angleBetween(float f, float f2, float f3) {
        float normalizeAngle = normalizeAngle(f2);
        float normalizeAngle2 = normalizeAngle(f3);
        boolean z = true;
        if (normalizeAngle > normalizeAngle2) {
            boolean z2 = true;
            if (f < normalizeAngle) {
                z2 = f <= normalizeAngle2;
            }
            return z2;
        }
        if (f < normalizeAngle || f > normalizeAngle2) {
            z = false;
        }
        return z;
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public FalsingClassifier.Result calculateFalsingResult(int i, double d, double d2) {
        float angle = getAngle();
        if (angle == Float.MAX_VALUE) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        if (i == 5 || i == 6 || i == 14) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        float f = this.mHorizontalAngleRange;
        float f2 = 0.7853982f - f;
        float f3 = f + 0.7853982f;
        if (isVertical()) {
            float f4 = this.mVerticalAngleRange;
            f2 = 0.7853982f - f4;
            f3 = f4 + 0.7853982f;
        }
        return angleBetween(angle, f2, f3) || angleBetween(angle, f2 + 1.5707964f, f3 + 1.5707964f) || angleBetween(angle, f2 - 1.5707964f, f3 - 1.5707964f) || angleBetween(angle, f2 + 3.1415927f, f3 + 3.1415927f) ? falsed(0.5d, getReason()) : FalsingClassifier.Result.passed(0.5d);
    }

    public final String getReason() {
        return String.format(null, "{angle=%f, vertical=%s}", Float.valueOf(getAngle()), Boolean.valueOf(isVertical()));
    }

    public final float normalizeAngle(float f) {
        if (f < ActionBarShadowController.ELEVATION_LOW) {
            return (f % 6.2831855f) + 6.2831855f;
        }
        float f2 = f;
        if (f > 6.2831855f) {
            f2 = f % 6.2831855f;
        }
        return f2;
    }
}