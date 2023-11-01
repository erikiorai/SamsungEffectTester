package com.android.keyguard.dagger;

import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusBarViewComponent.class */
public interface KeyguardStatusBarViewComponent {

    /* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusBarViewComponent$Factory.class */
    public interface Factory {
        KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider);
    }

    KeyguardStatusBarViewController getKeyguardStatusBarViewController();
}