package com.android.app.motiontool;

/* loaded from: mainsysui33.jar:com/android/app/motiontool/WindowNotFoundException.class */
public final class WindowNotFoundException extends Exception {
    private final String windowId;

    public WindowNotFoundException(String str) {
        this.windowId = str;
    }

    public final String getWindowId() {
        return this.windowId;
    }
}