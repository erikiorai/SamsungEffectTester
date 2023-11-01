package com.android.systemui.common.shared.model;

import android.content.Context;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/ContentDescription.class */
public abstract class ContentDescription {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/ContentDescription$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String loadContentDescription(ContentDescription contentDescription, Context context) {
            String string;
            if (contentDescription == null) {
                string = null;
            } else if (contentDescription instanceof Loaded) {
                string = ((Loaded) contentDescription).getDescription();
            } else if (!(contentDescription instanceof Resource)) {
                throw new NoWhenBranchMatchedException();
            } else {
                string = context.getString(((Resource) contentDescription).getRes());
            }
            return string;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/ContentDescription$Loaded.class */
    public static final class Loaded extends ContentDescription {
        public final String description;

        public Loaded(String str) {
            super(null);
            this.description = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.description, ((Loaded) obj).description);
        }

        public final String getDescription() {
            return this.description;
        }

        public int hashCode() {
            String str = this.description;
            return str == null ? 0 : str.hashCode();
        }

        public String toString() {
            String str = this.description;
            return "Loaded(description=" + str + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/ContentDescription$Resource.class */
    public static final class Resource extends ContentDescription {
        public final int res;

        public Resource(int i) {
            super(null);
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

    public ContentDescription() {
    }

    public /* synthetic */ ContentDescription(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}