package com.android.systemui.controls;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.systemui.R$array;
import java.util.List;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlsServiceInfo.class */
public final class ControlsServiceInfo extends DefaultAppInfo {
    public final ComponentName _panelActivity;
    public final Context context;
    public ComponentName panelActivity;
    public boolean resolved;
    public final ServiceInfo serviceInfo;

    public ControlsServiceInfo(Context context, ServiceInfo serviceInfo) {
        super(context, context.getPackageManager(), context.getUserId(), serviceInfo.getComponentName());
        this.context = context;
        this.serviceInfo = serviceInfo;
        Bundle bundle = serviceInfo.metaData;
        String string = bundle != null ? bundle.getString("android.service.controls.META_DATA_PANEL_ACTIVITY") : null;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(string == null ? "" : string);
        if (unflattenFromString == null || !Intrinsics.areEqual(unflattenFromString.getPackageName(), this.componentName.getPackageName())) {
            this._panelActivity = null;
        } else {
            this._panelActivity = unflattenFromString;
        }
    }

    public final ControlsServiceInfo copy() {
        ControlsServiceInfo controlsServiceInfo = new ControlsServiceInfo(this.context, this.serviceInfo);
        controlsServiceInfo.panelActivity = this.panelActivity;
        return controlsServiceInfo;
    }

    public boolean equals(Object obj) {
        boolean z;
        if (obj instanceof ControlsServiceInfo) {
            ControlsServiceInfo controlsServiceInfo = (ControlsServiceInfo) obj;
            if (this.userId == controlsServiceInfo.userId && Intrinsics.areEqual(this.componentName, controlsServiceInfo.componentName) && Intrinsics.areEqual(this.panelActivity, controlsServiceInfo.panelActivity)) {
                z = true;
                return z;
            }
        }
        z = false;
        return z;
    }

    public final ComponentName getPanelActivity() {
        return this.panelActivity;
    }

    public final ServiceInfo getServiceInfo() {
        return this.serviceInfo;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.userId), this.componentName, this.panelActivity);
    }

    public final boolean isComponentActuallyEnabled(ActivityInfo activityInfo) {
        int componentEnabledSetting = this.mPm.getComponentEnabledSetting(activityInfo.getComponentName());
        boolean z = true;
        if (componentEnabledSetting == 0) {
            z = activityInfo.enabled;
        } else if (componentEnabledSetting != 1) {
            z = false;
        }
        return z;
    }

    public final void resolvePanelActivity(boolean z) {
        if (this.resolved) {
            return;
        }
        this.resolved = true;
        if (ArraysKt___ArraysKt.contains(this.context.getResources().getStringArray(R$array.config_controlsPreferredPackages), this.componentName.getPackageName()) || z) {
            ComponentName componentName = this._panelActivity;
            ComponentName componentName2 = null;
            if (componentName != null) {
                List queryIntentActivitiesAsUser = this.mPm.queryIntentActivitiesAsUser(new Intent().setComponent(componentName), PackageManager.ResolveInfoFlags.of(786432L), UserHandle.of(this.userId));
                componentName2 = ((true ^ queryIntentActivitiesAsUser.isEmpty()) && verifyResolveInfo((ResolveInfo) queryIntentActivitiesAsUser.get(0))) ? componentName : null;
            }
            this.panelActivity = componentName2;
        }
    }

    public String toString() {
        ServiceInfo serviceInfo = this.serviceInfo;
        ComponentName componentName = this.panelActivity;
        boolean z = this.resolved;
        return StringsKt__IndentKt.trimIndent("\n            ControlsServiceInfo(serviceInfo=" + serviceInfo + ", panelActivity=" + componentName + ", resolved=" + z + ")\n        ");
    }

    public final boolean verifyResolveInfo(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        boolean z = false;
        if (activityInfo != null) {
            z = false;
            if (Intrinsics.areEqual(activityInfo.permission, "android.permission.BIND_CONTROLS")) {
                z = false;
                if (activityInfo.exported) {
                    z = false;
                    if (isComponentActuallyEnabled(activityInfo)) {
                        z = true;
                    }
                }
            }
        }
        return z;
    }
}