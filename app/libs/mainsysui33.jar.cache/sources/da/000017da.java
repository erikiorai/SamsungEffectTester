package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FloatFlag.class */
public final class FloatFlag implements ParcelableFlag<Float> {

    /* renamed from: default  reason: not valid java name */
    public final float f2default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<FloatFlag> CREATOR = new Parcelable.Creator<FloatFlag>() { // from class: com.android.systemui.flags.FloatFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FloatFlag createFromParcel(Parcel parcel) {
            return new FloatFlag(parcel, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FloatFlag[] newArray(int i) {
            return new FloatFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/FloatFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FloatFlag(int i, String str, String str2, float f, boolean z, boolean z2) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f2default = f;
        this.teamfood = z;
        this.overridden = z2;
    }

    public /* synthetic */ FloatFlag(int i, String str, String str2, float f, boolean z, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, str2, (i2 & 8) != 0 ? 0.0f : f, (i2 & 16) != 0 ? false : z, (i2 & 32) != 0 ? false : z2);
    }

    public FloatFlag(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readFloat(), false, false, 48, null);
    }

    public /* synthetic */ FloatFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
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
        if (obj instanceof FloatFlag) {
            FloatFlag floatFlag = (FloatFlag) obj;
            return getId() == floatFlag.getId() && Intrinsics.areEqual(getName(), floatFlag.getName()) && Intrinsics.areEqual(getNamespace(), floatFlag.getNamespace()) && Float.compare(getDefault().floatValue(), floatFlag.getDefault().floatValue()) == 0 && getTeamfood() == floatFlag.getTeamfood() && getOverridden() == floatFlag.getOverridden();
        }
        return false;
    }

    public Float getDefault() {
        return Float.valueOf(this.f2default);
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
        Float f = getDefault();
        boolean teamfood = getTeamfood();
        boolean overridden = getOverridden();
        return "FloatFlag(id=" + id + ", name=" + name + ", namespace=" + namespace + ", default=" + f + ", teamfood=" + teamfood + ", overridden=" + overridden + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeFloat(getDefault().floatValue());
    }
}