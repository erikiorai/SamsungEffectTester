package com.android.systemui.screenshot;

import android.os.UserManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotPolicyImpl$isManagedProfile$managed$1", f = "ScreenshotPolicyImpl.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl$isManagedProfile$managed$1.class */
public final class ScreenshotPolicyImpl$isManagedProfile$managed$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Boolean>, Object> {
    public final /* synthetic */ int $userId;
    public int label;
    public final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$isManagedProfile$managed$1(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super ScreenshotPolicyImpl$isManagedProfile$managed$1> continuation) {
        super(2, continuation);
        this.this$0 = screenshotPolicyImpl;
        this.$userId = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ScreenshotPolicyImpl$isManagedProfile$managed$1(this.this$0, this.$userId, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Boolean> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        UserManager userManager;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            userManager = this.this$0.userMgr;
            return Boxing.boxBoolean(userManager.isManagedProfile(this.$userId));
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}