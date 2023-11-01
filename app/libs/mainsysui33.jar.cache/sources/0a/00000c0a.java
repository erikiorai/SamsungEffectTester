package com.android.keyguard;

import android.app.admin.DevicePolicyManager;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.metrics.LogMaker;
import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.DejankUtils;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$bool;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.biometrics.SideFpsUiRequestSource;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.settings.GlobalSettings;
import java.util.Optional;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainerController.class */
public class KeyguardSecurityContainerController extends ViewController<KeyguardSecurityContainer> implements KeyguardSecurityView {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final AdminSecondaryLockScreenController mAdminSecondaryLockScreenController;
    public boolean mBouncerVisible;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public KeyguardSecurityModel.SecurityMode mCurrentSecurityMode;
    public final FalsingA11yDelegate mFalsingA11yDelegate;
    public final FalsingCollector mFalsingCollector;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final GlobalSettings mGlobalSettings;
    @VisibleForTesting
    public final Gefingerpoken mGlobalTouchListener;
    public KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public int mLastOrientation;
    public final LockPatternUtils mLockPatternUtils;
    public final MetricsLogger mMetricsLogger;
    public final KeyguardSecurityContainer.SecurityCallback mSecurityCallback;
    public final KeyguardSecurityModel mSecurityModel;
    public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
    public final SessionTracker mSessionTracker;
    public final Optional<SideFpsController> mSideFpsController;
    public final KeyguardSecurityContainer.SwipeListener mSwipeListener;
    public final UiEventLogger mUiEventLogger;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public UserSwitcherController.UserSwitchCallback mUserSwitchCallback;
    public final UserSwitcherController mUserSwitcherController;

    /* renamed from: com.android.keyguard.KeyguardSecurityContainerController$2 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainerController$2.class */
    public class AnonymousClass2 implements KeyguardSecurityCallback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$SZW6wiWaYNwiexvNOmieqp7iz6U() {
            lambda$reportUnlockAttempt$0();
        }

