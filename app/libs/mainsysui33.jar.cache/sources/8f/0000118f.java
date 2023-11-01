package com.android.systemui.biometrics;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.internal.widget.VerifyCredentialResponse;
import com.android.systemui.R$id;
import com.android.systemui.biometrics.AuthCredentialPatternView;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialPatternView.class */
public class AuthCredentialPatternView extends AuthCredentialView {
    @VisibleForTesting
    public LockPatternView mLockPatternView;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthCredentialPatternView$UnlockPatternListener.class */
    public class UnlockPatternListener implements LockPatternView.OnPatternListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthCredentialPatternView$UnlockPatternListener$$ExternalSyntheticLambda0.onVerified(com.android.internal.widget.VerifyCredentialResponse, int):void] */
        public static /* synthetic */ void $r8$lambda$DzEEwr7zVIysKmmyBDaJH_abX3g(UnlockPatternListener unlockPatternListener, VerifyCredentialResponse verifyCredentialResponse, int i) {
            unlockPatternListener.onPatternVerified(verifyCredentialResponse, i);
        }

        public UnlockPatternListener() {
            AuthCredentialPatternView.this = r4;
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> list) {
        }

        public void onPatternCleared() {
        }

        public void onPatternDetected(List<LockPatternView.Cell> list) {
            AsyncTask<?, ?, ?> asyncTask = AuthCredentialPatternView.this.mPendingLockCheck;
            if (asyncTask != null) {
                asyncTask.cancel(false);
            }
            AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            if (list.size() < 4) {
                onPatternVerified(VerifyCredentialResponse.ERROR, 0);
                return;
            }
            AuthCredentialPatternView authCredentialPatternView = AuthCredentialPatternView.this;
            LockscreenCredential createPattern = LockscreenCredential.createPattern(list, authCredentialPatternView.mLockPatternUtils.getLockPatternSize(authCredentialPatternView.mUserId));
            try {
                AuthCredentialPatternView authCredentialPatternView2 = AuthCredentialPatternView.this;
                authCredentialPatternView2.mPendingLockCheck = LockPatternChecker.verifyCredential(authCredentialPatternView2.mLockPatternUtils, createPattern, authCredentialPatternView2.mEffectiveUserId, 1, new LockPatternChecker.OnVerifyCallback() { // from class: com.android.systemui.biometrics.AuthCredentialPatternView$UnlockPatternListener$$ExternalSyntheticLambda0
                    public final void onVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
                        AuthCredentialPatternView.UnlockPatternListener.$r8$lambda$DzEEwr7zVIysKmmyBDaJH_abX3g(AuthCredentialPatternView.UnlockPatternListener.this, verifyCredentialResponse, i);
                    }
                });
                if (createPattern != null) {
                    createPattern.close();
                }
            } catch (Throwable th) {
                if (createPattern != null) {
                    try {
                        createPattern.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }

        public void onPatternStart() {
        }

        public final void onPatternVerified(VerifyCredentialResponse verifyCredentialResponse, int i) {
            AuthCredentialPatternView.this.onCredentialVerified(verifyCredentialResponse, i);
            if (i > 0) {
                AuthCredentialPatternView.this.mLockPatternView.setEnabled(false);
            } else {
                AuthCredentialPatternView.this.mLockPatternView.setEnabled(true);
            }
        }
    }

    public AuthCredentialPatternView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LockPatternView findViewById = findViewById(R$id.lockPattern);
        this.mLockPatternView = findViewById;
        findViewById.setLockPatternSize(this.mLockPatternUtils.getLockPatternSize(this.mUserId));
        this.mLockPatternView.setOnPatternListener(new UnlockPatternListener());
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(this.mUserId));
    }

    @Override // com.android.systemui.biometrics.AuthCredentialView
    public void onErrorTimeoutFinish() {
        super.onErrorTimeoutFinish();
        this.mLockPatternView.setEnabled(true);
    }
}