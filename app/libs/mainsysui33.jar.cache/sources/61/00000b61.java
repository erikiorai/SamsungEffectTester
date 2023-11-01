package com.android.keyguard;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.keyguard.ClockEventController$registerListeners$1", f = "ClockEventController.kt", l = {234}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$registerListeners$1.class */
public final class ClockEventController$registerListeners$1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ ClockEventController this$0;

    @DebugMetadata(c = "com.android.keyguard.ClockEventController$registerListeners$1$1", f = "ClockEventController.kt", l = {}, m = "invokeSuspend")
    /* renamed from: com.android.keyguard.ClockEventController$registerListeners$1$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController$registerListeners$1$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        public int label;
        public final /* synthetic */ ClockEventController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(ClockEventController clockEventController, Continuation<? super AnonymousClass1> continuation) {
            super(2, continuation);
            this.this$0 = clockEventController;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            FeatureFlags featureFlags;
            IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                this.this$0.listenForDozing$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                featureFlags = this.this$0.featureFlags;
                if (featureFlags.isEnabled(Flags.DOZING_MIGRATION_1)) {
                    this.this$0.listenForDozeAmountTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                    this.this$0.listenForAnyStateToAodTransition$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                } else {
                    this.this$0.listenForDozeAmount$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope);
                }
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClockEventController$registerListeners$1(ClockEventController clockEventController, Continuation<? super ClockEventController$registerListeners$1> continuation) {
        super(3, continuation);
        this.this$0 = clockEventController;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
        ClockEventController$registerListeners$1 clockEventController$registerListeners$1 = new ClockEventController$registerListeners$1(this.this$0, continuation);
        clockEventController$registerListeners$1.L$0 = lifecycleOwner;
        return clockEventController$registerListeners$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
            Lifecycle.State state = Lifecycle.State.STARTED;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(this.this$0, null);
            this.label = 1;
            if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) anonymousClass1, (Continuation<? super Unit>) this) == coroutine_suspended) {
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