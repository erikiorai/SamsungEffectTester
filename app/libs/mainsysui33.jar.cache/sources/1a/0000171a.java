package com.android.systemui.dreams.complication;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import com.android.systemui.dreams.complication.dagger.DaggerViewModelProviderFactory;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/ComplicationViewModelProvider.class */
public class ComplicationViewModelProvider extends ViewModelProvider {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.ComplicationViewModelProvider$$ExternalSyntheticLambda0.create():androidx.lifecycle.ViewModel] */
    public static /* synthetic */ ViewModel $r8$lambda$caeFEtopbg8_QKCOdaVOEUHfdsE(ComplicationViewModel complicationViewModel) {
        return lambda$new$0(complicationViewModel);
    }

    public ComplicationViewModelProvider(ViewModelStore viewModelStore, final ComplicationViewModel complicationViewModel) {
        super(viewModelStore, new DaggerViewModelProviderFactory(new DaggerViewModelProviderFactory.ViewModelCreator() { // from class: com.android.systemui.dreams.complication.ComplicationViewModelProvider$$ExternalSyntheticLambda0
            @Override // com.android.systemui.dreams.complication.dagger.DaggerViewModelProviderFactory.ViewModelCreator
            public final ViewModel create() {
                return ComplicationViewModelProvider.$r8$lambda$caeFEtopbg8_QKCOdaVOEUHfdsE(ComplicationViewModel.this);
            }
        }));
    }

    public static /* synthetic */ ViewModel lambda$new$0(ComplicationViewModel complicationViewModel) {
        return complicationViewModel;
    }
}