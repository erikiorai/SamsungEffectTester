package com.android.systemui.bluetooth;

import android.content.Context;
import android.view.View;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BroadcastDialogController.class */
public class BroadcastDialogController {
    public Context mContext;
    public DialogLaunchAnimator mDialogLaunchAnimator;
    public MediaOutputDialogFactory mMediaOutputDialogFactory;
    public UiEventLogger mUiEventLogger;

    public BroadcastDialogController(Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, MediaOutputDialogFactory mediaOutputDialogFactory) {
        this.mContext = context;
        this.mUiEventLogger = uiEventLogger;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
    }

    public void createBroadcastDialog(String str, String str2, boolean z, View view) {
        SystemUIDialog broadcastDialog = new BroadcastDialog(this.mContext, this.mMediaOutputDialogFactory, str, str2, this.mUiEventLogger);
        if (view != null) {
            this.mDialogLaunchAnimator.showFromView(broadcastDialog, view);
        } else {
            broadcastDialog.show();
        }
    }
}