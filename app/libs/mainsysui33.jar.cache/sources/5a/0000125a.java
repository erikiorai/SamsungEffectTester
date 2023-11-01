package com.android.systemui.bluetooth;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BluetoothLogger_Factory.class */
public final class BluetoothLogger_Factory implements Factory<BluetoothLogger> {
    public final Provider<LogBuffer> logBufferProvider;

    public BluetoothLogger_Factory(Provider<LogBuffer> provider) {
        this.logBufferProvider = provider;
    }

    public static BluetoothLogger_Factory create(Provider<LogBuffer> provider) {
        return new BluetoothLogger_Factory(provider);
    }

    public static BluetoothLogger newInstance(LogBuffer logBuffer) {
        return new BluetoothLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BluetoothLogger m1628get() {
        return newInstance((LogBuffer) this.logBufferProvider.get());
    }
}