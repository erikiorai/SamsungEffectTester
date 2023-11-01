package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$selections$2", f = "KeyguardQuickAffordanceLocalUserSelectionManager.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager$selections$2.class */
public final class KeyguardQuickAffordanceLocalUserSelectionManager$selections$2 extends SuspendLambda implements Function3<Integer, Unit, Continuation<? super Unit>, Object> {
    public int label;

    public KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(Continuation<? super KeyguardQuickAffordanceLocalUserSelectionManager$selections$2> continuation) {
        super(3, continuation);
    }

    public final Object invoke(int i, Unit unit, Continuation<? super Unit> continuation) {
        return new KeyguardQuickAffordanceLocalUserSelectionManager$selections$2(continuation).invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(((Number) obj).intValue(), (Unit) obj2, (Continuation) obj3);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}