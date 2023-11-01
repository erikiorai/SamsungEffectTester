package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/KeyguardPickerFlag.class */
public final class KeyguardPickerFlag {
    public final String name;
    public final boolean value;

    public KeyguardPickerFlag(String str, boolean z) {
        this.name = str;
        this.value = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof KeyguardPickerFlag) {
            KeyguardPickerFlag keyguardPickerFlag = (KeyguardPickerFlag) obj;
            return Intrinsics.areEqual(this.name, keyguardPickerFlag.name) && this.value == keyguardPickerFlag.value;
        }
        return false;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean getValue() {
        return this.value;
    }

    public int hashCode() {
        int hashCode = this.name.hashCode();
        boolean z = this.value;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (hashCode * 31) + i;
    }

    public String toString() {
        String str = this.name;
        boolean z = this.value;
        return "KeyguardPickerFlag(name=" + str + ", value=" + z + ")";
    }
}