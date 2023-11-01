package com.android.systemui.dreams.complication;

import com.android.systemui.CoreStartable;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.dreams.DreamOverlayStateController;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/DreamHomeControlsComplication$Registrant.class */
public class DreamHomeControlsComplication$Registrant implements CoreStartable {
    public final ControlsListingController.ControlsListingCallback mControlsCallback;
    public final ControlsComponent mControlsComponent;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final DreamOverlayStateController.Callback mOverlayStateCallback;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.complication.DreamHomeControlsComplication$Registrant$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$-MAk-2LNglJLelglXuYNVfIgf_I */
    public static /* synthetic */ void m2595$r8$lambda$MAk2LNglJLelglXuYNVfIgf_I(DreamHomeControlsComplication$Registrant dreamHomeControlsComplication$Registrant, ControlsListingController controlsListingController) {
        dreamHomeControlsComplication$Registrant.lambda$start$1(controlsListingController);
    }

    public /* synthetic */ void lambda$start$1(ControlsListingController controlsListingController) {
        controlsListingController.addCallback(this.mControlsCallback);
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mControlsComponent.getControlsListingController().ifPresent(new Consumer() { // from class: com.android.systemui.dreams.complication.DreamHomeControlsComplication$Registrant$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamHomeControlsComplication$Registrant.m2595$r8$lambda$MAk2LNglJLelglXuYNVfIgf_I(DreamHomeControlsComplication$Registrant.this, (ControlsListingController) obj);
            }
        });
        this.mDreamOverlayStateController.addCallback(this.mOverlayStateCallback);
    }
}