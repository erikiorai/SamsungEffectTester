package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.screenshot.ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2", f = "ScreenshotPolicyImpl.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2.class */
public final class ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>>, Object> {
    public final /* synthetic */ int $displayId;
    public int label;
    public final /* synthetic */ ScreenshotPolicyImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(ScreenshotPolicyImpl screenshotPolicyImpl, int i, Continuation<? super ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2> continuation) {
        super(2, continuation);
        this.this$0 = screenshotPolicyImpl;
        this.$displayId = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ScreenshotPolicyImpl$getAllRootTaskInfosOnDisplay$2(this.this$0, this.$displayId, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<? extends ActivityTaskManager.RootTaskInfo>> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        List emptyList;
        IActivityTaskManager iActivityTaskManager;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            try {
                iActivityTaskManager = this.this$0.atmService;
                emptyList = iActivityTaskManager.getAllRootTaskInfosOnDisplay(this.$displayId);
            } catch (RemoteException e) {
                Log.e("ScreenshotPolicyImpl", "getAllRootTaskInfosOnDisplay", e);
                emptyList = CollectionsKt__CollectionsKt.emptyList();
            }
            return emptyList;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}