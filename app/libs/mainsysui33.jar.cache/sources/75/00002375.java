package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import android.os.Handler;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/InternetDialogFactory_Factory.class */
public final class InternetDialogFactory_Factory implements Factory<InternetDialogFactory> {
    public final Provider<Context> contextProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<InternetDialogController> internetDialogControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public InternetDialogFactory_Factory(Provider<Handler> provider, Provider<Executor> provider2, Provider<InternetDialogController> provider3, Provider<Context> provider4, Provider<UiEventLogger> provider5, Provider<DialogLaunchAnimator> provider6, Provider<KeyguardStateController> provider7) {
        this.handlerProvider = provider;
        this.executorProvider = provider2;
        this.internetDialogControllerProvider = provider3;
        this.contextProvider = provider4;
        this.uiEventLoggerProvider = provider5;
        this.dialogLaunchAnimatorProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
    }

    public static InternetDialogFactory_Factory create(Provider<Handler> provider, Provider<Executor> provider2, Provider<InternetDialogController> provider3, Provider<Context> provider4, Provider<UiEventLogger> provider5, Provider<DialogLaunchAnimator> provider6, Provider<KeyguardStateController> provider7) {
        return new InternetDialogFactory_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static InternetDialogFactory newInstance(Handler handler, Executor executor, InternetDialogController internetDialogController, Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, KeyguardStateController keyguardStateController) {
        return new InternetDialogFactory(handler, executor, internetDialogController, context, uiEventLogger, dialogLaunchAnimator, keyguardStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InternetDialogFactory m4091get() {
        return newInstance((Handler) this.handlerProvider.get(), (Executor) this.executorProvider.get(), (InternetDialogController) this.internetDialogControllerProvider.get(), (Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}