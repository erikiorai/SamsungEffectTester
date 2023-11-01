package com.android.systemui.controls.management;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlHolder$accessibilityDelegate$2.class */
public final /* synthetic */ class ControlHolder$accessibilityDelegate$2 extends FunctionReferenceImpl implements Function0<Integer> {
    public ControlHolder$accessibilityDelegate$2(Object obj) {
        super(0, obj, ControlHolder.class, "getLayoutPosition", "getLayoutPosition()I", 0);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: invoke */
    public final Integer m1823invoke() {
        return Integer.valueOf(((ControlHolder) ((CallableReference) this).receiver).getLayoutPosition());
    }
}