package com.android.systemui.screenshot;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.screenshot.ScreenshotNotificationSmartActionsProvider;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotSmartActions.class */
public class ScreenshotSmartActions {
    public static final String TAG = LogConfig.logTag(ScreenshotSmartActions.class);
    public final Provider<ScreenshotNotificationSmartActionsProvider> mScreenshotNotificationSmartActionsProviderProvider;

    public ScreenshotSmartActions(Provider<ScreenshotNotificationSmartActionsProvider> provider) {
        this.mScreenshotNotificationSmartActionsProviderProvider = provider;
    }

    @VisibleForTesting
    public List<Notification.Action> getSmartActions(String str, CompletableFuture<List<Notification.Action>> completableFuture, int i, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType) {
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            List<Notification.Action> list = completableFuture.get(i, TimeUnit.MILLISECONDS);
            notifyScreenshotOp(str, screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotOp.WAIT_FOR_SMART_ACTIONS, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.SUCCESS, SystemClock.uptimeMillis() - uptimeMillis);
            return list;
        } catch (Throwable th) {
            notifyScreenshotOp(str, screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotOp.WAIT_FOR_SMART_ACTIONS, th instanceof TimeoutException ? ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.TIMEOUT : ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.ERROR, SystemClock.uptimeMillis() - uptimeMillis);
            return Collections.emptyList();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0038, code lost:
        if (r20 != null) goto L16;
     */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public CompletableFuture<List<Notification.Action>> getSmartActionsFuture(String str, Uri uri, Bitmap bitmap, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotSmartActionType screenshotSmartActionType, boolean z, UserHandle userHandle) {
        CompletableFuture<List<Notification.Action>> completableFuture;
        ComponentName componentName;
        if (z && bitmap.getConfig() == Bitmap.Config.HARDWARE) {
            long uptimeMillis = SystemClock.uptimeMillis();
            try {
                ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
                if (runningTask != null) {
                    componentName = runningTask.topActivity;
                }
                componentName = new ComponentName("", "");
                completableFuture = screenshotNotificationSmartActionsProvider.getActions(str, uri, bitmap, componentName, screenshotSmartActionType, userHandle);
            } catch (Throwable th) {
                long uptimeMillis2 = SystemClock.uptimeMillis();
                CompletableFuture<List<Notification.Action>> completedFuture = CompletableFuture.completedFuture(Collections.emptyList());
                notifyScreenshotOp(str, screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotOp.REQUEST_SMART_ACTIONS, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus.ERROR, uptimeMillis2 - uptimeMillis);
                completableFuture = completedFuture;
            }
            return completableFuture;
        }
        return CompletableFuture.completedFuture(Collections.emptyList());
    }

    public void notifyScreenshotAction(String str, String str2, boolean z, Intent intent) {
        try {
            ((ScreenshotNotificationSmartActionsProvider) this.mScreenshotNotificationSmartActionsProviderProvider.get()).notifyAction(str, str2, z, intent);
        } catch (Throwable th) {
            Log.e(TAG, "Error in notifyScreenshotAction: ", th);
        }
    }

    public void notifyScreenshotOp(String str, ScreenshotNotificationSmartActionsProvider screenshotNotificationSmartActionsProvider, ScreenshotNotificationSmartActionsProvider.ScreenshotOp screenshotOp, ScreenshotNotificationSmartActionsProvider.ScreenshotOpStatus screenshotOpStatus, long j) {
        try {
            screenshotNotificationSmartActionsProvider.notifyOp(str, screenshotOp, screenshotOpStatus, j);
        } catch (Throwable th) {
            Log.e(TAG, "Error in notifyScreenshotOp: ", th);
        }
    }
}