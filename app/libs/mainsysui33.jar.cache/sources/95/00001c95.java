package com.android.systemui.media.controls.ui;

import android.content.Intent;
import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaCarouselControllerKt.class */
public final class MediaCarouselControllerKt {
    public static final Intent settingsIntent = new Intent().setAction("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    public static final boolean DEBUG = Log.isLoggable("MediaCarouselController", 3);
}