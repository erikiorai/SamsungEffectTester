package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
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
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToGone$1", f = "FromBouncerTransitionInteractor.kt", l = {96}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$listenForBouncerToGone$1.class */
public final class FromBouncerTransitionInteractor$listenForBouncerToGone$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromBouncerTransitionInteractor this$0;

    @DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToGone$1$1", f = "FromBouncerTransitionInteractor.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToGone$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$listenForBouncerToGone$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function3<Boolean, KeyguardState, Continuation<? super Pair<? extends Boolean, ? extends KeyguardState>>, Object> {
        public /* synthetic */ Object L$0;
        public /* synthetic */ boolean Z$0;
        public int label;

        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (KeyguardState) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, KeyguardState keyguardState, Continuation<? super Pair<Boolean, ? extends KeyguardState>> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.Z$0 = z;
            anonymousClass1.L$0 = keyguardState;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                return new Pair(Boxing.boxBoolean(this.Z$0), (KeyguardState) this.L$0);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromBouncerTransitionInteractor$listenForBouncerToGone$1(FromBouncerTransitionInteractor fromBouncerTransitionInteractor, Continuation<? super FromBouncerTransitionInteractor$listenForBouncerToGone$1> continuation) {
        super(2, continuation);
        this.this$0 = fromBouncerTransitionInteractor;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromBouncerTransitionInteractor$listenForBouncerToGone$1(this.this$0, continuation);
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
            Flow<Boolean> isKeyguardGoingAway = keyguardInteractor.isKeyguardGoingAway();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow sample = FlowKt.sample(isKeyguardGoingAway, keyguardTransitionInteractor.getFinishedKeyguardState(), new AnonymousClass1(null));
            final FromBouncerTransitionInteractor fromBouncerTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToGone$1.2
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Pair) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Pair<Boolean, ? extends KeyguardState> pair, Continuation<? super Unit> continuation) {
                    KeyguardState keyguardState;
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator animator;
                    boolean booleanValue = ((Boolean) pair.component1()).booleanValue();
                    KeyguardState keyguardState2 = (KeyguardState) pair.component2();
                    if (booleanValue && keyguardState2 == (keyguardState = KeyguardState.BOUNCER)) {
                        keyguardTransitionRepository = FromBouncerTransitionInteractor.this.keyguardTransitionRepository;
                        String name = FromBouncerTransitionInteractor.this.getName();
                        KeyguardState keyguardState3 = KeyguardState.GONE;
                        animator = FromBouncerTransitionInteractor.this.getAnimator();
                        keyguardTransitionRepository.startTransition(new TransitionInfo(name, keyguardState, keyguardState3, animator));
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