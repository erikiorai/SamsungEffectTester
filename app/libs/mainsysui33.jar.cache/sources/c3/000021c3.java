package com.android.systemui.qs.external;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.UserHandle;
import android.service.quicksettings.IQSService;
import com.android.systemui.broadcast.BroadcastDispatcher;
import javax.inject.Provider;

/* renamed from: com.android.systemui.qs.external.TileLifecycleManager_Factory  reason: case insensitive filesystem */
/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileLifecycleManager_Factory.class */
public final class C0055TileLifecycleManager_Factory {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<PackageManagerAdapter> packageManagerAdapterProvider;
    public final Provider<IQSService> serviceProvider;

    public C0055TileLifecycleManager_Factory(Provider<Handler> provider, Provider<Context> provider2, Provider<IQSService> provider3, Provider<PackageManagerAdapter> provider4, Provider<BroadcastDispatcher> provider5) {
        this.handlerProvider = provider;
        this.contextProvider = provider2;
        this.serviceProvider = provider3;
        this.packageManagerAdapterProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
    }

    public static C0055TileLifecycleManager_Factory create(Provider<Handler> provider, Provider<Context> provider2, Provider<IQSService> provider3, Provider<PackageManagerAdapter> provider4, Provider<BroadcastDispatcher> provider5) {
        return new C0055TileLifecycleManager_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static TileLifecycleManager newInstance(Handler handler, Context context, IQSService iQSService, PackageManagerAdapter packageManagerAdapter, BroadcastDispatcher broadcastDispatcher, Intent intent, UserHandle userHandle) {
        return new TileLifecycleManager(handler, context, iQSService, packageManagerAdapter, broadcastDispatcher, intent, userHandle);
    }

    public TileLifecycleManager get(Intent intent, UserHandle userHandle) {
        return newInstance((Handler) this.handlerProvider.get(), (Context) this.contextProvider.get(), (IQSService) this.serviceProvider.get(), (PackageManagerAdapter) this.packageManagerAdapterProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), intent, userHandle);
    }
}