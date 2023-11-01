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

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFingerprintListenModel.class */
public final class KeyguardFingerprintListenModel extends KeyguardListenModel {
    public static final Companion Companion = new Companion(null);
    public static final List<String> TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(new String[]{"timestamp", "time_millis", "userId", "listening", "biometricAllowedForUser", "bouncerIsOrWillShow", "canSkipBouncer", "credentialAttempted", "deviceInteractive", "dreaming", "fingerprintDisabled", "fingerprintLockedOut", "goingToSleep", "keyguardGoingAway", "keyguardIsVisible", "keyguardOccluded", "occludingAppRequestingFp", "primaryUser", "shouldListenSidFingerprintState", "shouldListenForFingerprintAssistant", "strongAuthRequired", "switchingUser", "underDisplayFingerprint", "userDoesNotHaveTrust"});
    public final Lazy asStringList$delegate;
    public boolean biometricEnabledForUser;
    public boolean bouncerIsOrWillShow;
    public boolean canSkipBouncer;
    public boolean credentialAttempted;
    public boolean deviceInteractive;
    public boolean dreaming;
    public boolean fingerprintDisabled;
    public boolean fingerprintLockedOut;
    public boolean goingToSleep;
    public boolean keyguardGoingAway;
    public boolean keyguardIsVisible;
    public boolean keyguardOccluded;
    public boolean listening;
    public boolean occludingAppRequestingFp;
    public boolean primaryUser;
    public boolean shouldListenForFingerprintAssistant;
    public boolean shouldListenSfpsState;
    public boolean strongerAuthRequired;
    public boolean switchingUser;
    public long timeMillis;
    public boolean udfps;
    public boolean userDoesNotHaveTrust;
    public int userId;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFingerprintListenModel$Buffer.class */
    public static final class Buffer {
        public final RingBuffer<KeyguardFingerprintListenModel> buffer = new RingBuffer<>(20, new Function0<KeyguardFingerprintListenModel>() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$Buffer$buffer$1
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final KeyguardFingerprintListenModel m592invoke() {
                return new KeyguardFingerprintListenModel(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 8388607, null);
            }
        });

        public final void insert(KeyguardFingerprintListenModel keyguardFingerprintListenModel) {
            KeyguardFingerprintListenModel advance = this.buffer.advance();
            advance.setTimeMillis(keyguardFingerprintListenModel.getTimeMillis());
            advance.setUserId(keyguardFingerprintListenModel.getUserId());
            advance.setListening(keyguardFingerprintListenModel.getListening());
            advance.setBiometricEnabledForUser(keyguardFingerprintListenModel.getBiometricEnabledForUser());
            advance.setBouncerIsOrWillShow(keyguardFingerprintListenModel.getBouncerIsOrWillShow());
            advance.setCanSkipBouncer(keyguardFingerprintListenModel.getCanSkipBouncer());
            advance.setCredentialAttempted(keyguardFingerprintListenModel.getCredentialAttempted());
            advance.setDeviceInteractive(keyguardFingerprintListenModel.getDeviceInteractive());
            advance.setDreaming(keyguardFingerprintListenModel.getDreaming());
            advance.setFingerprintDisabled(keyguardFingerprintListenModel.getFingerprintDisabled());
            advance.setFingerprintLockedOut(keyguardFingerprintListenModel.getFingerprintLockedOut());
            advance.setGoingToSleep(keyguardFingerprintListenModel.getGoingToSleep());
            advance.setKeyguardGoingAway(keyguardFingerprintListenModel.getKeyguardGoingAway());
            advance.setKeyguardIsVisible(keyguardFingerprintListenModel.getKeyguardIsVisible());
            advance.setKeyguardOccluded(keyguardFingerprintListenModel.getKeyguardOccluded());
            advance.setOccludingAppRequestingFp(keyguardFingerprintListenModel.getOccludingAppRequestingFp());
            advance.setPrimaryUser(keyguardFingerprintListenModel.getPrimaryUser());
            advance.setShouldListenSfpsState(keyguardFingerprintListenModel.getShouldListenSfpsState());
            advance.setShouldListenForFingerprintAssistant(keyguardFingerprintListenModel.getShouldListenForFingerprintAssistant());
            advance.setStrongerAuthRequired(keyguardFingerprintListenModel.getStrongerAuthRequired());
            advance.setSwitchingUser(keyguardFingerprintListenModel.getSwitchingUser());
            advance.setUdfps(keyguardFingerprintListenModel.getUdfps());
            advance.setUserDoesNotHaveTrust(keyguardFingerprintListenModel.getUserDoesNotHaveTrust());
        }

