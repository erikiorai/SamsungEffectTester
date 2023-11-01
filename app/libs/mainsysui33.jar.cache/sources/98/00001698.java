package com.android.systemui.doze.dagger;

import android.content.Context;
import android.hardware.Sensor;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/dagger/DozeModule_ProvidesBrightnessSensorsFactory.class */
public final class DozeModule_ProvidesBrightnessSensorsFactory implements Factory<Optional<Sensor>[]> {
    public final Provider<Context> contextProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<AsyncSensorManager> sensorManagerProvider;

    public DozeModule_ProvidesBrightnessSensorsFactory(Provider<AsyncSensorManager> provider, Provider<Context> provider2, Provider<DozeParameters> provider3) {
        this.sensorManagerProvider = provider;
        this.contextProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static DozeModule_ProvidesBrightnessSensorsFactory create(Provider<AsyncSensorManager> provider, Provider<Context> provider2, Provider<DozeParameters> provider3) {
        return new DozeModule_ProvidesBrightnessSensorsFactory(provider, provider2, provider3);
    }

    public static Optional<Sensor>[] providesBrightnessSensors(AsyncSensorManager asyncSensorManager, Context context, DozeParameters dozeParameters) {
        return (Optional[]) Preconditions.checkNotNullFromProvides(DozeModule.providesBrightnessSensors(asyncSensorManager, context, dozeParameters));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<Sensor>[] get() {
        return providesBrightnessSensors((AsyncSensorManager) this.sensorManagerProvider.get(), (Context) this.contextProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}