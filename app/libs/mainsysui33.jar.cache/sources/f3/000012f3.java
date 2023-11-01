package com.android.systemui.classifier;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/TypeClassifier_Factory.class */
public final class TypeClassifier_Factory implements Factory<TypeClassifier> {
    public final Provider<FalsingDataProvider> dataProvider;

    public TypeClassifier_Factory(Provider<FalsingDataProvider> provider) {
        this.dataProvider = provider;
    }

    public static TypeClassifier_Factory create(Provider<FalsingDataProvider> provider) {
        return new TypeClassifier_Factory(provider);
    }

    public static TypeClassifier newInstance(FalsingDataProvider falsingDataProvider) {
        return new TypeClassifier(falsingDataProvider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TypeClassifier m1723get() {
        return newInstance((FalsingDataProvider) this.dataProvider.get());
    }
}