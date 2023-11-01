package com.android.keyguard;

import android.os.PowerManager;
import com.android.internal.logging.UiEventLogger;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/keyguard/FaceAuthUiEvent.class */
public enum FaceAuthUiEvent implements UiEventLogger.UiEventEnum {
    FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED(1146, "Face auth due to request from occluding app.", 0, 4, null),
    FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN(1147, "Face auth triggered due to finger down on UDFPS", 0, 4, null),
    FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER(1148, "Face auth due to swipe up on bouncer", 0, 4, null),
    FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD(1149, "Face auth requested when user reaches for the device on AoD.", 0, 4, null),
    FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET(1150, "Face auth due to face lockout reset.", 0, 4, null),
    FACE_AUTH_TRIGGERED_QS_EXPANDED(1151, "Face auth due to QS expansion.", 0, 4, null),
    FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED(1152, "Face auth due to notification panel click.", 0, 4, null),
    FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED(1153, "Face auth due to pickup gesture triggered when the device is awake and not from AOD.", 0, 4, null),
    FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN(1154, "Face auth due to alternate bouncer shown.", 0, 4, null),
    FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN(1155, "Face auth started/stopped due to primary bouncer shown.", 0, 4, null),
    FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN_OR_WILL_BE_SHOWN(1197, "Face auth started/stopped due to bouncer being shown or will be shown.", 0, 4, null),
    FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE(1156, "Face auth due to retry after hardware unavailable.", 0, 4, null),
    FACE_AUTH_TRIGGERED_TRUST_DISABLED(1158, "Face auth started due to trust disabled.", 0, 4, null),
    FACE_AUTH_STOPPED_TRUST_ENABLED(1173, "Face auth stopped due to trust enabled.", 0, 4, null),
    FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED(1159, "Face auth started/stopped due to keyguard occlusion change.", 0, 4, null),
    FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED(1160, "Face auth started/stopped due to assistant visibility change.", 0, 4, null),
    FACE_AUTH_UPDATED_STARTED_WAKING_UP { // from class: com.android.keyguard.FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP
        @Override // com.android.keyguard.FaceAuthUiEvent
        public String extraInfoToString() {
            return PowerManager.wakeReasonToString(getExtraInfo());
        }
    },
    FACE_AUTH_TRIGGERED_DREAM_STOPPED(1162, "Face auth due to dream stopped.", 0, 4, null),
    FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED(1163, "Face auth due to all authenticators registered.", 0, 4, null),
    FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED(1164, "Face auth due to enrolments changed.", 0, 4, null),
    FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED(1165, "Face auth stopped or started due to keyguard visibility changed.", 0, 4, null),
    FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED(1174, "Face auth stopped due to face cancel signal not received.", 0, 4, null),
    FACE_AUTH_TRIGGERED_DURING_CANCELLATION(1175, "Another request to start face auth received while cancelling face auth", 0, 4, null),
    FACE_AUTH_STOPPED_DREAM_STARTED(1176, "Face auth stopped because dreaming started", 0, 4, null),
    FACE_AUTH_STOPPED_FP_LOCKED_OUT(1177, "Face auth stopped because fp locked out", 0, 4, null),
    FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER(1178, "Face auth stopped because user started typing password/pin", 0, 4, null),
    FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY(1179, "Face auth stopped because keyguard going away", 0, 4, null),
    FACE_AUTH_UPDATED_CAMERA_LAUNCHED(1180, "Face auth started/stopped because camera launched", 0, 4, null),
    FACE_AUTH_UPDATED_FP_AUTHENTICATED(1181, "Face auth started/stopped because fingerprint launched", 0, 4, null),
    FACE_AUTH_UPDATED_GOING_TO_SLEEP(1182, "Face auth started/stopped because going to sleep", 0, 4, null),
    FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP(1183, "Face auth stopped because finished going to sleep", 0, 4, null),
    FACE_AUTH_UPDATED_ON_KEYGUARD_INIT(1189, "Face auth started/stopped because Keyguard is initialized", 0, 4, null),
    FACE_AUTH_UPDATED_KEYGUARD_RESET(1185, "Face auth started/stopped because Keyguard is reset", 0, 4, null),
    FACE_AUTH_UPDATED_USER_SWITCHING(1186, "Face auth started/stopped because user is switching", 0, 4, null),
    FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED(1187, "Face auth started/stopped because face is authenticated", 0, 4, null),
    FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD(1188, "Face auth started/stopped because biometric is enabled on keyguard", 0, 4, null),
    FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED(1255, "Face auth stopped because strong auth allowed changed", 0, 4, null),
    FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED(1256, "Face auth stopped because non strong biometric allowed changed", 0, 4, null);
    
    private int extraInfo;
    private final int id;
    private final String reason;

    FaceAuthUiEvent(int i, String str, int i2) {
        this.id = i;
        this.reason = str;
        this.extraInfo = i2;
    }

    /* synthetic */ FaceAuthUiEvent(int i, String str, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, str, (i3 & 4) != 0 ? 0 : i2);
    }

    public String extraInfoToString() {
        return "";
    }

    public final int getExtraInfo() {
        return this.extraInfo;
    }

    public int getId() {
        return this.id;
    }

    public final String getReason() {
        return this.reason;
    }

    public final void setExtraInfo(int i) {
        this.extraInfo = i;
    }
}