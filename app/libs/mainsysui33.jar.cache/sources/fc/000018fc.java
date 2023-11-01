package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.ProfilerInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/WorkLockActivityController.class */
public class WorkLockActivityController {
    public static final String TAG = "WorkLockActivityController";
    public final Context mContext;
    public final IActivityTaskManager mIatm;
    public final TaskStackChangeListener mLockListener;

    public WorkLockActivityController(Context context) {
        this(context, TaskStackChangeListeners.getInstance(), ActivityTaskManager.getService());
    }

    @VisibleForTesting
    public WorkLockActivityController(Context context, TaskStackChangeListeners taskStackChangeListeners, IActivityTaskManager iActivityTaskManager) {
        TaskStackChangeListener taskStackChangeListener = new TaskStackChangeListener() { // from class: com.android.systemui.keyguard.WorkLockActivityController.1
            public void onTaskProfileLocked(ActivityManager.RunningTaskInfo runningTaskInfo) {
                WorkLockActivityController.this.startWorkChallengeInTask(runningTaskInfo);
            }
        };
        this.mLockListener = taskStackChangeListener;
        this.mContext = context;
        this.mIatm = iActivityTaskManager;
        taskStackChangeListeners.registerTaskStackListener(taskStackChangeListener);
    }

    public final int startActivityAsUser(Intent intent, Bundle bundle, int i) {
        try {
            return this.mIatm.startActivityAsUser(this.mContext.getIApplicationThread(), this.mContext.getBasePackageName(), this.mContext.getAttributionTag(), intent, intent.resolveTypeIfNeeded(this.mContext.getContentResolver()), (IBinder) null, (String) null, 0, 268435456, (ProfilerInfo) null, bundle, i);
        } catch (RemoteException | Exception e) {
            return -96;
        }
    }

    public final void startWorkChallengeInTask(ActivityManager.RunningTaskInfo runningTaskInfo) {
        ComponentName componentName = runningTaskInfo.baseActivity;
        Intent addFlags = new Intent("android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER").setComponent(new ComponentName(this.mContext, WorkLockActivity.class)).putExtra("android.intent.extra.USER_ID", runningTaskInfo.userId).putExtra("android.intent.extra.PACKAGE_NAME", componentName != null ? componentName.getPackageName() : "").addFlags(67239936);
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setLaunchTaskId(runningTaskInfo.taskId);
        makeBasic.setTaskOverlay(true, false);
        if (ActivityManager.isStartResultSuccessful(startActivityAsUser(addFlags, makeBasic.toBundle(), -2))) {
            return;
        }
        try {
            this.mIatm.removeTask(runningTaskInfo.taskId);
        } catch (RemoteException e) {
            String str = TAG;
            Log.w(str, "Failed to get description for task=" + runningTaskInfo.taskId);
        }
    }
}