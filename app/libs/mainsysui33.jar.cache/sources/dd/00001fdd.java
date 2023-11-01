package com.android.systemui.plugins.statusbar;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(version = 1)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/statusbar/DozeParameters.class */
public interface DozeParameters {
    public static final int VERSION = 1;

    boolean shouldControlScreenOff();
}