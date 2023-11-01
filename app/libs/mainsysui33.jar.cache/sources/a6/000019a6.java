package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTransitionCallback;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1", f = "KeyguardRepository.kt", l = {396}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$dozeTransitionModel$1.class */
public final class KeyguardRepositoryImpl$dozeTransitionModel$1 extends SuspendLambda implements Function2<ProducerScope<? super DozeTransitionModel>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$dozeTransitionModel$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$dozeTransitionModel$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$dozeTransitionModel$1 keyguardRepositoryImpl$dozeTransitionModel$1 = new KeyguardRepositoryImpl$dozeTransitionModel$1(this.this$0, continuation);
        keyguardRepositoryImpl$dozeTransitionModel$1.L$0 = obj;
        return keyguardRepositoryImpl$dozeTransitionModel$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super DozeTransitionModel> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: com.android.systemui.doze.DozeTransitionListener */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1, com.android.systemui.doze.DozeTransitionCallback] */
    public final Object invokeSuspend(Object obj) {
        DozeTransitionListener dozeTransitionListener;
        DozeTransitionListener dozeTransitionListener2;
        DozeStateModel dozeMachineStateToModel;
        DozeTransitionListener dozeTransitionListener3;
        DozeStateModel dozeMachineStateToModel2;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r0 = new DozeTransitionCallback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1$callback$1
                @Override // com.android.systemui.doze.DozeTransitionCallback
                public void onDozeTransition(DozeMachine.State state, DozeMachine.State state2) {
                    DozeStateModel dozeMachineStateToModel3;
                    DozeStateModel dozeMachineStateToModel4;
                    dozeMachineStateToModel3 = keyguardRepositoryImpl.dozeMachineStateToModel(state);
                    dozeMachineStateToModel4 = keyguardRepositoryImpl.dozeMachineStateToModel(state2);
                    ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, new DozeTransitionModel(dozeMachineStateToModel3, dozeMachineStateToModel4), "KeyguardRepositoryImpl", "doze transition model");
                }
            };
            dozeTransitionListener = this.this$0.dozeTransitionListener;
            dozeTransitionListener.addCallback((DozeTransitionCallback) r0);
            ChannelExt channelExt = ChannelExt.INSTANCE;
            SendChannel sendChannel2 = sendChannel;
            KeyguardRepositoryImpl keyguardRepositoryImpl2 = this.this$0;
            dozeTransitionListener2 = keyguardRepositoryImpl2.dozeTransitionListener;
            dozeMachineStateToModel = keyguardRepositoryImpl2.dozeMachineStateToModel(dozeTransitionListener2.getOldState());
            KeyguardRepositoryImpl keyguardRepositoryImpl3 = this.this$0;
            dozeTransitionListener3 = keyguardRepositoryImpl3.dozeTransitionListener;
            dozeMachineStateToModel2 = keyguardRepositoryImpl3.dozeMachineStateToModel(dozeTransitionListener3.getNewState());
            channelExt.trySendWithFailureLogging(sendChannel2, new DozeTransitionModel(dozeMachineStateToModel, dozeMachineStateToModel2), "KeyguardRepositoryImpl", "initial doze transition model");
            final KeyguardRepositoryImpl keyguardRepositoryImpl4 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$dozeTransitionModel$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2974invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2974invoke() {
                    DozeTransitionListener dozeTransitionListener4;
                    dozeTransitionListener4 = KeyguardRepositoryImpl.this.dozeTransitionListener;
                    dozeTransitionListener4.removeCallback((DozeTransitionCallback) r0);
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