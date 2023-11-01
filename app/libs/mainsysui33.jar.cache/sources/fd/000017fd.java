package com.android.systemui.flags;

import com.android.internal.statusbar.IStatusBarService;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/SystemExitRestarter.class */
public final class SystemExitRestarter implements Restarter {
    public final IStatusBarService barService;

    public SystemExitRestarter(IStatusBarService iStatusBarService) {
        this.barService = iStatusBarService;
    }

    @Override // com.android.systemui.flags.Restarter
    public void restartAndroid() {
        this.barService.restart();
    }

    @Override // com.android.systemui.flags.Restarter
    public void restartSystemUI() {
        System.exit(0);
    }
}