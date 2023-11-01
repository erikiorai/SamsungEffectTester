package com.android.systemui.screenshot;

import com.android.systemui.screenshot.ScreenshotController;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ScreenshotController$$ExternalSyntheticLambda3.class */
public final /* synthetic */ class ScreenshotController$$ExternalSyntheticLambda3 implements ScreenshotController.ActionsReadyListener {
    public final /* synthetic */ ScreenshotController f$0;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ScreenshotController.onDestroy():void, com.android.systemui.screenshot.ScreenshotController.saveScreenshotInWorkerThread(android.os.UserHandle, java.util.function.Consumer<android.net.Uri>, com.android.systemui.screenshot.ScreenshotController$ActionsReadyListener, com.android.systemui.screenshot.ScreenshotController$QuickShareActionReadyListener):void] */
    public /* synthetic */ ScreenshotController$$ExternalSyntheticLambda3(ScreenshotController screenshotController) {
        this.f$0 = screenshotController;
    }

    @Override // com.android.systemui.screenshot.ScreenshotController.ActionsReadyListener
    public final void onActionsReady(ScreenshotController.SavedImageData savedImageData) {
        ScreenshotController.m4265$r8$lambda$pIoEcgYVCTGoWDtBJ9gT_xxCL8(this.f$0, savedImageData);
    }
}