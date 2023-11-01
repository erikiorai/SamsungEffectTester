package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1", f = "KeyguardRepository.kt", l = {418}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$statusBarState$1.class */
public final class KeyguardRepositoryImpl$statusBarState$1 extends SuspendLambda implements Function2<ProducerScope<? super StatusBarState>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ StatusBarStateController $statusBarStateController;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$statusBarState$1(StatusBarStateController statusBarStateController, KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$statusBarState$1> continuation) {
        super(2, continuation);
        this.$statusBarStateController = statusBarStateController;
        this.this$0 = keyguardRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$statusBarState$1 keyguardRepositoryImpl$statusBarState$1 = new KeyguardRepositoryImpl$statusBarState$1(this.$statusBarStateController, this.this$0, continuation);
        keyguardRepositoryImpl$statusBarState$1.L$0 = obj;
        return keyguardRepositoryImpl$statusBarState$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super StatusBarState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: com.android.systemui.plugins.statusbar.StatusBarStateController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1$callback$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    public final Object invokeSuspend(Object obj) {
        StatusBarState statusBarStateIntToObject;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r0 = new StatusBarStateController.StateListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1$callback$1
                @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
                public void onStateChanged(int i2) {
                    StatusBarState statusBarStateIntToObject2;
                    statusBarStateIntToObject2 = keyguardRepositoryImpl.statusBarStateIntToObject(i2);
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, statusBarStateIntToObject2, "KeyguardRepositoryImpl", "state");
                }
            };
            this.$statusBarStateController.addCallback(r0);
            statusBarStateIntToObject = this.this$0.statusBarStateIntToObject(this.$statusBarStateController.getState());
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, statusBarStateIntToObject, "KeyguardRepositoryImpl", "initial state");
            final StatusBarStateController statusBarStateController = this.$statusBarStateController;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$statusBarState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2985invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2985invoke() {
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