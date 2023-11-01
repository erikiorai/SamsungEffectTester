package com.android.systemui.screenshot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/DeleteScreenshotReceiver.class */
public class DeleteScreenshotReceiver extends BroadcastReceiver {
    public final Executor mBackgroundExecutor;
    public final ScreenshotSmartActions mScreenshotSmartActions;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.DeleteScreenshotReceiver$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$CEZI5sqJSK54hW_kaFE5KM0z6bY(Context context, Uri uri) {
        lambda$onReceive$0(context, uri);
    }

    public DeleteScreenshotReceiver(ScreenshotSmartActions screenshotSmartActions, Executor executor) {
        this.mScreenshotSmartActions = screenshotSmartActions;
        this.mBackgroundExecutor = executor;
    }

    public static /* synthetic */ void lambda$onReceive$0(Context context, Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(final Context context, Intent intent) {
        if (intent.hasExtra("android:screenshot_uri_id")) {
            final Uri parse = Uri.parse(intent.getStringExtra("android:screenshot_uri_id"));
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.screenshot.DeleteScreenshotReceiver$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DeleteScreenshotReceiver.$r8$lambda$CEZI5sqJSK54hW_kaFE5KM0z6bY(context, parse);
                }
            });
            if (intent.getBooleanExtra("android:smart_actions_enabled", false)) {
                this.mScreenshotSmartActions.notifyScreenshotAction(intent.getStringExtra("android:screenshot_id"), "Delete", false, null);
            }
        }
    }
}