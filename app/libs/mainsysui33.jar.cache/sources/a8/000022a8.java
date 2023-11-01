package com.android.systemui.qs.tiles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SubscriptionManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.Prefs;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SignalTileView;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CellularTile.class */
public class CellularTile extends SecureQSTile<QSTile.SignalState> {
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final KeyguardStateController mKeyguard;
    public final CellSignalCallback mSignalCallback;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CellularTile$CallbackInfo.class */
    public static final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean airplaneModeEnabled;
        public CharSequence dataContentDescription;
        public CharSequence dataSubscriptionName;
        public boolean multipleSubs;
        public boolean noSim;
        public boolean roaming;

        public CallbackInfo() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/CellularTile$CellSignalCallback.class */
    public final class CellSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo;

        public CellSignalCallback() {
            CellularTile.this = r6;
            this.mInfo = new CallbackInfo();
        }

        public void setIsAirplaneMode(IconState iconState) {
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.airplaneModeEnabled = iconState.visible;
            CellularTile.this.refreshState(callbackInfo);
        }

        public void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
            if (mobileDataIndicators.qsIcon == null) {
                return;
            }
            this.mInfo.dataSubscriptionName = CellularTile.this.mController.getMobileDataNetworkName();
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.dataContentDescription = mobileDataIndicators.qsDescription != null ? mobileDataIndicators.typeContentDescriptionHtml : null;
            callbackInfo.activityIn = mobileDataIndicators.activityIn;
            callbackInfo.activityOut = mobileDataIndicators.activityOut;
            callbackInfo.roaming = mobileDataIndicators.roaming;
            boolean z = true;
            if (CellularTile.this.mController.getNumberSubscriptions() <= 1) {
                z = false;
            }
            callbackInfo.multipleSubs = z;
            CellularTile.this.refreshState(this.mInfo);
        }

        public void setNoSims(boolean z, boolean z2) {
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.noSim = z;
            CellularTile.this.refreshState(callbackInfo);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.CellularTile$$ExternalSyntheticLambda0.onClick(android.content.DialogInterface, int):void] */
    /* renamed from: $r8$lambda$nDAciXoOl8-xp69YXZpbet2kmF0 */
    public static /* synthetic */ void m4001$r8$lambda$nDAciXoOl8xp69YXZpbet2kmF0(CellularTile cellularTile, DialogInterface dialogInterface, int i) {
        cellularTile.lambda$maybeShowDisableDialog$0(dialogInterface, i);
    }

    public CellularTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController);
        CellSignalCallback cellSignalCallback = new CellSignalCallback();
        this.mSignalCallback = cellSignalCallback;
        this.mController = networkController;
        this.mKeyguard = keyguardStateController;
        this.mDataController = networkController.getMobileDataController();
        networkController.observe(getLifecycle(), cellSignalCallback);
    }

    public static Intent getCellularSettingIntent() {
        Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        if (SubscriptionManager.getDefaultDataSubscriptionId() != -1) {
            intent.putExtra("android.provider.extra.SUB_ID", SubscriptionManager.getDefaultDataSubscriptionId());
        }
        return intent;
    }

    public /* synthetic */ void lambda$maybeShowDisableDialog$0(DialogInterface dialogInterface, int i) {
        this.mDataController.setMobileDataEnabled(false);
        Prefs.putBoolean(this.mContext, "QsHasTurnedOffMobileData", true);
    }

    public final CharSequence appendMobileDataType(CharSequence charSequence, CharSequence charSequence2) {
        return TextUtils.isEmpty(charSequence2) ? Html.fromHtml(charSequence.toString(), 0) : TextUtils.isEmpty(charSequence) ? Html.fromHtml(charSequence2.toString(), 0) : Html.fromHtml(this.mContext.getString(R$string.mobile_carrier_text_format, charSequence, charSequence2), 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public QSIconView createTileView(Context context) {
        return new SignalTileView(context);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return ((QSTile.SignalState) getState()).state == 0 ? new Intent("android.settings.WIRELESS_SETTINGS") : getCellularSettingIntent();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 115;
    }

    public final CharSequence getMobileDataContentName(CallbackInfo callbackInfo) {
        if (!callbackInfo.roaming || TextUtils.isEmpty(callbackInfo.dataContentDescription)) {
            return callbackInfo.roaming ? this.mContext.getString(R$string.data_connection_roaming) : callbackInfo.dataContentDescription;
        }
        return this.mContext.getString(R$string.mobile_data_text_format, this.mContext.getString(R$string.data_connection_roaming), callbackInfo.dataContentDescription.toString());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_cellular_detail_title);
    }

    @Override // com.android.systemui.qs.tiles.SecureQSTile
    public void handleClick(View view, boolean z) {
        if (checkKeyguard(view, z) || ((QSTile.SignalState) getState()).state == 0) {
            return;
        }
        if (this.mDataController.isMobileDataEnabled()) {
            maybeShowDisableDialog();
        } else {
            this.mDataController.setMobileDataEnabled(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSecondaryClick(View view) {
        handleLongClick(view);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState signalState, Object obj) {
        CallbackInfo callbackInfo = (CallbackInfo) obj;
        CallbackInfo callbackInfo2 = callbackInfo;
        if (callbackInfo == null) {
            callbackInfo2 = this.mSignalCallback.mInfo;
        }
        Resources resources = this.mContext.getResources();
        signalState.label = resources.getString(R$string.mobile_data);
        boolean z = this.mDataController.isMobileDataSupported() && this.mDataController.isMobileDataEnabled();
        signalState.value = z;
        signalState.activityIn = z && callbackInfo2.activityIn;
        signalState.activityOut = z && callbackInfo2.activityOut;
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        if (callbackInfo2.noSim) {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_sim);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_swap_vert);
        }
        if (callbackInfo2.noSim) {
            signalState.state = 0;
            signalState.secondaryLabel = resources.getString(R$string.keyguard_missing_sim_message_short);
        } else if (callbackInfo2.airplaneModeEnabled) {
            signalState.state = 0;
            signalState.secondaryLabel = resources.getString(R$string.status_bar_airplane);
        } else if (z) {
            signalState.state = 2;
            signalState.secondaryLabel = appendMobileDataType(callbackInfo2.multipleSubs ? callbackInfo2.dataSubscriptionName : "", getMobileDataContentName(callbackInfo2));
        } else {
            signalState.state = 1;
            signalState.secondaryLabel = resources.getString(R$string.cell_data_off);
        }
        signalState.contentDescription = signalState.label;
        if (signalState.state == 1) {
            signalState.stateDescription = "";
        } else {
            signalState.stateDescription = signalState.secondaryLabel;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mController.hasMobileDataFeature() && this.mHost.getUserContext().getUserId() == 0;
    }

    public final void maybeShowDisableDialog() {
        if (Prefs.getBoolean(this.mContext, "QsHasTurnedOffMobileData", false)) {
            this.mDataController.setMobileDataEnabled(false);
            return;
        }
        String mobileDataNetworkName = this.mController.getMobileDataNetworkName();
        boolean isMobileDataNetworkInService = this.mController.isMobileDataNetworkInService();
        if (TextUtils.isEmpty(mobileDataNetworkName) || !isMobileDataNetworkInService) {
            mobileDataNetworkName = this.mContext.getString(R$string.mobile_data_disable_message_default_carrier);
        }
        AlertDialog create = new AlertDialog.Builder(this.mContext).setTitle(R$string.mobile_data_disable_title).setMessage(this.mContext.getString(R$string.mobile_data_disable_message, mobileDataNetworkName)).setNegativeButton(17039360, (DialogInterface.OnClickListener) null).setPositiveButton(17039669, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.CellularTile$$ExternalSyntheticLambda0
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CellularTile.m4001$r8$lambda$nDAciXoOl8xp69YXZpbet2kmF0(CellularTile.this, dialogInterface, i);
            }
        }).create();
        create.getWindow().setType(2009);
        SystemUIDialog.setShowForAllUsers(create, true);
        SystemUIDialog.registerDismissListener(create);
        SystemUIDialog.setWindowOnTop(create, this.mKeyguard.isShowing());
        create.show();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }
}