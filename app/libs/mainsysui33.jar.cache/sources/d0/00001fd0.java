package com.android.systemui.plugins.qs;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/qs/QSContainerController.class */
public interface QSContainerController {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/qs/QSContainerController$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void setCustomizerShowing(QSContainerController qSContainerController, boolean z) {
            qSContainerController.setCustomizerShowing(z, 0L);
        }
    }

    void setCustomizerAnimating(boolean z);

    void setCustomizerShowing(boolean z);

    void setCustomizerShowing(boolean z, long j);

    void setDetailShowing(boolean z);
}