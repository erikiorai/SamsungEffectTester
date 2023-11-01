package com.android.systemui.accessibility;

import android.app.PendingIntent;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.KeyEvent;
import android.view.WindowManagerGlobal;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.util.ScreenshotHelper;
import com.android.systemui.CoreStartable;
import com.android.systemui.recents.Recents;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.util.Assert;
import dagger.Lazy;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/SystemActions.class */
public class SystemActions implements CoreStartable {
    public final AccessibilityManager mA11yManager;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final Context mContext;
    public boolean mDismissNotificationShadeActionRegistered;
    public Locale mLocale;
    public final NotificationShadeWindowController mNotificationShadeController;
    public final Optional<Recents> mRecentsOptional;
    public final ShadeController mShadeController;
    public final SystemActionsBroadcastReceiver mReceiver = new SystemActionsBroadcastReceiver();
    public final StatusBarWindowCallback mNotificationShadeCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda0
        public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
            SystemActions.m1335$r8$lambda$GHbqV9ie0FVxhkaJkcNEMFqxU0(SystemActions.this, z, z2, z3, z4, z5);
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/SystemActions$SystemActionsBroadcastReceiver.class */
    public class SystemActionsBroadcastReceiver extends BroadcastReceiver {
        public SystemActionsBroadcastReceiver() {
            SystemActions.this = r4;
        }

        public final IntentFilter createIntentFilter() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("SYSTEM_ACTION_BACK");
            intentFilter.addAction("SYSTEM_ACTION_HOME");
            intentFilter.addAction("SYSTEM_ACTION_RECENTS");
            intentFilter.addAction("SYSTEM_ACTION_NOTIFICATIONS");
            intentFilter.addAction("SYSTEM_ACTION_QUICK_SETTINGS");
            intentFilter.addAction("SYSTEM_ACTION_POWER_DIALOG");
            intentFilter.addAction("SYSTEM_ACTION_LOCK_SCREEN");
            intentFilter.addAction("SYSTEM_ACTION_TAKE_SCREENSHOT");
            intentFilter.addAction("SYSTEM_ACTION_HEADSET_HOOK");
            intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON");
            intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU");
            intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
            intentFilter.addAction("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE");
            intentFilter.addAction("SYSTEM_ACTION_DPAD_UP");
            intentFilter.addAction("SYSTEM_ACTION_DPAD_DOWN");
            intentFilter.addAction("SYSTEM_ACTION_DPAD_LEFT");
            intentFilter.addAction("SYSTEM_ACTION_DPAD_RIGHT");
            intentFilter.addAction("SYSTEM_ACTION_DPAD_CENTER");
            return intentFilter;
        }

