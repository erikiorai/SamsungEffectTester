package com.android.systemui.mediaprojection.appselector.data;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/RecentTask.class */
public final class RecentTask {
    public final ComponentName baseIntentComponent;
    public final Integer colorBackground;
    public final int taskId;
    public final ComponentName topActivityComponent;
    public final int userId;

    public RecentTask(int i, int i2, ComponentName componentName, ComponentName componentName2, Integer num) {
        this.taskId = i;
        this.userId = i2;
        this.topActivityComponent = componentName;
        this.baseIntentComponent = componentName2;
        this.colorBackground = num;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof RecentTask) {
            RecentTask recentTask = (RecentTask) obj;
            return this.taskId == recentTask.taskId && this.userId == recentTask.userId && Intrinsics.areEqual(this.topActivityComponent, recentTask.topActivityComponent) && Intrinsics.areEqual(this.baseIntentComponent, recentTask.baseIntentComponent) && Intrinsics.areEqual(this.colorBackground, recentTask.colorBackground);
        }
        return false;
    }

    public final ComponentName getBaseIntentComponent() {
        return this.baseIntentComponent;
    }

    public final Integer getColorBackground() {
        return this.colorBackground;
    }

    public final int getTaskId() {
        return this.taskId;
    }

    public final ComponentName getTopActivityComponent() {
        return this.topActivityComponent;
    }

    public final int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.taskId);
        int hashCode2 = Integer.hashCode(this.userId);
        ComponentName componentName = this.topActivityComponent;
        int i = 0;
        int hashCode3 = componentName == null ? 0 : componentName.hashCode();
        ComponentName componentName2 = this.baseIntentComponent;
        int hashCode4 = componentName2 == null ? 0 : componentName2.hashCode();
        Integer num = this.colorBackground;
        if (num != null) {
            i = num.hashCode();
        }
        return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
    }

    public String toString() {
        int i = this.taskId;
        int i2 = this.userId;
        ComponentName componentName = this.topActivityComponent;
        ComponentName componentName2 = this.baseIntentComponent;
        Integer num = this.colorBackground;
        return "RecentTask(taskId=" + i + ", userId=" + i2 + ", topActivityComponent=" + componentName + ", baseIntentComponent=" + componentName2 + ", colorBackground=" + num + ")";
    }
}