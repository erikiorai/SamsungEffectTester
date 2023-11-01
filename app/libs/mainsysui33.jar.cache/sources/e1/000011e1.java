package com.android.systemui.biometrics;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsBpViewController.class */
public final class UdfpsBpViewController extends UdfpsAnimationViewController<UdfpsBpView> {
    public final String tag;

    public UdfpsBpViewController(UdfpsBpView udfpsBpView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsBpView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.tag = "UdfpsBpViewController";
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return this.tag;
    }
}