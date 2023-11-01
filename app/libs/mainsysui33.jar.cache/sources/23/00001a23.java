package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1", f = "FromLockscreenTransitionInteractor.kt", l = {84}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1.class */
public final class FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<Boolean, TransitionStep, Continuation<? super Pair<? extends Boolean, ? extends TransitionStep>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (TransitionStep) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, TransitionStep transitionStep, Continuation<? super Pair<Boolean, TransitionStep>> continuation) {
            return FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1.invokeSuspend$lambda$0(z, transitionStep, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Continuation<? super FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1> continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(boolean z, TransitionStep transitionStep, Continuation continuation) {
        return new Pair(Boxing.boxBoolean(z), transitionStep);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardInteractor keyguardInteractor;
        KeyguardTransitionInteractor keyguardTransitionInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow<Boolean> isBouncerShowing = keyguardInteractor.isBouncerShowing();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow sample = FlowKt.sample(isBouncerShowing, keyguardTransitionInteractor.getStartedKeyguardTransitionStep(), AnonymousClass2.INSTANCE);
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncer$1.3
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Pair) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Pair<Boolean, TransitionStep> pair, Continuation<? super Unit> continuation) {
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator animator;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    TransitionStep transitionStep = (TransitionStep) pair.component2();
                    if (booleanValue) {
                        KeyguardState to = transitionStep.getTo();
                        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
                        if (to == keyguardState) {
                            keyguardTransitionRepository = FromLockscreenTransitionInteractor.this.keyguardTransitionRepository;
                            String name = FromLockscreenTransitionInteractor.this.getName();
                            KeyguardState keyguardState2 = KeyguardState.BOUNCER;
                            animator = FromLockscreenTransitionInteractor.this.getAnimator();
                            keyguardTransitionRepository.startTransition(new TransitionInfo(name, keyguardState, keyguardState2, animator));
                        }
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (sample.collect(flowCollector, this) == coroutine_suspended) {
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