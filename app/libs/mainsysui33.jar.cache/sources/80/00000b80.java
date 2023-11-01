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

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardActiveUnlockModel.class */
public final class KeyguardActiveUnlockModel extends KeyguardListenModel {
    public static final Companion Companion = new Companion(null);
    public static final List<String> TABLE_HEADERS = CollectionsKt__CollectionsKt.listOf(new String[]{"timestamp", "time_millis", "userId", "listening", "awakeKeyguard", "authInterruptActive", "fpLockedOut", "primaryAuthRequired", "switchingUser", "triggerActiveUnlockForAssistant", "userCanDismissLockScreen"});
    public final Lazy asStringList$delegate;
    public boolean authInterruptActive;
    public boolean awakeKeyguard;
    public boolean fpLockedOut;
    public boolean listening;
    public boolean primaryAuthRequired;
    public boolean switchingUser;
    public long timeMillis;
    public boolean triggerActiveUnlockForAssistant;
    public boolean userCanDismissLockScreen;
    public int userId;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardActiveUnlockModel$Buffer.class */
    public static final class Buffer {
        public final RingBuffer<KeyguardActiveUnlockModel> buffer = new RingBuffer<>(20, new Function0<KeyguardActiveUnlockModel>() { // from class: com.android.keyguard.KeyguardActiveUnlockModel$Buffer$buffer$1
            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final KeyguardActiveUnlockModel m561invoke() {
                return new KeyguardActiveUnlockModel(0L, 0, false, false, false, false, false, false, false, false, 1023, null);
            }
        });

        public final void insert(KeyguardActiveUnlockModel keyguardActiveUnlockModel) {
            KeyguardActiveUnlockModel advance = this.buffer.advance();
            advance.setTimeMillis(keyguardActiveUnlockModel.getTimeMillis());
            advance.setUserId(keyguardActiveUnlockModel.getUserId());
            advance.setListening(keyguardActiveUnlockModel.getListening());
            advance.setAwakeKeyguard(keyguardActiveUnlockModel.getAwakeKeyguard());
            advance.setAuthInterruptActive(keyguardActiveUnlockModel.getAuthInterruptActive());
            advance.setFpLockedOut(keyguardActiveUnlockModel.getFpLockedOut());
            advance.setPrimaryAuthRequired(keyguardActiveUnlockModel.getPrimaryAuthRequired());
            advance.setSwitchingUser(keyguardActiveUnlockModel.getSwitchingUser());
            advance.setTriggerActiveUnlockForAssistant(keyguardActiveUnlockModel.getTriggerActiveUnlockForAssistant());
            advance.setUserCanDismissLockScreen(keyguardActiveUnlockModel.getUserCanDismissLockScreen());
        }

        public final List<List<String>> toList() {
            return SequencesKt___SequencesKt.toList(SequencesKt___SequencesKt.map(CollectionsKt___CollectionsKt.asSequence(this.buffer), new Function1<KeyguardActiveUnlockModel, List<? extends String>>() { // from class: com.android.keyguard.KeyguardActiveUnlockModel$Buffer$toList$1
                /* JADX DEBUG: Method merged with bridge method */
                public final List<String> invoke(KeyguardActiveUnlockModel keyguardActiveUnlockModel) {
                    return keyguardActiveUnlockModel.getAsStringList();
                }
            }));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardActiveUnlockModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public KeyguardActiveUnlockModel() {
        this(0L, 0, false, false, false, false, false, false, false, false, 1023, null);
    }

    public KeyguardActiveUnlockModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
        super(null);
        this.timeMillis = j;
        this.userId = i;
        this.listening = z;
        this.awakeKeyguard = z2;
        this.authInterruptActive = z3;
        this.fpLockedOut = z4;
        this.primaryAuthRequired = z5;
        this.switchingUser = z6;
        this.triggerActiveUnlockForAssistant = z7;
        this.userCanDismissLockScreen = z8;
        this.asStringList$delegate = LazyKt__LazyJVMKt.lazy(new Function0<List<? extends String>>() { // from class: com.android.keyguard.KeyguardActiveUnlockModel$asStringList$2
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final List<String> invoke() {
                return CollectionsKt__CollectionsKt.listOf(new String[]{KeyguardListenModelKt.getDATE_FORMAT().format(Long.valueOf(KeyguardActiveUnlockModel.this.getTimeMillis())), String.valueOf(KeyguardActiveUnlockModel.this.getTimeMillis()), String.valueOf(KeyguardActiveUnlockModel.this.getUserId()), String.valueOf(KeyguardActiveUnlockModel.this.getListening()), String.valueOf(KeyguardActiveUnlockModel.this.getAwakeKeyguard()), String.valueOf(KeyguardActiveUnlockModel.this.getAuthInterruptActive()), String.valueOf(KeyguardActiveUnlockModel.this.getFpLockedOut()), String.valueOf(KeyguardActiveUnlockModel.this.getPrimaryAuthRequired()), String.valueOf(KeyguardActiveUnlockModel.this.getSwitchingUser()), String.valueOf(KeyguardActiveUnlockModel.this.getTriggerActiveUnlockForAssistant()), String.valueOf(KeyguardActiveUnlockModel.this.getUserCanDismissLockScreen())});
            }
        });
    }

