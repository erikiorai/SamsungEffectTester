package com.android.systemui.plugins;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPluginDebugFactory.class */
public final class PluginsModule_ProvidesPluginDebugFactory implements Factory<Boolean> {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPluginDebugFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        private static final PluginsModule_ProvidesPluginDebugFactory INSTANCE = new PluginsModule_ProvidesPluginDebugFactory();

        private InstanceHolder() {
        }
    }

    public static PluginsModule_ProvidesPluginDebugFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean providesPluginDebug() {
        return PluginsModule.providesPluginDebug();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3581get() {
        return Boolean.valueOf(providesPluginDebug());
    }
}