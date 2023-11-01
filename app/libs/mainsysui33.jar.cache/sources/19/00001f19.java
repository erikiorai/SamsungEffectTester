package com.android.systemui.people.data.repository;

import android.app.people.PeopleSpaceTile;
import com.android.systemui.people.PeopleTileViewHelper;
import com.android.systemui.people.data.model.PeopleTileModel;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleTileKey;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/repository/PeopleTileRepositoryImpl.class */
public final class PeopleTileRepositoryImpl implements PeopleTileRepository {
    public final PeopleSpaceWidgetManager peopleSpaceWidgetManager;

    public PeopleTileRepositoryImpl(PeopleSpaceWidgetManager peopleSpaceWidgetManager) {
        this.peopleSpaceWidgetManager = peopleSpaceWidgetManager;
    }

    @Override // com.android.systemui.people.data.repository.PeopleTileRepository
    public List<PeopleTileModel> priorityTiles() {
        List<PeopleSpaceTile> priorityTiles = this.peopleSpaceWidgetManager.getPriorityTiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(priorityTiles, 10));
        for (PeopleSpaceTile peopleSpaceTile : priorityTiles) {
            arrayList.add(toModel(peopleSpaceTile));
        }
        return arrayList;
    }

    @Override // com.android.systemui.people.data.repository.PeopleTileRepository
    public List<PeopleTileModel> recentTiles() {
        List<PeopleSpaceTile> recentTiles = this.peopleSpaceWidgetManager.getRecentTiles();
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(recentTiles, 10));
        for (PeopleSpaceTile peopleSpaceTile : recentTiles) {
            arrayList.add(toModel(peopleSpaceTile));
        }
        return arrayList;
    }

    public final PeopleTileModel toModel(PeopleSpaceTile peopleSpaceTile) {
        return new PeopleTileModel(new PeopleTileKey(peopleSpaceTile), peopleSpaceTile.getUserName().toString(), peopleSpaceTile.getUserIcon(), PeopleTileViewHelper.getHasNewStory(peopleSpaceTile), peopleSpaceTile.isImportantConversation(), PeopleTileViewHelper.isDndBlockingTileData(peopleSpaceTile));
    }
}