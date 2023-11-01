package com.android.systemui.screenshot;

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

@DebugMetadata(c = "com.android.systemui.screenshot.ActionIntentExecutor$launchIntentAsync$1", f = "ActionIntentExecutor.kt", l = {64}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutor$launchIntentAsync$1.class */
public final class ActionIntentExecutor$launchIntentAsync$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Bundle $bundle;
    public final /* synthetic */ Intent $intent;
    public final /* synthetic */ boolean $overrideTransition;
    public final /* synthetic */ int $userId;
    public int label;
    public final /* synthetic */ ActionIntentExecutor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActionIntentExecutor$launchIntentAsync$1(ActionIntentExecutor actionIntentExecutor, Intent intent, Bundle bundle, int i, boolean z, Continuation<? super ActionIntentExecutor$launchIntentAsync$1> continuation) {
        super(2, continuation);
        this.this$0 = actionIntentExecutor;
        this.$intent = intent;
        this.$bundle = bundle;
        this.$userId = i;
        this.$overrideTransition = z;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ActionIntentExecutor$launchIntentAsync$1(this.this$0, this.$intent, this.$bundle, this.$userId, this.$overrideTransition, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ActionIntentExecutor actionIntentExecutor = this.this$0;
            Intent intent = this.$intent;
            Bundle bundle = this.$bundle;
            int i2 = this.$userId;
            boolean z = this.$overrideTransition;
            this.label = 1;
            if (actionIntentExecutor.launchIntent(intent, bundle, i2, z, this) == coroutine_suspended) {
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