package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor.class */
public final class LightRevealScrimInteractor {
    public static final Companion Companion = new Companion(null);
    public final Flow<LightRevealEffect> lightRevealEffect;
    public final Flow<Float> revealAmount;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$Companion.class */
    public static final class Companion {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$Companion$WhenMappings.class */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[KeyguardState.values().length];
                try {
                    iArr[KeyguardState.OFF.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[KeyguardState.DOZING.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[KeyguardState.AOD.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[KeyguardState.DREAMING.ordinal()] = 4;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[KeyguardState.BOUNCER.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[KeyguardState.LOCKSCREEN.ordinal()] = 6;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[KeyguardState.GONE.ordinal()] = 7;
                } catch (NoSuchFieldError e7) {
                }
                try {
                    iArr[KeyguardState.OCCLUDED.ordinal()] = 8;
                } catch (NoSuchFieldError e8) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean willBeRevealedInState(KeyguardState keyguardState) {
            boolean z = false;
            switch (WhenMappings.$EnumSwitchMapping$0[keyguardState.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                    z = true;
                    break;
            }
            return z;
        }

        public final boolean willTransitionAffectRevealAmount(TransitionStep transitionStep) {
            return willBeRevealedInState(transitionStep.getFrom()) != willBeRevealedInState(transitionStep.getTo());
        }
    }

    public LightRevealScrimInteractor(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, LightRevealScrimRepository lightRevealScrimRepository) {
        this.lightRevealEffect = FlowKt.sample(keyguardTransitionInteractor.getStartedKeyguardTransitionStep(), lightRevealScrimRepository.getRevealEffect());
        final Flow<TransitionStep> transitions = keyguardTransitionRepository.getTransitions();
        final Flow<TransitionStep> flow = new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$special$$inlined$filter$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1$2", f = "LightRevealScrimInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$filter$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$special$$inlined$filter$1$2$1.class */
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
                                if (LightRevealScrimInteractor.Companion.willTransitionAffectRevealAmount((TransitionStep) obj)) {
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
        this.revealAmount = new Flow<Float>() { // from class: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$map$1$2  reason: invalid class name */
            /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$special$$inlined$map$1$2.class */
            public static final class AnonymousClass2<T> implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$map$1$2", f = "LightRevealScrimInteractor.kt", l = {223}, m = "emit")
                /* renamed from: com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor$special$$inlined$map$1$2$1  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor$special$$inlined$map$1$2$1.class */
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
                                Float boxFloat = Boxing.boxFloat(LightRevealScrimInteractor.Companion.willBeRevealedInState(transitionStep.getTo()) ? transitionStep.getValue() : 1.0f - transitionStep.getValue());
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

    public final Flow<LightRevealEffect> getLightRevealEffect() {
        return this.lightRevealEffect;
    }

    public final Flow<Float> getRevealAmount() {
        return this.revealAmount;
    }
}