package com.android.systemui.doze;

import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.hardware.display.AmbientDisplayConfiguration;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.IndentingPrintWriter;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R$bool;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.plugins.SensorManagerPlugin;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorEvent;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSensors.class */
public class DozeSensors {
    public static final UiEventLogger UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public final AuthController.Callback mAuthControllerCallback;
    public final AmbientDisplayConfiguration mConfig;
    public long mDebounceFrom;
    public int mDevicePosture;
    public final DevicePostureController.Callback mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final DozeLog mDozeLog;
    public final Handler mHandler;
    public boolean mListening;
    public boolean mListeningProxSensors;
    public boolean mListeningTouchScreenSensors;
    public final Consumer<Boolean> mProxCallback;
    public final ProximitySensor mProximitySensor;
    public final boolean mScreenOffUdfpsEnabled;
    public final SecureSettings mSecureSettings;
    public boolean mSelectivelyRegisterProxSensors;
    public final Callback mSensorCallback;
    public final AsyncSensorManager mSensorManager;
    public boolean mSettingRegistered;
    public final ContentObserver mSettingsObserver;
    public TriggerSensor[] mTriggerSensors;
    public boolean mUdfpsEnrolled;
    public final UserTracker mUserTracker;
    public final WakeLock mWakeLock;

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSensors$Callback.class */
    public interface Callback {
        void onSensorPulse(int i, boolean z, float f, float f2, float[] fArr);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSensors$DozeSensorsUiEvent.class */
    public enum DozeSensorsUiEvent implements UiEventLogger.UiEventEnum {
        ACTION_AMBIENT_GESTURE_PICKUP(459);
        
        private final int mId;

        DozeSensorsUiEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSensors$PluginSensor.class */
    public class PluginSensor extends TriggerSensor implements SensorManagerPlugin.SensorEventListener {
        public long mDebounce;
        public final SensorManagerPlugin.Sensor mPluginSensor;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeSensors$PluginSensor$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$Jf-mpgeLP4DiPTgosDukVPI0ViI */
        public static /* synthetic */ void m2500$r8$lambda$JfmpgeLP4DiPTgosDukVPI0ViI(PluginSensor pluginSensor, SensorManagerPlugin.SensorEvent sensorEvent) {
            pluginSensor.lambda$onSensorChanged$0(sensorEvent);
        }

        public PluginSensor(DozeSensors dozeSensors, SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3) {
            this(sensor, str, z, i, z2, z3, 0L);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PluginSensor(SensorManagerPlugin.Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3, long j) {
            super(r10, null, str, z, i, z2, z3);
            DozeSensors.this = r10;
            this.mPluginSensor = sensor;
            this.mDebounce = j;
        }

        public /* synthetic */ void lambda$onSensorChanged$0(SensorManagerPlugin.SensorEvent sensorEvent) {
            if (SystemClock.uptimeMillis() < DozeSensors.this.mDebounceFrom + this.mDebounce) {
                DozeSensors.this.mDozeLog.traceSensorEventDropped(this.mPulseReason, "debounce");
            } else {
                DozeSensors.this.mSensorCallback.onSensorPulse(this.mPulseReason, true, -1.0f, -1.0f, sensorEvent.getValues());
            }
        }

        @Override // com.android.systemui.plugins.SensorManagerPlugin.SensorEventListener
        public void onSensorChanged(final SensorManagerPlugin.SensorEvent sensorEvent) {
            DozeSensors.this.mDozeLog.traceSensor(this.mPulseReason);
            DozeSensors.this.mHandler.post(DozeSensors.this.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$PluginSensor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DozeSensors.PluginSensor.m2500$r8$lambda$JfmpgeLP4DiPTgosDukVPI0ViI(DozeSensors.PluginSensor.this, sensorEvent);
                }
            }));
        }

        @Override // com.android.systemui.doze.DozeSensors.TriggerSensor
        public String toString() {
            return "{mRegistered=" + this.mRegistered + ", mRequested=" + this.mRequested + ", mDisabled=" + this.mDisabled + ", mConfigured=" + this.mConfigured + ", mIgnoresSetting=" + this.mIgnoresSetting + ", mSensor=" + this.mPluginSensor + "}";
        }

