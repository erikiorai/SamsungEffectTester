package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotPolicyImpl", f = "ScreenshotPolicyImpl.kt", l = {71}, m = "isManagedProfile$suspendImpl")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$1.class */
public final class ScreenshotPolicyImpl$isManagedProfile$1 extends ContinuationImpl {
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$isManagedProfile$1(ScreenshotPolicyImpl screenshotPolicyImpl, Continuation<? super ScreenshotPolicyImpl$isManagedProfile$1> continuation) {
        super(continuation);
        this.this$0 = screenshotPolicyImpl;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ScreenshotPolicyImpl.isManagedProfile$suspendImpl(this.this$0, 0, this);
    }
}