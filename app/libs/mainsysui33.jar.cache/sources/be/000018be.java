package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.StatusBarManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.graphics.Matrix;
import android.hardware.biometrics.BiometricSourceType;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.EventLog;
import android.util.Log;
import android.util.Slog;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardConstants;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.CoreStartable;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.animation.LaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator.class */
public class KeyguardViewMediator implements CoreStartable, Dumpable, StatusBarStateController.StateListener {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public static final Intent USER_PRESENT_INTENT = new Intent("android.intent.action.USER_PRESENT").addFlags(606076928);
    public Lazy<ActivityLaunchAnimator> mActivityLaunchAnimator;
    public AlarmManager mAlarmManager;
    public boolean mAnimatingScreenOff;
    public boolean mAodShowing;
    public AudioManager mAudioManager;
    public boolean mBootCompleted;
    public boolean mBootSendUserPresent;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final BroadcastReceiver mBroadcastReceiver;
    public CentralSurfaces mCentralSurfaces;
    public final Context mContext;
    public CharSequence mCustomMessage;
    public final BroadcastReceiver mDelayedLockBroadcastReceiver;
    public int mDelayedProfileShowingSequence;
    public int mDelayedShowingSequence;
    public DeviceConfigProxy mDeviceConfig;
    public boolean mDeviceInteractive;
    public final DismissCallbackRegistry mDismissCallbackRegistry;
    public DozeParameters mDozeParameters;
    public boolean mDozing;
    public final int mDreamCloseAnimationDuration;
    public final int mDreamOpenAnimationDuration;
    public boolean mDreamOverlayShowing;
    public final DreamOverlayStateController.Callback mDreamOverlayStateCallback;
    public final DreamOverlayStateController mDreamOverlayStateController;
    public final FalsingCollector mFalsingCollector;
    public boolean mGoingToSleep;
    public Handler mHandler;
    public Animation mHideAnimation;
    public final Runnable mHideAnimationFinishedRunnable;
    public boolean mHiding;
    public boolean mInGestureNavigationMode;
    public boolean mInputRestricted;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final KeyguardDisplayManager mKeyguardDisplayManager;
    public IRemoteAnimationRunner mKeyguardExitAnimationRunner;
    public final Runnable mKeyguardGoingAwayRunnable;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardStateController.Callback mKeyguardStateControllerCallback;
    public final Lazy<KeyguardUnlockAnimationController> mKeyguardUnlockAnimationControllerLazy;
    public final Lazy<KeyguardViewController> mKeyguardViewControllerLazy;
    public boolean mLockLater;
    public final LockPatternUtils mLockPatternUtils;
    public int mLockSoundId;
    public int mLockSoundStreamId;
    public float mLockSoundVolume;
    public SoundPool mLockSounds;
    public final Lazy<NotificationShadeDepthController> mNotificationShadeDepthController;
    public final Lazy<NotificationShadeWindowController> mNotificationShadeWindowControllerLazy;
    public final ActivityLaunchAnimator.Controller mOccludeAnimationController;
    public IRemoteAnimationRunner mOccludeAnimationRunner;
    public final IRemoteAnimationRunner mOccludeByDreamAnimationRunner;
    public final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;
    public final PowerManager mPM;
    public boolean mPendingLock;
    public boolean mPendingReset;
    public final float mPowerButtonY;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public ScreenOnCoordinator mScreenOnCoordinator;
    public Lazy<ScrimController> mScrimControllerLazy;
    public final Lazy<ShadeController> mShadeController;
    public boolean mShowHomeOverLockscreen;
    public PowerManager.WakeLock mShowKeyguardWakeLock;
    public boolean mShowing;
    public boolean mShuttingDown;
    public StatusBarManager mStatusBarManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    public IRemoteAnimationFinishedCallback mSurfaceBehindRemoteAnimationFinishedCallback;
    public boolean mSurfaceBehindRemoteAnimationRunning;
    public boolean mSystemReady;
    public final TrustManager mTrustManager;
    public int mTrustedSoundId;
    public final Executor mUiBgExecutor;
    public int mUiSoundsStreamType;
    public int mUnlockSoundId;
    public final IRemoteAnimationRunner mUnoccludeAnimationRunner;
    public KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final UserSwitcherController mUserSwitcherController;
    public final UserTracker mUserTracker;
    public ViewMediatorCallback mViewMediatorCallback;
    public boolean mWallpaperSupportsAmbientMode;
    public final float mWindowCornerRadius;
    public WorkLockActivityController mWorkLockController;
    public boolean mExternallyEnabled = true;
    public boolean mNeedToReshowWhenReenabled = false;
    public boolean mOccluded = false;
    public boolean mOccludeAnimationPlaying = false;
    public boolean mWakeAndUnlocking = false;
    public final SparseIntArray mLastSimStates = new SparseIntArray();
    public final SparseBooleanArray mSimWasLocked = new SparseBooleanArray();
    public String mPhoneState = TelephonyManager.EXTRA_STATE_IDLE;
    public boolean mWaitingUntilKeyguardVisible = false;
    public boolean mKeyguardDonePending = false;
    public boolean mHideAnimationRun = false;
    public boolean mHideAnimationRunning = false;
    public final ArrayList<IKeyguardStateCallback> mKeyguardStateCallbacks = new ArrayList<>();
    public boolean mPendingPinLock = false;
    public boolean mPowerGestureIntercepted = false;
    public boolean mSurfaceBehindRemoteAnimationRequested = false;

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$11 */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$11.class */
    public class AnonymousClass11 extends Handler {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$11$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$Yd4f3XPF1dV3k-33xTj1jpVNUrg */
        public static /* synthetic */ void m2901$r8$lambda$Yd4f3XPF1dV3k33xTj1jpVNUrg(AnonymousClass11 anonymousClass11, StartKeyguardExitAnimParams startKeyguardExitAnimParams) {
            anonymousClass11.lambda$handleMessage$0(startKeyguardExitAnimParams);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass11(Looper looper, Handler.Callback callback, boolean z) {
            super(looper, callback, z);
            KeyguardViewMediator.this = r6;
        }

        public /* synthetic */ void lambda$handleMessage$0(StartKeyguardExitAnimParams startKeyguardExitAnimParams) {
            KeyguardViewMediator.this.handleStartKeyguardExitAnimation(startKeyguardExitAnimParams.startTime, startKeyguardExitAnimParams.fadeoutDuration, startKeyguardExitAnimParams.mApps, startKeyguardExitAnimParams.mWallpapers, startKeyguardExitAnimParams.mNonApps, startKeyguardExitAnimParams.mFinishedCallback);
            KeyguardViewMediator.this.mFalsingCollector.onSuccessfulUnlock();
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z = true;
            switch (message.what) {
                case 1:
                    KeyguardViewMediator.this.handleShow((Bundle) message.obj);
                    return;
                case 2:
                    KeyguardViewMediator.this.handleHide();
                    return;
                case 3:
                    KeyguardViewMediator.this.handleReset();
                    return;
                case 4:
                    Trace.beginSection("KeyguardViewMediator#handleMessage VERIFY_UNLOCK");
                    KeyguardViewMediator.this.handleVerifyUnlock();
                    Trace.endSection();
                    return;
                case 5:
                    KeyguardViewMediator.this.handleNotifyFinishedGoingToSleep();
                    return;
                case 6:
                case 15:
                case 16:
                default:
                    return;
                case 7:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE");
                    KeyguardViewMediator.this.handleKeyguardDone();
                    Trace.endSection();
                    return;
                case 8:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_DRAWING");
                    KeyguardViewMediator.this.handleKeyguardDoneDrawing();
                    Trace.endSection();
                    return;
                case 9:
                    Trace.beginSection("KeyguardViewMediator#handleMessage SET_OCCLUDED");
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    boolean z2 = message.arg1 != 0;
                    if (message.arg2 == 0) {
                        z = false;
                    }
                    keyguardViewMediator.handleSetOccluded(z2, z);
                    Trace.endSection();
                    return;
                case 10:
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.doKeyguardLocked((Bundle) message.obj);
                    }
                    return;
                case 11:
                    DismissMessage dismissMessage = (DismissMessage) message.obj;
                    KeyguardViewMediator.this.handleDismiss(dismissMessage.getCallback(), dismissMessage.getMessage());
                    return;
                case 12:
                    Trace.beginSection("KeyguardViewMediator#handleMessage START_KEYGUARD_EXIT_ANIM");
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mHiding = true;
                    }
                    final StartKeyguardExitAnimParams startKeyguardExitAnimParams = (StartKeyguardExitAnimParams) message.obj;
                    ((NotificationShadeWindowController) KeyguardViewMediator.this.mNotificationShadeWindowControllerLazy.get()).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$11$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediator.AnonymousClass11.m2901$r8$lambda$Yd4f3XPF1dV3k33xTj1jpVNUrg(KeyguardViewMediator.AnonymousClass11.this, startKeyguardExitAnimParams);
                        }
                    });
                    Trace.endSection();
                    return;
                case 13:
                    Trace.beginSection("KeyguardViewMediator#handleMessage KEYGUARD_DONE_PENDING_TIMEOUT");
                    Log.w("KeyguardViewMediator", "Timeout while waiting for activity drawn!");
                    Trace.endSection();
                    return;
                case 14:
                    Trace.beginSection("KeyguardViewMediator#handleMessage NOTIFY_STARTED_WAKING_UP");
                    KeyguardViewMediator.this.handleNotifyStartedWakingUp();
                    Trace.endSection();
                    return;
                case 17:
                    KeyguardViewMediator.this.handleNotifyStartedGoingToSleep();
                    return;
                case 18:
                    KeyguardViewMediator.this.handleSystemReady();
                    return;
                case 19:
                    Trace.beginSection("KeyguardViewMediator#handleMessage CANCEL_KEYGUARD_EXIT_ANIM");
                    KeyguardViewMediator.this.handleCancelKeyguardExitAnimation();
                    Trace.endSection();
                    return;
            }
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$12 */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$12.class */
    public class AnonymousClass12 implements Runnable {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$12$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$dWBR6zPwftY_o20UjuguVUWhcQ4(int i) {
            lambda$run$0(i);
        }

        public AnonymousClass12() {
            KeyguardViewMediator.this = r4;
        }

        public static /* synthetic */ void lambda$run$0(int i) {
            try {
                ActivityTaskManager.getService().keyguardGoingAway(i);
            } catch (RemoteException e) {
                Log.e("KeyguardViewMediator", "Error while calling WindowManager", e);
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:42:0x0055, code lost:
            if (com.android.systemui.keyguard.KeyguardViewMediator.this.mWallpaperSupportsAmbientMode == false) goto L31;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0086, code lost:
            if (com.android.systemui.keyguard.KeyguardViewMediator.this.mWallpaperSupportsAmbientMode != false) goto L30;
         */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0071  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x00a6  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x00c3  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x00d4  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void run() {
            int i;
            int i2;
            Trace.beginSection("KeyguardViewMediator.mKeyGuardGoingAwayRunnable");
            if (KeyguardViewMediator.DEBUG) {
                Log.d("KeyguardViewMediator", "keyguardGoingAway");
            }
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).keyguardGoingAway();
            if (!((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldDisableWindowAnimationsForUnlock()) {
                i = 0;
                if (KeyguardViewMediator.this.mWakeAndUnlocking) {
                    i = 0;
                }
                if (!((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isGoingToNotificationShade()) {
                    i2 = i;
                    if (KeyguardViewMediator.this.mWakeAndUnlocking) {
                        i2 = i;
                    }
                    int i3 = i2;
                    if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
                        i3 = i2 | 4;
                    }
                    int i4 = i3;
                    if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock()) {
                        i4 = i3 | 8;
                    }
                    int i5 = i4;
                    if (KeyguardViewMediator.this.mWakeAndUnlocking) {
                        i5 = i4;
                        if (KeyguardUnlockAnimationController.Companion.isNexusLauncherUnderneath()) {
                            i5 = i4 | 16;
                        }
                    }
                    KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
                    ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
                    final int i6 = i5;
                    KeyguardViewMediator.this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$12$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            KeyguardViewMediator.AnonymousClass12.$r8$lambda$dWBR6zPwftY_o20UjuguVUWhcQ4(i6);
                        }
                    });
                    Trace.endSection();
                }
                i2 = i | 1;
                int i32 = i2;
                if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
                }
                int i42 = i32;
                if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock()) {
                }
                int i52 = i42;
                if (KeyguardViewMediator.this.mWakeAndUnlocking) {
                }
                KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
                final int i62 = i52;
                KeyguardViewMediator.this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$12$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.AnonymousClass12.$r8$lambda$dWBR6zPwftY_o20UjuguVUWhcQ4(i62);
                    }
                });
                Trace.endSection();
            }
            i = 2;
            if (!((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isGoingToNotificationShade()) {
            }
            i2 = i | 1;
            int i322 = i2;
            if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).isUnlockWithWallpaper()) {
            }
            int i422 = i322;
            if (((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).shouldSubtleWindowAnimationsForUnlock()) {
            }
            int i522 = i422;
            if (KeyguardViewMediator.this.mWakeAndUnlocking) {
            }
            KeyguardViewMediator.this.mUpdateMonitor.setKeyguardGoingAway(true);
            ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(true);
            final int i622 = i522;
            KeyguardViewMediator.this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$12$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.AnonymousClass12.$r8$lambda$dWBR6zPwftY_o20UjuguVUWhcQ4(i622);
                }
            });
            Trace.endSection();
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$6 */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$6.class */
    public class AnonymousClass6 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mOccludeByDreamAnimator;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$6$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$66QB0YEndI_kAmF6DHUwlKcb_0k(AnonymousClass6 anonymousClass6, RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            anonymousClass6.lambda$onAnimationStart$1(remoteAnimationTarget, syncRtSurfaceTransactionApplier, iRemoteAnimationFinishedCallback);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$6$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
        public static /* synthetic */ void $r8$lambda$YarLxgPqY8jhjXmRopvNUkvcxWU(RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
            lambda$onAnimationStart$0(remoteAnimationTarget, syncRtSurfaceTransactionApplier, valueAnimator);
        }

        public AnonymousClass6() {
            KeyguardViewMediator.this = r4;
        }

        public static /* synthetic */ void lambda$onAnimationStart$0(RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
            syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
        }

        public /* synthetic */ void lambda$onAnimationStart$1(final RemoteAnimationTarget remoteAnimationTarget, final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ValueAnimator valueAnimator = this.mOccludeByDreamAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            this.mOccludeByDreamAnimator = ofFloat;
            ofFloat.setDuration(KeyguardViewMediator.this.mDreamOpenAnimationDuration);
            this.mOccludeByDreamAnimator.setInterpolator(Interpolators.LINEAR);
            this.mOccludeByDreamAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$6$$ExternalSyntheticLambda1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    KeyguardViewMediator.AnonymousClass6.$r8$lambda$YarLxgPqY8jhjXmRopvNUkvcxWU(remoteAnimationTarget, syncRtSurfaceTransactionApplier, valueAnimator2);
                }
            });
            this.mOccludeByDreamAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.6.1
                {
                    AnonymousClass6.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        AnonymousClass6.this.mOccludeByDreamAnimator = null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.mOccludeByDreamAnimator.start();
        }

        public void onAnimationCancelled(boolean z) {
            ValueAnimator valueAnimator = this.mOccludeByDreamAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            KeyguardViewMediator.this.setOccluded(z, false);
            if (KeyguardViewMediator.DEBUG) {
                Log.d("KeyguardViewMediator", "Occlude by Dream animation cancelled. Occluded state is now: " + KeyguardViewMediator.this.mOccluded);
            }
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            final RemoteAnimationTarget remoteAnimationTarget;
            boolean z = true;
            KeyguardViewMediator.this.setOccluded(true, true);
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "No apps provided to the OccludeByDream runner; skipping occluding animation.");
                }
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            if (remoteAnimationTarget.taskInfo.topActivityType != 5) {
                z = false;
            }
            if (z) {
                final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
                KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$6$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.AnonymousClass6.$r8$lambda$66QB0YEndI_kAmF6DHUwlKcb_0k(KeyguardViewMediator.AnonymousClass6.this, remoteAnimationTarget, syncRtSurfaceTransactionApplier, iRemoteAnimationFinishedCallback);
                    }
                });
                return;
            }
            Log.w("KeyguardViewMediator", "The occluding app isn't Dream; finishing up. Please check that the config is correct.");
            iRemoteAnimationFinishedCallback.onAnimationFinished();
        }
    }

    /* renamed from: com.android.systemui.keyguard.KeyguardViewMediator$7 */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$7.class */
    public class AnonymousClass7 extends IRemoteAnimationRunner.Stub {
        public ValueAnimator mUnoccludeAnimator;
        public final Matrix mUnoccludeMatrix = new Matrix();

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$ELE15Lnt69RzNjvFu_EMlF240y0(AnonymousClass7 anonymousClass7) {
            anonymousClass7.lambda$onAnimationCancelled$0();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$LqHnMN0hnWNz-M1-fF1CLSvbJPM */
        public static /* synthetic */ void m2903$r8$lambda$LqHnMN0hnWNzM1fF1CLSvbJPM(AnonymousClass7 anonymousClass7, boolean z, RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            anonymousClass7.lambda$onAnimationStart$2(z, remoteAnimationTarget, syncRtSurfaceTransactionApplier, iRemoteAnimationFinishedCallback);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
        public static /* synthetic */ void $r8$lambda$do6qGEsP9x7vInhlVUVEWPf7nyk(AnonymousClass7 anonymousClass7, RemoteAnimationTarget remoteAnimationTarget, boolean z, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
            anonymousClass7.lambda$onAnimationStart$1(remoteAnimationTarget, z, syncRtSurfaceTransactionApplier, valueAnimator);
        }

        public AnonymousClass7() {
            KeyguardViewMediator.this = r5;
        }

        public /* synthetic */ void lambda$onAnimationCancelled$0() {
            ValueAnimator valueAnimator = this.mUnoccludeAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
        }

        public /* synthetic */ void lambda$onAnimationStart$1(RemoteAnimationTarget remoteAnimationTarget, boolean z, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            float height = remoteAnimationTarget.screenSpaceBounds.height();
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder withAlpha = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(floatValue);
            if (!z) {
                this.mUnoccludeMatrix.setTranslate(ActionBarShadowController.ELEVATION_LOW, (1.0f - floatValue) * height * 0.1f);
                withAlpha.withMatrix(this.mUnoccludeMatrix).withCornerRadius(KeyguardViewMediator.this.mWindowCornerRadius);
            }
            syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{withAlpha.build()});
        }

        public /* synthetic */ void lambda$onAnimationStart$2(final boolean z, final RemoteAnimationTarget remoteAnimationTarget, final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            ValueAnimator valueAnimator = this.mUnoccludeAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
            this.mUnoccludeAnimator = ofFloat;
            ofFloat.setDuration(z ? KeyguardViewMediator.this.mDreamCloseAnimationDuration : 250L);
            this.mUnoccludeAnimator.setInterpolator(Interpolators.TOUCH_RESPONSE);
            this.mUnoccludeAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda2
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    KeyguardViewMediator.AnonymousClass7.$r8$lambda$do6qGEsP9x7vInhlVUVEWPf7nyk(KeyguardViewMediator.AnonymousClass7.this, remoteAnimationTarget, z, syncRtSurfaceTransactionApplier, valueAnimator2);
                }
            });
            this.mUnoccludeAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.7.1
                {
                    AnonymousClass7.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        AnonymousClass7.this.mUnoccludeAnimator = null;
                        KeyguardViewMediator.this.mInteractionJankMonitor.end(64);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            });
            this.mUnoccludeAnimator.start();
        }

        public void onAnimationCancelled(boolean z) {
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.AnonymousClass7.$r8$lambda$ELE15Lnt69RzNjvFu_EMlF240y0(KeyguardViewMediator.AnonymousClass7.this);
                }
            });
            KeyguardViewMediator.this.setOccluded(z, false);
            Log.d("KeyguardViewMediator", "Unocclude animation cancelled. Occluded state is now: " + KeyguardViewMediator.this.mOccluded);
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            final RemoteAnimationTarget remoteAnimationTarget;
            Log.d("KeyguardViewMediator", "UnoccludeAnimator#onAnimationStart. Set occluded = false.");
            KeyguardViewMediator.this.mInteractionJankMonitor.begin(KeyguardViewMediator.this.createInteractionJankMonitorConf(64).setTag("UNOCCLUDE"));
            KeyguardViewMediator.this.setOccluded(false, true);
            if (remoteAnimationTargetArr == null || remoteAnimationTargetArr.length == 0 || (remoteAnimationTarget = remoteAnimationTargetArr[0]) == null) {
                Log.d("KeyguardViewMediator", "No apps provided to unocclude runner; skipping animation and unoccluding.");
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                return;
            }
            boolean z = remoteAnimationTarget.taskInfo.topActivityType == 5;
            final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
            final boolean z2 = z;
            KeyguardViewMediator.this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$7$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.AnonymousClass7.m2903$r8$lambda$LqHnMN0hnWNzM1fF1CLSvbJPM(KeyguardViewMediator.AnonymousClass7.this, z2, remoteAnimationTarget, syncRtSurfaceTransactionApplier, iRemoteAnimationFinishedCallback);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$ActivityLaunchRemoteAnimationRunner.class */
    public class ActivityLaunchRemoteAnimationRunner extends IRemoteAnimationRunner.Stub {
        public final ActivityLaunchAnimator.Controller mActivityLaunchController;
        public ActivityLaunchAnimator.Runner mRunner;

        public ActivityLaunchRemoteAnimationRunner(ActivityLaunchAnimator.Controller controller) {
            KeyguardViewMediator.this = r4;
            this.mActivityLaunchController = controller;
        }

        public void onAnimationCancelled(boolean z) throws RemoteException {
            ActivityLaunchAnimator.Runner runner = this.mRunner;
            if (runner != null) {
                runner.onAnimationCancelled(z);
            }
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            ActivityLaunchAnimator.Runner createRunner = ((ActivityLaunchAnimator) KeyguardViewMediator.this.mActivityLaunchAnimator.get()).createRunner(this.mActivityLaunchController);
            this.mRunner = createRunner;
            createRunner.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$DismissMessage.class */
    public static class DismissMessage {
        public final IKeyguardDismissCallback mCallback;
        public final CharSequence mMessage;

        public DismissMessage(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            this.mCallback = iKeyguardDismissCallback;
            this.mMessage = charSequence;
        }

        public IKeyguardDismissCallback getCallback() {
            return this.mCallback;
        }

        public CharSequence getMessage() {
            return this.mMessage;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$OccludeActivityLaunchRemoteAnimationRunner.class */
    public class OccludeActivityLaunchRemoteAnimationRunner extends ActivityLaunchRemoteAnimationRunner {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public OccludeActivityLaunchRemoteAnimationRunner(ActivityLaunchAnimator.Controller controller) {
            super(controller);
            KeyguardViewMediator.this = r5;
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public void onAnimationCancelled(boolean z) throws RemoteException {
            super.onAnimationCancelled(z);
            Log.d("KeyguardViewMediator", "Occlude animation cancelled by WM. Setting occluded state to: " + z);
            KeyguardViewMediator.this.setOccluded(z, false);
            KeyguardViewMediator.this.mInteractionJankMonitor.cancel(64);
        }

        @Override // com.android.systemui.keyguard.KeyguardViewMediator.ActivityLaunchRemoteAnimationRunner
        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) throws RemoteException {
            super.onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            KeyguardViewMediator.this.mInteractionJankMonitor.begin(KeyguardViewMediator.this.createInteractionJankMonitorConf(64).setTag("OCCLUDE"));
            Log.d("KeyguardViewMediator", "OccludeAnimator#onAnimationStart. Set occluded = true.");
            KeyguardViewMediator.this.setOccluded(true, false);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardViewMediator$StartKeyguardExitAnimParams.class */
    public static class StartKeyguardExitAnimParams {
        public long fadeoutDuration;
        public RemoteAnimationTarget[] mApps;
        public IRemoteAnimationFinishedCallback mFinishedCallback;
        public RemoteAnimationTarget[] mNonApps;
        public int mTransit;
        public RemoteAnimationTarget[] mWallpapers;
        public long startTime;

        public StartKeyguardExitAnimParams(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            this.mTransit = i;
            this.startTime = j;
            this.fadeoutDuration = j2;
            this.mApps = remoteAnimationTargetArr;
            this.mWallpapers = remoteAnimationTargetArr2;
            this.mNonApps = remoteAnimationTargetArr3;
            this.mFinishedCallback = iRemoteAnimationFinishedCallback;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$00ehQOGK1Zp73BYM6Gt8XLoD13I(KeyguardViewMediator keyguardViewMediator, UserManager userManager, UserHandle userHandle, int i) {
        keyguardViewMediator.lambda$sendUserPresentBroadcast$2(userManager, userHandle, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$8rbPocBXr1kDKdp39FBeeFeFtwA(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.lambda$notifyDefaultDisplayCallbacks$11();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$C37rD581uNVPa0zKJYfOSlu7Z04(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.lambda$handleHide$6();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8.run():void] */
    public static /* synthetic */ void $r8$lambda$C_N2pvBHAIgJo3TaffLVl12notg(KeyguardViewMediator keyguardViewMediator, int i) {
        keyguardViewMediator.lambda$handleKeyguardDone$1(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9.run():void] */
    public static /* synthetic */ void $r8$lambda$Gum5Nk10mw3bohg7j38n2wL1aNs(KeyguardViewMediator keyguardViewMediator, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget[] remoteAnimationTargetArr) {
        keyguardViewMediator.lambda$handleStartKeyguardExitAnimation$8(iRemoteAnimationFinishedCallback, remoteAnimationTargetArr);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$J89F0gCaS9j1Hd4XK_A97waFiGo(RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
        lambda$handleStartKeyguardExitAnimation$7(remoteAnimationTarget, syncRtSurfaceTransactionApplier, valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$Q7K5bOIr2sDkGRLR4bqI44oo7Po(KeyguardViewMediator keyguardViewMediator, boolean z) {
        keyguardViewMediator.lambda$notifyDefaultDisplayCallbacks$10(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$ShDDxDpq7_z9hv9ydzmNxHC9Hes(boolean z, boolean z2) {
        lambda$updateActivityLockScreenState$4(z, z2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$UUYKe9T2h39WDF0SXTBkvczZjVw(KeyguardViewMediator keyguardViewMediator) {
        keyguardViewMediator.lambda$new$5();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$XTX2YbVzvNCzWLSm3rviYi4HtXU(KeyguardViewMediator keyguardViewMediator, boolean z, boolean z2) {
        keyguardViewMediator.lambda$exitKeyguardAndFinishSurfaceBehindRemoteAnimation$9(z, z2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda10.run():void] */
    /* renamed from: $r8$lambda$wva4u_JAP64VxYRHqTBvC-WvPig */
    public static /* synthetic */ void m2827$r8$lambda$wva4u_JAP64VxYRHqTBvCWvPig(KeyguardViewMediator keyguardViewMediator, int i) {
        keyguardViewMediator.lambda$playSound$3(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda2.onNavigationModeChanged(int):void] */
    /* renamed from: $r8$lambda$x-egRl58y870AMJ93TsCOlur3Oc */
    public static /* synthetic */ void m2828$r8$lambda$xegRl58y870AMJ93TsCOlur3Oc(KeyguardViewMediator keyguardViewMediator, int i) {
        keyguardViewMediator.lambda$new$0(i);
    }

    public KeyguardViewMediator(Context context, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy<KeyguardViewController> lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, Executor executor, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy<KeyguardUnlockAnimationController> lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy<NotificationShadeDepthController> lazy3, ScreenOnCoordinator screenOnCoordinator, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy<ShadeController> lazy4, Lazy<NotificationShadeWindowController> lazy5, Lazy<ActivityLaunchAnimator> lazy6, Lazy<ScrimController> lazy7) {
        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.1
            {
                KeyguardViewMediator.this = this;
            }

            public void onPropertiesChanged(DeviceConfig.Properties properties) {
                if (properties.getKeyset().contains("nav_bar_handle_show_over_lockscreen")) {
                    KeyguardViewMediator.this.mShowHomeOverLockscreen = properties.getBoolean("nav_bar_handle_show_over_lockscreen", true);
                }
            }
        };
        this.mOnPropertiesChangedListener = onPropertiesChangedListener;
        this.mDreamOverlayStateCallback = new DreamOverlayStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.2
            {
                KeyguardViewMediator.this = this;
            }

            @Override // com.android.systemui.dreams.DreamOverlayStateController.Callback
            public void onStateChanged() {
                KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                keyguardViewMediator.mDreamOverlayShowing = keyguardViewMediator.mDreamOverlayStateController.isOverlayActive();
            }
        };
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.3
            {
                KeyguardViewMediator.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(currentUser)) {
                    KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportFailedBiometricAttempt(currentUser);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (KeyguardViewMediator.this.mLockPatternUtils.isSecure(i)) {
                    KeyguardViewMediator.this.mLockPatternUtils.getDevicePolicyManager().reportSuccessfulBiometricAttempt(i);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onDeviceProvisioned() {
                KeyguardViewMediator.this.sendUserPresentBroadcast();
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.mustNotUnlockCurrentUser()) {
                        KeyguardViewMediator.this.doKeyguardLocked(null);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean z) {
                synchronized (KeyguardViewMediator.this) {
                    if (!z) {
                        if (KeyguardViewMediator.this.mPendingPinLock) {
                            Log.i("KeyguardViewMediator", "PIN lock requested, starting keyguard");
                            KeyguardViewMediator.this.mPendingPinLock = false;
                            KeyguardViewMediator.this.doKeyguardLocked(null);
                        }
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                boolean z;
                Log.d("KeyguardViewMediator", "onSimStateChanged(subId=" + i + ", slotId=" + i2 + ",state=" + i3 + ")");
                int size = KeyguardViewMediator.this.mKeyguardStateCallbacks.size();
                boolean isSimPinSecure = KeyguardViewMediator.this.mUpdateMonitor.isSimPinSecure();
                while (true) {
                    size--;
                    if (size < 0) {
                        break;
                    }
                    try {
                        ((IKeyguardStateCallback) KeyguardViewMediator.this.mKeyguardStateCallbacks.get(size)).onSimSecureStateChanged(isSimPinSecure);
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onSimSecureStateChanged", e);
                        if (e instanceof DeadObjectException) {
                            KeyguardViewMediator.this.mKeyguardStateCallbacks.remove(size);
                        }
                    }
                }
                synchronized (KeyguardViewMediator.this) {
                    int i4 = KeyguardViewMediator.this.mLastSimStates.get(i2);
                    z = i4 == 2 || i4 == 3;
                    KeyguardViewMediator.this.mLastSimStates.append(i2, i3);
                }
                if (i3 != 1) {
                    if (i3 == 2 || i3 == 3) {
                        synchronized (KeyguardViewMediator.this) {
                            KeyguardViewMediator.this.mSimWasLocked.append(i2, true);
                            KeyguardViewMediator.this.mPendingPinLock = true;
                            if (KeyguardViewMediator.this.mShowing) {
                                KeyguardViewMediator.this.resetStateLocked();
                            } else {
                                Log.d("KeyguardViewMediator", "INTENT_VALUE_ICC_LOCKED and keygaurd isn't showing; need to show keyguard so user can enter sim pin");
                                KeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        }
                        return;
                    } else if (i3 == 5) {
                        synchronized (KeyguardViewMediator.this) {
                            Log.d("KeyguardViewMediator", "READY, reset state? " + KeyguardViewMediator.this.mShowing);
                            if (KeyguardViewMediator.this.mShowing && KeyguardViewMediator.this.mSimWasLocked.get(i2, false)) {
                                Log.d("KeyguardViewMediator", "SIM moved to READY when the previously was locked. Reset the state.");
                                KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                                KeyguardViewMediator.this.resetStateLocked();
                            }
                        }
                        return;
                    } else if (i3 != 6) {
                        if (i3 != 7) {
                            Log.v("KeyguardViewMediator", "Unspecific state: " + i3);
                            return;
                        }
                        synchronized (KeyguardViewMediator.this) {
                            if (KeyguardViewMediator.this.mShowing) {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED, resetStateLocked toshow permanently disabled message in lockscreen.");
                                KeyguardViewMediator.this.resetStateLocked();
                            } else {
                                Log.d("KeyguardViewMediator", "PERM_DISABLED and keygaurd isn't showing.");
                                KeyguardViewMediator.this.doKeyguardLocked(null);
                            }
                        }
                        return;
                    }
                }
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.shouldWaitForProvisioning()) {
                        if (KeyguardViewMediator.this.mShowing) {
                            KeyguardViewMediator.this.resetStateLocked();
                        } else {
                            Log.d("KeyguardViewMediator", "ICC_ABSENT isn't showing, we need to show the keyguard since the device isn't provisioned yet.");
                            KeyguardViewMediator.this.doKeyguardLocked(null);
                        }
                    }
                    if (i3 == 1) {
                        if (z) {
                            Log.d("KeyguardViewMediator", "SIM moved to ABSENT when the previous state was locked. Reset the state.");
                            KeyguardViewMediator.this.resetStateLocked();
                        }
                        KeyguardViewMediator.this.mSimWasLocked.append(i2, false);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onTrustChanged(int i) {
                if (i == KeyguardUpdateMonitor.getCurrentUser()) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                        keyguardViewMediator.notifyTrustedChangedLocked(keyguardViewMediator.mUpdateMonitor.getUserHasTrust(i));
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitchComplete(int i) {
                UserInfo userInfo;
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", String.format("onUserSwitchComplete %d", Integer.valueOf(i)));
                }
                if (i == 0 || (userInfo = UserManager.get(KeyguardViewMediator.this.mContext).getUserInfo(i)) == null || KeyguardViewMediator.this.mLockPatternUtils.isSecure(i)) {
                    return;
                }
                if (userInfo.isGuest() || userInfo.isDemo()) {
                    KeyguardViewMediator.this.dismiss(null, null);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitching(int i) {
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", String.format("onUserSwitching %d", Integer.valueOf(i)));
                }
                synchronized (KeyguardViewMediator.this) {
                    KeyguardViewMediator.this.resetKeyguardDonePendingLocked();
                    if (KeyguardViewMediator.this.mLockPatternUtils.isLockScreenDisabled(i)) {
                        KeyguardViewMediator.this.dismiss(null, null);
                    } else {
                        KeyguardViewMediator.this.resetStateLocked();
                    }
                    KeyguardViewMediator.this.adjustStatusBarLocked();
                }
            }
        };
        this.mViewMediatorCallback = new ViewMediatorCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.4
            {
                KeyguardViewMediator.this = this;
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public CharSequence consumeCustomMessage() {
                CharSequence charSequence = KeyguardViewMediator.this.mCustomMessage;
                KeyguardViewMediator.this.mCustomMessage = null;
                return charSequence;
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public int getBouncerPromptReason() {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                boolean isTrustUsuallyManaged = KeyguardViewMediator.this.mUpdateMonitor.isTrustUsuallyManaged(currentUser);
                boolean z = isTrustUsuallyManaged || KeyguardViewMediator.this.mUpdateMonitor.isUnlockingWithBiometricsPossible(currentUser);
                KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = KeyguardViewMediator.this.mUpdateMonitor.getStrongAuthTracker();
                int strongAuthForUser = strongAuthTracker.getStrongAuthForUser(currentUser);
                boolean isNonStrongBiometricAllowedAfterIdleTimeout = strongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(currentUser);
                if (!z || strongAuthTracker.hasUserAuthenticatedSinceBoot()) {
                    if (!z || (strongAuthForUser & 16) == 0) {
                        if ((strongAuthForUser & 2) != 0) {
                            return 3;
                        }
                        if (!isTrustUsuallyManaged || (strongAuthForUser & 4) == 0) {
                            if (!isTrustUsuallyManaged || (strongAuthForUser & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) == 0) {
                                if (!z || ((strongAuthForUser & 8) == 0 && !KeyguardViewMediator.this.mUpdateMonitor.isFingerprintLockedOut())) {
                                    if (!z || (strongAuthForUser & 64) == 0) {
                                        if (!z || (strongAuthForUser & RecyclerView.ViewHolder.FLAG_IGNORE) == 0) {
                                            return (!z || isNonStrongBiometricAllowedAfterIdleTimeout) ? 0 : 7;
                                        }
                                        return 7;
                                    }
                                    return 6;
                                }
                                return 5;
                            }
                            return 8;
                        }
                        return 4;
                    }
                    return 2;
                }
                return 1;
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void keyguardDone(boolean z, int i) {
                if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                    return;
                }
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "keyguardDone");
                }
                KeyguardViewMediator.this.tryKeyguardDone();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void keyguardDoneDrawing() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDoneDrawing");
                KeyguardViewMediator.this.mHandler.sendEmptyMessage(8);
                Trace.endSection();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void keyguardDonePending(boolean z, int i) {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardDonePending");
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "keyguardDonePending");
                }
                if (i != KeyguardUpdateMonitor.getCurrentUser()) {
                    Trace.endSection();
                    return;
                }
                KeyguardViewMediator.this.mKeyguardDonePending = true;
                KeyguardViewMediator.this.mHideAnimationRun = true;
                KeyguardViewMediator.this.mHideAnimationRunning = true;
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).startPreHideAnimation(KeyguardViewMediator.this.mHideAnimationFinishedRunnable);
                KeyguardViewMediator.this.mHandler.sendEmptyMessageDelayed(13, 3000L);
                Trace.endSection();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void keyguardGone() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#keyguardGone");
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "keyguardGone");
                }
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
                KeyguardViewMediator.this.mKeyguardDisplayManager.hide();
                Trace.endSection();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void onCancelClicked() {
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).onCancelClicked();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void playTrustedSound() {
                KeyguardViewMediator.this.playTrustedSound();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void readyForKeyguardDone() {
                Trace.beginSection("KeyguardViewMediator.mViewMediatorCallback#readyForKeyguardDone");
                if (KeyguardViewMediator.this.mKeyguardDonePending) {
                    KeyguardViewMediator.this.mKeyguardDonePending = false;
                    KeyguardViewMediator.this.tryKeyguardDone();
                }
                Trace.endSection();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void resetKeyguard() {
                KeyguardViewMediator.this.resetStateLocked();
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void setNeedsInput(boolean z) {
                ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).setNeedsInput(z);
            }

            @Override // com.android.keyguard.ViewMediatorCallback
            public void userActivity() {
                KeyguardViewMediator.this.userActivity();
            }
        };
        ActivityLaunchAnimator.Controller controller = new ActivityLaunchAnimator.Controller() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.5
            {
                KeyguardViewMediator.this = this;
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public LaunchAnimator.State createAnimatorState() {
                int width = getLaunchContainer().getWidth();
                int height = getLaunchContainer().getHeight();
                if (KeyguardViewMediator.this.mUpdateMonitor.isSecureCameraLaunchedOverKeyguard()) {
                    float f = height / 3.0f;
                    float f2 = width;
                    float f3 = f2 / 3.0f;
                    float f4 = f / 2.0f;
                    return new LaunchAnimator.State((int) (KeyguardViewMediator.this.mPowerButtonY - f4), (int) (KeyguardViewMediator.this.mPowerButtonY + f4), (int) (f2 - f3), width, KeyguardViewMediator.this.mWindowCornerRadius, KeyguardViewMediator.this.mWindowCornerRadius);
                }
                float f5 = height;
                float f6 = f5 / 2.0f;
                float f7 = width;
                float f8 = f7 / 2.0f;
                float f9 = f5 - f6;
                int i = ((int) f9) / 2;
                int i2 = (int) (f6 + (f9 / 2.0f));
                float f10 = f7 - f8;
                return new LaunchAnimator.State(i, i2, ((int) f10) / 2, (int) (f8 + (f10 / 2.0f)), KeyguardViewMediator.this.mWindowCornerRadius, KeyguardViewMediator.this.mWindowCornerRadius);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public ViewGroup getLaunchContainer() {
                return (ViewGroup) ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
            public void onLaunchAnimationCancelled(Boolean bool) {
                Log.d("KeyguardViewMediator", "Occlude launch animation cancelled. Occluded state is now: " + KeyguardViewMediator.this.mOccluded);
                KeyguardViewMediator.this.mOccludeAnimationPlaying = false;
                KeyguardViewMediator.this.mCentralSurfaces.updateIsKeyguard();
                ((ScrimController) KeyguardViewMediator.this.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationEnd(boolean z) {
                if (z) {
                    ((ShadeController) KeyguardViewMediator.this.mShadeController.get()).instantCollapseShade();
                }
                KeyguardViewMediator.this.mOccludeAnimationPlaying = false;
                KeyguardViewMediator.this.mCentralSurfaces.updateIsKeyguard();
                ((ScrimController) KeyguardViewMediator.this.mScrimControllerLazy.get()).setOccludeAnimationPlaying(false);
                KeyguardViewMediator.this.mInteractionJankMonitor.end(64);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void onLaunchAnimationStart(boolean z) {
                KeyguardViewMediator.this.mOccludeAnimationPlaying = true;
                ((ScrimController) KeyguardViewMediator.this.mScrimControllerLazy.get()).setOccludeAnimationPlaying(true);
            }

            @Override // com.android.systemui.animation.LaunchAnimator.Controller
            public void setLaunchContainer(ViewGroup viewGroup) {
                Log.wtf("KeyguardViewMediator", "Someone tried to change the launch container for the ActivityLaunchAnimator, which should never happen.");
            }
        };
        this.mOccludeAnimationController = controller;
        this.mOccludeAnimationRunner = new OccludeActivityLaunchRemoteAnimationRunner(controller);
        this.mOccludeByDreamAnimationRunner = new AnonymousClass6();
        this.mUnoccludeAnimationRunner = new AnonymousClass7();
        KeyguardStateController.Callback callback = new KeyguardStateController.Callback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.8
            {
                KeyguardViewMediator.this = this;
            }

            public void onBouncerShowingChanged() {
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.mKeyguardStateController.isBouncerShowing()) {
                        KeyguardViewMediator.this.mPendingPinLock = false;
                    }
                    KeyguardViewMediator keyguardViewMediator = KeyguardViewMediator.this;
                    keyguardViewMediator.adjustStatusBarLocked(keyguardViewMediator.mKeyguardStateController.isBouncerShowing(), false);
                }
            }
        };
        this.mKeyguardStateControllerCallback = callback;
        this.mDelayedLockBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.9
            {
                KeyguardViewMediator.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (!"com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD".equals(intent.getAction())) {
                    if ("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK".equals(intent.getAction())) {
                        int intExtra = intent.getIntExtra("seq", 0);
                        int intExtra2 = intent.getIntExtra("android.intent.extra.USER_ID", 0);
                        if (intExtra2 != 0) {
                            synchronized (KeyguardViewMediator.this) {
                                if (KeyguardViewMediator.this.mDelayedProfileShowingSequence == intExtra) {
                                    KeyguardViewMediator.this.lockProfile(intExtra2);
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                }
                int intExtra3 = intent.getIntExtra("seq", 0);
                if (KeyguardViewMediator.DEBUG) {
                    Log.d("KeyguardViewMediator", "received DELAYED_KEYGUARD_ACTION with seq = " + intExtra3 + ", mDelayedShowingSequence = " + KeyguardViewMediator.this.mDelayedShowingSequence);
                }
                synchronized (KeyguardViewMediator.this) {
                    if (KeyguardViewMediator.this.mDelayedShowingSequence == intExtra3) {
                        KeyguardViewMediator.this.doKeyguardLocked(null);
                    }
                }
            }
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.10
            {
                KeyguardViewMediator.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if ("android.intent.action.ACTION_SHUTDOWN".equals(intent.getAction())) {
                    synchronized (KeyguardViewMediator.this) {
                        KeyguardViewMediator.this.mShuttingDown = true;
                    }
                }
            }
        };
        this.mHandler = new AnonymousClass11(Looper.myLooper(), null, true);
        this.mKeyguardGoingAwayRunnable = new AnonymousClass12();
        this.mHideAnimationFinishedRunnable = new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.$r8$lambda$UUYKe9T2h39WDF0SXTBkvczZjVw(KeyguardViewMediator.this);
            }
        };
        this.mContext = context;
        this.mUserTracker = userTracker;
        this.mFalsingCollector = falsingCollector;
        this.mLockPatternUtils = lockPatternUtils;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mKeyguardViewControllerLazy = lazy;
        this.mDismissCallbackRegistry = dismissCallbackRegistry;
        this.mNotificationShadeDepthController = lazy3;
        this.mUiBgExecutor = executor;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mPM = powerManager;
        this.mTrustManager = trustManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mKeyguardDisplayManager = keyguardDisplayManager;
        this.mShadeController = lazy4;
        dumpManager.registerDumpable(getClass().getName(), this);
        this.mDeviceConfig = deviceConfigProxy;
        this.mScreenOnCoordinator = screenOnCoordinator;
        this.mNotificationShadeWindowControllerLazy = lazy5;
        this.mShowHomeOverLockscreen = deviceConfigProxy.getBoolean("systemui", "nav_bar_handle_show_over_lockscreen", true);
        DeviceConfigProxy deviceConfigProxy2 = this.mDeviceConfig;
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        deviceConfigProxy2.addOnPropertiesChangedListener("systemui", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), onPropertiesChangedListener);
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(navigationModeController.addListener(new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda2
            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public final void onNavigationModeChanged(int i) {
                KeyguardViewMediator.m2828$r8$lambda$xegRl58y870AMJ93TsCOlur3Oc(KeyguardViewMediator.this, i);
            }
        }));
        this.mDozeParameters = dozeParameters;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        sysuiStatusBarStateController.addCallback(this);
        this.mKeyguardStateController = keyguardStateController;
        keyguardStateController.addCallback(callback);
        this.mKeyguardUnlockAnimationControllerLazy = lazy2;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mDreamOverlayStateController = dreamOverlayStateController;
        this.mActivityLaunchAnimator = lazy6;
        this.mScrimControllerLazy = lazy7;
        this.mPowerButtonY = context.getResources().getDimensionPixelSize(R$dimen.physical_power_button_center_screen_location_y);
        this.mWindowCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mDreamOpenAnimationDuration = context.getResources().getInteger(17694836);
        this.mDreamCloseAnimationDuration = (int) DreamingToLockscreenTransitionViewModel.LOCKSCREEN_ANIMATION_DURATION_MS;
    }

    public /* synthetic */ void lambda$exitKeyguardAndFinishSurfaceBehindRemoteAnimation$9(boolean z, boolean z2) {
        onKeyguardExitFinished();
        if (this.mKeyguardStateController.isDismissingFromSwipe() || z) {
            Log.d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished#hideKeyguardViewAfterRemoteAnimation");
            ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).hideKeyguardViewAfterRemoteAnimation();
        } else {
            Log.d("KeyguardViewMediator", "skip hideKeyguardViewAfterRemoteAnimation dismissFromSwipe=" + this.mKeyguardStateController.isDismissingFromSwipe() + " wasShowing=" + z);
        }
        finishSurfaceBehindRemoteAnimation(z2);
        this.mUpdateMonitor.dispatchKeyguardDismissAnimationFinished();
    }

    public /* synthetic */ void lambda$handleHide$6() {
        handleStartKeyguardExitAnimation(SystemClock.uptimeMillis() + this.mHideAnimation.getStartOffset(), this.mHideAnimation.getDuration(), null, null, null, null);
    }

    public /* synthetic */ void lambda$handleKeyguardDone$1(int i) {
        if (this.mLockPatternUtils.isSecure(i)) {
            this.mLockPatternUtils.getDevicePolicyManager().reportKeyguardDismissed(i);
        }
    }

    public static /* synthetic */ void lambda$handleStartKeyguardExitAnimation$7(RemoteAnimationTarget remoteAnimationTarget, SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier, ValueAnimator valueAnimator) {
        syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(valueAnimator.getAnimatedFraction()).build()});
    }

    public /* synthetic */ void lambda$handleStartKeyguardExitAnimation$8(final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, RemoteAnimationTarget[] remoteAnimationTargetArr) {
        if (iRemoteAnimationFinishedCallback == null) {
            ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(false);
            this.mInteractionJankMonitor.end(29);
            return;
        }
        final SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        final RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[0];
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(400L);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda11
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                KeyguardViewMediator.$r8$lambda$J89F0gCaS9j1Hd4XK_A97waFiGo(remoteAnimationTarget, syncRtSurfaceTransactionApplier, valueAnimator);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.14
            {
                KeyguardViewMediator.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                try {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        Slog.e("KeyguardViewMediator", "RemoteException");
                    }
                } finally {
                    KeyguardViewMediator.this.mInteractionJankMonitor.cancel(29);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                try {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        Slog.e("KeyguardViewMediator", "RemoteException");
                    }
                } finally {
                    KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                }
            }
        });
        ofFloat.start();
    }

    public /* synthetic */ void lambda$new$0(int i) {
        this.mInGestureNavigationMode = QuickStepContract.isGesturalMode(i);
    }

    public /* synthetic */ void lambda$new$5() {
        Log.e("KeyguardViewMediator", "mHideAnimationFinishedRunnable#run");
        this.mHideAnimationRunning = false;
        tryKeyguardDone();
    }

    public /* synthetic */ void lambda$notifyDefaultDisplayCallbacks$10(boolean z) {
        for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
            IKeyguardStateCallback iKeyguardStateCallback = this.mKeyguardStateCallbacks.get(size);
            try {
                iKeyguardStateCallback.onShowingStateChanged(z, KeyguardUpdateMonitor.getCurrentUser());
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call onShowingStateChanged", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                }
            }
        }
    }

    public /* synthetic */ void lambda$notifyDefaultDisplayCallbacks$11() {
        this.mTrustManager.reportKeyguardShowingChanged();
    }

    public /* synthetic */ void lambda$playSound$3(int i) {
        if (this.mAudioManager.isStreamMute(this.mUiSoundsStreamType)) {
            return;
        }
        SoundPool soundPool = this.mLockSounds;
        float f = this.mLockSoundVolume;
        int play = soundPool.play(i, f, f, 1, 0, 1.0f);
        synchronized (this) {
            this.mLockSoundStreamId = play;
        }
    }

    public /* synthetic */ void lambda$sendUserPresentBroadcast$2(UserManager userManager, UserHandle userHandle, int i) {
        for (int i2 : userManager.getProfileIdsWithDisabled(userHandle.getIdentifier())) {
            this.mContext.sendBroadcastAsUser(USER_PRESENT_INTENT, UserHandle.of(i2));
        }
        this.mLockPatternUtils.userPresent(i);
    }

    public static /* synthetic */ void lambda$updateActivityLockScreenState$4(boolean z, boolean z2) {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "updateActivityLockScreenState(" + z + ", " + z2 + ")");
        }
        try {
            ActivityTaskManager.getService().setLockScreenShown(z, z2);
        } catch (RemoteException e) {
        }
    }

    public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
        synchronized (this) {
            this.mKeyguardStateCallbacks.add(iKeyguardStateCallback);
            try {
                iKeyguardStateCallback.onSimSecureStateChanged(this.mUpdateMonitor.isSimPinSecure());
                iKeyguardStateCallback.onShowingStateChanged(this.mShowing, KeyguardUpdateMonitor.getCurrentUser());
                iKeyguardStateCallback.onInputRestrictedStateChanged(this.mInputRestricted);
                iKeyguardStateCallback.onTrustedChanged(this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()));
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call to IKeyguardStateCallback", e);
            }
        }
    }

    public final void adjustStatusBarLocked() {
        adjustStatusBarLocked(false, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:45:0x0057, code lost:
        if (r4.mInGestureNavigationMode == false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void adjustStatusBarLocked(boolean z, boolean z2) {
        int i;
        if (this.mStatusBarManager == null) {
            this.mStatusBarManager = (StatusBarManager) this.mContext.getSystemService("statusbar");
        }
        StatusBarManager statusBarManager = this.mStatusBarManager;
        if (statusBarManager == null) {
            Log.w("KeyguardViewMediator", "Could not get status bar manager");
            return;
        }
        int i2 = 0;
        if (z2) {
            statusBarManager.disable(0);
        }
        if (z || isShowingAndNotOccluded()) {
            if (this.mShowHomeOverLockscreen) {
                i = 0;
            }
            i = 2097152;
            i2 = i | 16777216;
        }
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "adjustStatusBarLocked: mShowing=" + this.mShowing + " mOccluded=" + this.mOccluded + " isSecure=" + isSecure() + " force=" + z + " --> flags=0x" + Integer.toHexString(i2));
        }
        this.mStatusBarManager.disable(i2);
    }

    public final void cancelDoKeyguardForChildProfilesLocked() {
        this.mDelayedProfileShowingSequence++;
    }

    public final void cancelDoKeyguardLaterLocked() {
        this.mDelayedShowingSequence++;
    }

    public void cancelKeyguardExitAnimation() {
        Trace.beginSection("KeyguardViewMediator#cancelKeyguardExitAnimation");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(19));
        Trace.endSection();
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf(int i) {
        return createInteractionJankMonitorConf(i, null);
    }

    public final InteractionJankMonitor.Configuration.Builder createInteractionJankMonitorConf(int i, String str) {
        InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(i, ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).getViewRootImpl().getView());
        InteractionJankMonitor.Configuration.Builder builder = withView;
        if (str != null) {
            builder = withView.setTag(str);
        }
        return builder;
    }

    public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        this.mHandler.obtainMessage(11, new DismissMessage(iKeyguardDismissCallback, charSequence)).sendToTarget();
    }

    public void dismissKeyguardToLaunch(Intent intent) {
    }

    public final void doKeyguardForChildProfilesLocked() {
        int[] enabledProfileIds;
        for (int i : UserManager.get(this.mContext).getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                lockProfile(i);
            }
        }
    }

    public final void doKeyguardLaterForChildProfilesLocked() {
        int[] enabledProfileIds;
        for (int i : UserManager.get(this.mContext).getEnabledProfileIds(UserHandle.myUserId())) {
            if (this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)) {
                long lockTimeout = getLockTimeout(i);
                if (lockTimeout == 0) {
                    doKeyguardForChildProfilesLocked();
                } else {
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
                    intent.setPackage(this.mContext.getPackageName());
                    intent.putExtra("seq", this.mDelayedProfileShowingSequence);
                    intent.putExtra("android.intent.extra.USER_ID", i);
                    intent.addFlags(268435456);
                    this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime + lockTimeout, PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320));
                }
            }
        }
    }

    public final void doKeyguardLaterLocked() {
        long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
        if (lockTimeout == 0) {
            doKeyguardLocked(null);
        } else {
            doKeyguardLaterLocked(lockTimeout);
        }
    }

    public final void doKeyguardLaterLocked(long j) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intent.setPackage(this.mContext.getPackageName());
        intent.putExtra("seq", this.mDelayedShowingSequence);
        intent.addFlags(268435456);
        this.mAlarmManager.setExactAndAllowWhileIdle(2, elapsedRealtime + j, PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320));
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "setting alarm to turn off keyguard, seq = " + this.mDelayedShowingSequence);
        }
        doKeyguardLaterForChildProfilesLocked();
    }

    public final void doKeyguardLocked(Bundle bundle) {
        if (KeyguardUpdateMonitor.CORE_APPS_ONLY) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because booting to cryptkeeper");
                return;
            }
            return;
        }
        boolean z = true;
        if (!this.mExternallyEnabled) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because externally disabled");
            }
            this.mNeedToReshowWhenReenabled = true;
        } else if (this.mShowing && this.mKeyguardStateController.isShowing()) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: not showing because it is already showing");
            }
            resetStateLocked();
        } else {
            if (!mustNotUnlockCurrentUser() || !this.mUpdateMonitor.isDeviceProvisioned()) {
                boolean z2 = this.mUpdateMonitor.isSimPinSecure() || ((SubscriptionManager.isValidSubscriptionId(this.mUpdateMonitor.getNextSubIdForState(1)) || SubscriptionManager.isValidSubscriptionId(this.mUpdateMonitor.getNextSubIdForState(7))) && (SystemProperties.getBoolean("keyguard.no_require_sim", false) ^ true));
                if (!z2 && shouldWaitForProvisioning()) {
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "doKeyguard: not showing because device isn't provisioned and the sim is not locked or missing");
                        return;
                    }
                    return;
                }
                if (bundle == null || !bundle.getBoolean("force_show", false)) {
                    z = false;
                }
                if (this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser()) && !z2 && !z) {
                    if (DEBUG) {
                        Log.d("KeyguardViewMediator", "doKeyguard: not showing because lockscreen is off");
                        return;
                    }
                    return;
                }
            }
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "doKeyguard: showing the lock screen");
            }
            showLocked(bundle);
        }
    }

    public void doKeyguardTimeout(Bundle bundle) {
        this.mHandler.removeMessages(10);
        this.mHandler.sendMessageAtFrontOfQueue(this.mHandler.obtainMessage(10, bundle));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.print("  mSystemReady: ");
        printWriter.println(this.mSystemReady);
        printWriter.print("  mBootCompleted: ");
        printWriter.println(this.mBootCompleted);
        printWriter.print("  mBootSendUserPresent: ");
        printWriter.println(this.mBootSendUserPresent);
        printWriter.print("  mExternallyEnabled: ");
        printWriter.println(this.mExternallyEnabled);
        printWriter.print("  mShuttingDown: ");
        printWriter.println(this.mShuttingDown);
        printWriter.print("  mNeedToReshowWhenReenabled: ");
        printWriter.println(this.mNeedToReshowWhenReenabled);
        printWriter.print("  mShowing: ");
        printWriter.println(this.mShowing);
        printWriter.print("  mInputRestricted: ");
        printWriter.println(this.mInputRestricted);
        printWriter.print("  mOccluded: ");
        printWriter.println(this.mOccluded);
        printWriter.print("  mDelayedShowingSequence: ");
        printWriter.println(this.mDelayedShowingSequence);
        printWriter.print("  mDeviceInteractive: ");
        printWriter.println(this.mDeviceInteractive);
        printWriter.print("  mGoingToSleep: ");
        printWriter.println(this.mGoingToSleep);
        printWriter.print("  mHiding: ");
        printWriter.println(this.mHiding);
        printWriter.print("  mDozing: ");
        printWriter.println(this.mDozing);
        printWriter.print("  mAodShowing: ");
        printWriter.println(this.mAodShowing);
        printWriter.print("  mWaitingUntilKeyguardVisible: ");
        printWriter.println(this.mWaitingUntilKeyguardVisible);
        printWriter.print("  mKeyguardDonePending: ");
        printWriter.println(this.mKeyguardDonePending);
        printWriter.print("  mHideAnimationRun: ");
        printWriter.println(this.mHideAnimationRun);
        printWriter.print("  mPendingReset: ");
        printWriter.println(this.mPendingReset);
        printWriter.print("  mPendingLock: ");
        printWriter.println(this.mPendingLock);
        printWriter.print("  wakeAndUnlocking: ");
        printWriter.println(this.mWakeAndUnlocking);
        printWriter.print("  mPendingPinLock: ");
        printWriter.println(this.mPendingPinLock);
    }

    public void exitKeyguardAndFinishSurfaceBehindRemoteAnimation(final boolean z) {
        Log.d("KeyguardViewMediator", "onKeyguardExitRemoteAnimationFinished");
        if (this.mSurfaceBehindRemoteAnimationRunning || this.mSurfaceBehindRemoteAnimationRequested) {
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).blockPanelExpansionFromCurrentTouch();
            final boolean z2 = this.mShowing;
            InteractionJankMonitor.getInstance().end(29);
            DejankUtils.postAfterTraversal(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.$r8$lambda$XTX2YbVzvNCzWLSm3rviYi4HtXU(KeyguardViewMediator.this, z2, z);
                }
            });
            ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyFinishedKeyguardExitAnimation(z);
            return;
        }
        Log.d("KeyguardViewMediator", "skip onKeyguardExitRemoteAnimationFinished cancelled=" + z + " surfaceAnimationRunning=" + this.mSurfaceBehindRemoteAnimationRunning + " surfaceAnimationRequested=" + this.mSurfaceBehindRemoteAnimationRequested);
    }

    public void finishSurfaceBehindRemoteAnimation(boolean z) {
        this.mSurfaceBehindRemoteAnimationRequested = false;
        this.mSurfaceBehindRemoteAnimationRunning = false;
        this.mKeyguardStateController.notifyKeyguardGoingAway(false);
        IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = this.mSurfaceBehindRemoteAnimationFinishedCallback;
        if (iRemoteAnimationFinishedCallback != null) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
                this.mSurfaceBehindRemoteAnimationFinishedCallback = null;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public final long getLockTimeout(int i) {
        ContentResolver contentResolver;
        long j = Settings.Secure.getInt(this.mContext.getContentResolver(), "lock_screen_lock_after_timeout", MagnificationModeSwitch.DEFAULT_FADE_OUT_ANIMATION_DELAY_MS);
        long maximumTimeToLock = this.mLockPatternUtils.getDevicePolicyManager().getMaximumTimeToLock(null, i);
        if (maximumTimeToLock > 0) {
            j = Math.max(Math.min(maximumTimeToLock - Math.max(Settings.System.getInt(contentResolver, "screen_off_timeout", 30000), 0L), j), 0L);
        }
        return j;
    }

    public IRemoteAnimationRunner getOccludeAnimationRunner() {
        return this.mOccludeAnimationRunner;
    }

    public IRemoteAnimationRunner getOccludeByDreamAnimationRunner() {
        return this.mOccludeByDreamAnimationRunner;
    }

    public IRemoteAnimationRunner getUnoccludeAnimationRunner() {
        return this.mUnoccludeAnimationRunner;
    }

    public ViewMediatorCallback getViewMediatorCallback() {
        return this.mViewMediatorCallback;
    }

    public final void handleCancelKeyguardExitAnimation() {
        if (this.mPendingLock) {
            Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. There's a pending lock, so we were cancelled because the device was locked again during the unlock sequence. We should end up locked.");
            finishSurfaceBehindRemoteAnimation(true);
            return;
        }
        Log.d("KeyguardViewMediator", "#handleCancelKeyguardExitAnimation: keyguard exit animation cancelled. No pending lock, we should end up unlocked with the app/launcher visible.");
        showSurfaceBehindKeyguard();
        exitKeyguardAndFinishSurfaceBehindRemoteAnimation(true);
    }

    public final void handleDismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
        if (!this.mShowing) {
            if (iKeyguardDismissCallback != null) {
                new DismissCallbackWrapper(iKeyguardDismissCallback).notifyDismissError();
                return;
            }
            return;
        }
        if (iKeyguardDismissCallback != null) {
            this.mDismissCallbackRegistry.addCallback(iKeyguardDismissCallback);
        }
        this.mCustomMessage = charSequence;
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
    }

    public final void handleHide() {
        Trace.beginSection("KeyguardViewMediator#handleHide");
        if (this.mAodShowing) {
            this.mPM.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:BOUNCER_DOZING");
        }
        synchronized (this) {
            boolean z = DEBUG;
            if (z) {
                Log.d("KeyguardViewMediator", "handleHide");
            }
            if (mustNotUnlockCurrentUser()) {
                if (z) {
                    Log.d("KeyguardViewMediator", "Split system user, quit unlocking.");
                }
                this.mKeyguardExitAnimationRunner = null;
                return;
            }
            this.mHiding = true;
            if (!this.mShowing || this.mOccluded) {
                ((NotificationShadeWindowController) this.mNotificationShadeWindowControllerLazy.get()).batchApplyWindowLayoutParams(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.$r8$lambda$C37rD581uNVPa0zKJYfOSlu7Z04(KeyguardViewMediator.this);
                    }
                });
            } else {
                this.mKeyguardGoingAwayRunnable.run();
            }
            if (this.mDreamOverlayShowing) {
                this.mPM.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:UNLOCK_DREAMING");
            }
            Trace.endSection();
        }
    }

    public final void handleKeyguardDone() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDone");
        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.$r8$lambda$C_N2pvBHAIgJo3TaffLVl12notg(KeyguardViewMediator.this, currentUser);
            }
        });
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "handleKeyguardDone");
        }
        synchronized (this) {
            resetKeyguardDonePendingLocked();
        }
        if (this.mGoingToSleep) {
            this.mUpdateMonitor.clearBiometricRecognizedWhenKeyguardDone(currentUser);
            Log.i("KeyguardViewMediator", "Device is going to sleep, aborting keyguardDone");
            return;
        }
        setPendingLock(false);
        handleHide();
        this.mUpdateMonitor.clearBiometricRecognizedWhenKeyguardDone(currentUser);
        Trace.endSection();
    }

    public final void handleKeyguardDoneDrawing() {
        Trace.beginSection("KeyguardViewMediator#handleKeyguardDoneDrawing");
        synchronized (this) {
            boolean z = DEBUG;
            if (z) {
                Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing");
            }
            if (this.mWaitingUntilKeyguardVisible) {
                if (z) {
                    Log.d("KeyguardViewMediator", "handleKeyguardDoneDrawing: notifying mWaitingUntilKeyguardVisible");
                }
                this.mWaitingUntilKeyguardVisible = false;
                notifyAll();
                this.mHandler.removeMessages(8);
            }
        }
        Trace.endSection();
    }

    public final void handleNotifyFinishedGoingToSleep() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyFinishedGoingToSleep");
            }
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onFinishedGoingToSleep();
        }
    }

    public final void handleNotifyStartedGoingToSleep() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyStartedGoingToSleep");
            }
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onStartedGoingToSleep();
        }
    }

    public final void handleNotifyStartedWakingUp() {
        Trace.beginSection("KeyguardViewMediator#handleMotifyStartedWakingUp");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleNotifyWakingUp");
            }
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).onStartedWakingUp();
        }
        Trace.endSection();
    }

    public final void handleReset() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleReset");
            }
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).reset(true);
        }
    }

    public final void handleSetOccluded(boolean z, boolean z2) {
        Trace.beginSection("KeyguardViewMediator#handleSetOccluded");
        Log.d("KeyguardViewMediator", "handleSetOccluded(" + z + ")");
        synchronized (this) {
            if (this.mHiding && z) {
                startKeyguardExitAnimation(0L, 0L);
            }
            if (this.mOccluded != z) {
                this.mOccluded = z;
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).setOccluded(z, z2 && this.mDeviceInteractive);
                adjustStatusBarLocked();
            }
        }
        Trace.endSection();
    }

    public final void handleShow(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#handleShow");
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        if (this.mLockPatternUtils.isSecure(currentUser)) {
            this.mLockPatternUtils.getDevicePolicyManager().reportKeyguardSecured(currentUser);
        }
        synchronized (this) {
            if (!this.mSystemReady) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "ignoring handleShow because system is not ready.");
                }
                return;
            }
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleShow");
            }
            this.mHiding = false;
            this.mKeyguardExitAnimationRunner = null;
            this.mWakeAndUnlocking = false;
            setPendingLock(false);
            setShowingLocked(true);
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).show(bundle);
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            adjustStatusBarLocked();
            userActivity();
            this.mUpdateMonitor.setKeyguardGoingAway(false);
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).setKeyguardGoingAwayState(false);
            this.mShowKeyguardWakeLock.release();
            this.mKeyguardDisplayManager.show();
            this.mLockPatternUtils.scheduleNonStrongBiometricIdleTimeout(KeyguardUpdateMonitor.getCurrentUser());
            Trace.endSection();
        }
    }

    public final void handleStartKeyguardExitAnimation(long j, long j2, final RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#handleStartKeyguardExitAnimation");
        Log.d("KeyguardViewMediator", "handleStartKeyguardExitAnimation startTime=" + j + " fadeoutDuration=" + j2);
        synchronized (this) {
            if (!this.mHiding && !this.mSurfaceBehindRemoteAnimationRequested && !this.mKeyguardStateController.isFlingingToDismissKeyguardDuringSwipeGesture()) {
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                    } catch (RemoteException e) {
                        Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e);
                    }
                }
                setShowingLocked(this.mShowing, true);
                return;
            }
            this.mHiding = false;
            IRemoteAnimationRunner iRemoteAnimationRunner = this.mKeyguardExitAnimationRunner;
            this.mKeyguardExitAnimationRunner = null;
            LatencyTracker.getInstance(this.mContext).onActionEnd(11);
            boolean z = KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation;
            if (z && iRemoteAnimationRunner != null && iRemoteAnimationFinishedCallback != null) {
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.keyguard.KeyguardViewMediator.13
                    {
                        KeyguardViewMediator.this = this;
                    }

                    public IBinder asBinder() {
                        return iRemoteAnimationFinishedCallback.asBinder();
                    }

                    public void onAnimationFinished() throws RemoteException {
                        try {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException e2) {
                            Slog.w("KeyguardViewMediator", "Failed to call onAnimationFinished", e2);
                        }
                        KeyguardViewMediator.this.onKeyguardExitFinished();
                        ((KeyguardViewController) KeyguardViewMediator.this.mKeyguardViewControllerLazy.get()).hide(0L, 0L);
                        KeyguardViewMediator.this.mInteractionJankMonitor.end(29);
                    }
                };
                try {
                    this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf(29, "RunRemoteAnimation"));
                    iRemoteAnimationRunner.onAnimationStart(7, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback2);
                } catch (RemoteException e2) {
                    Slog.w("KeyguardViewMediator", "Failed to call onAnimationStart", e2);
                }
            } else if (!z || this.mStatusBarStateController.leaveOpenOnKeyguardHide() || remoteAnimationTargetArr == null || remoteAnimationTargetArr.length <= 0) {
                this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf(29, "RemoteAnimationDisabled"));
                ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).hide(j, j2);
                this.mContext.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda9
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.$r8$lambda$Gum5Nk10mw3bohg7j38n2wL1aNs(KeyguardViewMediator.this, iRemoteAnimationFinishedCallback, remoteAnimationTargetArr);
                    }
                });
                onKeyguardExitFinished();
            } else {
                this.mSurfaceBehindRemoteAnimationFinishedCallback = iRemoteAnimationFinishedCallback;
                this.mSurfaceBehindRemoteAnimationRunning = true;
                this.mInteractionJankMonitor.begin(createInteractionJankMonitorConf(29, "DismissPanel"));
                ((KeyguardUnlockAnimationController) this.mKeyguardUnlockAnimationControllerLazy.get()).notifyStartSurfaceBehindRemoteAnimation(remoteAnimationTargetArr, j, this.mSurfaceBehindRemoteAnimationRequested);
            }
            Trace.endSection();
            return;
        }
    }

    public final void handleSystemReady() {
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "onSystemReady");
            }
            this.mSystemReady = true;
            doKeyguardLocked(null);
            this.mUpdateMonitor.registerCallback(this.mUpdateCallback);
            adjustStatusBarLocked();
            this.mDreamOverlayStateController.addCallback(this.mDreamOverlayStateCallback);
        }
        maybeSendUserPresentBroadcast();
    }

    public final void handleVerifyUnlock() {
        Trace.beginSection("KeyguardViewMediator#handleVerifyUnlock");
        synchronized (this) {
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "handleVerifyUnlock");
            }
            setShowingLocked(true);
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).dismissAndCollapse();
        }
        Trace.endSection();
    }

    public final void hideLocked() {
        Trace.beginSection("KeyguardViewMediator#hideLocked");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "hideLocked");
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(2));
        Trace.endSection();
    }

    public void hideSurfaceBehindKeyguard() {
        this.mSurfaceBehindRemoteAnimationRequested = false;
        this.mKeyguardStateController.notifyKeyguardGoingAway(false);
        if (this.mShowing) {
            setShowingLocked(true, true);
        }
    }

    public void hideWithAnimation(IRemoteAnimationRunner iRemoteAnimationRunner) {
        if (this.mKeyguardDonePending) {
            this.mKeyguardExitAnimationRunner = iRemoteAnimationRunner;
            this.mViewMediatorCallback.readyForKeyguardDone();
        }
    }

    public boolean isAnimatingBetweenKeyguardAndSurfaceBehind() {
        return this.mSurfaceBehindRemoteAnimationRunning;
    }

    public boolean isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe() {
        return this.mSurfaceBehindRemoteAnimationRunning || this.mKeyguardStateController.isFlingingToDismissKeyguard();
    }

    public boolean isAnySimPinSecure() {
        for (int i = 0; i < this.mLastSimStates.size(); i++) {
            if (KeyguardUpdateMonitor.isSimPinSecure(this.mLastSimStates.get(this.mLastSimStates.keyAt(i)))) {
                return true;
            }
        }
        return false;
    }

    public boolean isHiding() {
        return this.mHiding;
    }

    public boolean isInputRestricted() {
        return this.mShowing || this.mNeedToReshowWhenReenabled;
    }

    public final boolean isKeyguardServiceEnabled() {
        try {
            return this.mContext.getPackageManager().getServiceInfo(new ComponentName(this.mContext, KeyguardService.class), 0).isEnabled();
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }

    public boolean isOccludeAnimationPlaying() {
        return this.mOccludeAnimationPlaying;
    }

    public boolean isSecure() {
        return isSecure(KeyguardUpdateMonitor.getCurrentUser());
    }

    public boolean isSecure(int i) {
        return this.mLockPatternUtils.isSecure(i) || this.mUpdateMonitor.isSimPinSecure();
    }

    public boolean isShowingAndNotOccluded() {
        return this.mShowing && !this.mOccluded;
    }

    public final void keyguardDone() {
        Trace.beginSection("KeyguardViewMediator#keyguardDone");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "keyguardDone()");
        }
        userActivity();
        EventLog.writeEvent(70000, 2);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(7));
        Trace.endSection();
    }

    public final void lockProfile(int i) {
        this.mTrustManager.setDeviceLockedForUser(i, true);
    }

    public void maybeHandlePendingLock() {
        if (this.mPendingLock) {
            if (this.mScreenOffAnimationController.shouldDelayKeyguardShow()) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the screen off animation's shouldDelayKeyguardShow() returned true. This should be handled soon by #onStartedWakingUp, or by the end actions of the screen off animation.");
                }
            } else if (this.mKeyguardStateController.isKeyguardGoingAway()) {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: not handling because the keyguard is going away. This should be handled shortly by StatusBar#finishKeyguardFadingAway.");
                }
            } else {
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "#maybeHandlePendingLock: handling pending lock; locking keyguard.");
                }
                doKeyguardLocked(null);
                setPendingLock(false);
            }
        }
    }

    public final void maybeSendUserPresentBroadcast() {
        if (this.mSystemReady && this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
            sendUserPresentBroadcast();
        } else if (this.mSystemReady && shouldWaitForProvisioning()) {
            this.mLockPatternUtils.userPresent(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public boolean mustNotUnlockCurrentUser() {
        return UserManager.isSplitSystemUser() && KeyguardUpdateMonitor.getCurrentUser() == 0;
    }

    public final void notifyDefaultDisplayCallbacks(final boolean z) {
        DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.$r8$lambda$Q7K5bOIr2sDkGRLR4bqI44oo7Po(KeyguardViewMediator.this, z);
            }
        });
        updateInputRestrictedLocked();
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.$r8$lambda$8rbPocBXr1kDKdp39FBeeFeFtwA(KeyguardViewMediator.this);
            }
        });
    }

    public final void notifyFinishedGoingToSleep() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyFinishedGoingToSleep");
        }
        this.mHandler.sendEmptyMessage(5);
    }

    public final void notifyStartedGoingToSleep() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyStartedGoingToSleep");
        }
        this.mHandler.sendEmptyMessage(17);
    }

    public final void notifyStartedWakingUp() {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "notifyStartedWakingUp");
        }
        this.mHandler.sendEmptyMessage(14);
    }

    public final void notifyTrustedChangedLocked(boolean z) {
        for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
            try {
                this.mKeyguardStateCallbacks.get(size).onTrustedChanged(z);
            } catch (RemoteException e) {
                Slog.w("KeyguardViewMediator", "Failed to call notifyTrustedChangedLocked", e);
                if (e instanceof DeadObjectException) {
                    this.mKeyguardStateCallbacks.remove(size);
                }
            }
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onBootCompleted() {
        synchronized (this) {
            if (this.mContext.getResources().getBoolean(17891689)) {
                this.mUserSwitcherController.schedulePostBootGuestCreation();
            }
            this.mBootCompleted = true;
            adjustStatusBarLocked(false, true);
            if (this.mBootSendUserPresent) {
                sendUserPresentBroadcast();
            }
        }
    }

    @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
    public void onDozeAmountChanged(float f, float f2) {
        if (this.mAnimatingScreenOff && this.mDozing && f == 1.0f) {
            this.mAnimatingScreenOff = false;
            setShowingLocked(this.mShowing, true);
        }
    }

    public void onDreamingStarted() {
        this.mUpdateMonitor.dispatchDreamingStarted();
        synchronized (this) {
            if (this.mDeviceInteractive && this.mLockPatternUtils.isSecure(KeyguardUpdateMonitor.getCurrentUser())) {
                doKeyguardLaterLocked();
            }
        }
    }

    public void onDreamingStopped() {
        this.mUpdateMonitor.dispatchDreamingStopped();
        synchronized (this) {
            if (this.mDeviceInteractive) {
                cancelDoKeyguardLaterLocked();
            }
        }
    }

    public void onFinishedGoingToSleep(int i, boolean z) {
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "onFinishedGoingToSleep(" + i + ")");
        }
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mGoingToSleep = false;
            this.mWakeAndUnlocking = false;
            this.mAnimatingScreenOff = this.mDozeParameters.shouldAnimateDozingChange();
            resetKeyguardDonePendingLocked();
            this.mHideAnimationRun = false;
            notifyFinishedGoingToSleep();
            if (z) {
                ((PowerManager) this.mContext.getSystemService(PowerManager.class)).wakeUp(SystemClock.uptimeMillis(), 5, "com.android.systemui:CAMERA_GESTURE_PREVENT_LOCK");
                setPendingLock(false);
                this.mPendingReset = false;
            }
            if (this.mPendingReset) {
                resetStateLocked();
                this.mPendingReset = false;
            }
            maybeHandlePendingLock();
            if (!this.mLockLater && !z) {
                doKeyguardForChildProfilesLocked();
            }
        }
        this.mUpdateMonitor.dispatchFinishedGoingToSleep(i);
    }

    public final void onKeyguardExitFinished() {
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(this.mPhoneState) && this.mShowing && this.mDeviceInteractive) {
            playSounds(false);
        }
        setShowingLocked(false);
        this.mWakeAndUnlocking = false;
        this.mDismissCallbackRegistry.notifyDismissSucceeded();
        resetKeyguardDonePendingLocked();
        this.mHideAnimationRun = false;
        adjustStatusBarLocked();
        sendUserPresentBroadcast();
    }

    public void onScreenTurnedOff() {
        this.mUpdateMonitor.dispatchScreenTurnedOff();
    }

    public void onShortPowerPressedGoHome() {
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x00be A[Catch: all -> 0x00e6, TryCatch #0 {, blocks: (B:54:0x0031, B:56:0x004f, B:61:0x0066, B:63:0x007b, B:65:0x0087, B:79:0x00c4, B:81:0x00ca, B:83:0x00d0, B:74:0x00a5, B:75:0x00b3, B:77:0x00be), top: B:92:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00ca A[Catch: all -> 0x00e6, TryCatch #0 {, blocks: (B:54:0x0031, B:56:0x004f, B:61:0x0066, B:63:0x007b, B:65:0x0087, B:79:0x00c4, B:81:0x00ca, B:83:0x00d0, B:74:0x00a5, B:75:0x00b3, B:77:0x00be), top: B:92:0x0031 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onStartedGoingToSleep(int i) {
        boolean z;
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "onStartedGoingToSleep(" + i + ")");
        }
        synchronized (this) {
            this.mDeviceInteractive = false;
            this.mPowerGestureIntercepted = false;
            this.mGoingToSleep = true;
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (!this.mLockPatternUtils.getPowerButtonInstantlyLocks(currentUser) && this.mLockPatternUtils.isSecure(currentUser)) {
                z = false;
                long lockTimeout = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
                this.mLockLater = false;
                if (!this.mShowing && !this.mKeyguardStateController.isKeyguardGoingAway()) {
                    this.mPendingReset = true;
                } else if ((i != 3 && lockTimeout > 0) || (i == 2 && !z)) {
                    doKeyguardLaterLocked(lockTimeout);
                    this.mLockLater = true;
                } else if (!this.mLockPatternUtils.isLockScreenDisabled(currentUser)) {
                    setPendingLock(true);
                }
                if (this.mPendingLock) {
                    playSounds(true);
                }
            }
            z = true;
            long lockTimeout2 = getLockTimeout(KeyguardUpdateMonitor.getCurrentUser());
            this.mLockLater = false;
            if (!this.mShowing) {
            }
            if (i != 3) {
            }
            if (!this.mLockPatternUtils.isLockScreenDisabled(currentUser)) {
            }
            if (this.mPendingLock) {
            }
        }
        this.mUpdateMonitor.dispatchStartedGoingToSleep(i);
        this.mUpdateMonitor.dispatchKeyguardGoingAway(false);
        notifyStartedGoingToSleep();
    }

    public void onStartedWakingUp(int i, boolean z) {
        Trace.beginSection("KeyguardViewMediator#onStartedWakingUp");
        synchronized (this) {
            this.mDeviceInteractive = true;
            if (this.mPendingLock && !z && !this.mWakeAndUnlocking) {
                doKeyguardLocked(null);
            }
            this.mAnimatingScreenOff = false;
            cancelDoKeyguardLaterLocked();
            cancelDoKeyguardForChildProfilesLocked();
            if (DEBUG) {
                Log.d("KeyguardViewMediator", "onStartedWakingUp, seq = " + this.mDelayedShowingSequence);
            }
            notifyStartedWakingUp();
        }
        this.mUpdateMonitor.dispatchStartedWakingUp(i);
        maybeSendUserPresentBroadcast();
        Trace.endSection();
    }

    public void onSystemKeyPressed(int i) {
    }

    public void onSystemReady() {
        this.mHandler.obtainMessage(18).sendToTarget();
    }

    public void onWakeAndUnlocking() {
        Trace.beginSection("KeyguardViewMediator#onWakeAndUnlocking");
        this.mWakeAndUnlocking = true;
        keyguardDone();
        Trace.endSection();
    }

    public final void playSound(final int i) {
        if (i != 0 && Settings.System.getInt(this.mContext.getContentResolver(), "lockscreen_sounds_enabled", 1) == 1) {
            this.mLockSounds.stop(this.mLockSoundStreamId);
            if (this.mAudioManager == null) {
                AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
                this.mAudioManager = audioManager;
                if (audioManager == null) {
                    return;
                }
                this.mUiSoundsStreamType = audioManager.getUiSoundsStreamType();
            }
            this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardViewMediator.m2827$r8$lambda$wva4u_JAP64VxYRHqTBvCWvPig(KeyguardViewMediator.this, i);
                }
            });
        }
    }

    public final void playSounds(boolean z) {
        playSound(z ? this.mLockSoundId : this.mUnlockSoundId);
    }

    public final void playTrustedSound() {
        playSound(this.mTrustedSoundId);
    }

    public KeyguardViewController registerCentralSurfaces(CentralSurfaces centralSurfaces, NotificationPanelViewController notificationPanelViewController, ShadeExpansionStateManager shadeExpansionStateManager, BiometricUnlockController biometricUnlockController, View view, KeyguardBypassController keyguardBypassController) {
        this.mCentralSurfaces = centralSurfaces;
        ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).registerCentralSurfaces(centralSurfaces, notificationPanelViewController, shadeExpansionStateManager, biometricUnlockController, view, keyguardBypassController);
        return (KeyguardViewController) this.mKeyguardViewControllerLazy.get();
    }

    public boolean requestedShowSurfaceBehindKeyguard() {
        return this.mSurfaceBehindRemoteAnimationRequested;
    }

    public final void resetKeyguardDonePendingLocked() {
        this.mKeyguardDonePending = false;
        this.mHandler.removeMessages(13);
    }

    public final void resetStateLocked() {
        if (DEBUG) {
            Log.e("KeyguardViewMediator", "resetStateLocked");
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(3));
    }

    public final void sendUserPresentBroadcast() {
        synchronized (this) {
            if (this.mBootCompleted) {
                final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                final UserHandle userHandle = new UserHandle(currentUser);
                final UserManager userManager = (UserManager) this.mContext.getSystemService("user");
                this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardViewMediator.$r8$lambda$00ehQOGK1Zp73BYM6Gt8XLoD13I(KeyguardViewMediator.this, userManager, userHandle, currentUser);
                    }
                });
            } else {
                this.mBootSendUserPresent = true;
            }
        }
    }

    public void setBlursDisabledForAppLaunch(boolean z) {
        ((NotificationShadeDepthController) this.mNotificationShadeDepthController.get()).setBlursDisabledForAppLaunch(z);
    }

    public void setCurrentUser(int i) {
        KeyguardUpdateMonitor.setCurrentUser(i);
        synchronized (this) {
            notifyTrustedChangedLocked(this.mUpdateMonitor.getUserHasTrust(i));
        }
    }

    public void setDozing(boolean z) {
        if (z == this.mDozing) {
            return;
        }
        this.mDozing = z;
        if (!z) {
            this.mAnimatingScreenOff = false;
        }
        if (!this.mShowing && this.mPendingLock && this.mDozeParameters.canControlUnlockedScreenOff()) {
            return;
        }
        setShowingLocked(this.mShowing);
    }

    public void setKeyguardEnabled(boolean z) {
        synchronized (this) {
            boolean z2 = DEBUG;
            if (z2) {
                Log.d("KeyguardViewMediator", "setKeyguardEnabled(" + z + ")");
            }
            this.mExternallyEnabled = z;
            if (!z && this.mShowing) {
                if (z2) {
                    Log.d("KeyguardViewMediator", "remembering to reshow, hiding keyguard, disabling status bar expansion");
                }
                this.mNeedToReshowWhenReenabled = true;
                updateInputRestrictedLocked();
                hideLocked();
            } else if (z && this.mNeedToReshowWhenReenabled) {
                if (z2) {
                    Log.d("KeyguardViewMediator", "previously hidden, reshowing, reenabling status bar expansion");
                }
                this.mNeedToReshowWhenReenabled = false;
                updateInputRestrictedLocked();
                showLocked(null);
                this.mWaitingUntilKeyguardVisible = true;
                this.mHandler.sendEmptyMessageDelayed(8, 2000L);
                if (z2) {
                    Log.d("KeyguardViewMediator", "waiting until mWaitingUntilKeyguardVisible is false");
                }
                while (this.mWaitingUntilKeyguardVisible) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (DEBUG) {
                    Log.d("KeyguardViewMediator", "done waiting for mWaitingUntilKeyguardVisible");
                }
            }
        }
    }

    public void setOccluded(boolean z, boolean z2) {
        Log.d("KeyguardViewMediator", "setOccluded(" + z + ")");
        Trace.beginSection("KeyguardViewMediator#setOccluded");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "setOccluded " + z);
        }
        this.mInteractionJankMonitor.cancel(23);
        this.mHandler.removeMessages(9);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(9, z ? 1 : 0, z2 ? 1 : 0));
        Trace.endSection();
    }

    public final void setPendingLock(boolean z) {
        this.mPendingLock = z;
        Trace.traceCounter(4096L, "pendingLock", z ? 1 : 0);
    }

    public void setShowingLocked(boolean z) {
        setShowingLocked(z, false);
    }

    public final void setShowingLocked(boolean z, boolean z2) {
        boolean z3 = this.mDozing && !this.mWakeAndUnlocking;
        boolean z4 = this.mShowing;
        boolean z5 = z != z4 || z2;
        boolean z6 = true;
        if (z == z4) {
            z6 = true;
            if (z3 == this.mAodShowing) {
                z6 = z2;
            }
        }
        this.mShowing = z;
        this.mAodShowing = z3;
        if (z5) {
            notifyDefaultDisplayCallbacks(z);
        }
        if (z6) {
            updateActivityLockScreenState(z, z3);
        }
    }

    public void setSwitchingUser(boolean z) {
        this.mUpdateMonitor.setSwitchingUser(z);
    }

    public void setWallpaperSupportsAmbientMode(boolean z) {
        this.mWallpaperSupportsAmbientMode = z;
    }

    public final void setupLocked() {
        PowerManager.WakeLock newWakeLock = this.mPM.newWakeLock(1, "show keyguard");
        this.mShowKeyguardWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.ACTION_SHUTDOWN");
        this.mBroadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
        intentFilter2.addAction("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_LOCK");
        intentFilter2.setPriority(1000);
        this.mContext.registerReceiver(this.mDelayedLockBroadcastReceiver, intentFilter2, "com.android.systemui.permission.SELF", null, 2);
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService("alarm");
        KeyguardUpdateMonitor.setCurrentUser(this.mUserTracker.getUserId());
        if (isKeyguardServiceEnabled()) {
            boolean z = false;
            if (!shouldWaitForProvisioning()) {
                z = false;
                if (!this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                    z = true;
                }
            }
            setShowingLocked(z, true);
        } else {
            setShowingLocked(false, true);
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mDeviceInteractive = this.mPM.isInteractive();
        this.mLockSounds = new SoundPool.Builder().setMaxStreams(1).setAudioAttributes(new AudioAttributes.Builder().setUsage(13).setContentType(4).build()).build();
        String string = Settings.Global.getString(contentResolver, "lock_sound");
        if (string != null) {
            this.mLockSoundId = this.mLockSounds.load(string, 1);
        }
        if (string == null || this.mLockSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load lock sound from " + string);
        }
        String string2 = Settings.Global.getString(contentResolver, "unlock_sound");
        if (string2 != null) {
            this.mUnlockSoundId = this.mLockSounds.load(string2, 1);
        }
        if (string2 == null || this.mUnlockSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load unlock sound from " + string2);
        }
        String string3 = Settings.Global.getString(contentResolver, "trusted_sound");
        if (string3 != null) {
            this.mTrustedSoundId = this.mLockSounds.load(string3, 1);
        }
        if (string3 == null || this.mTrustedSoundId == 0) {
            Log.w("KeyguardViewMediator", "failed to load trusted sound from " + string3);
        }
        this.mLockSoundVolume = (float) Math.pow(10.0d, this.mContext.getResources().getInteger(17694869) / 20.0f);
        this.mHideAnimation = AnimationUtils.loadAnimation(this.mContext, 17432683);
        this.mWorkLockController = new WorkLockActivityController(this.mContext);
    }

    public final boolean shouldWaitForProvisioning() {
        return (this.mUpdateMonitor.isDeviceProvisioned() || isSecure()) ? false : true;
    }

    public final void showLocked(Bundle bundle) {
        Trace.beginSection("KeyguardViewMediator#showLocked acquiring mShowKeyguardWakeLock");
        if (DEBUG) {
            Log.d("KeyguardViewMediator", "showLocked");
        }
        this.mShowKeyguardWakeLock.acquire();
        this.mHandler.sendMessageAtFrontOfQueue(this.mHandler.obtainMessage(1, bundle));
        Trace.endSection();
    }

    public void showSurfaceBehindKeyguard() {
        this.mSurfaceBehindRemoteAnimationRequested = true;
        int i = 6;
        try {
            if (KeyguardUnlockAnimationController.Companion.isNexusLauncherUnderneath()) {
                i = 22;
            }
            ActivityTaskManager.getService().keyguardGoingAway(i);
            this.mKeyguardStateController.notifyKeyguardGoingAway(true);
        } catch (RemoteException e) {
            this.mSurfaceBehindRemoteAnimationRequested = false;
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        synchronized (this) {
            setupLocked();
        }
    }

    public final void startKeyguardExitAnimation(int i, long j, long j2, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        Trace.beginSection("KeyguardViewMediator#startKeyguardExitAnimation");
        this.mInteractionJankMonitor.cancel(23);
        this.mHandler.sendMessage(this.mHandler.obtainMessage(12, new StartKeyguardExitAnimParams(i, j, j2, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback)));
        Trace.endSection();
    }

    public void startKeyguardExitAnimation(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        startKeyguardExitAnimation(i, 0L, 0L, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
    }

    @Deprecated
    public void startKeyguardExitAnimation(long j, long j2) {
        startKeyguardExitAnimation(0, j, j2, null, null, null, null);
    }

    public final void tryKeyguardDone() {
        boolean z = DEBUG;
        if (z) {
            Log.d("KeyguardViewMediator", "tryKeyguardDone: pending - " + this.mKeyguardDonePending + ", animRan - " + this.mHideAnimationRun + " animRunning - " + this.mHideAnimationRunning);
        }
        if (!this.mKeyguardDonePending && this.mHideAnimationRun && !this.mHideAnimationRunning) {
            handleKeyguardDone();
        } else if (this.mHideAnimationRun) {
        } else {
            if (z) {
                Log.d("KeyguardViewMediator", "tryKeyguardDone: starting pre-hide animation");
            }
            this.mHideAnimationRun = true;
            this.mHideAnimationRunning = true;
            ((KeyguardViewController) this.mKeyguardViewControllerLazy.get()).startPreHideAnimation(this.mHideAnimationFinishedRunnable);
        }
    }

    public final void updateActivityLockScreenState(final boolean z, final boolean z2) {
        this.mUiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardViewMediator.$r8$lambda$ShDDxDpq7_z9hv9ydzmNxHC9Hes(z, z2);
            }
        });
    }

    public final void updateInputRestricted() {
        synchronized (this) {
            updateInputRestrictedLocked();
        }
    }

    public final void updateInputRestrictedLocked() {
        boolean isInputRestricted = isInputRestricted();
        if (this.mInputRestricted != isInputRestricted) {
            this.mInputRestricted = isInputRestricted;
            for (int size = this.mKeyguardStateCallbacks.size() - 1; size >= 0; size--) {
                IKeyguardStateCallback iKeyguardStateCallback = this.mKeyguardStateCallbacks.get(size);
                try {
                    iKeyguardStateCallback.onInputRestrictedStateChanged(isInputRestricted);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onDeviceProvisioned", e);
                    if (e instanceof DeadObjectException) {
                        this.mKeyguardStateCallbacks.remove(iKeyguardStateCallback);
                    }
                }
            }
        }
    }

    public void userActivity() {
        this.mPM.userActivity(SystemClock.uptimeMillis(), false);
    }

    public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
        Trace.beginSection("KeyguardViewMediator#verifyUnlock");
        synchronized (this) {
            boolean z = DEBUG;
            if (z) {
                Log.d("KeyguardViewMediator", "verifyUnlock");
            }
            if (shouldWaitForProvisioning()) {
                if (z) {
                    Log.d("KeyguardViewMediator", "ignoring because device isn't provisioned");
                }
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e);
                }
            } else if (this.mExternallyEnabled) {
                Log.w("KeyguardViewMediator", "verifyUnlock called when not externally disabled");
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e2) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e2);
                }
            } else if (isSecure()) {
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(false);
                } catch (RemoteException e3) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e3);
                }
            } else {
                this.mExternallyEnabled = true;
                this.mNeedToReshowWhenReenabled = false;
                updateInputRestricted();
                try {
                    iKeyguardExitCallback.onKeyguardExitResult(true);
                } catch (RemoteException e4) {
                    Slog.w("KeyguardViewMediator", "Failed to call onKeyguardExitResult(false)", e4);
                }
            }
        }
        Trace.endSection();
    }
}