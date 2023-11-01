package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import androidx.appcompat.R$styleable;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.DataUsageController;
import com.android.systemui.R$drawable;
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
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.statusbar.connectivity.WifiIndicators;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile.class */
public class InternetTile extends SecureQSTile<QSTile.SignalState> {
    public static final Intent WIFI_SETTINGS = new Intent("android.settings.WIFI_SETTINGS");
    public final AccessPointController mAccessPointController;
    public final NetworkController mController;
    public final DataUsageController mDataController;
    public final Handler mHandler;
    public final InternetDialogFactory mInternetDialogFactory;
    public int mLastTileState;
    public final InternetSignalCallback mSignalCallback;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile$CellularCallbackInfo.class */
    public static final class CellularCallbackInfo {
        public boolean mAirplaneModeEnabled;
        public CharSequence mDataContentDescription;
        public CharSequence mDataSubscriptionName;
        public int mMobileSignalIconId;
        public boolean mMultipleSubs;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoSim;
        public boolean mNoValidatedNetwork;
        public int mQsTypeIcon;
        public boolean mRoaming;

        public CellularCallbackInfo() {
        }

        public String toString() {
            return "CellularCallbackInfo[mAirplaneModeEnabled=" + this.mAirplaneModeEnabled + ",mDataSubscriptionName=" + this.mDataSubscriptionName + ",mDataContentDescription=" + this.mDataContentDescription + ",mMobileSignalIconId=" + this.mMobileSignalIconId + ",mQsTypeIcon=" + this.mQsTypeIcon + ",mNoSim=" + this.mNoSim + ",mRoaming=" + this.mRoaming + ",mMultipleSubs=" + this.mMultipleSubs + ",mNoDefaultNetwork=" + this.mNoDefaultNetwork + ",mNoValidatedNetwork=" + this.mNoValidatedNetwork + ",mNoNetworksAvailable=" + this.mNoNetworksAvailable + ']';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile$EthernetCallbackInfo.class */
    public static final class EthernetCallbackInfo {
        public boolean mConnected;
        public String mEthernetContentDescription;
        public int mEthernetSignalIconId;

        public EthernetCallbackInfo() {
        }

        public String toString() {
            return "EthernetCallbackInfo[mConnected=" + this.mConnected + ",mEthernetSignalIconId=" + this.mEthernetSignalIconId + ",mEthernetContentDescription=" + this.mEthernetContentDescription + ']';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile$InternetSignalCallback.class */
    public final class InternetSignalCallback implements SignalCallback {
        public final WifiCallbackInfo mWifiInfo = new WifiCallbackInfo();
        public final CellularCallbackInfo mCellularInfo = new CellularCallbackInfo();
        public final EthernetCallbackInfo mEthernetInfo = new EthernetCallbackInfo();

        public InternetSignalCallback() {
            InternetTile.this = r6;
        }

        public void setConnectivityStatus(boolean z, boolean z2, boolean z3) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setConnectivityStatus: noDefaultNetwork = " + z + ",noValidatedNetwork = " + z2 + ",noNetworksAvailable = " + z3);
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            cellularCallbackInfo.mNoDefaultNetwork = z;
            cellularCallbackInfo.mNoValidatedNetwork = z2;
            cellularCallbackInfo.mNoNetworksAvailable = z3;
            WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
            wifiCallbackInfo.mNoDefaultNetwork = z;
            wifiCallbackInfo.mNoValidatedNetwork = z2;
            wifiCallbackInfo.mNoNetworksAvailable = z3;
            if (z) {
                InternetTile.this.refreshState(wifiCallbackInfo);
            }
        }

        public void setEthernetIndicators(IconState iconState) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("setEthernetIndicators: icon = ");
                sb.append(iconState == null ? "" : iconState.toString());
                Log.d(str, sb.toString());
            }
            EthernetCallbackInfo ethernetCallbackInfo = this.mEthernetInfo;
            boolean z = iconState.visible;
            ethernetCallbackInfo.mConnected = z;
            ethernetCallbackInfo.mEthernetSignalIconId = iconState.icon;
            ethernetCallbackInfo.mEthernetContentDescription = iconState.contentDescription;
            if (z) {
                InternetTile.this.refreshState(ethernetCallbackInfo);
            }
        }

        public void setIsAirplaneMode(IconState iconState) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                StringBuilder sb = new StringBuilder();
                sb.append("setIsAirplaneMode: icon = ");
                sb.append(iconState == null ? "" : iconState.toString());
                Log.d(str, sb.toString());
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            boolean z = cellularCallbackInfo.mAirplaneModeEnabled;
            boolean z2 = iconState.visible;
            if (z == z2) {
                return;
            }
            cellularCallbackInfo.mAirplaneModeEnabled = z2;
            WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
            wifiCallbackInfo.mAirplaneModeEnabled = z2;
            InternetTile internetTile = InternetTile.this;
            if (internetTile.mSignalCallback.mEthernetInfo.mConnected) {
                return;
            }
            if (z2) {
                internetTile.refreshState(wifiCallbackInfo);
            } else if (!wifiCallbackInfo.mEnabled || wifiCallbackInfo.mWifiSignalIconId <= 0 || wifiCallbackInfo.mSsid == null) {
                internetTile.refreshState(cellularCallbackInfo);
            } else {
                internetTile.refreshState(wifiCallbackInfo);
            }
        }

