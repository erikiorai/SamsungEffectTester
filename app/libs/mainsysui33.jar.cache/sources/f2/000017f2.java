package com.android.systemui.flags;

import com.android.systemui.util.DeviceConfigProxy;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReaderModule.class */
public interface ServerFlagReaderModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/ServerFlagReaderModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String SYSUI_NAMESPACE = "systemui";

        public final ServerFlagReader bindsReader(DeviceConfigProxy deviceConfigProxy, Executor executor) {
            return new ServerFlagReaderImpl(SYSUI_NAMESPACE, deviceConfigProxy, executor);
        }
    }

    static ServerFlagReader bindsReader(DeviceConfigProxy deviceConfigProxy, Executor executor) {
        return Companion.bindsReader(deviceConfigProxy, executor);
    }
}