package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Insets;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImeAwareEditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.Dumpable;
import com.android.systemui.R$id;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialPasswordView.class */
public class AuthCredentialPasswordView extends AuthCredentialView implements TextView.OnEditorActionListener, View.OnApplyWindowInsetsListener, Dumpable {
    public ViewGroup mAuthCredentialHeader;
    public ViewGroup mAuthCredentialInput;
    public final OnBackInvokedCallback mBackCallback;
    public int mBottomInset;
    public final InputMethodManager mImm;
    public OnBackInvokedDispatcher mOnBackInvokedDispatcher;
    public ImeAwareEditText mPasswordField;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialPasswordView$$ExternalSyntheticLambda0.onKey(android.view.View, int, android.view.KeyEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$SFBFyvyzFIcRrwQMo5zWG7ZFpks(AuthCredentialPasswordView authCredentialPasswordView, View view, int i, KeyEvent keyEvent) {
        return authCredentialPasswordView.lambda$onFinishInflate$0(view, i, keyEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialPasswordView$$ExternalSyntheticLambda1.onBackInvoked():void] */
    /* renamed from: $r8$lambda$uldKy3uIWr_-_ecMZnQNU4B_u8s */
    public static /* synthetic */ void m1536$r8$lambda$uldKy3uIWr__ecMZnQNU4B_u8s(AuthCredentialPasswordView authCredentialPasswordView) {
        authCredentialPasswordView.onBackInvoked();
    }

    public AuthCredentialPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBottomInset = 0;
        this.mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.biometrics.AuthCredentialPasswordView$$ExternalSyntheticLambda1
            public final void onBackInvoked() {
                AuthCredentialPasswordView.m1536$r8$lambda$uldKy3uIWr__ecMZnQNU4B_u8s(AuthCredentialPasswordView.this);
            }
        };
        this.mImm = (InputMethodManager) ((LinearLayout) this).mContext.getSystemService(InputMethodManager.class);
    }

