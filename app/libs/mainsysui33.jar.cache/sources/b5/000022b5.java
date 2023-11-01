package com.android.systemui.qs.tiles;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.Prefs;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DataSaverController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/DataSaverTile.class */
public class DataSaverTile extends QSTileImpl<QSTile.BooleanState> implements DataSaverController.Listener {
    public final DataSaverController mDataSaverController;
    public final DialogLaunchAnimator mDialogLaunchAnimator;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda1.onClick(android.content.DialogInterface, int):void] */
    public static /* synthetic */ void $r8$lambda$OKU6RPuD9FufTCENoGUyTp1_vTg(DataSaverTile dataSaverTile, DialogInterface dialogInterface, int i) {
        dataSaverTile.lambda$handleClick$0(dialogInterface, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$sM2_jsFl18I2p9IXiyUQrFO-7ds */
    public static /* synthetic */ void m4007$r8$lambda$sM2_jsFl18I2p9IXiyUQrFO7ds(DataSaverTile dataSaverTile, View view) {
        dataSaverTile.lambda$handleClick$1(view);
    }

    public DataSaverTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, DataSaverController dataSaverController, DialogLaunchAnimator dialogLaunchAnimator) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mDataSaverController = dataSaverController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        dataSaverController.observe(getLifecycle(), this);
    }

    public /* synthetic */ void lambda$handleClick$0(DialogInterface dialogInterface, int i) {
        toggleDataSaver();
        Prefs.putBoolean(this.mContext, "QsDataSaverDialogShown", true);
    }

    public /* synthetic */ void lambda$handleClick$1(View view) {
        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext);
        systemUIDialog.setTitle(17040108);
        systemUIDialog.setMessage(17040106);
        systemUIDialog.setPositiveButton(17040107, new DialogInterface.OnClickListener() { // from class: com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                DataSaverTile.$r8$lambda$OKU6RPuD9FufTCENoGUyTp1_vTg(DataSaverTile.this, dialogInterface, i);
            }
        });
        systemUIDialog.setNeutralButton(17039360, (DialogInterface.OnClickListener) null);
        systemUIDialog.setShowForAllUsers(true);
        if (view != null) {
            this.mDialogLaunchAnimator.showFromView(systemUIDialog, view, new DialogCuj(58, "start_data_saver"));
        } else {
            systemUIDialog.show();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.DATA_SAVER_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 284;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.data_saver);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(final View view) {
        if (((QSTile.BooleanState) this.mState).value || Prefs.getBoolean(this.mContext, "QsDataSaverDialogShown", false)) {
            toggleDataSaver();
        } else {
            this.mUiHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.DataSaverTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DataSaverTile.m4007$r8$lambda$sM2_jsFl18I2p9IXiyUQrFO7ds(DataSaverTile.this, view);
                }
            });
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean booleanValue = obj instanceof Boolean ? ((Boolean) obj).booleanValue() : this.mDataSaverController.isDataSaverEnabled();
        booleanState.value = booleanValue;
        booleanState.state = booleanValue ? 2 : 1;
        String string = this.mContext.getString(R$string.data_saver);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_data_saver_icon_on : R$drawable.qs_data_saver_icon_off);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onDataSaverChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
    }

    public final void toggleDataSaver() {
        ((QSTile.BooleanState) this.mState).value = !this.mDataSaverController.isDataSaverEnabled();
        this.mDataSaverController.setDataSaverEnabled(((QSTile.BooleanState) this.mState).value);
        refreshState(Boolean.valueOf(((QSTile.BooleanState) this.mState).value));
    }
}