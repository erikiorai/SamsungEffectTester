package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$wakefulness$1", f = "KeyguardRepository.kt", l = {486}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$wakefulness$1.class */
public final class KeyguardRepositoryImpl$wakefulness$1 extends SuspendLambda implements Function2<ProducerScope<? super WakefulnessModel>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ WakefulnessLifecycle $wakefulnessLifecycle;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$wakefulness$1(WakefulnessLifecycle wakefulnessLifecycle, Continuation<? super KeyguardRepositoryImpl$wakefulness$1> continuation) {
        super(2, continuation);
        this.$wakefulnessLifecycle = wakefulnessLifecycle;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$wakefulness$1 keyguardRepositoryImpl$wakefulness$1 = new KeyguardRepositoryImpl$wakefulness$1(this.$wakefulnessLifecycle, continuation);
        keyguardRepositoryImpl$wakefulness$1.L$0 = obj;
        return keyguardRepositoryImpl$wakefulness$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super WakefulnessModel> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$wakefulness$1$observer$1, java.lang.Object] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final WakefulnessLifecycle wakefulnessLifecycle = this.$wakefulnessLifecycle;
            final ?? r0 = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$wakefulness$1$observer$1
                public final void dispatchNewState() {
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, WakefulnessModel.Companion.fromWakefulnessLifecycle(wakefulnessLifecycle), "KeyguardRepositoryImpl", "updated wakefulness state");
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public void onFinishedGoingToSleep() {
                    dispatchNewState();
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public void onFinishedWakingUp() {
                    dispatchNewState();
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public void onPostFinishedWakingUp() {
                    dispatchNewState();
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public void onStartedGoingToSleep() {
                    dispatchNewState();
                }

                @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
                public void onStartedWakingUp() {
                    dispatchNewState();
                }
            };
            this.$wakefulnessLifecycle.addObserver(r0);
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, WakefulnessModel.Companion.fromWakefulnessLifecycle(this.$wakefulnessLifecycle), "KeyguardRepositoryImpl", "initial wakefulness state");
            final WakefulnessLifecycle wakefulnessLifecycle2 = this.$wakefulnessLifecycle;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$wakefulness$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2986invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2986invoke() {
                    WakefulnessLifecycle.this.removeObserver(r0);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(sendChannel, function0, this) == coroutine_suspended) {
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