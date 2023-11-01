package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/PointerCountClassifier_Factory.class */
public final class PointerCountClassifier_Factory implements Factory<PointerCountClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;

    public PointerCountClassifier_Factory(Provider<FalsingDataProvider> provider) {
        this.dataProvider = provider;
    }

    public static PointerCountClassifier_Factory create(Provider<FalsingDataProvider> provider) {
        return new PointerCountClassifier_Factory(provider);
    }

    public static PointerCountClassifier newInstance(FalsingDataProvider falsingDataProvider) {
        return new PointerCountClassifier(falsingDataProvider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PointerCountClassifier m1719get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get());
    }
}