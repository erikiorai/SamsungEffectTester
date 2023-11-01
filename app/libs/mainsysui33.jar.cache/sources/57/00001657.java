package com.android.systemui.doze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeScreenBrightness.class */
public class DozeScreenBrightness extends BroadcastReceiver implements DozeMachine.Part, SensorEventListener {
    public static final boolean DEBUG_AOD_BRIGHTNESS = SystemProperties.getBoolean("debug.aod_brightness", false);
    public final Context mContext;
    public int mDefaultDozeBrightness;
    public int mDevicePosture;
    public final DevicePostureController.Callback mDevicePostureCallback;
    public final DevicePostureController mDevicePostureController;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final Optional<Sensor>[] mLightSensorOptional;
    public boolean mRegistered;
    public final int mScreenBrightnessDim;
    public final float mScreenBrightnessMinimumDimAmountFloat;
    public final SensorManager mSensorManager;
    public final int[] mSensorToBrightness;
    public final int[] mSensorToScrimOpacity;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public boolean mPaused = false;
    public boolean mScreenOff = false;
    public int mLastSensorValue = -1;
    public DozeMachine.State mState = DozeMachine.State.UNINITIALIZED;
    public int mDebugBrightnessBucket = -1;

    /* renamed from: com.android.systemui.doze.DozeScreenBrightness$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeScreenBrightness$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0065 -> B:41:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0069 -> B:37:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x006d -> B:49:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x0071 -> B:43:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:27:0x0075 -> B:39:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x0079 -> B:35:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x007d -> B:47:0x0058). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public DozeScreenBrightness(Context context, DozeMachine.Service service, AsyncSensorManager asyncSensorManager, Optional<Sensor>[] optionalArr, DozeHost dozeHost, Handler handler, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy, WakefulnessLifecycle wakefulnessLifecycle, DozeParameters dozeParameters, DevicePostureController devicePostureController, DozeLog dozeLog) {
        DevicePostureController.Callback callback = new DevicePostureController.Callback() { // from class: com.android.systemui.doze.DozeScreenBrightness.1
            public void onPostureChanged(int i) {
                if (DozeScreenBrightness.this.mDevicePosture == i || DozeScreenBrightness.this.mLightSensorOptional.length < 2 || i >= DozeScreenBrightness.this.mLightSensorOptional.length) {
                    return;
                }
                Sensor sensor = (Sensor) DozeScreenBrightness.this.mLightSensorOptional[DozeScreenBrightness.this.mDevicePosture].get();
                Sensor sensor2 = (Sensor) DozeScreenBrightness.this.mLightSensorOptional[i].get();
                if (Objects.equals(sensor, sensor2)) {
                    DozeScreenBrightness.this.mDevicePosture = i;
                    return;
                }
                if (DozeScreenBrightness.this.mRegistered) {
                    DozeScreenBrightness.this.setLightSensorEnabled(false);
                    DozeScreenBrightness.this.mDevicePosture = i;
                    DozeScreenBrightness.this.setLightSensorEnabled(true);
                } else {
                    DozeScreenBrightness.this.mDevicePosture = i;
                }
                DozeLog dozeLog2 = DozeScreenBrightness.this.mDozeLog;
                int i2 = DozeScreenBrightness.this.mDevicePosture;
                dozeLog2.tracePostureChanged(i2, "DozeScreenBrightness swap {" + sensor + "} => {" + sensor2 + "}, mRegistered=" + DozeScreenBrightness.this.mRegistered);
            }
        };
        this.mDevicePostureCallback = callback;
        this.mContext = context;
        this.mDozeService = service;
        this.mSensorManager = asyncSensorManager;
        this.mLightSensorOptional = optionalArr;
        this.mDevicePostureController = devicePostureController;
        this.mDevicePosture = devicePostureController.getDevicePosture();
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mDozeParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mHandler = handler;
        this.mDozeLog = dozeLog;
        this.mScreenBrightnessMinimumDimAmountFloat = context.getResources().getFloat(17105107);
        this.mDefaultDozeBrightness = alwaysOnDisplayPolicy.defaultDozeBrightness;
        this.mScreenBrightnessDim = alwaysOnDisplayPolicy.dimBrightness;
        this.mSensorToBrightness = alwaysOnDisplayPolicy.screenBrightnessArray;
        this.mSensorToScrimOpacity = alwaysOnDisplayPolicy.dimmingScrimArray;
        devicePostureController.addCallback(callback);
    }

    public final int clampToDimBrightnessForScreenOff(int i) {
        return (((this.mDozeParameters.shouldClampToDimBrightness() || this.mWakefulnessLifecycle.getWakefulness() == 3) && this.mState == DozeMachine.State.INITIALIZED) && this.mWakefulnessLifecycle.getLastSleepReason() == 2) ? Math.max(0, Math.min(i - ((int) Math.floor(this.mScreenBrightnessMinimumDimAmountFloat * 255.0f)), this.mScreenBrightnessDim)) : i;
    }

    public final int clampToUserSetting(int i) {
        return Math.min(i, Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", Integer.MAX_VALUE, -2));
    }

    public final int computeBrightness(int i) {
        if (i >= 0) {
            int[] iArr = this.mSensorToBrightness;
            if (i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }
        return -1;
    }

    public final int computeScrimOpacity(int i) {
        if (i >= 0) {
            int[] iArr = this.mSensorToScrimOpacity;
            if (i >= iArr.length) {
                return -1;
            }
            return iArr[i];
        }
        return -1;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter printWriter) {
        printWriter.println("DozeScreenBrightness:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("registered=" + this.mRegistered);
        indentingPrintWriter.println("posture=" + DevicePostureController.devicePostureToString(this.mDevicePosture));
    }

    public final Sensor getLightSensor() {
        if (lightSensorSupportsCurrentPosture()) {
            return this.mLightSensorOptional[this.mDevicePosture].get();
        }
        return null;
    }

    public final boolean isLightSensorPresent() {
        if (lightSensorSupportsCurrentPosture()) {
            return this.mLightSensorOptional[this.mDevicePosture].isPresent();
        }
        Optional<Sensor>[] optionalArr = this.mLightSensorOptional;
        boolean z = false;
        if (optionalArr != null) {
            z = false;
            if (optionalArr[0].isPresent()) {
                z = true;
            }
        }
        return z;
    }

    public final boolean lightSensorSupportsCurrentPosture() {
        Optional<Sensor>[] optionalArr = this.mLightSensorOptional;
        return optionalArr != null && this.mDevicePosture < optionalArr.length;
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public final void onDestroy() {
        setLightSensorEnabled(false);
        this.mDevicePostureController.removeCallback(this.mDevicePostureCallback);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.mDebugBrightnessBucket = intent.getIntExtra("brightness_bucket", -1);
        updateBrightnessAndReady(false);
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (Trace.isEnabled()) {
            Trace.traceBegin(4096L, "DozeScreenBrightness.onSensorChanged" + sensorEvent.values[0]);
        }
        try {
            if (this.mRegistered) {
                this.mLastSensorValue = (int) sensorEvent.values[0];
                updateBrightnessAndReady(false);
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void resetBrightnessToDefault() {
        this.mDozeService.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(clampToUserSetting(this.mDefaultDozeBrightness)));
        this.mDozeHost.setAodDimmingScrim(ActionBarShadowController.ELEVATION_LOW);
    }

    public final void setLightSensorEnabled(boolean z) {
        if (z && !this.mRegistered && isLightSensorPresent()) {
            this.mRegistered = this.mSensorManager.registerListener(this, getLightSensor(), 3, this.mHandler);
            this.mLastSensorValue = -1;
        } else if (z || !this.mRegistered) {
        } else {
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mLastSensorValue = -1;
        }
    }

    public final void setPaused(boolean z) {
        if (this.mPaused != z) {
            this.mPaused = z;
            updateBrightnessAndReady(false);
        }
    }

    public final void setScreenOff(boolean z) {
        if (this.mScreenOff != z) {
            this.mScreenOff = z;
            updateBrightnessAndReady(true);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        this.mState = state2;
        switch (AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                resetBrightnessToDefault();
                break;
            case 2:
            case 3:
            case 4:
                setLightSensorEnabled(true);
                break;
            case 5:
            case 6:
                setLightSensorEnabled(false);
                resetBrightnessToDefault();
                break;
            case 7:
                setLightSensorEnabled(false);
                break;
            case 8:
                onDestroy();
                break;
        }
        if (state2 != DozeMachine.State.FINISH) {
            setScreenOff(state2 == DozeMachine.State.DOZE);
            setPaused(state2 == DozeMachine.State.DOZE_AOD_PAUSED);
        }
    }

    public void updateBrightnessAndReady(boolean z) {
        int i = -1;
        if (z || this.mRegistered || this.mDebugBrightnessBucket != -1) {
            int i2 = this.mDebugBrightnessBucket;
            int i3 = i2;
            if (i2 == -1) {
                i3 = this.mLastSensorValue;
            }
            int computeBrightness = computeBrightness(i3);
            boolean z2 = computeBrightness > 0;
            if (z2) {
                this.mDozeService.setDozeScreenBrightness(clampToDimBrightnessForScreenOff(clampToUserSetting(computeBrightness)));
            }
            if (!isLightSensorPresent()) {
                i = 0;
            } else if (z2) {
                i = computeScrimOpacity(i3);
            }
            if (i >= 0) {
                this.mDozeHost.setAodDimmingScrim(i / 255.0f);
            }
        }
    }
}