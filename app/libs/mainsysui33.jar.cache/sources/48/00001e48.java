package com.android.systemui.navigationbar;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.Trace;
import android.provider.Settings;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.IWindowManager;
import android.view.View;
import android.view.WindowManagerGlobal;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.statusbar.RegisterStatusBarResult;
import com.android.internal.util.custom.NavbarUtils;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarComponent;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import java.io.PrintWriter;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarController.class */
public class NavigationBarController implements CommandQueue.Callbacks, ConfigurationController.ConfigurationListener, NavigationModeController.ModeChangedListener, Dumpable {
    public static final String TAG = "NavigationBarController";
    public final InterestingConfigChanges mConfigChanges;
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public FeatureFlags mFeatureFlags;
    public final Handler mHandler;
    @VisibleForTesting
    public boolean mIsTablet;
    public int mNavMode;
    public final NavigationBarComponent.Factory mNavigationBarComponentFactory;
    @VisibleForTesting
    public SparseArray<NavigationBar> mNavigationBars = new SparseArray<>();
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final TaskbarDelegate mTaskbarDelegate;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBarController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$eCLFWqxEyx7Ep_fem0hWsrbcP5Q(NavigationBarController navigationBarController, int i) {
        navigationBarController.lambda$onNavigationModeChanged$0(i);
    }

    public NavigationBarController(Context context, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, SysUiState sysUiState, CommandQueue commandQueue, Handler handler, ConfigurationController configurationController, NavBarHelper navBarHelper, TaskbarDelegate taskbarDelegate, NavigationBarComponent.Factory factory, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, AutoHideController autoHideController, LightBarController lightBarController, Optional<Pip> optional, Optional<BackAnimation> optional2, FeatureFlags featureFlags) {
        InterestingConfigChanges interestingConfigChanges = new InterestingConfigChanges(1073742592);
        this.mConfigChanges = interestingConfigChanges;
        this.mContext = context;
        this.mHandler = handler;
        this.mNavigationBarComponentFactory = factory;
        this.mFeatureFlags = featureFlags;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        commandQueue.addCallback(this);
        configurationController.addCallback(this);
        interestingConfigChanges.applyNewConfig(context.getResources());
        this.mNavMode = navigationModeController.addListener(this);
        this.mTaskbarDelegate = taskbarDelegate;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        taskbarDelegate.setDependencies(commandQueue, overviewProxyService, navBarHelper, navigationModeController, sysUiState, dumpManager, autoHideController, lightBarController, optional, optional2.orElse(null));
        this.mIsTablet = Utilities.isTablet(context);
        dumpManager.registerDumpable(this);
    }

    public /* synthetic */ void lambda$onNavigationModeChanged$0(int i) {
        if (i != this.mNavMode) {
            updateNavbarForTaskbar();
        }
        for (int i2 = 0; i2 < this.mNavigationBars.size(); i2++) {
            NavigationBar valueAt = this.mNavigationBars.valueAt(i2);
            if (valueAt != null) {
                valueAt.getView().updateStates();
            }
        }
    }

