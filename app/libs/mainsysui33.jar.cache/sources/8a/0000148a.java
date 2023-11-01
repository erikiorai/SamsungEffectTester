package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.service.controls.Control;
import com.android.systemui.controls.controller.ControlInfo;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlWithState.class */
public final class ControlWithState {
    public final ControlInfo ci;
    public final ComponentName componentName;
    public final Control control;

    public ControlWithState(ComponentName componentName, ControlInfo controlInfo, Control control) {
        this.componentName = componentName;
        this.ci = controlInfo;
        this.control = control;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ControlWithState) {
            ControlWithState controlWithState = (ControlWithState) obj;
            return Intrinsics.areEqual(this.componentName, controlWithState.componentName) && Intrinsics.areEqual(this.ci, controlWithState.ci) && Intrinsics.areEqual(this.control, controlWithState.control);
        }
        return false;
    }

    public final ControlInfo getCi() {
        return this.ci;
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final Control getControl() {
        return this.control;
    }

    public int hashCode() {
        int hashCode = this.componentName.hashCode();
        int hashCode2 = this.ci.hashCode();
        Control control = this.control;
        return (((hashCode * 31) + hashCode2) * 31) + (control == null ? 0 : control.hashCode());
    }

    public String toString() {
        ComponentName componentName = this.componentName;
        ControlInfo controlInfo = this.ci;
        Control control = this.control;
        return "ControlWithState(componentName=" + componentName + ", ci=" + controlInfo + ", control=" + control + ")";
    }
}