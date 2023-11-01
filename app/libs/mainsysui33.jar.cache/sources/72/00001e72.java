package com.android.systemui.navigationbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.InsetsState;
import android.view.InsetsVisibilities;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/TaskbarDelegate.class */
public class TaskbarDelegate implements CommandQueue.Callbacks, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable {
    public static final String TAG = "TaskbarDelegate";
    public int mAppearance;
    public AutoHideController mAutoHideController;
    public BackAnimation mBackAnimation;
    public int mBehavior;
    public CommandQueue mCommandQueue;
    public final Context mContext;
    public int mDisabledFlags;
    public int mDisplayId;
    public final DisplayManager mDisplayManager;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public boolean mInitialized;
    public LightBarController mLightBarController;
    public LightBarTransitionsController mLightBarTransitionsController;
    public final LightBarTransitionsController.Factory mLightBarTransitionsControllerFactory;
    public NavBarHelper mNavBarHelper;
    public int mNavigationIconHints;
    public NavigationModeController mNavigationModeController;
    public OverviewProxyService mOverviewProxyService;
    public final Consumer<Rect> mPipListener;
    public Optional<Pip> mPipOptional;
    public ScreenPinningNotify mScreenPinningNotify;
    public SysUiState mSysUiState;
    public boolean mTaskbarTransientShowing;
    public int mTransitionMode;
    public Context mWindowContext;
    public final NavBarHelper.NavbarTaskbarStateUpdater mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.1
        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public void updateAccessibilityServicesState() {
            TaskbarDelegate.this.updateSysuiFlags();
        }

