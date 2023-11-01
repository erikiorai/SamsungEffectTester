package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.content.Context;
import android.os.UserManager;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController_Factory.class */
public final class ScreenshotController_Factory implements Factory<ScreenshotController> {
    public final Provider<ActionIntentExecutor> actionExecutorProvider;
    public final Provider<ActivityManager> activityManagerProvider;
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> flagsProvider;
    public final Provider<ImageCapture> imageCaptureProvider;
    public final Provider<ImageExporter> imageExporterProvider;
    public final Provider<LongScreenshotData> longScreenshotHolderProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<ScreenshotNotificationSmartActionsProvider> screenshotNotificationSmartActionsProvider;
    public final Provider<ScreenshotNotificationsController> screenshotNotificationsControllerProvider;
    public final Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;
    public final Provider<ScrollCaptureClient> scrollCaptureClientProvider;
    public final Provider<ScrollCaptureController> scrollCaptureControllerProvider;
    public final Provider<IStatusBarService> statusBarServiceProvider;
    public final Provider<TimeoutHandler> timeoutHandlerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserManager> userManagerProvider;

    public ScreenshotController_Factory(Provider<Context> provider, Provider<FeatureFlags> provider2, Provider<ScreenshotSmartActions> provider3, Provider<ScreenshotNotificationsController> provider4, Provider<ScrollCaptureClient> provider5, Provider<UiEventLogger> provider6, Provider<ImageExporter> provider7, Provider<ImageCapture> provider8, Provider<Executor> provider9, Provider<ScrollCaptureController> provider10, Provider<IStatusBarService> provider11, Provider<LongScreenshotData> provider12, Provider<ActivityManager> provider13, Provider<TimeoutHandler> provider14, Provider<BroadcastSender> provider15, Provider<ScreenshotNotificationSmartActionsProvider> provider16, Provider<ActionIntentExecutor> provider17, Provider<UserManager> provider18) {
        this.contextProvider = provider;
        this.flagsProvider = provider2;
        this.screenshotSmartActionsProvider = provider3;
        this.screenshotNotificationsControllerProvider = provider4;
        this.scrollCaptureClientProvider = provider5;
        this.uiEventLoggerProvider = provider6;
        this.imageExporterProvider = provider7;
        this.imageCaptureProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.scrollCaptureControllerProvider = provider10;
        this.statusBarServiceProvider = provider11;
        this.longScreenshotHolderProvider = provider12;
        this.activityManagerProvider = provider13;
        this.timeoutHandlerProvider = provider14;
        this.broadcastSenderProvider = provider15;
        this.screenshotNotificationSmartActionsProvider = provider16;
        this.actionExecutorProvider = provider17;
        this.userManagerProvider = provider18;
    }

    public static ScreenshotController_Factory create(Provider<Context> provider, Provider<FeatureFlags> provider2, Provider<ScreenshotSmartActions> provider3, Provider<ScreenshotNotificationsController> provider4, Provider<ScrollCaptureClient> provider5, Provider<UiEventLogger> provider6, Provider<ImageExporter> provider7, Provider<ImageCapture> provider8, Provider<Executor> provider9, Provider<ScrollCaptureController> provider10, Provider<IStatusBarService> provider11, Provider<LongScreenshotData> provider12, Provider<ActivityManager> provider13, Provider<TimeoutHandler> provider14, Provider<BroadcastSender> provider15, Provider<ScreenshotNotificationSmartActionsProvider> provider16, Provider<ActionIntentExecutor> provider17, Provider<UserManager> provider18) {
        return new ScreenshotController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18);
    }

    public static ScreenshotController newInstance(Context context, FeatureFlags featureFlags, ScreenshotSmartActions screenshotSmartActions, ScreenshotNotificationsController screenshotNotificationsController, ScrollCaptureClient scrollCaptureClient, UiEventLogger uiEventLogger, Object obj, ImageCapture imageCapture, Executor executor, ScrollCaptureController scrollCaptureController, IStatusBarService iStatusBarService, LongScreenshotData longScreenshotData, ActivityManager activityManager, TimeoutHandler timeoutHandler, BroadcastSender broadcastSender, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ActionIntentExecutor actionIntentExecutor, UserManager userManager) {
        return new ScreenshotController(context, featureFlags, screenshotSmartActions, screenshotNotificationsController, scrollCaptureClient, uiEventLogger, (ImageExporter) obj, imageCapture, executor, scrollCaptureController, iStatusBarService, longScreenshotData, activityManager, timeoutHandler, broadcastSender, screenshotNotificationSmartActionsProvider, actionIntentExecutor, userManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenshotController m4284get() {
        return newInstance((Context) this.contextProvider.get(), (FeatureFlags) this.flagsProvider.get(), (ScreenshotSmartActions) this.screenshotSmartActionsProvider.get(), (ScreenshotNotificationsController) this.screenshotNotificationsControllerProvider.get(), (ScrollCaptureClient) this.scrollCaptureClientProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), this.imageExporterProvider.get(), (ImageCapture) this.imageCaptureProvider.get(), (Executor) this.mainExecutorProvider.get(), (ScrollCaptureController) this.scrollCaptureControllerProvider.get(), (IStatusBarService) this.statusBarServiceProvider.get(), (LongScreenshotData) this.longScreenshotHolderProvider.get(), (ActivityManager) this.activityManagerProvider.get(), (TimeoutHandler) this.timeoutHandlerProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get(), (ScreenshotNotificationSmartActionsProvider) this.screenshotNotificationSmartActionsProvider.get(), (ActionIntentExecutor) this.actionExecutorProvider.get(), (UserManager) this.userManagerProvider.get());
    }
}