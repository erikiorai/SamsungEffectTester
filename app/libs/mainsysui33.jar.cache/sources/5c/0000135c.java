package com.android.systemui.common.shared.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Icon.class */
public abstract class Icon {

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Icon$Loaded.class */
    public static final class Loaded extends Icon {
        public final ContentDescription contentDescription;
        public final Drawable drawable;

        public Loaded(Drawable drawable, ContentDescription contentDescription) {
            super(null);
            this.drawable = drawable;
            this.contentDescription = contentDescription;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Loaded) {
                Loaded loaded = (Loaded) obj;
                return Intrinsics.areEqual(this.drawable, loaded.drawable) && Intrinsics.areEqual(getContentDescription(), loaded.getContentDescription());
            }
            return false;
        }

        @Override // com.android.systemui.common.shared.model.Icon
        public ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        public final Drawable getDrawable() {
            return this.drawable;
        }

        public int hashCode() {
            return (this.drawable.hashCode() * 31) + (getContentDescription() == null ? 0 : getContentDescription().hashCode());
        }

        public String toString() {
            Drawable drawable = this.drawable;
            ContentDescription contentDescription = getContentDescription();
            return "Loaded(drawable=" + drawable + ", contentDescription=" + contentDescription + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Icon$Resource.class */
    public static final class Resource extends Icon {
        public final ContentDescription contentDescription;
        public final int res;

        public Resource(int i, ContentDescription contentDescription) {
            super(null);
            this.res = i;
            this.contentDescription = contentDescription;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Resource) {
                Resource resource = (Resource) obj;
                return this.res == resource.res && Intrinsics.areEqual(getContentDescription(), resource.getContentDescription());
            }
            return false;
        }

        @Override // com.android.systemui.common.shared.model.Icon
        public ContentDescription getContentDescription() {
            return this.contentDescription;
        }

        public final int getRes() {
            return this.res;
        }

        public int hashCode() {
            return (Integer.hashCode(this.res) * 31) + (getContentDescription() == null ? 0 : getContentDescription().hashCode());
        }

        public String toString() {
            int i = this.res;
            ContentDescription contentDescription = getContentDescription();
            return "Resource(res=" + i + ", contentDescription=" + contentDescription + ")";
        }
    }

    public Icon() {
    }

    public /* synthetic */ Icon(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    public abstract ContentDescription getContentDescription();
}