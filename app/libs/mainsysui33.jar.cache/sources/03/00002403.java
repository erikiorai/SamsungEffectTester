package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.RemoteAnimationAdapter;
import android.view.WindowManagerGlobal;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ActionProxyReceiver.class */
public class ActionProxyReceiver extends BroadcastReceiver {
    public final ActivityManagerWrapper mActivityManagerWrapper;
    public final CentralSurfaces mCentralSurfaces;
    public final ScreenshotSmartActions mScreenshotSmartActions;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ActionProxyReceiver$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$ukuT0qmNYLdDpxZysWn0ab2Dw18(ActionProxyReceiver actionProxyReceiver, Intent intent, Context context) {
        actionProxyReceiver.lambda$onReceive$0(intent, context);
    }

    public ActionProxyReceiver(Optional<CentralSurfaces> optional, ActivityManagerWrapper activityManagerWrapper, ScreenshotSmartActions screenshotSmartActions) {
        this.mCentralSurfaces = optional.orElse(null);
        this.mActivityManagerWrapper = activityManagerWrapper;
        this.mScreenshotSmartActions = screenshotSmartActions;
    }

    public /* synthetic */ void lambda$onReceive$0(Intent intent, Context context) {
        this.mActivityManagerWrapper.closeSystemWindows("screenshot");
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android:screenshot_action_intent");
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        makeBasic.setDisallowEnterPictureInPictureWhileLaunching(intent.getBooleanExtra("android:screenshot_disallow_enter_pip", false));
        try {
            pendingIntent.send(context, 0, null, null, null, null, makeBasic.toBundle());
            if (intent.getBooleanExtra("android:screenshot_override_transition", false)) {
                try {
                    WindowManagerGlobal.getWindowManagerService().overridePendingAppTransitionRemote(new RemoteAnimationAdapter(ScreenshotController.SCREENSHOT_REMOTE_RUNNER, 0L, 0L), 0);
                } catch (Exception e) {
                    Log.e("ActionProxyReceiver", "Error overriding screenshot app transition", e);
                }
            }
        } catch (PendingIntent.CanceledException e2) {
            Log.e("ActionProxyReceiver", "Pending intent canceled", e2);
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, final Intent intent) {
        Runnable runnable = new Runnable() { // from class: com.android.systemui.screenshot.ActionProxyReceiver$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ActionProxyReceiver.$r8$lambda$ukuT0qmNYLdDpxZysWn0ab2Dw18(ActionProxyReceiver.this, intent, context);
            }
        };
        CentralSurfaces centralSurfaces = this.mCentralSurfaces;
        if (centralSurfaces != null) {
            centralSurfaces.executeRunnableDismissingKeyguard(runnable, (Runnable) null, true, true, true);
        } else {
            runnable.run();
        }
        if (intent.getBooleanExtra("android:smart_actions_enabled", false)) {
            String action = intent.getAction();
            this.mScreenshotSmartActions.notifyScreenshotAction(intent.getStringExtra("android:screenshot_id"), "android.intent.action.VIEW".equals(action) ? "View" : "android.intent.action.EDIT".equals(action) ? "Edit" : "Share", false, null);
        }
    }
}