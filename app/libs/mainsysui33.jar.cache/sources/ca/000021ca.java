package com.android.systemui.qs.external;

import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialogEventLogger.class */
public final class TileRequestDialogEventLogger {
    public static final Companion Companion = new Companion(null);
    public final InstanceIdSequence instanceIdSequence;
    public final UiEventLogger uiEventLogger;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileRequestDialogEventLogger$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public TileRequestDialogEventLogger() {
        this(new UiEventLoggerImpl(), new InstanceIdSequence(1048576));
    }

    public TileRequestDialogEventLogger(UiEventLogger uiEventLogger, InstanceIdSequence instanceIdSequence) {
        this.uiEventLogger = uiEventLogger;
        this.instanceIdSequence = instanceIdSequence;
    }

    public final void logDialogShown(String str, InstanceId instanceId) {
        this.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, str, instanceId);
    }

    public final void logTileAlreadyAdded(String str, InstanceId instanceId) {
        this.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED, 0, str, instanceId);
    }

    public final void logUserResponse(int i, String str, InstanceId instanceId) {
        TileRequestDialogEvent tileRequestDialogEvent;
        if (i == 0) {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_NOT_ADDED;
        } else if (i == 2) {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ADDED;
        } else if (i != 3) {
            throw new IllegalArgumentException("User response not valid: " + i);
        } else {
            tileRequestDialogEvent = TileRequestDialogEvent.TILE_REQUEST_DIALOG_DISMISSED;
        }
        this.uiEventLogger.logWithInstanceId(tileRequestDialogEvent, 0, str, instanceId);
    }

    public final InstanceId newInstanceId() {
        return this.instanceIdSequence.newInstanceId();
    }
}