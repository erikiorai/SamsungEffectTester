package com.android.systemui.bluetooth;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.media.controls.util.MediaDataUtils;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BroadcastDialog.class */
public class BroadcastDialog extends SystemUIDialog {
    public static final boolean DEBUG = Log.isLoggable("BroadcastDialog", 3);
    public Context mContext;
    @VisibleForTesting
    public View mDialogView;
    public MediaOutputDialogFactory mMediaOutputDialogFactory;
    public String mOutputPackageName;
    public String mSwitchBroadcastApp;
    public UiEventLogger mUiEventLogger;

    /* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BroadcastDialog$BroadcastDialogEvent.class */
    public enum BroadcastDialogEvent implements UiEventLogger.UiEventEnum {
        BROADCAST_DIALOG_SHOW(1062);
        
        private final int mId;

        BroadcastDialogEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.bluetooth.BroadcastDialog$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$0X_YKKTMI3xB3ojjr2zELkzqOAc(BroadcastDialog broadcastDialog, View view) {
        broadcastDialog.lambda$onCreate$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.bluetooth.BroadcastDialog$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$0w_7AfpOXb8MSQNGnwv0h8spybs(BroadcastDialog broadcastDialog, View view) {
        broadcastDialog.lambda$onCreate$1(view);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.bluetooth.BroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public BroadcastDialog(Context context, MediaOutputDialogFactory mediaOutputDialogFactory, String str, String str2, UiEventLogger uiEventLogger) {
        super(context);
        if (DEBUG) {
            Log.d("BroadcastDialog", "Init BroadcastDialog");
        }
        this.mContext = getContext();
        this.mMediaOutputDialogFactory = mediaOutputDialogFactory;
        this.mSwitchBroadcastApp = str;
        this.mOutputPackageName = str2;
        this.mUiEventLogger = uiEventLogger;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.bluetooth.BroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        this.mMediaOutputDialogFactory.create(this.mOutputPackageName, true, null);
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.bluetooth.BroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public /* synthetic */ void lambda$onCreate$1(View view) {
        if (DEBUG) {
            Log.d("BroadcastDialog", "BroadcastDialog dismiss.");
        }
        dismiss();
    }

    /* JADX DEBUG: Multi-variable search result rejected for r11v0, resolved type: com.android.systemui.bluetooth.BroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (DEBUG) {
            Log.d("BroadcastDialog", "onCreate");
        }
        this.mUiEventLogger.log(BroadcastDialogEvent.BROADCAST_DIALOG_SHOW);
        this.mDialogView = LayoutInflater.from(this.mContext).inflate(R$layout.broadcast_dialog, (ViewGroup) null);
        getWindow().setContentView(this.mDialogView);
        TextView textView = (TextView) this.mDialogView.requireViewById(R$id.dialog_title);
        TextView textView2 = (TextView) this.mDialogView.requireViewById(R$id.dialog_subtitle);
        Context context = this.mContext;
        textView.setText(context.getString(R$string.bt_le_audio_broadcast_dialog_title, MediaDataUtils.getAppLabel(context, this.mOutputPackageName, context.getString(R$string.bt_le_audio_broadcast_dialog_unknown_name))));
        textView2.setText(this.mContext.getString(R$string.bt_le_audio_broadcast_dialog_sub_title, this.mSwitchBroadcastApp));
        Button button = (Button) this.mDialogView.requireViewById(R$id.switch_broadcast);
        Button button2 = (Button) this.mDialogView.requireViewById(R$id.change_output);
        button.setText(this.mContext.getString(R$string.bt_le_audio_broadcast_dialog_switch_app, this.mSwitchBroadcastApp), (TextView.BufferType) null);
        button2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.bluetooth.BroadcastDialog$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BroadcastDialog.$r8$lambda$0X_YKKTMI3xB3ojjr2zELkzqOAc(BroadcastDialog.this, view);
            }
        });
        ((Button) this.mDialogView.requireViewById(R$id.cancel)).setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.bluetooth.BroadcastDialog$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BroadcastDialog.$r8$lambda$0w_7AfpOXb8MSQNGnwv0h8spybs(BroadcastDialog.this, view);
            }
        });
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.bluetooth.BroadcastDialog */
    /* JADX WARN: Multi-variable type inference failed */
    public void onWindowFocusChanged(boolean z) {
        super/*android.app.AlertDialog*/.onWindowFocusChanged(z);
        if (z || !isShowing()) {
            return;
        }
        dismiss();
    }
}