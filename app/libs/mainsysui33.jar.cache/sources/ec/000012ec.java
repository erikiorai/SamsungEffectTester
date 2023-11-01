package com.android.systemui.classifier;

import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/ProximityClassifier_Factory.class */
public final class ProximityClassifier_Factory implements Factory<ProximityClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;
    public final Provider<DistanceClassifier> distanceClassifierProvider;

    public ProximityClassifier_Factory(Provider<DistanceClassifier> provider, Provider<FalsingDataProvider> provider2, Provider<DeviceConfigProxy> provider3) {
        this.distanceClassifierProvider = provider;
        this.dataProvider = provider2;
        this.deviceConfigProxyProvider = provider3;
    }

    public static ProximityClassifier_Factory create(Provider<DistanceClassifier> provider, Provider<FalsingDataProvider> provider2, Provider<DeviceConfigProxy> provider3) {
        return new ProximityClassifier_Factory(provider, provider2, provider3);
    }

    public static ProximityClassifier newInstance(Object obj, FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        return new ProximityClassifier((DistanceClassifier) obj, falsingDataProvider, deviceConfigProxy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ProximityClassifier m1720get() {
        return newInstance(this.distanceClassifierProvider.get(), (FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get());
    }
}