package com.android.keyguard;

import android.app.admin.IKeyguardCallback;
import android.app.admin.IKeyguardClient;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.SurfaceControlViewHost;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.KeyguardSecurityModel;
import java.util.NoSuchElementException;

/* loaded from: mainsysui33.jar:com/android/keyguard/AdminSecondaryLockScreenController.class */
public class AdminSecondaryLockScreenController {
    public final IKeyguardCallback mCallback;
    public IKeyguardClient mClient;
    public final ServiceConnection mConnection;
    public final Context mContext;
    public Handler mHandler;
    public KeyguardSecurityCallback mKeyguardCallback;
    public final IBinder.DeathRecipient mKeyguardClientDeathRecipient;
    public final ConstraintLayout mParent;
    @VisibleForTesting
    public SurfaceHolder.Callback mSurfaceHolderCallback;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public AdminSecurityView mView;

    /* renamed from: com.android.keyguard.AdminSecondaryLockScreenController$2 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/AdminSecondaryLockScreenController$2.class */
    public class AnonymousClass2 extends IKeyguardCallback.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$WBdouuANIhDEtwtB0KSDRkT2on8(AnonymousClass2 anonymousClass2) {
            anonymousClass2.lambda$onRemoteContentReady$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$mSbM06vdwCMMjQKvXz4jsuDC1bk(AnonymousClass2 anonymousClass2) {
            anonymousClass2.lambda$onDismiss$0();
        }

        public AnonymousClass2() {
            AdminSecondaryLockScreenController.this = r4;
        }

        public /* synthetic */ void lambda$onDismiss$0() {
            AdminSecondaryLockScreenController.this.dismiss(UserHandle.getCallingUserId());
        }

        public /* synthetic */ void lambda$onRemoteContentReady$1() {
            AdminSecondaryLockScreenController.this.dismiss(KeyguardUpdateMonitor.getCurrentUser());
        }

