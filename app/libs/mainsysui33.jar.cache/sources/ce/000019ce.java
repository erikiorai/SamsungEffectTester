package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepository.class */
public interface KeyguardTransitionRepository {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepository$DefaultImpls.class */
    public static final class DefaultImpls {
        public static Flow<TransitionStep> transition(KeyguardTransitionRepository keyguardTransitionRepository, final KeyguardState keyguardState, final KeyguardState keyguardState2) {
            final Flow<TransitionStep> transitions = keyguardTransitionRepository.getTransitions();
            return new Flow<TransitionStep>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1

                /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2  reason: invalid class name */
                /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2.class */
                public static final class AnonymousClass2<T> implements FlowCollector {
                    public final /* synthetic */ KeyguardState $from$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ KeyguardState $to$inlined;

                    @DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2", f = "KeyguardTransitionRepository.kt", l = {223}, m = "emit")
                    /* renamed from: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2$1  reason: invalid class name */
                    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepository$DefaultImpls$transition$$inlined$filter$1$2$1.class */
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

                    public AnonymousClass2(FlowCollector flowCollector, KeyguardState keyguardState, KeyguardState keyguardState2) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$from$inlined = keyguardState;
                        this.$to$inlined = keyguardState2;
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
                                    if (transitionStep.getFrom() == this.$from$inlined && transitionStep.getTo() == this.$to$inlined) {
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
                    Object collect = transitions.collect(new AnonymousClass2(flowCollector, keyguardState, keyguardState2), continuation);
                    return collect == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED() ? collect : Unit.INSTANCE;
                }
            };
        }
    }

    Flow<TransitionStep> getTransitions();

    UUID startTransition(TransitionInfo transitionInfo);

    Flow<TransitionStep> transition(KeyguardState keyguardState, KeyguardState keyguardState2);

    void updateTransition(UUID uuid, float f, TransitionState transitionState);
}