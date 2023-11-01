package com.android.systemui.screenshot;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotProxyService$mBinder$1$dismissKeyguard$1", f = "ScreenshotProxyService.kt", l = {53}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotProxyService$mBinder$1$dismissKeyguard$1.class */
public final class ScreenshotProxyService$mBinder$1$dismissKeyguard$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ IOnDoneCallback $callback;
    public int label;
    public final /* synthetic */ ScreenshotProxyService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotProxyService$mBinder$1$dismissKeyguard$1(ScreenshotProxyService screenshotProxyService, IOnDoneCallback iOnDoneCallback, Continuation<? super ScreenshotProxyService$mBinder$1$dismissKeyguard$1> continuation) {
        super(2, continuation);
        this.this$0 = screenshotProxyService;
        this.$callback = iOnDoneCallback;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ScreenshotProxyService$mBinder$1$dismissKeyguard$1(this.this$0, this.$callback, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        Object executeAfterDismissing;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ScreenshotProxyService screenshotProxyService = this.this$0;
            IOnDoneCallback iOnDoneCallback = this.$callback;
            this.label = 1;
            executeAfterDismissing = screenshotProxyService.executeAfterDismissing(iOnDoneCallback, this);
            if (executeAfterDismissing == coroutine_suspended) {
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