package com.android.systemui.privacy;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/PrivacyItem.class */
public final class PrivacyItem {
    public final PrivacyApplication application;
    public final String log;
    public final boolean paused;
    public final PrivacyType privacyType;
    public final long timeStampElapsed;

    public PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z) {
        this.privacyType = privacyType;
        this.application = privacyApplication;
        this.timeStampElapsed = j;
        this.paused = z;
        String logName = privacyType.getLogName();
        String packageName = privacyApplication.getPackageName();
        int uid = privacyApplication.getUid();
        this.log = "(" + logName + ", " + packageName + "(" + uid + "), " + j + ", paused=" + z + ")";
    }

    public /* synthetic */ PrivacyItem(PrivacyType privacyType, PrivacyApplication privacyApplication, long j, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(privacyType, privacyApplication, (i & 4) != 0 ? -1L : j, (i & 8) != 0 ? false : z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PrivacyItem) {
            PrivacyItem privacyItem = (PrivacyItem) obj;
            return this.privacyType == privacyItem.privacyType && Intrinsics.areEqual(this.application, privacyItem.application) && this.timeStampElapsed == privacyItem.timeStampElapsed && this.paused == privacyItem.paused;
        }
        return false;
    }

    public final PrivacyApplication getApplication() {
        return this.application;
    }

    public final String getLog() {
        return this.log;
    }

    public final boolean getPaused() {
        return this.paused;
    }

    public final PrivacyType getPrivacyType() {
        return this.privacyType;
    }

    public final long getTimeStampElapsed() {
        return this.timeStampElapsed;
    }

    public int hashCode() {
        int hashCode = this.privacyType.hashCode();
        int hashCode2 = this.application.hashCode();
        int hashCode3 = Long.hashCode(this.timeStampElapsed);
        boolean z = this.paused;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i;
    }

    public String toString() {
        PrivacyType privacyType = this.privacyType;
        PrivacyApplication privacyApplication = this.application;
        long j = this.timeStampElapsed;
        boolean z = this.paused;
        return "PrivacyItem(privacyType=" + privacyType + ", application=" + privacyApplication + ", timeStampElapsed=" + j + ", paused=" + z + ")";
    }
}