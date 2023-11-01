package com.android.systemui.media.dagger;

import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule_ProvidesMediaTttSenderLoggerFactory.class */
public final class MediaModule_ProvidesMediaTttSenderLoggerFactory implements Factory<MediaTttLogger<ChipbarInfo>> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaModule_ProvidesMediaTttSenderLoggerFactory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaModule_ProvidesMediaTttSenderLoggerFactory create(Provider<LogBuffer> provider) {
        return new MediaModule_ProvidesMediaTttSenderLoggerFactory(provider);
    }

    public static MediaTttLogger<ChipbarInfo> providesMediaTttSenderLogger(LogBuffer logBuffer) {
        return (MediaTttLogger) Preconditions.checkNotNullFromProvides(MediaModule.providesMediaTttSenderLogger(logBuffer));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttLogger<ChipbarInfo> m3292get() {
        return providesMediaTttSenderLogger((LogBuffer) this.bufferProvider.get());
    }
}