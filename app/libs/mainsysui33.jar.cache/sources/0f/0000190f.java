package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.statusbar.policy.ZenModeController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1", f = "DoNotDisturbQuickAffordanceConfig.kt", l = {121}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig$lockScreenState$1.class */
public final class DoNotDisturbQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2<ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ DoNotDisturbQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DoNotDisturbQuickAffordanceConfig$lockScreenState$1(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, Continuation<? super DoNotDisturbQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = doNotDisturbQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        DoNotDisturbQuickAffordanceConfig$lockScreenState$1 doNotDisturbQuickAffordanceConfig$lockScreenState$1 = new DoNotDisturbQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        doNotDisturbQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return doNotDisturbQuickAffordanceConfig$lockScreenState$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.Object, com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1$callback$1] */
    public final Object invokeSuspend(Object obj) {
        ZenModeController zenModeController;
        ZenModeController zenModeController2;
        KeyguardQuickAffordanceConfig.LockScreenState updateState;
        ZenModeController zenModeController3;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig = this.this$0;
            final ?? r0 = new ZenModeController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1$callback$1
                public void onZenAvailableChanged(boolean z) {
                    KeyguardQuickAffordanceConfig.LockScreenState updateState2;
                    DoNotDisturbQuickAffordanceConfig.this.isAvailable = z;
                    updateState2 = DoNotDisturbQuickAffordanceConfig.this.updateState();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, updateState2, "DoNotDisturbQuickAffordanceConfig", null, 4, null);
                }

                public void onZenChanged(int i2) {
                    KeyguardQuickAffordanceConfig.LockScreenState updateState2;
                    DoNotDisturbQuickAffordanceConfig.this.dndMode = i2;
                    updateState2 = DoNotDisturbQuickAffordanceConfig.this.updateState();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, updateState2, "DoNotDisturbQuickAffordanceConfig", null, 4, null);
                }
            };
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig2 = this.this$0;
            zenModeController = doNotDisturbQuickAffordanceConfig2.controller;
            doNotDisturbQuickAffordanceConfig2.dndMode = zenModeController.getZen();
            DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig3 = this.this$0;
            zenModeController2 = doNotDisturbQuickAffordanceConfig3.controller;
            doNotDisturbQuickAffordanceConfig3.isAvailable = zenModeController2.isZenAvailable();
            updateState = this.this$0.updateState();
            ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, updateState, "DoNotDisturbQuickAffordanceConfig", null, 4, null);
            zenModeController3 = this.this$0.controller;
            zenModeController3.addCallback((Object) r0);
            final DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig4 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig$lockScreenState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2920invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2920invoke() {
                    ZenModeController zenModeController4;
                    zenModeController4 = DoNotDisturbQuickAffordanceConfig.this.controller;
                    zenModeController4.removeCallback(r0);
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