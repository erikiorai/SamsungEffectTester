package com.android.systemui.media.taptotransfer;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttFlags.class */
public final class MediaTttFlags {
    public final FeatureFlags featureFlags;

    public MediaTttFlags(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }

    public final boolean isMediaTttEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_TAP_TO_TRANSFER());
    }

    public final boolean isMediaTttReceiverSuccessRippleEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_TTT_RECEIVER_SUCCESS_RIPPLE());
    }
}