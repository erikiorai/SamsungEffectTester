package com.android.systemui.globalactions;

import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsComponent_Factory.class */
public final class GlobalActionsComponent_Factory implements Factory<GlobalActionsComponent> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<ExtensionController> extensionControllerProvider;
    public final Provider<GlobalActions> globalActionsProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;

    public GlobalActionsComponent_Factory(Provider<CommandQueue> provider, Provider<ExtensionController> provider2, Provider<GlobalActions> provider3, Provider<StatusBarKeyguardViewManager> provider4) {
        this.commandQueueProvider = provider;
        this.extensionControllerProvider = provider2;
        this.globalActionsProvider = provider3;
        this.statusBarKeyguardViewManagerProvider = provider4;
    }

    public static GlobalActionsComponent_Factory create(Provider<CommandQueue> provider, Provider<ExtensionController> provider2, Provider<GlobalActions> provider3, Provider<StatusBarKeyguardViewManager> provider4) {
        return new GlobalActionsComponent_Factory(provider, provider2, provider3, provider4);
    }

    public static GlobalActionsComponent newInstance(CommandQueue commandQueue, ExtensionController extensionController, Provider<GlobalActions> provider, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        return new GlobalActionsComponent(commandQueue, extensionController, provider, statusBarKeyguardViewManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public GlobalActionsComponent m2729get() {
        return newInstance((CommandQueue) this.commandQueueProvider.get(), (ExtensionController) this.extensionControllerProvider.get(), this.globalActionsProvider, (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get());
    }
}