package com.android.systemui.qs.external;

import com.android.internal.logging.UiEventLogger;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialogEvent.class */
public enum TileRequestDialogEvent implements UiEventLogger.UiEventEnum {
    TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED(917),
    TILE_REQUEST_DIALOG_SHOWN(918),
    TILE_REQUEST_DIALOG_DISMISSED(919),
    TILE_REQUEST_DIALOG_TILE_ADDED(920),
    TILE_REQUEST_DIALOG_TILE_NOT_ADDED(921);
    
    private final int _id;

    TileRequestDialogEvent(int i) {
        this._id = i;
    }

    public int getId() {
        return this._id;
    }
}