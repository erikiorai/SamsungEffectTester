package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardStatusView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusViewModule_GetKeyguardClockSwitchFactory.class */
public final class KeyguardStatusViewModule_GetKeyguardClockSwitchFactory implements Factory<KeyguardClockSwitch> {
    public final Provider<KeyguardStatusView> keyguardPresentationProvider;

    public KeyguardStatusViewModule_GetKeyguardClockSwitchFactory(Provider<KeyguardStatusView> provider) {
        this.keyguardPresentationProvider = provider;
    }

    public static KeyguardStatusViewModule_GetKeyguardClockSwitchFactory create(Provider<KeyguardStatusView> provider) {
        return new KeyguardStatusViewModule_GetKeyguardClockSwitchFactory(provider);
    }

    public static KeyguardClockSwitch getKeyguardClockSwitch(KeyguardStatusView keyguardStatusView) {
        return (KeyguardClockSwitch) Preconditions.checkNotNullFromProvides(KeyguardStatusViewModule.getKeyguardClockSwitch(keyguardStatusView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardClockSwitch m843get() {
        return getKeyguardClockSwitch((KeyguardStatusView) this.keyguardPresentationProvider.get());
    }
}