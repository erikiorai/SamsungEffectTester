package com.android.keyguard;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.PluralsMessageFormatter;
import android.view.KeyEvent;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardAbsKeyInputView;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.ViewController;
import java.util.HashMap;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardAbsKeyInputViewController.class */
public abstract class KeyguardAbsKeyInputViewController<T extends KeyguardAbsKeyInputView> extends KeyguardInputViewController<T> {
    public CountDownTimer mCountdownTimer;
    public boolean mDismissing;
    public final EmergencyButtonController.EmergencyButtonCallback mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public final FalsingCollector mFalsingCollector;
    public final KeyguardAbsKeyInputView.KeyDownListener mKeyDownListener;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public AsyncTask<?, ?, ?> mPendingLockCheck;
    public boolean mResumed;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0.onKeyDown(int, android.view.KeyEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$jwKsJ0xDLiDNB3cU1kMIoemzMw8(KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController, int i, KeyEvent keyEvent) {
        return keyguardAbsKeyInputViewController.lambda$new$0(i, keyEvent);
    }

    public KeyguardAbsKeyInputViewController(T t, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController) {
        super(t, securityMode, keyguardSecurityCallback, emergencyButtonController, factory);
        this.mKeyDownListener = new KeyguardAbsKeyInputView.KeyDownListener() { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController$$ExternalSyntheticLambda0
            @Override // com.android.keyguard.KeyguardAbsKeyInputView.KeyDownListener
            public final boolean onKeyDown(int i, KeyEvent keyEvent) {
                return KeyguardAbsKeyInputViewController.$r8$lambda$jwKsJ0xDLiDNB3cU1kMIoemzMw8(KeyguardAbsKeyInputViewController.this, i, keyEvent);
            }
        };
        this.mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.1
            {
                KeyguardAbsKeyInputViewController.this = this;
            }

            @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
            public void onEmergencyButtonClickedWhenInCall() {
                KeyguardAbsKeyInputViewController.this.getKeyguardSecurityCallback().reset();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        this.mLatencyTracker = latencyTracker;
        this.mFalsingCollector = falsingCollector;
        this.mEmergencyButtonController = emergencyButtonController;
    }

    public /* synthetic */ boolean lambda$new$0(int i, KeyEvent keyEvent) {
        if (i != 0) {
            onUserInput();
            return false;
        }
        return false;
    }

    public void handleAttemptLockout(long j) {
        ((KeyguardAbsKeyInputView) ((ViewController) this).mView).setPasswordEntryEnabled(false);
        this.mCountdownTimer = new CountDownTimer(((long) Math.ceil((j - SystemClock.elapsedRealtime()) / 1000.0d)) * 1000, 1000L) { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.2
            {
                KeyguardAbsKeyInputViewController.this = this;
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                KeyguardAbsKeyInputViewController.this.mMessageAreaController.setMessage("");
                KeyguardAbsKeyInputViewController.this.resetState();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(round));
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mMessageAreaController.setMessage(PluralsMessageFormatter.format(((KeyguardAbsKeyInputView) ((ViewController) keyguardAbsKeyInputViewController).mView).getResources(), hashMap, R$string.kg_too_many_failed_attempts_countdown), false);
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onInit() {
        super.onInit();
    }

    public void onPasswordChecked(int i, boolean z, int i2, boolean z2) {
        boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
        if (z) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, true, 0);
            if (z3) {
                this.mDismissing = true;
                this.mLatencyTracker.onActionStart(11);
                getKeyguardSecurityCallback().dismiss(true, i, getSecurityMode());
                return;
            }
            return;
        }
        if (z2) {
            getKeyguardSecurityCallback().reportUnlockAttempt(i, false, i2);
            if (i2 > 0) {
                handleAttemptLockout(this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
            }
        }
        if (i2 == 0) {
            this.mMessageAreaController.setMessage(((KeyguardAbsKeyInputView) ((ViewController) this).mView).getWrongPasswordStringId());
        }
        ((KeyguardAbsKeyInputView) ((ViewController) this).mView).resetPasswordText(true, false);
        startErrorAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        this.mResumed = false;
        CountDownTimer countDownTimer = this.mCountdownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        AsyncTask<?, ?, ?> asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
            this.mPendingLockCheck = null;
        }
        reset();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        this.mResumed = true;
    }

    public void onUserInput() {
        this.mFalsingCollector.updateFalseConfidence(FalsingClassifier.Result.passed(0.6d));
        getKeyguardSecurityCallback().userActivity();
        getKeyguardSecurityCallback().onUserInput();
        this.mMessageAreaController.setMessage("");
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputView) ((ViewController) this).mView).setKeyDownListener(this.mKeyDownListener);
        this.mEmergencyButtonController.setEmergencyButtonCallback(this.mEmergencyButtonCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset() {
        this.mDismissing = false;
        ((KeyguardAbsKeyInputView) ((ViewController) this).mView).resetPasswordText(false, false);
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (shouldLockout(lockoutAttemptDeadline)) {
            handleAttemptLockout(lockoutAttemptDeadline);
        } else {
            resetState();
        }
    }

    public abstract void resetState();

    public boolean shouldLockout(long j) {
        return j != 0;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showMessage(CharSequence charSequence, ColorStateList colorStateList) {
        KeyguardMessageAreaController<BouncerKeyguardMessageArea> keyguardMessageAreaController = this.mMessageAreaController;
        if (keyguardMessageAreaController == null) {
            return;
        }
        if (colorStateList != null) {
            keyguardMessageAreaController.setNextMessageColor(colorStateList);
        }
        this.mMessageAreaController.setMessage(charSequence);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        int promptReasonStringRes;
        if (i == 0 || (promptReasonStringRes = ((KeyguardAbsKeyInputView) ((ViewController) this).mView).getPromptReasonStringRes(i)) == 0) {
            return;
        }
        this.mMessageAreaController.setMessage(((KeyguardAbsKeyInputView) ((ViewController) this).mView).getResources().getString(promptReasonStringRes), false);
    }

    public void startErrorAnimation() {
    }

    public void verifyPasswordAndUnlock() {
        if (this.mDismissing) {
            return;
        }
        final LockscreenCredential enteredCredential = ((KeyguardAbsKeyInputView) ((ViewController) this).mView).getEnteredCredential();
        ((KeyguardAbsKeyInputView) ((ViewController) this).mView).setPasswordEntryInputEnabled(false);
        AsyncTask<?, ?, ?> asyncTask = this.mPendingLockCheck;
        if (asyncTask != null) {
            asyncTask.cancel(false);
        }
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (enteredCredential.size() <= 3) {
            ((KeyguardAbsKeyInputView) ((ViewController) this).mView).setPasswordEntryInputEnabled(true);
            onPasswordChecked(currentUser, false, 0, false);
            enteredCredential.zeroize();
            return;
        }
        this.mLatencyTracker.onActionStart(3);
        this.mLatencyTracker.onActionStart(4);
        this.mKeyguardUpdateMonitor.setCredentialAttempted();
        this.mPendingLockCheck = LockPatternChecker.checkCredential(this.mLockPatternUtils, enteredCredential, currentUser, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.3
            {
                KeyguardAbsKeyInputViewController.this = this;
            }

            public void onCancelled() {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                enteredCredential.zeroize();
            }

            public void onChecked(boolean z, int i) {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                ((KeyguardAbsKeyInputView) ((ViewController) KeyguardAbsKeyInputViewController.this).mView).setPasswordEntryInputEnabled(true);
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mPendingLockCheck = null;
                if (!z) {
                    keyguardAbsKeyInputViewController.onPasswordChecked(currentUser, false, i, true);
                }
                enteredCredential.zeroize();
            }

            public void onEarlyMatched() {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(3);
                KeyguardAbsKeyInputViewController.this.onPasswordChecked(currentUser, true, 0, true);
                enteredCredential.zeroize();
            }
        });
    }
}