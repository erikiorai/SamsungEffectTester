package com.android.systemui.power.domain.interactor;

import com.android.systemui.power.data.repository.PowerRepository;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/power/domain/interactor/PowerInteractor.class */
public final class PowerInteractor {
    public final Flow<Boolean> isInteractive;

    public PowerInteractor(PowerRepository powerRepository) {
        this.isInteractive = powerRepository.isInteractive();
    }

    public final Flow<Boolean> isInteractive() {
        return this.isInteractive;
    }
}