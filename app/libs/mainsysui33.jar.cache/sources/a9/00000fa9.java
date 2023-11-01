package com.android.systemui;

import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResetOrExitSessionReceiver_Factory.class */
public final class GuestResetOrExitSessionReceiver_Factory implements Factory<GuestResetOrExitSessionReceiver> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> exitSessionDialogFactoryProvider;
    public final Provider<GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory> resetSessionDialogFactoryProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public GuestResetOrExitSessionReceiver_Factory(Provider<UserTracker> provider, Provider<BroadcastDispatcher> provider2, Provider<GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory> provider3, Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> provider4) {
        this.userTrackerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.resetSessionDialogFactoryProvider = provider3;
        this.exitSessionDialogFactoryProvider = provider4;
    }

    public static GuestResetOrExitSessionReceiver_Factory create(Provider<UserTracker> provider, Provider<BroadcastDispatcher> provider2, Provider<GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory> provider3, Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> provider4) {
        return new GuestResetOrExitSessionReceiver_Factory(provider, provider2, provider3, provider4);
    }

    public static GuestResetOrExitSessionReceiver newInstance(UserTracker userTracker, BroadcastDispatcher broadcastDispatcher, GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory factory, GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory factory2) {
        return new GuestResetOrExitSessionReceiver(userTracker, broadcastDispatcher, factory, factory2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public GuestResetOrExitSessionReceiver m1267get() {
        return newInstance((UserTracker) this.userTrackerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory) this.resetSessionDialogFactoryProvider.get(), (GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory) this.exitSessionDialogFactoryProvider.get());
    }
}