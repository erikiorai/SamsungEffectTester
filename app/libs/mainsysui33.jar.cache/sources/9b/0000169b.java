package com.android.systemui.doze.dagger;

import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.DozeParameters;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/dagger/DozeModule_ProvidesWrappedServiceFactory.class */
public final class DozeModule_ProvidesWrappedServiceFactory implements Factory<DozeMachine.Service> {
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeMachine.Service> dozeMachineServiceProvider;
    public final Provider<DozeParameters> dozeParametersProvider;

    public DozeModule_ProvidesWrappedServiceFactory(Provider<DozeMachine.Service> provider, Provider<DozeHost> provider2, Provider<DozeParameters> provider3) {
        this.dozeMachineServiceProvider = provider;
        this.dozeHostProvider = provider2;
        this.dozeParametersProvider = provider3;
    }

    public static DozeModule_ProvidesWrappedServiceFactory create(Provider<DozeMachine.Service> provider, Provider<DozeHost> provider2, Provider<DozeParameters> provider3) {
        return new DozeModule_ProvidesWrappedServiceFactory(provider, provider2, provider3);
    }

    public static DozeMachine.Service providesWrappedService(DozeMachine.Service service, DozeHost dozeHost, DozeParameters dozeParameters) {
        return (DozeMachine.Service) Preconditions.checkNotNullFromProvides(DozeModule.providesWrappedService(service, dozeHost, dozeParameters));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeMachine.Service m2536get() {
        return providesWrappedService((DozeMachine.Service) this.dozeMachineServiceProvider.get(), (DozeHost) this.dozeHostProvider.get(), (DozeParameters) this.dozeParametersProvider.get());
    }
}