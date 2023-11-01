package com.android.keyguard;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.keyguard.CarrierTextManager;
import com.android.settingslib.WirelessUtils;
import com.android.systemui.R$string;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.telephony.TelephonyListenerManager;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager.class */
public class CarrierTextManager {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final Executor mBgExecutor;
    public final KeyguardUpdateMonitorCallback mCallback;
    public CarrierTextCallback mCarrierTextCallback;
    public final Context mContext;
    public final boolean mIsEmergencyCallCapable;
    public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final Executor mMainExecutor;
    public final AtomicBoolean mNetworkSupported;
    public final TelephonyCallback.ActiveDataSubscriptionIdListener mPhoneStateListener;
    public final CharSequence mSeparator;
    public final boolean mShowAirplaneMode;
    public final boolean mShowMissingSim;
    public final boolean[] mSimErrorState;
    public final int mSimSlotsNumber;
    public boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WakefulnessLifecycle.Observer mWakefulnessObserver;
    public final WifiManager mWifiManager;

    /* renamed from: com.android.keyguard.CarrierTextManager$4 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager$4.class */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:101:0x0099 -> B:107:0x0064). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:103:0x009d -> B:121:0x0070). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:87:0x007d -> B:125:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:89:0x0081 -> B:119:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:91:0x0085 -> B:115:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:93:0x0089 -> B:109:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x008d -> B:123:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:97:0x0091 -> B:117:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x0095 -> B:113:0x0058). Please submit an issue!!! */
        static {
            int[] iArr = new int[StatusMode.values().length];
            $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode = iArr;
            try {
                iArr[StatusMode.Normal.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimNotReady.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.NetworkLocked.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimMissing.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimPermDisabled.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimMissingLocked.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimLocked.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimPukLocked.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimIoError.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[StatusMode.SimUnknown.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager$Builder.class */
    public static class Builder {
        public final Executor mBgExecutor;
        public final Context mContext;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final Executor mMainExecutor;
        public final String mSeparator;
        public boolean mShowAirplaneMode;
        public boolean mShowMissingSim;
        public final TelephonyListenerManager mTelephonyListenerManager;
        public final TelephonyManager mTelephonyManager;
        public final WakefulnessLifecycle mWakefulnessLifecycle;
        public final WifiManager mWifiManager;

        public Builder(Context context, Resources resources, WifiManager wifiManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor) {
            this.mContext = context;
            this.mSeparator = resources.getString(17040590);
            this.mWifiManager = wifiManager;
            this.mTelephonyManager = telephonyManager;
            this.mTelephonyListenerManager = telephonyListenerManager;
            this.mWakefulnessLifecycle = wakefulnessLifecycle;
            this.mMainExecutor = executor;
            this.mBgExecutor = executor2;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        }

        public CarrierTextManager build() {
            return new CarrierTextManager(this.mContext, this.mSeparator, this.mShowAirplaneMode, this.mShowMissingSim, this.mWifiManager, this.mTelephonyManager, this.mTelephonyListenerManager, this.mWakefulnessLifecycle, this.mMainExecutor, this.mBgExecutor, this.mKeyguardUpdateMonitor, null);
        }

        public Builder setShowAirplaneMode(boolean z) {
            this.mShowAirplaneMode = z;
            return this;
        }

        public Builder setShowMissingSim(boolean z) {
            this.mShowMissingSim = z;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager$CarrierTextCallback.class */
    public interface CarrierTextCallback {
        default void finishedWakingUp() {
        }

        default void startedGoingToSleep() {
        }

        default void updateCarrierInfo(CarrierTextCallbackInfo carrierTextCallbackInfo) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager$CarrierTextCallbackInfo.class */
    public static final class CarrierTextCallbackInfo {
        public boolean airplaneMode;
        public final boolean anySimReady;
        public final CharSequence carrierText;
        public final CharSequence[] listOfCarriers;
        public final int[] subscriptionIds;

        public CarrierTextCallbackInfo(CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int[] iArr) {
            this(charSequence, charSequenceArr, z, iArr, false);
        }

        public CarrierTextCallbackInfo(CharSequence charSequence, CharSequence[] charSequenceArr, boolean z, int[] iArr, boolean z2) {
            this.carrierText = charSequence;
            this.listOfCarriers = charSequenceArr;
            this.anySimReady = z;
            this.subscriptionIds = iArr;
            this.airplaneMode = z2;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/CarrierTextManager$StatusMode.class */
    public enum StatusMode {
        Normal,
        NetworkLocked,
        SimMissing,
        SimMissingLocked,
        SimPukLocked,
        SimLocked,
        SimPermDisabled,
        SimNotReady,
        SimIoError,
        SimUnknown
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$1PcBlFCWZvW0k91my1MlV7sgoXc(CarrierTextCallback carrierTextCallback, CarrierTextCallbackInfo carrierTextCallbackInfo) {
        carrierTextCallback.updateCarrierInfo(carrierTextCallbackInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$5puRjU3HECxaIJwQRD-WC5AlU-s */
    public static /* synthetic */ void m532$r8$lambda$5puRjU3HECxaIJwQRDWC5AlUs(CarrierTextManager carrierTextManager, CarrierTextCallback carrierTextCallback) {
        carrierTextManager.lambda$setListening$4(carrierTextCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$63achlzikhs62sf08YycVz-B9Zk */
    public static /* synthetic */ void m533$r8$lambda$63achlzikhs62sf08YycVzB9Zk(CarrierTextCallback carrierTextCallback) {
        lambda$handleSetListening$2(carrierTextCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$L9d1mgw5vNjn8Mg05dhUwzOrxVU(CarrierTextManager carrierTextManager) {
        carrierTextManager.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$b5bD2vFXFy_TMV_aPSbFpUnQzPE(CarrierTextManager carrierTextManager) {
        carrierTextManager.lambda$handleSetListening$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$yPhTveG85QRLdQUPcWaqi_CIZI0(CarrierTextManager carrierTextManager) {
        carrierTextManager.lambda$handleSetListening$1();
    }

    public CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiManager wifiManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mNetworkSupported = new AtomicBoolean();
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.keyguard.CarrierTextManager.1
            {
                CarrierTextManager.this = this;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedWakingUp() {
                CarrierTextCallback carrierTextCallback = CarrierTextManager.this.mCarrierTextCallback;
                if (carrierTextCallback != null) {
                    carrierTextCallback.finishedWakingUp();
                }
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedGoingToSleep() {
                CarrierTextCallback carrierTextCallback = CarrierTextManager.this.mCarrierTextCallback;
                if (carrierTextCallback != null) {
                    carrierTextCallback.startedGoingToSleep();
                }
            }
        };
        this.mCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.CarrierTextManager.2
            {
                CarrierTextManager.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onRefreshCarrierInfo() {
                if (CarrierTextManager.DEBUG) {
                    Log.d("CarrierTextController", "onRefreshCarrierInfo(), mTelephonyCapable: " + Boolean.toString(CarrierTextManager.this.mTelephonyCapable));
                }
                CarrierTextManager.this.updateCarrierText();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                if (i2 < 0 || i2 >= CarrierTextManager.this.mSimSlotsNumber) {
                    Log.d("CarrierTextController", "onSimStateChanged() - slotId invalid: " + i2 + " mTelephonyCapable: " + Boolean.toString(CarrierTextManager.this.mTelephonyCapable));
                    return;
                }
                if (CarrierTextManager.DEBUG) {
                    Log.d("CarrierTextController", "onSimStateChanged: " + CarrierTextManager.this.getStatusForIccState(i3));
                }
                if (CarrierTextManager.this.getStatusForIccState(i3) == StatusMode.SimIoError) {
                    CarrierTextManager.this.mSimErrorState[i2] = true;
                    CarrierTextManager.this.updateCarrierText();
                } else if (CarrierTextManager.this.mSimErrorState[i2]) {
                    CarrierTextManager.this.mSimErrorState[i2] = false;
                    CarrierTextManager.this.updateCarrierText();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onTelephonyCapable(boolean z3) {
                if (CarrierTextManager.DEBUG) {
                    Log.d("CarrierTextController", "onTelephonyCapable() mTelephonyCapable: " + Boolean.toString(z3));
                }
                CarrierTextManager.this.mTelephonyCapable = z3;
                CarrierTextManager.this.updateCarrierText();
            }
        };
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.CarrierTextManager.3
            {
                CarrierTextManager.this = this;
            }

            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public void onActiveDataSubscriptionIdChanged(int i) {
                if (!CarrierTextManager.this.mNetworkSupported.get() || CarrierTextManager.this.mCarrierTextCallback == null) {
                    return;
                }
                CarrierTextManager.this.updateCarrierText();
            }
        };
        this.mContext = context;
        this.mIsEmergencyCallCapable = telephonyManager.isVoiceCapable();
        this.mShowAirplaneMode = z;
        this.mShowMissingSim = z2;
        this.mWifiManager = wifiManager;
        this.mTelephonyManager = telephonyManager;
        this.mSeparator = charSequence;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        int supportedModemCount = getTelephonyManager().getSupportedModemCount();
        this.mSimSlotsNumber = supportedModemCount;
        this.mSimErrorState = new boolean[supportedModemCount];
        this.mMainExecutor = executor;
        this.mBgExecutor = executor2;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        executor2.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CarrierTextManager.$r8$lambda$L9d1mgw5vNjn8Mg05dhUwzOrxVU(CarrierTextManager.this);
            }
        });
    }

    public /* synthetic */ CarrierTextManager(Context context, CharSequence charSequence, boolean z, boolean z2, WifiManager wifiManager, TelephonyManager telephonyManager, TelephonyListenerManager telephonyListenerManager, WakefulnessLifecycle wakefulnessLifecycle, Executor executor, Executor executor2, KeyguardUpdateMonitor keyguardUpdateMonitor, CarrierTextManager-IA r25) {
        this(context, charSequence, z, z2, wifiManager, telephonyManager, telephonyListenerManager, wakefulnessLifecycle, executor, executor2, keyguardUpdateMonitor);
    }

    /* JADX DEBUG: TODO: convert one arg to string using `String.valueOf()`, args: [(r3v0 java.lang.CharSequence), (r5v0 java.lang.CharSequence), (r4v0 java.lang.CharSequence)] */
    public static CharSequence concatenate(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        boolean z = !TextUtils.isEmpty(charSequence);
        boolean z2 = !TextUtils.isEmpty(charSequence2);
        if (!z || !z2) {
            return z ? charSequence : z2 ? charSequence2 : "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(charSequence);
        sb.append(charSequence3);
        sb.append(charSequence2);
        return sb.toString();
    }

    public static CharSequence joinNotEmpty(CharSequence charSequence, CharSequence[] charSequenceArr) {
        int length = charSequenceArr.length;
        if (length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (!TextUtils.isEmpty(charSequenceArr[i])) {
                if (!TextUtils.isEmpty(sb)) {
                    sb.append(charSequence);
                }
                sb.append(charSequenceArr[i]);
            }
        }
        return sb.toString();
    }

    public /* synthetic */ void lambda$handleSetListening$1() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mCallback);
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
    }

    public static /* synthetic */ void lambda$handleSetListening$2(CarrierTextCallback carrierTextCallback) {
        carrierTextCallback.updateCarrierInfo(new CarrierTextCallbackInfo("", null, false, null));
    }

    public /* synthetic */ void lambda$handleSetListening$3() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mCallback);
        this.mWakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
    }

    public /* synthetic */ void lambda$new$0() {
        boolean hasSystemFeature = this.mContext.getPackageManager().hasSystemFeature("android.hardware.telephony");
        if (hasSystemFeature && this.mNetworkSupported.compareAndSet(false, hasSystemFeature)) {
            lambda$setListening$4(this.mCarrierTextCallback);
        }
    }

    public final String getAirplaneModeMessage() {
        return this.mShowAirplaneMode ? getContext().getString(R$string.airplane_mode) : "";
    }

    public final CharSequence getCarrierTextForSimState(int i, CharSequence charSequence) {
        CharSequence charSequence2 = charSequence;
        switch (AnonymousClass4.$SwitchMap$com$android$keyguard$CarrierTextManager$StatusMode[getStatusForIccState(i).ordinal()]) {
            case 1:
                break;
            case 2:
                charSequence2 = "";
                break;
            case 3:
                charSequence2 = makeCarrierStringOnEmergencyCapable(this.mContext.getText(R$string.keyguard_network_locked_message), charSequence);
                break;
            case 4:
            case 6:
            case 10:
            default:
                charSequence2 = null;
                break;
            case 5:
                charSequence2 = makeCarrierStringOnEmergencyCapable(getContext().getText(R$string.keyguard_permanent_disabled_sim_message_short), charSequence);
                break;
            case 7:
                charSequence2 = makeCarrierStringOnLocked(getContext().getText(R$string.keyguard_sim_locked_message), charSequence);
                break;
            case 8:
                charSequence2 = makeCarrierStringOnLocked(getContext().getText(R$string.keyguard_sim_puk_locked_message), charSequence);
                break;
            case 9:
                charSequence2 = makeCarrierStringOnEmergencyCapable(getContext().getText(R$string.keyguard_sim_error_message_short), charSequence);
                break;
        }
        return charSequence2;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final String getMissingSimMessage() {
        return (this.mShowMissingSim && this.mTelephonyCapable) ? getContext().getString(R$string.keyguard_missing_sim_message_short) : "";
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0084  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final StatusMode getStatusForIccState(int i) {
        boolean z;
        if (!this.mKeyguardUpdateMonitor.isDeviceProvisioned()) {
            z = true;
            if (i != 1) {
                if (i == 7) {
                    z = true;
                }
            }
            if (z) {
                i = 4;
            }
            switch (i) {
                case 0:
                    return StatusMode.SimUnknown;
                case 1:
                    return StatusMode.SimMissing;
                case 2:
                    return StatusMode.SimLocked;
                case 3:
                    return StatusMode.SimPukLocked;
                case 4:
                    return StatusMode.SimMissingLocked;
                case 5:
                    return StatusMode.Normal;
                case 6:
                    return StatusMode.SimNotReady;
                case 7:
                    return StatusMode.SimPermDisabled;
                case 8:
                    return StatusMode.SimIoError;
                default:
                    return StatusMode.SimUnknown;
            }
        }
        z = false;
        if (z) {
        }
        switch (i) {
        }
    }

    public List<SubscriptionInfo> getSubscriptionInfo() {
        return this.mKeyguardUpdateMonitor.getFilteredSubscriptionInfo();
    }

    public final TelephonyManager getTelephonyManager() {
        return this.mTelephonyManager;
    }

    /* renamed from: handleSetListening */
    public final void lambda$setListening$4(final CarrierTextCallback carrierTextCallback) {
        if (carrierTextCallback == null) {
            this.mCarrierTextCallback = null;
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierTextManager.$r8$lambda$b5bD2vFXFy_TMV_aPSbFpUnQzPE(CarrierTextManager.this);
                }
            });
            this.mTelephonyListenerManager.removeActiveDataSubscriptionIdListener(this.mPhoneStateListener);
            return;
        }
        this.mCarrierTextCallback = carrierTextCallback;
        if (!this.mNetworkSupported.get()) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierTextManager.m533$r8$lambda$63achlzikhs62sf08YycVzB9Zk(CarrierTextManager.CarrierTextCallback.this);
                }
            });
            return;
        }
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CarrierTextManager.$r8$lambda$yPhTveG85QRLdQUPcWaqi_CIZI0(CarrierTextManager.this);
            }
        });
        this.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(this.mPhoneStateListener);
    }

    public final CharSequence makeCarrierStringOnEmergencyCapable(CharSequence charSequence, CharSequence charSequence2) {
        return this.mIsEmergencyCallCapable ? concatenate(charSequence, charSequence2, this.mSeparator) : charSequence;
    }

    public final CharSequence makeCarrierStringOnLocked(CharSequence charSequence, CharSequence charSequence2) {
        boolean z = !TextUtils.isEmpty(charSequence);
        boolean z2 = !TextUtils.isEmpty(charSequence2);
        return (z && z2) ? this.mContext.getString(R$string.keyguard_carrier_name_with_sim_locked_template, charSequence2, charSequence) : z ? charSequence : z2 ? charSequence2 : "";
    }

    public void postToCallback(final CarrierTextCallbackInfo carrierTextCallbackInfo) {
        final CarrierTextCallback carrierTextCallback = this.mCarrierTextCallback;
        if (carrierTextCallback != null) {
            this.mMainExecutor.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CarrierTextManager.$r8$lambda$1PcBlFCWZvW0k91my1MlV7sgoXc(CarrierTextManager.CarrierTextCallback.this, carrierTextCallbackInfo);
                }
            });
        }
    }

    public void setListening(final CarrierTextCallback carrierTextCallback) {
        this.mBgExecutor.execute(new Runnable() { // from class: com.android.keyguard.CarrierTextManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CarrierTextManager.m532$r8$lambda$5puRjU3HECxaIJwQRDWC5AlUs(CarrierTextManager.this, carrierTextCallback);
            }
        });
    }

    public void updateCarrierText() {
        boolean z;
        boolean z2;
        CharSequence carrierName;
        List<SubscriptionInfo> subscriptionInfo = getSubscriptionInfo();
        int size = subscriptionInfo.size();
        int[] iArr = new int[size];
        int[] iArr2 = new int[this.mSimSlotsNumber];
        for (int i = 0; i < this.mSimSlotsNumber; i++) {
            iArr2[i] = -1;
        }
        CharSequence[] charSequenceArr = new CharSequence[size];
        if (DEBUG) {
            Log.d("CarrierTextController", "updateCarrierText(): " + size);
        }
        int i2 = 0;
        boolean z3 = true;
        boolean z4 = false;
        while (true) {
            z = z4;
            if (i2 >= size) {
                break;
            }
            int subscriptionId = subscriptionInfo.get(i2).getSubscriptionId();
            charSequenceArr[i2] = "";
            iArr[i2] = subscriptionId;
            iArr2[subscriptionInfo.get(i2).getSimSlotIndex()] = i2;
            int simState = this.mKeyguardUpdateMonitor.getSimState(subscriptionId);
            CharSequence carrierTextForSimState = getCarrierTextForSimState(simState, subscriptionInfo.get(i2).getCarrierName());
            boolean z5 = DEBUG;
            if (z5) {
                Log.d("CarrierTextController", "Handling (subId=" + subscriptionId + "): " + simState + " " + ((Object) carrierName));
            }
            if (carrierTextForSimState != null) {
                charSequenceArr[i2] = carrierTextForSimState;
                z3 = false;
            }
            boolean z6 = z;
            if (simState == 5) {
                ServiceState serviceState = this.mKeyguardUpdateMonitor.mServiceStates.get(Integer.valueOf(subscriptionId));
                z6 = z;
                if (serviceState != null) {
                    z6 = z;
                    if (serviceState.getDataRegistrationState() == 0) {
                        if (serviceState.getRilDataRadioTechnology() == 18) {
                            WifiManager wifiManager = this.mWifiManager;
                            z6 = z;
                            if (wifiManager != null) {
                                z6 = z;
                                if (wifiManager.isWifiEnabled()) {
                                    z6 = z;
                                    if (this.mWifiManager.getConnectionInfo() != null) {
                                        z6 = z;
                                        if (this.mWifiManager.getConnectionInfo().getBSSID() == null) {
                                        }
                                    }
                                }
                            }
                        }
                        if (z5) {
                            Log.d("CarrierTextController", "SIM ready and in service: subId=" + subscriptionId + ", ss=" + serviceState);
                        }
                        z6 = true;
                    }
                }
            }
            i2++;
            z4 = z6;
        }
        CharSequence charSequence = null;
        if (z3) {
            charSequence = null;
            if (!z) {
                if (size != 0) {
                    charSequence = makeCarrierStringOnEmergencyCapable(getMissingSimMessage(), subscriptionInfo.get(0).getCarrierName());
                } else {
                    String text = getContext().getText(17040217);
                    Intent registerReceiver = getContext().registerReceiver(null, new IntentFilter("android.telephony.action.SERVICE_PROVIDERS_UPDATED"));
                    if (registerReceiver != null) {
                        String stringExtra = registerReceiver.getBooleanExtra("android.telephony.extra.SHOW_SPN", false) ? registerReceiver.getStringExtra("android.telephony.extra.SPN") : "";
                        text = registerReceiver.getBooleanExtra("android.telephony.extra.SHOW_PLMN", false) ? registerReceiver.getStringExtra("android.telephony.extra.PLMN") : "";
                        if (DEBUG) {
                            Log.d("CarrierTextController", "Getting plmn/spn sticky brdcst " + text + "/" + stringExtra);
                        }
                        if (!Objects.equals(text, stringExtra)) {
                            text = concatenate(text, stringExtra, this.mSeparator);
                        }
                    }
                    charSequence = makeCarrierStringOnEmergencyCapable(getMissingSimMessage(), text);
                }
            }
        }
        CharSequence charSequence2 = charSequence;
        if (TextUtils.isEmpty(charSequence)) {
            charSequence2 = joinNotEmpty(this.mSeparator, charSequenceArr);
        }
        String updateCarrierTextWithSimIoError = updateCarrierTextWithSimIoError(charSequence2, charSequenceArr, iArr2, z3);
        if (z || !WirelessUtils.isAirplaneModeOn(this.mContext)) {
            z2 = false;
        } else {
            updateCarrierTextWithSimIoError = getAirplaneModeMessage();
            z2 = true;
        }
        postToCallback(new CarrierTextCallbackInfo(updateCarrierTextWithSimIoError, charSequenceArr, !z3, iArr, z2));
    }

    public final CharSequence updateCarrierTextWithSimIoError(CharSequence charSequence, CharSequence[] charSequenceArr, int[] iArr, boolean z) {
        CharSequence carrierTextForSimState = getCarrierTextForSimState(8, "");
        for (int i = 0; i < getTelephonyManager().getActiveModemCount(); i++) {
            if (this.mSimErrorState[i]) {
                if (z) {
                    return concatenate(carrierTextForSimState, getContext().getText(17040217), this.mSeparator);
                }
                int i2 = iArr[i];
                if (i2 != -1) {
                    charSequenceArr[i2] = concatenate(carrierTextForSimState, charSequenceArr[i2], this.mSeparator);
                } else {
                    charSequence = concatenate(charSequence, carrierTextForSimState, this.mSeparator);
                }
            }
        }
        return charSequence;
    }
}