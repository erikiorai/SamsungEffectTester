package com.android.systemui.media.taptotransfer.receiver;

import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttReceiverUiEventLogger_Factory.class */
public final class MediaTttReceiverUiEventLogger_Factory implements Factory<MediaTttReceiverUiEventLogger> {
    public final Provider<UiEventLogger> loggerProvider;

    public MediaTttReceiverUiEventLogger_Factory(Provider<UiEventLogger> provider) {
        this.loggerProvider = provider;
    }

    public static MediaTttReceiverUiEventLogger_Factory create(Provider<UiEventLogger> provider) {
        return new MediaTttReceiverUiEventLogger_Factory(provider);
    }

    public static MediaTttReceiverUiEventLogger newInstance(UiEventLogger uiEventLogger) {
        return new MediaTttReceiverUiEventLogger(uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttReceiverUiEventLogger m3357get() {
        return newInstance((UiEventLogger) this.loggerProvider.get());
    }
}