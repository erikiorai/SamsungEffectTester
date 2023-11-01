package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.recents.model.ThumbnailData;
import kotlin.coroutines.Continuation;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/RecentTaskThumbnailLoader.class */
public interface RecentTaskThumbnailLoader {
    Object loadThumbnail(int i, Continuation<? super ThumbnailData> continuation);
}