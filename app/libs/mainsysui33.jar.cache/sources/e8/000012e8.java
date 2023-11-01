package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/LongTapClassifier_Factory.class */
public final class LongTapClassifier_Factory implements Factory<LongTapClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;
    public final Provider<Float> touchSlopProvider;

    public LongTapClassifier_Factory(Provider<FalsingDataProvider> provider, Provider<Float> provider2) {
        this.dataProvider = provider;
        this.touchSlopProvider = provider2;
    }

    public static LongTapClassifier_Factory create(Provider<FalsingDataProvider> provider, Provider<Float> provider2) {
        return new LongTapClassifier_Factory(provider, provider2);
    }

    public static LongTapClassifier newInstance(FalsingDataProvider falsingDataProvider, float f) {
        return new LongTapClassifier(falsingDataProvider, f);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LongTapClassifier m1718get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get(), ((Float) this.touchSlopProvider.get()).floatValue());
    }
}