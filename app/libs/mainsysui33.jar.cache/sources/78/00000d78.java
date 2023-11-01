package com.android.settingslib;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedLockUtils.class */
public class RestrictedLockUtils {

    /* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedLockUtils$EnforcedAdmin.class */
    public static class EnforcedAdmin {
        public static final EnforcedAdmin MULTIPLE_ENFORCED_ADMIN = new EnforcedAdmin();
        public ComponentName component;
        public String enforcedRestriction;
        public UserHandle user;

        public EnforcedAdmin() {
            this.component = null;
            this.enforcedRestriction = null;
            this.user = null;
        }

        public EnforcedAdmin(ComponentName componentName, String str, UserHandle userHandle) {
            this.component = componentName;
            this.enforcedRestriction = str;
            this.user = userHandle;
        }

        public static EnforcedAdmin createDefaultEnforcedAdminWithRestriction(String str) {
            EnforcedAdmin enforcedAdmin = new EnforcedAdmin();
            enforcedAdmin.enforcedRestriction = str;
            return enforcedAdmin;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            EnforcedAdmin enforcedAdmin = (EnforcedAdmin) obj;
            if (!Objects.equals(this.user, enforcedAdmin.user) || !Objects.equals(this.component, enforcedAdmin.component) || !Objects.equals(this.enforcedRestriction, enforcedAdmin.enforcedRestriction)) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            return Objects.hash(this.component, this.enforcedRestriction, this.user);
        }

        public String toString() {
            return "EnforcedAdmin{component=" + this.component + ", enforcedRestriction='" + this.enforcedRestriction + ", user=" + this.user + '}';
        }
    }

    public static Intent getShowAdminSupportDetailsIntent(Context context, EnforcedAdmin enforcedAdmin) {
        Intent intent = new Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
        if (enforcedAdmin != null) {
            ComponentName componentName = enforcedAdmin.component;
            if (componentName != null) {
                intent.putExtra("android.app.extra.DEVICE_ADMIN", componentName);
            }
            intent.putExtra("android.intent.extra.USER", enforcedAdmin.user);
        }
        return intent;
    }

    public static boolean isCurrentUserOrProfile(Context context, int i) {
        return ((UserManager) context.getSystemService(UserManager.class)).getUserProfiles().contains(UserHandle.of(i));
    }

    public static void sendShowAdminSupportDetailsIntent(Context context, EnforcedAdmin enforcedAdmin) {
        Intent showAdminSupportDetailsIntent = getShowAdminSupportDetailsIntent(context, enforcedAdmin);
        int myUserId = UserHandle.myUserId();
        int i = myUserId;
        if (enforcedAdmin != null) {
            UserHandle userHandle = enforcedAdmin.user;
            i = myUserId;
            if (userHandle != null) {
                i = myUserId;
                if (isCurrentUserOrProfile(context, userHandle.getIdentifier())) {
                    i = enforcedAdmin.user.getIdentifier();
                }
            }
            showAdminSupportDetailsIntent.putExtra("android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        }
        context.startActivityAsUser(showAdminSupportDetailsIntent, UserHandle.of(i));
    }
}