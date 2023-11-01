package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/StringFlag.class */
public final class StringFlag implements ParcelableFlag<String> {

    /* renamed from: default  reason: not valid java name */
    public final String f5default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<StringFlag> CREATOR = new Parcelable.Creator<StringFlag>() { // from class: com.android.systemui.flags.StringFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringFlag createFromParcel(Parcel parcel) {
            return new StringFlag(parcel, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public StringFlag[] newArray(int i) {
            return new StringFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/StringFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StringFlag(int i, String str, String str2, String str3, boolean z, boolean z2) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f5default = str3;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ StringFlag(int i, String str, String str2, String str3, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? "" : str3, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public StringFlag(Parcel parcel) {
        this(r0, r0, r0, r0 == null ? "" : r0, false, false, 48, null);
        int readInt = parcel.readInt();
        String readString = parcel.readString();
        String readString2 = parcel.readString();
        String readString3 = parcel.readString();
    }

    public /* synthetic */ StringFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
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
        if (obj instanceof StringFlag) {
            StringFlag stringFlag = (StringFlag) obj;
            return getId() == stringFlag.getId() && Intrinsics.areEqual(getName(), stringFlag.getName()) && Intrinsics.areEqual(getNamespace(), stringFlag.getNamespace()) && Intrinsics.areEqual(getDefault(), stringFlag.getDefault()) && getTeamfood() == stringFlag.getTeamfood() && getOverridden() == stringFlag.getOverridden();
        }
        return false;
    }

    public String getDefault() {
        return this.f5default;
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
        String str = getDefault();
        boolean teamfood = getTeamfood();
        boolean overridden = getOverridden();
        return "StringFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", default=" + str + ", teamfood=" + teamfood + ", overridden=" + overridden + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeString(getDefault());
    }
}