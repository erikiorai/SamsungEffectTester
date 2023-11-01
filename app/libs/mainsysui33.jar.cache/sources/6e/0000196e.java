package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.common.coroutine.ChannelExt;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
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

@DebugMetadata(c = "com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1", f = "QrCodeScannerKeyguardQuickAffordanceConfig.kt", l = {69}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1.class */
public final class QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 extends SuspendLambda implements Function2<ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState>, Continuation<? super Unit>, Object> {
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ QrCodeScannerKeyguardQuickAffordanceConfig this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig, Continuation<? super QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1> continuation) {
        super(2, continuation);
        this.this$0 = qrCodeScannerKeyguardQuickAffordanceConfig;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1 = new QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1(this.this$0, continuation);
        qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1.L$0 = obj;
        return qrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(ProducerScope<? super KeyguardQuickAffordanceConfig.LockScreenState> producerScope, Continuation<? super Unit> continuation) {
        return create(producerScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v11, resolved type: com.android.systemui.qrcodescanner.controller.QRCodeScannerController */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.systemui.qrcodescanner.controller.QRCodeScannerController$Callback, com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1] */
    public final Object invokeSuspend(Object obj) {
        QRCodeScannerController qRCodeScannerController;
        QRCodeScannerController qRCodeScannerController2;
        KeyguardQuickAffordanceConfig.LockScreenState state;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SendChannel sendChannel = (ProducerScope) this.L$0;
            final QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig = this.this$0;
            final ?? r0 = new QRCodeScannerController.Callback() { // from class: com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1$callback$1
                @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                public void onQRCodeScannerActivityChanged() {
                    KeyguardQuickAffordanceConfig.LockScreenState state2;
                    state2 = qrCodeScannerKeyguardQuickAffordanceConfig.state();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, state2, "QrCodeScannerKeyguardQuickAffordanceConfig", null, 4, null);
                }

                @Override // com.android.systemui.qrcodescanner.controller.QRCodeScannerController.Callback
                public void onQRCodeScannerPreferenceChanged() {
                    KeyguardQuickAffordanceConfig.LockScreenState state2;
                    state2 = qrCodeScannerKeyguardQuickAffordanceConfig.state();
                    ChannelExt.trySendWithFailureLogging$default(ChannelExt.INSTANCE, sendChannel, state2, "QrCodeScannerKeyguardQuickAffordanceConfig", null, 4, null);
                }
            };
            qRCodeScannerController = this.this$0.controller;
            qRCodeScannerController.addCallback((QRCodeScannerController.Callback) r0);
            qRCodeScannerController2 = this.this$0.controller;
            qRCodeScannerController2.registerQRCodeScannerChangeObservers(0, 1);
            state = this.this$0.state();
            ChannelExt.INSTANCE.trySendWithFailureLogging(sendChannel, state, "initial state", "QrCodeScannerKeyguardQuickAffordanceConfig");
            final QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig2 = this.this$0;
            Function0<Unit> function0 = new Function0<Unit>() { // from class: com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig$lockScreenState$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                public /* bridge */ /* synthetic */ Object invoke() {
                    m2957invoke();
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke  reason: collision with other method in class */
                public final void m2957invoke() {
                    QRCodeScannerController qRCodeScannerController3;
                    QRCodeScannerController qRCodeScannerController4;
                    qRCodeScannerController3 = QrCodeScannerKeyguardQuickAffordanceConfig.this.controller;
                    qRCodeScannerController3.unregisterQRCodeScannerChangeObservers(0, 1);
                    qRCodeScannerController4 = QrCodeScannerKeyguardQuickAffordanceConfig.this.controller;
                    qRCodeScannerController4.removeCallback((QRCodeScannerController.Callback) r0);
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