package com.android.systemui.classifier;

import com.android.systemui.util.DeviceConfigProxy;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/DiagonalClassifier_Factory.class */
public final class DiagonalClassifier_Factory implements Factory<DiagonalClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProxyProvider;

    public DiagonalClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        this.dataProvider = provider;
        this.deviceConfigProxyProvider = provider2;
    }

    public static DiagonalClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<DeviceConfigProxy> provider2) {
        return new DiagonalClassifier_Factory(provider, provider2);
    }

    public static DiagonalClassifier newInstance(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        return new DiagonalClassifier(falsingDataProvider, deviceConfigProxy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DiagonalClassifier m1683get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), (DeviceConfigProxy) this.deviceConfigProxyProvider.get());
    }
}