package com.android.keyguard;

import android.graphics.Rect;
import android.util.Slog;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.ClockAnimations;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardStatusViewController.class */
public class KeyguardStatusViewController extends ViewController<KeyguardStatusView> {
    public final Rect mClipBounds;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardClockSwitchController mKeyguardClockSwitchController;
    public final KeyguardSliceViewController mKeyguardSliceViewController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardVisibilityHelper mKeyguardVisibilityHelper;
    public static final boolean DEBUG = KeyguardConstants.DEBUG;
    public static final AnimationProperties CLOCK_ANIMATION_PROPERTIES = new AnimationProperties().setDuration(360);

    public KeyguardStatusViewController(KeyguardStatusView keyguardStatusView, KeyguardSliceViewController keyguardSliceViewController, KeyguardClockSwitchController keyguardClockSwitchController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, DozeParameters dozeParameters, FeatureFlags featureFlags, ScreenOffAnimationController screenOffAnimationController) {
        super(keyguardStatusView);
        this.mClipBounds = new Rect();
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardStatusViewController.1
            public void onDensityOrFontScaleChanged() {
                KeyguardStatusViewController.this.mKeyguardClockSwitchController.onDensityOrFontScaleChanged();
            }

            public void onLocaleListChanged() {
                KeyguardStatusViewController.this.refreshTime();
                KeyguardStatusViewController.this.mKeyguardClockSwitchController.onLocaleListChanged();
            }
        };
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardStatusViewController.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardVisibilityChanged(boolean z) {
                if (z) {
                    if (KeyguardStatusViewController.DEBUG) {
                        Slog.v("KeyguardStatusViewController", "refresh statusview visible:true");
                    }
                    KeyguardStatusViewController.this.refreshTime();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onTimeChanged() {
                KeyguardStatusViewController.this.refreshTime();
            }
        };
        this.mKeyguardSliceViewController = keyguardSliceViewController;
        this.mKeyguardClockSwitchController = keyguardClockSwitchController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
        KeyguardVisibilityHelper keyguardVisibilityHelper = new KeyguardVisibilityHelper(((ViewController) this).mView, keyguardStateController, dozeParameters, screenOffAnimationController, true);
        this.mKeyguardVisibilityHelper = keyguardVisibilityHelper;
        keyguardVisibilityHelper.setOcclusionTransitionFlagEnabled(featureFlags.isEnabled(Flags.UNOCCLUSION_TRANSITION));
    }

    public void animateFoldToAod(float f) {
        this.mKeyguardClockSwitchController.animateFoldToAod(f);
    }

    public void displayClock(int i, boolean z) {
        this.mKeyguardClockSwitchController.displayClock(i, z);
    }

    public void dozeTimeTick() {
        refreshTime();
        this.mKeyguardSliceViewController.refresh();
    }

    public ClockAnimations getClockAnimations() {
        return this.mKeyguardClockSwitchController.getClockAnimations();
    }

    public int getClockBottom(int i) {
        return this.mKeyguardClockSwitchController.getClockBottom(i);
    }

    public int getLockscreenHeight() {
        return ((KeyguardStatusView) ((ViewController) this).mView).getHeight() - this.mKeyguardClockSwitchController.getNotificationIconAreaHeight();
    }

    public boolean isClockTopAligned() {
        return this.mKeyguardClockSwitchController.isClockTopAligned();
    }

    public void onInit() {
        this.mKeyguardClockSwitchController.init();
    }

    public void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
    }

    public void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public final void refreshTime() {
        this.mKeyguardClockSwitchController.refresh();
    }

    public void setAlpha(float f) {
        if (this.mKeyguardVisibilityHelper.isVisibilityAnimating()) {
            return;
        }
        ((KeyguardStatusView) ((ViewController) this).mView).setAlpha(f);
    }

    public void setClipBounds(Rect rect) {
        if (rect == null) {
            ((KeyguardStatusView) ((ViewController) this).mView).setClipBounds(null);
            return;
        }
        this.mClipBounds.set(rect.left, (int) (rect.top - ((KeyguardStatusView) ((ViewController) this).mView).getY()), rect.right, (int) (rect.bottom - ((KeyguardStatusView) ((ViewController) this).mView).getY()));
        ((KeyguardStatusView) ((ViewController) this).mView).setClipBounds(this.mClipBounds);
    }

    public void setKeyguardStatusViewVisibility(int i, boolean z, boolean z2, int i2) {
        this.mKeyguardVisibilityHelper.setViewVisibility(i, z, z2, i2);
    }

    public void setStatusAccessibilityImportance(int i) {
        ((KeyguardStatusView) ((ViewController) this).mView).setImportantForAccessibility(i);
    }

    public void setTranslationY(float f, boolean z) {
        ((KeyguardStatusView) ((ViewController) this).mView).setChildrenTranslationY(f, z);
    }

    public void updatePivot(float f, float f2) {
        ((KeyguardStatusView) ((ViewController) this).mView).setPivotX(f / 2.0f);
        ((KeyguardStatusView) ((ViewController) this).mView).setPivotY(this.mKeyguardClockSwitchController.getClockHeight() / 2.0f);
    }

    public void updatePosition(int i, int i2, float f, boolean z) {
        AnimationProperties animationProperties = CLOCK_ANIMATION_PROPERTIES;
        PropertyAnimator.setProperty((KeyguardStatusView) ((ViewController) this).mView, AnimatableProperty.Y, i2, animationProperties, z);
        this.mKeyguardClockSwitchController.updatePosition(i, f, animationProperties, z);
    }
}