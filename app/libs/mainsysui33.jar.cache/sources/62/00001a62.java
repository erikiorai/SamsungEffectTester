package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger$start$8", f = "KeyguardTransitionAuditLogger.kt", l = {68}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionAuditLogger$start$8.class */
public final class KeyguardTransitionAuditLogger$start$8 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ KeyguardTransitionAuditLogger this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionAuditLogger$start$8(KeyguardTransitionAuditLogger keyguardTransitionAuditLogger, Continuation<? super KeyguardTransitionAuditLogger$start$8> continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionAuditLogger;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardTransitionAuditLogger$start$8(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        KeyguardInteractor keyguardInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            keyguardInteractor = this.this$0.keyguardInteractor;
            Flow<DozeTransitionModel> dozeTransitionModel = keyguardInteractor.getDozeTransitionModel();
            final KeyguardTransitionAuditLogger keyguardTransitionAuditLogger = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger$start$8.1
                public final Object emit(DozeTransitionModel dozeTransitionModel2, Continuation<? super Unit> continuation) {
                    KeyguardLogger keyguardLogger;
                    keyguardLogger = KeyguardTransitionAuditLogger.this.logger;
                    keyguardLogger.i("Doze transition", dozeTransitionModel2);
                    return Unit.INSTANCE;
                }

                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit((DozeTransitionModel) obj2, (Continuation<? super Unit>) continuation);
                }
            };
            this.label = 1;
            if (dozeTransitionModel.collect(flowCollector, this) == coroutine_suspended) {
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