        @Override // com.android.systemui.doze.DozeSensors.TriggerSensor
        public void updateListening() {
            if (this.mConfigured) {
                AsyncSensorManager asyncSensorManager = DozeSensors.this.mSensorManager;
                if (this.mRequested && !this.mDisabled && ((enabledBySetting() || this.mIgnoresSetting) && !this.mRegistered)) {
                    asyncSensorManager.registerPluginListener(this.mPluginSensor, this);
                    this.mRegistered = true;
                    DozeSensors.this.mDozeLog.tracePluginSensorUpdate(true);
                } else if (this.mRegistered) {
                    asyncSensorManager.unregisterPluginListener(this.mPluginSensor, this);
                    this.mRegistered = false;
                    DozeSensors.this.mDozeLog.tracePluginSensorUpdate(false);
                }
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSensors$TriggerSensor.class */
    public class TriggerSensor extends TriggerEventListener {
        public boolean mConfigured;
        public boolean mDisabled;
        public boolean mIgnoresSetting;
        public final boolean mImmediatelyReRegister;
        public final boolean mPerformsProxCheck;
        public int mPosture;
        public final int mPulseReason;
        public boolean mRegistered;
        public final boolean mReportsTouchCoordinates;
        public boolean mRequested;
        public final boolean mRequiresProx;
        public final boolean mRequiresTouchscreen;
        public final Sensor[] mSensors;
        public final String mSetting;
        public final boolean mSettingDefault;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeSensors$TriggerSensor$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$JjRzg4rIGz5m09kTJqEKNCY6Xz0(TriggerSensor triggerSensor, Sensor sensor, TriggerEvent triggerEvent) {
            triggerSensor.lambda$onTrigger$0(sensor, triggerEvent);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3) {
            this(dozeSensors, sensor, str, z, i, z2, z3, true);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, int i, boolean z2, boolean z3, boolean z4) {
            this(dozeSensors, sensor, str, true, z, i, z2, z3, false, false, z4, true);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
            this(dozeSensors, sensor, str, z, z2, i, z3, z4, z5, z6, true, z7);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor sensor, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8) {
            this(new Sensor[]{sensor}, str, z, z2, i, z3, z4, z5, z6, z7, z8, 0);
        }

        public TriggerSensor(DozeSensors dozeSensors, Sensor[] sensorArr, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, int i2) {
            this(sensorArr, str, z, z2, i, z3, z4, z5, z6, true, z7, i2);
        }

        public TriggerSensor(Sensor[] sensorArr, String str, boolean z, boolean z2, int i, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7, boolean z8, int i2) {
            DozeSensors.this = r4;
            this.mSensors = sensorArr;
            this.mSetting = str;
            this.mSettingDefault = z;
            this.mConfigured = z2;
            this.mPulseReason = i;
            this.mReportsTouchCoordinates = z3;
            this.mRequiresTouchscreen = z4;
            this.mIgnoresSetting = z5;
            this.mRequiresProx = z6;
            this.mPerformsProxCheck = z7;
            this.mPosture = i2;
            this.mImmediatelyReRegister = z8;
        }

        public /* synthetic */ void lambda$onTrigger$0(Sensor sensor, TriggerEvent triggerEvent) {
            float f;
            float f2;
            if (sensor != null && sensor.getType() == 25) {
                DozeSensors.UI_EVENT_LOGGER.log(DozeSensorsUiEvent.ACTION_AMBIENT_GESTURE_PICKUP);
            }
            this.mRegistered = false;
            if (this.mReportsTouchCoordinates) {
                float[] fArr = triggerEvent.values;
                if (fArr.length >= 2) {
                    f = fArr[0];
                    f2 = fArr[1];
                    DozeSensors.this.mSensorCallback.onSensorPulse(this.mPulseReason, this.mPerformsProxCheck, f, f2, triggerEvent.values);
                    if (this.mRegistered && this.mImmediatelyReRegister) {
                        updateListening();
                        return;
                    }
                }
            }
            f = -1.0f;
            f2 = -1.0f;
            DozeSensors.this.mSensorCallback.onSensorPulse(this.mPulseReason, this.mPerformsProxCheck, f, f2, triggerEvent.values);
            if (this.mRegistered) {
            }
        }

        public boolean enabledBySetting() {
            boolean z = false;
            if (DozeSensors.this.mConfig.enabled(-2)) {
                if (TextUtils.isEmpty(this.mSetting)) {
                    return true;
                }
                if (DozeSensors.this.mSecureSettings.getIntForUser(this.mSetting, this.mSettingDefault ? 1 : 0, -2) != 0) {
                    z = true;
                }
                return z;
            }
            return false;
        }

        public void ignoreSetting(boolean z) {
            if (this.mIgnoresSetting == z) {
                return;
            }
            this.mIgnoresSetting = z;
            updateListening();
        }

        @Override // android.hardware.TriggerEventListener
        public void onTrigger(final TriggerEvent triggerEvent) {
            final Sensor sensor = this.mSensors[this.mPosture];
            DozeSensors.this.mDozeLog.traceSensor(this.mPulseReason);
            DozeSensors.this.mHandler.post(DozeSensors.this.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeSensors$TriggerSensor$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DozeSensors.TriggerSensor.$r8$lambda$JjRzg4rIGz5m09kTJqEKNCY6Xz0(DozeSensors.TriggerSensor.this, sensor, triggerEvent);
                }
            }));
        }

