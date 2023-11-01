package com.android.systemui.media.controls.resume;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/ResumeMediaBrowserFactory_Factory.class */
public final class ResumeMediaBrowserFactory_Factory implements Factory<ResumeMediaBrowserFactory> {
    public final Provider<MediaBrowserFactory> browserFactoryProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ResumeMediaBrowserLogger> loggerProvider;

    public ResumeMediaBrowserFactory_Factory(Provider<Context> provider, Provider<MediaBrowserFactory> provider2, Provider<ResumeMediaBrowserLogger> provider3) {
        this.contextProvider = provider;
        this.browserFactoryProvider = provider2;
        this.loggerProvider = provider3;
    }

    public static ResumeMediaBrowserFactory_Factory create(Provider<Context> provider, Provider<MediaBrowserFactory> provider2, Provider<ResumeMediaBrowserLogger> provider3) {
        return new ResumeMediaBrowserFactory_Factory(provider, provider2, provider3);
    }

    public static ResumeMediaBrowserFactory newInstance(Context context, MediaBrowserFactory mediaBrowserFactory, ResumeMediaBrowserLogger resumeMediaBrowserLogger) {
        return new ResumeMediaBrowserFactory(context, mediaBrowserFactory, resumeMediaBrowserLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ResumeMediaBrowserFactory m3219get() {
        return newInstance((Context) this.contextProvider.get(), (MediaBrowserFactory) this.browserFactoryProvider.get(), (ResumeMediaBrowserLogger) this.loggerProvider.get());
    }
}