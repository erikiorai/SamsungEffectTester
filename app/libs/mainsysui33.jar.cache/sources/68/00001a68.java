package com.android.systemui.keyguard.domain.interactor;

import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$;
import com.android.systemui.keyguard.shared.model.AnimationParams;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.time.Duration;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor.class */
public final class KeyguardTransitionInteractor {
    public final Flow<TransitionStep> anyStateToAodTransition;
    public final Flow<TransitionStep> aodToLockscreenTransition;
    public final Flow<TransitionStep> canceledKeyguardTransitionStep;
    public final Flow<TransitionStep> dozeAmountTransition;
    public final Flow<TransitionStep> dreamingToLockscreenTransition;
    public final Flow<KeyguardState> finishedKeyguardState;
    public final Flow<TransitionStep> finishedKeyguardTransitionStep;
    public final Flow<TransitionStep> lockscreenToAodTransition;
    public final Flow<TransitionStep> occludedToLockscreenTransition;
    public final Flow<TransitionStep> startedKeyguardTransitionStep;

    public KeyguardTransitionInteractor(KeyguardTransitionRepository keyguardTransitionRepository) {
        KeyguardState keyguardState = KeyguardState.AOD;
        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
        final Flow<TransitionStep> transition = keyguardTransitionRepository.transition(keyguardState, keyguardState2);
        this.aodToLockscreenTransition = transition;
        Flow<TransitionStep> transition2 = keyguardTransitionRepository.transition(keyguardState2, keyguardState);
        this.lockscreenToAodTransition = transition2;
        this.dreamingToLockscreenTransition = keyguardTransitionRepository.transition(KeyguardState.DREAMING, keyguardState2);
        this.occludedToLockscreenTransition = keyguardTransitionRepository.transition(KeyguardState.OCCLUDED, keyguardState2);
        final Flow<TransitionStep> transitions = keyguardTransitionRepository.getTransitions();
        this.anyStateToAodTransition = new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$1$2$1.class */
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
                                if (((TransitionStep) obj).getTo() == KeyguardState.AOD) {
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
                Object collect = transitions.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.dozeAmountTransition = FlowKt.merge(new Flow[]{new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$map$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
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
                                TransitionStep transitionStep = (TransitionStep) obj;
                                TransitionStep copy$default = TransitionStep.copy$default(transitionStep, null, null, 1.0f - transitionStep.getValue(), null, null, 27, null);
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(copy$default, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
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
                Object collect = transition.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        }, transition2});
        final Flow<TransitionStep> transitions2 = keyguardTransitionRepository.getTransitions();
        this.startedKeyguardTransitionStep = new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$2$2$1.class */
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
                                if (((TransitionStep) obj).getTransitionState() == TransitionState.STARTED) {
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
                Object collect = transitions2.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        final Flow<TransitionStep> transitions3 = keyguardTransitionRepository.getTransitions();
        this.canceledKeyguardTransitionStep = new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$3$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$3$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$3$2$1.class */
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
                                if (((TransitionStep) obj).getTransitionState() == TransitionState.CANCELED) {
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
                Object collect = transitions3.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        final Flow<TransitionStep> transitions4 = keyguardTransitionRepository.getTransitions();
        final Flow<TransitionStep> flow = new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$4

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$4$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$4$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$4$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$filter$4$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$filter$4$2$1.class */
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
                Object collect = transitions4.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        this.finishedKeyguardTransitionStep = flow;
        this.finishedKeyguardState = new Flow<KeyguardState>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$map$2$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$special$$inlined$map$2$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$special$$inlined$map$2$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
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
                                KeyguardState to = ((TransitionStep) obj).getTo();
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(to, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
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
                Object collect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }

    public final Flow<TransitionStep> getAnyStateToAodTransition() {
        return this.anyStateToAodTransition;
    }

    public final Flow<TransitionStep> getCanceledKeyguardTransitionStep() {
        return this.canceledKeyguardTransitionStep;
    }

    public final Flow<TransitionStep> getDozeAmountTransition() {
        return this.dozeAmountTransition;
    }

    public final Flow<TransitionStep> getDreamingToLockscreenTransition() {
        return this.dreamingToLockscreenTransition;
    }

    public final Flow<KeyguardState> getFinishedKeyguardState() {
        return this.finishedKeyguardState;
    }

    public final Flow<TransitionStep> getFinishedKeyguardTransitionStep() {
        return this.finishedKeyguardTransitionStep;
    }

    public final Flow<TransitionStep> getOccludedToLockscreenTransition() {
        return this.occludedToLockscreenTransition;
    }

    public final Flow<TransitionStep> getStartedKeyguardTransitionStep() {
        return this.startedKeyguardTransitionStep;
    }

    /* renamed from: transitionStepAnimation-SxA4cEA  reason: not valid java name */
    public final Flow<Float> m3047transitionStepAnimationSxA4cEA(final Flow<TransitionStep> flow, AnimationParams animationParams, long j) {
        final float f = (float) Duration.div-LRDsOJo(animationParams.m3059getStartTimeUwyO8pc(), j);
        final float f2 = (float) Duration.div-LRDsOJo(j, animationParams.m3058getDurationUwyO8pc());
        final Flow<Float> flow2 = new Flow<Float>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ float $chunks$inlined;
                public final /* synthetic */ float $start$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$$inlined$map$1$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$map$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
                    public int label;
                    public /* synthetic */ Object result;
                    public final /* synthetic */ KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$.inlined.map.1.2 this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$.inlined.map.1.2 r4, Continuation continuation) {
                        super(continuation);
                        this.this$0 = r4;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector, float f, float f2) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$start$inlined = f;
                    this.$chunks$inlined = f2;
                }

                /* JADX WARN: Removed duplicated region for block: B:10:0x0047  */
                /* JADX WARN: Removed duplicated region for block: B:15:0x005f  */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    int i;
                    float value;
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
                                TransitionStep transitionStep = (TransitionStep) obj;
                                if (transitionStep.getTransitionState() == TransitionState.STARTED) {
                                    value = 0.0f;
                                } else {
                                    value = this.$chunks$inlined * (transitionStep.getValue() - this.$start$inlined);
                                }
                                Float boxFloat = Boxing.boxFloat(value);
                                anonymousClass1.label = 1;
                                if (flowCollector.emit(boxFloat, anonymousClass1) == coroutine_suspended) {
                                    return coroutine_suspended;
                                }
                            } else if (i != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            } else {
                                ResultKt.throwOnFailure(obj2);
                            }
                            return Unit.INSTANCE;
                        }
                    }
                    anonymousClass1 = new AnonymousClass1(this, continuation);
                    Object obj22 = anonymousClass1.result;
                    Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = flow.collect(new AnonymousClass2(flowCollector, f, f2), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
        return new Flow<Float>() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$$inlined$filter$1$2", f = "KeyguardTransitionInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor$transitionStepAnimation_SxA4cEA$$inlined$filter$1$2$1.class */
                public static final class AnonymousClass1 extends ContinuationImpl {
                    public Object L$0;
                    public Object L$1;
                    public int label;
                    public /* synthetic */ Object result;
                    public final /* synthetic */ KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$.inlined.filter.1.2 this$0;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public AnonymousClass1(KeyguardTransitionInteractor$transitionStepAnimation-SxA4cEA$.inlined.filter.1.2 r4, Continuation continuation) {
                        super(continuation);
                        this.this$0 = r4;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit(null, this);
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
                                float floatValue = ((Number) obj).floatValue();
                                if (floatValue >= ActionBarShadowController.ELEVATION_LOW && floatValue <= 1.0f) {
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
                    anonymousClass1 = new AnonymousClass1(this, continuation);
                    Object obj22 = anonymousClass1.result;
                    Object coroutine_suspended2 = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                    i = anonymousClass1.label;
                    if (i != 0) {
                    }
                    return Unit.INSTANCE;
                }
            }

            public Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
            }
        };
    }
}