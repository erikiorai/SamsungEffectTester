package com.android.systemui.clipboardoverlay.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/dagger/ClipboardOverlayModule_ProvideWindowContextFactory.class */
public final class ClipboardOverlayModule_ProvideWindowContextFactory implements Factory<Context> {
    public final Provider<Context> contextProvider;
    public final Provider<DisplayManager> displayManagerProvider;

    public ClipboardOverlayModule_ProvideWindowContextFactory(Provider<DisplayManager> provider, Provider<Context> provider2) {
        this.displayManagerProvider = provider;
        this.contextProvider = provider2;
    }

    public static ClipboardOverlayModule_ProvideWindowContextFactory create(Provider<DisplayManager> provider, Provider<Context> provider2) {
        return new ClipboardOverlayModule_ProvideWindowContextFactory(provider, provider2);
    }

    public static Context provideWindowContext(DisplayManager displayManager, Context context) {
        return (Context) Preconditions.checkNotNullFromProvides(ClipboardOverlayModule.provideWindowContext(displayManager, context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Context m1774get() {
        return provideWindowContext((DisplayManager) this.displayManagerProvider.get(), (Context) this.contextProvider.get());
    }
}