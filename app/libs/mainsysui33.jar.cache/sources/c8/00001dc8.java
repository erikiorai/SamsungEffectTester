package com.android.systemui.mediaprojection.appselector;

import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$init$1", f = "MediaProjectionAppSelectorController.kt", l = {37}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorController$init$1.class */
public final class MediaProjectionAppSelectorController$init$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public Object L$0;
    public int label;
    public final /* synthetic */ MediaProjectionAppSelectorController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaProjectionAppSelectorController$init$1(MediaProjectionAppSelectorController mediaProjectionAppSelectorController, Continuation<? super MediaProjectionAppSelectorController$init$1> continuation) {
        super(2, continuation);
        this.this$0 = mediaProjectionAppSelectorController;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new MediaProjectionAppSelectorController$init$1(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        RecentTaskListProvider recentTaskListProvider;
        Object loadRecentTasks;
        MediaProjectionAppSelectorController mediaProjectionAppSelectorController;
        List<RecentTask> sortTasks;
        MediaProjectionAppSelectorView mediaProjectionAppSelectorView;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            MediaProjectionAppSelectorController mediaProjectionAppSelectorController2 = this.this$0;
            recentTaskListProvider = mediaProjectionAppSelectorController2.recentTaskListProvider;
            this.L$0 = mediaProjectionAppSelectorController2;
            this.label = 1;
            loadRecentTasks = recentTaskListProvider.loadRecentTasks(this);
            if (loadRecentTasks == coroutine_suspended) {
                return coroutine_suspended;
            }
            mediaProjectionAppSelectorController = mediaProjectionAppSelectorController2;
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            mediaProjectionAppSelectorController = (MediaProjectionAppSelectorController) this.L$0;
            ResultKt.throwOnFailure(obj);
            loadRecentTasks = obj;
        }
        sortTasks = mediaProjectionAppSelectorController.sortTasks((List) loadRecentTasks);
        mediaProjectionAppSelectorView = this.this$0.view;
        mediaProjectionAppSelectorView.bind(sortTasks);
        return Unit.INSTANCE;
    }
}