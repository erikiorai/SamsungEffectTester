package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.AdaptedFunctionReference;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAod$1", f = "FromGoneTransitionInteractor.kt", l = {71}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromGoneTransitionInteractor$listenForGoneToAod$1.class */
public final class FromGoneTransitionInteractor$listenForGoneToAod$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromGoneTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAod$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromGoneTransitionInteractor$listenForGoneToAod$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<WakefulnessModel, KeyguardState, Continuation<? super Pair<? extends WakefulnessModel, ? extends KeyguardState>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(WakefulnessModel wakefulnessModel, KeyguardState keyguardState, Continuation<? super Pair<WakefulnessModel, ? extends KeyguardState>> continuation) {
            return FromGoneTransitionInteractor$listenForGoneToAod$1.invokeSuspend$lambda$0(wakefulnessModel, keyguardState, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromGoneTransitionInteractor$listenForGoneToAod$1(FromGoneTransitionInteractor fromGoneTransitionInteractor, Continuation<? super FromGoneTransitionInteractor$listenForGoneToAod$1> continuation) {
        super(2, continuation);
        this.this$0 = fromGoneTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(WakefulnessModel wakefulnessModel, KeyguardState keyguardState, Continuation continuation) {
        return new Pair(wakefulnessModel, keyguardState);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromGoneTransitionInteractor$listenForGoneToAod$1(this.this$0, continuation);
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
            Flow<WakefulnessModel> wakefulnessModel = keyguardInteractor.getWakefulnessModel();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow sample = FlowKt.sample(wakefulnessModel, keyguardTransitionInteractor.getFinishedKeyguardState(), AnonymousClass2.INSTANCE);
            final FromGoneTransitionInteractor fromGoneTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor$listenForGoneToAod$1.3
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Pair) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Pair<WakefulnessModel, ? extends KeyguardState> pair, Continuation<? super Unit> continuation) {
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator animator;
                    WakefulnessModel wakefulnessModel2 = (WakefulnessModel) pair.component1();
                    KeyguardState keyguardState = (KeyguardState) pair.component2();
                    KeyguardState keyguardState2 = KeyguardState.GONE;
                    if (keyguardState == keyguardState2 && wakefulnessModel2.getState() == WakefulnessState.STARTING_TO_SLEEP) {
                        keyguardTransitionRepository = FromGoneTransitionInteractor.this.keyguardTransitionRepository;
                        String name = FromGoneTransitionInteractor.this.getName();
                        KeyguardState keyguardState3 = KeyguardState.AOD;
                        animator = FromGoneTransitionInteractor.this.getAnimator();
                        keyguardTransitionRepository.startTransition(new TransitionInfo(name, keyguardState2, keyguardState3, animator));
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