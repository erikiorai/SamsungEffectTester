package com.android.systemui.camera;

import android.content.Context;
import android.content.Intent;

/* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraIntentsWrapper.class */
public final class CameraIntentsWrapper {
    public final Context context;

    public CameraIntentsWrapper(Context context) {
        this.context = context;
    }

    public final Intent getInsecureCameraIntent() {
        return CameraIntents.Companion.getInsecureCameraIntent(this.context);
    }

    public final Intent getSecureCameraIntent() {
        return CameraIntents.Companion.getSecureCameraIntent(this.context);
    }
}