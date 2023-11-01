package com.android.systemui.navigationbar;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.Dumpable;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavBarHelper.class */
public final class NavBarHelper implements AccessibilityManager.AccessibilityServicesStateChangeListener, AccessibilityButtonModeObserver.ModeChangedListener, AccessibilityButtonTargetsObserver.TargetsChangedListener, OverviewProxyService.OverviewProxyListener, NavigationModeController.ModeChangedListener, Dumpable, CommandQueue.Callbacks {
    public int mA11yButtonState;
    public final AccessibilityButtonModeObserver mAccessibilityButtonModeObserver;
    public final AccessibilityButtonTargetsObserver mAccessibilityButtonTargetsObserver;
    public final AccessibilityManager mAccessibilityManager;
    public final Lazy<AssistManager> mAssistManagerLazy;
    public boolean mAssistantAvailable;
    public boolean mAssistantTouchGestureEnabled;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final ContentResolver mContentResolver;
    public final Context mContext;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mLongPressHomeEnabled;
    public int mNavBarMode;
    public final SystemActions mSystemActions;
    public final UserTracker mUserTracker;
    public int mWindowState;
    public int mWindowStateDisplayId;
    public final List<NavbarTaskbarStateUpdater> mA11yEventListeners = new ArrayList();
    public final ContentObserver mAssistContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.android.systemui.navigationbar.NavBarHelper.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            NavBarHelper.this.updateAssistantAvailability();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavBarHelper$CurrentSysuiState.class */
    public class CurrentSysuiState {
        public final int mWindowState;
        public final int mWindowStateDisplayId;

        public CurrentSysuiState() {
            this.mWindowStateDisplayId = NavBarHelper.this.mWindowStateDisplayId;
            this.mWindowState = NavBarHelper.this.mWindowState;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavBarHelper$NavbarTaskbarStateUpdater.class */
    public interface NavbarTaskbarStateUpdater {
        void updateAccessibilityServicesState();

        void updateAssistantAvailable(boolean z, boolean z2);
    }

    public NavBarHelper(Context context, AccessibilityManager accessibilityManager, AccessibilityButtonModeObserver accessibilityButtonModeObserver, AccessibilityButtonTargetsObserver accessibilityButtonTargetsObserver, SystemActions systemActions, OverviewProxyService overviewProxyService, Lazy<AssistManager> lazy, Lazy<Optional<CentralSurfaces>> lazy2, KeyguardStateController keyguardStateController, NavigationModeController navigationModeController, UserTracker userTracker, DumpManager dumpManager, CommandQueue commandQueue) {
        this.mContext = context;
        this.mCommandQueue = commandQueue;
        this.mContentResolver = context.getContentResolver();
        this.mAccessibilityManager = accessibilityManager;
        this.mAssistManagerLazy = lazy;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mKeyguardStateController = keyguardStateController;
        this.mUserTracker = userTracker;
        this.mSystemActions = systemActions;
        accessibilityManager.addAccessibilityServicesStateChangeListener(this);
        this.mAccessibilityButtonModeObserver = accessibilityButtonModeObserver;
        this.mAccessibilityButtonTargetsObserver = accessibilityButtonTargetsObserver;
        accessibilityButtonModeObserver.addListener(this);
        accessibilityButtonTargetsObserver.addListener(this);
        this.mNavBarMode = navigationModeController.addListener(this);
        overviewProxyService.addCallback((OverviewProxyService.OverviewProxyListener) this);
        dumpManager.registerDumpable(this);
    }

    public static int transitionMode(boolean z, int i) {
        if (z) {
            return 1;
        }
        if ((i & 6) == 6) {
            return 3;
        }
        if ((i & 4) != 0) {
            return 6;
        }
        if ((i & 2) != 0) {
            return 4;
        }
        return (i & 64) != 0 ? 1 : 0;
    }

    public void destroy() {
        this.mContentResolver.unregisterContentObserver(this.mAssistContentObserver);
        this.mCommandQueue.removeCallback(this);
    }

    public final void dispatchA11yEventUpdate() {
        for (NavbarTaskbarStateUpdater navbarTaskbarStateUpdater : this.mA11yEventListeners) {
            navbarTaskbarStateUpdater.updateAccessibilityServicesState();
        }
    }

    public final void dispatchAssistantEventUpdate(boolean z, boolean z2) {
        for (NavbarTaskbarStateUpdater navbarTaskbarStateUpdater : this.mA11yEventListeners) {
            navbarTaskbarStateUpdater.updateAssistantAvailable(z, z2);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("NavbarTaskbarFriendster");
        printWriter.println("  longPressHomeEnabled=" + this.mLongPressHomeEnabled);
        printWriter.println("  mAssistantTouchGestureEnabled=" + this.mAssistantTouchGestureEnabled);
        printWriter.println("  mAssistantAvailable=" + this.mAssistantAvailable);
        printWriter.println("  mNavBarMode=" + this.mNavBarMode);
    }

    public int getA11yButtonState() {
        return this.mA11yButtonState;
    }

    public CurrentSysuiState getCurrentSysuiState() {
        return new CurrentSysuiState();
    }

    public boolean getLongPressHomeEnabled() {
        return this.mLongPressHomeEnabled;
    }

    public void init() {
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assistant"), false, this.mAssistContentObserver, -1);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_long_press_home_enabled"), false, this.mAssistContentObserver, -1);
        this.mContentResolver.registerContentObserver(Settings.Secure.getUriFor("assist_touch_gesture_enabled"), false, this.mAssistContentObserver, -1);
        updateAssistantAvailability();
        updateA11yState();
        this.mCommandQueue.addCallback(this);
    }

