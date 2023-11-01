package com.android.systemui.common.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/TintedIcon.class */
public final class TintedIcon {
    public final Icon icon;
    public final Integer tintAttr;

    public TintedIcon(Icon icon, Integer num) {
        this.icon = icon;
        this.tintAttr = num;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TintedIcon) {
            TintedIcon tintedIcon = (TintedIcon) obj;
            return Intrinsics.areEqual(this.icon, tintedIcon.icon) && Intrinsics.areEqual(this.tintAttr, tintedIcon.tintAttr);
        }
        return false;
    }

    public final Icon getIcon() {
        return this.icon;
    }

    public final Integer getTintAttr() {
        return this.tintAttr;
    }

    public int hashCode() {
        int hashCode = this.icon.hashCode();
        Integer num = this.tintAttr;
        return (hashCode * 31) + (num == null ? 0 : num.hashCode());
    }

    public String toString() {
        Icon icon = this.icon;
        Integer num = this.tintAttr;
        return "TintedIcon(icon=" + icon + ", tintAttr=" + num + ")";
    }
}