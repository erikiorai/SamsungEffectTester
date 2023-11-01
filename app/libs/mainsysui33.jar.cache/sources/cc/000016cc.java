package com.android.systemui.dreams;

import android.content.ComponentName;
import android.content.Context;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayService_Factory.class */
public final class DreamOverlayService_Factory implements Factory<DreamOverlayService> {
    public final Provider<Context> contextProvider;
    public final Provider<DreamOverlayCallbackController> dreamOverlayCallbackControllerProvider;
    public final Provider<DreamOverlayComponent.Factory> dreamOverlayComponentFactoryProvider;
    public final Provider<DelayableExecutor> executorProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<ComponentName> lowLightDreamComponentProvider;
    public final Provider<DreamOverlayStateController> stateControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public DreamOverlayService_Factory(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<WindowManager> provider3, Provider<DreamOverlayComponent.Factory> provider4, Provider<DreamOverlayStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<UiEventLogger> provider7, Provider<ComponentName> provider8, Provider<DreamOverlayCallbackController> provider9) {
        this.contextProvider = provider;
        this.executorProvider = provider2;
        this.windowManagerProvider = provider3;
        this.dreamOverlayComponentFactoryProvider = provider4;
        this.stateControllerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.uiEventLoggerProvider = provider7;
        this.lowLightDreamComponentProvider = provider8;
        this.dreamOverlayCallbackControllerProvider = provider9;
    }

    public static DreamOverlayService_Factory create(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<WindowManager> provider3, Provider<DreamOverlayComponent.Factory> provider4, Provider<DreamOverlayStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<UiEventLogger> provider7, Provider<ComponentName> provider8, Provider<DreamOverlayCallbackController> provider9) {
        return new DreamOverlayService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static DreamOverlayService newInstance(Context context, DelayableExecutor delayableExecutor, WindowManager windowManager, DreamOverlayComponent.Factory factory, DreamOverlayStateController dreamOverlayStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, UiEventLogger uiEventLogger, ComponentName componentName, DreamOverlayCallbackController dreamOverlayCallbackController) {
        return new DreamOverlayService(context, delayableExecutor, windowManager, factory, dreamOverlayStateController, keyguardUpdateMonitor, uiEventLogger, componentName, dreamOverlayCallbackController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayService m2559get() {
        return newInstance((Context) this.contextProvider.get(), (DelayableExecutor) this.executorProvider.get(), (WindowManager) this.windowManagerProvider.get(), (DreamOverlayComponent.Factory) this.dreamOverlayComponentFactoryProvider.get(), (DreamOverlayStateController) this.stateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (ComponentName) this.lowLightDreamComponentProvider.get(), (DreamOverlayCallbackController) this.dreamOverlayCallbackControllerProvider.get());
    }
}