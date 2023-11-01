package com.android.systemui.flags;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ResourceIntFlag.class */
public final class ResourceIntFlag implements Flag {
    public final int id;
    public final String name;
    public final String namespace;
    public final int resourceId;
    public final boolean teamfood;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ResourceIntFlag) {
            ResourceIntFlag resourceIntFlag = (ResourceIntFlag) obj;
            return getId() == resourceIntFlag.getId() && Intrinsics.areEqual(getName(), resourceIntFlag.getName()) && Intrinsics.areEqual(getNamespace(), resourceIntFlag.getNamespace()) && getResourceId() == resourceIntFlag.getResourceId() && getTeamfood() == resourceIntFlag.getTeamfood();
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
        return "ResourceIntFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", resourceId=" + resourceId + ", teamfood=" + teamfood + ")";
    }
}