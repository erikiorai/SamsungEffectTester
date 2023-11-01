package com.android.systemui.media.muteawait;

import android.content.Context;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.settingslib.media.LocalMediaManager;
import com.android.systemui.media.controls.util.MediaFlags;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitConnectionManagerFactory.class */
public final class MediaMuteAwaitConnectionManagerFactory {
    public final Context context;
    public final DeviceIconUtil deviceIconUtil = new DeviceIconUtil();
    public final MediaMuteAwaitLogger logger;
    public final Executor mainExecutor;
    public final MediaFlags mediaFlags;

    public MediaMuteAwaitConnectionManagerFactory(MediaFlags mediaFlags, Context context, MediaMuteAwaitLogger mediaMuteAwaitLogger, Executor executor) {
        this.mediaFlags = mediaFlags;
        this.context = context;
        this.logger = mediaMuteAwaitLogger;
        this.mainExecutor = executor;
    }

    public final MediaMuteAwaitConnectionManager create(LocalMediaManager localMediaManager) {
        if (this.mediaFlags.areMuteAwaitConnectionsEnabled()) {
            return new MediaMuteAwaitConnectionManager(this.mainExecutor, localMediaManager, this.context, this.deviceIconUtil, this.logger);
        }
        return null;
    }
}