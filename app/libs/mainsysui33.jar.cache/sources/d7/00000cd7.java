package com.android.keyguard.dagger;

import android.widget.FrameLayout;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardQsUserSwitchComponent.class */
public interface KeyguardQsUserSwitchComponent {

    /* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardQsUserSwitchComponent$Factory.class */
    public interface Factory {
        KeyguardQsUserSwitchComponent build(FrameLayout frameLayout);
    }

    KeyguardQsUserSwitchController getKeyguardQsUserSwitchController();
}