package com.android.systemui.plugins;

import android.graphics.drawable.Drawable;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockProvider.class */
public interface ClockProvider {
    ClockController createClock(String str);

    Drawable getClockThumbnail(String str);

    List<ClockMetadata> getClocks();
}