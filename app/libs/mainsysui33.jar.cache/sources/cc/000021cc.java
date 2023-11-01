package com.android.systemui.qs.external;

import android.content.ComponentName;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceKey.class */
public final class TileServiceKey {
    public final ComponentName componentName;
    public final String string;
    public final int user;

    public TileServiceKey(ComponentName componentName, int i) {
        this.componentName = componentName;
        this.user = i;
        String flattenToString = componentName.flattenToString();
        this.string = flattenToString + ":" + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof TileServiceKey) {
            TileServiceKey tileServiceKey = (TileServiceKey) obj;
            return Intrinsics.areEqual(this.componentName, tileServiceKey.componentName) && this.user == tileServiceKey.user;
        }
        return false;
    }

    public int hashCode() {
        return (this.componentName.hashCode() * 31) + Integer.hashCode(this.user);
    }

    public String toString() {
        return this.string;
    }
}