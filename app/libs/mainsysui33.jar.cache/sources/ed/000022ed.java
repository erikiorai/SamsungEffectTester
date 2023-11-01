package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.NightDisplayListener;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.dagger.NightDisplayListenerModule;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.LocationController;
import java.text.DateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/NightDisplayTile.class */
public class NightDisplayTile extends QSTileImpl<QSTile.BooleanState> implements NightDisplayListener.Callback {
    public boolean mIsListening;
    public NightDisplayListener mListener;
    public final LocationController mLocationController;
    public ColorDisplayManager mManager;
    public final NightDisplayListenerModule.Builder mNightDisplayListenerBuilder;

    public NightDisplayTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, LocationController locationController, ColorDisplayManager colorDisplayManager, NightDisplayListenerModule.Builder builder) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mLocationController = locationController;
        this.mManager = colorDisplayManager;
        this.mNightDisplayListenerBuilder = builder;
        this.mListener = builder.setUser(qSHost.getUserContext().getUserId()).build();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.NIGHT_DISPLAY_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 491;
    }

    public final String getSecondaryLabel(boolean z) {
        LocalTime nightDisplayCustomStartTime;
        int i;
        int nightDisplayAutoMode = this.mManager.getNightDisplayAutoMode();
        if (nightDisplayAutoMode != 1) {
            if (nightDisplayAutoMode == 2 && this.mLocationController.isLocationEnabled()) {
                return z ? this.mContext.getString(R$string.quick_settings_night_secondary_label_until_sunrise) : this.mContext.getString(R$string.quick_settings_night_secondary_label_on_at_sunset);
            }
            return null;
        }
        if (z) {
            nightDisplayCustomStartTime = this.mManager.getNightDisplayCustomEndTime();
            i = R$string.quick_settings_secondary_label_until;
        } else {
            nightDisplayCustomStartTime = this.mManager.getNightDisplayCustomStartTime();
            i = R$string.quick_settings_night_secondary_label_on_at;
        }
        Calendar calendar = Calendar.getInstance();
        DateFormat timeFormat = android.text.format.DateFormat.getTimeFormat(this.mContext);
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeZone(timeFormat.getTimeZone());
        calendar.set(11, nightDisplayCustomStartTime.getHour());
        calendar.set(12, nightDisplayCustomStartTime.getMinute());
        calendar.set(13, 0);
        calendar.set(14, 0);
        return this.mContext.getString(i, timeFormat.format(calendar.getTime()));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_night_display_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if ("1".equals(Settings.Global.getString(this.mContext.getContentResolver(), "night_display_forced_auto_mode_available")) && this.mManager.getNightDisplayAutoModeRaw() == -1) {
            this.mManager.setNightDisplayAutoMode(1);
            Log.i("NightDisplayTile", "Enrolled in forced night display auto mode");
        }
        this.mManager.setNightDisplayActivated(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mIsListening = z;
        if (!z) {
            this.mListener.setCallback((NightDisplayListener.Callback) null);
            return;
        }
        this.mListener.setCallback(this);
        refreshState();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.value = this.mManager.isNightDisplayActivated();
        booleanState.label = this.mContext.getString(R$string.quick_settings_night_display_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        boolean z = booleanState.value;
        booleanState.state = z ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(z ? R$drawable.qs_nightlight_icon_on : R$drawable.qs_nightlight_icon_off);
        String secondaryLabel = getSecondaryLabel(booleanState.value);
        booleanState.secondaryLabel = secondaryLabel;
        booleanState.contentDescription = TextUtils.isEmpty(secondaryLabel) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        if (this.mIsListening) {
            this.mListener.setCallback((NightDisplayListener.Callback) null);
        }
        this.mManager = (ColorDisplayManager) getHost().getUserContext().getSystemService(ColorDisplayManager.class);
        NightDisplayListener build = this.mNightDisplayListenerBuilder.setUser(i).build();
        this.mListener = build;
        if (this.mIsListening) {
            build.setCallback(this);
        }
        super.handleUserSwitch(i);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return ColorDisplayManager.isNightDisplayAvailable(this.mContext);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onActivated(boolean z) {
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public LogMaker populate(LogMaker logMaker) {
        return super.populate(logMaker).addTaggedData(1311, Integer.valueOf(this.mManager.getNightDisplayAutoModeRaw()));
    }
}