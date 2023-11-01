package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeBrightnessHostForwarder.class */
public class DozeBrightnessHostForwarder extends DozeMachine.Service.Delegate {
    public final DozeHost mHost;

    public DozeBrightnessHostForwarder(DozeMachine.Service service, DozeHost dozeHost) {
        super(service);
        this.mHost = dozeHost;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public void setDozeScreenBrightness(int i) {
        super.setDozeScreenBrightness(i);
        this.mHost.setDozeScreenBrightness(i);
    }
}