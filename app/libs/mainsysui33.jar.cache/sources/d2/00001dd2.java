package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader$loadThumbnail$2", f = "RecentTaskThumbnailLoader.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ActivityTaskManagerThumbnailLoader$loadThumbnail$2.class */
public final class ActivityTaskManagerThumbnailLoader$loadThumbnail$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super ThumbnailData>, Object> {
    public final /* synthetic */ int $taskId;
    public int label;
    public final /* synthetic */ ActivityTaskManagerThumbnailLoader this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivityTaskManagerThumbnailLoader$loadThumbnail$2(ActivityTaskManagerThumbnailLoader activityTaskManagerThumbnailLoader, int i, Continuation<? super ActivityTaskManagerThumbnailLoader$loadThumbnail$2> continuation) {
        super(2, continuation);
        this.this$0 = activityTaskManagerThumbnailLoader;
        this.$taskId = i;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ActivityTaskManagerThumbnailLoader$loadThumbnail$2(this.this$0, this.$taskId, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super ThumbnailData> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        ActivityManagerWrapper activityManagerWrapper;
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            activityManagerWrapper = this.this$0.activityManager;
            ThumbnailData taskThumbnail = activityManagerWrapper.getTaskThumbnail(this.$taskId, false);
            ThumbnailData thumbnailData = taskThumbnail;
            if (taskThumbnail.thumbnail == null) {
                thumbnailData = null;
            }
            return thumbnailData;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}