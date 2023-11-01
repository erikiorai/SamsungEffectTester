package com.android.keyguard;

import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.plugins.util.RingBuffer;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFaceListenModel.class */
public final class KeyguardFaceListenModel extends KeyguardListenModel {
    public static final Companion Companion = new Companion(null);
    public static final List<String> TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(new String[]{"timestamp", "time_millis", "userId", "listening", "authInterruptActive", "biometricSettingEnabledForUser", "bouncerFullyShown", "faceAndFpNotAuthenticated", "faceAuthAllowed", "faceDisabled", "faceLockedOut", "goingToSleep", "keyguardAwake", "keyguardGoingAway", "listeningForFaceAssistant", "occludingAppRequestingFaceAuth", "primaryUser", "secureCameraLaunched", "supportsDetect", "switchingUser", "udfpsBouncerShowing", "udfpsFingerDown", "userNotTrustedOrDetectionIsNeeded"});
    public final Lazy asStringList$delegate;
    public boolean authInterruptActive;
    public boolean biometricSettingEnabledForUser;
    public boolean bouncerFullyShown;
    public boolean faceAndFpNotAuthenticated;
    public boolean faceAuthAllowed;
    public boolean faceDisabled;
    public boolean faceLockedOut;
    public boolean goingToSleep;
    public boolean keyguardAwake;
    public boolean keyguardGoingAway;
    public boolean listening;
    public boolean listeningForFaceAssistant;
    public boolean occludingAppRequestingFaceAuth;
    public boolean primaryUser;
    public boolean secureCameraLaunched;
    public boolean supportsDetect;
    public boolean switchingUser;
    public long timeMillis;
    public boolean udfpsBouncerShowing;
    public boolean udfpsFingerDown;
    public int userId;
    public boolean userNotTrustedOrDetectionIsNeeded;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFaceListenModel$Buffer.class */
    public static final class Buffer {
        public final RingBuffer<KeyguardFaceListenModel> buffer = new RingBuffer<>(40, new Function0<KeyguardFaceListenModel>() { // from class: com.android.keyguard.KeyguardFaceListenModel$Buffer$buffer$1
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final KeyguardFaceListenModel m587invoke() {
                return new KeyguardFaceListenModel(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 4194303, null);
            }
        });

        public final void insert(KeyguardFaceListenModel keyguardFaceListenModel) {
            KeyguardFaceListenModel advance = this.buffer.advance();
            advance.setTimeMillis(keyguardFaceListenModel.getTimeMillis());
            advance.setUserId(keyguardFaceListenModel.getUserId());
            advance.setListening(keyguardFaceListenModel.getListening());
            advance.setBiometricSettingEnabledForUser(keyguardFaceListenModel.getBiometricSettingEnabledForUser());
            advance.setBouncerFullyShown(keyguardFaceListenModel.getBouncerFullyShown());
            advance.setFaceAndFpNotAuthenticated(keyguardFaceListenModel.getFaceAndFpNotAuthenticated());
            advance.setFaceAuthAllowed(keyguardFaceListenModel.getFaceAuthAllowed());
            advance.setFaceDisabled(keyguardFaceListenModel.getFaceDisabled());
            advance.setFaceLockedOut(keyguardFaceListenModel.getFaceLockedOut());
            advance.setGoingToSleep(keyguardFaceListenModel.getGoingToSleep());
            advance.setKeyguardAwake(keyguardFaceListenModel.getKeyguardAwake());
            advance.setGoingToSleep(keyguardFaceListenModel.getGoingToSleep());
            advance.setKeyguardGoingAway(keyguardFaceListenModel.getKeyguardGoingAway());
            advance.setListeningForFaceAssistant(keyguardFaceListenModel.getListeningForFaceAssistant());
            advance.setOccludingAppRequestingFaceAuth(keyguardFaceListenModel.getOccludingAppRequestingFaceAuth());
            advance.setPrimaryUser(keyguardFaceListenModel.getPrimaryUser());
            advance.setSecureCameraLaunched(keyguardFaceListenModel.getSecureCameraLaunched());
            advance.setSupportsDetect(keyguardFaceListenModel.getSupportsDetect());
            advance.setSwitchingUser(keyguardFaceListenModel.getSwitchingUser());
            advance.setUdfpsBouncerShowing(keyguardFaceListenModel.getUdfpsBouncerShowing());
            advance.setSwitchingUser(keyguardFaceListenModel.getSwitchingUser());
            advance.setUdfpsFingerDown(keyguardFaceListenModel.getUdfpsFingerDown());
            advance.setUserNotTrustedOrDetectionIsNeeded(keyguardFaceListenModel.getUserNotTrustedOrDetectionIsNeeded());
        }

