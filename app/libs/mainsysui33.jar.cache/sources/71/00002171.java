package com.android.systemui.qs.carrier;

import com.android.systemui.qs.carrier.QSCarrierGroupController;
import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory.class */
public final class QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory implements Factory<QSCarrierGroupController.SubscriptionManagerSlotIndexResolver> {

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory INSTANCE = new QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory();
    }

    public static QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static QSCarrierGroupController.SubscriptionManagerSlotIndexResolver newInstance() {
        return new QSCarrierGroupController.SubscriptionManagerSlotIndexResolver();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSCarrierGroupController.SubscriptionManagerSlotIndexResolver m3830get() {
        return newInstance();
    }
}