package com.android.systemui.screenshot;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TakeScreenshotService_Factory.class */
public final class TakeScreenshotService_Factory implements Factory<TakeScreenshotService> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DevicePolicyManager> devicePolicyManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<ScreenshotNotificationsController> notificationsControllerProvider;
    public final Provider<RequestProcessor> processorProvider;
    public final Provider<ScreenshotController> screenshotControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserManager> userManagerProvider;

    public TakeScreenshotService_Factory(Provider<ScreenshotController> provider, Provider<UserManager> provider2, Provider<DevicePolicyManager> provider3, Provider<UiEventLogger> provider4, Provider<ScreenshotNotificationsController> provider5, Provider<Context> provider6, Provider<Executor> provider7, Provider<FeatureFlags> provider8, Provider<RequestProcessor> provider9) {
        this.screenshotControllerProvider = provider;
        this.userManagerProvider = provider2;
        this.devicePolicyManagerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.notificationsControllerProvider = provider5;
        this.contextProvider = provider6;
        this.bgExecutorProvider = provider7;
        this.featureFlagsProvider = provider8;
        this.processorProvider = provider9;
    }

    public static TakeScreenshotService_Factory create(Provider<ScreenshotController> provider, Provider<UserManager> provider2, Provider<DevicePolicyManager> provider3, Provider<UiEventLogger> provider4, Provider<ScreenshotNotificationsController> provider5, Provider<Context> provider6, Provider<Executor> provider7, Provider<FeatureFlags> provider8, Provider<RequestProcessor> provider9) {
        return new TakeScreenshotService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static TakeScreenshotService newInstance(ScreenshotController screenshotController, UserManager userManager, DevicePolicyManager devicePolicyManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController screenshotNotificationsController, Context context, Executor executor, FeatureFlags featureFlags, RequestProcessor requestProcessor) {
        return new TakeScreenshotService(screenshotController, userManager, devicePolicyManager, uiEventLogger, screenshotNotificationsController, context, executor, featureFlags, requestProcessor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TakeScreenshotService m4336get() {
        return newInstance((ScreenshotController) this.screenshotControllerProvider.get(), (UserManager) this.userManagerProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (ScreenshotNotificationsController) this.notificationsControllerProvider.get(), (Context) this.contextProvider.get(), (Executor) this.bgExecutorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (RequestProcessor) this.processorProvider.get());
    }
}