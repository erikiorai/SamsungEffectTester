package com.android.systemui.qs.tiles;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/WorkModeTile.class */
public class WorkModeTile extends QSTileImpl<QSTile.BooleanState> implements ManagedProfileController.Callback {
    public final QSTile.Icon mIcon;
    public final ManagedProfileController mProfileController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.WorkModeTile$$ExternalSyntheticLambda0.get():java.lang.Object] */
    /* renamed from: $r8$lambda$vO_izBdu_fLg_-AS6PzSr7pcUww */
    public static /* synthetic */ String m4060$r8$lambda$vO_izBdu_fLg_AS6PzSr7pcUww(WorkModeTile workModeTile) {
        return workModeTile.lambda$getTileLabel$0();
    }

    public WorkModeTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ManagedProfileController managedProfileController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.stat_sys_managed_profile_status);
        this.mProfileController = managedProfileController;
        managedProfileController.observe(getLifecycle(), this);
    }

    public /* synthetic */ String lambda$getTileLabel$0() {
        return this.mContext.getString(R$string.quick_settings_work_mode_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.MANAGED_PROFILE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 257;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getResources().getString("SystemUi.QS_WORK_PROFILE_LABEL", new Supplier() { // from class: com.android.systemui.qs.tiles.WorkModeTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return WorkModeTile.m4060$r8$lambda$vO_izBdu_fLg_AS6PzSr7pcUww(WorkModeTile.this);
            }
        });
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        this.mProfileController.setWorkModeEnabled(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        if (!isAvailable()) {
            onManagedProfileRemoved();
        }
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        if (obj instanceof Boolean) {
            booleanState.value = ((Boolean) obj).booleanValue();
        } else {
            booleanState.value = this.mProfileController.isWorkModeEnabled();
        }
        booleanState.icon = this.mIcon;
        int i = 1;
        if (booleanState.value) {
            booleanState.slash.isSlashed = false;
        } else {
            booleanState.slash.isSlashed = true;
        }
        CharSequence tileLabel = getTileLabel();
        booleanState.label = tileLabel;
        booleanState.contentDescription = tileLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mProfileController.hasActiveProfile();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onManagedProfileChanged() {
        refreshState(Boolean.valueOf(this.mProfileController.isWorkModeEnabled()));
    }

    public void onManagedProfileRemoved() {
        this.mHost.removeTile(getTileSpec());
    }
}