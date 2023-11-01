package com.android.systemui.qs.carrier;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.android.keyguard.CarrierTextManager;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.mobile.TelephonyIcons;
import com.android.systemui.R$drawable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.connectivity.IconState;
import com.android.systemui.statusbar.connectivity.MobileDataIndicators;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.SignalCallback;
import com.android.systemui.util.CarrierConfigTracker;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController.class */
public class QSCarrierGroupController {
    public final ActivityStarter mActivityStarter;
    public final Handler mBgHandler;
    public final Callback mCallback;
    public final CarrierConfigTracker mCarrierConfigTracker;
    public View[] mCarrierDividers;
    public QSCarrier[] mCarrierGroups;
    public final CarrierTextManager mCarrierTextManager;
    public final CellSignalState[] mInfos;
    public boolean mIsSingleCarrier;
    public int[] mLastSignalLevel;
    public String[] mLastSignalLevelDescription;
    public boolean mListening;
    public H mMainHandler;
    public final NetworkController mNetworkController;
    public final TextView mNoSimTextView;
    public OnSingleCarrierChangedListener mOnSingleCarrierChangedListener;
    public final SignalCallback mSignalCallback;
    public final SlotIndexResolver mSlotIndexResolver;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$Builder.class */
    public static class Builder {
        public final ActivityStarter mActivityStarter;
        public final CarrierConfigTracker mCarrierConfigTracker;
        public final CarrierTextManager.Builder mCarrierTextControllerBuilder;
        public final Context mContext;
        public final Handler mHandler;
        public final Looper mLooper;
        public final NetworkController mNetworkController;
        public final SlotIndexResolver mSlotIndexResolver;
        public QSCarrierGroup mView;

        public Builder(ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver) {
            this.mActivityStarter = activityStarter;
            this.mHandler = handler;
            this.mLooper = looper;
            this.mNetworkController = networkController;
            this.mCarrierTextControllerBuilder = builder;
            this.mContext = context;
            this.mCarrierConfigTracker = carrierConfigTracker;
            this.mSlotIndexResolver = slotIndexResolver;
        }

        public QSCarrierGroupController build() {
            return new QSCarrierGroupController(this.mView, this.mActivityStarter, this.mHandler, this.mLooper, this.mNetworkController, this.mCarrierTextControllerBuilder, this.mContext, this.mCarrierConfigTracker, this.mSlotIndexResolver, null);
        }

