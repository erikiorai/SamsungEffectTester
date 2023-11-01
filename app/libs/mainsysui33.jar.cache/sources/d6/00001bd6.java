package com.android.systemui.media.controls.models.player;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/player/MediaAction.class */
public final class MediaAction {
    public final Runnable action;
    public final Drawable background;
    public final CharSequence contentDescription;
    public final Drawable icon;
    public final Integer rebindId;

    public MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num) {
        this.icon = drawable;
        this.action = runnable;
        this.contentDescription = charSequence;
        this.background = drawable2;
        this.rebindId = num;
    }

    public /* synthetic */ MediaAction(Drawable drawable, Runnable runnable, CharSequence charSequence, Drawable drawable2, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable, runnable, charSequence, drawable2, (i & 16) != 0 ? null : num);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MediaAction) {
            MediaAction mediaAction = (MediaAction) obj;
            return Intrinsics.areEqual(this.icon, mediaAction.icon) && Intrinsics.areEqual(this.action, mediaAction.action) && Intrinsics.areEqual(this.contentDescription, mediaAction.contentDescription) && Intrinsics.areEqual(this.background, mediaAction.background) && Intrinsics.areEqual(this.rebindId, mediaAction.rebindId);
        }
        return false;
    }

    public final Runnable getAction() {
        return this.action;
    }

    public final Drawable getBackground() {
        return this.background;
    }

    public final CharSequence getContentDescription() {
        return this.contentDescription;
    }

    public final Drawable getIcon() {
        return this.icon;
    }

    public final Integer getRebindId() {
        return this.rebindId;
    }

    public int hashCode() {
        Drawable drawable = this.icon;
        int i = 0;
        int hashCode = drawable == null ? 0 : drawable.hashCode();
        Runnable runnable = this.action;
        int hashCode2 = runnable == null ? 0 : runnable.hashCode();
        CharSequence charSequence = this.contentDescription;
        int hashCode3 = charSequence == null ? 0 : charSequence.hashCode();
        Drawable drawable2 = this.background;
        int hashCode4 = drawable2 == null ? 0 : drawable2.hashCode();
        Integer num = this.rebindId;
        if (num != null) {
            i = num.hashCode();
        }
        return (((((((hashCode * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
    }

    public String toString() {
        Drawable drawable = this.icon;
        Runnable runnable = this.action;
        CharSequence charSequence = this.contentDescription;
        Drawable drawable2 = this.background;
        Integer num = this.rebindId;
        return "MediaAction(icon=" + drawable + ", action=" + runnable + ", contentDescription=" + ((Object) charSequence) + ", background=" + drawable2 + ", rebindId=" + num + ")";
    }
}