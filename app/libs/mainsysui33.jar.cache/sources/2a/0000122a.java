package com.android.systemui.biometrics;

import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
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

@DebugMetadata(c = "com.android.systemui.biometrics.UdfpsKeyguardViewController$listenForBouncerExpansion$2", f = "UdfpsKeyguardViewController.kt", l = {295}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardViewController$listenForBouncerExpansion$2.class */
public final class UdfpsKeyguardViewController$listenForBouncerExpansion$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public int label;
    public final /* synthetic */ UdfpsKeyguardViewController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsKeyguardViewController$listenForBouncerExpansion$2(UdfpsKeyguardViewController udfpsKeyguardViewController, Continuation<? super UdfpsKeyguardViewController$listenForBouncerExpansion$2> continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new UdfpsKeyguardViewController$listenForBouncerExpansion$2(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        PrimaryBouncerInteractor primaryBouncerInteractor;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            primaryBouncerInteractor = this.this$0.primaryBouncerInteractor;
            Flow<Float> bouncerExpansion = primaryBouncerInteractor.getBouncerExpansion();
            final UdfpsKeyguardViewController udfpsKeyguardViewController = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$listenForBouncerExpansion$2.1
                public final Object emit(float f, Continuation<? super Unit> continuation) {
                    UdfpsKeyguardViewController.this.inputBouncerExpansion = f;
                    UdfpsKeyguardViewController.this.updateAlpha();
                    UdfpsKeyguardViewController.this.updatePauseAuth();
                    return Unit.INSTANCE;
                }

                public /* bridge */ /* synthetic */ Object emit(Object obj2, Continuation continuation) {
                    return emit(((Number) obj2).floatValue(), continuation);
                }
            };
            this.label = 1;
            if (bouncerExpansion.collect(flowCollector, this) == coroutine_suspended) {
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