package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
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

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1", f = "FromLockscreenTransitionInteractor.kt", l = {187}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1.class */
public final class FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$2.class */
    public final /* synthetic */ class AnonymousClass2 extends AdaptedFunctionReference implements Function3<KeyguardState, Boolean, Continuation<? super Pair<? extends KeyguardState, ? extends Boolean>>, Object> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(3, Pair.class, "<init>", "<init>(Ljava/lang/Object;Ljava/lang/Object;)V", 4);
        }

        public final Object invoke(KeyguardState keyguardState, boolean z, Continuation<? super Pair<? extends KeyguardState, Boolean>> continuation) {
            return FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1.invokeSuspend$lambda$0(keyguardState, z, continuation);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke((KeyguardState) obj, ((Boolean) obj2).booleanValue(), (Continuation) obj3);
        }
    }

    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$3  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1$3.class */
    public final /* synthetic */ class AnonymousClass3 extends AdaptedFunctionReference implements Function3<Boolean, Pair<? extends KeyguardState, ? extends Boolean>, Continuation<? super Triple<? extends Boolean, ? extends KeyguardState, ? extends Boolean>>, Object> {
        public AnonymousClass3(Object obj) {
            super(3, obj, FromLockscreenTransitionInteractor.class, "toTriple", "toTriple(Ljava/lang/Object;Lkotlin/Pair;)Lkotlin/Triple;", 4);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Boolean) obj).booleanValue(), (Pair) obj2, (Continuation) obj3);
        }

        public final Object invoke(boolean z, Pair<? extends KeyguardState, Boolean> pair, Continuation<? super Triple<Boolean, ? extends KeyguardState, Boolean>> continuation) {
            return FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1.invokeSuspend$toTriple((FromLockscreenTransitionInteractor) ((AdaptedFunctionReference) this).receiver, z, pair, continuation);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Continuation<? super FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1> continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
    }

    public static final /* synthetic */ Object invokeSuspend$lambda$0(KeyguardState keyguardState, boolean z, Continuation continuation) {
        return new Pair(keyguardState, Boxing.boxBoolean(z));
    }

    public static final /* synthetic */ Object invokeSuspend$toTriple(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, boolean z, Pair pair, Continuation continuation) {
        return fromLockscreenTransitionInteractor.toTriple(Boxing.boxBoolean(z), pair);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardInteractor keyguardInteractor;
        KeyguardTransitionInteractor keyguardTransitionInteractor;
        KeyguardInteractor keyguardInteractor2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow<Boolean> isKeyguardOccluded = keyguardInteractor.isKeyguardOccluded();
            keyguardTransitionInteractor = this.this$0.keyguardTransitionInteractor;
            Flow<KeyguardState> finishedKeyguardState = keyguardTransitionInteractor.getFinishedKeyguardState();
            keyguardInteractor2 = this.this$0.keyguardInteractor;
            Flow sample = FlowKt.sample(isKeyguardOccluded, kotlinx.coroutines.flow.FlowKt.combine(finishedKeyguardState, keyguardInteractor2.isDreaming(), AnonymousClass2.INSTANCE), new AnonymousClass3(this.this$0));
            final FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToOccluded$1.4
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((Triple) obj2, (Continuation<? super Unit>) continuation);
                }

                public final Object emit(Triple<Boolean, ? extends KeyguardState, Boolean> triple, Continuation<? super Unit> continuation) {
                    KeyguardTransitionRepository keyguardTransitionRepository;
                    ValueAnimator animator;
                    boolean booleanValue = ((Boolean) triple.component1()).booleanValue();
                    KeyguardState keyguardState = (KeyguardState) triple.component2();
                    boolean booleanValue2 = ((Boolean) triple.component3()).booleanValue();
                    if (booleanValue && !booleanValue2) {
                        keyguardTransitionRepository = FromLockscreenTransitionInteractor.this.keyguardTransitionRepository;
                        String name = FromLockscreenTransitionInteractor.this.getName();
                        KeyguardState keyguardState2 = KeyguardState.OCCLUDED;
                        animator = FromLockscreenTransitionInteractor.this.getAnimator();
                        keyguardTransitionRepository.startTransition(new TransitionInfo(name, keyguardState, keyguardState2, animator));
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