package com.android.systemui.people.data.repository;

import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/repository/PeopleWidgetRepositoryImpl.class */
public final class PeopleWidgetRepositoryImpl implements PeopleWidgetRepository {
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;

    public PeopleWidgetRepositoryImpl(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    @Override // com.android.systemui.people.data.repository.PeopleWidgetRepository
    public void setWidgetTile(int i, PeopleTileKey peopleTileKey) {
        this.peopleSpaceWidgetManager.addNewWidget(i, peopleTileKey);
    }
}