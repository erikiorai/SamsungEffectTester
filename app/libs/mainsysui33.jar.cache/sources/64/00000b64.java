package com.android.keyguard;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$updateRegionSamplers$2.class */
public final /* synthetic */ class ClockEventController$updateRegionSamplers$2 extends FunctionReferenceImpl implements Function0<Unit> {
    public ClockEventController$updateRegionSamplers$2(Object obj) {
        super(0, obj, ClockEventController.class, "updateColors", "updateColors()V", 0);
    }

    public /* bridge */ /* synthetic */ Object invoke() {
        m547invoke();
        return Unit.INSTANCE;
    }

    /* renamed from: invoke  reason: collision with other method in class */
    public final void m547invoke() {
        ((ClockEventController) ((CallableReference) this).receiver).updateColors();
    }
}