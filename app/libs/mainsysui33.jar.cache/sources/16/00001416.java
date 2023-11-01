package com.android.systemui.controls.management;

import android.content.ComponentName;
import com.android.systemui.controls.ControlsServiceInfo;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.systemui.util.UserAwareController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingController.class */
public interface ControlsListingController extends CallbackController<ControlsListingCallback>, UserAwareController {

    @FunctionalInterface
    /* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingController$ControlsListingCallback.class */
    public interface ControlsListingCallback {
        void onServicesUpdated(List<ControlsServiceInfo> list);
    }

    CharSequence getAppLabel(ComponentName componentName);

    List<ControlsServiceInfo> getCurrentServices();
}