        public final PendingIntent createPendingIntent(Context context, String str) {
            str.hashCode();
            boolean z = true;
            switch (str.hashCode()) {
                case -1103811776:
                    if (str.equals("SYSTEM_ACTION_BACK")) {
                        z = false;
                        break;
                    }
                    break;
                case -1103619272:
                    if (str.equals("SYSTEM_ACTION_HOME")) {
                        z = true;
                        break;
                    }
                    break;
                case -720484549:
                    if (str.equals("SYSTEM_ACTION_POWER_DIALOG")) {
                        z = true;
                        break;
                    }
                    break;
                case -535129457:
                    if (str.equals("SYSTEM_ACTION_NOTIFICATIONS")) {
                        z = true;
                        break;
                    }
                    break;
                case -181386672:
                    if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT")) {
                        z = true;
                        break;
                    }
                    break;
                case -153384569:
                    if (str.equals("SYSTEM_ACTION_LOCK_SCREEN")) {
                        z = true;
                        break;
                    }
                    break;
                case 42571871:
                    if (str.equals("SYSTEM_ACTION_RECENTS")) {
                        z = true;
                        break;
                    }
                    break;
                case 526987266:
                    if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU")) {
                        z = true;
                        break;
                    }
                    break;
                case 689657964:
                    if (str.equals("SYSTEM_ACTION_DPAD_CENTER")) {
                        z = true;
                        break;
                    }
                    break;
                case 815482418:
                    if (str.equals("SYSTEM_ACTION_DPAD_UP")) {
                        z = true;
                        break;
                    }
                    break;
                case 1245940668:
                    if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON")) {
                        z = true;
                        break;
                    }
                    break;
                case 1493428793:
                    if (str.equals("SYSTEM_ACTION_HEADSET_HOOK")) {
                        z = true;
                        break;
                    }
                    break;
                case 1579999269:
                    if (str.equals("SYSTEM_ACTION_TAKE_SCREENSHOT")) {
                        z = true;
                        break;
                    }
                    break;
                case 1668921710:
                    if (str.equals("SYSTEM_ACTION_QUICK_SETTINGS")) {
                        z = true;
                        break;
                    }
                    break;
                case 1698779909:
                    if (str.equals("SYSTEM_ACTION_DPAD_RIGHT")) {
                        z = true;
                        break;
                    }
                    break;
                case 1894867256:
                    if (str.equals("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE")) {
                        z = true;
                        break;
                    }
                    break;
                case 1994051193:
                    if (str.equals("SYSTEM_ACTION_DPAD_DOWN")) {
                        z = true;
                        break;
                    }
                    break;
                case 1994279390:
                    if (str.equals("SYSTEM_ACTION_DPAD_LEFT")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                case true:
                    Intent intent = new Intent(str);
                    intent.setPackage(context.getPackageName());
                    return PendingIntent.getBroadcast(context, 0, intent, 67108864);
                default:
                    return null;
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            action.hashCode();
            boolean z = true;
            switch (action.hashCode()) {
                case -1103811776:
                    if (action.equals("SYSTEM_ACTION_BACK")) {
                        z = false;
                        break;
                    }
                    break;
                case -1103619272:
                    if (action.equals("SYSTEM_ACTION_HOME")) {
                        z = true;
                        break;
                    }
                    break;
                case -720484549:
                    if (action.equals("SYSTEM_ACTION_POWER_DIALOG")) {
                        z = true;
                        break;
                    }
                    break;
                case -535129457:
                    if (action.equals("SYSTEM_ACTION_NOTIFICATIONS")) {
                        z = true;
                        break;
                    }
                    break;
                case -181386672:
                    if (action.equals("SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT")) {
                        z = true;
                        break;
                    }
                    break;
                case -153384569:
                    if (action.equals("SYSTEM_ACTION_LOCK_SCREEN")) {
                        z = true;
                        break;
                    }
                    break;
                case 42571871:
                    if (action.equals("SYSTEM_ACTION_RECENTS")) {
                        z = true;
                        break;
                    }
                    break;
                case 526987266:
                    if (action.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU")) {
                        z = true;
                        break;
                    }
                    break;
                case 689657964:
                    if (action.equals("SYSTEM_ACTION_DPAD_CENTER")) {
                        z = true;
                        break;
                    }
                    break;
                case 815482418:
                    if (action.equals("SYSTEM_ACTION_DPAD_UP")) {
                        z = true;
                        break;
                    }
                    break;
                case 1245940668:
                    if (action.equals("SYSTEM_ACTION_ACCESSIBILITY_BUTTON")) {
                        z = true;
                        break;
                    }
                    break;
                case 1493428793:
                    if (action.equals("SYSTEM_ACTION_HEADSET_HOOK")) {
                        z = true;
                        break;
                    }
                    break;
                case 1579999269:
                    if (action.equals("SYSTEM_ACTION_TAKE_SCREENSHOT")) {
                        z = true;
                        break;
                    }
                    break;
                case 1668921710:
                    if (action.equals("SYSTEM_ACTION_QUICK_SETTINGS")) {
                        z = true;
                        break;
                    }
                    break;
                case 1698779909:
                    if (action.equals("SYSTEM_ACTION_DPAD_RIGHT")) {
                        z = true;
                        break;
                    }
                    break;
                case 1894867256:
                    if (action.equals("SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE")) {
                        z = true;
                        break;
                    }
                    break;
                case 1994051193:
                    if (action.equals("SYSTEM_ACTION_DPAD_DOWN")) {
                        z = true;
                        break;
                    }
                    break;
                case 1994279390:
                    if (action.equals("SYSTEM_ACTION_DPAD_LEFT")) {
                        z = true;
                        break;
                    }
                    break;
            }
            switch (z) {
                case false:
                    SystemActions.this.handleBack();
                    return;
                case true:
                    SystemActions.this.handleHome();
                    return;
                case true:
                    SystemActions.this.handlePowerDialog();
                    return;
                case true:
                    SystemActions.this.handleNotifications();
                    return;
                case true:
                    SystemActions.this.handleAccessibilityShortcut();
                    return;
                case true:
                    SystemActions.this.handleLockScreen();
                    return;
                case true:
                    SystemActions.this.handleRecents();
                    return;
                case true:
                    SystemActions.this.handleAccessibilityButtonChooser();
                    return;
                case true:
                    SystemActions.this.handleDpadCenter();
                    return;
                case true:
                    SystemActions.this.handleDpadUp();
                    return;
                case true:
                    SystemActions.this.handleAccessibilityButton();
                    return;
                case true:
                    SystemActions.this.handleHeadsetHook();
                    return;
                case true:
                    SystemActions.this.handleTakeScreenshot();
                    return;
                case true:
                    SystemActions.this.handleQuickSettings();
                    return;
                case true:
                    SystemActions.this.handleDpadRight();
                    return;
                case true:
                    SystemActions.this.handleAccessibilityDismissNotificationShade();
                    return;
                case true:
                    SystemActions.this.handleDpadDown();
                    return;
                case true:
                    SystemActions.this.handleDpadLeft();
                    return;
                default:
                    return;
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda0.onStateChanged(boolean, boolean, boolean, boolean, boolean):void] */
    /* renamed from: $r8$lambda$GHbqV9ie0-FVxhkaJkcNEMFqxU0 */
    public static /* synthetic */ void m1335$r8$lambda$GHbqV9ie0FVxhkaJkcNEMFqxU0(SystemActions systemActions, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        systemActions.lambda$new$0(z, z2, z3, z4, z5);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$zvoh-C28zCWfPiA1QkJ5-0jPBck */
    public static /* synthetic */ void m1336$r8$lambda$zvohC28zCWfPiA1QkJ50jPBck(CentralSurfaces centralSurfaces) {
        centralSurfaces.animateExpandSettingsPanel(null);
    }

    public SystemActions(Context context, NotificationShadeWindowController notificationShadeWindowController, ShadeController shadeController, Lazy<Optional<CentralSurfaces>> lazy, Optional<Recents> optional) {
        this.mContext = context;
        this.mShadeController = shadeController;
        this.mRecentsOptional = optional;
        this.mLocale = context.getResources().getConfiguration().getLocales().get(0);
        this.mA11yManager = (AccessibilityManager) context.getSystemService("accessibility");
        this.mNotificationShadeController = notificationShadeWindowController;
        this.mCentralSurfacesOptionalLazy = lazy;
    }

    public /* synthetic */ void lambda$new$0(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        registerOrUnregisterDismissNotificationShadeAction();
    }

    public final RemoteAction createRemoteAction(int i, String str) {
        return new RemoteAction(Icon.createWithResource(this.mContext, 17301684), this.mContext.getString(i), this.mContext.getString(i), this.mReceiver.createPendingIntent(this.mContext, str));
    }

    public final void handleAccessibilityButton() {
        AccessibilityManager.getInstance(this.mContext).notifyAccessibilityButtonClicked(0);
    }

    public final void handleAccessibilityButtonChooser() {
        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        intent.addFlags(268468224);
        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
        this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
    }

    public final void handleAccessibilityDismissNotificationShade() {
        this.mShadeController.animateCollapseShade(0);
    }

    public final void handleAccessibilityShortcut() {
        this.mA11yManager.performAccessibilityShortcut();
    }

    public final void handleBack() {
        sendDownAndUpKeyEvents(4);
    }

    public final void handleDpadCenter() {
        sendDownAndUpKeyEvents(23);
    }

    public final void handleDpadDown() {
        sendDownAndUpKeyEvents(20);
    }

    public final void handleDpadLeft() {
        sendDownAndUpKeyEvents(21);
    }

    public final void handleDpadRight() {
        sendDownAndUpKeyEvents(22);
    }

    public final void handleDpadUp() {
        sendDownAndUpKeyEvents(19);
    }

    public final void handleHeadsetHook() {
        sendDownAndUpKeyEvents(79);
    }

    public final void handleHome() {
        sendDownAndUpKeyEvents(3);
    }

    public final void handleLockScreen() {
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        ((PowerManager) this.mContext.getSystemService(PowerManager.class)).goToSleep(SystemClock.uptimeMillis(), 7, 0);
        try {
            windowManagerService.lockNow((Bundle) null);
        } catch (RemoteException e) {
            Log.e("SystemActions", "failed to lock screen.");
        }
    }

    public final void handleNotifications() {
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((CentralSurfaces) obj).animateExpandNotificationsPanel();
            }
        });
    }

    public final void handlePowerDialog() {
        try {
            WindowManagerGlobal.getWindowManagerService().showGlobalActions();
        } catch (RemoteException e) {
            Log.e("SystemActions", "failed to display power dialog.");
        }
    }

    public final void handleQuickSettings() {
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                SystemActions.m1336$r8$lambda$zvohC28zCWfPiA1QkJ50jPBck((CentralSurfaces) obj);
            }
        });
    }

    public final void handleRecents() {
        this.mRecentsOptional.ifPresent(new SystemActions$$ExternalSyntheticLambda4());
    }

    public final void handleTakeScreenshot() {
        new ScreenshotHelper(this.mContext).takeScreenshot(1, 4, new Handler(Looper.getMainLooper()), (Consumer) null);
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        Locale locale = this.mContext.getResources().getConfiguration().getLocales().get(0);
        if (locale.equals(this.mLocale)) {
            return;
        }
        this.mLocale = locale;
        registerActions();
    }

    public void register(int i) {
        int i2;
        String str;
        switch (i) {
            case 1:
                i2 = 17039614;
                str = "SYSTEM_ACTION_BACK";
                break;
            case 2:
                i2 = 17039623;
                str = "SYSTEM_ACTION_HOME";
                break;
            case 3:
                i2 = 17039630;
                str = "SYSTEM_ACTION_RECENTS";
                break;
            case 4:
                i2 = 17039625;
                str = "SYSTEM_ACTION_NOTIFICATIONS";
                break;
            case 5:
                i2 = 17039629;
                str = "SYSTEM_ACTION_QUICK_SETTINGS";
                break;
            case 6:
                i2 = 17039628;
                str = "SYSTEM_ACTION_POWER_DIALOG";
                break;
            case 7:
            case 14:
            default:
                return;
            case 8:
                i2 = 17039624;
                str = "SYSTEM_ACTION_LOCK_SCREEN";
                break;
            case 9:
                i2 = 17039631;
                str = "SYSTEM_ACTION_TAKE_SCREENSHOT";
                break;
            case 10:
                i2 = 17039622;
                str = "SYSTEM_ACTION_HEADSET_HOOK";
                break;
            case 11:
                i2 = 17039627;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON";
                break;
            case 12:
                i2 = 17039626;
                str = "SYSTEM_ACTION_ACCESSIBILITY_BUTTON_MENU";
                break;
            case 13:
                i2 = 17039621;
                str = "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT";
                break;
            case 15:
                i2 = 17039615;
                str = "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE";
                break;
            case 16:
                i2 = 17039620;
                str = "SYSTEM_ACTION_DPAD_UP";
                break;
            case 17:
                i2 = 17039617;
                str = "SYSTEM_ACTION_DPAD_DOWN";
                break;
            case 18:
                i2 = 17039618;
                str = "SYSTEM_ACTION_DPAD_LEFT";
                break;
            case 19:
                i2 = 17039619;
                str = "SYSTEM_ACTION_DPAD_RIGHT";
                break;
            case 20:
                i2 = 17039616;
                str = "SYSTEM_ACTION_DPAD_CENTER";
                break;
        }
        this.mA11yManager.registerSystemAction(createRemoteAction(i2, str), i);
    }

    public final void registerActions() {
        RemoteAction createRemoteAction = createRemoteAction(17039614, "SYSTEM_ACTION_BACK");
        RemoteAction createRemoteAction2 = createRemoteAction(17039623, "SYSTEM_ACTION_HOME");
        RemoteAction createRemoteAction3 = createRemoteAction(17039630, "SYSTEM_ACTION_RECENTS");
        RemoteAction createRemoteAction4 = createRemoteAction(17039625, "SYSTEM_ACTION_NOTIFICATIONS");
        RemoteAction createRemoteAction5 = createRemoteAction(17039629, "SYSTEM_ACTION_QUICK_SETTINGS");
        RemoteAction createRemoteAction6 = createRemoteAction(17039628, "SYSTEM_ACTION_POWER_DIALOG");
        RemoteAction createRemoteAction7 = createRemoteAction(17039624, "SYSTEM_ACTION_LOCK_SCREEN");
        RemoteAction createRemoteAction8 = createRemoteAction(17039631, "SYSTEM_ACTION_TAKE_SCREENSHOT");
        RemoteAction createRemoteAction9 = createRemoteAction(17039622, "SYSTEM_ACTION_HEADSET_HOOK");
        RemoteAction createRemoteAction10 = createRemoteAction(17039621, "SYSTEM_ACTION_ACCESSIBILITY_SHORTCUT");
        RemoteAction createRemoteAction11 = createRemoteAction(17039620, "SYSTEM_ACTION_DPAD_UP");
        RemoteAction createRemoteAction12 = createRemoteAction(17039617, "SYSTEM_ACTION_DPAD_DOWN");
        RemoteAction createRemoteAction13 = createRemoteAction(17039618, "SYSTEM_ACTION_DPAD_LEFT");
        RemoteAction createRemoteAction14 = createRemoteAction(17039619, "SYSTEM_ACTION_DPAD_RIGHT");
        RemoteAction createRemoteAction15 = createRemoteAction(17039616, "SYSTEM_ACTION_DPAD_CENTER");
        this.mA11yManager.registerSystemAction(createRemoteAction, 1);
        this.mA11yManager.registerSystemAction(createRemoteAction2, 2);
        this.mA11yManager.registerSystemAction(createRemoteAction3, 3);
        if (((Optional) this.mCentralSurfacesOptionalLazy.get()).isPresent()) {
            this.mA11yManager.registerSystemAction(createRemoteAction4, 4);
            this.mA11yManager.registerSystemAction(createRemoteAction5, 5);
        }
        this.mA11yManager.registerSystemAction(createRemoteAction6, 6);
        this.mA11yManager.registerSystemAction(createRemoteAction7, 8);
        this.mA11yManager.registerSystemAction(createRemoteAction8, 9);
        this.mA11yManager.registerSystemAction(createRemoteAction9, 10);
        this.mA11yManager.registerSystemAction(createRemoteAction10, 13);
        this.mA11yManager.registerSystemAction(createRemoteAction11, 16);
        this.mA11yManager.registerSystemAction(createRemoteAction12, 17);
        this.mA11yManager.registerSystemAction(createRemoteAction13, 18);
        this.mA11yManager.registerSystemAction(createRemoteAction14, 19);
        this.mA11yManager.registerSystemAction(createRemoteAction15, 20);
        registerOrUnregisterDismissNotificationShadeAction();
    }

    public final void registerOrUnregisterDismissNotificationShadeAction() {
        Assert.isMainThread();
        Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
        if (!((Boolean) optional.map(new Function() { // from class: com.android.systemui.accessibility.SystemActions$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((CentralSurfaces) obj).isPanelExpanded());
            }
        }).orElse(Boolean.FALSE)).booleanValue() || ((CentralSurfaces) optional.get()).isKeyguardShowing()) {
            if (this.mDismissNotificationShadeActionRegistered) {
                this.mA11yManager.unregisterSystemAction(15);
                this.mDismissNotificationShadeActionRegistered = false;
            }
        } else if (this.mDismissNotificationShadeActionRegistered) {
        } else {
            this.mA11yManager.registerSystemAction(createRemoteAction(17039615, "SYSTEM_ACTION_ACCESSIBILITY_DISMISS_NOTIFICATION_SHADE"), 15);
            this.mDismissNotificationShadeActionRegistered = true;
        }
    }

    public final void sendDownAndUpKeyEvents(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        sendKeyEventIdentityCleared(i, 0, uptimeMillis, uptimeMillis);
        sendKeyEventIdentityCleared(i, 1, uptimeMillis, SystemClock.uptimeMillis());
    }

    public final void sendKeyEventIdentityCleared(int i, int i2, long j, long j2) {
        KeyEvent obtain = KeyEvent.obtain(j, j2, i2, i, 0, 0, -1, 0, 8, 257, null);
        InputManager.getInstance().injectInputEvent(obtain, 0);
        obtain.recycle();
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mNotificationShadeController.registerCallback(this.mNotificationShadeCallback);
        Context context = this.mContext;
        SystemActionsBroadcastReceiver systemActionsBroadcastReceiver = this.mReceiver;
        context.registerReceiverForAllUsers(systemActionsBroadcastReceiver, systemActionsBroadcastReceiver.createIntentFilter(), "com.android.systemui.permission.SELF", null, 2);
        registerActions();
    }

    public void unregister(int i) {
        this.mA11yManager.unregisterSystemAction(i);
    }
}