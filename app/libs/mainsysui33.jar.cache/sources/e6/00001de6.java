package com.android.systemui.mediaprojection.appselector.view;

import android.content.ComponentName;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import com.android.systemui.shared.recents.model.ThumbnailData;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$bind$1", f = "RecentTaskViewHolder.kt", l = {}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder$bind$1.class */
public final class RecentTaskViewHolder$bind$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ RecentTask $task;
    private /* synthetic */ Object L$0;
    public int label;
    public final /* synthetic */ RecentTaskViewHolder this$0;

    @DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$bind$1$2", f = "RecentTaskViewHolder.kt", l = {69}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$bind$1$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder$bind$1$2.class */
    public static final class AnonymousClass2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ RecentTask $task;
        public int label;
        public final /* synthetic */ RecentTaskViewHolder this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(RecentTaskViewHolder recentTaskViewHolder, RecentTask recentTask, Continuation<? super AnonymousClass2> continuation) {
            super(2, continuation);
            this.this$0 = recentTaskViewHolder;
            this.$task = recentTask;
        }

        public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
            return new AnonymousClass2(this.this$0, this.$task, continuation);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
            return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            RecentTaskThumbnailLoader recentTaskThumbnailLoader;
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                recentTaskThumbnailLoader = this.this$0.thumbnailLoader;
                int taskId = this.$task.getTaskId();
                this.label = 1;
                Object loadThumbnail = recentTaskThumbnailLoader.loadThumbnail(taskId, this);
                obj = loadThumbnail;
                if (loadThumbnail == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            } else {
                ResultKt.throwOnFailure(obj);
            }
            this.this$0.getThumbnailView().bindTask(this.$task, (ThumbnailData) obj);
            return Unit.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecentTaskViewHolder$bind$1(RecentTask recentTask, RecentTaskViewHolder recentTaskViewHolder, Continuation<? super RecentTaskViewHolder$bind$1> continuation) {
        super(2, continuation);
        this.$task = recentTask;
        this.this$0 = recentTaskViewHolder;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        RecentTaskViewHolder$bind$1 recentTaskViewHolder$bind$1 = new RecentTaskViewHolder$bind$1(this.$task, this.this$0, continuation);
        recentTaskViewHolder$bind$1.L$0 = obj;
        return recentTaskViewHolder$bind$1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label == 0) {
            ResultKt.throwOnFailure(obj);
            CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
            ComponentName baseIntentComponent = this.$task.getBaseIntentComponent();
            if (baseIntentComponent != null) {
                BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new RecentTaskViewHolder$bind$1$1$1(this.this$0, this.$task, baseIntentComponent, null), 3, (Object) null);
            }
            BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new AnonymousClass2(this.this$0, this.$task, null), 3, (Object) null);
            return Unit.INSTANCE;
        }
        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
    }
}