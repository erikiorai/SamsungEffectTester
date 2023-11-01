package com.android.systemui.battery;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArraySet;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/battery/BatteryMeterViewController.class */
public class BatteryMeterViewController extends ViewController<BatteryMeterView> {
    public final BatteryController mBatteryController;
    public boolean mBatteryHidden;
    public final BatteryController.BatteryStateChangeCallback mBatteryStateChangeCallback;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public final ContentResolver mContentResolver;
    public boolean mIgnoreTunerUpdates;
    public boolean mIsSubscribedForTunerUpdates;
    public final Handler mMainHandler;
    public final SettingObserver mSettingObserver;
    public final String mSlotBattery;
    public final TunerService.Tunable mTunable;
    public final TunerService mTunerService;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/battery/BatteryMeterViewController$SettingObserver.class */
    public final class SettingObserver extends ContentObserver {
        public SettingObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z, uri);
            ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updateShowPercent();
            if (TextUtils.equals(uri.getLastPathSegment(), "battery_estimates_last_update_time")) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updatePercentText();
            }
            if (TextUtils.equals(uri.getLastPathSegment(), "status_bar_battery_style")) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updateBatteryStyle();
            }
        }
    }

    public BatteryMeterViewController(BatteryMeterView batteryMeterView, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, final BatteryController batteryController) {
        super(batteryMeterView);
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.battery.BatteryMeterViewController.1
            public void onDensityOrFontScaleChanged() {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).scaleBatteryMeterViews();
            }
        };
        this.mTunable = new TunerService.Tunable() { // from class: com.android.systemui.battery.BatteryMeterViewController.2
            public void onTuningChanged(String str, String str2) {
                if ("icon_blacklist".equals(str)) {
                    ArraySet iconHideList = StatusBarIconController.getIconHideList(BatteryMeterViewController.this.getContext(), str2);
                    BatteryMeterViewController batteryMeterViewController = BatteryMeterViewController.this;
                    batteryMeterViewController.mBatteryHidden = iconHideList.contains(batteryMeterViewController.mSlotBattery);
                    ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).setVisibility(BatteryMeterViewController.this.mBatteryHidden ? 8 : 0);
                    if (BatteryMeterViewController.this.mBatteryHidden) {
                        return;
                    }
                    ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updateBatteryStyle();
                }
            }
        };
        this.mBatteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.battery.BatteryMeterViewController.3
            public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).onBatteryLevelChanged(i, z);
            }

            public void onBatteryUnknownStateChanged(boolean z) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).onBatteryUnknownStateChanged(z);
            }

            public void onIsOverheatedChanged(boolean z) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).onIsOverheatedChanged(z);
            }

            public void onPowerSaveChanged(boolean z) {
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).onPowerSaveChanged(z);
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.battery.BatteryMeterViewController.4
            public void onUserChanged(int i, Context context) {
                BatteryMeterViewController.this.mContentResolver.unregisterContentObserver(BatteryMeterViewController.this.mSettingObserver);
                BatteryMeterViewController.this.registerShowBatteryPercentObserver(i);
                ((BatteryMeterView) ((ViewController) BatteryMeterViewController.this).mView).updateShowPercent();
            }
        };
        this.mUserTracker = userTracker;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mMainHandler = handler;
        this.mContentResolver = contentResolver;
        this.mBatteryController = batteryController;
        Objects.requireNonNull(batteryController);
        ((BatteryMeterView) ((ViewController) this).mView).setBatteryEstimateFetcher(new BatteryMeterView.BatteryEstimateFetcher() { // from class: com.android.systemui.battery.BatteryMeterViewController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.battery.BatteryMeterView.BatteryEstimateFetcher
            public final void fetchBatteryTimeRemainingEstimate(BatteryController.EstimateFetchCompletion estimateFetchCompletion) {
                batteryController.getEstimatedTimeRemainingString(estimateFetchCompletion);
            }
        });
        ((BatteryMeterView) ((ViewController) this).mView).setDisplayShieldEnabled(featureFlags.isEnabled(Flags.BATTERY_SHIELD_ICON));
        this.mSlotBattery = getResources().getString(17041589);
        this.mSettingObserver = new SettingObserver(handler);
    }

    public void ignoreTunerUpdates() {
        this.mIgnoreTunerUpdates = true;
        unsubscribeFromTunerUpdates();
    }

    public void onViewAttached() {
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        subscribeForTunerUpdates();
        this.mBatteryController.addCallback(this.mBatteryStateChangeCallback);
        registerShowBatteryPercentObserver(this.mUserTracker.getUserId());
        registerGlobalBatteryUpdateObserver();
        this.mUserTracker.addCallback(this.mUserChangedCallback, new HandlerExecutor(this.mMainHandler));
        ((BatteryMeterView) ((ViewController) this).mView).updateShowPercent();
    }

    public void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        unsubscribeFromTunerUpdates();
        this.mBatteryController.removeCallback(this.mBatteryStateChangeCallback);
        this.mUserTracker.removeCallback(this.mUserChangedCallback);
        this.mContentResolver.unregisterContentObserver(this.mSettingObserver);
    }

    public final void registerGlobalBatteryUpdateObserver() {
        this.mContentResolver.registerContentObserver(Settings.Global.getUriFor("battery_estimates_last_update_time"), false, this.mSettingObserver);
    }

    public final void registerShowBatteryPercentObserver(int i) {
        this.mContentResolver.registerContentObserver(Settings.System.getUriFor("status_bar_show_battery_percent"), false, this.mSettingObserver, i);
        this.mContentResolver.registerContentObserver(Settings.System.getUriFor("status_bar_battery_style"), false, this.mSettingObserver, i);
    }

    public final void subscribeForTunerUpdates() {
        if (this.mIsSubscribedForTunerUpdates || this.mIgnoreTunerUpdates) {
            return;
        }
        this.mTunerService.addTunable(this.mTunable, new String[]{"icon_blacklist"});
        this.mIsSubscribedForTunerUpdates = true;
    }

    public final void unsubscribeFromTunerUpdates() {
        if (this.mIsSubscribedForTunerUpdates) {
            this.mTunerService.removeTunable(this.mTunable);
            this.mIsSubscribedForTunerUpdates = false;
        }
    }
}