        public final List<List<String>> toList() {
            return SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(this.buffer), new Function1<KeyguardFingerprintListenModel, List<? extends String>>() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$Buffer$toList$1
                /* JADX DEBUG: Method merged with bridge method */
                public final List<String> invoke(KeyguardFingerprintListenModel keyguardFingerprintListenModel) {
                    return keyguardFingerprintListenModel.getAsStringList();
                }
            }));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardFingerprintListenModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardFingerprintListenModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, 8388607, null);
    }

    public KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.biometricEnabledForUser = z2;
        this.bouncerIsOrWillShow = z3;
        this.canSkipBouncer = z4;
        this.credentialAttempted = z5;
        this.deviceInteractive = z6;
        this.dreaming = z7;
        this.fingerprintDisabled = z8;
        this.fingerprintLockedOut = z9;
        this.goingToSleep = z10;
        this.keyguardGoingAway = z11;
        this.keyguardIsVisible = z12;
        this.keyguardOccluded = z13;
        this.occludingAppRequestingFp = z14;
        this.primaryUser = z15;
        this.shouldListenSfpsState = z16;
        this.shouldListenForFingerprintAssistant = z17;
        this.strongerAuthRequired = z18;
        this.switchingUser = z19;
        this.udfps = z20;
        this.userDoesNotHaveTrust = z21;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends String>>() { // from class: com.android.keyguard.KeyguardFingerprintListenModel$asStringList$2
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final List<String> invoke() {
                return CollectionsKt__CollectionsKt.listOf(new String[]{KeyguardListenModelKt.getDATE_FORMAT().format(Long.valueOf(KeyguardFingerprintListenModel.this.getTimeMillis())), String.valueOf(KeyguardFingerprintListenModel.this.getTimeMillis()), String.valueOf(KeyguardFingerprintListenModel.this.getUserId()), String.valueOf(KeyguardFingerprintListenModel.this.getListening()), String.valueOf(KeyguardFingerprintListenModel.this.getBiometricEnabledForUser()), String.valueOf(KeyguardFingerprintListenModel.this.getBouncerIsOrWillShow()), String.valueOf(KeyguardFingerprintListenModel.this.getCanSkipBouncer()), String.valueOf(KeyguardFingerprintListenModel.this.getCredentialAttempted()), String.valueOf(KeyguardFingerprintListenModel.this.getDeviceInteractive()), String.valueOf(KeyguardFingerprintListenModel.this.getDreaming()), String.valueOf(KeyguardFingerprintListenModel.this.getFingerprintDisabled()), String.valueOf(KeyguardFingerprintListenModel.this.getFingerprintLockedOut()), String.valueOf(KeyguardFingerprintListenModel.this.getGoingToSleep()), String.valueOf(KeyguardFingerprintListenModel.this.getKeyguardGoingAway()), String.valueOf(KeyguardFingerprintListenModel.this.getKeyguardIsVisible()), String.valueOf(KeyguardFingerprintListenModel.this.getKeyguardOccluded()), String.valueOf(KeyguardFingerprintListenModel.this.getOccludingAppRequestingFp()), String.valueOf(KeyguardFingerprintListenModel.this.getPrimaryUser()), String.valueOf(KeyguardFingerprintListenModel.this.getShouldListenSfpsState()), String.valueOf(KeyguardFingerprintListenModel.this.getShouldListenForFingerprintAssistant()), String.valueOf(KeyguardFingerprintListenModel.this.getStrongerAuthRequired()), String.valueOf(KeyguardFingerprintListenModel.this.getSwitchingUser()), String.valueOf(KeyguardFingerprintListenModel.this.getUdfps()), String.valueOf(KeyguardFingerprintListenModel.this.getUserDoesNotHaveTrust())});
            }
        });
    }

    public /* synthetic */ KeyguardFingerprintListenModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, boolean z9, boolean z10, boolean z11, boolean z12, boolean z13, boolean z14, boolean z15, boolean z16, boolean z17, boolean z18, boolean z19, boolean z20, boolean z21, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 ? false : z6, (i2 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0 ? false : z7, (i2 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0 ? false : z8, (i2 & RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE) != 0 ? false : z9, (i2 & RecyclerView.ViewHolder.FLAG_MOVED) != 0 ? false : z10, (i2 & RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT) != 0 ? false : z11, (i2 & RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST) != 0 ? false : z12, (i2 & 16384) != 0 ? false : z13, (i2 & 32768) != 0 ? false : z14, (i2 & 65536) != 0 ? false : z15, (i2 & 131072) != 0 ? false : z16, (i2 & 262144) != 0 ? false : z17, (i2 & 524288) != 0 ? false : z18, (i2 & 1048576) != 0 ? false : z19, (i2 & 2097152) != 0 ? false : z20, (i2 & 4194304) != 0 ? false : z21);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardFingerprintListenModel) {
            KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) obj;
            return getTimeMillis() == keyguardFingerprintListenModel.getTimeMillis() && getUserId() == keyguardFingerprintListenModel.getUserId() && getListening() == keyguardFingerprintListenModel.getListening() && this.biometricEnabledForUser == keyguardFingerprintListenModel.biometricEnabledForUser && this.bouncerIsOrWillShow == keyguardFingerprintListenModel.bouncerIsOrWillShow && this.canSkipBouncer == keyguardFingerprintListenModel.canSkipBouncer && this.credentialAttempted == keyguardFingerprintListenModel.credentialAttempted && this.deviceInteractive == keyguardFingerprintListenModel.deviceInteractive && this.dreaming == keyguardFingerprintListenModel.dreaming && this.fingerprintDisabled == keyguardFingerprintListenModel.fingerprintDisabled && this.fingerprintLockedOut == keyguardFingerprintListenModel.fingerprintLockedOut && this.goingToSleep == keyguardFingerprintListenModel.goingToSleep && this.keyguardGoingAway == keyguardFingerprintListenModel.keyguardGoingAway && this.keyguardIsVisible == keyguardFingerprintListenModel.keyguardIsVisible && this.keyguardOccluded == keyguardFingerprintListenModel.keyguardOccluded && this.occludingAppRequestingFp == keyguardFingerprintListenModel.occludingAppRequestingFp && this.primaryUser == keyguardFingerprintListenModel.primaryUser && this.shouldListenSfpsState == keyguardFingerprintListenModel.shouldListenSfpsState && this.shouldListenForFingerprintAssistant == keyguardFingerprintListenModel.shouldListenForFingerprintAssistant && this.strongerAuthRequired == keyguardFingerprintListenModel.strongerAuthRequired && this.switchingUser == keyguardFingerprintListenModel.switchingUser && this.udfps == keyguardFingerprintListenModel.udfps && this.userDoesNotHaveTrust == keyguardFingerprintListenModel.userDoesNotHaveTrust;
        }
        return false;
    }

    public final List<String> getAsStringList() {
        return (List) this.asStringList$delegate.getValue();
    }

    public final boolean getBiometricEnabledForUser() {
        return this.biometricEnabledForUser;
    }

    public final boolean getBouncerIsOrWillShow() {
        return this.bouncerIsOrWillShow;
    }

    public final boolean getCanSkipBouncer() {
        return this.canSkipBouncer;
    }

    public final boolean getCredentialAttempted() {
        return this.credentialAttempted;
    }

    public final boolean getDeviceInteractive() {
        return this.deviceInteractive;
    }

    public final boolean getDreaming() {
        return this.dreaming;
    }

    public final boolean getFingerprintDisabled() {
        return this.fingerprintDisabled;
    }

    public final boolean getFingerprintLockedOut() {
        return this.fingerprintLockedOut;
    }

    public final boolean getGoingToSleep() {
        return this.goingToSleep;
    }

    public final boolean getKeyguardGoingAway() {
        return this.keyguardGoingAway;
    }

    public final boolean getKeyguardIsVisible() {
        return this.keyguardIsVisible;
    }

    public final boolean getKeyguardOccluded() {
        return this.keyguardOccluded;
    }

    public boolean getListening() {
        return this.listening;
    }

    public final boolean getOccludingAppRequestingFp() {
        return this.occludingAppRequestingFp;
    }

    public final boolean getPrimaryUser() {
        return this.primaryUser;
    }

    public final boolean getShouldListenForFingerprintAssistant() {
        return this.shouldListenForFingerprintAssistant;
    }

    public final boolean getShouldListenSfpsState() {
        return this.shouldListenSfpsState;
    }

    public final boolean getStrongerAuthRequired() {
        return this.strongerAuthRequired;
    }

    public final boolean getSwitchingUser() {
        return this.switchingUser;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    public final boolean getUdfps() {
        return this.udfps;
    }

    public final boolean getUserDoesNotHaveTrust() {
        return this.userDoesNotHaveTrust;
    }

    public int getUserId() {
        return this.userId;
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
        boolean z2 = this.biometricEnabledForUser;
        int i2 = z2 ? 1 : 0;
        if (z2) {
            i2 = 1;
        }
        boolean z3 = this.bouncerIsOrWillShow;
        int i3 = z3 ? 1 : 0;
        if (z3) {
            i3 = 1;
        }
        boolean z4 = this.canSkipBouncer;
        int i4 = z4 ? 1 : 0;
        if (z4) {
            i4 = 1;
        }
        boolean z5 = this.credentialAttempted;
        int i5 = z5 ? 1 : 0;
        if (z5) {
            i5 = 1;
        }
        boolean z6 = this.deviceInteractive;
        int i6 = z6 ? 1 : 0;
        if (z6) {
            i6 = 1;
        }
        boolean z7 = this.dreaming;
        int i7 = z7 ? 1 : 0;
        if (z7) {
            i7 = 1;
        }
        boolean z8 = this.fingerprintDisabled;
        int i8 = z8 ? 1 : 0;
        if (z8) {
            i8 = 1;
        }
        boolean z9 = this.fingerprintLockedOut;
        int i9 = z9 ? 1 : 0;
        if (z9) {
            i9 = 1;
        }
        boolean z10 = this.goingToSleep;
        int i10 = z10 ? 1 : 0;
        if (z10) {
            i10 = 1;
        }
        boolean z11 = this.keyguardGoingAway;
        int i11 = z11 ? 1 : 0;
        if (z11) {
            i11 = 1;
        }
        boolean z12 = this.keyguardIsVisible;
        int i12 = z12 ? 1 : 0;
        if (z12) {
            i12 = 1;
        }
        boolean z13 = this.keyguardOccluded;
        int i13 = z13 ? 1 : 0;
        if (z13) {
            i13 = 1;
        }
        boolean z14 = this.occludingAppRequestingFp;
        int i14 = z14 ? 1 : 0;
        if (z14) {
            i14 = 1;
        }
        boolean z15 = this.primaryUser;
        int i15 = z15 ? 1 : 0;
        if (z15) {
            i15 = 1;
        }
        boolean z16 = this.shouldListenSfpsState;
        int i16 = z16 ? 1 : 0;
        if (z16) {
            i16 = 1;
        }
        boolean z17 = this.shouldListenForFingerprintAssistant;
        int i17 = z17 ? 1 : 0;
        if (z17) {
            i17 = 1;
        }
        boolean z18 = this.strongerAuthRequired;
        int i18 = z18 ? 1 : 0;
        if (z18) {
            i18 = 1;
        }
        boolean z19 = this.switchingUser;
        int i19 = z19 ? 1 : 0;
        if (z19) {
            i19 = 1;
        }
        boolean z20 = this.udfps;
        int i20 = z20 ? 1 : 0;
        if (z20) {
            i20 = 1;
        }
        boolean z21 = this.userDoesNotHaveTrust;
        if (!z21) {
            i = z21 ? 1 : 0;
        }
        return (((((((((((((((((((((((((((((((((((((((((((hashCode * 31) + hashCode2) * 31) + (z ? 1 : 0)) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6) * 31) + i7) * 31) + i8) * 31) + i9) * 31) + i10) * 31) + i11) * 31) + i12) * 31) + i13) * 31) + i14) * 31) + i15) * 31) + i16) * 31) + i17) * 31) + i18) * 31) + i19) * 31) + i20) * 31) + i;
    }

    public final void setBiometricEnabledForUser(boolean z) {
        this.biometricEnabledForUser = z;
    }

    public final void setBouncerIsOrWillShow(boolean z) {
        this.bouncerIsOrWillShow = z;
    }

    public final void setCanSkipBouncer(boolean z) {
        this.canSkipBouncer = z;
    }

    public final void setCredentialAttempted(boolean z) {
        this.credentialAttempted = z;
    }

    public final void setDeviceInteractive(boolean z) {
        this.deviceInteractive = z;
    }

    public final void setDreaming(boolean z) {
        this.dreaming = z;
    }

    public final void setFingerprintDisabled(boolean z) {
        this.fingerprintDisabled = z;
    }

    public final void setFingerprintLockedOut(boolean z) {
        this.fingerprintLockedOut = z;
    }

    public final void setGoingToSleep(boolean z) {
        this.goingToSleep = z;
    }

    public final void setKeyguardGoingAway(boolean z) {
        this.keyguardGoingAway = z;
    }

    public final void setKeyguardIsVisible(boolean z) {
        this.keyguardIsVisible = z;
    }

    public final void setKeyguardOccluded(boolean z) {
        this.keyguardOccluded = z;
    }

    public void setListening(boolean z) {
        this.listening = z;
    }

    public final void setOccludingAppRequestingFp(boolean z) {
        this.occludingAppRequestingFp = z;
    }

    public final void setPrimaryUser(boolean z) {
        this.primaryUser = z;
    }

    public final void setShouldListenForFingerprintAssistant(boolean z) {
        this.shouldListenForFingerprintAssistant = z;
    }

    public final void setShouldListenSfpsState(boolean z) {
        this.shouldListenSfpsState = z;
    }

    public final void setStrongerAuthRequired(boolean z) {
        this.strongerAuthRequired = z;
    }

    public final void setSwitchingUser(boolean z) {
        this.switchingUser = z;
    }

    public void setTimeMillis(long j) {
        this.timeMillis = j;
    }

    public final void setUdfps(boolean z) {
        this.udfps = z;
    }

    public final void setUserDoesNotHaveTrust(boolean z) {
        this.userDoesNotHaveTrust = z;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public String toString() {
        long timeMillis = getTimeMillis();
        int userId = getUserId();
        boolean listening = getListening();
        boolean z = this.biometricEnabledForUser;
        boolean z2 = this.bouncerIsOrWillShow;
        boolean z3 = this.canSkipBouncer;
        boolean z4 = this.credentialAttempted;
        boolean z5 = this.deviceInteractive;
        boolean z6 = this.dreaming;
        boolean z7 = this.fingerprintDisabled;
        boolean z8 = this.fingerprintLockedOut;
        boolean z9 = this.goingToSleep;
        boolean z10 = this.keyguardGoingAway;
        boolean z11 = this.keyguardIsVisible;
        boolean z12 = this.keyguardOccluded;
        boolean z13 = this.occludingAppRequestingFp;
        boolean z14 = this.primaryUser;
        boolean z15 = this.shouldListenSfpsState;
        boolean z16 = this.shouldListenForFingerprintAssistant;
        boolean z17 = this.strongerAuthRequired;
        boolean z18 = this.switchingUser;
        boolean z19 = this.udfps;
        boolean z20 = this.userDoesNotHaveTrust;
        return "KeyguardFingerprintListenModel(timeMillis=" + timeMillis + ", userId=" + userId + ", listening=" + listening + ", biometricEnabledForUser=" + z + ", bouncerIsOrWillShow=" + z2 + ", canSkipBouncer=" + z3 + ", credentialAttempted=" + z4 + ", deviceInteractive=" + z5 + ", dreaming=" + z6 + ", fingerprintDisabled=" + z7 + ", fingerprintLockedOut=" + z8 + ", goingToSleep=" + z9 + ", keyguardGoingAway=" + z10 + ", keyguardIsVisible=" + z11 + ", keyguardOccluded=" + z12 + ", occludingAppRequestingFp=" + z13 + ", primaryUser=" + z14 + ", shouldListenSfpsState=" + z15 + ", shouldListenForFingerprintAssistant=" + z16 + ", strongerAuthRequired=" + z17 + ", switchingUser=" + z18 + ", udfps=" + z19 + ", userDoesNotHaveTrust=" + z20 + ")";
    }
}