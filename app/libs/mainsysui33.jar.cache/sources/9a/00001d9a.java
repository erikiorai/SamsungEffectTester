package com.android.systemui.media.taptotransfer.receiver;

import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import com.android.systemui.temporarydisplay.TemporaryViewInfo;
import com.android.systemui.temporarydisplay.ViewPriority;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/ChipReceiverInfo.class */
public final class ChipReceiverInfo extends TemporaryViewInfo {
    public final Drawable appIconDrawableOverride;
    public final CharSequence appNameOverride;
    public final String id;
    public final ViewPriority priority;
    public final MediaRoute2Info routeInfo;
    public final String wakeReason;
    public final String windowTitle;

    public ChipReceiverInfo(MediaRoute2Info mediaRoute2Info, Drawable drawable, CharSequence charSequence, String str, String str2, String str3, ViewPriority viewPriority) {
        this.routeInfo = mediaRoute2Info;
        this.appIconDrawableOverride = drawable;
        this.appNameOverride = charSequence;
        this.windowTitle = str;
        this.wakeReason = str2;
        this.id = str3;
        this.priority = viewPriority;
    }

    public /* synthetic */ ChipReceiverInfo(MediaRoute2Info mediaRoute2Info, Drawable drawable, CharSequence charSequence, String str, String str2, String str3, ViewPriority viewPriority, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mediaRoute2Info, drawable, charSequence, (i & 8) != 0 ? "Media Transfer Chip View (Receiver)" : str, (i & 16) != 0 ? "MEDIA_TRANSFER_ACTIVATED_RECEIVER" : str2, str3, (i & 64) != 0 ? ViewPriority.NORMAL : viewPriority);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof ChipReceiverInfo) {
            ChipReceiverInfo chipReceiverInfo = (ChipReceiverInfo) obj;
            return Intrinsics.areEqual(this.routeInfo, chipReceiverInfo.routeInfo) && Intrinsics.areEqual(this.appIconDrawableOverride, chipReceiverInfo.appIconDrawableOverride) && Intrinsics.areEqual(this.appNameOverride, chipReceiverInfo.appNameOverride) && Intrinsics.areEqual(getWindowTitle(), chipReceiverInfo.getWindowTitle()) && Intrinsics.areEqual(getWakeReason(), chipReceiverInfo.getWakeReason()) && Intrinsics.areEqual(getId(), chipReceiverInfo.getId()) && getPriority() == chipReceiverInfo.getPriority();
        }
        return false;
    }

    public final Drawable getAppIconDrawableOverride() {
        return this.appIconDrawableOverride;
    }

    public final CharSequence getAppNameOverride() {
        return this.appNameOverride;
    }

    public String getId() {
        return this.id;
    }

    public ViewPriority getPriority() {
        return this.priority;
    }

    public final MediaRoute2Info getRouteInfo() {
        return this.routeInfo;
    }

    public String getWakeReason() {
        return this.wakeReason;
    }

    public String getWindowTitle() {
        return this.windowTitle;
    }

    public int hashCode() {
        int hashCode = this.routeInfo.hashCode();
        Drawable drawable = this.appIconDrawableOverride;
        int i = 0;
        int hashCode2 = drawable == null ? 0 : drawable.hashCode();
        CharSequence charSequence = this.appNameOverride;
        if (charSequence != null) {
            i = charSequence.hashCode();
        }
        return (((((((((((hashCode * 31) + hashCode2) * 31) + i) * 31) + getWindowTitle().hashCode()) * 31) + getWakeReason().hashCode()) * 31) + getId().hashCode()) * 31) + getPriority().hashCode();
    }

    public String toString() {
        MediaRoute2Info mediaRoute2Info = this.routeInfo;
        Drawable drawable = this.appIconDrawableOverride;
        CharSequence charSequence = this.appNameOverride;
        String windowTitle = getWindowTitle();
        String wakeReason = getWakeReason();
        String id = getId();
        ViewPriority priority = getPriority();
        return "ChipReceiverInfo(routeInfo=" + mediaRoute2Info + ", appIconDrawableOverride=" + drawable + ", appNameOverride=" + ((Object) charSequence) + ", windowTitle=" + windowTitle + ", wakeReason=" + wakeReason + ", id=" + id + ", priority=" + priority + ")";
    }
}