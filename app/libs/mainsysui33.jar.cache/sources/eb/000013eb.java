package com.android.systemui.controls.management;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.CallableReference;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlHolder$accessibilityDelegate$1.class */
public final /* synthetic */ class ControlHolder$accessibilityDelegate$1 extends FunctionReferenceImpl implements Function1<Boolean, CharSequence> {
    public ControlHolder$accessibilityDelegate$1(Object obj) {
        super(1, obj, ControlHolder.class, "stateDescription", "stateDescription(Z)Ljava/lang/CharSequence;", 0);
    }

    public final CharSequence invoke(boolean z) {
        CharSequence stateDescription;
        stateDescription = ((ControlHolder) ((CallableReference) this).receiver).stateDescription(z);
        return stateDescription;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj) {
        return invoke(((Boolean) obj).booleanValue());
    }
}