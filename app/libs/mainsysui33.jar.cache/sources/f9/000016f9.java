package com.android.systemui.dreams.complication;

import com.android.systemui.dreams.DreamOverlayStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationCollectionLiveData_Factory.class */
public final class ComplicationCollectionLiveData_Factory implements Factory<ComplicationCollectionLiveData> {
    public final Provider<DreamOverlayStateController> stateControllerProvider;

    public ComplicationCollectionLiveData_Factory(Provider<DreamOverlayStateController> provider) {
        this.stateControllerProvider = provider;
    }

    public static ComplicationCollectionLiveData_Factory create(Provider<DreamOverlayStateController> provider) {
        return new ComplicationCollectionLiveData_Factory(provider);
    }

    public static ComplicationCollectionLiveData newInstance(DreamOverlayStateController dreamOverlayStateController) {
        return new ComplicationCollectionLiveData(dreamOverlayStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComplicationCollectionLiveData m2575get() {
        return newInstance((DreamOverlayStateController) this.stateControllerProvider.get());
    }
}