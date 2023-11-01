package com.android.systemui.clipboardoverlay;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardToast_Factory.class */
public final class ClipboardToast_Factory implements Factory<ClipboardToast> {
    public final Provider<Context> contextProvider;

    public ClipboardToast_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static ClipboardToast_Factory create(Provider<Context> provider) {
        return new ClipboardToast_Factory(provider);
    }

    public static ClipboardToast newInstance(Context context) {
        return new ClipboardToast(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardToast m1772get() {
        return newInstance((Context) this.contextProvider.get());
    }
}