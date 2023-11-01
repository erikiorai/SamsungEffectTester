package com.android.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.plugins.ClockController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.keyguard.ClockEventController$listenForDozeAmountTransition$1", f = "ClockEventController.kt", l = {291}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForDozeAmountTransition$1.class */
public final class ClockEventController$listenForDozeAmountTransition$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ ClockEventController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$listenForDozeAmountTransition$1(ClockEventController clockEventController, Continuation<? super ClockEventController$listenForDozeAmountTransition$1> continuation) {
        super(2, continuation);
        this.this$0 = clockEventController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ClockEventController$listenForDozeAmountTransition$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardTransitionInteractor keyguardTransitionInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow<TransitionStep> dozeAmountTransition = keyguardTransitionInteractor.getDozeAmountTransition();
            final ClockEventController clockEventController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.ClockEventController$listenForDozeAmountTransition$1.1
                public final Object emit(TransitionStep transitionStep, Continuation<? super Unit> continuation) {
                    ClockAnimations animations;
                    float f;
                    ClockEventController.this.dozeAmount = transitionStep.getValue();
                    ClockController clock = ClockEventController.this.getClock();
                    if (clock != null && (animations = clock.getAnimations()) != null) {
                        f = ClockEventController.this.dozeAmount;
                        animations.doze(f);
                    }
                    return Unit.INSTANCE;
                }

                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((TransitionStep) obj2, (Continuation<? super Unit>) continuation);
                }
            };
            this.label = 1;
            if (dozeAmountTransition.collect(flowCollector, this) == coroutine_suspended) {
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