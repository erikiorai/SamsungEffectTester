package com.android.systemui.controls.settings;

import android.content.pm.UserInfo;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SettingsProxy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

@DebugMetadata(c = "com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1", f = "ControlsSettingsRepositoryImpl.kt", l = {77}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1.class */
public final class ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1 extends SuspendLambda implements Function2<ProducerScope<? super Boolean>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ String $setting;
    public final /* synthetic */ UserInfo $userInfo;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ ControlsSettingsRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(ControlsSettingsRepositoryImpl controlsSettingsRepositoryImpl, UserInfo userInfo, String str, Continuation<? super ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1> continuation) {
        super(2, continuation);
        this.this$0 = controlsSettingsRepositoryImpl;
        this.$userInfo = userInfo;
        this.$setting = str;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1 controlsSettingsRepositoryImpl$makeFlowForSetting$1$1 = new ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1(this.this$0, this.$userInfo, this.$setting, continuation);
        controlsSettingsRepositoryImpl$makeFlowForSetting$1$1.L$0 = obj;
        return controlsSettingsRepositoryImpl$makeFlowForSetting$1$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super Boolean> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v14, types: [com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1, com.android.systemui.qs.SettingObserver] */
    public final Object invokeSuspend(Object obj) {
        SecureSettings secureSettings;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope = (ProducerScope) this.L$0;
            secureSettings = this.this$0.secureSettings;
            final ?? r0 = new SettingObserver(this.$setting, producerScope, secureSettings, this.$userInfo.id) { // from class: com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1$observer$1
                public final /* synthetic */ ProducerScope<Boolean> $$this$conflatedCallbackFlow;

                /* JADX DEBUG: Multi-variable search result rejected for r8v0, resolved type: kotlinx.coroutines.channels.ProducerScope<? super java.lang.Boolean> */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    SettingsProxy settingsProxy = (SettingsProxy) secureSettings;
                }

                @Override // com.android.systemui.qs.SettingObserver
                public void handleValueChanged(int i2, boolean z) {
                    ProducerScope<Boolean> producerScope2 = this.$$this$conflatedCallbackFlow;
                    boolean z2 = true;
                    if (i2 != 1) {
                        z2 = false;
                    }
                    producerScope2.trySend-JP2dKIU(Boolean.valueOf(z2));
                }
            };
            r0.setListening(true);
            producerScope.trySend-JP2dKIU(Boxing.boxBoolean(r0.getValue() == 1));
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl$makeFlowForSetting$1$1.1
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m1845invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m1845invoke() {
                    setListening(false);
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