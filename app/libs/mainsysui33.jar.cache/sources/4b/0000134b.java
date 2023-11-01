package com.android.systemui.clipboardoverlay;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayWindow_Factory.class */
public final class ClipboardOverlayWindow_Factory implements Factory<ClipboardOverlayWindow> {
    public final Provider<Context> contextProvider;

    public ClipboardOverlayWindow_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static ClipboardOverlayWindow_Factory create(Provider<Context> provider) {
        return new ClipboardOverlayWindow_Factory(provider);
    }

    public static ClipboardOverlayWindow newInstance(Context context) {
        return new ClipboardOverlayWindow(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardOverlayWindow m1771get() {
        return newInstance((Context) this.contextProvider.get());
    }
}