package com.android.systemui.security.data.repository;

import com.android.systemui.security.data.model.SecurityModel;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepository.class */
public interface SecurityRepository {
    Flow<SecurityModel> getSecurity();
}