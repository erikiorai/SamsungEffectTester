package com.android.systemui.biometrics;

import android.app.ActivityTaskManager;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.WindowManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/SideFpsController_Factory.class */
public final class SideFpsController_Factory implements Factory<SideFpsController> {
    public final Provider<ActivityTaskManager> activityTaskManagerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public SideFpsController_Factory(Provider<Context> provider, Provider<LayoutInflater> provider2, Provider<FingerprintManager> provider3, Provider<WindowManager> provider4, Provider<ActivityTaskManager> provider5, Provider<OverviewProxyService> provider6, Provider<DisplayManager> provider7, Provider<DelayableExecutor> provider8, Provider<Handler> provider9, Provider<DumpManager> provider10) {
        this.contextProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.fingerprintManagerProvider = provider3;
        this.windowManagerProvider = provider4;
        this.activityTaskManagerProvider = provider5;
        this.overviewProxyServiceProvider = provider6;
        this.displayManagerProvider = provider7;
        this.mainExecutorProvider = provider8;
        this.handlerProvider = provider9;
        this.dumpManagerProvider = provider10;
    }

    public static SideFpsController_Factory create(Provider<Context> provider, Provider<LayoutInflater> provider2, Provider<FingerprintManager> provider3, Provider<WindowManager> provider4, Provider<ActivityTaskManager> provider5, Provider<OverviewProxyService> provider6, Provider<DisplayManager> provider7, Provider<DelayableExecutor> provider8, Provider<Handler> provider9, Provider<DumpManager> provider10) {
        return new SideFpsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static SideFpsController newInstance(Context context, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, ActivityTaskManager activityTaskManager, OverviewProxyService overviewProxyService, DisplayManager displayManager, DelayableExecutor delayableExecutor, Handler handler, DumpManager dumpManager) {
        return new SideFpsController(context, layoutInflater, fingerprintManager, windowManager, activityTaskManager, overviewProxyService, displayManager, delayableExecutor, handler, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SideFpsController m1549get() {
        return newInstance((Context) this.contextProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (ActivityTaskManager) this.activityTaskManagerProvider.get(), (OverviewProxyService) this.overviewProxyServiceProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (Handler) this.handlerProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}