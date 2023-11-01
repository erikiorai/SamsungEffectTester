package com.android.systemui.media.muteawait;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitLogger_Factory.class */
public final class MediaMuteAwaitLogger_Factory implements Factory<MediaMuteAwaitLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaMuteAwaitLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaMuteAwaitLogger_Factory create(Provider<LogBuffer> provider) {
        return new MediaMuteAwaitLogger_Factory(provider);
    }

    public static MediaMuteAwaitLogger newInstance(LogBuffer logBuffer) {
        return new MediaMuteAwaitLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaMuteAwaitLogger m3335get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}