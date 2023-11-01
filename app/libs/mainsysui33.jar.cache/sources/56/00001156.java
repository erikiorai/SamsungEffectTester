package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.biometrics.PromptInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$bool;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.AuthDialog;
import java.util.ArrayList;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricView.class */
public abstract class AuthBiometricView extends LinearLayout {
    public final AccessibilityManager mAccessibilityManager;
    @VisibleForTesting
    public int mAnimationDurationHideDialog;
    @VisibleForTesting
    public int mAnimationDurationLong;
    @VisibleForTesting
    public int mAnimationDurationShort;
    public final View.OnClickListener mBackgroundClickListener;
    public Callback mCallback;
    @VisibleForTesting
    public Button mCancelButton;
    @VisibleForTesting
    public Button mConfirmButton;
    public final int mCustomBpHeight;
    public final int mCustomBpWidth;
    public TextView mDescriptionView;
    public boolean mDialogSizeAnimating;
    public int mEffectiveUserId;
    public final Handler mHandler;
    @VisibleForTesting
    public AuthIconController mIconController;
    public View mIconHolderView;
    public float mIconOriginalY;
    public LottieAnimationView mIconView;
    public LottieAnimationView mIconViewOverlay;
    public TextView mIndicatorView;
    public Animator.AnimatorListener mJankListener;
    @VisibleForTesting
    public AuthDialog.LayoutParams mLayoutParams;
    public final LockPatternUtils mLockPatternUtils;
    @VisibleForTesting
    public Button mNegativeButton;
    public AuthPanelController mPanelController;
    public PromptInfo mPromptInfo;
    public boolean mRequireConfirmation;
    public final Runnable mResetErrorRunnable;
    public final Runnable mResetHelpRunnable;
    public Bundle mSavedState;
    public int mSize;
    public int mState;
    public TextView mSubtitleView;
    public final int mTextColorError;
    public final int mTextColorHint;
    public TextView mTitleView;
    @VisibleForTesting
    public Button mTryAgainButton;
    @VisibleForTesting
    public Button mUseCredentialButton;
    public final boolean mUseCustomBpSize;
    public int mUserId;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricView$Callback.class */
    public interface Callback {
        void onAction(int i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda14.run():void] */
    /* renamed from: $r8$lambda$-2-rwDSgFbOdOVjO-mncQYw0Cq8 */
    public static /* synthetic */ void m1500$r8$lambda$2rwDSgFbOdOVjOmncQYw0Cq8(AuthBiometricView authBiometricView) {
        authBiometricView.lambda$updateState$7();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda11.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$Bta1H82G51hrtufqd1L0WRZ9Vrg(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$new$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda13.run():void] */
    /* renamed from: $r8$lambda$E5gB7wJwa-qpjDpEc2uv6cwIdh4 */
    public static /* synthetic */ void m1501$r8$lambda$E5gB7wJwaqpjDpEc2uv6cwIdh4(AuthBiometricView authBiometricView) {
        authBiometricView.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda8.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$EJCeNa3xk6ENrX5FMGHXobUUa-E */
    public static /* synthetic */ void m1502$r8$lambda$EJCeNa3xk6ENrX5FMGHXobUUaE(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$13(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda3.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$Il6-jnadxa7kL3WO0RiSzfpz6ck */
    public static /* synthetic */ void m1503$r8$lambda$Il6jnadxa7kL3WO0RiSzfpz6ck(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        lambda$updateSize$6(authBiometricView, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda6.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$Nsf0YMm0r0CMfgCkmJfYoxAoLNc(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$11(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda12.run():void] */
    public static /* synthetic */ void $r8$lambda$QmdSW9m_Tvs55Xkd6PzDh2LRyNc(AuthBiometricView authBiometricView) {
        authBiometricView.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda9.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$TUVhAR6akTviLvh2_49UedUNOjw(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$14(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$VAsfUtJD-alPk1MGi8YaQ87a6H4 */
    public static /* synthetic */ void m1504$r8$lambda$VAsfUtJDalPk1MGi8YaQ87a6H4(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        authBiometricView.lambda$updateSize$3(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda15.run():void] */
    public static /* synthetic */ void $r8$lambda$VegE5yOsiHWU0vJWzcmzXwUISuc(AuthBiometricView authBiometricView) {
        authBiometricView.lambda$onError$8();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$m1SJ5XmbFfb9fxoQMu08fq3Wv8M(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        lambda$updateSize$5(authBiometricView, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$nX3sKkn-rg9G3GI_erF3EymQkRc */
    public static /* synthetic */ void m1505$r8$lambda$nX3sKknrg9G3GI_erF3EymQkRc(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        authBiometricView.lambda$updateSize$4(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda7.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$nY4MWrh-VD-AGSA4T4BLKCuTm-E */
    public static /* synthetic */ void m1506$r8$lambda$nY4MWrhVDAGSA4T4BLKCuTmE(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$12(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda4.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$vcX5w0vuWCIrqmRNglfbEc_XnrU(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$9(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda10.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$x-x9XBMxb3QYVDxvFKZcBjkDmQo */
    public static /* synthetic */ void m1507$r8$lambda$xx9XBMxb3QYVDxvFKZcBjkDmQo(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$15(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$xSMiMwg1iJ4A4YddMF3u7QDYZY4(AuthBiometricView authBiometricView, View view) {
        authBiometricView.lambda$onFinishInflate$10(view);
    }

    public AuthBiometricView(Context context) {
        this(context, null);
    }

    public AuthBiometricView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSize = 0;
        this.mAnimationDurationShort = 150;
        this.mAnimationDurationLong = 450;
        this.mAnimationDurationHideDialog = RecyclerView.MAX_SCROLL_DURATION;
        this.mBackgroundClickListener = new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda11
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.$r8$lambda$Bta1H82G51hrtufqd1L0WRZ9Vrg(AuthBiometricView.this, view);
            }
        };
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mTextColorError = getResources().getColor(R$color.biometric_dialog_error, context.getTheme());
        this.mTextColorHint = getResources().getColor(R$color.biometric_dialog_gray, context.getTheme());
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mLockPatternUtils = new LockPatternUtils(context);
        this.mResetErrorRunnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda12
            @Override // java.lang.Runnable
            public final void run() {
                AuthBiometricView.$r8$lambda$QmdSW9m_Tvs55Xkd6PzDh2LRyNc(AuthBiometricView.this);
            }
        };
        this.mResetHelpRunnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda13
            @Override // java.lang.Runnable
            public final void run() {
                AuthBiometricView.m1501$r8$lambda$E5gB7wJwaqpjDpEc2uv6cwIdh4(AuthBiometricView.this);
            }
        };
        this.mUseCustomBpSize = getResources().getBoolean(R$bool.use_custom_bp_size);
        this.mCustomBpWidth = getResources().getDimensionPixelSize(R$dimen.biometric_dialog_width);
        this.mCustomBpHeight = getResources().getDimensionPixelSize(R$dimen.biometric_dialog_height);
    }

    public /* synthetic */ void lambda$new$0(View view) {
        if (this.mState == 6) {
            Log.w("AuthBiometricView", "Ignoring background click after authenticated");
            return;
        }
        int i = this.mSize;
        if (i == 1) {
            Log.w("AuthBiometricView", "Ignoring background click during small dialog");
        } else if (i == 3) {
            Log.w("AuthBiometricView", "Ignoring background click during large dialog");
        } else {
            this.mCallback.onAction(2);
        }
    }

    public /* synthetic */ void lambda$new$1() {
        updateState(getStateForAfterError());
        handleResetAfterError();
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public /* synthetic */ void lambda$new$2() {
        updateState(2);
        handleResetAfterHelp();
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public /* synthetic */ void lambda$onError$8() {
        this.mCallback.onAction(5);
    }

    public /* synthetic */ void lambda$onFinishInflate$10(View view) {
        this.mCallback.onAction(2);
    }

    public /* synthetic */ void lambda$onFinishInflate$11(View view) {
        startTransitionToCredentialUI();
    }

    public /* synthetic */ void lambda$onFinishInflate$12(View view) {
        updateState(6);
    }

    public /* synthetic */ void lambda$onFinishInflate$13(View view) {
        updateState(2);
        this.mCallback.onAction(4);
        this.mTryAgainButton.setVisibility(8);
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public /* synthetic */ void lambda$onFinishInflate$14(View view) {
        if (this.mState == 5) {
            updateState(6);
        }
    }

    public /* synthetic */ void lambda$onFinishInflate$15(View view) {
        if (this.mState == 5) {
            updateState(6);
        }
    }

    public /* synthetic */ void lambda$onFinishInflate$9(View view) {
        this.mCallback.onAction(3);
    }

    public /* synthetic */ void lambda$updateSize$3(ValueAnimator valueAnimator) {
        this.mIconHolderView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$updateSize$4(ValueAnimator valueAnimator) {
        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        this.mTitleView.setAlpha(floatValue);
        this.mIndicatorView.setAlpha(floatValue);
        this.mNegativeButton.setAlpha(floatValue);
        this.mCancelButton.setAlpha(floatValue);
        this.mTryAgainButton.setAlpha(floatValue);
        if (!TextUtils.isEmpty(this.mSubtitleView.getText())) {
            this.mSubtitleView.setAlpha(floatValue);
        }
        if (TextUtils.isEmpty(this.mDescriptionView.getText())) {
            return;
        }
        this.mDescriptionView.setAlpha(floatValue);
    }

    public static /* synthetic */ void lambda$updateSize$5(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        authBiometricView.setTranslationY(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public static /* synthetic */ void lambda$updateSize$6(AuthBiometricView authBiometricView, ValueAnimator valueAnimator) {
        authBiometricView.setAlpha(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$updateState$7() {
        this.mCallback.onAction(1);
    }

    public abstract AuthIconController createIconController();

    public boolean forceRequireConfirmation(int i) {
        return false;
    }

    public int getConfirmationPrompt() {
        return R$string.biometric_dialog_tap_confirm;
    }

    public int getDelayAfterAuthenticatedDurationMs() {
        return 0;
    }

    public int getSize() {
        return this.mSize;
    }

    public int getStateForAfterError() {
        return 0;
    }

    public void handleResetAfterError() {
    }

    public void handleResetAfterHelp() {
    }

    public boolean ignoreUnsuccessfulEventsFrom(int i) {
        return false;
    }

    public final boolean isDeviceCredentialAllowed() {
        return Utils.isDeviceCredentialAllowed(this.mPromptInfo);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mTitleView.setText(this.mPromptInfo.getTitle());
        this.mTitleView.setSelected(true);
        this.mSubtitleView.setSelected(true);
        this.mDescriptionView.setMovementMethod(new ScrollingMovementMethod());
        if (isDeviceCredentialAllowed()) {
            int credentialType = Utils.getCredentialType(this.mLockPatternUtils, this.mEffectiveUserId);
            String string = credentialType != 1 ? credentialType != 2 ? getResources().getString(R$string.biometric_dialog_use_password) : getResources().getString(R$string.biometric_dialog_use_pattern) : getResources().getString(R$string.biometric_dialog_use_pin);
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setText(string);
            this.mUseCredentialButton.setVisibility(0);
        } else {
            this.mNegativeButton.setText(this.mPromptInfo.getNegativeButtonText());
        }
        setTextOrHide(this.mSubtitleView, this.mPromptInfo.getSubtitle());
        setTextOrHide(this.mDescriptionView, this.mPromptInfo.getDescription());
        Bundle bundle = this.mSavedState;
        if (bundle == null) {
            updateState(1);
            return;
        }
        updateState(bundle.getInt("state"));
        this.mConfirmButton.setVisibility(this.mSavedState.getInt("confirm_visibility"));
        if (this.mConfirmButton.getVisibility() == 8) {
            setRequireConfirmation(false);
        }
        this.mTryAgainButton.setVisibility(this.mSavedState.getInt("try_agian_visibility"));
    }

    public void onAuthenticationFailed(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i)) {
            return;
        }
        showTemporaryMessage(str, this.mResetErrorRunnable);
        updateState(4);
    }

    public void onAuthenticationSucceeded(int i) {
        removePendingAnimations();
        if (this.mRequireConfirmation || forceRequireConfirmation(i)) {
            updateState(5);
        } else {
            updateState(6);
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mIconController.onConfigurationChanged(configuration);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mIconController.setDeactivated(true);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public void onDialogAnimatedIn() {
        updateState(2);
    }

    public void onError(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i)) {
            return;
        }
        showTemporaryMessage(str, this.mResetErrorRunnable);
        updateState(4);
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda15
            @Override // java.lang.Runnable
            public final void run() {
                AuthBiometricView.$r8$lambda$VegE5yOsiHWU0vJWzcmzXwUISuc(AuthBiometricView.this);
            }
        }, this.mAnimationDurationHideDialog);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R$id.title);
        this.mSubtitleView = (TextView) findViewById(R$id.subtitle);
        this.mDescriptionView = (TextView) findViewById(R$id.description);
        this.mIconViewOverlay = (LottieAnimationView) findViewById(R$id.biometric_icon_overlay);
        this.mIconView = (LottieAnimationView) findViewById(R$id.biometric_icon);
        this.mIconHolderView = findViewById(R$id.biometric_icon_frame);
        this.mIndicatorView = (TextView) findViewById(R$id.indicator);
        this.mNegativeButton = (Button) findViewById(R$id.button_negative);
        this.mCancelButton = (Button) findViewById(R$id.button_cancel);
        this.mUseCredentialButton = (Button) findViewById(R$id.button_use_credential);
        this.mConfirmButton = (Button) findViewById(R$id.button_confirm);
        this.mTryAgainButton = (Button) findViewById(R$id.button_try_again);
        this.mNegativeButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.$r8$lambda$vcX5w0vuWCIrqmRNglfbEc_XnrU(AuthBiometricView.this, view);
            }
        });
        this.mCancelButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda5
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.$r8$lambda$xSMiMwg1iJ4A4YddMF3u7QDYZY4(AuthBiometricView.this, view);
            }
        });
        this.mUseCredentialButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda6
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.$r8$lambda$Nsf0YMm0r0CMfgCkmJfYoxAoLNc(AuthBiometricView.this, view);
            }
        });
        this.mConfirmButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.m1506$r8$lambda$nY4MWrhVDAGSA4T4BLKCuTmE(AuthBiometricView.this, view);
            }
        });
        this.mTryAgainButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda8
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AuthBiometricView.m1502$r8$lambda$EJCeNa3xk6ENrX5FMGHXobUUaE(AuthBiometricView.this, view);
            }
        });
        AuthIconController createIconController = createIconController();
        this.mIconController = createIconController;
        if (createIconController.getActsAsConfirmButton()) {
            this.mIconViewOverlay.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda9
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AuthBiometricView.$r8$lambda$TUVhAR6akTviLvh2_49UedUNOjw(AuthBiometricView.this, view);
                }
            });
            this.mIconView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda10
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AuthBiometricView.m1507$r8$lambda$xx9XBMxb3QYVDxvFKZcBjkDmQo(AuthBiometricView.this, view);
                }
            });
        }
    }

