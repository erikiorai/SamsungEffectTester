package com.android.systemui.media.controls.util;

import android.app.StatusBarManager;
import android.os.UserHandle;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaFlags.class */
public final class MediaFlags {
    public final FeatureFlags featureFlags;

    public MediaFlags(FeatureFlags featureFlags) {
        this.featureFlags = featureFlags;
    }

    public final boolean areMediaSessionActionsEnabled(String str, UserHandle userHandle) {
        return StatusBarManager.useMediaSessionActionsForApp(str, userHandle) || this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_SESSION_ACTIONS());
    }

    public final boolean areMuteAwaitConnectionsEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_MUTE_AWAIT());
    }

    public final boolean areNearbyMediaDevicesEnabled() {
        return this.featureFlags.isEnabled(Flags.INSTANCE.getMEDIA_NEARBY_DEVICES());
    }
}