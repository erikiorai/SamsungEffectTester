package com.android.systemui.animation;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/DialogCuj.class */
public final class DialogCuj {
    public final int cujType;
    public final String tag;

    public DialogCuj(int i, String str) {
        this.cujType = i;
        this.tag = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof DialogCuj) {
            DialogCuj dialogCuj = (DialogCuj) obj;
            return this.cujType == dialogCuj.cujType && Intrinsics.areEqual(this.tag, dialogCuj.tag);
        }
        return false;
    }

    public final int getCujType() {
        return this.cujType;
    }

    public final String getTag() {
        return this.tag;
    }

    public int hashCode() {
        int hashCode = Integer.hashCode(this.cujType);
        String str = this.tag;
        return (hashCode * 31) + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        int i = this.cujType;
        String str = this.tag;
        return "DialogCuj(cujType=" + i + ", tag=" + str + ")";
    }
}