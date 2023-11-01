package com.android.systemui.dreams;

import com.android.systemui.dreams.DreamOverlayAnimationsController;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
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

@DebugMetadata(c = "com.android.systemui.dreams.DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1", f = "DreamOverlayAnimationsController.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1.class */
public final class DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Float>, DreamOverlayAnimationsController.ConfigurationBasedDimensions, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ DreamOverlayAnimationsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1(Continuation continuation, DreamOverlayAnimationsController dreamOverlayAnimationsController) {
        super(3, continuation);
        this.this$0 = dreamOverlayAnimationsController;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (DreamOverlayAnimationsController.ConfigurationBasedDimensions) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Float> flowCollector, DreamOverlayAnimationsController.ConfigurationBasedDimensions configurationBasedDimensions, Continuation<? super Unit> continuation) {
        DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1 dreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1 = new DreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1(continuation, this.this$0);
        dreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1.L$0 = flowCollector;
        dreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1.L$1 = configurationBasedDimensions;
        return dreamOverlayAnimationsController$init$1$1$1$invokeSuspend$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            DreamOverlayAnimationsController.ConfigurationBasedDimensions configurationBasedDimensions = (DreamOverlayAnimationsController.ConfigurationBasedDimensions) this.L$1;
            dreamingToLockscreenTransitionViewModel = this.this$0.transitionViewModel;
            Flow<Float> dreamOverlayTranslationY = dreamingToLockscreenTransitionViewModel.dreamOverlayTranslationY(configurationBasedDimensions.getTranslationYPx());
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, dreamOverlayTranslationY, this) == coroutine_suspended) {
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