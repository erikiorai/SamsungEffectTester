package com.android.systemui.biometrics.udfps;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/PreprocessedTouch.class */
public final class PreprocessedTouch {
    public final NormalizedTouchData data;
    public final boolean isGoodOverlap;
    public final int previousPointerOnSensorId;

    public PreprocessedTouch(NormalizedTouchData normalizedTouchData, int i, boolean z) {
        this.data = normalizedTouchData;
        this.previousPointerOnSensorId = i;
        this.isGoodOverlap = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PreprocessedTouch) {
            PreprocessedTouch preprocessedTouch = (PreprocessedTouch) obj;
            return Intrinsics.areEqual(this.data, preprocessedTouch.data) && this.previousPointerOnSensorId == preprocessedTouch.previousPointerOnSensorId && this.isGoodOverlap == preprocessedTouch.isGoodOverlap;
        }
        return false;
    }

    public final NormalizedTouchData getData() {
        return this.data;
    }

    public final int getPreviousPointerOnSensorId() {
        return this.previousPointerOnSensorId;
    }

    public int hashCode() {
        int hashCode = this.data.hashCode();
        int hashCode2 = Integer.hashCode(this.previousPointerOnSensorId);
        boolean z = this.isGoodOverlap;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((hashCode * 31) + hashCode2) * 31) + i;
    }

    public final boolean isGoodOverlap() {
        return this.isGoodOverlap;
    }

    public String toString() {
        NormalizedTouchData normalizedTouchData = this.data;
        int i = this.previousPointerOnSensorId;
        boolean z = this.isGoodOverlap;
        return "PreprocessedTouch(data=" + normalizedTouchData + ", previousPointerOnSensorId=" + i + ", isGoodOverlap=" + z + ")";
    }
}