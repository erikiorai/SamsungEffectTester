package com.android.systemui.keyguard.shared.model;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/KeyguardQuickAffordancePickerRepresentation.class */
public final class KeyguardQuickAffordancePickerRepresentation {
    public final String actionComponentName;
    public final String actionText;
    public final int iconResourceId;
    public final String id;
    public final List<String> instructions;
    public final boolean isEnabled;
    public final String name;

    public KeyguardQuickAffordancePickerRepresentation(String str, String str2, int i, boolean z, List<String> list, String str3, String str4) {
        this.id = str;
        this.name = str2;
        this.iconResourceId = i;
        this.isEnabled = z;
        this.instructions = list;
        this.actionText = str3;
        this.actionComponentName = str4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardQuickAffordancePickerRepresentation) {
            KeyguardQuickAffordancePickerRepresentation keyguardQuickAffordancePickerRepresentation = (KeyguardQuickAffordancePickerRepresentation) obj;
            return Intrinsics.areEqual(this.id, keyguardQuickAffordancePickerRepresentation.id) && Intrinsics.areEqual(this.name, keyguardQuickAffordancePickerRepresentation.name) && this.iconResourceId == keyguardQuickAffordancePickerRepresentation.iconResourceId && this.isEnabled == keyguardQuickAffordancePickerRepresentation.isEnabled && Intrinsics.areEqual(this.instructions, keyguardQuickAffordancePickerRepresentation.instructions) && Intrinsics.areEqual(this.actionText, keyguardQuickAffordancePickerRepresentation.actionText) && Intrinsics.areEqual(this.actionComponentName, keyguardQuickAffordancePickerRepresentation.actionComponentName);
        }
        return false;
    }

    public final String getActionComponentName() {
        return this.actionComponentName;
    }

    public final String getActionText() {
        return this.actionText;
    }

    public final int getIconResourceId() {
        return this.iconResourceId;
    }

    public final String getId() {
        return this.id;
    }

    public final List<String> getInstructions() {
        return this.instructions;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        int hashCode = this.id.hashCode();
        int hashCode2 = this.name.hashCode();
        int hashCode3 = Integer.hashCode(this.iconResourceId);
        boolean z = this.isEnabled;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        List<String> list = this.instructions;
        int i2 = 0;
        int hashCode4 = list == null ? 0 : list.hashCode();
        String str = this.actionText;
        int hashCode5 = str == null ? 0 : str.hashCode();
        String str2 = this.actionComponentName;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return (((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i) * 31) + hashCode4) * 31) + hashCode5) * 31) + i2;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }

    public String toString() {
        String str = this.id;
        String str2 = this.name;
        int i = this.iconResourceId;
        boolean z = this.isEnabled;
        List<String> list = this.instructions;
        String str3 = this.actionText;
        String str4 = this.actionComponentName;
        return "KeyguardQuickAffordancePickerRepresentation(id=" + str + ", name=" + str2 + ", iconResourceId=" + i + ", isEnabled=" + z + ", instructions=" + list + ", actionText=" + str3 + ", actionComponentName=" + str4 + ")";
    }
}