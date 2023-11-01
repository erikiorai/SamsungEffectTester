package com.android.settingslib.graph;

import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/settingslib/graph/BluetoothDeviceLayerDrawable$BatteryMeterDrawable.class */
class BluetoothDeviceLayerDrawable$BatteryMeterDrawable extends BatteryMeterDrawableBase {
    public final float mAspectRatio;
    public int mFrameColor;

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    public float getAspectRatio() {
        return this.mAspectRatio;
    }

    @Override // com.android.settingslib.graph.BatteryMeterDrawableBase
    public float getRadiusRatio() {
        return ActionBarShadowController.ELEVATION_LOW;
    }
}