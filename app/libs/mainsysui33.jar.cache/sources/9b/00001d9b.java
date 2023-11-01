package com.android.systemui.media.taptotransfer.receiver;

import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/ChipStateReceiver.class */
public enum ChipStateReceiver {
    CLOSE_TO_SENDER(0, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_CLOSE_TO_SENDER),
    FAR_FROM_SENDER(1, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_FAR_FROM_SENDER),
    TRANSFER_TO_RECEIVER_SUCCEEDED(2, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_SUCCEEDED),
    TRANSFER_TO_RECEIVER_FAILED(3, MediaTttReceiverUiEvents.MEDIA_TTT_RECEIVER_TRANSFER_TO_RECEIVER_FAILED);
    
    public static final Companion Companion = new Companion(null);
    private final int stateInt;
    private final UiEventLogger.UiEventEnum uiEvent;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/ChipStateReceiver$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ChipStateReceiver getReceiverStateFromId(int i) {
            ChipStateReceiver chipStateReceiver;
            ChipStateReceiver[] values;
            int length;
            int i2;
            try {
                values = ChipStateReceiver.values();
                length = values.length;
            } catch (NoSuchElementException e) {
                Log.e("ChipStateReceiver", "Could not find requested state " + i, e);
                chipStateReceiver = null;
            }
            for (i2 = 0; i2 < length; i2++) {
                chipStateReceiver = values[i2];
                if (chipStateReceiver.getStateInt() == i) {
                    return chipStateReceiver;
                }
            }
            throw new NoSuchElementException("Array contains no element matching the predicate.");
        }

        public final int getReceiverStateIdFromName(String str) {
            return ChipStateReceiver.valueOf(str).getStateInt();
        }
    }

    ChipStateReceiver(int i, UiEventLogger.UiEventEnum uiEventEnum) {
        this.stateInt = i;
        this.uiEvent = uiEventEnum;
    }

    public final int getStateInt() {
        return this.stateInt;
    }

    public final UiEventLogger.UiEventEnum getUiEvent() {
        return this.uiEvent;
    }
}