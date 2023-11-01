package com.android.keyguard;

import android.view.LayoutInflater;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardInputViewController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewFlipperController_Factory.class */
public final class KeyguardSecurityViewFlipperController_Factory implements Factory<KeyguardSecurityViewFlipperController> {
    public final Provider<EmergencyButtonController.Factory> emergencyButtonControllerFactoryProvider;
    public final Provider<KeyguardInputViewController.Factory> keyguardSecurityViewControllerFactoryProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<KeyguardSecurityViewFlipper> viewProvider;

    public KeyguardSecurityViewFlipperController_Factory(Provider<KeyguardSecurityViewFlipper> provider, Provider<LayoutInflater> provider2, Provider<KeyguardInputViewController.Factory> provider3, Provider<EmergencyButtonController.Factory> provider4) {
        this.viewProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.keyguardSecurityViewControllerFactoryProvider = provider3;
        this.emergencyButtonControllerFactoryProvider = provider4;
    }

    public static KeyguardSecurityViewFlipperController_Factory create(Provider<KeyguardSecurityViewFlipper> provider, Provider<LayoutInflater> provider2, Provider<KeyguardInputViewController.Factory> provider3, Provider<EmergencyButtonController.Factory> provider4) {
        return new KeyguardSecurityViewFlipperController_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardSecurityViewFlipperController newInstance(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper, LayoutInflater layoutInflater, KeyguardInputViewController.Factory factory, EmergencyButtonController.Factory factory2) {
        return new KeyguardSecurityViewFlipperController(keyguardSecurityViewFlipper, layoutInflater, factory, factory2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardSecurityViewFlipperController m659get() {
        return newInstance((KeyguardSecurityViewFlipper) this.viewProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (KeyguardInputViewController.Factory) this.keyguardSecurityViewControllerFactoryProvider.get(), (EmergencyButtonController.Factory) this.emergencyButtonControllerFactoryProvider.get());
    }
}