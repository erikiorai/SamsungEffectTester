package com.android.settingslib.drawer;

import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import java.util.ArrayList;
import java.util.Comparator;

/* loaded from: mainsysui33.jar:com/android/settingslib/drawer/Tile.class */
public abstract class Tile implements Parcelable {
    public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>() { // from class: com.android.settingslib.drawer.Tile.1
        @Override // android.os.Parcelable.Creator
        public Tile createFromParcel(Parcel parcel) {
            boolean readBoolean = parcel.readBoolean();
            parcel.setDataPosition(0);
            return readBoolean ? new ProviderTile(parcel) : new ActivityTile(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public Tile[] newArray(int i) {
            return new Tile[i];
        }
    };
    public static final Comparator<Tile> TILE_COMPARATOR = new Comparator() { // from class: com.android.settingslib.drawer.Tile$$ExternalSyntheticLambda0
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return Tile.$r8$lambda$UXAt4ARpQFiubiw40N8kVxrxBUI((Tile) obj, (Tile) obj2);
        }
    };
    public String mCategory;
    public ComponentInfo mComponentInfo;
    public final String mComponentName;
    public final String mComponentPackage;
    public final Intent mIntent;
    public long mLastUpdateTime;
    public Bundle mMetaData;
    public ArrayList<UserHandle> userHandle = new ArrayList<>();

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.drawer.Tile$$ExternalSyntheticLambda0.compare(java.lang.Object, java.lang.Object):int] */
    public static /* synthetic */ int $r8$lambda$UXAt4ARpQFiubiw40N8kVxrxBUI(Tile tile, Tile tile2) {
        return lambda$static$0(tile, tile2);
    }

    public Tile(Parcel parcel) {
        String readString = parcel.readString();
        this.mComponentPackage = readString;
        String readString2 = parcel.readString();
        this.mComponentName = readString2;
        this.mIntent = new Intent().setClassName(readString, readString2);
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.userHandle.add((UserHandle) UserHandle.CREATOR.createFromParcel(parcel));
        }
        this.mCategory = parcel.readString();
        this.mMetaData = parcel.readBundle();
        if (isNewTask()) {
            this.mIntent.addFlags(268435456);
        }
    }

    public static /* synthetic */ int lambda$static$0(Tile tile, Tile tile2) {
        return tile2.getOrder() - tile.getOrder();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bundle getMetaData() {
        return this.mMetaData;
    }

    public int getOrder() {
        if (hasOrder()) {
            return this.mMetaData.getInt("com.android.settings.order");
        }
        return 0;
    }

    public boolean hasOrder() {
        return this.mMetaData.containsKey("com.android.settings.order") && (this.mMetaData.get("com.android.settings.order") instanceof Integer);
    }

    public boolean isNewTask() {
        Bundle bundle = this.mMetaData;
        if (bundle == null || !bundle.containsKey("com.android.settings.new_task")) {
            return false;
        }
        return this.mMetaData.getBoolean("com.android.settings.new_task");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this instanceof ProviderTile);
        parcel.writeString(this.mComponentPackage);
        parcel.writeString(this.mComponentName);
        int size = this.userHandle.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            this.userHandle.get(i2).writeToParcel(parcel, i);
        }
        parcel.writeString(this.mCategory);
        parcel.writeBundle(this.mMetaData);
    }
}