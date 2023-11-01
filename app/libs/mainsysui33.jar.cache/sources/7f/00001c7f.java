package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/KeyguardMediaController_Factory.class */
public final class KeyguardMediaController_Factory implements Factory<KeyguardMediaController> {
    public final Provider<KeyguardBypassController> bypassControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<MediaHost> mediaHostProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;

    public KeyguardMediaController_Factory(Provider<MediaHost> provider, Provider<KeyguardBypassController> provider2, Provider<SysuiStatusBarStateController> provider3, Provider<Context> provider4, Provider<SecureSettings> provider5, Provider<Handler> provider6, Provider<ConfigurationController> provider7) {
        this.mediaHostProvider = provider;
        this.bypassControllerProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.contextProvider = provider4;
        this.secureSettingsProvider = provider5;
        this.handlerProvider = provider6;
        this.configurationControllerProvider = provider7;
    }

    public static KeyguardMediaController_Factory create(Provider<MediaHost> provider, Provider<KeyguardBypassController> provider2, Provider<SysuiStatusBarStateController> provider3, Provider<Context> provider4, Provider<SecureSettings> provider5, Provider<Handler> provider6, Provider<ConfigurationController> provider7) {
        return new KeyguardMediaController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static KeyguardMediaController newInstance(MediaHost mediaHost, KeyguardBypassController keyguardBypassController, SysuiStatusBarStateController sysuiStatusBarStateController, Context context, SecureSettings secureSettings, Handler handler, ConfigurationController configurationController) {
        return new KeyguardMediaController(mediaHost, keyguardBypassController, sysuiStatusBarStateController, context, secureSettings, handler, configurationController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardMediaController m3232get() {
        return newInstance((MediaHost) this.mediaHostProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (Context) this.contextProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (Handler) this.handlerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}