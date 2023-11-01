package com.android.systemui.screenshot;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Insets;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FlagListenable;
import com.android.systemui.flags.Flags;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TakeScreenshotService.class */
public class TakeScreenshotService extends Service {
    public static final String TAG = LogConfig.logTag(TakeScreenshotService.class);
    public final Executor mBgExecutor;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final FeatureFlags mFeatureFlags;
    public final ScreenshotNotificationsController mNotificationsController;
    public final RequestProcessor mProcessor;
    public final ScreenshotController mScreenshot;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final BroadcastReceiver mCloseSystemDialogs = new BroadcastReceiver() { // from class: com.android.systemui.screenshot.TakeScreenshotService.1
        {
            TakeScreenshotService.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (!"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction()) || TakeScreenshotService.this.mScreenshot == null || TakeScreenshotService.this.mScreenshot.isPendingSharedTransition()) {
                return;
            }
            TakeScreenshotService.this.mScreenshot.dismissScreenshot(ScreenshotEvent.SCREENSHOT_DISMISSED_OTHER);
        }
    };
    public final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda4
        @Override // android.os.Handler.Callback
        public final boolean handleMessage(Message message) {
            return TakeScreenshotService.m4331$r8$lambda$Tu6Hb1eNHMdc6te5Monf_tnwS0(TakeScreenshotService.this, message);
        }
    });

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TakeScreenshotService$RequestCallback.class */
    public interface RequestCallback {
        void onFinish();

        void reportError();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/TakeScreenshotService$RequestCallbackImpl.class */
    public static class RequestCallbackImpl implements RequestCallback {
        public final Messenger mReplyTo;

        public RequestCallbackImpl(Messenger messenger) {
            this.mReplyTo = messenger;
        }

        @Override // com.android.systemui.screenshot.TakeScreenshotService.RequestCallback
        public void onFinish() {
            TakeScreenshotService.sendComplete(this.mReplyTo);
        }

        @Override // com.android.systemui.screenshot.TakeScreenshotService.RequestCallback
        public void reportError() {
            TakeScreenshotService.reportUri(this.mReplyTo, null);
            TakeScreenshotService.sendComplete(this.mReplyTo);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$91Fs3H8nhu_l0EwmjVApGqVgkUg(TakeScreenshotService takeScreenshotService, String str) {
        takeScreenshotService.lambda$handleRequest$2(str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda2.get():java.lang.Object] */
    public static /* synthetic */ String $r8$lambda$Cqn_4FnOG4894Zs6PfdCYUJHSvM(TakeScreenshotService takeScreenshotService) {
        return takeScreenshotService.lambda$handleRequest$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda6.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$DqBHcAB2PEYik8rbz_-JNgoaujM */
    public static /* synthetic */ void m4330$r8$lambda$DqBHcAB2PEYik8rbz_JNgoaujM(Messenger messenger, Uri uri) {
        reportUri(messenger, uri);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda4.handleMessage(android.os.Message):boolean] */
    /* renamed from: $r8$lambda$Tu6Hb1eNHMdc6te5Mon-f_tnwS0 */
    public static /* synthetic */ boolean m4331$r8$lambda$Tu6Hb1eNHMdc6te5Monf_tnwS0(TakeScreenshotService takeScreenshotService, Message message) {
        return takeScreenshotService.handleMessage(message);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$Zh4vHKdwBFqeA2AMIac6NFAy33Q(TakeScreenshotService takeScreenshotService, RequestCallback requestCallback) {
        takeScreenshotService.lambda$handleRequest$3(requestCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$ztRjqxurokDyGfL9mk057yW_XEo(TakeScreenshotService takeScreenshotService, Consumer consumer, RequestCallback requestCallback, ScreenshotHelper.ScreenshotRequest screenshotRequest) {
        takeScreenshotService.lambda$handleRequest$4(consumer, requestCallback, screenshotRequest);
    }

    public TakeScreenshotService(ScreenshotController screenshotController, UserManager userManager, DevicePolicyManager devicePolicyManager, UiEventLogger uiEventLogger, ScreenshotNotificationsController screenshotNotificationsController, Context context, Executor executor, FeatureFlags featureFlags, RequestProcessor requestProcessor) {
        this.mScreenshot = screenshotController;
        this.mUserManager = userManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationsController = screenshotNotificationsController;
        this.mContext = context;
        this.mBgExecutor = executor;
        this.mFeatureFlags = featureFlags;
        featureFlags.addListener(Flags.SCREENSHOT_REQUEST_PROCESSOR, new FlagListenable.Listener() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda5
            @Override // com.android.systemui.flags.FlagListenable.Listener
            public final void onFlagChanged(FlagListenable.FlagEvent flagEvent) {
                flagEvent.requestNoRestart();
            }
        });
        featureFlags.addListener(Flags.SCREENSHOT_WORK_PROFILE_POLICY, new FlagListenable.Listener() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda5
            @Override // com.android.systemui.flags.FlagListenable.Listener
            public final void onFlagChanged(FlagListenable.FlagEvent flagEvent) {
                flagEvent.requestNoRestart();
            }
        });
        this.mProcessor = requestProcessor;
    }

    public /* synthetic */ String lambda$handleRequest$1() {
        return this.mContext.getString(R$string.screenshot_blocked_by_admin);
    }

    public /* synthetic */ void lambda$handleRequest$2(String str) {
        Toast.makeText(this.mContext, str, 0).show();
    }

    public /* synthetic */ void lambda$handleRequest$3(RequestCallback requestCallback) {
        Log.w(TAG, "Skipping screenshot because an IT admin has disabled screenshots on the device");
        final String string = this.mDevicePolicyManager.getResources().getString("SystemUi.SCREENSHOT_BLOCKED_BY_ADMIN", new Supplier() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return TakeScreenshotService.$r8$lambda$Cqn_4FnOG4894Zs6PfdCYUJHSvM(TakeScreenshotService.this);
            }
        });
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                TakeScreenshotService.$r8$lambda$91Fs3H8nhu_l0EwmjVApGqVgkUg(TakeScreenshotService.this, string);
            }
        });
        requestCallback.reportError();
    }

    public static void reportUri(Messenger messenger, Uri uri) {
        try {
            messenger.send(Message.obtain(null, 1, uri));
        } catch (RemoteException e) {
            Log.d(TAG, "ignored remote exception", e);
        }
    }

    public static void sendComplete(Messenger messenger) {
        try {
            messenger.send(Message.obtain((Handler) null, 2));
        } catch (RemoteException e) {
            Log.d(TAG, "ignored remote exception", e);
        }
    }

    /* renamed from: dispatchToController */
    public final void lambda$handleRequest$4(ScreenshotHelper.ScreenshotRequest screenshotRequest, Consumer<Uri> consumer, RequestCallback requestCallback) {
        ComponentName topComponent = screenshotRequest.getTopComponent();
        this.mUiEventLogger.log(ScreenshotEvent.getScreenshotSource(screenshotRequest.getSource()), 0, topComponent == null ? "" : topComponent.getPackageName());
        int type = screenshotRequest.getType();
        if (type == 1) {
            this.mScreenshot.takeScreenshotFullscreen(topComponent, consumer, requestCallback);
        } else if (type == 2) {
            this.mScreenshot.takeScreenshotPartial(topComponent, consumer, requestCallback);
        } else if (type != 3) {
            String str = TAG;
            Log.w(str, "Invalid screenshot option: " + screenshotRequest.getType());
        } else {
            Bitmap bundleToHardwareBitmap = ScreenshotHelper.HardwareBitmapBundler.bundleToHardwareBitmap(screenshotRequest.getBitmapBundle());
            Rect boundsInScreen = screenshotRequest.getBoundsInScreen();
            Insets insets = screenshotRequest.getInsets();
            int taskId = screenshotRequest.getTaskId();
            int userId = screenshotRequest.getUserId();
            if (bundleToHardwareBitmap != null) {
                this.mScreenshot.handleImageAsScreenshot(bundleToHardwareBitmap, boundsInScreen, insets, taskId, userId, topComponent, consumer, requestCallback);
                return;
            }
            Log.e(TAG, "Got null bitmap from screenshot message");
            this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_capture_text);
            requestCallback.reportError();
        }
    }

    public final boolean handleMessage(Message message) {
        final Messenger messenger = message.replyTo;
        handleRequest((ScreenshotHelper.ScreenshotRequest) message.obj, new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TakeScreenshotService.m4330$r8$lambda$DqBHcAB2PEYik8rbz_JNgoaujM(messenger, (Uri) obj);
            }
        }, new RequestCallbackImpl(messenger));
        return true;
    }

    @VisibleForTesting
    public void handleRequest(ScreenshotHelper.ScreenshotRequest screenshotRequest, final Consumer<Uri> consumer, final RequestCallback requestCallback) {
        if (!this.mUserManager.isUserUnlocked()) {
            Log.w(TAG, "Skipping screenshot because storage is locked!");
            this.mNotificationsController.notifyScreenshotError(R$string.screenshot_failed_to_save_user_locked_text);
            requestCallback.reportError();
        } else if (this.mDevicePolicyManager.getScreenCaptureDisabled(null, -1)) {
            this.mBgExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TakeScreenshotService.$r8$lambda$Zh4vHKdwBFqeA2AMIac6NFAy33Q(TakeScreenshotService.this, requestCallback);
                }
            });
        } else if (!this.mFeatureFlags.isEnabled(Flags.SCREENSHOT_REQUEST_PROCESSOR)) {
            lambda$handleRequest$4(screenshotRequest, consumer, requestCallback);
        } else {
            Log.d(TAG, "handleMessage: Using request processor");
            this.mProcessor.processAsync(screenshotRequest, new Consumer() { // from class: com.android.systemui.screenshot.TakeScreenshotService$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TakeScreenshotService.$r8$lambda$ztRjqxurokDyGfL9mk057yW_XEo(TakeScreenshotService.this, consumer, requestCallback, (ScreenshotHelper.ScreenshotRequest) obj);
                }
            });
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        registerReceiver(this.mCloseSystemDialogs, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"), 2);
        return new Messenger(this.mHandler).getBinder();
    }

    @Override // android.app.Service
    public void onCreate() {
    }

    @Override // android.app.Service
    public void onDestroy() {
        super.onDestroy();
        this.mScreenshot.onDestroy();
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        this.mScreenshot.removeWindow();
        unregisterReceiver(this.mCloseSystemDialogs);
        return false;
    }
}