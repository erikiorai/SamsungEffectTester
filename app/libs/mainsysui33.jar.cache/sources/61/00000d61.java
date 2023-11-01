package com.android.settingslib;

/* loaded from: mainsysui33.jar:com/android/settingslib/AccessibilityContentDescriptions.class */
public class AccessibilityContentDescriptions {
    public static final int[] DATA_CONNECTION_STRENGTH;
    public static final int[] ETHERNET_CONNECTION_VALUES;
    public static final int NO_CALLING;
    public static final int[] PHONE_SIGNAL_STRENGTH;
    public static final int PHONE_SIGNAL_STRENGTH_NONE;
    public static final int[] WIFI_CONNECTION_STRENGTH;
    public static final int WIFI_NO_CONNECTION;

    static {
        int i = R$string.accessibility_no_phone;
        PHONE_SIGNAL_STRENGTH_NONE = i;
        PHONE_SIGNAL_STRENGTH = new int[]{i, R$string.accessibility_phone_one_bar, R$string.accessibility_phone_two_bars, R$string.accessibility_phone_three_bars, R$string.accessibility_phone_signal_full};
        DATA_CONNECTION_STRENGTH = new int[]{R$string.accessibility_no_data, R$string.accessibility_data_one_bar, R$string.accessibility_data_two_bars, R$string.accessibility_data_three_bars, R$string.accessibility_data_signal_full};
        int i2 = R$string.accessibility_no_wifi;
        WIFI_CONNECTION_STRENGTH = new int[]{i2, R$string.accessibility_wifi_one_bar, R$string.accessibility_wifi_two_bars, R$string.accessibility_wifi_three_bars, R$string.accessibility_wifi_signal_full};
        WIFI_NO_CONNECTION = i2;
        NO_CALLING = R$string.accessibility_no_calling;
        ETHERNET_CONNECTION_VALUES = new int[]{R$string.accessibility_ethernet_disconnected, R$string.accessibility_ethernet_connected};
    }
}