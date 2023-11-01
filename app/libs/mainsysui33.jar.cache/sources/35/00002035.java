package com.android.systemui.privacy;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyConfig_Factory.class */
public final class PrivacyConfig_Factory implements Factory<PrivacyConfig> {
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> uiExecutorProvider;

    public PrivacyConfig_Factory(Provider<DelayableExecutor> provider, Provider<DeviceConfigProxy> provider2, Provider<DumpManager> provider3) {
        this.uiExecutorProvider = provider;
        this.deviceConfigProxyProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static PrivacyConfig_Factory create(Provider<DelayableExecutor> provider, Provider<DeviceConfigProxy> provider2, Provider<DumpManager> provider3) {
        return new PrivacyConfig_Factory(provider, provider2, provider3);
    }

    public static PrivacyConfig newInstance(DelayableExecutor delayableExecutor, DeviceConfigProxy deviceConfigProxy, DumpManager dumpManager) {
        return new PrivacyConfig(delayableExecutor, deviceConfigProxy, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PrivacyConfig m3670get() {
        return newInstance((DelayableExecutor) this.uiExecutorProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}