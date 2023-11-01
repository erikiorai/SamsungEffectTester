package com.android.systemui.flags;

import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReaderModule_BindsReaderFactory.class */
public final class ServerFlagReaderModule_BindsReaderFactory implements Factory<ServerFlagReader> {
    public final Provider<DeviceConfigProxy> deviceConfigProvider;
    public final Provider<Executor> executorProvider;

    public ServerFlagReaderModule_BindsReaderFactory(Provider<DeviceConfigProxy> provider, Provider<Executor> provider2) {
        this.deviceConfigProvider = provider;
        this.executorProvider = provider2;
    }

    public static ServerFlagReader bindsReader(DeviceConfigProxy deviceConfigProxy, Executor executor) {
        return (ServerFlagReader) Preconditions.checkNotNullFromProvides(ServerFlagReaderModule.bindsReader(deviceConfigProxy, executor));
    }

    public static ServerFlagReaderModule_BindsReaderFactory create(Provider<DeviceConfigProxy> provider, Provider<Executor> provider2) {
        return new ServerFlagReaderModule_BindsReaderFactory(provider, provider2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ServerFlagReader m2705get() {
        return bindsReader((DeviceConfigProxy) this.deviceConfigProvider.get(), (Executor) this.executorProvider.get());
    }
}