        public void registerSettingsObserver(ContentObserver contentObserver) {
            if (!this.mConfigured || TextUtils.isEmpty(this.mSetting)) {
                return;
            }
            DozeSensors.this.mSecureSettings.registerContentObserverForUser(this.mSetting, DozeSensors.this.mSettingsObserver, -1);
        }

        public void setConfigured(boolean z) {
            if (this.mConfigured == z) {
                return;
            }
            this.mConfigured = z;
            updateListening();
        }

        public void setListening(boolean z) {
            if (this.mRequested == z) {
                return;
            }
            this.mRequested = z;
            updateListening();
        }

        public boolean setPosture(int i) {
            int i2 = this.mPosture;
            if (i2 != i) {
                Sensor[] sensorArr = this.mSensors;
                if (sensorArr.length < 2 || i >= sensorArr.length) {
                    return false;
                }
                Sensor sensor = sensorArr[i2];
                Sensor sensor2 = sensorArr[i];
                if (Objects.equals(sensor, sensor2)) {
                    this.mPosture = i;
                    return false;
                }
                if (this.mRegistered) {
                    DozeSensors.this.mDozeLog.traceSensorUnregisterAttempt(sensor.toString(), DozeSensors.this.mSensorManager.cancelTriggerSensor(this, sensor), "posture changed");
                    this.mRegistered = false;
                }
                this.mPosture = i;
                updateListening();
                DozeLog dozeLog = DozeSensors.this.mDozeLog;
                int i3 = this.mPosture;
                dozeLog.tracePostureChanged(i3, "DozeSensors swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + this.mRegistered);
                return true;
            }
            return false;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            sb.append("mRegistered=");
            sb.append(this.mRegistered);
            sb.append(", mRequested=");
            sb.append(this.mRequested);
            sb.append(", mDisabled=");
            sb.append(this.mDisabled);
            sb.append(", mConfigured=");
            sb.append(this.mConfigured);
            sb.append(", mIgnoresSetting=");
            sb.append(this.mIgnoresSetting);
            sb.append(", mSensors=");
            sb.append(Arrays.toString(this.mSensors));
            if (this.mSensors.length > 2) {
                sb.append(", mPosture=");
                sb.append(DevicePostureController.devicePostureToString(DozeSensors.this.mDevicePosture));
            }
            sb.append("}");
            return sb.toString();
        }

        public void updateListening() {
            Sensor sensor = this.mSensors[this.mPosture];
            if (!this.mConfigured || sensor == null) {
                return;
            }
            if (this.mRequested && !this.mDisabled && (enabledBySetting() || this.mIgnoresSetting)) {
                if (this.mRegistered) {
                    DozeSensors.this.mDozeLog.traceSkipRegisterSensor(sensor.toString());
                    return;
                }
                this.mRegistered = DozeSensors.this.mSensorManager.requestTriggerSensor(this, sensor);
                DozeSensors.this.mDozeLog.traceSensorRegisterAttempt(sensor.toString(), this.mRegistered);
            } else if (this.mRegistered) {
                DozeSensors.this.mDozeLog.traceSensorUnregisterAttempt(sensor.toString(), DozeSensors.this.mSensorManager.cancelTriggerSensor(this, sensor));
                this.mRegistered = false;
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda1.onThresholdCrossed(com.android.systemui.util.sensors.ThresholdSensorEvent):void] */
    /* renamed from: $r8$lambda$3KOYE8Q2-MlL-D0NdFYJt2eufCQ */
    public static /* synthetic */ void m2481$r8$lambda$3KOYE8Q2MlLD0NdFYJt2eufCQ(DozeSensors dozeSensors, ThresholdSensorEvent thresholdSensorEvent) {
        dozeSensors.lambda$new$0(thresholdSensorEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda0.onPostureChanged(int):void] */
    public static /* synthetic */ void $r8$lambda$VQjohpwsnLQ5JraKYCVNTU2ST1U(DozeSensors dozeSensors, int i) {
        dozeSensors.lambda$new$1(i);
    }

    public DozeSensors(Resources resources, AsyncSensorManager asyncSensorManager, DozeParameters dozeParameters, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, Callback callback, Consumer<Boolean> consumer, DozeLog dozeLog, ProximitySensor proximitySensor, SecureSettings secureSettings, AuthController authController, DevicePostureController devicePostureController, UserTracker userTracker) {
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mSettingsObserver = new ContentObserver(handler) { // from class: com.android.systemui.doze.DozeSensors.1
            {
                DozeSensors.this = this;
            }

            public void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                if (i2 != DozeSensors.this.mUserTracker.getUserId()) {
                    return;
                }
                for (TriggerSensor triggerSensor : DozeSensors.this.mTriggerSensors) {
                    triggerSensor.updateListening();
                }
            }
        };
        DevicePostureController.Callback callback2 = new DevicePostureController.Callback() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda0
            public final void onPostureChanged(int i) {
                DozeSensors.$r8$lambda$VQjohpwsnLQ5JraKYCVNTU2ST1U(DozeSensors.this, i);
            }
        };
        this.mDevicePostureCallback = callback2;
        AuthController.Callback callback3 = new AuthController.Callback() { // from class: com.android.systemui.doze.DozeSensors.2
            {
                DozeSensors.this = this;
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onAllAuthenticatorsRegistered() {
                updateUdfpsEnrolled();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onEnrollmentsChanged() {
                updateUdfpsEnrolled();
            }

            public final void updateUdfpsEnrolled() {
                TriggerSensor[] triggerSensorArr;
                DozeSensors dozeSensors = DozeSensors.this;
                dozeSensors.mUdfpsEnrolled = dozeSensors.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
                for (TriggerSensor triggerSensor : DozeSensors.this.mTriggerSensors) {
                    int i = triggerSensor.mPulseReason;
                    if (11 == i) {
                        triggerSensor.setConfigured(DozeSensors.this.quickPickUpConfigured());
                    } else if (10 == i) {
                        triggerSensor.setConfigured(DozeSensors.this.udfpsLongPressConfigured());
                    }
                }
            }
        };
        this.mAuthControllerCallback = callback3;
        this.mSensorManager = asyncSensorManager;
        this.mConfig = ambientDisplayConfiguration;
        this.mWakeLock = wakeLock;
        this.mProxCallback = consumer;
        this.mSecureSettings = secureSettings;
        this.mSensorCallback = callback;
        this.mDozeLog = dozeLog;
        this.mProximitySensor = proximitySensor;
        proximitySensor.setTag("DozeSensors");
        boolean selectivelyRegisterSensorsUsingProx = dozeParameters.getSelectivelyRegisterSensorsUsingProx();
        this.mSelectivelyRegisterProxSensors = selectivelyRegisterSensorsUsingProx;
        this.mListeningProxSensors = !selectivelyRegisterSensorsUsingProx;
        this.mScreenOffUdfpsEnabled = ambientDisplayConfiguration.screenOffUdfpsEnabled(KeyguardUpdateMonitor.getCurrentUser());
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = devicePostureController.getDevicePosture();
        this.mAuthController = authController;
        this.mUserTracker = userTracker;
        this.mUdfpsEnrolled = authController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser());
        authController.addCallback(callback3);
        this.mTriggerSensors = new TriggerSensor[]{new TriggerSensor(this, asyncSensorManager.getDefaultSensor(17), null, dozeParameters.getPulseOnSigMotion(), 2, false, false), new TriggerSensor(this, asyncSensorManager.getDefaultSensor(25), "doze_pulse_on_pick_up", resources.getBoolean(17891621), ambientDisplayConfiguration.dozePickupSensorAvailable(), 3, false, false, false, false, true), new TriggerSensor(this, findSensor(ambientDisplayConfiguration.doubleTapSensorType()), "doze_pulse_on_double_tap", true, 4, dozeParameters.doubleTapReportsTouchCoordinates(), true, !dozeParameters.doubleTapNeedsProximityCheck()), new TriggerSensor(this, findSensors(ambientDisplayConfiguration.tapSensorTypeMapping()), "doze_tap_gesture", true, true, 9, false, true, false, dozeParameters.singleTapUsesProx(this.mDevicePosture), true, this.mDevicePosture), new TriggerSensor(this, findSensor(ambientDisplayConfiguration.longPressSensorType()), "doze_pulse_on_long_press", false, true, 5, true, true, false, dozeParameters.longPressUsesProx(), !dozeParameters.longPressNeedsProximityCheck(), true), new TriggerSensor(this, findSensor(ambientDisplayConfiguration.udfpsLongPressSensorType()), "doze_pulse_on_auth", true, udfpsLongPressConfigured(), 10, true, true, false, dozeParameters.longPressUsesProx(), !dozeParameters.longPressNeedsProximityCheck(), false), new PluginSensor(this, new SensorManagerPlugin.Sensor(2), "doze_wake_display_gesture", ambientDisplayConfiguration.wakeScreenGestureAvailable() && ambientDisplayConfiguration.alwaysOnEnabled(-2), 7, false, false), new PluginSensor(new SensorManagerPlugin.Sensor(1), "doze_wake_screen_gesture", ambientDisplayConfiguration.wakeScreenGestureAvailable(), 8, false, false, ambientDisplayConfiguration.getWakeLockScreenDebounce()), new TriggerSensor(this, findSensor(ambientDisplayConfiguration.quickPickupSensorType()), "doze_quick_pickup_gesture", true, quickPickUpConfigured(), 11, false, false, false, false, true)};
        if (resources.getBoolean(R$bool.doze_proximity_sensor_supported)) {
            setProxListening(false);
            proximitySensor.register(new ThresholdSensor.Listener() { // from class: com.android.systemui.doze.DozeSensors$$ExternalSyntheticLambda1
                public final void onThresholdCrossed(ThresholdSensorEvent thresholdSensorEvent) {
                    DozeSensors.m2481$r8$lambda$3KOYE8Q2MlLD0NdFYJt2eufCQ(DozeSensors.this, thresholdSensorEvent);
                }
            });
        }
        devicePostureController.addCallback(callback2);
    }

    public static Sensor findSensor(SensorManager sensorManager, String str, String str2) {
        boolean z = !TextUtils.isEmpty(str2);
        boolean z2 = !TextUtils.isEmpty(str);
        if (z || z2) {
            for (Sensor sensor : sensorManager.getSensorList(-1)) {
                if (!z || str2.equals(sensor.getName())) {
                    if (!z2 || str.equals(sensor.getStringType())) {
                        return sensor;
                    }
                }
            }
            return null;
        }
        return null;
    }

    public /* synthetic */ void lambda$new$0(ThresholdSensorEvent thresholdSensorEvent) {
        if (thresholdSensorEvent != null) {
            this.mProxCallback.accept(Boolean.valueOf(!thresholdSensorEvent.getBelow()));
        }
    }

    public /* synthetic */ void lambda$new$1(int i) {
        if (this.mDevicePosture == i) {
            return;
        }
        this.mDevicePosture = i;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            triggerSensor.setPosture(this.mDevicePosture);
        }
    }

    public void destroy() {
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            triggerSensor.setListening(false);
        }
        this.mProximitySensor.destroy();
        this.mDevicePostureController.removeCallback(this.mDevicePostureCallback);
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
    }

