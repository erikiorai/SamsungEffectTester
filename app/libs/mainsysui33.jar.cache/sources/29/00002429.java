package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.hardware.display.DisplayManager;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageCaptureImpl_Factory.class */
public final class ImageCaptureImpl_Factory implements Factory<ImageCaptureImpl> {
    public final Provider<IActivityTaskManager> atmServiceProvider;
    public final Provider<CoroutineDispatcher> bgContextProvider;
    public final Provider<DisplayManager> displayManagerProvider;

    public ImageCaptureImpl_Factory(Provider<DisplayManager> provider, Provider<IActivityTaskManager> provider2, Provider<CoroutineDispatcher> provider3) {
        this.displayManagerProvider = provider;
        this.atmServiceProvider = provider2;
        this.bgContextProvider = provider3;
    }

    public static ImageCaptureImpl_Factory create(Provider<DisplayManager> provider, Provider<IActivityTaskManager> provider2, Provider<CoroutineDispatcher> provider3) {
        return new ImageCaptureImpl_Factory(provider, provider2, provider3);
    }

    public static ImageCaptureImpl newInstance(DisplayManager displayManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        return new ImageCaptureImpl(displayManager, iActivityTaskManager, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ImageCaptureImpl m4222get() {
        return newInstance((DisplayManager) this.displayManagerProvider.get(), (IActivityTaskManager) this.atmServiceProvider.get(), (CoroutineDispatcher) this.bgContextProvider.get());
    }
}