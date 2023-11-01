package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.net.VpnConfig;
import com.android.internal.net.VpnProfile;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.SecurityController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/VpnTile.class */
public class VpnTile extends QSTileImpl<QSTile.BooleanState> {
    public final Callback mCallback;
    public final SecurityController mController;
    public final KeyguardStateController mKeyguard;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/VpnTile$Callback.class */
    public final class Callback implements SecurityController.SecurityControllerCallback, KeyguardStateController.Callback {
        public Callback() {
            VpnTile.this = r4;
        }

        public void onKeyguardShowingChanged() {
            VpnTile.this.refreshState();
        }

        public void onStateChanged() {
            VpnTile.this.refreshState();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/VpnTile$UsernameAndPasswordWatcher.class */
    public static final class UsernameAndPasswordWatcher implements TextWatcher {
        public final Button mOkButton;
        public final EditText mPasswordEditor;
        public final EditText mUserNameEditor;

        public UsernameAndPasswordWatcher(EditText editText, EditText editText2, Button button) {
            this.mUserNameEditor = editText;
            this.mPasswordEditor = editText2;
            this.mOkButton = button;
            updateOkButtonState();
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            updateOkButtonState();
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public final void updateOkButtonState() {
            this.mOkButton.setEnabled(this.mUserNameEditor.getText().length() > 0 && this.mPasswordEditor.getText().length() > 0);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$4MAr17lY8rLioirmqSWyXVAEhAY(VpnTile vpnTile, List list, List list2) {
        vpnTile.lambda$showConnectDialogOrDisconnect$2(list, list2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda4.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$8fiihrIK8LC3ZRLl311Z_BSjvS8(VpnTile vpnTile, int i, List list, List list2, DialogInterface dialogInterface, int i2) {
        vpnTile.lambda$showConnectDialogOrDisconnect$1(i, list, list2, dialogInterface, i2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$A0PLUlFDuL5t3LFj5T_R62rJLYM(VpnTile vpnTile) {
        vpnTile.lambda$handleClick$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$cH4nNNXmHL1V5EBVeMJMC9qDf1w(EditText editText, EditText editText2, AlertDialog alertDialog) {
        lambda$connectVpnOrAskForCredentials$4(editText, editText2, alertDialog);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda2.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$iL6cu8mKMDsgcztdAW20Tpcnr0s(VpnTile vpnTile, VpnProfile vpnProfile, EditText editText, EditText editText2, DialogInterface dialogInterface, int i) {
        vpnTile.lambda$connectVpnOrAskForCredentials$3(vpnProfile, editText, editText2, dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$o4H5uVnsSl6HmtY4x8FjVdmDVhM(Dialog dialog) {
        dialog.show();
    }

    public VpnTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, SecurityController securityController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mCallback = new Callback();
        this.mController = securityController;
        this.mKeyguard = keyguardStateController;
    }

    public /* synthetic */ void lambda$connectVpnOrAskForCredentials$3(VpnProfile vpnProfile, EditText editText, EditText editText2, DialogInterface dialogInterface, int i) {
        vpnProfile.username = editText.getText().toString();
        vpnProfile.password = editText2.getText().toString();
        this.mController.connectLegacyVpn(vpnProfile);
    }

    public static /* synthetic */ void lambda$connectVpnOrAskForCredentials$4(EditText editText, EditText editText2, AlertDialog alertDialog) {
        UsernameAndPasswordWatcher usernameAndPasswordWatcher = new UsernameAndPasswordWatcher(editText, editText2, alertDialog.getButton(-1));
        editText.addTextChangedListener(usernameAndPasswordWatcher);
        editText2.addTextChangedListener(usernameAndPasswordWatcher);
    }

    public /* synthetic */ void lambda$showConnectDialogOrDisconnect$1(int i, List list, List list2, DialogInterface dialogInterface, int i2) {
        if (i2 < i) {
            connectVpnOrAskForCredentials((VpnProfile) list.get(i2));
        } else {
            this.mController.launchVpnApp((String) list2.get(i2 - i));
        }
    }

    public /* synthetic */ void lambda$showConnectDialogOrDisconnect$2(final List list, final List list2) {
        int i;
        CharSequence[] charSequenceArr = new CharSequence[list.size() + list2.size()];
        final int size = list.size();
        int i2 = 0;
        while (true) {
            if (i2 >= size) {
                break;
            }
            charSequenceArr[i2] = ((VpnProfile) list.get(i2)).name;
            i2++;
        }
        for (i = 0; i < list2.size(); i++) {
            int i3 = size + i;
            try {
                charSequenceArr[i3] = VpnConfig.getVpnLabel(this.mContext, (String) list2.get(i));
            } catch (PackageManager.NameNotFoundException e) {
                charSequenceArr[i3] = (CharSequence) list2.get(i);
            }
        }
        prepareAndShowDialog(new AlertDialog.Builder(this.mContext).setTitle(R$string.quick_settings_vpn_connect_dialog_title).setItems(charSequenceArr, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i4) {
                VpnTile.$r8$lambda$8fiihrIK8LC3ZRLl311Z_BSjvS8(VpnTile.this, size, list, list2, dialogInterface, i4);
            }
        }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create());
        this.mHost.collapsePanels();
    }

    public final void connectVpnOrAskForCredentials(final VpnProfile vpnProfile) {
        if (vpnProfile.saveLogin) {
            this.mController.connectLegacyVpn(vpnProfile);
            return;
        }
        View inflate = LayoutInflater.from(this.mContext).inflate(R$layout.vpn_credentials_dialog, (ViewGroup) null);
        final EditText editText = (EditText) inflate.findViewById(R$id.username);
        final EditText editText2 = (EditText) inflate.findViewById(R$id.password);
        ((TextView) inflate.findViewById(R$id.hint)).setText(this.mContext.getString(R$string.vpn_credentials_hint, vpnProfile.name));
        final AlertDialog create = new AlertDialog.Builder(this.mContext).setView(inflate).setPositiveButton(R$string.vpn_credentials_dialog_connect, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                VpnTile.$r8$lambda$iL6cu8mKMDsgcztdAW20Tpcnr0s(VpnTile.this, vpnProfile, editText, editText2, dialogInterface, i);
            }
        }).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).create();
        prepareAndShowDialog(create);
        this.mHost.collapsePanels();
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                VpnTile.$r8$lambda$cH4nNNXmHL1V5EBVeMJMC9qDf1w(editText, editText2, create);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.VPN_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 100;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_vpn_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (!this.mKeyguard.isMethodSecure() || this.mKeyguard.canDismissLockScreen()) {
            lambda$handleClick$0();
        } else {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    VpnTile.$r8$lambda$A0PLUlFDuL5t3LFj5T_R62rJLYM(VpnTile.this);
                }
            });
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        if (QSTileImpl.DEBUG) {
            String str = this.TAG;
            Log.d(str, "handleSetListening " + z);
        }
        if (z) {
            this.mController.addCallback(this.mCallback);
            this.mKeyguard.addCallback(this.mCallback);
            return;
        }
        this.mController.removeCallback(this.mCallback);
        this.mKeyguard.removeCallback(this.mCallback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.label = this.mContext.getString(R$string.quick_settings_vpn_label);
        booleanState.value = this.mController.isVpnEnabled();
        booleanState.secondaryLabel = this.mController.getPrimaryVpnName();
        booleanState.contentDescription = booleanState.label;
        booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_vpn);
        boolean z = this.mController.getConfiguredLegacyVpns().size() > 0 || this.mController.getVpnAppPackageNames().size() > 0;
        if (this.mController.isVpnRestricted() || !z) {
            booleanState.state = 0;
        } else if (booleanState.value) {
            booleanState.state = 2;
        } else {
            booleanState.state = 1;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        super.handleUserSwitch(i);
        this.mController.onUserSwitched(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public final void prepareAndShowDialog(final Dialog dialog) {
        dialog.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(dialog, true);
        SystemUIDialog.registerDismissListener(dialog);
        SystemUIDialog.setWindowOnTop(dialog, this.mKeyguard.isShowing());
        this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                VpnTile.$r8$lambda$o4H5uVnsSl6HmtY4x8FjVdmDVhM(dialog);
            }
        });
    }

    /* renamed from: showConnectDialogOrDisconnect */
    public final void lambda$handleClick$0() {
        if (this.mController.isVpnRestricted()) {
            return;
        }
        if (this.mController.isVpnEnabled()) {
            this.mController.disconnectPrimaryVpn();
            return;
        }
        final List configuredLegacyVpns = this.mController.getConfiguredLegacyVpns();
        final List vpnAppPackageNames = this.mController.getVpnAppPackageNames();
        if (configuredLegacyVpns.isEmpty() && vpnAppPackageNames.isEmpty()) {
            return;
        }
        if (configuredLegacyVpns.isEmpty() && vpnAppPackageNames.size() == 1) {
            this.mController.launchVpnApp((String) vpnAppPackageNames.get(0));
        } else if (vpnAppPackageNames.isEmpty() && configuredLegacyVpns.size() == 1) {
            connectVpnOrAskForCredentials((VpnProfile) configuredLegacyVpns.get(0));
        } else {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.VpnTile$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    VpnTile.$r8$lambda$4MAr17lY8rLioirmqSWyXVAEhAY(VpnTile.this, configuredLegacyVpns, vpnAppPackageNames);
                }
            });
        }
    }
}