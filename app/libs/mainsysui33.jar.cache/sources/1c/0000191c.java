package com.android.systemui.keyguard.data.quickaffordance;

import androidx.appcompat.R$styleable;
import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.statusbar.policy.FlashlightController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1", f = "FlashlightQuickAffordanceConfig.kt", l = {R$styleable.AppCompatTheme_windowMinWidthMajor}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig$lockScreenState$1.class */
public final class FlashlightQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2<ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ FlashlightQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlashlightQuickAffordanceConfig$lockScreenState$1(FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig, Continuation<? super FlashlightQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = flashlightQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        FlashlightQuickAffordanceConfig$lockScreenState$1 flashlightQuickAffordanceConfig$lockScreenState$1 = new FlashlightQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        flashlightQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return flashlightQuickAffordanceConfig$lockScreenState$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1$flashlightCallback$1, java.lang.Object] */
    public final Object invokeSuspend(Object obj) {
        FlashlightController flashlightController;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig = this.this$0;
            final ?? r0 = new FlashlightController.FlashlightListener() { // from class: com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1$flashlightCallback$1
                public void onFlashlightAvailabilityChanged(boolean z) {
                    FlashlightController flashlightController2;
                    KeyguardQuickAffordanceConfig.LockScreenState lockScreenState;
                    ChannelExt channelExt = ChannelExt.INSTANCE;
                    SendChannel sendChannel = producerScope;
                    if (z) {
                        flashlightController2 = flashlightQuickAffordanceConfig.flashlightController;
                        lockScreenState = flashlightController2.isEnabled() ? FlashlightQuickAffordanceConfig.FlashlightState.On.INSTANCE.toLockScreenState() : FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState();
                    } else {
                        lockScreenState = FlashlightQuickAffordanceConfig.FlashlightState.Unavailable.INSTANCE.toLockScreenState();
                    }
                    ChannelExt.trySendWithFailureLogging$default(channelExt, sendChannel, lockScreenState, "FlashlightQuickAffordanceConfig", null, 4, null);
                }

                public void onFlashlightChanged(boolean z) {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, z ? FlashlightQuickAffordanceConfig.FlashlightState.On.INSTANCE.toLockScreenState() : FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState(), "FlashlightQuickAffordanceConfig", null, 4, null);
                }

                public void onFlashlightError() {
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, producerScope, FlashlightQuickAffordanceConfig.FlashlightState.OffAvailable.INSTANCE.toLockScreenState(), "FlashlightQuickAffordanceConfig", null, 4, null);
                }
            };
            flashlightController = this.this$0.flashlightController;
            flashlightController.addCallback((Object) r0);
            final FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig$lockScreenState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2926invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2926invoke() {
                    FlashlightController flashlightController2;
                    flashlightController2 = FlashlightQuickAffordanceConfig.this.flashlightController;
                    flashlightController2.removeCallback(r0);
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