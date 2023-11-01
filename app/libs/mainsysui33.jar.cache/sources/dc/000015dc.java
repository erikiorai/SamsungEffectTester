package com.android.systemui.dagger;

import android.app.INotificationManager;
import android.content.Context;
import android.service.dreams.IDreamManager;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.wmshell.BubblesManager;
import com.android.wm.shell.bubbles.Bubbles;
import java.util.Optional;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/SystemUIModule.class */
public abstract class SystemUIModule {
    public static Optional<BubblesManager> provideBubblesManager(Context context, Optional<Bubbles> optional, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, ShadeController shadeController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, IDreamManager iDreamManager, NotificationVisibilityProvider notificationVisibilityProvider, NotificationInterruptStateProvider notificationInterruptStateProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, FeatureFlags featureFlags, Executor executor) {
        return Optional.ofNullable(BubblesManager.create(context, optional, notificationShadeWindowController, keyguardStateController, shadeController, iStatusBarService, iNotificationManager, iDreamManager, notificationVisibilityProvider, notificationInterruptStateProvider, zenModeController, notificationLockscreenUserManager, commonNotifCollection, notifPipeline, sysUiState, featureFlags, executor));
    }

    public static SysUiState provideSysUiState(DumpManager dumpManager) {
        SysUiState sysUiState = new SysUiState();
        dumpManager.registerDumpable(sysUiState);
        return sysUiState;
    }
}