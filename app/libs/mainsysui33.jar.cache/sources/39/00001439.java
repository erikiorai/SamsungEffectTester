package com.android.systemui.controls.management;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/DividerWrapper.class */
public final class DividerWrapper extends ElementWrapper {
    public boolean showDivider;
    public boolean showNone;

    public DividerWrapper() {
        this(false, false, 3, null);
    }

    public DividerWrapper(boolean z, boolean z2) {
        super(null);
        this.showNone = z;
        this.showDivider = z2;
    }

    public /* synthetic */ DividerWrapper(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DividerWrapper) {
            DividerWrapper dividerWrapper = (DividerWrapper) obj;
            return this.showNone == dividerWrapper.showNone && this.showDivider == dividerWrapper.showDivider;
        }
        return false;
    }

    public final boolean getShowDivider() {
        return this.showDivider;
    }

    public final boolean getShowNone() {
        return this.showNone;
    }

    public int hashCode() {
        boolean z = this.showNone;
        int i = 1;
        boolean z2 = z;
        if (z) {
            z2 = true;
        }
        boolean z3 = this.showDivider;
        if (!z3) {
            i = z3;
        }
        return ((z2 ? 1 : 0) * 31) + i;
    }

    public final void setShowDivider(boolean z) {
        this.showDivider = z;
    }

    public final void setShowNone(boolean z) {
        this.showNone = z;
    }

    public String toString() {
        boolean z = this.showNone;
        boolean z2 = this.showDivider;
        return "DividerWrapper(showNone=" + z + ", showDivider=" + z2 + ")";
    }
}