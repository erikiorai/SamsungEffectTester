package com.android.settingslib.wifi;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import com.android.settingslib.R$drawable;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiUtils.class */
public class WifiUtils {
    public static final String ACTION_WIFI_DIALOG = "com.android.settings.WIFI_DIALOG";
    public static final String EXTRA_CHOSEN_WIFI_ENTRY_KEY = "key_chosen_wifientry_key";
    public static final String EXTRA_CONNECT_FOR_CALLER = "connect_for_caller";
    public static final int[] WIFI_PIE = {17302903, 17302904, 17302905, 17302906, 17302907};
    public static final int[] NO_INTERNET_WIFI_PIE = {R$drawable.ic_no_internet_wifi_signal_0, R$drawable.ic_no_internet_wifi_signal_1, R$drawable.ic_no_internet_wifi_signal_2, R$drawable.ic_no_internet_wifi_signal_3, R$drawable.ic_no_internet_wifi_signal_4};

    /* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiUtils$InternetIconInjector.class */
    public static class InternetIconInjector {
        public final Context mContext;

        public InternetIconInjector(Context context) {
            this.mContext = context;
        }

        public Drawable getIcon(boolean z, int i) {
            return this.mContext.getDrawable(WifiUtils.getInternetIconResource(i, z));
        }
    }

    public static int getInternetIconResource(int i, boolean z) {
        int i2;
        if (i < 0) {
            Log.e("WifiUtils", "Wi-Fi level is out of range! level:" + i);
            i2 = 0;
        } else {
            int[] iArr = WIFI_PIE;
            i2 = i;
            if (i >= iArr.length) {
                Log.e("WifiUtils", "Wi-Fi level is out of range! level:" + i);
                i2 = iArr.length - 1;
            }
        }
        return z ? NO_INTERNET_WIFI_PIE[i2] : WIFI_PIE[i2];
    }

    public static int getSpecificApSpeed(ScanResult scanResult, Map<String, TimestampedScoredNetwork> map) {
        TimestampedScoredNetwork timestampedScoredNetwork = map.get(scanResult.BSSID);
        if (timestampedScoredNetwork == null) {
            return 0;
        }
        return timestampedScoredNetwork.getScore().calculateBadge(scanResult.level);
    }

