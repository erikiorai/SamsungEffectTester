package com.android.systemui.media.controls.ui;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselControllerLogger_Factory.class */
public final class MediaCarouselControllerLogger_Factory implements Factory<MediaCarouselControllerLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public MediaCarouselControllerLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static MediaCarouselControllerLogger_Factory create(Provider<LogBuffer> provider) {
        return new MediaCarouselControllerLogger_Factory(provider);
    }

    public static MediaCarouselControllerLogger newInstance(LogBuffer logBuffer) {
        return new MediaCarouselControllerLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaCarouselControllerLogger m3245get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}