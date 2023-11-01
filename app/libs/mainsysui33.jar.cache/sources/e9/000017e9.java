package com.android.systemui.flags;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ReleasedFlag.class */
public final class ReleasedFlag extends BooleanFlag {
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;

    public ReleasedFlag(int i, String str, String str2, boolean z, boolean z2) {
        super(i, str, str2, true, z, z2);
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ ReleasedFlag(int i, String str, String str2, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ReleasedFlag) {
            ReleasedFlag releasedFlag = (ReleasedFlag) obj;
            return getId() == releasedFlag.getId() && Intrinsics.areEqual(getName(), releasedFlag.getName()) && Intrinsics.areEqual(getNamespace(), releasedFlag.getNamespace()) && getTeamfood() == releasedFlag.getTeamfood() && getOverridden() == releasedFlag.getOverridden();
        }
        return false;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public int getId() {
        return this.id;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public String getNamespace() {
        return this.namespace;
    }

    @Override // com.android.systemui.flags.BooleanFlag
    public boolean getOverridden() {
        return this.overridden;
    }

    @Override // com.android.systemui.flags.BooleanFlag, com.android.systemui.flags.Flag
    public boolean getTeamfood() {
        return this.teamfood;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(getId());
        int hashCode2 = getName().hashCode();
        int hashCode3 = getNamespace().hashCode();
        boolean teamfood = getTeamfood();
        int i = 1;
        int i2 = teamfood;
        if (teamfood) {
            i2 = 1;
        }
        boolean overridden = getOverridden();
        if (!overridden) {
            i = overridden;
        }
        return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i2) * 31) + i;
    }

    public String toString() {
        int id = getId();
        String name = getName();
        String namespace = getNamespace();
        boolean teamfood = getTeamfood();
        boolean overridden = getOverridden();
        return "ReleasedFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", teamfood=" + teamfood + ", overridden=" + overridden + ")";
    }
}