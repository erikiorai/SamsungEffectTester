package com.android.systemui.dagger;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.TaskStackChangeListeners;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SharedLibraryModule.class */
public class SharedLibraryModule {
    public ActivityManagerWrapper provideActivityManagerWrapper() {
        return ActivityManagerWrapper.getInstance();
    }

    public DevicePolicyManagerWrapper provideDevicePolicyManagerWrapper() {
        return DevicePolicyManagerWrapper.getInstance();
    }

    public TaskStackChangeListeners provideTaskStackChangeListeners() {
        return TaskStackChangeListeners.getInstance();
    }
}