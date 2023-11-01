package com.android.systemui.dreams.complication;

import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/DreamClockTimeComplication$Registrant.class */
public class DreamClockTimeComplication$Registrant implements CoreStartable {
    public final DreamOverlayStateController mDreamOverlayStateController;

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mDreamOverlayStateController.addComplication(null);
    }
}