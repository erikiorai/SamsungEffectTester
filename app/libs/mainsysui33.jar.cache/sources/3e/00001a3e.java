package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.Position;
import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardBottomAreaInteractor.class */
public final class KeyguardBottomAreaInteractor {
    public final Flow<Float> alpha;
    public final Flow<Boolean> animateDozingTransitions;
    public final Flow<Position> clockPosition;
    public final KeyguardRepository repository;

    public KeyguardBottomAreaInteractor(KeyguardRepository keyguardRepository) {
        this.repository = keyguardRepository;
        this.animateDozingTransitions = keyguardRepository.getAnimateBottomAreaDozingTransitions();
        this.alpha = keyguardRepository.getBottomAreaAlpha();
        this.clockPosition = keyguardRepository.getClockPosition();
    }

    public final Flow<Float> getAlpha() {
        return this.alpha;
    }

    public final Flow<Boolean> getAnimateDozingTransitions() {
        return this.animateDozingTransitions;
    }

    public final Flow<Position> getClockPosition() {
        return this.clockPosition;
    }

    public final void setAlpha(float f) {
        this.repository.setBottomAreaAlpha(f);
    }

    public final void setAnimateDozingTransitions(boolean z) {
        this.repository.setAnimateDozingTransitions(z);
    }

    public final void setClockPosition(int i, int i2) {
        this.repository.setClockPosition(i, i2);
    }

    public final boolean shouldConstrainToTopOfLockIcon() {
        return this.repository.isUdfpsSupported();
    }
}