package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.FgsManagerController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1", f = "ForegroundServicesRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.class */
public final class ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Integer>, Boolean, Continuation<? super Unit>, Object> {
    public final /* synthetic */ FgsManagerController $fgsManagerController$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, FgsManagerController fgsManagerController) {
        super(3, continuation);
        this.$fgsManagerController$inlined = fgsManagerController;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Integer> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1 = new ForegroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1(continuation, this.$fgsManagerController$inlined);
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = bool;
        return foregroundServicesRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowOf = !((Boolean) this.L$1).booleanValue() ? FlowKt.flowOf(Boxing.boxInt(0)) : ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new ForegroundServicesRepositoryImpl$foregroundServicesCount$1$1(this.$fgsManagerController$inlined, null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowOf, this) == coroutine_suspended) {
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