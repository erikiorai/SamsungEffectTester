package com.android.keyguard;

import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/keyguard/TrustGrantFlags.class */
public class TrustGrantFlags {
    public final int mFlags;

    public TrustGrantFlags(int i) {
        this.mFlags = i;
    }

    public boolean dismissKeyguardRequested() {
        return (this.mFlags & 2) != 0;
    }

    public boolean displayMessage() {
        return (this.mFlags & 8) != 0;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj instanceof TrustGrantFlags) {
            if (((TrustGrantFlags) obj).mFlags == this.mFlags) {
                z = true;
            }
            return z;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.mFlags));
    }

    public boolean isInitiatedByUser() {
        boolean z = true;
        if ((this.mFlags & 1) == 0) {
            z = false;
        }
        return z;
    }

    public boolean temporaryAndRenewable() {
        return (this.mFlags & 4) != 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.mFlags);
        sb.append("]=");
        if (isInitiatedByUser()) {
            sb.append("initiatedByUser|");
        }
        if (dismissKeyguardRequested()) {
            sb.append("dismissKeyguard|");
        }
        if (temporaryAndRenewable()) {
            sb.append("temporaryAndRenewable|");
        }
        if (displayMessage()) {
            sb.append("displayMessage|");
        }
        return sb.toString();
    }
}