    public void checkNavBarModes(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.checkNavBarModes();
        }
    }

    @VisibleForTesting
    public void createNavigationBar(final Display display, Bundle bundle, final RegisterStatusBarResult registerStatusBarResult) {
        if (display == null) {
            return;
        }
        int displayId = display.getDisplayId();
        boolean z = displayId == 0;
        if (z && initializeTaskbarIfNecessary()) {
            return;
        }
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        Context createDisplayContext = z ? this.mContext : this.mContext.createDisplayContext(display);
        if (hasSoftNavigationBar(createDisplayContext, displayId)) {
            final NavigationBar navigationBar = this.mNavigationBarComponentFactory.create(createDisplayContext, bundle).getNavigationBar();
            navigationBar.init();
            this.mNavigationBars.put(displayId, navigationBar);
            navigationBar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.navigationbar.NavigationBarController.1
                {
                    NavigationBarController.this = this;
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewAttachedToWindow(View view) {
                    if (registerStatusBarResult != null) {
                        NavigationBar navigationBar2 = navigationBar;
                        int displayId2 = display.getDisplayId();
                        RegisterStatusBarResult registerStatusBarResult2 = registerStatusBarResult;
                        navigationBar2.setImeWindowStatus(displayId2, registerStatusBarResult2.mImeToken, registerStatusBarResult2.mImeWindowVis, registerStatusBarResult2.mImeBackDisposition, registerStatusBarResult2.mShowImeSwitcher);
                    }
                }

                @Override // android.view.View.OnAttachStateChangeListener
                public void onViewDetachedFromWindow(View view) {
                    view.removeOnAttachStateChangeListener(this);
                }
            });
            try {
                windowManagerService.onOverlayChanged();
            } catch (RemoteException e) {
            }
        }
    }

    public void createNavigationBars(boolean z, RegisterStatusBarResult registerStatusBarResult) {
        Display[] displays;
        updateAccessibilityButtonModeIfNeeded();
        boolean z2 = z && !initializeTaskbarIfNecessary();
        for (Display display : this.mDisplayManager.getDisplays()) {
            if (z2 || display.getDisplayId() != 0) {
                createNavigationBar(display, null, registerStatusBarResult);
            }
        }
    }

    public void disableAnimationsDuringHide(int i, long j) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.disableAnimationsDuringHide(j);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mIsTablet=" + this.mIsTablet);
        printWriter.println("mNavMode=" + this.mNavMode);
        for (int i = 0; i < this.mNavigationBars.size(); i++) {
            if (i > 0) {
                printWriter.println();
            }
            this.mNavigationBars.valueAt(i).dump(printWriter);
        }
    }

    public void finishBarAnimations(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.finishBarAnimations();
        }
    }

    public NavigationBar getDefaultNavigationBar() {
        return this.mNavigationBars.get(0);
    }

    public NavigationBarView getDefaultNavigationBarView() {
        return getNavigationBarView(0);
    }

    public final NavigationBar getNavigationBar(int i) {
        return this.mNavigationBars.get(i);
    }

    public NavigationBarView getNavigationBarView(int i) {
        NavigationBar navigationBar = getNavigationBar(i);
        return navigationBar == null ? null : navigationBar.getView();
    }

    public final boolean hasSoftNavigationBar(Context context, int i) {
        if (i == 0 && NavbarUtils.isEnabled(context)) {
            return true;
        }
        try {
            return WindowManagerGlobal.getWindowManagerService().hasNavigationBar(i);
        } catch (RemoteException e) {
            Log.e(TAG, "Failed to check soft navigation bar", e);
            return false;
        }
    }

    public final boolean initializeTaskbarIfNecessary() {
        boolean z = this.mIsTablet || this.mFeatureFlags.isEnabled(Flags.HIDE_NAVBAR_WINDOW);
        if (z) {
            Trace.beginSection("NavigationBarController#initializeTaskbarIfNecessary");
            removeNavigationBar(this.mContext.getDisplayId());
            this.mTaskbarDelegate.init(this.mContext.getDisplayId());
            Trace.endSection();
        } else {
            this.mTaskbarDelegate.destroy();
        }
        return z;
    }

    public boolean isOverviewEnabled(int i) {
        NavigationBarView navigationBarView = getNavigationBarView(i);
        return navigationBarView != null ? navigationBarView.isOverviewEnabled() : this.mTaskbarDelegate.isOverviewEnabled();
    }

    public void onConfigChanged(Configuration configuration) {
        boolean z = this.mIsTablet;
        this.mIsTablet = Utilities.isTablet(this.mContext);
        boolean applyNewConfig = this.mConfigChanges.applyNewConfig(this.mContext.getResources());
        boolean z2 = this.mIsTablet != z;
        Log.i("NoBackGesture", "NavbarController: newConfig=" + configuration + " mTaskbarDelegate initialized=" + this.mTaskbarDelegate.isInitialized() + " willApplyConfigToNavbars=" + applyNewConfig + " navBarCount=" + this.mNavigationBars.size());
        if (this.mTaskbarDelegate.isInitialized()) {
            this.mTaskbarDelegate.onConfigurationChanged(configuration);
        }
        if (z2 && updateNavbarForTaskbar()) {
            return;
        }
        if (!applyNewConfig) {
            for (int i = 0; i < this.mNavigationBars.size(); i++) {
                this.mNavigationBars.valueAt(i).onConfigurationChanged(configuration);
            }
            return;
        }
        for (int i2 = 0; i2 < this.mNavigationBars.size(); i2++) {
            recreateNavigationBar(this.mNavigationBars.keyAt(i2));
        }
    }

    public void onDisplayReady(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        this.mIsTablet = Utilities.isTablet(this.mContext);
        createNavigationBar(display, null, null);
    }

    public void onDisplayRemoved(int i) {
        removeNavigationBar(i);
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        final int i2 = this.mNavMode;
        if (i2 == i) {
            return;
        }
        this.mNavMode = i;
        updateAccessibilityButtonModeIfNeeded();
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBarController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarController.$r8$lambda$eCLFWqxEyx7Ep_fem0hWsrbcP5Q(NavigationBarController.this, i2);
            }
        });
    }

    public final void recreateNavigationBar(int i) {
        Bundle bundle = new Bundle();
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.onSaveInstanceState(bundle);
        }
        removeNavigationBar(i);
        createNavigationBar(this.mDisplayManager.getDisplay(i), bundle, null);
    }

    public void removeNavigationBar(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.destroyView();
            this.mNavigationBars.remove(i);
        }
    }

    public void setNavigationBarLumaSamplingEnabled(int i, boolean z) {
        NavigationBar navigationBar = getNavigationBar(i);
        if (navigationBar != null) {
            navigationBar.setNavigationBarLumaSamplingEnabled(z);
        }
    }

    public void showPinningEnterExitToast(int i, boolean z) {
        NavigationBarView navigationBarView = getNavigationBarView(i);
        if (navigationBarView != null) {
            navigationBarView.showPinningEnterExitToast(z);
        } else if (i == 0 && this.mTaskbarDelegate.isInitialized()) {
            this.mTaskbarDelegate.showPinningEnterExitToast(z);
        }
    }

    public void showPinningEscapeToast(int i) {
        NavigationBarView navigationBarView = getNavigationBarView(i);
        if (navigationBarView != null) {
            navigationBarView.showPinningEscapeToast();
        } else if (i == 0 && this.mTaskbarDelegate.isInitialized()) {
            this.mTaskbarDelegate.showPinningEscapeToast();
        }
    }

    public void touchAutoDim(int i) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.touchAutoDim();
        }
    }

    public void transitionTo(int i, int i2, boolean z) {
        NavigationBar navigationBar = this.mNavigationBars.get(i);
        if (navigationBar != null) {
            navigationBar.transitionTo(i2, z);
        }
    }

    public final void updateAccessibilityButtonModeIfNeeded() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int intForUser = Settings.Secure.getIntForUser(contentResolver, "accessibility_button_mode", 0, -2);
        if (intForUser == 1) {
            return;
        }
        if (QuickStepContract.isGesturalMode(this.mNavMode) && intForUser == 0) {
            Settings.Secure.putIntForUser(contentResolver, "accessibility_button_mode", 2, -2);
        } else if (QuickStepContract.isGesturalMode(this.mNavMode) || intForUser != 2) {
        } else {
            Settings.Secure.putIntForUser(contentResolver, "accessibility_button_mode", 0, -2);
        }
    }

    public final boolean updateNavbarForTaskbar() {
        boolean initializeTaskbarIfNecessary = initializeTaskbarIfNecessary();
        if (!initializeTaskbarIfNecessary && this.mNavigationBars.get(this.mContext.getDisplayId()) == null) {
            createNavigationBar(this.mContext.getDisplay(), null, null);
        }
        return initializeTaskbarIfNecessary;
    }
}