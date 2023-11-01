package com.android.systemui.media.controls.ui;

import android.content.Context;
import android.os.Handler;
import com.android.keyguard.KeyguardViewController;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.shade.ShadeStateEvents;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHierarchyManager_Factory.class */
public final class MediaHierarchyManager_Factory implements Factory<MediaHierarchyManager> {
    public final Provider<KeyguardBypassController> bypassControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardViewController> keyguardViewControllerProvider;
    public final Provider<MediaCarouselController> mediaCarouselControllerProvider;
    public final Provider<ShadeStateEvents> panelEventsEventsProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public MediaHierarchyManager_Factory(Provider<Context> provider, Provider<SysuiStatusBarStateController> provider2, Provider<KeyguardStateController> provider3, Provider<KeyguardBypassController> provider4, Provider<MediaCarouselController> provider5, Provider<KeyguardViewController> provider6, Provider<DreamOverlayStateController> provider7, Provider<ConfigurationController> provider8, Provider<WakefulnessLifecycle> provider9, Provider<ShadeStateEvents> provider10, Provider<SecureSettings> provider11, Provider<Handler> provider12) {
        this.contextProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.keyguardStateControllerProvider = provider3;
        this.bypassControllerProvider = provider4;
        this.mediaCarouselControllerProvider = provider5;
        this.keyguardViewControllerProvider = provider6;
        this.dreamOverlayStateControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.wakefulnessLifecycleProvider = provider9;
        this.panelEventsEventsProvider = provider10;
        this.secureSettingsProvider = provider11;
        this.handlerProvider = provider12;
    }

    public static MediaHierarchyManager_Factory create(Provider<Context> provider, Provider<SysuiStatusBarStateController> provider2, Provider<KeyguardStateController> provider3, Provider<KeyguardBypassController> provider4, Provider<MediaCarouselController> provider5, Provider<KeyguardViewController> provider6, Provider<DreamOverlayStateController> provider7, Provider<ConfigurationController> provider8, Provider<WakefulnessLifecycle> provider9, Provider<ShadeStateEvents> provider10, Provider<SecureSettings> provider11, Provider<Handler> provider12) {
        return new MediaHierarchyManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static MediaHierarchyManager newInstance(Context context, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, KeyguardBypassController keyguardBypassController, MediaCarouselController mediaCarouselController, KeyguardViewController keyguardViewController, DreamOverlayStateController dreamOverlayStateController, ConfigurationController configurationController, WakefulnessLifecycle wakefulnessLifecycle, ShadeStateEvents shadeStateEvents, SecureSettings secureSettings, Handler handler) {
        return new MediaHierarchyManager(context, sysuiStatusBarStateController, keyguardStateController, keyguardBypassController, mediaCarouselController, keyguardViewController, dreamOverlayStateController, configurationController, wakefulnessLifecycle, shadeStateEvents, secureSettings, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaHierarchyManager m3268get() {
        return newInstance((Context) this.contextProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardBypassController) this.bypassControllerProvider.get(), (MediaCarouselController) this.mediaCarouselControllerProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (ShadeStateEvents) this.panelEventsEventsProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (Handler) this.handlerProvider.get());
    }
}