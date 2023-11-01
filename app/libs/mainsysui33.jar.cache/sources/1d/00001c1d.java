package com.android.systemui.media.controls.pipeline;

import android.app.smartspace.SmartspaceManager;
import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaDataProvider;
import com.android.systemui.media.controls.resume.MediaResumeListener;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataManager_Factory.class */
public final class MediaDataManager_Factory implements Factory<MediaDataManager> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<SystemClock> clockProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> foregroundExecutorProvider;
    public final Provider<MediaUiEventLogger> loggerProvider;
    public final Provider<MediaControllerFactory> mediaControllerFactoryProvider;
    public final Provider<MediaDataCombineLatest> mediaDataCombineLatestProvider;
    public final Provider<MediaDataFilter> mediaDataFilterProvider;
    public final Provider<MediaDeviceManager> mediaDeviceManagerProvider;
    public final Provider<MediaFlags> mediaFlagsProvider;
    public final Provider<MediaResumeListener> mediaResumeListenerProvider;
    public final Provider<MediaSessionBasedFilter> mediaSessionBasedFilterProvider;
    public final Provider<MediaTimeoutListener> mediaTimeoutListenerProvider;
    public final Provider<SmartspaceManager> smartspaceManagerProvider;
    public final Provider<SmartspaceMediaDataProvider> smartspaceMediaDataProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<Executor> uiExecutorProvider;

    public MediaDataManager_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<DelayableExecutor> provider4, Provider<MediaControllerFactory> provider5, Provider<DumpManager> provider6, Provider<BroadcastDispatcher> provider7, Provider<MediaTimeoutListener> provider8, Provider<MediaResumeListener> provider9, Provider<MediaSessionBasedFilter> provider10, Provider<MediaDeviceManager> provider11, Provider<MediaDataCombineLatest> provider12, Provider<MediaDataFilter> provider13, Provider<ActivityStarter> provider14, Provider<SmartspaceMediaDataProvider> provider15, Provider<SystemClock> provider16, Provider<TunerService> provider17, Provider<MediaFlags> provider18, Provider<MediaUiEventLogger> provider19, Provider<SmartspaceManager> provider20) {
        this.contextProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.uiExecutorProvider = provider3;
        this.foregroundExecutorProvider = provider4;
        this.mediaControllerFactoryProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.broadcastDispatcherProvider = provider7;
        this.mediaTimeoutListenerProvider = provider8;
        this.mediaResumeListenerProvider = provider9;
        this.mediaSessionBasedFilterProvider = provider10;
        this.mediaDeviceManagerProvider = provider11;
        this.mediaDataCombineLatestProvider = provider12;
        this.mediaDataFilterProvider = provider13;
        this.activityStarterProvider = provider14;
        this.smartspaceMediaDataProvider = provider15;
        this.clockProvider = provider16;
        this.tunerServiceProvider = provider17;
        this.mediaFlagsProvider = provider18;
        this.loggerProvider = provider19;
        this.smartspaceManagerProvider = provider20;
    }

    public static MediaDataManager_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Executor> provider3, Provider<DelayableExecutor> provider4, Provider<MediaControllerFactory> provider5, Provider<DumpManager> provider6, Provider<BroadcastDispatcher> provider7, Provider<MediaTimeoutListener> provider8, Provider<MediaResumeListener> provider9, Provider<MediaSessionBasedFilter> provider10, Provider<MediaDeviceManager> provider11, Provider<MediaDataCombineLatest> provider12, Provider<MediaDataFilter> provider13, Provider<ActivityStarter> provider14, Provider<SmartspaceMediaDataProvider> provider15, Provider<SystemClock> provider16, Provider<TunerService> provider17, Provider<MediaFlags> provider18, Provider<MediaUiEventLogger> provider19, Provider<SmartspaceManager> provider20) {
        return new MediaDataManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20);
    }

    public static MediaDataManager newInstance(Context context, Executor executor, Executor executor2, DelayableExecutor delayableExecutor, MediaControllerFactory mediaControllerFactory, DumpManager dumpManager, BroadcastDispatcher broadcastDispatcher, MediaTimeoutListener mediaTimeoutListener, MediaResumeListener mediaResumeListener, MediaSessionBasedFilter mediaSessionBasedFilter, MediaDeviceManager mediaDeviceManager, MediaDataCombineLatest mediaDataCombineLatest, MediaDataFilter mediaDataFilter, ActivityStarter activityStarter, SmartspaceMediaDataProvider smartspaceMediaDataProvider, SystemClock systemClock, TunerService tunerService, MediaFlags mediaFlags, MediaUiEventLogger mediaUiEventLogger, SmartspaceManager smartspaceManager) {
        return new MediaDataManager(context, executor, executor2, delayableExecutor, mediaControllerFactory, dumpManager, broadcastDispatcher, mediaTimeoutListener, mediaResumeListener, mediaSessionBasedFilter, mediaDeviceManager, mediaDataCombineLatest, mediaDataFilter, activityStarter, smartspaceMediaDataProvider, systemClock, tunerService, mediaFlags, mediaUiEventLogger, smartspaceManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaDataManager m3194get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Executor) this.uiExecutorProvider.get(), (DelayableExecutor) this.foregroundExecutorProvider.get(), (MediaControllerFactory) this.mediaControllerFactoryProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (MediaTimeoutListener) this.mediaTimeoutListenerProvider.get(), (MediaResumeListener) this.mediaResumeListenerProvider.get(), (MediaSessionBasedFilter) this.mediaSessionBasedFilterProvider.get(), (MediaDeviceManager) this.mediaDeviceManagerProvider.get(), (MediaDataCombineLatest) this.mediaDataCombineLatestProvider.get(), (MediaDataFilter) this.mediaDataFilterProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (SmartspaceMediaDataProvider) this.smartspaceMediaDataProvider.get(), (SystemClock) this.clockProvider.get(), (TunerService) this.tunerServiceProvider.get(), (MediaFlags) this.mediaFlagsProvider.get(), (MediaUiEventLogger) this.loggerProvider.get(), (SmartspaceManager) this.smartspaceManagerProvider.get());
    }
}