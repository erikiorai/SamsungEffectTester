package com.android.systemui.screenshot;

import android.content.ComponentName;
import android.graphics.Rect;
import android.os.UserHandle;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicy.class */
public interface ScreenshotPolicy {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotPolicy$DisplayContentInfo.class */
    public static final class DisplayContentInfo {
        public final Rect bounds;
        public final ComponentName component;
        public final int taskId;
        public final UserHandle user;

        public DisplayContentInfo(ComponentName componentName, Rect rect, UserHandle userHandle, int i) {
            this.component = componentName;
            this.bounds = rect;
            this.user = userHandle;
            this.taskId = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof DisplayContentInfo) {
                DisplayContentInfo displayContentInfo = (DisplayContentInfo) obj;
                return Intrinsics.areEqual(this.component, displayContentInfo.component) && Intrinsics.areEqual(this.bounds, displayContentInfo.bounds) && Intrinsics.areEqual(this.user, displayContentInfo.user) && this.taskId == displayContentInfo.taskId;
            }
            return false;
        }

        public final Rect getBounds() {
            return this.bounds;
        }

        public final ComponentName getComponent() {
            return this.component;
        }

        public final int getTaskId() {
            return this.taskId;
        }

        public final UserHandle getUser() {
            return this.user;
        }

        public int hashCode() {
            return (((((this.component.hashCode() * 31) + this.bounds.hashCode()) * 31) + this.user.hashCode()) * 31) + Integer.hashCode(this.taskId);
        }

        public String toString() {
            ComponentName componentName = this.component;
            Rect rect = this.bounds;
            UserHandle userHandle = this.user;
            int i = this.taskId;
            return "DisplayContentInfo(component=" + componentName + ", bounds=" + rect + ", user=" + userHandle + ", taskId=" + i + ")";
        }
    }

    Object findPrimaryContent(int i, Continuation<? super DisplayContentInfo> continuation);

    int getDefaultDisplayId();

    Object isManagedProfile(int i, Continuation<? super Boolean> continuation);
}