package com.android.systemui.keyguard.data.repository;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1", f = "KeyguardRepository.kt", l = {444}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$biometricUnlockState$1.class */
public final class KeyguardRepositoryImpl$biometricUnlockState$1 extends SuspendLambda implements Function2<ProducerScope<? super BiometricUnlockModel>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ BiometricUnlockController $biometricUnlockController;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$biometricUnlockState$1(BiometricUnlockController biometricUnlockController, KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$biometricUnlockState$1> continuation) {
        super(2, continuation);
        this.$biometricUnlockController = biometricUnlockController;
        this.this$0 = keyguardRepositoryImpl;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1.onModeChanged(int):void, com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1.onResetMode():void] */
    public static final /* synthetic */ void access$invokeSuspend$dispatchUpdate(ProducerScope producerScope, KeyguardRepositoryImpl keyguardRepositoryImpl, BiometricUnlockController biometricUnlockController) {
        invokeSuspend$dispatchUpdate(producerScope, keyguardRepositoryImpl, biometricUnlockController);
    }

    public static final void invokeSuspend$dispatchUpdate(ProducerScope<? super BiometricUnlockModel> producerScope, KeyguardRepositoryImpl keyguardRepositoryImpl, BiometricUnlockController biometricUnlockController) {
        BiometricUnlockModel biometricModeIntToObject;
        biometricModeIntToObject = keyguardRepositoryImpl.biometricModeIntToObject(biometricUnlockController.getMode());
        ChannelExt.INSTANCE.trySendWithFailureLogging((SendChannel) producerScope, biometricModeIntToObject, "KeyguardRepositoryImpl", "biometric mode");
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$biometricUnlockState$1 keyguardRepositoryImpl$biometricUnlockState$1 = new KeyguardRepositoryImpl$biometricUnlockState$1(this.$biometricUnlockController, this.this$0, continuation);
        keyguardRepositoryImpl$biometricUnlockState$1.L$0 = obj;
        return keyguardRepositoryImpl$biometricUnlockState$1;
    }

    public final Object invoke(ProducerScope<? super BiometricUnlockModel> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricModeListener, com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1] */
    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final BiometricUnlockController biometricUnlockController = this.$biometricUnlockController;
            final ?? r0 = new BiometricUnlockController.BiometricModeListener() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1$callback$1
                public void onModeChanged(int i2) {
                    KeyguardRepositoryImpl$biometricUnlockState$1.access$invokeSuspend$dispatchUpdate(producerScope, keyguardRepositoryImpl, biometricUnlockController);
                }

                public void onResetMode() {
                    KeyguardRepositoryImpl$biometricUnlockState$1.access$invokeSuspend$dispatchUpdate(producerScope, keyguardRepositoryImpl, biometricUnlockController);
                }
            };
            this.$biometricUnlockController.addBiometricModeListener((BiometricUnlockController.BiometricModeListener) r0);
            invokeSuspend$dispatchUpdate(producerScope, this.this$0, this.$biometricUnlockController);
            final BiometricUnlockController biometricUnlockController2 = this.$biometricUnlockController;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$biometricUnlockState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2973invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke */
                public final void m2973invoke() {
                    biometricUnlockController2.removeBiometricModeListener(r0);
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