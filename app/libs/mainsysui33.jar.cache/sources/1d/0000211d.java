package com.android.systemui.qs;

import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSSquishinessController.class */
public final class QSSquishinessController {
    public final QSAnimator qsAnimator;
    public final QSPanelController qsPanelController;
    public final QuickQSPanelController quickQSPanelController;
    public float squishiness = 1.0f;

    public QSSquishinessController(QSAnimator qSAnimator, QSPanelController qSPanelController, QuickQSPanelController quickQSPanelController) {
        this.qsAnimator = qSAnimator;
        this.qsPanelController = qSPanelController;
        this.quickQSPanelController = quickQSPanelController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0041, code lost:
        if ((r4 == 1.0f) == false) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0069, code lost:
        if ((r4 == com.android.settingslib.widget.ActionBarShadowController.ELEVATION_LOW) != false) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x006c, code lost:
        r3.qsAnimator.requestAnimatorUpdate();
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setSquishiness(float f) {
        float f2 = this.squishiness;
        if (f2 == f) {
            return;
        }
        if (!(f2 == 1.0f)) {
        }
        if (!(f2 == ActionBarShadowController.ELEVATION_LOW)) {
        }
        this.squishiness = f;
        updateSquishiness();
    }

    public final void updateSquishiness() {
        this.qsPanelController.setSquishinessFraction(this.squishiness);
        this.quickQSPanelController.setSquishinessFraction(this.squishiness);
    }
}