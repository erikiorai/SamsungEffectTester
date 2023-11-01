package com.android.systemui.screenshot;

import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.os.UserHandle;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.screenshot.ScreenshotPolicy;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicyImplKt.class */
public final class ScreenshotPolicyImplKt {
    @VisibleForTesting
    public static final ScreenshotPolicy.DisplayContentInfo toDisplayContentInfo(ActivityTaskManager.RootTaskInfo rootTaskInfo) {
        ComponentName componentName = rootTaskInfo.topActivity;
        if (componentName != null) {
            int[] iArr = rootTaskInfo.childTaskIds;
            int length = iArr.length - 1;
            return new ScreenshotPolicy.DisplayContentInfo(componentName, rootTaskInfo.childTaskBounds[length], UserHandle.of(rootTaskInfo.childTaskUserIds[length]), iArr[length]);
        }
        throw new IllegalStateException("should not be null".toString());
    }
}