    public /* synthetic */ boolean lambda$onFinishInflate$0(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            onBackInvoked();
            return true;
        }
        return true;
    }

    public final void checkPasswordAndUnlock() {
        LockscreenCredential createPinOrNone = this.mCredentialType == 1 ? LockscreenCredential.createPinOrNone(this.mPasswordField.getText()) : LockscreenCredential.createPasswordOrNone(this.mPasswordField.getText());
        try {
            if (createPinOrNone.isNone()) {
                createPinOrNone.close();
                return;
            }
            this.mPendingLockCheck = LockPatternChecker.verifyCredential(this.mLockPatternUtils, createPinOrNone, this.mEffectiveUserId, 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.android.systemui.biometrics.AuthCredentialPasswordView$$ExternalSyntheticLambda2
                public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
                    AuthCredentialPasswordView.this.onCredentialVerified(verifyCredentialResponse, i);
                }
            });
            createPinOrNone.close();
        } catch (Throwable th) {
            if (createPinOrNone != null) {
                try {
                    createPinOrNone.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("BiometricPrompt/AuthCredentialPasswordViewState:");
        printWriter.println("  mBottomInset=" + this.mBottomInset);
        printWriter.println("  mAuthCredentialHeader size=(" + this.mAuthCredentialHeader.getWidth() + "," + this.mAuthCredentialHeader.getHeight());
        printWriter.println("  mAuthCredentialInput size=(" + this.mAuthCredentialInput.getWidth() + "," + this.mAuthCredentialInput.getHeight());
    }

    @Override // android.view.View.OnApplyWindowInsetsListener
    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        Insets insets = windowInsets.getInsets(WindowInsets.Type.ime());
        if (view instanceof AuthCredentialPasswordView) {
            int i = this.mBottomInset;
            int i2 = insets.bottom;
            if (i != i2) {
                this.mBottomInset = i2;
                boolean z = false;
                if (i2 <= 0 || getResources().getConfiguration().orientation != 2) {
                    this.mTitleView.setSingleLine(false);
                    this.mTitleView.setEllipsize(null);
                    this.mTitleView.setSelected(false);
                } else {
                    this.mTitleView.setSingleLine(true);
                    this.mTitleView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    this.mTitleView.setMarqueeRepeatLimit(-1);
                    TextView textView = this.mTitleView;
                    if (!this.mAccessibilityManager.isEnabled() || !this.mAccessibilityManager.isTouchExplorationEnabled()) {
                        z = true;
                    }
                    textView.setSelected(z);
                }
                requestLayout();
            }
        }
        return windowInsets;
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mPasswordField.setTextOperationUser(UserHandle.of(this.mUserId));
        if (this.mCredentialType == 1) {
            this.mPasswordField.setInputType(18);
        }
        this.mPasswordField.requestFocus();
        this.mPasswordField.scheduleShowSoftInput();
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = findOnBackInvokedDispatcher();
        this.mOnBackInvokedDispatcher = findOnBackInvokedDispatcher;
        if (findOnBackInvokedDispatcher != null) {
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.mBackCallback);
        }
    }

    public final void onBackInvoked() {
        this.mContainerView.sendEarlyUserCanceled();
        this.mContainerView.animateAway(1);
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView
    public void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        super.onCredentialVerified(verifyCredentialResponse, i);
        if (verifyCredentialResponse.isMatched()) {
            this.mImm.hideSoftInputFromWindow(getWindowToken(), 0);
        } else {
            this.mPasswordField.setText("");
        }
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mOnBackInvokedDispatcher;
        if (onBackInvokedDispatcher != null) {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackCallback);
            this.mOnBackInvokedDispatcher = null;
        }
    }

    @Override // android.widget.TextView.OnEditorActionListener
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (z || z2) {
            checkPasswordAndUnlock();
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mAuthCredentialHeader = (ViewGroup) findViewById(R$id.auth_credential_header);
        this.mAuthCredentialInput = (ViewGroup) findViewById(R$id.auth_credential_input);
        ImeAwareEditText findViewById = findViewById(R$id.lockPassword);
        this.mPasswordField = findViewById;
        findViewById.setOnEditorActionListener(this);
        this.mPasswordField.setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.biometrics.AuthCredentialPasswordView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return AuthCredentialPasswordView.$r8$lambda$SFBFyvyzFIcRrwQMo5zWG7ZFpks(AuthCredentialPasswordView.this, view, i, keyEvent);
            }
        });
        setOnApplyWindowInsetsListener(this);
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView, android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TextView textView;
        int height;
        int width;
        int i5;
        int i6;
        super.onLayout(z, i, i2, i3, i4);
        if (this.mAuthCredentialInput == null || this.mAuthCredentialHeader == null || (textView = this.mSubtitleView) == null || this.mDescriptionView == null || this.mPasswordField == null || this.mErrorView == null) {
            return;
        }
        int bottom = textView.getVisibility() == 8 ? this.mTitleView.getBottom() : this.mSubtitleView.getBottom();
        if (this.mDescriptionView.getVisibility() != 8) {
            bottom = this.mDescriptionView.getBottom();
        }
        if (getResources().getConfiguration().orientation == 2) {
            height = (i4 - this.mAuthCredentialInput.getHeight()) / 2;
            int i7 = (i3 - i) / 2;
            int min = i2 - Math.min(this.mIconView.getBottom(), this.mBottomInset);
            width = i7;
            i6 = i7;
            i5 = min;
        } else {
            height = bottom + (((i4 - bottom) - this.mAuthCredentialInput.getHeight()) / 2);
            width = ((i3 - i) - this.mAuthCredentialInput.getWidth()) / 2;
            i5 = i2;
            i6 = i3;
        }
        if (this.mDescriptionView.getBottom() > this.mBottomInset) {
            this.mAuthCredentialHeader.layout(i, i5, i6, i4);
        }
        this.mAuthCredentialInput.layout(width, height, i3, i4);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2) - this.mBottomInset;
        setMeasuredDimension(size, size2);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(getWidth() / 2, Integer.MIN_VALUE);
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(size2, 0);
        if (getResources().getConfiguration().orientation == 2) {
            measureChildren(makeMeasureSpec, makeMeasureSpec2);
        } else {
            measureChildren(i, makeMeasureSpec2);
        }
    }
}