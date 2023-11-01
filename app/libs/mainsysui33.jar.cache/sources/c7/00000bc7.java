package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Trace;
import android.util.AttributeSet;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationControlListener;
import android.view.WindowInsetsAnimationController;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.TextViewInputDisabler;
import com.android.keyguard.KeyguardPasswordView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.DejankUtils;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPasswordView.class */
public class KeyguardPasswordView extends KeyguardAbsKeyInputView {
    public final int mDisappearYTranslation;
    public Interpolator mFastOutLinearInInterpolator;
    public Interpolator mLinearOutSlowInInterpolator;
    public TextView mPasswordEntry;
    public TextViewInputDisabler mPasswordEntryDisabler;

    /* renamed from: com.android.keyguard.KeyguardPasswordView$1 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPasswordView$1.class */
    public class AnonymousClass1 implements WindowInsetsAnimationControlListener {
        public final /* synthetic */ Runnable val$finishRunnable;

        /* renamed from: com.android.keyguard.KeyguardPasswordView$1$1 */
        /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPasswordView$1$1.class */
        public class C00031 extends AnimatorListenerAdapter {
            public final /* synthetic */ WindowInsetsAnimationController val$controller;

            /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordView$1$1$$ExternalSyntheticLambda0.run():void] */
            /* renamed from: $r8$lambda$M3efZPi3ADbLgrwbj-6tm5a6qU0 */
            public static /* synthetic */ void m609$r8$lambda$M3efZPi3ADbLgrwbj6tm5a6qU0(C00031 c00031, WindowInsetsAnimationController windowInsetsAnimationController, Runnable runnable) {
                c00031.lambda$onAnimationEnd$0(windowInsetsAnimationController, runnable);
            }

            public C00031(WindowInsetsAnimationController windowInsetsAnimationController) {
                AnonymousClass1.this = r4;
                this.val$controller = windowInsetsAnimationController;
            }

