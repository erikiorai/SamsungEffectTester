package com.android.systemui.media.controls.pipeline;

import android.os.SystemProperties;
import com.android.internal.annotations.VisibleForTesting;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaTimeoutListenerKt.class */
public final class MediaTimeoutListenerKt {
    public static final long PAUSED_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout", TimeUnit.MINUTES.toMillis(10));
    public static final long RESUME_MEDIA_TIMEOUT = SystemProperties.getLong("debug.sysui.media_timeout_resume", TimeUnit.DAYS.toMillis(3));

    public static final long getPAUSED_MEDIA_TIMEOUT() {
        return PAUSED_MEDIA_TIMEOUT;
    }

    @VisibleForTesting
    public static /* synthetic */ void getPAUSED_MEDIA_TIMEOUT$annotations() {
    }

    public static final long getRESUME_MEDIA_TIMEOUT() {
        return RESUME_MEDIA_TIMEOUT;
    }

    @VisibleForTesting
    public static /* synthetic */ void getRESUME_MEDIA_TIMEOUT$annotations() {
    }
}