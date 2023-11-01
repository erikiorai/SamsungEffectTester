package com.android.systemui.assist;

import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/PhoneStateMonitor$$ExternalSyntheticLambda1.class */
public final /* synthetic */ class PhoneStateMonitor$$ExternalSyntheticLambda1 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return Boolean.valueOf(((CentralSurfaces) obj).isBouncerShowing());
    }
}