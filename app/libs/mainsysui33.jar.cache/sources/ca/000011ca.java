package com.android.systemui.biometrics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/Request.class */
public final class Request {
    public final int displayId;

    public Request(int i) {
        this.displayId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Request) && this.displayId == ((Request) obj).displayId;
    }

    public final int getDisplayId() {
        return this.displayId;
    }

    public int hashCode() {
        return Integer.hashCode(this.displayId);
    }

    public String toString() {
        int i = this.displayId;
        return "Request(displayId=" + i + ")";
    }
}