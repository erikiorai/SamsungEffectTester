package com.android.systemui.clipboardoverlay.dagger;

import android.content.Context;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/dagger/ClipboardOverlayModule_ProvideClipboardOverlayViewFactory.class */
public final class ClipboardOverlayModule_ProvideClipboardOverlayViewFactory implements Factory<ClipboardOverlayView> {
    public final Provider<Context> contextProvider;

    public ClipboardOverlayModule_ProvideClipboardOverlayViewFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static ClipboardOverlayModule_ProvideClipboardOverlayViewFactory create(Provider<Context> provider) {
        return new ClipboardOverlayModule_ProvideClipboardOverlayViewFactory(provider);
    }

    public static ClipboardOverlayView provideClipboardOverlayView(Context context) {
        return (ClipboardOverlayView) Preconditions.checkNotNullFromProvides(ClipboardOverlayModule.provideClipboardOverlayView(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardOverlayView m1773get() {
        return provideClipboardOverlayView((Context) this.contextProvider.get());
    }
}