package com.android.systemui.keyguard.data.quickaffordance;

import androidx.appcompat.R$styleable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$2", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {R$styleable.AppCompatTheme_windowMinWidthMinor}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$lockScreenState$2.class */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$2 extends SuspendLambda implements Function2<FlowCollector<? super Unit>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;

    public DoNotDisturbQuickAffordanceConfig$lockScreenState$2(Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$2> continuation) {
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$2 doNotDisturbQuickAffordanceConfig$lockScreenState$2 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$2(continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$2.L$0 = obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$2;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(FlowCollector<? super Unit> flowCollector, Continuation<? super Unit> continuation) {
        return create(flowCollector, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Unit unit = Unit.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(unit, this) == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}