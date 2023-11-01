package com.android.systemui.keyguard.ui.preview;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.view.WindowManager;
import com.android.keyguard.ClockEventController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.shared.clocks.ClockRegistry;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/preview/KeyguardPreviewRenderer_Factory.class */
public final class KeyguardPreviewRenderer_Factory {
    public final Provider<KeyguardBottomAreaViewModel> bottomAreaViewModelProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<ClockEventController> clockControllerProvider;
    public final Provider<ClockRegistry> clockRegistryProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<CoroutineDispatcher> mainDispatcherProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public KeyguardPreviewRenderer_Factory(Provider<Context> provider, Provider<CoroutineDispatcher> provider2, Provider<KeyguardBottomAreaViewModel> provider3, Provider<DisplayManager> provider4, Provider<WindowManager> provider5, Provider<ClockEventController> provider6, Provider<ClockRegistry> provider7, Provider<BroadcastDispatcher> provider8) {
        this.contextProvider = provider;
        this.mainDispatcherProvider = provider2;
        this.bottomAreaViewModelProvider = provider3;
        this.displayManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.clockControllerProvider = provider6;
        this.clockRegistryProvider = provider7;
        this.broadcastDispatcherProvider = provider8;
    }

    public static KeyguardPreviewRenderer_Factory create(Provider<Context> provider, Provider<CoroutineDispatcher> provider2, Provider<KeyguardBottomAreaViewModel> provider3, Provider<DisplayManager> provider4, Provider<WindowManager> provider5, Provider<ClockEventController> provider6, Provider<ClockRegistry> provider7, Provider<BroadcastDispatcher> provider8) {
        return new KeyguardPreviewRenderer_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static KeyguardPreviewRenderer newInstance(Context context, CoroutineDispatcher coroutineDispatcher, KeyguardBottomAreaViewModel keyguardBottomAreaViewModel, DisplayManager displayManager, WindowManager windowManager, ClockEventController clockEventController, ClockRegistry clockRegistry, BroadcastDispatcher broadcastDispatcher, Bundle bundle) {
        return new KeyguardPreviewRenderer(context, coroutineDispatcher, keyguardBottomAreaViewModel, displayManager, windowManager, clockEventController, clockRegistry, broadcastDispatcher, bundle);
    }

    public KeyguardPreviewRenderer get(Bundle bundle) {
        return newInstance((Context) this.contextProvider.get(), (CoroutineDispatcher) this.mainDispatcherProvider.get(), (KeyguardBottomAreaViewModel) this.bottomAreaViewModelProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (ClockEventController) this.clockControllerProvider.get(), (ClockRegistry) this.clockRegistryProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), bundle);
    }
}