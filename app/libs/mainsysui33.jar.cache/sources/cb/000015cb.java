package com.android.systemui.dagger;

import android.hardware.SensorPrivacyManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory.class */
public final class ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory implements Factory<IndividualSensorPrivacyController> {
    public final Provider<SensorPrivacyManager> sensorPrivacyManagerProvider;

    public ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory(Provider<SensorPrivacyManager> provider) {
        this.sensorPrivacyManagerProvider = provider;
    }

    public static ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory create(Provider<SensorPrivacyManager> provider) {
        return new ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory(provider);
    }

    public static IndividualSensorPrivacyController provideIndividualSensorPrivacyController(SensorPrivacyManager sensorPrivacyManager) {
        return (IndividualSensorPrivacyController) Preconditions.checkNotNullFromProvides(ReferenceSystemUIModule.provideIndividualSensorPrivacyController(sensorPrivacyManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IndividualSensorPrivacyController m2379get() {
        return provideIndividualSensorPrivacyController((SensorPrivacyManager) this.sensorPrivacyManagerProvider.get());
    }
}