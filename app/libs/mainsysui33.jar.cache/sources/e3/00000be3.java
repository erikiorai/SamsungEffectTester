package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPinBasedInputView.class */
public abstract class KeyguardPinBasedInputView extends KeyguardAbsKeyInputView {
    public NumPadKey[] mButtons;
    public NumPadButton mDeleteButton;
    public NumPadButton mOkButton;
    public PasswordTextView mPasswordEntry;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$8hLXpMsDM2zDZtQnkVNILvecJxc(View view, ValueAnimator valueAnimator) {
        lambda$startErrorAnimation$0(view, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$KgV1JM1p3EUHA7H1U2MlJRATWPU(View view, ValueAnimator valueAnimator) {
        lambda$startErrorAnimation$1(view, valueAnimator);
    }

    public KeyguardPinBasedInputView(Context context) {
        this(context, null);
    }

    public KeyguardPinBasedInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mButtons = new NumPadKey[10];
    }

    public static /* synthetic */ void lambda$startErrorAnimation$0(View view, ValueAnimator valueAnimator) {
        view.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
        view.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static /* synthetic */ void lambda$startErrorAnimation$1(View view, ValueAnimator valueAnimator) {
        view.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
        view.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public NumPadKey[] getButtons() {
        return this.mButtons;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPinOrNone(this.mPasswordEntry.getText());
    }

    public int getNumberIndex(int i) {
        return i;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPromptReasonStringRes(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return R$string.kg_prompt_reason_restart_pin;
            case 2:
                return R$string.kg_prompt_reason_timeout_pin;
            case 3:
                return R$string.kg_prompt_reason_device_admin;
            case 4:
                return R$string.kg_prompt_reason_user_request;
            case 5:
            default:
                return R$string.kg_prompt_reason_timeout_pin;
            case 6:
                return R$string.kg_prompt_reason_timeout_pin;
            case 7:
                return R$string.kg_prompt_reason_timeout_pin;
            case 8:
                return R$string.kg_prompt_reason_timeout_pin;
        }
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getContext().getString(17040540);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    public void onFinishInflate() {
        PasswordTextView passwordTextView = (PasswordTextView) findViewById(getPasswordTextViewId());
        this.mPasswordEntry = passwordTextView;
        passwordTextView.setSelected(true);
        this.mOkButton = (NumPadButton) findViewById(R$id.key_enter);
        NumPadButton numPadButton = (NumPadButton) findViewById(R$id.delete_button);
        this.mDeleteButton = numPadButton;
        numPadButton.setVisibility(0);
        this.mButtons[0] = (NumPadKey) findViewById(R$id.key0);
        this.mButtons[1] = (NumPadKey) findViewById(R$id.key1);
        this.mButtons[2] = (NumPadKey) findViewById(R$id.key2);
        this.mButtons[3] = (NumPadKey) findViewById(R$id.key3);
        this.mButtons[4] = (NumPadKey) findViewById(R$id.key4);
        this.mButtons[5] = (NumPadKey) findViewById(R$id.key5);
        this.mButtons[6] = (NumPadKey) findViewById(R$id.key6);
        this.mButtons[7] = (NumPadKey) findViewById(R$id.key7);
        this.mButtons[8] = (NumPadKey) findViewById(R$id.key8);
        this.mButtons[9] = (NumPadKey) findViewById(R$id.key9);
        this.mPasswordEntry.requestFocus();
        super.onFinishInflate();
        reloadColors();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(i)) {
            this.mOkButton.performClick();
            return true;
        } else if (i == 67) {
            this.mDeleteButton.performClick();
            return true;
        } else if (i >= 7 && i <= 16) {
            performNumberClick(i - 7);
            return true;
        } else if (i < 144 || i > 153) {
            return super.onKeyDown(i, keyEvent);
        } else {
            performNumberClick(i - 144);
            return true;
        }
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    public final void performNumberClick(int i) {
        if (i < 0 || i > 9) {
            return;
        }
        this.mButtons[getNumberIndex(i)].performClick();
    }

    public void reloadColors() {
        for (NumPadKey numPadKey : this.mButtons) {
            numPadKey.reloadColors();
        }
        this.mPasswordEntry.reloadColors();
        this.mDeleteButton.reloadColors();
        this.mOkButton.reloadColors();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.reset(z, z2);
    }

    public void resetState() {
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (!z || this.mPasswordEntry.hasFocus()) {
            return;
        }
        this.mPasswordEntry.requestFocus();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
        this.mOkButton.setEnabled(z);
        if (!z || this.mPasswordEntry.hasFocus()) {
            return;
        }
        this.mPasswordEntry.requestFocus();
    }

    public void startErrorAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 1; i <= 9; i++) {
            arrayList2.add(this.mButtons[i]);
        }
        arrayList2.add(this.mDeleteButton);
        arrayList2.add(this.mButtons[0]);
        arrayList2.add(this.mOkButton);
        int i2 = 0;
        for (int i3 = 0; i3 < arrayList2.size(); i3++) {
            final View view = (View) arrayList2.get(i3);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.setStartDelay(i2);
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.8f);
            Interpolator interpolator = Interpolators.STANDARD;
            ofFloat.setInterpolator(interpolator);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardPinBasedInputView.$r8$lambda$8hLXpMsDM2zDZtQnkVNILvecJxc(view, valueAnimator);
                }
            });
            ofFloat.setDuration(50L);
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
            ofFloat2.setInterpolator(interpolator);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPinBasedInputView$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardPinBasedInputView.$r8$lambda$KgV1JM1p3EUHA7H1U2MlJRATWPU(view, valueAnimator);
                }
            });
            ofFloat2.setDuration(617L);
            animatorSet2.playSequentially(ofFloat, ofFloat2);
            arrayList.add(animatorSet2);
            i2 += 33;
        }
        animatorSet.playTogether(arrayList);
        animatorSet.start();
    }
}