package com.android.systemui;

import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/LatencyTester_Factory.class */
public final class LatencyTester_Factory implements Factory<LatencyTester> {
    public final Provider<BiometricUnlockController> biometricUnlockControllerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;

    public LatencyTester_Factory(Provider<BiometricUnlockController> provider, Provider<BroadcastDispatcher> provider2, Provider<DeviceConfigProxy> provider3, Provider<DelayableExecutor> provider4) {
        this.biometricUnlockControllerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.deviceConfigProxyProvider = provider3;
        this.mainExecutorProvider = provider4;
    }

    public static LatencyTester_Factory create(Provider<BiometricUnlockController> provider, Provider<BroadcastDispatcher> provider2, Provider<DeviceConfigProxy> provider3, Provider<DelayableExecutor> provider4) {
        return new LatencyTester_Factory(provider, provider2, provider3, provider4);
    }

    public static LatencyTester newInstance(BiometricUnlockController biometricUnlockController, BroadcastDispatcher broadcastDispatcher, DeviceConfigProxy deviceConfigProxy, DelayableExecutor delayableExecutor) {
        return new LatencyTester(biometricUnlockController, broadcastDispatcher, deviceConfigProxy, delayableExecutor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LatencyTester m1281get() {
        return newInstance((BiometricUnlockController) this.biometricUnlockControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get());
    }
}