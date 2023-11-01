package com.android.keyguard;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.UserSwitchObserver;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardActiveUnlockModel;
import com.android.keyguard.KeyguardFaceListenModel;
import com.android.keyguard.KeyguardFingerprintListenModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.R$array;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.settings.SecureSettings;
import com.google.android.collect.Lists;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor.class */
public class KeyguardUpdateMonitor implements TrustManager.TrustListener, Dumpable {
    @VisibleForTesting
    public static final int BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED = -1;
    @VisibleForTesting
    public static final int BIOMETRIC_STATE_CANCELLING = 2;
    @VisibleForTesting
    public static final int BIOMETRIC_STATE_CANCELLING_RESTARTING = 3;
    public static final boolean CORE_APPS_ONLY;
    @VisibleForTesting
    public static final int DEFAULT_CANCEL_SIGNAL_TIMEOUT = 3000;
    public static final ComponentName FALLBACK_HOME_COMPONENT = new ComponentName("com.android.settings", "com.android.settings.FallbackHome");
    @VisibleForTesting
    public static final int HAL_POWER_PRESS_TIMEOUT = 50;
    public static int sCurrentUser;
    public int mActiveMobileDataSubscription;
    public final ActiveUnlockConfig mActiveUnlockConfig;
    public final KeyguardActiveUnlockModel.Buffer mActiveUnlockTriggerBuffer;
    public boolean mAssistantVisible;
    public final AuthController mAuthController;
    public boolean mAuthInterruptActive;
    public final Executor mBackgroundExecutor;
    @VisibleForTesting
    public BatteryStatus mBatteryStatus;
    public final IBiometricEnabledOnKeyguardCallback mBiometricEnabledCallback;
    public final SparseBooleanArray mBiometricEnabledForUser;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastAllReceiver;
    public final BroadcastDispatcher mBroadcastDispatcher;
    @VisibleForTesting
    public final BroadcastReceiver mBroadcastReceiver;
    public final ArrayList<WeakReference<KeyguardUpdateMonitorCallback>> mCallbacks;
    public final Context mContext;
    public boolean mCredentialAttempted;
    public boolean mDeviceInteractive;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mDeviceProvisioned;
    public ContentObserver mDeviceProvisionedObserver;
    public final IDreamManager mDreamManager;
    public final Set<Integer> mFaceAcquiredInfoIgnoreList;
    public final boolean mFaceAuthOnlyOnSecurityView;
    @VisibleForTesting
    public final FaceManager.AuthenticationCallback mFaceAuthenticationCallback;
    public final Runnable mFaceCancelNotReceived;
    @VisibleForTesting
    public CancellationSignal mFaceCancelSignal;
    public final FaceManager.FaceDetectionCallback mFaceDetectionCallback;
    public final KeyguardFaceListenModel.Buffer mFaceListenBuffer;
    public boolean mFaceLockedOutPermanent;
    public final FaceManager.LockoutResetCallback mFaceLockoutResetCallback;
    public final FaceManager mFaceManager;
    public int mFaceRunningState;
    public List<FaceSensorPropertiesInternal> mFaceSensorProperties;
    public int mFaceUnlockBehavior;
    public final FaceWakeUpTriggersConfig mFaceWakeUpTriggersConfig;
    @VisibleForTesting
    public final FingerprintManager.AuthenticationCallback mFingerprintAuthenticationCallback;
    @VisibleForTesting
    public CancellationSignal mFingerprintCancelSignal;
    public final KeyguardFingerprintListenModel.Buffer mFingerprintListenBuffer;
    public boolean mFingerprintLockedOut;
    public boolean mFingerprintLockedOutPermanent;
    public final FingerprintManager.LockoutResetCallback mFingerprintLockoutResetCallback;
    @VisibleForTesting
    public int mFingerprintRunningState;
    public List<FingerprintSensorPropertiesInternal> mFingerprintSensorProperties;
    @VisibleForTesting
    public final Runnable mFpCancelNotReceived;
    public final FingerprintManager mFpm;
    public boolean mGoingToSleep;
    public final Handler mHandler;
    public int mHardwareFaceUnavailableRetryCount;
    public int mHardwareFingerprintUnavailableRetryCount;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsDreaming;
    public boolean mIsFaceEnrolled;
    public final boolean mIsPrimaryUser;
    public final HashMap<Integer, Boolean> mIsUnlockWithFingerprintPossible;
    public KeyguardBypassController mKeyguardBypassController;
    public boolean mKeyguardGoingAway;
    public boolean mKeyguardOccluded;
    public boolean mKeyguardShowing;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardUpdateMonitorLogger mLogger;
    public boolean mLogoutEnabled;
    public boolean mNeedsSlowUnlockTransition;
    public boolean mOccludingAppRequestingFace;
    public boolean mOccludingAppRequestingFp;
    public final PackageManager mPackageManager;
    public int mPhoneState;
    @VisibleForTesting
    public TelephonyCallback.ActiveDataSubscriptionIdListener mPhoneStateListener;
    public final PowerManager mPowerManager;
    public boolean mPrimaryBouncerFullyShown;
    public boolean mPrimaryBouncerIsOrWillBeShowing;
    public final Runnable mRetryFaceAuthentication;
    public final Runnable mRetryFingerprintAuthenticationAfterHwUnavailable;
    public final Map<Integer, Intent> mSecondaryLockscreenRequirement;
    public boolean mSecureCameraLaunched;
    public final SecureSettings mSecureSettings;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public HashMap<Integer, ServiceState> mServiceStates;
    public final Provider<SessionTracker> mSessionTrackerProvider;
    public SettingsObserver mSettingsObserver;
    public boolean mSfpsRequireScreenOnToAuthPrefEnabled;
    public ContentObserver mSfpsRequireScreenOnToAuthPrefObserver;
    public HashMap<Integer, SimData> mSimDatas;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final StatusBarStateController.StateListener mStatusBarStateControllerListener;
    public StrongAuthTracker mStrongAuthTracker;
    public List<SubscriptionInfo> mSubscriptionInfo;
    public final SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public boolean mSwitchingUser;
    public final TaskStackChangeListener mTaskStackListener;
    @VisibleForTesting
    public boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final ContentObserver mTimeFormatChangeObserver;
    public final TrustManager mTrustManager;
    public boolean mUdfpsBouncerShowing;
    public final UiEventLogger mUiEventLogger;
    @VisibleForTesting
    public SparseArray<BiometricAuthenticated> mUserFaceAuthenticated;
    @VisibleForTesting
    public SparseArray<BiometricAuthenticated> mUserFingerprintAuthenticated;
    public final SparseBooleanArray mUserHasTrust;
    public final SparseBooleanArray mUserIsUnlocked;
    public final UserManager mUserManager;
    public final UserSwitchObserver mUserSwitchObserver;
    public final UserTracker mUserTracker;
    public final SparseBooleanArray mUserTrustIsManaged;
    public final SparseBooleanArray mUserTrustIsUsuallyManaged;
    public final boolean mWakeOnFingerprintAcquiredStart;

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$14 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$14.class */
    public class AnonymousClass14 implements AuthController.Callback {
        public final /* synthetic */ Executor val$mainExecutor;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$14$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$XZeY5wrSN7T9ERspYEiQ3CRSAAw(AnonymousClass14 anonymousClass14) {
            anonymousClass14.lambda$onEnrollmentsChanged$1();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$14$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$XyZz66Kn9UeHvNQOhMkMm3ffORA(AnonymousClass14 anonymousClass14) {
            anonymousClass14.lambda$onAllAuthenticatorsRegistered$0();
        }

        public AnonymousClass14(Executor executor) {
            KeyguardUpdateMonitor.this = r4;
            this.val$mainExecutor = executor;
        }

        public /* synthetic */ void lambda$onAllAuthenticatorsRegistered$0() {
            KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALL_AUTHENTICATORS_REGISTERED);
        }

