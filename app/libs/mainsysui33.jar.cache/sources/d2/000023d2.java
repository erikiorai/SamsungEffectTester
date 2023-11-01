package com.android.systemui.screenrecord;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$color;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.media.MediaProjectionCaptureTarget;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.screenrecord.ScreenMediaRecorder;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import java.io.IOException;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/RecordingService.class */
public class RecordingService extends Service implements ScreenMediaRecorder.ScreenMediaRecorderListener {
    public ScreenRecordingAudioSource mAudioSource;
    public final RecordingController mController;
    public final KeyguardDismissUtil mKeyguardDismissUtil;
    public final Executor mLongExecutor;
    public final Handler mMainHandler;
    public final NotificationManager mNotificationManager;
    public boolean mOriginalShowTaps;
    public ScreenMediaRecorder mRecorder;
    public boolean mShowTaps;
    public final UiEventLogger mUiEventLogger;
    public final UserContextProvider mUserContextTracker;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$8YyPpr0uTMCbaXZ0F6fqk8FPzK0(RecordingService recordingService, UserHandle userHandle) {
        recordingService.lambda$saveRecording$1(userHandle);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0.onDismiss():boolean] */
    public static /* synthetic */ boolean $r8$lambda$ftry27WcwqOYarCg2igvzA5KHWA(RecordingService recordingService, Intent intent, UserHandle userHandle) {
        return recordingService.lambda$onStartCommand$0(intent, userHandle);
    }

    public RecordingService(RecordingController recordingController, Executor executor, Handler handler, UiEventLogger uiEventLogger, NotificationManager notificationManager, UserContextProvider userContextProvider, KeyguardDismissUtil keyguardDismissUtil) {
        this.mController = recordingController;
        this.mLongExecutor = executor;
        this.mMainHandler = handler;
        this.mUiEventLogger = uiEventLogger;
        this.mNotificationManager = notificationManager;
        this.mUserContextTracker = userContextProvider;
        this.mKeyguardDismissUtil = keyguardDismissUtil;
    }

    public static Intent getNotificationIntent(Context context) {
        return new Intent(context, RecordingService.class).setAction("com.android.systemui.screenrecord.STOP_FROM_NOTIF");
    }

    public static Intent getShareIntent(Context context, String str) {
        return new Intent(context, RecordingService.class).setAction("com.android.systemui.screenrecord.SHARE").putExtra("extra_path", str);
    }

    public static Intent getStartIntent(Context context, int i, int i2, boolean z, MediaProjectionCaptureTarget mediaProjectionCaptureTarget) {
        return new Intent(context, RecordingService.class).setAction("com.android.systemui.screenrecord.START").putExtra("extra_resultCode", i).putExtra("extra_useAudio", i2).putExtra("extra_showTaps", z).putExtra("extra_captureTarget", mediaProjectionCaptureTarget);
    }

    public static Intent getStopIntent(Context context) {
        return new Intent(context, RecordingService.class).setAction("com.android.systemui.screenrecord.STOP").putExtra("android.intent.extra.user_handle", context.getUserId());
    }

    public /* synthetic */ boolean lambda$onStartCommand$0(Intent intent, UserHandle userHandle) {
        startActivity(Intent.createChooser(intent, getResources().getString(R$string.screenrecord_share_label)).setFlags(268435456));
        this.mNotificationManager.cancelAsUser(null, 4273, userHandle);
        return false;
    }

    public /* synthetic */ void lambda$saveRecording$1(UserHandle userHandle) {
        try {
            try {
                Log.d("RecordingService", "saving recording");
                Notification createSaveNotification = createSaveNotification(getRecorder().save());
                if (!this.mController.isRecording()) {
                    this.mNotificationManager.notifyAsUser(null, 4273, createSaveNotification, userHandle);
                }
            } catch (IOException e) {
                Log.e("RecordingService", "Error saving screen recording: " + e.getMessage());
                showErrorToast(R$string.screenrecord_delete_error);
            }
        } finally {
            this.mNotificationManager.cancelAsUser(null, 4275, userHandle);
        }
    }

    @VisibleForTesting
    public void createErrorNotification() {
        Resources resources = getResources();
        int i = R$string.screenrecord_name;
        NotificationChannel notificationChannel = new NotificationChannel("screen_record", getString(i), 3);
        notificationChannel.setDescription(getString(R$string.screenrecord_channel_description));
        notificationChannel.enableVibration(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(i));
        startForeground(4274, new Notification.Builder(this, "screen_record").setSmallIcon(R$drawable.ic_screenrecord).setContentTitle(resources.getString(R$string.screenrecord_start_error)).addExtras(bundle).build());
    }