        @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
        public void updateAssistantAvailable(boolean z, boolean z2) {
            TaskbarDelegate.this.updateAssistantAvailability(z, z2);
        }
    };
    public int mTaskBarWindowState = 0;
    public int mNavigationMode = -1;
    public final AutoHideUiElement mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.2
        public void hide() {
            TaskbarDelegate.this.clearTransient();
        }

        public boolean isVisible() {
            return TaskbarDelegate.this.mTaskbarTransientShowing;
        }

        public void synchronizeState() {
        }
    };

    public TaskbarDelegate(Context context, EdgeBackGestureHandler.Factory factory, LightBarTransitionsController.Factory factory2) {
        this.mLightBarTransitionsControllerFactory = factory2;
        final EdgeBackGestureHandler create = factory.create(context);
        this.mEdgeBackGestureHandler = create;
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService(DisplayManager.class);
        Objects.requireNonNull(create);
        this.mPipListener = new Consumer() { // from class: com.android.systemui.navigationbar.TaskbarDelegate$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EdgeBackGestureHandler.this.setPipStashExclusionBounds((Rect) obj);
            }
        };
    }

    public void abortTransient(int i, int[] iArr) {
        if (i == this.mDisplayId && InsetsState.containsType(iArr, 21)) {
            clearTransient();
        }
    }

    public void addPipExclusionBoundsChangeListener(Pip pip) {
        pip.addPipExclusionBoundsChangeListener(this.mPipListener);
    }

    public final boolean allowSystemGestureIgnoringBarVisibility() {
        return this.mBehavior != 2;
    }

    public final void clearTransient() {
        if (this.mTaskbarTransientShowing) {
            this.mTaskbarTransientShowing = false;
            onTransientStateChanged();
        }
    }

    public final LightBarTransitionsController createLightBarTransitionsController() {
        LightBarTransitionsController create = this.mLightBarTransitionsControllerFactory.create(new LightBarTransitionsController.DarkIntensityApplier() { // from class: com.android.systemui.navigationbar.TaskbarDelegate.3
            public void applyDarkIntensity(float f) {
                TaskbarDelegate.this.mOverviewProxyService.onNavButtonsDarkIntensityChanged(f);
            }

            public int getTintAnimationDuration() {
                return 120;
            }
        });
        create.overrideIconTintForNavMode(true);
        return create;
    }

    public void destroy() {
        if (this.mInitialized) {
            this.mCommandQueue.removeCallback(this);
            this.mOverviewProxyService.removeCallback((OverviewProxyService.OverviewProxyListener) this);
            this.mNavigationModeController.removeListener(this);
            this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
            this.mNavBarHelper.destroy();
            this.mEdgeBackGestureHandler.onNavBarDetached();
            this.mScreenPinningNotify = null;
            this.mWindowContext = null;
            this.mAutoHideController.setNavigationBar((AutoHideUiElement) null);
            this.mLightBarTransitionsController.destroy();
            this.mLightBarController.setNavigationBar((LightBarTransitionsController) null);
            this.mPipOptional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.TaskbarDelegate$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    TaskbarDelegate.this.removePipExclusionBoundsChangeListener((Pip) obj);
                }
            });
            this.mInitialized = false;
        }
    }

    public void disable(int i, int i2, int i3, boolean z) {
        this.mDisabledFlags = i2;
        updateSysuiFlags();
        this.mOverviewProxyService.disable(i, i2, i3, z);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("TaskbarDelegate (displayId=" + this.mDisplayId + "):");
        StringBuilder sb = new StringBuilder();
        sb.append("  mNavigationIconHints=");
        sb.append(this.mNavigationIconHints);
        printWriter.println(sb.toString());
        printWriter.println("  mNavigationMode=" + this.mNavigationMode);
        printWriter.println("  mDisabledFlags=" + this.mDisabledFlags);
        printWriter.println("  mTaskBarWindowState=" + this.mTaskBarWindowState);
        printWriter.println("  mBehavior=" + this.mBehavior);
        printWriter.println("  mTaskbarTransientShowing=" + this.mTaskbarTransientShowing);
        this.mEdgeBackGestureHandler.dump(printWriter);
    }

    public int getNavigationMode() {
        return this.mNavigationMode;
    }

    public void init(int i) {
        if (this.mInitialized) {
            return;
        }
        this.mDisplayId = i;
        parseCurrentSysuiState();
        this.mCommandQueue.addCallback(this);
        this.mOverviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        onNavigationModeChanged(this.mNavigationModeController.addListener(this));
        this.mNavBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        this.mNavBarHelper.init();
        this.mEdgeBackGestureHandler.onNavBarAttached();
        Context createWindowContext = this.mContext.createWindowContext(this.mDisplayManager.getDisplay(i), 2, null);
        this.mWindowContext = createWindowContext;
        this.mScreenPinningNotify = new ScreenPinningNotify(createWindowContext);
        updateSysuiFlags();
        this.mAutoHideController.setNavigationBar(this.mAutoHideUiElement);
        this.mLightBarController.setNavigationBar(this.mLightBarTransitionsController);
        this.mPipOptional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.TaskbarDelegate$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                TaskbarDelegate.this.addPipExclusionBoundsChangeListener((Pip) obj);
            }
        });
        this.mEdgeBackGestureHandler.setBackAnimation(this.mBackAnimation);
        this.mEdgeBackGestureHandler.onConfigurationChanged(this.mContext.getResources().getConfiguration());
        this.mInitialized = true;
    }

    public final boolean isImmersiveMode() {
        return this.mBehavior == 2;
    }

    public boolean isInitialized() {
        return this.mInitialized;
    }

    public boolean isOverviewEnabled() {
        return (this.mSysUiState.getFlags() & 16777216) == 0;
    }

    public final boolean isWindowVisible() {
        return this.mTaskBarWindowState == 0;
    }

    public void onConfigurationChanged(Configuration configuration) {
        this.mEdgeBackGestureHandler.onConfigurationChanged(configuration);
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavigationMode = i;
        this.mEdgeBackGestureHandler.onNavigationModeChanged(i);
    }

    public void onRecentsAnimationStateChanged(boolean z) {
    }

    public void onRotationProposal(int i, boolean z) {
        this.mOverviewProxyService.onRotationProposal(i, z);
    }

    public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str, LetterboxDetails[] letterboxDetailsArr) {
        boolean z2;
        this.mOverviewProxyService.onSystemBarAttributesChanged(i, i3);
        if (this.mAppearance != i2) {
            this.mAppearance = i2;
            z2 = updateTransitionMode(NavBarHelper.transitionMode(this.mTaskbarTransientShowing, i2));
        } else {
            z2 = false;
        }
        if (i == this.mDisplayId) {
            this.mLightBarController.onNavigationBarAppearanceChanged(i2, z2, 0, z);
        }
        if (this.mBehavior != i3) {
            this.mBehavior = i3;
            updateSysuiFlags();
        }
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public void onTaskbarAutohideSuspend(boolean z) {
        if (z) {
            this.mAutoHideController.suspendAutoHide();
        } else {
            this.mAutoHideController.resumeSuspendedAutoHide();
        }
    }

    public final void onTransientStateChanged() {
        this.mEdgeBackGestureHandler.onNavBarTransientStateChanged(this.mTaskbarTransientShowing);
        int transitionMode = NavBarHelper.transitionMode(this.mTaskbarTransientShowing, this.mAppearance);
        if (updateTransitionMode(transitionMode)) {
            this.mLightBarController.onNavigationBarModeChanged(transitionMode);
        }
    }

    public final void parseCurrentSysuiState() {
        NavBarHelper.CurrentSysuiState currentSysuiState = this.mNavBarHelper.getCurrentSysuiState();
        if (currentSysuiState.mWindowStateDisplayId == this.mDisplayId) {
            this.mTaskBarWindowState = currentSysuiState.mWindowState;
        }
    }

    public void removePipExclusionBoundsChangeListener(Pip pip) {
        pip.removePipExclusionBoundsChangeListener(this.mPipListener);
    }

    public void setDependencies(CommandQueue commandQueue, OverviewProxyService overviewProxyService, NavBarHelper navBarHelper, NavigationModeController navigationModeController, SysUiState sysUiState, DumpManager dumpManager, AutoHideController autoHideController, LightBarController lightBarController, Optional<Pip> optional, BackAnimation backAnimation) {
        this.mCommandQueue = commandQueue;
        this.mOverviewProxyService = overviewProxyService;
        this.mNavBarHelper = navBarHelper;
        this.mNavigationModeController = navigationModeController;
        this.mSysUiState = sysUiState;
        dumpManager.registerDumpable(this);
        this.mAutoHideController = autoHideController;
        this.mLightBarController = lightBarController;
        this.mPipOptional = optional;
        this.mBackAnimation = backAnimation;
        this.mLightBarTransitionsController = createLightBarTransitionsController();
    }

    public void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        boolean isImeShown = this.mNavBarHelper.isImeShown(i2);
        boolean z2 = isImeShown;
        if (!isImeShown) {
            z2 = (i2 & 8) != 0;
        }
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, z2, z2 && z);
        if (calculateBackDispositionHints != this.mNavigationIconHints) {
            this.mNavigationIconHints = calculateBackDispositionHints;
            updateSysuiFlags();
        }
    }

    public void setWindowState(int i, int i2, int i3) {
        if (i == this.mDisplayId && i2 == 2 && this.mTaskBarWindowState != i3) {
            this.mTaskBarWindowState = i3;
            updateSysuiFlags();
        }
    }

    public void showPinningEnterExitToast(boolean z) {
        updateSysuiFlags();
        ScreenPinningNotify screenPinningNotify = this.mScreenPinningNotify;
        if (screenPinningNotify == null) {
            return;
        }
        if (z) {
            screenPinningNotify.showPinningStartToast();
        } else {
            screenPinningNotify.showPinningExitToast();
        }
    }

    public void showPinningEscapeToast() {
        updateSysuiFlags();
        ScreenPinningNotify screenPinningNotify = this.mScreenPinningNotify;
        if (screenPinningNotify == null) {
            return;
        }
        screenPinningNotify.showEscapeToast(QuickStepContract.isGesturalMode(this.mNavigationMode), !QuickStepContract.isGesturalMode(this.mNavigationMode));
    }

    public void showTransient(int i, int[] iArr, boolean z) {
        if (i == this.mDisplayId && InsetsState.containsType(iArr, 21) && !this.mTaskbarTransientShowing) {
            this.mTaskbarTransientShowing = true;
            onTransientStateChanged();
        }
    }

    public final void updateAssistantAvailability(boolean z, boolean z2) {
        if (this.mOverviewProxyService.getProxy() == null) {
            return;
        }
        try {
            this.mOverviewProxyService.getProxy().onAssistantAvailable(z, z2);
        } catch (RemoteException e) {
            String str = TAG;
            Log.e(str, "onAssistantAvailable() failed, available: " + z, e);
        }
    }

    public final void updateSysuiFlags() {
        int a11yButtonState = this.mNavBarHelper.getA11yButtonState();
        SysUiState flag = this.mSysUiState.setFlag(16, (a11yButtonState & 16) != 0).setFlag(32, (a11yButtonState & 32) != 0).setFlag(262144, (this.mNavigationIconHints & 1) != 0).setFlag(1048576, (this.mNavigationIconHints & 4) != 0).setFlag(RecyclerView.ViewHolder.FLAG_IGNORE, (this.mDisabledFlags & 16777216) != 0).setFlag(RecyclerView.ViewHolder.FLAG_TMP_DETACHED, (this.mDisabledFlags & 2097152) != 0);
        boolean z = false;
        if ((this.mDisabledFlags & 4194304) != 0) {
            z = true;
        }
        flag.setFlag(4194304, z).setFlag(2, !isWindowVisible()).setFlag(131072, allowSystemGestureIgnoringBarVisibility()).setFlag(1, ActivityManagerWrapper.getInstance().isScreenPinningActive()).setFlag(16777216, isImmersiveMode()).commitUpdate(this.mDisplayId);
    }

    public final boolean updateTransitionMode(int i) {
        if (this.mTransitionMode != i) {
            this.mTransitionMode = i;
            AutoHideController autoHideController = this.mAutoHideController;
            if (autoHideController != null) {
                autoHideController.touchAutoHide();
                return true;
            }
            return true;
        }
        return false;
    }
}