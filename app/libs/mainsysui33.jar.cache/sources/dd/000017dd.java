package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/IntFlag.class */
public final class IntFlag implements ParcelableFlag<Integer> {

    /* renamed from: default  reason: not valid java name */
    public final int f3default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<IntFlag> CREATOR = new Parcelable.Creator<IntFlag>() { // from class: com.android.systemui.flags.IntFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntFlag createFromParcel(Parcel parcel) {
            return new IntFlag(parcel, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public IntFlag[] newArray(int i) {
            return new IntFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/IntFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public IntFlag(int i, String str, String str2, int i2, boolean z, boolean z2) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f3default = i2;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ IntFlag(int i, String str, String str2, int i2, boolean z, boolean z2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i3 & 8) != 0 ? 0 : i2, (i3 & 16) != 0 ? false : z, (i3 & 32) != 0 ? false : z2);
    }

    public IntFlag(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readInt(), false, false, 48, null);
    }

    public /* synthetic */ IntFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return ParcelableFlag.DefaultImpls.describeContents(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntFlag) {
            IntFlag intFlag = (IntFlag) obj;
            return getId() == intFlag.getId() && Intrinsics.areEqual(getName(), intFlag.getName()) && Intrinsics.areEqual(getNamespace(), intFlag.getNamespace()) && getDefault().intValue() == intFlag.getDefault().intValue() && getTeamfood() == intFlag.getTeamfood() && getOverridden() == intFlag.getOverridden();
        }
        return false;
    }

    public Integer getDefault() {
        return Integer.valueOf(this.f3default);
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

    public boolean getOverridden() {
        return this.overridden;
    }

    @Override // com.android.systemui.flags.Flag
    public boolean getTeamfood() {
        return this.teamfood;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(getId());
        int hashCode2 = getName().hashCode();
        int hashCode3 = getNamespace().hashCode();
        int hashCode4 = getDefault().hashCode();
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
        return (((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i2) * 31) + i;
    }

    public String toString() {
        int id = getId();
        String name = getName();
        String namespace = getNamespace();
        Integer num = getDefault();
        boolean teamfood = getTeamfood();
        boolean overridden = getOverridden();
        return "IntFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", default=" + num + ", teamfood=" + teamfood + ", overridden=" + overridden + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeInt(getDefault().intValue());
    }
}