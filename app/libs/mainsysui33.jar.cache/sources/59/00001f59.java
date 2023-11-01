package com.android.systemui.people.widget;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetProvider_Factory.class */
public final class PeopleSpaceWidgetProvider_Factory implements Factory<PeopleSpaceWidgetProvider> {
    public final Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;

    public PeopleSpaceWidgetProvider_Factory(Provider<PeopleSpaceWidgetManager> provider) {
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public static PeopleSpaceWidgetProvider_Factory create(Provider<PeopleSpaceWidgetManager> provider) {
        return new PeopleSpaceWidgetProvider_Factory(provider);
    }

    public static PeopleSpaceWidgetProvider newInstance(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        return new PeopleSpaceWidgetProvider(peopleSpaceWidgetManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleSpaceWidgetProvider m3573get() {
        return newInstance((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
    }
}