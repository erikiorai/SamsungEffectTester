package com.android.systemui.people.data.model;

import android.graphics.drawable.Icon;
import com.android.systemui.people.widget.PeopleTileKey;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/people/data/model/PeopleTileModel.class */
public final class PeopleTileModel {
    public final boolean hasNewStory;
    public final boolean isDndBlocking;
    public final boolean isImportant;
    public final PeopleTileKey key;
    public final Icon userIcon;
    public final String username;

    public PeopleTileModel(PeopleTileKey peopleTileKey, String str, Icon icon, boolean z, boolean z2, boolean z3) {
        this.key = peopleTileKey;
        this.username = str;
        this.userIcon = icon;
        this.hasNewStory = z;
        this.isImportant = z2;
        this.isDndBlocking = z3;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PeopleTileModel) {
            PeopleTileModel peopleTileModel = (PeopleTileModel) obj;
            return Intrinsics.areEqual(this.key, peopleTileModel.key) && Intrinsics.areEqual(this.username, peopleTileModel.username) && Intrinsics.areEqual(this.userIcon, peopleTileModel.userIcon) && this.hasNewStory == peopleTileModel.hasNewStory && this.isImportant == peopleTileModel.isImportant && this.isDndBlocking == peopleTileModel.isDndBlocking;
        }
        return false;
    }

    public final boolean getHasNewStory() {
        return this.hasNewStory;
    }

    public final PeopleTileKey getKey() {
        return this.key;
    }

    public final Icon getUserIcon() {
        return this.userIcon;
    }

    public final String getUsername() {
        return this.username;
    }

    public int hashCode() {
        int hashCode = this.key.hashCode();
        int hashCode2 = this.username.hashCode();
        int hashCode3 = this.userIcon.hashCode();
        boolean z = this.hasNewStory;
        int i = 1;
        int i2 = z ? 1 : 0;
        if (z) {
            i2 = 1;
        }
        boolean z2 = this.isImportant;
        int i3 = z2 ? 1 : 0;
        if (z2) {
            i3 = 1;
        }
        boolean z3 = this.isDndBlocking;
        if (!z3) {
            i = z3 ? 1 : 0;
        }
        return (((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i2) * 31) + i3) * 31) + i;
    }

    public final boolean isDndBlocking() {
        return this.isDndBlocking;
    }

    public final boolean isImportant() {
        return this.isImportant;
    }

    public String toString() {
        PeopleTileKey peopleTileKey = this.key;
        String str = this.username;
        Icon icon = this.userIcon;
        boolean z = this.hasNewStory;
        boolean z2 = this.isImportant;
        boolean z3 = this.isDndBlocking;
        return "PeopleTileModel(key=" + peopleTileKey + ", username=" + str + ", userIcon=" + icon + ", hasNewStory=" + z + ", isImportant=" + z2 + ", isDndBlocking=" + z3 + ")";
    }
}