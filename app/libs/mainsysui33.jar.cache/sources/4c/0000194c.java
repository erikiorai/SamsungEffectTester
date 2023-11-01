package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import java.util.List;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1", f = "KeyguardQuickAffordanceLocalUserSelectionManager.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1.class */
public final class KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Map<String, ? extends List<? extends String>>>, Unit, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceLocalUserSelectionManager this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordanceLocalUserSelectionManager;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Unit) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Map<String, ? extends List<? extends String>>> flowCollector, Unit unit, Continuation<? super Unit> continuation) {
        KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1 keyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1 = new KeyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        keyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1.L$1 = unit;
        return keyguardQuickAffordanceLocalUserSelectionManager$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Unit unit = (Unit) this.L$1;
            Flow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new KeyguardQuickAffordanceLocalUserSelectionManager$selections$3$1(this.this$0, null));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, conflatedCallbackFlow, this) == coroutine_suspended) {
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