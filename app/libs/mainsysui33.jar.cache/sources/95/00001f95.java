package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.plugins.Plugin;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginListener.class */
public interface PluginListener<T extends Plugin> {
    void onPluginConnected(T t, Context context);

    default void onPluginDisconnected(T t) {
    }
}