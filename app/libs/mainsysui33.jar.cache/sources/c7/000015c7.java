package com.android.systemui.dagger;

import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory.class */
public final class ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory implements Factory<DeviceProvisionedController> {
    public final Provider<DeviceProvisionedControllerImpl> deviceProvisionedControllerProvider;

    public ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(Provider<DeviceProvisionedControllerImpl> provider) {
        this.deviceProvisionedControllerProvider = provider;
    }

    public static DeviceProvisionedController bindDeviceProvisionedController(DeviceProvisionedControllerImpl deviceProvisionedControllerImpl) {
        return (DeviceProvisionedController) Preconditions.checkNotNullFromProvides(ReferenceSystemUIModule.bindDeviceProvisionedController(deviceProvisionedControllerImpl));
    }

    public static ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory create(Provider<DeviceProvisionedControllerImpl> provider) {
        return new ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory(provider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DeviceProvisionedController m2374get() {
        return bindDeviceProvisionedController((DeviceProvisionedControllerImpl) this.deviceProvisionedControllerProvider.get());
    }
}