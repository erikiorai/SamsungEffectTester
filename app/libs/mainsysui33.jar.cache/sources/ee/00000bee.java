package com.android.keyguard;

import android.view.View;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.R$id;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardPinViewController.class */
public class KeyguardPinViewController extends KeyguardPinBasedInputViewController<KeyguardPINView> {
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final DevicePostureController.Callback mPostureCallback;
    public final DevicePostureController mPostureController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$2bDHH20eFLR9VylhwTp8EepRQaQ(KeyguardPinViewController keyguardPinViewController, View view) {
        keyguardPinViewController.lambda$onViewAttached$1(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda1.onPostureChanged(int):void] */
    /* renamed from: $r8$lambda$HXoGmbJopctUdV2R-DF_dy2JqOY */
    public static /* synthetic */ void m629$r8$lambda$HXoGmbJopctUdV2RDF_dy2JqOY(KeyguardPinViewController keyguardPinViewController, int i) {
        keyguardPinViewController.lambda$new$0(i);
    }

    public KeyguardPinViewController(KeyguardPINView keyguardPINView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController) {
        super(keyguardPINView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector);
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda1
            public final void onPostureChanged(int i) {
                KeyguardPinViewController.m629$r8$lambda$HXoGmbJopctUdV2RDF_dy2JqOY(KeyguardPinViewController.this, i);
            }
        };
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPostureController = devicePostureController;
    }

    public /* synthetic */ void lambda$new$0(int i) {
        ((KeyguardPINView) ((ViewController) this).mView).onDevicePostureChanged(i);
    }

    public /* synthetic */ void lambda$onViewAttached$1(View view) {
        getKeyguardSecurityCallback().reset();
        getKeyguardSecurityCallback().onCancelClicked();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewAttached() {
        super.onViewAttached();
        View findViewById = ((KeyguardPINView) ((ViewController) this).mView).findViewById(R$id.cancel_button);
        if (findViewById != null) {
            findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardPinViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardPinViewController.$r8$lambda$2bDHH20eFLR9VylhwTp8EepRQaQ(KeyguardPinViewController.this, view);
                }
            });
        }
        this.mPostureController.addCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onViewDetached() {
        super.onViewDetached();
        this.mPostureController.removeCallback(this.mPostureCallback);
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardPINView) ((ViewController) this).mView).startDisappearAnimation(this.mKeyguardUpdateMonitor.needsSlowUnlockTransition(), runnable);
    }
}