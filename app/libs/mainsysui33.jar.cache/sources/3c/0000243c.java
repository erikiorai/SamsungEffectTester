package com.android.systemui.screenshot;

import android.os.Handler;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageTileSet_Factory.class */
public final class ImageTileSet_Factory implements Factory<ImageTileSet> {
    public final Provider<Handler> handlerProvider;

    public ImageTileSet_Factory(Provider<Handler> provider) {
        this.handlerProvider = provider;
    }

    public static ImageTileSet_Factory create(Provider<Handler> provider) {
        return new ImageTileSet_Factory(provider);
    }

    public static ImageTileSet newInstance(Handler handler) {
        return new ImageTileSet(handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ImageTileSet m4234get() {
        return newInstance((Handler) this.handlerProvider.get());
    }
}