package com.android.systemui.media.controls.ui;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewLogger_Factory.class */
public final class MediaViewLogger_Factory implements Factory<MediaViewLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaViewLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaViewLogger_Factory create(Provider<LogBuffer> provider) {
        return new MediaViewLogger_Factory(provider);
    }

    public static MediaViewLogger newInstance(LogBuffer logBuffer) {
        return new MediaViewLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaViewLogger m3283get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}