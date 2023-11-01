package com.android.systemui.controls.controller;

import android.content.ComponentName;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/StructureInfo.class */
public final class StructureInfo {
    public static final Companion Companion = new Companion(null);
    public static final ComponentName EMPTY_COMPONENT;
    public static final StructureInfo EMPTY_STRUCTURE;
    public final ComponentName componentName;
    public final List<ControlInfo> controls;
    public final CharSequence structure;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/StructureInfo$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ComponentName getEMPTY_COMPONENT() {
            return StructureInfo.EMPTY_COMPONENT;
        }

        public final StructureInfo getEMPTY_STRUCTURE() {
            return StructureInfo.EMPTY_STRUCTURE;
        }
    }

    static {
        ComponentName componentName = new ComponentName("", "");
        EMPTY_COMPONENT = componentName;
        EMPTY_STRUCTURE = new StructureInfo(componentName, "", new ArrayList());
    }

    public StructureInfo(ComponentName componentName, CharSequence charSequence, List<ControlInfo> list) {
        this.componentName = componentName;
        this.structure = charSequence;
        this.controls = list;
    }

    public static /* synthetic */ StructureInfo copy$default(StructureInfo structureInfo, ComponentName componentName, CharSequence charSequence, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            componentName = structureInfo.componentName;
        }
        if ((i & 2) != 0) {
            charSequence = structureInfo.structure;
        }
        if ((i & 4) != 0) {
            list = structureInfo.controls;
        }
        return structureInfo.copy(componentName, charSequence, list);
    }

    public final StructureInfo copy(ComponentName componentName, CharSequence charSequence, List<ControlInfo> list) {
        return new StructureInfo(componentName, charSequence, list);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StructureInfo) {
            StructureInfo structureInfo = (StructureInfo) obj;
            return Intrinsics.areEqual(this.componentName, structureInfo.componentName) && Intrinsics.areEqual(this.structure, structureInfo.structure) && Intrinsics.areEqual(this.controls, structureInfo.controls);
        }
        return false;
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final List<ControlInfo> getControls() {
        return this.controls;
    }

    public final CharSequence getStructure() {
        return this.structure;
    }

    public int hashCode() {
        return (((this.componentName.hashCode() * 31) + this.structure.hashCode()) * 31) + this.controls.hashCode();
    }

    public String toString() {
        ComponentName componentName = this.componentName;
        CharSequence charSequence = this.structure;
        List<ControlInfo> list = this.controls;
        return "StructureInfo(componentName=" + componentName + ", structure=" + ((Object) charSequence) + ", controls=" + list + ")";
    }
}