package com.android.systemui.keyguard.ui.preview;

import android.os.Handler;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardRemotePreviewManager_Factory.class */
public final class KeyguardRemotePreviewManager_Factory implements Factory<KeyguardRemotePreviewManager> {
    public final Provider<Handler> backgroundHandlerProvider;
    public final Provider<CoroutineDispatcher> mainDispatcherProvider;
    public final Provider<KeyguardPreviewRendererFactory> previewRendererFactoryProvider;

    public KeyguardRemotePreviewManager_Factory(Provider<KeyguardPreviewRendererFactory> provider, Provider<CoroutineDispatcher> provider2, Provider<Handler> provider3) {
        this.previewRendererFactoryProvider = provider;
        this.mainDispatcherProvider = provider2;
        this.backgroundHandlerProvider = provider3;
    }

    public static KeyguardRemotePreviewManager_Factory create(Provider<KeyguardPreviewRendererFactory> provider, Provider<CoroutineDispatcher> provider2, Provider<Handler> provider3) {
        return new KeyguardRemotePreviewManager_Factory(provider, provider2, provider3);
    }

    public static KeyguardRemotePreviewManager newInstance(KeyguardPreviewRendererFactory keyguardPreviewRendererFactory, CoroutineDispatcher coroutineDispatcher, Handler handler) {
        return new KeyguardRemotePreviewManager(keyguardPreviewRendererFactory, coroutineDispatcher, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardRemotePreviewManager m3083get() {
        return newInstance((KeyguardPreviewRendererFactory) this.previewRendererFactoryProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (Handler) this.backgroundHandlerProvider.get());
    }
}