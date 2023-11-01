package com.android.systemui.keyguard.data.repository;

import android.graphics.Point;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ChannelExt;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$faceSensorLocation$1", f = "KeyguardRepository.kt", l = {530}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$faceSensorLocation$1.class */
public final class KeyguardRepositoryImpl$faceSensorLocation$1 extends SuspendLambda implements Function2<ProducerScope<? super Point>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ KeyguardRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardRepositoryImpl$faceSensorLocation$1(KeyguardRepositoryImpl keyguardRepositoryImpl, Continuation<? super KeyguardRepositoryImpl$faceSensorLocation$1> continuation) {
        super(2, continuation);
        this.this$0 = keyguardRepositoryImpl;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$faceSensorLocation$1$callback$1.onFaceSensorLocationChanged():void] */
    public static final /* synthetic */ void access$invokeSuspend$sendSensorLocation(ProducerScope producerScope, KeyguardRepositoryImpl keyguardRepositoryImpl) {
        invokeSuspend$sendSensorLocation(producerScope, keyguardRepositoryImpl);
    }

    public static final void invokeSuspend$sendSensorLocation(ProducerScope<? super Point> producerScope, KeyguardRepositoryImpl keyguardRepositoryImpl) {
        AuthController authController;
        authController = keyguardRepositoryImpl.authController;
        ChannelExt.INSTANCE.trySendWithFailureLogging((SendChannel) producerScope, authController.getFaceSensorLocation(), "KeyguardRepositoryImpl", "AuthController.Callback#onFingerprintLocationChanged");
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        KeyguardRepositoryImpl$faceSensorLocation$1 keyguardRepositoryImpl$faceSensorLocation$1 = new KeyguardRepositoryImpl$faceSensorLocation$1(this.this$0, continuation);
        keyguardRepositoryImpl$faceSensorLocation$1.L$0 = obj;
        return keyguardRepositoryImpl$faceSensorLocation$1;
    }

    public final Object invoke(ProducerScope<? super Point> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: com.android.systemui.biometrics.AuthController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$faceSensorLocation$1$callback$1, com.android.systemui.biometrics.AuthController$Callback] */
    public final Object invokeSuspend(Object obj) {
        AuthController authController;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final KeyguardRepositoryImpl keyguardRepositoryImpl = this.this$0;
            final ?? r0 = new AuthController.Callback() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$faceSensorLocation$1$callback$1
                @Override // com.android.systemui.biometrics.AuthController.Callback
                public void onFaceSensorLocationChanged() {
                    KeyguardRepositoryImpl$faceSensorLocation$1.access$invokeSuspend$sendSensorLocation(producerScope, keyguardRepositoryImpl);
                }
            };
            authController = this.this$0.authController;
            authController.addCallback(r0);
            invokeSuspend$sendSensorLocation(producerScope, this.this$0);
            final KeyguardRepositoryImpl keyguardRepositoryImpl2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl$faceSensorLocation$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2975invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke */
                public final void m2975invoke() {
                    AuthController authController2;
                    authController2 = keyguardRepositoryImpl2.authController;
                    authController2.removeCallback(r0);
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