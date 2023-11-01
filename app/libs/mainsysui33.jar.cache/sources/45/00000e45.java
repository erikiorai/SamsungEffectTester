package com.android.settingslib.devicestate;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/settingslib/devicestate/DeviceStateRotationLockSettingsManager.class */
public final class DeviceStateRotationLockSettingsManager {
    public static DeviceStateRotationLockSettingsManager sSingleton;
    public String[] mDeviceStateRotationLockDefaults;
    public SparseIntArray mDeviceStateRotationLockFallbackSettings;
    public SparseIntArray mDeviceStateRotationLockSettings;
    public String mLastSettingValue;
    public final SecureSettings mSecureSettings;
    public List<SettableDeviceState> mSettableDeviceStates;
    public final Handler mMainHandler = new Handler(Looper.getMainLooper());
    public final Set<DeviceStateRotationLockSettingsListener> mListeners = new HashSet();

    /* loaded from: mainsysui33.jar:com/android/settingslib/devicestate/DeviceStateRotationLockSettingsManager$DeviceStateRotationLockSettingsListener.class */
    public interface DeviceStateRotationLockSettingsListener {
        void onSettingsChanged();
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/devicestate/DeviceStateRotationLockSettingsManager$SettableDeviceState.class */
    public static class SettableDeviceState {
        public final int mDeviceState;
        public final boolean mIsSettable;

        public SettableDeviceState(int i, boolean z) {
            this.mDeviceState = i;
            this.mIsSettable = z;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj instanceof SettableDeviceState) {
                SettableDeviceState settableDeviceState = (SettableDeviceState) obj;
                if (this.mDeviceState != settableDeviceState.mDeviceState || this.mIsSettable != settableDeviceState.mIsSettable) {
                    z = false;
                }
                return z;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(Integer.valueOf(this.mDeviceState), Boolean.valueOf(this.mIsSettable));
        }
    }

    @VisibleForTesting
    public DeviceStateRotationLockSettingsManager(Context context, SecureSettings secureSettings) {
        this.mSecureSettings = secureSettings;
        this.mDeviceStateRotationLockDefaults = context.getResources().getStringArray(17236116);
        loadDefaults();
        initializeInMemoryMap();
        listenForSettingsChange();
    }

    public static DeviceStateRotationLockSettingsManager getInstance(Context context) {
        DeviceStateRotationLockSettingsManager deviceStateRotationLockSettingsManager;
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            try {
                if (sSingleton == null) {
                    Context applicationContext = context.getApplicationContext();
                    sSingleton = new DeviceStateRotationLockSettingsManager(applicationContext, new AndroidSecureSettings(applicationContext.getContentResolver()));
                }
                deviceStateRotationLockSettingsManager = sSingleton;
            } catch (Throwable th) {
                throw th;
            }
        }
        return deviceStateRotationLockSettingsManager;
    }

    @VisibleForTesting
    public static void resetInstance() {
        synchronized (DeviceStateRotationLockSettingsManager.class) {
            try {
                sSingleton = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void fallbackOnDefaults() {
        loadDefaults();
        persistSettings();
    }

    public final int getFallbackRotationLockSetting(int i) {
        int indexOfKey = this.mDeviceStateRotationLockFallbackSettings.indexOfKey(i);
        if (indexOfKey < 0) {
            Log.w("DSRotLockSettingsMngr", "Setting is ignored, but no fallback was specified.");
            return 0;
        }
        return this.mDeviceStateRotationLockSettings.get(this.mDeviceStateRotationLockFallbackSettings.valueAt(indexOfKey), 0);
    }

    public int getRotationLockSetting(int i) {
        int i2 = this.mDeviceStateRotationLockSettings.get(i, 0);
        int i3 = i2;
        if (i2 == 0) {
            i3 = getFallbackRotationLockSetting(i);
        }
        return i3;
    }

    public final void initializeInMemoryMap() {
        String stringForUser = this.mSecureSettings.getStringForUser("device_state_rotation_lock", -2);
        if (TextUtils.isEmpty(stringForUser)) {
            fallbackOnDefaults();
            return;
        }
        String[] split = stringForUser.split(":");
        if (split.length % 2 != 0) {
            Log.wtf("DSRotLockSettingsMngr", "Can't deserialize saved settings, falling back on defaults");
            fallbackOnDefaults();
            return;
        }
        this.mDeviceStateRotationLockSettings = new SparseIntArray(split.length / 2);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= split.length - 1) {
                return;
            }
            int i3 = i2 + 1;
            try {
                this.mDeviceStateRotationLockSettings.put(Integer.parseInt(split[i2]), Integer.parseInt(split[i3]));
                i = i3 + 1;
            } catch (NumberFormatException e) {
                Log.wtf("DSRotLockSettingsMngr", "Error deserializing one of the saved settings", e);
                fallbackOnDefaults();
                return;
            }
        }
    }

    public boolean isRotationLocked(int i) {
        boolean z = true;
        if (getRotationLockSetting(i) != 1) {
            z = false;
        }
        return z;
    }

    public final void listenForSettingsChange() {
        this.mSecureSettings.registerContentObserver("device_state_rotation_lock", false, new ContentObserver(this.mMainHandler) { // from class: com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                DeviceStateRotationLockSettingsManager.this.onPersistedSettingsChanged();
            }
        }, -2);
    }

