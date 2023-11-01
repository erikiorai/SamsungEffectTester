package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.ui.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.media.controls.ui.MediaHostStatesManager;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.media.taptotransfer.receiver.ChipReceiverInfo;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import dagger.Lazy;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule.class */
public interface MediaModule {
    static MediaHost providesKeyguardMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }

    static Optional<MediaMuteAwaitConnectionCli> providesMediaMuteAwaitConnectionCli(MediaFlags mediaFlags, Lazy<MediaMuteAwaitConnectionCli> lazy) {
        return !mediaFlags.areMuteAwaitConnectionsEnabled() ? Optional.empty() : Optional.of((MediaMuteAwaitConnectionCli) lazy.get());
    }

    static MediaTttLogger<ChipReceiverInfo> providesMediaTttReceiverLogger(LogBuffer logBuffer) {
        return new MediaTttLogger<>("Receiver", logBuffer);
    }

    static MediaTttLogger<ChipbarInfo> providesMediaTttSenderLogger(LogBuffer logBuffer) {
        return new MediaTttLogger<>("Sender", logBuffer);
    }

    static Optional<NearbyMediaDevicesManager> providesNearbyMediaDevicesManager(MediaFlags mediaFlags, Lazy<NearbyMediaDevicesManager> lazy) {
        return !mediaFlags.areNearbyMediaDevicesEnabled() ? Optional.empty() : Optional.of((NearbyMediaDevicesManager) lazy.get());
    }

    static MediaHost providesQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }

    static MediaHost providesQuickQSMediaHost(MediaHost.MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager, MediaDataManager mediaDataManager, MediaHostStatesManager mediaHostStatesManager) {
        return new MediaHost(mediaHostStateHolder, mediaHierarchyManager, mediaDataManager, mediaHostStatesManager);
    }
}