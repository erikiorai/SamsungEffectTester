package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.window.TaskSnapshot;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ImageCaptureImpl$captureTask$snapshot$1", f = "ImageCaptureImpl.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageCaptureImpl$captureTask$snapshot$1.class */
public final class ImageCaptureImpl$captureTask$snapshot$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super TaskSnapshot>, Object> {
    public final /* synthetic */ int $taskId;
    public int label;
    public final /* synthetic */ ImageCaptureImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageCaptureImpl$captureTask$snapshot$1(ImageCaptureImpl imageCaptureImpl, int i, Continuation<? super ImageCaptureImpl$captureTask$snapshot$1> continuation) {
        super(2, continuation);
        this.this$0 = imageCaptureImpl;
        this.$taskId = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ImageCaptureImpl$captureTask$snapshot$1(this.this$0, this.$taskId, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super TaskSnapshot> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IActivityTaskManager iActivityTaskManager;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            iActivityTaskManager = this.this$0.atmService;
            return iActivityTaskManager.takeTaskSnapshot(this.$taskId);
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}