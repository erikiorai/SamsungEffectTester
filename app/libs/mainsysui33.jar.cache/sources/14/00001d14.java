package com.android.systemui.media.controls.util;

import androidx.recyclerview.widget.RecyclerView;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/SmallHash.class */
public final class SmallHash {
    public static int hash(int i) {
        return Math.abs(Math.floorMod(i, (int) RecyclerView.ViewHolder.FLAG_BOUNCED_FROM_HIDDEN_LIST));
    }

    public static int hash(String str) {
        return hash(Objects.hashCode(str));
    }
}