package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.screenshot.RequestProcessor", f = "RequestProcessor.kt", l = {64, 67, 68}, m = "process")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/RequestProcessor$process$1.class */
public final class RequestProcessor$process$1 extends ContinuationImpl {
    public Object L$0;
    public Object L$1;
    public Object L$2;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ RequestProcessor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RequestProcessor$process$1(RequestProcessor requestProcessor, Continuation<? super RequestProcessor$process$1> continuation) {
        super(continuation);
        this.this$0 = requestProcessor;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return this.this$0.process(null, this);
    }
}