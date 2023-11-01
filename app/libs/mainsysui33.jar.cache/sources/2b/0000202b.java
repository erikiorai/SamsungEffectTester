package com.android.systemui.privacy;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyApplication.class */
public final class PrivacyApplication {
    public final String packageName;
    public final int uid;

    public PrivacyApplication(String str, int i) {
        this.packageName = str;
        this.uid = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PrivacyApplication) {
            PrivacyApplication privacyApplication = (PrivacyApplication) obj;
            return Intrinsics.areEqual(this.packageName, privacyApplication.packageName) && this.uid == privacyApplication.uid;
        }
        return false;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final int getUid() {
        return this.uid;
    }

    public int hashCode() {
        return (this.packageName.hashCode() * 31) + Integer.hashCode(this.uid);
    }

    public String toString() {
        String str = this.packageName;
        int i = this.uid;
        return "PrivacyApplication(packageName=" + str + ", uid=" + i + ")";
    }
}