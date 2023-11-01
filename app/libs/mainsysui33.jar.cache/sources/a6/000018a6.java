package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Service;
import android.app.WindowConfiguration;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManagerPolicyConstants;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.SystemUIApplication;
import com.android.wm.shell.transition.ShellTransitions;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardService.class */
public class KeyguardService extends Service {
    public static final int sEnableRemoteKeyguardAnimation;
    public static boolean sEnableRemoteKeyguardGoingAwayAnimation;
    public static boolean sEnableRemoteKeyguardOccludeAnimation;
    public final KeyguardLifecyclesDispatcher mKeyguardLifecyclesDispatcher;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ScreenOnCoordinator mScreenOnCoordinator;
    public final ShellTransitions mShellTransitions;
    public final IRemoteAnimationRunner.Stub mExitAnimationRunner = new IRemoteAnimationRunner.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.2
        public void onAnimationCancelled(boolean z) {
            KeyguardService.this.mKeyguardViewMediator.cancelKeyguardExitAnimation();
        }

        public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            Trace.beginSection("mExitAnimationRunner.onAnimationStart#startKeyguardExitAnimation");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.startKeyguardExitAnimation(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
            Trace.endSection();
        }
    };
    public final IRemoteTransition mOccludeAnimation = new IRemoteTransition.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.3
        public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            transaction.close();
            transitionInfo.releaseAllSurfaces();
        }

        public void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
            transaction.apply();
            KeyguardService.this.mBinder.setOccluded(true, true);
            iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
            transitionInfo.releaseAllSurfaces();
        }
    };
    public final IRemoteTransition mUnoccludeAnimation = new IRemoteTransition.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.4
        public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            transaction.close();
            transitionInfo.releaseAllSurfaces();
        }

        public void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
            transaction.apply();
            KeyguardService.this.mBinder.setOccluded(false, true);
            iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
            transitionInfo.releaseAllSurfaces();
        }
    };
    public final IKeyguardService.Stub mBinder = new IKeyguardService.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.5
        public void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.addStateMonitorCallback(iKeyguardStateCallback);
        }

        public void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.dismiss(iKeyguardDismissCallback, charSequence);
        }

        public void dismissKeyguardToLaunch(Intent intent) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.dismissKeyguardToLaunch(intent);
        }

        public void doKeyguardTimeout(Bundle bundle) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.doKeyguardTimeout(bundle);
        }

        public void onBootCompleted() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onBootCompleted();
        }

        public void onDreamingStarted() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onDreamingStarted();
        }

        public void onDreamingStopped() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onDreamingStopped();
        }

        public void onFinishedGoingToSleep(int i, boolean z) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onFinishedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i), z);
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(7);
        }

        public void onFinishedWakingUp() {
            Trace.beginSection("KeyguardService.mBinder#onFinishedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(5);
            Trace.endSection();
        }

        public void onScreenTurnedOff() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onScreenTurnedOff();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(3);
        }

        public void onScreenTurnedOn() {
            Trace.beginSection("KeyguardService.mBinder#onScreenTurnedOn");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(1);
            KeyguardService.this.mScreenOnCoordinator.onScreenTurnedOn();
            Trace.endSection();
        }

        public void onScreenTurningOff() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(2);
        }

        public void onScreenTurningOn(final IKeyguardDrawnCallback iKeyguardDrawnCallback) {
            Trace.beginSection("KeyguardService.mBinder#onScreenTurningOn");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(0, iKeyguardDrawnCallback);
            final int identityHashCode = System.identityHashCode(iKeyguardDrawnCallback);
            Trace.beginAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
            KeyguardService.this.mScreenOnCoordinator.onScreenTurningOn(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardService.5.1
                public boolean mInvoked;

                @Override // java.lang.Runnable
                public void run() {
                    if (iKeyguardDrawnCallback == null) {
                        return;
                    }
                    if (this.mInvoked) {
                        Log.w("KeyguardService", "KeyguardDrawnCallback#onDrawn() invoked > 1 times");
                        return;
                    }
                    this.mInvoked = true;
                    try {
                        Trace.endAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
                        iKeyguardDrawnCallback.onDrawn();
                    } catch (RemoteException e) {
                        Log.w("KeyguardService", "Exception calling onDrawn():", e);
                    }
                }
            });
            Trace.endSection();
        }

        public void onShortPowerPressedGoHome() {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onShortPowerPressedGoHome();
        }

        public void onStartedGoingToSleep(int i) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onStartedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i));
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(6, i);
        }

        public void onStartedWakingUp(int i, boolean z) {
            Trace.beginSection("KeyguardService.mBinder#onStartedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onStartedWakingUp(i, z);
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(4, i);
            Trace.endSection();
        }

        public void onSystemKeyPressed(int i) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onSystemKeyPressed(i);
        }

        public void onSystemReady() {
            Trace.beginSection("KeyguardService.mBinder#onSystemReady");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onSystemReady();
            Trace.endSection();
        }

        public void setCurrentUser(int i) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setCurrentUser(i);
        }

        public void setKeyguardEnabled(boolean z) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setKeyguardEnabled(z);
        }

        public void setOccluded(boolean z, boolean z2) {
            Log.d("KeyguardService", "setOccluded(" + z + ")");
            Trace.beginSection("KeyguardService.mBinder#setOccluded");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setOccluded(z, z2);
            Trace.endSection();
        }

        public void setSwitchingUser(boolean z) {
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setSwitchingUser(z);
        }

        @Deprecated
        public void startKeyguardExitAnimation(long j, long j2) {
            Trace.beginSection("KeyguardService.mBinder#startKeyguardExitAnimation");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.startKeyguardExitAnimation(j, j2);
            Trace.endSection();
        }

        public void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
            Trace.beginSection("KeyguardService.mBinder#verifyUnlock");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.verifyUnlock(iKeyguardExitCallback);
            Trace.endSection();
        }
    };

    /* renamed from: com.android.systemui.keyguard.KeyguardService$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardService$1.class */
    public class AnonymousClass1 extends IRemoteTransition.Stub {
        public final ArrayMap<IBinder, IRemoteTransitionFinishedCallback> mFinishCallbacks = new ArrayMap<>();
        public final /* synthetic */ IRemoteAnimationRunner val$runner;

        public AnonymousClass1(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$runner = iRemoteAnimationRunner;
        }

        public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            IRemoteTransitionFinishedCallback remove;
            try {
                synchronized (this.mFinishCallbacks) {
                    remove = this.mFinishCallbacks.remove(iBinder);
                }
                transitionInfo.releaseAllSurfaces();
                transaction.close();
                if (remove == null) {
                    return;
                }
                this.val$runner.onAnimationCancelled(false);
                remove.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
            } catch (RemoteException e) {
            }
        }

        public void startAnimation(final IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) throws RemoteException {
            Slog.d("KeyguardService", "Starts IRemoteAnimationRunner: info=" + transitionInfo);
            RemoteAnimationTarget[] wrap = KeyguardService.wrap(transitionInfo, false);
            RemoteAnimationTarget[] wrap2 = KeyguardService.wrap(transitionInfo, true);
            int length = wrap.length;
            boolean z = false;
            for (int i = 0; i < length; i++) {
                RemoteAnimationTarget remoteAnimationTarget = wrap[i];
                if (remoteAnimationTarget.taskId == -1 || remoteAnimationTarget.mode != 0 || remoteAnimationTarget.hasAnimatingParent) {
                    transaction.setAlpha(remoteAnimationTarget.leash, 1.0f);
                } else if (z) {
                    Log.w("KeyguardService", "More than one opening target");
                    transaction.setAlpha(remoteAnimationTarget.leash, 1.0f);
                } else {
                    transaction.setAlpha(remoteAnimationTarget.leash, ActionBarShadowController.ELEVATION_LOW);
                    z = true;
                }
            }
            transaction.apply();
            synchronized (this.mFinishCallbacks) {
                this.mFinishCallbacks.put(iBinder, iRemoteTransitionFinishedCallback);
            }
            this.val$runner.onAnimationStart(KeyguardService.getTransitionOldType(transitionInfo.getType(), transitionInfo.getFlags(), wrap), wrap, wrap2, new RemoteAnimationTarget[0], new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.1.1
                public void onAnimationFinished() throws RemoteException {
                    synchronized (AnonymousClass1.this.mFinishCallbacks) {
                        if (AnonymousClass1.this.mFinishCallbacks.remove(iBinder) == null) {
                            return;
                        }
                        transitionInfo.releaseAllSurfaces();
                        Slog.d("KeyguardService", "Finish IRemoteAnimationRunner.");
                        iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
                    }
                }
            });
        }
    }

    static {
        int i = SystemProperties.getInt("persist.wm.enable_remote_keyguard_animation", 2);
        sEnableRemoteKeyguardAnimation = i;
        sEnableRemoteKeyguardGoingAwayAnimation = i >= 1;
        boolean z = false;
        if (i >= 2) {
            z = true;
        }
        sEnableRemoteKeyguardOccludeAnimation = z;
    }

    public KeyguardService(KeyguardViewMediator keyguardViewMediator, KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher, ScreenOnCoordinator screenOnCoordinator, ShellTransitions shellTransitions) {
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardLifecyclesDispatcher = keyguardLifecyclesDispatcher;
        this.mScreenOnCoordinator = screenOnCoordinator;
        this.mShellTransitions = shellTransitions;
    }

    public static int getTransitionOldType(int i, int i2, RemoteAnimationTarget[] remoteAnimationTargetArr) {
        if (i == 7 || (i2 & RecyclerView.ViewHolder.FLAG_TMP_DETACHED) != 0) {
            return remoteAnimationTargetArr.length == 0 ? 21 : 20;
        } else if (i == 8) {
            boolean z = false;
            if (remoteAnimationTargetArr.length > 0) {
                z = false;
                if (remoteAnimationTargetArr[0].taskInfo.topActivityType == 5) {
                    z = true;
                }
            }
            return z ? 33 : 22;
        } else if (i == 9) {
            return 23;
        } else {
            Slog.d("KeyguardService", "Unexpected transit type: " + i);
            return 0;
        }
    }

    public static int newModeToLegacyMode(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? 2 : 1;
                }
                return 0;
            }
            return 1;
        }
        return 0;
    }

    public static IRemoteTransition wrap(IRemoteAnimationRunner iRemoteAnimationRunner) {
        return new AnonymousClass1(iRemoteAnimationRunner);
    }

    public static RemoteAnimationTarget[] wrap(TransitionInfo transitionInfo, boolean z) {
        boolean z2;
        WindowConfiguration windowConfiguration;
        TransitionInfo.Change change;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            if (z == ((((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getFlags() & 2) != 0)) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                int i2 = taskInfo != null ? change2.getTaskInfo().taskId : -1;
                if (taskInfo != null) {
                    windowConfiguration = taskInfo.getConfiguration() != null ? change2.getTaskInfo().getConfiguration().windowConfiguration : null;
                    z2 = !change2.getTaskInfo().isRunning;
                } else {
                    z2 = true;
                    windowConfiguration = null;
                }
                Rect rect = new Rect(change2.getEndAbsBounds());
                rect.offsetTo(change2.getEndRelOffset().x, change2.getEndRelOffset().y);
                RemoteAnimationTarget remoteAnimationTarget = new RemoteAnimationTarget(i2, newModeToLegacyMode(change2.getMode()), change2.getLeash(), ((change2.getFlags() & 4) == 0 && (change2.getFlags() & 1) == 0) ? false : true, (Rect) null, new Rect(0, 0, 0, 0), transitionInfo.getChanges().size() - i, new Point(), rect, new Rect(change2.getEndAbsBounds()), windowConfiguration, z2, (SurfaceControl) null, change2.getStartAbsBounds(), taskInfo, false);
                if (i2 != -1 && change2.getParent() != null && (change = transitionInfo.getChange(change2.getParent())) != null && change.getTaskInfo() != null) {
                    remoteAnimationTarget.hasAnimatingParent = true;
                }
                arrayList.add(remoteAnimationTarget);
            }
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }

    public void checkPermission() {
        if (Binder.getCallingUid() == 1000 || getBaseContext().checkCallingOrSelfPermission("android.permission.CONTROL_KEYGUARD") == 0) {
            return;
        }
        Log.w("KeyguardService", "Caller needs permission 'android.permission.CONTROL_KEYGUARD' to call " + Debug.getCaller());
        throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission android.permission.CONTROL_KEYGUARD");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public void onCreate() {
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
        if (this.mShellTransitions == null || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            RemoteAnimationDefinition remoteAnimationDefinition = new RemoteAnimationDefinition();
            if (sEnableRemoteKeyguardGoingAwayAnimation) {
                RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(this.mExitAnimationRunner, 0L, 0L);
                remoteAnimationDefinition.addRemoteAnimation(20, remoteAnimationAdapter);
                remoteAnimationDefinition.addRemoteAnimation(21, remoteAnimationAdapter);
            }
            if (sEnableRemoteKeyguardOccludeAnimation) {
                remoteAnimationDefinition.addRemoteAnimation(22, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getOccludeAnimationRunner(), 0L, 0L));
                remoteAnimationDefinition.addRemoteAnimation(33, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getOccludeByDreamAnimationRunner(), 0L, 0L));
                remoteAnimationDefinition.addRemoteAnimation(23, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getUnoccludeAnimationRunner(), 0L, 0L));
            }
            ActivityTaskManager.getInstance().registerRemoteAnimationsForDisplay(0, remoteAnimationDefinition);
            return;
        }
        if (sEnableRemoteKeyguardGoingAwayAnimation) {
            Slog.d("KeyguardService", "KeyguardService registerRemote: TRANSIT_KEYGUARD_GOING_AWAY");
            TransitionFilter transitionFilter = new TransitionFilter();
            transitionFilter.mFlags = RecyclerView.ViewHolder.FLAG_TMP_DETACHED;
            this.mShellTransitions.registerRemote(transitionFilter, new RemoteTransition(wrap(this.mExitAnimationRunner), getIApplicationThread()));
        }
        if (sEnableRemoteKeyguardOccludeAnimation) {
            Slog.d("KeyguardService", "KeyguardService registerRemote: TRANSIT_KEYGUARD_(UN)OCCLUDE");
            RemoteTransition remoteTransition = new RemoteTransition(this.mOccludeAnimation, getIApplicationThread());
            TransitionFilter transitionFilter2 = new TransitionFilter();
            transitionFilter2.mFlags = 64;
            TransitionFilter.Requirement requirement = new TransitionFilter.Requirement();
            TransitionFilter.Requirement[] requirementArr = {new TransitionFilter.Requirement(), requirement};
            transitionFilter2.mRequirements = requirementArr;
            TransitionFilter.Requirement requirement2 = requirementArr[0];
            requirement2.mMustBeIndependent = false;
            requirement2.mFlags = 64;
            requirement2.mModes = new int[]{1, 3};
            requirement.mNot = true;
            requirement.mMustBeIndependent = false;
            requirement.mFlags = 64;
            requirement.mModes = new int[]{2, 4};
            this.mShellTransitions.registerRemote(transitionFilter2, remoteTransition);
            RemoteTransition remoteTransition2 = new RemoteTransition(this.mUnoccludeAnimation, getIApplicationThread());
            TransitionFilter transitionFilter3 = new TransitionFilter();
            transitionFilter3.mFlags = 64;
            TransitionFilter.Requirement requirement3 = new TransitionFilter.Requirement();
            TransitionFilter.Requirement[] requirementArr2 = {new TransitionFilter.Requirement(), requirement3};
            transitionFilter3.mRequirements = requirementArr2;
            requirement3.mMustBeIndependent = false;
            requirement3.mModes = new int[]{2, 4};
            requirement3.mMustBeTask = true;
            TransitionFilter.Requirement requirement4 = requirementArr2[0];
            requirement4.mNot = true;
            requirement4.mMustBeIndependent = false;
            requirement4.mFlags = 64;
            requirement4.mModes = new int[]{1, 3};
            this.mShellTransitions.registerRemote(transitionFilter3, remoteTransition2);
            TransitionFilter transitionFilter4 = new TransitionFilter();
            transitionFilter4.mTypeSet = new int[]{8};
            this.mShellTransitions.registerRemote(transitionFilter4, remoteTransition);
            TransitionFilter transitionFilter5 = new TransitionFilter();
            transitionFilter5.mTypeSet = new int[]{9};
            this.mShellTransitions.registerRemote(transitionFilter5, remoteTransition2);
            Slog.d("KeyguardService", "KeyguardService registerRemote: TRANSIT_KEYGUARD_OCCLUDE for DREAM");
            TransitionFilter transitionFilter6 = new TransitionFilter();
            transitionFilter6.mFlags = 64;
            TransitionFilter.Requirement requirement5 = new TransitionFilter.Requirement();
            TransitionFilter.Requirement[] requirementArr3 = {new TransitionFilter.Requirement(), requirement5};
            transitionFilter6.mRequirements = requirementArr3;
            TransitionFilter.Requirement requirement6 = requirementArr3[0];
            requirement6.mActivityType = 5;
            requirement6.mMustBeIndependent = false;
            requirement6.mFlags = 64;
            requirement6.mModes = new int[]{1, 3};
            requirement5.mNot = true;
            requirement5.mMustBeIndependent = false;
            requirement5.mFlags = 64;
            requirement5.mModes = new int[]{2, 4};
            this.mShellTransitions.registerRemote(transitionFilter6, new RemoteTransition(wrap(this.mKeyguardViewMediator.getOccludeByDreamAnimationRunner()), getIApplicationThread()));
        }
    }
}