        public final List<List<String>> toList() {
            return SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(this.buffer), new Function1<KeyguardFaceListenModel, List<? extends String>>() { // from class: com.android.keyguard.KeyguardFaceListenModel$Buffer$toList$1
                /* JADX DEBUG: Method merged with bridge method */
                public final List<String> invoke(KeyguardFaceListenModel keyguardFaceListenModel) {
                    return keyguardFaceListenModel.getAsStringList();
                }
            }));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFaceListenModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardFaceListenModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 4194303, null);
    }

    public KeyguardFaceListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.authInterruptActive = z2;
        this.biometricSettingEnabledForUser = z3;
        this.bouncerFullyShown = z4;
        this.faceAndFpNotAuthenticated = z5;
        this.faceAuthAllowed = z6;
        this.faceDisabled = z7;
        this.faceLockedOut = z8;
        this.goingToSleep = z9;
        this.keyguardAwake = z10;
        this.keyguardGoingAway = z11;
        this.listeningForFaceAssistant = z12;
        this.occludingAppRequestingFaceAuth = z13;
        this.primaryUser = z14;
        this.secureCameraLaunched = z15;
        this.supportsDetect = z16;
        this.switchingUser = z17;
        this.udfpsBouncerShowing = z18;
        this.udfpsFingerDown = z19;
        this.userNotTrustedOrDetectionIsNeeded = z20;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends String>>() { // from class: com.android.keyguard.KeyguardFaceListenModel$asStringList$2
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final List<String> invoke() {
                return CollectionsKt__CollectionsKt.listOf(new String[]{KeyguardListenModelKt.getDATE_FORMAT().format(Long.valueOf(KeyguardFaceListenModel.this.getTimeMillis())), String.valueOf(KeyguardFaceListenModel.this.getTimeMillis()), String.valueOf(KeyguardFaceListenModel.this.getUserId()), String.valueOf(KeyguardFaceListenModel.this.getListening()), String.valueOf(KeyguardFaceListenModel.this.getAuthInterruptActive()), String.valueOf(KeyguardFaceListenModel.this.getBiometricSettingEnabledForUser()), String.valueOf(KeyguardFaceListenModel.this.getBouncerFullyShown()), String.valueOf(KeyguardFaceListenModel.this.getFaceAndFpNotAuthenticated()), String.valueOf(KeyguardFaceListenModel.this.getFaceAuthAllowed()), String.valueOf(KeyguardFaceListenModel.this.getFaceDisabled()), String.valueOf(KeyguardFaceListenModel.this.getFaceLockedOut()), String.valueOf(KeyguardFaceListenModel.this.getGoingToSleep()), String.valueOf(KeyguardFaceListenModel.this.getKeyguardAwake()), String.valueOf(KeyguardFaceListenModel.this.getKeyguardGoingAway()), String.valueOf(KeyguardFaceListenModel.this.getListeningForFaceAssistant()), String.valueOf(KeyguardFaceListenModel.this.getOccludingAppRequestingFaceAuth()), String.valueOf(KeyguardFaceListenModel.this.getPrimaryUser()), String.valueOf(KeyguardFaceListenModel.this.getSecureCameraLaunched()), String.valueOf(KeyguardFaceListenModel.this.getSupportsDetect()), String.valueOf(KeyguardFaceListenModel.this.getSwitchingUser()), String.valueOf(KeyguardFaceListenModel.this.getUdfpsBouncerShowing()), String.valueOf(KeyguardFaceListenModel.this.getUdfpsFingerDown()), String.valueOf(KeyguardFaceListenModel.this.getUserNotTrustedOrDetectionIsNeeded())});
            }
        });
    }

    public /* synthetic */ KeyguardFaceListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 ? false : z6, (i2 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0 ? false : z7, (i2 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0 ? false : z8, (i2 & RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE) != 0 ? false : z9, (i2 & RecyclerView.ViewHolder.FLAG_MOVED) != 0 ? false : z10, (i2 & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0 ? false : z11, (i2 & RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST) != 0 ? false : z12, (i2 & 16384) != 0 ? false : z13, (i2 & 32768) != 0 ? false : z14, (i2 & 65536) != 0 ? false : z15, (i2 & 131072) != 0 ? false : z16, (i2 & 262144) != 0 ? false : z17, (i2 & 524288) != 0 ? false : z18, (i2 & 1048576) != 0 ? false : z19, (i2 & 2097152) != 0 ? false : z20);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardFaceListenModel) {
            KeyguardFaceListenModel keyguardFaceListenModel = (KeyguardFaceListenModel) obj;
            return getTimeMillis() == keyguardFaceListenModel.getTimeMillis() && getUserId() == keyguardFaceListenModel.getUserId() && getListening() == keyguardFaceListenModel.getListening() && this.authInterruptActive == keyguardFaceListenModel.authInterruptActive && this.biometricSettingEnabledForUser == keyguardFaceListenModel.biometricSettingEnabledForUser && this.bouncerFullyShown == keyguardFaceListenModel.bouncerFullyShown && this.faceAndFpNotAuthenticated == keyguardFaceListenModel.faceAndFpNotAuthenticated && this.faceAuthAllowed == keyguardFaceListenModel.faceAuthAllowed && this.faceDisabled == keyguardFaceListenModel.faceDisabled && this.faceLockedOut == keyguardFaceListenModel.faceLockedOut && this.goingToSleep == keyguardFaceListenModel.goingToSleep && this.keyguardAwake == keyguardFaceListenModel.keyguardAwake && this.keyguardGoingAway == keyguardFaceListenModel.keyguardGoingAway && this.listeningForFaceAssistant == keyguardFaceListenModel.listeningForFaceAssistant && this.occludingAppRequestingFaceAuth == keyguardFaceListenModel.occludingAppRequestingFaceAuth && this.primaryUser == keyguardFaceListenModel.primaryUser && this.secureCameraLaunched == keyguardFaceListenModel.secureCameraLaunched && this.supportsDetect == keyguardFaceListenModel.supportsDetect && this.switchingUser == keyguardFaceListenModel.switchingUser && this.udfpsBouncerShowing == keyguardFaceListenModel.udfpsBouncerShowing && this.udfpsFingerDown == keyguardFaceListenModel.udfpsFingerDown && this.userNotTrustedOrDetectionIsNeeded == keyguardFaceListenModel.userNotTrustedOrDetectionIsNeeded;
        }
        return false;
    }

    public final List<String> getAsStringList() {
        return (List) this.asStringList$delegate.getValue();
    }

    public final boolean getAuthInterruptActive() {
        return this.authInterruptActive;
    }

    public final boolean getBiometricSettingEnabledForUser() {
        return this.biometricSettingEnabledForUser;
    }

    public final boolean getBouncerFullyShown() {
        return this.bouncerFullyShown;
    }

    public final boolean getFaceAndFpNotAuthenticated() {
        return this.faceAndFpNotAuthenticated;
    }

    public final boolean getFaceAuthAllowed() {
        return this.faceAuthAllowed;
    }

    public final boolean getFaceDisabled() {
        return this.faceDisabled;
    }

    public final boolean getFaceLockedOut() {
        return this.faceLockedOut;
    }

    public final boolean getGoingToSleep() {
        return this.goingToSleep;
    }

    public final boolean getKeyguardAwake() {
        return this.keyguardAwake;
    }

    public final boolean getKeyguardGoingAway() {
        return this.keyguardGoingAway;
    }

    public boolean getListening() {
        return this.listening;
    }

    public final boolean getListeningForFaceAssistant() {
        return this.listeningForFaceAssistant;
    }

    public final boolean getOccludingAppRequestingFaceAuth() {
        return this.occludingAppRequestingFaceAuth;
    }

    public final boolean getPrimaryUser() {
        return this.primaryUser;
    }

    public final boolean getSecureCameraLaunched() {
        return this.secureCameraLaunched;
    }

    public final boolean getSupportsDetect() {
        return this.supportsDetect;
    }

    public final boolean getSwitchingUser() {
        return this.switchingUser;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    public final boolean getUdfpsBouncerShowing() {
        return this.udfpsBouncerShowing;
    }

    public final boolean getUdfpsFingerDown() {
        return this.udfpsFingerDown;
    }

    public int getUserId() {
        return this.userId;
    }

    public final boolean getUserNotTrustedOrDetectionIsNeeded() {
        return this.userNotTrustedOrDetectionIsNeeded;
    }

    public int hashCode() {
        int hashCode = Long.hashCode(getTimeMillis());
        int hashCode2 = Integer.hashCode(getUserId());
        boolean listening = getListening();
        int i = 1;
        boolean z = listening;
        if (listening) {
            z = true;
        }
        boolean z2 = this.authInterruptActive;
        int i2 = z2 ? 1 : 0;
        if (z2) {
            i2 = 1;
        }
        boolean z3 = this.biometricSettingEnabledForUser;
        int i3 = z3 ? 1 : 0;
        if (z3) {
            i3 = 1;
        }
        boolean z4 = this.bouncerFullyShown;
        int i4 = z4 ? 1 : 0;
        if (z4) {
            i4 = 1;
        }
        boolean z5 = this.faceAndFpNotAuthenticated;
        int i5 = z5 ? 1 : 0;
        if (z5) {
            i5 = 1;
        }
        boolean z6 = this.faceAuthAllowed;
        int i6 = z6 ? 1 : 0;
        if (z6) {
            i6 = 1;
        }
        boolean z7 = this.faceDisabled;
        int i7 = z7 ? 1 : 0;
        if (z7) {
            i7 = 1;
        }
        boolean z8 = this.faceLockedOut;
        int i8 = z8 ? 1 : 0;
        if (z8) {
            i8 = 1;
        }
        boolean z9 = this.goingToSleep;
        int i9 = z9 ? 1 : 0;
        if (z9) {
            i9 = 1;
        }
        boolean z10 = this.keyguardAwake;
        int i10 = z10 ? 1 : 0;
        if (z10) {
            i10 = 1;
        }
        boolean z11 = this.keyguardGoingAway;
        int i11 = z11 ? 1 : 0;
        if (z11) {
            i11 = 1;
        }
        boolean z12 = this.listeningForFaceAssistant;
        int i12 = z12 ? 1 : 0;
        if (z12) {
            i12 = 1;
        }
        boolean z13 = this.occludingAppRequestingFaceAuth;
        int i13 = z13 ? 1 : 0;
        if (z13) {
            i13 = 1;
        }
        boolean z14 = this.primaryUser;
        int i14 = z14 ? 1 : 0;
        if (z14) {
            i14 = 1;
        }
        boolean z15 = this.secureCameraLaunched;
        int i15 = z15 ? 1 : 0;
        if (z15) {
            i15 = 1;
        }
        boolean z16 = this.supportsDetect;
        int i16 = z16 ? 1 : 0;
        if (z16) {
            i16 = 1;
        }
        boolean z17 = this.switchingUser;
        int i17 = z17 ? 1 : 0;
        if (z17) {
            i17 = 1;
        }
        boolean z18 = this.udfpsBouncerShowing;
        int i18 = z18 ? 1 : 0;
        if (z18) {
            i18 = 1;
        }
        boolean z19 = this.udfpsFingerDown;
        int i19 = z19 ? 1 : 0;
        if (z19) {
            i19 = 1;
        }
        boolean z20 = this.userNotTrustedOrDetectionIsNeeded;
        if (!z20) {
            i = z20 ? 1 : 0;
        }
        return (((((((((((((((((((((((((((((((((((((((((hashCode * 31) + hashCode2) * 31) + (z ? 1 : 0)) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6) * 31) + i7) * 31) + i8) * 31) + i9) * 31) + i10) * 31) + i11) * 31) + i12) * 31) + i13) * 31) + i14) * 31) + i15) * 31) + i16) * 31) + i17) * 31) + i18) * 31) + i19) * 31) + i;
    }

    public final void setBiometricSettingEnabledForUser(boolean z) {
        this.biometricSettingEnabledForUser = z;
    }

    public final void setBouncerFullyShown(boolean z) {
        this.bouncerFullyShown = z;
    }

    public final void setFaceAndFpNotAuthenticated(boolean z) {
        this.faceAndFpNotAuthenticated = z;
    }

    public final void setFaceAuthAllowed(boolean z) {
        this.faceAuthAllowed = z;
    }

    public final void setFaceDisabled(boolean z) {
        this.faceDisabled = z;
    }

    public final void setFaceLockedOut(boolean z) {
        this.faceLockedOut = z;
    }

    public final void setGoingToSleep(boolean z) {
        this.goingToSleep = z;
    }

    public final void setKeyguardAwake(boolean z) {
        this.keyguardAwake = z;
    }

    public final void setKeyguardGoingAway(boolean z) {
        this.keyguardGoingAway = z;
    }

    public void setListening(boolean z) {
        this.listening = z;
    }

    public final void setListeningForFaceAssistant(boolean z) {
        this.listeningForFaceAssistant = z;
    }

    public final void setOccludingAppRequestingFaceAuth(boolean z) {
        this.occludingAppRequestingFaceAuth = z;
    }

    public final void setPrimaryUser(boolean z) {
        this.primaryUser = z;
    }

    public final void setSecureCameraLaunched(boolean z) {
        this.secureCameraLaunched = z;
    }

    public final void setSupportsDetect(boolean z) {
        this.supportsDetect = z;
    }

    public final void setSwitchingUser(boolean z) {
        this.switchingUser = z;
    }

    public void setTimeMillis(long j) {
        this.timeMillis = j;
    }

    public final void setUdfpsBouncerShowing(boolean z) {
        this.udfpsBouncerShowing = z;
    }

    public final void setUdfpsFingerDown(boolean z) {
        this.udfpsFingerDown = z;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public final void setUserNotTrustedOrDetectionIsNeeded(boolean z) {
        this.userNotTrustedOrDetectionIsNeeded = z;
    }

    public String toString() {
        long timeMillis = getTimeMillis();
        int userId = getUserId();
        boolean listening = getListening();
        boolean z = this.authInterruptActive;
        boolean z2 = this.biometricSettingEnabledForUser;
        boolean z3 = this.bouncerFullyShown;
        boolean z4 = this.faceAndFpNotAuthenticated;
        boolean z5 = this.faceAuthAllowed;
        boolean z6 = this.faceDisabled;
        boolean z7 = this.faceLockedOut;
        boolean z8 = this.goingToSleep;
        boolean z9 = this.keyguardAwake;
        boolean z10 = this.keyguardGoingAway;
        boolean z11 = this.listeningForFaceAssistant;
        boolean z12 = this.occludingAppRequestingFaceAuth;
        boolean z13 = this.primaryUser;
        boolean z14 = this.secureCameraLaunched;
        boolean z15 = this.supportsDetect;
        boolean z16 = this.switchingUser;
        boolean z17 = this.udfpsBouncerShowing;
        boolean z18 = this.udfpsFingerDown;
        boolean z19 = this.userNotTrustedOrDetectionIsNeeded;
        return "KeyguardFaceListenModel(timeMillis=" + timeMillis + ", userId=" + userId + ", listening=" + listening + ", authInterruptActive=" + z + ", biometricSettingEnabledForUser=" + z2 + ", bouncerFullyShown=" + z3 + ", faceAndFpNotAuthenticated=" + z4 + ", faceAuthAllowed=" + z5 + ", faceDisabled=" + z6 + ", faceLockedOut=" + z7 + ", goingToSleep=" + z8 + ", keyguardAwake=" + z9 + ", keyguardGoingAway=" + z10 + ", listeningForFaceAssistant=" + z11 + ", occludingAppRequestingFaceAuth=" + z12 + ", primaryUser=" + z13 + ", secureCameraLaunched=" + z14 + ", supportsDetect=" + z15 + ", switchingUser=" + z16 + ", udfpsBouncerShowing=" + z17 + ", udfpsFingerDown=" + z18 + ", userNotTrustedOrDetectionIsNeeded=" + z19 + ")";
    }
}