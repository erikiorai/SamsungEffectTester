package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.statusbar.LightRevealEffect;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1", f = "LightRevealScrimRepository.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.class */
public final class LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super LightRevealEffect>, BiometricUnlockSource, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ LightRevealScrimRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(Continuation continuation, LightRevealScrimRepositoryImpl lightRevealScrimRepositoryImpl) {
        super(3, continuation);
        this.this$0 = lightRevealScrimRepositoryImpl;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (BiometricUnlockSource) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super LightRevealEffect> flowCollector, BiometricUnlockSource biometricUnlockSource, Continuation<? super Unit> continuation) {
        LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1 = new LightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1(continuation, this.this$0);
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.L$0 = flowCollector;
        lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.L$1 = biometricUnlockSource;
        return lightRevealScrimRepositoryImpl$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Flow flow;
        Flow flow2;
        Flow flow3;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            BiometricUnlockSource biometricUnlockSource = (BiometricUnlockSource) this.L$1;
            int i2 = biometricUnlockSource == null ? -1 : LightRevealScrimRepositoryImpl.WhenMappings.$EnumSwitchMapping$0[biometricUnlockSource.ordinal()];
            if (i2 == 1) {
                flow = this.this$0.fingerprintRevealEffect;
                flow2 = flow;
            } else if (i2 != 2) {
                flow2 = FlowKt.flowOf((Object) null);
            } else {
                flow3 = this.this$0.faceRevealEffect;
                flow2 = flow3;
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow2, this) == coroutine_suspended) {
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