package com.android.systemui;

import android.os.Handler;
import com.android.systemui.appops.AppOpsController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServiceController_Factory.class */
public final class ForegroundServiceController_Factory implements Factory<ForegroundServiceController> {
    public final Provider<AppOpsController> appOpsControllerProvider;
    public final Provider<Handler> mainHandlerProvider;

    public ForegroundServiceController_Factory(Provider<AppOpsController> provider, Provider<Handler> provider2) {
        this.appOpsControllerProvider = provider;
        this.mainHandlerProvider = provider2;
    }

    public static ForegroundServiceController_Factory create(Provider<AppOpsController> provider, Provider<Handler> provider2) {
        return new ForegroundServiceController_Factory(provider, provider2);
    }

    public static ForegroundServiceController newInstance(AppOpsController appOpsController, Handler handler) {
        return new ForegroundServiceController(appOpsController, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ForegroundServiceController m1258get() {
        return newInstance((AppOpsController) this.appOpsControllerProvider.get(), (Handler) this.mainHandlerProvider.get());
    }
}