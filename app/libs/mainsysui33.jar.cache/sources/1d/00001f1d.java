package com.android.systemui.people.data.repository;

import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/repository/PeopleWidgetRepositoryImpl_Factory.class */
public final class PeopleWidgetRepositoryImpl_Factory implements Factory<PeopleWidgetRepositoryImpl> {
    public final Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;

    public PeopleWidgetRepositoryImpl_Factory(Provider<PeopleSpaceWidgetManager> provider) {
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public static PeopleWidgetRepositoryImpl_Factory create(Provider<PeopleSpaceWidgetManager> provider) {
        return new PeopleWidgetRepositoryImpl_Factory(provider);
    }

    public static PeopleWidgetRepositoryImpl newInstance(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        return new PeopleWidgetRepositoryImpl(peopleSpaceWidgetManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleWidgetRepositoryImpl m3547get() {
        return newInstance((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
    }
}