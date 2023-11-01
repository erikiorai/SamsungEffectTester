package com.android.settingslib;

/* loaded from: mainsysui33.jar:com/android/settingslib/SignalIcon$MobileIconGroup.class */
public class SignalIcon$MobileIconGroup extends SignalIcon$IconGroup {
    public final int dataContentDescription;
    public final int dataType;

    public SignalIcon$MobileIconGroup(String str, int i, int i2) {
        new Object(str, null, null, AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH, 0, 0, 0, 0, AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH_NONE) { // from class: com.android.settingslib.SignalIcon$IconGroup
            public final int[] contentDesc;
            public final int discContentDesc;
            public final String name;
            public final int qsDiscState;
            public final int[][] qsIcons;
            public final int qsNullState;
            public final int sbDiscState;
            public final int[][] sbIcons;
            public final int sbNullState;

            {
                this.name = str;
                this.sbIcons = r5;
                this.qsIcons = r6;
                this.contentDesc = r7;
                this.sbNullState = r8;
                this.qsNullState = r9;
                this.sbDiscState = r10;
                this.qsDiscState = r11;
                this.discContentDesc = r12;
            }

            public String toString() {
                return "IconGroup(" + this.name + ")";
            }
        };
        this.dataContentDescription = i;
        this.dataType = i2;
    }
}