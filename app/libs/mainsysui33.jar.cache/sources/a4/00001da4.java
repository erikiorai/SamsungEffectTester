package com.android.systemui.media.taptotransfer.receiver;

import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/MediaTttChipControllerReceiver_Factory.class */
public final class MediaTttChipControllerReceiver_Factory implements Factory<MediaTttChipControllerReceiver> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<MediaTttLogger<ChipReceiverInfo>> loggerProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MediaTttFlags> mediaTttFlagsProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<MediaTttReceiverUiEventLogger> uiEventLoggerProvider;
    public final Provider<ViewUtil> viewUtilProvider;
    public final Provider<WakeLock.Builder> wakeLockBuilderProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public MediaTttChipControllerReceiver_Factory(Provider<CommandQueue> provider, Provider<Context> provider2, Provider<MediaTttLogger<ChipReceiverInfo>> provider3, Provider<WindowManager> provider4, Provider<DelayableExecutor> provider5, Provider<AccessibilityManager> provider6, Provider<ConfigurationController> provider7, Provider<DumpManager> provider8, Provider<PowerManager> provider9, Provider<Handler> provider10, Provider<MediaTttFlags> provider11, Provider<MediaTttReceiverUiEventLogger> provider12, Provider<ViewUtil> provider13, Provider<WakeLock.Builder> provider14, Provider<SystemClock> provider15) {
        this.commandQueueProvider = provider;
        this.contextProvider = provider2;
        this.loggerProvider = provider3;
        this.windowManagerProvider = provider4;
        this.mainExecutorProvider = provider5;
        this.accessibilityManagerProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.powerManagerProvider = provider9;
        this.mainHandlerProvider = provider10;
        this.mediaTttFlagsProvider = provider11;
        this.uiEventLoggerProvider = provider12;
        this.viewUtilProvider = provider13;
        this.wakeLockBuilderProvider = provider14;
        this.systemClockProvider = provider15;
    }

    public static MediaTttChipControllerReceiver_Factory create(Provider<CommandQueue> provider, Provider<Context> provider2, Provider<MediaTttLogger<ChipReceiverInfo>> provider3, Provider<WindowManager> provider4, Provider<DelayableExecutor> provider5, Provider<AccessibilityManager> provider6, Provider<ConfigurationController> provider7, Provider<DumpManager> provider8, Provider<PowerManager> provider9, Provider<Handler> provider10, Provider<MediaTttFlags> provider11, Provider<MediaTttReceiverUiEventLogger> provider12, Provider<ViewUtil> provider13, Provider<WakeLock.Builder> provider14, Provider<SystemClock> provider15) {
        return new MediaTttChipControllerReceiver_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static MediaTttChipControllerReceiver newInstance(CommandQueue commandQueue, Context context, MediaTttLogger<ChipReceiverInfo> mediaTttLogger, WindowManager windowManager, DelayableExecutor delayableExecutor, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DumpManager dumpManager, PowerManager powerManager, Handler handler, MediaTttFlags mediaTttFlags, MediaTttReceiverUiEventLogger mediaTttReceiverUiEventLogger, ViewUtil viewUtil, WakeLock.Builder builder, SystemClock systemClock) {
        return new MediaTttChipControllerReceiver(commandQueue, context, mediaTttLogger, windowManager, delayableExecutor, accessibilityManager, configurationController, dumpManager, powerManager, handler, mediaTttFlags, mediaTttReceiverUiEventLogger, viewUtil, builder, systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttChipControllerReceiver m3356get() {
        return newInstance((CommandQueue) this.commandQueueProvider.get(), (Context) this.contextProvider.get(), (MediaTttLogger) this.loggerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (Handler) this.mainHandlerProvider.get(), (MediaTttFlags) this.mediaTttFlagsProvider.get(), (MediaTttReceiverUiEventLogger) this.uiEventLoggerProvider.get(), (ViewUtil) this.viewUtilProvider.get(), (WakeLock.Builder) this.wakeLockBuilderProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}