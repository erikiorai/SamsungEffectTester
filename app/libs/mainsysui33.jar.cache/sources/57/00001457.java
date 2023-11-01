package com.android.systemui.controls.settings;

import android.content.pm.UserInfo;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

@DebugMetadata(c = "com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1", f = "ControlsSettingsRepositoryImpl.kt", l = {190}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1.class */
public final class ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3<FlowCollector<? super Boolean>, UserInfo, Continuation<? super Unit>, Object> {
    public final /* synthetic */ String $setting$inlined;
    private /* synthetic */ Object L$0;
    public /* synthetic */ Object L$1;
    public int label;
    public final /* synthetic */ ControlsSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1(Continuation continuation, ControlsSettingsRepositoryImpl controlsSettingsRepositoryImpl, String str) {
        super(3, continuation);
        this.this$0 = controlsSettingsRepositoryImpl;
        this.$setting$inlined = str;
    }

    public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke((FlowCollector) obj, (UserInfo) obj2, (Continuation) obj3);
    }

    public final Object invoke(FlowCollector<? super Boolean> flowCollector, UserInfo userInfo, Continuation<? super Unit> continuation) {
        ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1 controlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1 = new ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1(continuation, this.this$0, this.$setting$inlined);
        controlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1.L$0 = flowCollector;
        controlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1.L$1 = userInfo;
        return controlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        CoroutineDispatcher coroutineDispatcher;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE.conflatedCallbackFlow(new ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(this.this$0, (UserInfo) this.L$1, this.$setting$inlined, null));
            coroutineDispatcher = this.this$0.backgroundDispatcher;
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(FlowKt.flowOn(conflatedCallbackFlow, coroutineDispatcher));
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, distinctUntilChanged, this) == coroutine_suspended) {
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