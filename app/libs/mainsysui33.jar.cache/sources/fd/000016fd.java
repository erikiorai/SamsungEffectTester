package com.android.systemui.dreams.complication;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationCollectionViewModel_Factory.class */
public final class ComplicationCollectionViewModel_Factory implements Factory<ComplicationCollectionViewModel> {
    public final Provider<ComplicationCollectionLiveData> complicationsProvider;
    public final Provider<ComplicationViewModelTransformer> transformerProvider;

    public ComplicationCollectionViewModel_Factory(Provider<ComplicationCollectionLiveData> provider, Provider<ComplicationViewModelTransformer> provider2) {
        this.complicationsProvider = provider;
        this.transformerProvider = provider2;
    }

    public static ComplicationCollectionViewModel_Factory create(Provider<ComplicationCollectionLiveData> provider, Provider<ComplicationViewModelTransformer> provider2) {
        return new ComplicationCollectionViewModel_Factory(provider, provider2);
    }

    public static ComplicationCollectionViewModel newInstance(ComplicationCollectionLiveData complicationCollectionLiveData, ComplicationViewModelTransformer complicationViewModelTransformer) {
        return new ComplicationCollectionViewModel(complicationCollectionLiveData, complicationViewModelTransformer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComplicationCollectionViewModel m2576get() {
        return newInstance((ComplicationCollectionLiveData) this.complicationsProvider.get(), (ComplicationViewModelTransformer) this.transformerProvider.get());
    }
}