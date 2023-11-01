package com.android.keyguard;

import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.PluralsMessageFormatter;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.ViewController;
import java.util.HashMap;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPatternViewController.class */
public class KeyguardPatternViewController extends KeyguardInputViewController<KeyguardPatternView> {
    public Runnable mCancelPatternRunnable;
    public CountDownTimer mCountdownTimer;
    public EmergencyButtonController.EmergencyButtonCallback mEmergencyButtonCallback;
    public final EmergencyButtonController mEmergencyButtonController;
    public final FalsingCollector mFalsingCollector;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public LockPatternView mLockPatternView;
    public AsyncTask<?, ?, ?> mPendingLockCheck;
    public final DevicePostureController.Callback mPostureCallback;
    public final DevicePostureController mPostureController;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPatternViewController$UnlockPatternListener.class */
    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        public UnlockPatternListener() {
            KeyguardPatternViewController.this = r4;
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> list) {
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
            KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
        }

        public final void onPatternChecked(int i, boolean z, int i2, boolean z2) {
            boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
            if (z) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, true, 0);
                if (z3) {
                    KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                    KeyguardPatternViewController.this.mLatencyTracker.onActionStart(11);
                    KeyguardPatternViewController.this.getKeyguardSecurityCallback().dismiss(true, i, KeyguardSecurityModel.SecurityMode.Pattern);
                    return;
                }
                return;
            }
            KeyguardPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            if (z2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, false, i2);
                if (i2 > 0) {
                    KeyguardPatternViewController.this.handleAttemptLockout(KeyguardPatternViewController.this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2));
                }
            }
            if (i2 == 0) {
                KeyguardPatternViewController.this.mMessageAreaController.setMessage(R$string.kg_wrong_pattern);
                KeyguardPatternViewController.this.mLockPatternView.postDelayed(KeyguardPatternViewController.this.mCancelPatternRunnable, 2000L);
            }
        }

        public void onPatternCleared() {
        }

        public void onPatternDetected(List<LockPatternView.Cell> list) {
            KeyguardPatternViewController.this.mKeyguardUpdateMonitor.setCredentialAttempted();
            KeyguardPatternViewController.this.mLockPatternView.disableInput();
            if (KeyguardPatternViewController.this.mPendingLockCheck != null) {
                KeyguardPatternViewController.this.mPendingLockCheck.cancel(false);
            }
            final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (list.size() < 4) {
                if (list.size() == 1) {
                    KeyguardPatternViewController.this.mFalsingCollector.updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, getClass().getSimpleName(), "empty pattern input"));
                }
                KeyguardPatternViewController.this.mLockPatternView.enableInput();
                onPatternChecked(currentUser, false, 0, false);
                return;
            }
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(3);
            KeyguardPatternViewController.this.mLatencyTracker.onActionStart(4);
            KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
            keyguardPatternViewController.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardPatternViewController.mLockPatternUtils, LockscreenCredential.createPattern(list, KeyguardPatternViewController.this.mLockPatternUtils.getLockPatternSize(currentUser)), currentUser, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener.1
                {
                    UnlockPatternListener.this = this;
                }

                public void onCancelled() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                }

                public void onChecked(boolean z, int i) {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(4);
                    KeyguardPatternViewController.this.mLockPatternView.enableInput();
                    KeyguardPatternViewController.this.mPendingLockCheck = null;
                    if (z) {
                        return;
                    }
                    UnlockPatternListener.this.onPatternChecked(currentUser, false, i, true);
                }

                public void onEarlyMatched() {
                    KeyguardPatternViewController.this.mLatencyTracker.onActionEnd(3);
                    UnlockPatternListener.this.onPatternChecked(currentUser, true, 0, true);
                }
            });
            if (list.size() > 2) {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().userActivity();
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
            }
        }

        public void onPatternStart() {
            KeyguardPatternViewController.this.mLockPatternView.removeCallbacks(KeyguardPatternViewController.this.mCancelPatternRunnable);
            KeyguardPatternViewController.this.mMessageAreaController.setMessage("");
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda0.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    /* renamed from: $r8$lambda$NC0LzAXv-ChW6ybDgJBCwl0n8IA */
    public static /* synthetic */ boolean m614$r8$lambda$NC0LzAXvChW6ybDgJBCwl0n8IA(KeyguardPatternViewController keyguardPatternViewController, View view, MotionEvent motionEvent) {
        return keyguardPatternViewController.lambda$onViewAttached$1(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda2.onPostureChanged(int):void] */
    public static /* synthetic */ void $r8$lambda$rCrHmdCFDbPgH6FHvjvU7vz668U(KeyguardPatternViewController keyguardPatternViewController, int i) {
        keyguardPatternViewController.lambda$new$0(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$sczUUS-PeCpNTiVnvYDgwRhExNc */
    public static /* synthetic */ void m615$r8$lambda$sczUUSPeCpNTiVnvYDgwRhExNc(KeyguardPatternViewController keyguardPatternViewController, View view) {
        keyguardPatternViewController.lambda$onViewAttached$2(view);
    }

    public KeyguardPatternViewController(KeyguardPatternView keyguardPatternView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, DevicePostureController devicePostureController) {
        super(keyguardPatternView, securityMode, keyguardSecurityCallback, emergencyButtonController, factory);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda2
            public final void onPostureChanged(int i) {
                KeyguardPatternViewController.$r8$lambda$rCrHmdCFDbPgH6FHvjvU7vz668U(KeyguardPatternViewController.this, i);
            }
        };
        this.mEmergencyButtonCallback = new EmergencyButtonController.EmergencyButtonCallback() { // from class: com.android.keyguard.KeyguardPatternViewController.1
            {
                KeyguardPatternViewController.this = this;
            }

            @Override // com.android.keyguard.EmergencyButtonController.EmergencyButtonCallback
            public void onEmergencyButtonClickedWhenInCall() {
                KeyguardPatternViewController.this.getKeyguardSecurityCallback().reset();
            }
        };
        this.mCancelPatternRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardPatternViewController.2
            {
                KeyguardPatternViewController.this = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                KeyguardPatternViewController.this.mLockPatternView.clearPattern();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mLockPatternUtils = lockPatternUtils;
        this.mLatencyTracker = latencyTracker;
        this.mFalsingCollector = falsingCollector;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mLockPatternView = ((KeyguardPatternView) ((ViewController) this).mView).findViewById(R$id.lockPatternView);
        this.mPostureController = devicePostureController;
    }

    public /* synthetic */ void lambda$new$0(int i) {
        ((KeyguardPatternView) ((ViewController) this).mView).onDevicePostureChanged(i);
    }

    public /* synthetic */ boolean lambda$onViewAttached$1(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mFalsingCollector.avoidGesture();
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$onViewAttached$2(View view) {
        getKeyguardSecurityCallback().reset();
        getKeyguardSecurityCallback().onCancelClicked();
    }

    public final void displayDefaultSecurityMessage() {
        this.mMessageAreaController.setMessage(getInitialMessageResId());
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R$string.keyguard_enter_your_pattern;
    }

    public final void handleAttemptLockout(long j) {
        this.mLockPatternView.clearPattern();
        this.mLockPatternView.setEnabled(false);
        this.mCountdownTimer = new CountDownTimer(((long) Math.ceil((j - SystemClock.elapsedRealtime()) / 1000.0d)) * 1000, 1000L) { // from class: com.android.keyguard.KeyguardPatternViewController.3
            {
                KeyguardPatternViewController.this = this;
            }

            @Override // android.os.CountDownTimer
            public void onFinish() {
                KeyguardPatternViewController.this.mLockPatternView.setEnabled(true);
                KeyguardPatternViewController.this.displayDefaultSecurityMessage();
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                HashMap hashMap = new HashMap();
                hashMap.put("count", Integer.valueOf(round));
                KeyguardPatternViewController keyguardPatternViewController = KeyguardPatternViewController.this;
                keyguardPatternViewController.mMessageAreaController.setMessage(PluralsMessageFormatter.format(((KeyguardPatternView) ((ViewController) keyguardPatternViewController).mView).getResources(), hashMap, R$string.kg_too_many_failed_attempts_countdown), false);
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

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        super.onPause();
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
        displayDefaultSecurityMessage();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mLockPatternView.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setSaveEnabled(false);
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser()));
        this.mLockPatternView.setLockPatternUtils(this.mLockPatternUtils);
        this.mLockPatternView.setLockPatternSize(this.mLockPatternUtils.getLockPatternSize(currentUser));
        this.mLockPatternView.setVisibleDots(this.mLockPatternUtils.isVisibleDotsEnabled(currentUser));
        this.mLockPatternView.setShowErrorPath(this.mLockPatternUtils.isShowErrorPath(currentUser));
        this.mLockPatternView.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return KeyguardPatternViewController.m614$r8$lambda$NC0LzAXvChW6ybDgJBCwl0n8IA(KeyguardPatternViewController.this, view, motionEvent);
            }
        });
        this.mEmergencyButtonController.setEmergencyButtonCallback(this.mEmergencyButtonCallback);
        View findViewById = ((KeyguardPatternView) ((ViewController) this).mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPatternViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPatternViewController.m615$r8$lambda$sczUUSPeCpNTiVnvYDgwRhExNc(KeyguardPatternViewController.this, view);
                }
            });
        }
        this.mPostureController.addCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mLockPatternView.setOnPatternListener((LockPatternView.OnPatternListener) null);
        this.mLockPatternView.setOnTouchListener((View.OnTouchListener) null);
        this.mEmergencyButtonController.setEmergencyButtonCallback(null);
        View findViewById = ((KeyguardPatternView) ((ViewController) this).mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(null);
        }
        this.mPostureController.removeCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void reset() {
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser()));
        this.mLockPatternView.enableInput();
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(KeyguardUpdateMonitor.getCurrentUser());
        if (lockoutAttemptDeadline != 0) {
            handleAttemptLockout(lockoutAttemptDeadline);
        } else {
            displayDefaultSecurityMessage();
        }
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
        switch (i) {
            case 0:
                return;
            case 1:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_restart_pattern);
                return;
            case 2:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
                return;
            case 3:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_device_admin);
                return;
            case 4:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_user_request);
                return;
            case 5:
            default:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
                return;
            case 6:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
                return;
            case 7:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
                return;
            case 8:
                this.mMessageAreaController.setMessage(R$string.kg_prompt_reason_timeout_pattern);
                return;
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void startAppearAnimation() {
        super.startAppearAnimation();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardPatternView) ((ViewController) this).mView).startDisappearAnimation(this.mKeyguardUpdateMonitor.needsSlowUnlockTransition(), runnable);
    }
}