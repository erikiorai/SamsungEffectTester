package com.android.systemui.controls.controller;

import android.service.controls.Control;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlInfo.class */
public final class ControlInfo {
    public static final Companion Companion = new Companion(null);
    public final String controlId;
    public final CharSequence controlSubtitle;
    public final CharSequence controlTitle;
    public final int deviceType;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlInfo$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ControlInfo fromControl(Control control) {
            return new ControlInfo(control.getControlId(), control.getTitle(), control.getSubtitle(), control.getDeviceType());
        }
    }

    public ControlInfo(String str, CharSequence charSequence, CharSequence charSequence2, int i) {
        this.controlId = str;
        this.controlTitle = charSequence;
        this.controlSubtitle = charSequence2;
        this.deviceType = i;
    }

    public static /* synthetic */ ControlInfo copy$default(ControlInfo controlInfo, String str, CharSequence charSequence, CharSequence charSequence2, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = controlInfo.controlId;
        }
        if ((i2 & 2) != 0) {
            charSequence = controlInfo.controlTitle;
        }
        if ((i2 & 4) != 0) {
            charSequence2 = controlInfo.controlSubtitle;
        }
        if ((i2 & 8) != 0) {
            i = controlInfo.deviceType;
        }
        return controlInfo.copy(str, charSequence, charSequence2, i);
    }

    public final ControlInfo copy(String str, CharSequence charSequence, CharSequence charSequence2, int i) {
        return new ControlInfo(str, charSequence, charSequence2, i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ControlInfo) {
            ControlInfo controlInfo = (ControlInfo) obj;
            return Intrinsics.areEqual(this.controlId, controlInfo.controlId) && Intrinsics.areEqual(this.controlTitle, controlInfo.controlTitle) && Intrinsics.areEqual(this.controlSubtitle, controlInfo.controlSubtitle) && this.deviceType == controlInfo.deviceType;
        }
        return false;
    }

    public final String getControlId() {
        return this.controlId;
    }

    public final CharSequence getControlSubtitle() {
        return this.controlSubtitle;
    }

    public final CharSequence getControlTitle() {
        return this.controlTitle;
    }

    public final int getDeviceType() {
        return this.deviceType;
    }

    public int hashCode() {
        return (((((this.controlId.hashCode() * 31) + this.controlTitle.hashCode()) * 31) + this.controlSubtitle.hashCode()) * 31) + Integer.hashCode(this.deviceType);
    }

    public String toString() {
        String str = this.controlId;
        CharSequence charSequence = this.controlTitle;
        int i = this.deviceType;
        return ":" + str + ":" + ((Object) charSequence) + ":" + i;
    }
}