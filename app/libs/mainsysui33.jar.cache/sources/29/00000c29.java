package com.android.keyguard;

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
import com.android.keyguard.KeyguardSimPinViewController;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.PluralMessageFormaterKt;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPinViewController.class */
public class KeyguardSimPinViewController extends KeyguardPinBasedInputViewController<KeyguardSimPinView> {
    public CheckSimPin mCheckSimPinThread;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public int mRemainingAttempts;
    public AlertDialog mRemainingAttemptsDialog;
    public boolean mShowDefaultMessage;
    public ImageView mSimImageView;
    public ProgressDialog mSimUnlockProgressDialog;
    public int mSubId;
    public final TelephonyManager mTelephonyManager;
    public KeyguardUpdateMonitorCallback mUpdateMonitorCallback;

    /* renamed from: com.android.keyguard.KeyguardSimPinViewController$2 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPinViewController$2.class */
    public class AnonymousClass2 extends CheckSimPin {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSimPinViewController$2$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$vW5JEhkfxaRSSiE-0Jabv-no8m4 */
        public static /* synthetic */ void m671$r8$lambda$vW5JEhkfxaRSSiE0Jabvno8m4(AnonymousClass2 anonymousClass2, PinResult pinResult) {
            anonymousClass2.lambda$onSimCheckResponse$0(pinResult);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, int i) {
            super(str, i);
            KeyguardSimPinViewController.this = r6;
        }

        public /* synthetic */ void lambda$onSimCheckResponse$0(PinResult pinResult) {
            KeyguardSimPinViewController.this.mRemainingAttempts = pinResult.getAttemptsRemaining();
            if (KeyguardSimPinViewController.this.mSimUnlockProgressDialog != null) {
                KeyguardSimPinViewController.this.mSimUnlockProgressDialog.hide();
            }
            ((KeyguardSimPinView) ((ViewController) KeyguardSimPinViewController.this).mView).resetPasswordText(true, pinResult.getResult() != 0);
            if (pinResult.getResult() == 0) {
                KeyguardSimPinViewController.this.mKeyguardUpdateMonitor.reportSimUnlocked(KeyguardSimPinViewController.this.mSubId);
                KeyguardSimPinViewController.this.mRemainingAttempts = -1;
                KeyguardSimPinViewController.this.mShowDefaultMessage = true;
                KeyguardSimPinViewController.this.getKeyguardSecurityCallback().dismiss(true, KeyguardUpdateMonitor.getCurrentUser(), KeyguardSecurityModel.SecurityMode.SimPin);
            } else {
                KeyguardSimPinViewController.this.mShowDefaultMessage = false;
                if (pinResult.getResult() != 1) {
                    KeyguardSimPinViewController keyguardSimPinViewController = KeyguardSimPinViewController.this;
                    keyguardSimPinViewController.mMessageAreaController.setMessage(((KeyguardSimPinView) ((ViewController) keyguardSimPinViewController).mView).getResources().getString(R$string.kg_password_pin_failed));
                } else if (pinResult.getAttemptsRemaining() <= 2) {
                    KeyguardSimPinViewController.this.getSimRemainingAttemptsDialog(pinResult.getAttemptsRemaining()).show();
                } else {
                    KeyguardSimPinViewController keyguardSimPinViewController2 = KeyguardSimPinViewController.this;
                    keyguardSimPinViewController2.mMessageAreaController.setMessage(keyguardSimPinViewController2.getPinPasswordErrorMessage(pinResult.getAttemptsRemaining(), false));
                }
                Log.d("KeyguardSimPinView", "verifyPasswordAndUnlock  CheckSimPin.onSimCheckResponse: " + pinResult + " attemptsRemaining=" + pinResult.getAttemptsRemaining());
            }
            KeyguardSimPinViewController.this.getKeyguardSecurityCallback().userActivity();
            KeyguardSimPinViewController.this.mCheckSimPinThread = null;
        }

