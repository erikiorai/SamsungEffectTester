package com.android.systemui.qs.footer.data.repository;

import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepository.class */
public interface ForegroundServicesRepository {
    Flow<Integer> getForegroundServicesCount();

    Flow<Boolean> getHasNewChanges();
}