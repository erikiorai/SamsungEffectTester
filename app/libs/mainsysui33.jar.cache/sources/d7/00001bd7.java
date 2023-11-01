package com.android.systemui.media.controls.models.player;

import com.android.systemui.R$id;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaButton.class */
public final class MediaButton {
    public final MediaAction custom0;
    public final MediaAction custom1;
    public final MediaAction nextOrCustom;
    public final MediaAction playOrPause;
    public final MediaAction prevOrCustom;
    public final boolean reserveNext;
    public final boolean reservePrev;

    public MediaButton() {
        this(null, null, null, null, null, false, false, 127, null);
    }

    public MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2) {
        this.playOrPause = mediaAction;
        this.nextOrCustom = mediaAction2;
        this.prevOrCustom = mediaAction3;
        this.custom0 = mediaAction4;
        this.custom1 = mediaAction5;
        this.reserveNext = z;
        this.reservePrev = z2;
    }

    public /* synthetic */ MediaButton(MediaAction mediaAction, MediaAction mediaAction2, MediaAction mediaAction3, MediaAction mediaAction4, MediaAction mediaAction5, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : mediaAction, (i & 2) != 0 ? null : mediaAction2, (i & 4) != 0 ? null : mediaAction3, (i & 8) != 0 ? null : mediaAction4, (i & 16) != 0 ? null : mediaAction5, (i & 32) != 0 ? false : z, (i & 64) != 0 ? false : z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MediaButton) {
            MediaButton mediaButton = (MediaButton) obj;
            return Intrinsics.areEqual(this.playOrPause, mediaButton.playOrPause) && Intrinsics.areEqual(this.nextOrCustom, mediaButton.nextOrCustom) && Intrinsics.areEqual(this.prevOrCustom, mediaButton.prevOrCustom) && Intrinsics.areEqual(this.custom0, mediaButton.custom0) && Intrinsics.areEqual(this.custom1, mediaButton.custom1) && this.reserveNext == mediaButton.reserveNext && this.reservePrev == mediaButton.reservePrev;
        }
        return false;
    }

    public final MediaAction getActionById(int i) {
        return i == R$id.actionPlayPause ? this.playOrPause : i == R$id.actionNext ? this.nextOrCustom : i == R$id.actionPrev ? this.prevOrCustom : i == R$id.action0 ? this.custom0 : i == R$id.action1 ? this.custom1 : null;
    }

    public final boolean getReserveNext() {
        return this.reserveNext;
    }

    public final boolean getReservePrev() {
        return this.reservePrev;
    }

    public int hashCode() {
        MediaAction mediaAction = this.playOrPause;
        int i = 0;
        int hashCode = mediaAction == null ? 0 : mediaAction.hashCode();
        MediaAction mediaAction2 = this.nextOrCustom;
        int hashCode2 = mediaAction2 == null ? 0 : mediaAction2.hashCode();
        MediaAction mediaAction3 = this.prevOrCustom;
        int hashCode3 = mediaAction3 == null ? 0 : mediaAction3.hashCode();
        MediaAction mediaAction4 = this.custom0;
        int hashCode4 = mediaAction4 == null ? 0 : mediaAction4.hashCode();
        MediaAction mediaAction5 = this.custom1;
        if (mediaAction5 != null) {
            i = mediaAction5.hashCode();
        }
        boolean z = this.reserveNext;
        int i2 = 1;
        int i3 = z ? 1 : 0;
        if (z) {
            i3 = 1;
        }
        boolean z2 = this.reservePrev;
        if (!z2) {
            i2 = z2 ? 1 : 0;
        }
        return (((((((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i) * 31) + i3) * 31) + i2;
    }

    public String toString() {
        MediaAction mediaAction = this.playOrPause;
        MediaAction mediaAction2 = this.nextOrCustom;
        MediaAction mediaAction3 = this.prevOrCustom;
        MediaAction mediaAction4 = this.custom0;
        MediaAction mediaAction5 = this.custom1;
        boolean z = this.reserveNext;
        boolean z2 = this.reservePrev;
        return "MediaButton(playOrPause=" + mediaAction + ", nextOrCustom=" + mediaAction2 + ", prevOrCustom=" + mediaAction3 + ", custom0=" + mediaAction4 + ", custom1=" + mediaAction5 + ", reserveNext=" + z + ", reservePrev=" + z2 + ")";
    }
}