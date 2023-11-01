package com.android.systemui.controls.controller;

import android.content.ComponentName;
import android.service.controls.Control;
import android.service.controls.actions.ControlAction;
import com.android.systemui.util.UserAwareController;
import java.util.List;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingController.class */
public interface ControlsBindingController extends UserAwareController {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsBindingController$LoadCallback.class */
    public interface LoadCallback extends Consumer<List<? extends Control>> {
        void error(String str);
    }

    void action(ComponentName componentName, ControlInfo controlInfo, ControlAction controlAction);

    Runnable bindAndLoad(ComponentName componentName, LoadCallback loadCallback);

    void bindAndLoadSuggested(ComponentName componentName, LoadCallback loadCallback);

    void onComponentRemoved(ComponentName componentName);

    void subscribe(StructureInfo structureInfo);

    void unsubscribe();
}