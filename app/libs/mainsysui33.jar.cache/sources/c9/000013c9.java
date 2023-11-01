package com.android.systemui.controls.controller;

import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/SeedResponse.class */
public final class SeedResponse {
    public final boolean accepted;
    public final String packageName;

    public SeedResponse(String str, boolean z) {
        this.packageName = str;
        this.accepted = z;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SeedResponse) {
            SeedResponse seedResponse = (SeedResponse) obj;
            return Intrinsics.areEqual(this.packageName, seedResponse.packageName) && this.accepted == seedResponse.accepted;
        }
        return false;
    }

    public final boolean getAccepted() {
        return this.accepted;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public int hashCode() {
        int hashCode = this.packageName.hashCode();
        boolean z = this.accepted;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (hashCode * 31) + i;
    }

    public String toString() {
        String str = this.packageName;
        boolean z = this.accepted;
        return "SeedResponse(packageName=" + str + ", accepted=" + z + ")";
    }
}