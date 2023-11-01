package com.android.keyguard;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPinBasedInputView;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.PasswordTextView;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPinBasedInputViewController.class */
public abstract class KeyguardPinBasedInputViewController<T extends KeyguardPinBasedInputView> extends KeyguardAbsKeyInputViewController<T> {
    public final View.OnTouchListener mActionButtonTouchListener;
    public final FalsingCollector mFalsingCollector;
    public final LiftToActivateListener mLiftToActivateListener;
    public final View.OnKeyListener mOnKeyListener;
    public PasswordTextView mPasswordEntry;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$9AInmX4hth5EzN384_m5GkcsZWQ(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, View view, MotionEvent motionEvent) {
        return keyguardPinBasedInputViewController.lambda$onViewAttached$2(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$pnuv2PYox2kbohigazbJLfUqaAY(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, View view, MotionEvent motionEvent) {
        return keyguardPinBasedInputViewController.lambda$new$1(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$sVhaIBw-pegVYYoUq1EphloEbjc */
    public static /* synthetic */ void m627$r8$lambda$sVhaIBwpegVYYoUq1EphloEbjc(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, View view) {
        keyguardPinBasedInputViewController.lambda$onViewAttached$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$tmYFnPmRpljTb2AR3U0SUcE_Oug(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, View view) {
        return keyguardPinBasedInputViewController.lambda$onViewAttached$4(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4.onKey(android.view.View, int, android.view.KeyEvent):boolean] */
    /* renamed from: $r8$lambda$ycb30UV0rTdlIFYcYY9-q1olm_o */
    public static /* synthetic */ boolean m628$r8$lambda$ycb30UV0rTdlIFYcYY9q1olm_o(KeyguardPinBasedInputViewController keyguardPinBasedInputViewController, View view, int i, KeyEvent keyEvent) {
        return keyguardPinBasedInputViewController.lambda$new$0(view, i, keyEvent);
    }

    public KeyguardPinBasedInputViewController(T t, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector) {
        super(t, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController);
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda4
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardPinBasedInputViewController.m628$r8$lambda$ycb30UV0rTdlIFYcYY9q1olm_o(KeyguardPinBasedInputViewController.this, view, i, keyEvent);
            }
        };
        this.mActionButtonTouchListener = new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda5
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return KeyguardPinBasedInputViewController.$r8$lambda$pnuv2PYox2kbohigazbJLfUqaAY(KeyguardPinBasedInputViewController.this, view, motionEvent);
            }
        };
        this.mLiftToActivateListener = liftToActivateListener;
        this.mFalsingCollector = falsingCollector;
        View view = ((ViewController) this).mView;
        this.mPasswordEntry = (PasswordTextView) ((KeyguardPinBasedInputView) view).findViewById(((KeyguardPinBasedInputView) view).getPasswordTextViewId());
    }

    public /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            return ((KeyguardPinBasedInputView) ((ViewController) this).mView).onKeyDown(i, keyEvent);
        }
        return false;
    }

    public /* synthetic */ boolean lambda$new$1(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            ((KeyguardPinBasedInputView) ((ViewController) this).mView).doHapticKeyClick();
            return false;
        }
        return false;
    }

    public /* synthetic */ boolean lambda$onViewAttached$2(View view, MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mFalsingCollector.avoidGesture();
            return false;
        }
        return false;
    }

    public /* synthetic */ void lambda$onViewAttached$3(View view) {
        if (this.mPasswordEntry.isEnabled()) {
            this.mPasswordEntry.deleteLastChar();
        }
    }

    public /* synthetic */ boolean lambda$onViewAttached$4(View view) {
        if (this.mPasswordEntry.isEnabled()) {
            ((KeyguardPinBasedInputView) ((ViewController) this).mView).resetPasswordText(true, true);
        }
        ((KeyguardPinBasedInputView) ((ViewController) this).mView).doHapticKeyClick();
        return true;
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R$string.keyguard_enter_your_pin;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        this.mPasswordEntry.requestFocus();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) ((ViewController) this).mView).getButtons()) {
            numPadKey.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return KeyguardPinBasedInputViewController.$r8$lambda$9AInmX4hth5EzN384_m5GkcsZWQ(KeyguardPinBasedInputViewController.this, view, motionEvent);
                }
            });
        }
        this.mPasswordEntry.setOnKeyListener(this.mOnKeyListener);
        this.mPasswordEntry.setUserActivityListener(new PasswordTextView.UserActivityListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda1
            @Override // com.android.keyguard.PasswordTextView.UserActivityListener
            public final void onUserActivity() {
                KeyguardPinBasedInputViewController.this.onUserInput();
            }
        });
        View findViewById = ((KeyguardPinBasedInputView) ((ViewController) this).mView).findViewById(R$id.delete_button);
        findViewById.setOnTouchListener(this.mActionButtonTouchListener);
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardPinBasedInputViewController.m627$r8$lambda$sVhaIBwpegVYYoUq1EphloEbjc(KeyguardPinBasedInputViewController.this, view);
            }
        });
        findViewById.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return KeyguardPinBasedInputViewController.$r8$lambda$tmYFnPmRpljTb2AR3U0SUcE_Oug(KeyguardPinBasedInputViewController.this, view);
            }
        });
        View findViewById2 = ((KeyguardPinBasedInputView) ((ViewController) this).mView).findViewById(R$id.key_enter);
        if (findViewById2 != null) {
            findViewById2.setOnTouchListener(this.mActionButtonTouchListener);
            findViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinBasedInputViewController.1
                {
                    KeyguardPinBasedInputViewController.this = this;
                }

                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (KeyguardPinBasedInputViewController.this.mPasswordEntry.isEnabled()) {
                        KeyguardPinBasedInputViewController.this.verifyPasswordAndUnlock();
                    }
                }
            });
            findViewById2.setOnHoverListener(this.mLiftToActivateListener);
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        for (NumPadKey numPadKey : ((KeyguardPinBasedInputView) ((ViewController) this).mView).getButtons()) {
            numPadKey.setOnTouchListener(null);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        ((KeyguardPinBasedInputView) ((ViewController) this).mView).setPasswordEntryEnabled(true);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void startErrorAnimation() {
        super.startErrorAnimation();
        ((KeyguardPinBasedInputView) ((ViewController) this).mView).startErrorAnimation();
    }
}