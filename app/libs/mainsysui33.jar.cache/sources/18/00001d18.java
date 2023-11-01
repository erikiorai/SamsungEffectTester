package com.android.systemui.media.dagger;

import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.media.taptotransfer.receiver.ChipReceiverInfo;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule_ProvidesMediaTttReceiverLoggerFactory.class */
public final class MediaModule_ProvidesMediaTttReceiverLoggerFactory implements Factory<MediaTttLogger<ChipReceiverInfo>> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaModule_ProvidesMediaTttReceiverLoggerFactory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaModule_ProvidesMediaTttReceiverLoggerFactory create(Provider<LogBuffer> provider) {
        return new MediaModule_ProvidesMediaTttReceiverLoggerFactory(provider);
    }

    public static MediaTttLogger<ChipReceiverInfo> providesMediaTttReceiverLogger(LogBuffer logBuffer) {
        return (MediaTttLogger) Preconditions.checkNotNullFromProvides(MediaModule.providesMediaTttReceiverLogger(logBuffer));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttLogger<ChipReceiverInfo> m3291get() {
        return providesMediaTttReceiverLogger((LogBuffer) this.bufferProvider.get());
    }
}