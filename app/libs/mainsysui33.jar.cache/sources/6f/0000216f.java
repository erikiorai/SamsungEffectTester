package com.android.systemui.qs.carrier;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import com.android.keyguard.CarrierTextManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.util.CarrierConfigTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/carrier/QSCarrierGroupController_Builder_Factory.class */
public final class QSCarrierGroupController_Builder_Factory implements Factory<QSCarrierGroupController.Builder> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<CarrierConfigTracker> carrierConfigTrackerProvider;
    public final Provider<CarrierTextManager.Builder> carrierTextControllerBuilderProvider;
    public final Provider<Context> contextProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<Looper> looperProvider;
    public final Provider<NetworkController> networkControllerProvider;
    public final Provider<QSCarrierGroupController.SlotIndexResolver> slotIndexResolverProvider;

    public QSCarrierGroupController_Builder_Factory(Provider<ActivityStarter> provider, Provider<Handler> provider2, Provider<Looper> provider3, Provider<NetworkController> provider4, Provider<CarrierTextManager.Builder> provider5, Provider<Context> provider6, Provider<CarrierConfigTracker> provider7, Provider<QSCarrierGroupController.SlotIndexResolver> provider8) {
        this.activityStarterProvider = provider;
        this.handlerProvider = provider2;
        this.looperProvider = provider3;
        this.networkControllerProvider = provider4;
        this.carrierTextControllerBuilderProvider = provider5;
        this.contextProvider = provider6;
        this.carrierConfigTrackerProvider = provider7;
        this.slotIndexResolverProvider = provider8;
    }

    public static QSCarrierGroupController_Builder_Factory create(Provider<ActivityStarter> provider, Provider<Handler> provider2, Provider<Looper> provider3, Provider<NetworkController> provider4, Provider<CarrierTextManager.Builder> provider5, Provider<Context> provider6, Provider<CarrierConfigTracker> provider7, Provider<QSCarrierGroupController.SlotIndexResolver> provider8) {
        return new QSCarrierGroupController_Builder_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static QSCarrierGroupController.Builder newInstance(ActivityStarter activityStarter, Handler handler, Looper looper, NetworkController networkController, CarrierTextManager.Builder builder, Context context, CarrierConfigTracker carrierConfigTracker, QSCarrierGroupController.SlotIndexResolver slotIndexResolver) {
        return new QSCarrierGroupController.Builder(activityStarter, handler, looper, networkController, builder, context, carrierConfigTracker, slotIndexResolver);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSCarrierGroupController.Builder m3829get() {
        return newInstance((ActivityStarter) this.activityStarterProvider.get(), (Handler) this.handlerProvider.get(), (Looper) this.looperProvider.get(), (NetworkController) this.networkControllerProvider.get(), (CarrierTextManager.Builder) this.carrierTextControllerBuilderProvider.get(), (Context) this.contextProvider.get(), (CarrierConfigTracker) this.carrierConfigTrackerProvider.get(), (QSCarrierGroupController.SlotIndexResolver) this.slotIndexResolverProvider.get());
    }
}