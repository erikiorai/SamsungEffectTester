package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
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
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToDozing$1", f = "FromDreamingTransitionInteractor.kt", l = {154}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$listenForDreamingToDozing$1.class */
public final class FromDreamingTransitionInteractor$listenForDreamingToDozing$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromDreamingTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToDozing$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor$listenForDreamingToDozing$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<DozeTransitionModel, KeyguardState, Continuation<? super Pair<? extends DozeTransitionModel, ? extends KeyguardState>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(DozeTransitionModel dozeTransitionModel, KeyguardState keyguardState, Continuation<? super Pair<DozeTransitionModel, ? extends KeyguardState>> continuation) {
            return FromDreamingTransitionInteractor$listenForDreamingToDozing$1.invokeSuspend$lambda$0(dozeTransitionModel, keyguardState, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromDreamingTransitionInteractor$listenForDreamingToDozing$1(FromDreamingTransitionInteractor fromDreamingTransitionInteractor, Continuation<? super FromDreamingTransitionInteractor$listenForDreamingToDozing$1> continuation) {
        super(2, continuation);
        this.this$0 = fromDreamingTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(DozeTransitionModel dozeTransitionModel, KeyguardState keyguardState, Continuation continuation) {
        return new Pair(dozeTransitionModel, keyguardState);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromDreamingTransitionInteractor$listenForDreamingToDozing$1(this.this$0, continuation);
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
            Flow<DozeTransitionModel> dozeTransitionModel = keyguardInteractor.getDozeTransitionModel();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow combine = FlowKt.combine(dozeTransitionModel, keyguardTransitionInteractor.getFinishedKeyguardState(), AnonymousClass2.INSTANCE);
            final FromDreamingTransitionInteractor fromDreamingTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor$listenForDreamingToDozing$1.3
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Pair) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Pair<DozeTransitionModel, ? extends KeyguardState> pair, Continuation<? super Unit> continuation) {
                    KeyguardState keyguardState;
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    DozeTransitionModel dozeTransitionModel2 = (DozeTransitionModel) pair.component1();
                    KeyguardState keyguardState2 = (KeyguardState) pair.component2();
                    if (dozeTransitionModel2.getTo() == DozeStateModel.DOZE && keyguardState2 == (keyguardState = KeyguardState.DREAMING)) {
                        keyguardTransitionRepository = FromDreamingTransitionInteractor.this.keyguardTransitionRepository;
                        keyguardTransitionRepository.startTransition(new TransitionInfo(FromDreamingTransitionInteractor.this.getName(), keyguardState, KeyguardState.DOZING, FromDreamingTransitionInteractor.m3010getAnimatorLRDsOJo$default(FromDreamingTransitionInteractor.this, 0L, 1, null)));
                    }
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (combine.collect(flowCollector, this) == coroutine_suspended) {
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