package com.android.systemui.keyboard;

import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyboard/KeyboardUI_Factory.class */
public final class KeyboardUI_Factory implements Factory<KeyboardUI> {
    public final Provider<LocalBluetoothManager> bluetoothManagerProvider;
    public final Provider<Context> contextProvider;

    public KeyboardUI_Factory(Provider<Context> provider, Provider<LocalBluetoothManager> provider2) {
        this.contextProvider = provider;
        this.bluetoothManagerProvider = provider2;
    }

    public static KeyboardUI_Factory create(Provider<Context> provider, Provider<LocalBluetoothManager> provider2) {
        return new KeyboardUI_Factory(provider, provider2);
    }

    public static KeyboardUI newInstance(Context context, Provider<LocalBluetoothManager> provider) {
        return new KeyboardUI(context, provider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyboardUI m2798get() {
        return newInstance((Context) this.contextProvider.get(), this.bluetoothManagerProvider);
    }
}