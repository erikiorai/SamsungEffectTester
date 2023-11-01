package com.android.systemui.people;

import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import dagger.MembersInjector;

/* loaded from: mainsysui33.jar:com/android/systemui/people/PeopleProvider_MembersInjector.class */
public final class PeopleProvider_MembersInjector implements MembersInjector<PeopleProvider> {
    public static void injectMPeopleSpaceWidgetManager(PeopleProvider peopleProvider, PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        peopleProvider.mPeopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }
}