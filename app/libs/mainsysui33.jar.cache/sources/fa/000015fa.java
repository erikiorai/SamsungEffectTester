package com.android.systemui.demomode;

import android.os.Bundle;

/* loaded from: mainsysui33.jar:com/android/systemui/demomode/DemoModeCommandReceiver.class */
public interface DemoModeCommandReceiver {
    void dispatchDemoCommand(String str, Bundle bundle);

    default void onDemoModeFinished() {
    }

    default void onDemoModeStarted() {
    }
}