        public void onDismiss() {
            AdminSecondaryLockScreenController.this.mHandler.post(new Runnable() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AdminSecondaryLockScreenController.AnonymousClass2.$r8$lambda$mSbM06vdwCMMjQKvXz4jsuDC1bk(AdminSecondaryLockScreenController.AnonymousClass2.this);
                }
            });
        }

        public void onRemoteContentReady(SurfaceControlViewHost.SurfacePackage surfacePackage) {
            if (AdminSecondaryLockScreenController.this.mHandler != null) {
                AdminSecondaryLockScreenController.this.mHandler.removeCallbacksAndMessages(null);
            }
            if (surfacePackage != null) {
                AdminSecondaryLockScreenController.this.mView.setChildSurfacePackage(surfacePackage);
            } else {
                AdminSecondaryLockScreenController.this.mHandler.post(new Runnable() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$2$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        AdminSecondaryLockScreenController.AnonymousClass2.$r8$lambda$WBdouuANIhDEtwtB0KSDRkT2on8(AdminSecondaryLockScreenController.AnonymousClass2.this);
                    }
                });
            }
        }
    }

    /* renamed from: com.android.keyguard.AdminSecondaryLockScreenController$4 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/AdminSecondaryLockScreenController$4.class */
    public class AnonymousClass4 implements SurfaceHolder.Callback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$wRTXW5h9Q_l2_we862GBJJUaq1E(AnonymousClass4 anonymousClass4, int i) {
            anonymousClass4.lambda$surfaceCreated$0(i);
        }

        public AnonymousClass4() {
            AdminSecondaryLockScreenController.this = r4;
        }

        public /* synthetic */ void lambda$surfaceCreated$0(int i) {
            AdminSecondaryLockScreenController.this.dismiss(i);
            Log.w("AdminSecondaryLockScreenController", "Timed out waiting for secondary lockscreen content.");
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            AdminSecondaryLockScreenController.this.mUpdateMonitor.registerCallback(AdminSecondaryLockScreenController.this.mUpdateCallback);
            if (AdminSecondaryLockScreenController.this.mClient != null) {
                AdminSecondaryLockScreenController.this.onSurfaceReady();
            }
            AdminSecondaryLockScreenController.this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AdminSecondaryLockScreenController.AnonymousClass4.$r8$lambda$wRTXW5h9Q_l2_we862GBJJUaq1E(AdminSecondaryLockScreenController.AnonymousClass4.this, currentUser);
                }
            }, 500L);
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            AdminSecondaryLockScreenController.this.mUpdateMonitor.removeCallback(AdminSecondaryLockScreenController.this.mUpdateCallback);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/AdminSecondaryLockScreenController$AdminSecurityView.class */
    public class AdminSecurityView extends SurfaceView {
        public SurfaceHolder.Callback mSurfaceHolderCallback;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AdminSecurityView(Context context, SurfaceHolder.Callback callback) {
            super(context);
            AdminSecondaryLockScreenController.this = r4;
            this.mSurfaceHolderCallback = callback;
            setZOrderOnTop(true);
        }

        @Override // android.view.SurfaceView, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            getHolder().addCallback(this.mSurfaceHolderCallback);
        }

        @Override // android.view.SurfaceView, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            getHolder().removeCallback(this.mSurfaceHolderCallback);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/AdminSecondaryLockScreenController$Factory.class */
    public static class Factory {
        public final Context mContext;
        public final Handler mHandler;
        public final KeyguardSecurityContainer mParent;
        public final KeyguardUpdateMonitor mUpdateMonitor;

        public Factory(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, Handler handler) {
            this.mContext = context;
            this.mParent = keyguardSecurityContainer;
            this.mUpdateMonitor = keyguardUpdateMonitor;
            this.mHandler = handler;
        }

        public AdminSecondaryLockScreenController create(KeyguardSecurityCallback keyguardSecurityCallback) {
            return new AdminSecondaryLockScreenController(this.mContext, this.mParent, this.mUpdateMonitor, keyguardSecurityCallback, this.mHandler, null);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.AdminSecondaryLockScreenController$$ExternalSyntheticLambda0.binderDied():void] */
    /* renamed from: $r8$lambda$qAfrlb0g71NK6qpAwXaDSRz-5zk */
    public static /* synthetic */ void m519$r8$lambda$qAfrlb0g71NK6qpAwXaDSRz5zk(AdminSecondaryLockScreenController adminSecondaryLockScreenController) {
        adminSecondaryLockScreenController.lambda$new$0();
    }

    public AdminSecondaryLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, Handler handler) {
        this.mConnection = new ServiceConnection() { // from class: com.android.keyguard.AdminSecondaryLockScreenController.1
            {
                AdminSecondaryLockScreenController.this = this;
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                AdminSecondaryLockScreenController.this.mClient = IKeyguardClient.Stub.asInterface(iBinder);
                if (!AdminSecondaryLockScreenController.this.mView.isAttachedToWindow() || AdminSecondaryLockScreenController.this.mClient == null) {
                    return;
                }
                AdminSecondaryLockScreenController.this.onSurfaceReady();
                try {
                    iBinder.linkToDeath(AdminSecondaryLockScreenController.this.mKeyguardClientDeathRecipient, 0);
                } catch (RemoteException e) {
                    Log.e("AdminSecondaryLockScreenController", "Lost connection to secondary lockscreen service", e);
                    AdminSecondaryLockScreenController.this.dismiss(KeyguardUpdateMonitor.getCurrentUser());
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                AdminSecondaryLockScreenController.this.mClient = null;
            }
        };
        this.mKeyguardClientDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.keyguard.AdminSecondaryLockScreenController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                AdminSecondaryLockScreenController.m519$r8$lambda$qAfrlb0g71NK6qpAwXaDSRz5zk(AdminSecondaryLockScreenController.this);
            }
        };
        this.mCallback = new AnonymousClass2();
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.AdminSecondaryLockScreenController.3
            {
                AdminSecondaryLockScreenController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSecondaryLockscreenRequirementChanged(int i) {
                if (AdminSecondaryLockScreenController.this.mUpdateMonitor.getSecondaryLockscreenRequirement(i) == null) {
                    AdminSecondaryLockScreenController.this.dismiss(i);
                }
            }
        };
        this.mSurfaceHolderCallback = new AnonymousClass4();
        this.mContext = context;
        this.mHandler = handler;
        this.mParent = keyguardSecurityContainer;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mKeyguardCallback = keyguardSecurityCallback;
        AdminSecurityView adminSecurityView = new AdminSecurityView(context, this.mSurfaceHolderCallback);
        this.mView = adminSecurityView;
        adminSecurityView.setId(View.generateViewId());
    }

    public /* synthetic */ AdminSecondaryLockScreenController(Context context, KeyguardSecurityContainer keyguardSecurityContainer, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityCallback keyguardSecurityCallback, Handler handler, AdminSecondaryLockScreenController-IA r13) {
        this(context, keyguardSecurityContainer, keyguardUpdateMonitor, keyguardSecurityCallback, handler);
    }

    public /* synthetic */ void lambda$new$0() {
        hide();
        Log.d("AdminSecondaryLockScreenController", "KeyguardClient service died");
    }

    public final void dismiss(int i) {
        this.mHandler.removeCallbacksAndMessages(null);
        if (this.mView.isAttachedToWindow() && i == KeyguardUpdateMonitor.getCurrentUser()) {
            hide();
            KeyguardSecurityCallback keyguardSecurityCallback = this.mKeyguardCallback;
            if (keyguardSecurityCallback != null) {
                keyguardSecurityCallback.dismiss(true, i, true, KeyguardSecurityModel.SecurityMode.Invalid);
            }
        }
    }

    public void hide() {
        if (this.mView.isAttachedToWindow()) {
            this.mParent.removeView(this.mView);
        }
        IKeyguardClient iKeyguardClient = this.mClient;
        if (iKeyguardClient != null) {
            try {
                iKeyguardClient.asBinder().unlinkToDeath(this.mKeyguardClientDeathRecipient, 0);
            } catch (NoSuchElementException e) {
                Log.w("AdminSecondaryLockScreenController", "IKeyguardClient death recipient already released");
            }
            this.mContext.unbindService(this.mConnection);
            this.mClient = null;
        }
    }

    public final void onSurfaceReady() {
        try {
            IBinder hostToken = this.mView.getHostToken();
            if (hostToken != null) {
                this.mClient.onCreateKeyguardSurface(hostToken, this.mCallback);
            } else {
                hide();
            }
        } catch (RemoteException e) {
            Log.e("AdminSecondaryLockScreenController", "Error in onCreateKeyguardSurface", e);
            dismiss(KeyguardUpdateMonitor.getCurrentUser());
        }
    }

    public void show(Intent intent) {
        if (this.mClient == null) {
            this.mContext.bindService(intent, this.mConnection, 1);
        }
        if (this.mView.isAttachedToWindow()) {
            return;
        }
        this.mParent.addView(this.mView);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(this.mParent);
        constraintSet.connect(this.mView.getId(), 3, 0, 3);
        constraintSet.connect(this.mView.getId(), 6, 0, 6);
        constraintSet.connect(this.mView.getId(), 7, 0, 7);
        constraintSet.connect(this.mView.getId(), 4, 0, 4);
        constraintSet.constrainHeight(this.mView.getId(), 0);
        constraintSet.constrainWidth(this.mView.getId(), 0);
        constraintSet.applyTo(this.mParent);
    }
}