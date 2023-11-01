package com.android.keyguard.dagger;

import com.android.keyguard.KeyguardClockSwitchController;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusViewComponent.class */
public interface KeyguardStatusViewComponent {

    /* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusViewComponent$Factory.class */
    public interface Factory {
        KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView);
    }

    KeyguardClockSwitchController getKeyguardClockSwitchController();

    KeyguardStatusViewController getKeyguardStatusViewController();
}