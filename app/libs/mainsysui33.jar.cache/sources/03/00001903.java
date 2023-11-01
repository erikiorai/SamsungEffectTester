package com.android.systemui.keyguard.data;

import android.view.KeyEvent;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/BouncerViewDelegate.class */
public interface BouncerViewDelegate {
    boolean dispatchBackKeyEventPreIme();

    boolean interceptMediaKey(KeyEvent keyEvent);

    boolean isFullScreenBouncer();

    void resume();

    void setDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable);

    boolean shouldDismissOnMenuPressed();

    boolean showNextSecurityScreenOrFinish();

    boolean willDismissWithActions();
}