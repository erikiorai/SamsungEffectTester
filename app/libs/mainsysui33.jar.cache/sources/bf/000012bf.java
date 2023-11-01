package com.android.systemui.classifier;

import android.hardware.biometrics.BiometricSourceType;
import android.util.Log;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeQsExpansionListener;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.time.SystemClock;
import java.util.Collections;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingCollectorImpl.class */
public class FalsingCollectorImpl implements FalsingCollector {
    public static final boolean DEBUG = Log.isLoggable("FalsingCollector", 3);
    public boolean mAvoidGesture;
    public final BatteryController mBatteryController;
    public final BatteryController.BatteryStateChangeCallback mBatteryListener;
    public final DockManager.DockEventListener mDockEventListener;
    public final DockManager mDockManager;
    public final FalsingDataProvider mFalsingDataProvider;
    public final FalsingManager mFalsingManager;
    public final HistoryTracker mHistoryTracker;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final DelayableExecutor mMainExecutor;
    public MotionEvent mPendingDownEvent;
    public final ProximitySensor mProximitySensor;
    public boolean mScreenOn;
    public final ThresholdSensor.Listener mSensorEventListener = new ThresholdSensor.Listener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda1
        public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
            FalsingCollectorImpl.m1689$r8$lambda$46E8rIKAPM_BziFC6P7CAGq5TY(FalsingCollectorImpl.this, thresholdSensorEvent);
        }
    };
    public boolean mSessionStarted;
    public boolean mShowingAod;
    public int mState;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarStateController.StateListener mStatusBarStateListener;
    public final SystemClock mSystemClock;

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingCollectorImpl$ProximityEventImpl.class */
    public static class ProximityEventImpl implements FalsingManager.ProximityEvent {
        public ThresholdSensorEvent mThresholdSensorEvent;

        public ProximityEventImpl(ThresholdSensorEvent thresholdSensorEvent) {
            this.mThresholdSensorEvent = thresholdSensorEvent;
        }

        @Override // com.android.systemui.plugins.FalsingManager.ProximityEvent
        public boolean getCovered() {
            return this.mThresholdSensorEvent.getBelow();
        }

        @Override // com.android.systemui.plugins.FalsingManager.ProximityEvent
        public long getTimestampNs() {
            return this.mThresholdSensorEvent.getTimestampNs();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda1.onThresholdCrossed(com.android.systemui.util.sensors.ThresholdSensorEvent):void] */
    /* renamed from: $r8$lambda$46E8rIKAPM-_BziFC6P7CAGq5TY */
    public static /* synthetic */ void m1689$r8$lambda$46E8rIKAPM_BziFC6P7CAGq5TY(FalsingCollectorImpl falsingCollectorImpl, ThresholdSensorEvent thresholdSensorEvent) {
        falsingCollectorImpl.onProximityEvent(thresholdSensorEvent);
    }

    public FalsingCollectorImpl(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, ShadeExpansionStateManager shadeExpansionStateManager, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.1
            {
                FalsingCollectorImpl.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                FalsingCollectorImpl.logDebug("StatusBarState=" + StatusBarState.toString(i));
                FalsingCollectorImpl.this.mState = i;
                FalsingCollectorImpl.this.updateSessionActive();
            }
        };
        this.mStatusBarStateListener = stateListener;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.2
            {
                FalsingCollectorImpl.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (i == KeyguardUpdateMonitor.getCurrentUser() && biometricSourceType == BiometricSourceType.FACE) {
                    FalsingCollectorImpl.this.mFalsingDataProvider.setJustUnlockedWithFace(true);
                }
            }
        };
        this.mKeyguardUpdateCallback = keyguardUpdateMonitorCallback;
        BatteryController.BatteryStateChangeCallback batteryStateChangeCallback = new BatteryController.BatteryStateChangeCallback() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.3
            {
                FalsingCollectorImpl.this = this;
            }

            public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
            }

            public void onWirelessChargingChanged(boolean z) {
                if (z || FalsingCollectorImpl.this.mDockManager.isDocked()) {
                    FalsingCollectorImpl.this.mProximitySensor.pause();
                } else {
                    FalsingCollectorImpl.this.mProximitySensor.resume();
                }
            }
        };
        this.mBatteryListener = batteryStateChangeCallback;
        DockManager.DockEventListener dockEventListener = new DockManager.DockEventListener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl.4
            {
                FalsingCollectorImpl.this = this;
            }

            @Override // com.android.systemui.dock.DockManager.DockEventListener
            public void onEvent(int i) {
                if (i != 0 || FalsingCollectorImpl.this.mBatteryController.isWirelessCharging()) {
                    FalsingCollectorImpl.this.mProximitySensor.pause();
                } else {
                    FalsingCollectorImpl.this.mProximitySensor.resume();
                }
            }
        };
        this.mDockEventListener = dockEventListener;
        this.mFalsingDataProvider = falsingDataProvider;
        this.mFalsingManager = falsingManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mHistoryTracker = historyTracker;
        this.mProximitySensor = proximitySensor;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mBatteryController = batteryController;
        this.mDockManager = dockManager;
        this.mMainExecutor = delayableExecutor;
        this.mSystemClock = systemClock;
        proximitySensor.setTag("FalsingCollector");
        proximitySensor.setDelay(1);
        statusBarStateController.addCallback(stateListener);
        this.mState = statusBarStateController.getState();
        keyguardUpdateMonitor.registerCallback(keyguardUpdateMonitorCallback);
        shadeExpansionStateManager.addQsExpansionListener(new ShadeQsExpansionListener() { // from class: com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda2
            public final void onQsExpansionChanged(boolean z) {
                FalsingCollectorImpl.this.onQsExpansionChanged(Boolean.valueOf(z));
            }
        });
        batteryController.addCallback(batteryStateChangeCallback);
        dockManager.addListener(dockEventListener);
    }

    public static void logDebug(String str) {
        logDebug(str, null);
    }

    public static void logDebug(String str, Throwable th) {
        if (DEBUG) {
            Log.d("FalsingCollector", str, th);
        }
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void avoidGesture() {
        this.mAvoidGesture = true;
        MotionEvent motionEvent = this.mPendingDownEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPendingDownEvent = null;
        }
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public boolean isReportingEnabled() {
        return false;
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onA11yAction() {
        MotionEvent motionEvent = this.mPendingDownEvent;
        if (motionEvent != null) {
            motionEvent.recycle();
            this.mPendingDownEvent = null;
        }
        this.mFalsingDataProvider.onA11yAction();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onBouncerHidden() {
        if (this.mSessionStarted) {
            registerSensors();
        }
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onBouncerShown() {
        unregisterSensors();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onExpansionFromPulseStopped() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onMotionEventComplete() {
        DelayableExecutor delayableExecutor = this.mMainExecutor;
        final FalsingDataProvider falsingDataProvider = this.mFalsingDataProvider;
        Objects.requireNonNull(falsingDataProvider);
        delayableExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.classifier.FalsingCollectorImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                FalsingDataProvider.this.onMotionEventComplete();
            }
        }, 100L);
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationActive() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationDismissed() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationStartDismissing() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationStartDraggingDown() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationStopDismissing() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onNotificationStopDraggingDown() {
    }

    public final void onProximityEvent(ThresholdSensorEvent thresholdSensorEvent) {
        this.mFalsingManager.onProximityEvent(new ProximityEventImpl(thresholdSensorEvent));
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onQsDown() {
    }

    public void onQsExpansionChanged(Boolean bool) {
        if (bool.booleanValue()) {
            unregisterSensors();
        } else if (this.mSessionStarted) {
            registerSensors();
        }
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onScreenOff() {
        this.mScreenOn = false;
        updateSessionActive();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onScreenOnFromTouch() {
        onScreenTurningOn();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onScreenTurningOn() {
        this.mScreenOn = true;
        updateSessionActive();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onStartExpandingFromPulse() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onSuccessfulUnlock() {
        this.mFalsingManager.onSuccessfulUnlock();
        sessionEnd();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onTouchEvent(MotionEvent motionEvent) {
        if (!this.mKeyguardStateController.isShowing()) {
            avoidGesture();
        } else if (motionEvent.getActionMasked() == 4) {
        } else {
            if (motionEvent.getActionMasked() == 0) {
                this.mPendingDownEvent = MotionEvent.obtain(motionEvent);
                this.mAvoidGesture = false;
            } else if (this.mAvoidGesture) {
            } else {
                MotionEvent motionEvent2 = this.mPendingDownEvent;
                if (motionEvent2 != null) {
                    this.mFalsingDataProvider.onMotionEvent(motionEvent2);
                    this.mPendingDownEvent.recycle();
                    this.mPendingDownEvent = null;
                }
                this.mFalsingDataProvider.onMotionEvent(motionEvent);
            }
        }
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onTrackingStarted(boolean z) {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onTrackingStopped() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void onUnlockHintStarted() {
    }

    public final void registerSensors() {
        this.mProximitySensor.register(this.mSensorEventListener);
    }

    public final void sessionEnd() {
        if (this.mSessionStarted) {
            logDebug("Ending Session");
            this.mSessionStarted = false;
            unregisterSensors();
            this.mFalsingDataProvider.onSessionEnd();
        }
    }

    public final void sessionStart() {
        if (this.mSessionStarted || !shouldSessionBeActive()) {
            return;
        }
        logDebug("Starting Session");
        this.mSessionStarted = true;
        this.mFalsingDataProvider.setJustUnlockedWithFace(false);
        registerSensors();
        this.mFalsingDataProvider.onSessionStarted();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void setNotificationExpanded() {
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void setShowingAod(boolean z) {
        this.mShowingAod = z;
        updateSessionActive();
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public boolean shouldEnforceBouncer() {
        return false;
    }

    public final boolean shouldSessionBeActive() {
        boolean z = true;
        if (!this.mScreenOn || this.mState != 1 || this.mShowingAod) {
            z = false;
        }
        return z;
    }

    public final void unregisterSensors() {
        this.mProximitySensor.unregister(this.mSensorEventListener);
    }

    @Override // com.android.systemui.classifier.FalsingCollector
    public void updateFalseConfidence(FalsingClassifier.Result result) {
        this.mHistoryTracker.addResults(Collections.singleton(result), this.mSystemClock.uptimeMillis());
    }

    public final void updateSessionActive() {
        if (shouldSessionBeActive()) {
            sessionStart();
        } else {
            sessionEnd();
        }
    }
}