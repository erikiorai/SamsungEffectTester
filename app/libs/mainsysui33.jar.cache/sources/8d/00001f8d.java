package com.android.systemui.plugins;

import android.content.Context;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/Plugin.class */
public interface Plugin {
    default int getVersion() {
        return -1;
    }

    default void onCreate(Context context, Context context2) {
    }

    default void onDestroy() {
    }
}