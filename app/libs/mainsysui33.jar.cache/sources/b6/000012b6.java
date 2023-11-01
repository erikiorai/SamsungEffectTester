package com.android.systemui.classifier;

import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/DistanceClassifier_Factory.class */
public final class DistanceClassifier_Factory implements Factory<DistanceClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;

    public DistanceClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        this.dataProvider = provider;
        this.deviceConfigProxyProvider = provider2;
    }

    public static DistanceClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        return new DistanceClassifier_Factory(provider, provider2);
    }

    public static DistanceClassifier newInstance(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        return new DistanceClassifier(falsingDataProvider, deviceConfigProxy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DistanceClassifier m1686get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get());
    }
}