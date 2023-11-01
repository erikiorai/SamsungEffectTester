package com.android.systemui.controls.ui;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import com.android.systemui.controls.ui.SelectedItem;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/SelectionItem.class */
public final class SelectionItem {
    public final CharSequence appName;
    public final ComponentName componentName;
    public final Drawable icon;
    public final boolean isPanel;
    public final ComponentName panelComponentName;
    public final CharSequence structure;
    public final int uid;

    public SelectionItem(CharSequence charSequence, CharSequence charSequence2, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2) {
        this.appName = charSequence;
        this.structure = charSequence2;
        this.icon = drawable;
        this.componentName = componentName;
        this.uid = i;
        this.panelComponentName = componentName2;
        this.isPanel = componentName2 != null;
    }

    public static /* synthetic */ SelectionItem copy$default(SelectionItem selectionItem, CharSequence charSequence, CharSequence charSequence2, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charSequence = selectionItem.appName;
        }
        if ((i2 & 2) != 0) {
            charSequence2 = selectionItem.structure;
        }
        if ((i2 & 4) != 0) {
            drawable = selectionItem.icon;
        }
        if ((i2 & 8) != 0) {
            componentName = selectionItem.componentName;
        }
        if ((i2 & 16) != 0) {
            i = selectionItem.uid;
        }
        if ((i2 & 32) != 0) {
            componentName2 = selectionItem.panelComponentName;
        }
        return selectionItem.copy(charSequence, charSequence2, drawable, componentName, i, componentName2);
    }

    public final SelectionItem copy(CharSequence charSequence, CharSequence charSequence2, Drawable drawable, ComponentName componentName, int i, ComponentName componentName2) {
        return new SelectionItem(charSequence, charSequence2, drawable, componentName, i, componentName2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SelectionItem) {
            SelectionItem selectionItem = (SelectionItem) obj;
            return Intrinsics.areEqual(this.appName, selectionItem.appName) && Intrinsics.areEqual(this.structure, selectionItem.structure) && Intrinsics.areEqual(this.icon, selectionItem.icon) && Intrinsics.areEqual(this.componentName, selectionItem.componentName) && this.uid == selectionItem.uid && Intrinsics.areEqual(this.panelComponentName, selectionItem.panelComponentName);
        }
        return false;
    }

    public final CharSequence getAppName() {
        return this.appName;
    }

    public final ComponentName getComponentName() {
        return this.componentName;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public final ComponentName getPanelComponentName() {
        return this.panelComponentName;
    }

    public final CharSequence getStructure() {
        return this.structure;
    }

    public final CharSequence getTitle() {
        return this.structure.length() == 0 ? this.appName : this.structure;
    }

    public final int getUid() {
        return this.uid;
    }

    public int hashCode() {
        int hashCode = this.appName.hashCode();
        int hashCode2 = this.structure.hashCode();
        int hashCode3 = this.icon.hashCode();
        int hashCode4 = this.componentName.hashCode();
        int hashCode5 = Integer.hashCode(this.uid);
        ComponentName componentName = this.panelComponentName;
        return (((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + (componentName == null ? 0 : componentName.hashCode());
    }

    public final boolean isPanel() {
        return this.isPanel;
    }

    public final boolean matches(SelectedItem selectedItem) {
        if (Intrinsics.areEqual(this.componentName, selectedItem.getComponentName())) {
            if (this.isPanel || (selectedItem instanceof SelectedItem.PanelItem)) {
                return true;
            }
            return Intrinsics.areEqual(this.structure, ((SelectedItem.StructureItem) selectedItem).getStructure().getStructure());
        }
        return false;
    }

    public String toString() {
        CharSequence charSequence = this.appName;
        CharSequence charSequence2 = this.structure;
        Drawable drawable = this.icon;
        ComponentName componentName = this.componentName;
        int i = this.uid;
        ComponentName componentName2 = this.panelComponentName;
        return "SelectionItem(appName=" + ((Object) charSequence) + ", structure=" + ((Object) charSequence2) + ", icon=" + drawable + ", componentName=" + componentName + ", uid=" + i + ", panelComponentName=" + componentName2 + ")";
    }
}