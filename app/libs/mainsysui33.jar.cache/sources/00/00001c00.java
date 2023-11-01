package com.android.systemui.media.controls.pipeline;

import android.os.SystemProperties;
import com.android.internal.annotations.VisibleForTesting;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataFilterKt.class */
public final class MediaDataFilterKt {
    public static final long SMARTSPACE_MAX_AGE = SystemProperties.getLong("debug.sysui.smartspace_max_age", TimeUnit.MINUTES.toMillis(30));

    public static final long getSMARTSPACE_MAX_AGE() {
        return SMARTSPACE_MAX_AGE;
    }

    @VisibleForTesting
    public static /* synthetic */ void getSMARTSPACE_MAX_AGE$annotations() {
    }
}