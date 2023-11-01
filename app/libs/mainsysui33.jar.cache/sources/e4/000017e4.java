package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/LongFlag.class */
public final class LongFlag implements ParcelableFlag<Long> {

    /* renamed from: default  reason: not valid java name */
    public final long f4default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<LongFlag> CREATOR = new Parcelable.Creator<LongFlag>() { // from class: com.android.systemui.flags.LongFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongFlag createFromParcel(Parcel parcel) {
            return new LongFlag(parcel, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LongFlag[] newArray(int i) {
            return new LongFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/LongFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public LongFlag(int i, long j, boolean z, String str, String str2, boolean z2) {
        this.id = i;
        this.f4default = j;
        this.teamfood = z;
        this.name = str;
        this.namespace = str2;
        this.overridden = z2;
    }

    public /* synthetic */ LongFlag(int i, long j, boolean z, String str, String str2, boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? 0L : j, (i2 & 4) != 0 ? false : z, str, str2, (i2 & 32) != 0 ? false : z2);
    }

    public LongFlag(Parcel parcel) {
        this(parcel.readInt(), parcel.readLong(), false, parcel.readString(), parcel.readString(), false, 36, null);
    }

    public /* synthetic */ LongFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
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
        if (obj instanceof LongFlag) {
            LongFlag longFlag = (LongFlag) obj;
            return getId() == longFlag.getId() && getDefault().longValue() == longFlag.getDefault().longValue() && getTeamfood() == longFlag.getTeamfood() && Intrinsics.areEqual(getName(), longFlag.getName()) && Intrinsics.areEqual(getNamespace(), longFlag.getNamespace()) && getOverridden() == longFlag.getOverridden();
        }
        return false;
    }

    public Long getDefault() {
        return Long.valueOf(this.f4default);
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
        int hashCode2 = getDefault().hashCode();
        boolean teamfood = getTeamfood();
        int i = 1;
        int i2 = teamfood;
        if (teamfood) {
            i2 = 1;
        }
        int hashCode3 = getName().hashCode();
        int hashCode4 = getNamespace().hashCode();
        boolean overridden = getOverridden();
        if (!overridden) {
            i = overridden;
        }
        return (((((((((hashCode * 31) + hashCode2) * 31) + i2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
    }

    public String toString() {
        int id = getId();
        Long l = getDefault();
        boolean teamfood = getTeamfood();
        String name = getName();
        String namespace = getNamespace();
        boolean overridden = getOverridden();
        return "LongFlag(id=" + id + ", default=" + l + ", teamfood=" + teamfood + ", name=" + name + ", namespace=" + namespace + ", overridden=" + overridden + ")";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeLong(getDefault().longValue());
    }
}