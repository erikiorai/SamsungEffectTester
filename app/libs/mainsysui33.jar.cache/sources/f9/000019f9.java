package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.keyguard.shared.model.WakefulnessState;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
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

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1", f = "FromBouncerTransitionInteractor.kt", l = {65}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1.class */
public final class FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromBouncerTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<WakefulnessModel, TransitionStep, Continuation<? super Pair<? extends WakefulnessModel, ? extends TransitionStep>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(WakefulnessModel wakefulnessModel, TransitionStep transitionStep, Continuation<? super Pair<WakefulnessModel, TransitionStep>> continuation) {
            return FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1.invokeSuspend$lambda$0(wakefulnessModel, transitionStep, continuation);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1$3  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1$3.class */
    public final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3<Boolean, Pair<? extends WakefulnessModel, ? extends TransitionStep>, Continuation<? super Triple<? extends Boolean, ? extends WakefulnessModel, ? extends TransitionStep>>, Object> {
        public AnonymousClass3(Object obj) {
            super(3, obj, FromBouncerTransitionInteractor.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (Pair) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, Pair<WakefulnessModel, TransitionStep> pair, Continuation<? super Triple<Boolean, WakefulnessModel, TransitionStep>> continuation) {
            return FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1.invokeSuspend$toTriple((FromBouncerTransitionInteractor) ((AdaptedFunctionReference) this).receiver, z, pair, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1(FromBouncerTransitionInteractor fromBouncerTransitionInteractor, Continuation<? super FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1> continuation) {
        super(2, continuation);
        this.this$0 = fromBouncerTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(WakefulnessModel wakefulnessModel, TransitionStep transitionStep, Continuation continuation) {
        return new Pair(wakefulnessModel, transitionStep);
    }

    public static final /* synthetic */ Object invokeSuspend$toTriple(FromBouncerTransitionInteractor fromBouncerTransitionInteractor, boolean z, Pair pair, Continuation continuation) {
        return fromBouncerTransitionInteractor.toTriple(Boxing.boxBoolean(z), pair);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardInteractor keyguardInteractor;
        KeyguardInteractor keyguardInteractor2;
        KeyguardTransitionInteractor keyguardTransitionInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow<Boolean> isBouncerShowing = keyguardInteractor.isBouncerShowing();
            keyguardInteractor2 = this.this$0.keyguardInteractor;
            Flow<WakefulnessModel> wakefulnessModel = keyguardInteractor2.getWakefulnessModel();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow sample = FlowKt.sample(isBouncerShowing, kotlinx.coroutines.flow.FlowKt.combine(wakefulnessModel, keyguardTransitionInteractor.getStartedKeyguardTransitionStep(), AnonymousClass2.INSTANCE), new AnonymousClass3(this.this$0));
            final FromBouncerTransitionInteractor fromBouncerTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor$listenForBouncerToLockscreenOrAod$1.4
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Triple) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Triple<Boolean, WakefulnessModel, TransitionStep> triple, Continuation<? super Unit> continuation) {
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator animator;
                    boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                    WakefulnessModel wakefulnessModel2 = (WakefulnessModel) triple.component2();
                    TransitionStep transitionStep = (TransitionStep) triple.component3();
                    if (!booleanValue) {
                        KeyguardState to = transitionStep.getTo();
                        KeyguardState keyguardState = KeyguardState.BOUNCER;
                        if (to == keyguardState) {
                            KeyguardState keyguardState2 = (wakefulnessModel2.getState() == WakefulnessState.STARTING_TO_SLEEP || wakefulnessModel2.getState() == WakefulnessState.ASLEEP) ? KeyguardState.AOD : KeyguardState.LOCKSCREEN;
                            keyguardTransitionRepository = FromBouncerTransitionInteractor.this.keyguardTransitionRepository;
                            animator = FromBouncerTransitionInteractor.this.getAnimator();
                            keyguardTransitionRepository.startTransition(new TransitionInfo(FromBouncerTransitionInteractor.this.getName(), keyguardState, keyguardState2, animator));
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