package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SettingsLibraryModule_ProvideLocalBluetoothControllerFactory.class */
public final class SettingsLibraryModule_ProvideLocalBluetoothControllerFactory implements Factory<LocalBluetoothManager> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<Context> contextProvider;

    public SettingsLibraryModule_ProvideLocalBluetoothControllerFactory(Provider<Context> provider, Provider<Handler> provider2) {
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
    }

    public static SettingsLibraryModule_ProvideLocalBluetoothControllerFactory create(Provider<Context> provider, Provider<Handler> provider2) {
        return new SettingsLibraryModule_ProvideLocalBluetoothControllerFactory(provider, provider2);
    }

    public static LocalBluetoothManager provideLocalBluetoothController(Context context, Handler handler) {
        return SettingsLibraryModule.provideLocalBluetoothController(context, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LocalBluetoothManager m2385get() {
        return provideLocalBluetoothController((Context) this.contextProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}