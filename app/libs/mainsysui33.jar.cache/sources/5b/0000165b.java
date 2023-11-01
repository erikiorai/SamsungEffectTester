package com.android.systemui.doze;

import android.os.Handler;
import android.util.Log;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeScreenState.class */
public class DozeScreenState implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final AuthController mAuthController;
    public final AuthController.Callback mAuthControllerCallback;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final DozeScreenBrightness mDozeScreenBrightness;
    public final DozeMachine.Service mDozeService;
    public final Handler mHandler;
    public final DozeParameters mParameters;
    public UdfpsController mUdfpsController;
    public final Provider<UdfpsController> mUdfpsControllerProvider;
    public SettableWakeLock mWakeLock;
    public final Runnable mApplyPendingScreenState = new Runnable() { // from class: com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            DozeScreenState.$r8$lambda$njd9_4eogrifOoXmQqJ1NBnHD5U(DozeScreenState.this);
        }
    };
    public int mPendingScreenState = 0;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$3qjB_EtpkzWD-Hl-Rw1njM-F0H8 */
    public static /* synthetic */ void m2477$r8$lambda$3qjB_EtpkzWDHlRw1njMF0H8(DozeScreenState dozeScreenState, int i) {
        dozeScreenState.lambda$transitionTo$0(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$njd9_4eogrifOoXmQqJ1NBnHD5U(DozeScreenState dozeScreenState) {
        dozeScreenState.applyPendingScreenState();
    }

    public DozeScreenState(DozeMachine.Service service, Handler handler, DozeHost dozeHost, DozeParameters dozeParameters, WakeLock wakeLock, AuthController authController, Provider<UdfpsController> provider, DozeLog dozeLog, DozeScreenBrightness dozeScreenBrightness) {
        AuthController.Callback callback = new AuthController.Callback() { // from class: com.android.systemui.doze.DozeScreenState.1
            {
                DozeScreenState.this = this;
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onAllAuthenticatorsRegistered() {
                DozeScreenState.this.updateUdfpsController();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onEnrollmentsChanged() {
                DozeScreenState.this.updateUdfpsController();
            }
        };
        this.mAuthControllerCallback = callback;
        this.mDozeService = service;
        this.mHandler = handler;
        this.mParameters = dozeParameters;
        this.mDozeHost = dozeHost;
        this.mWakeLock = new SettableWakeLock(wakeLock, "DozeScreenState");
        this.mAuthController = authController;
        this.mUdfpsControllerProvider = provider;
        this.mDozeLog = dozeLog;
        this.mDozeScreenBrightness = dozeScreenBrightness;
        updateUdfpsController();
        if (this.mUdfpsController == null) {
            authController.addCallback(callback);
        }
    }

    public final void applyPendingScreenState() {
        UdfpsController udfpsController = this.mUdfpsController;
        if (udfpsController == null || !udfpsController.isFingerDown()) {
            lambda$transitionTo$0(this.mPendingScreenState);
            this.mPendingScreenState = 0;
            return;
        }
        this.mDozeLog.traceDisplayStateDelayedByUdfps(this.mPendingScreenState);
        this.mHandler.postDelayed(this.mApplyPendingScreenState, 1200L);
    }

    /* renamed from: applyScreenState */
    public final void lambda$transitionTo$0(int i) {
        if (i != 0) {
            if (DEBUG) {
                Log.d("DozeScreenState", "setDozeScreenState(" + i + ")");
            }
            this.mDozeService.setDozeScreenState(i);
            if (i == 3) {
                this.mDozeScreenBrightness.updateBrightnessAndReady(false);
            }
            this.mPendingScreenState = 0;
            this.mWakeLock.setAcquired(false);
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void destroy() {
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        final int screenState = state2.screenState(this.mParameters);
        this.mDozeHost.cancelGentleSleep();
        if (state2 == DozeMachine.State.FINISH) {
            this.mPendingScreenState = 0;
            this.mHandler.removeCallbacks(this.mApplyPendingScreenState);
            lambda$transitionTo$0(screenState);
            this.mWakeLock.setAcquired(false);
        } else if (screenState == 0) {
        } else {
            boolean hasCallbacks = this.mHandler.hasCallbacks(this.mApplyPendingScreenState);
            boolean z = state == DozeMachine.State.DOZE_PULSE_DONE && state2.isAlwaysOn();
            DozeMachine.State state3 = DozeMachine.State.DOZE_AOD_PAUSED;
            boolean z2 = (state == state3 || state == DozeMachine.State.DOZE) && state2.isAlwaysOn();
            boolean z3 = (state.isAlwaysOn() && state2 == DozeMachine.State.DOZE) || (state == DozeMachine.State.DOZE_AOD_PAUSING && state2 == state3);
            boolean z4 = state == DozeMachine.State.INITIALIZED;
            if (!hasCallbacks && !z4 && !z && !z2) {
                if (z3) {
                    this.mDozeHost.prepareForGentleSleep(new Runnable() { // from class: com.android.systemui.doze.DozeScreenState$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            DozeScreenState.m2477$r8$lambda$3qjB_EtpkzWDHlRw1njMF0H8(DozeScreenState.this, screenState);
                        }
                    });
                    return;
                } else {
                    lambda$transitionTo$0(screenState);
                    return;
                }
            }
            this.mPendingScreenState = screenState;
            DozeMachine.State state4 = DozeMachine.State.DOZE_AOD;
            boolean z5 = state2 == state4 && this.mParameters.shouldDelayDisplayDozeTransition() && !z2;
            boolean z6 = false;
            if (state2 == state4) {
                UdfpsController udfpsController = this.mUdfpsController;
                z6 = false;
                if (udfpsController != null) {
                    z6 = false;
                    if (udfpsController.isFingerDown()) {
                        z6 = true;
                    }
                }
            }
            if (!hasCallbacks) {
                if (DEBUG) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("Display state changed to ");
                    sb.append(screenState);
                    sb.append(" delayed by ");
                    sb.append(z5 ? 4000 : 1);
                    Log.d("DozeScreenState", sb.toString());
                }
                if (z5) {
                    if (z4) {
                        lambda$transitionTo$0(2);
                        this.mPendingScreenState = screenState;
                    }
                    this.mHandler.postDelayed(this.mApplyPendingScreenState, InternetDialogController.SHORT_DURATION_TIMEOUT);
                } else if (z6) {
                    this.mDozeLog.traceDisplayStateDelayedByUdfps(this.mPendingScreenState);
                    this.mHandler.postDelayed(this.mApplyPendingScreenState, 1200L);
                } else {
                    this.mHandler.post(this.mApplyPendingScreenState);
                }
            } else if (DEBUG) {
                Log.d("DozeScreenState", "Pending display state change to " + screenState);
            }
            if (z5 || z6) {
                this.mWakeLock.setAcquired(true);
            }
        }
    }

    public final void updateUdfpsController() {
        if (this.mAuthController.isUdfpsEnrolled(KeyguardUpdateMonitor.getCurrentUser())) {
            this.mUdfpsController = (UdfpsController) this.mUdfpsControllerProvider.get();
        } else {
            this.mUdfpsController = null;
        }
    }
}