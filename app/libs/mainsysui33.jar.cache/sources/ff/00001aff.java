package com.android.systemui.keyguard.ui.binder;

import android.view.KeyEvent;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.systemui.keyguard.data.BouncerViewDelegate;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/binder/KeyguardBouncerViewBinder.class */
public final class KeyguardBouncerViewBinder {
    public static final KeyguardBouncerViewBinder INSTANCE = new KeyguardBouncerViewBinder();

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1] */
    public static final void bind(ViewGroup viewGroup, KeyguardBouncerViewModel keyguardBouncerViewModel, KeyguardBouncerComponent.Factory factory) {
        final KeyguardHostViewController keyguardHostViewController = factory.create(viewGroup).getKeyguardHostViewController();
        keyguardHostViewController.init();
        RepeatWhenAttachedKt.repeatWhenAttached$default(viewGroup, null, new KeyguardBouncerViewBinder$bind$1(keyguardBouncerViewModel, new BouncerViewDelegate() { // from class: com.android.systemui.keyguard.ui.binder.KeyguardBouncerViewBinder$bind$delegate$1
            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean dispatchBackKeyEventPreIme() {
                return KeyguardHostViewController.this.dispatchBackKeyEventPreIme();
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean interceptMediaKey(KeyEvent keyEvent) {
                return KeyguardHostViewController.this.interceptMediaKey(keyEvent);
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean isFullScreenBouncer() {
                KeyguardSecurityModel.SecurityMode currentSecurityMode = KeyguardHostViewController.this.getCurrentSecurityMode();
                return currentSecurityMode == KeyguardSecurityModel.SecurityMode.SimPin || currentSecurityMode == KeyguardSecurityModel.SecurityMode.SimPuk;
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public void resume() {
                KeyguardHostViewController.this.showPrimarySecurityScreen();
                KeyguardHostViewController.this.onResume();
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public void setDismissAction(ActivityStarter.OnDismissAction onDismissAction, Runnable runnable) {
                KeyguardHostViewController.this.setOnDismissAction(onDismissAction, runnable);
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean shouldDismissOnMenuPressed() {
                return KeyguardHostViewController.this.shouldEnableMenuKey();
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean showNextSecurityScreenOrFinish() {
                return KeyguardHostViewController.this.dismiss(KeyguardUpdateMonitor.getCurrentUser());
            }

            @Override // com.android.systemui.keyguard.data.BouncerViewDelegate
            public boolean willDismissWithActions() {
                return KeyguardHostViewController.this.hasDismissActions();
            }
        }, keyguardHostViewController, viewGroup, null), 1, null);
    }
}