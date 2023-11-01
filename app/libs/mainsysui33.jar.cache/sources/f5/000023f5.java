package com.android.systemui.screenrecord;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/ScreenShareOption.class */
public final class ScreenShareOption {
    public final int mode;
    public final int spinnerText;
    public final int warningText;

    public ScreenShareOption(int i, int i2, int i3) {
        this.mode = i;
        this.spinnerText = i2;
        this.warningText = i3;
    }

    public final int getMode() {
        return this.mode;
    }

    public final int getSpinnerText() {
        return this.spinnerText;
    }

    public final int getWarningText() {
        return this.warningText;
    }
}