package com.android.systemui.recents;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/ScreenPinningRequest_Factory.class */
public final class ScreenPinningRequest_Factory implements Factory<ScreenPinningRequest> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<Context> contextProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ScreenPinningRequest_Factory(Provider<Context> provider, Provider<Optional<CentralSurfaces>> provider2, Provider<NavigationModeController> provider3, Provider<BroadcastDispatcher> provider4, Provider<UserTracker> provider5) {
        this.contextProvider = provider;
        this.centralSurfacesOptionalLazyProvider = provider2;
        this.navigationModeControllerProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.userTrackerProvider = provider5;
    }

    public static ScreenPinningRequest_Factory create(Provider<Context> provider, Provider<Optional<CentralSurfaces>> provider2, Provider<NavigationModeController> provider3, Provider<BroadcastDispatcher> provider4, Provider<UserTracker> provider5) {
        return new ScreenPinningRequest_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ScreenPinningRequest newInstance(Context context, Lazy<Optional<CentralSurfaces>> lazy, NavigationModeController navigationModeController, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker) {
        return new ScreenPinningRequest(context, lazy, navigationModeController, broadcastDispatcher, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenPinningRequest m4168get() {
        return newInstance((Context) this.contextProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (NavigationModeController) this.navigationModeControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}