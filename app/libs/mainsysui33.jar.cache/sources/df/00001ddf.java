package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.settings.UserTracker;
import com.android.wm.shell.recents.RecentTasks;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ShellRecentTaskListProvider_Factory.class */
public final class ShellRecentTaskListProvider_Factory implements Factory<ShellRecentTaskListProvider> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<CoroutineDispatcher> coroutineDispatcherProvider;
    public final Provider<Optional<RecentTasks>> recentTasksProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ShellRecentTaskListProvider_Factory(Provider<CoroutineDispatcher> provider, Provider<Executor> provider2, Provider<Optional<RecentTasks>> provider3, Provider<UserTracker> provider4) {
        this.coroutineDispatcherProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.recentTasksProvider = provider3;
        this.userTrackerProvider = provider4;
    }

    public static ShellRecentTaskListProvider_Factory create(Provider<CoroutineDispatcher> provider, Provider<Executor> provider2, Provider<Optional<RecentTasks>> provider3, Provider<UserTracker> provider4) {
        return new ShellRecentTaskListProvider_Factory(provider, provider2, provider3, provider4);
    }

    public static ShellRecentTaskListProvider newInstance(CoroutineDispatcher coroutineDispatcher, Executor executor, Optional<RecentTasks> optional, UserTracker userTracker) {
        return new ShellRecentTaskListProvider(coroutineDispatcher, executor, optional, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ShellRecentTaskListProvider m3375get() {
        return newInstance((CoroutineDispatcher) this.coroutineDispatcherProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Optional) this.recentTasksProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}