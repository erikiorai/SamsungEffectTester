package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.content.Context;
import android.service.controls.Control;
import android.view.ViewGroup;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiController.class */
public interface ControlsUiController {
    SelectedItem getPreferredSelectedItem(List<StructureInfo> list);

    void hide();

    void onActionResponse(ComponentName componentName, String str, int i);

    void onRefreshState(ComponentName componentName, List<Control> list);

    Class<?> resolveActivity();

    void show(ViewGroup viewGroup, Runnable runnable, Context context);
}