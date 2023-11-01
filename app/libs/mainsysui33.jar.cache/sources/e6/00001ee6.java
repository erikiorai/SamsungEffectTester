package com.android.systemui.notetask;

import android.content.pm.ActivityInfo;

/* loaded from: mainsysui33.jar:com/android/systemui/notetask/NoteTaskIntentResolverKt.class */
public final class NoteTaskIntentResolverKt {
    public static final boolean getShowWhenLocked(ActivityInfo activityInfo) {
        return (activityInfo.flags & 8388608) != 0;
    }

    public static final boolean getTurnScreenOn(ActivityInfo activityInfo) {
        return (activityInfo.flags & 16777216) != 0;
    }
}