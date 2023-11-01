package com.android.keyguard;

import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
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
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.keyguard.ClockEventController$listenForDozing$1", f = "ClockEventController.kt", l = {323}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForDozing$1.class */
public final class ClockEventController$listenForDozing$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ ClockEventController this$0;

    @DebugMetadata(c = "com.android.keyguard.ClockEventController$listenForDozing$1$1", f = "ClockEventController.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.keyguard.ClockEventController$listenForDozing$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$listenForDozing$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function3<Float, Boolean, Continuation<? super Boolean>, Object> {
        public /* synthetic */ float F$0;
        public /* synthetic */ boolean Z$0;
        public int label;
        public final /* synthetic */ ClockEventController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ClockEventController clockEventController, Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
            this.this$0 = clockEventController;
        }

        public final Object invoke(float f, boolean z, Continuation<? super Boolean> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.F$0 = f;
            anonymousClass1.Z$0 = z;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
            return invoke(((Number) obj).floatValue(), ((Boolean) obj2).booleanValue(), (Continuation) obj3);
        }

        public final Object invokeSuspend(Object obj) {
            float f;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                float f2 = this.F$0;
                boolean z = this.Z$0;
                f = this.this$0.dozeAmount;
                return Boxing.boxBoolean(f2 > f || z);
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$listenForDozing$1(ClockEventController clockEventController, Continuation<? super ClockEventController$listenForDozing$1> continuation) {
        super(2, continuation);
        this.this$0 = clockEventController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ClockEventController$listenForDozing$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardInteractor keyguardInteractor;
        KeyguardInteractor keyguardInteractor2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow<Float> dozeAmount = keyguardInteractor.getDozeAmount();
            keyguardInteractor2 = this.this$0.keyguardInteractor;
            Flow combine = FlowKt.combine(dozeAmount, keyguardInteractor2.isDozing(), new AnonymousClass1(this.this$0, null));
            final ClockEventController clockEventController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.keyguard.ClockEventController$listenForDozing$1.2
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit(((Boolean) obj2).booleanValue(), continuation);
                }

                public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                    ClockEventController.this.isDozing = z;
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