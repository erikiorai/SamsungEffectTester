package com.android.systemui.media.taptotransfer.sender;

import android.content.Context;
import android.media.MediaRoute2Info;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IUndoMediaTransferCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$string;
import com.android.systemui.common.shared.model.Text;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.media.taptotransfer.common.MediaTttUtils;
import com.android.systemui.media.taptotransfer.sender.ChipStateSender;
import com.android.systemui.media.taptotransfer.sender.SenderEndItem;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.temporarydisplay.ViewPriority;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarEndItem;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/MediaTttSenderCoordinator.class */
public final class MediaTttSenderCoordinator implements CoreStartable {
    public final ChipbarCoordinator chipbarCoordinator;
    public final CommandQueue commandQueue;
    public final Context context;
    public ChipStateSender displayedState;
    public final MediaTttLogger<ChipbarInfo> logger;
    public final MediaTttFlags mediaTttFlags;
    public final MediaTttSenderUiEventLogger uiEventLogger;
    public Map<String, ChipStateSender> stateMap = new LinkedHashMap();
    public final MediaTttSenderCoordinator$commandQueueCallbacks$1 commandQueueCallbacks = new CommandQueue.Callbacks() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1
        public void updateMediaTapToTransferSenderDisplay(int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
            MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i, mediaRoute2Info, iUndoMediaTransferCallback);
        }
    };

    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1] */
    public MediaTttSenderCoordinator(ChipbarCoordinator chipbarCoordinator, CommandQueue commandQueue, Context context, MediaTttLogger<ChipbarInfo> mediaTttLogger, MediaTttFlags mediaTttFlags, MediaTttSenderUiEventLogger mediaTttSenderUiEventLogger) {
        this.chipbarCoordinator = chipbarCoordinator;
        this.commandQueue = commandQueue;
        this.context = context;
        this.logger = mediaTttLogger;
        this.mediaTttFlags = mediaTttFlags;
        this.uiEventLogger = mediaTttSenderUiEventLogger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$updateMediaTapToTransferSenderDisplay$1.run():void] */
    public static final /* synthetic */ Map access$getStateMap$p(MediaTttSenderCoordinator mediaTttSenderCoordinator) {
        return mediaTttSenderCoordinator.stateMap;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1.onClick(android.view.View):void] */
    public static final /* synthetic */ MediaTttSenderUiEventLogger access$getUiEventLogger$p(MediaTttSenderCoordinator mediaTttSenderCoordinator) {
        return mediaTttSenderCoordinator.uiEventLogger;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$commandQueueCallbacks$1.updateMediaTapToTransferSenderDisplay(int, android.media.MediaRoute2Info, com.android.internal.statusbar.IUndoMediaTransferCallback):void, com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1.onClick(android.view.View):void] */
    public static final /* synthetic */ void access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator mediaTttSenderCoordinator, int i, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        mediaTttSenderCoordinator.updateMediaTapToTransferSenderDisplay(i, mediaRoute2Info, iUndoMediaTransferCallback);
    }

    public final ChipbarInfo createChipbarInfo(ChipStateSender chipStateSender, MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback, Context context, MediaTttLogger<ChipbarInfo> mediaTttLogger) {
        ChipbarEndItem.Button undoButton;
        String clientPackageName = mediaRoute2Info.getClientPackageName();
        String obj = mediaRoute2Info.getName().toString();
        TintedIcon tintedIcon = MediaTttUtils.Companion.getIconInfoFromPackageName(context, clientPackageName, mediaTttLogger).toTintedIcon();
        Text chipTextString = chipStateSender.getChipTextString(context, obj);
        SenderEndItem endItem = chipStateSender.getEndItem();
        if (endItem != null) {
            if (endItem instanceof SenderEndItem.Loading) {
                undoButton = ChipbarEndItem.Loading.INSTANCE;
            } else if (endItem instanceof SenderEndItem.Error) {
                undoButton = ChipbarEndItem.Error.INSTANCE;
            } else if (!(endItem instanceof SenderEndItem.UndoButton)) {
                throw new NoWhenBranchMatchedException();
            } else {
                if (iUndoMediaTransferCallback != null) {
                    undoButton = getUndoButton(iUndoMediaTransferCallback, ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).getUiEventOnClick(), ((SenderEndItem.UndoButton) chipStateSender.getEndItem()).getNewState(), mediaRoute2Info);
                }
            }
            return new ChipbarInfo(tintedIcon, chipTextString, undoButton, chipStateSender.getTransferStatus().getVibrationEffect(), "Media Transfer Chip View (Sender)", "MEDIA_TRANSFER_ACTIVATED_SENDER", chipStateSender.getTimeout(), mediaRoute2Info.getId(), ViewPriority.NORMAL);
        }
        undoButton = null;
        return new ChipbarInfo(tintedIcon, chipTextString, undoButton, chipStateSender.getTransferStatus().getVibrationEffect(), "Media Transfer Chip View (Sender)", "MEDIA_TRANSFER_ACTIVATED_SENDER", chipStateSender.getTimeout(), mediaRoute2Info.getId(), ViewPriority.NORMAL);
    }

    public final ChipbarEndItem.Button getUndoButton(final IUndoMediaTransferCallback iUndoMediaTransferCallback, final UiEventLogger.UiEventEnum uiEventEnum, final int i, final MediaRoute2Info mediaRoute2Info) {
        return new ChipbarEndItem.Button(new Text.Resource(R$string.media_transfer_undo), new View.OnClickListener() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$getUndoButton$onClickListener$1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaTttSenderCoordinator.access$getUiEventLogger$p(MediaTttSenderCoordinator.this).logUndoClicked(uiEventEnum);
                iUndoMediaTransferCallback.onUndoTriggered();
                MediaTttSenderCoordinator.access$updateMediaTapToTransferSenderDisplay(MediaTttSenderCoordinator.this, i, mediaRoute2Info, null);
            }
        });
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        if (this.mediaTttFlags.isMediaTttEnabled()) {
            this.commandQueue.addCallback(this.commandQueueCallbacks);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x001f, code lost:
        if (r0 == null) goto L34;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateMediaTapToTransferSenderDisplay(int i, final MediaRoute2Info mediaRoute2Info, IUndoMediaTransferCallback iUndoMediaTransferCallback) {
        String str;
        ChipStateSender.Companion companion = ChipStateSender.Companion;
        ChipStateSender senderStateFromId = companion.getSenderStateFromId(i);
        if (senderStateFromId != null) {
            String name = senderStateFromId.name();
            str = name;
        }
        str = "Invalid";
        this.logger.logStateChange(str, mediaRoute2Info.getId(), mediaRoute2Info.getClientPackageName());
        if (senderStateFromId == null) {
            this.logger.logStateChangeError(i);
            return;
        }
        ChipStateSender chipStateSender = this.stateMap.get(mediaRoute2Info.getId());
        if (!companion.isValidStateTransition(chipStateSender, senderStateFromId)) {
            MediaTttLogger<ChipbarInfo> mediaTttLogger = this.logger;
            String str2 = "FAR_FROM_RECEIVER";
            if (chipStateSender != null) {
                str2 = chipStateSender.name();
                if (str2 == null) {
                    str2 = "FAR_FROM_RECEIVER";
                }
            }
            mediaTttLogger.logInvalidStateTransitionError(str2, senderStateFromId.name());
            return;
        }
        this.uiEventLogger.logSenderStateChange(senderStateFromId);
        this.stateMap.put(mediaRoute2Info.getId(), senderStateFromId);
        if (senderStateFromId != ChipStateSender.FAR_FROM_RECEIVER) {
            this.displayedState = senderStateFromId;
            this.chipbarCoordinator.displayView(createChipbarInfo(senderStateFromId, mediaRoute2Info, iUndoMediaTransferCallback, this.context, this.logger), new Runnable() { // from class: com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator$updateMediaTapToTransferSenderDisplay$1
                @Override // java.lang.Runnable
                public final void run() {
                    MediaTttSenderCoordinator.access$getStateMap$p(MediaTttSenderCoordinator.this).remove(mediaRoute2Info.getId());
                }
            });
            return;
        }
        this.stateMap.remove(mediaRoute2Info.getId());
        ChipStateSender chipStateSender2 = this.displayedState;
        if (chipStateSender2 == null) {
            return;
        }
        if (chipStateSender2.getTransferStatus() != TransferStatus.IN_PROGRESS && chipStateSender2.getTransferStatus() != TransferStatus.SUCCEEDED) {
            this.displayedState = null;
            this.chipbarCoordinator.removeView(mediaRoute2Info.getId(), "FAR_FROM_RECEIVER");
            return;
        }
        MediaTttLogger<ChipbarInfo> mediaTttLogger2 = this.logger;
        String name2 = chipStateSender2.getTransferStatus().name();
        mediaTttLogger2.logRemovalBypass("FAR_FROM_RECEIVER", "transferStatus=" + name2);
    }
}