package com.android.keyguard;

import android.app.ActivityManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.media.AudioManager;
import android.os.SystemClock;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.MathUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$bool;
import com.android.systemui.R$dimen;
import com.android.systemui.R$integer;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.util.ViewController;
import java.io.File;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardHostViewController.class */
public class KeyguardHostViewController extends ViewController<KeyguardHostView> {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public final AudioManager mAudioManager;
    public Runnable mCancelAction;
    public ActivityStarter.OnDismissAction mDismissAction;
    public final KeyguardSecurityContainerController mKeyguardSecurityContainerController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public View.OnKeyListener mOnKeyListener;
    public final KeyguardSecurityContainer.SecurityCallback mSecurityCallback;
    public final TelephonyManager mTelephonyManager;
    public int mTranslationY;
    public final KeyguardUpdateMonitorCallback mUpdateCallback;
    public final ViewMediatorCallback mViewMediatorCallback;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardHostViewController$$ExternalSyntheticLambda0.onKey(android.view.View, int, android.view.KeyEvent):boolean] */
    /* renamed from: $r8$lambda$JrjRYhNBUiyIPkhGa_AFPX2_-a8 */
    public static /* synthetic */ boolean m595$r8$lambda$JrjRYhNBUiyIPkhGa_AFPX2_a8(KeyguardHostViewController keyguardHostViewController, View view, int i, KeyEvent keyEvent) {
        return keyguardHostViewController.lambda$new$0(view, i, keyEvent);
    }

    public KeyguardHostViewController(KeyguardHostView keyguardHostView, KeyguardUpdateMonitor keyguardUpdateMonitor, AudioManager audioManager, TelephonyManager telephonyManager, ViewMediatorCallback viewMediatorCallback, KeyguardSecurityContainerController.Factory factory) {
        super(keyguardHostView);
        this.mUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardHostViewController.1
            {
                KeyguardHostViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
                if (z) {
                    if (!((KeyguardHostView) ((ViewController) KeyguardHostViewController.this).mView).isVisibleToUser()) {
                        Log.i("KeyguardViewBase", "TrustAgent dismissed Keyguard.");
                    }
                    KeyguardHostViewController.this.mSecurityCallback.dismiss(false, KeyguardUpdateMonitor.getCurrentUser(), false, KeyguardSecurityModel.SecurityMode.Invalid);
                } else if (trustGrantFlags.isInitiatedByUser() || trustGrantFlags.dismissKeyguardRequested()) {
                    KeyguardHostViewController.this.mViewMediatorCallback.playTrustedSound();
                }
            }
        };
        KeyguardSecurityContainer.SecurityCallback securityCallback = new KeyguardSecurityContainer.SecurityCallback() { // from class: com.android.keyguard.KeyguardHostViewController.2
            {
                KeyguardHostViewController.this = this;
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public boolean dismiss(boolean z, int i, boolean z2, KeyguardSecurityModel.SecurityMode securityMode) {
                return KeyguardHostViewController.this.mKeyguardSecurityContainerController.showNextSecurityScreenOrFinish(z, i, z2, securityMode);
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public void finish(boolean z, int i) {
                boolean z2;
                if (KeyguardHostViewController.this.mDismissAction != null) {
                    z2 = KeyguardHostViewController.this.mDismissAction.onDismiss();
                    KeyguardHostViewController.this.mDismissAction = null;
                    KeyguardHostViewController.this.mCancelAction = null;
                } else {
                    z2 = false;
                }
                if (KeyguardHostViewController.this.mViewMediatorCallback != null) {
                    if (z2) {
                        KeyguardHostViewController.this.mViewMediatorCallback.keyguardDonePending(z, i);
                    } else {
                        KeyguardHostViewController.this.mViewMediatorCallback.keyguardDone(z, i);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public void onCancelClicked() {
                KeyguardHostViewController.this.mViewMediatorCallback.onCancelClicked();
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public void onSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode, boolean z) {
                KeyguardHostViewController.this.mViewMediatorCallback.setNeedsInput(z);
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public void reset() {
                KeyguardHostViewController.this.mViewMediatorCallback.resetKeyguard();
            }

            @Override // com.android.keyguard.KeyguardSecurityContainer.SecurityCallback
            public void userActivity() {
                KeyguardHostViewController.this.mViewMediatorCallback.userActivity();
            }
        };
        this.mSecurityCallback = securityCallback;
        this.mOnKeyListener = new View.OnKeyListener() { // from class: com.android.keyguard.KeyguardHostViewController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return KeyguardHostViewController.m595$r8$lambda$JrjRYhNBUiyIPkhGa_AFPX2_a8(KeyguardHostViewController.this, view, i, keyEvent);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAudioManager = audioManager;
        this.mTelephonyManager = telephonyManager;
        this.mViewMediatorCallback = viewMediatorCallback;
        this.mKeyguardSecurityContainerController = factory.create(securityCallback);
    }

    public /* synthetic */ boolean lambda$new$0(View view, int i, KeyEvent keyEvent) {
        return interceptMediaKey(keyEvent);
    }

    public void appear(int i) {
        if (((KeyguardHostView) ((ViewController) this).mView).getHeight() != 0 && ((KeyguardHostView) ((ViewController) this).mView).getHeight() != i) {
            this.mKeyguardSecurityContainerController.startAppearAnimation();
            return;
        }
        ((KeyguardHostView) ((ViewController) this).mView).getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.android.keyguard.KeyguardHostViewController.3
            {
                KeyguardHostViewController.this = this;
            }

            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ((KeyguardHostView) ((ViewController) KeyguardHostViewController.this).mView).getViewTreeObserver().removeOnPreDrawListener(this);
                KeyguardHostViewController.this.mKeyguardSecurityContainerController.startAppearAnimation();
                return true;
            }
        });
        ((KeyguardHostView) ((ViewController) this).mView).requestLayout();
    }

    public void cancelDismissAction() {
        setOnDismissAction(null, null);
    }

    public void cleanUp() {
        this.mKeyguardSecurityContainerController.onPause();
    }

    public boolean dismiss(int i) {
        return this.mSecurityCallback.dismiss(false, i, false, getCurrentSecurityMode());
    }

    public boolean dispatchBackKeyEventPreIme() {
        return this.mKeyguardSecurityContainerController.getCurrentSecurityMode() == KeyguardSecurityModel.SecurityMode.Password;
    }

    public void finish(boolean z, int i) {
        this.mSecurityCallback.finish(z, i);
    }

    public CharSequence getAccessibilityTitleForCurrentMode() {
        return this.mKeyguardSecurityContainerController.getTitle();
    }

    public KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.mKeyguardSecurityContainerController.getCurrentSecurityMode();
    }

    public final void handleMediaKeyEvent(KeyEvent keyEvent) {
        this.mAudioManager.dispatchMediaKeyEvent(keyEvent);
    }

    public boolean hasDismissActions() {
        return (this.mDismissAction == null && this.mCancelAction == null) ? false : true;
    }

    public boolean interceptMediaKey(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            if (keyEvent.getAction() == 1) {
                if (keyCode != 79 && keyCode != 130 && keyCode != 222 && keyCode != 126 && keyCode != 127) {
                    switch (keyCode) {
                        case 85:
                        case 86:
                        case 87:
                        case 88:
                        case 89:
                        case 90:
                        case 91:
                            break;
                        default:
                            return false;
                    }
                }
                handleMediaKeyEvent(keyEvent);
                return true;
            }
            return false;
        }
        if (keyCode != 79 && keyCode != 130 && keyCode != 222) {
            if (keyCode != 126 && keyCode != 127) {
                switch (keyCode) {
                    case 85:
                        break;
                    case 86:
                    case 87:
                    case 88:
                    case 89:
                    case 90:
                    case 91:
                        break;
                    default:
                        return false;
                }
            }
            TelephonyManager telephonyManager = this.mTelephonyManager;
            if (telephonyManager != null && telephonyManager.getCallState() != 0) {
                return true;
            }
        }
        handleMediaKeyEvent(keyEvent);
        return true;
    }

    public void onBouncerVisibilityChanged(int i) {
        this.mKeyguardSecurityContainerController.onBouncerVisibilityChanged(i);
    }

    public void onInit() {
        this.mKeyguardSecurityContainerController.init();
        updateResources();
    }

    public void onPause() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", String.format("screen off, instance %s at %s", Integer.toHexString(hashCode()), Long.valueOf(SystemClock.uptimeMillis())));
        }
        this.mKeyguardSecurityContainerController.showPrimarySecurityScreen(true);
        this.mKeyguardSecurityContainerController.onPause();
        ((KeyguardHostView) ((ViewController) this).mView).clearFocus();
    }

    public void onResume() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", "screen on, instance " + Integer.toHexString(hashCode()));
        }
        this.mKeyguardSecurityContainerController.onResume(1);
        ((KeyguardHostView) ((ViewController) this).mView).requestFocus();
    }

    public void onStartingToHide() {
        this.mKeyguardSecurityContainerController.onStartingToHide();
    }

    public void onViewAttached() {
        ((KeyguardHostView) ((ViewController) this).mView).setViewMediatorCallback(this.mViewMediatorCallback);
        this.mViewMediatorCallback.setNeedsInput(this.mKeyguardSecurityContainerController.needsInput());
        this.mKeyguardUpdateMonitor.registerCallback(this.mUpdateCallback);
        ((KeyguardHostView) ((ViewController) this).mView).setOnKeyListener(this.mOnKeyListener);
        this.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
    }

    public void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mUpdateCallback);
        ((KeyguardHostView) ((ViewController) this).mView).setOnKeyListener(null);
    }

    public void resetSecurityContainer() {
        this.mKeyguardSecurityContainerController.reset();
    }

    public void setExpansion(float f) {
        float showBouncerProgress = BouncerPanelExpansionCalculator.showBouncerProgress(f);
        ((KeyguardHostView) ((ViewController) this).mView).setAlpha(MathUtils.constrain(1.0f - showBouncerProgress, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f));
        ((KeyguardHostView) ((ViewController) this).mView).setTranslationY(showBouncerProgress * this.mTranslationY);
    }

    public void setOnDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
        Runnable runnable2 = this.mCancelAction;
        if (runnable2 != null) {
            runnable2.run();
            this.mCancelAction = null;
        }
        this.mDismissAction = onDismissAction;
        this.mCancelAction = runnable;
    }

    public boolean shouldEnableMenuKey() {
        return !((KeyguardHostView) ((ViewController) this).mView).getResources().getBoolean(R$bool.config_disableMenuKeyInLockScreen) || ActivityManager.isRunningInTestHarness() || new File("/data/local/enable_menu_key").exists();
    }

    public void showErrorMessage(CharSequence charSequence) {
        showMessage(charSequence, Utils.getColorError(((KeyguardHostView) ((ViewController) this).mView).getContext()));
    }

    public void showMessage(CharSequence charSequence, ColorStateList colorStateList) {
        this.mKeyguardSecurityContainerController.showMessage(charSequence, colorStateList);
    }

    public void showPrimarySecurityScreen() {
        if (DEBUG) {
            Log.d("KeyguardViewBase", "show()");
        }
        this.mKeyguardSecurityContainerController.showPrimarySecurityScreen(false);
    }

    public void showPromptReason(int i) {
        this.mKeyguardSecurityContainerController.showPromptReason(i);
    }

    public void startDisappearAnimation(Runnable runnable) {
        if (this.mKeyguardSecurityContainerController.startDisappearAnimation(runnable) || runnable == null) {
            return;
        }
        runnable.run();
    }

    public void updateKeyguardPosition(float f) {
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.mKeyguardSecurityContainerController;
        if (keyguardSecurityContainerController != null) {
            keyguardSecurityContainerController.updateKeyguardPosition(f);
        }
    }

    public void updateResources() {
        Resources resources = ((KeyguardHostView) ((ViewController) this).mView).getResources();
        int integer = resources.getBoolean(R$bool.can_use_one_handed_bouncer) ? resources.getInteger(R$integer.keyguard_host_view_one_handed_gravity) : resources.getInteger(R$integer.keyguard_host_view_gravity);
        this.mTranslationY = resources.getDimensionPixelSize(R$dimen.keyguard_host_view_translation_y);
        if (((KeyguardHostView) ((ViewController) this).mView).getLayoutParams() instanceof FrameLayout.LayoutParams) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) ((KeyguardHostView) ((ViewController) this).mView).getLayoutParams();
            if (layoutParams.gravity != integer) {
                layoutParams.gravity = integer;
                ((KeyguardHostView) ((ViewController) this).mView).setLayoutParams(layoutParams);
            }
        }
        KeyguardSecurityContainerController keyguardSecurityContainerController = this.mKeyguardSecurityContainerController;
        if (keyguardSecurityContainerController != null) {
            keyguardSecurityContainerController.updateResources();
        }
    }
}