package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionStep;
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

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1", f = "FromDreamingTransitionInteractor.kt", l = {68}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1.class */
public final class FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<DozeTransitionModel, TransitionStep, Continuation<? super Pair<? extends DozeTransitionModel, ? extends TransitionStep>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(DozeTransitionModel dozeTransitionModel, TransitionStep transitionStep, Continuation<? super Pair<DozeTransitionModel, TransitionStep>> continuation) {
            return FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1.invokeSuspend$lambda$0(dozeTransitionModel, transitionStep, continuation);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1$3  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1$3.class */
    public final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3<Boolean, Pair<? extends DozeTransitionModel, ? extends TransitionStep>, Continuation<? super Triple<? extends Boolean, ? extends DozeTransitionModel, ? extends TransitionStep>>, Object> {
        public AnonymousClass3(Object obj) {
            super(3, obj, FromDreamingTransitionInteractor.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (Pair) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, Pair<DozeTransitionModel, TransitionStep> pair, Continuation<? super Triple<Boolean, DozeTransitionModel, TransitionStep>> continuation) {
            return FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1.invokeSuspend$toTriple((FromDreamingTransitionInteractor) ((AdaptedFunctionReference) this).receiver, z, pair, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, Continuation<? super FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1> continuation) {
        super(2, continuation);
        this.this$0 = fromDreamingTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(DozeTransitionModel dozeTransitionModel, TransitionStep transitionStep, Continuation continuation) {
        return new Pair(dozeTransitionModel, transitionStep);
    }

    public static final /* synthetic */ Object invokeSuspend$toTriple(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, boolean z, Pair pair, Continuation continuation) {
        return fromDreamingTransitionInteractor.toTriple(Boxing.boxBoolean(z), pair);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1(this.this$0, continuation);
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
            Flow<Boolean> isDreamingWithOverlay = keyguardInteractor.isDreamingWithOverlay();
            keyguardInteractor2 = this.this$0.keyguardInteractor;
            Flow<DozeTransitionModel> dozeTransitionModel = keyguardInteractor2.getDozeTransitionModel();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow sample = FlowKt.sample(isDreamingWithOverlay, kotlinx.coroutines.flow.FlowKt.combine(dozeTransitionModel, keyguardTransitionInteractor.getStartedKeyguardTransitionStep(), AnonymousClass2.INSTANCE), new AnonymousClass3(this.this$0));
            final FromDreamingTransitionInteractor fromDreamingTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToLockscreen$1.4
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Triple) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Triple<Boolean, DozeTransitionModel, TransitionStep> triple, Continuation<? super Unit> continuation) {
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator m3011getAnimatorLRDsOJo;
                    boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                    DozeTransitionModel dozeTransitionModel2 = (DozeTransitionModel) triple.component2();
                    TransitionStep transitionStep = (TransitionStep) triple.component3();
                    if (!booleanValue && DozeStateModel.Companion.isDozeOff(dozeTransitionModel2.getTo())) {
                        KeyguardState to = transitionStep.getTo();
                        KeyguardState keyguardState = KeyguardState.DREAMING;
                        if (to == keyguardState) {
                            keyguardTransitionRepository = FromDreamingTransitionInteractor.this.keyguardTransitionRepository;
                            String name = FromDreamingTransitionInteractor.this.getName();
                            KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
                            m3011getAnimatorLRDsOJo = FromDreamingTransitionInteractor.this.m3011getAnimatorLRDsOJo(FromDreamingTransitionInteractor.Companion.m3012getTO_LOCKSCREEN_DURATIONUwyO8pc());
                            keyguardTransitionRepository.startTransition(new TransitionInfo(name, keyguardState, keyguardState2, m3011getAnimatorLRDsOJo));
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