package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.util.Log;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.common.shared.model.Text;
import java.util.NoSuchElementException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/ChipStateSender.class */
public enum ChipStateSender {
    ALMOST_CLOSE_TO_START_CAST { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_START_CAST
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED;
        }
    },
    ALMOST_CLOSE_TO_END_CAST { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.ALMOST_CLOSE_TO_END_CAST
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED;
        }
    },
    TRANSFER_TO_RECEIVER_TRIGGERED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_FAILED;
        }
    },
    TRANSFER_TO_THIS_DEVICE_TRIGGERED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED;
        }
    },
    TRANSFER_TO_RECEIVER_SUCCEEDED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_SUCCEEDED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.ALMOST_CLOSE_TO_START_CAST || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED;
        }
    },
    TRANSFER_TO_THIS_DEVICE_SUCCEEDED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_SUCCEEDED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.ALMOST_CLOSE_TO_END_CAST || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED;
        }
    },
    TRANSFER_TO_RECEIVER_FAILED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_RECEIVER_FAILED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.ALMOST_CLOSE_TO_START_CAST || chipStateSender == ChipStateSender.TRANSFER_TO_THIS_DEVICE_TRIGGERED;
        }
    },
    TRANSFER_TO_THIS_DEVICE_FAILED { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.TRANSFER_TO_THIS_DEVICE_FAILED
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == ChipStateSender.FAR_FROM_RECEIVER || chipStateSender == ChipStateSender.ALMOST_CLOSE_TO_END_CAST || chipStateSender == ChipStateSender.TRANSFER_TO_RECEIVER_TRIGGERED;
        }
    },
    FAR_FROM_RECEIVER { // from class: com.android.systemui.media.taptotransfer.sender.ChipStateSender.FAR_FROM_RECEIVER
        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public Text getChipTextString(Context context, String str) {
            throw new IllegalArgumentException("FAR_FROM_RECEIVER should never be displayed, so its string should never be fetched");
        }

        @Override // com.android.systemui.media.taptotransfer.sender.ChipStateSender
        public boolean isValidNextState(ChipStateSender chipStateSender) {
            return chipStateSender == this || chipStateSender.getTransferStatus() == TransferStatus.NOT_STARTED || chipStateSender.getTransferStatus() == TransferStatus.IN_PROGRESS;
        }
    };
    
    public static final Companion Companion = new Companion(null);
    private final SenderEndItem endItem;
    private final int stateInt;
    private final Integer stringResId;
    private final int timeout;
    private final TransferStatus transferStatus;
    private final UiEventLogger.UiEventEnum uiEvent;

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/ChipStateSender$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ChipStateSender getSenderStateFromId(int i) {
            ChipStateSender chipStateSender;
            ChipStateSender[] values;
            int length;
            int i2;
            try {
                values = ChipStateSender.values();
                length = values.length;
            } catch (NoSuchElementException e) {
                Log.e("ChipStateSender", "Could not find requested state " + i, e);
                chipStateSender = null;
            }
            for (i2 = 0; i2 < length; i2++) {
                chipStateSender = values[i2];
                if (chipStateSender.getStateInt() == i) {
                    return chipStateSender;
                }
            }
            throw new NoSuchElementException("Array contains no element matching the predicate.");
        }

        public final int getSenderStateIdFromName(String str) {
            return ChipStateSender.valueOf(str).getStateInt();
        }

        public final boolean isValidStateTransition(ChipStateSender chipStateSender, ChipStateSender chipStateSender2) {
            if (chipStateSender == null) {
                return ChipStateSender.FAR_FROM_RECEIVER.isValidNextState(chipStateSender2);
            }
            if (chipStateSender == chipStateSender2) {
                return true;
            }
            return chipStateSender.isValidNextState(chipStateSender2);
        }
    }

    ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, int i2) {
        this.stateInt = i;
        this.uiEvent = uiEventEnum;
        this.stringResId = num;
        this.transferStatus = transferStatus;
        this.endItem = senderEndItem;
        this.timeout = i2;
    }

    /* synthetic */ ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uiEventEnum, num, transferStatus, senderEndItem, (i3 & 32) != 0 ? 10000 : i2);
    }

    /* synthetic */ ChipStateSender(int i, UiEventLogger.UiEventEnum uiEventEnum, Integer num, TransferStatus transferStatus, SenderEndItem senderEndItem, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, uiEventEnum, num, transferStatus, senderEndItem, i2);
    }

    public Text getChipTextString(Context context, String str) {
        Integer num = this.stringResId;
        Intrinsics.checkNotNull(num);
        return new Text.Loaded(context.getString(num.intValue(), str));
    }

    public final SenderEndItem getEndItem() {
        return this.endItem;
    }

    public final int getStateInt() {
        return this.stateInt;
    }

    public final int getTimeout() {
        return this.timeout;
    }

    public final TransferStatus getTransferStatus() {
        return this.transferStatus;
    }

    public final UiEventLogger.UiEventEnum getUiEvent() {
        return this.uiEvent;
    }

    public abstract boolean isValidNextState(ChipStateSender chipStateSender);
}