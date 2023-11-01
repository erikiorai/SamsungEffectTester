package com.android.systemui.media.dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialogReceiver.class */
public final class MediaOutputDialogReceiver extends BroadcastReceiver {
    public final MediaOutputBroadcastDialogFactory mediaOutputBroadcastDialogFactory;
    public final MediaOutputDialogFactory mediaOutputDialogFactory;

    public MediaOutputDialogReceiver(MediaOutputDialogFactory mediaOutputDialogFactory, MediaOutputBroadcastDialogFactory mediaOutputBroadcastDialogFactory) {
        this.mediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mediaOutputBroadcastDialogFactory = mediaOutputBroadcastDialogFactory;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        boolean z;
        boolean z2;
        if (TextUtils.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_DIALOG", intent.getAction())) {
            String stringExtra = intent.getStringExtra("package_name");
            if (!TextUtils.isEmpty(stringExtra)) {
                MediaOutputDialogFactory mediaOutputDialogFactory = this.mediaOutputDialogFactory;
                Intrinsics.checkNotNull(stringExtra);
                MediaOutputDialogFactory.create$default(mediaOutputDialogFactory, stringExtra, false, null, 4, null);
                return;
            }
            z2 = MediaOutputDialogReceiverKt.DEBUG;
            if (z2) {
                Log.e("MediaOutputDlgReceiver", "Unable to launch media output dialog. Package name is empty.");
            }
        } else if (TextUtils.equals("com.android.systemui.action.LAUNCH_MEDIA_OUTPUT_BROADCAST_DIALOG", intent.getAction())) {
            String stringExtra2 = intent.getStringExtra("package_name");
            if (!TextUtils.isEmpty(stringExtra2)) {
                MediaOutputBroadcastDialogFactory mediaOutputBroadcastDialogFactory = this.mediaOutputBroadcastDialogFactory;
                Intrinsics.checkNotNull(stringExtra2);
                MediaOutputBroadcastDialogFactory.create$default(mediaOutputBroadcastDialogFactory, stringExtra2, false, null, 4, null);
                return;
            }
            z = MediaOutputDialogReceiverKt.DEBUG;
            if (z) {
                Log.e("MediaOutputDlgReceiver", "Unable to launch media output broadcast dialog. Package name is empty.");
            }
        }
    }
}