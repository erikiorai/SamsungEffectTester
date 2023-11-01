package com.android.systemui.dreams.complication;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.dreams.DreamOverlayStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationHostViewController_Factory.class */
public final class ComplicationHostViewController_Factory implements Factory<ComplicationHostViewController> {
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<ComplicationLayoutEngine> layoutEngineProvider;
    public final Provider<LifecycleOwner> lifecycleOwnerProvider;
    public final Provider<ComplicationCollectionViewModel> viewModelProvider;
    public final Provider<ConstraintLayout> viewProvider;

    public ComplicationHostViewController_Factory(Provider<ConstraintLayout> provider, Provider<ComplicationLayoutEngine> provider2, Provider<DreamOverlayStateController> provider3, Provider<LifecycleOwner> provider4, Provider<ComplicationCollectionViewModel> provider5) {
        this.viewProvider = provider;
        this.layoutEngineProvider = provider2;
        this.dreamOverlayStateControllerProvider = provider3;
        this.lifecycleOwnerProvider = provider4;
        this.viewModelProvider = provider5;
    }

    public static ComplicationHostViewController_Factory create(Provider<ConstraintLayout> provider, Provider<ComplicationLayoutEngine> provider2, Provider<DreamOverlayStateController> provider3, Provider<LifecycleOwner> provider4, Provider<ComplicationCollectionViewModel> provider5) {
        return new ComplicationHostViewController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ComplicationHostViewController newInstance(ConstraintLayout constraintLayout, ComplicationLayoutEngine complicationLayoutEngine, DreamOverlayStateController dreamOverlayStateController, LifecycleOwner lifecycleOwner, ComplicationCollectionViewModel complicationCollectionViewModel) {
        return new ComplicationHostViewController(constraintLayout, complicationLayoutEngine, dreamOverlayStateController, lifecycleOwner, complicationCollectionViewModel);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComplicationHostViewController m2582get() {
        return newInstance((ConstraintLayout) this.viewProvider.get(), (ComplicationLayoutEngine) this.layoutEngineProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), (LifecycleOwner) this.lifecycleOwnerProvider.get(), (ComplicationCollectionViewModel) this.viewModelProvider.get());
    }
}