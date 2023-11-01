package com.android.systemui.dreams.complication.dagger;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/DaggerViewModelProviderFactory.class */
public class DaggerViewModelProviderFactory implements ViewModelProvider.Factory {
    public final ViewModelCreator mCreator;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/DaggerViewModelProviderFactory$ViewModelCreator.class */
    public interface ViewModelCreator {
        ViewModel create();
    }

    public DaggerViewModelProviderFactory(ViewModelCreator viewModelCreator) {
        this.mCreator = viewModelCreator;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> cls) {
        return (T) this.mCreator.create();
    }
}