package com.android.systemui.decor;

import android.content.Context;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/decor/FaceScanningProviderFactory_Factory.class */
public final class FaceScanningProviderFactory_Factory implements Factory<FaceScanningProviderFactory> {
    public final Provider<AuthController> authControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public FaceScanningProviderFactory_Factory(Provider<AuthController> provider, Provider<Context> provider2, Provider<StatusBarStateController> provider3, Provider<KeyguardUpdateMonitor> provider4, Provider<Executor> provider5) {
        this.authControllerProvider = provider;
        this.contextProvider = provider2;
        this.statusBarStateControllerProvider = provider3;
        this.keyguardUpdateMonitorProvider = provider4;
        this.mainExecutorProvider = provider5;
    }

    public static FaceScanningProviderFactory_Factory create(Provider<AuthController> provider, Provider<Context> provider2, Provider<StatusBarStateController> provider3, Provider<KeyguardUpdateMonitor> provider4, Provider<Executor> provider5) {
        return new FaceScanningProviderFactory_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FaceScanningProviderFactory newInstance(AuthController authController, Context context, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor) {
        return new FaceScanningProviderFactory(authController, context, statusBarStateController, keyguardUpdateMonitor, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FaceScanningProviderFactory m2393get() {
        return newInstance((AuthController) this.authControllerProvider.get(), (Context) this.contextProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}