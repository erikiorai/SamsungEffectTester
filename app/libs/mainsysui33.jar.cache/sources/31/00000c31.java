package com.android.keyguard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.telephony.PinResult;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSimPukViewController;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPukViewController.class */
public class KeyguardSimPukViewController extends KeyguardPinBasedInputViewController<KeyguardSimPukView> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public CheckSimPuk mCheckSimPukThread;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public String mPinText;
    public String mPukText;
    public int mRemainingAttempts;
    public AlertDialog mRemainingAttemptsDialog;
    public boolean mShowDefaultMessage;
    public ImageView mSimImageView;
    public ProgressDialog mSimUnlockProgressDialog;
    public StateMachine mStateMachine;
    public int mSubId;
    public final TelephonyManager mTelephonyManager;
    public KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* renamed from: com.android.keyguard.KeyguardSimPukViewController$3 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPukViewController$3.class */
    public class AnonymousClass3 extends CheckSimPuk {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSimPukViewController$3$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$D4YXQJ16o3cMhvtpU1r8aJ4UUVw(AnonymousClass3 anonymousClass3, PinResult pinResult) {
            anonymousClass3.lambda$onSimLockChangedResponse$0(pinResult);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(String str, String str2, int i) {
            super(str, str2, i);
            KeyguardSimPukViewController.this = r7;
        }

        public /* synthetic */ void lambda$onSimLockChangedResponse$0(PinResult pinResult) {
            if (KeyguardSimPukViewController.this.mSimUnlockProgressDialog != null) {
                KeyguardSimPukViewController.this.mSimUnlockProgressDialog.hide();
            }
            ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).resetPasswordText(true, pinResult.getResult() != 0);
            if (pinResult.getResult() == 0) {
                KeyguardSimPukViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(KeyguardSimPukViewController.this.mSubId);
                KeyguardSimPukViewController.this.mRemainingAttempts = -1;
                KeyguardSimPukViewController.this.mShowDefaultMessage = true;
                KeyguardSimPukViewController.this.getKeyguardSecurityCallback().dismiss(true, KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPuk);
            } else {
                KeyguardSimPukViewController.this.mShowDefaultMessage = false;
                if (pinResult.getResult() == 1) {
                    KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
                    keyguardSimPukViewController.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController).mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext(), KeyguardSimPukViewController.this.mSubId)));
                    if (pinResult.getAttemptsRemaining() <= 2) {
                        KeyguardSimPukViewController.this.getPukRemainingAttemptsDialog(pinResult.getAttemptsRemaining()).show();
                    } else {
                        KeyguardSimPukViewController keyguardSimPukViewController2 = KeyguardSimPukViewController.this;
                        keyguardSimPukViewController2.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController2).mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), false, KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext(), KeyguardSimPukViewController.this.mSubId)));
                    }
                } else {
                    KeyguardSimPukViewController keyguardSimPukViewController3 = KeyguardSimPukViewController.this;
                    keyguardSimPukViewController3.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController3).mView).getResources().getString(R$string.kg_password_puk_failed));
                }
                if (KeyguardSimPukViewController.DEBUG) {
                    Log.d("KeyguardSimPukView", "verifyPasswordAndUnlock  UpdateSim.onSimCheckResponse:  attemptsRemaining=" + pinResult.getAttemptsRemaining());
                }
            }
            KeyguardSimPukViewController.this.mStateMachine.reset();
            KeyguardSimPukViewController.this.mCheckSimPukThread = null;
        }

        @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
        public void onSimLockChangedResponse(final PinResult pinResult) {
            ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPukViewController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSimPukViewController.AnonymousClass3.$r8$lambda$D4YXQJ16o3cMhvtpU1r8aJ4UUVw(KeyguardSimPukViewController.AnonymousClass3.this, pinResult);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPukViewController$CheckSimPuk.class */
    public abstract class CheckSimPuk extends Thread {
        public final String mPin;
        public final String mPuk;
        public final int mSubId;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSimPukViewController$CheckSimPuk$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$wlV7vzwZ_sTGeFwZR9-x0WzSmNE */
        public static /* synthetic */ void m692$r8$lambda$wlV7vzwZ_sTGeFwZR9x0WzSmNE(CheckSimPuk checkSimPuk, PinResult pinResult) {
            checkSimPuk.lambda$run$0(pinResult);
        }

        public CheckSimPuk(String str, String str2, int i) {
            KeyguardSimPukViewController.this = r4;
            this.mPuk = str;
            this.mPin = str2;
            this.mSubId = i;
        }

        /* renamed from: onSimLockChangedResponse */
        public abstract void lambda$run$0(PinResult pinResult);

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            if (KeyguardSimPukViewController.DEBUG) {
                Log.v("KeyguardSimPukView", "call supplyIccLockPuk(subid=" + this.mSubId + ")");
            }
            final PinResult supplyIccLockPuk = KeyguardSimPukViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId).supplyIccLockPuk(this.mPuk, this.mPin);
            if (KeyguardSimPukViewController.DEBUG) {
                Log.v("KeyguardSimPukView", "supplyIccLockPuk returned: " + supplyIccLockPuk.toString());
            }
            ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPukViewController$CheckSimPuk$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSimPukViewController.CheckSimPuk.m692$r8$lambda$wlV7vzwZ_sTGeFwZR9x0WzSmNE(KeyguardSimPukViewController.CheckSimPuk.this, supplyIccLockPuk);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPukViewController$StateMachine.class */
    public class StateMachine {
        public int mState;

        public StateMachine() {
            KeyguardSimPukViewController.this = r4;
            this.mState = 0;
        }

        public void next() {
            int i;
            int i2 = this.mState;
            if (i2 == 0) {
                if (KeyguardSimPukViewController.this.checkPuk()) {
                    this.mState = 1;
                    i = R$string.kg_puk_enter_pin_hint;
                } else {
                    i = R$string.kg_invalid_sim_puk_hint;
                }
            } else if (i2 == 1) {
                if (KeyguardSimPukViewController.this.checkPin()) {
                    this.mState = 2;
                    i = R$string.kg_enter_confirm_pin_hint;
                } else {
                    i = R$string.kg_invalid_sim_pin_hint;
                }
            } else if (i2 != 2) {
                i = 0;
            } else if (KeyguardSimPukViewController.this.confirmPin()) {
                this.mState = 3;
                i = R$string.keyguard_sim_unlock_progress_dialog_message;
                KeyguardSimPukViewController.this.updateSim();
            } else {
                this.mState = 1;
                i = R$string.kg_invalid_confirm_pin_hint;
            }
            ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).resetPasswordText(true, true);
            if (i != 0) {
                KeyguardSimPukViewController.this.mMessageAreaController.setMessage(i);
            }
        }

        public void reset() {
            KeyguardSimPukViewController.this.mPinText = "";
            KeyguardSimPukViewController.this.mPukText = "";
            this.mState = 0;
            KeyguardSimPukViewController.this.handleSubInfoChangeIfNeeded();
            if (KeyguardSimPukViewController.this.mShowDefaultMessage) {
                KeyguardSimPukViewController.this.showDefaultMessage();
            }
            ((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).setESimLocked(KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext(), KeyguardSimPukViewController.this.mSubId), KeyguardSimPukViewController.this.mSubId);
            KeyguardSimPukViewController.this.mPasswordEntry.requestFocus();
        }
    }

    public KeyguardSimPukViewController(KeyguardSimPukView keyguardSimPukView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController) {
        super(keyguardSimPukView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector);
        this.mStateMachine = new StateMachine();
        this.mSubId = -1;
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPukViewController.1
            {
                KeyguardSimPukViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                if (KeyguardSimPukViewController.DEBUG) {
                    Log.v("KeyguardSimPukView", "onSimStateChanged(subId=" + i + ",state=" + i3 + ")");
                }
                if (i3 != 5) {
                    KeyguardSimPukViewController.this.resetState();
                    return;
                }
                KeyguardSimPukViewController.this.mRemainingAttempts = -1;
                KeyguardSimPukViewController.this.mShowDefaultMessage = true;
                KeyguardSimPukViewController.this.getKeyguardSecurityCallback().dismiss(true, KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPuk);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mSimImageView = (ImageView) ((KeyguardSimPukView) ((ViewController) this).mView).findViewById(R$id.keyguard_sim);
    }

    public final boolean checkPin() {
        int length = this.mPasswordEntry.getText().length();
        if (length < 4 || length > 8) {
            return false;
        }
        this.mPinText = this.mPasswordEntry.getText();
        return true;
    }

    public final boolean checkPuk() {
        if (this.mPasswordEntry.getText().length() == 8) {
            this.mPukText = this.mPasswordEntry.getText();
            return true;
        }
        return false;
    }

    public boolean confirmPin() {
        return this.mPinText.equals(this.mPasswordEntry.getText());
    }

    public final Dialog getPukRemainingAttemptsDialog(int i) {
        View view = ((ViewController) this).mView;
        String pukPasswordErrorMessage = ((KeyguardSimPukView) view).getPukPasswordErrorMessage(i, false, KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) view).getContext(), this.mSubId));
        AlertDialog alertDialog = this.mRemainingAttemptsDialog;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(((KeyguardSimPukView) ((ViewController) this).mView).getContext());
            builder.setMessage(pukPasswordErrorMessage);
            builder.setCancelable(false);
            builder.setNeutralButton(R$string.ok, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            this.mRemainingAttemptsDialog = create;
            create.getWindow().setType(2009);
        } else {
            alertDialog.setMessage(pukPasswordErrorMessage);
        }
        return this.mRemainingAttemptsDialog;
    }

    public final Dialog getSimUnlockProgressDialog() {
        if (this.mSimUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((KeyguardSimPukView) ((ViewController) this).mView).getContext());
            this.mSimUnlockProgressDialog = progressDialog;
            progressDialog.setMessage(((KeyguardSimPukView) ((ViewController) this).mView).getResources().getString(R$string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            if (!(((KeyguardSimPukView) ((ViewController) this).mView).getContext() instanceof Activity)) {
                this.mSimUnlockProgressDialog.getWindow().setType(2009);
            }
        }
        return this.mSimUnlockProgressDialog;
    }

    public final void handleSubInfoChangeIfNeeded() {
        int nextSubIdForState = this.mKeyguardUpdateMonitor.getNextSubIdForState(3);
        if (nextSubIdForState == this.mSubId || !SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            return;
        }
        this.mSubId = nextSubIdForState;
        this.mShowDefaultMessage = true;
        this.mRemainingAttempts = -1;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        ProgressDialog progressDialog = this.mSimUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        if (this.mShowDefaultMessage) {
            showDefaultMessage();
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        super.resetState();
        this.mStateMachine.reset();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public boolean shouldLockout(long j) {
        return false;
    }

    public final void showDefaultMessage() {
        String string;
        int i = this.mRemainingAttempts;
        if (i >= 0) {
            KeyguardMessageAreaController<BouncerKeyguardMessageArea> keyguardMessageAreaController = this.mMessageAreaController;
            View view = ((ViewController) this).mView;
            keyguardMessageAreaController.setMessage(((KeyguardSimPukView) view).getPukPasswordErrorMessage(i, true, KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) view).getContext(), this.mSubId)));
            return;
        }
        boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) ((ViewController) this).mView).getContext(), this.mSubId);
        TelephonyManager telephonyManager = this.mTelephonyManager;
        int activeModemCount = telephonyManager != null ? telephonyManager.getActiveModemCount() : 1;
        Resources resources = ((KeyguardSimPukView) ((ViewController) this).mView).getResources();
        TypedArray obtainStyledAttributes = ((KeyguardSimPukView) ((ViewController) this).mView).getContext().obtainStyledAttributes(new int[]{16842904});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        if (activeModemCount < 2) {
            string = resources.getString(R$string.kg_puk_enter_puk_hint);
        } else {
            SubscriptionInfo subscriptionInfoForSubId = this.mKeyguardUpdateMonitor.getSubscriptionInfoForSubId(this.mSubId);
            string = resources.getString(R$string.kg_puk_enter_puk_hint_multi, subscriptionInfoForSubId != null ? subscriptionInfoForSubId.getDisplayName() : "");
            if (subscriptionInfoForSubId != null) {
                color = subscriptionInfoForSubId.getIconTint();
            }
        }
        String str = string;
        if (isEsimLocked) {
            str = resources.getString(R$string.kg_sim_lock_esim_instructions, string);
        }
        this.mMessageAreaController.setMessage(str);
        this.mSimImageView.setImageTintList(ColorStateList.valueOf(color));
        new CheckSimPuk("", "", this.mSubId) { // from class: com.android.keyguard.KeyguardSimPukViewController.2
            {
                KeyguardSimPukViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardSimPukViewController.CheckSimPuk
            public void onSimLockChangedResponse(PinResult pinResult) {
                if (pinResult == null) {
                    Log.e("KeyguardSimPukView", "onSimCheckResponse, pin result is NULL");
                    return;
                }
                Log.d("KeyguardSimPukView", "onSimCheckResponse  empty One result " + pinResult.toString());
                if (pinResult.getAttemptsRemaining() >= 0) {
                    KeyguardSimPukViewController.this.mRemainingAttempts = pinResult.getAttemptsRemaining();
                    KeyguardSimPukViewController keyguardSimPukViewController = KeyguardSimPukViewController.this;
                    keyguardSimPukViewController.mMessageAreaController.setMessage(((KeyguardSimPukView) ((ViewController) keyguardSimPukViewController).mView).getPukPasswordErrorMessage(pinResult.getAttemptsRemaining(), true, KeyguardEsimArea.isEsimLocked(((KeyguardSimPukView) ((ViewController) KeyguardSimPukViewController.this).mView).getContext(), KeyguardSimPukViewController.this.mSubId)));
                }
            }
        }.start();
    }

    public final void updateSim() {
        getSimUnlockProgressDialog().show();
        if (this.mCheckSimPukThread == null) {
            AnonymousClass3 anonymousClass3 = new AnonymousClass3(this.mPukText, this.mPinText, this.mSubId);
            this.mCheckSimPukThread = anonymousClass3;
            anonymousClass3.start();
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void verifyPasswordAndUnlock() {
        this.mStateMachine.next();
    }
}