    @VisibleForTesting
    public Notification createProcessingNotification() {
        Resources resources = getApplicationContext().getResources();
        String string = this.mAudioSource == ScreenRecordingAudioSource.NONE ? resources.getString(R$string.screenrecord_ongoing_screen_only) : resources.getString(R$string.screenrecord_ongoing_screen_and_audio);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(R$string.screenrecord_name));
        return new Notification.Builder(getApplicationContext(), "screen_record").setContentTitle(string).setContentText(getResources().getString(R$string.screenrecord_background_processing_label)).setSmallIcon(R$drawable.ic_screenrecord).addExtras(bundle).build();
    }

    @VisibleForTesting
    public void createRecordingNotification() {
        Resources resources = getResources();
        int i = R$string.screenrecord_name;
        NotificationChannel notificationChannel = new NotificationChannel("screen_record", getString(i), 3);
        notificationChannel.setDescription(getString(R$string.screenrecord_channel_description));
        notificationChannel.enableVibration(true);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", resources.getString(i));
        startForeground(4274, new Notification.Builder(this, "screen_record").setSmallIcon(R$drawable.ic_screenrecord).setContentTitle(this.mAudioSource == ScreenRecordingAudioSource.NONE ? resources.getString(R$string.screenrecord_ongoing_screen_only) : resources.getString(R$string.screenrecord_ongoing_screen_and_audio)).setUsesChronometer(true).setColorized(true).setColor(getResources().getColor(R$color.GM2_red_700)).setOngoing(true).setForegroundServiceBehavior(1).addAction(new Notification.Action.Builder(Icon.createWithResource(this, R$drawable.ic_android), getResources().getString(R$string.screenrecord_stop_label), PendingIntent.getService(this, 2, getNotificationIntent(this), 201326592)).build()).addExtras(bundle).build());
    }

    @VisibleForTesting
    public Notification createSaveNotification(ScreenMediaRecorder.SavedRecording savedRecording) {
        Uri uri = savedRecording.getUri();
        Intent dataAndType = new Intent("android.intent.action.VIEW").setFlags(268435457).setDataAndType(uri, "video/mp4");
        int i = R$drawable.ic_screenrecord;
        Notification.Action build = new Notification.Action.Builder(Icon.createWithResource(this, i), getResources().getString(R$string.screenrecord_share_label), PendingIntent.getService(this, 2, getShareIntent(this, uri.toString()), 201326592)).build();
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", getResources().getString(R$string.screenrecord_name));
        Notification.Builder addExtras = new Notification.Builder(this, "screen_record").setSmallIcon(i).setContentTitle(getResources().getString(R$string.screenrecord_save_title)).setContentText(getResources().getString(R$string.screenrecord_save_text)).setContentIntent(PendingIntent.getActivity(this, 2, dataAndType, 67108864)).addAction(build).setAutoCancel(true).addExtras(bundle);
        Bitmap thumbnail = savedRecording.getThumbnail();
        if (thumbnail != null) {
            addExtras.setStyle(new Notification.BigPictureStyle().bigPicture(thumbnail).showBigPictureWhenCollapsed(true));
        }
        return addExtras.build();
    }

    @VisibleForTesting
    public ScreenMediaRecorder getRecorder() {
        return this.mRecorder;
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
    }

    @Override // com.android.systemui.screenrecord.ScreenMediaRecorder.ScreenMediaRecorderListener
    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        Log.d("RecordingService", "Media recorder info: " + i);
        onStartCommand(getStopIntent(this), 0, 0);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        boolean z;
        if (intent == null) {
            return 2;
        }
        String action = intent.getAction();
        Log.d("RecordingService", "onStartCommand " + action);
        int userId = this.mUserContextTracker.getUserContext().getUserId();
        final UserHandle userHandle = new UserHandle(userId);
        action.hashCode();
        switch (action.hashCode()) {
            case -1688140755:
                if (action.equals("com.android.systemui.screenrecord.SHARE")) {
                    z = false;
                    break;
                }
                z = true;
                break;
            case -1687783248:
                if (action.equals("com.android.systemui.screenrecord.START")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case -470086188:
                if (action.equals("com.android.systemui.screenrecord.STOP")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            case -288359034:
                if (action.equals("com.android.systemui.screenrecord.STOP_FROM_NOTIF")) {
                    z = true;
                    break;
                }
                z = true;
                break;
            default:
                z = true;
                break;
        }
        switch (z) {
            case false:
                final Intent putExtra = new Intent("android.intent.action.SEND").setType("video/mp4").putExtra("android.intent.extra.STREAM", Uri.parse(intent.getStringExtra("extra_path")));
                this.mKeyguardDismissUtil.executeWhenUnlocked(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda0
                    @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                    public final boolean onDismiss() {
                        return RecordingService.$r8$lambda$ftry27WcwqOYarCg2igvzA5KHWA(RecordingService.this, putExtra, userHandle);
                    }
                }, false, false);
                sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
                return 1;
            case true:
                this.mAudioSource = ScreenRecordingAudioSource.values()[intent.getIntExtra("extra_useAudio", 0)];
                Log.d("RecordingService", "recording with audio source" + this.mAudioSource);
                this.mShowTaps = intent.getBooleanExtra("extra_showTaps", false);
                MediaProjectionCaptureTarget mediaProjectionCaptureTarget = (MediaProjectionCaptureTarget) intent.getParcelableExtra("extra_captureTarget", MediaProjectionCaptureTarget.class);
                this.mOriginalShowTaps = Settings.System.getInt(getApplicationContext().getContentResolver(), "show_touches", 0) != 0;
                setTapsVisible(this.mShowTaps);
                this.mRecorder = new ScreenMediaRecorder(this.mUserContextTracker.getUserContext(), this.mMainHandler, userId, this.mAudioSource, mediaProjectionCaptureTarget, this);
                if (startRecording()) {
                    updateState(true);
                    createRecordingNotification();
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_START);
                    return 1;
                }
                updateState(false);
                createErrorNotification();
                stopForeground(true);
                stopSelf();
                return 2;
            case true:
            case true:
                if ("com.android.systemui.screenrecord.STOP_FROM_NOTIF".equals(action)) {
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_END_NOTIFICATION);
                } else {
                    this.mUiEventLogger.log(Events$ScreenRecordEvent.SCREEN_RECORD_END_QS_TILE);
                }
                stopService(intent.getIntExtra("android.intent.extra.user_handle", -1));
                return 1;
            default:
                return 1;
        }
    }

    @Override // com.android.systemui.screenrecord.ScreenMediaRecorder.ScreenMediaRecorderListener
    public void onStopped() {
        if (this.mController.isRecording()) {
            Log.d("RecordingService", "Stopping recording because the system requested the stop");
            stopService();
        }
    }

    public final void saveRecording(int i) {
        final UserHandle userHandle = new UserHandle(i);
        this.mNotificationManager.notifyAsUser(null, 4275, createProcessingNotification(), userHandle);
        this.mLongExecutor.execute(new Runnable() { // from class: com.android.systemui.screenrecord.RecordingService$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RecordingService.$r8$lambda$8YyPpr0uTMCbaXZ0F6fqk8FPzK0(RecordingService.this, userHandle);
            }
        });
    }

    public final void setTapsVisible(boolean z) {
        Settings.System.putInt(getContentResolver(), "show_touches", z ? 1 : 0);
    }

    @VisibleForTesting
    public void showErrorToast(int i) {
        Toast.makeText(this, i, 1).show();
    }

    public final boolean startRecording() {
        try {
            getRecorder().start();
            return true;
        } catch (RemoteException | IOException | RuntimeException e) {
            showErrorToast(R$string.screenrecord_start_error);
            e.printStackTrace();
            return false;
        }
    }

    public final void stopService() {
        stopService(-1);
    }

    public final void stopService(int i) {
        int i2 = i;
        if (i == -1) {
            i2 = this.mUserContextTracker.getUserContext().getUserId();
        }
        Log.d("RecordingService", "notifying for user " + i2);
        setTapsVisible(this.mOriginalShowTaps);
        if (getRecorder() != null) {
            try {
                getRecorder().end();
                saveRecording(i2);
            } catch (RuntimeException e) {
                getRecorder().release();
                showErrorToast(R$string.screenrecord_start_error);
                Log.e("RecordingService", "stopRecording called, but there was an error when endingrecording");
                e.printStackTrace();
            } catch (Throwable th) {
                getRecorder().release();
                throw new RuntimeException(th);
            }
        } else {
            Log.e("RecordingService", "stopRecording called, but recorder was null");
        }
        updateState(false);
        stopSelf();
    }

    public final void updateState(boolean z) {
        if (this.mUserContextTracker.getUserContext().getUserId() == 0) {
            this.mController.updateState(z);
            return;
        }
        Intent intent = new Intent("com.android.systemui.screenrecord.UPDATE_STATE");
        intent.putExtra("extra_state", z);
        intent.addFlags(1073741824);
        sendBroadcast(intent, "com.android.systemui.permission.SELF");
    }
}