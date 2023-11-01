package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
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

@DebugMetadata(c = "com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1", f = "KeyguardBottomAreaViewModel.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.class */
public final class KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Float>, Boolean, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ KeyguardBottomAreaViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        super(3, continuation);
        this.this$0 = keyguardBottomAreaViewModel;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Float> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1 = new KeyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.L$1 = bool;
        return keyguardBottomAreaViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardBottomAreaInteractor keyguardBottomAreaInteractor;
        Flow distinctUntilChanged;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                distinctUntilChanged = FlowKt.flowOf(Boxing.boxFloat(1.0f));
            } else {
                keyguardBottomAreaInteractor = this.this$0.bottomAreaInteractor;
                distinctUntilChanged = FlowKt.distinctUntilChanged(keyguardBottomAreaInteractor.getAlpha());
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, distinctUntilChanged, this) == coroutine_suspended) {
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