package com.android.settingslib.mobile;

import android.os.Handler;
import android.os.Looper;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileStatusTracker.class */
public class MobileStatusTracker {
    public final Callback mCallback;
    public final SubscriptionDefaults mDefaults;
    public boolean mListening = false;
    public final MobileStatus mMobileStatus;
    public final TelephonyManager mPhone;
    public final Handler mReceiverHandler;
    public final SubscriptionInfo mSubscriptionInfo;
    public final MobileTelephonyCallback mTelephonyCallback;

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileStatusTracker$Callback.class */
    public interface Callback {
        void onMobileStatusChanged(boolean z, MobileStatus mobileStatus);
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileStatusTracker$MobileStatus.class */
    public static class MobileStatus {
        public boolean activityIn;
        public boolean activityOut;
        public boolean carrierNetworkChangeMode;
        public boolean dataSim;
        public ServiceState serviceState;
        public SignalStrength signalStrength;
        public int dataState = 0;
        public TelephonyDisplayInfo telephonyDisplayInfo = new TelephonyDisplayInfo(0, 0);

        public MobileStatus() {
        }

        public MobileStatus(MobileStatus mobileStatus) {
            copyFrom(mobileStatus);
        }

        public void copyFrom(MobileStatus mobileStatus) {
            this.activityIn = mobileStatus.activityIn;
            this.activityOut = mobileStatus.activityOut;
            this.dataSim = mobileStatus.dataSim;
            this.carrierNetworkChangeMode = mobileStatus.carrierNetworkChangeMode;
            this.dataState = mobileStatus.dataState;
            this.serviceState = mobileStatus.serviceState;
            this.signalStrength = mobileStatus.signalStrength;
            this.telephonyDisplayInfo = mobileStatus.telephonyDisplayInfo;
        }

