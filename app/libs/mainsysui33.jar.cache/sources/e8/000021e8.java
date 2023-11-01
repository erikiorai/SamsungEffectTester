package com.android.systemui.qs.footer.data.model;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/model/UserSwitcherStatusModel.class */
public abstract class UserSwitcherStatusModel {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Disabled.class */
    public static final class Disabled extends UserSwitcherStatusModel {
        public static final Disabled INSTANCE = new Disabled();

        public Disabled() {
            super(null);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/model/UserSwitcherStatusModel$Enabled.class */
    public static final class Enabled extends UserSwitcherStatusModel {
        public final Drawable currentUserImage;
        public final String currentUserName;
        public final boolean isGuestUser;

        public Enabled(String str, Drawable drawable, boolean z) {
            super(null);
            this.currentUserName = str;
            this.currentUserImage = drawable;
            this.isGuestUser = z;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Enabled) {
                Enabled enabled = (Enabled) obj;
                return Intrinsics.areEqual(this.currentUserName, enabled.currentUserName) && Intrinsics.areEqual(this.currentUserImage, enabled.currentUserImage) && this.isGuestUser == enabled.isGuestUser;
            }
            return false;
        }

        public final Drawable getCurrentUserImage() {
            return this.currentUserImage;
        }

        public final String getCurrentUserName() {
            return this.currentUserName;
        }

        public int hashCode() {
            String str = this.currentUserName;
            int i = 0;
            int hashCode = str == null ? 0 : str.hashCode();
            Drawable drawable = this.currentUserImage;
            if (drawable != null) {
                i = drawable.hashCode();
            }
            boolean z = this.isGuestUser;
            int i2 = z ? 1 : 0;
            if (z) {
                i2 = 1;
            }
            return (((hashCode * 31) + i) * 31) + i2;
        }

        public String toString() {
            String str = this.currentUserName;
            Drawable drawable = this.currentUserImage;
            boolean z = this.isGuestUser;
            return "Enabled(currentUserName=" + str + ", currentUserImage=" + drawable + ", isGuestUser=" + z + ")";
        }
    }

    public UserSwitcherStatusModel() {
    }

    public /* synthetic */ UserSwitcherStatusModel(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}