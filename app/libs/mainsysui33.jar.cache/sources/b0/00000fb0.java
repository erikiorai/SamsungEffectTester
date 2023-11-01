package com.android.systemui;

import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestResumeSessionReceiver_Factory.class */
public final class GuestResumeSessionReceiver_Factory implements Factory<GuestResumeSessionReceiver> {
    public final Provider<GuestSessionNotification> guestSessionNotificationProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<GuestResumeSessionReceiver.ResetSessionDialog.Factory> resetSessionDialogFactoryProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public GuestResumeSessionReceiver_Factory(Provider<Executor> provider, Provider<UserTracker> provider2, Provider<SecureSettings> provider3, Provider<GuestSessionNotification> provider4, Provider<GuestResumeSessionReceiver.ResetSessionDialog.Factory> provider5) {
        this.mainExecutorProvider = provider;
        this.userTrackerProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.guestSessionNotificationProvider = provider4;
        this.resetSessionDialogFactoryProvider = provider5;
    }

    public static GuestResumeSessionReceiver_Factory create(Provider<Executor> provider, Provider<UserTracker> provider2, Provider<SecureSettings> provider3, Provider<GuestSessionNotification> provider4, Provider<GuestResumeSessionReceiver.ResetSessionDialog.Factory> provider5) {
        return new GuestResumeSessionReceiver_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static GuestResumeSessionReceiver newInstance(Executor executor, UserTracker userTracker, SecureSettings secureSettings, GuestSessionNotification guestSessionNotification, GuestResumeSessionReceiver.ResetSessionDialog.Factory factory) {
        return new GuestResumeSessionReceiver(executor, userTracker, secureSettings, guestSessionNotification, factory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public GuestResumeSessionReceiver m1273get() {
        return newInstance((Executor) this.mainExecutorProvider.get(), (UserTracker) this.userTrackerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (GuestSessionNotification) this.guestSessionNotificationProvider.get(), (GuestResumeSessionReceiver.ResetSessionDialog.Factory) this.resetSessionDialogFactoryProvider.get());
    }
}