package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.settings.UserTracker;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.util.GroupedRecentTaskInfo;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.SafeContinuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ShellRecentTaskListProvider.class */
public final class ShellRecentTaskListProvider implements RecentTaskListProvider {
    public final Executor backgroundExecutor;
    public final CoroutineDispatcher coroutineDispatcher;
    public final Optional<RecentTasks> recentTasks;
    public final Lazy recents$delegate = LazyKt__LazyJVMKt.lazy(new Function0<RecentTasks>() { // from class: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$recents$2
        {
            super(0);
        }

        /* JADX DEBUG: Method merged with bridge method */
        /* renamed from: invoke */
        public final RecentTasks m3374invoke() {
            return (RecentTasks) ShellRecentTaskListProvider.access$getRecentTasks$p(ShellRecentTaskListProvider.this).orElse(null);
        }
    });
    public final UserTracker userTracker;

    public ShellRecentTaskListProvider(CoroutineDispatcher coroutineDispatcher, Executor executor, Optional<RecentTasks> optional, UserTracker userTracker) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.backgroundExecutor = executor;
        this.recentTasks = optional;
        this.userTracker = userTracker;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$recents$2.invoke():com.android.wm.shell.recents.RecentTasks] */
    public static final /* synthetic */ Optional access$getRecentTasks$p(ShellRecentTaskListProvider shellRecentTaskListProvider) {
        return shellRecentTaskListProvider.recentTasks;
    }

    public final RecentTasks getRecents() {
        return (RecentTasks) this.recents$delegate.getValue();
    }

    public final Object getTasks(RecentTasks recentTasks, Continuation<? super List<? extends GroupedRecentTaskInfo>> continuation) {
        final SafeContinuation safeContinuation = new SafeContinuation(IntrinsicsKt__IntrinsicsJvmKt.intercepted(continuation));
        recentTasks.getRecentTasks(Integer.MAX_VALUE, 2, this.userTracker.getUserId(), this.backgroundExecutor, new Consumer() { // from class: com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider$getTasks$2$1
            /* JADX DEBUG: Method merged with bridge method */
            @Override // java.util.function.Consumer
            public final void accept(List<GroupedRecentTaskInfo> list) {
                Continuation<List<? extends GroupedRecentTaskInfo>> continuation2 = safeContinuation;
                Result.Companion companion = Result.Companion;
                continuation2.resumeWith(Result.constructor-impl(list));
            }
        });
        Object orThrow = safeContinuation.getOrThrow();
        if (orThrow == IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended(continuation);
        }
        return orThrow;
    }

    @Override // com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider
    public Object loadRecentTasks(Continuation<? super List<RecentTask>> continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ShellRecentTaskListProvider$loadRecentTasks$2(this, null), continuation);
    }
}