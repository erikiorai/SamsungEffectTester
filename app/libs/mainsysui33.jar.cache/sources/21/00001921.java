package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1", f = "HomeControlsKeyguardQuickAffordanceConfig.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.class */
public final class HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super KeyguardQuickAffordanceConfig.LockScreenState>, Boolean, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ HomeControlsKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(Continuation continuation, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig) {
        super(3, continuation);
        this.this$0 = homeControlsKeyguardQuickAffordanceConfig;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (Boolean) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super KeyguardQuickAffordanceConfig.LockScreenState> flowCollector, Boolean bool, Continuation<? super Unit> continuation) {
        HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 homeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1 = new HomeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        homeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        homeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.L$1 = bool;
        return homeControlsKeyguardQuickAffordanceConfig$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Flow flowOf;
        ControlsComponent controlsComponent;
        Flow stateInternal;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig = this.this$0;
                controlsComponent = homeControlsKeyguardQuickAffordanceConfig.component;
                stateInternal = homeControlsKeyguardQuickAffordanceConfig.stateInternal(controlsComponent.getControlsListingController().orElse(null));
                flowOf = stateInternal;
            } else {
                flowOf = FlowKt.flowOf(KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE);
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowOf, this) == coroutine_suspended) {
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