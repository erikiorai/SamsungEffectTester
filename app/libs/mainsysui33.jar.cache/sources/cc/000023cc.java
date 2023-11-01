package com.android.systemui.screenrecord;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.CountDownTimer;
import android.os.UserHandle;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/RecordingController.class */
public class RecordingController implements CallbackController<RecordingStateChangeCallback> {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public boolean mIsRecording;
    public boolean mIsStarting;
    public final Executor mMainExecutor;
    public PendingIntent mStopIntent;
    public final UserContextProvider mUserContextProvider;
    public final UserTracker mUserTracker;
    public CountDownTimer mCountDownTimer = null;
    public CopyOnWriteArrayList<RecordingStateChangeCallback> mListeners = new CopyOnWriteArrayList<>();
    @VisibleForTesting
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.screenrecord.RecordingController.1
        public void onUserChanged(int i, Context context) {
            RecordingController.this.stopRecording();
        }
    };
    @VisibleForTesting
    public final BroadcastReceiver mStateChangeReceiver = new BroadcastReceiver() { // from class: com.android.systemui.screenrecord.RecordingController.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent == null || !"com.android.systemui.screenrecord.UPDATE_STATE".equals(intent.getAction())) {
                return;
            }
            if (!intent.hasExtra("extra_state")) {
                Log.e("RecordingController", "Received update intent with no state");
                return;
            }
            RecordingController.this.updateState(intent.getBooleanExtra("extra_state", false));
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/screenrecord/RecordingController$RecordingStateChangeCallback.class */
    public interface RecordingStateChangeCallback {
        default void onCountdown(long j) {
        }

        default void onCountdownEnd() {
        }

        default void onRecordingEnd() {
        }

        default void onRecordingStart() {
        }
    }

    public RecordingController(Executor executor, BroadcastDispatcher broadcastDispatcher, UserContextProvider userContextProvider, UserTracker userTracker) {
        this.mMainExecutor = executor;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mUserContextProvider = userContextProvider;
        this.mUserTracker = userTracker;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void addCallback(RecordingStateChangeCallback recordingStateChangeCallback) {
        this.mListeners.add(recordingStateChangeCallback);
    }

    public void cancelCountdown() {
        CountDownTimer countDownTimer = this.mCountDownTimer;
        if (countDownTimer != null) {
            countDownTimer.cancel();
        } else {
            Log.e("RecordingController", "Timer was null");
        }
        this.mIsStarting = false;
        Iterator<RecordingStateChangeCallback> it = this.mListeners.iterator();
        while (it.hasNext()) {
            it.next().onCountdownEnd();
        }
    }

    public Dialog createScreenRecordDialog(Context context, FeatureFlags featureFlags, DialogLaunchAnimator dialogLaunchAnimator, ActivityStarter activityStarter, Runnable runnable) {
        return featureFlags.isEnabled(Flags.WM_ENABLE_PARTIAL_SCREEN_SHARING) ? new ScreenRecordPermissionDialog(context, this, activityStarter, dialogLaunchAnimator, this.mUserContextProvider, runnable) : new ScreenRecordDialog(context, this, activityStarter, this.mUserContextProvider, featureFlags, dialogLaunchAnimator, runnable);
    }

    public boolean isRecording() {
        boolean z;
        synchronized (this) {
            z = this.mIsRecording;
        }
        return z;
    }

    public boolean isStarting() {
        return this.mIsStarting;
    }

    /* JADX DEBUG: Method merged with bridge method */
    public void removeCallback(RecordingStateChangeCallback recordingStateChangeCallback) {
        this.mListeners.remove(recordingStateChangeCallback);
    }

    public void startCountdown(long j, long j2, final PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        this.mIsStarting = true;
        this.mStopIntent = pendingIntent2;
        CountDownTimer countDownTimer = new CountDownTimer(j, j2) { // from class: com.android.systemui.screenrecord.RecordingController.3
            @Override // android.os.CountDownTimer
            public void onFinish() {
                RecordingController.this.mIsStarting = false;
                RecordingController.this.mIsRecording = true;
                Iterator it = RecordingController.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((RecordingStateChangeCallback) it.next()).onCountdownEnd();
                }
                try {
                    pendingIntent.send();
                    UserTracker userTracker = RecordingController.this.mUserTracker;
                    RecordingController recordingController = RecordingController.this;
                    userTracker.addCallback(recordingController.mUserChangedCallback, recordingController.mMainExecutor);
                    RecordingController.this.mBroadcastDispatcher.registerReceiver(RecordingController.this.mStateChangeReceiver, new IntentFilter("com.android.systemui.screenrecord.UPDATE_STATE"), null, UserHandle.ALL);
                    Log.d("RecordingController", "sent start intent");
                } catch (PendingIntent.CanceledException e) {
                    Log.e("RecordingController", "Pending intent was cancelled: " + e.getMessage());
                }
            }

            @Override // android.os.CountDownTimer
            public void onTick(long j3) {
                Iterator it = RecordingController.this.mListeners.iterator();
                while (it.hasNext()) {
                    ((RecordingStateChangeCallback) it.next()).onCountdown(j3);
                }
            }
        };
        this.mCountDownTimer = countDownTimer;
        countDownTimer.start();
    }

    public void stopRecording() {
        try {
            PendingIntent pendingIntent = this.mStopIntent;
            if (pendingIntent != null) {
                pendingIntent.send();
            } else {
                Log.e("RecordingController", "Stop intent was null");
            }
            updateState(false);
        } catch (PendingIntent.CanceledException e) {
            Log.e("RecordingController", "Error stopping: " + e.getMessage());
        }
    }

    public void updateState(boolean z) {
        synchronized (this) {
            if (!z) {
                if (this.mIsRecording) {
                    this.mUserTracker.removeCallback(this.mUserChangedCallback);
                    this.mBroadcastDispatcher.unregisterReceiver(this.mStateChangeReceiver);
                }
            }
            this.mIsRecording = z;
            Iterator<RecordingStateChangeCallback> it = this.mListeners.iterator();
            while (it.hasNext()) {
                RecordingStateChangeCallback next = it.next();
                if (z) {
                    next.onRecordingStart();
                } else {
                    next.onRecordingEnd();
                }
            }
        }
    }
}