package com.android.systemui.media.controls.util;

import com.android.internal.logging.UiEventLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaUiEventLogger_Factory.class */
public final class MediaUiEventLogger_Factory implements Factory<MediaUiEventLogger> {
    public final Provider<UiEventLogger> loggerProvider;

    public MediaUiEventLogger_Factory(Provider<UiEventLogger> provider) {
        this.loggerProvider = provider;
    }

    public static MediaUiEventLogger_Factory create(Provider<UiEventLogger> provider) {
        return new MediaUiEventLogger_Factory(provider);
    }

    public static MediaUiEventLogger newInstance(UiEventLogger uiEventLogger) {
        return new MediaUiEventLogger(uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaUiEventLogger m3288get() {
        return newInstance((UiEventLogger) this.loggerProvider.get());
    }
}