        public void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
            if (QSTileImpl.DEBUG) {
                Log.d(InternetTile.this.TAG, "setMobileDataIndicators: " + mobileDataIndicators);
            }
            if (mobileDataIndicators.qsIcon == null || !mobileDataIndicators.isDefault) {
                return;
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            CharSequence charSequence = mobileDataIndicators.qsDescription;
            String str = charSequence;
            if (charSequence == null) {
                str = InternetTile.this.mController.getMobileDataNetworkName();
            }
            cellularCallbackInfo.mDataSubscriptionName = str;
            CellularCallbackInfo cellularCallbackInfo2 = this.mCellularInfo;
            cellularCallbackInfo2.mDataContentDescription = mobileDataIndicators.qsDescription != null ? mobileDataIndicators.typeContentDescriptionHtml : null;
            cellularCallbackInfo2.mMobileSignalIconId = mobileDataIndicators.qsIcon.icon;
            cellularCallbackInfo2.mQsTypeIcon = mobileDataIndicators.qsType;
            cellularCallbackInfo2.mRoaming = mobileDataIndicators.roaming;
            boolean z = true;
            if (InternetTile.this.mController.getNumberSubscriptions() <= 1) {
                z = false;
            }
            cellularCallbackInfo2.mMultipleSubs = z;
            InternetTile.this.refreshState(this.mCellularInfo);
        }

