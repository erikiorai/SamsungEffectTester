package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsAnimationView.class */
public abstract class UdfpsAnimationView extends FrameLayout {
    public int mAlpha;
    public float mDialogSuggestedAlpha;
    public float mNotificationShadeExpansion;
    public boolean mPauseAuth;
    public boolean mUseExpandedOverlay;

    public UdfpsAnimationView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDialogSuggestedAlpha = 1.0f;
        this.mNotificationShadeExpansion = ActionBarShadowController.ELEVATION_LOW;
        this.mUseExpandedOverlay = false;
    }

    public int calculateAlpha() {
        int expansionToAlpha = (int) (expansionToAlpha(this.mNotificationShadeExpansion) * this.mDialogSuggestedAlpha);
        this.mAlpha = expansionToAlpha;
        if (!this.mPauseAuth) {
            expansionToAlpha = 255;
        }
        return expansionToAlpha;
    }

    public boolean dozeTimeTick() {
        return false;
    }

    public final int expansionToAlpha(float f) {
        if (f >= 0.4f) {
            return 0;
        }
        return (int) ((1.0f - (f / 0.4f)) * 255.0f);
    }

    public RectF getBoundsRelativeToView(RectF rectF) {
        int[] locationOnScreen = getLocationOnScreen();
        float f = rectF.left;
        int i = locationOnScreen[0];
        float f2 = i;
        float f3 = rectF.top;
        int i2 = locationOnScreen[1];
        return new RectF(f - f2, f3 - i2, rectF.right - i, rectF.bottom - i2);
    }

    public float getDialogSuggestedAlpha() {
        return this.mDialogSuggestedAlpha;
    }

    public abstract UdfpsDrawable getDrawable();

    public boolean isPauseAuth() {
        return this.mPauseAuth;
    }

    public void onDisplayConfiguring() {
        getDrawable().setDisplayConfigured(true);
        getDrawable().invalidateSelf();
    }

    public void onDisplayUnconfigured() {
        getDrawable().setDisplayConfigured(false);
        getDrawable().invalidateSelf();
    }

    public void onExpansionChanged(float f) {
        this.mNotificationShadeExpansion = f;
        updateAlpha();
    }

    public void onSensorRectUpdated(RectF rectF) {
        getDrawable().onSensorRectUpdated(rectF);
    }

    public void setDialogSuggestedAlpha(float f) {
        this.mDialogSuggestedAlpha = f;
        updateAlpha();
    }

    public boolean setPauseAuth(boolean z) {
        if (z != this.mPauseAuth) {
            this.mPauseAuth = z;
            updateAlpha();
            return true;
        }
        return false;
    }

    public int updateAlpha() {
        int calculateAlpha = calculateAlpha();
        getDrawable().setAlpha(calculateAlpha);
        if (this.mPauseAuth && calculateAlpha == 0 && getParent() != null) {
            ((ViewGroup) getParent()).setVisibility(4);
        } else {
            ((ViewGroup) getParent()).setVisibility(0);
        }
        return calculateAlpha;
    }
}