    public void dump(PrintWriter printWriter) {
        TriggerSensor[] triggerSensorArr;
        printWriter.println("mListening=" + this.mListening);
        printWriter.println("mDevicePosture=" + DevicePostureController.devicePostureToString(this.mDevicePosture));
        printWriter.println("mListeningTouchScreenSensors=" + this.mListeningTouchScreenSensors);
        printWriter.println("mSelectivelyRegisterProxSensors=" + this.mSelectivelyRegisterProxSensors);
        printWriter.println("mListeningProxSensors=" + this.mListeningProxSensors);
        printWriter.println("mScreenOffUdfpsEnabled=" + this.mScreenOffUdfpsEnabled);
        printWriter.println("mUdfpsEnrolled=" + this.mUdfpsEnrolled);
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            indentingPrintWriter.println("Sensor: " + triggerSensor.toString());
        }
        indentingPrintWriter.println("ProxSensor: " + this.mProximitySensor.toString());
    }

    public final Sensor findSensor(String str) {
        return findSensor(this.mSensorManager, str, null);
    }

    public final Sensor[] findSensors(String[] strArr) {
        Sensor[] sensorArr = new Sensor[5];
        HashMap hashMap = new HashMap();
        for (int i = 0; i < strArr.length; i++) {
            String str = strArr[i];
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, findSensor(str));
            }
            sensorArr[i] = (Sensor) hashMap.get(str);
        }
        return sensorArr;
    }

    public void ignoreTouchScreenSensorsSettingInterferingWithDocking(boolean z) {
        TriggerSensor[] triggerSensorArr;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            if (triggerSensor.mRequiresTouchscreen) {
                triggerSensor.ignoreSetting(z);
            }
        }
    }

    public Boolean isProximityCurrentlyNear() {
        return this.mProximitySensor.isNear();
    }

    public void onScreenState(int i) {
        ProximitySensor proximitySensor = this.mProximitySensor;
        boolean z = true;
        if (i != 3) {
            z = true;
            if (i != 4) {
                z = i == 1;
            }
        }
        proximitySensor.setSecondarySafe(z);
    }

    public void onUserSwitched() {
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            triggerSensor.updateListening();
        }
    }

    public final boolean quickPickUpConfigured() {
        return this.mUdfpsEnrolled && this.mConfig.quickPickupSensorEnabled(KeyguardUpdateMonitor.getCurrentUser());
    }

    public void requestTemporaryDisable() {
        this.mDebounceFrom = SystemClock.uptimeMillis();
    }

    public void setListening(boolean z, boolean z2) {
        if (this.mListening == z && this.mListeningTouchScreenSensors == z2) {
            return;
        }
        this.mListening = z;
        this.mListeningTouchScreenSensors = z2;
        updateListening();
    }

    public void setListening(boolean z, boolean z2, boolean z3) {
        boolean z4 = !this.mSelectivelyRegisterProxSensors || z3;
        if (this.mListening == z && this.mListeningTouchScreenSensors == z2 && this.mListeningProxSensors == z4) {
            return;
        }
        this.mListening = z;
        this.mListeningTouchScreenSensors = z2;
        this.mListeningProxSensors = z4;
        updateListening();
    }

    public void setProxListening(boolean z) {
        if (this.mProximitySensor.isRegistered() && z) {
            this.mProximitySensor.alertListeners();
        } else if (z) {
            this.mProximitySensor.resume();
        } else {
            this.mProximitySensor.pause();
        }
    }

    public final boolean udfpsLongPressConfigured() {
        return this.mUdfpsEnrolled && (this.mConfig.alwaysOnEnabled(-2) || this.mScreenOffUdfpsEnabled);
    }

    public final void updateListening() {
        TriggerSensor[] triggerSensorArr;
        boolean z = false;
        for (TriggerSensor triggerSensor : this.mTriggerSensors) {
            boolean z2 = this.mListening && (!triggerSensor.mRequiresTouchscreen || this.mListeningTouchScreenSensors) && (!triggerSensor.mRequiresProx || this.mListeningProxSensors);
            triggerSensor.setListening(z2);
            if (z2) {
                z = true;
            }
        }
        if (!z) {
            this.mSecureSettings.unregisterContentObserver(this.mSettingsObserver);
        } else if (!this.mSettingRegistered) {
            for (TriggerSensor triggerSensor2 : this.mTriggerSensors) {
                triggerSensor2.registerSettingsObserver(this.mSettingsObserver);
            }
        }
        this.mSettingRegistered = z;
    }
}