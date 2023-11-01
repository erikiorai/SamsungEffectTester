package com.android.systemui.screenrecord;

import android.app.NotificationManager;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/RecordingService_Factory.class */
public final class RecordingService_Factory implements Factory<RecordingService> {
    public final Provider<RecordingController> controllerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
    public final Provider<NotificationManager> notificationManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserContextProvider> userContextTrackerProvider;

    public RecordingService_Factory(Provider<RecordingController> provider, Provider<Executor> provider2, Provider<Handler> provider3, Provider<UiEventLogger> provider4, Provider<NotificationManager> provider5, Provider<UserContextProvider> provider6, Provider<KeyguardDismissUtil> provider7) {
        this.controllerProvider = provider;
        this.executorProvider = provider2;
        this.handlerProvider = provider3;
        this.uiEventLoggerProvider = provider4;
        this.notificationManagerProvider = provider5;
        this.userContextTrackerProvider = provider6;
        this.keyguardDismissUtilProvider = provider7;
    }

    public static RecordingService_Factory create(Provider<RecordingController> provider, Provider<Executor> provider2, Provider<Handler> provider3, Provider<UiEventLogger> provider4, Provider<NotificationManager> provider5, Provider<UserContextProvider> provider6, Provider<KeyguardDismissUtil> provider7) {
        return new RecordingService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static RecordingService newInstance(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil) {
        return new RecordingService(recordingController, executor, handler, uiEventLogger, notificationManager, userContextProvider, keyguardDismissUtil);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public RecordingService m4178get() {
        return newInstance((RecordingController) this.controllerProvider.get(), (Executor) this.executorProvider.get(), (Handler) this.handlerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (NotificationManager) this.notificationManagerProvider.get(), (UserContextProvider) this.userContextTrackerProvider.get(), (KeyguardDismissUtil) this.keyguardDismissUtilProvider.get());
    }
}