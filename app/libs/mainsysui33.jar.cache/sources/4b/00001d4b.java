package com.android.systemui.media.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.drawable.IconCompat;
import com.android.settingslib.qrcode.QrCodeGenerator;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.dialog.MediaOutputController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.google.zxing.WriterException;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputBroadcastDialog.class */
public class MediaOutputBroadcastDialog extends MediaOutputBaseDialog {
    public AlertDialog mAlertDialog;
    public TextView mBroadcastCode;
    public ImageView mBroadcastCodeEdit;
    public ImageView mBroadcastCodeEye;
    public TextView mBroadcastErrorMessage;
    public ViewStub mBroadcastInfoArea;
    public TextView mBroadcastName;
    public ImageView mBroadcastNameEdit;
    public ImageView mBroadcastNotify;
    public ImageView mBroadcastQrCodeView;
    public String mCurrentBroadcastCode;
    public String mCurrentBroadcastName;
    public Boolean mIsPasswordHide;
    public boolean mIsStopbyUpdateBroadcastCode;
    public int mRetryCount;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda4.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$2G0Zc5nKD9bIm-JsvqaUOPLLvII */
    public static /* synthetic */ void m3311$r8$lambda$2G0Zc5nKD9bImJsvqaUOPLLvII(MediaOutputBroadcastDialog mediaOutputBroadcastDialog, boolean z, EditText editText, DialogInterface dialogInterface, int i) {
        mediaOutputBroadcastDialog.lambda$launchBroadcastUpdatedDialog$4(z, editText, dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$BS6qAto5-IS1L1JEIV48vLGixto */
    public static /* synthetic */ void m3312$r8$lambda$BS6qAto5IS1L1JEIV48vLGixto(MediaOutputBroadcastDialog mediaOutputBroadcastDialog, View view) {
        mediaOutputBroadcastDialog.lambda$initBtQrCodeUI$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$DntFaepzv0SMimTezh04zrmM-Zw */
    public static /* synthetic */ void m3313$r8$lambda$DntFaepzv0SMimTezh04zrmMZw(MediaOutputBroadcastDialog mediaOutputBroadcastDialog, View view) {
        mediaOutputBroadcastDialog.lambda$initBtQrCodeUI$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$a3KvMq7414ypPsP-oyF_Dw4NxCA */
    public static /* synthetic */ void m3314$r8$lambda$a3KvMq7414ypPsPoyF_Dw4NxCA(MediaOutputBroadcastDialog mediaOutputBroadcastDialog, View view) {
        mediaOutputBroadcastDialog.lambda$initBtQrCodeUI$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$dZwwPfSVFbXDm3XUv_HtlYhumwA(MediaOutputBroadcastDialog mediaOutputBroadcastDialog, View view) {
        mediaOutputBroadcastDialog.lambda$initBtQrCodeUI$1(view);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.media.dialog.MediaOutputBroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public MediaOutputBroadcastDialog(Context context, boolean z, BroadcastSender broadcastSender, MediaOutputController mediaOutputController) {
        super(context, broadcastSender, mediaOutputController);
        this.mIsPasswordHide = Boolean.TRUE;
        this.mRetryCount = 0;
        this.mIsStopbyUpdateBroadcastCode = false;
        this.mAdapter = new MediaOutputAdapter(this.mMediaOutputController);
        if (z) {
            return;
        }
        getWindow().setType(2038);
    }

    public /* synthetic */ void lambda$initBtQrCodeUI$0(View view) {
        this.mMediaOutputController.launchLeBroadcastNotifyDialog(null, null, MediaOutputController.BroadcastNotifyDialog.ACTION_BROADCAST_INFO_ICON, null);
    }

    public /* synthetic */ void lambda$initBtQrCodeUI$1(View view) {
        launchBroadcastUpdatedDialog(false, this.mBroadcastName.getText().toString());
    }

    public /* synthetic */ void lambda$initBtQrCodeUI$2(View view) {
        updateBroadcastCodeVisibility();
    }

    public /* synthetic */ void lambda$initBtQrCodeUI$3(View view) {
        launchBroadcastUpdatedDialog(true, this.mBroadcastCode.getText().toString());
    }

    public /* synthetic */ void lambda$launchBroadcastUpdatedDialog$4(boolean z, EditText editText, DialogInterface dialogInterface, int i) {
        updateBroadcastInfo(z, editText.getText().toString());
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public IconCompat getAppSourceIcon() {
        return this.mMediaOutputController.getNotificationSmallIcon();
    }

    public final String getBroadcastMetadata() {
        return this.mMediaOutputController.getBroadcastMetadata();
    }

    public final String getBroadcastMetadataInfo(int i) {
        return i != 0 ? i != 1 ? "" : this.mMediaOutputController.getBroadcastCode() : this.mMediaOutputController.getBroadcastName();
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
    public int getStopButtonVisibility() {
        return 0;
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastMetadataChanged() {
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastStartFailed() {
        this.mMediaOutputController.setBroadcastCode(this.mCurrentBroadcastCode);
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastStarted() {
        this.mRetryCount = 0;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        refreshUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastStopFailed() {
        this.mMediaOutputController.setBroadcastCode(this.mCurrentBroadcastCode);
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.media.dialog.MediaOutputBroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastStopped() {
        if (!this.mIsStopbyUpdateBroadcastCode) {
            dismiss();
            return;
        }
        this.mIsStopbyUpdateBroadcastCode = false;
        this.mRetryCount = 0;
        if (this.mMediaOutputController.startBluetoothLeBroadcast()) {
            return;
        }
        handleLeBroadcastStartFailed();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastUpdateFailed() {
        this.mMediaOutputController.setBroadcastName(this.mCurrentBroadcastName);
        this.mRetryCount++;
        handleUpdateFailedUi();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void handleLeBroadcastUpdated() {
        this.mRetryCount = 0;
        AlertDialog alertDialog = this.mAlertDialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        refreshUi();
    }

    public final void handleUpdateFailedUi() {
        Button button = this.mAlertDialog.getButton(-1);
        this.mBroadcastErrorMessage.setVisibility(0);
        if (this.mRetryCount >= 3) {
            this.mRetryCount = 0;
            this.mBroadcastErrorMessage.setText(R$string.media_output_broadcast_last_update_error);
            return;
        }
        if (button != null) {
            button.setEnabled(true);
        }
        this.mBroadcastErrorMessage.setText(R$string.media_output_broadcast_update_error);
    }

    public final void inflateBroadcastInfoArea() {
        ViewStub viewStub = (ViewStub) getDialogView().requireViewById(R$id.broadcast_qrcode);
        this.mBroadcastInfoArea = viewStub;
        viewStub.inflate();
    }

    public final void initBtQrCodeUI() {
        inflateBroadcastInfoArea();
        this.mBroadcastQrCodeView = (ImageView) getDialogView().requireViewById(R$id.qrcode_view);
        ImageView imageView = (ImageView) getDialogView().requireViewById(R$id.broadcast_info);
        this.mBroadcastNotify = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBroadcastDialog.m3314$r8$lambda$a3KvMq7414ypPsPoyF_Dw4NxCA(MediaOutputBroadcastDialog.this, view);
            }
        });
        this.mBroadcastName = (TextView) getDialogView().requireViewById(R$id.broadcast_name_summary);
        ImageView imageView2 = (ImageView) getDialogView().requireViewById(R$id.broadcast_name_edit);
        this.mBroadcastNameEdit = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBroadcastDialog.$r8$lambda$dZwwPfSVFbXDm3XUv_HtlYhumwA(MediaOutputBroadcastDialog.this, view);
            }
        });
        TextView textView = (TextView) getDialogView().requireViewById(R$id.broadcast_code_summary);
        this.mBroadcastCode = textView;
        textView.setTransformationMethod(PasswordTransformationMethod.getInstance());
        ImageView imageView3 = (ImageView) getDialogView().requireViewById(R$id.broadcast_code_eye);
        this.mBroadcastCodeEye = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBroadcastDialog.m3312$r8$lambda$BS6qAto5IS1L1JEIV48vLGixto(MediaOutputBroadcastDialog.this, view);
            }
        });
        ImageView imageView4 = (ImageView) getDialogView().requireViewById(R$id.broadcast_code_edit);
        this.mBroadcastCodeEdit = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MediaOutputBroadcastDialog.m3313$r8$lambda$DntFaepzv0SMimTezh04zrmMZw(MediaOutputBroadcastDialog.this, view);
            }
        });
        refreshUi();
    }

    public final void launchBroadcastUpdatedDialog(final boolean z, String str) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R$layout.media_output_broadcast_update_dialog, (ViewGroup) null);
        final EditText editText = (EditText) inflate.requireViewById(R$id.broadcast_edit_text);
        editText.setText(str);
        this.mBroadcastErrorMessage = (TextView) inflate.requireViewById(R$id.broadcast_error_message);
        AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle(z ? R$string.media_output_broadcast_code : R$string.media_output_broadcast_name).setView(inflate).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton(R$string.media_output_broadcast_dialog_save, new DialogInterface.OnClickListener() { // from class: com.android.systemui.media.dialog.MediaOutputBroadcastDialog$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                MediaOutputBroadcastDialog.m3311$r8$lambda$2G0Zc5nKD9bImJsvqaUOPLLvII(MediaOutputBroadcastDialog.this, z, editText, dialogInterface, i);
            }
        }).create();
        this.mAlertDialog = create;
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(this.mAlertDialog, true);
        SystemUIDialog.registerDismissListener(this.mAlertDialog);
        this.mAlertDialog.show();
    }

    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        initBtQrCodeUI();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.media.dialog.MediaOutputBroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.media.dialog.MediaOutputBaseDialog
    public void onStopButtonClick() {
        this.mMediaOutputController.stopBluetoothLeBroadcast();
        dismiss();
    }

