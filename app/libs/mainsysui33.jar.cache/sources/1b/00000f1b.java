package com.android.settingslib.wifi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.NetworkRequest;
import android.net.NetworkScoreManager;
import android.net.ScoredNetwork;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.provider.Settings;
import com.android.settingslib.R$string;
import com.android.settingslib.Utils;
import com.android.settingslib.wifi.WifiStatusTracker;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiStatusTracker.class */
public class WifiStatusTracker {
    public static final SimpleDateFormat SSDF = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
    public boolean connected;
    public boolean enabled;
    public boolean isCaptivePortal;
    public boolean isCarrierMerged;
    public boolean isDefaultNetwork;
    public int level;
    public final WifiNetworkScoreCache.CacheListener mCacheListener;
    public final Runnable mCallback;
    public final ConnectivityManager mConnectivityManager;
    public final Context mContext;
    public final Handler mHandler;
    public int mHistoryIndex;
    public final Handler mMainThreadHandler;
    public final NetworkScoreManager mNetworkScoreManager;
    public int mPrimaryNetworkId;
    public WifiInfo mWifiInfo;
    public final WifiManager mWifiManager;
    public final WifiNetworkScoreCache mWifiNetworkScoreCache;
    public int rssi;
    public String ssid;
    public int state;
    public String statusLabel;
    public int subId;
    public final Set<Integer> mNetworks = new HashSet();
    public final String[] mHistory = new String[32];
    public final NetworkRequest mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).addTransportType(0).build();
    public final ConnectivityManager.NetworkCallback mNetworkCallback = new AnonymousClass1(1);
    public final ConnectivityManager.NetworkCallback mDefaultNetworkCallback = new AnonymousClass2(1);
    public Network mDefaultNetwork = null;
    public NetworkCapabilities mDefaultNetworkCapabilities = null;

    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$1 */
    /* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiStatusTracker$1.class */
    public class AnonymousClass1 extends ConnectivityManager.NetworkCallback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$1$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$oIldb5fe6U0ojUjymlkNvi16S3Y(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onLost$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$rAjuo1d72P3VOtfiEDEqodlVgos(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onCapabilitiesChanged$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(int i) {
            super(i);
            WifiStatusTracker.this = r4;
        }

        public /* synthetic */ void lambda$onCapabilitiesChanged$0() {
            WifiStatusTracker.this.lambda$refreshLocale$0();
        }

        public /* synthetic */ void lambda$onLost$1() {
            WifiStatusTracker.this.lambda$refreshLocale$0();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiInfo wifiInfo;
            boolean z = false;
            boolean z2 = true;
            if (networkCapabilities.hasTransport(0)) {
                wifiInfo = Utils.tryGetWifiInfoForVcn(networkCapabilities);
                z = wifiInfo != null;
                z2 = false;
            } else if (networkCapabilities.hasTransport(1)) {
                wifiInfo = (WifiInfo) networkCapabilities.getTransportInfo();
            } else {
                wifiInfo = null;
                z2 = false;
            }
            if (z || z2) {
                WifiStatusTracker.this.recordLastWifiNetwork(WifiStatusTracker.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onCapabilitiesChanged: network=" + network + ",networkCapabilities=" + networkCapabilities);
            }
            if (wifiInfo == null || !wifiInfo.isPrimary()) {
                if (WifiStatusTracker.this.mNetworks.contains(Integer.valueOf(network.getNetId()))) {
                    WifiStatusTracker.this.mNetworks.remove(Integer.valueOf(network.getNetId()));
                    return;
                }
                return;
            }
            if (!WifiStatusTracker.this.mNetworks.contains(Integer.valueOf(network.getNetId()))) {
                WifiStatusTracker.this.mNetworks.add(Integer.valueOf(network.getNetId()));
            }
            WifiStatusTracker.this.mPrimaryNetworkId = network.getNetId();
            WifiStatusTracker.this.updateWifiInfo(wifiInfo);
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiStatusTracker.AnonymousClass1.$r8$lambda$rAjuo1d72P3VOtfiEDEqodlVgos(WifiStatusTracker.AnonymousClass1.this);
                }
            });
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            WifiStatusTracker.this.recordLastWifiNetwork(WifiStatusTracker.SSDF.format(Long.valueOf(System.currentTimeMillis())) + ",onLost: network=" + network);
            if (WifiStatusTracker.this.mNetworks.contains(Integer.valueOf(network.getNetId()))) {
                WifiStatusTracker.this.mNetworks.remove(Integer.valueOf(network.getNetId()));
            }
            if (network.getNetId() != WifiStatusTracker.this.mPrimaryNetworkId) {
                return;
            }
            WifiStatusTracker.this.updateWifiInfo(null);
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WifiStatusTracker.AnonymousClass1.$r8$lambda$oIldb5fe6U0ojUjymlkNvi16S3Y(WifiStatusTracker.AnonymousClass1.this);
                }
            });
        }
    }

    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$2 */
    /* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiStatusTracker$2.class */
    public class AnonymousClass2 extends ConnectivityManager.NetworkCallback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$2$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$P1JjMryg1XCcdeP8BWdCoHmSsy0(AnonymousClass2 anonymousClass2) {
            anonymousClass2.lambda$onLost$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$2$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$RwmY68rfL45zPa_G3AgQSxmfg70(AnonymousClass2 anonymousClass2) {
            anonymousClass2.lambda$onCapabilitiesChanged$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i) {
            super(i);
            WifiStatusTracker.this = r4;
        }

        public /* synthetic */ void lambda$onCapabilitiesChanged$0() {
            WifiStatusTracker.this.lambda$refreshLocale$0();
        }

        public /* synthetic */ void lambda$onLost$1() {
            WifiStatusTracker.this.lambda$refreshLocale$0();
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            WifiStatusTracker.this.mDefaultNetwork = network;
            WifiStatusTracker.this.mDefaultNetworkCapabilities = networkCapabilities;
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiStatusTracker.AnonymousClass2.$r8$lambda$RwmY68rfL45zPa_G3AgQSxmfg70(WifiStatusTracker.AnonymousClass2.this);
                }
            });
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            WifiStatusTracker.this.mDefaultNetwork = null;
            WifiStatusTracker.this.mDefaultNetworkCapabilities = null;
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$2$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    WifiStatusTracker.AnonymousClass2.$r8$lambda$P1JjMryg1XCcdeP8BWdCoHmSsy0(WifiStatusTracker.AnonymousClass2.this);
                }
            });
        }
    }

    /* renamed from: com.android.settingslib.wifi.WifiStatusTracker$3 */
    /* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiStatusTracker$3.class */
    public class AnonymousClass3 extends WifiNetworkScoreCache.CacheListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$3$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$Y8yDTjyB2_GWDYd6Lq-n7kzjJeo */
        public static /* synthetic */ void m1207$r8$lambda$Y8yDTjyB2_GWDYd6Lqn7kzjJeo(AnonymousClass3 anonymousClass3) {
            anonymousClass3.lambda$networkCacheUpdated$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Handler handler) {
            super(handler);
            WifiStatusTracker.this = r4;
        }

        public /* synthetic */ void lambda$networkCacheUpdated$0() {
            WifiStatusTracker.this.lambda$refreshLocale$0();
        }

        public void networkCacheUpdated(List<ScoredNetwork> list) {
            WifiStatusTracker.this.updateStatusLabel();
            WifiStatusTracker.this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiStatusTracker.AnonymousClass3.m1207$r8$lambda$Y8yDTjyB2_GWDYd6Lqn7kzjJeo(WifiStatusTracker.AnonymousClass3.this);
                }
            });
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.WifiStatusTracker$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$bY4CL-kOWWPy-o0OWEI952dPOBQ */
    public static /* synthetic */ void m1194$r8$lambda$bY4CLkOWWPyo0OWEI952dPOBQ(WifiStatusTracker wifiStatusTracker) {
        wifiStatusTracker.lambda$refreshLocale$0();
    }

    public WifiStatusTracker(Context context, WifiManager wifiManager, NetworkScoreManager networkScoreManager, ConnectivityManager connectivityManager, Runnable runnable, Handler handler, Handler handler2) {
        this.mContext = context;
        this.mWifiManager = wifiManager;
        this.mWifiNetworkScoreCache = new WifiNetworkScoreCache(context);
        this.mNetworkScoreManager = networkScoreManager;
        this.mConnectivityManager = connectivityManager;
        this.mCallback = runnable;
        if (handler2 == null) {
            HandlerThread handlerThread = new HandlerThread("WifiStatusTrackerHandler");
            handlerThread.start();
            this.mHandler = new Handler(handlerThread.getLooper());
        } else {
            this.mHandler = handler2;
        }
        this.mMainThreadHandler = handler == null ? new Handler(Looper.getMainLooper()) : handler;
        this.mCacheListener = new AnonymousClass3(this.mHandler);
    }

    public void dump(PrintWriter printWriter) {
        int i;
        printWriter.println("  - WiFi Network History ------");
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = i3;
            if (i2 >= 32) {
                break;
            }
            int i4 = i;
            if (this.mHistory[i2] != null) {
                i4 = i + 1;
            }
            i2++;
            i3 = i4;
        }
        for (int i5 = (this.mHistoryIndex + 32) - 1; i5 >= (this.mHistoryIndex + 32) - i; i5 += -1) {
            printWriter.println("  Previous WiFiNetwork(" + ((this.mHistoryIndex + 32) - i5) + "): " + this.mHistory[i5 & 31]);
        }
    }

    public void fetchInitialState() {
        if (this.mWifiManager == null) {
            return;
        }
        updateWifiState();
        boolean z = true;
        NetworkInfo networkInfo = this.mConnectivityManager.getNetworkInfo(1);
        if (networkInfo == null || !networkInfo.isConnected()) {
            z = false;
        }
        this.connected = z;
        this.mWifiInfo = null;
        this.ssid = null;
        if (z) {
            WifiInfo connectionInfo = this.mWifiManager.getConnectionInfo();
            this.mWifiInfo = connectionInfo;
            if (connectionInfo != null) {
                if (connectionInfo.isPasspointAp() || this.mWifiInfo.isOsuAp()) {
                    this.ssid = this.mWifiInfo.getPasspointProviderFriendlyName();
                } else {
                    this.ssid = getValidSsid(this.mWifiInfo);
                }
                this.isCarrierMerged = this.mWifiInfo.isCarrierMerged();
                this.subId = this.mWifiInfo.getSubscriptionId();
                updateRssi(this.mWifiInfo.getRssi());
                maybeRequestNetworkScore();
            }
        }
        updateStatusLabel();
    }

    public final String getValidSsid(WifiInfo wifiInfo) {
        String ssid = wifiInfo.getSSID();
        if (ssid == null || "<unknown ssid>".equals(ssid)) {
            return null;
        }
        return ssid;
    }

    public void handleBroadcast(Intent intent) {
        if (this.mWifiManager != null && intent.getAction().equals("android.net.wifi.WIFI_STATE_CHANGED")) {
            updateWifiState();
        }
    }

    public final void maybeRequestNetworkScore() {
        NetworkKey createFromWifiInfo = NetworkKey.createFromWifiInfo(this.mWifiInfo);
        if (this.mWifiNetworkScoreCache.getScoredNetwork(createFromWifiInfo) == null) {
            this.mNetworkScoreManager.requestScores(new NetworkKey[]{createFromWifiInfo});
        }
    }

    /* renamed from: postResults */
    public final void lambda$refreshLocale$0() {
        this.mCallback.run();
    }

    public final void recordLastWifiNetwork(String str) {
        String[] strArr = this.mHistory;
        int i = this.mHistoryIndex;
        strArr[i] = str;
        this.mHistoryIndex = (i + 1) % 32;
    }

    public void refreshLocale() {
        updateStatusLabel();
        this.mMainThreadHandler.post(new Runnable() { // from class: com.android.settingslib.wifi.WifiStatusTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                WifiStatusTracker.m1194$r8$lambda$bY4CLkOWWPyo0OWEI952dPOBQ(WifiStatusTracker.this);
            }
        });
    }

    public void setListening(boolean z) {
        if (z) {
            this.mNetworkScoreManager.registerNetworkScoreCache(1, this.mWifiNetworkScoreCache, 1);
            this.mWifiNetworkScoreCache.registerListener(this.mCacheListener);
            this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback, this.mHandler);
            this.mConnectivityManager.registerDefaultNetworkCallback(this.mDefaultNetworkCallback, this.mHandler);
            return;
        }
        this.mNetworkScoreManager.unregisterNetworkScoreCache(1, this.mWifiNetworkScoreCache);
        this.mWifiNetworkScoreCache.unregisterListener();
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mDefaultNetworkCallback);
    }

    public final void updateRssi(int i) {
        this.rssi = i;
        this.level = this.mWifiManager.calculateSignalLevel(i);
    }

    public final void updateStatusLabel() {
        NetworkCapabilities networkCapabilities;
        if (this.mWifiManager == null) {
            return;
        }
        this.isDefaultNetwork = false;
        NetworkCapabilities networkCapabilities2 = this.mDefaultNetworkCapabilities;
        if (networkCapabilities2 != null) {
            boolean hasTransport = networkCapabilities2.hasTransport(1);
            boolean z = this.mDefaultNetworkCapabilities.hasTransport(0) && Utils.tryGetWifiInfoForVcn(this.mDefaultNetworkCapabilities) != null;
            if (hasTransport || z) {
                this.isDefaultNetwork = true;
            }
        }
        NetworkCapabilities networkCapabilities3 = this.isDefaultNetwork ? this.mDefaultNetworkCapabilities : this.mConnectivityManager.getNetworkCapabilities(this.mWifiManager.getCurrentNetwork());
        this.isCaptivePortal = false;
        if (networkCapabilities3 != null) {
            if (networkCapabilities3.hasCapability(17)) {
                this.statusLabel = this.mContext.getString(R$string.wifi_status_sign_in_required);
                this.isCaptivePortal = true;
                return;
            } else if (networkCapabilities3.hasCapability(24)) {
                this.statusLabel = this.mContext.getString(R$string.wifi_limited_connection);
                return;
            } else if (!networkCapabilities3.hasCapability(16)) {
                Settings.Global.getString(this.mContext.getContentResolver(), "private_dns_mode");
                if (networkCapabilities3.isPrivateDnsBroken()) {
                    this.statusLabel = this.mContext.getString(R$string.private_dns_broken);
                    return;
                } else {
                    this.statusLabel = this.mContext.getString(R$string.wifi_status_no_internet);
                    return;
                }
            } else if (!this.isDefaultNetwork && (networkCapabilities = this.mDefaultNetworkCapabilities) != null && networkCapabilities.hasTransport(0)) {
                this.statusLabel = this.mContext.getString(R$string.wifi_connected_low_quality);
                return;
            }
        }
        ScoredNetwork scoredNetwork = this.mWifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(this.mWifiInfo));
        this.statusLabel = scoredNetwork == null ? null : AccessPoint.getSpeedLabel(this.mContext, scoredNetwork, this.rssi);
    }

    public final void updateWifiInfo(WifiInfo wifiInfo) {
        updateWifiState();
        this.connected = wifiInfo != null;
        this.mWifiInfo = wifiInfo;
        this.ssid = null;
        if (wifiInfo != null) {
            if (wifiInfo.isPasspointAp() || this.mWifiInfo.isOsuAp()) {
                this.ssid = this.mWifiInfo.getPasspointProviderFriendlyName();
            } else {
                this.ssid = getValidSsid(this.mWifiInfo);
            }
            this.isCarrierMerged = this.mWifiInfo.isCarrierMerged();
            this.subId = this.mWifiInfo.getSubscriptionId();
            updateRssi(this.mWifiInfo.getRssi());
            maybeRequestNetworkScore();
        }
    }

    public final void updateWifiState() {
        int wifiState = this.mWifiManager.getWifiState();
        this.state = wifiState;
        this.enabled = wifiState == 3;
    }
}