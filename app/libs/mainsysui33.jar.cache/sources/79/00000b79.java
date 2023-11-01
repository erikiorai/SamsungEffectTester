package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.android.internal.widget.LockscreenCredential;
import com.android.systemui.R$id;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardAbsKeyInputView.class */
public abstract class KeyguardAbsKeyInputView extends KeyguardInputView {
    public View mEcaView;
    public KeyDownListener mKeyDownListener;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardAbsKeyInputView$KeyDownListener.class */
    public interface KeyDownListener {
        boolean onKeyDown(int i, KeyEvent keyEvent);
    }

    public KeyguardAbsKeyInputView(Context context) {
        this(context, null);
    }

    public KeyguardAbsKeyInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void doHapticKeyClick() {
        performHapticFeedback(1, 1);
    }

    public abstract LockscreenCredential getEnteredCredential();

    public abstract int getPasswordTextViewId();

    public abstract int getPromptReasonStringRes(int i);

    public int getWrongPasswordStringId() {
        return R$string.kg_wrong_password;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        this.mEcaView = findViewById(R$id.keyguard_selector_fade_container);
    }

    @Override // android.view.View, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        KeyDownListener keyDownListener = this.mKeyDownListener;
        return keyDownListener != null && keyDownListener.onKeyDown(i, keyEvent);
    }

    public abstract void resetPasswordText(boolean z, boolean z2);

    public void setKeyDownListener(KeyDownListener keyDownListener) {
        this.mKeyDownListener = keyDownListener;
    }

    public abstract void setPasswordEntryEnabled(boolean z);

    public abstract void setPasswordEntryInputEnabled(boolean z);
}