package com.android.systemui.biometrics;

import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.UserInfo;
import android.hardware.biometrics.PromptInfo;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialView.class */
public abstract class AuthCredentialView extends LinearLayout {
    public final AccessibilityManager mAccessibilityManager;
    public DelayableExecutor mBackgroundExecutor;
    public Callback mCallback;
    public final Runnable mClearErrorRunnable;
    public AuthContainerView mContainerView;
    public int mCredentialType;
    public TextView mDescriptionView;
    public final DevicePolicyManager mDevicePolicyManager;
    public int mEffectiveUserId;
    public ErrorTimer mErrorTimer;
    public TextView mErrorView;
    public final Handler mHandler;
    public ImageView mIconView;
    public final LockPatternUtils mLockPatternUtils;
    public long mOperationId;
    public AuthPanelController mPanelController;
    public AsyncTask<?, ?, ?> mPendingLockCheck;
    public PromptInfo mPromptInfo;
    public boolean mShouldAnimateContents;
    public boolean mShouldAnimatePanel;
    public TextView mSubtitleView;
    public TextView mTitleView;
    public int mUserId;
    public final UserManager mUserManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialView$Callback.class */
    public interface Callback {
        void onCredentialMatched(byte[] bArr);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialView$ErrorTimer.class */
    public static class ErrorTimer extends CountDownTimer {
        public final Context mContext;
        public final TextView mErrorView;

        public ErrorTimer(Context context, long j, long j2, TextView textView) {
            super(j, j2);
            this.mErrorView = textView;
            this.mContext = context;
        }

