package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotPolicyImpl", f = "ScreenshotPolicyImpl.kt", l = {96, 100}, m = "findPrimaryContent$suspendImpl")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl$findPrimaryContent$1.class */
public final class ScreenshotPolicyImpl$findPrimaryContent$1 extends ContinuationImpl {
    public int I$0;
    public Object L$0;
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$findPrimaryContent$1(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation<? super ScreenshotPolicyImpl$findPrimaryContent$1> continuation) {
        super(continuation);
        this.this$0 = screenshotPolicyImpl;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ScreenshotPolicyImpl.findPrimaryContent$suspendImpl(this.this$0, 0, this);
    }
}