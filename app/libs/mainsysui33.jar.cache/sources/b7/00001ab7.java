package com.android.systemui.keyguard.shared.model;

import android.content.res.ColorStateList;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BouncerShowMessageModel.class */
public final class BouncerShowMessageModel {
    public final ColorStateList colorStateList;
    public final String message;

    public BouncerShowMessageModel(String str, ColorStateList colorStateList) {
        this.message = str;
        this.colorStateList = colorStateList;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BouncerShowMessageModel) {
            BouncerShowMessageModel bouncerShowMessageModel = (BouncerShowMessageModel) obj;
            return Intrinsics.areEqual(this.message, bouncerShowMessageModel.message) && Intrinsics.areEqual(this.colorStateList, bouncerShowMessageModel.colorStateList);
        }
        return false;
    }

    public final ColorStateList getColorStateList() {
        return this.colorStateList;
    }

    public final String getMessage() {
        return this.message;
    }

    public int hashCode() {
        String str = this.message;
        int i = 0;
        int hashCode = str == null ? 0 : str.hashCode();
        ColorStateList colorStateList = this.colorStateList;
        if (colorStateList != null) {
            i = colorStateList.hashCode();
        }
        return (hashCode * 31) + i;
    }

    public String toString() {
        String str = this.message;
        ColorStateList colorStateList = this.colorStateList;
        return "BouncerShowMessageModel(message=" + str + ", colorStateList=" + colorStateList + ")";
    }
}