    public final void loadDefaults() {
        String[] strArr;
        this.mSettableDeviceStates = new ArrayList(this.mDeviceStateRotationLockDefaults.length);
        this.mDeviceStateRotationLockSettings = new SparseIntArray(this.mDeviceStateRotationLockDefaults.length);
        this.mDeviceStateRotationLockFallbackSettings = new SparseIntArray(1);
        for (String str : this.mDeviceStateRotationLockDefaults) {
            String[] split = str.split(":");
            try {
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                if (parseInt2 == 0) {
                    if (split.length == 3) {
                        this.mDeviceStateRotationLockFallbackSettings.put(parseInt, Integer.parseInt(split[2]));
                    } else {
                        Log.w("DSRotLockSettingsMngr", "Rotation lock setting is IGNORED, but values have unexpected size of " + split.length);
                    }
                }
                this.mSettableDeviceStates.add(new SettableDeviceState(parseInt, parseInt2 != 0));
                this.mDeviceStateRotationLockSettings.put(parseInt, parseInt2);
            } catch (NumberFormatException e) {
                Log.wtf("DSRotLockSettingsMngr", "Error parsing settings entry. Entry was: " + str, e);
                return;
            }
        }
    }

    public final void notifyListeners() {
        for (DeviceStateRotationLockSettingsListener deviceStateRotationLockSettingsListener : this.mListeners) {
            deviceStateRotationLockSettingsListener.onSettingsChanged();
        }
    }

    @VisibleForTesting
    public void onPersistedSettingsChanged() {
        initializeInMemoryMap();
        notifyListeners();
    }

    public final void persistSettingIfChanged(String str) {
        if (TextUtils.equals(this.mLastSettingValue, str)) {
            return;
        }
        this.mLastSettingValue = str;
        this.mSecureSettings.putStringForUser("device_state_rotation_lock", str, -2);
    }

    public final void persistSettings() {
        if (this.mDeviceStateRotationLockSettings.size() == 0) {
            persistSettingIfChanged("");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.mDeviceStateRotationLockSettings.keyAt(0));
        sb.append(":");
        sb.append(this.mDeviceStateRotationLockSettings.valueAt(0));
        for (int i = 1; i < this.mDeviceStateRotationLockSettings.size(); i++) {
            sb.append(":");
            sb.append(this.mDeviceStateRotationLockSettings.keyAt(i));
            sb.append(":");
            sb.append(this.mDeviceStateRotationLockSettings.valueAt(i));
        }
        persistSettingIfChanged(sb.toString());
    }

    public void registerListener(DeviceStateRotationLockSettingsListener deviceStateRotationLockSettingsListener) {
        this.mListeners.add(deviceStateRotationLockSettingsListener);
    }

    @VisibleForTesting
    public void resetStateForTesting(Resources resources) {
        this.mDeviceStateRotationLockDefaults = resources.getStringArray(17236116);
        fallbackOnDefaults();
    }

    public void unregisterListener(DeviceStateRotationLockSettingsListener deviceStateRotationLockSettingsListener) {
        if (this.mListeners.remove(deviceStateRotationLockSettingsListener)) {
            return;
        }
        Log.w("DSRotLockSettingsMngr", "Attempting to unregister a listener hadn't been registered");
    }

    public void updateSetting(int i, boolean z) {
        int i2 = i;
        if (this.mDeviceStateRotationLockFallbackSettings.indexOfKey(i) >= 0) {
            i2 = this.mDeviceStateRotationLockFallbackSettings.get(i);
        }
        this.mDeviceStateRotationLockSettings.put(i2, z ? 1 : 2);
        persistSettings();
    }
}