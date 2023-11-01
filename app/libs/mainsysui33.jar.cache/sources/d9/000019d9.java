package com.android.systemui.keyguard.data.repository;

import com.android.systemui.statusbar.LightRevealEffect;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepository.class */
public interface LightRevealScrimRepository {
    Flow<LightRevealEffect> getRevealEffect();
}