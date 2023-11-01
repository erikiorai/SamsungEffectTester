package com.android.systemui.keyguard.shared.model;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.WakeSleepReason;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakefulnessModel.class */
public final class WakefulnessModel {
    public static final Companion Companion = new Companion(null);
    public final boolean isWakingUpOrAwake;
    public final WakeSleepReason lastSleepReason;
    public final WakeSleepReason lastWakeReason;
    public final WakefulnessState state;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakefulnessModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WakefulnessModel fromWakefulnessLifecycle(WakefulnessLifecycle wakefulnessLifecycle) {
            WakefulnessState fromWakefulnessLifecycleInt = WakefulnessState.Companion.fromWakefulnessLifecycleInt(wakefulnessLifecycle.getWakefulness());
            boolean z = true;
            if (wakefulnessLifecycle.getWakefulness() != 1) {
                z = wakefulnessLifecycle.getWakefulness() == 2;
            }
            WakeSleepReason.Companion companion = WakeSleepReason.Companion;
            return new WakefulnessModel(fromWakefulnessLifecycleInt, z, companion.fromPowerManagerWakeReason(wakefulnessLifecycle.getLastWakeReason()), companion.fromPowerManagerSleepReason(wakefulnessLifecycle.getLastSleepReason()));
        }
    }

    public WakefulnessModel(WakefulnessState wakefulnessState, boolean z, WakeSleepReason wakeSleepReason, WakeSleepReason wakeSleepReason2) {
        this.state = wakefulnessState;
        this.isWakingUpOrAwake = z;
        this.lastWakeReason = wakeSleepReason;
        this.lastSleepReason = wakeSleepReason2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof WakefulnessModel) {
            WakefulnessModel wakefulnessModel = (WakefulnessModel) obj;
            return this.state == wakefulnessModel.state && this.isWakingUpOrAwake == wakefulnessModel.isWakingUpOrAwake && this.lastWakeReason == wakefulnessModel.lastWakeReason && this.lastSleepReason == wakefulnessModel.lastSleepReason;
        }
        return false;
    }

    public final WakeSleepReason getLastSleepReason() {
        return this.lastSleepReason;
    }

    public final WakeSleepReason getLastWakeReason() {
        return this.lastWakeReason;
    }

    public final WakefulnessState getState() {
        return this.state;
    }

    public int hashCode() {
        int hashCode = this.state.hashCode();
        boolean z = this.isWakingUpOrAwake;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((((hashCode * 31) + i) * 31) + this.lastWakeReason.hashCode()) * 31) + this.lastSleepReason.hashCode();
    }

    public final boolean isWakingUpOrAwake() {
        return this.isWakingUpOrAwake;
    }

    public String toString() {
        WakefulnessState wakefulnessState = this.state;
        boolean z = this.isWakingUpOrAwake;
        WakeSleepReason wakeSleepReason = this.lastWakeReason;
        WakeSleepReason wakeSleepReason2 = this.lastSleepReason;
        return "WakefulnessModel(state=" + wakefulnessState + ", isWakingUpOrAwake=" + z + ", lastWakeReason=" + wakeSleepReason + ", lastSleepReason=" + wakeSleepReason2 + ")";
    }
}