package com.android.systemui.classifier;

import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/ZigZagClassifier_Factory.class */
public final class ZigZagClassifier_Factory implements Factory<ZigZagClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;

    public ZigZagClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        this.dataProvider = provider;
        this.deviceConfigProxyProvider = provider2;
    }

    public static ZigZagClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        return new ZigZagClassifier_Factory(provider, provider2);
    }

    public static ZigZagClassifier newInstance(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        return new ZigZagClassifier(falsingDataProvider, deviceConfigProxy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ZigZagClassifier m1724get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get());
    }
}