        public String toString() {
            String str;
            StringBuilder sb = new StringBuilder();
            sb.append("[activityIn=");
            sb.append(this.activityIn);
            sb.append(',');
            sb.append("activityOut=");
            sb.append(this.activityOut);
            sb.append(',');
            sb.append("dataSim=");
            sb.append(this.dataSim);
            sb.append(',');
            sb.append("carrierNetworkChangeMode=");
            sb.append(this.carrierNetworkChangeMode);
            sb.append(',');
            sb.append("dataState=");
            sb.append(this.dataState);
            sb.append(',');
            sb.append("serviceState=");
            if (this.serviceState == null) {
                str = "";
            } else {
                str = "mVoiceRegState=" + this.serviceState.getState() + "(" + ServiceState.rilServiceStateToString(this.serviceState.getState()) + "), mDataRegState=" + this.serviceState.getDataRegState() + "(" + ServiceState.rilServiceStateToString(this.serviceState.getDataRegState()) + ")";
            }
            sb.append(str);
            sb.append(',');
            sb.append("signalStrength=");
            SignalStrength signalStrength = this.signalStrength;
            sb.append(signalStrength == null ? "" : Integer.valueOf(signalStrength.getLevel()));
            sb.append(',');
            sb.append("telephonyDisplayInfo=");
            TelephonyDisplayInfo telephonyDisplayInfo = this.telephonyDisplayInfo;
            sb.append(telephonyDisplayInfo == null ? "" : telephonyDisplayInfo.toString());
            sb.append(']');
            return sb.toString();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileStatusTracker$MobileTelephonyCallback.class */
    public class MobileTelephonyCallback extends TelephonyCallback implements TelephonyCallback.ServiceStateListener, TelephonyCallback.SignalStrengthsListener, TelephonyCallback.DataConnectionStateListener, TelephonyCallback.DataActivityListener, TelephonyCallback.CarrierNetworkListener, TelephonyCallback.ActiveDataSubscriptionIdListener, TelephonyCallback.DisplayInfoListener {
        public MobileTelephonyCallback() {
            MobileStatusTracker.this = r4;
        }

        @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
        public void onActiveDataSubscriptionIdChanged(int i) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onActiveDataSubscriptionIdChanged: subId=" + i);
            }
            MobileStatusTracker.this.updateDataSim();
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.CarrierNetworkListener
        public void onCarrierNetworkChange(boolean z) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onCarrierNetworkChange: active=" + z);
            }
            MobileStatusTracker.this.mMobileStatus.carrierNetworkChangeMode = z;
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DataActivityListener
        public void onDataActivity(int i) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onDataActivity: direction=" + i);
            }
            MobileStatusTracker.this.setActivity(i);
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(false, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DataConnectionStateListener
        public void onDataConnectionStateChanged(int i, int i2) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onDataConnectionStateChanged: state=" + i + " type=" + i2);
            }
            MobileStatusTracker.this.mMobileStatus.dataState = i;
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.DisplayInfoListener
        public void onDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                Log.d("MobileStatusTracker", "onDisplayInfoChanged: telephonyDisplayInfo=" + telephonyDisplayInfo);
            }
            MobileStatusTracker.this.mMobileStatus.telephonyDisplayInfo = telephonyDisplayInfo;
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.ServiceStateListener
        public void onServiceStateChanged(ServiceState serviceState) {
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("onServiceStateChanged voiceState=");
                sb.append(serviceState == null ? "" : Integer.valueOf(serviceState.getState()));
                sb.append(" dataState=");
                sb.append(serviceState == null ? "" : Integer.valueOf(serviceState.getDataRegistrationState()));
                Log.d("MobileStatusTracker", sb.toString());
            }
            MobileStatusTracker.this.mMobileStatus.serviceState = serviceState;
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }

        @Override // android.telephony.TelephonyCallback.SignalStrengthsListener
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            String str;
            if (Log.isLoggable("MobileStatusTracker", 3)) {
                StringBuilder sb = new StringBuilder();
                sb.append("onSignalStrengthsChanged signalStrength=");
                sb.append(signalStrength);
                if (signalStrength == null) {
                    str = "";
                } else {
                    str = " level=" + signalStrength.getLevel();
                }
                sb.append(str);
                Log.d("MobileStatusTracker", sb.toString());
            }
            MobileStatusTracker.this.mMobileStatus.signalStrength = signalStrength;
            MobileStatusTracker.this.mCallback.onMobileStatusChanged(true, new MobileStatus(MobileStatusTracker.this.mMobileStatus));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileStatusTracker$SubscriptionDefaults.class */
    public static class SubscriptionDefaults {
        public int getActiveDataSubId() {
            return SubscriptionManager.getActiveDataSubscriptionId();
        }

        public int getDefaultDataSubId() {
            return SubscriptionManager.getDefaultDataSubscriptionId();
        }

        public int getDefaultVoiceSubId() {
            return SubscriptionManager.getDefaultVoiceSubscriptionId();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$hp3_06HtFGHEpyHOUdie5xxysLU(MobileStatusTracker mobileStatusTracker) {
        mobileStatusTracker.lambda$new$0();
    }

    public MobileStatusTracker(TelephonyManager telephonyManager, Looper looper, SubscriptionInfo subscriptionInfo, SubscriptionDefaults subscriptionDefaults, Callback callback) {
        this.mPhone = telephonyManager;
        Handler handler = new Handler(looper);
        this.mReceiverHandler = handler;
        this.mTelephonyCallback = new MobileTelephonyCallback();
        this.mSubscriptionInfo = subscriptionInfo;
        this.mDefaults = subscriptionDefaults;
        this.mCallback = callback;
        this.mMobileStatus = new MobileStatus();
        updateDataSim();
        handler.post(new Runnable() { // from class: com.android.settingslib.mobile.MobileStatusTracker$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MobileStatusTracker.$r8$lambda$hp3_06HtFGHEpyHOUdie5xxysLU(MobileStatusTracker.this);
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        this.mCallback.onMobileStatusChanged(false, new MobileStatus(this.mMobileStatus));
    }

    public boolean isListening() {
        return this.mListening;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x002d, code lost:
        if (r4 == 2) goto L13;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setActivity(int i) {
        boolean z;
        MobileStatus mobileStatus = this.mMobileStatus;
        mobileStatus.activityIn = i == 3 || i == 1;
        if (i != 3) {
            z = false;
        }
        z = true;
        mobileStatus.activityOut = z;
    }

    public void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (!z) {
            this.mPhone.unregisterTelephonyCallback(this.mTelephonyCallback);
            return;
        }
        TelephonyManager telephonyManager = this.mPhone;
        Handler handler = this.mReceiverHandler;
        Objects.requireNonNull(handler);
        telephonyManager.registerTelephonyCallback(new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), this.mTelephonyCallback);
    }

    public final void updateDataSim() {
        int activeDataSubId = this.mDefaults.getActiveDataSubId();
        boolean z = true;
        if (!SubscriptionManager.isValidSubscriptionId(activeDataSubId)) {
            this.mMobileStatus.dataSim = true;
            return;
        }
        MobileStatus mobileStatus = this.mMobileStatus;
        if (activeDataSubId != this.mSubscriptionInfo.getSubscriptionId()) {
            z = false;
        }
        mobileStatus.dataSim = z;
    }
}