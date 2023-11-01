package com.android.systemui.qs.footer.domain.model;

import com.android.systemui.common.shared.model.Icon;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/domain/model/SecurityButtonConfig.class */
public final class SecurityButtonConfig {
    public final Icon icon;
    public final boolean isClickable;
    public final String text;

    public SecurityButtonConfig(Icon icon, String str, boolean z) {
        this.icon = icon;
        this.text = str;
        this.isClickable = z;
    }

    public final Icon component1() {
        return this.icon;
    }

    public final String component2() {
        return this.text;
    }

    public final boolean component3() {
        return this.isClickable;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SecurityButtonConfig) {
            SecurityButtonConfig securityButtonConfig = (SecurityButtonConfig) obj;
            return Intrinsics.areEqual(this.icon, securityButtonConfig.icon) && Intrinsics.areEqual(this.text, securityButtonConfig.text) && this.isClickable == securityButtonConfig.isClickable;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.icon.hashCode();
        int hashCode2 = this.text.hashCode();
        boolean z = this.isClickable;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((hashCode * 31) + hashCode2) * 31) + i;
    }

    public String toString() {
        Icon icon = this.icon;
        String str = this.text;
        boolean z = this.isClickable;
        return "SecurityButtonConfig(icon=" + icon + ", text=" + str + ", isClickable=" + z + ")";
    }
}