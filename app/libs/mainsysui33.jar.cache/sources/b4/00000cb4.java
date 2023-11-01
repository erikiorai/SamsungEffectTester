package com.android.keyguard.clock;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.widget.FrameLayout;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.doze.util.BurnInHelperKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockLayout.class */
public class ClockLayout extends FrameLayout {
    public View mAnalogClock;
    public int mBurnInPreventionOffsetX;
    public int mBurnInPreventionOffsetY;
    public float mDarkAmount;

    public ClockLayout(Context context) {
        this(context, null);
    }

    public ClockLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClockLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mAnalogClock = findViewById(R$id.analog_clock);
        Resources resources = getResources();
        this.mBurnInPreventionOffsetX = resources.getDimensionPixelSize(R$dimen.burn_in_prevention_offset_x);
        this.mBurnInPreventionOffsetY = resources.getDimensionPixelSize(R$dimen.burn_in_prevention_offset_y);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        positionChildren();
    }

    public final void positionChildren() {
        float lerp = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mBurnInPreventionOffsetX * 2, true) - this.mBurnInPreventionOffsetX, this.mDarkAmount);
        float lerp2 = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mBurnInPreventionOffsetY * 2, false) - (this.mBurnInPreventionOffsetY * 0.5f), this.mDarkAmount);
        View view = this.mAnalogClock;
        if (view != null) {
            view.setX(Math.max((float) ActionBarShadowController.ELEVATION_LOW, (getWidth() - this.mAnalogClock.getWidth()) * 0.5f) + (lerp * 3.0f));
            this.mAnalogClock.setY(Math.max((float) ActionBarShadowController.ELEVATION_LOW, (getHeight() - this.mAnalogClock.getHeight()) * 0.5f) + (lerp2 * 3.0f));
        }
    }
}