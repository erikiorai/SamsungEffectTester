package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$5", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$lockScreenState$5.class */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$5 extends SuspendLambda implements Function3<KeyguardQuickAffordanceConfig.LockScreenState, Integer, Continuation<? super KeyguardQuickAffordanceConfig.LockScreenState>, Object> {
    public /* synthetic */ Object L$0;
    public int label;

    public DoNotDisturbQuickAffordanceConfig$lockScreenState$5(Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$5> continuation) {
        super(3, continuation);
    }

    public final Object invoke(KeyguardQuickAffordanceConfig.LockScreenState lockScreenState, int i, Continuation<? super KeyguardQuickAffordanceConfig.LockScreenState> continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$5 doNotDisturbQuickAffordanceConfig$lockScreenState$5 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$5(continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$5.L$0 = lockScreenState;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$5.invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((KeyguardQuickAffordanceConfig.LockScreenState) obj, ((Number) obj2).intValue(), (Continuation) obj3);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return (KeyguardQuickAffordanceConfig.LockScreenState) this.L$0;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}