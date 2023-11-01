package com.android.systemui.biometrics;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.TaskStackListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricStateListener;
import android.hardware.biometrics.IBiometricContextListener;
import android.hardware.biometrics.IBiometricSysuiReceiver;
import android.hardware.biometrics.PromptInfo;
import android.hardware.display.DisplayManager;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.IUdfpsHbmListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.UserManager;
import android.util.DisplayUtils;
import android.util.Log;
import android.util.RotationUtils;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.os.SomeArgs;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.CoreStartable;
import com.android.systemui.R$array;
import com.android.systemui.R$dimen;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController.class */
public class AuthController implements CoreStartable, CommandQueue.Callbacks, AuthDialogCallback, DozeReceiver {
    public final ActivityTaskManager mActivityTaskManager;
    public boolean mAllFingerprintAuthenticatorsRegistered;
    public final DelayableExecutor mBackgroundExecutor;
    public IBiometricContextListener mBiometricContextListener;
    public final BiometricStateListener mBiometricStateListener;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastReceiver;
    public final DisplayInfo mCachedDisplayInfo;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    @VisibleForTesting
    public AuthDialog mCurrentDialog;
    public SomeArgs mCurrentDialogArgs;
    public final Display mDisplay;
    public final DisplayManager mDisplayManager;
    public final Execution mExecution;
    public final FaceManager mFaceManager;
    public final List<FaceSensorPropertiesInternal> mFaceProps;
    public Point mFaceSensorLocation;
    public final Point mFaceSensorLocationDefault;
    public final IFingerprintAuthenticatorsRegisteredCallback mFingerprintAuthenticatorsRegisteredCallback;
    public final FingerprintManager mFingerprintManager;
    public Point mFingerprintSensorLocation;
    public final Point mFingerprintSensorLocationDefault;
    public List<FingerprintSensorPropertiesInternal> mFpProps;
    public final Handler mHandler;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final LockPatternUtils mLockPatternUtils;
    @VisibleForTesting
    public final BiometricDisplayListener mOrientationListener;
    @VisibleForTesting
    public IBiometricSysuiReceiver mReceiver;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final SparseBooleanArray mSfpsEnrolledForUser;
    public SideFpsController mSideFpsController;
    public final Provider<SideFpsController> mSidefpsControllerFactory;
    public List<FingerprintSensorPropertiesInternal> mSidefpsProps;
    public final StatusBarStateController mStatusBarStateController;
    @VisibleForTesting
    public final TaskStackListener mTaskStackListener;
    public Rect mUdfpsBounds;
    public UdfpsController mUdfpsController;
    public final Provider<UdfpsController> mUdfpsControllerFactory;
    public final SparseBooleanArray mUdfpsEnrolledForUser;
    public IUdfpsHbmListener mUdfpsHbmListener;
    public List<FingerprintSensorPropertiesInternal> mUdfpsProps;
    public final UserManager mUserManager;
    public final VibratorHelper mVibratorHelper;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WindowManager mWindowManager;
    public float mScaleFactor = 1.0f;
    public final Set<Callback> mCallbacks = new HashSet();

    /* renamed from: com.android.systemui.biometrics.AuthController$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController$1.class */
    public class AnonymousClass1 extends TaskStackListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$1$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$RCtJs7DdoQxRWxWqPjzq_63_jxM(AuthController authController) {
            AuthController.m1530$$Nest$mcancelIfOwnerIsNotInForeground(authController);
        }

        public AnonymousClass1() {
            AuthController.this = r4;
        }

