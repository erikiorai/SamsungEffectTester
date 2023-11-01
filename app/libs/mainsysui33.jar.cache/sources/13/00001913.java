package com.android.systemui.keyguard.data.quickaffordance;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$4", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$lockScreenState$4.class */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$4 extends SuspendLambda implements Function2<Integer, Continuation<? super Unit>, Object> {
    public /* synthetic */ int I$0;
    public int label;
    public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$4(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$4> continuation) {
        super(2, continuation);
        this.this$0 = doNotDisturbQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$4 doNotDisturbQuickAffordanceConfig$lockScreenState$4 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$4(this.this$0, continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$4.I$0 = ((Number) obj).intValue();
        return doNotDisturbQuickAffordanceConfig$lockScreenState$4;
    }

    public final Object invoke(int i, Continuation<? super Unit> continuation) {
        return create(Integer.valueOf(i), continuation).invokeSuspend(Unit.INSTANCE);
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
        return invoke(((Number) obj).intValue(), (Continuation) obj2);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            this.this$0.settingsValue = this.I$0;
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}