package com.android.systemui.screenshot;

import android.app.ActivityOptions;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/SmartActionsReceiver.class */
public class SmartActionsReceiver extends BroadcastReceiver {
    public final ScreenshotSmartActions mScreenshotSmartActions;

    public SmartActionsReceiver(ScreenshotSmartActions screenshotSmartActions) {
        this.mScreenshotSmartActions = screenshotSmartActions;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        PendingIntent pendingIntent = (PendingIntent) intent.getParcelableExtra("android:screenshot_action_intent");
        String stringExtra = intent.getStringExtra("android:screenshot_action_type");
        try {
            pendingIntent.send(context, 0, null, null, null, null, ActivityOptions.makeBasic().toBundle());
        } catch (PendingIntent.CanceledException e) {
            Log.e("SmartActionsReceiver", "Pending intent canceled", e);
        }
        this.mScreenshotSmartActions.notifyScreenshotAction(intent.getStringExtra("android:screenshot_id"), stringExtra, true, pendingIntent.getIntent());
    }
}