package com.android.systemui.dreams.complication.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.complication.dagger.DaggerViewModelProviderFactory;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationModule.class */
public interface ComplicationModule {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.dagger.ComplicationModule$$ExternalSyntheticLambda0.create():androidx.lifecycle.ViewModel] */
    /* renamed from: $r8$lambda$Z_8oOyJ_FwMNaoOTQUTc3-d1iKU */
    static /* synthetic */ ViewModel m2605$r8$lambda$Z_8oOyJ_FwMNaoOTQUTc3d1iKU(ComplicationCollectionViewModel complicationCollectionViewModel) {
        return lambda$providesComplicationCollectionViewModel$0(complicationCollectionViewModel);
    }

    static /* synthetic */ ViewModel lambda$providesComplicationCollectionViewModel$0(ComplicationCollectionViewModel complicationCollectionViewModel) {
        return complicationCollectionViewModel;
    }

    static ComplicationCollectionViewModel providesComplicationCollectionViewModel(ViewModelStore viewModelStore, final ComplicationCollectionViewModel complicationCollectionViewModel) {
        return (ComplicationCollectionViewModel) new ViewModelProvider(viewModelStore, new DaggerViewModelProviderFactory(new DaggerViewModelProviderFactory.ViewModelCreator() { // from class: com.android.systemui.dreams.complication.dagger.ComplicationModule$$ExternalSyntheticLambda0
            @Override // com.android.systemui.dreams.complication.dagger.DaggerViewModelProviderFactory.ViewModelCreator
            public final ViewModel create() {
                return ComplicationModule.m2605$r8$lambda$Z_8oOyJ_FwMNaoOTQUTc3d1iKU(ComplicationCollectionViewModel.this);
            }
        })).get(ComplicationCollectionViewModel.class);
    }

    static Complication.VisibilityController providesVisibilityController(ComplicationLayoutEngine complicationLayoutEngine) {
        return complicationLayoutEngine;
    }
}