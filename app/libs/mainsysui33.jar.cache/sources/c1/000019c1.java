package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.statusbar.policy.KeyguardStateController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1", f = "KeyguardRepository.kt", l = {219}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$isKeyguardShowing$1.class */
public final class KeyguardRepositoryImpl$isKeyguardShowing$1 extends SuspendLambda implements Function2<ProducerScope<? super Boolean>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$isKeyguardShowing$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$isKeyguardShowing$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$isKeyguardShowing$1 keyguardRepositoryImpl$isKeyguardShowing$1 = new KeyguardRepositoryImpl$isKeyguardShowing$1(this.this$0, continuation);
        keyguardRepositoryImpl$isKeyguardShowing$1.L$0 = obj;
        return keyguardRepositoryImpl$isKeyguardShowing$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Boolean> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1$callback$1, java.lang.Object] */
    public final Object invokeSuspend(Object obj) {
        KeyguardStateController keyguardStateController;
        KeyguardStateController keyguardStateController2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r0 = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1$callback$1
                public void onKeyguardShowingChanged() {
                    KeyguardStateController keyguardStateController3;
                    keyguardStateController3 = keyguardRepositoryImpl.keyguardStateController;
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boolean.valueOf(keyguardStateController3.isShowing()), "KeyguardRepositoryImpl", "updated isKeyguardShowing");
                }
            };
            keyguardStateController = this.this$0.keyguardStateController;
            keyguardStateController.addCallback((Object) r0);
            keyguardStateController2 = this.this$0.keyguardStateController;
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boxing.boxBoolean(keyguardStateController2.isShowing()), "KeyguardRepositoryImpl", "initial isKeyguardShowing");
            final KeyguardRepositoryImpl keyguardRepositoryImpl2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isKeyguardShowing$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2983invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2983invoke() {
                    KeyguardStateController keyguardStateController3;
                    keyguardStateController3 = KeyguardRepositoryImpl.this.keyguardStateController;
                    keyguardStateController3.removeCallback(r0);
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