package com.android.systemui.doze;

import android.content.Context;
import android.hardware.Sensor;
import android.os.Handler;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeScreenBrightness_Factory.class */
public final class DozeScreenBrightness_Factory implements Factory<DozeScreenBrightness> {
    public final Provider<AlwaysOnDisplayPolicy> alwaysOnDisplayPolicyProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DevicePostureController> devicePostureControllerProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<DozeHost> hostProvider;
    public final Provider<Optional<Sensor>[]> lightSensorOptionalProvider;
    public final Provider<AsyncSensorManager> sensorManagerProvider;
    public final Provider<DozeMachine.Service> serviceProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public DozeScreenBrightness_Factory(Provider<Context> provider, Provider<DozeMachine.Service> provider2, Provider<AsyncSensorManager> provider3, Provider<Optional<Sensor>[]> provider4, Provider<DozeHost> provider5, Provider<Handler> provider6, Provider<AlwaysOnDisplayPolicy> provider7, Provider<WakefulnessLifecycle> provider8, Provider<DozeParameters> provider9, Provider<DevicePostureController> provider10, Provider<DozeLog> provider11) {
        this.contextProvider = provider;
        this.serviceProvider = provider2;
        this.sensorManagerProvider = provider3;
        this.lightSensorOptionalProvider = provider4;
        this.hostProvider = provider5;
        this.handlerProvider = provider6;
        this.alwaysOnDisplayPolicyProvider = provider7;
        this.wakefulnessLifecycleProvider = provider8;
        this.dozeParametersProvider = provider9;
        this.devicePostureControllerProvider = provider10;
        this.dozeLogProvider = provider11;
    }

    public static DozeScreenBrightness_Factory create(Provider<Context> provider, Provider<DozeMachine.Service> provider2, Provider<AsyncSensorManager> provider3, Provider<Optional<Sensor>[]> provider4, Provider<DozeHost> provider5, Provider<Handler> provider6, Provider<AlwaysOnDisplayPolicy> provider7, Provider<WakefulnessLifecycle> provider8, Provider<DozeParameters> provider9, Provider<DevicePostureController> provider10, Provider<DozeLog> provider11) {
        return new DozeScreenBrightness_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static DozeScreenBrightness newInstance(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog) {
        return new DozeScreenBrightness(context, service, asyncSensorManager, optionalArr, dozeHost, handler, alwaysOnDisplayPolicy, wakefulnessLifecycle, dozeParameters, devicePostureController, dozeLog);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeScreenBrightness m2476get() {
        return newInstance((Context) this.contextProvider.get(), (DozeMachine.Service) this.serviceProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (Optional[]) this.lightSensorOptionalProvider.get(), (DozeHost) this.hostProvider.get(), (Handler) this.handlerProvider.get(), (AlwaysOnDisplayPolicy) this.alwaysOnDisplayPolicyProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (DozeLog) this.dozeLogProvider.get());
    }
}