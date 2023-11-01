package com.android.systemui.log;

import android.app.StatusBarManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.InstanceIdSequence;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.CoreStartable;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/systemui/log/SessionTracker.class */
public class SessionTracker implements CoreStartable {
    public static final boolean DEBUG = Log.isLoggable("SessionTracker", 3);
    public final AuthController mAuthController;
    public boolean mKeyguardSessionStarted;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final IStatusBarService mStatusBarManagerService;
    public final InstanceIdSequence mInstanceIdGenerator = new InstanceIdSequence(1048576);
    public final Map<Integer, InstanceId> mSessionToInstanceId = new HashMap();
    public KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.log.SessionTracker.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStartedGoingToSleep(int i) {
            if (SessionTracker.this.mKeyguardSessionStarted) {
                SessionTracker.this.endSession(1);
            }
            SessionTracker.this.mKeyguardSessionStarted = true;
            SessionTracker.this.startSession(1);
        }
    };
    public KeyguardStateController.Callback mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.log.SessionTracker.2
        public void onKeyguardShowingChanged() {
            boolean z = SessionTracker.this.mKeyguardSessionStarted;
            boolean isShowing = SessionTracker.this.mKeyguardStateController.isShowing();
            if (isShowing && !z) {
                SessionTracker.this.mKeyguardSessionStarted = true;
                SessionTracker.this.startSession(1);
            } else if (isShowing || !z) {
            } else {
                SessionTracker.this.mKeyguardSessionStarted = false;
                SessionTracker.this.endSession(1);
            }
        }
    };
    public AuthController.Callback mAuthControllerCallback = new AuthController.Callback() { // from class: com.android.systemui.log.SessionTracker.3
        @Override // com.android.systemui.biometrics.AuthController.Callback
        public void onBiometricPromptDismissed() {
            SessionTracker.this.endSession(2);
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public void onBiometricPromptShown() {
            SessionTracker.this.startSession(2);
        }
    };

    public SessionTracker(IStatusBarService iStatusBarService, AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController) {
        this.mStatusBarManagerService = iStatusBarService;
        this.mAuthController = authController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardStateController = keyguardStateController;
    }

    public static String getString(int i) {
        if (i == 1) {
            return "KEYGUARD";
        }
        if (i == 2) {
            return "BIOMETRIC_PROMPT";
        }
        return "unknownType=" + i;
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        for (Integer num : StatusBarManager.ALL_SESSIONS) {
            int intValue = num.intValue();
            printWriter.println("  " + getString(intValue) + " instanceId=" + this.mSessionToInstanceId.get(Integer.valueOf(intValue)));
        }
    }

    public final void endSession(int i) {
        if (this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), null) == null) {
            Log.e("SessionTracker", "session [" + getString(i) + "] was not started");
            return;
        }
        InstanceId instanceId = this.mSessionToInstanceId.get(Integer.valueOf(i));
        this.mSessionToInstanceId.put(Integer.valueOf(i), null);
        try {
            if (DEBUG) {
                Log.d("SessionTracker", "Session end for [" + getString(i) + "] id=" + instanceId);
            }
            this.mStatusBarManagerService.onSessionEnded(i, instanceId);
        } catch (RemoteException e) {
            Log.e("SessionTracker", "Unable to send onSessionEnded for session=[" + getString(i) + "]", e);
        }
    }

    public InstanceId getSessionId(int i) {
        return this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), null);
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
        if (this.mKeyguardStateController.isShowing()) {
            this.mKeyguardSessionStarted = true;
            startSession(1);
        }
    }

    public final void startSession(int i) {
        if (this.mSessionToInstanceId.getOrDefault(Integer.valueOf(i), null) != null) {
            Log.e("SessionTracker", "session [" + getString(i) + "] was already started");
            return;
        }
        InstanceId newInstanceId = this.mInstanceIdGenerator.newInstanceId();
        this.mSessionToInstanceId.put(Integer.valueOf(i), newInstanceId);
        try {
            if (DEBUG) {
                Log.d("SessionTracker", "Session start for [" + getString(i) + "] id=" + newInstanceId);
            }
            this.mStatusBarManagerService.onSessionStarted(i, newInstanceId);
        } catch (RemoteException e) {
            Log.e("SessionTracker", "Unable to send onSessionStarted for session=[" + getString(i) + "]", e);
        }
    }
}