package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import com.android.keyguard.KeyguardMessageArea;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardMessageAreaController.class */
public class KeyguardMessageAreaController<T extends KeyguardMessageArea> extends ViewController<T> {
    public final ConfigurationController mConfigurationController;
    public ConfigurationController.ConfigurationListener mConfigurationListener;
    public KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardMessageAreaController$Factory.class */
    public static class Factory {
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
        }

        public KeyguardMessageAreaController create(KeyguardMessageArea keyguardMessageArea) {
            return new KeyguardMessageAreaController(keyguardMessageArea, this.mKeyguardUpdateMonitor, this.mConfigurationController);
        }
    }

    public KeyguardMessageAreaController(T t, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        super(t);
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardMessageAreaController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onFinishedGoingToSleep(int i) {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).setSelected(false);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onStartedWakingUp() {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).setSelected(true);
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardMessageAreaController.2
            public void onConfigChanged(Configuration configuration) {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).onConfigChanged();
            }

            public void onDensityOrFontScaleChanged() {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).onDensityOrFontScaleChanged();
            }

            public void onThemeChanged() {
                ((KeyguardMessageArea) ((ViewController) KeyguardMessageAreaController.this).mView).onThemeChanged();
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mConfigurationController = configurationController;
    }

    public CharSequence getMessage() {
        return ((KeyguardMessageArea) ((ViewController) this).mView).getText();
    }

    public void onViewAttached() {
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        ((KeyguardMessageArea) ((ViewController) this).mView).setSelected(this.mKeyguardUpdateMonitor.isDeviceInteractive());
        ((KeyguardMessageArea) ((ViewController) this).mView).onThemeChanged();
    }

    public void onViewDetached() {
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
    }

    public void setIsVisible(boolean z) {
        ((KeyguardMessageArea) ((ViewController) this).mView).setIsVisible(z);
    }

    public void setMessage(int i) {
        setMessage(i != 0 ? ((KeyguardMessageArea) ((ViewController) this).mView).getResources().getString(i) : null);
    }

    public void setMessage(CharSequence charSequence) {
        setMessage(charSequence, true);
    }

    public void setMessage(CharSequence charSequence, boolean z) {
        ((KeyguardMessageArea) ((ViewController) this).mView).setMessage(charSequence, z);
    }

    public void setNextMessageColor(ColorStateList colorStateList) {
        ((KeyguardMessageArea) ((ViewController) this).mView).setNextMessageColor(colorStateList);
    }
}