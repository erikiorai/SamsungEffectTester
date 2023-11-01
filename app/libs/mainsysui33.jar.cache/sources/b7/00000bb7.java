package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$id;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardInputViewController.class */
public abstract class KeyguardInputViewController<T extends KeyguardInputView> extends ViewController<T> implements KeyguardSecurityView {
    public final EmergencyButton mEmergencyButton;
    public final EmergencyButtonController mEmergencyButtonController;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public KeyguardMessageAreaController<BouncerKeyguardMessageArea> mMessageAreaController;
    public KeyguardSecurityCallback mNullCallback;
    public boolean mPaused;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardInputViewController$Factory.class */
    public static class Factory {
        public final DevicePostureController mDevicePostureController;
        public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
        public final FalsingCollector mFalsingCollector;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final KeyguardViewController mKeyguardViewController;
        public final LatencyTracker mLatencyTracker;
        public final LiftToActivateListener mLiftToActivateListener;
        public final LockPatternUtils mLockPatternUtils;
        public final DelayableExecutor mMainExecutor;
        public final KeyguardMessageAreaController.Factory mMessageAreaControllerFactory;
        public final Resources mResources;
        public final TelephonyManager mTelephonyManager;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, LatencyTracker latencyTracker, KeyguardMessageAreaController.Factory factory, InputMethodManager inputMethodManager, DelayableExecutor delayableExecutor, Resources resources, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController.Factory factory2, DevicePostureController devicePostureController, KeyguardViewController keyguardViewController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mLatencyTracker = latencyTracker;
            this.mMessageAreaControllerFactory = factory;
            this.mInputMethodManager = inputMethodManager;
            this.mMainExecutor = delayableExecutor;
            this.mResources = resources;
            this.mLiftToActivateListener = liftToActivateListener;
            this.mTelephonyManager = telephonyManager;
            this.mEmergencyButtonControllerFactory = factory2;
            this.mFalsingCollector = falsingCollector;
            this.mDevicePostureController = devicePostureController;
            this.mKeyguardViewController = keyguardViewController;
        }

        public KeyguardInputViewController create(KeyguardInputView keyguardInputView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback) {
            EmergencyButtonController create = this.mEmergencyButtonControllerFactory.create((EmergencyButton) keyguardInputView.findViewById(R$id.emergency_call_button));
            if (keyguardInputView instanceof KeyguardPatternView) {
                return new KeyguardPatternViewController((KeyguardPatternView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mLatencyTracker, this.mFalsingCollector, create, this.mMessageAreaControllerFactory, this.mDevicePostureController);
            }
            if (keyguardInputView instanceof KeyguardPasswordView) {
                return new KeyguardPasswordViewController((KeyguardPasswordView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mInputMethodManager, create, this.mMainExecutor, this.mResources, this.mFalsingCollector, this.mKeyguardViewController);
            }
            if (keyguardInputView instanceof KeyguardPINView) {
                return new KeyguardPinViewController((KeyguardPINView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mDevicePostureController);
            }
            if (keyguardInputView instanceof KeyguardSimPinView) {
                return new KeyguardSimPinViewController((KeyguardSimPinView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, this.mTelephonyManager, this.mFalsingCollector, create);
            }
            if (keyguardInputView instanceof KeyguardSimPukView) {
                return new KeyguardSimPukViewController((KeyguardSimPukView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, this.mTelephonyManager, this.mFalsingCollector, create);
            }
            throw new RuntimeException("Unable to find controller for " + keyguardInputView);
        }
    }

    public KeyguardInputViewController(T t, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory) {
        super(t);
        this.mNullCallback = new KeyguardSecurityCallback() { // from class: com.android.keyguard.KeyguardInputViewController.1
            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode2) {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode2) {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void onUserInput() {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reportUnlockAttempt(int i, boolean z, int i2) {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void reset() {
            }

            @Override // com.android.keyguard.KeyguardSecurityCallback
            public void userActivity() {
            }
        };
        this.mSecurityMode = securityMode;
        this.mKeyguardSecurityCallback = keyguardSecurityCallback;
        this.mEmergencyButton = t == null ? null : (EmergencyButton) t.findViewById(R$id.emergency_call_button);
        this.mEmergencyButtonController = emergencyButtonController;
        if (factory != null) {
            try {
                KeyguardMessageAreaController<BouncerKeyguardMessageArea> create = factory.create((BouncerKeyguardMessageArea) t.requireViewById(R$id.bouncer_message_area));
                this.mMessageAreaController = create;
                create.init();
                this.mMessageAreaController.setIsVisible(true);
            } catch (IllegalArgumentException e) {
                Log.e("KeyguardInputViewController", "Ensure that a BouncerKeyguardMessageArea is included in the layout");
            }
        }
    }

    public int getIndexIn(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper) {
        return keyguardSecurityViewFlipper.indexOfChild(((ViewController) this).mView);
    }

    public abstract int getInitialMessageResId();

    public KeyguardSecurityCallback getKeyguardSecurityCallback() {
        return this.mPaused ? this.mNullCallback : this.mKeyguardSecurityCallback;
    }

    public KeyguardSecurityModel.SecurityMode getSecurityMode() {
        return this.mSecurityMode;
    }

    public void onInit() {
        this.mEmergencyButtonController.init();
    }

    public void onPause() {
        this.mPaused = true;
    }

    public void onResume(int i) {
        this.mPaused = false;
    }

    public void onViewAttached() {
    }

    public void onViewDetached() {
    }

    public void reset() {
    }

    public void showMessage(CharSequence charSequence, ColorStateList colorStateList) {
    }

    public void showPromptReason(int i) {
    }

    public void startAppearAnimation() {
        if (TextUtils.isEmpty(this.mMessageAreaController.getMessage()) && getInitialMessageResId() != 0) {
            this.mMessageAreaController.setMessage(((KeyguardInputView) ((ViewController) this).mView).getResources().getString(getInitialMessageResId()), false);
        }
        ((KeyguardInputView) ((ViewController) this).mView).startAppearAnimation();
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardInputView) ((ViewController) this).mView).startDisappearAnimation(runnable);
    }
}