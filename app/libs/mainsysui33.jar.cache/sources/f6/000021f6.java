package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.qs.FgsManagerController;
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

@DebugMetadata(c = "com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1", f = "ForegroundServicesRepository.kt", l = {105}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.class */
public final class ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ FgsManagerController $fgsManagerController;
    private /* synthetic */ Object L$0;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(FgsManagerController fgsManagerController, Continuation<? super ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1> continuation) {
        super(2, continuation);
        this.$fgsManagerController = fgsManagerController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1.onDialogDismissed():void] */
    public static final /* synthetic */ void access$invokeSuspend$updateState(ProducerScope producerScope) {
        invokeSuspend$updateState(producerScope);
    }

    public static final void invokeSuspend$updateState(ProducerScope<? super Unit> producerScope) {
        ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, (SendChannel) producerScope, Unit.INSTANCE, "ForegroundServicesRepositoryImpl", null, 4, null);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1 = new ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1(this.$fgsManagerController, continuation);
        foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.L$0 = obj;
        return foregroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1;
    }

    public final Object invoke(ProducerScope<? super Unit> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: com.android.systemui.qs.FgsManagerController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1, com.android.systemui.qs.FgsManagerController$OnDialogDismissedListener] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r0 = new FgsManagerController.OnDialogDismissedListener() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1$listener$1
                @Override // com.android.systemui.qs.FgsManagerController.OnDialogDismissedListener
                public void onDialogDismissed() {
                    ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.access$invokeSuspend$updateState(producerScope);
                }
            };
            this.$fgsManagerController.addOnDialogDismissedListener(r0);
            final FgsManagerController fgsManagerController = this.$fgsManagerController;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl$hasNewChanges$1$dialogDismissedEvents$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m3922invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke */
                public final void m3922invoke() {
                    fgsManagerController.removeOnDialogDismissedListener(r0);
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutine_suspended) {
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