package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.recents.model.ThumbnailData;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ActivityTaskManagerThumbnailLoader.class */
public final class ActivityTaskManagerThumbnailLoader implements RecentTaskThumbnailLoader {
    public final ActivityManagerWrapper activityManager;
    public final CoroutineDispatcher coroutineDispatcher;

    public ActivityTaskManagerThumbnailLoader(CoroutineDispatcher coroutineDispatcher, ActivityManagerWrapper activityManagerWrapper) {
        this.coroutineDispatcher = coroutineDispatcher;
        this.activityManager = activityManagerWrapper;
    }

    @Override // com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader
    public Object loadThumbnail(int i, Continuation<? super ThumbnailData> continuation) {
        return BuildersKt.withContext(this.coroutineDispatcher, new ActivityTaskManagerThumbnailLoader$loadThumbnail$2(this, i, null), continuation);
    }
}