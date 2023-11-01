package com.android.keyguard.dagger;

import com.android.keyguard.CarrierText;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusBarViewModule_GetCarrierTextFactory.class */
public final class KeyguardStatusBarViewModule_GetCarrierTextFactory implements Factory<CarrierText> {
    public final Provider<KeyguardStatusBarView> viewProvider;

    public KeyguardStatusBarViewModule_GetCarrierTextFactory(Provider<KeyguardStatusBarView> provider) {
        this.viewProvider = provider;
    }

    public static KeyguardStatusBarViewModule_GetCarrierTextFactory create(Provider<KeyguardStatusBarView> provider) {
        return new KeyguardStatusBarViewModule_GetCarrierTextFactory(provider);
    }

    public static CarrierText getCarrierText(KeyguardStatusBarView keyguardStatusBarView) {
        return (CarrierText) Preconditions.checkNotNullFromProvides(KeyguardStatusBarViewModule.getCarrierText(keyguardStatusBarView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CarrierText m842get() {
        return getCarrierText((KeyguardStatusBarView) this.viewProvider.get());
    }
}