        public void onTaskStackChanged() {
            Handler handler = AuthController.this.mHandler;
            final AuthController authController = AuthController.this;
            handler.post(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AuthController.AnonymousClass1.$r8$lambda$RCtJs7DdoQxRWxWqPjzq_63_jxM(AuthController.this);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.biometrics.AuthController$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController$2.class */
    public class AnonymousClass2 extends IFingerprintAuthenticatorsRegisteredCallback.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$2$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$c6N7T47bTH867uj7v68eDR19vhw(AnonymousClass2 anonymousClass2, List list) {
            anonymousClass2.lambda$onAllAuthenticatorsRegistered$0(list);
        }

        public AnonymousClass2() {
            AuthController.this = r4;
        }

        public /* synthetic */ void lambda$onAllAuthenticatorsRegistered$0(List list) {
            AuthController.this.handleAllFingerprintAuthenticatorsRegistered(list);
        }

        public void onAllAuthenticatorsRegistered(final List<FingerprintSensorPropertiesInternal> list) {
            AuthController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AuthController.AnonymousClass2.$r8$lambda$c6N7T47bTH867uj7v68eDR19vhw(AuthController.AnonymousClass2.this, list);
                }
            });
        }
    }

    /* renamed from: com.android.systemui.biometrics.AuthController$3 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController$3.class */
    public class AnonymousClass3 extends BiometricStateListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$3$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$-LaINYUc3tWSR8zjM-X4MK1LI2Y */
        public static /* synthetic */ void m1534$r8$lambda$LaINYUc3tWSR8zjMX4MK1LI2Y(AnonymousClass3 anonymousClass3, int i, int i2, boolean z) {
            anonymousClass3.lambda$onEnrollmentsChanged$0(i, i2, z);
        }

        public AnonymousClass3() {
            AuthController.this = r4;
        }

        public /* synthetic */ void lambda$onEnrollmentsChanged$0(int i, int i2, boolean z) {
            AuthController.this.handleEnrollmentsChanged(i, i2, z);
        }

        public void onEnrollmentsChanged(final int i, final int i2, final boolean z) {
            AuthController.this.mHandler.post(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$3$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AuthController.AnonymousClass3.m1534$r8$lambda$LaINYUc3tWSR8zjMX4MK1LI2Y(AuthController.AnonymousClass3.this, i, i2, z);
                }
            });
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController$Callback.class */
    public interface Callback {
        default void onAllAuthenticatorsRegistered() {
        }

        default void onBiometricPromptDismissed() {
        }

        default void onBiometricPromptShown() {
        }

        default void onEnrollmentsChanged() {
        }

        default void onFaceSensorLocationChanged() {
        }

        default void onFingerprintLocationChanged() {
        }

        default void onUdfpsLocationChanged() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthController$ScaleFactorProvider.class */
    public interface ScaleFactorProvider {
        float provide();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda2.provide():float] */
    public static /* synthetic */ float $r8$lambda$CzJI3lBA48ELgdlri3pJENOGJiU(AuthController authController) {
        return authController.lambda$buildDialog$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$GeOHnzHEf2hgIh0QyCCtF7qo904(AuthController authController) {
        authController.updateUdfpsLocation();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda3.run():void] */
    /* renamed from: $r8$lambda$KCCqu7mug3eoI7zRt1qNjYhCY-Y */
    public static /* synthetic */ void m1525$r8$lambda$KCCqu7mug3eoI7zRt1qNjYhCYY(AuthController authController, int i) {
        authController.lambda$onBiometricError$1(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$k06pSb32I7QlAkRH4q3j8z64dRs(AuthController authController) {
        authController.cancelIfOwnerIsNotInForeground();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda0.invoke():java.lang.Object] */
    /* renamed from: $r8$lambda$uLGZf-GX0HBBLoGLSvBVj74dsYw */
    public static /* synthetic */ Unit m1526$r8$lambda$uLGZfGX0HBBLoGLSvBVj74dsYw(AuthController authController) {
        return authController.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthController.1.$r8$lambda$RCtJs7DdoQxRWxWqPjzq_63_jxM(com.android.systemui.biometrics.AuthController):void, com.android.systemui.biometrics.AuthController.1.$r8$lambda$RCtJs7DdoQxRWxWqPjzq_63_jxM(com.android.systemui.biometrics.AuthController):void] */
    /* renamed from: -$$Nest$mcancelIfOwnerIsNotInForeground */
    public static /* bridge */ /* synthetic */ void m1530$$Nest$mcancelIfOwnerIsNotInForeground(AuthController authController) {
        authController.cancelIfOwnerIsNotInForeground();
    }

    public AuthController(Context context, Execution execution, CommandQueue commandQueue, ActivityTaskManager activityTaskManager, WindowManager windowManager, FingerprintManager fingerprintManager, FaceManager faceManager, Provider<UdfpsController> provider, Provider<SideFpsController> provider2, DisplayManager displayManager, final WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils, StatusBarStateController statusBarStateController, InteractionJankMonitor interactionJankMonitor, Handler handler, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mCachedDisplayInfo = displayInfo;
        this.mTaskStackListener = new AnonymousClass1();
        this.mFingerprintAuthenticatorsRegisteredCallback = new AnonymousClass2();
        this.mBiometricStateListener = new AnonymousClass3();
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.AuthController.4
            {
                AuthController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                if (AuthController.this.mCurrentDialog == null || !"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    return;
                }
                String stringExtra = intent.getStringExtra("reason");
                if (stringExtra == null) {
                    stringExtra = "unknown";
                }
                Log.d("AuthController", "ACTION_CLOSE_SYSTEM_DIALOGS received, reason: " + stringExtra);
                AuthController.this.mCurrentDialog.dismissWithoutCallback(true);
                AuthController authController = AuthController.this;
                authController.mCurrentDialog = null;
                for (Callback callback : authController.mCallbacks) {
                    callback.onBiometricPromptDismissed();
                }
                try {
                    IBiometricSysuiReceiver iBiometricSysuiReceiver = AuthController.this.mReceiver;
                    if (iBiometricSysuiReceiver != null) {
                        iBiometricSysuiReceiver.onDialogDismissed(3, (byte[]) null);
                        AuthController.this.mReceiver = null;
                    }
                } catch (RemoteException e) {
                    Log.e("AuthController", "Remote exception", e);
                }
            }
        };
        this.mContext = context;
        this.mExecution = execution;
        this.mUserManager = userManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mHandler = handler;
        this.mBackgroundExecutor = delayableExecutor;
        this.mCommandQueue = commandQueue;
        this.mActivityTaskManager = activityTaskManager;
        this.mFingerprintManager = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mUdfpsControllerFactory = provider;
        this.mSidefpsControllerFactory = provider2;
        this.mDisplayManager = displayManager;
        this.mWindowManager = windowManager;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mUdfpsEnrolledForUser = new SparseBooleanArray();
        this.mSfpsEnrolledForUser = new SparseBooleanArray();
        this.mVibratorHelper = vibratorHelper;
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.Generic.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda0
            public final Object invoke() {
                return AuthController.m1526$r8$lambda$uLGZfGX0HBBLoGLSvBVj74dsYw(AuthController.this);
            }
        });
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        wakefulnessLifecycle.addObserver(new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.biometrics.AuthController.6
            {
                AuthController.this = this;
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedWakingUp() {
                AuthController authController = AuthController.this;
                authController.notifyDozeChanged(authController.mStatusBarStateController.isDozing(), 2);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedGoingToSleep() {
                AuthController authController = AuthController.this;
                authController.notifyDozeChanged(authController.mStatusBarStateController.isDozing(), 3);
            }
        });
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(new StatusBarStateController.StateListener() { // from class: com.android.systemui.biometrics.AuthController.7
            {
                AuthController.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozingChanged(boolean z) {
                AuthController.this.notifyDozeChanged(z, wakefulnessLifecycle.getWakefulness());
            }
        });
        this.mFaceProps = faceManager != null ? faceManager.getSensorPropertiesInternal() : null;
        int[] intArray = context.getResources().getIntArray(R$array.config_face_auth_props);
        if (intArray == null || intArray.length < 2) {
            this.mFaceSensorLocationDefault = null;
        } else {
            this.mFaceSensorLocationDefault = new Point(intArray[0], intArray[1]);
        }
        Display display = context.getDisplay();
        this.mDisplay = display;
        display.getDisplayInfo(displayInfo);
        int naturalWidth = displayInfo.getNaturalWidth() / 2;
        try {
            naturalWidth = context.getResources().getDimensionPixelSize(R$dimen.physical_fingerprint_sensor_center_screen_location_x);
        } catch (Resources.NotFoundException e) {
        }
        this.mFingerprintSensorLocationDefault = new Point(naturalWidth, this.mContext.getResources().getDimensionPixelSize(R$dimen.physical_fingerprint_sensor_center_screen_location_y));
        updateSensorLocations();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(this.mBroadcastReceiver, intentFilter, 2);
        this.mSensorPrivacyManager = (SensorPrivacyManager) context.getSystemService(SensorPrivacyManager.class);
    }

    public /* synthetic */ Unit lambda$new$0() {
        onOrientationChanged();
        return Unit.INSTANCE;
    }

    public /* synthetic */ void lambda$onBiometricError$1(int i) {
        this.mCurrentDialog.onAuthenticationFailed(i, this.mContext.getString(17040324));
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public boolean areAllFingerprintAuthenticatorsRegistered() {
        return this.mAllFingerprintAuthenticatorsRegistered;
    }

    public AuthDialog buildDialog(DelayableExecutor delayableExecutor, PromptInfo promptInfo, boolean z, int i, int[] iArr, String str, boolean z2, long j, long j2, int i2, WakefulnessLifecycle wakefulnessLifecycle, UserManager userManager, LockPatternUtils lockPatternUtils) {
        return new AuthContainerView.Builder(this.mContext).setCallback(this).setPromptInfo(promptInfo).setRequireConfirmation(z).setUserId(i).setOpPackageName(str).setSkipIntro(z2).setOperationId(j).setRequestId(j2).setMultiSensorConfig(i2).setScaleFactorProvider(new ScaleFactorProvider() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda2
            @Override // com.android.systemui.biometrics.AuthController.ScaleFactorProvider
            public final float provide() {
                return AuthController.$r8$lambda$CzJI3lBA48ELgdlri3pJENOGJiU(AuthController.this);
            }
        }).build(delayableExecutor, iArr, this.mFpProps, this.mFaceProps, wakefulnessLifecycle, userManager, lockPatternUtils, this.mInteractionJankMonitor);
    }

    public final void cancelIfOwnerIsNotInForeground() {
        this.mExecution.assertIsMainThread();
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            try {
                String opPackageName = authDialog.getOpPackageName();
                Log.w("AuthController", "Task stack changed, current client: " + opPackageName);
                List tasks = this.mActivityTaskManager.getTasks(1);
                if (tasks.isEmpty()) {
                    return;
                }
                String packageName = ((ActivityManager.RunningTaskInfo) tasks.get(0)).topActivity.getPackageName();
                if (packageName.contentEquals(opPackageName) || Utils.isSystem(this.mContext, opPackageName)) {
                    return;
                }
                Log.e("AuthController", "Evicting client due to: " + packageName);
                this.mCurrentDialog.dismissWithoutCallback(true);
                this.mCurrentDialog = null;
                for (Callback callback : this.mCallbacks) {
                    callback.onBiometricPromptDismissed();
                }
                IBiometricSysuiReceiver iBiometricSysuiReceiver = this.mReceiver;
                if (iBiometricSysuiReceiver != null) {
                    iBiometricSysuiReceiver.onDialogDismissed(3, (byte[]) null);
                    this.mReceiver = null;
                }
            } catch (RemoteException e) {
                Log.e("AuthController", "Remote exception", e);
            }
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public void dozeTimeTick() {
        UdfpsController udfpsController = this.mUdfpsController;
        if (udfpsController != null) {
            udfpsController.dozeTimeTick();
        }
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        AuthDialog authDialog = this.mCurrentDialog;
        printWriter.println("  mCachedDisplayInfo=" + this.mCachedDisplayInfo);
        printWriter.println("  mScaleFactor=" + this.mScaleFactor);
        printWriter.println("  faceAuthSensorLocationDefault=" + this.mFaceSensorLocationDefault);
        printWriter.println("  faceAuthSensorLocation=" + getFaceSensorLocation());
        printWriter.println("  fingerprintSensorLocationDefault=" + this.mFingerprintSensorLocationDefault);
        printWriter.println("  fingerprintSensorLocationInNaturalOrientation=" + getFingerprintSensorLocationInNaturalOrientation());
        printWriter.println("  fingerprintSensorLocation=" + getFingerprintSensorLocation());
        printWriter.println("  udfpsBounds=" + this.mUdfpsBounds);
        printWriter.println("  allFingerprintAuthenticatorsRegistered=" + this.mAllFingerprintAuthenticatorsRegistered);
        printWriter.println("  currentDialog=" + authDialog);
        if (authDialog != null) {
            authDialog.dump(printWriter, strArr);
        }
    }

    public final IBiometricSysuiReceiver getCurrentReceiver(long j) {
        if (isRequestIdValid(j)) {
            if (this.mReceiver == null) {
                Log.w("AuthController", "getCurrentReceiver: Receiver is null");
            }
            return this.mReceiver;
        }
        return null;
    }

    public final String getErrorString(int i, int i2, int i3) {
        return i != 2 ? i != 8 ? "" : FaceManager.getErrorString(this.mContext, i2, i3) : FingerprintManager.getErrorString(this.mContext, i2, i3);
    }

    public Point getFaceSensorLocation() {
        return this.mFaceSensorLocation;
    }

    public Point getFingerprintSensorLocation() {
        return this.mFingerprintSensorLocation;
    }

    public final Point getFingerprintSensorLocationInNaturalOrientation() {
        Point point;
        if (getUdfpsLocation() != null) {
            return getUdfpsLocation();
        }
        float f = this.mFingerprintSensorLocationDefault.x;
        float f2 = this.mScaleFactor;
        return new Point((int) (f * f2), (int) (point.y * f2));
    }

    /* renamed from: getScaleFactor */
    public float lambda$buildDialog$2() {
        return this.mScaleFactor;
    }

    public List<FingerprintSensorPropertiesInternal> getSfpsProps() {
        return this.mSidefpsProps;
    }

    public IUdfpsHbmListener getUdfpsHbmListener() {
        return this.mUdfpsHbmListener;
    }

    public Point getUdfpsLocation() {
        if (this.mUdfpsController == null || this.mUdfpsBounds == null) {
            return null;
        }
        return new Point(this.mUdfpsBounds.centerX(), this.mUdfpsBounds.centerY());
    }

    public List<FingerprintSensorPropertiesInternal> getUdfpsProps() {
        return this.mUdfpsProps;
    }

    public float getUdfpsRadius() {
        Rect rect;
        if (this.mUdfpsController == null || (rect = this.mUdfpsBounds) == null) {
            return -1.0f;
        }
        return rect.height() / 2.0f;
    }

    public final void handleAllFingerprintAuthenticatorsRegistered(List<FingerprintSensorPropertiesInternal> list) {
        this.mExecution.assertIsMainThread();
        Log.d("AuthController", "handleAllAuthenticatorsRegistered | sensors: " + Arrays.toString(list.toArray()));
        this.mAllFingerprintAuthenticatorsRegistered = true;
        this.mFpProps = list;
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : this.mFpProps) {
            if (fingerprintSensorPropertiesInternal.isAnyUdfpsType()) {
                arrayList.add(fingerprintSensorPropertiesInternal);
            }
            if (fingerprintSensorPropertiesInternal.isAnySidefpsType()) {
                arrayList2.add(fingerprintSensorPropertiesInternal);
            }
        }
        if (arrayList.isEmpty()) {
            arrayList = null;
        }
        this.mUdfpsProps = arrayList;
        if (arrayList != null) {
            UdfpsController udfpsController = (UdfpsController) this.mUdfpsControllerFactory.get();
            this.mUdfpsController = udfpsController;
            udfpsController.addCallback(new UdfpsController.Callback() { // from class: com.android.systemui.biometrics.AuthController.5
                {
                    AuthController.this = this;
                }

                @Override // com.android.systemui.biometrics.UdfpsController.Callback
                public void onFingerDown() {
                    AuthDialog authDialog = AuthController.this.mCurrentDialog;
                    if (authDialog != null) {
                        authDialog.onPointerDown();
                    }
                }

                @Override // com.android.systemui.biometrics.UdfpsController.Callback
                public void onFingerUp() {
                }
            });
            this.mUdfpsController.setAuthControllerUpdateUdfpsLocation(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    AuthController.$r8$lambda$GeOHnzHEf2hgIh0QyCCtF7qo904(AuthController.this);
                }
            });
            this.mUdfpsController.setUdfpsDisplayMode(new UdfpsDisplayMode(this.mContext, this.mExecution, this));
            this.mUdfpsBounds = this.mUdfpsProps.get(0).getLocation().getRect();
        }
        ArrayList arrayList3 = !arrayList2.isEmpty() ? arrayList2 : null;
        this.mSidefpsProps = arrayList3;
        if (arrayList3 != null) {
            this.mSideFpsController = (SideFpsController) this.mSidefpsControllerFactory.get();
        }
        updateSensorLocations();
        this.mFingerprintManager.registerBiometricStateListener(this.mBiometricStateListener);
        for (Callback callback : this.mCallbacks) {
            callback.onAllAuthenticatorsRegistered();
        }
    }

    public final void handleEnrollmentsChanged(int i, int i2, boolean z) {
        this.mExecution.assertIsMainThread();
        Log.d("AuthController", "handleEnrollmentsChanged, userId: " + i + ", sensorId: " + i2 + ", hasEnrollments: " + z);
        List<FingerprintSensorPropertiesInternal> list = this.mUdfpsProps;
        if (list == null) {
            Log.d("AuthController", "handleEnrollmentsChanged, mUdfpsProps is null");
        } else {
            for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal : list) {
                if (fingerprintSensorPropertiesInternal.sensorId == i2) {
                    this.mUdfpsEnrolledForUser.put(i, z);
                }
            }
        }
        List<FingerprintSensorPropertiesInternal> list2 = this.mSidefpsProps;
        if (list2 == null) {
            Log.d("AuthController", "handleEnrollmentsChanged, mSidefpsProps is null");
        } else {
            for (FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal2 : list2) {
                if (fingerprintSensorPropertiesInternal2.sensorId == i2) {
                    this.mSfpsEnrolledForUser.put(i, z);
                }
            }
        }
        for (Callback callback : this.mCallbacks) {
            callback.onEnrollmentsChanged();
        }
    }

    public void hideAuthenticationDialog(long j) {
        Log.d("AuthController", "hideAuthenticationDialog: " + this.mCurrentDialog);
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.d("AuthController", "dialog already gone");
        } else if (j == authDialog.getRequestId()) {
            this.mCurrentDialog.dismissFromSystemServer();
            this.mCurrentDialog = null;
        } else {
            Log.w("AuthController", "ignore - ids do not match: " + j + " current: " + this.mCurrentDialog.getRequestId());
        }
    }

    public final boolean isRequestIdValid(long j) {
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "shouldNotifyReceiver: dialog already gone");
            return false;
        } else if (j != authDialog.getRequestId()) {
            Log.w("AuthController", "shouldNotifyReceiver: requestId doesn't match");
            return false;
        } else {
            return true;
        }
    }

    public boolean isSfpsEnrolled(int i) {
        if (this.mSideFpsController == null) {
            return false;
        }
        return this.mSfpsEnrolledForUser.get(i);
    }

    public boolean isUdfpsEnrolled(int i) {
        if (this.mUdfpsController == null) {
            return false;
        }
        return this.mUdfpsEnrolledForUser.get(i);
    }

    public boolean isUdfpsFingerDown() {
        UdfpsController udfpsController = this.mUdfpsController;
        if (udfpsController == null) {
            return false;
        }
        return udfpsController.isFingerDown();
    }

    public final void notifyDozeChanged(boolean z, int i) {
        IBiometricContextListener iBiometricContextListener = this.mBiometricContextListener;
        if (iBiometricContextListener != null) {
            try {
                iBiometricContextListener.onDozeChanged(z, i == 2);
            } catch (RemoteException e) {
                Log.w("AuthController", "failed to notify initial doze state");
            }
        }
    }

    public void onAodInterrupt(int i, int i2, float f, float f2) {
        UdfpsController udfpsController = this.mUdfpsController;
        if (udfpsController == null) {
            return;
        }
        udfpsController.onAodInterrupt(i, i2, f, f2);
    }

    public void onBiometricAuthenticated(int i) {
        Log.d("AuthController", "onBiometricAuthenticated: ");
        vibrateSuccess(i);
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            authDialog.onAuthenticationSucceeded(i);
        } else {
            Log.w("AuthController", "onBiometricAuthenticated callback but dialog gone");
        }
    }

    public void onBiometricError(final int i, int i2, int i3) {
        boolean z = false;
        Log.d("AuthController", String.format("onBiometricError(%d, %d, %d)", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)));
        vibrateError(i);
        boolean z2 = i2 == 7 || i2 == 9;
        boolean z3 = i2 == 1 && this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
        if (i2 == 100 || i2 == 3 || z3) {
            z = true;
        }
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog == null) {
            Log.w("AuthController", "onBiometricError callback but dialog is gone");
        } else if (authDialog.isAllowDeviceCredentials() && z2) {
            Log.d("AuthController", "onBiometricError, lockout");
            this.mCurrentDialog.animateToCredentialUI();
        } else if (!z) {
            String errorString = getErrorString(i, i2, i3);
            Log.d("AuthController", "onBiometricError, hard error: " + errorString);
            this.mCurrentDialog.onError(i, errorString);
        } else {
            String string = i2 == 100 ? this.mContext.getString(17039821) : getErrorString(i, i2, i3);
            Log.d("AuthController", "onBiometricError, soft error: " + string);
            if (z3) {
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        AuthController.m1525$r8$lambda$KCCqu7mug3eoI7zRt1qNjYhCYY(AuthController.this, i);
                    }
                }, 500L);
            } else {
                this.mCurrentDialog.onAuthenticationFailed(i, string);
            }
        }
    }

    public void onBiometricHelp(int i, String str) {
        Log.d("AuthController", "onBiometricHelp: " + str);
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            authDialog.onHelp(i, str);
        } else {
            Log.w("AuthController", "onBiometricHelp callback but dialog gone");
        }
    }

    @Override // com.android.systemui.CoreStartable
    public void onConfigurationChanged(Configuration configuration) {
        updateSensorLocations();
        if (this.mCurrentDialog != null) {
            Bundle bundle = new Bundle();
            this.mCurrentDialog.onSaveState(bundle);
            this.mCurrentDialog.dismissWithoutCallback(false);
            this.mCurrentDialog = null;
            if (bundle.getBoolean("container_going_away", false)) {
                return;
            }
            if (bundle.getBoolean("credential_showing")) {
                ((PromptInfo) this.mCurrentDialogArgs.arg1).setAuthenticators(32768);
            }
            showDialog(this.mCurrentDialogArgs, true, bundle);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialogCallback
    public void onDeviceCredentialPressed(long j) {
        IBiometricSysuiReceiver currentReceiver = getCurrentReceiver(j);
        if (currentReceiver == null) {
            Log.w("AuthController", "Skip onDeviceCredentialPressed");
            return;
        }
        try {
            currentReceiver.onDeviceCredentialPressed();
        } catch (RemoteException e) {
            Log.e("AuthController", "RemoteException when handling credential button", e);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialogCallback
    public void onDialogAnimatedIn(long j) {
        IBiometricSysuiReceiver currentReceiver = getCurrentReceiver(j);
        if (currentReceiver == null) {
            Log.w("AuthController", "Skip onDialogAnimatedIn");
            return;
        }
        try {
            currentReceiver.onDialogAnimatedIn();
        } catch (RemoteException e) {
            Log.e("AuthController", "RemoteException when sending onDialogAnimatedIn", e);
        }
    }

    public final void onDialogDismissed(int i) {
        Log.d("AuthController", "onDialogDismissed: " + i);
        if (this.mCurrentDialog == null) {
            Log.w("AuthController", "Dialog already dismissed");
        }
        for (Callback callback : this.mCallbacks) {
            callback.onBiometricPromptDismissed();
        }
        this.mReceiver = null;
        this.mCurrentDialog = null;
    }

    @Override // com.android.systemui.biometrics.AuthDialogCallback
    public void onDismissed(int i, byte[] bArr, long j) {
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null && j != authDialog.getRequestId()) {
            Log.w("AuthController", "requestId doesn't match, skip onDismissed");
            return;
        }
        switch (i) {
            case 1:
                sendResultAndCleanUp(3, bArr);
                return;
            case 2:
                sendResultAndCleanUp(2, bArr);
                return;
            case 3:
                sendResultAndCleanUp(1, bArr);
                return;
            case 4:
                sendResultAndCleanUp(4, bArr);
                return;
            case 5:
                sendResultAndCleanUp(5, bArr);
                return;
            case 6:
                sendResultAndCleanUp(6, bArr);
                return;
            case 7:
                sendResultAndCleanUp(7, bArr);
                return;
            default:
                Log.e("AuthController", "Unhandled reason: " + i);
                return;
        }
    }

    public final void onOrientationChanged() {
        updateSensorLocations();
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            authDialog.onOrientationChanged();
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialogCallback
    public void onSystemEvent(int i, long j) {
        IBiometricSysuiReceiver currentReceiver = getCurrentReceiver(j);
        if (currentReceiver == null) {
            Log.w("AuthController", "Skip onSystemEvent");
            return;
        }
        try {
            currentReceiver.onSystemEvent(i);
        } catch (RemoteException e) {
            Log.e("AuthController", "RemoteException when sending system event", e);
        }
    }

    @Override // com.android.systemui.biometrics.AuthDialogCallback
    public void onTryAgainPressed(long j) {
        IBiometricSysuiReceiver currentReceiver = getCurrentReceiver(j);
        if (currentReceiver == null) {
            Log.w("AuthController", "Skip onTryAgainPressed");
            return;
        }
        try {
            currentReceiver.onTryAgainPressed();
        } catch (RemoteException e) {
            Log.e("AuthController", "RemoteException when handling try again", e);
        }
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    @VisibleForTesting
    public Point rotateToCurrentOrientation(Point point, DisplayInfo displayInfo) {
        RotationUtils.rotatePoint(point, displayInfo.rotation, displayInfo.getNaturalWidth(), displayInfo.getNaturalHeight());
        return point;
    }

    public final void sendResultAndCleanUp(int i, byte[] bArr) {
        IBiometricSysuiReceiver iBiometricSysuiReceiver = this.mReceiver;
        if (iBiometricSysuiReceiver == null) {
            Log.e("AuthController", "sendResultAndCleanUp: Receiver is null");
            return;
        }
        try {
            iBiometricSysuiReceiver.onDialogDismissed(i, bArr);
        } catch (RemoteException e) {
            Log.w("AuthController", "Remote exception", e);
        }
        onDialogDismissed(i);
    }

    public void setBiometicContextListener(IBiometricContextListener iBiometricContextListener) {
        this.mBiometricContextListener = iBiometricContextListener;
        notifyDozeChanged(this.mStatusBarStateController.isDozing(), this.mWakefulnessLifecycle.getWakefulness());
    }

    public void setUdfpsHbmListener(IUdfpsHbmListener iUdfpsHbmListener) {
        this.mUdfpsHbmListener = iUdfpsHbmListener;
    }

    public void showAuthenticationDialog(PromptInfo promptInfo, IBiometricSysuiReceiver iBiometricSysuiReceiver, int[] iArr, boolean z, boolean z2, int i, long j, String str, long j2, int i2) {
        int authenticators = promptInfo.getAuthenticators();
        StringBuilder sb = new StringBuilder();
        for (int i3 : iArr) {
            sb.append(i3);
            sb.append(" ");
        }
        Log.d("AuthController", "showAuthenticationDialog, authenticators: " + authenticators + ", sensorIds: " + sb.toString() + ", credentialAllowed: " + z + ", requireConfirmation: " + z2 + ", operationId: " + j + ", requestId: " + j2 + ", multiSensorConfig: " + i2);
        SomeArgs obtain = SomeArgs.obtain();
        obtain.arg1 = promptInfo;
        obtain.arg2 = iBiometricSysuiReceiver;
        obtain.arg3 = iArr;
        obtain.arg4 = Boolean.valueOf(z);
        obtain.arg5 = Boolean.valueOf(z2);
        obtain.argi1 = i;
        obtain.arg6 = str;
        obtain.argl1 = j;
        obtain.argl2 = j2;
        obtain.argi2 = i2;
        boolean z3 = false;
        if (this.mCurrentDialog != null) {
            Log.w("AuthController", "mCurrentDialog: " + this.mCurrentDialog);
            z3 = true;
        }
        showDialog(obtain, z3, null);
    }

    public final void showDialog(SomeArgs someArgs, boolean z, Bundle bundle) {
        this.mCurrentDialogArgs = someArgs;
        PromptInfo promptInfo = (PromptInfo) someArgs.arg1;
        int[] iArr = (int[]) someArgs.arg3;
        ((Boolean) someArgs.arg4).booleanValue();
        boolean booleanValue = ((Boolean) someArgs.arg5).booleanValue();
        int i = someArgs.argi1;
        AuthDialog buildDialog = buildDialog(this.mBackgroundExecutor, promptInfo, booleanValue, i, iArr, (String) someArgs.arg6, z, someArgs.argl1, someArgs.argl2, someArgs.argi2, this.mWakefulnessLifecycle, this.mUserManager, this.mLockPatternUtils);
        if (buildDialog == null) {
            Log.e("AuthController", "Unsupported type configuration");
            return;
        }
        Log.d("AuthController", "userId: " + i + " savedState: " + bundle + " mCurrentDialog: " + this.mCurrentDialog + " newDialog: " + buildDialog);
        AuthDialog authDialog = this.mCurrentDialog;
        if (authDialog != null) {
            authDialog.dismissWithoutCallback(false);
        }
        this.mReceiver = (IBiometricSysuiReceiver) someArgs.arg2;
        for (Callback callback : this.mCallbacks) {
            callback.onBiometricPromptShown();
        }
        this.mCurrentDialog = buildDialog;
        buildDialog.show(this.mWindowManager, bundle);
        if (promptInfo.isAllowBackgroundAuthentication()) {
            return;
        }
        this.mHandler.post(new Runnable() { // from class: com.android.systemui.biometrics.AuthController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AuthController.$r8$lambda$k06pSb32I7QlAkRH4q3j8z64dRs(AuthController.this);
            }
        });
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.mCommandQueue.addCallback(this);
        FingerprintManager fingerprintManager = this.mFingerprintManager;
        if (fingerprintManager != null) {
            fingerprintManager.addAuthenticatorsRegisteredCallback(this.mFingerprintAuthenticatorsRegisteredCallback);
        }
        this.mActivityTaskManager.registerTaskStackListener(this.mTaskStackListener);
        this.mOrientationListener.enable();
        updateSensorLocations();
    }

    public final void updateFaceLocation() {
        Point point;
        if (this.mFaceProps == null || this.mFaceSensorLocationDefault == null) {
            this.mFaceSensorLocation = null;
        } else {
            float f = this.mFaceSensorLocationDefault.x;
            float f2 = this.mScaleFactor;
            this.mFaceSensorLocation = rotateToCurrentOrientation(new Point((int) (f * f2), (int) (point.y * f2)), this.mCachedDisplayInfo);
        }
        for (Callback callback : this.mCallbacks) {
            callback.onFaceSensorLocationChanged();
        }
    }

    public final void updateFingerprintLocation() {
        if (this.mFpProps == null) {
            this.mFingerprintSensorLocation = null;
        } else {
            this.mFingerprintSensorLocation = rotateToCurrentOrientation(getFingerprintSensorLocationInNaturalOrientation(), this.mCachedDisplayInfo);
        }
        for (Callback callback : this.mCallbacks) {
            callback.onFingerprintLocationChanged();
        }
    }

    public final void updateSensorLocations() {
        this.mDisplay.getDisplayInfo(this.mCachedDisplayInfo);
        Display.Mode maximumResolutionDisplayMode = DisplayUtils.getMaximumResolutionDisplayMode(this.mCachedDisplayInfo.supportedModes);
        float physicalPixelDisplaySizeRatio = DisplayUtils.getPhysicalPixelDisplaySizeRatio(maximumResolutionDisplayMode.getPhysicalWidth(), maximumResolutionDisplayMode.getPhysicalHeight(), this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight());
        if (physicalPixelDisplaySizeRatio == Float.POSITIVE_INFINITY) {
            this.mScaleFactor = 1.0f;
        } else {
            this.mScaleFactor = physicalPixelDisplaySizeRatio;
        }
        updateUdfpsLocation();
        updateFingerprintLocation();
        updateFaceLocation();
    }

    public final void updateUdfpsLocation() {
        if (this.mUdfpsController != null) {
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.mUdfpsProps.get(0);
            Rect rect = this.mUdfpsBounds;
            Rect rect2 = fingerprintSensorPropertiesInternal.getLocation().getRect();
            this.mUdfpsBounds = rect2;
            rect2.scale(this.mScaleFactor);
            this.mUdfpsController.updateOverlayParams(fingerprintSensorPropertiesInternal, new UdfpsOverlayParams(this.mUdfpsBounds, new Rect(0, this.mCachedDisplayInfo.getNaturalHeight() / 2, this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight()), this.mCachedDisplayInfo.getNaturalWidth(), this.mCachedDisplayInfo.getNaturalHeight(), this.mScaleFactor, this.mCachedDisplayInfo.rotation));
            if (Objects.equals(rect, this.mUdfpsBounds)) {
                return;
            }
            for (Callback callback : this.mCallbacks) {
                callback.onUdfpsLocationChanged();
            }
        }
    }

    public final void vibrateError(int i) {
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        vibratorHelper.vibrateAuthError(getClass().getSimpleName() + ", modality = " + i + "BP::error");
    }

    public final void vibrateSuccess(int i) {
        VibratorHelper vibratorHelper = this.mVibratorHelper;
        vibratorHelper.vibrateAuthSuccess(getClass().getSimpleName() + ", modality = " + i + "BP::success");
    }
}