package com.android.systemui.clipboardoverlay;

import android.content.ClipboardManager;
import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardListener_Factory.class */
public final class ClipboardListener_Factory implements Factory<ClipboardListener> {
    public final Provider<ClipboardManager> clipboardManagerProvider;
    public final Provider<ClipboardOverlayController> clipboardOverlayControllerProvider;
    public final Provider<ClipboardToast> clipboardToastProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<ClipboardOverlayControllerLegacyFactory> overlayFactoryProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public ClipboardListener_Factory(Provider<Context> provider, Provider<DeviceConfigProxy> provider2, Provider<ClipboardOverlayController> provider3, Provider<ClipboardOverlayControllerLegacyFactory> provider4, Provider<ClipboardToast> provider5, Provider<ClipboardManager> provider6, Provider<UiEventLogger> provider7, Provider<FeatureFlags> provider8) {
        this.contextProvider = provider;
        this.deviceConfigProxyProvider = provider2;
        this.clipboardOverlayControllerProvider = provider3;
        this.overlayFactoryProvider = provider4;
        this.clipboardToastProvider = provider5;
        this.clipboardManagerProvider = provider6;
        this.uiEventLoggerProvider = provider7;
        this.featureFlagsProvider = provider8;
    }

    public static ClipboardListener_Factory create(Provider<Context> provider, Provider<DeviceConfigProxy> provider2, Provider<ClipboardOverlayController> provider3, Provider<ClipboardOverlayControllerLegacyFactory> provider4, Provider<ClipboardToast> provider5, Provider<ClipboardManager> provider6, Provider<UiEventLogger> provider7, Provider<FeatureFlags> provider8) {
        return new ClipboardListener_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static ClipboardListener newInstance(Context context, DeviceConfigProxy deviceConfigProxy, Provider<ClipboardOverlayController> provider, ClipboardOverlayControllerLegacyFactory clipboardOverlayControllerLegacyFactory, Object obj, ClipboardManager clipboardManager, UiEventLogger uiEventLogger, FeatureFlags featureFlags) {
        return new ClipboardListener(context, deviceConfigProxy, provider, clipboardOverlayControllerLegacyFactory, (ClipboardToast) obj, clipboardManager, uiEventLogger, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardListener m1725get() {
        return newInstance((Context) this.contextProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get(), this.clipboardOverlayControllerProvider, (ClipboardOverlayControllerLegacyFactory) this.overlayFactoryProvider.get(), this.clipboardToastProvider.get(), (ClipboardManager) this.clipboardManagerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}