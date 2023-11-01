package com.android.systemui.plugins;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockMetadata.class */
public final class ClockMetadata {
    private final String clockId;
    private final String name;

    public ClockMetadata(String str, String str2) {
        this.clockId = str;
        this.name = str2;
    }

    public static /* synthetic */ ClockMetadata copy$default(ClockMetadata clockMetadata, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = clockMetadata.clockId;
        }
        if ((i & 2) != 0) {
            str2 = clockMetadata.name;
        }
        return clockMetadata.copy(str, str2);
    }

    public final String component1() {
        return this.clockId;
    }

    public final String component2() {
        return this.name;
    }

    public final ClockMetadata copy(String str, String str2) {
        return new ClockMetadata(str, str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ClockMetadata) {
            ClockMetadata clockMetadata = (ClockMetadata) obj;
            return Intrinsics.areEqual(this.clockId, clockMetadata.clockId) && Intrinsics.areEqual(this.name, clockMetadata.name);
        }
        return false;
    }

    public final String getClockId() {
        return this.clockId;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (this.clockId.hashCode() * 31) + this.name.hashCode();
    }

    public String toString() {
        String str = this.clockId;
        String str2 = this.name;
        return "ClockMetadata(clockId=" + str + ", name=" + str2 + ")";
    }
}