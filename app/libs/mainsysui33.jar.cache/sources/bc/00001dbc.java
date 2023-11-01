package com.android.systemui.media.taptotransfer.sender;

import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/MediaTttSenderUiEventLogger.class */
public final class MediaTttSenderUiEventLogger {
    public final UiEventLogger logger;

    public MediaTttSenderUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final void logSenderStateChange(ChipStateSender chipStateSender) {
        this.logger.log(chipStateSender.getUiEvent());
    }

    public final void logUndoClicked(UiEventLogger.UiEventEnum uiEventEnum) {
        if (uiEventEnum == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_RECEIVER_CLICKED || uiEventEnum == MediaTttSenderUiEvents.MEDIA_TTT_SENDER_UNDO_TRANSFER_TO_THIS_DEVICE_CLICKED) {
            this.logger.log(uiEventEnum);
            return;
        }
        String simpleName = Reflection.getOrCreateKotlinClass(MediaTttSenderUiEventLogger.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        Log.w(simpleName, "Must pass an undo-specific UiEvent.");
    }
}