        @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin
        public void onSimCheckResponse(final PinResult pinResult) {
            ((KeyguardSimPinView) ((ViewController) KeyguardSimPinViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPinViewController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSimPinViewController.AnonymousClass2.m671$r8$lambda$vW5JEhkfxaRSSiE0Jabvno8m4(KeyguardSimPinViewController.AnonymousClass2.this, pinResult);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPinViewController$CheckSimPin.class */
    public abstract class CheckSimPin extends Thread {
        public final String mPin;
        public int mSubId;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSimPinViewController$CheckSimPin$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$1kOP50kSuEUYTf0LQp5XjoBxo0Q(CheckSimPin checkSimPin, PinResult pinResult) {
            checkSimPin.lambda$run$0(pinResult);
        }

        public CheckSimPin(String str, int i) {
            KeyguardSimPinViewController.this = r4;
            this.mPin = str;
            this.mSubId = i;
        }

        /* renamed from: onSimCheckResponse */
        public abstract void lambda$run$0(PinResult pinResult);

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            Log.v("KeyguardSimPinView", "call supplyIccLockPin(subid=" + this.mSubId + ")");
            final PinResult supplyIccLockPin = KeyguardSimPinViewController.this.mTelephonyManager.createForSubscriptionId(this.mSubId).supplyIccLockPin(this.mPin);
            Log.v("KeyguardSimPinView", "supplyIccLockPin returned: " + supplyIccLockPin.toString());
            ((KeyguardSimPinView) ((ViewController) KeyguardSimPinViewController.this).mView).post(new Runnable() { // from class: com.android.keyguard.KeyguardSimPinViewController$CheckSimPin$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSimPinViewController.CheckSimPin.$r8$lambda$1kOP50kSuEUYTf0LQp5XjoBxo0Q(KeyguardSimPinViewController.CheckSimPin.this, supplyIccLockPin);
                }
            });
        }
    }

    public KeyguardSimPinViewController(KeyguardSimPinView keyguardSimPinView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController) {
        super(keyguardSimPinView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector);
        this.mSubId = -1;
        this.mUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSimPinViewController.1
            {
                KeyguardSimPinViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                Log.v("KeyguardSimPinView", "onSimStateChanged(subId=" + i + ",state=" + i3 + ")");
                if (i3 != 5) {
                    KeyguardSimPinViewController.this.resetState();
                    return;
                }
                KeyguardSimPinViewController.this.mRemainingAttempts = -1;
                KeyguardSimPinViewController.this.resetState();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mSimImageView = (ImageView) ((KeyguardSimPinView) ((ViewController) this).mView).findViewById(R$id.keyguard_sim);
    }

    public final String getPinPasswordErrorMessage(int i, boolean z) {
        String string;
        if (i == 0) {
            string = ((KeyguardSimPinView) ((ViewController) this).mView).getResources().getString(R$string.kg_password_wrong_pin_code_pukked);
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(((KeyguardSimPinView) ((ViewController) this).mView).getResources(), z ? R$string.kg_password_default_pin_message : R$string.kg_password_wrong_pin_code, i);
        } else {
            string = ((KeyguardSimPinView) ((ViewController) this).mView).getResources().getString(z ? R$string.kg_sim_pin_instructions : R$string.kg_password_pin_failed);
        }
        String str = string;
        if (KeyguardEsimArea.isEsimLocked(((KeyguardSimPinView) ((ViewController) this).mView).getContext(), this.mSubId)) {
            str = ((KeyguardSimPinView) ((ViewController) this).mView).getResources().getString(R$string.kg_sim_lock_esim_instructions, string);
        }
        Log.d("KeyguardSimPinView", "getPinPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + str);
        return str;
    }

    public final Dialog getSimRemainingAttemptsDialog(int i) {
        String pinPasswordErrorMessage = getPinPasswordErrorMessage(i, false);
        AlertDialog alertDialog = this.mRemainingAttemptsDialog;
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(((KeyguardSimPinView) ((ViewController) this).mView).getContext());
            builder.setMessage(pinPasswordErrorMessage);
            builder.setCancelable(false);
            builder.setNeutralButton(R$string.ok, (DialogInterface.OnClickListener) null);
            AlertDialog create = builder.create();
            this.mRemainingAttemptsDialog = create;
            create.getWindow().setType(2009);
        } else {
            alertDialog.setMessage(pinPasswordErrorMessage);
        }
        return this.mRemainingAttemptsDialog;
    }

    public final Dialog getSimUnlockProgressDialog() {
        if (this.mSimUnlockProgressDialog == null) {
            ProgressDialog progressDialog = new ProgressDialog(((KeyguardSimPinView) ((ViewController) this).mView).getContext());
            this.mSimUnlockProgressDialog = progressDialog;
            progressDialog.setMessage(((KeyguardSimPinView) ((ViewController) this).mView).getResources().getString(R$string.kg_sim_unlock_progress_dialog_message));
            this.mSimUnlockProgressDialog.setIndeterminate(true);
            this.mSimUnlockProgressDialog.setCancelable(false);
            this.mSimUnlockProgressDialog.getWindow().setType(2009);
        }
        return this.mSimUnlockProgressDialog;
    }

    public final void handleSubInfoChangeIfNeeded() {
        int nextSubIdForState = this.mKeyguardUpdateMonitor.getNextSubIdForState(2);
        if (nextSubIdForState == this.mSubId || !SubscriptionManager.isValidSubscriptionId(nextSubIdForState)) {
            return;
        }
        this.mSubId = nextSubIdForState;
        this.mShowDefaultMessage = true;
        this.mRemainingAttempts = -1;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        super.onPause();
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallback);
        ProgressDialog progressDialog = this.mSimUnlockProgressDialog;
        if (progressDialog != null) {
            progressDialog.dismiss();
            this.mSimUnlockProgressDialog = null;
        }
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallback);
        ((KeyguardSimPinView) ((ViewController) this).mView).resetState();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        super.resetState();
        Log.v("KeyguardSimPinView", "Resetting state");
        handleSubInfoChangeIfNeeded();
        this.mMessageAreaController.setMessage("");
        if (this.mShowDefaultMessage) {
            showDefaultMessage();
        }
        View view = ((ViewController) this).mView;
        ((KeyguardSimPinView) view).setESimLocked(KeyguardEsimArea.isEsimLocked(((KeyguardSimPinView) view).getContext(), this.mSubId), this.mSubId);
    }

