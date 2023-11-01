package com.android.systemui.mediaprojection.appselector.data;

import java.util.List;
import kotlin.coroutines.Continuation;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/RecentTaskListProvider.class */
public interface RecentTaskListProvider {
    Object loadRecentTasks(Continuation<? super List<RecentTask>> continuation);
}