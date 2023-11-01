package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeUi_Factory.class */
public final class DozeUi_Factory implements Factory<DozeUi> {
    public final Provider<AlarmManager> alarmManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<DozeHost> hostProvider;
    public final Provider<DozeParameters> paramsProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WakeLock> wakeLockProvider;

    public DozeUi_Factory(Provider<Context> provider, Provider<AlarmManager> provider2, Provider<WakeLock> provider3, Provider<DozeHost> provider4, Provider<Handler> provider5, Provider<DozeParameters> provider6, Provider<StatusBarStateController> provider7, Provider<DozeLog> provider8) {
        this.contextProvider = provider;
        this.alarmManagerProvider = provider2;
        this.wakeLockProvider = provider3;
        this.hostProvider = provider4;
        this.handlerProvider = provider5;
        this.paramsProvider = provider6;
        this.statusBarStateControllerProvider = provider7;
        this.dozeLogProvider = provider8;
    }

    public static DozeUi_Factory create(Provider<Context> provider, Provider<AlarmManager> provider2, Provider<WakeLock> provider3, Provider<DozeHost> provider4, Provider<Handler> provider5, Provider<DozeParameters> provider6, Provider<StatusBarStateController> provider7, Provider<DozeLog> provider8) {
        return new DozeUi_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static DozeUi newInstance(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, DozeLog dozeLog) {
        return new DozeUi(context, alarmManager, wakeLock, dozeHost, handler, dozeParameters, statusBarStateController, dozeLog);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeUi m2529get() {
        return newInstance((Context) this.contextProvider.get(), (AlarmManager) this.alarmManagerProvider.get(), (WakeLock) this.wakeLockProvider.get(), (DozeHost) this.hostProvider.get(), (Handler) this.handlerProvider.get(), (DozeParameters) this.paramsProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (DozeLog) this.dozeLogProvider.get());
    }
}