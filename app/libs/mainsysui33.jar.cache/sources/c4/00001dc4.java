package com.android.systemui.mediaprojection.appselector;

import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorComponent.class */
public interface MediaProjectionAppSelectorComponent {

    /* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorComponent$Factory.class */
    public interface Factory {
        MediaProjectionAppSelectorComponent create(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler);
    }

    ConfigurationController getConfigurationController();

    MediaProjectionAppSelectorController getController();

    MediaProjectionRecentsViewController getRecentsViewController();
}