        public void setNoSims(boolean z, boolean z2) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setNoSims: show = " + z + ",simDetected = " + z2);
            }
            CellularCallbackInfo cellularCallbackInfo = this.mCellularInfo;
            cellularCallbackInfo.mNoSim = z;
            if (z) {
                cellularCallbackInfo.mMobileSignalIconId = 0;
                cellularCallbackInfo.mQsTypeIcon = 0;
            }
        }

        public void setWifiIndicators(WifiIndicators wifiIndicators) {
            if (QSTileImpl.DEBUG) {
                String str = InternetTile.this.TAG;
                Log.d(str, "setWifiIndicators: " + wifiIndicators);
            }
            WifiCallbackInfo wifiCallbackInfo = this.mWifiInfo;
            boolean z = wifiIndicators.enabled;
            wifiCallbackInfo.mEnabled = z;
            IconState iconState = wifiIndicators.qsIcon;
            if (iconState == null || !wifiIndicators.isDefault) {
                return;
            }
            wifiCallbackInfo.mConnected = iconState.visible;
            wifiCallbackInfo.mWifiSignalIconId = iconState.icon;
            wifiCallbackInfo.mWifiSignalContentDescription = iconState.contentDescription;
            wifiCallbackInfo.mEnabled = z;
            wifiCallbackInfo.mSsid = wifiIndicators.description;
            wifiCallbackInfo.mIsTransient = wifiIndicators.isTransient;
            wifiCallbackInfo.mStatusLabel = wifiIndicators.statusLabel;
            InternetTile.this.refreshState(wifiCallbackInfo);
        }

        public String toString() {
            return "InternetSignalCallback[mWifiInfo=" + this.mWifiInfo + ",mCellularInfo=" + this.mCellularInfo + ",mEthernetInfo=" + this.mEthernetInfo + ']';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile$SignalIcon.class */
    public static class SignalIcon extends QSTile.Icon {
        public final int mState;

        public SignalIcon(int i) {
            this.mState = i;
        }

        @Override // com.android.systemui.plugins.qs.QSTile.Icon
        public Drawable getDrawable(Context context) {
            SignalDrawable signalDrawable = new SignalDrawable(context);
            signalDrawable.setLevel(getState());
            return signalDrawable;
        }

        public int getState() {
            return this.mState;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile$WifiCallbackInfo.class */
    public static final class WifiCallbackInfo {
        public boolean mAirplaneModeEnabled;
        public boolean mConnected;
        public boolean mEnabled;
        public boolean mIsTransient;
        public boolean mNoDefaultNetwork;
        public boolean mNoNetworksAvailable;
        public boolean mNoValidatedNetwork;
        public String mSsid;
        public String mStatusLabel;
        public String mWifiSignalContentDescription;
        public int mWifiSignalIconId;

        public WifiCallbackInfo() {
        }

        public String toString() {
            return "WifiCallbackInfo[mAirplaneModeEnabled=" + this.mAirplaneModeEnabled + ",mEnabled=" + this.mEnabled + ",mConnected=" + this.mConnected + ",mWifiSignalIconId=" + this.mWifiSignalIconId + ",mSsid=" + this.mSsid + ",mWifiSignalContentDescription=" + this.mWifiSignalContentDescription + ",mIsTransient=" + this.mIsTransient + ",mNoDefaultNetwork=" + this.mNoDefaultNetwork + ",mNoValidatedNetwork=" + this.mNoValidatedNetwork + ",mNoNetworksAvailable=" + this.mNoNetworksAvailable + ']';
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.InternetTile$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$vHKUiYvLOXoDzQBVjrSXKOaA-Gc */
    public static /* synthetic */ void m4019$r8$lambda$vHKUiYvLOXoDzQBVjrSXKOaAGc(InternetTile internetTile, View view) {
        internetTile.lambda$handleClick$0(view);
    }

    public InternetTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, InternetDialogFactory internetDialogFactory, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController);
        this.mLastTileState = -1;
        InternetSignalCallback internetSignalCallback = new InternetSignalCallback();
        this.mSignalCallback = internetSignalCallback;
        this.mInternetDialogFactory = internetDialogFactory;
        this.mHandler = handler;
        this.mController = networkController;
        this.mAccessPointController = accessPointController;
        this.mDataController = networkController.getMobileDataController();
        networkController.observe(getLifecycle(), internetSignalCallback);
    }

    public /* synthetic */ void lambda$handleClick$0(View view) {
        this.mInternetDialogFactory.create(true, this.mAccessPointController.canConfigMobileData(), this.mAccessPointController.canConfigWifi(), view);
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

    public final CharSequence appendMobileDataType(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.isEmpty(charSequence2)) {
            return Html.fromHtml(charSequence != null ? charSequence.toString() : "", 0);
        } else if (TextUtils.isEmpty(charSequence)) {
            return Html.fromHtml(charSequence2 != null ? charSequence2.toString() : "", 0);
        } else {
            return Html.fromHtml(this.mContext.getString(R$string.mobile_carrier_text_format, charSequence, charSequence2), 0);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public QSIconView createTileView(Context context) {
        return new AlphaControlledSignalTileView(context);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + ":");
        printWriter.print("    ");
        printWriter.println(((QSTile.SignalState) getState()).toString());
        printWriter.print("    ");
        printWriter.println("mLastTileState=" + this.mLastTileState);
        printWriter.print("    ");
        printWriter.println("mSignalCallback=" + this.mSignalCallback.toString());
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return WIFI_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return R$styleable.AppCompatTheme_windowNoTitle;
    }

    public final CharSequence getMobileDataContentName(CellularCallbackInfo cellularCallbackInfo) {
        if (!cellularCallbackInfo.mRoaming || TextUtils.isEmpty(cellularCallbackInfo.mDataContentDescription)) {
            return cellularCallbackInfo.mRoaming ? this.mContext.getString(R$string.data_connection_roaming) : cellularCallbackInfo.mDataContentDescription;
        }
        String string = this.mContext.getString(R$string.data_connection_roaming);
        CharSequence charSequence = cellularCallbackInfo.mDataContentDescription;
        return this.mContext.getString(R$string.mobile_data_text_format, string, charSequence == null ? "" : charSequence.toString());
    }

    public final CharSequence getSecondaryLabel(boolean z, String str) {
        if (z) {
            str = this.mContext.getString(R$string.quick_settings_wifi_secondary_label_transient);
        }
        return str;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_internet_label);
    }

    @Override // com.android.systemui.qs.tiles.SecureQSTile
    public void handleClick(final View view, boolean z) {
        if (checkKeyguard(view, z)) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.InternetTile$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                InternetTile.m4019$r8$lambda$vHKUiYvLOXoDzQBVjrSXKOaAGc(InternetTile.this, view);
            }
        });
    }

    public final void handleUpdateCellularState(QSTile.SignalState signalState, Object obj) {
        CellularCallbackInfo cellularCallbackInfo = (CellularCallbackInfo) obj;
        boolean z = QSTileImpl.DEBUG;
        if (z) {
            String str = this.TAG;
            Log.d(str, "handleUpdateCellularState: CellularCallbackInfo = " + cellularCallbackInfo.toString());
        }
        Resources resources = this.mContext.getResources();
        signalState.label = resources.getString(R$string.quick_settings_internet_label);
        signalState.state = 2;
        signalState.value = this.mDataController.isMobileDataSupported() && this.mDataController.isMobileDataEnabled();
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        if (cellularCallbackInfo.mAirplaneModeEnabled && cellularCallbackInfo.mQsTypeIcon != TelephonyIcons.ICON_CWF) {
            signalState.state = 1;
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_unavailable);
            signalState.secondaryLabel = resources.getString(R$string.status_bar_airplane);
        } else if (!cellularCallbackInfo.mNoDefaultNetwork) {
            signalState.icon = new SignalIcon(cellularCallbackInfo.mMobileSignalIconId);
            signalState.secondaryLabel = appendMobileDataType(cellularCallbackInfo.mDataSubscriptionName, getMobileDataContentName(cellularCallbackInfo));
        } else if (cellularCallbackInfo.mNoNetworksAvailable || !this.mSignalCallback.mWifiInfo.mEnabled) {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_unavailable);
            signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_unavailable);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_available);
            signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_available);
        }
        signalState.contentDescription = signalState.label;
        if (signalState.state == 1) {
            signalState.stateDescription = "";
        } else {
            signalState.stateDescription = signalState.secondaryLabel;
        }
        if (z) {
            String str2 = this.TAG;
            Log.d(str2, "handleUpdateCellularState: SignalState = " + signalState.toString());
        }
    }

    public final void handleUpdateEthernetState(QSTile.SignalState signalState, Object obj) {
        EthernetCallbackInfo ethernetCallbackInfo = (EthernetCallbackInfo) obj;
        boolean z = QSTileImpl.DEBUG;
        if (z) {
            String str = this.TAG;
            Log.d(str, "handleUpdateEthernetState: EthernetCallbackInfo = " + ethernetCallbackInfo.toString());
        }
        signalState.label = this.mContext.getResources().getString(R$string.quick_settings_internet_label);
        signalState.state = 2;
        signalState.icon = QSTileImpl.ResourceIcon.get(ethernetCallbackInfo.mEthernetSignalIconId);
        signalState.secondaryLabel = ethernetCallbackInfo.mEthernetContentDescription;
        if (z) {
            String str2 = this.TAG;
            Log.d(str2, "handleUpdateEthernetState: SignalState = " + signalState.toString());
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.SignalState signalState, Object obj) {
        this.mQSLogger.logInternetTileUpdate(getTileSpec(), this.mLastTileState, obj == null ? "null" : obj.toString());
        if (obj instanceof CellularCallbackInfo) {
            this.mLastTileState = 0;
            handleUpdateCellularState(signalState, obj);
        } else if (obj instanceof WifiCallbackInfo) {
            this.mLastTileState = 1;
            handleUpdateWifiState(signalState, obj);
        } else if (obj instanceof EthernetCallbackInfo) {
            this.mLastTileState = 2;
            handleUpdateEthernetState(signalState, obj);
        } else {
            int i = this.mLastTileState;
            if (i == 0) {
                handleUpdateCellularState(signalState, this.mSignalCallback.mCellularInfo);
            } else if (i == 1) {
                handleUpdateWifiState(signalState, this.mSignalCallback.mWifiInfo);
            } else if (i == 2) {
                handleUpdateEthernetState(signalState, this.mSignalCallback.mEthernetInfo);
            }
        }
    }

    public final void handleUpdateWifiState(QSTile.SignalState signalState, Object obj) {
        WifiCallbackInfo wifiCallbackInfo = (WifiCallbackInfo) obj;
        boolean z = QSTileImpl.DEBUG;
        if (z) {
            String str = this.TAG;
            Log.d(str, "handleUpdateWifiState: WifiCallbackInfo = " + wifiCallbackInfo.toString());
        }
        boolean z2 = wifiCallbackInfo.mEnabled && wifiCallbackInfo.mWifiSignalIconId > 0 && wifiCallbackInfo.mSsid != null;
        boolean z3 = wifiCallbackInfo.mWifiSignalIconId > 0 && wifiCallbackInfo.mSsid == null;
        if (signalState.slash == null) {
            QSTile.SlashState slashState = new QSTile.SlashState();
            signalState.slash = slashState;
            slashState.rotation = 6.0f;
        }
        signalState.slash.isSlashed = false;
        signalState.secondaryLabel = getSecondaryLabel(wifiCallbackInfo.mIsTransient, removeDoubleQuotes(wifiCallbackInfo.mSsid));
        signalState.state = 2;
        signalState.dualTarget = true;
        signalState.value = wifiCallbackInfo.mEnabled;
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        Resources resources = this.mContext.getResources();
        int i = R$string.quick_settings_internet_label;
        signalState.label = resources.getString(i);
        if (wifiCallbackInfo.mAirplaneModeEnabled) {
            if (!signalState.value) {
                signalState.state = 1;
                signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_unavailable);
                signalState.secondaryLabel = resources.getString(R$string.status_bar_airplane);
            } else if (z2) {
                signalState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
            } else {
                signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_unavailable);
                if (wifiCallbackInfo.mNoNetworksAvailable) {
                    signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_unavailable);
                } else {
                    signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_available);
                }
            }
        } else if (wifiCallbackInfo.mNoDefaultNetwork) {
            if (wifiCallbackInfo.mNoNetworksAvailable || !wifiCallbackInfo.mEnabled) {
                signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_unavailable);
                signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_unavailable);
            } else {
                signalState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_no_internet_available);
                signalState.secondaryLabel = resources.getString(R$string.quick_settings_networks_available);
            }
        } else if (wifiCallbackInfo.mIsTransient) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302870);
        } else if (!signalState.value) {
            signalState.slash.isSlashed = true;
            signalState.state = 1;
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
        } else if (z2) {
            signalState.icon = QSTileImpl.ResourceIcon.get(wifiCallbackInfo.mWifiSignalIconId);
        } else if (z3) {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
        } else {
            signalState.icon = QSTileImpl.ResourceIcon.get(17302903);
        }
        stringBuffer.append(this.mContext.getString(i));
        stringBuffer.append(",");
        if (signalState.value && z2) {
            stringBuffer2.append(wifiCallbackInfo.mWifiSignalContentDescription);
            stringBuffer.append(removeDoubleQuotes(wifiCallbackInfo.mSsid));
        } else if (!TextUtils.isEmpty(signalState.secondaryLabel)) {
            stringBuffer.append(",");
            stringBuffer.append(signalState.secondaryLabel);
        }
        signalState.stateDescription = stringBuffer2.toString();
        signalState.contentDescription = stringBuffer.toString();
        signalState.dualLabelContentDescription = resources.getString(R$string.accessibility_quick_settings_open_settings, getTileLabel());
        signalState.expandedAccessibilityClassName = Switch.class.getName();
        if (z) {
            String str2 = this.TAG;
            Log.d(str2, "handleUpdateWifiState: SignalState = " + signalState.toString());
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.wifi") || (this.mController.hasMobileDataFeature() && this.mHost.getUserContext().getUserId() == 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.SignalState newTileState() {
        QSTile.SignalState signalState = new QSTile.SignalState();
        signalState.forceExpandIcon = true;
        return signalState;
    }
}