package com.android.systemui.people.widget;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/people/widget/PeopleSpaceWidgetPinnedReceiver_Factory.class */
public final class PeopleSpaceWidgetPinnedReceiver_Factory implements Factory<PeopleSpaceWidgetPinnedReceiver> {
    public final Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;

    public PeopleSpaceWidgetPinnedReceiver_Factory(Provider<PeopleSpaceWidgetManager> provider) {
        this.peopleSpaceWidgetManagerProvider = provider;
    }

    public static PeopleSpaceWidgetPinnedReceiver_Factory create(Provider<PeopleSpaceWidgetManager> provider) {
        return new PeopleSpaceWidgetPinnedReceiver_Factory(provider);
    }

    public static PeopleSpaceWidgetPinnedReceiver newInstance(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        return new PeopleSpaceWidgetPinnedReceiver(peopleSpaceWidgetManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PeopleSpaceWidgetPinnedReceiver m3572get() {
        return newInstance((PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
    }
}