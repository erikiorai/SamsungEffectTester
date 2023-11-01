package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/SysPropBooleanFlag.class */
public final class SysPropBooleanFlag implements SysPropFlag<Boolean> {

    /* renamed from: default  reason: not valid java name */
    public final boolean f6default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean teamfood;

    public SysPropBooleanFlag(int i, String str, String str2, boolean z) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f6default = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SysPropBooleanFlag) {
            SysPropBooleanFlag sysPropBooleanFlag = (SysPropBooleanFlag) obj;
            return getId() == sysPropBooleanFlag.getId() && Intrinsics.areEqual(getName(), sysPropBooleanFlag.getName()) && Intrinsics.areEqual(getNamespace(), sysPropBooleanFlag.getNamespace()) && getDefault().booleanValue() == sysPropBooleanFlag.getDefault().booleanValue();
        }
        return false;
    }

    public Boolean getDefault() {
        return Boolean.valueOf(this.f6default);
    }

    @Override // com.android.systemui.flags.Flag
    public int getId() {
        return this.id;
    }

    @Override // com.android.systemui.flags.Flag
    public String getName() {
        return this.name;
    }

    @Override // com.android.systemui.flags.Flag
    public String getNamespace() {
        return this.namespace;
    }

    @Override // com.android.systemui.flags.Flag
    public boolean getTeamfood() {
        return this.teamfood;
    }

    public int hashCode() {
        return (((((Integer.hashCode(getId()) * 31) + getName().hashCode()) * 31) + getNamespace().hashCode()) * 31) + getDefault().hashCode();
    }

    public String toString() {
        int id = getId();
        String name = getName();
        String namespace = getNamespace();
        Boolean bool = getDefault();
        return "SysPropBooleanFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", default=" + bool + ")";
    }
}