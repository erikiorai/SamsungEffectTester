package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.dreams.DreamOverlayCallbackController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1", f = "KeyguardRepository.kt", l = {331}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$isDreamingWithOverlay$1.class */
public final class KeyguardRepositoryImpl$isDreamingWithOverlay$1 extends SuspendLambda implements Function2<ProducerScope<? super Boolean>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$isDreamingWithOverlay$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$isDreamingWithOverlay$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$isDreamingWithOverlay$1 keyguardRepositoryImpl$isDreamingWithOverlay$1 = new KeyguardRepositoryImpl$isDreamingWithOverlay$1(this.this$0, continuation);
        keyguardRepositoryImpl$isDreamingWithOverlay$1.L$0 = obj;
        return keyguardRepositoryImpl$isDreamingWithOverlay$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Boolean> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: com.android.systemui.dreams.DreamOverlayCallbackController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1, com.android.systemui.dreams.DreamOverlayCallbackController$Callback] */
    public final Object invokeSuspend(Object obj) {
        DreamOverlayCallbackController dreamOverlayCallbackController;
        DreamOverlayCallbackController dreamOverlayCallbackController2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final ?? r0 = new DreamOverlayCallbackController.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1$callback$1
                @Override // com.android.systemui.dreams.DreamOverlayCallbackController.Callback
                public void onStartDream() {
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boolean.TRUE, "KeyguardRepositoryImpl", "updated isDreamingWithOverlay");
                }

                @Override // com.android.systemui.dreams.DreamOverlayCallbackController.Callback
                public void onWakeUp() {
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boolean.FALSE, "KeyguardRepositoryImpl", "updated isDreamingWithOverlay");
                }
            };
            dreamOverlayCallbackController = this.this$0.dreamOverlayCallbackController;
            dreamOverlayCallbackController.addCallback((DreamOverlayCallbackController.Callback) r0);
            dreamOverlayCallbackController2 = this.this$0.dreamOverlayCallbackController;
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, Boxing.boxBoolean(dreamOverlayCallbackController2.isDreaming()), "KeyguardRepositoryImpl", "initial isDreamingWithOverlay");
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$isDreamingWithOverlay$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2980invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2980invoke() {
                    DreamOverlayCallbackController dreamOverlayCallbackController3;
                    dreamOverlayCallbackController3 = KeyguardRepositoryImpl.this.dreamOverlayCallbackController;
                    dreamOverlayCallbackController3.removeCallback((DreamOverlayCallbackController.Callback) r0);
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