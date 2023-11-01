package com.android.systemui.bluetooth;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BluetoothLogger.class */
public final class BluetoothLogger {
    public final LogBuffer logBuffer;

    public BluetoothLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void logAclConnectionStateChanged(String str, String str2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logAclConnectionStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "AclConnectionStateChanged. address=" + str1 + " state=" + str22;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logActiveDeviceChanged(String str, int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logActiveDeviceChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "ActiveDeviceChanged. address=" + str1 + " profileId=" + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logBondStateChange(String str, int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logBondStateChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "DeviceBondStateChanged. address=" + str1 + " state=" + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logDeviceAdded(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logDeviceAdded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "DeviceAdded. address=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logDeviceAttributesChanged() {
        LogBuffer logBuffer = this.logBuffer;
        logBuffer.commit(logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logDeviceAttributesChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "DeviceAttributesChanged.";
            }
        }, null));
    }

    public final void logDeviceConnectionStateChanged(String str, String str2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logDeviceConnectionStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                return "DeviceConnectionStateChanged. address=" + str1 + " state=" + str22;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logDeviceDeleted(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logDeviceDeleted$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "DeviceDeleted. address=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logProfileConnectionStateChanged(String str, String str2, int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logProfileConnectionStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                return "ProfileConnectionStateChanged. address=" + str1 + " state=" + str22 + " profileId=" + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logStateChange(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BluetoothLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.bluetooth.BluetoothLogger$logStateChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "BluetoothStateChanged. state=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }
}