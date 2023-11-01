package com.android.systemui.media.controls.util;

import android.content.Context;
import com.android.systemui.util.Utils;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaFeatureFlag.class */
public final class MediaFeatureFlag {
    public final Context context;

    public MediaFeatureFlag(Context context) {
        this.context = context;
    }

    public final boolean getEnabled() {
        return Utils.useQsMediaPlayer(this.context);
    }
}