        @Override // android.os.CountDownTimer
        public void onTick(long j) {
            this.mErrorView.setText(this.mContext.getString(R$string.biometric_dialog_credential_too_many_attempts, Integer.valueOf((int) (j / 1000))));
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda5.get():java.lang.Object] */
    /* renamed from: $r8$lambda$06Ge7WVOmdRy4oAG0qAVkE-qopc */
    public static /* synthetic */ String m1537$r8$lambda$06Ge7WVOmdRy4oAG0qAVkEqopc(AuthCredentialView authCredentialView, int i) {
        return authCredentialView.lambda$getNowWipingMessage$5(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$4bP0GkhsIVjMeXEqBuxkvphZVwQ(AuthCredentialView authCredentialView) {
        authCredentialView.lambda$showNowWipingDialog$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda6.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$CnNAEf5m7Okdt4yq8_mRRO23tb4(AuthCredentialView authCredentialView, int i) {
        return authCredentialView.lambda$getLastAttemptBeforeWipeProfileMessage$4(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$DE1bvlhUnDPh9tMREGAUhTuvNiA(AuthCredentialView authCredentialView) {
        authCredentialView.lambda$onAttachedToWindow$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda4.onDismiss(android.content.DialogInterface):void] */
    public static /* synthetic */ void $r8$lambda$jmadMBZ1d1tifa5wbbhPQa6ZbPY(AuthCredentialView authCredentialView, DialogInterface dialogInterface) {
        authCredentialView.lambda$showNowWipingDialog$2(dialogInterface);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$nI-igrPbFzSq7-enBYMNo4EYwFc */
    public static /* synthetic */ void m1538$r8$lambda$nIigrPbFzSq7enBYMNo4EYwFc(AuthCredentialView authCredentialView) {
        authCredentialView.lambda$showLastAttemptBeforeWipeDialog$1();
    }

    public AuthCredentialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mClearErrorRunnable = new Runnable() { // from class: com.android.systemui.biometrics.AuthCredentialView.1
            {
                AuthCredentialView.this = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                TextView textView = AuthCredentialView.this.mErrorView;
                if (textView != null) {
                    textView.setText("");
                }
            }
        };
        this.mLockPatternUtils = new LockPatternUtils(((LinearLayout) this).mContext);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mAccessibilityManager = (AccessibilityManager) ((LinearLayout) this).mContext.getSystemService(AccessibilityManager.class);
        this.mUserManager = (UserManager) ((LinearLayout) this).mContext.getSystemService(UserManager.class);
        this.mDevicePolicyManager = (DevicePolicyManager) ((LinearLayout) this).mContext.getSystemService(DevicePolicyManager.class);
    }

    public static CharSequence getDescription(PromptInfo promptInfo) {
        CharSequence deviceCredentialDescription = promptInfo.getDeviceCredentialDescription();
        return deviceCredentialDescription != null ? deviceCredentialDescription : promptInfo.getDescription();
    }

    public static String getLastAttemptBeforeWipeProfileUpdatableStringId(int i) {
        return i != 1 ? i != 2 ? "SystemUi.BIOMETRIC_DIALOG_WORK_PASSWORD_LAST_ATTEMPT" : "SystemUi.BIOMETRIC_DIALOG_WORK_PATTERN_LAST_ATTEMPT" : "SystemUi.BIOMETRIC_DIALOG_WORK_PIN_LAST_ATTEMPT";
    }

    public static CharSequence getSubtitle(PromptInfo promptInfo) {
        CharSequence deviceCredentialSubtitle = promptInfo.getDeviceCredentialSubtitle();
        return deviceCredentialSubtitle != null ? deviceCredentialSubtitle : promptInfo.getSubtitle();
    }

    public static CharSequence getTitle(PromptInfo promptInfo) {
        CharSequence deviceCredentialTitle = promptInfo.getDeviceCredentialTitle();
        return deviceCredentialTitle != null ? deviceCredentialTitle : promptInfo.getTitle();
    }

    public /* synthetic */ void lambda$onAttachedToWindow$0() {
        animate().translationY(ActionBarShadowController.ELEVATION_LOW).setDuration(150L).alpha(1.0f).setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN).withLayer().start();
    }

    public /* synthetic */ void lambda$showLastAttemptBeforeWipeDialog$1() {
        AlertDialog create = new AlertDialog.Builder(((LinearLayout) this).mContext).setTitle(R$string.biometric_dialog_last_attempt_before_wipe_dialog_title).setMessage(getLastAttemptBeforeWipeMessage(getUserTypeForWipe(), this.mCredentialType)).setPositiveButton(17039370, (DialogInterface.OnClickListener) null).create();
        create.getWindow().setType(2017);
        this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda3(create));
    }

    public /* synthetic */ void lambda$showNowWipingDialog$2(DialogInterface dialogInterface) {
        this.mContainerView.animateAway(5);
    }

    public /* synthetic */ void lambda$showNowWipingDialog$3() {
        AlertDialog create = new AlertDialog.Builder(((LinearLayout) this).mContext).setMessage(getNowWipingMessage(getUserTypeForWipe())).setPositiveButton(com.android.settingslib.R$string.failed_attempts_now_wiping_dialog_dismiss, (DialogInterface.OnClickListener) null).setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnDismissListener
            public final void onDismiss(DialogInterface dialogInterface) {
                AuthCredentialView.$r8$lambda$jmadMBZ1d1tifa5wbbhPQa6ZbPY(AuthCredentialView.this, dialogInterface);
            }
        }).create();
        create.getWindow().setType(2017);
        this.mHandler.post(new AuthCredentialView$$ExternalSyntheticLambda3(create));
    }

    private void setTextOrHide(TextView textView, CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            textView.setVisibility(8);
        } else {
            textView.setText(charSequence);
        }
        Utils.notifyAccessibilityContentChanged(this.mAccessibilityManager, this);
    }

    public final String getLastAttemptBeforeWipeDeviceMessage(int i) {
        return i != 1 ? i != 2 ? ((LinearLayout) this).mContext.getString(R$string.biometric_dialog_last_password_attempt_before_wipe_device) : ((LinearLayout) this).mContext.getString(R$string.biometric_dialog_last_pattern_attempt_before_wipe_device) : ((LinearLayout) this).mContext.getString(R$string.biometric_dialog_last_pin_attempt_before_wipe_device);
    }

