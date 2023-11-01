package com.android.systemui.people.ui.viewmodel;

import android.content.Context;
import com.android.systemui.people.data.repository.PeopleTileRepository;
import com.android.systemui.people.data.repository.PeopleWidgetRepository;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleViewModel_Factory_Factory.class */
public final class PeopleViewModel_Factory_Factory implements Factory<PeopleViewModel.Factory> {
    public final Provider<Context> contextProvider;
    public final Provider<PeopleTileRepository> tileRepositoryProvider;
    public final Provider<PeopleWidgetRepository> widgetRepositoryProvider;

    public PeopleViewModel_Factory_Factory(Provider<Context> provider, Provider<PeopleTileRepository> provider2, Provider<PeopleWidgetRepository> provider3) {
        this.contextProvider = provider;
        this.tileRepositoryProvider = provider2;
        this.widgetRepositoryProvider = provider3;
    }

    public static PeopleViewModel_Factory_Factory create(Provider<Context> provider, Provider<PeopleTileRepository> provider2, Provider<PeopleWidgetRepository> provider3) {
        return new PeopleViewModel_Factory_Factory(provider, provider2, provider3);
    }

    public static PeopleViewModel.Factory newInstance(Context context, PeopleTileRepository peopleTileRepository, PeopleWidgetRepository peopleWidgetRepository) {
        return new PeopleViewModel.Factory(context, peopleTileRepository, peopleWidgetRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleViewModel.Factory m3552get() {
        return newInstance((Context) this.contextProvider.get(), (PeopleTileRepository) this.tileRepositoryProvider.get(), (PeopleWidgetRepository) this.widgetRepositoryProvider.get());
    }
}