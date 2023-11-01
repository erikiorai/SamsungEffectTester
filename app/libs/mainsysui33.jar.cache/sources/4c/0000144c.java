package com.android.systemui.controls.management;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ZoneNameWrapper.class */
public final class ZoneNameWrapper extends ElementWrapper {
    public final CharSequence zoneName;

    public ZoneNameWrapper(CharSequence charSequence) {
        super(null);
        this.zoneName = charSequence;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ZoneNameWrapper) && Intrinsics.areEqual(this.zoneName, ((ZoneNameWrapper) obj).zoneName);
    }

    public final CharSequence getZoneName() {
        return this.zoneName;
    }

    public int hashCode() {
        return this.zoneName.hashCode();
    }

    public String toString() {
        CharSequence charSequence = this.zoneName;
        return "ZoneNameWrapper(zoneName=" + ((Object) charSequence) + ")";
    }
}