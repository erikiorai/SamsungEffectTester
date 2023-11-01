package com.android.systemui.dagger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;
import com.android.settingslib.bluetooth.LocalBluetoothManager;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SettingsLibraryModule.class */
public class SettingsLibraryModule {
    @SuppressLint({"MissingPermission"})
    public static LocalBluetoothManager provideLocalBluetoothController(Context context, Handler handler) {
        return LocalBluetoothManager.create(context, handler, UserHandle.ALL);
    }
}