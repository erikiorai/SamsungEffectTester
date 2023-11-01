package com.android.keyguard;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.SecureSettings;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.SetsKt__SetsJVMKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__StringsKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/ActiveUnlockConfig.class */
public final class ActiveUnlockConfig implements Dumpable {
    public static final Companion Companion = new Companion(null);
    public final ContentResolver contentResolver;
    public final Handler handler;
    public KeyguardUpdateMonitor keyguardUpdateMonitor;
    public boolean requestActiveUnlockOnBioFail;
    public boolean requestActiveUnlockOnUnlockIntent;
    public boolean requestActiveUnlockOnWakeup;
    public final SecureSettings secureSettings;
    public final ActiveUnlockConfig$settingsObserver$1 settingsObserver;
    public Set<Integer> faceErrorsToTriggerBiometricFailOn = SetsKt__SetsKt.mutableSetOf(new Integer[]{3});
    public Set<Integer> faceAcquireInfoToTriggerBiometricFailOn = new LinkedHashSet();
    public Set<Integer> onUnlockIntentWhenBiometricEnrolled = SetsKt__SetsKt.mutableSetOf(new Integer[]{0});

    /* loaded from: mainsysui33.jar:com/android/keyguard/ActiveUnlockConfig$ACTIVE_UNLOCK_REQUEST_ORIGIN.class */
    public enum ACTIVE_UNLOCK_REQUEST_ORIGIN {
        WAKE,
        UNLOCK_INTENT,
        BIOMETRIC_FAIL,
        ASSISTANT
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/ActiveUnlockConfig$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/ActiveUnlockConfig$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ACTIVE_UNLOCK_REQUEST_ORIGIN.values().length];
            try {
                iArr[ACTIVE_UNLOCK_REQUEST_ORIGIN.WAKE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ACTIVE_UNLOCK_REQUEST_ORIGIN.UNLOCK_INTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ACTIVE_UNLOCK_REQUEST_ORIGIN.BIOMETRIC_FAIL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[ACTIVE_UNLOCK_REQUEST_ORIGIN.ASSISTANT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.keyguard.ActiveUnlockConfig$settingsObserver$1] */
    public ActiveUnlockConfig(final Handler handler, SecureSettings secureSettings, ContentResolver contentResolver, DumpManager dumpManager) {
        this.handler = handler;
        this.secureSettings = secureSettings;
        this.contentResolver = contentResolver;
        ?? r0 = new ContentObserver(handler) { // from class: com.android.keyguard.ActiveUnlockConfig$settingsObserver$1
            public final Uri bioFailUri;
            public final Uri faceAcquireInfoUri;
            public final Uri faceErrorsUri;
            public final Uri unlockIntentUri;
            public final Uri unlockIntentWhenBiometricEnrolledUri;
            public final Uri wakeUri;

            {
                SecureSettings secureSettings2;
                SecureSettings secureSettings3;
                SecureSettings secureSettings4;
                SecureSettings secureSettings5;
                SecureSettings secureSettings6;
                SecureSettings secureSettings7;
                secureSettings2 = ActiveUnlockConfig.this.secureSettings;
                this.wakeUri = secureSettings2.getUriFor("active_unlock_on_wake");
                secureSettings3 = ActiveUnlockConfig.this.secureSettings;
                this.unlockIntentUri = secureSettings3.getUriFor("active_unlock_on_unlock_intent");
                secureSettings4 = ActiveUnlockConfig.this.secureSettings;
                this.bioFailUri = secureSettings4.getUriFor("active_unlock_on_biometric_fail");
                secureSettings5 = ActiveUnlockConfig.this.secureSettings;
                this.faceErrorsUri = secureSettings5.getUriFor("active_unlock_on_face_errors");
                secureSettings6 = ActiveUnlockConfig.this.secureSettings;
                this.faceAcquireInfoUri = secureSettings6.getUriFor("active_unlock_on_face_acquire_info");
                secureSettings7 = ActiveUnlockConfig.this.secureSettings;
                this.unlockIntentWhenBiometricEnrolledUri = secureSettings7.getUriFor("active_unlock_on_unlock_intent_when_biometric_enrolled");
            }

            public void onChange(boolean z, Collection<? extends Uri> collection, int i, int i2) {
                SecureSettings secureSettings2;
                SecureSettings secureSettings3;
                SecureSettings secureSettings4;
                SecureSettings secureSettings5;
                Set<Integer> set;
                SecureSettings secureSettings6;
                Set<Integer> set2;
                SecureSettings secureSettings7;
                Set<Integer> set3;
                if (KeyguardUpdateMonitor.getCurrentUser() != i2) {
                    return;
                }
                if (z || collection.contains(this.wakeUri)) {
                    ActiveUnlockConfig activeUnlockConfig = ActiveUnlockConfig.this;
                    secureSettings2 = activeUnlockConfig.secureSettings;
                    activeUnlockConfig.requestActiveUnlockOnWakeup = secureSettings2.getIntForUser("active_unlock_on_wake", 0, KeyguardUpdateMonitor.getCurrentUser()) == 1;
                }
                if (z || collection.contains(this.unlockIntentUri)) {
                    ActiveUnlockConfig activeUnlockConfig2 = ActiveUnlockConfig.this;
                    secureSettings3 = activeUnlockConfig2.secureSettings;
                    activeUnlockConfig2.requestActiveUnlockOnUnlockIntent = secureSettings3.getIntForUser("active_unlock_on_unlock_intent", 0, KeyguardUpdateMonitor.getCurrentUser()) == 1;
                }
                if (z || collection.contains(this.bioFailUri)) {
                    ActiveUnlockConfig activeUnlockConfig3 = ActiveUnlockConfig.this;
                    secureSettings4 = activeUnlockConfig3.secureSettings;
                    activeUnlockConfig3.requestActiveUnlockOnBioFail = secureSettings4.getIntForUser("active_unlock_on_biometric_fail", 0, KeyguardUpdateMonitor.getCurrentUser()) == 1;
                }
                if (z || collection.contains(this.faceErrorsUri)) {
                    secureSettings5 = ActiveUnlockConfig.this.secureSettings;
                    String stringForUser = secureSettings5.getStringForUser("active_unlock_on_face_errors", KeyguardUpdateMonitor.getCurrentUser());
                    set = ActiveUnlockConfig.this.faceErrorsToTriggerBiometricFailOn;
                    processStringArray(stringForUser, set, SetsKt__SetsJVMKt.setOf(3));
                }
                if (z || collection.contains(this.faceAcquireInfoUri)) {
                    secureSettings6 = ActiveUnlockConfig.this.secureSettings;
                    String stringForUser2 = secureSettings6.getStringForUser("active_unlock_on_face_acquire_info", KeyguardUpdateMonitor.getCurrentUser());
                    set2 = ActiveUnlockConfig.this.faceAcquireInfoToTriggerBiometricFailOn;
                    processStringArray(stringForUser2, set2, SetsKt__SetsKt.emptySet());
                }
                if (z || collection.contains(this.unlockIntentWhenBiometricEnrolledUri)) {
                    secureSettings7 = ActiveUnlockConfig.this.secureSettings;
                    String stringForUser3 = secureSettings7.getStringForUser("active_unlock_on_unlock_intent_when_biometric_enrolled", KeyguardUpdateMonitor.getCurrentUser());
                    set3 = ActiveUnlockConfig.this.onUnlockIntentWhenBiometricEnrolled;
                    processStringArray(stringForUser3, set3, SetsKt__SetsJVMKt.setOf(0));
                }
            }

            public final void processStringArray(String str, Set<Integer> set, Set<Integer> set2) {
                set.clear();
                if (str == null) {
                    set.addAll(set2);
                    return;
                }
                for (String str2 : StringsKt__StringsKt.split$default(str, new String[]{"|"}, false, 0, 6, (Object) null)) {
                    try {
                        set.add(Integer.valueOf(Integer.parseInt(str2)));
                    } catch (NumberFormatException e) {
                        Log.e("ActiveUnlockConfig", "Passed an invalid setting=" + str2);
                    }
                }
            }

            public final void register() {
                registerUri(CollectionsKt__CollectionsKt.listOf(new Uri[]{this.wakeUri, this.unlockIntentUri, this.bioFailUri, this.faceErrorsUri, this.faceAcquireInfoUri, this.unlockIntentWhenBiometricEnrolledUri}));
                onChange(true, new ArrayList(), 0, KeyguardUpdateMonitor.getCurrentUser());
            }

            public final void registerUri(Collection<? extends Uri> collection) {
                ContentResolver contentResolver2;
                for (Uri uri : collection) {
                    contentResolver2 = ActiveUnlockConfig.this.contentResolver;
                    contentResolver2.registerContentObserver(uri, false, this, -1);
                }
            }
        };
        this.settingsObserver = r0;
        r0.register();
        dumpManager.registerDumpable(this);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        Unit unit;
        printWriter.println("Settings:");
        printWriter.println("   requestActiveUnlockOnWakeup=" + this.requestActiveUnlockOnWakeup);
        printWriter.println("   requestActiveUnlockOnUnlockIntent=" + this.requestActiveUnlockOnUnlockIntent);
        printWriter.println("   requestActiveUnlockOnBioFail=" + this.requestActiveUnlockOnBioFail);
        printWriter.println("   requestActiveUnlockOnUnlockIntentWhenBiometricEnrolled=" + this.onUnlockIntentWhenBiometricEnrolled);
        printWriter.println("   requestActiveUnlockOnFaceError=" + this.faceErrorsToTriggerBiometricFailOn);
        printWriter.println("   requestActiveUnlockOnFaceAcquireInfo=" + this.faceAcquireInfoToTriggerBiometricFailOn);
        printWriter.println("Current state:");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.keyguardUpdateMonitor;
        if (keyguardUpdateMonitor != null) {
            printWriter.println("   shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment=" + shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment());
            printWriter.println("   faceEnrolled=" + keyguardUpdateMonitor.isFaceEnrolled());
            printWriter.println("   fpEnrolled=" + keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(KeyguardUpdateMonitor.getCurrentUser()));
            printWriter.println("   udfpsEnrolled=" + keyguardUpdateMonitor.isUdfpsEnrolled());
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            printWriter.println("   keyguardUpdateMonitor is uninitialized");
        }
    }

    public final boolean isActiveUnlockEnabled() {
        return this.requestActiveUnlockOnWakeup || this.requestActiveUnlockOnUnlockIntent || this.requestActiveUnlockOnBioFail;
    }

    public final void setKeyguardUpdateMonitor(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0041, code lost:
        if (r3.requestActiveUnlockOnWakeup == false) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0059, code lost:
        if (shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() == false) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldAllowActiveUnlockFromOrigin(ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin) {
        int i = WhenMappings.$EnumSwitchMapping$0[active_unlock_request_origin.ordinal()];
        boolean z = false;
        if (i == 1) {
            z = this.requestActiveUnlockOnWakeup;
        } else if (i == 2) {
            if (!this.requestActiveUnlockOnUnlockIntent) {
                if (!this.requestActiveUnlockOnWakeup) {
                }
            }
            z = true;
        } else if (i == 3) {
            if (!this.requestActiveUnlockOnBioFail) {
                if (!this.requestActiveUnlockOnUnlockIntent) {
                }
            }
            z = true;
        } else if (i != 4) {
            throw new NoWhenBranchMatchedException();
        } else {
            z = isActiveUnlockEnabled();
        }
        return z;
    }

    public final boolean shouldRequestActiveUnlockOnFaceAcquireInfo(int i) {
        return this.faceAcquireInfoToTriggerBiometricFailOn.contains(Integer.valueOf(i));
    }

    public final boolean shouldRequestActiveUnlockOnFaceError(int i) {
        return this.faceErrorsToTriggerBiometricFailOn.contains(Integer.valueOf(i));
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0070, code lost:
        if (r3.onUnlockIntentWhenBiometricEnrolled.contains(3) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() {
        KeyguardUpdateMonitor keyguardUpdateMonitor;
        boolean z;
        if (this.requestActiveUnlockOnBioFail && (keyguardUpdateMonitor = this.keyguardUpdateMonitor) != null) {
            boolean isFaceEnrolled = keyguardUpdateMonitor.isFaceEnrolled();
            boolean cachedIsUnlockWithFingerprintPossible = keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(KeyguardUpdateMonitor.getCurrentUser());
            boolean isUdfpsEnrolled = keyguardUpdateMonitor.isUdfpsEnrolled();
            if (isFaceEnrolled || cachedIsUnlockWithFingerprintPossible) {
                if (isFaceEnrolled || !cachedIsUnlockWithFingerprintPossible) {
                    if (cachedIsUnlockWithFingerprintPossible || !isFaceEnrolled) {
                        return false;
                    }
                    return this.onUnlockIntentWhenBiometricEnrolled.contains(1);
                }
                if (!this.onUnlockIntentWhenBiometricEnrolled.contains(2)) {
                    z = false;
                    if (isUdfpsEnrolled) {
                        z = false;
                    }
                    return z;
                }
                z = true;
                return z;
            }
            return this.onUnlockIntentWhenBiometricEnrolled.contains(0);
        }
        return false;
    }
}