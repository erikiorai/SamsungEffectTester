package com.android.systemui.security.data.model;

import android.app.admin.DeviceAdminInfo;
import android.graphics.drawable.Drawable;
import com.android.systemui.statusbar.policy.SecurityController;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/security/data/model/SecurityModel.class */
public final class SecurityModel {
    public static final Companion Companion = new Companion(null);
    public final Drawable deviceAdminIcon;
    public final String deviceOwnerOrganizationName;
    public final boolean hasCACertInCurrentUser;
    public final boolean hasCACertInWorkProfile;
    public final boolean hasWorkProfile;
    public final boolean isDeviceManaged;
    public final boolean isNetworkLoggingEnabled;
    public final boolean isParentalControlsEnabled;
    public final boolean isProfileOwnerOfOrganizationOwnedDevice;
    public final boolean isVpnBranded;
    public final boolean isWorkProfileOn;
    public final String primaryVpnName;
    public final String workProfileOrganizationName;
    public final String workProfileVpnName;

    /* loaded from: mainsysui33.jar:com/android/systemui/security/data/model/SecurityModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final SecurityModel create(SecurityController securityController) {
            String str = null;
            DeviceAdminInfo deviceAdminInfo = securityController.isParentalControlsEnabled() ? securityController.getDeviceAdminInfo() : null;
            boolean isDeviceManaged = securityController.isDeviceManaged();
            boolean hasWorkProfile = securityController.hasWorkProfile();
            boolean isWorkProfileOn = securityController.isWorkProfileOn();
            boolean isProfileOwnerOfOrganizationOwnedDevice = securityController.isProfileOwnerOfOrganizationOwnedDevice();
            CharSequence deviceOwnerOrganizationName = securityController.getDeviceOwnerOrganizationName();
            String obj = deviceOwnerOrganizationName != null ? deviceOwnerOrganizationName.toString() : null;
            CharSequence workProfileOrganizationName = securityController.getWorkProfileOrganizationName();
            if (workProfileOrganizationName != null) {
                str = workProfileOrganizationName.toString();
            }
            return new SecurityModel(isDeviceManaged, hasWorkProfile, isWorkProfileOn, isProfileOwnerOfOrganizationOwnedDevice, obj, str, securityController.isNetworkLoggingEnabled(), securityController.isVpnBranded(), securityController.getPrimaryVpnName(), securityController.getWorkProfileVpnName(), securityController.hasCACertInCurrentUser(), securityController.hasCACertInWorkProfile(), securityController.isParentalControlsEnabled(), securityController.getIcon(deviceAdminInfo));
        }

        public final Object create(SecurityController securityController, CoroutineDispatcher coroutineDispatcher, Continuation<? super SecurityModel> continuation) {
            return BuildersKt.withContext(coroutineDispatcher, new SecurityModel$Companion$create$2(securityController, null), continuation);
        }
    }

    public SecurityModel(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2, boolean z5, boolean z6, String str3, String str4, boolean z7, boolean z8, boolean z9, Drawable drawable) {
        this.isDeviceManaged = z;
        this.hasWorkProfile = z2;
        this.isWorkProfileOn = z3;
        this.isProfileOwnerOfOrganizationOwnedDevice = z4;
        this.deviceOwnerOrganizationName = str;
        this.workProfileOrganizationName = str2;
        this.isNetworkLoggingEnabled = z5;
        this.isVpnBranded = z6;
        this.primaryVpnName = str3;
        this.workProfileVpnName = str4;
        this.hasCACertInCurrentUser = z7;
        this.hasCACertInWorkProfile = z8;
        this.isParentalControlsEnabled = z9;
        this.deviceAdminIcon = drawable;
    }

    public static final SecurityModel create(SecurityController securityController) {
        return Companion.create(securityController);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SecurityModel) {
            SecurityModel securityModel = (SecurityModel) obj;
            return this.isDeviceManaged == securityModel.isDeviceManaged && this.hasWorkProfile == securityModel.hasWorkProfile && this.isWorkProfileOn == securityModel.isWorkProfileOn && this.isProfileOwnerOfOrganizationOwnedDevice == securityModel.isProfileOwnerOfOrganizationOwnedDevice && Intrinsics.areEqual(this.deviceOwnerOrganizationName, securityModel.deviceOwnerOrganizationName) && Intrinsics.areEqual(this.workProfileOrganizationName, securityModel.workProfileOrganizationName) && this.isNetworkLoggingEnabled == securityModel.isNetworkLoggingEnabled && this.isVpnBranded == securityModel.isVpnBranded && Intrinsics.areEqual(this.primaryVpnName, securityModel.primaryVpnName) && Intrinsics.areEqual(this.workProfileVpnName, securityModel.workProfileVpnName) && this.hasCACertInCurrentUser == securityModel.hasCACertInCurrentUser && this.hasCACertInWorkProfile == securityModel.hasCACertInWorkProfile && this.isParentalControlsEnabled == securityModel.isParentalControlsEnabled && Intrinsics.areEqual(this.deviceAdminIcon, securityModel.deviceAdminIcon);
        }
        return false;
    }

    public final Drawable getDeviceAdminIcon() {
        return this.deviceAdminIcon;
    }

    public final String getDeviceOwnerOrganizationName() {
        return this.deviceOwnerOrganizationName;
    }

    public final boolean getHasCACertInCurrentUser() {
        return this.hasCACertInCurrentUser;
    }

    public final boolean getHasCACertInWorkProfile() {
        return this.hasCACertInWorkProfile;
    }

    public final boolean getHasWorkProfile() {
        return this.hasWorkProfile;
    }

    public final String getPrimaryVpnName() {
        return this.primaryVpnName;
    }

    public final String getWorkProfileOrganizationName() {
        return this.workProfileOrganizationName;
    }

    public final String getWorkProfileVpnName() {
        return this.workProfileVpnName;
    }

    public int hashCode() {
        boolean z = this.isDeviceManaged;
        int i = 1;
        boolean z2 = z;
        if (z) {
            z2 = true;
        }
        boolean z3 = this.hasWorkProfile;
        boolean z4 = z3;
        if (z3) {
            z4 = true;
        }
        boolean z5 = this.isWorkProfileOn;
        boolean z6 = z5;
        if (z5) {
            z6 = true;
        }
        boolean z7 = this.isProfileOwnerOfOrganizationOwnedDevice;
        boolean z8 = z7;
        if (z7) {
            z8 = true;
        }
        String str = this.deviceOwnerOrganizationName;
        int i2 = 0;
        int hashCode = str == null ? 0 : str.hashCode();
        String str2 = this.workProfileOrganizationName;
        int hashCode2 = str2 == null ? 0 : str2.hashCode();
        boolean z9 = this.isNetworkLoggingEnabled;
        int i3 = z9 ? 1 : 0;
        if (z9) {
            i3 = 1;
        }
        boolean z10 = this.isVpnBranded;
        int i4 = z10 ? 1 : 0;
        if (z10) {
            i4 = 1;
        }
        String str3 = this.primaryVpnName;
        int hashCode3 = str3 == null ? 0 : str3.hashCode();
        String str4 = this.workProfileVpnName;
        int hashCode4 = str4 == null ? 0 : str4.hashCode();
        boolean z11 = this.hasCACertInCurrentUser;
        int i5 = z11 ? 1 : 0;
        if (z11) {
            i5 = 1;
        }
        boolean z12 = this.hasCACertInWorkProfile;
        int i6 = z12 ? 1 : 0;
        if (z12) {
            i6 = 1;
        }
        boolean z13 = this.isParentalControlsEnabled;
        if (!z13) {
            i = z13 ? 1 : 0;
        }
        Drawable drawable = this.deviceAdminIcon;
        if (drawable != null) {
            i2 = drawable.hashCode();
        }
        return ((((((((((((((((((((((((((z2 ? 1 : 0) * 31) + (z4 ? 1 : 0)) * 31) + (z6 ? 1 : 0)) * 31) + (z8 ? 1 : 0)) * 31) + hashCode) * 31) + hashCode2) * 31) + i3) * 31) + i4) * 31) + hashCode3) * 31) + hashCode4) * 31) + i5) * 31) + i6) * 31) + i) * 31) + i2;
    }

    public final boolean isDeviceManaged() {
        return this.isDeviceManaged;
    }

    public final boolean isNetworkLoggingEnabled() {
        return this.isNetworkLoggingEnabled;
    }

    public final boolean isParentalControlsEnabled() {
        return this.isParentalControlsEnabled;
    }

    public final boolean isProfileOwnerOfOrganizationOwnedDevice() {
        return this.isProfileOwnerOfOrganizationOwnedDevice;
    }

    public final boolean isVpnBranded() {
        return this.isVpnBranded;
    }

    public final boolean isWorkProfileOn() {
        return this.isWorkProfileOn;
    }

    public String toString() {
        boolean z = this.isDeviceManaged;
        boolean z2 = this.hasWorkProfile;
        boolean z3 = this.isWorkProfileOn;
        boolean z4 = this.isProfileOwnerOfOrganizationOwnedDevice;
        String str = this.deviceOwnerOrganizationName;
        String str2 = this.workProfileOrganizationName;
        boolean z5 = this.isNetworkLoggingEnabled;
        boolean z6 = this.isVpnBranded;
        String str3 = this.primaryVpnName;
        String str4 = this.workProfileVpnName;
        boolean z7 = this.hasCACertInCurrentUser;
        boolean z8 = this.hasCACertInWorkProfile;
        boolean z9 = this.isParentalControlsEnabled;
        Drawable drawable = this.deviceAdminIcon;
        return "SecurityModel(isDeviceManaged=" + z + ", hasWorkProfile=" + z2 + ", isWorkProfileOn=" + z3 + ", isProfileOwnerOfOrganizationOwnedDevice=" + z4 + ", deviceOwnerOrganizationName=" + str + ", workProfileOrganizationName=" + str2 + ", isNetworkLoggingEnabled=" + z5 + ", isVpnBranded=" + z6 + ", primaryVpnName=" + str3 + ", workProfileVpnName=" + str4 + ", hasCACertInCurrentUser=" + z7 + ", hasCACertInWorkProfile=" + z8 + ", isParentalControlsEnabled=" + z9 + ", deviceAdminIcon=" + drawable + ")";
    }
}