        public Builder setQSCarrierGroup(QSCarrierGroup qSCarrierGroup) {
            this.mView = qSCarrierGroup;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$Callback.class */
    public static class Callback implements CarrierTextManager.CarrierTextCallback {
        public H mHandler;

        public Callback(H h) {
            this.mHandler = h;
        }

        @Override // com.android.keyguard.CarrierTextManager.CarrierTextCallback
        public void updateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
            this.mHandler.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$H.class */
    public static class H extends Handler {
        public Consumer<CarrierTextManager.CarrierTextCallbackInfo> mUpdateCarrierInfo;
        public Runnable mUpdateState;

        public H(Looper looper, Consumer<CarrierTextManager.CarrierTextCallbackInfo> consumer, Runnable runnable) {
            super(looper);
            this.mUpdateCarrierInfo = consumer;
            this.mUpdateState = runnable;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                this.mUpdateCarrierInfo.accept((CarrierTextManager.CarrierTextCallbackInfo) message.obj);
            } else if (i != 1) {
                super.handleMessage(message);
            } else {
                this.mUpdateState.run();
            }
        }
    }

    @FunctionalInterface
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$OnSingleCarrierChangedListener.class */
    public interface OnSingleCarrierChangedListener {
        void onSingleCarrierChanged(boolean z);
    }

    @FunctionalInterface
    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$SlotIndexResolver.class */
    public interface SlotIndexResolver {
        int getSlotIndex(int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController$SubscriptionManagerSlotIndexResolver.class */
    public static class SubscriptionManagerSlotIndexResolver implements SlotIndexResolver {
        @Override // com.android.systemui.qs.carrier.QSCarrierGroupController.SlotIndexResolver
        public int getSlotIndex(int i) {
            return SubscriptionManager.getSlotIndex(i);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$E69Fd2ATEWm2a5j1VRN3UOrfG2M(QSCarrierGroupController qSCarrierGroupController) {
        qSCarrierGroupController.updateListeners();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$wGn5mJHe17xtDa4qO-pWgn8KTJY */
    public static /* synthetic */ void m3825$r8$lambda$wGn5mJHe17xtDa4qOpWgn8KTJY(QSCarrierGroupController qSCarrierGroupController) {
        qSCarrierGroupController.handleUpdateState();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$zzAMabyS67V05gIa4sMIlM-l-30 */
    public static /* synthetic */ void m3826$r8$lambda$zzAMabyS67V05gIa4sMIlMl30(QSCarrierGroupController qSCarrierGroupController, View view) {
        qSCarrierGroupController.lambda$new$0(view);
    }

    public QSCarrierGroupController(QSCarrierGroup qSCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver) {
        this.mInfos = new CellSignalState[3];
        this.mCarrierDividers = new View[2];
        this.mCarrierGroups = new QSCarrier[3];
        this.mLastSignalLevel = new int[3];
        this.mLastSignalLevelDescription = new String[3];
        this.mSignalCallback = new SignalCallback() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController.1
            {
                QSCarrierGroupController.this = this;
            }

            public void setMobileDataIndicators(MobileDataIndicators mobileDataIndicators) {
                int slotIndex = QSCarrierGroupController.this.getSlotIndex(mobileDataIndicators.subId);
                if (slotIndex >= 3) {
                    Log.w("QSCarrierGroup", "setMobileDataIndicators - slot: " + slotIndex);
                } else if (slotIndex == -1) {
                    Log.e("QSCarrierGroup", "Invalid SIM slot index for subscription: " + mobileDataIndicators.subId);
                } else {
                    CellSignalState[] cellSignalStateArr = QSCarrierGroupController.this.mInfos;
                    IconState iconState = mobileDataIndicators.statusIcon;
                    cellSignalStateArr[slotIndex] = new CellSignalState(iconState.visible, iconState.icon, iconState.contentDescription, mobileDataIndicators.typeContentDescription.toString(), mobileDataIndicators.roaming);
                    QSCarrierGroupController.this.mMainHandler.obtainMessage(1).sendToTarget();
                }
            }

            public void setNoSims(boolean z, boolean z2) {
                if (z) {
                    for (int i = 0; i < 3; i++) {
                        QSCarrierGroupController.this.mInfos[i] = QSCarrierGroupController.this.mInfos[i].changeVisibility(false);
                    }
                }
                QSCarrierGroupController.this.mMainHandler.obtainMessage(1).sendToTarget();
            }
        };
        this.mActivityStarter = activityStarter;
        this.mBgHandler = handler;
        this.mNetworkController = networkController;
        this.mCarrierTextManager = builder.setShowAirplaneMode(false).setShowMissingSim(false).build();
        this.mCarrierConfigTracker = carrierConfigTracker;
        this.mSlotIndexResolver = slotIndexResolver;
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSCarrierGroupController.m3826$r8$lambda$zzAMabyS67V05gIa4sMIlMl30(QSCarrierGroupController.this, view);
            }
        };
        TextView noSimTextView = qSCarrierGroup.getNoSimTextView();
        this.mNoSimTextView = noSimTextView;
        noSimTextView.setOnClickListener(onClickListener);
        H h = new H(looper, new Consumer() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                QSCarrierGroupController.this.handleUpdateCarrierInfo((CarrierTextManager.CarrierTextCallbackInfo) obj);
            }
        }, new Runnable() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                QSCarrierGroupController.m3825$r8$lambda$wGn5mJHe17xtDa4qOpWgn8KTJY(QSCarrierGroupController.this);
            }
        });
        this.mMainHandler = h;
        this.mCallback = new Callback(h);
        this.mCarrierGroups[0] = qSCarrierGroup.getCarrier1View();
        this.mCarrierGroups[1] = qSCarrierGroup.getCarrier2View();
        this.mCarrierGroups[2] = qSCarrierGroup.getCarrier3View();
        this.mCarrierDividers[0] = qSCarrierGroup.getCarrierDivider1();
        this.mCarrierDividers[1] = qSCarrierGroup.getCarrierDivider2();
        for (int i = 0; i < 3; i++) {
            this.mInfos[i] = new CellSignalState(true, R$drawable.ic_qs_no_calling_sms, context.getText(AccessibilityContentDescriptions.NO_CALLING).toString(), "", false);
            this.mLastSignalLevel[i] = TelephonyIcons.MOBILE_CALL_STRENGTH_ICONS[0];
            this.mLastSignalLevelDescription[i] = context.getText(AccessibilityContentDescriptions.PHONE_SIGNAL_STRENGTH[0]).toString();
            this.mCarrierGroups[i].setOnClickListener(onClickListener);
        }
        this.mIsSingleCarrier = computeIsSingleCarrier();
        qSCarrierGroup.setImportantForAccessibility(1);
        qSCarrierGroup.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController.2
            {
                QSCarrierGroupController.this = this;
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
                QSCarrierGroupController.this.setListening(false);
            }
        });
    }

    public /* synthetic */ QSCarrierGroupController(QSCarrierGroup qSCarrierGroup, ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, SlotIndexResolver slotIndexResolver, QSCarrierGroupController-IA r21) {
        this(qSCarrierGroup, activityStarter, handler, looper, networkController, builder, context, carrierConfigTracker, slotIndexResolver);
    }

    public /* synthetic */ void lambda$new$0(View view) {
        if (view.isVisibleToUser()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.WIRELESS_SETTINGS"), 0);
        }
    }

    public final boolean computeIsSingleCarrier() {
        int i;
        boolean z = false;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            i = i3;
            if (i2 >= 3) {
                break;
            }
            int i4 = i;
            if (this.mInfos[i2].visible) {
                i4 = i + 1;
            }
            i2++;
            i3 = i4;
        }
        if (i == 1) {
            z = true;
        }
        return z;
    }

    public int getSlotIndex(int i) {
        return this.mSlotIndexResolver.getSlotIndex(i);
    }

    public final void handleUpdateCarrierInfo(CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo) {
        if (!this.mMainHandler.getLooper().isCurrentThread()) {
            this.mMainHandler.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
            return;
        }
        this.mNoSimTextView.setVisibility(8);
        if (carrierTextCallbackInfo.airplaneMode || !carrierTextCallbackInfo.anySimReady) {
            for (int i = 0; i < 3; i++) {
                CellSignalState[] cellSignalStateArr = this.mInfos;
                cellSignalStateArr[i] = cellSignalStateArr[i].changeVisibility(false);
                this.mCarrierGroups[i].setCarrierText("");
                this.mCarrierGroups[i].setVisibility(8);
            }
            this.mNoSimTextView.setText(carrierTextCallbackInfo.carrierText);
            if (!TextUtils.isEmpty(carrierTextCallbackInfo.carrierText)) {
                this.mNoSimTextView.setVisibility(0);
            }
        } else {
            boolean[] zArr = new boolean[3];
            if (carrierTextCallbackInfo.listOfCarriers.length == carrierTextCallbackInfo.subscriptionIds.length) {
                for (int i2 = 0; i2 < 3 && i2 < carrierTextCallbackInfo.listOfCarriers.length; i2++) {
                    int slotIndex = getSlotIndex(carrierTextCallbackInfo.subscriptionIds[i2]);
                    if (slotIndex >= 3) {
                        Log.w("QSCarrierGroup", "updateInfoCarrier - slot: " + slotIndex);
                    } else if (slotIndex == -1) {
                        Log.e("QSCarrierGroup", "Invalid SIM slot index for subscription: " + carrierTextCallbackInfo.subscriptionIds[i2]);
                    } else {
                        CellSignalState[] cellSignalStateArr2 = this.mInfos;
                        cellSignalStateArr2[slotIndex] = cellSignalStateArr2[slotIndex].changeVisibility(true);
                        zArr[slotIndex] = true;
                        this.mCarrierGroups[slotIndex].setCarrierText(carrierTextCallbackInfo.listOfCarriers[i2].toString().trim());
                        this.mCarrierGroups[slotIndex].setVisibility(0);
                    }
                }
                for (int i3 = 0; i3 < 3; i3++) {
                    if (!zArr[i3]) {
                        CellSignalState[] cellSignalStateArr3 = this.mInfos;
                        cellSignalStateArr3[i3] = cellSignalStateArr3[i3].changeVisibility(false);
                        this.mCarrierGroups[i3].setVisibility(8);
                    }
                }
            } else {
                Log.e("QSCarrierGroup", "Carrier information arrays not of same length");
            }
        }
        handleUpdateState();
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x00d3, code lost:
        if (r0[2].visible == false) goto L43;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleUpdateState() {
        int i;
        if (!this.mMainHandler.getLooper().isCurrentThread()) {
            this.mMainHandler.obtainMessage(1).sendToTarget();
            return;
        }
        boolean computeIsSingleCarrier = computeIsSingleCarrier();
        if (computeIsSingleCarrier) {
            for (int i2 = 0; i2 < 3; i2++) {
                CellSignalState[] cellSignalStateArr = this.mInfos;
                CellSignalState cellSignalState = cellSignalStateArr[i2];
                if (cellSignalState.visible && cellSignalState.mobileSignalIconId == R$drawable.ic_qs_sim_card) {
                    cellSignalStateArr[i2] = new CellSignalState(true, R$drawable.ic_blank, "", "", false);
                }
            }
        }
        for (int i3 = 0; i3 < 3; i3++) {
            this.mCarrierGroups[i3].updateState(this.mInfos[i3], computeIsSingleCarrier);
        }
        View view = this.mCarrierDividers[0];
        CellSignalState[] cellSignalStateArr2 = this.mInfos;
        view.setVisibility((cellSignalStateArr2[0].visible && cellSignalStateArr2[1].visible) ? 0 : 8);
        View view2 = this.mCarrierDividers[1];
        CellSignalState[] cellSignalStateArr3 = this.mInfos;
        if (cellSignalStateArr3[1].visible) {
            i = 0;
        }
        i = (cellSignalStateArr3[0].visible && cellSignalStateArr3[2].visible) ? 0 : 8;
        view2.setVisibility(i);
        if (this.mIsSingleCarrier != computeIsSingleCarrier) {
            this.mIsSingleCarrier = computeIsSingleCarrier;
            OnSingleCarrierChangedListener onSingleCarrierChangedListener = this.mOnSingleCarrierChangedListener;
            if (onSingleCarrierChangedListener != null) {
                onSingleCarrierChangedListener.onSingleCarrierChanged(computeIsSingleCarrier);
            }
        }
    }

    public boolean isSingleCarrier() {
        return this.mIsSingleCarrier;
    }

    public void setListening(boolean z) {
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.mBgHandler.post(new Runnable() { // from class: com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QSCarrierGroupController.$r8$lambda$E69Fd2ATEWm2a5j1VRN3UOrfG2M(QSCarrierGroupController.this);
            }
        });
    }

    public void setOnSingleCarrierChangedListener(OnSingleCarrierChangedListener onSingleCarrierChangedListener) {
        this.mOnSingleCarrierChangedListener = onSingleCarrierChangedListener;
    }

    public final void updateListeners() {
        if (!this.mListening) {
            this.mNetworkController.removeCallback(this.mSignalCallback);
            this.mCarrierTextManager.setListening(null);
            return;
        }
        if (this.mNetworkController.hasVoiceCallingFeature()) {
            this.mNetworkController.addCallback(this.mSignalCallback);
        }
        this.mCarrierTextManager.setListening(this.mCallback);
    }
}