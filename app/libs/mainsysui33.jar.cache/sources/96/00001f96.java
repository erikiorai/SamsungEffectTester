package com.android.systemui.plugins;

import android.text.TextUtils;
import com.android.systemui.plugins.annotations.ProvidesInterface;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginManager.class */
public interface PluginManager {
    public static final String NOTIFICATION_CHANNEL_ID = "ALR";
    public static final String PLUGIN_CHANGED = "com.android.systemui.action.PLUGIN_CHANGED";

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginManager$Helper.class */
    public static class Helper {
        public static <P> String getAction(Class<P> cls) {
            ProvidesInterface providesInterface = (ProvidesInterface) cls.getDeclaredAnnotation(ProvidesInterface.class);
            if (providesInterface == null) {
                throw new RuntimeException(cls + " doesn't provide an interface");
            } else if (TextUtils.isEmpty(providesInterface.action())) {
                throw new RuntimeException(cls + " doesn't provide an action");
            } else {
                return providesInterface.action();
            }
        }
    }

    <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls);

    <T extends Plugin> void addPluginListener(PluginListener<T> pluginListener, Class<T> cls, boolean z);

    <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls);

    <T extends Plugin> void addPluginListener(String str, PluginListener<T> pluginListener, Class<T> cls, boolean z);

    <T> boolean dependsOn(Plugin plugin, Class<T> cls);

    String[] getPrivilegedPlugins();

    void removePluginListener(PluginListener<?> pluginListener);
}