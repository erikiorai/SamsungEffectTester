package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataFilter_Factory.class */
public final class MediaDataFilter_Factory implements Factory<MediaDataFilter> {
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<NotificationLockscreenUserManager> lockscreenUserManagerProvider;
    public final Provider<MediaUiEventLogger> loggerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public MediaDataFilter_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<BroadcastSender> provider3, Provider<NotificationLockscreenUserManager> provider4, Provider<Executor> provider5, Provider<SystemClock> provider6, Provider<MediaUiEventLogger> provider7) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.broadcastSenderProvider = provider3;
        this.lockscreenUserManagerProvider = provider4;
        this.executorProvider = provider5;
        this.systemClockProvider = provider6;
        this.loggerProvider = provider7;
    }

    public static MediaDataFilter_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<BroadcastSender> provider3, Provider<NotificationLockscreenUserManager> provider4, Provider<Executor> provider5, Provider<SystemClock> provider6, Provider<MediaUiEventLogger> provider7) {
        return new MediaDataFilter_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static MediaDataFilter newInstance(Context context, UserTracker userTracker, BroadcastSender broadcastSender, NotificationLockscreenUserManager notificationLockscreenUserManager, Executor executor, SystemClock systemClock, MediaUiEventLogger mediaUiEventLogger) {
        return new MediaDataFilter(context, userTracker, broadcastSender, notificationLockscreenUserManager, executor, systemClock, mediaUiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaDataFilter m3189get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (NotificationLockscreenUserManager) this.lockscreenUserManagerProvider.get(), (Executor) this.executorProvider.get(), (SystemClock) this.systemClockProvider.get(), (MediaUiEventLogger) this.loggerProvider.get());
    }
}