    public final void refreshUi() {
        setQrCodeView();
        this.mCurrentBroadcastName = getBroadcastMetadataInfo(0);
        this.mCurrentBroadcastCode = getBroadcastMetadataInfo(1);
        this.mBroadcastName.setText(this.mCurrentBroadcastName);
        this.mBroadcastCode.setText(this.mCurrentBroadcastCode);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.media.dialog.MediaOutputBroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public final void setQrCodeView() {
        String broadcastMetadata = getBroadcastMetadata();
        if (broadcastMetadata.isEmpty()) {
            return;
        }
        try {
            this.mBroadcastQrCodeView.setImageBitmap(QrCodeGenerator.encodeQrCode(broadcastMetadata, getContext().getResources().getDimensionPixelSize(R$dimen.media_output_qrcode_size)));
        } catch (WriterException e) {
            Log.e("BroadcastDialog", "Error generatirng QR code bitmap " + e);
        }
    }

    public final void updateBroadcastCodeVisibility() {
        this.mBroadcastCode.setTransformationMethod(this.mIsPasswordHide.booleanValue() ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
        this.mIsPasswordHide = Boolean.valueOf(!this.mIsPasswordHide.booleanValue());
    }

    public final void updateBroadcastInfo(boolean z, String str) {
        Button button = this.mAlertDialog.getButton(-1);
        if (button != null) {
            button.setEnabled(false);
        }
        if (!z) {
            this.mMediaOutputController.setBroadcastName(str);
            if (this.mMediaOutputController.updateBluetoothLeBroadcast()) {
                return;
            }
            handleLeBroadcastUpdateFailed();
            return;
        }
        this.mIsStopbyUpdateBroadcastCode = true;
        this.mMediaOutputController.setBroadcastCode(str);
        if (this.mMediaOutputController.stopBluetoothLeBroadcast()) {
            return;
        }
        handleLeBroadcastStopFailed();
    }
}