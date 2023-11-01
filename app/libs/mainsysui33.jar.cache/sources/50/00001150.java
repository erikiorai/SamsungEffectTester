package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFaceView.class */
public final class AuthBiometricFaceView extends AuthBiometricView {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFaceView$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AuthBiometricFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public /* synthetic */ AuthBiometricFaceView(Context context, AttributeSet attributeSet, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i & 2) != 0 ? null : attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public AuthIconController createIconController() {
        return new AuthBiometricFaceIconController(((LinearLayout) this).mContext, this.mIconView);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public int getDelayAfterAuthenticatedDurationMs() {
        return 500;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public int getStateForAfterError() {
        return 0;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void handleResetAfterError() {
        resetErrorView();
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void handleResetAfterHelp() {
        resetErrorView();
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void onAuthenticationFailed(int i, String str) {
        if (getSize() == 2 && supportsManualRetry()) {
            this.mTryAgainButton.setVisibility(0);
            this.mConfirmButton.setVisibility(8);
        }
        super.onAuthenticationFailed(i, str);
    }

    public final void resetErrorView() {
        this.mIndicatorView.setTextColor(this.mTextColorHint);
        this.mIndicatorView.setVisibility(4);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean supportsManualRetry() {
        return true;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean supportsRequireConfirmation() {
        return true;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean supportsSmallDialog() {
        return true;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public void updateState(int i) {
        if (i == 1 || (i == 2 && getSize() == 2)) {
            resetErrorView();
        }
        super.updateState(i);
    }
}