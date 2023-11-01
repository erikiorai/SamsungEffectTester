package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.footer.data.model.UserSwitcherStatusModel;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/UserSwitcherRepository.class */
public interface UserSwitcherRepository {
    Flow<UserSwitcherStatusModel> getUserSwitcherStatus();
}