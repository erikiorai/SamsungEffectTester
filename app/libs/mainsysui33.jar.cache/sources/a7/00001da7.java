package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttReceiverUiEvents.class */
public enum MediaTttReceiverUiEvents implements UiEventLogger.UiEventEnum {
    MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER(982),
    MEDIA_TTT_RECEIVER_FAR_FROM_SENDER(983),
    MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED(1263),
    MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED(1264);
    
    private final int metricId;

    MediaTttReceiverUiEvents(int i) {
        this.metricId = i;
    }

    public int getId() {
        return this.metricId;
    }
}