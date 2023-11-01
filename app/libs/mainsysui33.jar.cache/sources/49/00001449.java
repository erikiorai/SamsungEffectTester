package com.android.systemui.controls.management;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/StructureContainer.class */
public final class StructureContainer {
    public final ControlsModel model;
    public final CharSequence structureName;

    public StructureContainer(CharSequence charSequence, ControlsModel controlsModel) {
        this.structureName = charSequence;
        this.model = controlsModel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StructureContainer) {
            StructureContainer structureContainer = (StructureContainer) obj;
            return Intrinsics.areEqual(this.structureName, structureContainer.structureName) && Intrinsics.areEqual(this.model, structureContainer.model);
        }
        return false;
    }

    public final ControlsModel getModel() {
        return this.model;
    }

    public final CharSequence getStructureName() {
        return this.structureName;
    }

    public int hashCode() {
        return (this.structureName.hashCode() * 31) + this.model.hashCode();
    }

    public String toString() {
        CharSequence charSequence = this.structureName;
        ControlsModel controlsModel = this.model;
        return "StructureContainer(structureName=" + ((Object) charSequence) + ", model=" + controlsModel + ")";
    }
}