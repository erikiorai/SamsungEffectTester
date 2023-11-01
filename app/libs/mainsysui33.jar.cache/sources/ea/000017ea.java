package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ResourceBooleanFlag.class */
public final class ResourceBooleanFlag implements Flag {
    public final int id;
    public final String name;
    public final String namespace;
    public final int resourceId;
    public final boolean teamfood;

    public ResourceBooleanFlag(int i, String str, String str2, int i2, boolean z) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.resourceId = i2;
        this.teamfood = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResourceBooleanFlag) {
            ResourceBooleanFlag resourceBooleanFlag = (ResourceBooleanFlag) obj;
            return getId() == resourceBooleanFlag.getId() && Intrinsics.areEqual(getName(), resourceBooleanFlag.getName()) && Intrinsics.areEqual(getNamespace(), resourceBooleanFlag.getNamespace()) && getResourceId() == resourceBooleanFlag.getResourceId() && getTeamfood() == resourceBooleanFlag.getTeamfood();
        }
        return false;
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

    public int getResourceId() {
        return this.resourceId;
    }

    @Override // com.android.systemui.flags.Flag
    public boolean getTeamfood() {
        return this.teamfood;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(getId());
        int hashCode2 = getName().hashCode();
        int hashCode3 = getNamespace().hashCode();
        int hashCode4 = Integer.hashCode(getResourceId());
        boolean teamfood = getTeamfood();
        int i = teamfood;
        if (teamfood) {
            i = 1;
        }
        return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
    }

    public String toString() {
        int id = getId();
        String name = getName();
        String namespace = getNamespace();
        int resourceId = getResourceId();
        boolean teamfood = getTeamfood();
        return "ResourceBooleanFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", resourceId=" + resourceId + ", teamfood=" + teamfood + ")";
    }
}