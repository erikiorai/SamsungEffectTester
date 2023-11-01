package com.android.systemui.log.dagger;

import android.content.ContentResolver;
import android.os.Looper;
import com.android.systemui.plugins.log.LogcatEchoTracker;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideLogcatEchoTrackerFactory.class */
public final class LogModule_ProvideLogcatEchoTrackerFactory implements Factory<LogcatEchoTracker> {
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<Looper> looperProvider;

    public LogModule_ProvideLogcatEchoTrackerFactory(Provider<ContentResolver> provider, Provider<Looper> provider2) {
        this.contentResolverProvider = provider;
        this.looperProvider = provider2;
    }

    public static LogModule_ProvideLogcatEchoTrackerFactory create(Provider<ContentResolver> provider, Provider<Looper> provider2) {
        return new LogModule_ProvideLogcatEchoTrackerFactory(provider, provider2);
    }

    public static LogcatEchoTracker provideLogcatEchoTracker(ContentResolver contentResolver, Looper looper) {
        return (LogcatEchoTracker) Preconditions.checkNotNullFromProvides(LogModule.provideLogcatEchoTracker(contentResolver, looper));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogcatEchoTracker m3112get() {
        return provideLogcatEchoTracker((ContentResolver) this.contentResolverProvider.get(), (Looper) this.looperProvider.get());
    }
}