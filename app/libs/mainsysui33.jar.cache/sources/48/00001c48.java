package com.android.systemui.media.controls.pipeline;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaTimeoutLogger_Factory.class */
public final class MediaTimeoutLogger_Factory implements Factory<MediaTimeoutLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaTimeoutLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaTimeoutLogger_Factory create(Provider<LogBuffer> provider) {
        return new MediaTimeoutLogger_Factory(provider);
    }

    public static MediaTimeoutLogger newInstance(LogBuffer logBuffer) {
        return new MediaTimeoutLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTimeoutLogger m3209get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}