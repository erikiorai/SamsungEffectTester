package com.android.keyguard;

import android.os.Bundle;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.KeyguardBypassController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardViewController.class */
public interface KeyguardViewController {
    void blockPanelExpansionFromCurrentTouch();

    void dismissAndCollapse();

    ViewRootImpl getViewRootImpl();

    void hide(long j, long j2);

    void hideAlternateBouncer(boolean z);

    boolean isBouncerShowing();

    boolean isGoingToNotificationShade();

    boolean isUnlockWithWallpaper();

    void keyguardGoingAway();

    void notifyKeyguardAuthenticated(boolean z);

    void onCancelClicked();

    default void onFinishedGoingToSleep() {
    }

    default void onStartedGoingToSleep() {
    }

    default void onStartedWakingUp() {
    }

    boolean primaryBouncerIsOrWillBeShowing();

    void registerCentralSurfaces(CentralSurfaces centralSurfaces, NotificationPanelViewController notificationPanelViewController, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view, KeyguardBypassController keyguardBypassController);

    void reset(boolean z);

    void setKeyguardGoingAwayState(boolean z);

    void setNeedsInput(boolean z);

    void setOccluded(boolean z, boolean z2);

    boolean shouldDisableWindowAnimationsForUnlock();

    boolean shouldSubtleWindowAnimationsForUnlock();

    void show(Bundle bundle);

    void showPrimaryBouncer(boolean z);

    void startPreHideAnimation(Runnable runnable);
}