package com.android.systemui.media.controls.ui;

import android.content.Context;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaViewController_Factory.class */
public final class MediaViewController_Factory implements Factory<MediaViewController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<MediaViewLogger> loggerProvider;
    public final Provider<MediaHostStatesManager> mediaHostStatesManagerProvider;

    public MediaViewController_Factory(Provider<Context> provider, Provider<ConfigurationController> provider2, Provider<MediaHostStatesManager> provider3, Provider<MediaViewLogger> provider4) {
        this.contextProvider = provider;
        this.configurationControllerProvider = provider2;
        this.mediaHostStatesManagerProvider = provider3;
        this.loggerProvider = provider4;
    }

    public static MediaViewController_Factory create(Provider<Context> provider, Provider<ConfigurationController> provider2, Provider<MediaHostStatesManager> provider3, Provider<MediaViewLogger> provider4) {
        return new MediaViewController_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaViewController newInstance(Context context, ConfigurationController configurationController, MediaHostStatesManager mediaHostStatesManager, MediaViewLogger mediaViewLogger) {
        return new MediaViewController(context, configurationController, mediaHostStatesManager, mediaViewLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaViewController m3280get() {
        return newInstance((Context) this.contextProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (MediaHostStatesManager) this.mediaHostStatesManagerProvider.get(), (MediaViewLogger) this.loggerProvider.get());
    }
}