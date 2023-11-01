package com.android.systemui.media.controls.models.player;

import android.app.PendingIntent;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaDeviceData.class */
public final class MediaDeviceData {
    public final boolean enabled;
    public final Drawable icon;
    public final String id;
    public final PendingIntent intent;
    public final CharSequence name;
    public final boolean showBroadcastButton;

    public MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2) {
        this.enabled = z;
        this.icon = drawable;
        this.name = charSequence;
        this.intent = pendingIntent;
        this.id = str;
        this.showBroadcastButton = z2;
    }

    public /* synthetic */ MediaDeviceData(boolean z, Drawable drawable, CharSequence charSequence, PendingIntent pendingIntent, String str, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, drawable, charSequence, (i & 8) != 0 ? null : pendingIntent, (i & 16) != 0 ? null : str, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MediaDeviceData) {
            MediaDeviceData mediaDeviceData = (MediaDeviceData) obj;
            return this.enabled == mediaDeviceData.enabled && Intrinsics.areEqual(this.icon, mediaDeviceData.icon) && Intrinsics.areEqual(this.name, mediaDeviceData.name) && Intrinsics.areEqual(this.intent, mediaDeviceData.intent) && Intrinsics.areEqual(this.id, mediaDeviceData.id) && this.showBroadcastButton == mediaDeviceData.showBroadcastButton;
        }
        return false;
    }

    public final boolean equalsWithoutIcon(MediaDeviceData mediaDeviceData) {
        if (mediaDeviceData == null) {
            return false;
        }
        boolean z = false;
        if (this.enabled == mediaDeviceData.enabled) {
            z = false;
            if (Intrinsics.areEqual(this.name, mediaDeviceData.name)) {
                z = false;
                if (Intrinsics.areEqual(this.intent, mediaDeviceData.intent)) {
                    z = false;
                    if (Intrinsics.areEqual(this.id, mediaDeviceData.id)) {
                        z = false;
                        if (this.showBroadcastButton == mediaDeviceData.showBroadcastButton) {
                            z = true;
                        }
                    }
                }
            }
        }
        return z;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public final PendingIntent getIntent() {
        return this.intent;
    }

    public final CharSequence getName() {
        return this.name;
    }

    public final boolean getShowBroadcastButton() {
        return this.showBroadcastButton;
    }

    public int hashCode() {
        boolean z = this.enabled;
        int i = 1;
        boolean z2 = z;
        if (z) {
            z2 = true;
        }
        Drawable drawable = this.icon;
        int i2 = 0;
        int hashCode = drawable == null ? 0 : drawable.hashCode();
        CharSequence charSequence = this.name;
        int hashCode2 = charSequence == null ? 0 : charSequence.hashCode();
        PendingIntent pendingIntent = this.intent;
        int hashCode3 = pendingIntent == null ? 0 : pendingIntent.hashCode();
        String str = this.id;
        if (str != null) {
            i2 = str.hashCode();
        }
        boolean z3 = this.showBroadcastButton;
        if (!z3) {
            i = z3 ? 1 : 0;
        }
        return ((((((((((z2 ? 1 : 0) * 31) + hashCode) * 31) + hashCode2) * 31) + hashCode3) * 31) + i2) * 31) + i;
    }

    public String toString() {
        boolean z = this.enabled;
        Drawable drawable = this.icon;
        CharSequence charSequence = this.name;
        PendingIntent pendingIntent = this.intent;
        String str = this.id;
        boolean z2 = this.showBroadcastButton;
        return "MediaDeviceData(enabled=" + z + ", icon=" + drawable + ", name=" + ((Object) charSequence) + ", intent=" + pendingIntent + ", id=" + str + ", showBroadcastButton=" + z2 + ")";
    }
}