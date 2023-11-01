package com.android.systemui.appops;

import android.content.Context;
import android.media.AudioManager;
import android.os.Looper;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/appops/AppOpsControllerImpl_Factory.class */
public final class AppOpsControllerImpl_Factory implements Factory<AppOpsControllerImpl> {
    public final Provider<AudioManager> audioManagerProvider;
    public final Provider<Looper> bgLooperProvider;
    public final Provider<SystemClock> clockProvider;
    public final Provider<Context> contextProvider;
    public final Provider<BroadcastDispatcher> dispatcherProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<IndividualSensorPrivacyController> sensorPrivacyControllerProvider;

    public AppOpsControllerImpl_Factory(Provider<Context> provider, Provider<Looper> provider2, Provider<DumpManager> provider3, Provider<AudioManager> provider4, Provider<IndividualSensorPrivacyController> provider5, Provider<BroadcastDispatcher> provider6, Provider<SystemClock> provider7) {
        this.contextProvider = provider;
        this.bgLooperProvider = provider2;
        this.dumpManagerProvider = provider3;
        this.audioManagerProvider = provider4;
        this.sensorPrivacyControllerProvider = provider5;
        this.dispatcherProvider = provider6;
        this.clockProvider = provider7;
    }

    public static AppOpsControllerImpl_Factory create(Provider<Context> provider, Provider<Looper> provider2, Provider<DumpManager> provider3, Provider<AudioManager> provider4, Provider<IndividualSensorPrivacyController> provider5, Provider<BroadcastDispatcher> provider6, Provider<SystemClock> provider7) {
        return new AppOpsControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static AppOpsControllerImpl newInstance(Context context, Looper looper, DumpManager dumpManager, AudioManager audioManager, IndividualSensorPrivacyController individualSensorPrivacyController, BroadcastDispatcher broadcastDispatcher, SystemClock systemClock) {
        return new AppOpsControllerImpl(context, looper, dumpManager, audioManager, individualSensorPrivacyController, broadcastDispatcher, systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AppOpsControllerImpl m1465get() {
        return newInstance((Context) this.contextProvider.get(), (Looper) this.bgLooperProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (AudioManager) this.audioManagerProvider.get(), (IndividualSensorPrivacyController) this.sensorPrivacyControllerProvider.get(), (BroadcastDispatcher) this.dispatcherProvider.get(), (SystemClock) this.clockProvider.get());
    }
}