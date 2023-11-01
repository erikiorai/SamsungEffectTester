package com.android.systemui.power.data.repository;

import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/power/data/repository/PowerRepository.class */
public interface PowerRepository {
    Flow<Boolean> isInteractive();
}