package com.android.systemui.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import dagger.MembersInjector;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider_MembersInjector.class */
public final class CustomizationProvider_MembersInjector implements MembersInjector<CustomizationProvider> {
    public static void injectInteractor(CustomizationProvider customizationProvider, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor) {
        customizationProvider.interactor = keyguardQuickAffordanceInteractor;
    }

    public static void injectPreviewManager(CustomizationProvider customizationProvider, KeyguardRemotePreviewManager keyguardRemotePreviewManager) {
        customizationProvider.previewManager = keyguardRemotePreviewManager;
    }
}