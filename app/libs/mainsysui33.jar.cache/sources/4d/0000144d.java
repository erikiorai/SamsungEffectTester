package com.android.systemui.controls.settings;

import android.content.Context;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsDialogManager.class */
public interface ControlsSettingsDialogManager {
    void closeDialog();

    void maybeShowDialog(Context context, Function0<Unit> function0);
}