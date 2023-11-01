package com.android.systemui.mediaprojection.appselector.view;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import com.android.systemui.mediaprojection.appselector.data.AppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder$bind$1$1$1", f = "RecentTaskViewHolder.kt", l = {64}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/view/RecentTaskViewHolder$bind$1$1$1.class */
public final class RecentTaskViewHolder$bind$1$1$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ComponentName $component;
    public final /* synthetic */ RecentTask $task;
    public int label;
    public final /* synthetic */ RecentTaskViewHolder this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RecentTaskViewHolder$bind$1$1$1(RecentTaskViewHolder recentTaskViewHolder, RecentTask recentTask, ComponentName componentName, Continuation<? super RecentTaskViewHolder$bind$1$1$1> continuation) {
        super(2, continuation);
        this.this$0 = recentTaskViewHolder;
        this.$task = recentTask;
        this.$component = componentName;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new RecentTaskViewHolder$bind$1$1$1(this.this$0, this.$task, this.$component, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object obj) {
        AppIconLoader appIconLoader;
        ImageView imageView;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            appIconLoader = this.this$0.iconLoader;
            int userId = this.$task.getUserId();
            ComponentName componentName = this.$component;
            this.label = 1;
            Object loadIcon = appIconLoader.loadIcon(userId, componentName, this);
            obj = loadIcon;
            if (loadIcon == coroutine_suspended) {
                return coroutine_suspended;
            }
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        imageView = this.this$0.iconView;
        imageView.setImageDrawable((Drawable) obj);
        return Unit.INSTANCE;
    }
}