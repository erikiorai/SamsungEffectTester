package com.android.systemui.charging;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$style;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import java.text.NumberFormat;

/* loaded from: mainsysui33.jar:com/android/systemui/charging/WirelessChargingLayout.class */
public final class WirelessChargingLayout extends FrameLayout {
    public RippleView mRippleView;

    public WirelessChargingLayout(Context context, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        super(context);
        init(context, null, i, i2, z, rippleShape);
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        boolean z2 = i != -1;
        int i3 = R$style.ChargingAnim_WallpaperBackground;
        if (z) {
            i3 = R$style.ChargingAnim_DarkBackground;
        }
        FrameLayout.inflate(new ContextThemeWrapper(context, i3), R$layout.wireless_charging_layout, this);
        TextView textView = (TextView) findViewById(R$id.wireless_charging_percentage);
        if (i2 != -1) {
            textView.setText(NumberFormat.getPercentInstance().format(i2 / 100.0f));
            textView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        }
        long integer = context.getResources().getInteger(R$integer.wireless_charging_fade_offset);
        long integer2 = context.getResources().getInteger(R$integer.wireless_charging_fade_duration);
        float f = context.getResources().getFloat(R$dimen.wireless_charging_anim_battery_level_text_size_start);
        float f2 = context.getResources().getFloat(R$dimen.wireless_charging_anim_battery_level_text_size_end) * (z2 ? 0.75f : 1.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "textSize", f, f2);
        ofFloat.setInterpolator(new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f));
        Resources resources = context.getResources();
        int i4 = R$integer.wireless_charging_battery_level_text_scale_animation_duration;
        ofFloat.setDuration(resources.getInteger(i4));
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        Resources resources2 = context.getResources();
        int i5 = R$integer.wireless_charging_battery_level_text_opacity_duration;
        ofFloat2.setDuration(resources2.getInteger(i5));
        Resources resources3 = context.getResources();
        int i6 = R$integer.wireless_charging_anim_opacity_offset;
        ofFloat2.setStartDelay(resources3.getInteger(i6));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat3.setDuration(integer2);
        ofFloat3.setInterpolator(interpolator);
        ofFloat3.setStartDelay(integer);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        ObjectAnimator ofArgb = ObjectAnimator.ofArgb(this, "backgroundColor", 0, 1275068416);
        ofArgb.setDuration(300L);
        ofArgb.setInterpolator(interpolator);
        ObjectAnimator ofArgb2 = ObjectAnimator.ofArgb(this, "backgroundColor", 1275068416, 0);
        ofArgb2.setDuration(300L);
        ofArgb2.setInterpolator(interpolator);
        ofArgb2.setStartDelay((rippleShape == RippleShader.RippleShape.CIRCLE ? 1500L : 1750L) - 300);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(ofArgb, ofArgb2);
        animatorSet2.start();
        RippleView findViewById = findViewById(R$id.wireless_charging_ripple);
        this.mRippleView = findViewById;
        findViewById.setupShader(rippleShape);
        int defaultColor = Utils.getColorAttr(this.mRippleView.getContext(), 16843829).getDefaultColor();
        if (this.mRippleView.getRippleShape() == RippleShader.RippleShape.ROUNDED_BOX) {
            this.mRippleView.setDuration(1750L);
            this.mRippleView.setSparkleStrength(0.22f);
            this.mRippleView.setColor(defaultColor, 28);
        } else {
            this.mRippleView.setDuration(1500L);
            this.mRippleView.setColor(defaultColor, 115);
        }
        this.mRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WirelessChargingLayout.1
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view) {
                WirelessChargingLayout.this.mRippleView.startRipple();
                WirelessChargingLayout.this.mRippleView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view) {
            }
        });
        if (!z2) {
            animatorSet.start();
            return;
        }
        TextView textView2 = (TextView) findViewById(R$id.reverse_wireless_charging_percentage);
        textView2.setVisibility(0);
        textView2.setText(NumberFormat.getPercentInstance().format(i / 100.0f));
        textView2.setAlpha(ActionBarShadowController.ELEVATION_LOW);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(textView2, "textSize", f, f2);
        ofFloat4.setInterpolator(new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f));
        ofFloat4.setDuration(context.getResources().getInteger(i4));
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(textView2, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat5.setInterpolator(interpolator);
        ofFloat5.setDuration(context.getResources().getInteger(i5));
        ofFloat5.setStartDelay(context.getResources().getInteger(i6));
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(textView2, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat6.setDuration(integer2);
        ofFloat6.setInterpolator(interpolator);
        ofFloat6.setStartDelay(integer);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(ofFloat4, ofFloat5, ofFloat6);
        ImageView imageView = (ImageView) findViewById(R$id.reverse_wireless_charging_icon);
        imageView.setVisibility(0);
        int round = Math.round(TypedValue.applyDimension(1, f2, getResources().getDisplayMetrics()));
        imageView.setPadding(round, 0, round, 0);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(imageView, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat7.setInterpolator(interpolator);
        ofFloat7.setDuration(context.getResources().getInteger(i5));
        ofFloat7.setStartDelay(context.getResources().getInteger(i6));
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
        ofFloat8.setDuration(integer2);
        ofFloat8.setInterpolator(interpolator);
        ofFloat8.setStartDelay(integer);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(ofFloat7, ofFloat8);
        animatorSet.start();
        animatorSet3.start();
        animatorSet4.start();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mRippleView != null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = measuredWidth;
            float f2 = measuredHeight;
            this.mRippleView.setCenter(f * 0.5f, 0.5f * f2);
            if (this.mRippleView.getRippleShape() == RippleShader.RippleShape.ROUNDED_BOX) {
                this.mRippleView.setMaxSize(f * 1.36f, f2 * 1.46f);
            } else {
                float max = Math.max(measuredWidth, measuredHeight);
                this.mRippleView.setMaxSize(max, max);
            }
        }
        super.onLayout(z, i, i2, i3, i4);
    }
}