package com.android.systemui.dreams;

import android.app.AlarmManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.text.format.DateFormat;
import android.util.PluralsMessageFormatter;
import com.android.systemui.R$string;
import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.time.DateFormatUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarViewController.class */
public class DreamOverlayStatusBarViewController extends ViewController<DreamOverlayStatusBarView> {
    public final AlarmManager mAlarmManager;
    public final ConnectivityManager mConnectivityManager;
    public final DateFormatUtil mDateFormatUtil;
    public final Optional<DreamOverlayNotificationCountProvider> mDreamOverlayNotificationCountProvider;
    public final DreamOverlayStateController.Callback mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public boolean mEntryAnimationsFinished;
    public final List<DreamOverlayStatusBarItemsProvider.StatusBarItem> mExtraStatusBarItems;
    public boolean mIsAttached;
    public final Executor mMainExecutor;
    public final ConnectivityManager.NetworkCallback mNetworkCallback;
    public final NetworkRequest mNetworkRequest;
    public final NextAlarmController.NextAlarmChangeCallback mNextAlarmCallback;
    public final NextAlarmController mNextAlarmController;
    public final DreamOverlayNotificationCountProvider.Callback mNotificationCountCallback;
    public final Resources mResources;
    public final IndividualSensorPrivacyController.Callback mSensorCallback;
    public final IndividualSensorPrivacyController mSensorPrivacyController;
    public final DreamOverlayStatusBarItemsProvider mStatusBarItemsProvider;
    public final DreamOverlayStatusBarItemsProvider.Callback mStatusBarItemsProviderCallback;
    public final StatusBarWindowStateController mStatusBarWindowStateController;
    public final TouchInsetManager.TouchInsetSession mTouchInsetSession;
    public final ZenModeController.Callback mZenModeCallback;
    public final ZenModeController mZenModeController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda2.onSensorBlockedChanged(int, boolean):void] */
    public static /* synthetic */ void $r8$lambda$0mCEIaBfXEFYS2CZXlzWIXmfnLA(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i, boolean z) {
        dreamOverlayStatusBarViewController.lambda$new$0(i, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3.onNextAlarmChanged(android.app.AlarmManager$AlarmClockInfo):void] */
    public static /* synthetic */ void $r8$lambda$5GYNnktlvUrQEpU6HWZY6P_rnZI(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, AlarmManager.AlarmClockInfo alarmClockInfo) {
        dreamOverlayStatusBarViewController.lambda$new$1(alarmClockInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4.onNotificationCountChanged(int):void] */
    /* renamed from: $r8$lambda$6wJm0bby0fngbIyzrC4j-APXkA8 */
    public static /* synthetic */ void m2568$r8$lambda$6wJm0bby0fngbIyzrC4jAPXkA8(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i) {
        dreamOverlayStatusBarViewController.lambda$new$2(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$AVCIpFsy00ZyAzLw3f84bIJzJxo(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider) {
        dreamOverlayStatusBarViewController.lambda$onViewAttached$3(dreamOverlayNotificationCountProvider);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$FWJEADeDL984WZO86hF8lRNG4p0(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController) {
        dreamOverlayStatusBarViewController.updateVisibility();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$MEkdPtp5VpJgDtL9MVrsNeUPIxg(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider) {
        dreamOverlayStatusBarViewController.lambda$onViewDetached$4(dreamOverlayNotificationCountProvider);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda8.run():void] */
    public static /* synthetic */ void $r8$lambda$UiadcpIbGAAA4FTeVDLkvfVlSU0(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i, boolean z, String str) {
        dreamOverlayStatusBarViewController.lambda$showIcon$5(i, z, str);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5.onStatusBarItemsChanged(java.util.List):void] */
    public static /* synthetic */ void $r8$lambda$WFNQAkalumFwz4amMPlsQmisxxA(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, List list) {
        dreamOverlayStatusBarViewController.onStatusBarItemsChanged(list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$ck_GGtOsdyv3ntzrHqPOnXzKFzU(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, List list) {
        dreamOverlayStatusBarViewController.lambda$onStatusBarItemsChanged$6(list);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6.onStatusBarWindowStateChanged(int):void] */
    public static /* synthetic */ void $r8$lambda$hgUjWXuxn0VYiYIV4HLb_SWchIw(DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, int i) {
        dreamOverlayStatusBarViewController.onSystemStatusBarStateChanged(i);
    }

    public DreamOverlayStatusBarViewController(DreamOverlayStatusBarView dreamOverlayStatusBarView, Resources resources, Executor executor, ConnectivityManager connectivityManager, TouchInsetManager.TouchInsetSession touchInsetSession, AlarmManager alarmManager, NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil, IndividualSensorPrivacyController individualSensorPrivacyController, Optional<DreamOverlayNotificationCountProvider> optional, ZenModeController zenModeController, StatusBarWindowStateController statusBarWindowStateController, DreamOverlayStatusBarItemsProvider dreamOverlayStatusBarItemsProvider, DreamOverlayStateController dreamOverlayStateController) {
        super(dreamOverlayStatusBarView);
        this.mExtraStatusBarItems = new ArrayList();
        this.mEntryAnimationsFinished = false;
        this.mNetworkRequest = new NetworkRequest.Builder().clearCapabilities().addTransportType(1).build();
        this.mNetworkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.1
            {
                DreamOverlayStatusBarViewController.this = this;
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network) {
                DreamOverlayStatusBarViewController.this.updateWifiUnavailableStatusIcon();
            }
        };
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.2
            {
                DreamOverlayStatusBarViewController.this = this;
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onStateChanged() {
                DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController = DreamOverlayStatusBarViewController.this;
                dreamOverlayStatusBarViewController.mEntryAnimationsFinished = dreamOverlayStatusBarViewController.mDreamOverlayStateController.areEntryAnimationsFinished();
                DreamOverlayStatusBarViewController.this.updateVisibility();
            }
        };
        this.mSensorCallback = new IndividualSensorPrivacyController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda2
            public final void onSensorBlockedChanged(int i, boolean z) {
                DreamOverlayStatusBarViewController.$r8$lambda$0mCEIaBfXEFYS2CZXlzWIXmfnLA(DreamOverlayStatusBarViewController.this, i, z);
            }
        };
        this.mNextAlarmCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda3
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                DreamOverlayStatusBarViewController.$r8$lambda$5GYNnktlvUrQEpU6HWZY6P_rnZI(DreamOverlayStatusBarViewController.this, alarmClockInfo);
            }
        };
        this.mZenModeCallback = new ZenModeController.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController.3
            {
                DreamOverlayStatusBarViewController.this = this;
            }

            public void onZenChanged(int i) {
                DreamOverlayStatusBarViewController.this.updatePriorityModeStatusIcon();
            }
        };
        this.mNotificationCountCallback = new DreamOverlayNotificationCountProvider.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.dreams.DreamOverlayNotificationCountProvider.Callback
            public final void onNotificationCountChanged(int i) {
                DreamOverlayStatusBarViewController.m2568$r8$lambda$6wJm0bby0fngbIyzrC4jAPXkA8(DreamOverlayStatusBarViewController.this, i);
            }
        };
        this.mStatusBarItemsProviderCallback = new DreamOverlayStatusBarItemsProvider.Callback() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda5
            @Override // com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider.Callback
            public final void onStatusBarItemsChanged(List list) {
                DreamOverlayStatusBarViewController.$r8$lambda$WFNQAkalumFwz4amMPlsQmisxxA(DreamOverlayStatusBarViewController.this, list);
            }
        };
        this.mResources = resources;
        this.mMainExecutor = executor;
        this.mConnectivityManager = connectivityManager;
        this.mTouchInsetSession = touchInsetSession;
        this.mAlarmManager = alarmManager;
        this.mNextAlarmController = nextAlarmController;
        this.mDateFormatUtil = dateFormatUtil;
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mDreamOverlayNotificationCountProvider = optional;
        this.mStatusBarWindowStateController = statusBarWindowStateController;
        this.mStatusBarItemsProvider = dreamOverlayStatusBarItemsProvider;
        this.mZenModeController = zenModeController;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        statusBarWindowStateController.addListener(new StatusBarWindowStateListener() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda6
            public final void onStatusBarWindowStateChanged(int i) {
                DreamOverlayStatusBarViewController.$r8$lambda$hgUjWXuxn0VYiYIV4HLb_SWchIw(DreamOverlayStatusBarViewController.this, i);
            }
        });
    }

    public /* synthetic */ void lambda$new$0(int i, boolean z) {
        updateMicCameraBlockedStatusIcon();
    }

    public /* synthetic */ void lambda$new$1(AlarmManager.AlarmClockInfo alarmClockInfo) {
        updateAlarmStatusIcon();
    }

    public /* synthetic */ void lambda$new$2(int i) {
        showIcon(0, i > 0, i > 0 ? buildNotificationsContentDescription(i) : null);
    }

    public /* synthetic */ void lambda$onStatusBarItemsChanged$6(List list) {
        this.mExtraStatusBarItems.clear();
        this.mExtraStatusBarItems.addAll(list);
        ((DreamOverlayStatusBarView) ((ViewController) this).mView).setExtraStatusBarItemViews((List) list.stream().map(new Function() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda9
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DreamOverlayStatusBarItemsProvider.StatusBarItem) obj).getView();
            }
        }).collect(Collectors.toList()));
    }

    public /* synthetic */ void lambda$onViewAttached$3(DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider) {
        dreamOverlayNotificationCountProvider.addCallback(this.mNotificationCountCallback);
    }

    public /* synthetic */ void lambda$onViewDetached$4(DreamOverlayNotificationCountProvider dreamOverlayNotificationCountProvider) {
        dreamOverlayNotificationCountProvider.removeCallback(this.mNotificationCountCallback);
    }

    public /* synthetic */ void lambda$showIcon$5(int i, boolean z, String str) {
        if (this.mIsAttached) {
            ((DreamOverlayStatusBarView) ((ViewController) this).mView).showIcon(i, z, str);
        }
    }

    public final String buildAlarmContentDescription(AlarmManager.AlarmClockInfo alarmClockInfo) {
        return this.mResources.getString(R$string.accessibility_quick_settings_alarm, DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), this.mDateFormatUtil.is24HourFormat() ? "EHm" : "Ehma"), alarmClockInfo.getTriggerTime()).toString());
    }

    public final String buildNotificationsContentDescription(int i) {
        return PluralsMessageFormatter.format(this.mResources, Map.of("count", Integer.valueOf(i)), R$string.dream_overlay_status_bar_notification_indicator);
    }

    public final void onStatusBarItemsChanged(final List<DreamOverlayStatusBarItemsProvider.StatusBarItem> list) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStatusBarViewController.$r8$lambda$ck_GGtOsdyv3ntzrHqPOnXzKFzU(DreamOverlayStatusBarViewController.this, list);
            }
        });
    }

    public final void onSystemStatusBarStateChanged(int i) {
        if (this.mIsAttached && this.mEntryAnimationsFinished) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    DreamOverlayStatusBarViewController.$r8$lambda$FWJEADeDL984WZO86hF8lRNG4p0(DreamOverlayStatusBarViewController.this);
                }
            });
        }
    }

    public void onViewAttached() {
        this.mIsAttached = true;
        this.mConnectivityManager.registerNetworkCallback(this.mNetworkRequest, this.mNetworkCallback);
        updateWifiUnavailableStatusIcon();
        this.mNextAlarmController.addCallback(this.mNextAlarmCallback);
        updateAlarmStatusIcon();
        this.mSensorPrivacyController.addCallback(this.mSensorCallback);
        updateMicCameraBlockedStatusIcon();
        this.mZenModeController.addCallback(this.mZenModeCallback);
        updatePriorityModeStatusIcon();
        this.mDreamOverlayNotificationCountProvider.ifPresent(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamOverlayStatusBarViewController.$r8$lambda$AVCIpFsy00ZyAzLw3f84bIJzJxo(DreamOverlayStatusBarViewController.this, (DreamOverlayNotificationCountProvider) obj);
            }
        });
        this.mStatusBarItemsProvider.addCallback(this.mStatusBarItemsProviderCallback);
        this.mDreamOverlayStateController.addCallback(this.mDreamOverlayStateCallback);
        this.mTouchInsetSession.addViewToTracking(((ViewController) this).mView);
    }

    public void onViewDetached() {
        this.mZenModeController.removeCallback(this.mZenModeCallback);
        this.mSensorPrivacyController.removeCallback(this.mSensorCallback);
        this.mNextAlarmController.removeCallback(this.mNextAlarmCallback);
        this.mConnectivityManager.unregisterNetworkCallback(this.mNetworkCallback);
        this.mDreamOverlayNotificationCountProvider.ifPresent(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DreamOverlayStatusBarViewController.$r8$lambda$MEkdPtp5VpJgDtL9MVrsNeUPIxg(DreamOverlayStatusBarViewController.this, (DreamOverlayNotificationCountProvider) obj);
            }
        });
        this.mStatusBarItemsProvider.removeCallback(this.mStatusBarItemsProviderCallback);
        ((DreamOverlayStatusBarView) ((ViewController) this).mView).removeAllExtraStatusBarItemViews();
        this.mDreamOverlayStateController.removeCallback(this.mDreamOverlayStateCallback);
        this.mTouchInsetSession.clear();
        this.mIsAttached = false;
    }

    public void setFadeAmount(float f, boolean z) {
        updateVisibility();
        if (((DreamOverlayStatusBarView) ((ViewController) this).mView).getVisibility() != 0) {
            return;
        }
        if (z) {
            CrossFadeHelper.fadeOut(((ViewController) this).mView, 1.0f - f, false);
        } else {
            CrossFadeHelper.fadeIn(((ViewController) this).mView, f, false);
        }
    }

    public void setTranslationY(float f) {
        ((DreamOverlayStatusBarView) ((ViewController) this).mView).setTranslationY(f);
    }

    public final boolean shouldShowStatusBar() {
        return (this.mDreamOverlayStateController.isLowLightActive() || this.mStatusBarWindowStateController.windowIsShowing()) ? false : true;
    }

    public final void showIcon(int i, boolean z) {
        showIcon(i, z, null);
    }

    public final void showIcon(final int i, final boolean z, final String str) {
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.dreams.DreamOverlayStatusBarViewController$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                DreamOverlayStatusBarViewController.$r8$lambda$UiadcpIbGAAA4FTeVDLkvfVlSU0(DreamOverlayStatusBarViewController.this, i, z, str);
            }
        });
    }

    public final void updateAlarmStatusIcon() {
        AlarmManager.AlarmClockInfo nextAlarmClock = this.mAlarmManager.getNextAlarmClock(-2);
        boolean z = nextAlarmClock != null && nextAlarmClock.getTriggerTime() > 0;
        showIcon(2, z, z ? buildAlarmContentDescription(nextAlarmClock) : null);
    }

    public final void updateMicCameraBlockedStatusIcon() {
        boolean isSensorBlocked = this.mSensorPrivacyController.isSensorBlocked(1);
        boolean isSensorBlocked2 = this.mSensorPrivacyController.isSensorBlocked(2);
        showIcon(3, !isSensorBlocked && isSensorBlocked2);
        showIcon(4, isSensorBlocked && !isSensorBlocked2);
        showIcon(5, isSensorBlocked && isSensorBlocked2);
    }

    public final void updatePriorityModeStatusIcon() {
        showIcon(6, this.mZenModeController.getZen() != 0);
    }

    public final void updateVisibility() {
        if (shouldShowStatusBar()) {
            ((DreamOverlayStatusBarView) ((ViewController) this).mView).setVisibility(0);
        } else {
            ((DreamOverlayStatusBarView) ((ViewController) this).mView).setVisibility(4);
        }
    }

    public final void updateWifiUnavailableStatusIcon() {
        ConnectivityManager connectivityManager = this.mConnectivityManager;
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        showIcon(1, !(networkCapabilities != null && networkCapabilities.hasTransport(1)));
    }
}