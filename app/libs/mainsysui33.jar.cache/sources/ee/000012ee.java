package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/SingleTapClassifier_Factory.class */
public final class SingleTapClassifier_Factory implements Factory<SingleTapClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<Float> touchSlopProvider;

    public SingleTapClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<Float> provider2) {
        this.dataProvider = provider;
        this.touchSlopProvider = provider2;
    }

    public static SingleTapClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<Float> provider2) {
        return new SingleTapClassifier_Factory(provider, provider2);
    }

    public static SingleTapClassifier newInstance(FalsingDataProvider falsingDataProvider, float f) {
        return new SingleTapClassifier(falsingDataProvider, f);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SingleTapClassifier m1721get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), ((Float) this.touchSlopProvider.get()).floatValue());
    }
}