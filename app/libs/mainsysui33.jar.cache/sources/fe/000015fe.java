package com.android.systemui.demomode;

import android.os.Bundle;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsJVMKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@DebugMetadata(c = "com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1", f = "DemoModeController.kt", l = {157}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/demomode/DemoModeController$demoFlowForCommand$1.class */
public final class DemoModeController$demoFlowForCommand$1 extends SuspendLambda implements Function2<ProducerScope<? super Bundle>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ String $command;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ DemoModeController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DemoModeController$demoFlowForCommand$1(DemoModeController demoModeController, String str, Continuation<? super DemoModeController$demoFlowForCommand$1> continuation) {
        super(2, continuation);
        this.this$0 = demoModeController;
        this.$command = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DemoModeController$demoFlowForCommand$1 demoModeController$demoFlowForCommand$1 = new DemoModeController$demoFlowForCommand$1(this.this$0, this.$command, continuation);
        demoModeController$demoFlowForCommand$1.L$0 = obj;
        return demoModeController$demoFlowForCommand$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Bundle> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v10, resolved type: com.android.systemui.demomode.DemoModeController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1$callback$1, com.android.systemui.demomode.DemoMode] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final String str = this.$command;
            final ?? r0 = new DemoMode() { // from class: com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1$callback$1
                @Override // com.android.systemui.demomode.DemoMode
                public List<String> demoCommands() {
                    return CollectionsKt__CollectionsJVMKt.listOf(str);
                }

                @Override // com.android.systemui.demomode.DemoModeCommandReceiver
                public void dispatchDemoCommand(String str2, Bundle bundle) {
                    producerScope.trySend-JP2dKIU(bundle);
                }
            };
            this.this$0.addCallback((DemoMode) r0);
            final DemoModeController demoModeController = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.demomode.DemoModeController$demoFlowForCommand$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2397invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2397invoke() {
                    DemoModeController.this.removeCallback((DemoMode) r0);
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