            public /* synthetic */ void lambda$onAnimationEnd$0(WindowInsetsAnimationController windowInsetsAnimationController, Runnable runnable) {
                Trace.beginSection("KeyguardPasswordView#onAnimationEnd");
                windowInsetsAnimationController.finish(false);
                KeyguardPasswordView.this.runOnFinishImeAnimationRunnable();
                runnable.run();
                Trace.endSection();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                final WindowInsetsAnimationController windowInsetsAnimationController = this.val$controller;
                final Runnable runnable = AnonymousClass1.this.val$finishRunnable;
                DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordView$1$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardPasswordView.AnonymousClass1.C00031.m609$r8$lambda$M3efZPi3ADbLgrwbj6tm5a6qU0(KeyguardPasswordView.AnonymousClass1.C00031.this, windowInsetsAnimationController, runnable);
                    }
                });
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordView$1$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
        /* renamed from: $r8$lambda$2hxqAG6-eMJPz803KTO261_JPSI */
        public static /* synthetic */ void m608$r8$lambda$2hxqAG6eMJPz803KTO261_JPSI(WindowInsetsAnimationController windowInsetsAnimationController, ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
            lambda$onReady$0(windowInsetsAnimationController, valueAnimator, valueAnimator2);
        }

        public AnonymousClass1(Runnable runnable) {
            KeyguardPasswordView.this = r4;
            this.val$finishRunnable = runnable;
        }

        public static /* synthetic */ void lambda$onReady$0(WindowInsetsAnimationController windowInsetsAnimationController, ValueAnimator valueAnimator, ValueAnimator valueAnimator2) {
            if (windowInsetsAnimationController.isCancelled()) {
                return;
            }
            Insets shownStateInsets = windowInsetsAnimationController.getShownStateInsets();
            windowInsetsAnimationController.setInsetsAndAlpha(Insets.add(shownStateInsets, Insets.of(0, 0, 0, (int) (((-shownStateInsets.bottom) / 4) * valueAnimator.getAnimatedFraction()))), ((Float) valueAnimator2.getAnimatedValue()).floatValue(), valueAnimator.getAnimatedFraction());
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onCancelled(WindowInsetsAnimationController windowInsetsAnimationController) {
            KeyguardPasswordView.this.runOnFinishImeAnimationRunnable();
            this.val$finishRunnable.run();
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onFinished(WindowInsetsAnimationController windowInsetsAnimationController) {
        }

        @Override // android.view.WindowInsetsAnimationControlListener
        public void onReady(final WindowInsetsAnimationController windowInsetsAnimationController, int i) {
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardPasswordView$1$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    KeyguardPasswordView.AnonymousClass1.m608$r8$lambda$2hxqAG6eMJPz803KTO261_JPSI(windowInsetsAnimationController, ofFloat, valueAnimator);
                }
            });
            ofFloat.addListener(new C00031(windowInsetsAnimationController));
            ofFloat.setInterpolator(Interpolators.FAST_OUT_LINEAR_IN);
            ofFloat.start();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordView$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$6n7VjDntA2ZoJ2TYAFvSomzIA_0(KeyguardPasswordView keyguardPasswordView) {
        keyguardPasswordView.lambda$showKeyboard$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPasswordView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$HjNjCh5uWt30PX1MeQZDEi4vS0I(KeyguardPasswordView keyguardPasswordView) {
        keyguardPasswordView.lambda$hideKeyboard$1();
    }

    public KeyguardPasswordView(Context context) {
        this(context, null);
    }

    public KeyguardPasswordView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDisappearYTranslation = getResources().getDimensionPixelSize(R$dimen.disappear_y_translation);
        this.mLinearOutSlowInInterpolator = AnimationUtils.loadInterpolator(context, 17563662);
        this.mFastOutLinearInInterpolator = AnimationUtils.loadInterpolator(context, 17563663);
    }

    public /* synthetic */ void lambda$hideKeyboard$1() {
        if (this.mPasswordEntry.isAttachedToWindow() && this.mPasswordEntry.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
            this.mPasswordEntry.clearFocus();
            this.mPasswordEntry.getWindowInsetsController().hide(WindowInsets.Type.ime());
        }
    }

    public /* synthetic */ void lambda$showKeyboard$0() {
        if (!this.mPasswordEntry.isAttachedToWindow() || this.mPasswordEntry.getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
            return;
        }
        this.mPasswordEntry.requestFocus();
        this.mPasswordEntry.getWindowInsetsController().show(WindowInsets.Type.ime());
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public LockscreenCredential getEnteredCredential() {
        return LockscreenCredential.createPasswordOrNone(this.mPasswordEntry.getText());
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPasswordTextViewId() {
        return R$id.passwordEntry;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPromptReasonStringRes(int i) {
        switch (i) {
            case 0:
                return 0;
            case 1:
                return R$string.kg_prompt_reason_restart_password;
            case 2:
                return R$string.kg_prompt_reason_timeout_password;
            case 3:
                return R$string.kg_prompt_reason_device_admin;
            case 4:
                return R$string.kg_prompt_reason_user_request;
            case 5:
            default:
                return R$string.kg_prompt_reason_timeout_password;
            case 6:
                return R$string.kg_prompt_reason_timeout_password;
            case 7:
                return R$string.kg_prompt_reason_timeout_password;
            case 8:
                return R$string.kg_prompt_reason_timeout_password;
        }
    }

    @Override // com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getResources().getString(17040537);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getWrongPasswordStringId() {
        return R$string.kg_wrong_password;
    }

    public void hideKeyboard() {
        post(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPasswordView.$r8$lambda$HjNjCh5uWt30PX1MeQZDEi4vS0I(KeyguardPasswordView.this);
            }
        });
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if (!this.mPasswordEntry.isFocused() && isVisibleToUser()) {
            this.mPasswordEntry.requestFocus();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mPasswordEntry = (TextView) findViewById(getPasswordTextViewId());
        this.mPasswordEntryDisabler = new TextViewInputDisabler(this.mPasswordEntry);
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        return this.mPasswordEntry.requestFocus(i, rect);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            if (isVisibleToUser()) {
                showKeyboard();
            } else {
                hideKeyboard();
            }
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void resetPasswordText(boolean z, boolean z2) {
        this.mPasswordEntry.setText("");
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryEnabled(boolean z) {
        this.mPasswordEntry.setEnabled(z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public void setPasswordEntryInputEnabled(boolean z) {
        this.mPasswordEntryDisabler.setInputEnabled(z);
    }

    public void showKeyboard() {
        post(new Runnable() { // from class: com.android.keyguard.KeyguardPasswordView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardPasswordView.$r8$lambda$6n7VjDntA2ZoJ2TYAFvSomzIA_0(KeyguardPasswordView.this);
            }
        });
    }

    @Override // com.android.keyguard.KeyguardInputView
    public void startAppearAnimation() {
        setAlpha(ActionBarShadowController.ELEVATION_LOW);
        animate().alpha(1.0f).setDuration(300L).start();
        setTranslationY(ActionBarShadowController.ELEVATION_LOW);
    }

    @Override // com.android.keyguard.KeyguardInputView
    public boolean startDisappearAnimation(Runnable runnable) {
        getWindowInsetsController().controlWindowInsetsAnimation(WindowInsets.Type.ime(), 100L, Interpolators.LINEAR, null, new AnonymousClass1(runnable));
        return true;
    }
}