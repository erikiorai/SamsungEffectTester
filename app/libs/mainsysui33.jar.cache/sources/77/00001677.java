package com.android.systemui.doze;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.IndentingPrintWriter;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeSensors;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.Assert;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers.class */
public class DozeTriggers implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public static boolean sWakeDisplaySensorState = true;
    public Runnable mAodInterruptRunnable;
    public final AuthController mAuthController;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AmbientDisplayConfiguration mConfig;
    public final Context mContext;
    public final DockManager mDockManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final DozeSensors mDozeSensors;
    public final KeyguardStateController mKeyguardStateController;
    public DozeMachine mMachine;
    public long mNotificationPulseTime;
    public final ProximityCheck mProxCheck;
    public final AsyncSensorManager mSensorManager;
    public final SessionTracker mSessionTracker;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker mUserTracker;
    public final WakeLock mWakeLock;
    public boolean mWantProxSensor;
    public boolean mWantSensors;
    public boolean mWantTouchScreenSensors;
    public final TriggerReceiver mBroadcastReceiver = new TriggerReceiver();
    public final DockEventListener mDockEventListener = new DockEventListener();
    public final UserTracker.Callback mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.doze.DozeTriggers.1
        {
            DozeTriggers.this = this;
        }

        public void onUserChanged(int i, Context context) {
            DozeTriggers.this.mDozeSensors.onUserSwitched();
        }
    };
    public DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.android.systemui.doze.DozeTriggers.2
        {
            DozeTriggers.this = this;
        }

        @Override // com.android.systemui.doze.DozeHost.Callback
        public void onNotificationAlerted(Runnable runnable) {
            DozeTriggers.this.onNotification(runnable);
        }
    };
    public final boolean mAllowPulseTriggers = true;

    /* renamed from: com.android.systemui.doze.DozeTriggers$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers$3.class */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0095 -> B:125:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:103:0x0099 -> B:119:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x009d -> B:135:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x00a1 -> B:129:0x0058). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x00a5 -> B:123:0x0064). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:111:0x00a9 -> B:117:0x0070). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:113:0x00ad -> B:133:0x007c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x0089 -> B:121:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:97:0x008d -> B:137:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x0091 -> B:131:0x002a). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers$DockEventListener.class */
    public class DockEventListener implements DockManager.DockEventListener {
        public DockEventListener() {
            DozeTriggers.this = r4;
        }

        @Override // com.android.systemui.dock.DockManager.DockEventListener
        public void onEvent(int i) {
            if (DozeTriggers.DEBUG) {
                Log.d("DozeTriggers", "dock event = " + i);
            }
            if (i == 0) {
                DozeTriggers.this.mDozeSensors.ignoreTouchScreenSensorsSettingInterferingWithDocking(false);
            } else if (i == 1 || i == 2) {
                DozeTriggers.this.mDozeSensors.ignoreTouchScreenSensorsSettingInterferingWithDocking(true);
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers$DozingUpdateUiEvent.class */
    public enum DozingUpdateUiEvent implements UiEventLogger.UiEventEnum {
        DOZING_UPDATE_NOTIFICATION(433),
        DOZING_UPDATE_SIGMOTION(434),
        DOZING_UPDATE_SENSOR_PICKUP(435),
        DOZING_UPDATE_SENSOR_DOUBLE_TAP(436),
        DOZING_UPDATE_SENSOR_LONG_SQUEEZE(437),
        DOZING_UPDATE_DOCKING(438),
        DOZING_UPDATE_SENSOR_WAKEUP(439),
        DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN(440),
        DOZING_UPDATE_SENSOR_TAP(441),
        DOZING_UPDATE_AUTH_TRIGGERED(657),
        DOZING_UPDATE_QUICK_PICKUP(708),
        DOZING_UPDATE_WAKE_TIMEOUT(794);
        
        private final int mId;

        DozingUpdateUiEvent(int i) {
            this.mId = i;
        }

        public static DozingUpdateUiEvent fromReason(int i) {
            switch (i) {
                case 1:
                    return DOZING_UPDATE_NOTIFICATION;
                case 2:
                    return DOZING_UPDATE_SIGMOTION;
                case 3:
                    return DOZING_UPDATE_SENSOR_PICKUP;
                case 4:
                    return DOZING_UPDATE_SENSOR_DOUBLE_TAP;
                case 5:
                    return DOZING_UPDATE_SENSOR_LONG_SQUEEZE;
                case 6:
                    return DOZING_UPDATE_DOCKING;
                case 7:
                    return DOZING_UPDATE_SENSOR_WAKEUP;
                case 8:
                    return DOZING_UPDATE_SENSOR_WAKE_LOCKSCREEN;
                case 9:
                    return DOZING_UPDATE_SENSOR_TAP;
                case 10:
                    return DOZING_UPDATE_AUTH_TRIGGERED;
                case 11:
                    return DOZING_UPDATE_QUICK_PICKUP;
                default:
                    return null;
            }
        }

        public int getId() {
            return this.mId;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers$TriggerReceiver.class */
    public class TriggerReceiver extends BroadcastReceiver {
        public boolean mRegistered;

        public TriggerReceiver() {
            DozeTriggers.this = r4;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("com.android.systemui.doze.pulse".equals(intent.getAction())) {
                if (DozeMachine.DEBUG) {
                    Log.d("DozeTriggers", "Received pulse intent");
                }
                DozeTriggers.this.requestPulse(0, false, null);
            }
        }

        public void register(BroadcastDispatcher broadcastDispatcher) {
            if (this.mRegistered) {
                return;
            }
            broadcastDispatcher.registerReceiver(this, new IntentFilter("com.android.systemui.doze.pulse"));
            this.mRegistered = true;
        }

        public void unregister(BroadcastDispatcher broadcastDispatcher) {
            if (this.mRegistered) {
                broadcastDispatcher.unregisterReceiver(this);
                this.mRegistered = false;
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$0a0HT3fS9nKIxetOKlscese90qY(DozeTriggers dozeTriggers, int i, boolean z, boolean z2, float f, float f2, boolean z3, boolean z4, float[] fArr, Boolean bool) {
        dozeTriggers.lambda$onSensor$2(i, z, z2, f, f2, z3, z4, fArr, bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$7x5GD8oRADMJNF_wwAFPsufPGw4(DozeTriggers dozeTriggers, float f, float f2, float[] fArr) {
        dozeTriggers.lambda$onSensor$1(f, f2, fArr);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$9jRAaGV_-QR9imFeIDq8ZoA3_B0 */
    public static /* synthetic */ void m2514$r8$lambda$9jRAaGV_QR9imFeIDq8ZoA3_B0(DozeTriggers dozeTriggers, DozingUpdateUiEvent dozingUpdateUiEvent) {
        dozeTriggers.lambda$requestPulse$7(dozingUpdateUiEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda9.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$Ged5PTnhuW1c_qi6-1uYT7NnMaA */
    public static /* synthetic */ void m2515$r8$lambda$Ged5PTnhuW1c_qi61uYT7NnMaA(DozeTriggers dozeTriggers, DozingUpdateUiEvent dozingUpdateUiEvent) {
        dozeTriggers.lambda$gentleWakeUp$3(dozingUpdateUiEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda6.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$LL-FaZptufirxDFdxqwlS-JNi7g */
    public static /* synthetic */ void m2516$r8$lambda$LLFaZptufirxDFdxqwlSJNi7g(DozeTriggers dozeTriggers, long j, int i, Consumer consumer, Boolean bool) {
        dozeTriggers.lambda$proximityCheckThenCall$0(j, i, consumer, bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda5.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$ifD47vLa_mHYa7FFrccrDH73jVI(DozeTriggers dozeTriggers, DozeMachine.State state, int i, Boolean bool) {
        dozeTriggers.lambda$onWakeScreen$5(state, i, bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda8.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$jOpOJVByu0vLEfDDRQH01b2lHfI(DozeTriggers dozeTriggers, DozingUpdateUiEvent dozingUpdateUiEvent) {
        dozeTriggers.lambda$onWakeScreen$4(dozingUpdateUiEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda3.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$kZWKlw5U3jwELw-S9bsOWIogFko */
    public static /* synthetic */ void m2517$r8$lambda$kZWKlw5U3jwELwS9bsOWIogFko(DozeTriggers dozeTriggers, Runnable runnable, DozeMachine.State state, boolean z, int i, Boolean bool) {
        dozeTriggers.lambda$requestPulse$6(runnable, state, z, i, bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$tYVQpUBU5w_IkmuBvGNlrH1O8EI(DozeTriggers dozeTriggers, boolean z) {
        dozeTriggers.onProximityFar(z);
    }

    public DozeTriggers(Context context, DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeParameters dozeParameters, AsyncSensorManager asyncSensorManager, WakeLock wakeLock, DockManager dockManager, ProximitySensor proximitySensor, ProximityCheck proximityCheck, DozeLog dozeLog, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, AuthController authController, UiEventLogger uiEventLogger, SessionTracker sessionTracker, KeyguardStateController keyguardStateController, DevicePostureController devicePostureController, UserTracker userTracker) {
        this.mContext = context;
        this.mDozeHost = dozeHost;
        this.mConfig = ambientDisplayConfiguration;
        this.mDozeParameters = dozeParameters;
        this.mSensorManager = asyncSensorManager;
        this.mWakeLock = wakeLock;
        this.mSessionTracker = sessionTracker;
        this.mDozeSensors = new DozeSensors(context.getResources(), asyncSensorManager, dozeParameters, ambientDisplayConfiguration, wakeLock, new DozeSensors.Callback() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda1
            @Override // com.android.systemui.doze.DozeSensors.Callback
            public final void onSensorPulse(int i, boolean z, float f, float f2, float[] fArr) {
                DozeTriggers.this.onSensor(i, z, f, f2, fArr);
            }
        }, new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DozeTriggers.$r8$lambda$tYVQpUBU5w_IkmuBvGNlrH1O8EI(DozeTriggers.this, ((Boolean) obj).booleanValue());
            }
        }, dozeLog, proximitySensor, secureSettings, authController, devicePostureController, userTracker);
        this.mDockManager = dockManager;
        this.mProxCheck = proximityCheck;
        this.mDozeLog = dozeLog;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mAuthController = authController;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mUserTracker = userTracker;
    }

    public /* synthetic */ void lambda$gentleWakeUp$3(DozingUpdateUiEvent dozingUpdateUiEvent) {
        this.mUiEventLogger.log(dozingUpdateUiEvent, getKeyguardSessionId());
    }

    public /* synthetic */ void lambda$onSensor$1(float f, float f2, float[] fArr) {
        this.mAuthController.onAodInterrupt((int) f, (int) f2, fArr[3], fArr[4]);
    }

    public /* synthetic */ void lambda$onSensor$2(int i, boolean z, boolean z2, final float f, final float f2, boolean z3, boolean z4, final float[] fArr, Boolean bool) {
        if (bool != null && bool.booleanValue()) {
            this.mDozeLog.traceSensorEventDropped(i, "prox reporting near");
        } else if (z || z2) {
            if (f != -1.0f && f2 != -1.0f) {
                this.mDozeHost.onSlpiTap(f, f2);
            }
            gentleWakeUp(i);
        } else if (z3) {
            if (shouldDropPickupEvent()) {
                this.mDozeLog.traceSensorEventDropped(i, "keyguard occluded");
            } else {
                gentleWakeUp(i);
            }
        } else if (!z4) {
            this.mDozeHost.extendPulse(i);
        } else {
            if (canPulse(this.mMachine.getState(), true)) {
                this.mDozeLog.d("updfsLongPress - setting aodInterruptRunnable to run when the display is on");
                this.mAodInterruptRunnable = new Runnable() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        DozeTriggers.$r8$lambda$7x5GD8oRADMJNF_wwAFPsufPGw4(DozeTriggers.this, f, f2, fArr);
                    }
                };
            } else {
                this.mDozeLog.d("udfpsLongPress - Not sending aodInterrupt. Unsupported doze state.");
            }
            requestPulse(10, true, null);
        }
    }

    public /* synthetic */ void lambda$onWakeScreen$4(DozingUpdateUiEvent dozingUpdateUiEvent) {
        this.mUiEventLogger.log(dozingUpdateUiEvent, getKeyguardSessionId());
    }

    public /* synthetic */ void lambda$onWakeScreen$5(DozeMachine.State state, int i, Boolean bool) {
        if ((bool == null || !bool.booleanValue()) && state == DozeMachine.State.DOZE) {
            this.mMachine.requestState(DozeMachine.State.DOZE_AOD);
            Optional.ofNullable(DozingUpdateUiEvent.fromReason(i)).ifPresent(new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.$r8$lambda$jOpOJVByu0vLEfDDRQH01b2lHfI(DozeTriggers.this, (DozeTriggers.DozingUpdateUiEvent) obj);
                }
            });
        }
    }

    public /* synthetic */ void lambda$proximityCheckThenCall$0(long j, int i, Consumer consumer, Boolean bool) {
        this.mDozeLog.traceProximityResult(bool != null && bool.booleanValue(), SystemClock.uptimeMillis() - j, i);
        consumer.accept(bool);
        this.mWakeLock.release("DozeTriggers");
    }

    public /* synthetic */ void lambda$requestPulse$6(Runnable runnable, DozeMachine.State state, boolean z, int i, Boolean bool) {
        if (bool != null && bool.booleanValue()) {
            this.mDozeLog.tracePulseDropped("requestPulse - inPocket");
            this.mDozeHost.setPulsePending(false);
            runIfNotNull(runnable);
            return;
        }
        boolean isPulsePending = this.mDozeHost.isPulsePending();
        this.mDozeHost.setPulsePending(false);
        if (isPulsePending && !this.mDozeHost.isPulsingBlocked() && canPulse(state, z)) {
            this.mMachine.requestPulse(i);
            return;
        }
        if (!isPulsePending) {
            this.mDozeLog.tracePulseDropped("continuePulseRequest - pulse no longer pending, pulse was cancelled before it could start transitioning to pulsing state.");
        } else if (this.mDozeHost.isPulsingBlocked()) {
            this.mDozeLog.tracePulseDropped("continuePulseRequest - pulsingBlocked");
        } else if (!canPulse(state, z)) {
            this.mDozeLog.tracePulseDropped("continuePulseRequest - doze state cannot pulse", state);
        }
        runIfNotNull(runnable);
    }

    public /* synthetic */ void lambda$requestPulse$7(DozingUpdateUiEvent dozingUpdateUiEvent) {
        this.mUiEventLogger.log(dozingUpdateUiEvent, getKeyguardSessionId());
    }

    public static void runIfNotNull(Runnable runnable) {
        if (runnable != null) {
            runnable.run();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0042, code lost:
        if (r5 != false) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean canPulse(DozeMachine.State state, boolean z) {
        boolean z2;
        boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE_AOD_PAUSING;
        if (state != DozeMachine.State.DOZE && state != DozeMachine.State.DOZE_AOD && state != DozeMachine.State.DOZE_AOD_DOCKED) {
            z2 = false;
            if (z3) {
                z2 = false;
            }
            return z2;
        }
        z2 = true;
        return z2;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void destroy() {
        this.mDozeSensors.destroy();
        this.mProxCheck.destroy();
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter printWriter) {
        printWriter.println(" mAodInterruptRunnable=" + this.mAodInterruptRunnable);
        printWriter.print(" notificationPulseTime=");
        printWriter.println(Formatter.formatShortElapsedTime(this.mContext, this.mNotificationPulseTime));
        printWriter.println(" DozeHost#isPulsePending=" + this.mDozeHost.isPulsePending());
        printWriter.println("DozeSensors:");
        PrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        this.mDozeSensors.dump(indentingPrintWriter);
    }

    public final void gentleWakeUp(int i) {
        Optional.ofNullable(DozingUpdateUiEvent.fromReason(i)).ifPresent(new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda9
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DozeTriggers.m2515$r8$lambda$Ged5PTnhuW1c_qi61uYT7NnMaA(DozeTriggers.this, (DozeTriggers.DozingUpdateUiEvent) obj);
            }
        });
        if (this.mDozeParameters.getDisplayNeedsBlanking()) {
            this.mDozeHost.setAodDimmingScrim(1.0f);
        }
        this.mMachine.wakeUp(i);
    }

    public final InstanceId getKeyguardSessionId() {
        return this.mSessionTracker.getSessionId(1);
    }

    public final void onNotification(Runnable runnable) {
        if (DozeMachine.DEBUG) {
            Log.d("DozeTriggers", "requestNotificationPulse");
        }
        if (!sWakeDisplaySensorState) {
            Log.d("DozeTriggers", "Wake display false. Pulse denied.");
            runIfNotNull(runnable);
            this.mDozeLog.tracePulseDropped("wakeDisplaySensor");
            return;
        }
        this.mNotificationPulseTime = SystemClock.elapsedRealtime();
        if (!this.mConfig.pulseOnNotificationEnabled(this.mUserTracker.getUserId())) {
            runIfNotNull(runnable);
            this.mDozeLog.tracePulseDropped("pulseOnNotificationsDisabled");
        } else if (this.mDozeHost.isAlwaysOnSuppressed()) {
            runIfNotNull(runnable);
            this.mDozeLog.tracePulseDropped("dozeSuppressed");
        } else {
            requestPulse(1, false, runnable);
            this.mDozeLog.traceNotificationPulse();
        }
    }

    public final void onProximityFar(boolean z) {
        if (this.mMachine.isExecutingTransition()) {
            this.mDozeLog.d("onProximityFar called during transition. Ignoring sensor response.");
            return;
        }
        boolean z2 = !z;
        DozeMachine.State state = this.mMachine.getState();
        boolean z3 = false;
        boolean z4 = state == DozeMachine.State.DOZE_AOD_PAUSED;
        DozeMachine.State state2 = DozeMachine.State.DOZE_AOD_PAUSING;
        boolean z5 = state == state2;
        DozeMachine.State state3 = DozeMachine.State.DOZE_AOD;
        if (state == state3) {
            z3 = true;
        }
        if (state == DozeMachine.State.DOZE_PULSING || state == DozeMachine.State.DOZE_PULSING_BRIGHT) {
            this.mDozeLog.traceSetIgnoreTouchWhilePulsing(z2);
            this.mDozeHost.onIgnoreTouchWhilePulsing(z2);
        }
        if (z && (z4 || z5)) {
            this.mDozeLog.d("Prox FAR, unpausing AOD");
            this.mMachine.requestState(state3);
        } else if (z2 && z3) {
            this.mDozeLog.d("Prox NEAR, starting pausing AOD countdown");
            this.mMachine.requestState(state2);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void onScreenState(int i) {
        this.mDozeSensors.onScreenState(i);
        boolean z = i == 3 || i == 4 || i == 1;
        DozeSensors dozeSensors = this.mDozeSensors;
        boolean z2 = false;
        if (this.mWantProxSensor) {
            z2 = false;
            if (z) {
                z2 = true;
            }
        }
        dozeSensors.setProxListening(z2);
        this.mDozeSensors.setListening(this.mWantSensors, this.mWantTouchScreenSensors, z);
        Runnable runnable = this.mAodInterruptRunnable;
        if (runnable == null || i != 2) {
            return;
        }
        runnable.run();
        this.mAodInterruptRunnable = null;
    }

    @VisibleForTesting
    public void onSensor(final int i, boolean z, final float f, final float f2, final float[] fArr) {
        boolean z2 = i == 4;
        boolean z3 = i == 9;
        boolean z4 = i == 3;
        boolean z5 = i == 5;
        boolean z6 = i == 7;
        boolean z7 = i == 8;
        boolean z8 = i == 10;
        boolean z9 = i == 11;
        boolean z10 = z9 || ((z6 || z7) && fArr != null && fArr.length > 0 && fArr[0] != ActionBarShadowController.ELEVATION_LOW);
        if (z6) {
            onWakeScreen(z10, this.mMachine.isExecutingTransition() ? null : this.mMachine.getState(), i);
        } else if (z5) {
            requestPulse(i, z, null);
        } else if (!z7 && !z9) {
            final boolean z11 = z2;
            final boolean z12 = z3;
            final boolean z13 = z4;
            final boolean z14 = z8;
            proximityCheckThenCall(new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.$r8$lambda$0a0HT3fS9nKIxetOKlscese90qY(DozeTriggers.this, i, z11, z12, f, f2, z13, z14, fArr, (Boolean) obj);
                }
            }, z, i);
        } else if (z10) {
            requestPulse(i, z, null);
        }
        if (!z4 || shouldDropPickupEvent()) {
            return;
        }
        this.mDozeLog.tracePickupWakeUp(SystemClock.elapsedRealtime() - this.mNotificationPulseTime < ((long) this.mDozeParameters.getPickupVibrationThreshold()));
    }

    public final void onWakeScreen(boolean z, final DozeMachine.State state, final int i) {
        this.mDozeLog.traceWakeDisplay(z, i);
        sWakeDisplaySensorState = z;
        boolean z2 = false;
        if (z) {
            proximityCheckThenCall(new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.$r8$lambda$ifD47vLa_mHYa7FFrccrDH73jVI(DozeTriggers.this, state, i, (Boolean) obj);
                }
            }, false, i);
            return;
        }
        boolean z3 = state == DozeMachine.State.DOZE_AOD_PAUSED;
        if (state == DozeMachine.State.DOZE_AOD_PAUSING) {
            z2 = true;
        }
        if (z2 || z3) {
            return;
        }
        this.mMachine.requestState(DozeMachine.State.DOZE);
        this.mUiEventLogger.log(DozingUpdateUiEvent.DOZING_UPDATE_WAKE_TIMEOUT);
    }

    public final void proximityCheckThenCall(final Consumer<Boolean> consumer, boolean z, final int i) {
        Boolean isProximityCurrentlyNear = this.mDozeSensors.isProximityCurrentlyNear();
        if (z) {
            consumer.accept(null);
        } else if (isProximityCurrentlyNear != null) {
            consumer.accept(isProximityCurrentlyNear);
        } else {
            final long uptimeMillis = SystemClock.uptimeMillis();
            this.mProxCheck.check(500L, new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.m2516$r8$lambda$LLFaZptufirxDFdxqwlSJNi7g(DozeTriggers.this, uptimeMillis, i, consumer, (Boolean) obj);
                }
            });
            this.mWakeLock.acquire("DozeTriggers");
        }
    }

    public final void registerCallbacks() {
        this.mBroadcastReceiver.register(this.mBroadcastDispatcher);
        this.mDockManager.addListener(this.mDockEventListener);
        this.mDozeHost.addCallback(this.mHostCallback);
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
    }

    public final void requestPulse(final int i, final boolean z, final Runnable runnable) {
        Assert.isMainThread();
        this.mDozeHost.extendPulse(i);
        DozeMachine.State state = this.mMachine.isExecutingTransition() ? null : this.mMachine.getState();
        if (state == DozeMachine.State.DOZE_PULSING && i == 8) {
            this.mMachine.requestState(DozeMachine.State.DOZE_PULSING_BRIGHT);
        } else if (!this.mAllowPulseTriggers || this.mDozeHost.isPulsePending() || !canPulse(state, z)) {
            if (!this.mAllowPulseTriggers) {
                this.mDozeLog.tracePulseDropped("requestPulse - !mAllowPulseTriggers");
            } else if (this.mDozeHost.isPulsePending()) {
                this.mDozeLog.tracePulseDropped("requestPulse - pulsePending");
            } else if (!canPulse(state, z)) {
                this.mDozeLog.tracePulseDropped("requestPulse - dozeState cannot pulse", state);
            }
            runIfNotNull(runnable);
        } else {
            this.mDozeHost.setPulsePending(true);
            final DozeMachine.State state2 = state;
            Consumer<Boolean> consumer = new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.m2517$r8$lambda$kZWKlw5U3jwELwS9bsOWIogFko(DozeTriggers.this, runnable, state2, z, i, (Boolean) obj);
                }
            };
            boolean z2 = true;
            if (this.mDozeParameters.getProxCheckBeforePulse()) {
                z2 = z;
            }
            proximityCheckThenCall(consumer, z2, i);
            Optional.ofNullable(DozingUpdateUiEvent.fromReason(i)).ifPresent(new Consumer() { // from class: com.android.systemui.doze.DozeTriggers$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    DozeTriggers.m2514$r8$lambda$9jRAaGV_QR9imFeIDq8ZoA3_B0(DozeTriggers.this, (DozeTriggers.DozingUpdateUiEvent) obj);
                }
            });
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    public final boolean shouldDropPickupEvent() {
        return this.mKeyguardStateController.isOccluded();
    }

    public final void stopListeningToAllTriggers() {
        unregisterCallbacks();
        this.mDozeSensors.setListening(false, false);
        this.mDozeSensors.setProxListening(false);
        this.mWantSensors = false;
        this.mWantProxSensor = false;
        this.mWantTouchScreenSensors = false;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (state == DozeMachine.State.DOZE_SUSPEND_TRIGGERS && state2 != DozeMachine.State.FINISH && state2 != DozeMachine.State.UNINITIALIZED) {
            registerCallbacks();
        }
        switch (AnonymousClass3.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
                this.mAodInterruptRunnable = null;
                sWakeDisplaySensorState = true;
                registerCallbacks();
                this.mDozeSensors.requestTemporaryDisable();
                break;
            case 2:
            case 3:
                this.mAodInterruptRunnable = null;
                this.mWantProxSensor = state2 != DozeMachine.State.DOZE;
                this.mWantSensors = true;
                this.mWantTouchScreenSensors = true;
                if (state2 == DozeMachine.State.DOZE_AOD && !sWakeDisplaySensorState) {
                    onWakeScreen(false, state2, 7);
                    break;
                }
                break;
            case 4:
            case 5:
                this.mWantProxSensor = true;
                break;
            case 6:
            case 7:
                this.mWantProxSensor = true;
                this.mWantTouchScreenSensors = false;
                break;
            case 8:
                this.mWantProxSensor = false;
                this.mWantTouchScreenSensors = false;
                break;
            case 9:
                this.mDozeSensors.requestTemporaryDisable();
                break;
            case 10:
            case 11:
                stopListeningToAllTriggers();
                break;
        }
        this.mDozeSensors.setListening(this.mWantSensors, this.mWantTouchScreenSensors);
    }

    public final void unregisterCallbacks() {
        this.mBroadcastReceiver.unregister(this.mBroadcastDispatcher);
        this.mDozeHost.removeCallback(this.mHostCallback);
        this.mDockManager.removeListener(this.mDockEventListener);
        this.mUserTracker.removeCallback(this.mUserChangedCallback);
    }
}