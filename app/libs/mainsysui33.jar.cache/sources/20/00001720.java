package com.android.systemui.dreams.complication;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;

@VisibleForTesting
/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent.class */
public enum DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent implements UiEventLogger.UiEventEnum {
    DREAM_HOME_CONTROLS_TAPPED(1212);
    
    private final int mId;

    DreamHomeControlsComplication$DreamHomeControlsChipViewController$DreamOverlayEvent(int i) {
        this.mId = i;
    }

    public int getId() {
        return this.mId;
    }
}