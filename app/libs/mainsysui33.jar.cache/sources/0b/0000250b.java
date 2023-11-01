package com.android.systemui.security.data.model;

import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.security.data.model.SecurityModel$Companion$create$2", f = "SecurityModel.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/security/data/model/SecurityModel$Companion$create$2.class */
public final class SecurityModel$Companion$create$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super SecurityModel>, Object> {
    public final /* synthetic */ SecurityController $securityController;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SecurityModel$Companion$create$2(SecurityController securityController, Continuation<? super SecurityModel$Companion$create$2> continuation) {
        super(2, continuation);
        this.$securityController = securityController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new SecurityModel$Companion$create$2(this.$securityController, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super SecurityModel> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            return SecurityModel.Companion.create(this.$securityController);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}