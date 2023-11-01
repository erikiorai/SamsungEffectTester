package com.android.systemui.dagger;

import android.bluetooth.BluetoothManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideBluetoothManagerFactory.class */
public final class FrameworkServicesModule_ProvideBluetoothManagerFactory implements Factory<BluetoothManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideBluetoothManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideBluetoothManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideBluetoothManagerFactory(provider);
    }

    public static BluetoothManager provideBluetoothManager(Context context) {
        return (BluetoothManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideBluetoothManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BluetoothManager m2273get() {
        return provideBluetoothManager((Context) this.contextProvider.get());
    }
}