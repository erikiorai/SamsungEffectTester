package com.android.systemui.media.dialog;

import android.content.Context;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$dimen;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastSender;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialog.class */
public class MediaOutputDialog extends MediaOutputBaseDialog {
    public final UiEventLogger mUiEventLogger;

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialog$MediaOutputEvent.class */
    public enum MediaOutputEvent implements UiEventLogger.UiEventEnum {
        MEDIA_OUTPUT_DIALOG_SHOW(655);
        
        private final int mId;

        MediaOutputEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.media.dialog.MediaOutputDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaOutputDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController, UiEventLogger uiEventLogger) {
        super(context, broadcastSender, mediaOutputController);
        this.mUiEventLogger = uiEventLogger;
        this.mAdapter = new MediaOutputAdapter(this.mMediaOutputController);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public IconCompat getAppSourceIcon() {
        return this.mMediaOutputController.getNotificationSmallIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public int getBroadcastIconVisibility() {
        return (isBroadcastSupported() && this.mMediaOutputController.isBluetoothLeBroadcastEnabled()) ? 0 : 8;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public IconCompat getHeaderIcon() {
        return this.mMediaOutputController.getHeaderIcon();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public int getHeaderIconRes() {
        return 0;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public int getHeaderIconSize() {
        return this.mContext.getResources().getDimensionPixelSize(R$dimen.media_output_dialog_header_album_icon_size);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public CharSequence getHeaderSubtitle() {
        return this.mMediaOutputController.getHeaderSubTitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public CharSequence getHeaderText() {
        return this.mMediaOutputController.getHeaderTitle();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public CharSequence getStopButtonText() {
        int i = R$string.keyboard_key_media_stop;
        int i2 = i;
        if (isBroadcastSupported()) {
            i2 = i;
            if (this.mMediaOutputController.isPlaying()) {
                i2 = i;
                if (!this.mMediaOutputController.isBluetoothLeBroadcastEnabled()) {
                    i2 = R$string.media_output_broadcast;
                }
            }
        }
        return this.mContext.getText(i2);
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public int getStopButtonVisibility() {
        boolean z;
        if (this.mMediaOutputController.getCurrentConnectedMediaDevice() != null) {
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            z = mediaOutputController.isActiveRemoteDevice(mediaOutputController.getCurrentConnectedMediaDevice());
        } else {
            z = false;
        }
        boolean z2 = isBroadcastSupported() && this.mMediaOutputController.isPlaying();
        int i = 0;
        if (!z) {
            i = z2 ? 0 : 8;
        }
        return i;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public boolean isBroadcastSupported() {
        boolean z;
        if (this.mMediaOutputController.getCurrentConnectedMediaDevice() != null) {
            MediaOutputController mediaOutputController = this.mMediaOutputController;
            z = mediaOutputController.isBluetoothLeDevice(mediaOutputController.getCurrentConnectedMediaDevice());
        } else {
            z = false;
        }
        boolean z2 = false;
        if (this.mMediaOutputController.isBroadcastSupported()) {
            z2 = false;
            if (z) {
                z2 = true;
            }
        }
        return z2;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void onBroadcastIconClick() {
        startLeBroadcastDialog();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mUiEventLogger.log(MediaOutputEvent.MEDIA_OUTPUT_DIALOG_SHOW);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void onStopButtonClick() {
        if (!isBroadcastSupported() || !this.mMediaOutputController.isPlaying()) {
            this.mMediaOutputController.releaseSession();
            dismiss();
        } else if (this.mMediaOutputController.isBluetoothLeBroadcastEnabled()) {
            stopLeBroadcast();
        } else if (startLeBroadcastDialogForFirstTime()) {
        } else {
            startLeBroadcast();
        }
    }
}