    public /* synthetic */ KeyguardActiveUnlockModel(long j, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0L : j, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? false : z, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4, (i2 & 64) != 0 ? false : z5, (i2 & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 ? false : z6, (i2 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0 ? false : z7, (i2 & RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN) != 0 ? false : z8);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardActiveUnlockModel) {
            KeyguardActiveUnlockModel keyguardActiveUnlockModel = (KeyguardActiveUnlockModel) obj;
            return getTimeMillis() == keyguardActiveUnlockModel.getTimeMillis() && getUserId() == keyguardActiveUnlockModel.getUserId() && getListening() == keyguardActiveUnlockModel.getListening() && this.awakeKeyguard == keyguardActiveUnlockModel.awakeKeyguard && this.authInterruptActive == keyguardActiveUnlockModel.authInterruptActive && this.fpLockedOut == keyguardActiveUnlockModel.fpLockedOut && this.primaryAuthRequired == keyguardActiveUnlockModel.primaryAuthRequired && this.switchingUser == keyguardActiveUnlockModel.switchingUser && this.triggerActiveUnlockForAssistant == keyguardActiveUnlockModel.triggerActiveUnlockForAssistant && this.userCanDismissLockScreen == keyguardActiveUnlockModel.userCanDismissLockScreen;
        }
        return false;
    }

    public final List<String> getAsStringList() {
        return (List) this.asStringList$delegate.getValue();
    }

    public final boolean getAuthInterruptActive() {
        return this.authInterruptActive;
    }

    public final boolean getAwakeKeyguard() {
        return this.awakeKeyguard;
    }

    public final boolean getFpLockedOut() {
        return this.fpLockedOut;
    }

    public boolean getListening() {
        return this.listening;
    }

    public final boolean getPrimaryAuthRequired() {
        return this.primaryAuthRequired;
    }

    public final boolean getSwitchingUser() {
        return this.switchingUser;
    }

    public long getTimeMillis() {
        return this.timeMillis;
    }

    public final boolean getTriggerActiveUnlockForAssistant() {
        return this.triggerActiveUnlockForAssistant;
    }

    public final boolean getUserCanDismissLockScreen() {
        return this.userCanDismissLockScreen;
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
        boolean z2 = this.awakeKeyguard;
        int i2 = z2 ? 1 : 0;
        if (z2) {
            i2 = 1;
        }
        boolean z3 = this.authInterruptActive;
        int i3 = z3 ? 1 : 0;
        if (z3) {
            i3 = 1;
        }
        boolean z4 = this.fpLockedOut;
        int i4 = z4 ? 1 : 0;
        if (z4) {
            i4 = 1;
        }
        boolean z5 = this.primaryAuthRequired;
        int i5 = z5 ? 1 : 0;
        if (z5) {
            i5 = 1;
        }
        boolean z6 = this.switchingUser;
        int i6 = z6 ? 1 : 0;
        if (z6) {
            i6 = 1;
        }
        boolean z7 = this.triggerActiveUnlockForAssistant;
        int i7 = z7 ? 1 : 0;
        if (z7) {
            i7 = 1;
        }
        boolean z8 = this.userCanDismissLockScreen;
        if (!z8) {
            i = z8 ? 1 : 0;
        }
        return (((((((((((((((((hashCode * 31) + hashCode2) * 31) + (z ? 1 : 0)) * 31) + i2) * 31) + i3) * 31) + i4) * 31) + i5) * 31) + i6) * 31) + i7) * 31) + i;
    }

    public final void setAuthInterruptActive(boolean z) {
        this.authInterruptActive = z;
    }

    public final void setAwakeKeyguard(boolean z) {
        this.awakeKeyguard = z;
    }

    public final void setFpLockedOut(boolean z) {
        this.fpLockedOut = z;
    }

    public void setListening(boolean z) {
        this.listening = z;
    }

    public final void setPrimaryAuthRequired(boolean z) {
        this.primaryAuthRequired = z;
    }

    public final void setSwitchingUser(boolean z) {
        this.switchingUser = z;
    }

    public void setTimeMillis(long j) {
        this.timeMillis = j;
    }

    public final void setTriggerActiveUnlockForAssistant(boolean z) {
        this.triggerActiveUnlockForAssistant = z;
    }

    public final void setUserCanDismissLockScreen(boolean z) {
        this.userCanDismissLockScreen = z;
    }

    public void setUserId(int i) {
        this.userId = i;
    }

    public String toString() {
        long timeMillis = getTimeMillis();
        int userId = getUserId();
        boolean listening = getListening();
        boolean z = this.awakeKeyguard;
        boolean z2 = this.authInterruptActive;
        boolean z3 = this.fpLockedOut;
        boolean z4 = this.primaryAuthRequired;
        boolean z5 = this.switchingUser;
        boolean z6 = this.triggerActiveUnlockForAssistant;
        boolean z7 = this.userCanDismissLockScreen;
        return "KeyguardActiveUnlockModel(timeMillis=" + timeMillis + ", userId=" + userId + ", listening=" + listening + ", awakeKeyguard=" + z + ", authInterruptActive=" + z2 + ", fpLockedOut=" + z3 + ", primaryAuthRequired=" + z4 + ", switchingUser=" + z5 + ", triggerActiveUnlockForAssistant=" + z6 + ", userCanDismissLockScreen=" + z7 + ")";
    }
}