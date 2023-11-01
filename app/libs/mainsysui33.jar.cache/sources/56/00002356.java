package com.android.systemui.qs.tiles.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.UserHandle;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.DeviceInfoUtils;
import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.Utils;
import com.android.settingslib.graph.SignalDrawable;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.settingslib.net.SignalStrengthUtil;
import com.android.settingslib.wifi.WifiUtils;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.toast.SystemUIToast;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController.class */
public class InternetDialogController implements AccessPointController.AccessPointCallback {
    public static final long SHORT_DURATION_TIMEOUT = 4000;
    public static final float TOAST_PARAMS_HORIZONTAL_WEIGHT = 1.0f;
    public static final float TOAST_PARAMS_VERTICAL_WEIGHT = 1.0f;
    public AccessPointController mAccessPointController;
    public ActivityStarter mActivityStarter;
    public BroadcastDispatcher mBroadcastDispatcher;
    public InternetDialogCallback mCallback;
    public boolean mCanConfigWifi;
    public CarrierConfigTracker mCarrierConfigTracker;
    public ConnectedWifiInternetMonitor mConnectedWifiInternetMonitor;
    public IntentFilter mConnectionStateFilter;
    public ConnectivityManager mConnectivityManager;
    public ConnectivityManager.NetworkCallback mConnectivityManagerNetworkCallback;
    public Context mContext;
    public DialogLaunchAnimator mDialogLaunchAnimator;
    public Executor mExecutor;
    public final FeatureFlags mFeatureFlags;
    public GlobalSettings mGlobalSettings;
    public Handler mHandler;
    public boolean mHasWifiEntries;
    public KeyguardStateController mKeyguardStateController;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LocationController mLocationController;
    public SubscriptionManager.OnSubscriptionsChangedListener mOnSubscriptionsChangedListener;
    public SignalDrawable mSecondarySignalDrawable;
    public SignalDrawable mSignalDrawable;
    public SubscriptionManager mSubscriptionManager;
    public TelephonyManager mTelephonyManager;
    public ToastFactory mToastFactory;
    public UiEventLogger mUiEventLogger;
    public WifiUtils.InternetIconInjector mWifiIconInjector;
    public WifiManager mWifiManager;
    public WifiStateWorker mWifiStateWorker;
    public WindowManager mWindowManager;
    public Handler mWorkerHandler;
    public static final Drawable EMPTY_DRAWABLE = new ColorDrawable(0);
    public static final int SUBTITLE_TEXT_WIFI_IS_OFF = R$string.wifi_is_off;
    public static final int SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT = R$string.tap_a_network_to_connect;
    public static final int SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS = R$string.unlock_to_view_networks;
    public static final int SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS = R$string.wifi_empty_list_wifi_on;
    public static final int SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE = R$string.non_carrier_network_unavailable;
    public static final int SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE = R$string.all_network_unavailable;
    public static final boolean DEBUG = Log.isLoggable("InternetDialogController", 3);
    public static final TelephonyDisplayInfo DEFAULT_TELEPHONY_DISPLAY_INFO = new TelephonyDisplayInfo(0, 0);
    public final Map<Integer, TelephonyDisplayInfo> mSubIdTelephonyDisplayInfoMap = new HashMap();
    public Map<Integer, TelephonyManager> mSubIdTelephonyManagerMap = new HashMap();
    public Map<Integer, TelephonyCallback> mSubIdTelephonyCallbackMap = new HashMap();
    public MobileMappings.Config mConfig = null;
    public int mDefaultDataSubId = -1;
    public boolean mHasEthernet = false;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.1
        {
            InternetDialogController.this = this;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onRefreshCarrierInfo() {
            InternetDialogController.this.mCallback.onRefreshCarrierInfo();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onSimStateChanged(int i, int i2, int i3) {
            InternetDialogController.this.mCallback.onSimStateChanged();
        }
    };
    public final BroadcastReceiver mConnectionStateReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.2
        {
            InternetDialogController.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                if ("android.net.wifi.supplicant.CONNECTION_CHANGE".equals(action)) {
                    InternetDialogController.this.updateListener();
                    return;
                }
                return;
            }
            if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
            }
            InternetDialogController.this.mConfig = MobileMappings.Config.readConfig(context);
            InternetDialogController.this.updateListener();
        }
    };

    /* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialogController$1DisplayInfo */
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$1DisplayInfo.class */
    public class C1DisplayInfo {
        public CharSequence originalName;
        public SubscriptionInfo subscriptionInfo;
        public CharSequence uniqueName;

        public C1DisplayInfo(SubscriptionInfo subscriptionInfo, CharSequence charSequence) {
            InternetDialogController.this = r4;
            this.subscriptionInfo = subscriptionInfo;
            this.originalName = charSequence;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$ConnectedWifiInternetMonitor.class */
    public class ConnectedWifiInternetMonitor implements WifiEntry.WifiEntryCallback {
        public WifiEntry mWifiEntry;

        public ConnectedWifiInternetMonitor() {
            InternetDialogController.this = r4;
        }

        public void onUpdated() {
            WifiEntry wifiEntry = this.mWifiEntry;
            if (wifiEntry == null) {
                return;
            }
            if (wifiEntry.getConnectedState() != 2) {
                unregisterCallback();
            } else if (wifiEntry.isDefaultNetwork() && wifiEntry.hasInternetAccess()) {
                unregisterCallback();
                InternetDialogController.this.scanWifiAccessPoints();
            }
        }

        public void registerCallbackIfNeed(WifiEntry wifiEntry) {
            if (wifiEntry != null && this.mWifiEntry == null && wifiEntry.getConnectedState() == 2) {
                if (wifiEntry.isDefaultNetwork() && wifiEntry.hasInternetAccess()) {
                    return;
                }
                this.mWifiEntry = wifiEntry;
                wifiEntry.setListener(this);
            }
        }

        public void unregisterCallback() {
            WifiEntry wifiEntry = this.mWifiEntry;
            if (wifiEntry == null) {
                return;
            }
            wifiEntry.setListener((WifiEntry.WifiEntryCallback) null);
            this.mWifiEntry = null;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$DataConnectivityListener.class */
    public class DataConnectivityListener extends ConnectivityManager.NetworkCallback {
        public DataConnectivityListener() {
            InternetDialogController.this = r4;
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            InternetDialogController.this.mHasEthernet = networkCapabilities.hasTransport(3);
            InternetDialogController internetDialogController = InternetDialogController.this;
            if (internetDialogController.mCanConfigWifi && (internetDialogController.mHasEthernet || networkCapabilities.hasTransport(1))) {
                InternetDialogController.this.scanWifiAccessPoints();
            }
            InternetDialogController.this.mCallback.onCapabilitiesChanged(network, networkCapabilities);
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            InternetDialogController internetDialogController = InternetDialogController.this;
            internetDialogController.mHasEthernet = false;
            internetDialogController.mCallback.onLost(network);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$InternetDialogCallback.class */
    public interface InternetDialogCallback {
        void dismissDialog();

        void onAccessPointsChanged(List<WifiEntry> list, WifiEntry wifiEntry, boolean z);

        void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities);

        void onDataConnectionStateChanged(int i, int i2);

        void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo);

        void onLost(Network network);

        void onRefreshCarrierInfo();

        void onServiceStateChanged(ServiceState serviceState);

        void onSignalStrengthsChanged(SignalStrength signalStrength);

        void onSimStateChanged();

        void onSubscriptionsChanged(int i);

        void onUserMobileDataStateChanged(boolean z);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$InternetOnSubscriptionChangedListener.class */
    public class InternetOnSubscriptionChangedListener extends SubscriptionManager.OnSubscriptionsChangedListener {
        public InternetOnSubscriptionChangedListener() {
            InternetDialogController.this = r4;
        }

        @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
        public void onSubscriptionsChanged() {
            InternetDialogController.this.updateListener();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$InternetTelephonyCallback.class */
    public class InternetTelephonyCallback extends TelephonyCallback implements TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DisplayInfoListener, TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.UserMobileDataStateListener {
        public final int mSubId;

        public InternetTelephonyCallback(int i) {
            InternetDialogController.this = r4;
            this.mSubId = i;
        }

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            InternetDialogController.this.mCallback.onDataConnectionStateChanged(i, i2);
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            InternetDialogController.this.mSubIdTelephonyDisplayInfoMap.put(Integer.valueOf(this.mSubId), telephonyDisplayInfo);
            InternetDialogController.this.mCallback.onDisplayInfoChanged(telephonyDisplayInfo);
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            InternetDialogController.this.mCallback.onServiceStateChanged(serviceState);
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            InternetDialogController.this.mCallback.onSignalStrengthsChanged(signalStrength);
        }

        @Override // android.telephony.TelephonyCallback.UserMobileDataStateListener
        public void onUserMobileDataStateChanged(boolean z) {
            InternetDialogController.this.mCallback.onUserMobileDataStateChanged(z);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogController$WifiEntryConnectCallback.class */
    public static class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final ActivityStarter mActivityStarter;
        public final InternetDialogController mInternetDialogController;
        public final WifiEntry mWifiEntry;

        public WifiEntryConnectCallback(ActivityStarter activityStarter, WifiEntry wifiEntry, InternetDialogController internetDialogController) {
            this.mActivityStarter = activityStarter;
            this.mWifiEntry = wifiEntry;
            this.mInternetDialogController = internetDialogController;
        }

        public void onConnectResult(int i) {
            if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "onConnectResult " + i);
            }
            if (i == 1) {
                Intent wifiDialogIntent = WifiUtils.getWifiDialogIntent(this.mWifiEntry.getKey(), true);
                wifiDialogIntent.addFlags(268435456);
                this.mActivityStarter.startActivity(wifiDialogIntent, false);
            } else if (i == 2) {
                this.mInternetDialogController.makeOverlayToast(R$string.wifi_failed_connect_message);
            } else if (InternetDialogController.DEBUG) {
                Log.d("InternetDialogController", "connect failure reason=" + i);
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda7.get():java.lang.Object] */
    /* renamed from: $r8$lambda$-WkqNLCDzviXUMkmKwiSY1WL2bA */
    public static /* synthetic */ Stream m4073$r8$lambda$WkqNLCDzviXUMkmKwiSY1WL2bA(Supplier supplier, Set set, Context context) {
        return lambda$getUniqueSubscriptionDisplayNames$6(supplier, set, context);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda10.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$Gcl7I8R-dlKPcX2SZ8TbYLyxRps */
    public static /* synthetic */ C1DisplayInfo m4074$r8$lambda$Gcl7I8RdlKPcX2SZ8TbYLyxRps(Set set, C1DisplayInfo c1DisplayInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$9(set, c1DisplayInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda8.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$OvYO_FeMLRYNE-GfkI9cFlvz4QU */
    public static /* synthetic */ boolean m4075$r8$lambda$OvYO_FeMLRYNEGfkI9cFlvz4QU(Set set, C1DisplayInfo c1DisplayInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$7(set, c1DisplayInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda12.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ CharSequence $r8$lambda$S7cqsWllMBCGpbKQy05XlWTMVMc(C1DisplayInfo c1DisplayInfo) {
        return c1DisplayInfo.uniqueName;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$ST92crOT-E9tJ6sJBJYIutf-qow */
    public static /* synthetic */ C1DisplayInfo m4076$r8$lambda$ST92crOTE9tJ6sJBJYIutfqow(InternetDialogController internetDialogController, SubscriptionInfo subscriptionInfo) {
        return internetDialogController.lambda$getUniqueSubscriptionDisplayNames$1(subscriptionInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda0.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ C1DisplayInfo $r8$lambda$VswS62bG9S2UvWmDF_vw7xEtTVU(Set set, Context context, C1DisplayInfo c1DisplayInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$5(set, context, c1DisplayInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$Y07hn1tX7HdFFUSB6Ju8diJb-zU */
    public static /* synthetic */ void m4077$r8$lambda$Y07hn1tX7HdFFUSB6Ju8diJbzU(InternetDialogController internetDialogController, int i, boolean z) {
        internetDialogController.lambda$setMobileDataEnabled$12(i, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9.apply(java.lang.Object):java.lang.Object] */
    /* renamed from: $r8$lambda$ZNiHurhHe1v2Ck-C-DakQdS1lS0 */
    public static /* synthetic */ CharSequence m4078$r8$lambda$ZNiHurhHe1v2CkCDakQdS1lS0(C1DisplayInfo c1DisplayInfo) {
        return c1DisplayInfo.uniqueName;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda11.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ Integer $r8$lambda$aliCsf5EjkkPnm8koGn51QcI6hg(C1DisplayInfo c1DisplayInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$10(c1DisplayInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda1.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$fnx1-2lue7Q1E6mt2auFncSHqGw */
    public static /* synthetic */ boolean m4079$r8$lambda$fnx12lue7Q1E6mt2auFncSHqGw(SubscriptionInfo subscriptionInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$0(subscriptionInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda5.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$g2ntY-H7RHIPMFhXgYsi2XNg_Dw */
    public static /* synthetic */ boolean m4080$r8$lambda$g2ntYH7RHIPMFhXgYsi2XNg_Dw(Set set, C1DisplayInfo c1DisplayInfo) {
        return lambda$getUniqueSubscriptionDisplayNames$3(set, c1DisplayInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda6.apply(java.lang.Object):java.lang.Object] */
    public static /* synthetic */ CharSequence $r8$lambda$hfVVr6WyR8x05ygFUmFW6rZK9Rs(C1DisplayInfo c1DisplayInfo) {
        return c1DisplayInfo.originalName;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda4.get():java.lang.Object] */
    public static /* synthetic */ Stream $r8$lambda$vGJUXGn5CMxtBKXACYL_3vfqwQI(InternetDialogController internetDialogController) {
        return internetDialogController.lambda$getUniqueSubscriptionDisplayNames$2();
    }

    public InternetDialogController(Context context, UiEventLogger uiEventLogger, ActivityStarter activityStarter, AccessPointController accessPointController, SubscriptionManager subscriptionManager, TelephonyManager telephonyManager, WifiManager wifiManager, ConnectivityManager connectivityManager, Handler handler, Executor executor, BroadcastDispatcher broadcastDispatcher, KeyguardUpdateMonitor keyguardUpdateMonitor, GlobalSettings globalSettings, KeyguardStateController keyguardStateController, WindowManager windowManager, ToastFactory toastFactory, Handler handler2, CarrierConfigTracker carrierConfigTracker, LocationController locationController, DialogLaunchAnimator dialogLaunchAnimator, WifiStateWorker wifiStateWorker, FeatureFlags featureFlags) {
        if (DEBUG) {
            Log.d("InternetDialogController", "Init InternetDialogController");
        }
        this.mHandler = handler;
        this.mWorkerHandler = handler2;
        this.mExecutor = executor;
        this.mContext = context;
        this.mGlobalSettings = globalSettings;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mConnectivityManager = connectivityManager;
        this.mSubscriptionManager = subscriptionManager;
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
        IntentFilter intentFilter = new IntentFilter();
        this.mConnectionStateFilter = intentFilter;
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        this.mConnectionStateFilter.addAction("android.net.wifi.supplicant.CONNECTION_CHANGE");
        this.mUiEventLogger = uiEventLogger;
        this.mActivityStarter = activityStarter;
        this.mAccessPointController = accessPointController;
        this.mWifiIconInjector = new WifiUtils.InternetIconInjector(this.mContext);
        this.mConnectivityManagerNetworkCallback = new DataConnectivityListener();
        this.mWindowManager = windowManager;
        this.mToastFactory = toastFactory;
        this.mSignalDrawable = new SignalDrawable(this.mContext);
        this.mSecondarySignalDrawable = new SignalDrawable(this.mContext);
        this.mLocationController = locationController;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mConnectedWifiInternetMonitor = new ConnectedWifiInternetMonitor();
        this.mWifiStateWorker = wifiStateWorker;
        this.mFeatureFlags = featureFlags;
    }

    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$0(SubscriptionInfo subscriptionInfo) {
        return (subscriptionInfo == null || subscriptionInfo.getDisplayName() == null) ? false : true;
    }

    public /* synthetic */ C1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$1(SubscriptionInfo subscriptionInfo) {
        return new C1DisplayInfo(subscriptionInfo, subscriptionInfo.getDisplayName().toString().trim());
    }

    public static /* synthetic */ Integer lambda$getUniqueSubscriptionDisplayNames$10(C1DisplayInfo c1DisplayInfo) {
        return Integer.valueOf(c1DisplayInfo.subscriptionInfo.getSubscriptionId());
    }

    public /* synthetic */ Stream lambda$getUniqueSubscriptionDisplayNames$2() {
        return getSubscriptionInfo().stream().filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InternetDialogController.m4079$r8$lambda$fnx12lue7Q1E6mt2auFncSHqGw((SubscriptionInfo) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.m4076$r8$lambda$ST92crOTE9tJ6sJBJYIutfqow(InternetDialogController.this, (SubscriptionInfo) obj);
            }
        });
    }

    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$3(Set set, C1DisplayInfo c1DisplayInfo) {
        return !set.add(c1DisplayInfo.originalName);
    }

    public static /* synthetic */ C1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$5(Set set, Context context, C1DisplayInfo c1DisplayInfo) {
        String str;
        if (set.contains(c1DisplayInfo.originalName)) {
            String bidiFormattedPhoneNumber = DeviceInfoUtils.getBidiFormattedPhoneNumber(context, c1DisplayInfo.subscriptionInfo);
            if (bidiFormattedPhoneNumber != null) {
                str = bidiFormattedPhoneNumber;
                if (bidiFormattedPhoneNumber.length() > 4) {
                    str = bidiFormattedPhoneNumber.substring(bidiFormattedPhoneNumber.length() - 4);
                }
            } else {
                str = "";
            }
            if (TextUtils.isEmpty(str)) {
                c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
            } else {
                c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + str;
            }
        } else {
            c1DisplayInfo.uniqueName = c1DisplayInfo.originalName;
        }
        return c1DisplayInfo;
    }

    public static /* synthetic */ Stream lambda$getUniqueSubscriptionDisplayNames$6(Supplier supplier, final Set set, final Context context) {
        return ((Stream) supplier.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.$r8$lambda$VswS62bG9S2UvWmDF_vw7xEtTVU(set, context, (InternetDialogController.C1DisplayInfo) obj);
            }
        });
    }

    public static /* synthetic */ boolean lambda$getUniqueSubscriptionDisplayNames$7(Set set, C1DisplayInfo c1DisplayInfo) {
        return !set.add(c1DisplayInfo.uniqueName);
    }

    public static /* synthetic */ C1DisplayInfo lambda$getUniqueSubscriptionDisplayNames$9(Set set, C1DisplayInfo c1DisplayInfo) {
        if (set.contains(c1DisplayInfo.uniqueName)) {
            c1DisplayInfo.uniqueName = ((Object) c1DisplayInfo.originalName) + " " + c1DisplayInfo.subscriptionInfo.getSubscriptionId();
        }
        return c1DisplayInfo;
    }

    public boolean activeNetworkIsCellular() {
        NetworkCapabilities networkCapabilities;
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        if (connectivityManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "ConnectivityManager is null, can not check active network.");
                return false;
            }
            return false;
        }
        Network activeNetwork = connectivityManager.getActiveNetwork();
        if (activeNetwork == null || (networkCapabilities = this.mConnectivityManager.getNetworkCapabilities(activeNetwork)) == null) {
            return false;
        }
        return networkCapabilities.hasTransport(0);
    }

    public boolean connect(WifiEntry wifiEntry) {
        if (wifiEntry == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "No Wi-Fi ap to connect.");
                return false;
            }
            return false;
        }
        if (wifiEntry.getWifiConfiguration() != null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "connect networkId=" + wifiEntry.getWifiConfiguration().networkId);
            }
        } else if (DEBUG) {
            Log.d("InternetDialogController", "connect to unsaved network " + wifiEntry.getTitle());
        }
        wifiEntry.connect(new WifiEntryConnectCallback(this.mActivityStarter, wifiEntry, this));
        return false;
    }

    public void connectCarrierNetwork() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        if (mergedCarrierEntry == null || !mergedCarrierEntry.canConnect()) {
            return;
        }
        mergedCarrierEntry.connect((WifiEntry.ConnectCallback) null, false);
        makeOverlayToast(R$string.wifi_wont_autoconnect_for_now);
    }

    public int getActiveAutoSwitchNonDdsSubId() {
        SubscriptionInfo activeSubscriptionInfo;
        if (!this.mFeatureFlags.isEnabled(Flags.QS_SECONDARY_DATA_SUB_INFO) || (activeSubscriptionInfo = this.mSubscriptionManager.getActiveSubscriptionInfo(SubscriptionManager.getActiveDataSubscriptionId())) == null || activeSubscriptionInfo.getSubscriptionId() == this.mDefaultDataSubId || activeSubscriptionInfo.isOpportunistic()) {
            return -1;
        }
        int subscriptionId = activeSubscriptionInfo.getSubscriptionId();
        if (this.mSubIdTelephonyManagerMap.get(Integer.valueOf(subscriptionId)) == null) {
            TelephonyManager createForSubscriptionId = this.mTelephonyManager.createForSubscriptionId(subscriptionId);
            InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(subscriptionId);
            createForSubscriptionId.registerTelephonyCallback(this.mExecutor, internetTelephonyCallback);
            this.mSubIdTelephonyCallbackMap.put(Integer.valueOf(subscriptionId), internetTelephonyCallback);
            this.mSubIdTelephonyManagerMap.put(Integer.valueOf(subscriptionId), createForSubscriptionId);
        }
        return subscriptionId;
    }

    public int getCarrierNetworkLevel() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        int i = 0;
        if (mergedCarrierEntry == null) {
            return 0;
        }
        int level = mergedCarrierEntry.getLevel();
        if (level >= 0) {
            i = level;
        }
        return i;
    }

    public int getDefaultDataSubscriptionId() {
        return SubscriptionManager.getDefaultDataSubscriptionId();
    }

    public CharSequence getDialogTitleText() {
        return isAirplaneModeEnabled() ? this.mContext.getText(R$string.airplane_mode) : this.mContext.getText(R$string.quick_settings_internet_label);
    }

    public Drawable getInternetWifiDrawable(WifiEntry wifiEntry) {
        Drawable icon;
        if (wifiEntry.getLevel() == -1 || (icon = this.mWifiIconInjector.getIcon(wifiEntry.shouldShowXLevelIcon(), wifiEntry.getLevel())) == null) {
            return null;
        }
        icon.setTint(this.mContext.getColor(R$color.connected_network_primary_color));
        return icon;
    }

    public String getMobileNetworkSummary(int i) {
        return getMobileSummary(this.mContext, getNetworkTypeDescription(this.mContext, this.mConfig, i), i);
    }

    public CharSequence getMobileNetworkTitle(int i) {
        return getUniqueSubscriptionDisplayName(i, this.mContext);
    }

    public final String getMobileSummary(Context context, String str, int i) {
        if (isMobileDataEnabled()) {
            boolean z = i == this.mDefaultDataSubId;
            boolean z2 = getActiveAutoSwitchNonDdsSubId() != -1;
            if (activeNetworkIsCellular() || isCarrierNetworkActive()) {
                str = context.getString(R$string.preference_summary_default_combination, context.getString(z ? z2 ? R$string.mobile_data_poor_connection : R$string.mobile_data_connection_active : R$string.mobile_data_temp_connection_active), str);
            } else if (!isDataStateInService(i)) {
                str = context.getString(R$string.mobile_data_no_connection);
            }
            return str;
        }
        return context.getString(R$string.mobile_data_off_summary);
    }

    public final String getNetworkTypeDescription(Context context, MobileMappings.Config config, int i) {
        String iconKey = MobileMappings.getIconKey(this.mSubIdTelephonyDisplayInfoMap.getOrDefault(Integer.valueOf(i), DEFAULT_TELEPHONY_DISPLAY_INFO));
        if (MobileMappings.mapIconSets(config) == null || MobileMappings.mapIconSets(config).get(iconKey) == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "The description of network type is empty.");
                return "";
            }
            return "";
        }
        SignalIcon$MobileIconGroup signalIcon$MobileIconGroup = MobileMappings.mapIconSets(config).get(iconKey);
        Objects.requireNonNull(signalIcon$MobileIconGroup);
        int i2 = signalIcon$MobileIconGroup.dataContentDescription;
        if (isCarrierNetworkActive()) {
            i2 = TelephonyIcons.CARRIER_MERGED_WIFI.dataContentDescription;
        }
        return i2 != 0 ? SubscriptionManager.getResourcesForSubId(context, i).getString(i2) : "";
    }

    public Intent getSettingsIntent() {
        return new Intent("android.settings.NETWORK_PROVIDER_SETTINGS").addFlags(268435456);
    }

    /* JADX WARN: Code restructure failed: missing block: B:77:0x004b, code lost:
        if (r0 != false) goto L42;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Drawable getSignalStrengthDrawable(int i) {
        Drawable drawable;
        Drawable drawable2 = this.mContext.getDrawable(R$drawable.ic_signal_strength_zero_bar_no_internet);
        try {
        } catch (Throwable th) {
            th.printStackTrace();
            drawable = drawable2;
        }
        if (this.mTelephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null");
            }
            return drawable2;
        }
        boolean isCarrierNetworkActive = isCarrierNetworkActive();
        if (!isDataStateInService(i) && !isVoiceStateInService(i)) {
            drawable = drawable2;
        }
        AtomicReference atomicReference = new AtomicReference();
        atomicReference.set(getSignalStrengthDrawableWithLevel(isCarrierNetworkActive, i));
        drawable = (Drawable) atomicReference.get();
        Drawable drawable3 = drawable;
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(this.mContext, 16843282);
        Drawable drawable4 = drawable;
        if (activeNetworkIsCellular() || isCarrierNetworkActive) {
            colorAttrDefaultColor = this.mContext.getColor(R$color.connected_network_primary_color);
        }
        Drawable drawable5 = drawable;
        drawable.setTint(colorAttrDefaultColor);
        return drawable;
    }

    public Drawable getSignalStrengthDrawableWithLevel(boolean z, int i) {
        int i2;
        int i3;
        SignalStrength signalStrength = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getSignalStrength();
        int level = signalStrength == null ? 0 : signalStrength.getLevel();
        if (z) {
            i2 = getCarrierNetworkLevel();
            i3 = 5;
        } else {
            i2 = level;
            i3 = 5;
            if (this.mSubscriptionManager != null) {
                i2 = level;
                i3 = 5;
                if (shouldInflateSignalStrength(i)) {
                    i2 = level + 1;
                    i3 = 6;
                }
            }
        }
        return getSignalStrengthIcon(i, this.mContext, i2, i3, 0, !isMobileDataEnabled());
    }

    public Drawable getSignalStrengthIcon(int i, Context context, int i2, int i3, int i4, boolean z) {
        boolean z2 = i == this.mDefaultDataSubId;
        if (z2) {
            this.mSignalDrawable.setLevel(SignalDrawable.getState(i2, i3, z));
        } else {
            this.mSecondarySignalDrawable.setLevel(SignalDrawable.getState(i2, i3, z));
        }
        Drawable drawable = i4 == 0 ? EMPTY_DRAWABLE : context.getResources().getDrawable(i4, context.getTheme());
        SignalDrawable signalDrawable = z2 ? this.mSignalDrawable : this.mSecondarySignalDrawable;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.signal_strength_icon_size);
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{drawable, signalDrawable});
        layerDrawable.setLayerGravity(0, 51);
        layerDrawable.setLayerGravity(1, 85);
        layerDrawable.setLayerSize(1, dimensionPixelSize, dimensionPixelSize);
        layerDrawable.setTintList(Utils.getColorAttr(context, 16843282));
        return layerDrawable;
    }

    public Intent getSubSettingIntent(int i) {
        Intent intent = new Intent("android.settings.NETWORK_OPERATOR_SETTINGS");
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", "auto_data_switch");
        intent.putExtra("android.provider.extra.SUB_ID", i);
        intent.putExtra(":settings:show_fragment_args", bundle);
        return intent;
    }

    public List<SubscriptionInfo> getSubscriptionInfo() {
        return this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo();
    }

    public SubscriptionManager getSubscriptionManager() {
        return this.mSubscriptionManager;
    }

    public CharSequence getSubtitleText(boolean z) {
        if (this.mCanConfigWifi && !isWifiEnabled()) {
            if (DEBUG) {
                Log.d("InternetDialogController", "Wi-Fi off.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_WIFI_IS_OFF);
        } else if (isDeviceLocked()) {
            if (DEBUG) {
                Log.d("InternetDialogController", "The device is locked.");
            }
            return this.mContext.getText(SUBTITLE_TEXT_UNLOCK_TO_VIEW_NETWORKS);
        } else {
            CharSequence charSequence = null;
            if (this.mHasWifiEntries) {
                if (this.mCanConfigWifi) {
                    charSequence = this.mContext.getText(SUBTITLE_TEXT_TAP_A_NETWORK_TO_CONNECT);
                }
                return charSequence;
            } else if (this.mCanConfigWifi && z) {
                return this.mContext.getText(SUBTITLE_TEXT_SEARCHING_FOR_NETWORKS);
            } else {
                if (isCarrierNetworkActive()) {
                    return this.mContext.getText(SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                }
                boolean z2 = DEBUG;
                if (z2) {
                    Log.d("InternetDialogController", "No Wi-Fi item.");
                }
                boolean z3 = getActiveAutoSwitchNonDdsSubId() != -1;
                if (!hasActiveSubId() || (!isVoiceStateInService(this.mDefaultDataSubId) && !isDataStateInService(this.mDefaultDataSubId) && !z3)) {
                    if (z2) {
                        Log.d("InternetDialogController", "No carrier or service is out of service.");
                    }
                    return this.mContext.getText(SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
                } else if (this.mCanConfigWifi && !isMobileDataEnabled()) {
                    if (z2) {
                        Log.d("InternetDialogController", "Mobile data off");
                    }
                    return this.mContext.getText(SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                } else if (activeNetworkIsCellular()) {
                    if (this.mCanConfigWifi) {
                        return this.mContext.getText(SUBTITLE_TEXT_NON_CARRIER_NETWORK_UNAVAILABLE);
                    }
                    return null;
                } else {
                    if (z2) {
                        Log.d("InternetDialogController", "No carrier data.");
                    }
                    return this.mContext.getText(SUBTITLE_TEXT_ALL_CARRIER_NETWORK_UNAVAILABLE);
                }
            }
        }
    }

    public TelephonyManager getTelephonyManager() {
        return this.mTelephonyManager;
    }

    public final CharSequence getUniqueSubscriptionDisplayName(int i, Context context) {
        return getUniqueSubscriptionDisplayNames(context).getOrDefault(Integer.valueOf(i), "");
    }

    public final Map<Integer, CharSequence> getUniqueSubscriptionDisplayNames(final Context context) {
        final Supplier supplier = new Supplier() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return InternetDialogController.$r8$lambda$vGJUXGn5CMxtBKXACYL_3vfqwQI(InternetDialogController.this);
            }
        };
        final HashSet hashSet = new HashSet();
        final Set set = (Set) ((Stream) supplier.get()).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InternetDialogController.m4080$r8$lambda$g2ntYH7RHIPMFhXgYsi2XNg_Dw(hashSet, (InternetDialogController.C1DisplayInfo) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda6
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.$r8$lambda$hfVVr6WyR8x05ygFUmFW6rZK9Rs((InternetDialogController.C1DisplayInfo) obj);
            }
        }).collect(Collectors.toSet());
        Supplier supplier2 = new Supplier() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                return InternetDialogController.m4073$r8$lambda$WkqNLCDzviXUMkmKwiSY1WL2bA(supplier, set, context);
            }
        };
        hashSet.clear();
        final Set set2 = (Set) ((Stream) supplier2.get()).filter(new Predicate() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda8
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return InternetDialogController.m4075$r8$lambda$OvYO_FeMLRYNEGfkI9cFlvz4QU(hashSet, (InternetDialogController.C1DisplayInfo) obj);
            }
        }).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.m4078$r8$lambda$ZNiHurhHe1v2CkCDakQdS1lS0((InternetDialogController.C1DisplayInfo) obj);
            }
        }).collect(Collectors.toSet());
        return (Map) ((Stream) supplier2.get()).map(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda10
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.m4074$r8$lambda$Gcl7I8RdlKPcX2SZ8TbYLyxRps(set2, (InternetDialogController.C1DisplayInfo) obj);
            }
        }).collect(Collectors.toMap(new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda11
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.$r8$lambda$aliCsf5EjkkPnm8koGn51QcI6hg((InternetDialogController.C1DisplayInfo) obj);
            }
        }, new Function() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda12
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return InternetDialogController.$r8$lambda$S7cqsWllMBCGpbKQy05XlWTMVMc((InternetDialogController.C1DisplayInfo) obj);
            }
        }));
    }

    public Intent getWifiDetailsSettingsIntent(String str) {
        if (TextUtils.isEmpty(str)) {
            if (DEBUG) {
                Log.d("InternetDialogController", "connected entry's key is empty");
                return null;
            }
            return null;
        }
        return WifiUtils.getWifiDetailsSettingsIntent(str);
    }

    public WifiUtils.InternetIconInjector getWifiIconInjector() {
        return this.mWifiIconInjector;
    }

    public boolean hasActiveSubId() {
        if (this.mSubscriptionManager != null) {
            return (isAirplaneModeEnabled() || this.mTelephonyManager == null || this.mSubscriptionManager.getActiveSubscriptionIdList().length <= 0) ? false : true;
        } else if (DEBUG) {
            Log.d("InternetDialogController", "SubscriptionManager is null, can not check carrier.");
            return false;
        } else {
            return false;
        }
    }

    public boolean hasEthernet() {
        return this.mHasEthernet;
    }

    public boolean isAirplaneModeEnabled() {
        boolean z = false;
        if (this.mGlobalSettings.getInt("airplane_mode_on", 0) != 0) {
            z = true;
        }
        return z;
    }

    public boolean isCarrierNetworkActive() {
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        return mergedCarrierEntry != null && mergedCarrierEntry.isDefaultNetwork();
    }

    public boolean isDataStateInService(int i) {
        ServiceState serviceState = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getServiceState();
        NetworkRegistrationInfo networkRegistrationInfo = serviceState == null ? null : serviceState.getNetworkRegistrationInfo(2, 1);
        return networkRegistrationInfo == null ? false : networkRegistrationInfo.isRegistered();
    }

    public boolean isDeviceLocked() {
        return !this.mKeyguardStateController.isUnlocked();
    }

    public boolean isMobileDataEnabled() {
        TelephonyManager telephonyManager = this.mTelephonyManager;
        return telephonyManager != null && telephonyManager.isDataEnabled();
    }

    public boolean isVoiceStateInService(int i) {
        if (this.mTelephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not detect voice state.");
                return false;
            }
            return false;
        }
        ServiceState serviceState = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager).getServiceState();
        boolean z = false;
        if (serviceState != null) {
            z = false;
            if (serviceState.getState() == 0) {
                z = true;
            }
        }
        return z;
    }

    public boolean isWifiEnabled() {
        return this.mWifiStateWorker.isWifiEnabled();
    }

    public boolean isWifiScanEnabled() {
        if (this.mLocationController.isLocationEnabled()) {
            WifiManager wifiManager = this.mWifiManager;
            boolean z = false;
            if (wifiManager != null) {
                z = false;
                if (wifiManager.isScanAlwaysAvailable()) {
                    z = true;
                }
            }
            return z;
        }
        return false;
    }

    public void launchMobileNetworkSettings(View view) {
        int activeAutoSwitchNonDdsSubId = getActiveAutoSwitchNonDdsSubId();
        if (activeAutoSwitchNonDdsSubId != -1) {
            startActivity(getSubSettingIntent(activeAutoSwitchNonDdsSubId), view);
            return;
        }
        Log.w("InternetDialogController", "launchMobileNetworkSettings fail, invalid subId:" + activeAutoSwitchNonDdsSubId);
    }

    public void launchNetworkSetting(View view) {
        startActivity(getSettingsIntent(), view);
    }

    public void launchWifiDetailsSetting(String str, View view) {
        Intent wifiDetailsSettingsIntent = getWifiDetailsSettingsIntent(str);
        if (wifiDetailsSettingsIntent != null) {
            startActivity(wifiDetailsSettingsIntent, view);
        }
    }

    public void launchWifiScanningSetting(View view) {
        Intent intent = new Intent("android.settings.WIFI_SCANNING_SETTINGS");
        intent.addFlags(268435456);
        startActivity(intent, view);
    }

    public void makeOverlayToast(int i) {
        Resources resources = this.mContext.getResources();
        final SystemUIToast createToast = this.mToastFactory.createToast(this.mContext, resources.getString(i), this.mContext.getPackageName(), UserHandle.myUserId(), resources.getConfiguration().orientation);
        if (createToast == null) {
            return;
        }
        final View view = createToast.getView();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.height = -2;
        layoutParams.width = -2;
        layoutParams.format = -3;
        layoutParams.type = 2017;
        layoutParams.flags = 152;
        layoutParams.y = createToast.getYOffset().intValue();
        int absoluteGravity = Gravity.getAbsoluteGravity(createToast.getGravity().intValue(), resources.getConfiguration().getLayoutDirection());
        layoutParams.gravity = absoluteGravity;
        if ((absoluteGravity & 7) == 7) {
            layoutParams.horizontalWeight = 1.0f;
        }
        if ((absoluteGravity & 112) == 112) {
            layoutParams.verticalWeight = 1.0f;
        }
        this.mWindowManager.addView(view, layoutParams);
        Animator inAnimation = createToast.getInAnimation();
        if (inAnimation != null) {
            inAnimation.start();
        }
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.3
            {
                InternetDialogController.this = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                Animator outAnimation = createToast.getOutAnimation();
                if (outAnimation != null) {
                    outAnimation.start();
                    outAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController.3.1
                        {
                            AnonymousClass3.this = this;
                        }

                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            InternetDialogController.this.mWindowManager.removeViewImmediate(view);
                        }
                    });
                }
            }
        }, SHORT_DURATION_TIMEOUT);
    }

    public void onAccessPointsChanged(List<WifiEntry> list) {
        ArrayList arrayList;
        if (this.mCanConfigWifi) {
            int size = list == null ? 0 : list.size();
            boolean z = size > 3;
            WifiEntry wifiEntry = null;
            if (size > 0) {
                ArrayList arrayList2 = new ArrayList();
                if (z) {
                    size = 3;
                }
                this.mConnectedWifiInternetMonitor.unregisterCallback();
                for (int i = 0; i < size; i++) {
                    WifiEntry wifiEntry2 = list.get(i);
                    this.mConnectedWifiInternetMonitor.registerCallbackIfNeed(wifiEntry2);
                    if (wifiEntry == null && wifiEntry2.isDefaultNetwork() && wifiEntry2.hasInternetAccess()) {
                        wifiEntry = wifiEntry2;
                    } else {
                        arrayList2.add(wifiEntry2);
                    }
                }
                this.mHasWifiEntries = true;
                arrayList = arrayList2;
            } else {
                this.mHasWifiEntries = false;
                wifiEntry = null;
                arrayList = null;
            }
            this.mCallback.onAccessPointsChanged(arrayList, wifiEntry, z);
        }
    }

    public void onStart(InternetDialogCallback internetDialogCallback, boolean z) {
        boolean z2 = DEBUG;
        if (z2) {
            Log.d("InternetDialogController", "onStart");
        }
        this.mCallback = internetDialogCallback;
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateCallback);
        this.mAccessPointController.addAccessPointCallback(this);
        this.mBroadcastDispatcher.registerReceiver(this.mConnectionStateReceiver, this.mConnectionStateFilter, this.mExecutor);
        InternetOnSubscriptionChangedListener internetOnSubscriptionChangedListener = new InternetOnSubscriptionChangedListener();
        this.mOnSubscriptionsChangedListener = internetOnSubscriptionChangedListener;
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mExecutor, internetOnSubscriptionChangedListener);
        this.mDefaultDataSubId = getDefaultDataSubscriptionId();
        if (z2) {
            Log.d("InternetDialogController", "Init, SubId: " + this.mDefaultDataSubId);
        }
        this.mConfig = MobileMappings.Config.readConfig(this.mContext);
        this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(this.mDefaultDataSubId);
        this.mSubIdTelephonyManagerMap.put(Integer.valueOf(this.mDefaultDataSubId), this.mTelephonyManager);
        InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(this.mDefaultDataSubId);
        this.mSubIdTelephonyCallbackMap.put(Integer.valueOf(this.mDefaultDataSubId), internetTelephonyCallback);
        this.mTelephonyManager.registerTelephonyCallback(this.mExecutor, internetTelephonyCallback);
        this.mConnectivityManager.registerDefaultNetworkCallback(this.mConnectivityManagerNetworkCallback);
        this.mCanConfigWifi = z;
        scanWifiAccessPoints();
    }

    public void onStop() {
        if (DEBUG) {
            Log.d("InternetDialogController", "onStop");
        }
        this.mBroadcastDispatcher.unregisterReceiver(this.mConnectionStateReceiver);
        for (TelephonyManager telephonyManager : this.mSubIdTelephonyManagerMap.values()) {
            TelephonyCallback telephonyCallback = this.mSubIdTelephonyCallbackMap.get(Integer.valueOf(telephonyManager.getSubscriptionId()));
            if (telephonyCallback != null) {
                telephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (DEBUG) {
                Log.e("InternetDialogController", "Unexpected null telephony call back for Sub " + telephonyManager.getSubscriptionId());
            }
        }
        this.mSubscriptionManager.removeOnSubscriptionsChangedListener(this.mOnSubscriptionsChangedListener);
        this.mAccessPointController.removeAccessPointCallback(this);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mConnectivityManagerNetworkCallback);
        this.mConnectedWifiInternetMonitor.unregisterCallback();
    }

    public final void scanWifiAccessPoints() {
        if (this.mCanConfigWifi) {
            this.mAccessPointController.scanForAccessPoints();
        }
    }

    public void setAirplaneModeDisabled() {
        this.mConnectivityManager.setAirplaneMode(false);
    }

    public void setAutoDataSwitchMobileDataPolicy(int i, boolean z) {
        TelephonyManager orDefault = this.mSubIdTelephonyManagerMap.getOrDefault(Integer.valueOf(i), this.mTelephonyManager);
        if (orDefault != null) {
            orDefault.setMobileDataPolicyEnabled(3, z);
        } else if (DEBUG) {
            Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
        }
    }

    /* renamed from: setMergedCarrierWifiEnabledIfNeed */
    public void lambda$setMobileDataEnabled$12(int i, boolean z) {
        if (this.mCarrierConfigTracker.getCarrierProvisionsWifiMergedNetworksBool(i)) {
            return;
        }
        MergedCarrierEntry mergedCarrierEntry = this.mAccessPointController.getMergedCarrierEntry();
        if (mergedCarrierEntry != null) {
            mergedCarrierEntry.setEnabled(z);
        } else if (DEBUG) {
            Log.d("InternetDialogController", "MergedCarrierEntry is null, can not set the status.");
        }
    }

    public void setMobileDataEnabled(Context context, final int i, final boolean z, boolean z2) {
        List<SubscriptionInfo> activeSubscriptionInfoList;
        TelephonyManager telephonyManager = this.mTelephonyManager;
        if (telephonyManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "TelephonyManager is null, can not set mobile data.");
            }
        } else if (this.mSubscriptionManager == null) {
            if (DEBUG) {
                Log.d("InternetDialogController", "SubscriptionManager is null, can not set mobile data.");
            }
        } else {
            telephonyManager.setDataEnabledForReason(0, z);
            if (z2 && (activeSubscriptionInfoList = this.mSubscriptionManager.getActiveSubscriptionInfoList()) != null) {
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    if (subscriptionInfo.getSubscriptionId() != i && !subscriptionInfo.isOpportunistic()) {
                        ((TelephonyManager) context.getSystemService(TelephonyManager.class)).createForSubscriptionId(subscriptionInfo.getSubscriptionId()).setDataEnabled(false);
                    }
                }
            }
            this.mWorkerHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.dialog.InternetDialogController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    InternetDialogController.m4077$r8$lambda$Y07hn1tX7HdFFUSB6Ju8diJbzU(InternetDialogController.this, i, z);
                }
            });
        }
    }

    public void setWifiEnabled(boolean z) {
        this.mWifiStateWorker.setWifiEnabled(z);
    }

    public final boolean shouldInflateSignalStrength(int i) {
        return SignalStrengthUtil.shouldInflateSignalStrength(this.mContext, i);
    }

    public final void startActivity(Intent intent, View view) {
        ActivityLaunchAnimator.Controller createActivityLaunchController = this.mDialogLaunchAnimator.createActivityLaunchController(view);
        if (createActivityLaunchController == null) {
            this.mCallback.dismissDialog();
        }
        this.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0, createActivityLaunchController);
    }

    public final void updateListener() {
        int defaultDataSubscriptionId = getDefaultDataSubscriptionId();
        if (this.mDefaultDataSubId == getDefaultDataSubscriptionId()) {
            if (DEBUG) {
                Log.d("InternetDialogController", "DDS: no change");
                return;
            }
            return;
        }
        boolean z = DEBUG;
        if (z) {
            Log.d("InternetDialogController", "DDS: defaultDataSubId:" + defaultDataSubscriptionId);
        }
        if (SubscriptionManager.isUsableSubscriptionId(defaultDataSubscriptionId)) {
            TelephonyCallback telephonyCallback = this.mSubIdTelephonyCallbackMap.get(Integer.valueOf(this.mDefaultDataSubId));
            if (telephonyCallback != null) {
                this.mTelephonyManager.unregisterTelephonyCallback(telephonyCallback);
            } else if (z) {
                Log.e("InternetDialogController", "Unexpected null telephony call back for Sub " + this.mDefaultDataSubId);
            }
            this.mSubIdTelephonyCallbackMap.remove(Integer.valueOf(this.mDefaultDataSubId));
            this.mSubIdTelephonyDisplayInfoMap.remove(Integer.valueOf(this.mDefaultDataSubId));
            this.mSubIdTelephonyManagerMap.remove(Integer.valueOf(this.mDefaultDataSubId));
            this.mTelephonyManager = this.mTelephonyManager.createForSubscriptionId(defaultDataSubscriptionId);
            this.mSubIdTelephonyManagerMap.put(Integer.valueOf(defaultDataSubscriptionId), this.mTelephonyManager);
            InternetTelephonyCallback internetTelephonyCallback = new InternetTelephonyCallback(defaultDataSubscriptionId);
            this.mSubIdTelephonyCallbackMap.put(Integer.valueOf(defaultDataSubscriptionId), internetTelephonyCallback);
            TelephonyManager telephonyManager = this.mTelephonyManager;
            Handler handler = this.mHandler;
            Objects.requireNonNull(handler);
            telephonyManager.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), internetTelephonyCallback);
            this.mCallback.onSubscriptionsChanged(defaultDataSubscriptionId);
        }
        this.mDefaultDataSubId = defaultDataSubscriptionId;
    }
}