package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttReceiverUiEventLogger.class */
public final class MediaTttReceiverUiEventLogger {
    public final UiEventLogger logger;

    public MediaTttReceiverUiEventLogger(UiEventLogger uiEventLogger) {
        this.logger = uiEventLogger;
    }

    public final void logReceiverStateChange(ChipStateReceiver chipStateReceiver) {
        this.logger.log(chipStateReceiver.getUiEvent());
    }
}