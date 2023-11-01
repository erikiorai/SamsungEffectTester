package com.android.systemui.common.shared.model;

import android.content.Context;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Text.class */
public abstract class Text {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Text$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String loadText(Text text, Context context) {
            String string;
            if (text == null) {
                string = null;
            } else if (text instanceof Loaded) {
                string = ((Loaded) text).getText();
            } else if (!(text instanceof Resource)) {
                throw new NoWhenBranchMatchedException();
            } else {
                string = context.getString(((Resource) text).getRes());
            }
            return string;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Text$Loaded.class */
    public static final class Loaded extends Text {
        public final String text;

        public Loaded(String str) {
            super(null);
            this.text = str;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Loaded) && Intrinsics.areEqual(this.text, ((Loaded) obj).text);
        }

        public final String getText() {
            return this.text;
        }

        public int hashCode() {
            String str = this.text;
            return str == null ? 0 : str.hashCode();
        }

        public String toString() {
            String str = this.text;
            return "Loaded(text=" + str + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/common/shared/model/Text$Resource.class */
    public static final class Resource extends Text {
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

    public Text() {
    }

    public /* synthetic */ Text(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}