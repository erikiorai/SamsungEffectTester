package com.android.systemui.power;

/* loaded from: mainsysui33.jar:com/android/systemui/power/BatteryStateSnapshot.class */
public final class BatteryStateSnapshot {
    public final long averageTimeToDischargeMillis;
    public final int batteryLevel;
    public final int batteryStatus;
    public final int bucket;
    public final boolean isBasedOnUsage;
    public boolean isHybrid;
    public final boolean isLowWarningEnabled;
    public final boolean isPowerSaver;
    public final int lowLevelThreshold;
    public final long lowThresholdMillis;
    public final boolean plugged;
    public final int severeLevelThreshold;
    public final long severeThresholdMillis;
    public final long timeRemainingMillis;

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5) {
        this(i, z, z2, i2, i3, i4, i5, -1L, -1L, -1L, -1L, false, true);
        this.isHybrid = false;
    }

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, long j, long j2, long j3, long j4, boolean z3, boolean z4) {
        this.batteryLevel = i;
        this.isPowerSaver = z;
        this.plugged = z2;
        this.bucket = i2;
        this.batteryStatus = i3;
        this.severeLevelThreshold = i4;
        this.lowLevelThreshold = i5;
        this.timeRemainingMillis = j;
        this.averageTimeToDischargeMillis = j2;
        this.severeThresholdMillis = j3;
        this.lowThresholdMillis = j4;
        this.isBasedOnUsage = z3;
        this.isLowWarningEnabled = z4;
        this.isHybrid = true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BatteryStateSnapshot) {
            BatteryStateSnapshot batteryStateSnapshot = (BatteryStateSnapshot) obj;
            return this.batteryLevel == batteryStateSnapshot.batteryLevel && this.isPowerSaver == batteryStateSnapshot.isPowerSaver && this.plugged == batteryStateSnapshot.plugged && this.bucket == batteryStateSnapshot.bucket && this.batteryStatus == batteryStateSnapshot.batteryStatus && this.severeLevelThreshold == batteryStateSnapshot.severeLevelThreshold && this.lowLevelThreshold == batteryStateSnapshot.lowLevelThreshold && this.timeRemainingMillis == batteryStateSnapshot.timeRemainingMillis && this.averageTimeToDischargeMillis == batteryStateSnapshot.averageTimeToDischargeMillis && this.severeThresholdMillis == batteryStateSnapshot.severeThresholdMillis && this.lowThresholdMillis == batteryStateSnapshot.lowThresholdMillis && this.isBasedOnUsage == batteryStateSnapshot.isBasedOnUsage && this.isLowWarningEnabled == batteryStateSnapshot.isLowWarningEnabled;
        }
        return false;
    }

    public final long getAverageTimeToDischargeMillis() {
        return this.averageTimeToDischargeMillis;
    }

    public final int getBatteryLevel() {
        return this.batteryLevel;
    }

    public final int getBatteryStatus() {
        return this.batteryStatus;
    }

    public final int getBucket() {
        return this.bucket;
    }

    public final int getLowLevelThreshold() {
        return this.lowLevelThreshold;
    }

    public final boolean getPlugged() {
        return this.plugged;
    }

    public final int getSevereLevelThreshold() {
        return this.severeLevelThreshold;
    }

    public final long getSevereThresholdMillis() {
        return this.severeThresholdMillis;
    }

    public final long getTimeRemainingMillis() {
        return this.timeRemainingMillis;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.batteryLevel);
        boolean z = this.isPowerSaver;
        int i = 1;
        int i2 = z ? 1 : 0;
        if (z) {
            i2 = 1;
        }
        boolean z2 = this.plugged;
        int i3 = z2 ? 1 : 0;
        if (z2) {
            i3 = 1;
        }
        int hashCode2 = Integer.hashCode(this.bucket);
        int hashCode3 = Integer.hashCode(this.batteryStatus);
        int hashCode4 = Integer.hashCode(this.severeLevelThreshold);
        int hashCode5 = Integer.hashCode(this.lowLevelThreshold);
        int hashCode6 = Long.hashCode(this.timeRemainingMillis);
        int hashCode7 = Long.hashCode(this.averageTimeToDischargeMillis);
        int hashCode8 = Long.hashCode(this.severeThresholdMillis);
        int hashCode9 = Long.hashCode(this.lowThresholdMillis);
        boolean z3 = this.isBasedOnUsage;
        int i4 = z3 ? 1 : 0;
        if (z3) {
            i4 = 1;
        }
        boolean z4 = this.isLowWarningEnabled;
        if (!z4) {
            i = z4 ? 1 : 0;
        }
        return (((((((((((((((((((((((hashCode * 31) + i2) * 31) + i3) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + hashCode8) * 31) + hashCode9) * 31) + i4) * 31) + i;
    }

    public final boolean isBasedOnUsage() {
        return this.isBasedOnUsage;
    }

    public final boolean isHybrid() {
        return this.isHybrid;
    }

    public final boolean isPowerSaver() {
        return this.isPowerSaver;
    }

    public String toString() {
        int i = this.batteryLevel;
        boolean z = this.isPowerSaver;
        boolean z2 = this.plugged;
        int i2 = this.bucket;
        int i3 = this.batteryStatus;
        int i4 = this.severeLevelThreshold;
        int i5 = this.lowLevelThreshold;
        long j = this.timeRemainingMillis;
        long j2 = this.averageTimeToDischargeMillis;
        long j3 = this.severeThresholdMillis;
        long j4 = this.lowThresholdMillis;
        boolean z3 = this.isBasedOnUsage;
        boolean z4 = this.isLowWarningEnabled;
        return "BatteryStateSnapshot(batteryLevel=" + i + ", isPowerSaver=" + z + ", plugged=" + z2 + ", bucket=" + i2 + ", batteryStatus=" + i3 + ", severeLevelThreshold=" + i4 + ", lowLevelThreshold=" + i5 + ", timeRemainingMillis=" + j + ", averageTimeToDischargeMillis=" + j2 + ", severeThresholdMillis=" + j3 + ", lowThresholdMillis=" + j4 + ", isBasedOnUsage=" + z3 + ", isLowWarningEnabled=" + z4 + ")";
    }
}