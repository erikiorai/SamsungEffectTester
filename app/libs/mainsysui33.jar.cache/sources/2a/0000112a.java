package com.android.systemui.assist;

import android.content.Context;
import com.android.systemui.BootCompleteCache;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/PhoneStateMonitor_Factory.class */
public final class PhoneStateMonitor_Factory implements Factory<PhoneStateMonitor> {
    public final Provider<BootCompleteCache> bootCompleteCacheProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<Context> contextProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public PhoneStateMonitor_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Optional<CentralSurfaces>> provider3, Provider<BootCompleteCache> provider4, Provider<StatusBarStateController> provider5) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.centralSurfacesOptionalLazyProvider = provider3;
        this.bootCompleteCacheProvider = provider4;
        this.statusBarStateControllerProvider = provider5;
    }

    public static PhoneStateMonitor_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<Optional<CentralSurfaces>> provider3, Provider<BootCompleteCache> provider4, Provider<StatusBarStateController> provider5) {
        return new PhoneStateMonitor_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static PhoneStateMonitor newInstance(Context context, BroadcastDispatcher broadcastDispatcher, Lazy<Optional<CentralSurfaces>> lazy, BootCompleteCache bootCompleteCache, StatusBarStateController statusBarStateController) {
        return new PhoneStateMonitor(context, broadcastDispatcher, lazy, bootCompleteCache, statusBarStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PhoneStateMonitor m1479get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (BootCompleteCache) this.bootCompleteCacheProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get());
    }
}