package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/DoubleFlag.class */
public final class DoubleFlag implements ParcelableFlag<Double> {

    /* renamed from: default  reason: not valid java name */
    public final double f1default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<DoubleFlag> CREATOR = new Parcelable.Creator<DoubleFlag>() { // from class: com.android.systemui.flags.DoubleFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoubleFlag createFromParcel(Parcel parcel) {
            return new DoubleFlag(parcel, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DoubleFlag[] newArray(int i) {
            return new DoubleFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/DoubleFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public DoubleFlag(int i, String str, String str2, double d, boolean z, boolean z2) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f1default = d;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ DoubleFlag(int i, String str, String str2, double d, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? 0.0d : d, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2);
    }

    public DoubleFlag(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readDouble(), false, false, 48, null);
    }

    public /* synthetic */ DoubleFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
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
        if (obj instanceof DoubleFlag) {
            DoubleFlag doubleFlag = (DoubleFlag) obj;
            return getId() == doubleFlag.getId() && Intrinsics.areEqual(getName(), doubleFlag.getName()) && Intrinsics.areEqual(getNamespace(), doubleFlag.getNamespace()) && Double.compare(getDefault().doubleValue(), doubleFlag.getDefault().doubleValue()) == 0 && getTeamfood() == doubleFlag.getTeamfood() && getOverridden() == doubleFlag.getOverridden();
        }
        return false;
    }

    public Double getDefault() {
        return Double.valueOf(this.f1default);
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
        Double d = getDefault();
        boolean teamfood = getTeamfood();
        boolean overridden = getOverridden();
        return "DoubleFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", default=" + d + ", teamfood=" + teamfood + ", overridden=" + overridden + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeDouble(getDefault().doubleValue());
    }
}