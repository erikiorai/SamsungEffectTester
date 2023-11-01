package com.android.settingslib.drawer;

import android.content.pm.ProviderInfo;
import android.os.Parcel;

/* loaded from: mainsysui33.jar:com/android/settingslib/drawer/ProviderTile.class */
public class ProviderTile extends Tile {
    public String mAuthority;
    public String mKey;

    public ProviderTile(Parcel parcel) {
        super(parcel);
        this.mAuthority = ((ProviderInfo) this.mComponentInfo).authority;
        this.mKey = getMetaData().getString("com.android.settings.keyhint");
    }
}