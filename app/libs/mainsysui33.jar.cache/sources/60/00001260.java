package com.android.systemui.bluetooth;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/bluetooth/BroadcastDialogController_Factory.class */
public final class BroadcastDialogController_Factory implements Factory<BroadcastDialogController> {
    public final Provider<Context> contextProvider;
    public final Provider<DialogLaunchAnimator> dialogLaunchAnimatorProvider;
    public final Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public BroadcastDialogController_Factory(Provider<Context> provider, Provider<UiEventLogger> provider2, Provider<DialogLaunchAnimator> provider3, Provider<MediaOutputDialogFactory> provider4) {
        this.contextProvider = provider;
        this.uiEventLoggerProvider = provider2;
        this.dialogLaunchAnimatorProvider = provider3;
        this.mediaOutputDialogFactoryProvider = provider4;
    }

    public static BroadcastDialogController_Factory create(Provider<Context> provider, Provider<UiEventLogger> provider2, Provider<DialogLaunchAnimator> provider3, Provider<MediaOutputDialogFactory> provider4) {
        return new BroadcastDialogController_Factory(provider, provider2, provider3, provider4);
    }

    public static BroadcastDialogController newInstance(Context context, UiEventLogger uiEventLogger, DialogLaunchAnimator dialogLaunchAnimator, MediaOutputDialogFactory mediaOutputDialogFactory) {
        return new BroadcastDialogController(context, uiEventLogger, dialogLaunchAnimator, mediaOutputDialogFactory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BroadcastDialogController m1631get() {
        return newInstance((Context) this.contextProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (DialogLaunchAnimator) this.dialogLaunchAnimatorProvider.get(), (MediaOutputDialogFactory) this.mediaOutputDialogFactoryProvider.get());
    }
}