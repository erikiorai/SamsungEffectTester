package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1", f = "KeyguardRepository.kt", l = {365}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$linearDozeAmount$1.class */
public final class KeyguardRepositoryImpl$linearDozeAmount$1 extends SuspendLambda implements Function2<ProducerScope<? super Float>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ StatusBarStateController $statusBarStateController;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$linearDozeAmount$1(StatusBarStateController statusBarStateController, Continuation<? super KeyguardRepositoryImpl$linearDozeAmount$1> continuation) {
        super(2, continuation);
        this.$statusBarStateController = statusBarStateController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$linearDozeAmount$1 keyguardRepositoryImpl$linearDozeAmount$1 = new KeyguardRepositoryImpl$linearDozeAmount$1(this.$statusBarStateController, continuation);
        keyguardRepositoryImpl$linearDozeAmount$1.L$0 = obj;
        return keyguardRepositoryImpl$linearDozeAmount$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Float> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: com.android.systemui.plugins.statusbar.StatusBarStateController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1$callback$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final ?? r0 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public void onDozeAmountChanged(float f, float f2) {
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Float.valueOf(f), "KeyguardRepositoryImpl", "updated dozeAmount");
                }
            };
            this.$statusBarStateController.addCallback(r0);
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boxing.boxFloat(this.$statusBarStateController.getDozeAmount()), "KeyguardRepositoryImpl", "initial dozeAmount");
            final StatusBarStateController statusBarStateController = this.$statusBarStateController;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$linearDozeAmount$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2984invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2984invoke() {
                    StatusBarStateController.this.removeCallback(r0);
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