    public final void setLockedSimMessage() {
        String string;
        boolean isEsimLocked = KeyguardEsimArea.isEsimLocked(((KeyguardSimPinView) ((ViewController) this).mView).getContext(), this.mSubId);
        TelephonyManager telephonyManager = this.mTelephonyManager;
        int activeModemCount = telephonyManager != null ? telephonyManager.getActiveModemCount() : 1;
        Resources resources = ((KeyguardSimPinView) ((ViewController) this).mView).getResources();
        TypedArray obtainStyledAttributes = ((KeyguardSimPinView) ((ViewController) this).mView).getContext().obtainStyledAttributes(new int[]{16842904});
        int color = obtainStyledAttributes.getColor(0, -1);
        obtainStyledAttributes.recycle();
        if (activeModemCount < 2) {
            string = resources.getString(R$string.kg_sim_pin_instructions);
        } else {
            SubscriptionInfo subscriptionInfoForSubId = this.mKeyguardUpdateMonitor.getSubscriptionInfoForSubId(this.mSubId);
            string = resources.getString(R$string.kg_sim_pin_instructions_multi, subscriptionInfoForSubId != null ? subscriptionInfoForSubId.getDisplayName() : "");
            if (subscriptionInfoForSubId != null) {
                color = subscriptionInfoForSubId.getIconTint();
            }
        }
        String str = string;
        if (isEsimLocked) {
            str = resources.getString(R$string.kg_sim_lock_esim_instructions, string);
        }
        if (((KeyguardSimPinView) ((ViewController) this).mView).getVisibility() == 0) {
            this.mMessageAreaController.setMessage(str);
        }
        this.mSimImageView.setImageTintList(ColorStateList.valueOf(color));
    }

    public final void showDefaultMessage() {
        setLockedSimMessage();
        if (this.mRemainingAttempts >= 0) {
            return;
        }
        new CheckSimPin("", this.mSubId) { // from class: com.android.keyguard.KeyguardSimPinViewController.3
            {
                KeyguardSimPinViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardSimPinViewController.CheckSimPin
            public void onSimCheckResponse(PinResult pinResult) {
                Log.d("KeyguardSimPinView", "onSimCheckResponse  empty One result " + pinResult.toString());
                if (pinResult.getAttemptsRemaining() >= 0) {
                    KeyguardSimPinViewController.this.mRemainingAttempts = pinResult.getAttemptsRemaining();
                    KeyguardSimPinViewController.this.setLockedSimMessage();
                }
            }
        }.start();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(Runnable runnable) {
        return false;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void verifyPasswordAndUnlock() {
        if (this.mPasswordEntry.getText().length() < 4) {
            this.mMessageAreaController.setMessage(R$string.kg_invalid_sim_pin_hint);
            ((KeyguardSimPinView) ((ViewController) this).mView).resetPasswordText(true, true);
            getKeyguardSecurityCallback().userActivity();
            return;
        }
        getSimUnlockProgressDialog().show();
        if (this.mCheckSimPinThread == null) {
            AnonymousClass2 anonymousClass2 = new AnonymousClass2(this.mPasswordEntry.getText(), this.mSubId);
            this.mCheckSimPinThread = anonymousClass2;
            anonymousClass2.start();
        }
    }
}