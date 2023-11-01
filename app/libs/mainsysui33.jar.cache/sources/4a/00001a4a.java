package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.domain.model.KeyguardQuickAffordanceModel;
import com.android.systemui.keyguard.shared.quickaffordance.KeyguardQuickAffordancePosition;
import java.util.List;
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

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1", f = "KeyguardQuickAffordanceInteractor.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1.class */
public final class KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super KeyguardQuickAffordanceModel>, List<? extends KeyguardQuickAffordanceConfig>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardQuickAffordancePosition $position$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ KeyguardQuickAffordanceInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1(Continuation continuation, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardQuickAffordancePosition keyguardQuickAffordancePosition) {
        super(3, continuation);
        this.this$0 = keyguardQuickAffordanceInteractor;
        this.$position$inlined = keyguardQuickAffordancePosition;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (List<? extends KeyguardQuickAffordanceConfig>) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super KeyguardQuickAffordanceModel> flowCollector, List<? extends KeyguardQuickAffordanceConfig> list, Continuation<? super Unit> continuation) {
        KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1 keyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1 = new KeyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1(continuation, this.this$0, this.$position$inlined);
        keyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1.L$1 = list;
        return keyguardQuickAffordanceInteractor$quickAffordanceInternal$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Flow combinedConfigs;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            combinedConfigs = this.this$0.combinedConfigs(this.$position$inlined, (List) this.L$1);
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, combinedConfigs, this) == coroutine_suspended) {
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