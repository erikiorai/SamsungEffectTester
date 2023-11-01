package com.android.systemui.camera;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.android.systemui.R$string;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraIntents.class */
public final class CameraIntents {
    public static final Companion Companion = new Companion(null);
    public static final String DEFAULT_SECURE_CAMERA_INTENT_ACTION = "android.media.action.STILL_IMAGE_CAMERA_SECURE";
    public static final String DEFAULT_INSECURE_CAMERA_INTENT_ACTION = "android.media.action.STILL_IMAGE_CAMERA";

    /* loaded from: mainsysui33.jar:com/android/systemui/camera/CameraIntents$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getDEFAULT_INSECURE_CAMERA_INTENT_ACTION() {
            return CameraIntents.DEFAULT_INSECURE_CAMERA_INTENT_ACTION;
        }

        public final String getDEFAULT_SECURE_CAMERA_INTENT_ACTION() {
            return CameraIntents.DEFAULT_SECURE_CAMERA_INTENT_ACTION;
        }

        public final Intent getInsecureCameraIntent(Context context) {
            Intent intent = new Intent(getDEFAULT_INSECURE_CAMERA_INTENT_ACTION());
            String overrideCameraPackage = getOverrideCameraPackage(context);
            if (overrideCameraPackage != null) {
                intent.setPackage(overrideCameraPackage);
            }
            return intent;
        }

        public final String getOverrideCameraPackage(Context context) {
            String string = context.getResources().getString(R$string.config_cameraGesturePackage);
            if (string == null || TextUtils.isEmpty(string)) {
                return null;
            }
            return string;
        }

        public final Intent getSecureCameraIntent(Context context) {
            Intent intent = new Intent(getDEFAULT_SECURE_CAMERA_INTENT_ACTION());
            String overrideCameraPackage = getOverrideCameraPackage(context);
            if (overrideCameraPackage != null) {
                intent.setPackage(overrideCameraPackage);
            }
            return intent.addFlags(8388608);
        }

        public final boolean isInsecureCameraIntent(Intent intent) {
            String action;
            return (intent == null || (action = intent.getAction()) == null) ? false : action.equals(getDEFAULT_INSECURE_CAMERA_INTENT_ACTION());
        }

        public final boolean isSecureCameraIntent(Intent intent) {
            String action;
            return (intent == null || (action = intent.getAction()) == null) ? false : action.equals(getDEFAULT_SECURE_CAMERA_INTENT_ACTION());
        }
    }

    public static final Intent getInsecureCameraIntent(Context context) {
        return Companion.getInsecureCameraIntent(context);
    }

    public static final String getOverrideCameraPackage(Context context) {
        return Companion.getOverrideCameraPackage(context);
    }

    public static final Intent getSecureCameraIntent(Context context) {
        return Companion.getSecureCameraIntent(context);
    }

    public static final boolean isInsecureCameraIntent(Intent intent) {
        return Companion.isInsecureCameraIntent(intent);
    }
}