package com.android.systemui.keyguard.domain.interactor;

import android.os.Handler;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.data.BouncerView;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerInteractor_Factory.class */
public final class PrimaryBouncerInteractor_Factory implements Factory<PrimaryBouncerInteractor> {
    public final Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<KeyguardBypassController> keyguardBypassControllerProvider;
    public final Provider<KeyguardSecurityModel> keyguardSecurityModelProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<PrimaryBouncerCallbackInteractor> primaryBouncerCallbackInteractorProvider;
    public final Provider<BouncerView> primaryBouncerViewProvider;
    public final Provider<KeyguardBouncerRepository> repositoryProvider;

    public PrimaryBouncerInteractor_Factory(Provider<KeyguardBouncerRepository> provider, Provider<BouncerView> provider2, Provider<Handler> provider3, Provider<KeyguardStateController> provider4, Provider<KeyguardSecurityModel> provider5, Provider<PrimaryBouncerCallbackInteractor> provider6, Provider<FalsingCollector> provider7, Provider<DismissCallbackRegistry> provider8, Provider<KeyguardBypassController> provider9, Provider<KeyguardUpdateMonitor> provider10) {
        this.repositoryProvider = provider;
        this.primaryBouncerViewProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.keyguardSecurityModelProvider = provider5;
        this.primaryBouncerCallbackInteractorProvider = provider6;
        this.falsingCollectorProvider = provider7;
        this.dismissCallbackRegistryProvider = provider8;
        this.keyguardBypassControllerProvider = provider9;
        this.keyguardUpdateMonitorProvider = provider10;
    }

    public static PrimaryBouncerInteractor_Factory create(Provider<KeyguardBouncerRepository> provider, Provider<BouncerView> provider2, Provider<Handler> provider3, Provider<KeyguardStateController> provider4, Provider<KeyguardSecurityModel> provider5, Provider<PrimaryBouncerCallbackInteractor> provider6, Provider<FalsingCollector> provider7, Provider<DismissCallbackRegistry> provider8, Provider<KeyguardBypassController> provider9, Provider<KeyguardUpdateMonitor> provider10) {
        return new PrimaryBouncerInteractor_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static PrimaryBouncerInteractor newInstance(KeyguardBouncerRepository keyguardBouncerRepository, BouncerView bouncerView, Handler handler, KeyguardStateController keyguardStateController, KeyguardSecurityModel keyguardSecurityModel, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, FalsingCollector falsingCollector, DismissCallbackRegistry dismissCallbackRegistry, KeyguardBypassController keyguardBypassController, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new PrimaryBouncerInteractor(keyguardBouncerRepository, bouncerView, handler, keyguardStateController, keyguardSecurityModel, primaryBouncerCallbackInteractor, falsingCollector, dismissCallbackRegistry, keyguardBypassController, keyguardUpdateMonitor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PrimaryBouncerInteractor m3055get() {
        return newInstance((KeyguardBouncerRepository) this.repositoryProvider.get(), (BouncerView) this.primaryBouncerViewProvider.get(), (Handler) this.mainHandlerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardSecurityModel) this.keyguardSecurityModelProvider.get(), (PrimaryBouncerCallbackInteractor) this.primaryBouncerCallbackInteractorProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (DismissCallbackRegistry) this.dismissCallbackRegistryProvider.get(), (KeyguardBypassController) this.keyguardBypassControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}