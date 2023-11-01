package com.android.systemui.media.taptotransfer.common;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttIcon.class */
public interface MediaTttIcon {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttIcon$Loaded.class */
    public static final class Loaded implements MediaTttIcon {
        public final Drawable drawable;

        public Loaded(Drawable drawable) {
            this.drawable = drawable;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.drawable, ((Loaded) obj).drawable);
        }

        public final Drawable getDrawable() {
            return this.drawable;
        }

        public int hashCode() {
            return this.drawable.hashCode();
        }

        public String toString() {
            Drawable drawable = this.drawable;
            return "Loaded(drawable=" + drawable + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/common/MediaTttIcon$Resource.class */
    public static final class Resource implements MediaTttIcon {
        public final int res;

        public Resource(int i) {
            this.res = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Resource) && this.res == ((Resource) obj).res;
        }

        public final int getRes() {
            return this.res;
        }

        public int hashCode() {
            return Integer.hashCode(this.res);
        }

        public String toString() {
            int i = this.res;
            return "Resource(res=" + i + ")";
        }
    }
}