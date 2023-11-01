package com.android.keyguard;

import android.content.res.Resources;
import android.os.UserHandle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$bool;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPasswordViewController.class */
public class KeyguardPasswordViewController extends KeyguardAbsKeyInputViewController<KeyguardPasswordView> {
    public final InputMethodManager mInputMethodManager;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardViewController mKeyguardViewController;
    public final DelayableExecutor mMainExecutor;
    public final TextView.OnEditorActionListener mOnEditorActionListener;
    public EditText mPasswordEntry;
    public boolean mPaused;
    public final boolean mShowImeAtScreenOn;
    public ImageView mSwitchImeButton;
    public final TextWatcher mTextWatcher;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$CDEK7bb0-AOTw-g2px7Ikmrdzi4 */
    public static /* synthetic */ void m610$r8$lambda$CDEK7bb0AOTwg2px7Ikmrdzi4(KeyguardPasswordViewController keyguardPasswordViewController, View view) {
        keyguardPasswordViewController.lambda$onViewAttached$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$E8NESg9njuD3CE_2lO85RzDq898(KeyguardPasswordViewController keyguardPasswordViewController) {
        keyguardPasswordViewController.lambda$onPause$4();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$Eiq3caR9EqOsF66hcnJstquHQoM(KeyguardPasswordViewController keyguardPasswordViewController, View view) {
        keyguardPasswordViewController.lambda$onViewAttached$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$VKPhU1_HmxqtAw5TOQ7KU-ddouM */
    public static /* synthetic */ void m611$r8$lambda$VKPhU1_HmxqtAw5TOQ7KUddouM(KeyguardPasswordViewController keyguardPasswordViewController, View view) {
        keyguardPasswordViewController.lambda$onViewAttached$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$Zhzz1UPUfNmuBI1APfewHkvPxKk(KeyguardPasswordViewController keyguardPasswordViewController) {
        keyguardPasswordViewController.updateSwitchImeButton();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda0.onEditorAction(android.widget.TextView, int, android.view.KeyEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$z6IClKSbIGCjIJq9eqLAG1nDTBU(KeyguardPasswordViewController keyguardPasswordViewController, TextView textView, int i, KeyEvent keyEvent) {
        return keyguardPasswordViewController.lambda$new$0(textView, i, keyEvent);
    }

    public KeyguardPasswordViewController(KeyguardPasswordView keyguardPasswordView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController) {
        super(keyguardPasswordView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController);
        this.mOnEditorActionListener = new TextView.OnEditorActionListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda0
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return KeyguardPasswordViewController.$r8$lambda$z6IClKSbIGCjIJq9eqLAG1nDTBU(KeyguardPasswordViewController.this, textView, i, keyEvent);
            }
        };
        this.mTextWatcher = new TextWatcher() { // from class: com.android.keyguard.KeyguardPasswordViewController.1
            {
                KeyguardPasswordViewController.this = this;
            }

            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable editable) {
                if (TextUtils.isEmpty(editable)) {
                    return;
                }
                KeyguardPasswordViewController.this.onUserInput();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                KeyguardPasswordViewController.this.mKeyguardSecurityCallback.userActivity();
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }
        };
        this.mKeyguardSecurityCallback = keyguardSecurityCallback;
        this.mInputMethodManager = inputMethodManager;
        this.mMainExecutor = delayableExecutor;
        this.mKeyguardViewController = keyguardViewController;
        this.mShowImeAtScreenOn = resources.getBoolean(R$bool.kg_show_ime_at_screen_on);
        View view = ((ViewController) this).mView;
        this.mPasswordEntry = (EditText) ((KeyguardPasswordView) view).findViewById(((KeyguardPasswordView) view).getPasswordTextViewId());
        this.mSwitchImeButton = (ImageView) ((KeyguardPasswordView) ((ViewController) this).mView).findViewById(R$id.switch_ime_button);
    }

    public /* synthetic */ boolean lambda$new$0(TextView textView, int i, KeyEvent keyEvent) {
        boolean z = keyEvent == null && (i == 0 || i == 6 || i == 5);
        boolean z2 = keyEvent != null && KeyEvent.isConfirmKey(keyEvent.getKeyCode()) && keyEvent.getAction() == 0;
        if (z || z2) {
            verifyPasswordAndUnlock();
            return true;
        }
        return false;
    }

    public /* synthetic */ void lambda$onPause$4() {
        this.mPasswordEntry.clearFocus();
        super.onPause();
    }

    public /* synthetic */ void lambda$onViewAttached$1(View view) {
        this.mKeyguardSecurityCallback.userActivity();
    }

    public /* synthetic */ void lambda$onViewAttached$2(View view) {
        this.mKeyguardSecurityCallback.userActivity();
        this.mInputMethodManager.showInputMethodPickerFromSystem(false, ((KeyguardPasswordView) ((ViewController) this).mView).getContext().getDisplayId());
    }

    public /* synthetic */ void lambda$onViewAttached$3(View view) {
        this.mKeyguardSecurityCallback.reset();
        this.mKeyguardSecurityCallback.onCancelClicked();
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public int getInitialMessageResId() {
        return R$string.keyguard_enter_your_password;
    }

    /* JADX WARN: Code restructure failed: missing block: B:75:0x00a3, code lost:
        if (r5.getEnabledInputMethodSubtypeList(null, false).size() > 1) goto L44;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean hasMultipleEnabledIMEsOrSubtypes(InputMethodManager inputMethodManager, boolean z) {
        int i = 0;
        for (InputMethodInfo inputMethodInfo : inputMethodManager.getEnabledInputMethodListAsUser(KeyguardUpdateMonitor.getCurrentUser())) {
            if (i > 1) {
                return true;
            }
            List<InputMethodSubtype> enabledInputMethodSubtypeList = inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
            if (!enabledInputMethodSubtypeList.isEmpty()) {
                int i2 = 0;
                for (InputMethodSubtype inputMethodSubtype : enabledInputMethodSubtypeList) {
                    if (inputMethodSubtype.isAuxiliary()) {
                        i2++;
                    }
                }
                if (enabledInputMethodSubtypeList.size() - i2 <= 0) {
                    if (z && i2 > 1) {
                    }
                }
            }
            i++;
        }
        boolean z2 = i > 1;
        return z2;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return true;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onPause() {
        if (this.mPaused) {
            return;
        }
        this.mPaused = true;
        if (this.mPasswordEntry.isVisibleToUser()) {
            ((KeyguardPasswordView) ((ViewController) this).mView).setOnFinishImeAnimationRunnable(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardPasswordViewController.$r8$lambda$E8NESg9njuD3CE_2lO85RzDq898(KeyguardPasswordViewController.this);
                }
            });
        } else {
            super.onPause();
        }
        ((KeyguardPasswordView) ((ViewController) this).mView).hideKeyboard();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        this.mPaused = false;
        if (i != 1 || this.mShowImeAtScreenOn) {
            showInput();
        }
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onStartingToHide() {
        ((KeyguardPasswordView) ((ViewController) this).mView).hideKeyboard();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        this.mPasswordEntry.setTextOperationUser(UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()));
        this.mPasswordEntry.setKeyListener(TextKeyListener.getInstance());
        this.mPasswordEntry.setInputType(129);
        this.mPasswordEntry.setSelected(true);
        this.mPasswordEntry.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mPasswordEntry.addTextChangedListener(this.mTextWatcher);
        this.mPasswordEntry.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardPasswordViewController.m611$r8$lambda$VKPhU1_HmxqtAw5TOQ7KUddouM(KeyguardPasswordViewController.this, view);
            }
        });
        this.mSwitchImeButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                KeyguardPasswordViewController.$r8$lambda$Eiq3caR9EqOsF66hcnJstquHQoM(KeyguardPasswordViewController.this, view);
            }
        });
        View findViewById = ((KeyguardPasswordView) ((ViewController) this).mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPasswordViewController.m610$r8$lambda$CDEK7bb0AOTwg2px7Ikmrdzi4(KeyguardPasswordViewController.this, view);
                }
            });
        }
        updateSwitchImeButton();
        this.mMainExecutor.executeDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordViewController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPasswordViewController.$r8$lambda$Zhzz1UPUfNmuBI1APfewHkvPxKk(KeyguardPasswordViewController.this);
            }
        }, 500L);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mPasswordEntry.setOnEditorActionListener(null);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        this.mPasswordEntry.setTextOperationUser(UserHandle.of(KeyguardUpdateMonitor.getCurrentUser()));
        this.mMessageAreaController.setMessage(getInitialMessageResId());
        boolean isEnabled = this.mPasswordEntry.isEnabled();
        ((KeyguardPasswordView) ((ViewController) this).mView).setPasswordEntryEnabled(true);
        ((KeyguardPasswordView) ((ViewController) this).mView).setPasswordEntryInputEnabled(true);
        if (this.mResumed && this.mPasswordEntry.isVisibleToUser() && isEnabled) {
            showInput();
        }
    }

    public final void showInput() {
        if (this.mKeyguardViewController.isBouncerShowing() && ((KeyguardPasswordView) ((ViewController) this).mView).isShown()) {
            ((KeyguardPasswordView) ((ViewController) this).mView).showKeyboard();
        }
    }

    public final void updateSwitchImeButton() {
        boolean z = this.mSwitchImeButton.getVisibility() == 0;
        boolean hasMultipleEnabledIMEsOrSubtypes = hasMultipleEnabledIMEsOrSubtypes(this.mInputMethodManager, false);
        if (z != hasMultipleEnabledIMEsOrSubtypes) {
            this.mSwitchImeButton.setVisibility(hasMultipleEnabledIMEsOrSubtypes ? 0 : 8);
        }
        if (this.mSwitchImeButton.getVisibility() != 0) {
            ViewGroup.LayoutParams layoutParams = this.mPasswordEntry.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMarginStart(0);
                this.mPasswordEntry.setLayoutParams(layoutParams);
            }
        }
    }
}