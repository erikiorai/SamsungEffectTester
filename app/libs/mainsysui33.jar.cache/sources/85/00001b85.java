package com.android.systemui.log.dagger;

import android.content.ContentResolver;
import android.os.Build;
import android.os.Looper;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogcatEchoTracker;
import com.android.systemui.plugins.log.LogcatEchoTrackerDebug;
import com.android.systemui.plugins.log.LogcatEchoTrackerProd;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule.class */
public class LogModule {
    public static LogBuffer provideBiometricLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("BiometricLog", 200);
    }

    public static TableLogBuffer provideBouncerLogBuffer(TableLogBufferFactory tableLogBufferFactory) {
        return tableLogBufferFactory.create("BouncerLog", 250);
    }

    public static LogBuffer provideBroadcastDispatcherLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("BroadcastDispatcherLog", 500, false);
    }

    public static LogBuffer provideCollapsedSbFragmentLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("CollapsedSbFragmentLog", 20);
    }

    public static LogBuffer provideDozeLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("DozeLog", 150);
    }

    public static LogBuffer provideKeyguardClockLog(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("KeyguardClockLog", 500);
    }

    public static LogBuffer provideKeyguardLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("KeyguardLog", 250);
    }

    public static LogBuffer provideKeyguardUpdateMonitorLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("KeyguardUpdateMonitorLog", 400);
    }

    public static LogBuffer provideLSShadeTransitionControllerBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("LSShadeTransitionLog", 50);
    }

    public static LogcatEchoTracker provideLogcatEchoTracker(ContentResolver contentResolver, Looper looper) {
        return Build.isDebuggable() ? LogcatEchoTrackerDebug.create(contentResolver, looper) : new LogcatEchoTrackerProd();
    }

    public static LogBuffer provideMediaBrowserBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaBrowser", 100);
    }

    public static LogBuffer provideMediaCarouselControllerBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaCarouselCtlrLog", 20);
    }

    public static LogBuffer provideMediaMuteAwaitLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaMuteAwaitLog", 20);
    }

    public static LogBuffer provideMediaTttReceiverLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaTttReceiver", 20);
    }

    public static LogBuffer provideMediaTttSenderLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaTttSender", 20);
    }

    public static LogBuffer provideMediaViewLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaView", 100);
    }

    public static LogBuffer provideNearbyMediaDevicesLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NearbyMediaDevicesLog", 20);
    }

    public static LogBuffer provideNotifInteractionLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifInteractionLog", 50);
    }

    public static LogBuffer provideNotificationHeadsUpLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifHeadsUpLog", 1000);
    }

    public static LogBuffer provideNotificationInterruptLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifInterruptLog", 100);
    }

    public static LogBuffer provideNotificationRenderLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("NotifRenderLog", 100);
    }

    public static LogBuffer provideNotificationsLogBuffer(LogBufferFactory logBufferFactory, NotifPipelineFlags notifPipelineFlags) {
        return logBufferFactory.create("NotifLog", notifPipelineFlags.isDevLoggingEnabled() ? 10000 : 1000, false);
    }

    public static LogBuffer providePrivacyLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("PrivacyLog", 100);
    }

    public static LogBuffer provideQSFragmentDisableLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("QSFragmentDisableFlagsLog", 10, false);
    }

    public static LogBuffer provideQuickSettingsLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("QSLog", 700, false);
    }

    public static LogBuffer provideShadeHeightLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("ShadeHeightLog", 500);
    }

    public static LogBuffer provideShadeLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("ShadeLog", 500, false);
    }

    public static LogBuffer provideShadeWindowLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("ShadeWindowLog", 600, false);
    }

    public static LogBuffer provideStatusBarConnectivityBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("SbConnectivity", 64);
    }

    public static LogBuffer provideStatusBarNetworkControllerBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("StatusBarNetworkControllerLog", 20);
    }

    public static LogBuffer provideSwipeAwayGestureLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("SwipeStatusBarAwayLog", 30);
    }

    public static LogBuffer provideToastLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("ToastLog", 50);
    }

    public static LogBuffer providerBluetoothLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("BluetoothLog", 50);
    }

    public static LogBuffer providesMediaTimeoutListenerLogBuffer(LogBufferFactory logBufferFactory) {
        return logBufferFactory.create("MediaTimeout", 100);
    }
}