        public AnonymousClass2() {
            KeyguardSecurityContainerController.this = r4;
        }

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0010 -> B:13:0x0006). Please submit an issue!!! */
        public static /* synthetic */ void lambda$reportUnlockAttempt$0() {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
            }
            System.gc();
            System.runFinalization();
            System.gc();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void dismiss(boolean z, int i, KeyguardSecurityModel.SecurityMode securityMode) {
            dismiss(z, i, false, securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
            KeyguardSecurityContainerController.this.mSecurityCallback.dismiss(z, i, z2, securityMode);
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void onCancelClicked() {
            KeyguardSecurityContainerController.this.mSecurityCallback.onCancelClicked();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void onUserInput() {
            KeyguardSecurityContainerController.this.mUpdateMonitor.cancelFaceAuth();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void reportUnlockAttempt(int i, boolean z, int i2) {
            int i3 = ((KeyguardSecurityContainer) ((ViewController) KeyguardSecurityContainerController.this).mView).isSidedSecurityMode() ? ((KeyguardSecurityContainer) ((ViewController) KeyguardSecurityContainerController.this).mView).isSecurityLeftAligned() ? 1 : 2 : 0;
            if (z) {
                SysUiStatsLog.write(64, 2, i3);
                KeyguardSecurityContainerController.this.mLockPatternUtils.reportSuccessfulPasswordAttempt(i);
                ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.keyguard.KeyguardSecurityContainerController$2$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecurityContainerController.AnonymousClass2.$r8$lambda$SZW6wiWaYNwiexvNOmieqp7iz6U();
                    }
                });
            } else {
                SysUiStatsLog.write(64, 1, i3);
                KeyguardSecurityContainerController.this.reportFailedUnlockAttempt(i, i2);
            }
            KeyguardSecurityContainerController.this.mMetricsLogger.write(new LogMaker(197).setType(z ? 10 : 11));
            KeyguardSecurityContainerController.this.mUiEventLogger.log(z ? KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_SUCCESS : KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_PASSWORD_FAILURE, KeyguardSecurityContainerController.this.getSessionId());
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void reset() {
            KeyguardSecurityContainerController.this.mSecurityCallback.reset();
        }

        @Override // com.android.keyguard.KeyguardSecurityCallback
        public void userActivity() {
            if (KeyguardSecurityContainerController.this.mSecurityCallback != null) {
                KeyguardSecurityContainerController.this.mSecurityCallback.userActivity();
            }
        }
    }

    /* renamed from: com.android.keyguard.KeyguardSecurityContainerController$6 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainerController$6.class */
    public static /* synthetic */ class AnonymousClass6 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x0041 -> B:61:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0045 -> B:59:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0049 -> B:57:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x004d -> B:63:0x0035). Please submit an issue!!! */
        static {
            int[] iArr = new int[KeyguardSecurityModel.SecurityMode.values().length];
            $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode = iArr;
            try {
                iArr[KeyguardSecurityModel.SecurityMode.Pattern.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.Password.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.PIN.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPin.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[KeyguardSecurityModel.SecurityMode.SimPuk.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityContainerController$Factory.class */
    public static class Factory {
        public final AdminSecondaryLockScreenController.Factory mAdminSecondaryLockScreenControllerFactory;
        public final ConfigurationController mConfigurationController;
        public final FalsingA11yDelegate mFalsingA11yDelegate;
        public final FalsingCollector mFalsingCollector;
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public final GlobalSettings mGlobalSettings;
        public final KeyguardSecurityModel mKeyguardSecurityModel;
        public final KeyguardStateController mKeyguardStateController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LockPatternUtils mLockPatternUtils;
        public final MetricsLogger mMetricsLogger;
        public final KeyguardSecurityViewFlipperController mSecurityViewFlipperController;
        public final SessionTracker mSessionTracker;
        public final Optional<SideFpsController> mSidefpsController;
        public final UiEventLogger mUiEventLogger;
        public final UserSwitcherController mUserSwitcherController;
        public final KeyguardSecurityContainer mView;

        public Factory(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional<SideFpsController> optional, FalsingA11yDelegate falsingA11yDelegate) {
            this.mView = keyguardSecurityContainer;
            this.mAdminSecondaryLockScreenControllerFactory = factory;
            this.mLockPatternUtils = lockPatternUtils;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mKeyguardSecurityModel = keyguardSecurityModel;
            this.mMetricsLogger = metricsLogger;
            this.mUiEventLogger = uiEventLogger;
            this.mKeyguardStateController = keyguardStateController;
            this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
            this.mConfigurationController = configurationController;
            this.mFalsingCollector = falsingCollector;
            this.mFalsingManager = falsingManager;
            this.mFeatureFlags = featureFlags;
            this.mGlobalSettings = globalSettings;
            this.mUserSwitcherController = userSwitcherController;
            this.mSessionTracker = sessionTracker;
            this.mSidefpsController = optional;
            this.mFalsingA11yDelegate = falsingA11yDelegate;
        }

        public KeyguardSecurityContainerController create(KeyguardSecurityContainer.SecurityCallback securityCallback) {
            return new KeyguardSecurityContainerController(this.mView, this.mAdminSecondaryLockScreenControllerFactory, this.mLockPatternUtils, this.mKeyguardUpdateMonitor, this.mKeyguardSecurityModel, this.mMetricsLogger, this.mUiEventLogger, this.mKeyguardStateController, securityCallback, this.mSecurityViewFlipperController, this.mConfigurationController, this.mFalsingCollector, this.mFalsingManager, this.mUserSwitcherController, this.mFeatureFlags, this.mGlobalSettings, this.mSessionTracker, this.mSidefpsController, this.mFalsingA11yDelegate, null);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda2.onUserSwitched():void] */
    /* renamed from: $r8$lambda$FSwz5ahVMZY6Q-nMyRLqSO5Mro0 */
    public static /* synthetic */ void m639$r8$lambda$FSwz5ahVMZY6QnMyRLqSO5Mro0(KeyguardSecurityContainerController keyguardSecurityContainerController) {
        keyguardSecurityContainerController.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda1.get():java.lang.Object] */
    public static /* synthetic */ KeyguardSecurityModel.SecurityMode $r8$lambda$o8U_yX2YqPk4jucJscETY_NArbc(KeyguardSecurityContainerController keyguardSecurityContainerController) {
        return keyguardSecurityContainerController.lambda$showPrimarySecurityScreen$1();
    }

    public KeyguardSecurityContainerController(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityContainer.SecurityCallback securityCallback, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional<SideFpsController> optional, FalsingA11yDelegate falsingA11yDelegate) {
        super(keyguardSecurityContainer);
        this.mLastOrientation = 0;
        this.mCurrentSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mUserSwitchCallback = new UserSwitcherController.UserSwitchCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda2
            public final void onUserSwitched() {
                KeyguardSecurityContainerController.m639$r8$lambda$FSwz5ahVMZY6QnMyRLqSO5Mro0(KeyguardSecurityContainerController.this);
            }
        };
        this.mGlobalTouchListener = new Gefingerpoken() { // from class: com.android.keyguard.KeyguardSecurityContainerController.1
            public MotionEvent mTouchDown;

            {
                KeyguardSecurityContainerController.this = this;
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return false;
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 0) {
                    if (((KeyguardSecurityContainer) ((ViewController) KeyguardSecurityContainerController.this).mView).isTouchOnTheOtherSideOfSecurity(motionEvent)) {
                        KeyguardSecurityContainerController.this.mFalsingCollector.avoidGesture();
                    }
                    MotionEvent motionEvent2 = this.mTouchDown;
                    if (motionEvent2 != null) {
                        motionEvent2.recycle();
                        this.mTouchDown = null;
                    }
                    this.mTouchDown = MotionEvent.obtain(motionEvent);
                    return false;
                } else if (this.mTouchDown != null) {
                    if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
                        this.mTouchDown.recycle();
                        this.mTouchDown = null;
                        return false;
                    }
                    return false;
                } else {
                    return false;
                }
            }
        };
        this.mKeyguardSecurityCallback = new AnonymousClass2();
        this.mSwipeListener = new KeyguardSecurityContainer.SwipeListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.3
            {
                KeyguardSecurityContainerController.this = this;
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SwipeListener
            public void onSwipeUp() {
                if (!KeyguardSecurityContainerController.this.mUpdateMonitor.isFaceDetectionRunning()) {
                    boolean requestFaceAuth = KeyguardSecurityContainerController.this.mUpdateMonitor.requestFaceAuth("Face auth due to swipe up on bouncer");
                    KeyguardSecurityContainerController.this.mKeyguardSecurityCallback.userActivity();
                    if (requestFaceAuth) {
                        KeyguardSecurityContainerController.this.showMessage(null, null);
                    }
                }
                if (KeyguardSecurityContainerController.this.mUpdateMonitor.isFaceEnrolled()) {
                    KeyguardSecurityContainerController.this.mUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN.UNLOCK_INTENT, "swipeUpOnBouncer");
                }
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecurityContainerController.4
            {
                KeyguardSecurityContainerController.this = this;
            }

            public void onDensityOrFontScaleChanged() {
                KeyguardSecurityContainerController.this.onDensityOrFontScaleChanged();
            }

            public void onThemeChanged() {
                KeyguardSecurityContainerController.this.reloadColors();
            }

            public void onUiModeChanged() {
                KeyguardSecurityContainerController.this.reloadColors();
            }
        };
        this.mBouncerVisible = false;
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController.5
            {
                KeyguardSecurityContainerController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardSecurityContainerController.this.updateSideFpsVisibility();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onDevicePolicyManagerStateChanged() {
                KeyguardSecurityContainerController.this.showPrimarySecurityScreen(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStrongAuthStateChanged(int i) {
                KeyguardSecurityContainerController.this.updateSideFpsVisibility();
            }
        };
        this.mLockPatternUtils = lockPatternUtils;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mSecurityModel = keyguardSecurityModel;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mKeyguardStateController = keyguardStateController;
        this.mSecurityCallback = securityCallback;
        this.mSecurityViewFlipperController = keyguardSecurityViewFlipperController;
        this.mAdminSecondaryLockScreenController = factory.create(this.mKeyguardSecurityCallback);
        this.mConfigurationController = configurationController;
        this.mLastOrientation = getResources().getConfiguration().orientation;
        this.mFalsingCollector = falsingCollector;
        this.mFalsingManager = falsingManager;
        this.mUserSwitcherController = userSwitcherController;
        this.mFeatureFlags = featureFlags;
        this.mGlobalSettings = globalSettings;
        this.mSessionTracker = sessionTracker;
        this.mSideFpsController = optional;
        this.mFalsingA11yDelegate = falsingA11yDelegate;
    }

    public /* synthetic */ KeyguardSecurityContainerController(KeyguardSecurityContainer keyguardSecurityContainer, AdminSecondaryLockScreenController.Factory factory, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel keyguardSecurityModel, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, KeyguardStateController keyguardStateController, KeyguardSecurityContainer.SecurityCallback securityCallback, KeyguardSecurityViewFlipperController keyguardSecurityViewFlipperController, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, UserSwitcherController userSwitcherController, FeatureFlags featureFlags, GlobalSettings globalSettings, SessionTracker sessionTracker, Optional optional, FalsingA11yDelegate falsingA11yDelegate, KeyguardSecurityContainerController-IA r41) {
        this(keyguardSecurityContainer, factory, lockPatternUtils, keyguardUpdateMonitor, keyguardSecurityModel, metricsLogger, uiEventLogger, keyguardStateController, securityCallback, keyguardSecurityViewFlipperController, configurationController, falsingCollector, falsingManager, userSwitcherController, featureFlags, globalSettings, sessionTracker, optional, falsingA11yDelegate);
    }

    public /* synthetic */ void lambda$configureMode$2() {
        showMessage(getContext().getString(R$string.keyguard_unlock_to_continue), null);
    }

    public /* synthetic */ void lambda$new$0() {
        showPrimarySecurityScreen(false);
    }

    public /* synthetic */ KeyguardSecurityModel.SecurityMode lambda$showPrimarySecurityScreen$1() {
        return this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser());
    }

    public final boolean canDisplayUserSwitcher() {
        return this.mFeatureFlags.isEnabled(Flags.BOUNCER_USER_SWITCHER);
    }

    public final boolean canUseOneHandedBouncer() {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        if (securityMode == KeyguardSecurityModel.SecurityMode.Pattern || securityMode == KeyguardSecurityModel.SecurityMode.PIN) {
            return getResources().getBoolean(R$bool.can_use_one_handed_bouncer);
        }
        return false;
    }

    public final KeyguardInputViewController<KeyguardInputView> changeSecurityMode(KeyguardSecurityModel.SecurityMode securityMode) {
        this.mCurrentSecurityMode = securityMode;
        return getCurrentSecurityController();
    }

    public final void configureMode() {
        int i;
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        boolean z = securityMode == KeyguardSecurityModel.SecurityMode.SimPin || securityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
        if (!canDisplayUserSwitcher() || z) {
            i = 0;
            if (canUseOneHandedBouncer()) {
                i = 1;
            }
        } else {
            i = 2;
        }
        ((KeyguardSecurityContainer) ((ViewController) this).mView).initMode(i, this.mGlobalSettings, this.mFalsingManager, this.mUserSwitcherController, new KeyguardSecurityContainer.UserSwitcherViewMode.UserSwitcherCallback() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda0
            @Override // com.android.keyguard.KeyguardSecurityContainer.UserSwitcherViewMode.UserSwitcherCallback
            public final void showUnlockToContinueMessage() {
                KeyguardSecurityContainerController.this.lambda$configureMode$2();
            }
        }, this.mFalsingA11yDelegate);
    }

    public final KeyguardInputViewController<KeyguardInputView> getCurrentSecurityController() {
        return this.mSecurityViewFlipperController.getSecurityView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback);
    }

    public KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.mCurrentSecurityMode;
    }

    public final InstanceId getSessionId() {
        return this.mSessionTracker.getSessionId(1);
    }

    public CharSequence getTitle() {
        return ((KeyguardSecurityContainer) ((ViewController) this).mView).getTitle();
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public boolean needsInput() {
        return getCurrentSecurityController().needsInput();
    }

    public void onBouncerVisibilityChanged(int i) {
        setBouncerVisible(i == 0);
    }

    public final void onDensityOrFontScaleChanged() {
        resetViewFlipper();
        ((KeyguardSecurityContainer) ((ViewController) this).mView).onDensityOrFontScaleChanged();
    }

    public void onInit() {
        this.mSecurityViewFlipperController.init();
        configureMode();
    }

    public void onPause() {
        this.mAdminSecondaryLockScreenController.hide();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController().onPause();
        }
        ((KeyguardSecurityContainer) ((ViewController) this).mView).onPause();
        setBouncerVisible(false);
    }

    public void onResume(int i) {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            int i2 = 2;
            if (((KeyguardSecurityContainer) ((ViewController) this).mView).isSidedSecurityMode()) {
                i2 = ((KeyguardSecurityContainer) ((ViewController) this).mView).isSecurityLeftAligned() ? 3 : 4;
            }
            SysUiStatsLog.write(63, i2);
            getCurrentSecurityController().onResume(i);
            updateSideFpsVisibility();
        }
        ((KeyguardSecurityContainer) ((ViewController) this).mView).onResume(this.mSecurityModel.getSecurityMode(KeyguardUpdateMonitor.getCurrentUser()), this.mKeyguardStateController.isFaceAuthEnabled());
    }

    @Override // com.android.keyguard.KeyguardSecurityView
    public void onStartingToHide() {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController().onStartingToHide();
        }
        setBouncerVisible(false);
    }

    public void onViewAttached() {
        this.mUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KeyguardSecurityContainer) ((ViewController) this).mView).setSwipeListener(this.mSwipeListener);
        ((KeyguardSecurityContainer) ((ViewController) this).mView).addMotionEventListener(this.mGlobalTouchListener);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mUserSwitcherController.addUserSwitchCallback(this.mUserSwitchCallback);
    }

    public void onViewDetached() {
        this.mUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        ((KeyguardSecurityContainer) ((ViewController) this).mView).removeMotionEventListener(this.mGlobalTouchListener);
        this.mUserSwitcherController.removeUserSwitchCallback(this.mUserSwitchCallback);
    }

    public final void reloadColors() {
        resetViewFlipper();
        ((KeyguardSecurityContainer) ((ViewController) this).mView).reloadColors();
    }

    public void reportFailedUnlockAttempt(int i, int i2) {
        int i3 = 1;
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(i) + 1;
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "reportFailedPatternAttempt: #" + currentFailedPasswordAttempts);
        }
        DevicePolicyManager devicePolicyManager = this.mLockPatternUtils.getDevicePolicyManager();
        int maximumFailedPasswordsForWipe = devicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        int i4 = maximumFailedPasswordsForWipe > 0 ? maximumFailedPasswordsForWipe - currentFailedPasswordAttempts : Integer.MAX_VALUE;
        if (i4 < 5) {
            int profileWithMinimumFailedPasswordsForWipe = devicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(i);
            if (profileWithMinimumFailedPasswordsForWipe == i) {
                if (profileWithMinimumFailedPasswordsForWipe != 0) {
                    i3 = 3;
                }
            } else if (profileWithMinimumFailedPasswordsForWipe != -10000) {
                i3 = 2;
            }
            if (i4 > 0) {
                ((KeyguardSecurityContainer) ((ViewController) this).mView).showAlmostAtWipeDialog(currentFailedPasswordAttempts, i4, i3);
            } else {
                Slog.i("KeyguardSecurityView", "Too many unlock attempts; user " + profileWithMinimumFailedPasswordsForWipe + " will be wiped!");
                ((KeyguardSecurityContainer) ((ViewController) this).mView).showWipeDialog(currentFailedPasswordAttempts, i3);
            }
        }
        this.mLockPatternUtils.reportFailedPasswordAttempt(i);
        if (i2 > 0) {
            this.mLockPatternUtils.reportPasswordLockout(i2, i);
            ((KeyguardSecurityContainer) ((ViewController) this).mView).showTimeoutDialog(i, i2, this.mLockPatternUtils, this.mSecurityModel.getSecurityMode(i));
        }
    }

    public void reset() {
        ((KeyguardSecurityContainer) ((ViewController) this).mView).reset();
        this.mSecurityViewFlipperController.reset();
    }

    public final void resetViewFlipper() {
        this.mSecurityViewFlipperController.clearViews();
        this.mSecurityViewFlipperController.getSecurityView(this.mCurrentSecurityMode, this.mKeyguardSecurityCallback);
    }

    public final void setBouncerVisible(boolean z) {
        this.mBouncerVisible = z;
        updateSideFpsVisibility();
    }

    public void showMessage(CharSequence charSequence, ColorStateList colorStateList) {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            getCurrentSecurityController().showMessage(charSequence, colorStateList);
        }
    }

    public boolean showNextSecurityScreenOrFinish(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent;
        boolean z3;
        Intent secondaryLockscreenRequirement;
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showNextSecurityScreenOrFinish(" + z + ")");
        }
        if (securityMode != KeyguardSecurityModel.SecurityMode.Invalid && securityMode != getCurrentSecurityMode()) {
            Log.w("KeyguardSecurityView", "Attempted to invoke showNextSecurityScreenOrFinish with securityMode " + securityMode + ", but current mode is " + getCurrentSecurityMode());
            return false;
        }
        KeyguardSecurityContainer.BouncerUiEvent bouncerUiEvent2 = KeyguardSecurityContainer.BouncerUiEvent.UNKNOWN;
        int i2 = 4;
        boolean z4 = true;
        if (this.mUpdateMonitor.getUserHasTrust(i)) {
            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_EXTENDED_ACCESS;
            z3 = false;
            i2 = 3;
        } else if (this.mUpdateMonitor.getUserUnlockedWithBiometric(i)) {
            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_BIOMETRIC;
            z3 = false;
            i2 = 2;
        } else {
            KeyguardSecurityModel.SecurityMode securityMode2 = KeyguardSecurityModel.SecurityMode.None;
            if (securityMode2 == getCurrentSecurityMode()) {
                KeyguardSecurityModel.SecurityMode securityMode3 = this.mSecurityModel.getSecurityMode(i);
                if (securityMode2 == securityMode3) {
                    bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_NONE_SECURITY;
                    i2 = 0;
                    z4 = true;
                } else {
                    showSecurityScreen(securityMode3);
                    bouncerUiEvent = bouncerUiEvent2;
                    z4 = false;
                    i2 = -1;
                }
            } else {
                if (z) {
                    int i3 = AnonymousClass6.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[getCurrentSecurityMode().ordinal()];
                    if (i3 == 1 || i3 == 2 || i3 == 3) {
                        bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_PASSWORD;
                        z3 = true;
                        i2 = 1;
                    } else if (i3 == 4 || i3 == 5) {
                        KeyguardSecurityModel.SecurityMode securityMode4 = this.mSecurityModel.getSecurityMode(i);
                        if (securityMode4 == securityMode2 && this.mLockPatternUtils.isLockScreenDisabled(KeyguardUpdateMonitor.getCurrentUser())) {
                            bouncerUiEvent = KeyguardSecurityContainer.BouncerUiEvent.BOUNCER_DISMISS_SIM;
                            z4 = true;
                        } else {
                            showSecurityScreen(securityMode4);
                        }
                    } else {
                        Log.v("KeyguardSecurityView", "Bad security screen " + getCurrentSecurityMode() + ", fail safe");
                        showPrimarySecurityScreen(false);
                    }
                }
                bouncerUiEvent = bouncerUiEvent2;
                z3 = false;
                z4 = false;
                i2 = -1;
            }
            z3 = false;
        }
        if (z4 && !z2 && (secondaryLockscreenRequirement = this.mUpdateMonitor.getSecondaryLockscreenRequirement(i)) != null) {
            this.mAdminSecondaryLockScreenController.show(secondaryLockscreenRequirement);
            return false;
        }
        if (i2 != -1) {
            this.mMetricsLogger.write(new LogMaker(197).setType(5).setSubtype(i2));
        }
        if (bouncerUiEvent != bouncerUiEvent2) {
            this.mUiEventLogger.log(bouncerUiEvent, getSessionId());
        }
        if (z4) {
            this.mSecurityCallback.finish(z3, i);
        }
        return z4;
    }

    public void showPrimarySecurityScreen(boolean z) {
        KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardSecurityContainerController$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardSecurityContainerController.$r8$lambda$o8U_yX2YqPk4jucJscETY_NArbc(KeyguardSecurityContainerController.this);
            }
        });
        if (DEBUG) {
            Log.v("KeyguardSecurityView", "showPrimarySecurityScreen(turningOff=" + z + ")");
        }
        showSecurityScreen(securityMode);
    }

    public void showPromptReason(int i) {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            if (i != 0) {
                Log.i("KeyguardSecurityView", "Strong auth required, reason: " + i);
            }
            getCurrentSecurityController().showPromptReason(i);
        }
    }

    @VisibleForTesting
    public void showSecurityScreen(KeyguardSecurityModel.SecurityMode securityMode) {
        if (DEBUG) {
            Log.d("KeyguardSecurityView", "showSecurityScreen(" + securityMode + ")");
        }
        if (securityMode == KeyguardSecurityModel.SecurityMode.Invalid || securityMode == this.mCurrentSecurityMode) {
            return;
        }
        KeyguardInputViewController<KeyguardInputView> currentSecurityController = getCurrentSecurityController();
        if (currentSecurityController != null) {
            currentSecurityController.onPause();
        }
        KeyguardInputViewController<KeyguardInputView> changeSecurityMode = changeSecurityMode(securityMode);
        if (changeSecurityMode != null) {
            changeSecurityMode.onResume(2);
            this.mSecurityViewFlipperController.show(changeSecurityMode);
            configureMode();
        }
        this.mSecurityCallback.onSecurityModeChanged(securityMode, changeSecurityMode != null && changeSecurityMode.needsInput());
    }

    public void startAppearAnimation() {
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.None) {
            ((KeyguardSecurityContainer) ((ViewController) this).mView).setAlpha(1.0f);
            ((KeyguardSecurityContainer) ((ViewController) this).mView).startAppearAnimation(this.mCurrentSecurityMode);
            getCurrentSecurityController().startAppearAnimation();
        }
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        if (securityMode != KeyguardSecurityModel.SecurityMode.None) {
            ((KeyguardSecurityContainer) ((ViewController) this).mView).startDisappearAnimation(securityMode);
            return getCurrentSecurityController().startDisappearAnimation(runnable);
        }
        return false;
    }

    public void updateKeyguardPosition(float f) {
        ((KeyguardSecurityContainer) ((ViewController) this).mView).updatePositionByTouchX(f);
    }

    public void updateResources() {
        int i = getResources().getConfiguration().orientation;
        if (i != this.mLastOrientation) {
            this.mLastOrientation = i;
            configureMode();
        }
    }

    public final void updateSideFpsVisibility() {
        if (this.mSideFpsController.isPresent()) {
            boolean z = getResources().getBoolean(R$bool.config_show_sidefps_hint_on_bouncer);
            boolean isFingerprintDetectionRunning = this.mUpdateMonitor.isFingerprintDetectionRunning();
            boolean isUnlockingWithFingerprintAllowed = this.mUpdateMonitor.isUnlockingWithFingerprintAllowed();
            boolean z2 = this.mBouncerVisible && z && isFingerprintDetectionRunning && isUnlockingWithFingerprintAllowed;
            if (DEBUG) {
                Log.d("KeyguardSecurityView", "sideFpsToShow=" + z2 + ", mBouncerVisible=" + this.mBouncerVisible + ", configEnabled=" + z + ", fpsDetectionRunning=" + isFingerprintDetectionRunning + ", isUnlockingWithFpAllowed=" + isUnlockingWithFingerprintAllowed);
            }
            if (z2) {
                this.mSideFpsController.get().show(SideFpsUiRequestSource.PRIMARY_BOUNCER);
            } else {
                this.mSideFpsController.get().hide(SideFpsUiRequestSource.PRIMARY_BOUNCER);
            }
        }
    }
}