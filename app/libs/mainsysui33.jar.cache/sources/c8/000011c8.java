package com.android.systemui.biometrics;

import android.content.res.Resources;
import com.android.keyguard.logging.FaceMessageDeferralLogger;
import com.android.systemui.R$array;
import com.android.systemui.R$dimen;
import com.android.systemui.dump.DumpManager;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/FaceHelpMessageDeferral.class */
public final class FaceHelpMessageDeferral extends BiometricMessageDeferral {
    public FaceHelpMessageDeferral(Resources resources, FaceMessageDeferralLogger faceMessageDeferralLogger, DumpManager dumpManager) {
        super(ArraysKt___ArraysKt.toHashSet(resources.getIntArray(R$array.config_face_help_msgs_defer_until_timeout)), resources.getFloat(R$dimen.config_face_help_msgs_defer_until_timeout_threshold), faceMessageDeferralLogger, dumpManager);
    }
}