package com.android.settingslib.mobile;

import android.content.Context;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyDisplayInfo;
import com.android.settingslib.R$bool;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import java.util.HashMap;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileMappings.class */
public class MobileMappings {

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileMappings$Config.class */
    public static class Config {
        public boolean hspaDataDistinguishable;
        public boolean showAtLeast3G = false;
        public boolean show4gFor3g = false;
        public boolean alwaysShowCdmaRssi = false;
        public boolean show4gForLte = false;
        public boolean show4glteForLte = false;
        public boolean hideLtePlus = false;
        public boolean alwaysShowDataRatIcon = false;

        public static Config readConfig(Context context) {
            Config config = new Config();
            Resources resources = context.getResources();
            config.showAtLeast3G = resources.getBoolean(R$bool.config_showMin3G);
            config.alwaysShowCdmaRssi = resources.getBoolean(17891367);
            config.hspaDataDistinguishable = resources.getBoolean(R$bool.config_hspa_data_distinguishable);
            SubscriptionManager.from(context);
            PersistableBundle configForSubId = ((CarrierConfigManager) context.getSystemService("carrier_config")).getConfigForSubId(SubscriptionManager.getDefaultDataSubscriptionId());
            if (configForSubId != null) {
                config.alwaysShowDataRatIcon = configForSubId.getBoolean("always_show_data_rat_icon_bool");
                config.show4gForLte = configForSubId.getBoolean("show_4g_for_lte_data_icon_bool");
                config.show4glteForLte = configForSubId.getBoolean("show_4glte_for_lte_data_icon_bool");
                config.show4gFor3g = configForSubId.getBoolean("show_4g_for_3g_data_icon_bool");
                config.hideLtePlus = configForSubId.getBoolean("hide_lte_plus_data_icon_bool");
            }
            return config;
        }
    }

    public static SignalIcon$MobileIconGroup getDefaultIcons(Config config) {
        return !config.showAtLeast3G ? TelephonyIcons.G : TelephonyIcons.THREE_G;
    }

    public static String getIconKey(TelephonyDisplayInfo telephonyDisplayInfo) {
        return telephonyDisplayInfo.getOverrideNetworkType() == 0 ? toIconKey(telephonyDisplayInfo.getNetworkType()) : toDisplayIconKey(telephonyDisplayInfo.getOverrideNetworkType());
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0162  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x019c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Map<String, SignalIcon$MobileIconGroup> mapIconSets(Config config) {
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup;
        HashMap hashMap = new HashMap();
        String iconKey = toIconKey(5);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup2 = TelephonyIcons.THREE_G;
        hashMap.put(iconKey, signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(6), signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(12), signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(14), signalIcon$MobileIconGroup2);
        if (config.show4gFor3g) {
            hashMap.put(toIconKey(3), TelephonyIcons.FOUR_G);
        } else {
            hashMap.put(toIconKey(3), signalIcon$MobileIconGroup2);
        }
        hashMap.put(toIconKey(17), signalIcon$MobileIconGroup2);
        if (config.showAtLeast3G) {
            hashMap.put(toIconKey(0), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(2), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(1), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(4), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(7), signalIcon$MobileIconGroup2);
        } else {
            hashMap.put(toIconKey(0), TelephonyIcons.UNKNOWN);
            hashMap.put(toIconKey(2), TelephonyIcons.E);
            hashMap.put(toIconKey(1), TelephonyIcons.G);
            String iconKey2 = toIconKey(4);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup3 = TelephonyIcons.ONE_X;
            hashMap.put(iconKey2, signalIcon$MobileIconGroup3);
            hashMap.put(toIconKey(7), signalIcon$MobileIconGroup3);
        }
        if (config.show4gFor3g) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.FOUR_G;
        } else if (config.hspaDataDistinguishable) {
            signalIcon$MobileIconGroup2 = TelephonyIcons.H;
            signalIcon$MobileIconGroup = TelephonyIcons.H_PLUS;
            hashMap.put(toIconKey(8), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(9), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(10), signalIcon$MobileIconGroup2);
            hashMap.put(toIconKey(15), signalIcon$MobileIconGroup);
            if (!config.show4gForLte) {
                String iconKey3 = toIconKey(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup4 = TelephonyIcons.FOUR_G;
                hashMap.put(iconKey3, signalIcon$MobileIconGroup4);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup4);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_PLUS);
                }
            } else if (config.show4glteForLte) {
                String iconKey4 = toIconKey(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup5 = TelephonyIcons.FOUR_G_LTE;
                hashMap.put(iconKey4, signalIcon$MobileIconGroup5);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup5);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.FOUR_G_LTE_PLUS);
                }
            } else {
                String iconKey5 = toIconKey(13);
                SignalIcon$MobileIconGroup signalIcon$MobileIconGroup6 = TelephonyIcons.LTE;
                hashMap.put(iconKey5, signalIcon$MobileIconGroup6);
                if (config.hideLtePlus) {
                    hashMap.put(toDisplayIconKey(1), signalIcon$MobileIconGroup6);
                } else {
                    hashMap.put(toDisplayIconKey(1), TelephonyIcons.LTE_PLUS);
                }
            }
            hashMap.put(toIconKey(18), TelephonyIcons.WFC);
            hashMap.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
            String displayIconKey = toDisplayIconKey(3);
            SignalIcon$MobileIconGroup signalIcon$MobileIconGroup7 = TelephonyIcons.NR_5G;
            hashMap.put(displayIconKey, signalIcon$MobileIconGroup7);
            hashMap.put(toDisplayIconKey(5), TelephonyIcons.NR_5G_PLUS);
            hashMap.put(toIconKey(20), signalIcon$MobileIconGroup7);
            return hashMap;
        }
        signalIcon$MobileIconGroup = signalIcon$MobileIconGroup2;
        hashMap.put(toIconKey(8), signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(9), signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(10), signalIcon$MobileIconGroup2);
        hashMap.put(toIconKey(15), signalIcon$MobileIconGroup);
        if (!config.show4gForLte) {
        }
        hashMap.put(toIconKey(18), TelephonyIcons.WFC);
        hashMap.put(toDisplayIconKey(2), TelephonyIcons.LTE_CA_5G_E);
        String displayIconKey2 = toDisplayIconKey(3);
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup72 = TelephonyIcons.NR_5G;
        hashMap.put(displayIconKey2, signalIcon$MobileIconGroup72);
        hashMap.put(toDisplayIconKey(5), TelephonyIcons.NR_5G_PLUS);
        hashMap.put(toIconKey(20), signalIcon$MobileIconGroup72);
        return hashMap;
    }

    public static String toDisplayIconKey(int i) {
        if (i == 1) {
            return toIconKey(13) + "_CA";
        } else if (i == 2) {
            return toIconKey(13) + "_CA_Plus";
        } else if (i != 3) {
            if (i != 5) {
                return "unsupported";
            }
            return toIconKey(20) + "_Plus";
        } else {
            return toIconKey(20);
        }
    }

    public static String toIconKey(int i) {
        return Integer.toString(i);
    }
}