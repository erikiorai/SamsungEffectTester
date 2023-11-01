package com.android.systemui.biometrics;

import android.graphics.PointF;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$integer;
import com.android.systemui.biometrics.UdfpsEnrollHelper;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollViewController.class */
public class UdfpsEnrollViewController extends UdfpsAnimationViewController<UdfpsEnrollView> {
    public final UdfpsEnrollHelper mEnrollHelper;
    public final UdfpsEnrollHelper.Listener mEnrollHelperListener;
    public final int mEnrollProgressBarRadius;

    public UdfpsEnrollViewController(UdfpsEnrollView udfpsEnrollView, UdfpsEnrollHelper udfpsEnrollHelper, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, FeatureFlags featureFlags, float f) {
        super(udfpsEnrollView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.mEnrollHelperListener = new UdfpsEnrollHelper.Listener() { // from class: com.android.systemui.biometrics.UdfpsEnrollViewController.1
            @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
            public void onEnrollmentHelp(int i, int i2) {
                ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onEnrollmentHelp(i, i2);
            }

            @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
            public void onEnrollmentProgress(int i, int i2) {
                ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onEnrollmentProgress(i, i2);
            }

            @Override // com.android.systemui.biometrics.UdfpsEnrollHelper.Listener
            public void onLastStepAcquired() {
                ((UdfpsEnrollView) ((ViewController) UdfpsEnrollViewController.this).mView).onLastStepAcquired();
            }
        };
        int integer = (int) (f * getContext().getResources().getInteger(R$integer.config_udfpsEnrollProgressBar));
        this.mEnrollProgressBarRadius = integer;
        this.mEnrollHelper = udfpsEnrollHelper;
        ((UdfpsEnrollView) ((ViewController) this).mView).setEnrollHelper(udfpsEnrollHelper);
        ((UdfpsEnrollView) ((ViewController) this).mView).setProgressBarRadius(integer);
        if (featureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
            ((UdfpsEnrollView) ((ViewController) this).mView).mUseExpandedOverlay = true;
        }
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void doAnnounceForAccessibility(String str) {
        ((UdfpsEnrollView) ((ViewController) this).mView).announceForAccessibility(str);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public int getPaddingX() {
        return this.mEnrollProgressBarRadius;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public int getPaddingY() {
        return this.mEnrollProgressBarRadius;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return "UdfpsEnrollViewController";
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public PointF getTouchTranslation() {
        return !this.mEnrollHelper.isGuidedEnrollmentStage() ? new PointF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW) : this.mEnrollHelper.getNextGuidedEnrollmentPoint();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void onViewAttached() {
        super.onViewAttached();
        if (this.mEnrollHelper.shouldShowProgressBar()) {
            this.mEnrollHelper.setListener(this.mEnrollHelperListener);
        }
    }
}