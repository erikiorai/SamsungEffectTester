package com.android.systemui.doze;

import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSuspendScreenStatePreventingAdapter.class */
public class DozeSuspendScreenStatePreventingAdapter extends DozeMachine.Service.Delegate {
    public DozeSuspendScreenStatePreventingAdapter(DozeMachine.Service service) {
        super(service);
    }

    public static boolean isNeeded(DozeParameters dozeParameters) {
        return !dozeParameters.getDozeSuspendDisplayStateSupported();
    }

    public static DozeMachine.Service wrapIfNeeded(DozeMachine.Service service, DozeParameters dozeParameters) {
        DozeSuspendScreenStatePreventingAdapter dozeSuspendScreenStatePreventingAdapter = service;
        if (isNeeded(dozeParameters)) {
            dozeSuspendScreenStatePreventingAdapter = new DozeSuspendScreenStatePreventingAdapter(service);
        }
        return dozeSuspendScreenStatePreventingAdapter;
    }

    @Override // com.android.systemui.doze.DozeMachine.Service.Delegate, com.android.systemui.doze.DozeMachine.Service
    public void setDozeScreenState(int i) {
        int i2 = i;
        if (i == 4) {
            i2 = 3;
        }
        super.setDozeScreenState(i2);
    }
}