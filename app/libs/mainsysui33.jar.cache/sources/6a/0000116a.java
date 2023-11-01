package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.biometrics.PromptInfo;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserManager;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.widget.LockPatternUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthBiometricView;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthCredentialView;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthContainerView.class */
public class AuthContainerView extends LinearLayout implements AuthDialog, WakefulnessLifecycle.Observer {
    public final OnBackInvokedCallback mBackCallback;
    public final DelayableExecutor mBackgroundExecutor;
    public final ImageView mBackgroundView;
    @VisibleForTesting
    public final BiometricCallback mBiometricCallback;
    public final ScrollView mBiometricScrollView;
    public AuthBiometricView mBiometricView;
    public final Config mConfig;
    @VisibleForTesting
    public int mContainerState;
    public byte[] mCredentialAttestation;
    public final CredentialCallback mCredentialCallback;
    @VisibleForTesting
    public AuthCredentialView mCredentialView;
    public final int mEffectiveUserId;
    public final Set<Integer> mFailedModalities;
    public final FrameLayout mFrameLayout;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsOrientationChanged;
    public final Interpolator mLinearOutSlowIn;
    public final LockPatternUtils mLockPatternUtils;
    public OnBackInvokedDispatcher mOnBackInvokedDispatcher;
    public final AuthPanelController mPanelController;
    public final View mPanelView;
    public Integer mPendingCallbackReason;
    public final float mTranslationY;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WindowManager mWindowManager;
    public final IBinder mWindowToken;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthContainerView$BiometricCallback.class */
    public final class BiometricCallback implements AuthBiometricView.Callback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$BiometricCallback$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$WGzy0GjnS_OQKdqRRdbhRtCDdC0(BiometricCallback biometricCallback) {
            biometricCallback.lambda$onAction$0();
        }

        public BiometricCallback() {
            AuthContainerView.this = r4;
        }

        public /* synthetic */ void lambda$onAction$0() {
            AuthContainerView.this.addCredentialView(false, true);
        }

