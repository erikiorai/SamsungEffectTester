package com.android.systemui.qs.carrier;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/CellSignalState.class */
public final class CellSignalState {
    public final String contentDescription;
    public final int mobileSignalIconId;
    public final boolean roaming;
    public final String typeContentDescription;
    public final boolean visible;

    public CellSignalState() {
        this(false, 0, null, null, false, 31, null);
    }

    public CellSignalState(boolean z, int i, String str, String str2, boolean z2) {
        this.visible = z;
        this.mobileSignalIconId = i;
        this.contentDescription = str;
        this.typeContentDescription = str2;
        this.roaming = z2;
    }

    public /* synthetic */ CellSignalState(boolean z, int i, String str, String str2, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? false : z, (i2 & 2) != 0 ? 0 : i, (i2 & 4) != 0 ? null : str, (i2 & 8) != 0 ? null : str2, (i2 & 16) != 0 ? false : z2);
    }

    public static /* synthetic */ CellSignalState copy$default(CellSignalState cellSignalState, boolean z, int i, String str, String str2, boolean z2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = cellSignalState.visible;
        }
        if ((i2 & 2) != 0) {
            i = cellSignalState.mobileSignalIconId;
        }
        if ((i2 & 4) != 0) {
            str = cellSignalState.contentDescription;
        }
        if ((i2 & 8) != 0) {
            str2 = cellSignalState.typeContentDescription;
        }
        if ((i2 & 16) != 0) {
            z2 = cellSignalState.roaming;
        }
        return cellSignalState.copy(z, i, str, str2, z2);
    }

    public final CellSignalState changeVisibility(boolean z) {
        return this.visible == z ? this : copy$default(this, z, 0, null, null, false, 30, null);
    }

    public final CellSignalState copy(boolean z, int i, String str, String str2, boolean z2) {
        return new CellSignalState(z, i, str, str2, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CellSignalState) {
            CellSignalState cellSignalState = (CellSignalState) obj;
            return this.visible == cellSignalState.visible && this.mobileSignalIconId == cellSignalState.mobileSignalIconId && Intrinsics.areEqual(this.contentDescription, cellSignalState.contentDescription) && Intrinsics.areEqual(this.typeContentDescription, cellSignalState.typeContentDescription) && this.roaming == cellSignalState.roaming;
        }
        return false;
    }

    public int hashCode() {
        boolean z = this.visible;
        int i = 1;
        boolean z2 = z;
        if (z) {
            z2 = true;
        }
        int hashCode = Integer.hashCode(this.mobileSignalIconId);
        String str = this.contentDescription;
        int i2 = 0;
        int hashCode2 = str == null ? 0 : str.hashCode();
        String str2 = this.typeContentDescription;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        boolean z3 = this.roaming;
        if (!z3) {
            i = z3 ? 1 : 0;
        }
        return ((((((((z2 ? 1 : 0) * 31) + hashCode) * 31) + hashCode2) * 31) + i2) * 31) + i;
    }

    public String toString() {
        boolean z = this.visible;
        int i = this.mobileSignalIconId;
        String str = this.contentDescription;
        String str2 = this.typeContentDescription;
        boolean z2 = this.roaming;
        return "CellSignalState(visible=" + z + ", mobileSignalIconId=" + i + ", contentDescription=" + str + ", typeContentDescription=" + str2 + ", roaming=" + z2 + ")";
    }
}