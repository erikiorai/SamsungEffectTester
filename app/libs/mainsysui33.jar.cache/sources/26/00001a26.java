package com.android.systemui.keyguard.domain.interactor;

import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.shade.data.repository.ShadeRepository;
import com.android.systemui.shade.domain.model.ShadeModel;
import com.android.systemui.util.kotlin.FlowKt;
import java.util.UUID;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Triple;
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

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1", f = "FromLockscreenTransitionInteractor.kt", l = {114}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1.class */
public final class FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<KeyguardState, StatusBarState, Continuation<? super Pair<? extends KeyguardState, ? extends StatusBarState>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(KeyguardState keyguardState, StatusBarState statusBarState, Continuation<? super Pair<? extends KeyguardState, ? extends StatusBarState>> continuation) {
            return FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1.invokeSuspend$lambda$0(keyguardState, statusBarState, continuation);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1$3  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1$3.class */
    public final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3<ShadeModel, Pair<? extends KeyguardState, ? extends StatusBarState>, Continuation<? super Triple<? extends ShadeModel, ? extends KeyguardState, ? extends StatusBarState>>, Object> {
        public AnonymousClass3(Object obj) {
            super(3, obj, FromLockscreenTransitionInteractor.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(ShadeModel shadeModel, Pair<? extends KeyguardState, ? extends StatusBarState> pair, Continuation<? super Triple<ShadeModel, ? extends KeyguardState, ? extends StatusBarState>> continuation) {
            return FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1.invokeSuspend$toTriple((FromLockscreenTransitionInteractor) ((AdaptedFunctionReference) this).receiver, shadeModel, pair, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Continuation<? super FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1> continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(KeyguardState keyguardState, StatusBarState statusBarState, Continuation continuation) {
        return new Pair(keyguardState, statusBarState);
    }

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1.3.invoke(com.android.systemui.shade.domain.model.ShadeModel, kotlin.Pair<? extends com.android.systemui.keyguard.shared.model.KeyguardState, ? extends com.android.systemui.keyguard.shared.model.StatusBarState>, kotlin.coroutines.Continuation<? super kotlin.Triple<com.android.systemui.shade.domain.model.ShadeModel, ? extends com.android.systemui.keyguard.shared.model.KeyguardState, ? extends com.android.systemui.keyguard.shared.model.StatusBarState>>):java.lang.Object] */
    public static final /* synthetic */ Object invokeSuspend$toTriple(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, ShadeModel shadeModel, Pair pair, Continuation continuation) {
        return fromLockscreenTransitionInteractor.toTriple(shadeModel, pair);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        ShadeRepository shadeRepository;
        KeyguardTransitionInteractor keyguardTransitionInteractor;
        KeyguardInteractor keyguardInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            shadeRepository = this.this$0.shadeRepository;
            Flow shadeModel = shadeRepository.getShadeModel();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow<KeyguardState> finishedKeyguardState = keyguardTransitionInteractor.getFinishedKeyguardState();
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow sample = FlowKt.sample(shadeModel, kotlinx.coroutines.flow.FlowKt.combine(finishedKeyguardState, keyguardInteractor.getStatusBarState(), AnonymousClass2.INSTANCE), new AnonymousClass3(this.this$0));
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToBouncerDragging$1.4
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Triple) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Triple<ShadeModel, ? extends KeyguardState, ? extends StatusBarState> triple, Continuation<? super Unit> continuation) {
                    UUID uuid;
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    KeyguardTransitionRepository keyguardTransitionRepository2;
                    TransitionState transitionState;
                    ShadeModel shadeModel2 = (ShadeModel) triple.component1();
                    KeyguardState keyguardState = (KeyguardState) triple.component2();
                    StatusBarState statusBarState = (StatusBarState) triple.component3();
                    uuid = FromLockscreenTransitionInteractor.this.transitionId;
                    if (uuid != null) {
                        keyguardTransitionRepository2 = FromLockscreenTransitionInteractor.this.keyguardTransitionRepository;
                        float expansionAmount = shadeModel2.getExpansionAmount();
                        if (!(shadeModel2.getExpansionAmount() == ActionBarShadowController.ELEVATION_LOW)) {
                            if (!(shadeModel2.getExpansionAmount() == 1.0f)) {
                                transitionState = TransitionState.RUNNING;
                                keyguardTransitionRepository2.updateTransition(uuid, 1.0f - expansionAmount, transitionState);
                            }
                        }
                        FromLockscreenTransitionInteractor.this.transitionId = null;
                        transitionState = TransitionState.FINISHED;
                        keyguardTransitionRepository2.updateTransition(uuid, 1.0f - expansionAmount, transitionState);
                    } else {
                        KeyguardState keyguardState2 = KeyguardState.LOCKSCREEN;
                        if (keyguardState == keyguardState2 && shadeModel2.isUserDragging() && statusBarState == StatusBarState.KEYGUARD) {
                            FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor2 = FromLockscreenTransitionInteractor.this;
                            keyguardTransitionRepository = fromLockscreenTransitionInteractor2.keyguardTransitionRepository;
                            fromLockscreenTransitionInteractor2.transitionId = keyguardTransitionRepository.startTransition(new TransitionInfo(FromLockscreenTransitionInteractor.this.getName(), keyguardState2, KeyguardState.BOUNCER, null));
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