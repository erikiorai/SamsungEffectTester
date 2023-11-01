package com.android.keyguard;

import com.android.keyguard.CarrierTextManager;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextController.class */
public class CarrierTextController extends ViewController<CarrierText> {
    public final CarrierTextManager.CarrierTextCallback mCarrierTextCallback;
    public final CarrierTextManager mCarrierTextManager;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    public CarrierTextController(CarrierText carrierText, CarrierTextManager.Builder builder, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        super(carrierText);
        this.mCarrierTextCallback = new CarrierTextManager.CarrierTextCallback() { // from class: com.android.keyguard.CarrierTextController.1
            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public void finishedWakingUp() {
                ((CarrierText) ((ViewController) CarrierTextController.this).mView).setSelected(true);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public void startedGoingToSleep() {
                ((CarrierText) ((ViewController) CarrierTextController.this).mView).setSelected(false);
            }

            @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
            public void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
                ((CarrierText) ((ViewController) CarrierTextController.this).mView).setText(carrierTextCallbackInfo.carrierText);
            }
        };
        this.mCarrierTextManager = builder.setShowAirplaneMode(((CarrierText) ((ViewController) this).mView).getShowAirplaneMode()).setShowMissingSim(((CarrierText) ((ViewController) this).mView).getShowMissingSim()).build();
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public void onInit() {
        super.onInit();
        ((CarrierText) ((ViewController) this).mView).setSelected(this.mKeyguardUpdateMonitor.isDeviceInteractive());
    }

    public void onViewAttached() {
        this.mCarrierTextManager.setListening(this.mCarrierTextCallback);
    }

    public void onViewDetached() {
        this.mCarrierTextManager.setListening(null);
    }
}