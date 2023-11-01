package com.android.systemui.qs.tiles.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.util.Log;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/WifiStateWorker.class */
public class WifiStateWorker extends BroadcastReceiver {
    public DelayableExecutor mBackgroundExecutor;
    public WifiManager mWifiManager;
    public int mWifiState = 1;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$NFcZE_PYNvNEOt0PEAQY0B44J3w(WifiStateWorker wifiStateWorker) {
        wifiStateWorker.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$zlfOEnptLrrqlfr7JQ0XzGXD6aA(WifiStateWorker wifiStateWorker, boolean z) {
        wifiStateWorker.lambda$setWifiEnabled$1(z);
    }

    public WifiStateWorker(BroadcastDispatcher broadcastDispatcher, DelayableExecutor delayableExecutor, WifiManager wifiManager) {
        this.mWifiManager = wifiManager;
        this.mBackgroundExecutor = delayableExecutor;
        broadcastDispatcher.registerReceiver(this, new IntentFilter("android.net.wifi.WIFI_STATE_CHANGED"));
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WifiStateWorker.$r8$lambda$NFcZE_PYNvNEOt0PEAQY0B44J3w(WifiStateWorker.this);
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        this.mWifiState = wifiManager.getWifiState();
        Log.i("WifiStateWorker", "WifiManager.getWifiState():" + this.mWifiState);
    }

    public /* synthetic */ void lambda$setWifiEnabled$1(boolean z) {
        WifiManager wifiManager = this.mWifiManager;
        if (wifiManager == null) {
            return;
        }
        this.mWifiState = z ? 2 : 0;
        if (wifiManager.setWifiEnabled(z)) {
            return;
        }
        Log.e("WifiStateWorker", "Failed to WifiManager.setWifiEnabled(" + z + ");");
    }

    public boolean isWifiEnabled() {
        int i = this.mWifiState;
        return i == 3 || i == 2;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        int intExtra;
        if (intent == null || !"android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction()) || (intExtra = intent.getIntExtra("wifi_state", 1)) == 4) {
            return;
        }
        this.mWifiState = intExtra;
    }

    public void setWifiEnabled(final boolean z) {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.WifiStateWorker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                WifiStateWorker.$r8$lambda$zlfOEnptLrrqlfr7JQ0XzGXD6aA(WifiStateWorker.this, z);
            }
        });
    }
}