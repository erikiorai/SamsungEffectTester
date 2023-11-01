package com.android.systemui.people;

import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleSpaceActivity_Factory.class */
public final class PeopleSpaceActivity_Factory implements Factory<PeopleSpaceActivity> {
    public final Provider<PeopleViewModel.Factory> viewModelFactoryProvider;

    public PeopleSpaceActivity_Factory(Provider<PeopleViewModel.Factory> provider) {
        this.viewModelFactoryProvider = provider;
    }

    public static PeopleSpaceActivity_Factory create(Provider<PeopleViewModel.Factory> provider) {
        return new PeopleSpaceActivity_Factory(provider);
    }

    public static PeopleSpaceActivity newInstance(PeopleViewModel.Factory factory) {
        return new PeopleSpaceActivity(factory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleSpaceActivity m3531get() {
        return newInstance((PeopleViewModel.Factory) this.viewModelFactoryProvider.get());
    }
}