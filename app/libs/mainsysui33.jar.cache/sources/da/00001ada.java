package com.android.systemui.keyguard.ui.binder;

import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
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

@DebugMetadata(c = "com.android.systemui.keyguard.ui.binder.KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1", f = "KeyguardBottomAreaViewBinder.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1.class */
public final class KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Float>, Integer, Continuation<? super Unit>, Object> {
    public final /* synthetic */ KeyguardBottomAreaViewModel $viewModel$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1(Continuation continuation, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel) {
        super(3, continuation);
        this.$viewModel$inlined = keyguardBottomAreaViewModel;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Integer) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Float> flowCollector, Integer num, Continuation<? super Unit> continuation) {
        KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1 keyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1 = new KeyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1(continuation, this.$viewModel$inlined);
        keyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1.L$0 = flowCollector;
        keyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1.L$1 = num;
        return keyguardBottomAreaViewBinder$bind$1$1$9$invokeSuspend$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow<Float> indicationAreaTranslationY = this.$viewModel$inlined.indicationAreaTranslationY(((Number) this.L$1).intValue());
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, indicationAreaTranslationY, this) == coroutine_suspended) {
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