package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthDialog;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFingerprintView.class */
public class AuthBiometricFingerprintView extends AuthBiometricView {
    public boolean isUdfps;
    public AuthController.ScaleFactorProvider scaleFactorProvider;
    public UdfpsDialogMeasureAdapter udfpsAdapter;

    public AuthBiometricFingerprintView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ AuthBiometricFingerprintView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public AuthIconController createIconController() {
        return new AuthBiometricFingerprintIconController(((LinearLayout) this).mContext, this.mIconView, this.mIconViewOverlay);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public int getDelayAfterAuthenticatedDurationMs() {
        return 500;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public int getStateForAfterError() {
        return 2;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void handleResetAfterError() {
        showTouchSensorString();
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void handleResetAfterHelp() {
        showTouchSensorString();
    }

    public final boolean isUdfps() {
        return this.isUdfps;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        showTouchSensorString();
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        if (udfpsDialogMeasureAdapter != null) {
            int bottomSpacerHeight = udfpsDialogMeasureAdapter.getBottomSpacerHeight();
            Log.w("AuthBiometricFingerprintView", "bottomSpacerHeight: " + bottomSpacerHeight);
            if (bottomSpacerHeight < 0) {
                View findViewById = findViewById(R$id.biometric_icon_frame);
                Intrinsics.checkNotNull(findViewById);
                FrameLayout frameLayout = (FrameLayout) findViewById;
                float f = -bottomSpacerHeight;
                frameLayout.setTranslationY(f);
                View findViewById2 = findViewById(R$id.indicator);
                Intrinsics.checkNotNull(findViewById2);
                ((TextView) findViewById2).setTranslationY(f);
            }
        }
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        AuthDialog.LayoutParams onMeasureInternal = super.onMeasureInternal(i, i2);
        AuthController.ScaleFactorProvider scaleFactorProvider = this.scaleFactorProvider;
        float provide = scaleFactorProvider != null ? scaleFactorProvider.provide() : 1.0f;
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        AuthDialog.LayoutParams onMeasureInternal2 = udfpsDialogMeasureAdapter != null ? udfpsDialogMeasureAdapter.onMeasureInternal(i, i2, onMeasureInternal, provide) : null;
        if (onMeasureInternal2 == null) {
            onMeasureInternal2 = onMeasureInternal;
        }
        return onMeasureInternal2;
    }

    public final void setScaleFactorProvider(AuthController.ScaleFactorProvider scaleFactorProvider) {
        this.scaleFactorProvider = scaleFactorProvider;
    }

    public final void setSensorProperties(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        boolean isAnyUdfpsType = fingerprintSensorPropertiesInternal.isAnyUdfpsType();
        this.isUdfps = isAnyUdfpsType;
        this.udfpsAdapter = isAnyUdfpsType ? new UdfpsDialogMeasureAdapter(this, fingerprintSensorPropertiesInternal) : null;
    }

    public final void showTouchSensorString() {
        this.mIndicatorView.setText(R$string.fingerprint_dialog_touch_sensor);
        this.mIndicatorView.setTextColor(this.mTextColorHint);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean supportsSmallDialog() {
        return false;
    }

    public final void updateOverrideIconLayoutParamsSize() {
        UdfpsDialogMeasureAdapter udfpsDialogMeasureAdapter = this.udfpsAdapter;
        if (udfpsDialogMeasureAdapter != null) {
            AuthController.ScaleFactorProvider scaleFactorProvider = this.scaleFactorProvider;
            int sensorDiameter = udfpsDialogMeasureAdapter.getSensorDiameter(scaleFactorProvider != null ? scaleFactorProvider.provide() : 1.0f);
            AuthIconController authIconController = this.mIconController;
            AuthBiometricFingerprintIconController authBiometricFingerprintIconController = authIconController instanceof AuthBiometricFingerprintIconController ? (AuthBiometricFingerprintIconController) authIconController : null;
            if (authBiometricFingerprintIconController == null) {
                return;
            }
            authBiometricFingerprintIconController.setIconLayoutParamSize(new Pair<>(Integer.valueOf(sensorDiameter), Integer.valueOf(sensorDiameter)));
        }
    }
}