    public final String getLastAttemptBeforeWipeMessage(int i, int i2) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return getLastAttemptBeforeWipeUserMessage(i2);
                }
                throw new IllegalArgumentException("Unrecognized user type:" + i);
            }
            return getLastAttemptBeforeWipeProfileMessage(i2);
        }
        return getLastAttemptBeforeWipeDeviceMessage(i2);
    }

    /* renamed from: getLastAttemptBeforeWipeProfileDefaultMessage */
    public final String lambda$getLastAttemptBeforeWipeProfileMessage$4(int i) {
        return ((LinearLayout) this).mContext.getString(i != 1 ? i != 2 ? R$string.biometric_dialog_last_password_attempt_before_wipe_profile : R$string.biometric_dialog_last_pattern_attempt_before_wipe_profile : R$string.biometric_dialog_last_pin_attempt_before_wipe_profile);
    }

    public final String getLastAttemptBeforeWipeProfileMessage(final int i) {
        return this.mDevicePolicyManager.getResources().getString(getLastAttemptBeforeWipeProfileUpdatableStringId(i), new Supplier() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda6
            @Override // java.util.function.Supplier
            public final Object get() {
                return AuthCredentialView.$r8$lambda$CnNAEf5m7Okdt4yq8_mRRO23tb4(AuthCredentialView.this, i);
            }
        });
    }

    public final String getLastAttemptBeforeWipeUserMessage(int i) {
        return ((LinearLayout) this).mContext.getString(i != 1 ? i != 2 ? R$string.biometric_dialog_last_password_attempt_before_wipe_user : R$string.biometric_dialog_last_pattern_attempt_before_wipe_user : R$string.biometric_dialog_last_pin_attempt_before_wipe_user);
    }

    /* renamed from: getNowWipingDefaultMessage */
    public final String lambda$getNowWipingMessage$5(int i) {
        int i2;
        if (i == 1) {
            i2 = com.android.settingslib.R$string.failed_attempts_now_wiping_device;
        } else if (i == 2) {
            i2 = com.android.settingslib.R$string.failed_attempts_now_wiping_profile;
        } else if (i != 3) {
            throw new IllegalArgumentException("Unrecognized user type:" + i);
        } else {
            i2 = com.android.settingslib.R$string.failed_attempts_now_wiping_user;
        }
        return ((LinearLayout) this).mContext.getString(i2);
    }

    public final String getNowWipingMessage(final int i) {
        return this.mDevicePolicyManager.getResources().getString(getNowWipingUpdatableStringId(i), new Supplier() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return AuthCredentialView.m1537$r8$lambda$06Ge7WVOmdRy4oAG0qAVkEqopc(AuthCredentialView.this, i);
            }
        });
    }

    public final String getNowWipingUpdatableStringId(int i) {
        return i != 2 ? "UNDEFINED" : "SystemUi.BIOMETRIC_DIALOG_WORK_LOCK_FAILED_ATTEMPTS";
    }

    public final int getUserTypeForWipe() {
        UserInfo userInfo = this.mUserManager.getUserInfo(this.mDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(this.mEffectiveUserId));
        if (userInfo == null || userInfo.isPrimary()) {
            return 1;
        }
        return userInfo.isManagedProfile() ? 2 : 3;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        CharSequence title = getTitle(this.mPromptInfo);
        setText(this.mTitleView, title);
        setTextOrHide(this.mSubtitleView, getSubtitle(this.mPromptInfo));
        setTextOrHide(this.mDescriptionView, getDescription(this.mPromptInfo));
        announceForAccessibility(title);
        if (this.mIconView != null) {
            this.mIconView.setImageDrawable(Utils.isManagedProfile(((LinearLayout) this).mContext, this.mEffectiveUserId) ? getResources().getDrawable(R$drawable.auth_dialog_enterprise, ((LinearLayout) this).mContext.getTheme()) : getResources().getDrawable(R$drawable.auth_dialog_lock, ((LinearLayout) this).mContext.getTheme()));
        }
        if (this.mShouldAnimateContents) {
            setTranslationY(getResources().getDimension(R$dimen.biometric_dialog_credential_translation_offset));
            setAlpha(ActionBarShadowController.ELEVATION_LOW);
            postOnAnimation(new Runnable() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AuthCredentialView.$r8$lambda$DE1bvlhUnDPh9tMREGAUhTuvNiA(AuthCredentialView.this);
                }
            });
        }
    }

    public void onCredentialVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
        if (verifyCredentialResponse.isMatched()) {
            this.mClearErrorRunnable.run();
            this.mLockPatternUtils.userPresent(this.mEffectiveUserId);
            long gatekeeperPasswordHandle = verifyCredentialResponse.getGatekeeperPasswordHandle();
            this.mCallback.onCredentialMatched(this.mLockPatternUtils.verifyGatekeeperPasswordHandle(gatekeeperPasswordHandle, this.mOperationId, this.mEffectiveUserId).getGatekeeperHAT());
            this.mLockPatternUtils.removeGatekeeperPasswordHandle(gatekeeperPasswordHandle);
        } else if (i > 0) {
            this.mHandler.removeCallbacks(this.mClearErrorRunnable);
            ErrorTimer errorTimer = new ErrorTimer(((LinearLayout) this).mContext, this.mLockPatternUtils.setLockoutAttemptDeadline(this.mEffectiveUserId, i) - SystemClock.elapsedRealtime(), 1000L, this.mErrorView) { // from class: com.android.systemui.biometrics.AuthCredentialView.2
                {
                    AuthCredentialView.this = this;
                }

                @Override // android.os.CountDownTimer
                public void onFinish() {
                    AuthCredentialView.this.onErrorTimeoutFinish();
                    AuthCredentialView.this.mClearErrorRunnable.run();
                }
            };
            this.mErrorTimer = errorTimer;
            errorTimer.start();
        } else if (reportFailedAttempt()) {
        } else {
            int i2 = this.mCredentialType;
            showError(getResources().getString(i2 != 1 ? i2 != 2 ? R$string.biometric_dialog_wrong_password : R$string.biometric_dialog_wrong_pattern : R$string.biometric_dialog_wrong_pin));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ErrorTimer errorTimer = this.mErrorTimer;
        if (errorTimer != null) {
            errorTimer.cancel();
        }
    }

    public void onErrorTimeoutFinish() {
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTitleView = (TextView) findViewById(R$id.title);
        this.mSubtitleView = (TextView) findViewById(R$id.subtitle);
        this.mDescriptionView = (TextView) findViewById(R$id.description);
        this.mIconView = (ImageView) findViewById(R$id.icon);
        this.mErrorView = (TextView) findViewById(R$id.error);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mShouldAnimatePanel) {
            this.mPanelController.setUseFullScreen(true);
            AuthPanelController authPanelController = this.mPanelController;
            authPanelController.updateForContentDimensions(authPanelController.getContainerWidth(), this.mPanelController.getContainerHeight(), 0);
            this.mShouldAnimatePanel = false;
        }
    }

    public final boolean reportFailedAttempt() {
        boolean updateErrorMessage = updateErrorMessage(this.mLockPatternUtils.getCurrentFailedPasswordAttempts(this.mEffectiveUserId) + 1);
        this.mLockPatternUtils.reportFailedPasswordAttempt(this.mEffectiveUserId);
        return updateErrorMessage;
    }

    public void setBackgroundExecutor(DelayableExecutor delayableExecutor) {
        this.mBackgroundExecutor = delayableExecutor;
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setContainerView(AuthContainerView authContainerView) {
        this.mContainerView = authContainerView;
    }

    public void setCredentialType(int i) {
        this.mCredentialType = i;
    }

    public void setEffectiveUserId(int i) {
        this.mEffectiveUserId = i;
    }

    public void setOperationId(long j) {
        this.mOperationId = j;
    }

    public void setPanelController(AuthPanelController authPanelController, boolean z) {
        this.mPanelController = authPanelController;
        this.mShouldAnimatePanel = z;
    }

    public void setPromptInfo(PromptInfo promptInfo) {
        this.mPromptInfo = promptInfo;
    }

    public void setShouldAnimateContents(boolean z) {
        this.mShouldAnimateContents = z;
    }

    public final void setText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
    }

    public void setUserId(int i) {
        this.mUserId = i;
    }

    public void showError(String str) {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacks(this.mClearErrorRunnable);
            this.mHandler.postDelayed(this.mClearErrorRunnable, 3000L);
        }
        TextView textView = this.mErrorView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public final void showLastAttemptBeforeWipeDialog() {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AuthCredentialView.m1538$r8$lambda$nIigrPbFzSq7enBYMNo4EYwFc(AuthCredentialView.this);
            }
        });
    }

    public final void showNowWipingDialog() {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.AuthCredentialView$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AuthCredentialView.$r8$lambda$4bP0GkhsIVjMeXEqBuxkvphZVwQ(AuthCredentialView.this);
            }
        });
    }

    public final boolean updateErrorMessage(int i) {
        int maximumFailedPasswordsForWipe = this.mLockPatternUtils.getMaximumFailedPasswordsForWipe(this.mEffectiveUserId);
        if (maximumFailedPasswordsForWipe <= 0 || i <= 0) {
            return false;
        }
        if (this.mErrorView != null) {
            showError(getResources().getString(R$string.biometric_dialog_credential_attempts_before_wipe, Integer.valueOf(i), Integer.valueOf(maximumFailedPasswordsForWipe)));
        }
        int i2 = maximumFailedPasswordsForWipe - i;
        if (i2 == 1) {
            showLastAttemptBeforeWipeDialog();
            return true;
        } else if (i2 <= 0) {
            showNowWipingDialog();
            return true;
        } else {
            return true;
        }
    }
}