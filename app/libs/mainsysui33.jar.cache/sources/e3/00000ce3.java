package com.android.keyguard.dagger;

import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardUserSwitcherComponent.class */
public interface KeyguardUserSwitcherComponent {

    /* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardUserSwitcherComponent$Factory.class */
    public interface Factory {
        KeyguardUserSwitcherComponent build(KeyguardUserSwitcherView keyguardUserSwitcherView);
    }

    KeyguardUserSwitcherController getKeyguardUserSwitcherController();
}