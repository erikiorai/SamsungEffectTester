package com.android.systemui.controls.ui;

import android.content.Context;
import android.service.controls.Control;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlActionCoordinator.class */
public interface ControlActionCoordinator {
    void closeDialogs();

    void drag(boolean z);

    void enableActionOnTouch(String str);

    void longPress(ControlViewHolder controlViewHolder);

    void setActivityContext(Context context);

    void setValue(ControlViewHolder controlViewHolder, String str, float f);

    void toggle(ControlViewHolder controlViewHolder, String str, boolean z);

    void touch(ControlViewHolder controlViewHolder, String str, Control control);
}