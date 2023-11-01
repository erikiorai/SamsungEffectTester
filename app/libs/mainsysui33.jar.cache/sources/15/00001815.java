package com.android.systemui.globalactions;

import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ExtensionController;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsComponent.class */
public class GlobalActionsComponent implements CoreStartable, CommandQueue.Callbacks, GlobalActions.GlobalActionsManager {
    public IStatusBarService mBarService;
    public final CommandQueue mCommandQueue;
    public ExtensionController.Extension<GlobalActions> mExtension;
    public final ExtensionController mExtensionController;
    public final Provider<GlobalActions> mGlobalActionsProvider;
    public GlobalActions mPlugin;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsComponent$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$1xiWj4llACQFeadr-Z8c57CyYi4 */
    public static /* synthetic */ void m2728$r8$lambda$1xiWj4llACQFeadrZ8c57CyYi4(GlobalActionsComponent globalActionsComponent, GlobalActions globalActions) {
        globalActionsComponent.onExtensionCallback(globalActions);
    }

    public GlobalActionsComponent(CommandQueue commandQueue, ExtensionController extensionController, Provider<GlobalActions> provider, StatusBarKeyguardViewManager statusBarKeyguardViewManager) {
        this.mCommandQueue = commandQueue;
        this.mExtensionController = extensionController;
        this.mGlobalActionsProvider = provider;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
    }

    public void handleShowGlobalActionsMenu() {
        this.mStatusBarKeyguardViewManager.setGlobalActionsVisible(true);
        ((GlobalActions) this.mExtension.get()).showGlobalActions(this);
    }

    public void handleShowShutdownUi(boolean z, String str, boolean z2) {
        ((GlobalActions) this.mExtension.get()).showShutdownUi(z, str, z2);
    }

    public final void onExtensionCallback(GlobalActions globalActions) {
        GlobalActions globalActions2 = this.mPlugin;
        if (globalActions2 != null) {
            globalActions2.destroy();
        }
        this.mPlugin = globalActions;
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public void onGlobalActionsHidden() {
        try {
            this.mStatusBarKeyguardViewManager.setGlobalActionsVisible(false);
            this.mBarService.onGlobalActionsHidden();
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public void onGlobalActionsShown() {
        try {
            this.mBarService.onGlobalActionsShown();
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public void reboot(boolean z, String str) {
        try {
            this.mBarService.reboot(z, str);
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.plugins.GlobalActions.GlobalActionsManager
    public void shutdown() {
        try {
            this.mBarService.shutdown();
        } catch (RemoteException e) {
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
        ExtensionController.ExtensionBuilder withPlugin = this.mExtensionController.newExtension(GlobalActions.class).withPlugin(GlobalActions.class);
        final Provider<GlobalActions> provider = this.mGlobalActionsProvider;
        Objects.requireNonNull(provider);
        ExtensionController.Extension<GlobalActions> build = withPlugin.withDefault(new Supplier() { // from class: com.android.systemui.globalactions.GlobalActionsComponent$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return (GlobalActions) provider.get();
            }
        }).withCallback(new Consumer() { // from class: com.android.systemui.globalactions.GlobalActionsComponent$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                GlobalActionsComponent.m2728$r8$lambda$1xiWj4llACQFeadrZ8c57CyYi4(GlobalActionsComponent.this, (GlobalActions) obj);
            }
        }).build();
        this.mExtension = build;
        this.mPlugin = (GlobalActions) build.get();
        this.mCommandQueue.addCallback(this);
    }
}