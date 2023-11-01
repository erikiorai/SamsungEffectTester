package com.android.systemui.qs.external;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.RemoteException;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/PackageManagerAdapter.class */
public class PackageManagerAdapter {
    public IPackageManager mIPackageManager = AppGlobals.getPackageManager();
    public PackageManager mPackageManager;

    public PackageManagerAdapter(Context context) {
        this.mPackageManager = context.getPackageManager();
    }

    public PackageInfo getPackageInfoAsUser(String str, int i, int i2) throws PackageManager.NameNotFoundException {
        return this.mPackageManager.getPackageInfoAsUser(str, i, i2);
    }

    public ServiceInfo getServiceInfo(ComponentName componentName, int i) throws PackageManager.NameNotFoundException {
        return this.mPackageManager.getServiceInfo(componentName, i);
    }

    public ServiceInfo getServiceInfo(ComponentName componentName, int i, int i2) throws RemoteException {
        return this.mIPackageManager.getServiceInfo(componentName, i, i2);
    }
}