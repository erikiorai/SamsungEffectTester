package com.android.systemui.controls.ui;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlKey.class */
public final class ControlKey {
    public final ComponentName componentName;
    public final String controlId;

    public ControlKey(ComponentName componentName, String str) {
        this.componentName = componentName;
        this.controlId = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ControlKey) {
            ControlKey controlKey = (ControlKey) obj;
            return Intrinsics.areEqual(this.componentName, controlKey.componentName) && Intrinsics.areEqual(this.controlId, controlKey.controlId);
        }
        return false;
    }

    public int hashCode() {
        return (this.componentName.hashCode() * 31) + this.controlId.hashCode();
    }

    public String toString() {
        ComponentName componentName = this.componentName;
        String str = this.controlId;
        return "ControlKey(componentName=" + componentName + ", controlId=" + str + ")";
    }
}