package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor$combinedConfigs$1$1", f = "KeyguardQuickAffordanceInteractor.kt", l = {263}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor$combinedConfigs$1$1.class */
public final class KeyguardQuickAffordanceInteractor$combinedConfigs$1$1 extends SuspendLambda implements Function2<FlowCollector<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;

    public KeyguardQuickAffordanceInteractor$combinedConfigs$1$1(Continuation<? super KeyguardQuickAffordanceInteractor$combinedConfigs$1$1> continuation) {
        super(2, continuation);
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardQuickAffordanceInteractor$combinedConfigs$1$1 keyguardQuickAffordanceInteractor$combinedConfigs$1$1 = new KeyguardQuickAffordanceInteractor$combinedConfigs$1$1(continuation);
        keyguardQuickAffordanceInteractor$combinedConfigs$1$1.L$0 = obj;
        return keyguardQuickAffordanceInteractor$combinedConfigs$1$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(FlowCollector<? super KeyguardQuickAffordanceConfig.LockScreenState> flowCollector, Continuation<? super Unit> continuation) {
        return create(flowCollector, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            KeyguardQuickAffordanceConfig.LockScreenState.Hidden hidden = KeyguardQuickAffordanceConfig.LockScreenState.Hidden.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(hidden, this) == coroutine_suspended) {
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