        public /* synthetic */ void lambda$onEnrollmentsChanged$1() {
            KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ENROLLMENTS_CHANGED);
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public void onAllAuthenticatorsRegistered() {
            this.val$mainExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$14$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor.AnonymousClass14.$r8$lambda$XyZz66Kn9UeHvNQOhMkMm3ffORA(KeyguardUpdateMonitor.AnonymousClass14.this);
                }
            });
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public void onEnrollmentsChanged() {
            this.val$mainExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$14$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor.AnonymousClass14.$r8$lambda$XZeY5wrSN7T9ERspYEiQ3CRSAAw(KeyguardUpdateMonitor.AnonymousClass14.this);
                }
            });
        }
    }

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$2 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$2.class */
    public class AnonymousClass2 extends IBiometricEnabledOnKeyguardCallback.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$Q6QoSPhzvNKCrBCgEoJ3-NvURR4 */
        public static /* synthetic */ void m769$r8$lambda$Q6QoSPhzvNKCrBCgEoJ3NvURR4(AnonymousClass2 anonymousClass2, int i, boolean z) {
            anonymousClass2.lambda$onChanged$0(i, z);
        }

        public AnonymousClass2() {
            KeyguardUpdateMonitor.this = r4;
        }

        public /* synthetic */ void lambda$onChanged$0(int i, boolean z) {
            KeyguardUpdateMonitor.this.mBiometricEnabledForUser.put(i, z);
            KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_BIOMETRIC_ENABLED_ON_KEYGUARD);
        }

        public void onChanged(final boolean z, final int i) {
            KeyguardUpdateMonitor.this.mHandler.post(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor.AnonymousClass2.m769$r8$lambda$Q6QoSPhzvNKCrBCgEoJ3NvURR4(KeyguardUpdateMonitor.AnonymousClass2.this, i, z);
                }
            });
        }
    }

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$20 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$20.class */
    public static /* synthetic */ class AnonymousClass20 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:23:0x0020 -> B:27:0x0014). Please submit an issue!!! */
        static {
            int[] iArr = new int[BiometricSourceType.values().length];
            $SwitchMap$android$hardware$biometrics$BiometricSourceType = iArr;
            try {
                iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$hardware$biometrics$BiometricSourceType[BiometricSourceType.FACE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$BiometricAuthenticated.class */
    public static class BiometricAuthenticated {
        public final boolean mAuthenticated;
        public final boolean mIsStrongBiometric;

        public BiometricAuthenticated(boolean z, boolean z2) {
            this.mAuthenticated = z;
            this.mIsStrongBiometric = z2;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$SettingsObserver.class */
    public class SettingsObserver extends ContentObserver {
        public ContentResolver mContentResolver;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SettingsObserver(Handler handler) {
            super(handler);
            KeyguardUpdateMonitor.this = r4;
        }

        public void observe() {
            ContentResolver contentResolver = KeyguardUpdateMonitor.this.mContext.getContentResolver();
            this.mContentResolver = contentResolver;
            contentResolver.registerContentObserver(Settings.Secure.getUriFor("face_unlock_method"), false, this, -1);
            KeyguardUpdateMonitor.this.updateSettings();
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            KeyguardUpdateMonitor.this.updateSettings();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$SimData.class */
    public static class SimData {
        public int simState;
        public int slotId;
        public int subId;

        public SimData(int i, int i2, int i3) {
            this.simState = i;
            this.slotId = i2;
            this.subId = i3;
        }

        /* JADX WARN: Code restructure failed: missing block: B:68:0x00ae, code lost:
            if ("IMSI".equals(r0) == false) goto L8;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static SimData fromIntent(Intent intent) {
            if ("android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                String stringExtra = intent.getStringExtra("ss");
                int i = 0;
                int intExtra = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0);
                int intExtra2 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                if ("ABSENT".equals(stringExtra)) {
                    i = "PERM_DISABLED".equals(intent.getStringExtra("reason")) ? 7 : 1;
                } else {
                    if (!"READY".equals(stringExtra)) {
                        if ("LOCKED".equals(stringExtra)) {
                            String stringExtra2 = intent.getStringExtra("reason");
                            if ("PIN".equals(stringExtra2)) {
                                i = 2;
                            } else if ("PUK".equals(stringExtra2)) {
                                i = 3;
                            }
                        } else if ("NETWORK".equals(stringExtra)) {
                            i = 4;
                        } else if ("CARD_IO_ERROR".equals(stringExtra)) {
                            i = 8;
                        } else if (!"LOADED".equals(stringExtra)) {
                        }
                    }
                    i = 5;
                }
                return new SimData(i, intExtra, intExtra2);
            }
            throw new IllegalArgumentException("only handles intent ACTION_SIM_STATE_CHANGED");
        }

        public String toString() {
            return "SimData{state=" + this.simState + ",slotId=" + this.slotId + ",subId=" + this.subId + "}";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor$StrongAuthTracker.class */
    public class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public StrongAuthTracker(Context context) {
            super(context);
            KeyguardUpdateMonitor.this = r4;
        }

        public boolean hasUserAuthenticatedSinceBoot() {
            boolean z = true;
            if ((getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()) & 1) != 0) {
                z = false;
            }
            return z;
        }

        public boolean isUnlockingWithBiometricAllowed(boolean z) {
            return isBiometricAllowedForUser(z, KeyguardUpdateMonitor.getCurrentUser());
        }

        public void onIsNonStrongBiometricAllowedChanged(int i) {
            KeyguardUpdateMonitor.this.notifyNonStrongBiometricAllowedChanged(i);
        }

        public void onStrongAuthRequiredChanged(int i) {
            KeyguardUpdateMonitor.this.notifyStrongAuthAllowedChanged(i);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8.run():void] */
    public static /* synthetic */ void $r8$lambda$0z0G1S2d5hpCluaojbsT9WiZ1bo(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.lambda$setSwitchingUser$10();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda3.onFaceDetected(int, int, boolean):void] */
    public static /* synthetic */ void $r8$lambda$DU91XbrezVB3UGGG9E8Zw9AmvUk(KeyguardUpdateMonitor keyguardUpdateMonitor, int i, int i2, boolean z) {
        keyguardUpdateMonitor.lambda$new$5(i, i2, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda10.run():void] */
    public static /* synthetic */ void $r8$lambda$E_LXV5UP82IkJRv0IaLa65Rrvns(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.lambda$handleFingerprintError$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda5.run():void] */
    /* renamed from: $r8$lambda$F0-GoNj1iOfaym_zj8P2-vbeigc */
    public static /* synthetic */ void m708$r8$lambda$F0GoNj1iOfaym_zj8P2vbeigc(KeyguardUpdateMonitor keyguardUpdateMonitor, boolean z, int i) {
        keyguardUpdateMonitor.lambda$reportSuccessfulBiometricUnlock$0(z, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$KYWm5mV3sSwzw4TNNtCwwaCGsO8(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.lambda$new$6();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$Ktak-IMdk41HxunRledh8Dm-wo4 */
    public static /* synthetic */ void m709$r8$lambda$KtakIMdk41HxunRledh8Dmwo4(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.onFaceCancelNotReceived();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$T49MKzabbosDxCKYc8-bGVTR0dQ */
    public static /* synthetic */ boolean m710$r8$lambda$T49MKzabbosDxCKYc8bGVTR0dQ(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback, WeakReference weakReference) {
        return lambda$removeCallback$9(keyguardUpdateMonitorCallback, weakReference);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$W5kKjsMrYheN7jlvSzd1IQzWzig(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.lambda$handleFaceLockoutReset$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda12.onFingerprintDetected(int, int, boolean):void] */
    public static /* synthetic */ void $r8$lambda$bcukyu9zWt1P9dp3_MqNNap0KYw(KeyguardUpdateMonitor keyguardUpdateMonitor, int i, int i2, boolean z) {
        keyguardUpdateMonitor.lambda$startListeningForFingerprint$8(i, i2, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$dtzaN3RPhGIpEAtaRDeXIljDj0w(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.lambda$handleFingerprintLockoutReset$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda1.run():void] */
    /* renamed from: $r8$lambda$hn7uDT4UHhfKpis2Ks7-UtgS9dk */
    public static /* synthetic */ void m711$r8$lambda$hn7uDT4UHhfKpis2Ks7UtgS9dk(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        keyguardUpdateMonitor.onFingerprintCancelNotReceived();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9.get():java.lang.Object] */
    public static /* synthetic */ Boolean $r8$lambda$lR0vlGVJ1ALtTBPRdbdxVk6EpsA(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        return keyguardUpdateMonitor.lambda$isFaceDisabled$4(i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda11.get():java.lang.Object] */
    public static /* synthetic */ Boolean $r8$lambda$y0eEZ6Ck9QKjp6zFdce8p4Cq_XM(KeyguardUpdateMonitor keyguardUpdateMonitor, int i) {
        return keyguardUpdateMonitor.lambda$updateFaceEnrolled$7(i);
    }

    static {
        try {
            CORE_APPS_ONLY = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isOnlyCoreApps();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    public KeyguardUpdateMonitor(Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, UiEventLogger uiEventLogger, Provider<SessionTracker> provider, PowerManager powerManager, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FaceManager faceManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig) {
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.1
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onExpandedChanged(boolean z) {
                for (int i = 0; i < KeyguardUpdateMonitor.this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) KeyguardUpdateMonitor.this.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onShadeExpandedChanged(z);
                    }
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                KeyguardUpdateMonitor.this.mStatusBarState = i;
            }
        };
        this.mStatusBarStateControllerListener = stateListener;
        this.mSimDatas = new HashMap<>();
        this.mServiceStates = new HashMap<>();
        this.mCallbacks = Lists.newArrayList();
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
        this.mActiveMobileDataSubscription = -1;
        this.mHardwareFingerprintUnavailableRetryCount = 0;
        this.mHardwareFaceUnavailableRetryCount = 0;
        this.mFpCancelNotReceived = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.m711$r8$lambda$hn7uDT4UHhfKpis2Ks7UtgS9dk(KeyguardUpdateMonitor.this);
            }
        };
        this.mFaceCancelNotReceived = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.m709$r8$lambda$KtakIMdk41HxunRledh8Dmwo4(KeyguardUpdateMonitor.this);
            }
        };
        this.mBiometricEnabledCallback = new AnonymousClass2();
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.3
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public void onActiveDataSubscriptionIdChanged(int i) {
                KeyguardUpdateMonitor.this.mActiveMobileDataSubscription = i;
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(328);
            }
        };
        SubscriptionManager.OnSubscriptionsChangedListener onSubscriptionsChangedListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.4
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public void onSubscriptionsChanged() {
                KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(328);
            }
        };
        this.mSubscriptionListener = onSubscriptionsChangedListener;
        this.mUserIsUnlocked = new SparseBooleanArray();
        this.mUserHasTrust = new SparseBooleanArray();
        this.mUserTrustIsManaged = new SparseBooleanArray();
        this.mUserTrustIsUsuallyManaged = new SparseBooleanArray();
        this.mBiometricEnabledForUser = new SparseBooleanArray();
        this.mSecondaryLockscreenRequirement = new HashMap();
        this.mFingerprintListenBuffer = new KeyguardFingerprintListenModel.Buffer();
        this.mFaceListenBuffer = new KeyguardFaceListenModel.Buffer();
        this.mActiveUnlockTriggerBuffer = new KeyguardActiveUnlockModel.Buffer();
        this.mUserFingerprintAuthenticated = new SparseArray<>();
        this.mUserFaceAuthenticated = new SparseArray<>();
        this.mFaceUnlockBehavior = 0;
        this.mRetryFingerprintAuthenticationAfterHwUnavailable = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.5
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // java.lang.Runnable
            @SuppressLint({"MissingPermission"})
            public void run() {
                KeyguardUpdateMonitor.this.mLogger.logRetryAfterFpHwUnavailable(KeyguardUpdateMonitor.this.mHardwareFingerprintUnavailableRetryCount);
                if (KeyguardUpdateMonitor.this.mFpm.isHardwareDetected()) {
                    KeyguardUpdateMonitor.this.updateFingerprintListeningState(2);
                } else if (KeyguardUpdateMonitor.this.mHardwareFingerprintUnavailableRetryCount < 20) {
                    KeyguardUpdateMonitor.this.mHardwareFingerprintUnavailableRetryCount++;
                    KeyguardUpdateMonitor.this.mHandler.postDelayed(KeyguardUpdateMonitor.this.mRetryFingerprintAuthenticationAfterHwUnavailable, 500L);
                }
            }
        };
        this.mRetryFaceAuthentication = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.6
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                KeyguardUpdateMonitor.this.mLogger.logRetryingAfterFaceHwUnavailable(KeyguardUpdateMonitor.this.mHardwareFaceUnavailableRetryCount);
                KeyguardUpdateMonitor.this.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE);
            }
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.7
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                KeyguardUpdateMonitor.this.mLogger.logBroadcastReceived(action);
                if ("android.intent.action.TIME_TICK".equals(action) || "android.intent.action.TIME_SET".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(301);
                } else if ("android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(339, intent.getStringExtra("time-zone")));
                } else if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(302, new BatteryStatus(intent)));
                } else if ("android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    SimData fromIntent = SimData.fromIntent(intent);
                    if (!intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                        KeyguardUpdateMonitor.this.mLogger.logSimStateFromIntent(action, intent.getStringExtra("ss"), fromIntent.slotId, fromIntent.subId);
                        KeyguardUpdateMonitor.this.mHandler.obtainMessage(304, fromIntent.subId, fromIntent.slotId, Integer.valueOf(fromIntent.simState)).sendToTarget();
                    } else if (fromIntent.simState == 1) {
                        KeyguardUpdateMonitor.this.mHandler.obtainMessage(338, Boolean.TRUE).sendToTarget();
                    }
                } else if ("android.intent.action.PHONE_STATE".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(306, intent.getStringExtra("state")));
                } else if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(329);
                } else if ("android.intent.action.SERVICE_STATE".equals(action)) {
                    ServiceState newFromBundle = ServiceState.newFromBundle(intent.getExtras());
                    int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                    KeyguardUpdateMonitor.this.mLogger.logServiceStateIntent(action, newFromBundle, intExtra);
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(330, intExtra, 0, newFromBundle));
                } else if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(328);
                } else if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(337);
                }
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.8
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(301);
                } else if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(309, Integer.valueOf(getSendingUserId())));
                } else if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(334, getSendingUserId(), 0));
                } else if ("android.intent.action.USER_STOPPED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(340, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(341, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC".equals(action)) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(345);
                }
            }
        };
        this.mBroadcastAllReceiver = broadcastReceiver2;
        this.mFingerprintLockoutResetCallback = new FingerprintManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.9
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onLockoutReset(int i) {
                KeyguardUpdateMonitor.this.handleFingerprintLockoutReset(0);
            }
        };
        this.mFaceLockoutResetCallback = new FaceManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.10
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onLockoutReset(int i) {
                KeyguardUpdateMonitor.this.handleFaceLockoutReset(0);
            }
        };
        this.mFingerprintAuthenticationCallback = new FingerprintManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.11
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onAuthenticationAcquired(int i) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationAcquired");
                KeyguardUpdateMonitor.this.handleFingerprintAcquired(i);
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationError(int i, CharSequence charSequence) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationError");
                KeyguardUpdateMonitor.this.handleFingerprintError(i, charSequence.toString());
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationFailed() {
                KeyguardUpdateMonitor.this.requestActiveUnlockDismissKeyguard(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.BIOMETRIC_FAIL, "fingerprintFailure");
                KeyguardUpdateMonitor.this.handleFingerprintAuthFailed();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationHelp(int i, CharSequence charSequence) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationHelp");
                KeyguardUpdateMonitor.this.handleFingerprintHelp(i, charSequence.toString());
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                KeyguardUpdateMonitor.this.handleFingerprintAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                Trace.endSection();
            }

            public void onUdfpsPointerDown(int i) {
                KeyguardUpdateMonitor.this.mLogger.logUdfpsPointerDown(i);
                KeyguardUpdateMonitor.this.requestFaceAuth("Face auth triggered due to finger down on UDFPS");
            }

            public void onUdfpsPointerUp(int i) {
                KeyguardUpdateMonitor.this.mLogger.logUdfpsPointerUp(i);
            }
        };
        this.mFaceDetectionCallback = new FaceManager.FaceDetectionCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda3
            public final void onFaceDetected(int i, int i2, boolean z) {
                KeyguardUpdateMonitor.$r8$lambda$DU91XbrezVB3UGGG9E8Zw9AmvUk(KeyguardUpdateMonitor.this, i, i2, z);
            }
        };
        this.mFaceAuthenticationCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.12
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onAuthenticationAcquired(int i) {
                KeyguardUpdateMonitor.this.handleFaceAcquired(i);
                if (KeyguardUpdateMonitor.this.mActiveUnlockConfig.shouldRequestActiveUnlockOnFaceAcquireInfo(i)) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin = ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.BIOMETRIC_FAIL;
                    keyguardUpdateMonitor.requestActiveUnlock(active_unlock_request_origin, "faceAcquireInfo-" + i);
                }
            }

            public void onAuthenticationError(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceError(i, charSequence.toString());
                if (KeyguardUpdateMonitor.this.mActiveUnlockConfig.shouldRequestActiveUnlockOnFaceError(i)) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin = ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.BIOMETRIC_FAIL;
                    keyguardUpdateMonitor.requestActiveUnlock(active_unlock_request_origin, "faceError-" + i);
                }
            }

            public void onAuthenticationFailed() {
                String str = KeyguardUpdateMonitor.this.mKeyguardBypassController.canBypass() ? "bypass" : KeyguardUpdateMonitor.this.mUdfpsBouncerShowing ? "udfpsBouncer" : KeyguardUpdateMonitor.this.mPrimaryBouncerFullyShown ? "bouncer" : "udfpsFpDown";
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin = ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.BIOMETRIC_FAIL;
                keyguardUpdateMonitor.requestActiveUnlock(active_unlock_request_origin, "faceFailure-" + str);
                KeyguardUpdateMonitor.this.handleFaceAuthFailed();
            }

            public void onAuthenticationHelp(int i, CharSequence charSequence) {
                if (KeyguardUpdateMonitor.this.mFaceAcquiredInfoIgnoreList.contains(Integer.valueOf(i))) {
                    return;
                }
                KeyguardUpdateMonitor.this.handleFaceHelp(i, charSequence.toString());
            }

            public void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                KeyguardUpdateMonitor.this.handleFaceAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                Trace.endSection();
            }
        };
        this.mIsUnlockWithFingerprintPossible = new HashMap<>();
        UserSwitchObserver userSwitchObserver = new UserSwitchObserver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.17
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onUserSwitchComplete(int i) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(314, i, 0));
            }

            public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(310, i, 0, iRemoteCallback));
            }
        };
        this.mUserSwitchObserver = userSwitchObserver;
        this.mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.19
            {
                KeyguardUpdateMonitor.this = this;
            }

            public void onTaskStackChangedBackground() {
                try {
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(0, 4);
                    if (rootTaskInfo == null) {
                        return;
                    }
                    KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(335, Boolean.valueOf(rootTaskInfo.visible)));
                } catch (RemoteException e) {
                    KeyguardUpdateMonitor.this.mLogger.logException(e, "unable to check task stack ");
                }
            }
        };
        this.mContext = context;
        this.mSubscriptionManager = subscriptionManager;
        this.mUserTracker = userTracker;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb();
        this.mStrongAuthTracker = new StrongAuthTracker(context);
        this.mFaceAuthOnlyOnSecurityView = context.getResources().getBoolean(17891673);
        this.mBackgroundExecutor = executor;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(stateListener);
        this.mStatusBarState = statusBarStateController.getState();
        this.mLockPatternUtils = lockPatternUtils;
        this.mAuthController = authController;
        this.mSecureSettings = secureSettings;
        dumpManager.registerDumpable(getClass().getName(), this);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mActiveUnlockConfig = activeUnlockConfig;
        this.mLogger = keyguardUpdateMonitorLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mSessionTrackerProvider = provider;
        this.mPowerManager = powerManager;
        this.mTrustManager = trustManager;
        this.mUserManager = userManager;
        this.mDreamManager = iDreamManager;
        this.mTelephonyManager = telephonyManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mPackageManager = packageManager;
        this.mFpm = fingerprintManager;
        this.mFaceManager = faceManager;
        activeUnlockConfig.setKeyguardUpdateMonitor(this);
        this.mWakeOnFingerprintAcquiredStart = context.getResources().getBoolean(17891890);
        this.mFaceAcquiredInfoIgnoreList = (Set) Arrays.stream(context.getResources().getIntArray(R$array.config_face_acquire_device_entry_ignorelist)).boxed().collect(Collectors.toSet());
        this.mFaceWakeUpTriggersConfig = faceWakeUpTriggersConfig;
        Handler handler = new Handler(looper) { // from class: com.android.keyguard.KeyguardUpdateMonitor.13
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                switch (message.what) {
                    case 301:
                        KeyguardUpdateMonitor.this.handleTimeUpdate();
                        return;
                    case 302:
                        KeyguardUpdateMonitor.this.handleBatteryUpdate((BatteryStatus) message.obj);
                        return;
                    case 303:
                    case 305:
                    case 307:
                    case 311:
                    case 313:
                    case 315:
                    case 316:
                    case 317:
                    case 323:
                    case 324:
                    case 325:
                    case 326:
                    case 327:
                    case 331:
                    case 343:
                    default:
                        super.handleMessage(message);
                        return;
                    case 304:
                        KeyguardUpdateMonitor.this.handleSimStateChange(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        return;
                    case 306:
                        KeyguardUpdateMonitor.this.handlePhoneStateChanged((String) message.obj);
                        return;
                    case 308:
                        KeyguardUpdateMonitor.this.handleDeviceProvisioned();
                        return;
                    case 309:
                        KeyguardUpdateMonitor.this.handleDevicePolicyManagerStateChanged(message.arg1);
                        return;
                    case 310:
                        KeyguardUpdateMonitor.this.handleUserSwitching(message.arg1, (IRemoteCallback) message.obj);
                        return;
                    case 312:
                        KeyguardUpdateMonitor.this.handleKeyguardReset();
                        return;
                    case 314:
                        KeyguardUpdateMonitor.this.handleUserSwitchComplete(message.arg1);
                        return;
                    case 318:
                        KeyguardUpdateMonitor.this.handleReportEmergencyCallAction();
                        return;
                    case 319:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_STARTED_WAKING_UP");
                        KeyguardUpdateMonitor.this.handleStartedWakingUp(message.arg1);
                        Trace.endSection();
                        return;
                    case 320:
                        KeyguardUpdateMonitor.this.handleFinishedGoingToSleep(message.arg1);
                        return;
                    case 321:
                        KeyguardUpdateMonitor.this.handleStartedGoingToSleep(message.arg1);
                        return;
                    case 322:
                        KeyguardUpdateMonitor.this.handlePrimaryBouncerChanged(message.arg1, message.arg2);
                        return;
                    case 328:
                        KeyguardUpdateMonitor.this.handleSimSubscriptionInfoChanged();
                        return;
                    case 329:
                        KeyguardUpdateMonitor.this.handleAirplaneModeChanged();
                        return;
                    case 330:
                        KeyguardUpdateMonitor.this.handleServiceStateChange(message.arg1, (ServiceState) message.obj);
                        return;
                    case 332:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_OFF");
                        KeyguardUpdateMonitor.this.handleScreenTurnedOff();
                        Trace.endSection();
                        return;
                    case 333:
                        KeyguardUpdateMonitor.this.handleDreamingStateChanged(message.arg1);
                        return;
                    case 334:
                        KeyguardUpdateMonitor.this.handleUserUnlocked(message.arg1);
                        return;
                    case 335:
                        KeyguardUpdateMonitor.this.setAssistantVisible(((Boolean) message.obj).booleanValue());
                        return;
                    case 336:
                        KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FP_AUTHENTICATED);
                        return;
                    case 337:
                        KeyguardUpdateMonitor.this.updateLogoutEnabled();
                        return;
                    case 338:
                        KeyguardUpdateMonitor.this.updateTelephonyCapable(((Boolean) message.obj).booleanValue());
                        return;
                    case 339:
                        KeyguardUpdateMonitor.this.handleTimeZoneUpdate((String) message.obj);
                        return;
                    case 340:
                        KeyguardUpdateMonitor.this.handleUserStopped(message.arg1);
                        return;
                    case 341:
                        KeyguardUpdateMonitor.this.handleUserRemoved(message.arg1);
                        return;
                    case 342:
                        KeyguardUpdateMonitor.this.handleKeyguardGoingAway(((Boolean) message.obj).booleanValue());
                        return;
                    case 344:
                        KeyguardUpdateMonitor.this.handleTimeFormatUpdate((String) message.obj);
                        return;
                    case 345:
                        KeyguardUpdateMonitor.this.handleRequireUnlockForNfc();
                        return;
                    case 346:
                        KeyguardUpdateMonitor.this.handleKeyguardDismissAnimationFinished();
                        return;
                }
            }
        };
        this.mHandler = handler;
        if (!this.mDeviceProvisioned) {
            watchForDeviceProvisioning();
        }
        this.mBatteryStatus = new BatteryStatus(1, 100, 0, 0, 0, false, true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(broadcastReceiver, intentFilter, handler);
        executor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.$r8$lambda$KYWm5mV3sSwzw4TNNtCwwaCGsO8(KeyguardUpdateMonitor.this);
            }
        });
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC");
        broadcastDispatcher.registerReceiverWithHandler(broadcastReceiver2, intentFilter2, handler, UserHandle.ALL);
        subscriptionManager.addOnSubscriptionsChangedListener(onSubscriptionsChangedListener);
        try {
            ActivityManager.getService().registerUserSwitchObserver(userSwitchObserver, "KeyguardUpdateMonitor");
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
        this.mTrustManager.registerTrustListener(this);
        setStrongAuthTracker(this.mStrongAuthTracker);
        FingerprintManager fingerprintManager2 = this.mFpm;
        if (fingerprintManager2 != null) {
            this.mFingerprintSensorProperties = fingerprintManager2.getSensorPropertiesInternal();
            this.mFpm.addLockoutResetCallback(this.mFingerprintLockoutResetCallback);
        }
        FaceManager faceManager2 = this.mFaceManager;
        if (faceManager2 != null) {
            this.mFaceSensorProperties = faceManager2.getSensorPropertiesInternal();
            this.mFaceManager.addLockoutResetCallback(this.mFaceLockoutResetCallback);
        }
        if (biometricManager != null) {
            biometricManager.registerEnabledOnKeyguardCallback(this.mBiometricEnabledCallback);
        }
        this.mAuthController.addCallback(new AnonymousClass14(executor2));
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ON_KEYGUARD_INIT);
        TaskStackChangeListeners.getInstance().registerTaskStackListener(this.mTaskStackListener);
        this.mIsPrimaryUser = this.mUserManager.isPrimaryUser();
        int userId = this.mUserTracker.getUserId();
        this.mUserIsUnlocked.put(userId, this.mUserManager.isUserUnlocked(userId));
        this.mLogoutEnabled = this.mDevicePolicyManager.isLogoutEnabled();
        updateSecondaryLockscreenRequirement(userId);
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            SparseBooleanArray sparseBooleanArray = this.mUserTrustIsUsuallyManaged;
            int i = userInfo.id;
            sparseBooleanArray.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        }
        updateAirplaneModeState();
        this.mTelephonyListenerManager.addActiveDataSubscriptionIdListener(this.mPhoneStateListener);
        initializeSimState();
        ContentObserver contentObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.15
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                KeyguardUpdateMonitor.this.mHandler.sendMessage(KeyguardUpdateMonitor.this.mHandler.obtainMessage(344, Settings.System.getString(KeyguardUpdateMonitor.this.mContext.getContentResolver(), "time_12_24")));
            }
        };
        this.mTimeFormatChangeObserver = contentObserver;
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), false, contentObserver, -1);
        updateSfpsRequireScreenOnToAuthPref();
        this.mSfpsRequireScreenOnToAuthPrefObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.16
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                KeyguardUpdateMonitor.this.updateSfpsRequireScreenOnToAuthPref();
            }
        };
        this.mContext.getContentResolver().registerContentObserver(this.mSecureSettings.getUriFor("sfps_require_screen_on_to_auth_enabled"), false, this.mSfpsRequireScreenOnToAuthPrefObserver, getCurrentUser());
        SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSettingsObserver = settingsObserver;
        settingsObserver.observe();
    }

    public static int getCurrentUser() {
        int i;
        synchronized (KeyguardUpdateMonitor.class) {
            try {
                i = sCurrentUser;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public static boolean isSimPinSecure(int i) {
        return i == 2 || i == 3 || i == 7;
    }

    public /* synthetic */ void lambda$handleFaceLockoutReset$3() {
        updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
    }

    public /* synthetic */ void lambda$handleFingerprintError$1() {
        this.mLogger.d("Retrying fingerprint listening after power pressed error.");
        updateFingerprintListeningState(0);
    }

    public /* synthetic */ void lambda$handleFingerprintLockoutReset$2() {
        updateFingerprintListeningState(2);
    }

    public /* synthetic */ Boolean lambda$isFaceDisabled$4(int i) {
        return Boolean.valueOf((this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & RecyclerView.ViewHolder.FLAG_IGNORE) != 0 || isSimPinSecure());
    }

    public /* synthetic */ void lambda$new$5(int i, int i2, boolean z) {
        handleFaceAuthenticated(i2, z);
    }

    public /* synthetic */ void lambda$new$6() {
        int defaultSubscriptionId = SubscriptionManager.getDefaultSubscriptionId();
        ServiceState serviceStateForSubscriber = this.mTelephonyManager.getServiceStateForSubscriber(defaultSubscriptionId);
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(330, defaultSubscriptionId, 0, serviceStateForSubscriber));
    }

    public static /* synthetic */ boolean lambda$removeCallback$9(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback, WeakReference weakReference) {
        return weakReference.get() == keyguardUpdateMonitorCallback;
    }

    public /* synthetic */ void lambda$reportSuccessfulBiometricUnlock$0(boolean z, int i) {
        this.mLockPatternUtils.reportSuccessfulBiometricUnlock(z, i);
    }

    public /* synthetic */ void lambda$setSwitchingUser$10() {
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
    }

    public /* synthetic */ void lambda$startListeningForFingerprint$8(int i, int i2, boolean z) {
        this.mLogger.d("fingerprint detected");
        handleFingerprintAuthenticated(i2, z);
    }

    public /* synthetic */ Boolean lambda$updateFaceEnrolled$7(int i) {
        FaceManager faceManager = this.mFaceManager;
        return Boolean.valueOf(faceManager != null && faceManager.isHardwareDetected() && this.mFaceManager.hasEnrolledTemplates(i) && this.mBiometricEnabledForUser.get(i));
    }

    public static void setCurrentUser(int i) {
        synchronized (KeyguardUpdateMonitor.class) {
            try {
                sCurrentUser = i;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void callbacksRefreshCarrierInfo() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onRefreshCarrierInfo();
            }
        }
    }

    public void cancelFaceAuth() {
        stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
    }

    public void clearBiometricRecognized() {
        clearBiometricRecognized(-10000);
    }

    public final void clearBiometricRecognized(int i) {
        Assert.isMainThread();
        this.mUserFingerprintAuthenticated.clear();
        this.mUserFaceAuthenticated.clear();
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FINGERPRINT, i);
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FACE, i);
        this.mLogger.d("clearBiometricRecognized");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricsCleared();
            }
        }
    }

    public void clearBiometricRecognizedWhenKeyguardDone(int i) {
        clearBiometricRecognized(i);
    }

    public final boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    public void dispatchDreamingStarted() {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(333, 1, 0));
    }

    public void dispatchDreamingStopped() {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(333, 0, 0));
    }

    public final void dispatchErrorMessage(CharSequence charSequence) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustAgentErrorMessage(charSequence);
            }
        }
    }

    public void dispatchFinishedGoingToSleep(int i) {
        synchronized (this) {
            this.mDeviceInteractive = false;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(320, i, 0));
    }

    public void dispatchKeyguardDismissAnimationFinished() {
        this.mHandler.sendEmptyMessage(346);
    }

    public void dispatchKeyguardGoingAway(boolean z) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(342, Boolean.valueOf(z)));
    }

    public void dispatchScreenTurnedOff() {
        this.mHandler.sendEmptyMessage(332);
    }

    public void dispatchStartedGoingToSleep(int i) {
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(321, i, 0));
    }

    public void dispatchStartedWakingUp(int i) {
        synchronized (this) {
            this.mDeviceInteractive = true;
        }
        Handler handler = this.mHandler;
        handler.sendMessage(handler.obtainMessage(319, i, 0));
    }

    @Override // com.android.systemui.Dumpable
    @SuppressLint({"MissingPermission"})
    public void dump(PrintWriter printWriter, String[] strArr) {
        int intValue;
        printWriter.println("KeyguardUpdateMonitor state:");
        printWriter.println("  getUserHasTrust()=" + getUserHasTrust(getCurrentUser()));
        printWriter.println("  getUserUnlockedWithBiometric()=" + getUserUnlockedWithBiometric(getCurrentUser()));
        printWriter.println("  mWakeOnFingerprintAcquiredStart=" + this.mWakeOnFingerprintAcquiredStart);
        printWriter.println("  SIM States:");
        for (SimData simData : this.mSimDatas.values()) {
            printWriter.println("    " + simData.toString());
        }
        printWriter.println("  Subs:");
        if (this.mSubscriptionInfo != null) {
            for (int i = 0; i < this.mSubscriptionInfo.size(); i++) {
                printWriter.println("    " + this.mSubscriptionInfo.get(i));
            }
        }
        printWriter.println("  Current active data subId=" + this.mActiveMobileDataSubscription);
        printWriter.println("  Service states:");
        for (Integer num : this.mServiceStates.keySet()) {
            printWriter.println("    " + num.intValue() + "=" + this.mServiceStates.get(Integer.valueOf(intValue)));
        }
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager != null && fingerprintManager.isHardwareDetected()) {
            int userId = this.mUserTracker.getUserId();
            int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(userId);
            BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(userId);
            printWriter.println("  Fingerprint state (user=" + userId + ")");
            StringBuilder sb = new StringBuilder();
            sb.append("    areAllFpAuthenticatorsRegistered=");
            sb.append(this.mAuthController.areAllFingerprintAuthenticatorsRegistered());
            printWriter.println(sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("    allowed=");
            sb2.append(biometricAuthenticated != null && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric));
            printWriter.println(sb2.toString());
            StringBuilder sb3 = new StringBuilder();
            sb3.append("    auth'd=");
            sb3.append(biometricAuthenticated != null && biometricAuthenticated.mAuthenticated);
            printWriter.println(sb3.toString());
            printWriter.println("    authSinceBoot=" + getStrongAuthTracker().hasUserAuthenticatedSinceBoot());
            printWriter.println("    disabled(DPM)=" + isFingerprintDisabled(userId));
            printWriter.println("    possible=" + isUnlockWithFingerprintPossible(userId));
            printWriter.println("    listening: actual=" + this.mFingerprintRunningState + " expected=" + (shouldListenForFingerprint(isUdfpsEnrolled()) ? 1 : 0));
            StringBuilder sb4 = new StringBuilder();
            sb4.append("    strongAuthFlags=");
            sb4.append(Integer.toHexString(strongAuthForUser));
            printWriter.println(sb4.toString());
            printWriter.println("    trustManaged=" + getUserTrustIsManaged(userId));
            printWriter.println("    mFingerprintLockedOut=" + this.mFingerprintLockedOut);
            printWriter.println("    mFingerprintLockedOutPermanent=" + this.mFingerprintLockedOutPermanent);
            printWriter.println("    enabledByUser=" + this.mBiometricEnabledForUser.get(userId));
            printWriter.println("    mKeyguardOccluded=" + this.mKeyguardOccluded);
            printWriter.println("    mIsDreaming=" + this.mIsDreaming);
            if (isUdfpsSupported()) {
                printWriter.println("        udfpsEnrolled=" + isUdfpsEnrolled());
                printWriter.println("        shouldListenForUdfps=" + shouldListenForFingerprint(true));
                printWriter.println("        mPrimaryBouncerIsOrWillBeShowing=" + this.mPrimaryBouncerIsOrWillBeShowing);
                printWriter.println("        mStatusBarState=" + StatusBarState.toString(this.mStatusBarState));
                printWriter.println("        mUdfpsBouncerShowing=" + this.mUdfpsBouncerShowing);
            } else if (isSfpsSupported()) {
                printWriter.println("        sfpsEnrolled=" + isSfpsEnrolled());
                printWriter.println("        shouldListenForSfps=" + shouldListenForFingerprint(false));
                printWriter.println("        mSfpsRequireScreenOnToAuthPrefEnabled=" + this.mSfpsRequireScreenOnToAuthPrefEnabled);
            }
            new DumpsysTableLogger("KeyguardFingerprintListen", KeyguardFingerprintListenModel.TABLE_HEADERS, this.mFingerprintListenBuffer.toList()).printTableData(printWriter);
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null && faceManager.isHardwareDetected()) {
            int userId2 = this.mUserTracker.getUserId();
            int strongAuthForUser2 = this.mStrongAuthTracker.getStrongAuthForUser(userId2);
            BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(userId2);
            printWriter.println("  Face authentication state (user=" + userId2 + ")");
            StringBuilder sb5 = new StringBuilder();
            sb5.append("    allowed=");
            sb5.append(biometricAuthenticated2 != null && isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric));
            printWriter.println(sb5.toString());
            StringBuilder sb6 = new StringBuilder();
            sb6.append("    auth'd=");
            sb6.append(biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated);
            printWriter.println(sb6.toString());
            printWriter.println("    authSinceBoot=" + getStrongAuthTracker().hasUserAuthenticatedSinceBoot());
            printWriter.println("    disabled(DPM)=" + isFaceDisabled(userId2));
            printWriter.println("    possible=" + isUnlockWithFacePossible(userId2));
            printWriter.println("    listening: actual=" + this.mFaceRunningState + " expected=(" + (shouldListenForFace() ? 1 : 0));
            StringBuilder sb7 = new StringBuilder();
            sb7.append("    strongAuthFlags=");
            sb7.append(Integer.toHexString(strongAuthForUser2));
            printWriter.println(sb7.toString());
            printWriter.println("    isNonStrongBiometricAllowedAfterIdleTimeout=" + this.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(userId2));
            printWriter.println("    trustManaged=" + getUserTrustIsManaged(userId2));
            printWriter.println("    mFaceLockedOutPermanent=" + this.mFaceLockedOutPermanent);
            printWriter.println("    enabledByUser=" + this.mBiometricEnabledForUser.get(userId2));
            printWriter.println("    mSecureCameraLaunched=" + this.mSecureCameraLaunched);
            printWriter.println("    mPrimaryBouncerFullyShown=" + this.mPrimaryBouncerFullyShown);
            printWriter.println("    mNeedsSlowUnlockTransition=" + this.mNeedsSlowUnlockTransition);
            new DumpsysTableLogger("KeyguardFaceListen", KeyguardFaceListenModel.TABLE_HEADERS, this.mFaceListenBuffer.toList()).printTableData(printWriter);
        }
        new DumpsysTableLogger("KeyguardActiveUnlockTriggers", KeyguardActiveUnlockModel.TABLE_HEADERS, this.mActiveUnlockTriggerBuffer.toList()).printTableData(printWriter);
    }

    public int getBiometricLockoutDelay() {
        return 600;
    }

    public boolean getCachedIsUnlockWithFingerprintPossible(int i) {
        return this.mIsUnlockWithFingerprintPossible.getOrDefault(Integer.valueOf(i), Boolean.FALSE).booleanValue();
    }

    public int getFaceUnlockBehavior() {
        return this.mFaceUnlockBehavior;
    }

    public List<SubscriptionInfo> getFilteredSubscriptionInfo() {
        List<SubscriptionInfo> subscriptionInfo = getSubscriptionInfo(false);
        if (subscriptionInfo.size() == 2) {
            SubscriptionInfo subscriptionInfo2 = subscriptionInfo.get(0);
            SubscriptionInfo subscriptionInfo3 = subscriptionInfo.get(1);
            if (subscriptionInfo2.getGroupUuid() != null && subscriptionInfo2.getGroupUuid().equals(subscriptionInfo3.getGroupUuid())) {
                if (!subscriptionInfo2.isOpportunistic() && !subscriptionInfo3.isOpportunistic()) {
                    return subscriptionInfo;
                }
                if (CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean")) {
                    if (subscriptionInfo2.isOpportunistic()) {
                        subscriptionInfo3 = subscriptionInfo2;
                    }
                    subscriptionInfo.remove(subscriptionInfo3);
                } else {
                    SubscriptionInfo subscriptionInfo4 = subscriptionInfo2;
                    if (subscriptionInfo2.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                        subscriptionInfo4 = subscriptionInfo3;
                    }
                    subscriptionInfo.remove(subscriptionInfo4);
                }
            }
        }
        return subscriptionInfo;
    }

    @VisibleForTesting
    public Handler getHandler() {
        return this.mHandler;
    }

    public boolean getIsFaceAuthenticated() {
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(getCurrentUser());
        return biometricAuthenticated != null ? biometricAuthenticated.mAuthenticated : false;
    }

    public final InstanceId getKeyguardSessionId() {
        return ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1);
    }

    public int getNextSubIdForState(int i) {
        int i2 = 0;
        List<SubscriptionInfo> subscriptionInfo = getSubscriptionInfo(false);
        int i3 = -1;
        int i4 = Integer.MAX_VALUE;
        while (true) {
            int i5 = i4;
            if (i2 >= subscriptionInfo.size()) {
                return i3;
            }
            int subscriptionId = subscriptionInfo.get(i2).getSubscriptionId();
            int slotId = getSlotId(subscriptionId);
            int i6 = i3;
            int i7 = i5;
            if (i == getSimState(subscriptionId)) {
                i6 = i3;
                i7 = i5;
                if (i5 > slotId) {
                    i6 = subscriptionId;
                    i7 = slotId;
                }
            }
            i2++;
            i3 = i6;
            i4 = i7;
        }
    }

    public Intent getSecondaryLockscreenRequirement(int i) {
        return this.mSecondaryLockscreenRequirement.get(Integer.valueOf(i));
    }

    public ServiceState getServiceState(int i) {
        return this.mServiceStates.get(Integer.valueOf(i));
    }

    public int getSimState(int i) {
        if (this.mSimDatas.containsKey(Integer.valueOf(i))) {
            return this.mSimDatas.get(Integer.valueOf(i)).simState;
        }
        return 0;
    }

    public final int getSlotId(int i) {
        if (!this.mSimDatas.containsKey(Integer.valueOf(i))) {
            refreshSimState(i, SubscriptionManager.getSlotIndex(i));
        }
        return this.mSimDatas.get(Integer.valueOf(i)).slotId;
    }

    public StrongAuthTracker getStrongAuthTracker() {
        return this.mStrongAuthTracker;
    }

    public List<SubscriptionInfo> getSubscriptionInfo(boolean z) {
        List<SubscriptionInfo> list = this.mSubscriptionInfo;
        if (list == null || z) {
            list = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        }
        if (list == null) {
            this.mSubscriptionInfo = new ArrayList();
        } else {
            this.mSubscriptionInfo = list;
        }
        return new ArrayList(this.mSubscriptionInfo);
    }

    public SubscriptionInfo getSubscriptionInfoForSubId(int i) {
        List<SubscriptionInfo> subscriptionInfo = getSubscriptionInfo(false);
        for (int i2 = 0; i2 < subscriptionInfo.size(); i2++) {
            SubscriptionInfo subscriptionInfo2 = subscriptionInfo.get(i2);
            if (i == subscriptionInfo2.getSubscriptionId()) {
                return subscriptionInfo2;
            }
        }
        return null;
    }

    public boolean getUserCanSkipBouncer(int i) {
        return getUserHasTrust(i) || getUserUnlockedWithBiometric(i);
    }

    public boolean getUserHasTrust(int i) {
        return !isTrustDisabled() && this.mUserHasTrust.get(i);
    }

    public boolean getUserTrustIsManaged(int i) {
        return this.mUserTrustIsManaged.get(i) && !isTrustDisabled();
    }

    public boolean getUserUnlockedWithBiometric(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(i);
        boolean z = biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric);
        boolean z2 = biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric);
        boolean z3 = true;
        if (!z) {
            z3 = z2;
        }
        return z3;
    }

    public boolean getUserUnlockedWithBiometricAndIsBypassing(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(i);
        boolean z = biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric);
        boolean z2 = biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric);
        boolean z3 = true;
        if (!z) {
            z3 = z2 && this.mKeyguardBypassController.canBypass();
        }
        return z3;
    }

    public final void handleAirplaneModeChanged() {
        callbacksRefreshCarrierInfo();
    }

    public final void handleBatteryUpdate(BatteryStatus batteryStatus) {
        Assert.isMainThread();
        this.mLogger.d("handleBatteryUpdate");
        boolean isBatteryUpdateInteresting = isBatteryUpdateInteresting(this.mBatteryStatus, batteryStatus);
        this.mBatteryStatus = batteryStatus;
        if (isBatteryUpdateInteresting) {
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onRefreshBatteryInfo(batteryStatus);
                }
            }
        }
    }

    public final void handleDevicePolicyManagerStateChanged(int i) {
        Assert.isMainThread();
        updateFingerprintListeningState(2);
        updateSecondaryLockscreenRequirement(i);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onDevicePolicyManagerStateChanged();
            }
        }
    }

    public final void handleDeviceProvisioned() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onDeviceProvisioned();
            }
        }
        if (this.mDeviceProvisionedObserver != null) {
            this.mContext.getContentResolver().unregisterContentObserver(this.mDeviceProvisionedObserver);
            this.mDeviceProvisionedObserver = null;
        }
    }

    public final void handleDreamingStateChanged(int i) {
        Assert.isMainThread();
        this.mIsDreaming = i == 1;
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onDreamingStateChanged(this.mIsDreaming);
            }
        }
        updateFingerprintListeningState(2);
        if (this.mIsDreaming) {
            updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_DREAM_STARTED);
        }
    }

    public final void handleFaceAcquired(int i) {
        Assert.isMainThread();
        this.mLogger.logFaceAcquired(i);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FACE, i);
            }
        }
    }

    public final void handleFaceAuthFailed() {
        Assert.isMainThread();
        this.mLogger.d("onFaceAuthFailed");
        this.mFaceCancelSignal = null;
        setFaceRunningState(0);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FACE);
            }
        }
        handleFaceHelp(-2, this.mContext.getString(R$string.kg_face_not_recognized));
    }

    public final void handleFaceAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFaceAuthenticated");
        if (this.mOccludingAppRequestingFace) {
            requestFaceAuthOnOccludingApp(false);
        }
        try {
            if (this.mGoingToSleep) {
                this.mLogger.d("Aborted successful auth because device is going to sleep.");
                return;
            }
            int userId = this.mUserTracker.getUserId();
            if (userId != i) {
                this.mLogger.logFaceAuthForWrongUser(i);
            } else if (isFaceDisabled(userId)) {
                this.mLogger.logFaceAuthDisabledForUser(userId);
            } else {
                this.mLogger.logFaceAuthSuccess(userId);
                onFaceAuthenticated(userId, z);
                setFaceRunningState(0);
                Trace.endSection();
            }
        } finally {
            setFaceRunningState(0);
        }
    }

    public final void handleFaceError(int i, String str) {
        int i2;
        boolean z;
        Assert.isMainThread();
        this.mLogger.logFaceAuthError(i, str);
        if (this.mHandler.hasCallbacks(this.mFaceCancelNotReceived)) {
            this.mHandler.removeCallbacks(this.mFaceCancelNotReceived);
        }
        this.mFaceCancelSignal = null;
        boolean isSensorPrivacyEnabled = this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
        if (i == 5 && this.mFaceRunningState == 3) {
            setFaceRunningState(0);
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_DURING_CANCELLATION);
        } else {
            setFaceRunningState(0);
        }
        boolean z2 = i == 1;
        if ((z2 || i == 2) && (i2 = this.mHardwareFaceUnavailableRetryCount) < 20) {
            this.mHardwareFaceUnavailableRetryCount = i2 + 1;
            this.mHandler.removeCallbacks(this.mRetryFaceAuthentication);
            this.mHandler.postDelayed(this.mRetryFaceAuthentication, 500L);
        }
        if (i == 9) {
            z = !this.mFaceLockedOutPermanent;
            this.mFaceLockedOutPermanent = true;
        } else {
            z = false;
        }
        int i3 = 0;
        String str2 = str;
        if (z2) {
            i3 = 0;
            str2 = str;
            if (isSensorPrivacyEnabled) {
                str2 = this.mContext.getString(R$string.kg_face_sensor_privacy_enabled);
                i3 = 0;
            }
        }
        while (i3 < this.mCallbacks.size()) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i3).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricError(i, str2, BiometricSourceType.FACE);
            }
            i3++;
        }
        if (z) {
            notifyLockedOutStateChanged(BiometricSourceType.FACE);
        }
    }

    public final void handleFaceHelp(int i, String str) {
        Assert.isMainThread();
        this.mLogger.logFaceAuthHelpMsg(i, str);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FACE);
            }
        }
    }

    public final void handleFaceLockoutReset(int i) {
        this.mLogger.logFaceLockoutReset(i);
        boolean z = this.mFaceLockedOutPermanent;
        boolean z2 = i == 2;
        this.mFaceLockedOutPermanent = z2;
        boolean z3 = z2 != z;
        this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.$r8$lambda$W5kKjsMrYheN7jlvSzd1IQzWzig(KeyguardUpdateMonitor.this);
            }
        }, getBiometricLockoutDelay());
        if (z3) {
            notifyLockedOutStateChanged(BiometricSourceType.FACE);
        }
    }

    public final void handleFingerprintAcquired(int i) {
        Assert.isMainThread();
        if (this.mWakeOnFingerprintAcquiredStart && i == 7) {
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 17, "com.android.systemui.keyguard:FINGERPRINT_ACQUIRED_START");
        }
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i);
            }
        }
    }

    public final void handleFingerprintAuthFailed() {
        Assert.isMainThread();
        if (this.mHandler.hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.d("handleFingerprintAuthFailed() triggered while waiting for cancellation, removing watchdog");
            this.mHandler.removeCallbacks(this.mFpCancelNotReceived);
        }
        this.mLogger.d("handleFingerprintAuthFailed");
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
            }
        }
        if (isUdfpsSupported()) {
            handleFingerprintHelp(-1, this.mContext.getString(17040383));
        } else {
            handleFingerprintHelp(-1, this.mContext.getString(17040368));
        }
    }

    public final void handleFingerprintAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        if (this.mOccludingAppRequestingFace) {
            requestFaceAuthOnOccludingApp(false);
        }
        if (this.mHandler.hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.d("handleFingerprintAuthenticated() triggered while waiting for cancellation, removing watchdog");
            this.mHandler.removeCallbacks(this.mFpCancelNotReceived);
        }
        try {
            int userId = this.mUserTracker.getUserId();
            if (userId != i) {
                this.mLogger.logFingerprintAuthForWrongUser(i);
            } else if (isFingerprintDisabled(userId)) {
                this.mLogger.logFingerprintDisabledForUser(userId);
            } else {
                onFingerprintAuthenticated(userId, z);
                setFingerprintRunningState(0);
                Trace.endSection();
            }
        } finally {
            setFingerprintRunningState(0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x00c2, code lost:
        if (r6 == 9) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleFingerprintError(int i, String str) {
        boolean z;
        boolean z2;
        Assert.isMainThread();
        if (this.mHandler.hasCallbacks(this.mFpCancelNotReceived)) {
            this.mHandler.removeCallbacks(this.mFpCancelNotReceived);
        }
        this.mFingerprintCancelSignal = null;
        if (i == 5 && this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(0);
            updateFingerprintListeningState(2);
        } else {
            setFingerprintRunningState(0);
        }
        if (i == 1) {
            this.mLogger.logRetryAfterFpErrorWithDelay(i, str, 500);
            this.mHandler.postDelayed(this.mRetryFingerprintAuthenticationAfterHwUnavailable, 500L);
        }
        if (i == 19) {
            this.mLogger.logRetryAfterFpErrorWithDelay(i, str, 50);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor.$r8$lambda$E_LXV5UP82IkJRv0IaLa65Rrvns(KeyguardUpdateMonitor.this);
                }
            }, 50L);
        }
        if (i == 9) {
            z = !this.mFingerprintLockedOutPermanent;
            this.mFingerprintLockedOutPermanent = true;
            this.mLogger.d("Fingerprint permanently locked out - requiring stronger auth");
            this.mLockPatternUtils.requireStrongAuth(8, getCurrentUser());
        } else {
            z = false;
        }
        if (i != 7) {
            z2 = z;
        }
        z2 = z | (!this.mFingerprintLockedOut);
        this.mFingerprintLockedOut = true;
        this.mLogger.d("Fingerprint temporarily locked out - requiring stronger auth");
        if (isUdfpsEnrolled()) {
            updateFingerprintListeningState(2);
        }
        stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_FP_LOCKED_OUT);
        this.mLogger.logFingerprintError(i, str);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricError(i, str, BiometricSourceType.FINGERPRINT);
            }
        }
        if (z2) {
            notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
        }
    }

    public final void handleFingerprintHelp(int i, String str) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FINGERPRINT);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:43:0x0051, code lost:
        if (r11 != r0) goto L23;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleFingerprintLockoutReset(int i) {
        boolean z;
        this.mLogger.logFingerprintLockoutReset(i);
        boolean z2 = this.mFingerprintLockedOut;
        boolean z3 = this.mFingerprintLockedOutPermanent;
        boolean z4 = i == 1 || i == 2;
        this.mFingerprintLockedOut = z4;
        boolean z5 = i == 2;
        this.mFingerprintLockedOutPermanent = z5;
        if (z4 == z2) {
            z = false;
        }
        z = true;
        if (isUdfpsEnrolled()) {
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor.$r8$lambda$dtzaN3RPhGIpEAtaRDeXIljDj0w(KeyguardUpdateMonitor.this);
                }
            }, getBiometricLockoutDelay());
        } else {
            updateFingerprintListeningState(2);
        }
        if (z) {
            notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
        }
    }

    public void handleFinishedGoingToSleep(int i) {
        Assert.isMainThread();
        this.mGoingToSleep = false;
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onFinishedGoingToSleep(i);
            }
        }
        updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP);
    }

    public final void handleKeyguardDismissAnimationFinished() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onKeyguardDismissAnimationFinished();
            }
        }
    }

    public final void handleKeyguardGoingAway(boolean z) {
        Assert.isMainThread();
        setKeyguardGoingAway(z);
    }

    public final void handleKeyguardReset() {
        this.mLogger.d("handleKeyguardReset");
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_RESET);
        this.mPrimaryBouncerFullyShown = false;
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
    }

    public final void handlePhoneStateChanged(String str) {
        int i;
        Assert.isMainThread();
        this.mLogger.logPhoneStateChanged(str);
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(str)) {
            this.mPhoneState = 0;
            i = 0;
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(str)) {
            this.mPhoneState = 2;
            i = 0;
        } else {
            i = 0;
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(str)) {
                this.mPhoneState = 1;
                i = 0;
            }
        }
        while (i < this.mCallbacks.size()) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onPhoneStateChanged(this.mPhoneState);
            }
            i++;
        }
    }

    public final void handlePrimaryBouncerChanged(int i, int i2) {
        Assert.isMainThread();
        boolean z = this.mPrimaryBouncerIsOrWillBeShowing;
        boolean z2 = this.mPrimaryBouncerFullyShown;
        boolean z3 = i == 1;
        this.mPrimaryBouncerIsOrWillBeShowing = z3;
        boolean z4 = i2 == 1;
        this.mPrimaryBouncerFullyShown = z4;
        this.mLogger.logPrimaryKeyguardBouncerChanged(z3, z4);
        if (this.mPrimaryBouncerFullyShown) {
            this.mSecureCameraLaunched = false;
        } else {
            this.mCredentialAttempted = false;
        }
        if (z != this.mPrimaryBouncerIsOrWillBeShowing) {
            for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i3).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onKeyguardBouncerStateChanged(this.mPrimaryBouncerIsOrWillBeShowing);
                }
            }
            updateFingerprintListeningState(2);
        }
        boolean z5 = this.mPrimaryBouncerFullyShown;
        if (z2 != z5) {
            int i4 = 0;
            if (z5) {
                requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.UNLOCK_INTENT, "bouncerFullyShown");
                i4 = 0;
            }
            while (i4 < this.mCallbacks.size()) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = this.mCallbacks.get(i4).get();
                if (keyguardUpdateMonitorCallback2 != null) {
                    keyguardUpdateMonitorCallback2.onKeyguardBouncerFullyShowingChanged(this.mPrimaryBouncerFullyShown);
                }
                i4++;
            }
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN);
        }
    }

    public final void handleReportEmergencyCallAction() {
        this.mPrimaryBouncerFullyShown = false;
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onEmergencyCallAction();
            }
        }
    }

    public final void handleRequireUnlockForNfc() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onRequireUnlockForNfc();
            }
        }
    }

    public final void handleScreenTurnedOff() {
        Assert.isMainThread();
        this.mHardwareFingerprintUnavailableRetryCount = 0;
        this.mHardwareFaceUnavailableRetryCount = 0;
    }

    @VisibleForTesting
    public void handleServiceStateChange(int i, ServiceState serviceState) {
        this.mLogger.logServiceStateChange(i, serviceState);
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            this.mLogger.w("invalid subId in handleServiceStateChange()");
            return;
        }
        updateTelephonyCapable(true);
        this.mServiceStates.put(Integer.valueOf(i), serviceState);
        callbacksRefreshCarrierInfo();
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x0098  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0112  */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleSimStateChange(int i, int i2, int i3) {
        boolean z;
        SimData simData;
        int i4;
        Assert.isMainThread();
        this.mLogger.logSimState(i, i2, i3);
        boolean z2 = true;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            this.mLogger.w("invalid subId in handleSimStateChange()");
            if (i3 == 1) {
                updateTelephonyCapable(true);
                for (SimData simData2 : this.mSimDatas.values()) {
                    if (simData2.slotId == i2) {
                        simData2.simState = 1;
                    }
                }
                z = true;
                simData = this.mSimDatas.get(Integer.valueOf(i));
                if (simData != null) {
                    this.mSimDatas.put(Integer.valueOf(i), new SimData(i3, i2, i));
                } else {
                    z2 = true;
                    if (simData.simState == i3) {
                        z2 = true;
                        if (simData.subId == i) {
                            z2 = simData.slotId != i2;
                        }
                    }
                    simData.simState = i3;
                    simData.subId = i;
                    simData.slotId = i2;
                }
                if ((!z2 || z) && i3 != 0) {
                    for (i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i4).get();
                        if (keyguardUpdateMonitorCallback != null) {
                            keyguardUpdateMonitorCallback.onSimStateChanged(i, i2, i3);
                        }
                    }
                }
                return;
            } else if (i3 != 8) {
                return;
            } else {
                updateTelephonyCapable(true);
            }
        }
        z = false;
        simData = this.mSimDatas.get(Integer.valueOf(i));
        if (simData != null) {
        }
        if (z2) {
        }
        while (i4 < this.mCallbacks.size()) {
        }
    }

    public final void handleSimSubscriptionInfoChanged() {
        Assert.isMainThread();
        this.mLogger.v("onSubscriptionInfoChanged()");
        List<SubscriptionInfo> completeActiveSubscriptionInfoList = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        if (completeActiveSubscriptionInfoList != null) {
            for (SubscriptionInfo subscriptionInfo : completeActiveSubscriptionInfoList) {
                this.mLogger.logSubInfo(subscriptionInfo);
            }
        } else {
            this.mLogger.v("onSubscriptionInfoChanged: list is null");
        }
        List<SubscriptionInfo> subscriptionInfo2 = getSubscriptionInfo(true);
        ArrayList arrayList = new ArrayList();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < subscriptionInfo2.size(); i++) {
            SubscriptionInfo subscriptionInfo3 = subscriptionInfo2.get(i);
            hashSet.add(Integer.valueOf(subscriptionInfo3.getSubscriptionId()));
            if (refreshSimState(subscriptionInfo3.getSubscriptionId(), subscriptionInfo3.getSimSlotIndex())) {
                arrayList.add(subscriptionInfo3);
            }
        }
        Iterator<Map.Entry<Integer, SimData>> it = this.mSimDatas.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, SimData> next = it.next();
            if (!hashSet.contains(next.getKey())) {
                this.mLogger.logInvalidSubId(next.getKey().intValue());
                it.remove();
                SimData value = next.getValue();
                for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onSimStateChanged(value.subId, value.slotId, value.simState);
                    }
                }
            }
        }
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            SimData simData = this.mSimDatas.get(Integer.valueOf(((SubscriptionInfo) arrayList.get(i3)).getSubscriptionId()));
            for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = this.mCallbacks.get(i4).get();
                if (keyguardUpdateMonitorCallback2 != null) {
                    keyguardUpdateMonitorCallback2.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
                }
            }
        }
        callbacksRefreshCarrierInfo();
    }

    public void handleStartedGoingToSleep(int i) {
        Assert.isMainThread();
        clearBiometricRecognized();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStartedGoingToSleep(i);
            }
        }
        this.mGoingToSleep = true;
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_GOING_TO_SLEEP);
    }

    public void handleStartedWakingUp(int i) {
        Trace.beginSection("KeyguardUpdateMonitor#handleStartedWakingUp");
        Assert.isMainThread();
        updateFingerprintListeningState(2);
        if (this.mFaceWakeUpTriggersConfig.shouldTriggerFaceAuthOnWakeUpFrom(i)) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP;
            faceAuthUiEvent.setExtraInfo(i);
            updateFaceListeningState(2, faceAuthUiEvent);
            requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.WAKE, "wakingUp - " + PowerManager.wakeReasonToString(i));
        } else {
            this.mLogger.logSkipUpdateFaceListeningOnWakeup(i);
        }
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStartedWakingUp();
            }
        }
        Trace.endSection();
    }

    public final void handleTimeFormatUpdate(String str) {
        Assert.isMainThread();
        this.mLogger.logTimeFormatChanged(str);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTimeFormatChanged(str);
            }
        }
    }

    public final void handleTimeUpdate() {
        Assert.isMainThread();
        this.mLogger.d("handleTimeUpdate");
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTimeChanged();
            }
        }
    }

    public final void handleTimeZoneUpdate(String str) {
        Assert.isMainThread();
        this.mLogger.d("handleTimeZoneUpdate");
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTimeZoneChanged(TimeZone.getTimeZone(str));
                keyguardUpdateMonitorCallback.onTimeChanged();
            }
        }
    }

    @VisibleForTesting
    public void handleUserRemoved(int i) {
        Assert.isMainThread();
        this.mUserIsUnlocked.delete(i);
        this.mUserTrustIsUsuallyManaged.delete(i);
    }

    public final void handleUserStopped(int i) {
        Assert.isMainThread();
        this.mUserIsUnlocked.put(i, this.mUserManager.isUserUnlocked(i));
    }

    @VisibleForTesting
    public void handleUserSwitchComplete(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitchComplete(i);
            }
        }
        if (this.mFaceManager != null && !this.mFaceSensorProperties.isEmpty()) {
            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
            handleFaceLockoutReset(this.mFaceManager.getLockoutModeForUser(this.mFaceSensorProperties.get(0).sensorId, i));
        }
        if (this.mFpm != null && !this.mFingerprintSensorProperties.isEmpty()) {
            stopListeningForFingerprint();
            handleFingerprintLockoutReset(this.mFpm.getLockoutModeForUser(this.mFingerprintSensorProperties.get(0).sensorId, i));
        }
        this.mInteractionJankMonitor.end(37);
        this.mLatencyTracker.onActionEnd(12);
    }

    @VisibleForTesting
    public void handleUserSwitching(int i, IRemoteCallback iRemoteCallback) {
        Assert.isMainThread();
        clearBiometricRecognized();
        this.mUserTrustIsUsuallyManaged.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitching(i);
            }
        }
        try {
            iRemoteCallback.sendResult((Bundle) null);
        } catch (RemoteException e) {
            this.mLogger.logException(e, "Ignored exception while userSwitching");
        }
    }

    public final void handleUserUnlocked(int i) {
        Assert.isMainThread();
        this.mUserIsUnlocked.put(i, true);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserUnlocked();
            }
        }
    }

    public final void initializeSimState() {
        for (int i = 0; i < this.mTelephonyManager.getActiveModemCount(); i++) {
            int simState = this.mTelephonyManager.getSimState(i);
            int[] subscriptionIds = this.mSubscriptionManager.getSubscriptionIds(i);
            if (subscriptionIds != null) {
                for (int i2 : subscriptionIds) {
                    this.mHandler.obtainMessage(304, i2, i, Integer.valueOf(simState)).sendToTarget();
                }
            }
        }
    }

    public final void initiateActiveUnlock(String str) {
        if (!this.mHandler.hasMessages(336) && shouldTriggerActiveUnlock()) {
            this.mLogger.logActiveUnlockTriggered(str);
            this.mTrustManager.reportUserMayRequestUnlock(getCurrentUser());
        }
    }

    public final boolean isBatteryUpdateInteresting(BatteryStatus batteryStatus, BatteryStatus batteryStatus2) {
        boolean isPluggedIn = batteryStatus2.isPluggedIn();
        boolean isPluggedIn2 = batteryStatus.isPluggedIn();
        boolean z = false;
        boolean z2 = isPluggedIn2 && isPluggedIn && batteryStatus.status != batteryStatus2.status;
        boolean z3 = batteryStatus2.present;
        boolean z4 = batteryStatus.present;
        if (isPluggedIn2 == isPluggedIn && !z2 && batteryStatus.level == batteryStatus2.level) {
            if (!isPluggedIn || batteryStatus2.maxChargingWattage == batteryStatus.maxChargingWattage) {
                if ((!isPluggedIn || batteryStatus2.oemFastChargeStatus == batteryStatus.oemFastChargeStatus) && z4 == z3) {
                    if (batteryStatus2.health != batteryStatus.health) {
                        z = true;
                    }
                    return z;
                }
                return true;
            }
            return true;
        }
        return true;
    }

    public boolean isDeviceInteractive() {
        return this.mDeviceInteractive;
    }

    public boolean isDeviceProvisioned() {
        return this.mDeviceProvisioned;
    }

    public final boolean isDeviceProvisionedInSettingsDb() {
        boolean z = false;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0) {
            z = true;
        }
        return z;
    }

    public boolean isDreaming() {
        return this.mIsDreaming;
    }

    public boolean isEncryptedOrLockdown(int i) {
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(i);
        boolean z = false;
        boolean z2 = containsFlag(strongAuthForUser, 2) || containsFlag(strongAuthForUser, 32);
        if (containsFlag(strongAuthForUser, 1) || z2) {
            z = true;
        }
        return z;
    }

    public boolean isFaceAuthEnabledForUser(int i) {
        updateFaceEnrolled(i);
        return this.mIsFaceEnrolled;
    }

    public boolean isFaceDetectionRunning() {
        boolean z = true;
        if (this.mFaceRunningState != 1) {
            z = false;
        }
        return z;
    }

    public final boolean isFaceDisabled(final int i) {
        return ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardUpdateMonitor.$r8$lambda$lR0vlGVJ1ALtTBPRdbdxVk6EpsA(KeyguardUpdateMonitor.this, i);
            }
        })).booleanValue();
    }

    public boolean isFaceEnrolled() {
        return this.mIsFaceEnrolled;
    }

    public boolean isFaceLockedOut() {
        return this.mFaceLockedOutPermanent;
    }

    public boolean isFingerprintDetectionRunning() {
        boolean z = true;
        if (this.mFingerprintRunningState != 1) {
            z = false;
        }
        return z;
    }

    public final boolean isFingerprintDisabled(int i) {
        return (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 32) != 0 || isSimPinSecure();
    }

    public boolean isFingerprintLockedOut() {
        return this.mFingerprintLockedOut || this.mFingerprintLockedOutPermanent;
    }

    public boolean isGoingToSleep() {
        return this.mGoingToSleep;
    }

    public boolean isKeyguardVisible() {
        return this.mKeyguardShowing && !this.mKeyguardOccluded;
    }

    public boolean isLogoutEnabled() {
        return this.mLogoutEnabled;
    }

    public boolean isSecureCameraLaunchedOverKeyguard() {
        return this.mSecureCameraLaunched;
    }

    public boolean isSfpsEnrolled() {
        return this.mAuthController.isSfpsEnrolled(getCurrentUser());
    }

    public boolean isSfpsSupported() {
        return (this.mAuthController.getSfpsProps() == null || this.mAuthController.getSfpsProps().isEmpty()) ? false : true;
    }

    public boolean isSimPinSecure() {
        for (SubscriptionInfo subscriptionInfo : getSubscriptionInfo(false)) {
            if (isSimPinSecure(getSimState(subscriptionInfo.getSubscriptionId()))) {
                return true;
            }
        }
        return false;
    }

    public boolean isSimPinVoiceSecure() {
        return isSimPinSecure();
    }

    public boolean isSwitchingUser() {
        return this.mSwitchingUser;
    }

    public final boolean isTrustDisabled() {
        return isSimPinSecure();
    }

    public boolean isTrustUsuallyManaged(int i) {
        Assert.isMainThread();
        return this.mUserTrustIsUsuallyManaged.get(i);
    }

    public boolean isUdfpsEnrolled() {
        return this.mAuthController.isUdfpsEnrolled(getCurrentUser());
    }

    public boolean isUdfpsSupported() {
        return (this.mAuthController.getUdfpsProps() == null || this.mAuthController.getUdfpsProps().isEmpty()) ? false : true;
    }

    public boolean isUnlockWithFacePossible(int i) {
        return isFaceAuthEnabledForUser(i) && !isFaceDisabled(i);
    }

    @VisibleForTesting
    @SuppressLint({"MissingPermission"})
    public boolean isUnlockWithFingerprintPossible(int i) {
        HashMap<Integer, Boolean> hashMap = this.mIsUnlockWithFingerprintPossible;
        FingerprintManager fingerprintManager = this.mFpm;
        hashMap.put(Integer.valueOf(i), Boolean.valueOf(fingerprintManager != null && fingerprintManager.isHardwareDetected() && !isFingerprintDisabled(i) && this.mFpm.hasEnrolledTemplates(i)));
        return this.mIsUnlockWithFingerprintPossible.get(Integer.valueOf(i)).booleanValue();
    }

    public boolean isUnlockingWithBiometricAllowed(BiometricSourceType biometricSourceType) {
        int i = AnonymousClass20.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return false;
            }
            return isUnlockingWithBiometricAllowed(false);
        }
        return isUnlockingWithBiometricAllowed(true);
    }

    public boolean isUnlockingWithBiometricAllowed(boolean z) {
        return this.mStrongAuthTracker.isUnlockingWithBiometricAllowed(z) && !this.mFingerprintLockedOut;
    }

    public boolean isUnlockingWithBiometricsPossible(int i) {
        return isUnlockWithFacePossible(i) || isUnlockWithFingerprintPossible(i);
    }

    public boolean isUnlockingWithFingerprintAllowed() {
        return isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
    }

    public boolean isUserInLockdown(int i) {
        return containsFlag(this.mStrongAuthTracker.getStrongAuthForUser(i), 32);
    }

    public boolean isUserUnlocked(int i) {
        return this.mUserIsUnlocked.get(i);
    }

    public final void logListenerModelData(KeyguardListenModel keyguardListenModel) {
        this.mLogger.logKeyguardListenerModel(keyguardListenModel);
        if (keyguardListenModel instanceof KeyguardFingerprintListenModel) {
            this.mFingerprintListenBuffer.insert((KeyguardFingerprintListenModel) keyguardListenModel);
        } else if (keyguardListenModel instanceof KeyguardActiveUnlockModel) {
            this.mActiveUnlockTriggerBuffer.insert((KeyguardActiveUnlockModel) keyguardListenModel);
        } else if (keyguardListenModel instanceof KeyguardFaceListenModel) {
            this.mFaceListenBuffer.insert((KeyguardFaceListenModel) keyguardListenModel);
        }
    }

    public boolean needsSlowUnlockTransition() {
        return this.mNeedsSlowUnlockTransition;
    }

    public final void notifyFaceRunningStateChanged() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(isFaceDetectionRunning(), BiometricSourceType.FACE);
            }
        }
    }

    public final void notifyFingerprintRunningStateChanged() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(isFingerprintDetectionRunning(), BiometricSourceType.FINGERPRINT);
            }
        }
    }

    public final void notifyLockedOutStateChanged(BiometricSourceType biometricSourceType) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onLockedOutStateChanged(biometricSourceType);
            }
        }
    }

    @VisibleForTesting
    public void notifyNonStrongBiometricAllowedChanged(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onNonStrongBiometricAllowedChanged(i);
            }
        }
        if (i == getCurrentUser()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(getCurrentUser()) ? -1 : 1);
            updateBiometricListeningState(1, faceAuthUiEvent);
        }
    }

    @VisibleForTesting
    public void notifyStrongAuthAllowedChanged(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStrongAuthStateChanged(i);
            }
        }
        if (i == getCurrentUser()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.getStrongAuthForUser(getCurrentUser()));
            updateBiometricListeningState(1, faceAuthUiEvent);
        }
    }

    public void onAuthInterruptDetected(boolean z) {
        this.mLogger.logAuthInterruptDetected(z);
        if (this.mAuthInterruptActive == z) {
            return;
        }
        this.mAuthInterruptActive = z;
        updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD);
        requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.WAKE, "onReach");
    }

    public void onCameraLaunched() {
        this.mSecureCameraLaunched = true;
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_CAMERA_LAUNCHED);
    }

    @VisibleForTesting
    public void onFaceAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#onFaceAuthenticated");
        Assert.isMainThread();
        this.mUserFaceAuthenticated.put(i, new BiometricAuthenticated(true, z));
        if (getUserCanSkipBouncer(i)) {
            this.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FACE);
        }
        this.mFaceCancelSignal = null;
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED);
        this.mLogger.d("onFaceAuthenticated");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FACE, z);
            }
        }
        this.mAssistantVisible = false;
        reportSuccessfulBiometricUnlock(z, i);
        Trace.endSection();
    }

    public final void onFaceCancelNotReceived() {
        this.mLogger.e("Face cancellation not received, transitioning to STOPPED");
        this.mFaceRunningState = 0;
        updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_CANCEL_NOT_RECEIVED);
    }

    @VisibleForTesting
    public void onFingerprintAuthenticated(int i, boolean z) {
        Assert.isMainThread();
        Trace.beginSection("KeyGuardUpdateMonitor#onFingerPrintAuthenticated");
        this.mUserFingerprintAuthenticated.put(i, new BiometricAuthenticated(true, z));
        if (getUserCanSkipBouncer(i)) {
            this.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FINGERPRINT);
        }
        this.mFingerprintCancelSignal = null;
        this.mLogger.logFingerprintSuccess(i, z);
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FP_AUTHENTICATED);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FINGERPRINT, z);
            }
        }
        Handler handler = this.mHandler;
        handler.sendMessageDelayed(handler.obtainMessage(336), 500L);
        this.mAssistantVisible = false;
        reportSuccessfulBiometricUnlock(z, i);
        Trace.endSection();
    }

    public final void onFingerprintCancelNotReceived() {
        this.mLogger.e("Fp cancellation not received, transitioning to STOPPED");
        this.mFingerprintRunningState = 0;
        updateFingerprintListeningState(1);
    }

    public void onTrustChanged(boolean z, boolean z2, int i, int i2, List<String> list) {
        Assert.isMainThread();
        boolean z3 = this.mUserHasTrust.get(i, false);
        this.mUserHasTrust.put(i, z);
        if (z3 == z || z) {
            updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_TRUST_ENABLED);
        } else {
            updateBiometricListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_TRUST_DISABLED);
        }
        if (z) {
            String str = null;
            if (getCurrentUser() == i) {
                str = null;
                if (list != null) {
                    Iterator<String> it = list.iterator();
                    str = null;
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        String next = it.next();
                        str = next;
                        if (!TextUtils.isEmpty(next)) {
                            str = next;
                            break;
                        }
                    }
                }
            }
            this.mLogger.logTrustGrantedWithFlags(i2, z2, i, str);
            if (i == getCurrentUser()) {
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(i2);
                for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i3).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onTrustGrantedForCurrentUser(shouldDismissKeyguardOnTrustGrantedWithCurrentUser(trustGrantFlags), z2, trustGrantFlags, str);
                    }
                }
            }
        }
        this.mLogger.logTrustChanged(z3, z, i);
        for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = this.mCallbacks.get(i4).get();
            if (keyguardUpdateMonitorCallback2 != null) {
                keyguardUpdateMonitorCallback2.onTrustChanged(i);
            }
        }
    }

    public void onTrustError(CharSequence charSequence) {
        dispatchErrorMessage(charSequence);
    }

    public void onTrustManagedChanged(boolean z, int i) {
        Assert.isMainThread();
        this.mUserTrustIsManaged.put(i, z);
        this.mUserTrustIsUsuallyManaged.put(i, this.mTrustManager.isTrustUsuallyManaged(i));
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustManagedChanged(i);
            }
        }
    }

    public void onUdfpsPointerDown(int i) {
        this.mFingerprintAuthenticationCallback.onUdfpsPointerDown(i);
    }

    public void onUdfpsPointerUp(int i) {
        this.mFingerprintAuthenticationCallback.onUdfpsPointerUp(i);
    }

    public final boolean refreshSimState(int i, int i2) {
        int simState = this.mTelephonyManager.getSimState(i2);
        SimData simData = this.mSimDatas.get(Integer.valueOf(i));
        boolean z = true;
        if (simData == null) {
            this.mSimDatas.put(Integer.valueOf(i), new SimData(simState, i2, i));
            z = true;
        } else {
            if (simData.simState == simState) {
                z = false;
            }
            simData.simState = simState;
        }
        return z;
    }

    public void registerCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        this.mLogger.logRegisterCallback(keyguardUpdateMonitorCallback);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (this.mCallbacks.get(i).get() == keyguardUpdateMonitorCallback) {
                this.mLogger.logException(new Exception("Called by"), "Object tried to add another callback");
                return;
            }
        }
        this.mCallbacks.add(new WeakReference<>(keyguardUpdateMonitorCallback));
        removeCallback(null);
        sendUpdates(keyguardUpdateMonitorCallback);
    }

    public void removeCallback(final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        this.mLogger.logUnregisterCallback(keyguardUpdateMonitorCallback);
        this.mCallbacks.removeIf(new Predicate() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return KeyguardUpdateMonitor.m710$r8$lambda$T49MKzabbosDxCKYc8bGVTR0dQ(KeyguardUpdateMonitorCallback.this, (WeakReference) obj);
            }
        });
    }

    public void reportEmergencyCallAction(boolean z) {
        if (!z) {
            this.mHandler.obtainMessage(318).sendToTarget();
            return;
        }
        Assert.isMainThread();
        handleReportEmergencyCallAction();
    }

    public void reportSimUnlocked(int i) {
        this.mLogger.logSimUnlocked(i);
        handleSimStateChange(i, getSlotId(i), 5);
    }

    public final void reportSuccessfulBiometricUnlock(final boolean z, final int i) {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.m708$r8$lambda$F0GoNj1iOfaym_zj8P2vbeigc(KeyguardUpdateMonitor.this, z, i);
            }
        });
    }

    public void requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin, String str) {
        KeyguardBypassController keyguardBypassController;
        boolean z = true;
        if (!(isFaceEnrolled() && (keyguardBypassController = this.mKeyguardBypassController) != null && keyguardBypassController.canBypass())) {
            z = true;
            if (!this.mUdfpsBouncerShowing) {
                z = true;
                if (!this.mPrimaryBouncerFullyShown) {
                    z = this.mAuthController.isUdfpsFingerDown();
                }
            }
        }
        requestActiveUnlock(active_unlock_request_origin, str, z);
    }

    public final void requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin, String str, boolean z) {
        if (this.mHandler.hasMessages(336)) {
            return;
        }
        boolean shouldAllowActiveUnlockFromOrigin = this.mActiveUnlockConfig.shouldAllowActiveUnlockFromOrigin(active_unlock_request_origin);
        if (active_unlock_request_origin == ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.WAKE && !shouldAllowActiveUnlockFromOrigin && this.mActiveUnlockConfig.isActiveUnlockEnabled()) {
            initiateActiveUnlock(str);
        } else if (shouldAllowActiveUnlockFromOrigin && shouldTriggerActiveUnlock()) {
            this.mLogger.logUserRequestedUnlock(active_unlock_request_origin, str, z);
            this.mTrustManager.reportUserRequestedUnlock(getCurrentUser(), z);
        }
    }

    public void requestActiveUnlockDismissKeyguard(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin, String str) {
        requestActiveUnlock(active_unlock_request_origin, str + "-dismissKeyguard", true);
    }

    public boolean requestFaceAuth(String str) {
        this.mLogger.logFaceAuthRequested(str);
        updateFaceListeningState(0, FaceAuthReasonKt.apiRequestReasonToUiEvent(str));
        return isFaceDetectionRunning();
    }

    public void requestFaceAuthOnOccludingApp(boolean z) {
        this.mOccludingAppRequestingFace = z;
        updateFaceListeningState(z ? 2 : 1, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED);
    }

    public void requestFingerprintAuthOnOccludingApp(boolean z) {
        this.mOccludingAppRequestingFp = z;
        updateFingerprintListeningState(2);
    }

    @VisibleForTesting
    public void resetBiometricListeningState() {
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
    }

    public final boolean resolveNeedsSlowUnlockTransition() {
        if (isUserUnlocked(getCurrentUser())) {
            return false;
        }
        ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0, getCurrentUser());
        if (resolveActivityAsUser == null) {
            this.mLogger.w("resolveNeedsSlowUnlockTransition: returning false since activity could not be resolved.");
            return false;
        }
        return FALLBACK_HOME_COMPONENT.equals(resolveActivityAsUser.getComponentInfo().getComponentName());
    }

    public void sendKeyguardReset() {
        this.mHandler.obtainMessage(312).sendToTarget();
    }

    public void sendPrimaryBouncerChanged(boolean z, boolean z2) {
        this.mLogger.logSendPrimaryBouncerChanged(z, z2);
        Message obtainMessage = this.mHandler.obtainMessage(322);
        obtainMessage.arg1 = z ? 1 : 0;
        obtainMessage.arg2 = z2 ? 1 : 0;
        obtainMessage.sendToTarget();
    }

    public final void sendUpdates(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        keyguardUpdateMonitorCallback.onRefreshBatteryInfo(this.mBatteryStatus);
        keyguardUpdateMonitorCallback.onTimeChanged();
        keyguardUpdateMonitorCallback.onPhoneStateChanged(this.mPhoneState);
        keyguardUpdateMonitorCallback.onRefreshCarrierInfo();
        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(isKeyguardVisible());
        keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
        for (Map.Entry<Integer, SimData> entry : this.mSimDatas.entrySet()) {
            SimData value = entry.getValue();
            keyguardUpdateMonitorCallback.onSimStateChanged(value.subId, value.slotId, value.simState);
        }
    }

    @VisibleForTesting
    public void setAssistantVisible(boolean z) {
        this.mAssistantVisible = z;
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED);
        if (this.mAssistantVisible) {
            requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.ASSISTANT, "assistant", false);
        }
    }

    public void setCredentialAttempted() {
        this.mCredentialAttempted = true;
        updateFingerprintListeningState(2);
    }

    public final void setFaceRunningState(int i) {
        boolean z = false;
        boolean z2 = this.mFaceRunningState == 1;
        if (i == 1) {
            z = true;
        }
        this.mFaceRunningState = i;
        this.mLogger.logFaceRunningState(i);
        if (z2 != z) {
            notifyFaceRunningStateChanged();
        }
    }

    public final void setFingerprintRunningState(int i) {
        boolean z = false;
        boolean z2 = this.mFingerprintRunningState == 1;
        if (i == 1) {
            z = true;
        }
        this.mFingerprintRunningState = i;
        this.mLogger.logFingerprintRunningState(i);
        if (z2 != z) {
            notifyFingerprintRunningStateChanged();
        }
    }

    public void setKeyguardBypassController(KeyguardBypassController keyguardBypassController) {
        this.mKeyguardBypassController = keyguardBypassController;
    }

    public void setKeyguardGoingAway(boolean z) {
        this.mKeyguardGoingAway = z;
        updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY);
    }

    public void setKeyguardShowing(boolean z, boolean z2) {
        boolean z3 = true;
        boolean z4 = this.mKeyguardOccluded != z2;
        if (this.mKeyguardShowing == z) {
            z3 = false;
        }
        if (z4 || z3) {
            boolean isKeyguardVisible = isKeyguardVisible();
            this.mKeyguardShowing = z;
            this.mKeyguardOccluded = z2;
            this.mPrimaryBouncerFullyShown = false;
            boolean isKeyguardVisible2 = isKeyguardVisible();
            this.mLogger.logKeyguardShowingChanged(z, z2, isKeyguardVisible2);
            if (isKeyguardVisible2 != isKeyguardVisible) {
                int i = 0;
                if (isKeyguardVisible2) {
                    this.mSecureCameraLaunched = false;
                    i = 0;
                }
                while (i < this.mCallbacks.size()) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(isKeyguardVisible2);
                    }
                    i++;
                }
            }
            if (z4) {
                updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED);
            } else if (z3) {
                updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED);
            }
        }
    }

    @VisibleForTesting
    public void setStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        StrongAuthTracker strongAuthTracker2 = this.mStrongAuthTracker;
        if (strongAuthTracker2 != null) {
            this.mLockPatternUtils.unregisterStrongAuthTracker(strongAuthTracker2);
        }
        this.mStrongAuthTracker = strongAuthTracker;
        this.mLockPatternUtils.registerStrongAuthTracker(strongAuthTracker);
    }

    public void setSwitchingUser(boolean z) {
        this.mSwitchingUser = z;
        this.mHandler.post(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor.$r8$lambda$0z0G1S2d5hpCluaojbsT9WiZ1bo(KeyguardUpdateMonitor.this);
            }
        });
    }

    public void setUdfpsBouncerShowing(boolean z) {
        this.mUdfpsBouncerShowing = z;
        if (z) {
            updateFaceListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN);
            requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.UNLOCK_INTENT, "udfpsBouncer");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x002b, code lost:
        if (r3.dismissKeyguardRequested() != false) goto L13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x003b, code lost:
        if (r3.temporaryAndRenewable() != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0049, code lost:
        if (r3.dismissKeyguardRequested() != false) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldDismissKeyguardOnTrustGrantedWithCurrentUser(TrustGrantFlags trustGrantFlags) {
        boolean z;
        boolean z2 = this.mPrimaryBouncerIsOrWillBeShowing || this.mUdfpsBouncerShowing;
        if (!trustGrantFlags.isInitiatedByUser()) {
            z = false;
        }
        if (!this.mDeviceInteractive) {
            z = false;
        }
        if (!z2) {
            z = false;
        }
        z = true;
        return z;
    }

    public boolean shouldListenForFace() {
        if (this.mFaceManager == null) {
            return false;
        }
        boolean z = isKeyguardVisible() && this.mDeviceInteractive && !(this.mStatusBarState == 2);
        int currentUser = getCurrentUser();
        boolean isUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(BiometricSourceType.FACE);
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        boolean z2 = keyguardBypassController != null && keyguardBypassController.canBypass();
        boolean z3 = !getUserHasTrust(currentUser) || z2;
        boolean z4 = (this.mFaceSensorProperties.isEmpty() || !this.mFaceSensorProperties.get(0).supportsFaceDetection || !z2 || this.mPrimaryBouncerIsOrWillBeShowing || isUserInLockdown(currentUser)) ? false : true;
        boolean z5 = isUnlockingWithBiometricAllowed || z4;
        boolean z6 = !getUserUnlockedWithBiometric(currentUser);
        boolean isFaceDisabled = isFaceDisabled(currentUser);
        boolean z7 = this.mBiometricEnabledForUser.get(currentUser);
        boolean shouldListenForFaceAssistant = shouldListenForFaceAssistant();
        boolean isUdfpsFingerDown = this.mAuthController.isUdfpsFingerDown();
        boolean z8 = this.mPrimaryBouncerFullyShown;
        boolean z9 = (z8 || this.mAuthInterruptActive || this.mOccludingAppRequestingFace || z || shouldListenForFaceAssistant || isUdfpsFingerDown || this.mUdfpsBouncerShowing) && !this.mSwitchingUser && !isFaceDisabled && z3 && !this.mKeyguardGoingAway && z7 && z5 && this.mIsPrimaryUser && (!this.mSecureCameraLaunched || this.mOccludingAppRequestingFace) && z6 && !this.mGoingToSleep;
        if (z9 && this.mFaceUnlockBehavior == 1 && !z8) {
            z9 = false;
        }
        logListenerModelData(new KeyguardFaceListenModel(System.currentTimeMillis(), currentUser, z9, this.mAuthInterruptActive, z7, this.mPrimaryBouncerFullyShown, z6, isUnlockingWithBiometricAllowed, isFaceDisabled, isFaceLockedOut(), this.mGoingToSleep, z, this.mKeyguardGoingAway, shouldListenForFaceAssistant, this.mOccludingAppRequestingFace, this.mIsPrimaryUser, this.mSecureCameraLaunched, z4, this.mSwitchingUser, this.mUdfpsBouncerShowing, isUdfpsFingerDown, z3));
        return z9;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
        if (r0.mAuthenticated == false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldListenForFaceAssistant() {
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(getCurrentUser());
        boolean z = false;
        if (this.mAssistantVisible) {
            z = false;
            if (this.mKeyguardOccluded) {
                if (biometricAuthenticated != null) {
                    z = false;
                }
                z = false;
                if (!this.mUserHasTrust.get(getCurrentUser(), false)) {
                    z = true;
                }
            }
        }
        return z;
    }

    @VisibleForTesting
    public boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        int currentUser = getCurrentUser();
        boolean z3 = !getUserHasTrust(currentUser);
        boolean shouldListenForFingerprintAssistant = shouldListenForFingerprintAssistant();
        boolean z4 = isKeyguardVisible() || !this.mDeviceInteractive || (this.mPrimaryBouncerIsOrWillBeShowing && !this.mKeyguardGoingAway) || this.mGoingToSleep || shouldListenForFingerprintAssistant || (((z2 = this.mKeyguardOccluded) && this.mIsDreaming) || (z2 && z3 && (this.mOccludingAppRequestingFp || z)));
        boolean z5 = this.mBiometricEnabledForUser.get(currentUser);
        boolean userCanSkipBouncer = getUserCanSkipBouncer(currentUser);
        boolean isFingerprintDisabled = isFingerprintDisabled(currentUser);
        boolean z6 = (this.mSwitchingUser || isFingerprintDisabled || (this.mKeyguardGoingAway && this.mDeviceInteractive) || !this.mIsPrimaryUser || !z5 || isUserInLockdown(currentUser)) ? false : true;
        boolean z7 = !isUnlockingWithFingerprintAllowed();
        boolean z8 = isSfpsSupported() && isSfpsEnrolled();
        boolean z9 = (z7 && this.mPrimaryBouncerIsOrWillBeShowing) ? false : true;
        boolean z10 = (z && (userCanSkipBouncer || z7 || !z3)) ? false : true;
        boolean isDeviceInteractive = z8 ? this.mSfpsRequireScreenOnToAuthPrefEnabled ? isDeviceInteractive() : true : true;
        boolean z11 = z4 && z6 && z9 && z10 && isDeviceInteractive && !isFingerprintLockedOut();
        logListenerModelData(new KeyguardFingerprintListenModel(System.currentTimeMillis(), currentUser, z11, z5, this.mPrimaryBouncerIsOrWillBeShowing, userCanSkipBouncer, this.mCredentialAttempted, this.mDeviceInteractive, this.mIsDreaming, isFingerprintDisabled, this.mFingerprintLockedOut, this.mGoingToSleep, this.mKeyguardGoingAway, isKeyguardVisible(), this.mKeyguardOccluded, this.mOccludingAppRequestingFp, this.mIsPrimaryUser, isDeviceInteractive, shouldListenForFingerprintAssistant, z7, this.mSwitchingUser, z, z3));
        return z11;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
        if (r0.mAuthenticated == false) goto L9;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldListenForFingerprintAssistant() {
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(getCurrentUser());
        boolean z = false;
        if (this.mAssistantVisible) {
            z = false;
            if (this.mKeyguardOccluded) {
                if (biometricAuthenticated != null) {
                    z = false;
                }
                z = false;
                if (!this.mUserHasTrust.get(getCurrentUser(), false)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final boolean shouldTriggerActiveUnlock() {
        boolean shouldTriggerActiveUnlockForAssistant = shouldTriggerActiveUnlockForAssistant();
        boolean z = this.mPrimaryBouncerFullyShown || this.mUdfpsBouncerShowing || !(!isKeyguardVisible() || this.mGoingToSleep || this.mStatusBarState == 2);
        int currentUser = getCurrentUser();
        boolean z2 = getUserCanSkipBouncer(currentUser) || !this.mLockPatternUtils.isSecure(currentUser);
        boolean z3 = this.mFingerprintLockedOut || this.mFingerprintLockedOutPermanent;
        boolean z4 = !isUnlockingWithBiometricAllowed(true);
        boolean z5 = ((!this.mAuthInterruptActive && !shouldTriggerActiveUnlockForAssistant && !z) || this.mSwitchingUser || z2 || z3 || z4 || this.mKeyguardGoingAway || this.mSecureCameraLaunched) ? false : true;
        logListenerModelData(new KeyguardActiveUnlockModel(System.currentTimeMillis(), currentUser, z5, z, this.mAuthInterruptActive, z3, z4, this.mSwitchingUser, shouldTriggerActiveUnlockForAssistant, z2));
        return z5;
    }

    public final boolean shouldTriggerActiveUnlockForAssistant() {
        boolean z = false;
        if (this.mAssistantVisible) {
            z = false;
            if (this.mKeyguardOccluded) {
                z = false;
                if (!this.mUserHasTrust.get(getCurrentUser(), false)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public final void startListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
        int currentUser = getCurrentUser();
        boolean isUnlockWithFacePossible = isUnlockWithFacePossible(currentUser);
        if (this.mFaceCancelSignal != null) {
            this.mLogger.logUnexpectedFaceCancellationSignalState(this.mFaceRunningState, isUnlockWithFacePossible);
        }
        int i = this.mFaceRunningState;
        if (i == 2) {
            setFaceRunningState(3);
        } else if (i == 3) {
        } else {
            this.mLogger.logStartedListeningForFace(i, faceAuthUiEvent);
            this.mUiEventLogger.logWithInstanceIdAndPosition(faceAuthUiEvent, 0, (String) null, getKeyguardSessionId(), faceAuthUiEvent.getExtraInfo());
            if (isUnlockWithFacePossible) {
                this.mFaceCancelSignal = new CancellationSignal();
                boolean z = !this.mFaceSensorProperties.isEmpty() && this.mFaceSensorProperties.get(0).supportsFaceDetection;
                if (isUnlockingWithBiometricAllowed(BiometricSourceType.FACE) || !z) {
                    this.mLogger.v("startListeningForFace - authenticate");
                    KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
                    this.mFaceManager.authenticate((CryptoObject) null, this.mFaceCancelSignal, this.mFaceAuthenticationCallback, (Handler) null, currentUser, keyguardBypassController != null && keyguardBypassController.isBypassEnabled());
                } else {
                    this.mLogger.v("startListeningForFace - detect");
                    this.mFaceManager.detectFace(this.mFaceCancelSignal, this.mFaceDetectionCallback, currentUser);
                }
                setFaceRunningState(1);
            }
        }
    }

    public final void startListeningForFingerprint() {
        int currentUser = getCurrentUser();
        boolean isUnlockWithFingerprintPossible = isUnlockWithFingerprintPossible(currentUser);
        if (this.mFingerprintCancelSignal != null) {
            this.mLogger.logUnexpectedFpCancellationSignalState(this.mFingerprintRunningState, isUnlockWithFingerprintPossible);
        }
        int i = this.mFingerprintRunningState;
        if (i == 2) {
            setFingerprintRunningState(3);
        } else if (i != 3 && isUnlockWithFingerprintPossible) {
            this.mFingerprintCancelSignal = new CancellationSignal();
            if (isUnlockingWithFingerprintAllowed()) {
                this.mLogger.v("startListeningForFingerprint - authenticate");
                this.mFpm.authenticate(null, this.mFingerprintCancelSignal, this.mFingerprintAuthenticationCallback, null, -1, currentUser, 0);
            } else {
                this.mLogger.v("startListeningForFingerprint - detect");
                this.mFpm.detectFingerprint(this.mFingerprintCancelSignal, new FingerprintManager.FingerprintDetectionCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda12
                    public final void onFingerprintDetected(int i2, int i3, boolean z) {
                        KeyguardUpdateMonitor.$r8$lambda$bcukyu9zWt1P9dp3_MqNNap0KYw(KeyguardUpdateMonitor.this, i2, i3, z);
                    }
                }, currentUser);
            }
            setFingerprintRunningState(1);
        }
    }

    public final void stopListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
        this.mLogger.v("stopListeningForFace()");
        this.mLogger.logStoppedListeningForFace(this.mFaceRunningState, faceAuthUiEvent.getReason());
        this.mUiEventLogger.log(faceAuthUiEvent, getKeyguardSessionId());
        if (this.mFaceRunningState == 1) {
            CancellationSignal cancellationSignal = this.mFaceCancelSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mFaceCancelSignal = null;
                this.mHandler.removeCallbacks(this.mFaceCancelNotReceived);
                this.mHandler.postDelayed(this.mFaceCancelNotReceived, 3000L);
            }
            setFaceRunningState(2);
        }
        if (this.mFaceRunningState == 3) {
            setFaceRunningState(2);
        }
    }

    public final void stopListeningForFingerprint() {
        this.mLogger.v("stopListeningForFingerprint()");
        if (this.mFingerprintRunningState == 1) {
            CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mFingerprintCancelSignal = null;
                this.mHandler.removeCallbacks(this.mFpCancelNotReceived);
                this.mHandler.postDelayed(this.mFpCancelNotReceived, 3000L);
            }
            setFingerprintRunningState(2);
        }
        if (this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(2);
        }
    }

    public final void updateAirplaneModeState() {
        if (!WirelessUtils.isAirplaneModeOn(this.mContext) || this.mHandler.hasMessages(329)) {
            return;
        }
        this.mHandler.sendEmptyMessage(329);
    }

    public final void updateBiometricListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        updateFingerprintListeningState(i);
        updateFaceListeningState(i, faceAuthUiEvent);
    }

    public final void updateFaceEnrolled(final int i) {
        this.mIsFaceEnrolled = ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda11
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardUpdateMonitor.$r8$lambda$y0eEZ6Ck9QKjp6zFdce8p4Cq_XM(KeyguardUpdateMonitor.this, i);
            }
        })).booleanValue();
    }

    public final void updateFaceListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        if (this.mHandler.hasMessages(336)) {
            return;
        }
        this.mHandler.removeCallbacks(this.mRetryFaceAuthentication);
        boolean shouldListenForFace = shouldListenForFace();
        int i2 = this.mFaceRunningState;
        if (i2 == 1 && !shouldListenForFace) {
            if (i == 0) {
                this.mLogger.v("Ignoring stopListeningForFace()");
            } else {
                stopListeningForFace(faceAuthUiEvent);
            }
        } else if (i2 == 1 || !shouldListenForFace) {
        } else {
            if (i == 1) {
                this.mLogger.v("Ignoring startListeningForFace()");
            } else {
                startListeningForFace(faceAuthUiEvent);
            }
        }
    }

    public void updateFaceListeningStateForBehavior(boolean z) {
        if (this.mPrimaryBouncerFullyShown != z) {
            this.mPrimaryBouncerFullyShown = z;
            if (this.mFaceUnlockBehavior == 1) {
                updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED);
            }
        }
    }

    public final void updateFaceUnlockBehavior() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mFaceAuthOnlyOnSecurityView) {
            this.mFaceUnlockBehavior = 1;
        } else {
            this.mFaceUnlockBehavior = Settings.Secure.getIntForUser(contentResolver, "face_unlock_method", 0, -2);
        }
    }

    public final void updateFingerprintListeningState(int i) {
        if (!this.mHandler.hasMessages(336) && this.mAuthController.areAllFingerprintAuthenticatorsRegistered()) {
            boolean shouldListenForFingerprint = shouldListenForFingerprint(isUdfpsSupported());
            int i2 = this.mFingerprintRunningState;
            boolean z = i2 == 1 || i2 == 3;
            if (z && !shouldListenForFingerprint) {
                if (i == 0) {
                    this.mLogger.v("Ignoring stopListeningForFingerprint()");
                } else {
                    stopListeningForFingerprint();
                }
            } else if (z || !shouldListenForFingerprint) {
            } else {
                if (i == 1) {
                    this.mLogger.v("Ignoring startListeningForFingerprint()");
                } else {
                    startListeningForFingerprint();
                }
            }
        }
    }

    public final void updateLogoutEnabled() {
        Assert.isMainThread();
        boolean isLogoutEnabled = this.mDevicePolicyManager.isLogoutEnabled();
        if (this.mLogoutEnabled != isLogoutEnabled) {
            this.mLogoutEnabled = isLogoutEnabled;
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onLogoutEnabledChanged();
                }
            }
        }
    }

    public final void updateSecondaryLockscreenRequirement(int i) {
        Intent intent = this.mSecondaryLockscreenRequirement.get(Integer.valueOf(i));
        boolean isSecondaryLockscreenEnabled = this.mDevicePolicyManager.isSecondaryLockscreenEnabled(UserHandle.of(i));
        boolean z = true;
        if (isSecondaryLockscreenEnabled && intent == null) {
            ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle.of(i));
            if (profileOwnerOrDeviceOwnerSupervisionComponent == null) {
                this.mLogger.logMissingSupervisorAppError(i);
            } else {
                ResolveInfo resolveService = this.mPackageManager.resolveService(new Intent("android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE").setPackage(profileOwnerOrDeviceOwnerSupervisionComponent.getPackageName()), 0);
                if (resolveService != null && resolveService.serviceInfo != null) {
                    this.mSecondaryLockscreenRequirement.put(Integer.valueOf(i), new Intent().setComponent(resolveService.serviceInfo.getComponentName()));
                }
            }
            z = false;
        } else {
            if (!isSecondaryLockscreenEnabled && intent != null) {
                this.mSecondaryLockscreenRequirement.put(Integer.valueOf(i), null);
            }
            z = false;
        }
        if (z) {
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i2).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onSecondaryLockscreenRequirementChanged(i);
                }
            }
        }
    }

    public final void updateSettings() {
        updateFaceUnlockBehavior();
    }

    public void updateSfpsRequireScreenOnToAuthPref() {
        this.mSfpsRequireScreenOnToAuthPrefEnabled = this.mSecureSettings.getIntForUser("sfps_require_screen_on_to_auth_enabled", this.mContext.getResources().getBoolean(17891764) ? 1 : 0, getCurrentUser()) != 0;
    }

    @VisibleForTesting
    public void updateTelephonyCapable(boolean z) {
        Assert.isMainThread();
        if (z == this.mTelephonyCapable) {
            return;
        }
        this.mTelephonyCapable = z;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = this.mCallbacks.get(i).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
            }
        }
    }

    public final void watchForDeviceProvisioning() {
        this.mDeviceProvisionedObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.18
            {
                KeyguardUpdateMonitor.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mDeviceProvisioned = keyguardUpdateMonitor.isDeviceProvisionedInSettingsDb();
                if (KeyguardUpdateMonitor.this.mDeviceProvisioned) {
                    KeyguardUpdateMonitor.this.mHandler.sendEmptyMessage(308);
                }
                KeyguardUpdateMonitor.this.mLogger.logDeviceProvisionedState(KeyguardUpdateMonitor.this.mDeviceProvisioned);
            }
        };
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
        boolean isDeviceProvisionedInSettingsDb = isDeviceProvisionedInSettingsDb();
        if (isDeviceProvisionedInSettingsDb != this.mDeviceProvisioned) {
            this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb;
            if (isDeviceProvisionedInSettingsDb) {
                this.mHandler.sendEmptyMessage(308);
            }
        }
    }
}