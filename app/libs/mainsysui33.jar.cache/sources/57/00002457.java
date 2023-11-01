package com.android.systemui.screenshot;

import com.android.internal.util.ScreenshotHelper;
import java.util.function.Consumer;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.RequestProcessor$processAsync$1", f = "RequestProcessor.kt", l = {95}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/RequestProcessor$processAsync$1.class */
public final class RequestProcessor$processAsync$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ Consumer<ScreenshotHelper.ScreenshotRequest> $callback;
    public final /* synthetic */ ScreenshotHelper.ScreenshotRequest $request;
    public int label;
    public final /* synthetic */ RequestProcessor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequestProcessor$processAsync$1(RequestProcessor requestProcessor, ScreenshotHelper.ScreenshotRequest screenshotRequest, Consumer<ScreenshotHelper.ScreenshotRequest> consumer, Continuation<? super RequestProcessor$processAsync$1> continuation) {
        super(2, continuation);
        this.this$0 = requestProcessor;
        this.$request = screenshotRequest;
        this.$callback = consumer;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new RequestProcessor$processAsync$1(this.this$0, this.$request, this.$callback, continuation);
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
            RequestProcessor requestProcessor = this.this$0;
            ScreenshotHelper.ScreenshotRequest screenshotRequest = this.$request;
            this.label = 1;
            Object process = requestProcessor.process(screenshotRequest, this);
            obj = process;
            if (process == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        this.$callback.accept((ScreenshotHelper.ScreenshotRequest) obj);
        return Unit.INSTANCE;
    }
}