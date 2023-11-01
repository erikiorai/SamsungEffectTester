package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/KeyguardSlotPickerRepresentation.class */
public final class KeyguardSlotPickerRepresentation {
    public final String id;
    public final int maxSelectedAffordances;

    public KeyguardSlotPickerRepresentation(String str, int i) {
        this.id = str;
        this.maxSelectedAffordances = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardSlotPickerRepresentation) {
            KeyguardSlotPickerRepresentation keyguardSlotPickerRepresentation = (KeyguardSlotPickerRepresentation) obj;
            return Intrinsics.areEqual(this.id, keyguardSlotPickerRepresentation.id) && this.maxSelectedAffordances == keyguardSlotPickerRepresentation.maxSelectedAffordances;
        }
        return false;
    }

    public final String getId() {
        return this.id;
    }

    public final int getMaxSelectedAffordances() {
        return this.maxSelectedAffordances;
    }

    public int hashCode() {
        return (this.id.hashCode() * 31) + Integer.hashCode(this.maxSelectedAffordances);
    }

    public String toString() {
        String str = this.id;
        int i = this.maxSelectedAffordances;
        return "KeyguardSlotPickerRepresentation(id=" + str + ", maxSelectedAffordances=" + i + ")";
    }
}