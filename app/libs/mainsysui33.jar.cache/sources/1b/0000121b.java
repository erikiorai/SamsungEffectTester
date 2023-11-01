package com.android.systemui.biometrics;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsFpmOtherViewController.class */
public final class UdfpsFpmOtherViewController extends UdfpsAnimationViewController<UdfpsFpmOtherView> {
    public final String tag;

    public UdfpsFpmOtherViewController(UdfpsFpmOtherView udfpsFpmOtherView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager) {
        super(udfpsFpmOtherView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.tag = "UdfpsFpmOtherViewController";
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return this.tag;
    }
}