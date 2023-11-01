package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/DoubleTapClassifier_Factory.class */
public final class DoubleTapClassifier_Factory implements Factory<DoubleTapClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<Float> doubleTapSlopProvider;
    public final Provider<Long> doubleTapTimeMsProvider;
    public final Provider<SingleTapClassifier> singleTapClassifierProvider;

    public DoubleTapClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<SingleTapClassifier> provider2, Provider<Float> provider3, Provider<Long> provider4) {
        this.dataProvider = provider;
        this.singleTapClassifierProvider = provider2;
        this.doubleTapSlopProvider = provider3;
        this.doubleTapTimeMsProvider = provider4;
    }

    public static DoubleTapClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<SingleTapClassifier> provider2, Provider<Float> provider3, Provider<Long> provider4) {
        return new DoubleTapClassifier_Factory(provider, provider2, provider3, provider4);
    }

    public static DoubleTapClassifier newInstance(FalsingDataProvider falsingDataProvider, SingleTapClassifier singleTapClassifier, float f, long j) {
        return new DoubleTapClassifier(falsingDataProvider, singleTapClassifier, f, j);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DoubleTapClassifier m1687get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), (SingleTapClassifier) this.singleTapClassifierProvider.get(), ((Float) this.doubleTapSlopProvider.get()).floatValue(), ((Long) this.doubleTapTimeMsProvider.get()).longValue());
    }
}