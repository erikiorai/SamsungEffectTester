package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaSessionBasedFilter_Factory.class */
public final class MediaSessionBasedFilter_Factory implements Factory<MediaSessionBasedFilter> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Executor> foregroundExecutorProvider;
    public final Provider<MediaSessionManager> sessionManagerProvider;

    public MediaSessionBasedFilter_Factory(Provider<Context> provider, Provider<MediaSessionManager> provider2, Provider<Executor> provider3, Provider<Executor> provider4) {
        this.contextProvider = provider;
        this.sessionManagerProvider = provider2;
        this.foregroundExecutorProvider = provider3;
        this.backgroundExecutorProvider = provider4;
    }

    public static MediaSessionBasedFilter_Factory create(Provider<Context> provider, Provider<MediaSessionManager> provider2, Provider<Executor> provider3, Provider<Executor> provider4) {
        return new MediaSessionBasedFilter_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaSessionBasedFilter newInstance(Context context, MediaSessionManager mediaSessionManager, Executor executor, Executor executor2) {
        return new MediaSessionBasedFilter(context, mediaSessionManager, executor, executor2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaSessionBasedFilter m3196get() {
        return newInstance((Context) this.contextProvider.get(), (MediaSessionManager) this.sessionManagerProvider.get(), (Executor) this.foregroundExecutorProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}