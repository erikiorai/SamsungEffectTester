package com.android.settingslib;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;
import com.android.settingslib.RestrictedLockUtils;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedLockUtilsInternal.class */
public class RestrictedLockUtilsInternal extends RestrictedLockUtils {
    public static final boolean DEBUG = Log.isLoggable("RestrictedLockUtils", 3);
    public static Proxy sProxy = new Proxy();

    /* loaded from: mainsysui33.jar:com/android/settingslib/RestrictedLockUtilsInternal$Proxy.class */
    public static class Proxy {
    }

    public static RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced(Context context, String str, int i) {
        if (((DevicePolicyManager) context.getSystemService("device_policy")) == null) {
            return null;
        }
        UserManager userManager = UserManager.get(context);
        UserHandle of = UserHandle.of(i);
        List userRestrictionSources = userManager.getUserRestrictionSources(str, of);
        if (userRestrictionSources.isEmpty()) {
            return null;
        }
        int size = userRestrictionSources.size();
        if (size <= 1) {
            int userRestrictionSource = ((UserManager.EnforcingUser) userRestrictionSources.get(0)).getUserRestrictionSource();
            int identifier = ((UserManager.EnforcingUser) userRestrictionSources.get(0)).getUserHandle().getIdentifier();
            if (userRestrictionSource != 4) {
                if (userRestrictionSource == 2) {
                    return identifier == i ? getDeviceOwner(context, str) : RestrictedLockUtils.EnforcedAdmin.createDefaultEnforcedAdminWithRestriction(str);
                }
                return null;
            } else if (identifier == i) {
                return getProfileOwner(context, str, identifier);
            } else {
                UserInfo profileParent = userManager.getProfileParent(identifier);
                return (profileParent == null || profileParent.id != i) ? RestrictedLockUtils.EnforcedAdmin.createDefaultEnforcedAdminWithRestriction(str) : getProfileOwner(context, str, identifier);
            }
        }
        RestrictedLockUtils.EnforcedAdmin createDefaultEnforcedAdminWithRestriction = RestrictedLockUtils.EnforcedAdmin.createDefaultEnforcedAdminWithRestriction(str);
        createDefaultEnforcedAdminWithRestriction.user = of;
        if (DEBUG) {
            Log.d("RestrictedLockUtils", "Multiple (" + size + ") enforcing users for restriction '" + str + "' on user " + of + "; returning default admin (" + createDefaultEnforcedAdminWithRestriction + ")");
        }
        return createDefaultEnforcedAdminWithRestriction;
    }

    public static RestrictedLockUtils.EnforcedAdmin getDeviceOwner(Context context, String str) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy");
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (devicePolicyManager == null) {
            return null;
        }
        ComponentName deviceOwnerComponentOnAnyUser = devicePolicyManager.getDeviceOwnerComponentOnAnyUser();
        if (deviceOwnerComponentOnAnyUser != null) {
            enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(deviceOwnerComponentOnAnyUser, str, devicePolicyManager.getDeviceOwnerUser());
        }
        return enforcedAdmin;
    }

    public static RestrictedLockUtils.EnforcedAdmin getProfileOwner(Context context, String str, int i) {
        DevicePolicyManager devicePolicyManager;
        if (i == -10000 || (devicePolicyManager = (DevicePolicyManager) context.getSystemService("device_policy")) == null) {
            return null;
        }
        ComponentName profileOwnerAsUser = devicePolicyManager.getProfileOwnerAsUser(i);
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (profileOwnerAsUser != null) {
            enforcedAdmin = new RestrictedLockUtils.EnforcedAdmin(profileOwnerAsUser, str, getUserHandleOf(i));
        }
        return enforcedAdmin;
    }

    public static Intent getShowRestrictedSettingsIntent(String str, int i) {
        Intent intent = new Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.UID", i);
        return intent;
    }

    public static UserHandle getUserHandleOf(int i) {
        if (i == -10000) {
            return null;
        }
        return UserHandle.of(i);
    }

    public static boolean hasBaseUserRestriction(Context context, String str, int i) {
        return ((UserManager) context.getSystemService("user")).hasBaseUserRestriction(str, UserHandle.of(i));
    }

    public static void sendShowRestrictedSettingDialogIntent(Context context, String str, int i) {
        context.startActivity(getShowRestrictedSettingsIntent(str, i));
    }
}