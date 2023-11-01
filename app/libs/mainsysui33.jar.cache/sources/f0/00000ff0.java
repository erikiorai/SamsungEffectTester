package com.android.systemui;

import android.content.Context;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.concurrency.ThreadFactory;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ScreenDecorations_Factory.class */
public final class ScreenDecorations_Factory implements Factory<ScreenDecorations> {
    public final Provider<Context> contextProvider;
    public final Provider<PrivacyDotDecorProviderFactory> dotFactoryProvider;
    public final Provider<PrivacyDotViewController> dotViewControllerProvider;
    public final Provider<FaceScanningProviderFactory> faceScanningFactoryProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<ThreadFactory> threadFactoryProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ScreenDecorations_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<SecureSettings> provider3, Provider<TunerService> provider4, Provider<UserTracker> provider5, Provider<PrivacyDotViewController> provider6, Provider<ThreadFactory> provider7, Provider<PrivacyDotDecorProviderFactory> provider8, Provider<FaceScanningProviderFactory> provider9) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.userTrackerProvider = provider5;
        this.dotViewControllerProvider = provider6;
        this.threadFactoryProvider = provider7;
        this.dotFactoryProvider = provider8;
        this.faceScanningFactoryProvider = provider9;
    }

    public static ScreenDecorations_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<SecureSettings> provider3, Provider<TunerService> provider4, Provider<UserTracker> provider5, Provider<PrivacyDotViewController> provider6, Provider<ThreadFactory> provider7, Provider<PrivacyDotDecorProviderFactory> provider8, Provider<FaceScanningProviderFactory> provider9) {
        return new ScreenDecorations_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static ScreenDecorations newInstance(Context context, Executor executor, SecureSettings secureSettings, TunerService tunerService, UserTracker userTracker, PrivacyDotViewController privacyDotViewController, ThreadFactory threadFactory, PrivacyDotDecorProviderFactory privacyDotDecorProviderFactory, FaceScanningProviderFactory faceScanningProviderFactory) {
        return new ScreenDecorations(context, executor, secureSettings, tunerService, userTracker, privacyDotViewController, threadFactory, privacyDotDecorProviderFactory, faceScanningProviderFactory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenDecorations m1299get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (TunerService) this.tunerServiceProvider.get(), (UserTracker) this.userTrackerProvider.get(), (PrivacyDotViewController) this.dotViewControllerProvider.get(), (ThreadFactory) this.threadFactoryProvider.get(), (PrivacyDotDecorProviderFactory) this.dotFactoryProvider.get(), (FaceScanningProviderFactory) this.faceScanningFactoryProvider.get());
    }
}