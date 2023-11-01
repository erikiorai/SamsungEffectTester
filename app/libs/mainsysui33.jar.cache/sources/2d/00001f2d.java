package com.android.systemui.people.ui.viewmodel;

import android.graphics.Bitmap;
import com.android.systemui.people.widget.PeopleTileKey;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/people/ui/viewmodel/PeopleTileViewModel.class */
public final class PeopleTileViewModel {
    public final Bitmap icon;
    public final PeopleTileKey key;
    public final String username;

    public PeopleTileViewModel(PeopleTileKey peopleTileKey, Bitmap bitmap, String str) {
        this.key = peopleTileKey;
        this.icon = bitmap;
        this.username = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PeopleTileViewModel) {
            PeopleTileViewModel peopleTileViewModel = (PeopleTileViewModel) obj;
            return Intrinsics.areEqual(this.key, peopleTileViewModel.key) && Intrinsics.areEqual(this.icon, peopleTileViewModel.icon) && Intrinsics.areEqual(this.username, peopleTileViewModel.username);
        }
        return false;
    }

    public final Bitmap getIcon() {
        return this.icon;
    }

    public final PeopleTileKey getKey() {
        return this.key;
    }

    public final String getUsername() {
        return this.username;
    }

    public int hashCode() {
        int hashCode = this.key.hashCode();
        int hashCode2 = this.icon.hashCode();
        String str = this.username;
        return (((hashCode * 31) + hashCode2) * 31) + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        PeopleTileKey peopleTileKey = this.key;
        Bitmap bitmap = this.icon;
        String str = this.username;
        return "PeopleTileViewModel(key=" + peopleTileKey + ", icon=" + bitmap + ", username=" + str + ")";
    }
}