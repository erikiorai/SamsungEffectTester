package com.android.systemui.controls.ui;

import android.content.ComponentName;
import com.android.systemui.controls.controller.StructureInfo;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/SelectedItem.class */
public abstract class SelectedItem {
    public static final Companion Companion = new Companion(null);
    public static final SelectedItem EMPTY_SELECTION = new StructureItem(StructureInfo.Companion.getEMPTY_STRUCTURE());

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/SelectedItem$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SelectedItem getEMPTY_SELECTION() {
            return SelectedItem.EMPTY_SELECTION;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/SelectedItem$PanelItem.class */
    public static final class PanelItem extends SelectedItem {
        public final CharSequence appName;
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;

        public PanelItem(CharSequence charSequence, ComponentName componentName) {
            super(null);
            this.appName = charSequence;
            this.componentName = componentName;
            this.name = charSequence;
            this.hasControls = true;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof PanelItem) {
                PanelItem panelItem = (PanelItem) obj;
                return Intrinsics.areEqual(this.appName, panelItem.appName) && Intrinsics.areEqual(getComponentName(), panelItem.getComponentName());
            }
            return false;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public boolean getHasControls() {
            return this.hasControls;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public CharSequence getName() {
            return this.name;
        }

        public int hashCode() {
            return (this.appName.hashCode() * 31) + getComponentName().hashCode();
        }

        public String toString() {
            CharSequence charSequence = this.appName;
            ComponentName componentName = getComponentName();
            return "PanelItem(appName=" + ((Object) charSequence) + ", componentName=" + componentName + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/SelectedItem$StructureItem.class */
    public static final class StructureItem extends SelectedItem {
        public final ComponentName componentName;
        public final boolean hasControls;
        public final CharSequence name;
        public final StructureInfo structure;

        public StructureItem(StructureInfo structureInfo) {
            super(null);
            this.structure = structureInfo;
            this.name = structureInfo.getStructure();
            this.hasControls = !structureInfo.getControls().isEmpty();
            this.componentName = structureInfo.getComponentName();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof StructureItem) && Intrinsics.areEqual(this.structure, ((StructureItem) obj).structure);
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public ComponentName getComponentName() {
            return this.componentName;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public boolean getHasControls() {
            return this.hasControls;
        }

        @Override // com.android.systemui.controls.ui.SelectedItem
        public CharSequence getName() {
            return this.name;
        }

        public final StructureInfo getStructure() {
            return this.structure;
        }

        public int hashCode() {
            return this.structure.hashCode();
        }

        public String toString() {
            StructureInfo structureInfo = this.structure;
            return "StructureItem(structure=" + structureInfo + ")";
        }
    }

    public SelectedItem() {
    }

    public /* synthetic */ SelectedItem(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ComponentName getComponentName();

    public abstract boolean getHasControls();

    public abstract CharSequence getName();
}