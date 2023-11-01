package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.appcompat.R$styleable;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSIconView;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.AlphaControlledSignalTileView;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/WifiTile.class */
public class WifiTile extends SecureQSTile<QSTile.SignalState> {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final NetworkController mController;
    public boolean mExpectDisabled;
    public final WifiSignalCallback mSignalCallback;
    public final QSTile.SignalState mStateBeforeClick;
    public final AccessPointController mWifiController;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/WifiTile$CallbackInfo.class */
    public static final class CallbackInfo {
        public boolean activityIn;
        public boolean activityOut;
        public boolean connected;
        public boolean enabled;
        public boolean isTransient;
        public String ssid;
        public String statusLabel;
        public String wifiSignalContentDescription;
        public int wifiSignalIconId;

        public String toString() {
            return "CallbackInfo[enabled=" + this.enabled + ",connected=" + this.connected + ",wifiSignalIconId=" + this.wifiSignalIconId + ",ssid=" + this.ssid + ",activityIn=" + this.activityIn + ",activityOut=" + this.activityOut + ",wifiSignalContentDescription=" + this.wifiSignalContentDescription + ",isTransient=" + this.isTransient + ']';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/WifiTile$WifiSignalCallback.class */
    public final class WifiSignalCallback implements SignalCallback {
        public final CallbackInfo mInfo = new CallbackInfo();

        public WifiSignalCallback() {
            WifiTile.this = r5;
        }

        public void setWifiIndicators(WifiIndicators wifiIndicators) {
            if (QSTileImpl.DEBUG) {
                String str = WifiTile.this.TAG;
                Log.d(str, "onWifiSignalChanged enabled=" + wifiIndicators.enabled);
            }
            IconState iconState = wifiIndicators.qsIcon;
            if (iconState == null) {
                return;
            }
            CallbackInfo callbackInfo = this.mInfo;
            callbackInfo.enabled = wifiIndicators.enabled;
            callbackInfo.connected = iconState.visible;
            callbackInfo.wifiSignalIconId = iconState.icon;
            callbackInfo.ssid = wifiIndicators.description;
            callbackInfo.activityIn = wifiIndicators.activityIn;
            callbackInfo.activityOut = wifiIndicators.activityOut;
            callbackInfo.wifiSignalContentDescription = iconState.contentDescription;
            callbackInfo.isTransient = wifiIndicators.isTransient;
            callbackInfo.statusLabel = wifiIndicators.statusLabel;
            WifiTile.this.refreshState();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.WifiTile$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$qHEAkN6nwbIz4KtgQy2uhvbp_z4(WifiTile wifiTile) {
        wifiTile.lambda$handleClick$0();
    }

    public WifiTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController);
        QSTile.SignalState newTileState = newTileState();
        this.mStateBeforeClick = newTileState;
        WifiSignalCallback wifiSignalCallback = new WifiSignalCallback();
        this.mSignalCallback = wifiSignalCallback;
        this.mController = networkController;
        this.mWifiController = accessPointController;
        networkController.observe(getLifecycle(), wifiSignalCallback);
        newTileState.spec = "wifi";
    }

    public /* synthetic */ void lambda$handleClick$0() {
        if (this.mExpectDisabled) {
            this.mExpectDisabled = false;
            refreshState();
        }
    }

    public static String removeDoubleQuotes(String str) {
        if (str == null) {
            return null;
        }
        int length = str.length();
        String str2 = str;
        if (length > 1) {
            str2 = str;
            if (str.charAt(0) == '\"') {
                int i = length - 1;
                str2 = str;
                if (str.charAt(i) == '\"') {
                    str2 = str.substring(1, i);
                }
            }
        }
        return str2;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public QSIconView createTileView(Context context) {
        return new AlphaControlledSignalTileView(context);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return R$styleable.AppCompatTheme_windowNoTitle;
    }

    public final CharSequence getSecondaryLabel(boolean z, String str) {
        if (z) {
            str = this.mContext.getString(R$string.quick_settings_wifi_secondary_label_transient);
        }
        return str;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_wifi_label);
    }

    @Override // com.android.systemui.qs.tiles.SecureQSTile
    public void handleClick(View view, boolean z) {
        if (checkKeyguard(view, z)) {
            return;
        }
        ((QSTile.SignalState) this.mState).copyTo(this.mStateBeforeClick);
        boolean z2 = ((QSTile.SignalState) this.mState).value;
        refreshState(z2 ? null : QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        this.mController.setWifiEnabled(!z2);
        this.mExpectDisabled = z2;
        if (z2) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.WifiTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    WifiTile.$r8$lambda$qHEAkN6nwbIz4KtgQy2uhvbp_z4(WifiTile.this);
                }
            }, 350L);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSecondaryClick(View view) {
        if (!this.mWifiController.canConfigWifi()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIFI_SETTINGS"), 0);
        } else if (((QSTile.SignalState) this.mState).value) {
        } else {
            this.mController.setWifiEnabled(true);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState signalState, Object obj) {
        int i;
        if (QSTileImpl.DEBUG) {
            String str = this.TAG;
            Log.d(str, "handleUpdateState arg=" + obj);
        }
        CallbackInfo callbackInfo = this.mSignalCallback.mInfo;
        if (this.mExpectDisabled) {
            if (callbackInfo.enabled) {
                return;
            }
            this.mExpectDisabled = false;
        }
        boolean z = obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean z2 = callbackInfo.enabled && (i = callbackInfo.wifiSignalIconId) > 0 && !(callbackInfo.ssid == null && i == 17302903);
        boolean z3 = callbackInfo.ssid == null && callbackInfo.wifiSignalIconId == 17302903;
        if (signalState.slash == null) {
            QSTile.SlashState slashState = new QSTile.SlashState();
            signalState.slash = slashState;
            slashState.rotation = 6.0f;
        }
        signalState.slash.isSlashed = false;
        boolean z4 = z || callbackInfo.isTransient;
        signalState.secondaryLabel = getSecondaryLabel(z4, callbackInfo.statusLabel);
        signalState.state = 2;
        signalState.dualTarget = true;
        signalState.value = z || callbackInfo.enabled;
        boolean z5 = callbackInfo.enabled;
        signalState.activityIn = z5 && callbackInfo.activityIn;
        signalState.activityOut = z5 && callbackInfo.activityOut;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        Resources resources = this.mContext.getResources();
        if (z4) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302870);
            signalState.label = resources.getString(R$string.quick_settings_wifi_label);
        } else if (!signalState.value) {
            signalState.slash.isSlashed = true;
            signalState.state = 1;
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
            signalState.label = resources.getString(R$string.quick_settings_wifi_label);
        } else if (z2) {
            signalState.icon = QSTileImpl.ResourceIcon.get(callbackInfo.wifiSignalIconId);
            String str2 = callbackInfo.ssid;
            signalState.label = str2 != null ? removeDoubleQuotes(str2) : getTileLabel();
        } else if (z3) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
            signalState.label = resources.getString(R$string.quick_settings_wifi_label);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
            signalState.label = resources.getString(R$string.quick_settings_wifi_label);
        }
        stringBuffer.append(this.mContext.getString(R$string.quick_settings_wifi_label));
        stringBuffer.append(",");
        if (signalState.value && z2) {
            stringBuffer2.append(callbackInfo.wifiSignalContentDescription);
            stringBuffer.append(removeDoubleQuotes(callbackInfo.ssid));
            if (!TextUtils.isEmpty(signalState.secondaryLabel)) {
                stringBuffer.append(",");
                stringBuffer.append(signalState.secondaryLabel);
            }
        }
        signalState.stateDescription = stringBuffer2.toString();
        signalState.contentDescription = stringBuffer.toString();
        signalState.dualLabelContentDescription = resources.getString(R$string.accessibility_quick_settings_open_settings, getTileLabel());
        signalState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        return new QSTile.SignalState();
    }
}