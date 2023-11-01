package com.android.systemui.keyguard.ui.preview;

import android.os.Bundle;
import dagger.internal.InstanceFactory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRendererFactory_Impl.class */
public final class KeyguardPreviewRendererFactory_Impl implements KeyguardPreviewRendererFactory {
    public final KeyguardPreviewRenderer_Factory delegateFactory;

    public KeyguardPreviewRendererFactory_Impl(KeyguardPreviewRenderer_Factory keyguardPreviewRenderer_Factory) {
        this.delegateFactory = keyguardPreviewRenderer_Factory;
    }

    public static Provider<KeyguardPreviewRendererFactory> create(KeyguardPreviewRenderer_Factory keyguardPreviewRenderer_Factory) {
        return InstanceFactory.create(new KeyguardPreviewRendererFactory_Impl(keyguardPreviewRenderer_Factory));
    }

    @Override // com.android.systemui.keyguard.ui.preview.KeyguardPreviewRendererFactory
    public KeyguardPreviewRenderer create(Bundle bundle) {
        return this.delegateFactory.get(bundle);
    }
}