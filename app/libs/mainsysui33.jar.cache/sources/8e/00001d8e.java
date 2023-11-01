package com.android.systemui.media.taptotransfer.common;

import com.android.systemui.common.shared.model.ContentDescription;
import com.android.systemui.common.shared.model.Icon;
import com.android.systemui.common.shared.model.TintedIcon;
import com.android.systemui.media.taptotransfer.common.MediaTttIcon;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/IconInfo.class */
public final class IconInfo {
    public final ContentDescription contentDescription;
    public final MediaTttIcon icon;
    public final boolean isAppIcon;
    public final Integer tintAttr;

    public IconInfo(ContentDescription contentDescription, MediaTttIcon mediaTttIcon, Integer num, boolean z) {
        this.contentDescription = contentDescription;
        this.icon = mediaTttIcon;
        this.tintAttr = num;
        this.isAppIcon = z;
    }

    public static /* synthetic */ IconInfo copy$default(IconInfo iconInfo, ContentDescription contentDescription, MediaTttIcon mediaTttIcon, Integer num, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            contentDescription = iconInfo.contentDescription;
        }
        if ((i & 2) != 0) {
            mediaTttIcon = iconInfo.icon;
        }
        if ((i & 4) != 0) {
            num = iconInfo.tintAttr;
        }
        if ((i & 8) != 0) {
            z = iconInfo.isAppIcon;
        }
        return iconInfo.copy(contentDescription, mediaTttIcon, num, z);
    }

    public final IconInfo copy(ContentDescription contentDescription, MediaTttIcon mediaTttIcon, Integer num, boolean z) {
        return new IconInfo(contentDescription, mediaTttIcon, num, z);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IconInfo) {
            IconInfo iconInfo = (IconInfo) obj;
            return Intrinsics.areEqual(this.contentDescription, iconInfo.contentDescription) && Intrinsics.areEqual(this.icon, iconInfo.icon) && Intrinsics.areEqual(this.tintAttr, iconInfo.tintAttr) && this.isAppIcon == iconInfo.isAppIcon;
        }
        return false;
    }

    public int hashCode() {
        int hashCode = this.contentDescription.hashCode();
        int hashCode2 = this.icon.hashCode();
        Integer num = this.tintAttr;
        int hashCode3 = num == null ? 0 : num.hashCode();
        boolean z = this.isAppIcon;
        int i = z ? 1 : 0;
        if (z) {
            i = 1;
        }
        return (((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + i;
    }

    public final boolean isAppIcon() {
        return this.isAppIcon;
    }

    public String toString() {
        ContentDescription contentDescription = this.contentDescription;
        MediaTttIcon mediaTttIcon = this.icon;
        Integer num = this.tintAttr;
        boolean z = this.isAppIcon;
        return "IconInfo(contentDescription=" + contentDescription + ", icon=" + mediaTttIcon + ", tintAttr=" + num + ", isAppIcon=" + z + ")";
    }

    public final TintedIcon toTintedIcon() {
        Icon resource;
        MediaTttIcon mediaTttIcon = this.icon;
        if (mediaTttIcon instanceof MediaTttIcon.Loaded) {
            resource = new Icon.Loaded(((MediaTttIcon.Loaded) mediaTttIcon).getDrawable(), this.contentDescription);
        } else if (!(mediaTttIcon instanceof MediaTttIcon.Resource)) {
            throw new NoWhenBranchMatchedException();
        } else {
            resource = new Icon.Resource(((MediaTttIcon.Resource) mediaTttIcon).getRes(), this.contentDescription);
        }
        return new TintedIcon(resource, this.tintAttr);
    }
}