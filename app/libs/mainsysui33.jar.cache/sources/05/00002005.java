package com.android.systemui.power;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.IThermalEventListener;
import android.os.IThermalService;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Temperature;
import android.provider.Settings;
import android.util.Log;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$integer;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.power.PowerUI;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Future;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI.class */
public class PowerUI implements CoreStartable, CommandQueue.Callbacks {
    public static final boolean DEBUG = Log.isLoggable("PowerUI", 3);
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    @VisibleForTesting
    public BatteryStateSnapshot mCurrentBatteryStateSnapshot;
    public boolean mEnableSkinTemperatureWarning;
    public boolean mEnableUsbTemperatureAlarm;
    public final EnhancedEstimates mEnhancedEstimates;
    @VisibleForTesting
    public BatteryStateSnapshot mLastBatteryStateSnapshot;
    public Future mLastShowWarningTask;
    public int mLowBatteryAlertCloseLevel;
    @VisibleForTesting
    public boolean mLowWarningShownThisChargeCycle;
    public InattentiveSleepWarningView mOverlayView;
    public final PowerManager mPowerManager;
    @VisibleForTesting
    public boolean mSevereWarningShownThisChargeCycle;
    public IThermalEventListener mSkinThermalEventListener;
    @VisibleForTesting
    public IThermalService mThermalService;
    public IThermalEventListener mUsbThermalEventListener;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WarningsUI mWarnings;
    public final Handler mHandler = new Handler();
    @VisibleForTesting
    public final Receiver mReceiver = new Receiver();
    public final Configuration mLastConfiguration = new Configuration();
    public int mPlugType = 0;
    public int mInvalidCharger = 0;
    public final int[] mLowBatteryReminderLevels = new int[2];
    public long mScreenOffTime = -1;
    @VisibleForTesting
    public int mBatteryLevel = 100;
    @VisibleForTesting
    public int mBatteryStatus = 1;
    public final WakefulnessLifecycle.Observer mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.power.PowerUI.1
        {
            PowerUI.this = this;
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onFinishedGoingToSleep() {
            PowerUI.this.mScreenOffTime = SystemClock.elapsedRealtime();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public void onStartedWakingUp() {
            PowerUI.this.mScreenOffTime = -1L;
        }
    };
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.power.PowerUI.2
        {
            PowerUI.this = this;
        }

        public void onUserChanged(int i, Context context) {
            PowerUI.this.mWarnings.userSwitched();
        }
    };

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI$Receiver.class */
    public final class Receiver extends BroadcastReceiver {
        public boolean mHasReceivedBattery = false;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$LgPsgH30Xtyv_Be4gFySfDoAypE(Receiver receiver, boolean z, int i) {
            receiver.lambda$onReceive$1(z, i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$kLNEfjeZS_vcKL6a9dUt9KPdf_4(Receiver receiver) {
            receiver.lambda$onReceive$0();
        }

        public Receiver() {
            PowerUI.this = r4;
        }

        public /* synthetic */ void lambda$onReceive$0() {
            if (PowerUI.this.mPowerManager.isPowerSaveMode()) {
                PowerUI.this.mWarnings.dismissLowBatteryWarning();
            }
        }

        public /* synthetic */ void lambda$onReceive$1(boolean z, int i) {
            PowerUI.this.maybeShowBatteryWarningV2(z, i);
        }

        public void init() {
            Intent registerReceiver;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.os.action.POWER_SAVE_MODE_CHANGED");
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            PowerUI.this.mBroadcastDispatcher.registerReceiverWithHandler(this, intentFilter, PowerUI.this.mHandler);
            if (this.mHasReceivedBattery || (registerReceiver = PowerUI.this.mContext.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"))) == null) {
                return;
            }
            onReceive(PowerUI.this.mContext, registerReceiver);
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.os.action.POWER_SAVE_MODE_CHANGED".equals(action)) {
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerUI.Receiver.$r8$lambda$kLNEfjeZS_vcKL6a9dUt9KPdf_4(PowerUI.Receiver.this);
                    }
                });
            } else if (!"android.intent.action.BATTERY_CHANGED".equals(action)) {
                Slog.w("PowerUI", "unknown intent: " + intent);
            } else {
                this.mHasReceivedBattery = true;
                PowerUI powerUI = PowerUI.this;
                int i = powerUI.mBatteryLevel;
                powerUI.mBatteryLevel = intent.getIntExtra("level", 100);
                PowerUI powerUI2 = PowerUI.this;
                int i2 = powerUI2.mBatteryStatus;
                powerUI2.mBatteryStatus = intent.getIntExtra("status", 1);
                int i3 = PowerUI.this.mPlugType;
                PowerUI.this.mPlugType = intent.getIntExtra("plugged", 1);
                int i4 = PowerUI.this.mInvalidCharger;
                PowerUI.this.mInvalidCharger = intent.getIntExtra("invalid_charger", 0);
                PowerUI powerUI3 = PowerUI.this;
                powerUI3.mLastBatteryStateSnapshot = powerUI3.mCurrentBatteryStateSnapshot;
                boolean z = powerUI3.mPlugType != 0;
                boolean z2 = i3 != 0;
                int findBatteryLevelBucket = PowerUI.this.findBatteryLevelBucket(i);
                PowerUI powerUI4 = PowerUI.this;
                final int findBatteryLevelBucket2 = powerUI4.findBatteryLevelBucket(powerUI4.mBatteryLevel);
                boolean z3 = PowerUI.DEBUG;
                if (z3) {
                    Slog.d("PowerUI", "buckets   ....." + PowerUI.this.mLowBatteryAlertCloseLevel + " .. " + PowerUI.this.mLowBatteryReminderLevels[0] + " .. " + PowerUI.this.mLowBatteryReminderLevels[1]);
                    StringBuilder sb = new StringBuilder();
                    sb.append("level          ");
                    sb.append(i);
                    sb.append(" --> ");
                    sb.append(PowerUI.this.mBatteryLevel);
                    Slog.d("PowerUI", sb.toString());
                    Slog.d("PowerUI", "status         " + i2 + " --> " + PowerUI.this.mBatteryStatus);
                    Slog.d("PowerUI", "plugType       " + i3 + " --> " + PowerUI.this.mPlugType);
                    Slog.d("PowerUI", "invalidCharger " + i4 + " --> " + PowerUI.this.mInvalidCharger);
                    Slog.d("PowerUI", "bucket         " + findBatteryLevelBucket + " --> " + findBatteryLevelBucket2);
                    Slog.d("PowerUI", "plugged        " + z2 + " --> " + z);
                }
                WarningsUI warningsUI = PowerUI.this.mWarnings;
                PowerUI powerUI5 = PowerUI.this;
                warningsUI.update(powerUI5.mBatteryLevel, findBatteryLevelBucket2, powerUI5.mScreenOffTime);
                if (i4 == 0 && PowerUI.this.mInvalidCharger != 0) {
                    Slog.d("PowerUI", "showing invalid charger warning");
                    PowerUI.this.mWarnings.showInvalidChargerWarning();
                    return;
                }
                if (i4 != 0 && PowerUI.this.mInvalidCharger == 0) {
                    PowerUI.this.mWarnings.dismissInvalidChargerWarning();
                } else if (PowerUI.this.mWarnings.isInvalidChargerWarningShowing()) {
                    if (z3) {
                        Slog.d("PowerUI", "Bad Charger");
                        return;
                    }
                    return;
                }
                if (PowerUI.this.mLastShowWarningTask != null) {
                    PowerUI.this.mLastShowWarningTask.cancel(true);
                    if (z3) {
                        Slog.d("PowerUI", "cancelled task");
                    }
                }
                final boolean z4 = z;
                PowerUI.this.mLastShowWarningTask = ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.systemui.power.PowerUI$Receiver$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        PowerUI.Receiver.$r8$lambda$LgPsgH30Xtyv_Be4gFySfDoAypE(PowerUI.Receiver.this, z4, findBatteryLevelBucket2);
                    }
                });
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI$SkinThermalEventListener.class */
    public final class SkinThermalEventListener extends IThermalEventListener.Stub {
        public SkinThermalEventListener() {
            PowerUI.this = r4;
        }

        public void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status < 5) {
                PowerUI.this.mWarnings.dismissHighTemperatureWarning();
            } else if (((Boolean) ((Optional) PowerUI.this.mCentralSurfacesOptionalLazy.get()).map(new Function() { // from class: com.android.systemui.power.PowerUI$SkinThermalEventListener$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Boolean.valueOf(((CentralSurfaces) obj).isDeviceInVrMode());
                }
            }).orElse(Boolean.FALSE)).booleanValue()) {
            } else {
                PowerUI.this.mWarnings.showHighTemperatureWarning();
                Slog.d("PowerUI", "SkinThermalEventListener: notifyThrottling was called , current skin status = " + status + ", temperature = " + temperature.getValue());
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI$UsbThermalEventListener.class */
    public final class UsbThermalEventListener extends IThermalEventListener.Stub {
        public UsbThermalEventListener() {
            PowerUI.this = r4;
        }

        public void notifyThrottling(Temperature temperature) {
            int status = temperature.getStatus();
            if (status >= 5) {
                PowerUI.this.mWarnings.showUsbHighTemperatureAlarm();
                Slog.d("PowerUI", "UsbThermalEventListener: notifyThrottling was called , current usb port status = " + status + ", temperature = " + temperature.getValue());
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI$WarningsUI.class */
    public interface WarningsUI {
        void dismissHighTemperatureWarning();

        void dismissInvalidChargerWarning();

        void dismissLowBatteryWarning();

        void dump(PrintWriter printWriter);

        boolean isInvalidChargerWarningShowing();

        void showHighTemperatureWarning();

        void showInvalidChargerWarning();

        void showLowBatteryWarning(boolean z);

        void showThermalShutdownWarning();

        void showUsbHighTemperatureAlarm();

        void update(int i, int i2, long j);

        void updateLowBatteryWarning();

        void updateSnapshot(BatteryStateSnapshot batteryStateSnapshot);

        void userSwitched();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$RV10pGPWbWiFRF4HsbLl2gxn-Hs */
    public static /* synthetic */ void m3638$r8$lambda$RV10pGPWbWiFRF4HsbLl2gxnHs(PowerUI powerUI) {
        powerUI.initThermalEventListeners();
    }

    public PowerUI(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, Lazy<Optional<CentralSurfaces>> lazy, WarningsUI warningsUI, EnhancedEstimates enhancedEstimates, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker) {
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mCommandQueue = commandQueue;
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mWarnings = warningsUI;
        this.mEnhancedEstimates = enhancedEstimates;
        this.mPowerManager = powerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mUserTracker = userTracker;
    }

    public void dismissInattentiveSleepWarning(boolean z) {
        InattentiveSleepWarningView inattentiveSleepWarningView = this.mOverlayView;
        if (inattentiveSleepWarningView != null) {
            inattentiveSleepWarningView.dismiss(z);
        }
    }

    @VisibleForTesting
    public void doSkinThermalEventListenerRegistration() {
        boolean z;
        synchronized (this) {
            boolean z2 = this.mEnableSkinTemperatureWarning;
            boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_temperature_warning", this.mContext.getResources().getInteger(R$integer.config_showTemperatureWarning)) != 0;
            this.mEnableSkinTemperatureWarning = z3;
            if (z3 != z2) {
                try {
                    if (this.mSkinThermalEventListener == null) {
                        this.mSkinThermalEventListener = new SkinThermalEventListener();
                    }
                    if (this.mThermalService == null) {
                        this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                    }
                    z = this.mEnableSkinTemperatureWarning ? this.mThermalService.registerThermalEventListenerWithType(this.mSkinThermalEventListener, 3) : this.mThermalService.unregisterThermalEventListener(this.mSkinThermalEventListener);
                } catch (RemoteException e) {
                    Slog.e("PowerUI", "Exception while (un)registering skin thermal event listener.", e);
                    z = false;
                }
                if (!z) {
                    this.mEnableSkinTemperatureWarning = !this.mEnableSkinTemperatureWarning;
                    Slog.e("PowerUI", "Failed to register or unregister skin thermal event listener.");
                }
            }
        }
    }

    @VisibleForTesting
    public void doUsbThermalEventListenerRegistration() {
        boolean z;
        synchronized (this) {
            boolean z2 = this.mEnableUsbTemperatureAlarm;
            boolean z3 = Settings.Global.getInt(this.mContext.getContentResolver(), "show_usb_temperature_alarm", this.mContext.getResources().getInteger(R$integer.config_showUsbPortAlarm)) != 0;
            this.mEnableUsbTemperatureAlarm = z3;
            if (z3 != z2) {
                try {
                    if (this.mUsbThermalEventListener == null) {
                        this.mUsbThermalEventListener = new UsbThermalEventListener();
                    }
                    if (this.mThermalService == null) {
                        this.mThermalService = IThermalService.Stub.asInterface(ServiceManager.getService("thermalservice"));
                    }
                    z = this.mEnableUsbTemperatureAlarm ? this.mThermalService.registerThermalEventListenerWithType(this.mUsbThermalEventListener, 4) : this.mThermalService.unregisterThermalEventListener(this.mUsbThermalEventListener);
                } catch (RemoteException e) {
                    Slog.e("PowerUI", "Exception while (un)registering usb thermal event listener.", e);
                    z = false;
                }
                if (!z) {
                    this.mEnableUsbTemperatureAlarm = !this.mEnableUsbTemperatureAlarm;
                    Slog.e("PowerUI", "Failed to register or unregister usb thermal event listener.");
                }
            }
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("mLowBatteryAlertCloseLevel=");
        printWriter.println(this.mLowBatteryAlertCloseLevel);
        printWriter.print("mLowBatteryReminderLevels=");
        printWriter.println(Arrays.toString(this.mLowBatteryReminderLevels));
        printWriter.print("mBatteryLevel=");
        printWriter.println(Integer.toString(this.mBatteryLevel));
        printWriter.print("mBatteryStatus=");
        printWriter.println(Integer.toString(this.mBatteryStatus));
        printWriter.print("mPlugType=");
        printWriter.println(Integer.toString(this.mPlugType));
        printWriter.print("mInvalidCharger=");
        printWriter.println(Integer.toString(this.mInvalidCharger));
        printWriter.print("mScreenOffTime=");
        printWriter.print(this.mScreenOffTime);
        if (this.mScreenOffTime >= 0) {
            printWriter.print(" (");
            printWriter.print(SystemClock.elapsedRealtime() - this.mScreenOffTime);
            printWriter.print(" ago)");
        }
        printWriter.println();
        printWriter.print("soundTimeout=");
        printWriter.println(Settings.Global.getInt(this.mContext.getContentResolver(), "low_battery_sound_timeout", 0));
        printWriter.print("bucket: ");
        printWriter.println(Integer.toString(findBatteryLevelBucket(this.mBatteryLevel)));
        printWriter.print("mEnableSkinTemperatureWarning=");
        printWriter.println(this.mEnableSkinTemperatureWarning);
        printWriter.print("mEnableUsbTemperatureAlarm=");
        printWriter.println(this.mEnableUsbTemperatureAlarm);
        this.mWarnings.dump(printWriter);
    }

    public final int findBatteryLevelBucket(int i) {
        if (i >= this.mLowBatteryAlertCloseLevel) {
            return 1;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        if (i > iArr[0]) {
            return 0;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (i <= this.mLowBatteryReminderLevels[length]) {
                return (-1) - length;
            }
        }
        throw new RuntimeException("not possible!");
    }

    public final void initThermalEventListeners() {
        doSkinThermalEventListenerRegistration();
        doUsbThermalEventListenerRegistration();
    }

    public void maybeShowBatteryWarningV2(boolean z, int i) {
        boolean isHybridNotificationEnabled = this.mEnhancedEstimates.isHybridNotificationEnabled();
        boolean isPowerSaveMode = this.mPowerManager.isPowerSaveMode();
        boolean z2 = DEBUG;
        if (z2) {
            Slog.d("PowerUI", "evaluating which notification to show");
        }
        if (isHybridNotificationEnabled) {
            if (z2) {
                Slog.d("PowerUI", "using hybrid");
            }
            Estimate refreshEstimateIfNeeded = refreshEstimateIfNeeded();
            int i2 = this.mBatteryLevel;
            int i3 = this.mBatteryStatus;
            int[] iArr = this.mLowBatteryReminderLevels;
            this.mCurrentBatteryStateSnapshot = new BatteryStateSnapshot(i2, isPowerSaveMode, z, i, i3, iArr[1], iArr[0], refreshEstimateIfNeeded.getEstimateMillis(), refreshEstimateIfNeeded.getAverageDischargeTime(), this.mEnhancedEstimates.getSevereWarningThreshold(), this.mEnhancedEstimates.getLowWarningThreshold(), refreshEstimateIfNeeded.isBasedOnUsage(), this.mEnhancedEstimates.getLowWarningEnabled());
        } else {
            if (z2) {
                Slog.d("PowerUI", "using standard");
            }
            int i4 = this.mBatteryLevel;
            int i5 = this.mBatteryStatus;
            int[] iArr2 = this.mLowBatteryReminderLevels;
            this.mCurrentBatteryStateSnapshot = new BatteryStateSnapshot(i4, isPowerSaveMode, z, i, i5, iArr2[1], iArr2[0]);
        }
        this.mWarnings.updateSnapshot(this.mCurrentBatteryStateSnapshot);
        maybeShowHybridWarning(this.mCurrentBatteryStateSnapshot, this.mLastBatteryStateSnapshot);
    }

    @VisibleForTesting
    public void maybeShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        boolean z = false;
        if (batteryStateSnapshot.getBatteryLevel() >= 30) {
            this.mLowWarningShownThisChargeCycle = false;
            this.mSevereWarningShownThisChargeCycle = false;
            if (DEBUG) {
                Slog.d("PowerUI", "Charge cycle reset! Can show warnings again");
            }
        }
        if (batteryStateSnapshot.getBucket() != batteryStateSnapshot2.getBucket() || batteryStateSnapshot2.getPlugged()) {
            z = true;
        }
        if (!shouldShowHybridWarning(batteryStateSnapshot)) {
            if (shouldDismissHybridWarning(batteryStateSnapshot)) {
                if (DEBUG) {
                    Slog.d("PowerUI", "Dismissing warning");
                }
                this.mWarnings.dismissLowBatteryWarning();
                return;
            }
            if (DEBUG) {
                Slog.d("PowerUI", "Updating warning");
            }
            this.mWarnings.updateLowBatteryWarning();
            return;
        }
        this.mWarnings.showLowBatteryWarning(z);
        if (batteryStateSnapshot.getBatteryLevel() > batteryStateSnapshot.getSevereLevelThreshold()) {
            Slog.d("PowerUI", "Low warning marked as shown this cycle");
            this.mLowWarningShownThisChargeCycle = true;
            return;
        }
        this.mSevereWarningShownThisChargeCycle = true;
        this.mLowWarningShownThisChargeCycle = true;
        if (DEBUG) {
            Slog.d("PowerUI", "Severe warning marked as shown this cycle");
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        if ((this.mLastConfiguration.updateFrom(configuration) & 3) != 0) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.power.PowerUI$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    PowerUI.m3638$r8$lambda$RV10pGPWbWiFRF4HsbLl2gxnHs(PowerUI.this);
                }
            });
        }
    }

    @VisibleForTesting
    public Estimate refreshEstimateIfNeeded() {
        BatteryStateSnapshot batteryStateSnapshot = this.mLastBatteryStateSnapshot;
        if (batteryStateSnapshot == null || batteryStateSnapshot.getTimeRemainingMillis() == -1 || this.mBatteryLevel != this.mLastBatteryStateSnapshot.getBatteryLevel()) {
            Estimate estimate = this.mEnhancedEstimates.getEstimate();
            if (DEBUG) {
                Slog.d("PowerUI", "updated estimate: " + estimate.getEstimateMillis());
            }
            return estimate;
        }
        return new Estimate(this.mLastBatteryStateSnapshot.getTimeRemainingMillis(), this.mLastBatteryStateSnapshot.isBasedOnUsage(), this.mLastBatteryStateSnapshot.getAverageTimeToDischargeMillis());
    }

    @VisibleForTesting
    public boolean shouldDismissHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        return batteryStateSnapshot.getPlugged() || batteryStateSnapshot.getBatteryLevel() > batteryStateSnapshot.getLowLevelThreshold();
    }

    @VisibleForTesting
    public boolean shouldDismissLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        return batteryStateSnapshot.isPowerSaver() || batteryStateSnapshot.getPlugged() || (batteryStateSnapshot.getBucket() > batteryStateSnapshot2.getBucket() && batteryStateSnapshot.getBucket() > 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x0060, code lost:
        if (r9 != false) goto L27;
     */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean shouldShowHybridWarning(BatteryStateSnapshot batteryStateSnapshot) {
        boolean z;
        boolean z2 = true;
        if (batteryStateSnapshot.getPlugged() || batteryStateSnapshot.getBatteryStatus() == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("can't show warning due to - plugged: ");
            sb.append(batteryStateSnapshot.getPlugged());
            sb.append(" status unknown: ");
            if (batteryStateSnapshot.getBatteryStatus() != 1) {
                z2 = false;
            }
            sb.append(z2);
            Slog.d("PowerUI", sb.toString());
            return false;
        }
        boolean z3 = (this.mLowWarningShownThisChargeCycle || batteryStateSnapshot.isPowerSaver() || batteryStateSnapshot.getBatteryLevel() > batteryStateSnapshot.getLowLevelThreshold()) ? false : true;
        boolean z4 = !this.mSevereWarningShownThisChargeCycle && batteryStateSnapshot.getBatteryLevel() <= batteryStateSnapshot.getSevereLevelThreshold();
        if (!z3) {
            z = false;
        }
        z = true;
        if (DEBUG) {
            Slog.d("PowerUI", "Enhanced trigger is: " + z + "\nwith battery snapshot: mLowWarningShownThisChargeCycle: " + this.mLowWarningShownThisChargeCycle + " mSevereWarningShownThisChargeCycle: " + this.mSevereWarningShownThisChargeCycle + "\n" + batteryStateSnapshot.toString());
        }
        return z;
    }

    @VisibleForTesting
    public boolean shouldShowLowBatteryWarning(BatteryStateSnapshot batteryStateSnapshot, BatteryStateSnapshot batteryStateSnapshot2) {
        boolean z = true;
        if (batteryStateSnapshot.getPlugged() || batteryStateSnapshot.isPowerSaver() || ((batteryStateSnapshot.getBucket() >= batteryStateSnapshot2.getBucket() && !batteryStateSnapshot2.getPlugged()) || batteryStateSnapshot.getBucket() >= 0 || batteryStateSnapshot.getBatteryStatus() == 1)) {
            z = false;
        }
        return z;
    }

    public void showInattentiveSleepWarning() {
        if (this.mOverlayView == null) {
            this.mOverlayView = new InattentiveSleepWarningView(this.mContext);
        }
        this.mOverlayView.show();
    }

    public final void showWarnOnThermalShutdown() {
        int i = -1;
        int i2 = this.mContext.getSharedPreferences("powerui_prefs", 0).getInt("boot_count", -1);
        try {
            i = Settings.Global.getInt(this.mContext.getContentResolver(), "boot_count");
        } catch (Settings.SettingNotFoundException e) {
            Slog.e("PowerUI", "Failed to read system boot count from Settings.Global.BOOT_COUNT");
        }
        if (i > i2) {
            this.mContext.getSharedPreferences("powerui_prefs", 0).edit().putInt("boot_count", i).apply();
            if (this.mPowerManager.getLastShutdownReason() == 4) {
                this.mWarnings.showThermalShutdownWarning();
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mScreenOffTime = this.mPowerManager.isScreenOn() ? -1L : SystemClock.elapsedRealtime();
        this.mLastConfiguration.setTo(this.mContext.getResources().getConfiguration());
        ContentObserver contentObserver = new ContentObserver(this.mHandler) { // from class: com.android.systemui.power.PowerUI.3
            {
                PowerUI.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                PowerUI.this.updateBatteryWarningLevels();
            }
        };
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, contentObserver, -1);
        updateBatteryWarningLevels();
        this.mReceiver.init();
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        showWarnOnThermalShutdown();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_temperature_warning"), false, new ContentObserver(this.mHandler) { // from class: com.android.systemui.power.PowerUI.4
            {
                PowerUI.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                PowerUI.this.doSkinThermalEventListenerRegistration();
            }
        });
        contentResolver.registerContentObserver(Settings.Global.getUriFor("show_usb_temperature_alarm"), false, new ContentObserver(this.mHandler) { // from class: com.android.systemui.power.PowerUI.5
            {
                PowerUI.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                PowerUI.this.doUsbThermalEventListenerRegistration();
            }
        });
        initThermalEventListeners();
        this.mCommandQueue.addCallback(this);
    }

    public void updateBatteryWarningLevels() {
        int integer = this.mContext.getResources().getInteger(17694776);
        int integer2 = this.mContext.getResources().getInteger(17694880);
        int i = integer2;
        if (integer2 < integer) {
            i = integer;
        }
        int[] iArr = this.mLowBatteryReminderLevels;
        iArr[0] = i;
        iArr[1] = integer;
        this.mLowBatteryAlertCloseLevel = i + this.mContext.getResources().getInteger(17694879);
    }
}