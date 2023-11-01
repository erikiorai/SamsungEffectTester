package com.android.systemui.screenshot;

import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionIntentExecutorKt.class */
public final class ActionIntentExecutorKt {
    public static final IRemoteAnimationRunner.Stub SCREENSHOT_REMOTE_RUNNER = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.screenshot.ActionIntentExecutorKt$SCREENSHOT_REMOTE_RUNNER$1
        public void onAnimationCancelled(boolean z) {
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                Log.e("ActionIntentExecutor", "Error finishing screenshot remote animation", e);
            }
        }
    };
}