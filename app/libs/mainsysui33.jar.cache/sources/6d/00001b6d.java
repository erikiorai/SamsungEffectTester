package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import com.android.systemui.statusbar.LightRevealEffect;
import kotlinx.coroutines.flow.Flow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/LightRevealScrimViewModel.class */
public final class LightRevealScrimViewModel {
    public final Flow<LightRevealEffect> lightRevealEffect;
    public final Flow<Float> revealAmount;

    public LightRevealScrimViewModel(LightRevealScrimInteractor lightRevealScrimInteractor) {
        this.lightRevealEffect = lightRevealScrimInteractor.getLightRevealEffect();
        this.revealAmount = lightRevealScrimInteractor.getRevealAmount();
    }

    public final Flow<LightRevealEffect> getLightRevealEffect() {
        return this.lightRevealEffect;
    }

    public final Flow<Float> getRevealAmount() {
        return this.revealAmount;
    }
}