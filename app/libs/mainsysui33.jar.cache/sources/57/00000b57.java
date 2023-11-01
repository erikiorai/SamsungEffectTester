package com.android.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.plugins.ClockController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1", f = "ClockEventController.kt", l = {307}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForAnyStateToAodTransition$1.class */
public final class ClockEventController$listenForAnyStateToAodTransition$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ ClockEventController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$listenForAnyStateToAodTransition$1(ClockEventController clockEventController, Continuation<? super ClockEventController$listenForAnyStateToAodTransition$1> continuation) {
        super(2, continuation);
        this.this$0 = clockEventController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ClockEventController$listenForAnyStateToAodTransition$1(this.this$0, continuation);
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
            final Flow<TransitionStep> anyStateToAodTransition = keyguardTransitionInteractor.getAnyStateToAodTransition();
            Flow<TransitionStep> flow = new Flow<TransitionStep>() { // from class: com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1

                /* renamed from: com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    @DebugMetadata(c = "com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1$2", f = "ClockEventController.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForAnyStateToAodTransition$1$invokeSuspend$$inlined$filter$1$2$1.class */
                    public static final class AnonymousClass1 extends ContinuationImpl {
                        public Object L$0;
                        public Object L$1;
                        public int label;
                        public /* synthetic */ Object result;

                        public AnonymousClass1(Continuation continuation) {
                            super(continuation);
                        }

                        public final Object invokeSuspend(Object obj) {
                            this.result = obj;
                            this.label |= Integer.MIN_VALUE;
                            return AnonymousClass2.this.emit(null, this);
                        }
                    }

                    public AnonymousClass2(FlowCollector flowCollector) {
                        this.$this_unsafeFlow = flowCollector;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                    /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        int i;
                        if (continuation instanceof AnonymousClass1) {
                            AnonymousClass1 anonymousClass12 = (AnonymousClass1) continuation;
                            int i2 = anonymousClass12.label;
                            if ((i2 & Integer.MIN_VALUE) != 0) {
                                anonymousClass12.label = i2 - 2147483648;
                                anonymousClass1 = anonymousClass12;
                                Object obj2 = anonymousClass1.result;
                                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                                i = anonymousClass1.label;
                                if (i != 0) {
                                    ResultKt.throwOnFailure(obj2);
                                    FlowCollector flowCollector = this.$this_unsafeFlow;
                                    if (((TransitionStep) obj).getTransitionState() == TransitionState.FINISHED) {
                                        anonymousClass1.label = 1;
                                        if (flowCollector.emit(obj, anonymousClass1) == coroutine_suspended) {
                                            return coroutine_suspended;
                                        }
                                    }
                                } else if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                } else {
                                    ResultKt.throwOnFailure(obj2);
                                }
                                return Unit.INSTANCE;
                            }
                        }
                        anonymousClass1 = new AnonymousClass1(continuation);
                        Object obj22 = anonymousClass1.result;
                        Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                        i = anonymousClass1.label;
                        if (i != 0) {
                        }
                        return Unit.INSTANCE;
                    }
                }

                public Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = anyStateToAodTransition.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            };
            final ClockEventController clockEventController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.ClockEventController$listenForAnyStateToAodTransition$1.2
                public final Object emit(TransitionStep transitionStep, Continuation<? super Unit> continuation) {
                    ClockAnimations animations;
                    float f;
                    ClockEventController.this.dozeAmount = 1.0f;
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
            if (flow.collect(flowCollector, this) == coroutine_suspended) {
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