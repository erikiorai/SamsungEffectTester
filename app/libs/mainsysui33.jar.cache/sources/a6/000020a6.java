package com.android.systemui.qs;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/FooterActionsController.class */
public final class FooterActionsController {
    public final FgsManagerController fgsManagerController;

    public FooterActionsController(FgsManagerController fgsManagerController) {
        this.fgsManagerController = fgsManagerController;
    }

    public final void init() {
        this.fgsManagerController.init();
    }
}