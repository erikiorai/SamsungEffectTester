package com.android.systemui.screenshot;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;

@DebugMetadata(c = "com.android.systemui.screenshot.ImageCaptureImpl", f = "ImageCaptureImpl.kt", l = {56}, m = "captureTask$suspendImpl")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageCaptureImpl$captureTask$1.class */
public final class ImageCaptureImpl$captureTask$1 extends ContinuationImpl {
    public int label;
    public /* synthetic */ Object result;
    public final /* synthetic */ ImageCaptureImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageCaptureImpl$captureTask$1(ImageCaptureImpl imageCaptureImpl, Continuation<? super ImageCaptureImpl$captureTask$1> continuation) {
        super(continuation);
        this.this$0 = imageCaptureImpl;
    }

    public final Object invokeSuspend(Object obj) {
        this.result = obj;
        this.label |= Integer.MIN_VALUE;
        return ImageCaptureImpl.captureTask$suspendImpl(this.this$0, 0, this);
    }
}