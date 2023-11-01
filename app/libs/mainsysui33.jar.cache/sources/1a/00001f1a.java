package com.android.systemui.people.data.repository;

import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/repository/PeopleTileRepositoryImpl_Factory.class */
public final class PeopleTileRepositoryImpl_Factory implements Factory<PeopleTileRepositoryImpl> {
    public final Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;

    public PeopleTileRepositoryImpl_Factory(Provider<PeopleSpaceWidgetManager> provider) {
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public static PeopleTileRepositoryImpl_Factory create(Provider<PeopleSpaceWidgetManager> provider) {
        return new PeopleTileRepositoryImpl_Factory(provider);
    }

    public static PeopleTileRepositoryImpl newInstance(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        return new PeopleTileRepositoryImpl(peopleSpaceWidgetManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleTileRepositoryImpl m3546get() {
        return newInstance((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
    }
}