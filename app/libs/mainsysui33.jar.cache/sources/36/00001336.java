package com.android.systemui.clipboardoverlay;

import android.view.textclassifier.TextClassificationManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/clipboardoverlay/ClipboardOverlayUtils_Factory.class */
public final class ClipboardOverlayUtils_Factory implements Factory<ClipboardOverlayUtils> {
    public final Provider<TextClassificationManager> textClassificationManagerProvider;

    public ClipboardOverlayUtils_Factory(Provider<TextClassificationManager> provider) {
        this.textClassificationManagerProvider = provider;
    }

    public static ClipboardOverlayUtils_Factory create(Provider<TextClassificationManager> provider) {
        return new ClipboardOverlayUtils_Factory(provider);
    }

    public static ClipboardOverlayUtils newInstance(TextClassificationManager textClassificationManager) {
        return new ClipboardOverlayUtils(textClassificationManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClipboardOverlayUtils m1765get() {
        return newInstance((TextClassificationManager) this.textClassificationManagerProvider.get());
    }
}