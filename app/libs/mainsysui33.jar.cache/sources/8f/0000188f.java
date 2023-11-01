package com.android.systemui.keyguard;

import android.database.Cursor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.keyguard.CustomizationProvider$query$1", f = "CustomizationProvider.kt", l = {142}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/CustomizationProvider$query$1.class */
public final class CustomizationProvider$query$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Cursor>, Object> {
    public int label;
    public final /* synthetic */ CustomizationProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CustomizationProvider$query$1(CustomizationProvider customizationProvider, Continuation<? super CustomizationProvider$query$1> continuation) {
        super(2, continuation);
        this.this$0 = customizationProvider;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new CustomizationProvider$query$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Cursor> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object queryAffordances;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CustomizationProvider customizationProvider = this.this$0;
            this.label = 1;
            queryAffordances = customizationProvider.queryAffordances(this);
            obj = queryAffordances;
            if (queryAffordances == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        return obj;
    }
}