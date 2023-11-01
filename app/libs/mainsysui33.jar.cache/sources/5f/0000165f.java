package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeScreenStatePreventingAdapter.class */
public class DozeScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeScreenStatePreventingAdapter(DozeMachine.Service service) {
        super(service);
    }

    public static boolean isNeeded(DozeParameters dozeParameters) {
        return !dozeParameters.getDisplayStateSupported();
    }

    public static DozeMachine.Service wrapIfNeeded(DozeMachine.Service service, DozeParameters dozeParameters) {
        DozeScreenStatePreventingAdapter dozeScreenStatePreventingAdapter = service;
        if (isNeeded(dozeParameters)) {
            dozeScreenStatePreventingAdapter = new DozeScreenStatePreventingAdapter(service);
        }
        return dozeScreenStatePreventingAdapter;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public void setDozeScreenState(int i) {
        int i2;
        if (i == 3) {
            i2 = 2;
        } else {
            i2 = i;
            if (i == 4) {
                i2 = 6;
            }
        }
        super.setDozeScreenState(i2);
    }
}