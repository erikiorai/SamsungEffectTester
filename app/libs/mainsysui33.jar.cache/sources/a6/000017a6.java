package com.android.systemui.flags;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.systemui.flags.ParcelableFlag;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/BooleanFlag.class */
public abstract class BooleanFlag implements ParcelableFlag<Boolean> {

    /* renamed from: default  reason: not valid java name */
    public final boolean f0default;
    public final int id;
    public final String name;
    public final String namespace;
    public final boolean overridden;
    public final boolean teamfood;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<BooleanFlag> CREATOR = new Parcelable.Creator<BooleanFlag>() { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1
        /* JADX DEBUG: Method merged with bridge method */
        /* JADX DEBUG: Return type fixed from 'com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1$createFromParcel$1' to match base method */
        @Override // android.os.Parcelable.Creator
        /* renamed from: createFromParcel */
        public BooleanFlag createFromParcel2(final Parcel parcel) {
            return new BooleanFlag(parcel) { // from class: com.android.systemui.flags.BooleanFlag$Companion$CREATOR$1$createFromParcel$1
            };
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public BooleanFlag[] newArray(int i) {
            return new BooleanFlag[i];
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/BooleanFlag$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public BooleanFlag(int i, String str, String str2, boolean z, boolean z2, boolean z3) {
        this.id = i;
        this.name = str;
        this.namespace = str2;
        this.f0default = z;
        this.teamfood = z2;
        this.overridden = z3;
    }

    public BooleanFlag(Parcel parcel) {
        this(parcel.readInt(), parcel.readString(), parcel.readString(), parcel.readBoolean(), parcel.readBoolean(), parcel.readBoolean());
    }

    public /* synthetic */ BooleanFlag(Parcel parcel, DefaultConstructorMarker defaultConstructorMarker) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return ParcelableFlag.DefaultImpls.describeContents(this);
    }

    public Boolean getDefault() {
        return Boolean.valueOf(this.f0default);
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

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(getId());
        parcel.writeString(getName());
        parcel.writeString(getNamespace());
        parcel.writeBoolean(getDefault().booleanValue());
        parcel.writeBoolean(getTeamfood());
        parcel.writeBoolean(getOverridden());
    }
}