    public void onHelp(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(i)) {
            return;
        }
        if (this.mSize != 2) {
            Log.w("AuthBiometricView", "Help received in size: " + this.mSize);
        } else if (TextUtils.isEmpty(str)) {
            Log.w("AuthBiometricView", "Ignoring blank help message");
        } else {
            showTemporaryMessage(str, this.mResetHelpRunnable);
            updateState(3);
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mIconOriginalY == ActionBarShadowController.ELEVATION_LOW) {
            this.mIconOriginalY = this.mIconHolderView.getY();
            Bundle bundle = this.mSavedState;
            if (bundle == null) {
                updateSize((this.mRequireConfirmation || !supportsSmallDialog()) ? 2 : 1);
                return;
            }
            updateSize(bundle.getInt("size"));
            String string = this.mSavedState.getString("indicator_string");
            if (this.mSavedState.getBoolean("hint_is_temporary")) {
                onHelp(0, string);
            } else if (this.mSavedState.getBoolean("error_is_temporary")) {
                onAuthenticationFailed(0, string);
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        int min;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        if (this.mUseCustomBpSize) {
            min = this.mCustomBpWidth;
            size2 = this.mCustomBpHeight;
        } else {
            min = Math.min(size, size2);
        }
        AuthDialog.LayoutParams onMeasureInternal = onMeasureInternal(min, size2);
        this.mLayoutParams = onMeasureInternal;
        setMeasuredDimension(onMeasureInternal.mMediumWidth, onMeasureInternal.mMediumHeight);
    }

    public AuthDialog.LayoutParams onMeasureInternal(int i, int i2) {
        int childCount = getChildCount();
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i3 >= childCount) {
                return new AuthDialog.LayoutParams(i, i5);
            }
            View childAt = getChildAt(i3);
            if (childAt.getId() == R$id.space_above_icon || childAt.getId() == R$id.space_below_icon || childAt.getId() == R$id.button_bar) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R$id.biometric_icon_frame) {
                View findViewById = findViewById(R$id.biometric_icon);
                childAt.measure(View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().width, 1073741824), View.MeasureSpec.makeMeasureSpec(findViewById.getLayoutParams().height, 1073741824));
            } else if (childAt.getId() == R$id.biometric_icon) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            } else {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(i, 1073741824), View.MeasureSpec.makeMeasureSpec(i2, Integer.MIN_VALUE));
            }
            int i6 = i5;
            if (childAt.getVisibility() != 8) {
                i6 = i5 + childAt.getMeasuredHeight();
            }
            i3++;
            i4 = i6;
        }
    }

    public boolean onPointerDown(Set<Integer> set) {
        return false;
    }

    public void onSaveState(Bundle bundle) {
        bundle.putInt("confirm_visibility", this.mConfirmButton.getVisibility());
        bundle.putInt("try_agian_visibility", this.mTryAgainButton.getVisibility());
        bundle.putInt("state", this.mState);
        bundle.putString("indicator_string", this.mIndicatorView.getText() != null ? this.mIndicatorView.getText().toString() : "");
        bundle.putBoolean("error_is_temporary", this.mHandler.hasCallbacks(this.mResetErrorRunnable));
        bundle.putBoolean("hint_is_temporary", this.mHandler.hasCallbacks(this.mResetHelpRunnable));
        bundle.putInt("size", this.mSize);
    }

    public final void removePendingAnimations() {
        this.mHandler.removeCallbacks(this.mResetHelpRunnable);
        this.mHandler.removeCallbacks(this.mResetErrorRunnable);
    }

    public void restoreState(Bundle bundle) {
        this.mSavedState = bundle;
    }

    public void setBackgroundView(View view) {
        view.setOnClickListener(this.mBackgroundClickListener);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setEffectiveUserId(int i) {
        this.mEffectiveUserId = i;
    }

    public void setJankListener(Animator.AnimatorListener animatorListener) {
        this.mJankListener = animatorListener;
    }

    public void setPanelController(AuthPanelController authPanelController) {
        this.mPanelController = authPanelController;
    }

    public void setPromptInfo(PromptInfo promptInfo) {
        this.mPromptInfo = promptInfo;
    }

    public void setRequireConfirmation(boolean z) {
        this.mRequireConfirmation = z && supportsRequireConfirmation();
    }

    public final void setTextOrHide(TextView textView, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public final void showTemporaryMessage(String str, Runnable runnable) {
        removePendingAnimations();
        this.mIndicatorView.setText(str);
        this.mIndicatorView.setTextColor(this.mTextColorError);
        boolean z = false;
        this.mIndicatorView.setVisibility(0);
        TextView textView = this.mIndicatorView;
        if (!this.mAccessibilityManager.isEnabled() || !this.mAccessibilityManager.isTouchExplorationEnabled()) {
            z = true;
        }
        textView.setSelected(z);
        this.mHandler.postDelayed(runnable, this.mAnimationDurationHideDialog);
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void startTransitionToCredentialUI() {
        updateSize(3);
        this.mCallback.onAction(6);
    }

    public boolean supportsManualRetry() {
        return false;
    }

    public boolean supportsRequireConfirmation() {
        return false;
    }

    public boolean supportsSmallDialog() {
        return false;
    }

    @VisibleForTesting
    public final void updateSize(final int i) {
        Log.v("AuthBiometricView", "Current size: " + this.mSize + " New size: " + i);
        if (i == 1) {
            this.mTitleView.setVisibility(8);
            this.mSubtitleView.setVisibility(8);
            this.mDescriptionView.setVisibility(8);
            this.mIndicatorView.setVisibility(8);
            this.mNegativeButton.setVisibility(8);
            this.mUseCredentialButton.setVisibility(8);
            float dimension = getResources().getDimension(R$dimen.biometric_dialog_icon_padding);
            this.mIconHolderView.setY((getHeight() - this.mIconHolderView.getHeight()) - dimension);
            int height = this.mIconHolderView.getHeight();
            int i2 = (int) dimension;
            this.mPanelController.updateForContentDimensions(this.mLayoutParams.mMediumWidth, ((height + (i2 * 2)) - this.mIconHolderView.getPaddingTop()) - this.mIconHolderView.getPaddingBottom(), 0);
            this.mSize = i;
        } else if (this.mSize == 1 && i == 2) {
            if (this.mDialogSizeAnimating) {
                return;
            }
            this.mDialogSizeAnimating = true;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mIconHolderView.getY(), this.mIconOriginalY);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AuthBiometricView.m1504$r8$lambda$VAsfUtJDalPk1MGi8YaQ87a6H4(AuthBiometricView.this, valueAnimator);
                }
            });
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AuthBiometricView.m1505$r8$lambda$nX3sKknrg9G3GI_erF3EymQkRc(AuthBiometricView.this, valueAnimator);
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(this.mAnimationDurationShort);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthBiometricView.1
                {
                    AuthBiometricView.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    AuthBiometricView.this.mSize = i;
                    AuthBiometricView authBiometricView = AuthBiometricView.this;
                    authBiometricView.mDialogSizeAnimating = false;
                    Utils.notifyAccessibilityContentChanged(authBiometricView.mAccessibilityManager, AuthBiometricView.this);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    super.onAnimationStart(animator);
                    AuthBiometricView.this.mTitleView.setVisibility(0);
                    AuthBiometricView.this.mIndicatorView.setVisibility(0);
                    if (AuthBiometricView.this.isDeviceCredentialAllowed()) {
                        AuthBiometricView.this.mUseCredentialButton.setVisibility(0);
                    } else {
                        AuthBiometricView.this.mNegativeButton.setVisibility(0);
                    }
                    if (AuthBiometricView.this.supportsManualRetry()) {
                        AuthBiometricView.this.mTryAgainButton.setVisibility(0);
                    }
                    if (!TextUtils.isEmpty(AuthBiometricView.this.mSubtitleView.getText())) {
                        AuthBiometricView.this.mSubtitleView.setVisibility(0);
                    }
                    if (TextUtils.isEmpty(AuthBiometricView.this.mDescriptionView.getText())) {
                        return;
                    }
                    AuthBiometricView.this.mDescriptionView.setVisibility(0);
                }
            });
            Animator.AnimatorListener animatorListener = this.mJankListener;
            if (animatorListener != null) {
                animatorSet.addListener(animatorListener);
            }
            animatorSet.play(ofFloat).with(ofFloat2);
            animatorSet.start();
            AuthPanelController authPanelController = this.mPanelController;
            AuthDialog.LayoutParams layoutParams = this.mLayoutParams;
            authPanelController.updateForContentDimensions(layoutParams.mMediumWidth, layoutParams.mMediumHeight, 150);
        } else if (i == 2) {
            AuthPanelController authPanelController2 = this.mPanelController;
            AuthDialog.LayoutParams layoutParams2 = this.mLayoutParams;
            authPanelController2.updateForContentDimensions(layoutParams2.mMediumWidth, layoutParams2.mMediumHeight, 0);
            this.mSize = i;
        } else if (i == 3) {
            ValueAnimator ofFloat3 = ValueAnimator.ofFloat(getY(), getY() - getResources().getDimension(R$dimen.biometric_dialog_medium_to_large_translation_offset));
            ofFloat3.setDuration(this.mAnimationDurationLong);
            ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AuthBiometricView.$r8$lambda$m1SJ5XmbFfb9fxoQMu08fq3Wv8M(AuthBiometricView.this, valueAnimator);
                }
            });
            ofFloat3.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthBiometricView.2
                {
                    AuthBiometricView.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (this.getParent() instanceof ViewGroup) {
                        ((ViewGroup) this.getParent()).removeView(this);
                    }
                    AuthBiometricView.this.mSize = i;
                }
            });
            ValueAnimator ofFloat4 = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
            ofFloat4.setDuration(this.mAnimationDurationLong / 2);
            ofFloat4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AuthBiometricView.m1503$r8$lambda$Il6jnadxa7kL3WO0RiSzfpz6ck(AuthBiometricView.this, valueAnimator);
                }
            });
            this.mPanelController.setUseFullScreen(true);
            AuthPanelController authPanelController3 = this.mPanelController;
            authPanelController3.updateForContentDimensions(authPanelController3.getContainerWidth(), this.mPanelController.getContainerHeight(), this.mAnimationDurationLong);
            AnimatorSet animatorSet2 = new AnimatorSet();
            ArrayList arrayList = new ArrayList();
            arrayList.add(ofFloat3);
            arrayList.add(ofFloat4);
            Animator.AnimatorListener animatorListener2 = this.mJankListener;
            if (animatorListener2 != null) {
                animatorSet2.addListener(animatorListener2);
            }
            animatorSet2.playTogether(arrayList);
            animatorSet2.setDuration((this.mAnimationDurationLong * 2) / 3);
            animatorSet2.start();
        } else {
            Log.e("AuthBiometricView", "Unknown transition from: " + this.mSize + " to: " + i);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public void updateState(int i) {
        Log.v("AuthBiometricView", "newState: " + i);
        this.mIconController.updateState(this.mState, i);
        if (i == 1 || i == 2) {
            removePendingAnimations();
            if (this.mRequireConfirmation) {
                this.mConfirmButton.setEnabled(false);
                this.mConfirmButton.setVisibility(0);
            }
        } else if (i != 4) {
            int i2 = 8;
            if (i == 5) {
                removePendingAnimations();
                this.mNegativeButton.setVisibility(8);
                this.mCancelButton.setVisibility(0);
                this.mUseCredentialButton.setVisibility(8);
                this.mConfirmButton.setEnabled(this.mRequireConfirmation);
                Button button = this.mConfirmButton;
                if (this.mRequireConfirmation) {
                    i2 = 0;
                }
                button.setVisibility(i2);
                this.mIndicatorView.setTextColor(this.mTextColorHint);
                this.mIndicatorView.setText(getConfirmationPrompt());
                this.mIndicatorView.setVisibility(0);
            } else if (i != 6) {
                Log.w("AuthBiometricView", "Unhandled state: " + i);
            } else {
                removePendingAnimations();
                if (this.mSize != 1) {
                    this.mConfirmButton.setVisibility(8);
                    this.mNegativeButton.setVisibility(8);
                    this.mUseCredentialButton.setVisibility(8);
                    this.mCancelButton.setVisibility(8);
                    this.mIndicatorView.setVisibility(4);
                }
                announceForAccessibility(getResources().getString(R$string.biometric_dialog_authenticated));
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.AuthBiometricView$$ExternalSyntheticLambda14
                    @Override // java.lang.Runnable
                    public final void run() {
                        AuthBiometricView.m1500$r8$lambda$2rwDSgFbOdOVjOmncQYw0Cq8(AuthBiometricView.this);
                    }
                }, getDelayAfterAuthenticatedDurationMs());
            }
        } else if (this.mSize == 1) {
            updateSize(2);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
        this.mState = i;
    }
}