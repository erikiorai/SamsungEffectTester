package com.android.systemui.qs.tiles;

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
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/LocationTile.class */
public class LocationTile extends QSTileImpl<QSTile.BooleanState> {
    public final Callback mCallback;
    public final LocationController mController;
    public final KeyguardStateController mKeyguard;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/LocationTile$Callback.class */
    public final class Callback implements LocationController.LocationChangeCallback, KeyguardStateController.Callback {
        public Callback() {
            LocationTile.this = r4;
        }

        public void onKeyguardShowingChanged() {
            LocationTile.this.refreshState();
        }

        public void onLocationSettingsChanged(boolean z) {
            LocationTile.this.refreshState();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$-BbB0UxWWDbN_6ZiK8QRFH0R93I */
    public static /* synthetic */ void m4027$r8$lambda$BbB0UxWWDbN_6ZiK8QRFH0R93I(LocationTile locationTile) {
        locationTile.lambda$handleClick$0();
    }

    public LocationTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        Callback callback = new Callback();
        this.mCallback = callback;
        this.mController = locationController;
        this.mKeyguard = keyguardStateController;
        locationController.observe(this, callback);
        keyguardStateController.observe(this, callback);
    }

    public /* synthetic */ void lambda$handleClick$0() {
        boolean z = ((QSTile.BooleanState) this.mState).value;
        this.mHost.openPanels();
        this.mController.setLocationEnabled(!z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 122;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_location_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (this.mKeyguard.isMethodSecure() && this.mKeyguard.isShowing()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.LocationTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    LocationTile.m4027$r8$lambda$BbB0UxWWDbN_6ZiK8QRFH0R93I(LocationTile.this);
                }
            });
            return;
        }
        this.mController.setLocationEnabled(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        booleanState.value = this.mController.isLocationEnabled();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_share_location");
        if (!booleanState.disabledByPolicy) {
            checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_location");
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_location_icon_on : R$drawable.qs_location_icon_off);
        String string = this.mContext.getString(R$string.quick_settings_location_label);
        booleanState.label = string;
        booleanState.contentDescription = string;
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}