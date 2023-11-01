package com.android.settingslib.wifi;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.NetworkKey;
import android.net.ScoredNetwork;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkScoreCache;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.CollectionUtils;
import com.android.settingslib.R$string;
import com.android.settingslib.utils.ThreadUtils;
import com.android.settingslib.wifi.AccessPoint;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/settingslib/wifi/AccessPoint.class */
public class AccessPoint implements Comparable<AccessPoint> {
    public static final AtomicInteger sLastId = new AtomicInteger(0);
    public String bssid;
    public WifiConfiguration mConfig;
    public WifiManager.ActionListener mConnectListener;
    public final Context mContext;
    public int mEapType;
    public final ArraySet<ScanResult> mExtraScanResults;
    public String mFqdn;
    public WifiInfo mInfo;
    public boolean mIsOweTransitionMode;
    public boolean mIsPskSaeTransitionMode;
    public boolean mIsRoaming;
    public boolean mIsScoredNetworkMetered;
    public String mKey;
    public final Object mLock;
    public NetworkInfo mNetworkInfo;
    public String mOsuFailure;
    public OsuProvider mOsuProvider;
    public boolean mOsuProvisioningComplete;
    public String mOsuStatus;
    public int mPasspointConfigurationVersion;
    public String mPasspointUniqueId;
    public String mProviderFriendlyName;
    public int mRssi;
    public final ArraySet<ScanResult> mScanResults;
    public final Map<String, TimestampedScoredNetwork> mScoredNetworkCache;
    public int mSpeed;
    public long mSubscriptionExpirationTimeInMillis;
    public Object mTag;
    public WifiManager mWifiManager;
    public int networkId;
    public int pskType;
    public int security;
    public String ssid;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/settingslib/wifi/AccessPoint$AccessPointProvisioningCallback.class */
    public class AccessPointProvisioningCallback extends ProvisioningCallback {
        public final /* synthetic */ AccessPoint this$0;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda1.run():void] */
        /* renamed from: $r8$lambda$Ad-IMAj4XL5vF-wXbWlpzENST4s */
        public static /* synthetic */ void m1190$r8$lambda$AdIMAj4XL5vFwXbWlpzENST4s(AccessPointProvisioningCallback accessPointProvisioningCallback) {
            accessPointProvisioningCallback.lambda$onProvisioningComplete$2();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda2.run():void] */
        /* renamed from: $r8$lambda$F5BH6OuwGlHQBUo7DyB1s-3hZzQ */
        public static /* synthetic */ void m1191$r8$lambda$F5BH6OuwGlHQBUo7DyB1s3hZzQ(AccessPointProvisioningCallback accessPointProvisioningCallback) {
            accessPointProvisioningCallback.lambda$onProvisioningFailure$0();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$jJXQxP8dnxL3IonFSsnUmrhqyPI(AccessPointProvisioningCallback accessPointProvisioningCallback) {
            accessPointProvisioningCallback.lambda$onProvisioningStatus$1();
        }

        public /* synthetic */ void lambda$onProvisioningComplete$2() {
            this.this$0.getClass();
        }

        public /* synthetic */ void lambda$onProvisioningFailure$0() {
            this.this$0.getClass();
        }

        public /* synthetic */ void lambda$onProvisioningStatus$1() {
            this.this$0.getClass();
        }

        public void onProvisioningComplete() {
            this.this$0.mOsuProvisioningComplete = true;
            this.this$0.mOsuFailure = null;
            this.this$0.mOsuStatus = null;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.AccessPointProvisioningCallback.m1190$r8$lambda$AdIMAj4XL5vFwXbWlpzENST4s(AccessPoint.AccessPointProvisioningCallback.this);
                }
            });
            WifiManager wifiManager = this.this$0.getWifiManager();
            PasspointConfiguration passpointConfiguration = (PasspointConfiguration) wifiManager.getMatchingPasspointConfigsForOsuProviders(Collections.singleton(this.this$0.mOsuProvider)).get(this.this$0.mOsuProvider);
            if (passpointConfiguration == null) {
                Log.e("SettingsLib.AccessPoint", "Missing PasspointConfiguration for newly provisioned network!");
                if (this.this$0.mConnectListener != null) {
                    this.this$0.mConnectListener.onFailure(0);
                    return;
                }
                return;
            }
            String uniqueId = passpointConfiguration.getUniqueId();
            for (Pair pair : wifiManager.getAllMatchingWifiConfigs(wifiManager.getScanResults())) {
                WifiConfiguration wifiConfiguration = (WifiConfiguration) pair.first;
                if (TextUtils.equals(wifiConfiguration.getKey(), uniqueId)) {
                    wifiManager.connect(new AccessPoint(this.this$0.mContext, wifiConfiguration, (List) ((Map) pair.second).get(0), (List) ((Map) pair.second).get(1)).getConfig(), this.this$0.mConnectListener);
                    return;
                }
            }
            if (this.this$0.mConnectListener != null) {
                this.this$0.mConnectListener.onFailure(0);
            }
        }

        public void onProvisioningFailure(int i) {
            if (TextUtils.equals(this.this$0.mOsuStatus, this.this$0.mContext.getString(R$string.osu_completing_sign_up))) {
                AccessPoint accessPoint = this.this$0;
                accessPoint.mOsuFailure = accessPoint.mContext.getString(R$string.osu_sign_up_failed);
            } else {
                AccessPoint accessPoint2 = this.this$0;
                accessPoint2.mOsuFailure = accessPoint2.mContext.getString(R$string.osu_connect_failed);
            }
            this.this$0.mOsuStatus = null;
            this.this$0.mOsuProvisioningComplete = false;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.AccessPointProvisioningCallback.m1191$r8$lambda$F5BH6OuwGlHQBUo7DyB1s3hZzQ(AccessPoint.AccessPointProvisioningCallback.this);
                }
            });
        }

        public void onProvisioningStatus(int i) {
            String format;
            switch (i) {
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    format = String.format(this.this$0.mContext.getString(R$string.osu_opening_provider), this.this$0.mOsuProvider.getFriendlyName());
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                    format = this.this$0.mContext.getString(R$string.osu_completing_sign_up);
                    break;
                default:
                    format = null;
                    break;
            }
            boolean equals = TextUtils.equals(this.this$0.mOsuStatus, format);
            this.this$0.mOsuStatus = format;
            this.this$0.mOsuFailure = null;
            this.this$0.mOsuProvisioningComplete = false;
            if (true ^ equals) {
                ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$AccessPointProvisioningCallback$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AccessPoint.AccessPointProvisioningCallback.$r8$lambda$jJXQxP8dnxL3IonFSsnUmrhqyPI(AccessPoint.AccessPointProvisioningCallback.this);
                    }
                });
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$1MyryaP_FPxIoOWXMURmr9gSCko(AccessPoint accessPoint) {
        accessPoint.lambda$setScanResults$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$9sqJQrSDLPPBbVafxevqWDKYWqc(AccessPoint accessPoint) {
        accessPoint.lambda$update$5();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda3.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$eeEFeIMR6UCrf_qCtv1iNqRPOo8(long j, Iterator it, TimestampedScoredNetwork timestampedScoredNetwork) {
        lambda$updateScores$0(j, it, timestampedScoredNetwork);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$l7HvoRkL0mn502GEmI0-49YA2Xk */
    public static /* synthetic */ void m1180$r8$lambda$l7HvoRkL0mn502GEmI049YA2Xk(AccessPoint accessPoint) {
        accessPoint.lambda$setScanResults$2();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        loadConfig(wifiConfiguration);
        updateKey();
    }

    public AccessPoint(Context context, WifiConfiguration wifiConfiguration, Collection<ScanResult> collection, Collection<ScanResult> collection2) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
        this.mPasspointUniqueId = wifiConfiguration.getKey();
        this.mFqdn = wifiConfiguration.FQDN;
        setScanResultsPasspoint(collection, collection2);
        updateKey();
    }

    public AccessPoint(Context context, OsuProvider osuProvider, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        this.mOsuProvider = osuProvider;
        setScanResults(collection);
        updateKey();
    }

    public AccessPoint(Context context, Bundle bundle) {
        this.mLock = new Object();
        ArraySet<ScanResult> arraySet = new ArraySet<>();
        this.mScanResults = arraySet;
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        if (bundle.containsKey("key_config")) {
            this.mConfig = (WifiConfiguration) bundle.getParcelable("key_config");
        }
        WifiConfiguration wifiConfiguration = this.mConfig;
        if (wifiConfiguration != null) {
            loadConfig(wifiConfiguration);
        }
        if (bundle.containsKey("key_ssid")) {
            this.ssid = bundle.getString("key_ssid");
        }
        if (bundle.containsKey("key_security")) {
            this.security = bundle.getInt("key_security");
        }
        if (bundle.containsKey("key_speed")) {
            this.mSpeed = bundle.getInt("key_speed");
        }
        if (bundle.containsKey("key_psktype")) {
            this.pskType = bundle.getInt("key_psktype");
        }
        if (bundle.containsKey("eap_psktype")) {
            this.mEapType = bundle.getInt("eap_psktype");
        }
        this.mInfo = (WifiInfo) bundle.getParcelable("key_wifiinfo");
        if (bundle.containsKey("key_networkinfo")) {
            this.mNetworkInfo = (NetworkInfo) bundle.getParcelable("key_networkinfo");
        }
        if (bundle.containsKey("key_scanresults")) {
            Parcelable[] parcelableArray = bundle.getParcelableArray("key_scanresults");
            arraySet.clear();
            for (Parcelable parcelable : parcelableArray) {
                this.mScanResults.add((ScanResult) parcelable);
            }
        }
        if (bundle.containsKey("key_scorednetworkcache")) {
            Iterator it = bundle.getParcelableArrayList("key_scorednetworkcache").iterator();
            while (it.hasNext()) {
                TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) it.next();
                this.mScoredNetworkCache.put(timestampedScoredNetwork.getScore().networkKey.wifiKey.bssid, timestampedScoredNetwork);
            }
        }
        if (bundle.containsKey("key_passpoint_unique_id")) {
            this.mPasspointUniqueId = bundle.getString("key_passpoint_unique_id");
        }
        if (bundle.containsKey("key_fqdn")) {
            this.mFqdn = bundle.getString("key_fqdn");
        }
        if (bundle.containsKey("key_provider_friendly_name")) {
            this.mProviderFriendlyName = bundle.getString("key_provider_friendly_name");
        }
        if (bundle.containsKey("key_subscription_expiration_time_in_millis")) {
            this.mSubscriptionExpirationTimeInMillis = bundle.getLong("key_subscription_expiration_time_in_millis");
        }
        if (bundle.containsKey("key_passpoint_configuration_version")) {
            this.mPasspointConfigurationVersion = bundle.getInt("key_passpoint_configuration_version");
        }
        if (bundle.containsKey("key_is_psk_sae_transition_mode")) {
            this.mIsPskSaeTransitionMode = bundle.getBoolean("key_is_psk_sae_transition_mode");
        }
        if (bundle.containsKey("key_is_owe_transition_mode")) {
            this.mIsOweTransitionMode = bundle.getBoolean("key_is_owe_transition_mode");
        }
        update(this.mConfig, this.mInfo, this.mNetworkInfo);
        updateKey();
        updateBestRssiInfo();
    }

    public AccessPoint(Context context, Collection<ScanResult> collection) {
        this.mLock = new Object();
        this.mScanResults = new ArraySet<>();
        this.mExtraScanResults = new ArraySet<>();
        this.mScoredNetworkCache = new HashMap();
        this.networkId = -1;
        this.pskType = 0;
        this.mEapType = 0;
        this.mRssi = Integer.MIN_VALUE;
        this.mSpeed = 0;
        this.mIsScoredNetworkMetered = false;
        this.mIsRoaming = false;
        this.mPasspointConfigurationVersion = 0;
        this.mOsuProvisioningComplete = false;
        this.mIsPskSaeTransitionMode = false;
        this.mIsOweTransitionMode = false;
        this.mContext = context;
        setScanResults(collection);
        updateKey();
    }

    public static String convertToQuotedString(String str) {
        return "\"" + str + "\"";
    }

    public static int getEapType(ScanResult scanResult) {
        if (scanResult.capabilities.contains("RSN-EAP")) {
            return 2;
        }
        return scanResult.capabilities.contains("WPA-EAP") ? 1 : 0;
    }

    public static String getKey(Context context, ScanResult scanResult) {
        return getKey(scanResult.SSID, scanResult.BSSID, getSecurity(context, scanResult));
    }

    public static String getKey(WifiConfiguration wifiConfiguration) {
        return wifiConfiguration.isPasspoint() ? getKey(wifiConfiguration.getKey()) : getKey(removeDoubleQuotes(wifiConfiguration.SSID), wifiConfiguration.BSSID, getSecurity(wifiConfiguration));
    }

    public static String getKey(OsuProvider osuProvider) {
        return "OSU:" + osuProvider.getFriendlyName() + ',' + osuProvider.getServerUri();
    }

    public static String getKey(String str) {
        return "PASSPOINT:" + str;
    }

    public static String getKey(String str, String str2, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("AP:");
        if (TextUtils.isEmpty(str)) {
            sb.append(str2);
        } else {
            sb.append(str);
        }
        sb.append(',');
        sb.append(i);
        return sb.toString();
    }

    public static int getPskType(ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WPA-PSK");
        boolean contains2 = scanResult.capabilities.contains("RSN-PSK");
        boolean contains3 = scanResult.capabilities.contains("RSN-SAE");
        if (contains2 && contains) {
            return 3;
        }
        if (contains2) {
            return 2;
        }
        if (contains) {
            return 1;
        }
        if (contains3) {
            return 0;
        }
        Log.w("SettingsLib.AccessPoint", "Received abnormal flag string: " + scanResult.capabilities);
        return 0;
    }

    public static int getSecurity(Context context, ScanResult scanResult) {
        boolean contains = scanResult.capabilities.contains("WEP");
        boolean contains2 = scanResult.capabilities.contains("SAE");
        boolean contains3 = scanResult.capabilities.contains("PSK");
        boolean contains4 = scanResult.capabilities.contains("EAP_SUITE_B_192");
        boolean contains5 = scanResult.capabilities.contains("EAP");
        boolean contains6 = scanResult.capabilities.contains("OWE");
        boolean contains7 = scanResult.capabilities.contains("OWE_TRANSITION");
        int i = 5;
        if (contains2 && contains3) {
            if (!((WifiManager) context.getSystemService("wifi")).isWpa3SaeSupported()) {
                i = 2;
            }
            return i;
        }
        int i2 = 4;
        if (contains7) {
            if (!((WifiManager) context.getSystemService("wifi")).isEnhancedOpenSupported()) {
                i2 = 0;
            }
            return i2;
        } else if (contains) {
            return 1;
        } else {
            if (contains2) {
                return 5;
            }
            if (contains3) {
                return 2;
            }
            if (contains4) {
                return 6;
            }
            if (contains5) {
                return 3;
            }
            return contains6 ? 4 : 0;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x006c, code lost:
        if (r0[r0] != null) goto L24;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static int getSecurity(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.allowedKeyManagement.get(8)) {
            return 5;
        }
        int i = 1;
        if (wifiConfiguration.allowedKeyManagement.get(1)) {
            return 2;
        }
        if (wifiConfiguration.allowedKeyManagement.get(10)) {
            return 6;
        }
        if (wifiConfiguration.allowedKeyManagement.get(2) || wifiConfiguration.allowedKeyManagement.get(3)) {
            return 3;
        }
        if (wifiConfiguration.allowedKeyManagement.get(9)) {
            return 4;
        }
        int i2 = wifiConfiguration.wepTxKeyIndex;
        if (i2 >= 0) {
            String[] strArr = wifiConfiguration.wepKeys;
            if (i2 < strArr.length) {
            }
        }
        i = 0;
        return i;
    }

    public static String getSpeedLabel(Context context, int i) {
        if (i != 5) {
            if (i != 10) {
                if (i != 20) {
                    if (i != 30) {
                        return null;
                    }
                    return context.getString(R$string.speed_label_very_fast);
                }
                return context.getString(R$string.speed_label_fast);
            }
            return context.getString(R$string.speed_label_okay);
        }
        return context.getString(R$string.speed_label_slow);
    }

    public static String getSpeedLabel(Context context, ScoredNetwork scoredNetwork, int i) {
        return getSpeedLabel(context, roundToClosestSpeedEnum(scoredNetwork.calculateBadge(i)));
    }

    public static boolean isOweTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("OWE_TRANSITION");
    }

    public static boolean isPskSaeTransitionMode(ScanResult scanResult) {
        return scanResult.capabilities.contains("PSK") && scanResult.capabilities.contains("SAE");
    }

    public static boolean isVerboseLoggingEnabled() {
        return WifiTracker.sVerboseLogging || Log.isLoggable("SettingsLib.AccessPoint", 2);
    }

    public /* synthetic */ void lambda$setScanResults$1() {
    }

    public /* synthetic */ void lambda$setScanResults$2() {
    }

    public /* synthetic */ void lambda$update$5() {
    }

    public static /* synthetic */ void lambda$updateScores$0(long j, Iterator it, TimestampedScoredNetwork timestampedScoredNetwork) {
        if (timestampedScoredNetwork.getUpdatedTimestampMillis() < j) {
            it.remove();
        }
    }

    public static String removeDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        int length = str.length();
        String str2 = str;
        if (length > 1) {
            str2 = str;
            if (str.charAt(0) == '\"') {
                int i = length - 1;
                str2 = str;
                if (str.charAt(i) == '\"') {
                    str2 = str.substring(1, i);
                }
            }
        }
        return str2;
    }

    public static int roundToClosestSpeedEnum(int i) {
        if (i < 5) {
            return 0;
        }
        if (i < 7) {
            return 5;
        }
        if (i < 15) {
            return 10;
        }
        return i < 25 ? 20 : 30;
    }

    public static String securityToString(int i, int i2) {
        return i == 1 ? "WEP" : i == 2 ? i2 == 1 ? "WPA" : i2 == 2 ? "WPA2" : i2 == 3 ? "WPA_WPA2" : "PSK" : i == 3 ? "EAP" : i == 5 ? "SAE" : i == 6 ? "SUITE_B" : i == 4 ? "OWE" : "NONE";
    }

    @Override // java.lang.Comparable
    public int compareTo(AccessPoint accessPoint) {
        if (!isActive() || accessPoint.isActive()) {
            if (isActive() || !accessPoint.isActive()) {
                if (!isReachable() || accessPoint.isReachable()) {
                    if (isReachable() || !accessPoint.isReachable()) {
                        if (!isSaved() || accessPoint.isSaved()) {
                            if (isSaved() || !accessPoint.isSaved()) {
                                if (getSpeed() != accessPoint.getSpeed()) {
                                    return accessPoint.getSpeed() - getSpeed();
                                }
                                WifiManager wifiManager = getWifiManager();
                                int calculateSignalLevel = wifiManager.calculateSignalLevel(accessPoint.mRssi) - wifiManager.calculateSignalLevel(this.mRssi);
                                if (calculateSignalLevel != 0) {
                                    return calculateSignalLevel;
                                }
                                int compareToIgnoreCase = getTitle().compareToIgnoreCase(accessPoint.getTitle());
                                return compareToIgnoreCase != 0 ? compareToIgnoreCase : getSsidStr().compareTo(accessPoint.getSsidStr());
                            }
                            return 1;
                        }
                        return -1;
                    }
                    return 1;
                }
                return -1;
            }
            return 1;
        }
        return -1;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (obj instanceof AccessPoint) {
            if (compareTo((AccessPoint) obj) == 0) {
                z = true;
            }
            return z;
        }
        return false;
    }

    public final int generateAverageSpeedForSsid() {
        if (this.mScoredNetworkCache.isEmpty()) {
            return 0;
        }
        if (Log.isLoggable("SettingsLib.AccessPoint", 3)) {
            Log.d("SettingsLib.AccessPoint", String.format("Generating fallbackspeed for %s using cache: %s", getSsidStr(), this.mScoredNetworkCache));
        }
        int i = 0;
        int i2 = 0;
        for (TimestampedScoredNetwork timestampedScoredNetwork : this.mScoredNetworkCache.values()) {
            int calculateBadge = timestampedScoredNetwork.getScore().calculateBadge(this.mRssi);
            if (calculateBadge != 0) {
                i++;
                i2 += calculateBadge;
            }
        }
        int i3 = i == 0 ? 0 : i2 / i;
        if (isVerboseLoggingEnabled()) {
            Log.i("SettingsLib.AccessPoint", String.format("%s generated fallback speed is: %d", getSsidStr(), Integer.valueOf(i3)));
        }
        return roundToClosestSpeedEnum(i3);
    }

    public String getBssid() {
        return this.bssid;
    }

    public WifiConfiguration getConfig() {
        return this.mConfig;
    }

    public NetworkInfo.DetailedState getDetailedState() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        if (networkInfo != null) {
            return networkInfo.getDetailedState();
        }
        Log.w("SettingsLib.AccessPoint", "NetworkInfo is null, cannot return detailed state");
        return null;
    }

    public WifiInfo getInfo() {
        return this.mInfo;
    }

    public String getKey() {
        return this.mKey;
    }

    public int getLevel() {
        return getWifiManager().calculateSignalLevel(this.mRssi);
    }

    public Set<ScanResult> getScanResults() {
        ArraySet arraySet = new ArraySet();
        synchronized (this.mLock) {
            arraySet.addAll((Collection) this.mScanResults);
            arraySet.addAll((Collection) this.mExtraScanResults);
        }
        return arraySet;
    }

    public Map<String, TimestampedScoredNetwork> getScoredNetworkCache() {
        return this.mScoredNetworkCache;
    }

    public int getSecurity() {
        return this.security;
    }

    public int getSpeed() {
        return this.mSpeed;
    }

    public String getSpeedLabel() {
        return getSpeedLabel(this.mSpeed);
    }

    public String getSpeedLabel(int i) {
        return getSpeedLabel(this.mContext, i);
    }

    public String getSsidStr() {
        return this.ssid;
    }

    public String getTitle() {
        return (!isPasspoint() || TextUtils.isEmpty(this.mConfig.providerFriendlyName)) ? (!isPasspointConfig() || TextUtils.isEmpty(this.mProviderFriendlyName)) ? (!isOsuProvider() || TextUtils.isEmpty(this.mOsuProvider.getFriendlyName())) ? !TextUtils.isEmpty(getSsidStr()) ? getSsidStr() : "" : this.mOsuProvider.getFriendlyName() : this.mProviderFriendlyName : this.mConfig.providerFriendlyName;
    }

    public final WifiManager getWifiManager() {
        if (this.mWifiManager == null) {
            this.mWifiManager = (WifiManager) this.mContext.getSystemService("wifi");
        }
        return this.mWifiManager;
    }

    public int hashCode() {
        WifiInfo wifiInfo = this.mInfo;
        int i = 0;
        if (wifiInfo != null) {
            i = 0 + (wifiInfo.hashCode() * 13);
        }
        return i + (this.mRssi * 19) + (this.networkId * 23) + (this.ssid.hashCode() * 29);
    }

    public boolean isActive() {
        NetworkInfo networkInfo = this.mNetworkInfo;
        return (networkInfo == null || (this.networkId == -1 && networkInfo.getState() == NetworkInfo.State.DISCONNECTED)) ? false : true;
    }

    public boolean isConnectable() {
        return getLevel() != -1 && getDetailedState() == null;
    }

    public boolean isEphemeral() {
        NetworkInfo networkInfo;
        WifiInfo wifiInfo = this.mInfo;
        return (wifiInfo == null || !wifiInfo.isEphemeral() || (networkInfo = this.mNetworkInfo) == null || networkInfo.getState() == NetworkInfo.State.DISCONNECTED) ? false : true;
    }

    public final boolean isInfoForThisAccessPoint(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo) {
        boolean z = true;
        if (wifiInfo.isOsuAp() || this.mOsuStatus != null) {
            return wifiInfo.isOsuAp() && this.mOsuStatus != null;
        } else if (wifiInfo.isPasspointAp() || isPasspoint()) {
            return wifiInfo.isPasspointAp() && isPasspoint() && TextUtils.equals(wifiInfo.getPasspointFqdn(), this.mConfig.FQDN) && TextUtils.equals(wifiInfo.getPasspointProviderFriendlyName(), this.mConfig.providerFriendlyName);
        } else {
            int i = this.networkId;
            if (i == -1) {
                return wifiConfiguration != null ? matches(wifiConfiguration, wifiInfo) : TextUtils.equals(removeDoubleQuotes(wifiInfo.getSSID()), this.ssid);
            }
            if (i != wifiInfo.getNetworkId()) {
                z = false;
            }
            return z;
        }
    }

    public boolean isMetered() {
        return this.mIsScoredNetworkMetered || WifiConfiguration.isMetered(this.mConfig, this.mInfo);
    }

    public boolean isOsuProvider() {
        return this.mOsuProvider != null;
    }

    public boolean isPasspoint() {
        WifiConfiguration wifiConfiguration = this.mConfig;
        return wifiConfiguration != null && wifiConfiguration.isPasspoint();
    }

    public boolean isPasspointConfig() {
        return this.mPasspointUniqueId != null && this.mConfig == null;
    }

    public boolean isReachable() {
        return this.mRssi != Integer.MIN_VALUE;
    }

    public final boolean isSameSsidOrBssid(ScanResult scanResult) {
        if (scanResult == null) {
            return false;
        }
        if (TextUtils.equals(this.ssid, scanResult.SSID)) {
            return true;
        }
        String str = scanResult.BSSID;
        return str != null && TextUtils.equals(this.bssid, str);
    }

    public final boolean isSameSsidOrBssid(WifiInfo wifiInfo) {
        if (wifiInfo == null) {
            return false;
        }
        if (TextUtils.equals(this.ssid, removeDoubleQuotes(wifiInfo.getSSID()))) {
            return true;
        }
        return wifiInfo.getBSSID() != null && TextUtils.equals(this.bssid, wifiInfo.getBSSID());
    }

    public boolean isSaved() {
        return this.mConfig != null;
    }

    @VisibleForTesting
    public void loadConfig(WifiConfiguration wifiConfiguration) {
        String str = wifiConfiguration.SSID;
        this.ssid = str == null ? "" : removeDoubleQuotes(str);
        this.bssid = wifiConfiguration.BSSID;
        this.security = getSecurity(wifiConfiguration);
        this.networkId = wifiConfiguration.networkId;
        this.mConfig = wifiConfiguration;
    }

    @VisibleForTesting
    public boolean matches(ScanResult scanResult) {
        boolean z = false;
        if (scanResult == null) {
            return false;
        }
        if (isPasspoint() || isOsuProvider()) {
            throw new IllegalStateException("Should not matches a Passpoint by ScanResult");
        }
        if (isSameSsidOrBssid(scanResult)) {
            if (!this.mIsPskSaeTransitionMode) {
                int i = this.security;
                if ((i == 5 || i == 2) && isPskSaeTransitionMode(scanResult)) {
                    return true;
                }
            } else if ((scanResult.capabilities.contains("SAE") && getWifiManager().isWpa3SaeSupported()) || scanResult.capabilities.contains("PSK")) {
                return true;
            }
            if (this.mIsOweTransitionMode) {
                int security = getSecurity(this.mContext, scanResult);
                if ((security == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0) {
                    return true;
                }
            } else {
                int i2 = this.security;
                if ((i2 == 4 || i2 == 0) && isOweTransitionMode(scanResult)) {
                    return true;
                }
            }
            if (this.security == getSecurity(this.mContext, scanResult)) {
                z = true;
            }
            return z;
        }
        return false;
    }

    public boolean matches(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration.isPasspoint()) {
            boolean z = false;
            if (isPasspoint()) {
                z = false;
                if (wifiConfiguration.getKey().equals(this.mConfig.getKey())) {
                    z = true;
                }
            }
            return z;
        }
        boolean z2 = false;
        if (this.ssid.equals(removeDoubleQuotes(wifiConfiguration.SSID))) {
            WifiConfiguration wifiConfiguration2 = this.mConfig;
            if (wifiConfiguration2 == null || wifiConfiguration2.shared == wifiConfiguration.shared) {
                int security = getSecurity(wifiConfiguration);
                if (this.mIsPskSaeTransitionMode && ((security == 5 && getWifiManager().isWpa3SaeSupported()) || security == 2)) {
                    return true;
                }
                if (this.mIsOweTransitionMode && ((security == 4 && getWifiManager().isEnhancedOpenSupported()) || security == 0)) {
                    return true;
                }
                z2 = false;
                if (this.security == getSecurity(wifiConfiguration)) {
                    z2 = true;
                }
            } else {
                z2 = false;
            }
        }
        return z2;
    }

    public final boolean matches(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo) {
        if (wifiConfiguration == null || wifiInfo == null) {
            return false;
        }
        if (wifiConfiguration.isPasspoint() || isSameSsidOrBssid(wifiInfo)) {
            return matches(wifiConfiguration);
        }
        return false;
    }

    @VisibleForTesting
    public void setRssi(int i) {
        this.mRssi = i;
    }

    public void setScanResults(Collection<ScanResult> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            Log.d("SettingsLib.AccessPoint", "Cannot set scan results to empty list");
            return;
        }
        if (this.mKey != null && !isPasspoint() && !isOsuProvider()) {
            for (ScanResult scanResult : collection) {
                if (!matches(scanResult)) {
                    Log.d("SettingsLib.AccessPoint", String.format("ScanResult %s\nkey of %s did not match current AP key %s", scanResult, getKey(this.mContext, scanResult), this.mKey));
                    return;
                }
            }
        }
        int level = getLevel();
        synchronized (this.mLock) {
            this.mScanResults.clear();
            this.mScanResults.addAll(collection);
        }
        updateBestRssiInfo();
        int level2 = getLevel();
        if (level2 > 0 && level2 != level) {
            updateSpeed();
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AccessPoint.$r8$lambda$1MyryaP_FPxIoOWXMURmr9gSCko(AccessPoint.this);
                }
            });
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AccessPoint.m1180$r8$lambda$l7HvoRkL0mn502GEmI049YA2Xk(AccessPoint.this);
            }
        });
    }

    public void setScanResultsPasspoint(Collection<ScanResult> collection, Collection<ScanResult> collection2) {
        synchronized (this.mLock) {
            this.mExtraScanResults.clear();
            if (!CollectionUtils.isEmpty(collection)) {
                this.mIsRoaming = false;
                if (!CollectionUtils.isEmpty(collection2)) {
                    this.mExtraScanResults.addAll(collection2);
                }
                setScanResults(collection);
            } else if (!CollectionUtils.isEmpty(collection2)) {
                this.mIsRoaming = true;
                setScanResults(collection2);
            }
        }
    }

    public void setTag(Object obj) {
        this.mTag = obj;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AccessPoint(");
        sb.append(this.ssid);
        if (this.bssid != null) {
            sb.append(":");
            sb.append(this.bssid);
        }
        if (isSaved()) {
            sb.append(',');
            sb.append("saved");
        }
        if (isActive()) {
            sb.append(',');
            sb.append("active");
        }
        if (isEphemeral()) {
            sb.append(',');
            sb.append("ephemeral");
        }
        if (isConnectable()) {
            sb.append(',');
            sb.append("connectable");
        }
        int i = this.security;
        if (i != 0 && i != 4) {
            sb.append(',');
            sb.append(securityToString(this.security, this.pskType));
        }
        sb.append(",level=");
        sb.append(getLevel());
        if (this.mSpeed != 0) {
            sb.append(",speed=");
            sb.append(this.mSpeed);
        }
        sb.append(",metered=");
        sb.append(isMetered());
        if (isVerboseLoggingEnabled()) {
            sb.append(",rssi=");
            sb.append(this.mRssi);
            synchronized (this.mLock) {
                sb.append(",scan cache size=");
                sb.append(this.mScanResults.size() + this.mExtraScanResults.size());
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public void update(WifiConfiguration wifiConfiguration) {
        this.mConfig = wifiConfiguration;
        if (wifiConfiguration != null && !isPasspoint()) {
            this.ssid = removeDoubleQuotes(this.mConfig.SSID);
        }
        this.networkId = wifiConfiguration != null ? wifiConfiguration.networkId : -1;
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AccessPoint.$r8$lambda$9sqJQrSDLPPBbVafxevqWDKYWqc(AccessPoint.this);
            }
        });
    }

    /* JADX WARN: Code restructure failed: missing block: B:56:0x0076, code lost:
        if (r0.getDetailedState() != r7.getDetailedState()) goto L18;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean update(WifiConfiguration wifiConfiguration, WifiInfo wifiInfo, NetworkInfo networkInfo) {
        getLevel();
        boolean z = false;
        boolean z2 = false;
        if (wifiInfo != null && isInfoForThisAccessPoint(wifiConfiguration, wifiInfo)) {
            if (this.mInfo == null) {
                z2 = true;
            }
            if (!isPasspoint() && this.mConfig != wifiConfiguration) {
                update(wifiConfiguration);
            }
            if (this.mRssi == wifiInfo.getRssi() || wifiInfo.getRssi() == -127) {
                NetworkInfo networkInfo2 = this.mNetworkInfo;
                z = z2;
                if (networkInfo2 != null) {
                    z = z2;
                    if (networkInfo != null) {
                        z = z2;
                    }
                }
                this.mInfo = wifiInfo;
                this.mNetworkInfo = networkInfo;
            } else {
                this.mRssi = wifiInfo.getRssi();
            }
            z = true;
            this.mInfo = wifiInfo;
            this.mNetworkInfo = networkInfo;
        } else if (this.mInfo != null) {
            this.mInfo = null;
            this.mNetworkInfo = null;
            z = true;
        }
        return z;
    }

    public boolean update(WifiNetworkScoreCache wifiNetworkScoreCache, boolean z, long j) {
        boolean z2 = false;
        boolean updateScores = z ? updateScores(wifiNetworkScoreCache, j) : false;
        if (updateMetered(wifiNetworkScoreCache) || updateScores) {
            z2 = true;
        }
        return z2;
    }

    public final void updateBestRssiInfo() {
        int i;
        int i2;
        if (isActive()) {
            return;
        }
        ScanResult scanResult = null;
        synchronized (this.mLock) {
            Iterator<ScanResult> it = this.mScanResults.iterator();
            i = Integer.MIN_VALUE;
            while (it.hasNext()) {
                ScanResult next = it.next();
                int i3 = next.level;
                if (i3 > i) {
                    scanResult = next;
                    i = i3;
                }
            }
        }
        if (i == Integer.MIN_VALUE || (i2 = this.mRssi) == Integer.MIN_VALUE) {
            this.mRssi = i;
        } else {
            this.mRssi = (i2 + i) / 2;
        }
        if (scanResult != null) {
            this.ssid = scanResult.SSID;
            this.bssid = scanResult.BSSID;
            int security = getSecurity(this.mContext, scanResult);
            this.security = security;
            if (security == 2 || security == 5) {
                this.pskType = getPskType(scanResult);
            }
            if (this.security == 3) {
                this.mEapType = getEapType(scanResult);
            }
            this.mIsPskSaeTransitionMode = isPskSaeTransitionMode(scanResult);
            this.mIsOweTransitionMode = isOweTransitionMode(scanResult);
        }
        if (isPasspoint()) {
            this.mConfig.SSID = convertToQuotedString(this.ssid);
        }
    }

    public final void updateKey() {
        if (isPasspoint()) {
            this.mKey = getKey(this.mConfig);
        } else if (isPasspointConfig()) {
            this.mKey = getKey(this.mPasspointUniqueId);
        } else if (isOsuProvider()) {
            this.mKey = getKey(this.mOsuProvider);
        } else {
            this.mKey = getKey(getSsidStr(), getBssid(), getSecurity());
        }
    }

    public final boolean updateMetered(WifiNetworkScoreCache wifiNetworkScoreCache) {
        WifiInfo wifiInfo;
        boolean z = this.mIsScoredNetworkMetered;
        boolean z2 = false;
        this.mIsScoredNetworkMetered = false;
        if (!isActive() || (wifiInfo = this.mInfo) == null) {
            synchronized (this.mLock) {
                Iterator<ScanResult> it = this.mScanResults.iterator();
                while (it.hasNext()) {
                    ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(it.next());
                    if (scoredNetwork != null) {
                        this.mIsScoredNetworkMetered = scoredNetwork.meteredHint | this.mIsScoredNetworkMetered;
                    }
                }
            }
        } else {
            ScoredNetwork scoredNetwork2 = wifiNetworkScoreCache.getScoredNetwork(NetworkKey.createFromWifiInfo(wifiInfo));
            if (scoredNetwork2 != null) {
                this.mIsScoredNetworkMetered = scoredNetwork2.meteredHint | this.mIsScoredNetworkMetered;
            }
        }
        if (z != this.mIsScoredNetworkMetered) {
            z2 = true;
        }
        return z2;
    }

    public final boolean updateScores(WifiNetworkScoreCache wifiNetworkScoreCache, long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        synchronized (this.mLock) {
            Iterator<ScanResult> it = this.mScanResults.iterator();
            while (it.hasNext()) {
                ScanResult next = it.next();
                ScoredNetwork scoredNetwork = wifiNetworkScoreCache.getScoredNetwork(next);
                if (scoredNetwork != null) {
                    TimestampedScoredNetwork timestampedScoredNetwork = this.mScoredNetworkCache.get(next.BSSID);
                    if (timestampedScoredNetwork == null) {
                        this.mScoredNetworkCache.put(next.BSSID, new TimestampedScoredNetwork(scoredNetwork, elapsedRealtime));
                    } else {
                        timestampedScoredNetwork.update(scoredNetwork, elapsedRealtime);
                    }
                }
            }
        }
        final Iterator<TimestampedScoredNetwork> it2 = this.mScoredNetworkCache.values().iterator();
        final long j2 = elapsedRealtime - j;
        it2.forEachRemaining(new Consumer() { // from class: com.android.settingslib.wifi.AccessPoint$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AccessPoint.$r8$lambda$eeEFeIMR6UCrf_qCtv1iNqRPOo8(j2, it2, (TimestampedScoredNetwork) obj);
            }
        });
        return updateSpeed();
    }

    public final boolean updateSpeed() {
        int i = this.mSpeed;
        int generateAverageSpeedForSsid = generateAverageSpeedForSsid();
        this.mSpeed = generateAverageSpeedForSsid;
        boolean z = i != generateAverageSpeedForSsid;
        if (isVerboseLoggingEnabled() && z) {
            Log.i("SettingsLib.AccessPoint", String.format("%s: Set speed to %d", this.ssid, Integer.valueOf(this.mSpeed)));
        }
        return z;
    }
}