        @Override // com.android.systemui.biometrics.AuthBiometricView.Callback
        public void onAction(int i) {
            switch (i) {
                case 1:
                    AuthContainerView.this.animateAway(4);
                    return;
                case 2:
                    AuthContainerView.this.sendEarlyUserCanceled();
                    AuthContainerView.this.animateAway(1);
                    return;
                case 3:
                    AuthContainerView.this.animateAway(2);
                    return;
                case 4:
                    AuthContainerView.this.mFailedModalities.clear();
                    AuthContainerView.this.mConfig.mCallback.onTryAgainPressed(AuthContainerView.this.getRequestId());
                    return;
                case 5:
                    AuthContainerView.this.animateAway(5);
                    return;
                case 6:
                    AuthContainerView.this.mConfig.mCallback.onDeviceCredentialPressed(AuthContainerView.this.getRequestId());
                    AuthContainerView.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.AuthContainerView$BiometricCallback$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AuthContainerView.BiometricCallback.$r8$lambda$WGzy0GjnS_OQKdqRRdbhRtCDdC0(AuthContainerView.BiometricCallback.this);
                        }
                    }, AuthContainerView.this.mConfig.mSkipAnimation ? 0L : 300L);
                    return;
                default:
                    Log.e("AuthContainerView", "Unhandled action: " + i);
                    return;
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthContainerView$Builder.class */
    public static class Builder {
        public Config mConfig;

        public Builder(Context context) {
            Config config = new Config();
            this.mConfig = config;
            config.mContext = context;
        }

        public AuthContainerView build(DelayableExecutor delayableExecutor, int[] iArr, List<FingerprintSensorPropertiesInternal> list, List<FaceSensorPropertiesInternal> list2, WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils, InteractionJankMonitor interactionJankMonitor) {
            this.mConfig.mSensorIds = iArr;
            return new AuthContainerView(this.mConfig, list, list2, wakefulnessLifecycle, userManager, lockPatternUtils, interactionJankMonitor, new Handler(Looper.getMainLooper()), delayableExecutor);
        }

        public Builder setCallback(AuthDialogCallback authDialogCallback) {
            this.mConfig.mCallback = authDialogCallback;
            return this;
        }

        public Builder setMultiSensorConfig(int i) {
            this.mConfig.mMultiSensorConfig = i;
            return this;
        }

        public Builder setOpPackageName(String str) {
            this.mConfig.mOpPackageName = str;
            return this;
        }

        public Builder setOperationId(long j) {
            this.mConfig.mOperationId = j;
            return this;
        }

        public Builder setPromptInfo(PromptInfo promptInfo) {
            this.mConfig.mPromptInfo = promptInfo;
            return this;
        }

        public Builder setRequestId(long j) {
            this.mConfig.mRequestId = j;
            return this;
        }

        public Builder setRequireConfirmation(boolean z) {
            this.mConfig.mRequireConfirmation = z;
            return this;
        }

        public Builder setScaleFactorProvider(AuthController.ScaleFactorProvider scaleFactorProvider) {
            this.mConfig.mScaleProvider = scaleFactorProvider;
            return this;
        }

        @VisibleForTesting
        public Builder setSkipAnimationDuration(boolean z) {
            this.mConfig.mSkipAnimation = z;
            return this;
        }

        public Builder setSkipIntro(boolean z) {
            this.mConfig.mSkipIntro = z;
            return this;
        }

        public Builder setUserId(int i) {
            this.mConfig.mUserId = i;
            return this;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthContainerView$Config.class */
    public static class Config {
        public AuthDialogCallback mCallback;
        public Context mContext;
        public String mOpPackageName;
        public long mOperationId;
        public PromptInfo mPromptInfo;
        public boolean mRequireConfirmation;
        public AuthController.ScaleFactorProvider mScaleProvider;
        public int[] mSensorIds;
        public boolean mSkipIntro;
        public int mUserId;
        public long mRequestId = -1;
        public boolean mSkipAnimation = false;
        public int mMultiSensorConfig = 0;
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthContainerView$CredentialCallback.class */
    public final class CredentialCallback implements AuthCredentialView.Callback {
        public CredentialCallback() {
            AuthContainerView.this = r4;
        }

        @Override // com.android.systemui.biometrics.AuthCredentialView.Callback
        public void onCredentialMatched(byte[] bArr) {
            AuthContainerView.this.mCredentialAttestation = bArr;
            AuthContainerView.this.animateAway(7);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda0.onBackInvoked():void] */
    /* renamed from: $r8$lambda$BXpCvARR-rKTtla9oGPJNlCFM6s */
    public static /* synthetic */ void m1514$r8$lambda$BXpCvARRrKTtla9oGPJNlCFM6s(AuthContainerView authContainerView) {
        authContainerView.onBackInvoked();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$Toj-OkoMPxgdMsY5WQjQCIODXcc */
    public static /* synthetic */ void m1515$r8$lambda$TojOkoMPxgdMsY5WQjQCIODXcc(AuthContainerView authContainerView) {
        authContainerView.onDialogAnimatedIn();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda5.run():void] */
    /* renamed from: $r8$lambda$b-kkUI2sz6vdKlpov7n3GSYOQ5o */
    public static /* synthetic */ void m1516$r8$lambda$bkkUI2sz6vdKlpov7n3GSYOQ5o(AuthContainerView authContainerView, long j, Runnable runnable) {
        authContainerView.lambda$animateAway$4(j, runnable);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$bIGy-gNfzujH3wrqXc8g66l3WEU */
    public static /* synthetic */ void m1517$r8$lambda$bIGygNfzujH3wrqXc8g66l3WEU(AuthContainerView authContainerView, long j) {
        authContainerView.lambda$onAttachedToWindow$1(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda6.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$bIbR3jaZJANlUEMJPxkd63WLah4(AuthContainerView authContainerView, ValueAnimator valueAnimator) {
        authContainerView.lambda$animateAway$3(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda1.onKey(android.view.View, int, android.view.KeyEvent):boolean] */
    /* renamed from: $r8$lambda$oMF-WSV5YVcuos3s6avRqh8LAbU */
    public static /* synthetic */ boolean m1518$r8$lambda$oMFWSV5YVcuos3s6avRqh8LAbU(AuthContainerView authContainerView, View view, int i, KeyEvent keyEvent) {
        return authContainerView.lambda$new$0(view, i, keyEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$ySF5tx7ooDt4ZBVBk1Bqeqn34DI(AuthContainerView authContainerView) {
        authContainerView.lambda$animateAway$2();
    }

    @VisibleForTesting
    public AuthContainerView(Config config, List<FingerprintSensorPropertiesInternal> list, List<FaceSensorPropertiesInternal> list2, WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils, InteractionJankMonitor interactionJankMonitor, Handler handler, DelayableExecutor delayableExecutor) {
        super(config.mContext);
        this.mWindowToken = new Binder();
        this.mContainerState = 0;
        this.mFailedModalities = new HashSet();
        this.mBackCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda0
            public final void onBackInvoked() {
                AuthContainerView.m1514$r8$lambda$BXpCvARRrKTtla9oGPJNlCFM6s(AuthContainerView.this);
            }
        };
        this.mIsOrientationChanged = false;
        this.mConfig = config;
        this.mLockPatternUtils = lockPatternUtils;
        int credentialOwnerProfile = userManager.getCredentialOwnerProfile(config.mUserId);
        this.mEffectiveUserId = credentialOwnerProfile;
        this.mHandler = handler;
        this.mWindowManager = (WindowManager) ((LinearLayout) this).mContext.getSystemService(WindowManager.class);
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mTranslationY = getResources().getDimension(R$dimen.biometric_dialog_animation_translation_offset);
        this.mLinearOutSlowIn = Interpolators.LINEAR_OUT_SLOW_IN;
        BiometricCallback biometricCallback = new BiometricCallback();
        this.mBiometricCallback = biometricCallback;
        this.mCredentialCallback = new CredentialCallback();
        LayoutInflater from = LayoutInflater.from(((LinearLayout) this).mContext);
        FrameLayout frameLayout = (FrameLayout) from.inflate(R$layout.auth_container_view, (ViewGroup) this, false);
        this.mFrameLayout = frameLayout;
        addView(frameLayout);
        this.mBiometricScrollView = (ScrollView) frameLayout.findViewById(R$id.biometric_scrollview);
        ImageView imageView = (ImageView) frameLayout.findViewById(R$id.background);
        this.mBackgroundView = imageView;
        View findViewById = frameLayout.findViewById(R$id.panel);
        this.mPanelView = findViewById;
        AuthPanelController authPanelController = new AuthPanelController(((LinearLayout) this).mContext, findViewById);
        this.mPanelController = authPanelController;
        this.mBackgroundExecutor = delayableExecutor;
        this.mInteractionJankMonitor = interactionJankMonitor;
        if (Utils.isBiometricAllowed(config.mPromptInfo)) {
            FingerprintSensorPropertiesInternal findFirstSensorProperties = Utils.findFirstSensorProperties(list, config.mSensorIds);
            FaceSensorPropertiesInternal findFirstSensorProperties2 = Utils.findFirstSensorProperties(list2, config.mSensorIds);
            if (findFirstSensorProperties != null && findFirstSensorProperties2 != null) {
                AuthBiometricFingerprintAndFaceView authBiometricFingerprintAndFaceView = (AuthBiometricFingerprintAndFaceView) from.inflate(R$layout.auth_biometric_fingerprint_and_face_view, (ViewGroup) null, false);
                authBiometricFingerprintAndFaceView.setSensorProperties(findFirstSensorProperties);
                authBiometricFingerprintAndFaceView.setScaleFactorProvider(config.mScaleProvider);
                authBiometricFingerprintAndFaceView.updateOverrideIconLayoutParamsSize();
                this.mBiometricView = authBiometricFingerprintAndFaceView;
            } else if (findFirstSensorProperties != null) {
                AuthBiometricFingerprintView authBiometricFingerprintView = (AuthBiometricFingerprintView) from.inflate(R$layout.auth_biometric_fingerprint_view, (ViewGroup) null, false);
                authBiometricFingerprintView.setSensorProperties(findFirstSensorProperties);
                authBiometricFingerprintView.setScaleFactorProvider(config.mScaleProvider);
                authBiometricFingerprintView.updateOverrideIconLayoutParamsSize();
                this.mBiometricView = authBiometricFingerprintView;
            } else if (findFirstSensorProperties2 != null) {
                this.mBiometricView = (AuthBiometricFaceView) from.inflate(R$layout.auth_biometric_face_view, (ViewGroup) null, false);
            } else {
                Log.e("AuthContainerView", "No sensors found!");
            }
        }
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.setRequireConfirmation(config.mRequireConfirmation);
            this.mBiometricView.setPanelController(authPanelController);
            this.mBiometricView.setPromptInfo(config.mPromptInfo);
            this.mBiometricView.setCallback(biometricCallback);
            this.mBiometricView.setBackgroundView(imageView);
            this.mBiometricView.setUserId(config.mUserId);
            this.mBiometricView.setEffectiveUserId(credentialOwnerProfile);
            AuthBiometricView authBiometricView2 = this.mBiometricView;
            authBiometricView2.setJankListener(getJankListener(authBiometricView2, "transit", 450L));
        }
        setOnKeyListener(new View.OnKeyListener() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return AuthContainerView.m1518$r8$lambda$oMFWSV5YVcuos3s6avRqh8LAbU(AuthContainerView.this, view, i, keyEvent);
            }
        });
        setImportantForAccessibility(2);
        setFocusableInTouchMode(true);
        requestFocus();
    }

    @VisibleForTesting
    public static WindowManager.LayoutParams getLayoutParams(IBinder iBinder, CharSequence charSequence) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2041, 17309698, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.setFitInsetsTypes(layoutParams.getFitInsetsTypes() & (WindowInsets.Type.ime() ^ (-1)));
        layoutParams.setTitle("BiometricPrompt");
        layoutParams.accessibilityTitle = charSequence;
        layoutParams.dimAmount = 0.5f;
        layoutParams.token = iBinder;
        return layoutParams;
    }

    public /* synthetic */ void lambda$animateAway$2() {
        setVisibility(4);
        removeWindowIfAttached();
    }

    public /* synthetic */ void lambda$animateAway$3(ValueAnimator valueAnimator) {
        if (this.mWindowManager == null || getViewRootImpl() == null) {
            Log.w("AuthContainerView", "skip updateViewLayout() for dim animation.");
            return;
        }
        WindowManager.LayoutParams layoutParams = getViewRootImpl().mWindowAttributes;
        layoutParams.dimAmount = (1.0f - ((Float) valueAnimator.getAnimatedValue()).floatValue()) * 0.5f;
        this.mWindowManager.updateViewLayout(this, layoutParams);
    }

    public /* synthetic */ void lambda$animateAway$4(long j, Runnable runnable) {
        animate().alpha(ActionBarShadowController.ELEVATION_LOW).translationY(this.mTranslationY).setDuration(j).setInterpolator(this.mLinearOutSlowIn).setListener(getJankListener(this, "dismiss", j)).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthContainerView.$r8$lambda$bIbR3jaZJANlUEMJPxkd63WLah4(AuthContainerView.this, valueAnimator);
            }
        }).withLayer().withEndAction(runnable).start();
    }

    public /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        if (i != 4) {
            return false;
        }
        if (keyEvent.getAction() == 1) {
            onBackInvoked();
            return true;
        }
        return true;
    }

    public /* synthetic */ void lambda$onAttachedToWindow$1(long j) {
        animate().alpha(1.0f).translationY(ActionBarShadowController.ELEVATION_LOW).setDuration(j).setInterpolator(this.mLinearOutSlowIn).withLayer().setListener(getJankListener(this, "show", j)).withEndAction(new Runnable() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                AuthContainerView.m1515$r8$lambda$TojOkoMPxgdMsY5WQjQCIODXcc(AuthContainerView.this);
            }
        }).start();
    }

    public static boolean shouldUpdatePositionForUdfps(View view) {
        if (view instanceof AuthBiometricFingerprintView) {
            return ((AuthBiometricFingerprintView) view).isUdfps();
        }
        return false;
    }

    public final void addCredentialView(boolean z, boolean z2) {
        LayoutInflater from = LayoutInflater.from(((LinearLayout) this).mContext);
        int credentialType = Utils.getCredentialType(this.mLockPatternUtils, this.mEffectiveUserId);
        if (credentialType != 1) {
            if (credentialType == 2) {
                this.mCredentialView = (AuthCredentialView) from.inflate(R$layout.auth_credential_pattern_view, (ViewGroup) null, false);
                this.mBackgroundView.setOnClickListener(null);
                this.mBackgroundView.setImportantForAccessibility(2);
                this.mCredentialView.setContainerView(this);
                this.mCredentialView.setUserId(this.mConfig.mUserId);
                this.mCredentialView.setOperationId(this.mConfig.mOperationId);
                this.mCredentialView.setEffectiveUserId(this.mEffectiveUserId);
                this.mCredentialView.setCredentialType(credentialType);
                this.mCredentialView.setCallback(this.mCredentialCallback);
                this.mCredentialView.setPromptInfo(this.mConfig.mPromptInfo);
                this.mCredentialView.setPanelController(this.mPanelController, z);
                this.mCredentialView.setShouldAnimateContents(z2);
                this.mCredentialView.setBackgroundExecutor(this.mBackgroundExecutor);
                this.mFrameLayout.addView(this.mCredentialView);
            } else if (credentialType != 3) {
                throw new IllegalStateException("Unknown credential type: " + credentialType);
            }
        }
        this.mCredentialView = (AuthCredentialView) from.inflate(R$layout.auth_credential_password_view, (ViewGroup) null, false);
        this.mBackgroundView.setOnClickListener(null);
        this.mBackgroundView.setImportantForAccessibility(2);
        this.mCredentialView.setContainerView(this);
        this.mCredentialView.setUserId(this.mConfig.mUserId);
        this.mCredentialView.setOperationId(this.mConfig.mOperationId);
        this.mCredentialView.setEffectiveUserId(this.mEffectiveUserId);
        this.mCredentialView.setCredentialType(credentialType);
        this.mCredentialView.setCallback(this.mCredentialCallback);
        this.mCredentialView.setPromptInfo(this.mConfig.mPromptInfo);
        this.mCredentialView.setPanelController(this.mPanelController, z);
        this.mCredentialView.setShouldAnimateContents(z2);
        this.mCredentialView.setBackgroundExecutor(this.mBackgroundExecutor);
        this.mFrameLayout.addView(this.mCredentialView);
    }

    public void animateAway(int i) {
        animateAway(true, i);
    }

    public final void animateAway(boolean z, int i) {
        int i2 = this.mContainerState;
        if (i2 == 1) {
            Log.w("AuthContainerView", "startDismiss(): waiting for onDialogAnimatedIn");
            this.mContainerState = 2;
        } else if (i2 == 4) {
            Log.w("AuthContainerView", "Already dismissing, sendReason: " + z + " reason: " + i);
        } else {
            this.mContainerState = 4;
            if (isAttachedToWindow() && getRootWindowInsets().isVisible(WindowInsets.Type.ime())) {
                getWindowInsetsController().hide(WindowInsets.Type.ime());
            }
            if (z) {
                this.mPendingCallbackReason = Integer.valueOf(i);
            } else {
                this.mPendingCallbackReason = null;
            }
            final Runnable runnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    AuthContainerView.$r8$lambda$ySF5tx7ooDt4ZBVBk1Bqeqn34DI(AuthContainerView.this);
                }
            };
            final long j = this.mConfig.mSkipAnimation ? 0L : 350L;
            postOnAnimation(new Runnable() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    AuthContainerView.m1516$r8$lambda$bkkUI2sz6vdKlpov7n3GSYOQ5o(AuthContainerView.this, j, runnable);
                }
            });
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void animateToCredentialUI() {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.startTransitionToCredentialUI();
        } else {
            Log.e("AuthContainerView", "animateToCredentialUI(): mBiometricView is null");
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void dismissFromSystemServer() {
        animateAway(false, 0);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void dismissWithoutCallback(boolean z) {
        if (z) {
            animateAway(false, 0);
            return;
        }
        forceExecuteAnimatedIn();
        removeWindowIfAttached();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("    isAttachedToWindow=" + isAttachedToWindow());
        printWriter.println("    containerState=" + this.mContainerState);
        printWriter.println("    pendingCallbackReason=" + this.mPendingCallbackReason);
        StringBuilder sb = new StringBuilder();
        sb.append("    config exist=");
        sb.append(this.mConfig != null);
        printWriter.println(sb.toString());
        if (this.mConfig != null) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("    config.sensorIds exist=");
            sb2.append(this.mConfig.mSensorIds != null);
            printWriter.println(sb2.toString());
        }
        AuthBiometricView authBiometricView = this.mBiometricView;
        printWriter.println("    scrollView=" + findViewById(R$id.biometric_scrollview));
        printWriter.println("      biometricView=" + authBiometricView);
        if (authBiometricView != null) {
            int i = R$id.title;
            int i2 = R$id.subtitle;
            int i3 = R$id.description;
            int i4 = R$id.biometric_icon_frame;
            int i5 = R$id.biometric_icon;
            int i6 = R$id.indicator;
            int i7 = R$id.button_bar;
            int i8 = R$id.button_negative;
            int i9 = R$id.button_use_credential;
            int i10 = R$id.button_confirm;
            int i11 = R$id.button_try_again;
            for (int i12 = 0; i12 < 11; i12++) {
                int i13 = new int[]{i, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11}[i12];
                printWriter.println("        " + authBiometricView.findViewById(i13));
            }
        }
    }

    public final void forceExecuteAnimatedIn() {
        if (this.mContainerState == 1) {
            AuthCredentialView authCredentialView = this.mCredentialView;
            if (authCredentialView != null && authCredentialView.isAttachedToWindow()) {
                this.mCredentialView.animate().cancel();
            }
            this.mPanelView.animate().cancel();
            this.mBiometricView.animate().cancel();
            animate().cancel();
            onDialogAnimatedIn();
        }
    }

    public final Animator.AnimatorListener getJankListener(final View view, final String str, final long j) {
        return new Animator.AnimatorListener() { // from class: com.android.systemui.biometrics.AuthContainerView.1
            {
                AuthContainerView.this = this;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                if (view.isAttachedToWindow()) {
                    AuthContainerView.this.mInteractionJankMonitor.cancel(56);
                } else {
                    Log.w("AuthContainerView", "Un-attached view should not cancel Jank trace.");
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (view.isAttachedToWindow()) {
                    AuthContainerView.this.mInteractionJankMonitor.end(56);
                } else {
                    Log.w("AuthContainerView", "Un-attached view should not end Jank trace.");
                }
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                if (view.isAttachedToWindow()) {
                    AuthContainerView.this.mInteractionJankMonitor.begin(InteractionJankMonitor.Configuration.Builder.withView(56, view).setTag(str).setTimeout(j));
                } else {
                    Log.w("AuthContainerView", "Un-attached view should not begin Jank trace.");
                }
            }
        };
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public String getOpPackageName() {
        return this.mConfig.mOpPackageName;
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public long getRequestId() {
        return this.mConfig.mRequestId;
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public boolean isAllowDeviceCredentials() {
        return Utils.isDeviceCredentialAllowed(this.mConfig.mPromptInfo);
    }

    public final boolean maybeUpdatePositionForUdfps(boolean z) {
        Display display = getDisplay();
        if (display != null && shouldUpdatePositionForUdfps(this.mBiometricView)) {
            int rotation = display.getRotation();
            if (rotation == 0) {
                this.mPanelController.setPosition(1);
                setScrollViewGravity(81);
            } else if (rotation == 1) {
                this.mPanelController.setPosition(3);
                setScrollViewGravity(21);
            } else if (rotation != 3) {
                Log.e("AuthContainerView", "Unsupported display rotation: " + rotation);
                this.mPanelController.setPosition(1);
                setScrollViewGravity(81);
            } else {
                this.mPanelController.setPosition(2);
                setScrollViewGravity(19);
            }
            if (z) {
                this.mPanelView.invalidateOutline();
                this.mBiometricView.requestLayout();
                return true;
            }
            return true;
        }
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mWakefulnessLifecycle.addObserver(this);
        if (Utils.isBiometricAllowed(this.mConfig.mPromptInfo)) {
            this.mBiometricScrollView.addView(this.mBiometricView);
        } else if (!Utils.isDeviceCredentialAllowed(this.mConfig.mPromptInfo)) {
            throw new IllegalStateException("Unknown configuration: " + this.mConfig.mPromptInfo.getAuthenticators());
        } else {
            addCredentialView(true, false);
        }
        maybeUpdatePositionForUdfps(false);
        if (this.mConfig.mSkipIntro) {
            this.mContainerState = 3;
        } else {
            this.mContainerState = 1;
            setY(this.mTranslationY);
            setAlpha(ActionBarShadowController.ELEVATION_LOW);
            final long j = this.mConfig.mSkipAnimation ? 0L : 250L;
            postOnAnimation(new Runnable() { // from class: com.android.systemui.biometrics.AuthContainerView$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AuthContainerView.m1517$r8$lambda$bIGygNfzujH3wrqXc8g66l3WEU(AuthContainerView.this, j);
                }
            });
        }
        OnBackInvokedDispatcher findOnBackInvokedDispatcher = findOnBackInvokedDispatcher();
        this.mOnBackInvokedDispatcher = findOnBackInvokedDispatcher;
        if (findOnBackInvokedDispatcher != null) {
            findOnBackInvokedDispatcher.registerOnBackInvokedCallback(0, this.mBackCallback);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onAuthenticationFailed(int i, String str) {
        if (this.mBiometricView == null) {
            Log.e("AuthContainerView", "onAuthenticationFailed(): mBiometricView is null");
            return;
        }
        this.mFailedModalities.add(Integer.valueOf(i));
        this.mBiometricView.onAuthenticationFailed(i, str);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onAuthenticationSucceeded(int i) {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.onAuthenticationSucceeded(i);
        } else {
            Log.e("AuthContainerView", "onAuthenticationSucceeded(): mBiometricView is null");
        }
    }

    public final void onBackInvoked() {
        sendEarlyUserCanceled();
        animateAway(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mOnBackInvokedDispatcher;
        if (onBackInvokedDispatcher != null) {
            onBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.mBackCallback);
            this.mOnBackInvokedDispatcher = null;
        }
        this.mWakefulnessLifecycle.removeObserver(this);
    }

    public final void onDialogAnimatedIn() {
        int i = this.mContainerState;
        if (i == 2) {
            Log.d("AuthContainerView", "onDialogAnimatedIn(): mPendingDismissDialog=true, dismissing now");
            animateAway(1);
        } else if (i == 4 || i == 5) {
            Log.d("AuthContainerView", "onDialogAnimatedIn(): ignore, already animating out or gone - state: " + this.mContainerState);
        } else {
            this.mContainerState = 3;
            if (this.mBiometricView != null) {
                this.mConfig.mCallback.onDialogAnimatedIn(getRequestId());
                this.mBiometricView.onDialogAnimatedIn();
            }
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onError(int i, String str) {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.onError(i, str);
        } else {
            Log.e("AuthContainerView", "onError(): mBiometricView is null");
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onHelp(int i, String str) {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.onHelp(i, str);
        } else {
            Log.e("AuthContainerView", "onHelp(): mBiometricView is null");
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mPanelController.setContainerDimensions(getMeasuredWidth(), getMeasuredHeight());
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onOrientationChanged() {
        maybeUpdatePositionForUdfps(true);
        this.mIsOrientationChanged = true;
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onPointerDown() {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView == null) {
            Log.e("AuthContainerView", "onPointerDown(): mBiometricView is null");
        } else if (authBiometricView.onPointerDown(this.mFailedModalities)) {
            Log.d("AuthContainerView", "retrying failed modalities (pointer down)");
            this.mBiometricCallback.onAction(4);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void onSaveState(Bundle bundle) {
        bundle.putBoolean("container_going_away", this.mContainerState == 4);
        bundle.putBoolean("biometric_showing", this.mBiometricView != null && this.mCredentialView == null);
        bundle.putBoolean("credential_showing", this.mCredentialView != null);
        bundle.putBoolean("orientation_changed", this.mIsOrientationChanged);
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.onSaveState(bundle);
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        animateAway(1);
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            return;
        }
        if (this.mIsOrientationChanged) {
            this.mIsOrientationChanged = false;
            return;
        }
        Log.v("AuthContainerView", "Lost window focus, dismissing the dialog");
        animateAway(1);
    }

    public final void removeWindowIfAttached() {
        sendPendingCallbackIfNotNull();
        if (this.mContainerState == 5) {
            return;
        }
        this.mContainerState = 5;
        if (isAttachedToWindow()) {
            this.mWindowManager.removeViewImmediate(this);
        }
    }

    public void sendEarlyUserCanceled() {
        this.mConfig.mCallback.onSystemEvent(1, getRequestId());
    }

    public final void sendPendingCallbackIfNotNull() {
        Log.d("AuthContainerView", "pendingCallback: " + this.mPendingCallbackReason);
        Integer num = this.mPendingCallbackReason;
        if (num != null) {
            this.mConfig.mCallback.onDismissed(num.intValue(), this.mCredentialAttestation, getRequestId());
            this.mPendingCallbackReason = null;
        }
    }

    public final void setScrollViewGravity(int i) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBiometricScrollView.getLayoutParams();
        layoutParams.gravity = i;
        this.mBiometricScrollView.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.biometrics.AuthDialog
    public void show(WindowManager windowManager, Bundle bundle) {
        AuthBiometricView authBiometricView = this.mBiometricView;
        if (authBiometricView != null) {
            authBiometricView.restoreState(bundle);
        }
        if (bundle != null) {
            this.mIsOrientationChanged = bundle.getBoolean("orientation_changed");
        }
        windowManager.addView(this, getLayoutParams(this.mWindowToken, this.mConfig.mPromptInfo.getTitle()));
    }
}