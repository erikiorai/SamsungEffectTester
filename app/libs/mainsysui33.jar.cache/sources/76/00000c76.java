package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import com.android.settingslib.fuelgauge.BatteryStatus;
import java.util.TimeZone;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitorCallback.class */
public class KeyguardUpdateMonitorCallback {
    public void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
    }

    public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
    }

    public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
    }

    public void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
    }

    public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
    }

    public void onBiometricsCleared() {
    }

    public void onDevicePolicyManagerStateChanged() {
    }

    public void onDeviceProvisioned() {
    }

    public void onDreamingStateChanged(boolean z) {
    }

    public void onEmergencyCallAction() {
    }

    @Deprecated
    public void onFinishedGoingToSleep(int i) {
    }

    public void onKeyguardBouncerFullyShowingChanged(boolean z) {
    }

    public void onKeyguardBouncerStateChanged(boolean z) {
    }

    public void onKeyguardDismissAnimationFinished() {
    }

    public void onKeyguardVisibilityChanged(boolean z) {
    }

    public void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
    }

    public void onLogoutEnabledChanged() {
    }

    public void onNonStrongBiometricAllowedChanged(int i) {
    }

    public void onPhoneStateChanged(int i) {
    }

    public void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
    }

    public void onRefreshCarrierInfo() {
    }

    public void onRequireUnlockForNfc() {
    }

    public void onSecondaryLockscreenRequirementChanged(int i) {
    }

    public void onShadeExpandedChanged(boolean z) {
    }

    public void onSimStateChanged(int i, int i2, int i3) {
    }

    @Deprecated
    public void onStartedGoingToSleep(int i) {
    }

    @Deprecated
    public void onStartedWakingUp() {
    }

    public void onStrongAuthStateChanged(int i) {
    }

    public void onTelephonyCapable(boolean z) {
    }

    public void onTimeChanged() {
    }

    public void onTimeFormatChanged(String str) {
    }

    public void onTimeZoneChanged(TimeZone timeZone) {
    }

    public void onTrustAgentErrorMessage(CharSequence charSequence) {
    }

    public void onTrustChanged(int i) {
    }

    public void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
    }

    public void onTrustManagedChanged(int i) {
    }

    public void onUserSwitchComplete(int i) {
    }

    public void onUserSwitching(int i) {
    }

    public void onUserUnlocked() {
    }
}