package com.android.systemui.clipboardoverlay;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.screenshot.TimeoutHandler;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayController_Factory.class */
public final class ClipboardOverlayController_Factory implements Factory<ClipboardOverlayController> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<ClipboardOverlayView> clipboardOverlayViewProvider;
    public final Provider<ClipboardOverlayWindow> clipboardOverlayWindowProvider;
    public final Provider<ClipboardOverlayUtils> clipboardUtilsProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<TimeoutHandler> timeoutHandlerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public ClipboardOverlayController_Factory(Provider<Context> provider, Provider<ClipboardOverlayView> provider2, Provider<ClipboardOverlayWindow> provider3, Provider<BroadcastDispatcher> provider4, Provider<BroadcastSender> provider5, Provider<TimeoutHandler> provider6, Provider<FeatureFlags> provider7, Provider<ClipboardOverlayUtils> provider8, Provider<Executor> provider9, Provider<UiEventLogger> provider10) {
        this.contextProvider = provider;
        this.clipboardOverlayViewProvider = provider2;
        this.clipboardOverlayWindowProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.broadcastSenderProvider = provider5;
        this.timeoutHandlerProvider = provider6;
        this.featureFlagsProvider = provider7;
        this.clipboardUtilsProvider = provider8;
        this.bgExecutorProvider = provider9;
        this.uiEventLoggerProvider = provider10;
    }

    public static ClipboardOverlayController_Factory create(Provider<Context> provider, Provider<ClipboardOverlayView> provider2, Provider<ClipboardOverlayWindow> provider3, Provider<BroadcastDispatcher> provider4, Provider<BroadcastSender> provider5, Provider<TimeoutHandler> provider6, Provider<FeatureFlags> provider7, Provider<ClipboardOverlayUtils> provider8, Provider<Executor> provider9, Provider<UiEventLogger> provider10) {
        return new ClipboardOverlayController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static ClipboardOverlayController newInstance(Context context, ClipboardOverlayView clipboardOverlayView, ClipboardOverlayWindow clipboardOverlayWindow, BroadcastDispatcher broadcastDispatcher, BroadcastSender broadcastSender, TimeoutHandler timeoutHandler, FeatureFlags featureFlags, Object obj, Executor executor, UiEventLogger uiEventLogger) {
        return new ClipboardOverlayController(context, clipboardOverlayView, clipboardOverlayWindow, broadcastDispatcher, broadcastSender, timeoutHandler, featureFlags, (ClipboardOverlayUtils) obj, executor, uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardOverlayController m1763get() {
        return newInstance((Context) this.contextProvider.get(), (ClipboardOverlayView) this.clipboardOverlayViewProvider.get(), (ClipboardOverlayWindow) this.clipboardOverlayWindowProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (TimeoutHandler) this.timeoutHandlerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), this.clipboardUtilsProvider.get(), (Executor) this.bgExecutorProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
    }
}