package com.android.systemui.controls.controller;

import com.android.systemui.R$drawable;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsTileResourceConfigurationImpl.class */
public final class ControlsTileResourceConfigurationImpl implements ControlsTileResourceConfiguration {
    @Override // com.android.systemui.controls.controller.ControlsTileResourceConfiguration
    public int getTileImageId() {
        return R$drawable.controls_icon;
    }

    @Override // com.android.systemui.controls.controller.ControlsTileResourceConfiguration
    public int getTileTitleId() {
        return R$string.quick_controls_title;
    }
}