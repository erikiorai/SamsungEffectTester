package com.android.systemui.recents;

import android.app.ActivityTaskManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Insets;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IVoiceInteractionSessionListener;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.ScreenshotHelper;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shared.recents.IOverviewProxy;
import com.android.systemui.shared.recents.ISystemUiProxy;
import com.android.systemui.shared.recents.model.Task;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.CallbackController;
import com.android.wm.shell.sysui.ShellInterface;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyService.class */
public class OverviewProxyService implements CallbackController<OverviewProxyListener>, NavigationModeController.ModeChangedListener, Dumpable {
    public Region mActiveNavBarRegion;
    public boolean mBound;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public int mConnectionBackoffAttempts;
    public final Context mContext;
    public final Handler mHandler;
    public long mInputFocusTransferStartMillis;
    public float mInputFocusTransferStartY;
    public boolean mInputFocusTransferStarted;
    public boolean mIsEnabled;
    public final BroadcastReceiver mLauncherStateChangedReceiver;
    public final ScreenLifecycle.Observer mLifecycleObserver;
    public final Executor mMainExecutor;
    public final Lazy<NavigationBarController> mNavBarControllerLazy;
    public int mNavBarMode;
    public SurfaceControl mNavigationBarSurface;
    public IOverviewProxy mOverviewProxy;
    public final ServiceConnection mOverviewServiceConnection;
    public final IBinder.DeathRecipient mOverviewServiceDeathRcpt;
    public final Intent mQuickStepIntent;
    public final ComponentName mRecentsComponentName;
    public final ScreenshotHelper mScreenshotHelper;
    public final ShellInterface mShellInterface;
    public final NotificationShadeWindowController mStatusBarWinController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public boolean mSupportsRoundedCornersOnWindows;
    public SysUiState mSysUiState;
    public final KeyguardUnlockAnimationController mSysuiUnlockAnimationController;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final IVoiceInteractionSessionListener mVoiceInteractionSessionListener;
    public float mWindowCornerRadius;
    public final Runnable mConnectionRunnable = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            OverviewProxyService.$r8$lambda$5woo0ZUfUI59FZVuoOikLBgk2hQ(OverviewProxyService.this);
        }
    };
    public final List<OverviewProxyListener> mConnectionCallbacks = new ArrayList();
    public int mCurrentBoundedUserId = -1;
    @VisibleForTesting
    public ISystemUiProxy mSysUiProxy = new AnonymousClass1();
    public final Runnable mDeferredConnectionCallback = new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            OverviewProxyService.m4104$r8$lambda$BaFuB_VKFOEOVnRbye2hfBUy8(OverviewProxyService.this);
        }
    };

    /* renamed from: com.android.systemui.recents.OverviewProxyService$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyService$1.class */
    public class AnonymousClass1 extends ISystemUiProxy.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda13.run():void] */
        /* renamed from: $r8$lambda$3MyHYUESwjq1QhDV1UOje-Aug2o */
        public static /* synthetic */ void m4149$r8$lambda$3MyHYUESwjq1QhDV1UOjeAug2o(AnonymousClass1 anonymousClass1, boolean z) {
            anonymousClass1.lambda$onOverviewShown$11(z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7.run():void] */
        public static /* synthetic */ void $r8$lambda$4jFp4JwEtAwnpqQGpgOJ6jEKebM() {
            lambda$stopScreenPinning$2();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda14.run():void] */
        public static /* synthetic */ void $r8$lambda$74_0Idkh_hbsXddzoh2cGicJakQ(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$notifyAccessibilityButtonLongClicked$16();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda8.run():void] */
        public static /* synthetic */ void $r8$lambda$8GVPnlGuOV62tQe9f7hc_Yu5Dic(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$onBackPressed$6();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda21.get():java.lang.Object] */
        public static /* synthetic */ Boolean $r8$lambda$9p19KJpTDDVD1pPYUlXb9XOPCoE(AnonymousClass1 anonymousClass1, Runnable runnable) {
            return anonymousClass1.lambda$verifyCallerAndClearCallingIdentityPostMain$21(runnable);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda16.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$AefU5kCQI4iBUcsJVJejFBzELMo(int i, CentralSurfaces centralSurfaces) {
            centralSurfaces.showScreenPinningRequest(i, false);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12.run():void] */
        public static /* synthetic */ void $r8$lambda$AouN_ZrfzxwHIrS7RQInGx89bTY(AnonymousClass1 anonymousClass1, int i) {
            anonymousClass1.lambda$notifyAccessibilityButtonClicked$15(i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda17.run():void] */
        /* renamed from: $r8$lambda$LH-44PafQiAaDgNB3OGPlnJdAqw */
        public static /* synthetic */ void m4150$r8$lambda$LH44PafQiAaDgNB3OGPlnJdAqw(AnonymousClass1 anonymousClass1, boolean z) {
            anonymousClass1.lambda$setHomeRotationEnabled$7(z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10.run():void] */
        public static /* synthetic */ void $r8$lambda$LHSw5Mhgwmxa03NsqRrQqsuKMZE(AnonymousClass1 anonymousClass1, float f) {
            anonymousClass1.lambda$onAssistantProgress$12(f);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda6.run():void] */
        public static /* synthetic */ void $r8$lambda$MYtnpxfI0IaHwV2wCHDJcLlCmWg(AnonymousClass1 anonymousClass1, MotionEvent motionEvent) {
            anonymousClass1.lambda$onStatusBarMotionEvent$5(motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda19.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$RO2o79c25PFukTBSKWbhFVoScSs(AnonymousClass1 anonymousClass1, MotionEvent motionEvent, CentralSurfaces centralSurfaces) {
            anonymousClass1.lambda$onStatusBarMotionEvent$4(motionEvent, centralSurfaces);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda11.run():void] */
        public static /* synthetic */ void $r8$lambda$VGMDturZzNGp1sPW8stJwzIzxx8(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$expandNotificationPanel$18();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1.run():void] */
        /* renamed from: $r8$lambda$W8h7K19fVKEq-PX5-84STDoQP1g */
        public static /* synthetic */ void m4151$r8$lambda$W8h7K19fVKEqPX584STDoQP1g(AnonymousClass1 anonymousClass1, boolean z) {
            anonymousClass1.lambda$setHomeRotationEnabled$8(z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2.run():void] */
        public static /* synthetic */ void $r8$lambda$ZEjXN7HK4zmsfd0Uc_c7959dflY(AnonymousClass1 anonymousClass1) {
            anonymousClass1.lambda$toggleNotificationPanel$19();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda5.run():void] */
        public static /* synthetic */ void $r8$lambda$_Jk1G8y_Opyq2CHxsvobqunYwn8(AnonymousClass1 anonymousClass1, int i) {
            anonymousClass1.lambda$startScreenPinning$1(i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda15.run():void] */
        public static /* synthetic */ void $r8$lambda$cfa42ktpei2hAZ2lOXTQW6mR8L0(AnonymousClass1 anonymousClass1, int i) {
            anonymousClass1.lambda$notifyPrioritizedRotation$17(i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3.run():void] */
        public static /* synthetic */ void $r8$lambda$ihHSKTQmoJ2WFA5g3EHIigkfKhU(AnonymousClass1 anonymousClass1, float f) {
            anonymousClass1.lambda$onAssistantGestureCompletion$13(f);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$l0Grgg8HqM_Cz46z6uFeGkW7SsE(AnonymousClass1 anonymousClass1, boolean z, boolean z2) {
            anonymousClass1.lambda$notifyTaskbarStatus$9(z, z2);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda9.run():void] */
        public static /* synthetic */ void $r8$lambda$n5oZu9SyUl3TfyS_R5bQu9Sv6mU(AnonymousClass1 anonymousClass1, Bundle bundle) {
            anonymousClass1.lambda$startAssistant$14(bundle);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda18.get():java.lang.Object] */
        public static /* synthetic */ Object $r8$lambda$qicfu3Ral3TjTxsI8lfHAhVYn0k(Runnable runnable) {
            return lambda$verifyCallerAndClearCallingIdentity$20(runnable);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4.run():void] */
        /* renamed from: $r8$lambda$tAJ1cIROgAYzbS4RqeO9XeCpY-E */
        public static /* synthetic */ void m4152$r8$lambda$tAJ1cIROgAYzbS4RqeO9XeCpYE(AnonymousClass1 anonymousClass1, boolean z) {
            anonymousClass1.lambda$notifyTaskbarAutohideSuspend$10(z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda22.run():void] */
        /* renamed from: $r8$lambda$unreWvD-GXmK044zYCvGgv3rmHE */
        public static /* synthetic */ void m4153$r8$lambda$unreWvDGXmK044zYCvGgv3rmHE(AnonymousClass1 anonymousClass1, MotionEvent motionEvent, CentralSurfaces centralSurfaces) {
            anonymousClass1.lambda$onStatusBarMotionEvent$3(motionEvent, centralSurfaces);
        }

        public AnonymousClass1() {
            OverviewProxyService.this = r4;
        }

        public /* synthetic */ void lambda$expandNotificationPanel$18() {
            OverviewProxyService.this.mCommandQueue.handleSystemKey(281);
        }

        public /* synthetic */ void lambda$notifyAccessibilityButtonClicked$15(int i) {
            AccessibilityManager.getInstance(OverviewProxyService.this.mContext).notifyAccessibilityButtonClicked(i);
        }

        public /* synthetic */ void lambda$notifyAccessibilityButtonLongClicked$16() {
            Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
            intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
            intent.addFlags(268468224);
            OverviewProxyService.this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        }

        public /* synthetic */ void lambda$notifyPrioritizedRotation$17(int i) {
            OverviewProxyService.this.notifyPrioritizedRotationInternal(i);
        }

        public /* synthetic */ void lambda$notifyTaskbarAutohideSuspend$10(boolean z) {
            OverviewProxyService.this.onTaskbarAutohideSuspend(z);
        }

        public /* synthetic */ void lambda$notifyTaskbarStatus$9(boolean z, boolean z2) {
            OverviewProxyService.this.onTaskbarStatusUpdated(z, z2);
        }

        public /* synthetic */ void lambda$onAssistantGestureCompletion$13(float f) {
            OverviewProxyService.this.notifyAssistantGestureCompletion(f);
        }

        public /* synthetic */ void lambda$onAssistantProgress$12(float f) {
            OverviewProxyService.this.notifyAssistantProgress(f);
        }

        public /* synthetic */ void lambda$onBackPressed$6() {
            sendEvent(0, 4);
            sendEvent(1, 4);
        }

        public /* synthetic */ void lambda$onOverviewShown$11(boolean z) {
            for (int size = OverviewProxyService.this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
                ((OverviewProxyListener) OverviewProxyService.this.mConnectionCallbacks.get(size)).onOverviewShown(z);
            }
        }

        public /* synthetic */ void lambda$onStatusBarMotionEvent$3(MotionEvent motionEvent, CentralSurfaces centralSurfaces) {
            int actionMasked = motionEvent.getActionMasked();
            boolean z = false;
            if (actionMasked == 0) {
                OverviewProxyService.this.mInputFocusTransferStarted = true;
                OverviewProxyService.this.mInputFocusTransferStartY = motionEvent.getY();
                OverviewProxyService.this.mInputFocusTransferStartMillis = motionEvent.getEventTime();
                centralSurfaces.onInputFocusTransfer(OverviewProxyService.this.mInputFocusTransferStarted, false, (float) ActionBarShadowController.ELEVATION_LOW);
            }
            if (actionMasked == 1 || actionMasked == 3) {
                OverviewProxyService.this.mInputFocusTransferStarted = false;
                float y = (motionEvent.getY() - OverviewProxyService.this.mInputFocusTransferStartY) / ((float) (motionEvent.getEventTime() - OverviewProxyService.this.mInputFocusTransferStartMillis));
                boolean z2 = OverviewProxyService.this.mInputFocusTransferStarted;
                if (actionMasked == 3) {
                    z = true;
                }
                centralSurfaces.onInputFocusTransfer(z2, z, y);
            }
            motionEvent.recycle();
        }

        public /* synthetic */ void lambda$onStatusBarMotionEvent$4(final MotionEvent motionEvent, final CentralSurfaces centralSurfaces) {
            if (motionEvent.getActionMasked() == 0) {
                centralSurfaces.getNotificationPanelViewController().startExpandLatencyTracking();
            }
            OverviewProxyService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda22
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.m4153$r8$lambda$unreWvDGXmK044zYCvGgv3rmHE(OverviewProxyService.AnonymousClass1.this, motionEvent, centralSurfaces);
                }
            });
        }

        public /* synthetic */ void lambda$onStatusBarMotionEvent$5(final MotionEvent motionEvent) {
            ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda19
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$RO2o79c25PFukTBSKWbhFVoScSs(OverviewProxyService.AnonymousClass1.this, motionEvent, (CentralSurfaces) obj);
                }
            });
        }

        public /* synthetic */ void lambda$setHomeRotationEnabled$7(boolean z) {
            OverviewProxyService.this.notifyHomeRotationEnabled(z);
        }

        public /* synthetic */ void lambda$setHomeRotationEnabled$8(final boolean z) {
            OverviewProxyService.this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.m4150$r8$lambda$LH44PafQiAaDgNB3OGPlnJdAqw(OverviewProxyService.AnonymousClass1.this, z);
                }
            });
        }

        public /* synthetic */ void lambda$startAssistant$14(Bundle bundle) {
            OverviewProxyService.this.notifyStartAssistant(bundle);
        }

        public /* synthetic */ void lambda$startScreenPinning$1(final int i) {
            ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda16
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$AefU5kCQI4iBUcsJVJejFBzELMo(i, (CentralSurfaces) obj);
                }
            });
        }

        public static /* synthetic */ void lambda$stopScreenPinning$2() {
            try {
                ActivityTaskManager.getService().stopSystemLockTaskMode();
            } catch (RemoteException e) {
                Log.e("OverviewProxyService", "Failed to stop screen pinning");
            }
        }

        public /* synthetic */ void lambda$toggleNotificationPanel$19() {
            ((Optional) OverviewProxyService.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda20
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CentralSurfaces) obj).togglePanel();
                }
            });
        }

        public static /* synthetic */ Object lambda$verifyCallerAndClearCallingIdentity$20(Runnable runnable) {
            runnable.run();
            return null;
        }

        public /* synthetic */ Boolean lambda$verifyCallerAndClearCallingIdentityPostMain$21(Runnable runnable) {
            return Boolean.valueOf(OverviewProxyService.this.mHandler.post(runnable));
        }

        public void expandNotificationPanel() {
            verifyCallerAndClearCallingIdentity("expandNotificationPanel", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda11
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$VGMDturZzNGp1sPW8stJwzIzxx8(OverviewProxyService.AnonymousClass1.this);
                }
            });
        }

        public void handleImageBundleAsScreenshot(Bundle bundle, Rect rect, Insets insets, Task.TaskKey taskKey) {
            OverviewProxyService.this.mScreenshotHelper.provideScreenshot(bundle, rect, insets, taskKey.id, taskKey.userId, taskKey.sourceComponent, 3, OverviewProxyService.this.mHandler, (Consumer) null);
        }

        public void notifyAccessibilityButtonClicked(final int i) {
            verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonClicked", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda12
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$AouN_ZrfzxwHIrS7RQInGx89bTY(OverviewProxyService.AnonymousClass1.this, i);
                }
            });
        }

        public void notifyAccessibilityButtonLongClicked() {
            verifyCallerAndClearCallingIdentity("notifyAccessibilityButtonLongClicked", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda14
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$74_0Idkh_hbsXddzoh2cGicJakQ(OverviewProxyService.AnonymousClass1.this);
                }
            });
        }

        public void notifyPrioritizedRotation(final int i) {
            verifyCallerAndClearCallingIdentityPostMain("notifyPrioritizedRotation", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda15
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$cfa42ktpei2hAZ2lOXTQW6mR8L0(OverviewProxyService.AnonymousClass1.this, i);
                }
            });
        }

        public void notifyTaskbarAutohideSuspend(final boolean z) {
            verifyCallerAndClearCallingIdentityPostMain("notifyTaskbarAutohideSuspend", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.m4152$r8$lambda$tAJ1cIROgAYzbS4RqeO9XeCpYE(OverviewProxyService.AnonymousClass1.this, z);
                }
            });
        }

        public void notifyTaskbarStatus(final boolean z, final boolean z2) {
            verifyCallerAndClearCallingIdentityPostMain("notifyTaskbarStatus", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$l0Grgg8HqM_Cz46z6uFeGkW7SsE(OverviewProxyService.AnonymousClass1.this, z, z2);
                }
            });
        }

        public void onAssistantGestureCompletion(final float f) {
            verifyCallerAndClearCallingIdentityPostMain("onAssistantGestureCompletion", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$ihHSKTQmoJ2WFA5g3EHIigkfKhU(OverviewProxyService.AnonymousClass1.this, f);
                }
            });
        }

        public void onAssistantProgress(final float f) {
            verifyCallerAndClearCallingIdentityPostMain("onAssistantProgress", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$LHSw5Mhgwmxa03NsqRrQqsuKMZE(OverviewProxyService.AnonymousClass1.this, f);
                }
            });
        }

        public void onBackPressed() {
            verifyCallerAndClearCallingIdentityPostMain("onBackPressed", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$8GVPnlGuOV62tQe9f7hc_Yu5Dic(OverviewProxyService.AnonymousClass1.this);
                }
            });
        }

        public void onImeSwitcherPressed() {
            ((InputMethodManager) OverviewProxyService.this.mContext.getSystemService(InputMethodManager.class)).showInputMethodPickerFromSystem(true, 0);
            OverviewProxyService.this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
        }

        public void onOverviewShown(final boolean z) {
            verifyCallerAndClearCallingIdentityPostMain("onOverviewShown", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda13
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.m4149$r8$lambda$3MyHYUESwjq1QhDV1UOjeAug2o(OverviewProxyService.AnonymousClass1.this, z);
                }
            });
        }

        public void onStatusBarMotionEvent(final MotionEvent motionEvent) {
            verifyCallerAndClearCallingIdentity("onStatusBarMotionEvent", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$MYtnpxfI0IaHwV2wCHDJcLlCmWg(OverviewProxyService.AnonymousClass1.this, motionEvent);
                }
            });
        }

        public final boolean sendEvent(int i, int i2) {
            long uptimeMillis = SystemClock.uptimeMillis();
            KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, i2, 0, 0, -1, 0, 72, 257);
            keyEvent.setDisplayId(OverviewProxyService.this.mContext.getDisplay().getDisplayId());
            return InputManager.getInstance().injectInputEvent(keyEvent, 0);
        }

        public void setHomeRotationEnabled(final boolean z) {
            verifyCallerAndClearCallingIdentityPostMain("setHomeRotationEnabled", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.m4151$r8$lambda$W8h7K19fVKEqPX584STDoQP1g(OverviewProxyService.AnonymousClass1.this, z);
                }
            });
        }

        public void startAssistant(final Bundle bundle) {
            verifyCallerAndClearCallingIdentityPostMain("startAssistant", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda9
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$n5oZu9SyUl3TfyS_R5bQu9Sv6mU(OverviewProxyService.AnonymousClass1.this, bundle);
                }
            });
        }

        public void startScreenPinning(final int i) {
            verifyCallerAndClearCallingIdentityPostMain("startScreenPinning", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$_Jk1G8y_Opyq2CHxsvobqunYwn8(OverviewProxyService.AnonymousClass1.this, i);
                }
            });
        }

        public void stopScreenPinning() {
            verifyCallerAndClearCallingIdentityPostMain("stopScreenPinning", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$4jFp4JwEtAwnpqQGpgOJ6jEKebM();
                }
            });
        }

        public void toggleNotificationPanel() {
            verifyCallerAndClearCallingIdentityPostMain("toggleNotificationPanel", new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass1.$r8$lambda$ZEjXN7HK4zmsfd0Uc_c7959dflY(OverviewProxyService.AnonymousClass1.this);
                }
            });
        }

        public final boolean verifyCaller(String str) {
            int identifier = Binder.getCallingUserHandle().getIdentifier();
            if (identifier != OverviewProxyService.this.mCurrentBoundedUserId) {
                Log.w("OverviewProxyService", "Launcher called sysui with invalid user: " + identifier + ", reason: " + str);
                return false;
            }
            return true;
        }

        public final <T> T verifyCallerAndClearCallingIdentity(String str, Supplier<T> supplier) {
            if (verifyCaller(str)) {
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return supplier.get();
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
            return null;
        }

        public final void verifyCallerAndClearCallingIdentity(String str, final Runnable runnable) {
            verifyCallerAndClearCallingIdentity(str, new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda18
                @Override // java.util.function.Supplier
                public final Object get() {
                    return OverviewProxyService.AnonymousClass1.$r8$lambda$qicfu3Ral3TjTxsI8lfHAhVYn0k(runnable);
                }
            });
        }

        public final void verifyCallerAndClearCallingIdentityPostMain(String str, final Runnable runnable) {
            verifyCallerAndClearCallingIdentity(str, new Supplier() { // from class: com.android.systemui.recents.OverviewProxyService$1$$ExternalSyntheticLambda21
                @Override // java.util.function.Supplier
                public final Object get() {
                    return OverviewProxyService.AnonymousClass1.$r8$lambda$9p19KJpTDDVD1pPYUlXb9XOPCoE(OverviewProxyService.AnonymousClass1.this, runnable);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.recents.OverviewProxyService$4 */
    /* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyService$4.class */
    public class AnonymousClass4 extends IVoiceInteractionSessionListener.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$4$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$wvuJZM1tF5jB1JKoFyGi1iLVq9U(AnonymousClass4 anonymousClass4, boolean z) {
            anonymousClass4.lambda$onVoiceSessionWindowVisibilityChanged$0(z);
        }

        public AnonymousClass4() {
            OverviewProxyService.this = r4;
        }

        public /* synthetic */ void lambda$onVoiceSessionWindowVisibilityChanged$0(boolean z) {
            OverviewProxyService.this.onVoiceSessionWindowVisibilityChanged(z);
        }

        public void onSetUiHints(Bundle bundle) {
        }

        public void onVoiceSessionHidden() {
        }

        public void onVoiceSessionShown() {
        }

        public void onVoiceSessionWindowVisibilityChanged(final boolean z) {
            OverviewProxyService.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.AnonymousClass4.$r8$lambda$wvuJZM1tF5jB1JKoFyGi1iLVq9U(OverviewProxyService.AnonymousClass4.this, z);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyService$OverviewProxyListener.class */
    public interface OverviewProxyListener {
        default void onAssistantGestureCompletion(float f) {
        }

        default void onAssistantProgress(float f) {
        }

        default void onConnectionChanged(boolean z) {
        }

        default void onHomeRotationEnabled(boolean z) {
        }

        default void onOverviewShown(boolean z) {
        }

        default void onPrioritizedRotation(int i) {
        }

        default void onTaskbarAutohideSuspend(boolean z) {
        }

        default void onTaskbarStatusUpdated(boolean z, boolean z2) {
        }

        default void onToggleRecentApps() {
        }

        default void startAssistant(Bundle bundle) {
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$5woo0ZUfUI59FZVuoOikLBgk2hQ(OverviewProxyService overviewProxyService) {
        overviewProxyService.internalConnectToCurrentUser();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda2.onStateChanged(boolean, boolean, boolean, boolean, boolean):void] */
    public static /* synthetic */ void $r8$lambda$85q850aC8ulY_C_yWs5YeATSuh4(OverviewProxyService overviewProxyService, boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        overviewProxyService.onStatusBarStateChanged(z, z2, z3, z4, z5);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$BaF-uB_VKFOEOVnRby-e2hfBUy8 */
    public static /* synthetic */ void m4104$r8$lambda$BaFuB_VKFOEOVnRbye2hfBUy8(OverviewProxyService overviewProxyService) {
        overviewProxyService.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda6.accept(java.lang.Object):void] */
    /* renamed from: $r8$lambda$UgRmow9-mSsMmj60_aUeqtp5Q7c */
    public static /* synthetic */ void m4105$r8$lambda$UgRmow9mSsMmj60_aUeqtp5Q7c(OverviewProxyService overviewProxyService, CentralSurfaces centralSurfaces) {
        overviewProxyService.lambda$cleanupAfterDeath$1(centralSurfaces);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$jLrIrn3XPwZC57WCFjL_HBtShq8(OverviewProxyService overviewProxyService) {
        overviewProxyService.lambda$cleanupAfterDeath$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda4.onSystemUiStateChanged(int):void] */
    /* renamed from: $r8$lambda$zhQ-o-xDJ5Q9exIJnaLTc1_-AzM */
    public static /* synthetic */ void m4106$r8$lambda$zhQoxDJ5Q9exIJnaLTc1_AzM(OverviewProxyService overviewProxyService, int i) {
        overviewProxyService.notifySystemUiStateFlags(i);
    }

    public OverviewProxyService(Context context, Executor executor, CommandQueue commandQueue, ShellInterface shellInterface, Lazy<NavigationBarController> lazy, Lazy<Optional<CentralSurfaces>> lazy2, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, SysUiState sysUiState, UserTracker userTracker, ScreenLifecycle screenLifecycle, UiEventLogger uiEventLogger, KeyguardUnlockAnimationController keyguardUnlockAnimationController, AssistUtils assistUtils, DumpManager dumpManager) {
        this.mNavBarMode = 0;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.recents.OverviewProxyService.2
            {
                OverviewProxyService.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                OverviewProxyService.this.updateEnabledState();
                OverviewProxyService.this.startConnectionToCurrentUser();
            }
        };
        this.mLauncherStateChangedReceiver = broadcastReceiver;
        this.mOverviewServiceConnection = new ServiceConnection() { // from class: com.android.systemui.recents.OverviewProxyService.3
            {
                OverviewProxyService.this = this;
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                Log.w("OverviewProxyService", "Binding died of '" + componentName + "', try reconnecting");
                OverviewProxyService.this.mCurrentBoundedUserId = -1;
                OverviewProxyService.this.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                Log.w("OverviewProxyService", "Null binding of '" + componentName + "', try reconnecting");
                OverviewProxyService.this.mCurrentBoundedUserId = -1;
                OverviewProxyService.this.retryConnectionWithBackoff();
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                OverviewProxyService.this.mConnectionBackoffAttempts = 0;
                OverviewProxyService.this.mHandler.removeCallbacks(OverviewProxyService.this.mDeferredConnectionCallback);
                try {
                    iBinder.linkToDeath(OverviewProxyService.this.mOverviewServiceDeathRcpt, 0);
                    OverviewProxyService overviewProxyService = OverviewProxyService.this;
                    overviewProxyService.mCurrentBoundedUserId = overviewProxyService.mUserTracker.getUserId();
                    OverviewProxyService.this.mOverviewProxy = IOverviewProxy.Stub.asInterface(iBinder);
                    Bundle bundle = new Bundle();
                    bundle.putBinder("extra_sysui_proxy", OverviewProxyService.this.mSysUiProxy.asBinder());
                    bundle.putFloat("extra_window_corner_radius", OverviewProxyService.this.mWindowCornerRadius);
                    bundle.putBoolean("extra_supports_window_corners", OverviewProxyService.this.mSupportsRoundedCornersOnWindows);
                    bundle.putBinder("unlock_animation", OverviewProxyService.this.mSysuiUnlockAnimationController.asBinder());
                    OverviewProxyService.this.mShellInterface.createExternalInterfaces(bundle);
                    try {
                        Log.d("OverviewProxyService", "OverviewProxyService connected, initializing overview proxy");
                        OverviewProxyService.this.mOverviewProxy.onInitialize(bundle);
                    } catch (RemoteException e) {
                        OverviewProxyService.this.mCurrentBoundedUserId = -1;
                        Log.e("OverviewProxyService", "Failed to call onInitialize()", e);
                    }
                    OverviewProxyService.this.dispatchNavButtonBounds();
                    OverviewProxyService.this.dispatchNavigationBarSurface();
                    OverviewProxyService.this.updateSystemUiStateFlags();
                    OverviewProxyService overviewProxyService2 = OverviewProxyService.this;
                    overviewProxyService2.notifySystemUiStateFlags(overviewProxyService2.mSysUiState.getFlags());
                    OverviewProxyService.this.notifyConnectionChanged();
                } catch (RemoteException e2) {
                    Log.e("OverviewProxyService", "Lost connection to launcher service", e2);
                    OverviewProxyService.this.disconnectFromLauncherService();
                    OverviewProxyService.this.retryConnectionWithBackoff();
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                Log.w("OverviewProxyService", "Service disconnected");
                OverviewProxyService.this.mCurrentBoundedUserId = -1;
            }
        };
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda2
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
                OverviewProxyService.$r8$lambda$85q850aC8ulY_C_yWs5YeATSuh4(OverviewProxyService.this, z, z2, z3, z4, z5);
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        this.mOverviewServiceDeathRcpt = new IBinder.DeathRecipient() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda3
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                OverviewProxyService.this.cleanupAfterDeath();
            }
        };
        AnonymousClass4 anonymousClass4 = new AnonymousClass4();
        this.mVoiceInteractionSessionListener = anonymousClass4;
        UserTracker.Callback callback = new UserTracker.Callback() { // from class: com.android.systemui.recents.OverviewProxyService.5
            {
                OverviewProxyService.this = this;
            }

            public void onUserChanged(int i, Context context2) {
                OverviewProxyService.this.mConnectionBackoffAttempts = 0;
                OverviewProxyService.this.internalConnectToCurrentUser();
            }
        };
        this.mUserChangedCallback = callback;
        ScreenLifecycle.Observer observer = new ScreenLifecycle.Observer() { // from class: com.android.systemui.recents.OverviewProxyService.7
            {
                OverviewProxyService.this = this;
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public void onScreenTurnedOn() {
                try {
                    if (OverviewProxyService.this.mOverviewProxy != null) {
                        OverviewProxyService.this.mOverviewProxy.onScreenTurnedOn();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turned on event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurnedOn()", e);
                }
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public void onScreenTurningOff() {
                try {
                    if (OverviewProxyService.this.mOverviewProxy != null) {
                        OverviewProxyService.this.mOverviewProxy.onScreenTurningOff();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turning off event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurningOff()", e);
                }
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public void onScreenTurningOn() {
                try {
                    if (OverviewProxyService.this.mOverviewProxy != null) {
                        OverviewProxyService.this.mOverviewProxy.onScreenTurningOn();
                    } else {
                        Log.e("OverviewProxyService", "Failed to get overview proxy for screen turning on event.");
                    }
                } catch (RemoteException e) {
                    Log.e("OverviewProxyService", "Failed to call onScreenTurningOn()", e);
                }
            }
        };
        this.mLifecycleObserver = observer;
        if (!Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            Log.e("OverviewProxyService", "Unexpected initialization for non-primary user", new Throwable());
        }
        this.mContext = context;
        this.mMainExecutor = executor;
        this.mShellInterface = shellInterface;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mHandler = new Handler();
        this.mNavBarControllerLazy = lazy;
        this.mStatusBarWinController = notificationShadeWindowController;
        this.mUserTracker = userTracker;
        this.mConnectionBackoffAttempts = 0;
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(17040039));
        this.mRecentsComponentName = unflattenFromString;
        this.mQuickStepIntent = new Intent("android.intent.action.QUICKSTEP_SERVICE").setPackage(unflattenFromString.getPackageName());
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mSupportsRoundedCornersOnWindows = ScreenDecorationsUtils.supportsRoundedCornersOnWindows(context.getResources());
        this.mSysUiState = sysUiState;
        sysUiState.addCallback(new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda4
            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public final void onSystemUiStateChanged(int i) {
                OverviewProxyService.m4106$r8$lambda$zhQoxDJ5Q9exIJnaLTc1_AzM(OverviewProxyService.this, i);
            }
        });
        this.mUiEventLogger = uiEventLogger;
        dumpManager.registerDumpable(getClass().getSimpleName(), this);
        this.mNavBarMode = navigationModeController.addListener(this);
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        intentFilter.addDataSchemeSpecificPart(unflattenFromString.getPackageName(), 0);
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        context.registerReceiver(broadcastReceiver, intentFilter);
        notificationShadeWindowController.registerCallback(statusBarWindowCallback);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        commandQueue.addCallback(new CommandQueue.Callbacks() { // from class: com.android.systemui.recents.OverviewProxyService.6
            {
                OverviewProxyService.this = this;
            }

            public void enterStageSplitFromRunningApp(boolean z) {
                if (OverviewProxyService.this.mOverviewProxy != null) {
                    try {
                        OverviewProxyService.this.mOverviewProxy.enterStageSplitFromRunningApp(z);
                    } catch (RemoteException e) {
                        Log.w("OverviewProxyService", "Unable to enter stage split from the current running app");
                    }
                }
            }

            public void onTracingStateChanged(boolean z) {
                OverviewProxyService.this.mSysUiState.setFlag(RecyclerView.ViewHolder.FLAG_APPEARED_IN_PRE_LAYOUT, z).commitUpdate(OverviewProxyService.this.mContext.getDisplayId());
            }
        });
        this.mCommandQueue = commandQueue;
        userTracker.addCallback(callback, executor);
        screenLifecycle.addObserver(observer);
        updateEnabledState();
        startConnectionToCurrentUser();
        this.mSysuiUnlockAnimationController = keyguardUnlockAnimationController;
        assistUtils.registerVoiceInteractionSessionListener(anonymousClass4);
    }

    public /* synthetic */ void lambda$cleanupAfterDeath$1(CentralSurfaces centralSurfaces) {
        this.mInputFocusTransferStarted = false;
        centralSurfaces.onInputFocusTransfer(false, true, (float) ActionBarShadowController.ELEVATION_LOW);
    }

    public /* synthetic */ void lambda$cleanupAfterDeath$2() {
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OverviewProxyService.m4105$r8$lambda$UgRmow9mSsMmj60_aUeqtp5Q7c(OverviewProxyService.this, (CentralSurfaces) obj);
            }
        });
    }

    public /* synthetic */ void lambda$new$0() {
        Log.w("OverviewProxyService", "Binder supposed established connection but actual connection to service timed out, trying again");
        retryConnectionWithBackoff();
    }

    public void addCallback(OverviewProxyListener overviewProxyListener) {
        if (!this.mConnectionCallbacks.contains(overviewProxyListener)) {
            this.mConnectionCallbacks.add(overviewProxyListener);
        }
        overviewProxyListener.onConnectionChanged(this.mOverviewProxy != null);
    }

    public void cleanupAfterDeath() {
        if (this.mInputFocusTransferStarted) {
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.recents.OverviewProxyService$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    OverviewProxyService.$r8$lambda$jLrIrn3XPwZC57WCFjL_HBtShq8(OverviewProxyService.this);
                }
            });
        }
        startConnectionToCurrentUser();
    }

    public void disable(int i, int i2, int i3, boolean z) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.disable(i, i2, i3, z);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy for disable flags.");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call disable()", e);
        }
    }

    public final void disconnectFromLauncherService() {
        if (this.mBound) {
            this.mContext.unbindService(this.mOverviewServiceConnection);
            this.mBound = false;
        }
        IOverviewProxy iOverviewProxy = this.mOverviewProxy;
        if (iOverviewProxy != null) {
            iOverviewProxy.asBinder().unlinkToDeath(this.mOverviewServiceDeathRcpt, 0);
            this.mOverviewProxy = null;
            notifyConnectionChanged();
        }
    }

    public final void dispatchNavButtonBounds() {
        Region region;
        IOverviewProxy iOverviewProxy = this.mOverviewProxy;
        if (iOverviewProxy == null || (region = this.mActiveNavBarRegion) == null) {
            return;
        }
        try {
            iOverviewProxy.onActiveNavBarRegionChanges(region);
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onActiveNavBarRegionChanges()", e);
        }
    }

    public final void dispatchNavigationBarSurface() {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onNavigationBarSurface(this.mNavigationBarSurface);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to notify back action", e);
        }
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("OverviewProxyService state:");
        printWriter.print("  isConnected=");
        printWriter.println(this.mOverviewProxy != null);
        printWriter.print("  mIsEnabled=");
        printWriter.println(isEnabled());
        printWriter.print("  mRecentsComponentName=");
        printWriter.println(this.mRecentsComponentName);
        printWriter.print("  mQuickStepIntent=");
        printWriter.println(this.mQuickStepIntent);
        printWriter.print("  mBound=");
        printWriter.println(this.mBound);
        printWriter.print("  mCurrentBoundedUserId=");
        printWriter.println(this.mCurrentBoundedUserId);
        printWriter.print("  mConnectionBackoffAttempts=");
        printWriter.println(this.mConnectionBackoffAttempts);
        printWriter.print("  mInputFocusTransferStarted=");
        printWriter.println(this.mInputFocusTransferStarted);
        printWriter.print("  mInputFocusTransferStartY=");
        printWriter.println(this.mInputFocusTransferStartY);
        printWriter.print("  mInputFocusTransferStartMillis=");
        printWriter.println(this.mInputFocusTransferStartMillis);
        printWriter.print("  mWindowCornerRadius=");
        printWriter.println(this.mWindowCornerRadius);
        printWriter.print("  mSupportsRoundedCornersOnWindows=");
        printWriter.println(this.mSupportsRoundedCornersOnWindows);
        printWriter.print("  mActiveNavBarRegion=");
        printWriter.println(this.mActiveNavBarRegion);
        printWriter.print("  mNavigationBarSurface=");
        printWriter.println(this.mNavigationBarSurface);
        printWriter.print("  mNavBarMode=");
        printWriter.println(this.mNavBarMode);
        this.mSysUiState.dump(printWriter, strArr);
    }

    public IOverviewProxy getProxy() {
        return this.mOverviewProxy;
    }

    public final void internalConnectToCurrentUser() {
        disconnectFromLauncherService();
        if (!isEnabled()) {
            Log.v("OverviewProxyService", "Cannot attempt connection, is enabled " + isEnabled());
            return;
        }
        this.mHandler.removeCallbacks(this.mConnectionRunnable);
        try {
            this.mBound = this.mContext.bindServiceAsUser(new Intent("android.intent.action.QUICKSTEP_SERVICE").setPackage(this.mRecentsComponentName.getPackageName()), this.mOverviewServiceConnection, 33554433, UserHandle.of(this.mUserTracker.getUserId()));
        } catch (SecurityException e) {
            Log.e("OverviewProxyService", "Unable to bind because of security error", e);
        }
        if (this.mBound) {
            this.mHandler.postDelayed(this.mDeferredConnectionCallback, 5000L);
        } else {
            retryConnectionWithBackoff();
        }
    }

    public boolean isEnabled() {
        return this.mIsEnabled;
    }

    public final void notifyAssistantGestureCompletion(float f) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onAssistantGestureCompletion(f);
        }
    }

    public final void notifyAssistantProgress(float f) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onAssistantProgress(f);
        }
    }

    public final void notifyConnectionChanged() {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onConnectionChanged(this.mOverviewProxy != null);
        }
    }

    public final void notifyHomeRotationEnabled(boolean z) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onHomeRotationEnabled(z);
        }
    }

    public final void notifyPrioritizedRotationInternal(int i) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onPrioritizedRotation(i);
        }
    }

    public final void notifyStartAssistant(Bundle bundle) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).startAssistant(bundle);
        }
    }

    public final void notifySystemUiStateFlags(int i) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onSystemUiStateChanged(i);
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to notify sysui state change", e);
        }
    }

    public void notifyToggleRecentApps() {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onToggleRecentApps();
        }
    }

    public void onActiveNavBarRegionChanges(Region region) {
        this.mActiveNavBarRegion = region;
        dispatchNavButtonBounds();
    }

    public void onNavButtonsDarkIntensityChanged(float f) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onNavButtonsDarkIntensityChanged(f);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy to update nav buttons dark intensity");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onNavButtonsDarkIntensityChanged()", e);
        }
    }

    public void onNavigationBarSurfaceChanged(SurfaceControl surfaceControl) {
        this.mNavigationBarSurface = surfaceControl;
        dispatchNavigationBarSurface();
    }

    @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
    public void onNavigationModeChanged(int i) {
        this.mNavBarMode = i;
    }

    public void onRotationProposal(int i, boolean z) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onRotationProposal(i, z);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy for proposing rotation.");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onRotationProposal()", e);
        }
    }

    public final void onStatusBarStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5) {
        this.mSysUiState.setFlag(64, z && !z2).setFlag(RecyclerView.ViewHolder.FLAG_ADAPTER_POSITION_UNKNOWN, z && z2).setFlag(8, z3).setFlag(2097152, z4).commitUpdate(this.mContext.getDisplayId());
    }

    public void onSystemBarAttributesChanged(int i, int i2) {
        try {
            IOverviewProxy iOverviewProxy = this.mOverviewProxy;
            if (iOverviewProxy != null) {
                iOverviewProxy.onSystemBarAttributesChanged(i, i2);
            } else {
                Log.e("OverviewProxyService", "Failed to get overview proxy for system bar attr change.");
            }
        } catch (RemoteException e) {
            Log.e("OverviewProxyService", "Failed to call onSystemBarAttributesChanged()", e);
        }
    }

    public final void onTaskbarAutohideSuspend(boolean z) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onTaskbarAutohideSuspend(z);
        }
    }

    public final void onTaskbarStatusUpdated(boolean z, boolean z2) {
        for (int size = this.mConnectionCallbacks.size() - 1; size >= 0; size--) {
            this.mConnectionCallbacks.get(size).onTaskbarStatusUpdated(z, z2);
        }
    }

    public void onVoiceSessionWindowVisibilityChanged(boolean z) {
        this.mSysUiState.setFlag(33554432, z).commitUpdate(this.mContext.getDisplayId());
    }

    public void removeCallback(OverviewProxyListener overviewProxyListener) {
        this.mConnectionCallbacks.remove(overviewProxyListener);
    }

    public final void retryConnectionWithBackoff() {
        if (this.mHandler.hasCallbacks(this.mConnectionRunnable)) {
            return;
        }
        long min = Math.min(Math.scalb(1000.0f, this.mConnectionBackoffAttempts), 600000.0f);
        this.mHandler.postDelayed(this.mConnectionRunnable, min);
        this.mConnectionBackoffAttempts++;
        Log.w("OverviewProxyService", "Failed to connect on attempt " + this.mConnectionBackoffAttempts + " will try again in " + min + "ms");
    }

    public boolean shouldShowSwipeUpUI() {
        return isEnabled() && !QuickStepContract.isLegacyMode(this.mNavBarMode);
    }

    public void startConnectionToCurrentUser() {
        if (this.mHandler.getLooper() != Looper.myLooper()) {
            this.mHandler.post(this.mConnectionRunnable);
        } else {
            internalConnectToCurrentUser();
        }
    }

    public final void updateEnabledState() {
        this.mIsEnabled = this.mContext.getPackageManager().resolveServiceAsUser(this.mQuickStepIntent, 1048576, this.mUserTracker.getUserId()) != null;
    }

    public final void updateSystemUiStateFlags() {
        NavigationBar defaultNavigationBar = ((NavigationBarController) this.mNavBarControllerLazy.get()).getDefaultNavigationBar();
        NavigationBarView navigationBarView = ((NavigationBarController) this.mNavBarControllerLazy.get()).getNavigationBarView(this.mContext.getDisplayId());
        NotificationPanelViewController notificationPanelViewController = ((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get()).getNotificationPanelViewController();
        if (defaultNavigationBar != null) {
            defaultNavigationBar.updateSystemUiStateFlags();
        }
        if (navigationBarView != null) {
            navigationBarView.updateDisabledSystemUiStateFlags(this.mSysUiState);
        }
        if (notificationPanelViewController != null) {
            notificationPanelViewController.updateSystemUiStateFlags();
        }
        NotificationShadeWindowController notificationShadeWindowController = this.mStatusBarWinController;
        if (notificationShadeWindowController != null) {
            notificationShadeWindowController.notifyStateChangedCallbacks();
        }
    }
}