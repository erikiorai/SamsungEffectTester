package com.android.keyguard;

import java.util.Map;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/keyguard/FaceAuthReasonKt.class */
public final class FaceAuthReasonKt {
    public static final Map<String, FaceAuthUiEvent> apiRequestReasonToUiEvent = MapsKt__MapsKt.mapOf(new Pair[]{TuplesKt.to("Face auth due to swipe up on bouncer", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_SWIPE_UP_ON_BOUNCER), TuplesKt.to("Face auth triggered due to finger down on UDFPS", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_UDFPS_POINTER_DOWN), TuplesKt.to("Face auth due to notification panel click.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_NOTIFICATION_PANEL_CLICKED), TuplesKt.to("Face auth due to QS expansion.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_QS_EXPANDED), TuplesKt.to("Face auth due to pickup gesture triggered when the device is awake and not from AOD.", FaceAuthUiEvent.FACE_AUTH_TRIGGERED_PICK_UP_GESTURE_TRIGGERED)});

    public static final FaceAuthUiEvent apiRequestReasonToUiEvent(String str) {
        FaceAuthUiEvent faceAuthUiEvent = apiRequestReasonToUiEvent.get(str);
        Intrinsics.checkNotNull(faceAuthUiEvent);
        return faceAuthUiEvent;
    }
}