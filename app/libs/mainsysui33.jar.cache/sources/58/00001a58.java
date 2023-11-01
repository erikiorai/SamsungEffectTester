package com.android.systemui.keyguard.domain.interactor;

import com.android.keyguard.logging.KeyguardLogger;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@DebugMetadata(c = "com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger$start$3", f = "KeyguardTransitionAuditLogger.kt", l = {47}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionAuditLogger$start$3.class */
public final class KeyguardTransitionAuditLogger$start$3 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ KeyguardTransitionAuditLogger this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardTransitionAuditLogger$start$3(KeyguardTransitionAuditLogger keyguardTransitionAuditLogger, Continuation<? super KeyguardTransitionAuditLogger$start$3> continuation) {
        super(2, continuation);
        this.this$0 = keyguardTransitionAuditLogger;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new KeyguardTransitionAuditLogger$start$3(this.this$0, continuation);
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
            Flow<Boolean> isDozing = keyguardInteractor.isDozing();
            final KeyguardTransitionAuditLogger keyguardTransitionAuditLogger = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger$start$3.1
                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit(((Boolean) obj2).booleanValue(), continuation);
                }

                public final Object emit(boolean z, Continuation<? super Unit> continuation) {
                    KeyguardLogger keyguardLogger;
                    keyguardLogger = KeyguardTransitionAuditLogger.this.logger;
                    keyguardLogger.v("isDozing", Boxing.boxBoolean(z));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (isDozing.collect(flowCollector, this) == coroutine_suspended) {
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