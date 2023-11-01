package com.android.systemui.qs.external;

import android.service.quicksettings.Tile;
import com.android.internal.annotations.VisibleForTesting;
import org.json.JSONObject;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/CustomTileStatePersisterKt.class */
public final class CustomTileStatePersisterKt {
    public static final String getStringOrNull(JSONObject jSONObject, String str) {
        return jSONObject.has(str) ? jSONObject.getString(str) : null;
    }

    @VisibleForTesting
    public static final Tile readTileFromString(String str) {
        JSONObject jSONObject = new JSONObject(str);
        Tile tile = new Tile();
        tile.setState(jSONObject.getInt("state"));
        tile.setLabel(getStringOrNull(jSONObject, "label"));
        tile.setSubtitle(getStringOrNull(jSONObject, "subtitle"));
        tile.setContentDescription(getStringOrNull(jSONObject, "content_description"));
        tile.setStateDescription(getStringOrNull(jSONObject, "state_description"));
        return tile;
    }

    @VisibleForTesting
    public static final String writeToString(Tile tile) {
        return new JSONObject().put("state", tile.getState()).put("label", tile.getLabel()).put("subtitle", tile.getSubtitle()).put("content_description", tile.getContentDescription()).put("state_description", tile.getStateDescription()).toString();
    }
}