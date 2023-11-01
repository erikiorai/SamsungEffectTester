package com.android.keyguard;

import android.hardware.biometrics.BiometricSourceType;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger;
import com.android.systemui.CoreStartable;
import com.android.systemui.log.SessionTracker;
import java.io.PrintWriter;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardBiometricLockoutLogger.class */
public final class KeyguardBiometricLockoutLogger implements CoreStartable {
    public static final Companion Companion = new Companion(null);
    public boolean encryptedOrLockdown;
    public boolean faceLockedOut;
    public boolean fingerprintLockedOut;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                boolean isFingerprintLockedOut = KeyguardBiometricLockoutLogger.access$getKeyguardUpdateMonitor$p(KeyguardBiometricLockoutLogger.this).isFingerprintLockedOut();
                if (isFingerprintLockedOut && !KeyguardBiometricLockoutLogger.access$getFingerprintLockedOut$p(KeyguardBiometricLockoutLogger.this)) {
                    KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT);
                } else if (!isFingerprintLockedOut && KeyguardBiometricLockoutLogger.access$getFingerprintLockedOut$p(KeyguardBiometricLockoutLogger.this)) {
                    KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET);
                }
                KeyguardBiometricLockoutLogger.access$setFingerprintLockedOut$p(KeyguardBiometricLockoutLogger.this, isFingerprintLockedOut);
            } else if (biometricSourceType == BiometricSourceType.FACE) {
                boolean isFaceLockedOut = KeyguardBiometricLockoutLogger.access$getKeyguardUpdateMonitor$p(KeyguardBiometricLockoutLogger.this).isFaceLockedOut();
                if (isFaceLockedOut && !KeyguardBiometricLockoutLogger.access$getFaceLockedOut$p(KeyguardBiometricLockoutLogger.this)) {
                    KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT);
                } else if (!isFaceLockedOut && KeyguardBiometricLockoutLogger.access$getFaceLockedOut$p(KeyguardBiometricLockoutLogger.this)) {
                    KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET);
                }
                KeyguardBiometricLockoutLogger.access$setFaceLockedOut$p(KeyguardBiometricLockoutLogger.this, isFaceLockedOut);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStrongAuthStateChanged(int i) {
            if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                return;
            }
            int strongAuthForUser = KeyguardBiometricLockoutLogger.access$getKeyguardUpdateMonitor$p(KeyguardBiometricLockoutLogger.this).getStrongAuthTracker().getStrongAuthForUser(i);
            boolean isEncryptedOrLockdown = KeyguardBiometricLockoutLogger.access$getKeyguardUpdateMonitor$p(KeyguardBiometricLockoutLogger.this).isEncryptedOrLockdown(i);
            if (isEncryptedOrLockdown && !KeyguardBiometricLockoutLogger.access$getEncryptedOrLockdown$p(KeyguardBiometricLockoutLogger.this)) {
                KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN);
            }
            KeyguardBiometricLockoutLogger.access$setEncryptedOrLockdown$p(KeyguardBiometricLockoutLogger.this, isEncryptedOrLockdown);
            boolean access$isUnattendedUpdate = KeyguardBiometricLockoutLogger.access$isUnattendedUpdate(KeyguardBiometricLockoutLogger.this, strongAuthForUser);
            if (access$isUnattendedUpdate && !KeyguardBiometricLockoutLogger.access$getUnattendedUpdate$p(KeyguardBiometricLockoutLogger.this)) {
                KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE);
            }
            KeyguardBiometricLockoutLogger.access$setUnattendedUpdate$p(KeyguardBiometricLockoutLogger.this, access$isUnattendedUpdate);
            boolean access$isStrongAuthTimeout = KeyguardBiometricLockoutLogger.access$isStrongAuthTimeout(KeyguardBiometricLockoutLogger.this, strongAuthForUser);
            if (access$isStrongAuthTimeout && !KeyguardBiometricLockoutLogger.access$getTimeout$p(KeyguardBiometricLockoutLogger.this)) {
                KeyguardBiometricLockoutLogger.access$log(KeyguardBiometricLockoutLogger.this, KeyguardBiometricLockoutLogger.PrimaryAuthRequiredEvent.PRIMARY_AUTH_REQUIRED_TIMEOUT);
            }
            KeyguardBiometricLockoutLogger.access$setTimeout$p(KeyguardBiometricLockoutLogger.this, access$isStrongAuthTimeout);
        }
    };
    public final SessionTracker sessionTracker;
    public boolean timeout;
    public final UiEventLogger uiEventLogger;
    public boolean unattendedUpdate;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardBiometricLockoutLogger$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean containsFlag(int i, int i2) {
            return (i & i2) != 0;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardBiometricLockoutLogger$PrimaryAuthRequiredEvent.class */
    public enum PrimaryAuthRequiredEvent implements UiEventLogger.UiEventEnum {
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT(924),
        PRIMARY_AUTH_REQUIRED_FINGERPRINT_LOCKED_OUT_RESET(925),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT(926),
        PRIMARY_AUTH_REQUIRED_FACE_LOCKED_OUT_RESET(927),
        PRIMARY_AUTH_REQUIRED_ENCRYPTED_OR_LOCKDOWN(928),
        PRIMARY_AUTH_REQUIRED_TIMEOUT(929),
        PRIMARY_AUTH_REQUIRED_UNATTENDED_UPDATE(931);
        
        private final int mId;

        PrimaryAuthRequiredEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    public KeyguardBiometricLockoutLogger(UiEventLogger uiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, SessionTracker sessionTracker) {
        this.uiEventLogger = uiEventLogger;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.sessionTracker = sessionTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ boolean access$getEncryptedOrLockdown$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.encryptedOrLockdown;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void] */
    public static final /* synthetic */ boolean access$getFaceLockedOut$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.faceLockedOut;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void] */
    public static final /* synthetic */ boolean access$getFingerprintLockedOut$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.fingerprintLockedOut;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void, com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ KeyguardUpdateMonitor access$getKeyguardUpdateMonitor$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.keyguardUpdateMonitor;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ boolean access$getTimeout$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.timeout;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ boolean access$getUnattendedUpdate$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger) {
        return keyguardBiometricLockoutLogger.unattendedUpdate;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ boolean access$isStrongAuthTimeout(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, int i) {
        return keyguardBiometricLockoutLogger.isStrongAuthTimeout(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ boolean access$isUnattendedUpdate(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, int i) {
        return keyguardBiometricLockoutLogger.isUnattendedUpdate(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void, com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ void access$log(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, PrimaryAuthRequiredEvent primaryAuthRequiredEvent) {
        keyguardBiometricLockoutLogger.log(primaryAuthRequiredEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ void access$setEncryptedOrLockdown$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, boolean z) {
        keyguardBiometricLockoutLogger.encryptedOrLockdown = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void] */
    public static final /* synthetic */ void access$setFaceLockedOut$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, boolean z) {
        keyguardBiometricLockoutLogger.faceLockedOut = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onLockedOutStateChanged(android.hardware.biometrics.BiometricSourceType):void] */
    public static final /* synthetic */ void access$setFingerprintLockedOut$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, boolean z) {
        keyguardBiometricLockoutLogger.fingerprintLockedOut = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ void access$setTimeout$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, boolean z) {
        keyguardBiometricLockoutLogger.timeout = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardBiometricLockoutLogger$mKeyguardUpdateMonitorCallback$1.onStrongAuthStateChanged(int):void] */
    public static final /* synthetic */ void access$setUnattendedUpdate$p(KeyguardBiometricLockoutLogger keyguardBiometricLockoutLogger, boolean z) {
        keyguardBiometricLockoutLogger.unattendedUpdate = z;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        boolean z = this.fingerprintLockedOut;
        printWriter.println("  mFingerprintLockedOut=" + z);
        boolean z2 = this.faceLockedOut;
        printWriter.println("  mFaceLockedOut=" + z2);
        boolean z3 = this.encryptedOrLockdown;
        printWriter.println("  mIsEncryptedOrLockdown=" + z3);
        boolean z4 = this.unattendedUpdate;
        printWriter.println("  mIsUnattendedUpdate=" + z4);
        boolean z5 = this.timeout;
        printWriter.println("  mIsTimeout=" + z5);
    }

    public final boolean isStrongAuthTimeout(int i) {
        Companion companion = Companion;
        return companion.containsFlag(i, 16) || companion.containsFlag(i, RecyclerView.ViewHolder.FLAG_IGNORE);
    }

    public final boolean isUnattendedUpdate(int i) {
        return Companion.containsFlag(i, 64);
    }

    public final void log(PrimaryAuthRequiredEvent primaryAuthRequiredEvent) {
        this.uiEventLogger.log(primaryAuthRequiredEvent, this.sessionTracker.getSessionId(1));
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mKeyguardUpdateMonitorCallback.onStrongAuthStateChanged(KeyguardUpdateMonitor.getCurrentUser());
        this.keyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
    }
}