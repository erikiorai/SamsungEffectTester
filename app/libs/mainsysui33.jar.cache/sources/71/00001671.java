package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSuppressor_Factory.class */
public final class DozeSuppressor_Factory implements Factory<DozeSuppressor> {
    public final Provider<BiometricUnlockController> biometricUnlockControllerLazyProvider;
    public final Provider<AmbientDisplayConfiguration> configProvider;
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeLog> dozeLogProvider;

    public DozeSuppressor_Factory(Provider<DozeHost> provider, Provider<AmbientDisplayConfiguration> provider2, Provider<DozeLog> provider3, Provider<BiometricUnlockController> provider4) {
        this.dozeHostProvider = provider;
        this.configProvider = provider2;
        this.dozeLogProvider = provider3;
        this.biometricUnlockControllerLazyProvider = provider4;
    }

    public static DozeSuppressor_Factory create(Provider<DozeHost> provider, Provider<AmbientDisplayConfiguration> provider2, Provider<DozeLog> provider3, Provider<BiometricUnlockController> provider4) {
        return new DozeSuppressor_Factory(provider, provider2, provider3, provider4);
    }

    public static DozeSuppressor newInstance(DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeLog dozeLog, Lazy<BiometricUnlockController> lazy) {
        return new DozeSuppressor(dozeHost, ambientDisplayConfiguration, dozeLog, lazy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeSuppressor m2510get() {
        return newInstance((DozeHost) this.dozeHostProvider.get(), (AmbientDisplayConfiguration) this.configProvider.get(), (DozeLog) this.dozeLogProvider.get(), DoubleCheck.lazy(this.biometricUnlockControllerLazyProvider));
    }
}