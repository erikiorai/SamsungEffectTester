package com.android.systemui.media.taptotransfer.sender;

import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/sender/MediaTttSenderUiEventLogger_Factory.class */
public final class MediaTttSenderUiEventLogger_Factory implements Factory<MediaTttSenderUiEventLogger> {
    public final Provider<UiEventLogger> loggerProvider;

    public MediaTttSenderUiEventLogger_Factory(Provider<UiEventLogger> provider) {
        this.loggerProvider = provider;
    }

    public static MediaTttSenderUiEventLogger_Factory create(Provider<UiEventLogger> provider) {
        return new MediaTttSenderUiEventLogger_Factory(provider);
    }

    public static MediaTttSenderUiEventLogger newInstance(UiEventLogger uiEventLogger) {
        return new MediaTttSenderUiEventLogger(uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttSenderUiEventLogger m3361get() {
        return newInstance((UiEventLogger) this.loggerProvider.get());
    }
}