    public boolean isImeShown(int i) {
        View notificationShadeWindowView = ((Optional) this.mCentralSurfacesOptionalLazy.get()).isPresent() ? ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get()).getNotificationShadeWindowView() : null;
        boolean isShowing = this.mKeyguardStateController.isShowing();
        boolean z = true;
        if (!(notificationShadeWindowView != null && notificationShadeWindowView.isAttachedToWindow() && notificationShadeWindowView.getRootWindowInsets().isVisible(WindowInsets.Type.ime()))) {
            z = (isShowing || (i & 2) == 0) ? false : true;
        }
        return z;
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonModeObserver.ModeChangedListener
    public void onAccessibilityButtonModeChanged(int i) {
        updateA11yState();
        dispatchA11yEventUpdate();
    }

    @Override // com.android.systemui.accessibility.AccessibilityButtonTargetsObserver.TargetsChangedListener
    public void onAccessibilityButtonTargetsChanged(String str) {
        updateA11yState();
        dispatchA11yEventUpdate();
    }

    public void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        dispatchA11yEventUpdate();
        updateA11yState();
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public void onConnectionChanged(boolean z) {
        if (z) {
            updateAssistantAvailability();
        }
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
        updateAssistantAvailability();
    }

    public void registerNavTaskStateUpdater(NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        this.mA11yEventListeners.add(navbarTaskbarStateUpdater);
        navbarTaskbarStateUpdater.updateAccessibilityServicesState();
        navbarTaskbarStateUpdater.updateAssistantAvailable(this.mAssistantAvailable, this.mLongPressHomeEnabled);
    }

    public void removeNavTaskStateUpdater(NavbarTaskbarStateUpdater navbarTaskbarStateUpdater) {
        this.mA11yEventListeners.remove(navbarTaskbarStateUpdater);
    }

    public void setWindowState(int i, int i2, int i3) {
        super.setWindowState(i, i2, i3);
        if (i2 != 2) {
            return;
        }
        this.mWindowStateDisplayId = i;
        this.mWindowState = i3;
    }

    @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
    public void startAssistant(Bundle bundle) {
        ((AssistManager) this.mAssistManagerLazy.get()).startAssist(bundle);
    }

    public final void updateA11yState() {
        int i = this.mA11yButtonState;
        boolean z = true;
        boolean z2 = false;
        int i2 = 0;
        if (this.mAccessibilityButtonModeObserver.getCurrentAccessibilityButtonMode() == 1) {
            this.mA11yButtonState = 0;
            z = false;
        } else {
            int size = this.mAccessibilityManager.getAccessibilityShortcutTargets(0).size();
            z2 = size >= 1;
            if (size < 2) {
                z = false;
            }
            int i3 = z2 ? 16 : 0;
            if (z) {
                i2 = 32;
            }
            this.mA11yButtonState = i3 | i2;
        }
        if (i != this.mA11yButtonState) {
            updateSystemAction(z2, 11);
            updateSystemAction(z, 12);
        }
    }

    public final void updateAssistantAvailability() {
        byte b = ((AssistManager) this.mAssistManagerLazy.get()).getAssistInfoForUser(-2) != null ? (byte) 1 : (byte) 0;
        this.mLongPressHomeEnabled = Settings.Secure.getIntForUser(this.mContentResolver, "assist_long_press_home_enabled", this.mContext.getResources().getBoolean(17891371) ? 1 : 0, this.mUserTracker.getUserId()) != 0;
        boolean z = Settings.Secure.getIntForUser(this.mContentResolver, "assist_touch_gesture_enabled", this.mContext.getResources().getBoolean(17891372) ? 1 : 0, this.mUserTracker.getUserId()) != 0;
        this.mAssistantTouchGestureEnabled = z;
        boolean z2 = b != 0 && z && QuickStepContract.isGesturalMode(this.mNavBarMode);
        this.mAssistantAvailable = z2;
        dispatchAssistantEventUpdate(z2, this.mLongPressHomeEnabled);
    }

    public final void updateSystemAction(boolean z, int i) {
        if (z) {
            this.mSystemActions.register(i);
        } else {
            this.mSystemActions.unregister(i);
        }
    }
}