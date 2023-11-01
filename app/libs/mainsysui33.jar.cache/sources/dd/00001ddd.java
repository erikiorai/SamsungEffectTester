package com.android.systemui.mediaprojection.appselector.data;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.util.GroupedRecentTaskInfo;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt__MutableCollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

@DebugMetadata(c = "com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$loadRecentTasks$2", f = "RecentTaskListProvider.kt", l = {51}, m = "invokeSuspend")
/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ShellRecentTaskListProvider$loadRecentTasks$2.class */
public final class ShellRecentTaskListProvider$loadRecentTasks$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super List<? extends RecentTask>>, Object> {
    public int label;
    public final /* synthetic */ ShellRecentTaskListProvider this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ShellRecentTaskListProvider$loadRecentTasks$2(ShellRecentTaskListProvider shellRecentTaskListProvider, Continuation<? super ShellRecentTaskListProvider$loadRecentTasks$2> continuation) {
        super(2, continuation);
        this.this$0 = shellRecentTaskListProvider;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        return new ShellRecentTaskListProvider$loadRecentTasks$2(this.this$0, continuation);
    }

    /* JADX DEBUG: Method merged with bridge method */
    public final Object invoke(CoroutineScope coroutineScope, Continuation<? super List<RecentTask>> continuation) {
        return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0059, code lost:
        if (r10 != null) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        RecentTasks recents;
        Object tasks;
        List<GroupedRecentTaskInfo> list;
        Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            recents = this.this$0.getRecents();
            if (recents != null) {
                ShellRecentTaskListProvider shellRecentTaskListProvider = this.this$0;
                this.label = 1;
                tasks = shellRecentTaskListProvider.getTasks(recents, this);
                obj = tasks;
                if (tasks == coroutine_suspended) {
                    return coroutine_suspended;
                }
            }
            list = CollectionsKt__CollectionsKt.emptyList();
            ArrayList<ActivityManager.RecentTaskInfo> arrayList = new ArrayList();
            for (GroupedRecentTaskInfo groupedRecentTaskInfo : list) {
                CollectionsKt__MutableCollectionsKt.addAll(arrayList, CollectionsKt__CollectionsKt.listOfNotNull(new ActivityManager.RecentTaskInfo[]{groupedRecentTaskInfo.getTaskInfo1(), groupedRecentTaskInfo.getTaskInfo2()}));
            }
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
            for (ActivityManager.RecentTaskInfo recentTaskInfo : arrayList) {
                int i2 = recentTaskInfo.taskId;
                int i3 = recentTaskInfo.userId;
                ComponentName componentName = recentTaskInfo.topActivity;
                Intent intent = recentTaskInfo.baseIntent;
                ComponentName component = intent != null ? intent.getComponent() : null;
                ActivityManager.TaskDescription taskDescription = recentTaskInfo.taskDescription;
                arrayList2.add(new RecentTask(i2, i3, componentName, component, taskDescription != null ? Boxing.boxInt(taskDescription.getBackgroundColor()) : null));
            }
            return arrayList2;
        } else if (i != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        } else {
            ResultKt.throwOnFailure(obj);
        }
        list = (List) obj;
    }
}