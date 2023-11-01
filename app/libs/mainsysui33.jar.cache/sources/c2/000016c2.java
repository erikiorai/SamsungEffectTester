package com.android.systemui.dreams;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.android.systemui.CoreStartable;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayRegistrant.class */
public class DreamOverlayRegistrant implements CoreStartable {
    public static final boolean DEBUG = Log.isLoggable("DreamOverlayRegistrant", 3);
    public final Context mContext;
    public boolean mCurrentRegisteredState;
    public final IDreamManager mDreamManager;
    public final ComponentName mOverlayServiceComponent;
    public final BroadcastReceiver mReceiver;

    public final void registerOverlayService() {
        boolean z;
        String str;
        PackageManager packageManager = this.mContext.getPackageManager();
        packageManager.getComponentEnabledSetting(this.mOverlayServiceComponent);
        try {
            z = packageManager.getServiceInfo(this.mOverlayServiceComponent, RecyclerView.ViewHolder.FLAG_IGNORE).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("DreamOverlayRegistrant", "could not find dream overlay service");
            z = false;
        }
        if (this.mCurrentRegisteredState == z) {
            return;
        }
        this.mCurrentRegisteredState = z;
        try {
            if (DEBUG) {
                if (z) {
                    str = "registering dream overlay service:" + this.mOverlayServiceComponent;
                } else {
                    str = "clearing dream overlay service";
                }
                Log.d("DreamOverlayRegistrant", str);
            }
            this.mDreamManager.registerDreamOverlayService(this.mCurrentRegisteredState ? this.mOverlayServiceComponent : null);
        } catch (RemoteException e2) {
            Log.e("DreamOverlayRegistrant", "could not register dream overlay service:" + e2);
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(this.mOverlayServiceComponent.getPackageName(), 0);
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
        registerOverlayService();
    }
}