    public static String getVisibilityStatus(AccessPoint accessPoint) {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        WifiInfo info = accessPoint.getInfo();
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        int i7 = 0;
        if (!accessPoint.isActive() || info == null) {
            str = null;
        } else {
            str = info.getBSSID();
            if (str != null) {
                sb.append(" ");
                sb.append(str);
            }
            sb.append(" standard = ");
            sb.append(info.getWifiStandard());
            sb.append(" rssi=");
            sb.append(info.getRssi());
            sb.append(" ");
            sb.append(" score=");
            sb.append(info.getScore());
            if (accessPoint.getSpeed() != 0) {
                sb.append(" speed=");
                sb.append(accessPoint.getSpeedLabel());
            }
            sb.append(String.format(" tx=%.1f,", Double.valueOf(info.getSuccessfulTxPacketsPerSecond())));
            sb.append(String.format("%.1f,", Double.valueOf(info.getRetriedTxPacketsPerSecond())));
            sb.append(String.format("%.1f ", Double.valueOf(info.getLostTxPacketsPerSecond())));
            sb.append(String.format("rx=%.1f", Double.valueOf(info.getSuccessfulRxPacketsPerSecond())));
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        int i8 = 0;
        int i9 = -127;
        int i10 = -127;
        int i11 = -127;
        int i12 = 0;
        for (ScanResult scanResult : accessPoint.getScanResults()) {
            if (scanResult != null) {
                int i13 = scanResult.frequency;
                if (i13 >= 4900 && i13 <= 5900) {
                    int i14 = i12 + 1;
                    int i15 = scanResult.level;
                    int i16 = i10;
                    if (i15 > i10) {
                        i16 = i15;
                    }
                    i = i7;
                    i2 = i14;
                    i3 = i8;
                    i4 = i9;
                    i5 = i16;
                    i6 = i11;
                    if (i14 <= 4) {
                        sb3.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                        i = i7;
                        i2 = i14;
                        i3 = i8;
                        i4 = i9;
                        i5 = i16;
                        i6 = i11;
                    }
                } else if (i13 < 2400 || i13 > 2500) {
                    i = i7;
                    i2 = i12;
                    i3 = i8;
                    i4 = i9;
                    i5 = i10;
                    i6 = i11;
                    if (i13 >= 58320) {
                        i = i7;
                        i2 = i12;
                        i3 = i8;
                        i4 = i9;
                        i5 = i10;
                        i6 = i11;
                        if (i13 <= 70200) {
                            int i17 = i8 + 1;
                            int i18 = scanResult.level;
                            int i19 = i11;
                            if (i18 > i11) {
                                i19 = i18;
                            }
                            i = i7;
                            i2 = i12;
                            i3 = i17;
                            i4 = i9;
                            i5 = i10;
                            i6 = i19;
                            if (i17 <= 4) {
                                sb4.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                                i6 = i19;
                                i5 = i10;
                                i4 = i9;
                                i3 = i17;
                                i2 = i12;
                                i = i7;
                            }
                        }
                    }
                } else {
                    int i20 = i7 + 1;
                    int i21 = scanResult.level;
                    int i22 = i9;
                    if (i21 > i9) {
                        i22 = i21;
                    }
                    i = i20;
                    i2 = i12;
                    i3 = i8;
                    i4 = i22;
                    i5 = i10;
                    i6 = i11;
                    if (i20 <= 4) {
                        sb2.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                        i = i20;
                        i2 = i12;
                        i3 = i8;
                        i4 = i22;
                        i5 = i10;
                        i6 = i11;
                    }
                }
                i7 = i;
                i12 = i2;
                i8 = i3;
                i9 = i4;
                i10 = i5;
                i11 = i6;
            }
        }
        sb.append(" [");
        if (i7 > 0) {
            sb.append("(");
            sb.append(i7);
            sb.append(")");
            if (i7 > 4) {
                sb.append("max=");
                sb.append(i9);
                sb.append(",");
            }
            sb.append(sb2.toString());
        }
        sb.append(";");
        if (i12 > 0) {
            sb.append("(");
            sb.append(i12);
            sb.append(")");
            if (i12 > 4) {
                sb.append("max=");
                sb.append(i10);
                sb.append(",");
            }
            sb.append(sb3.toString());
        }
        sb.append(";");
        if (i8 > 0) {
            sb.append("(");
            sb.append(i8);
            sb.append(")");
            if (i8 > 4) {
                sb.append("max=");
                sb.append(i11);
                sb.append(",");
            }
            sb.append(sb4.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public static Intent getWifiDetailsSettingsIntent(String str) {
        Intent intent = new Intent("android.settings.WIFI_DETAILS_SETTINGS");
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_CHOSEN_WIFI_ENTRY_KEY, str);
        intent.putExtra(":settings:show_fragment_args", bundle);
        return intent;
    }

    public static Intent getWifiDialogIntent(String str, boolean z) {
        Intent intent = new Intent(ACTION_WIFI_DIALOG);
        intent.putExtra(EXTRA_CHOSEN_WIFI_ENTRY_KEY, str);
        intent.putExtra(EXTRA_CONNECT_FOR_CALLER, z);
        return intent;
    }

    public static String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
        StringBuilder sb = new StringBuilder();
        sb.append(" \n{");
        sb.append(scanResult.BSSID);
        if (scanResult.BSSID.equals(str)) {
            sb.append("*");
        }
        sb.append("=");
        sb.append(scanResult.frequency);
        sb.append(",");
        sb.append(scanResult.level);
        int specificApSpeed = getSpecificApSpeed(scanResult, accessPoint.getScoredNetworkCache());
        if (specificApSpeed != 0) {
            sb.append(",");
            sb.append(accessPoint.getSpeedLabel(specificApSpeed));
        }
        sb.append(",");
        sb.append(((int) (j - (scanResult.timestamp / 1000))) / 1000);
        sb.append("s");
        sb.append("}");
        return sb.toString();
    }
}