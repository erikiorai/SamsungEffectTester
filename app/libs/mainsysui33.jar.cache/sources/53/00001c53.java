package com.android.systemui.media.controls.resume;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/MediaResumeListener_Factory.class */
public final class MediaResumeListener_Factory implements Factory<MediaResumeListener> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<ResumeMediaBrowserFactory> mediaBrowserFactoryProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public MediaResumeListener_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<UserTracker> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<TunerService> provider6, Provider<ResumeMediaBrowserFactory> provider7, Provider<DumpManager> provider8, Provider<SystemClock> provider9) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.userTrackerProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.backgroundExecutorProvider = provider5;
        this.tunerServiceProvider = provider6;
        this.mediaBrowserFactoryProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.systemClockProvider = provider9;
    }

    public static MediaResumeListener_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<UserTracker> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<TunerService> provider6, Provider<ResumeMediaBrowserFactory> provider7, Provider<DumpManager> provider8, Provider<SystemClock> provider9) {
        return new MediaResumeListener_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static MediaResumeListener newInstance(Context context, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, Executor executor, Executor executor2, TunerService tunerService, ResumeMediaBrowserFactory resumeMediaBrowserFactory, DumpManager dumpManager, SystemClock systemClock) {
        return new MediaResumeListener(context, broadcastDispatcher, userTracker, executor, executor2, tunerService, resumeMediaBrowserFactory, dumpManager, systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaResumeListener m3211get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (TunerService) this.tunerServiceProvider.get(), (ResumeMediaBrowserFactory) this.mediaBrowserFactoryProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}