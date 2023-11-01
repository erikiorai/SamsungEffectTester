package com.android.systemui.clipboardoverlay;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayControllerLegacyFactory_Factory.class */
public final class ClipboardOverlayControllerLegacyFactory_Factory implements Factory<ClipboardOverlayControllerLegacyFactory> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public ClipboardOverlayControllerLegacyFactory_Factory(Provider<BroadcastDispatcher> provider, Provider<BroadcastSender> provider2, Provider<UiEventLogger> provider3) {
        this.broadcastDispatcherProvider = provider;
        this.broadcastSenderProvider = provider2;
        this.uiEventLoggerProvider = provider3;
    }

    public static ClipboardOverlayControllerLegacyFactory_Factory create(Provider<BroadcastDispatcher> provider, Provider<BroadcastSender> provider2, Provider<UiEventLogger> provider3) {
        return new ClipboardOverlayControllerLegacyFactory_Factory(provider, provider2, provider3);
    }

    public static ClipboardOverlayControllerLegacyFactory newInstance(BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, UiEventLogger uiEventLogger) {
        return new ClipboardOverlayControllerLegacyFactory(broadcastDispatcher, broadcastSender, uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardOverlayControllerLegacyFactory m1762get() {
        return newInstance((BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
    }
}