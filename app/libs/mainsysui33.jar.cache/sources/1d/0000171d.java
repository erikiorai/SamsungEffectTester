package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationViewModelTransformer_Factory.class */
public final class ComplicationViewModelTransformer_Factory implements Factory<ComplicationViewModelTransformer> {
    public final Provider<ComplicationViewModelComponent.Factory> viewModelComponentFactoryProvider;

    public ComplicationViewModelTransformer_Factory(Provider<ComplicationViewModelComponent.Factory> provider) {
        this.viewModelComponentFactoryProvider = provider;
    }

    public static ComplicationViewModelTransformer_Factory create(Provider<ComplicationViewModelComponent.Factory> provider) {
        return new ComplicationViewModelTransformer_Factory(provider);
    }

    public static ComplicationViewModelTransformer newInstance(ComplicationViewModelComponent.Factory factory) {
        return new ComplicationViewModelTransformer(factory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComplicationViewModelTransformer m2593get() {
        return newInstance((ComplicationViewModelComponent.Factory) this.viewModelComponentFactoryProvider.get());
    }
}