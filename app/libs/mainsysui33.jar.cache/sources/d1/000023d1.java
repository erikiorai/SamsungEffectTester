package com.android.systemui.screenrecord;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/RecordingController_Factory.class */
public final class RecordingController_Factory implements Factory<RecordingController> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UserContextProvider> userContextProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public RecordingController_Factory(Provider<Executor> provider, Provider<BroadcastDispatcher> provider2, Provider<UserContextProvider> provider3, Provider<UserTracker> provider4) {
        this.mainExecutorProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.userContextProvider = provider3;
        this.userTrackerProvider = provider4;
    }

    public static RecordingController_Factory create(Provider<Executor> provider, Provider<BroadcastDispatcher> provider2, Provider<UserContextProvider> provider3, Provider<UserTracker> provider4) {
        return new RecordingController_Factory(provider, provider2, provider3, provider4);
    }

    public static RecordingController newInstance(Executor executor, BroadcastDispatcher broadcastDispatcher, UserContextProvider userContextProvider, UserTracker userTracker) {
        return new RecordingController(executor, broadcastDispatcher, userContextProvider, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RecordingController m4177get() {
        return newInstance((Executor) this.mainExecutorProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserContextProvider) this.userContextProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}