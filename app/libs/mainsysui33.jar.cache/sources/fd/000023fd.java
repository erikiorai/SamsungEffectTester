package com.android.systemui.screenshot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ActionIntentExecutor$launchIntent$2", f = "ActionIntentExecutor.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutor$launchIntent$2.class */
public final class ActionIntentExecutor$launchIntent$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Bundle $bundle;
    public final /* synthetic */ Intent $intent;
    public int label;
    public final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntent$2(ActionIntentExecutor actionIntentExecutor, Intent intent, Bundle bundle, Continuation<? super ActionIntentExecutor$launchIntent$2> continuation) {
        super(2, continuation);
        this.this$0 = actionIntentExecutor;
        this.$intent = intent;
        this.$bundle = bundle;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ActionIntentExecutor$launchIntent$2(this.this$0, this.$intent, this.$bundle, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Context context;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            context = this.this$0.context;
            context.startActivity(this.$intent, this.$bundle);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}