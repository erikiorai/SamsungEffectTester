package com.android.systemui.dagger;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideBluetoothAdapterFactory.class */
public final class FrameworkServicesModule_ProvideBluetoothAdapterFactory implements Factory<BluetoothAdapter> {
    public final Provider<BluetoothManager> bluetoothManagerProvider;

    public FrameworkServicesModule_ProvideBluetoothAdapterFactory(Provider<BluetoothManager> provider) {
        this.bluetoothManagerProvider = provider;
    }

    public static FrameworkServicesModule_ProvideBluetoothAdapterFactory create(Provider<BluetoothManager> provider) {
        return new FrameworkServicesModule_ProvideBluetoothAdapterFactory(provider);
    }

    public static BluetoothAdapter provideBluetoothAdapter(BluetoothManager bluetoothManager) {
        return FrameworkServicesModule.provideBluetoothAdapter(bluetoothManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BluetoothAdapter m2272get() {
        return provideBluetoothAdapter((BluetoothManager) this.bluetoothManagerProvider.get());
    }
}