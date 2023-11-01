package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import com.android.systemui.mediaprojection.appselector.data.RecentTask;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CancellationException;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorController.class */
public final class MediaProjectionAppSelectorController {
    public final ComponentName appSelectorComponentName;
    public final RecentTaskListProvider recentTaskListProvider;
    public final CoroutineScope scope;
    public final MediaProjectionAppSelectorView view;

    public MediaProjectionAppSelectorController(RecentTaskListProvider recentTaskListProvider, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, CoroutineScope coroutineScope, ComponentName componentName) {
        this.recentTaskListProvider = recentTaskListProvider;
        this.view = mediaProjectionAppSelectorView;
        this.scope = coroutineScope;
        this.appSelectorComponentName = componentName;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortTasks$$inlined$sortedBy$1.compare(T, T):int] */
    public static final /* synthetic */ ComponentName access$getAppSelectorComponentName$p(MediaProjectionAppSelectorController mediaProjectionAppSelectorController) {
        return mediaProjectionAppSelectorController.appSelectorComponentName;
    }

    public final void destroy() {
        CoroutineScopeKt.cancel$default(this.scope, (CancellationException) null, 1, (Object) null);
    }

    public final void init() {
        BuildersKt.launch$default(this.scope, (CoroutineContext) null, (CoroutineStart) null, new MediaProjectionAppSelectorController$init$1(this, null), 3, (Object) null);
    }

    public final List<RecentTask> sortTasks(List<RecentTask> list) {
        return CollectionsKt___CollectionsKt.sortedWith(list, new Comparator() { // from class: com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController$sortTasks$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt__ComparisonsKt.compareValues(Boolean.valueOf(Intrinsics.areEqual(((RecentTask) t).getTopActivityComponent(), MediaProjectionAppSelectorController.access$getAppSelectorComponentName$p(MediaProjectionAppSelectorController.this))), Boolean.valueOf(Intrinsics.areEqual(((RecentTask) t2).getTopActivityComponent(), MediaProjectionAppSelectorController.access$getAppSelectorComponentName$p(MediaProjectionAppSelectorController.this))));
            }
        });
    }
}