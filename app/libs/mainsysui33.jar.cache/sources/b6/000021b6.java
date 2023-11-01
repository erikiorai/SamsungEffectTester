package com.android.systemui.qs.external;

import android.content.Context;
import android.content.SharedPreferences;
import android.service.quicksettings.Tile;
import android.util.Log;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.json.JSONException;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTileStatePersister.class */
public final class CustomTileStatePersister {
    public static final Companion Companion = new Companion(null);
    public final SharedPreferences sharedPreferences;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTileStatePersister$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public CustomTileStatePersister(Context context) {
        this.sharedPreferences = context.getSharedPreferences("custom_tiles_state", 0);
    }

    public final void persistState(TileServiceKey tileServiceKey, Tile tile) {
        this.sharedPreferences.edit().putString(tileServiceKey.toString(), CustomTileStatePersisterKt.writeToString(tile)).apply();
    }

    public final Tile readState(TileServiceKey tileServiceKey) {
        SharedPreferences sharedPreferences = this.sharedPreferences;
        String tileServiceKey2 = tileServiceKey.toString();
        Tile tile = null;
        String string = sharedPreferences.getString(tileServiceKey2, null);
        if (string == null) {
            return null;
        }
        try {
            tile = CustomTileStatePersisterKt.readTileFromString(string);
        } catch (JSONException e) {
            Log.e("TileServicePersistence", "Bad saved state: " + string, e);
        }
        return tile;
    }

    public final void removeState(TileServiceKey tileServiceKey) {
        this.sharedPreferences.edit().remove(tileServiceKey.toString()).apply();
    }
}