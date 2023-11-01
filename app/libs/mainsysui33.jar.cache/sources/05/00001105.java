package com.android.systemui.appops;

import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/appops/AppOpsController.class */
public interface AppOpsController {

    /* loaded from: mainsysui33.jar:com/android/systemui/appops/AppOpsController$Callback.class */
    public interface Callback {
        void onActiveStateChanged(int i, int i2, String str, boolean z);
    }

    void addCallback(int[] iArr, Callback callback);

    List<AppOpItem> getActiveAppOps();

    List<AppOpItem> getActiveAppOps(boolean z);

    boolean isMicMuted();

    void removeCallback(int[] iArr, Callback callback);
}