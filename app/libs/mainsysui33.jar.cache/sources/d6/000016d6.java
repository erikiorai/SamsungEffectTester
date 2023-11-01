package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStateController$$ExternalSyntheticLambda7.class */
public final /* synthetic */ class DreamOverlayStateController$$ExternalSyntheticLambda7 implements Consumer {
    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        ((DreamOverlayStateController.Callback) obj).onAvailableComplicationTypesChanged();
    }
}