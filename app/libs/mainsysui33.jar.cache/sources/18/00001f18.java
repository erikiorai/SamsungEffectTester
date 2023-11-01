package com.android.systemui.people.data.repository;

import com.android.systemui.people.data.model.PeopleTileModel;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/repository/PeopleTileRepository.class */
public interface PeopleTileRepository {
    List<PeopleTileModel> priorityTiles();

    List<PeopleTileModel> recentTiles();
}