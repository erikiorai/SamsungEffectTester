package com.android.systemui.battery;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.settingslib.graph.CircleBatteryDrawable;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$styleable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.policy.BatteryController;
import java.text.NumberFormat;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/battery/BatteryMeterView.class */
public class BatteryMeterView extends LinearLayout implements DarkIconDispatcher.DarkReceiver {
    public final AccessorizedBatteryDrawable mAccessorizedDrawable;
    public BatteryEstimateFetcher mBatteryEstimateFetcher;
    public final ImageView mBatteryIconView;
    public TextView mBatteryPercentView;
    public boolean mBatteryStateUnknown;
    public boolean mCharging;
    public final CircleBatteryDrawable mCircleDrawable;
    public boolean mDisplayShieldEnabled;
    public DualToneHandler mDualToneHandler;
    public String mEstimateText;
    public boolean mIsOverheated;
    public int mLevel;
    public int mNonAdaptedBackgroundColor;
    public int mNonAdaptedForegroundColor;
    public int mNonAdaptedSingleToneColor;
    public final int mPercentageStyleId;
    public int mShowPercentMode;
    public int mTextColor;
    public Drawable mUnknownStateDrawable;

    /* loaded from: mainsysui33.jar:com/android/systemui/battery/BatteryMeterView$BatteryEstimateFetcher.class */
    public interface BatteryEstimateFetcher {
        void fetchBatteryTimeRemainingEstimate(BatteryController.EstimateFetchCompletion estimateFetchCompletion);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0.onBatteryRemainingEstimateRetrieved(java.lang.String):void] */
    /* renamed from: $r8$lambda$Fr7tCs3K1JnPKi_am1jB-OSiCe0 */
    public static /* synthetic */ void m1490$r8$lambda$Fr7tCs3K1JnPKi_am1jBOSiCe0(BatteryMeterView batteryMeterView, String str) {
        batteryMeterView.lambda$updatePercentText$0(str);
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BatteryMeterView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowPercentMode = 0;
        this.mEstimateText = null;
        setOrientation(0);
        setGravity(8388627);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BatteryMeterView, i, 0);
        int color = obtainStyledAttributes.getColor(R$styleable.BatteryMeterView_frameColor, context.getColor(R$color.meter_background_color));
        this.mPercentageStyleId = obtainStyledAttributes.getResourceId(R$styleable.BatteryMeterView_textAppearance, 0);
        this.mAccessorizedDrawable = new AccessorizedBatteryDrawable(context, color);
        this.mCircleDrawable = new CircleBatteryDrawable(context, color);
        obtainStyledAttributes.recycle();
        setupLayoutTransition();
        ImageView imageView = new ImageView(context);
        this.mBatteryIconView = imageView;
        updateDrawable();
        ViewGroup.MarginLayoutParams marginLayoutParams = new ViewGroup.MarginLayoutParams(getBatteryStyle() == 0 ? getResources().getDimensionPixelSize(R$dimen.status_bar_battery_icon_width) : getResources().getDimensionPixelSize(R$dimen.status_bar_battery_icon_circle_width), getResources().getDimensionPixelSize(R$dimen.status_bar_battery_icon_height));
        marginLayoutParams.setMargins(0, 0, 0, getResources().getDimensionPixelOffset(R$dimen.battery_margin_bottom));
        addView(imageView, marginLayoutParams);
        updateShowPercent();
        this.mDualToneHandler = new DualToneHandler(context);
        onDarkChanged(new ArrayList<>(), ActionBarShadowController.ELEVATION_LOW, -1);
        setClipChildren(false);
        setClipToPadding(false);
    }

    public /* synthetic */ void lambda$updatePercentText$0(String str) {
        TextView textView = this.mBatteryPercentView;
        if (textView == null) {
            return;
        }
        if (str == null || this.mShowPercentMode != 3) {
            setPercentTextAtCurrentLevel();
            return;
        }
        this.mEstimateText = str;
        textView.setText(str);
        updateContentDescription();
    }

    public CharSequence getBatteryPercentViewText() {
        return this.mBatteryPercentView.getText();
    }

    public final int getBatteryStyle() {
        return Settings.System.getIntForUser(getContext().getContentResolver(), "status_bar_battery_style", 0, -2);
    }

    public final Drawable getUnknownStateDrawable() {
        if (this.mUnknownStateDrawable == null) {
            Drawable drawable = ((LinearLayout) this).mContext.getDrawable(R$drawable.ic_battery_unknown);
            this.mUnknownStateDrawable = drawable;
            drawable.setTint(this.mTextColor);
        }
        return this.mUnknownStateDrawable;
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final TextView loadPercentView() {
        return (TextView) LayoutInflater.from(getContext()).inflate(R$layout.battery_percentage_view, (ViewGroup) null);
    }

    public void onBatteryLevelChanged(int i, boolean z) {
        this.mAccessorizedDrawable.setCharging(z);
        this.mCircleDrawable.setCharging(z);
        this.mAccessorizedDrawable.setBatteryLevel(i);
        this.mCircleDrawable.setBatteryLevel(i);
        this.mCharging = z;
        this.mLevel = i;
        updatePercentText();
        if (z) {
            updateShowPercent();
        }
    }

    public void onBatteryUnknownStateChanged(boolean z) {
        if (this.mBatteryStateUnknown == z) {
            return;
        }
        this.mBatteryStateUnknown = z;
        updateContentDescription();
        if (this.mBatteryStateUnknown) {
            this.mBatteryIconView.setImageDrawable(getUnknownStateDrawable());
        } else {
            updateDrawable();
        }
        updateShowPercent();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateBatteryStyle();
        this.mAccessorizedDrawable.notifyDensityChanged();
    }

    @Override // com.android.systemui.plugins.DarkIconDispatcher.DarkReceiver
    public void onDarkChanged(ArrayList<Rect> arrayList, float f, int i) {
        if (!DarkIconDispatcher.isInAreas(arrayList, this)) {
            f = 0.0f;
        }
        this.mNonAdaptedSingleToneColor = this.mDualToneHandler.getSingleColor(f);
        this.mNonAdaptedForegroundColor = this.mDualToneHandler.getFillColor(f);
        int backgroundColor = this.mDualToneHandler.getBackgroundColor(f);
        this.mNonAdaptedBackgroundColor = backgroundColor;
        updateColors(this.mNonAdaptedForegroundColor, backgroundColor, this.mNonAdaptedSingleToneColor);
    }

    public void onIsOverheatedChanged(boolean z) {
        boolean z2 = this.mIsOverheated != z;
        this.mIsOverheated = z;
        if (z2) {
            updateContentDescription();
            scaleBatteryMeterViews();
        }
    }

    public void onPowerSaveChanged(boolean z) {
        this.mAccessorizedDrawable.setPowerSaveEnabled(z);
        this.mCircleDrawable.setPowerSaveEnabled(z);
    }

    public void scaleBatteryMeterViews() {
        Resources resources = getContext().getResources();
        TypedValue typedValue = new TypedValue();
        boolean z = true;
        resources.getValue(R$dimen.status_bar_icon_scale_factor, typedValue, true);
        float f = typedValue.getFloat();
        int dimensionPixelSize = resources.getDimensionPixelSize(R$dimen.status_bar_battery_icon_height);
        int dimensionPixelSize2 = getBatteryStyle() == 1 ? resources.getDimensionPixelSize(R$dimen.status_bar_battery_icon_circle_width) : resources.getDimensionPixelSize(R$dimen.status_bar_battery_icon_width);
        float f2 = dimensionPixelSize * f;
        float f3 = dimensionPixelSize2;
        if (!this.mDisplayShieldEnabled || !this.mIsOverheated) {
            z = false;
        }
        float fullBatteryHeight = BatterySpecs.getFullBatteryHeight(f2, z);
        float fullBatteryWidth = BatterySpecs.getFullBatteryWidth(f3 * f, z);
        int round = z ? Math.round(fullBatteryHeight - f2) - resources.getDimensionPixelSize(R$dimen.status_bar_battery_extra_vertical_spacing) : 0;
        int dimensionPixelSize3 = resources.getDimensionPixelSize(R$dimen.battery_margin_bottom);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(fullBatteryWidth), Math.round(fullBatteryHeight));
        layoutParams.setMargins(0, round, 0, dimensionPixelSize3);
        this.mAccessorizedDrawable.setDisplayShield(z);
        this.mBatteryIconView.setLayoutParams(layoutParams);
        this.mBatteryIconView.invalidateDrawable(this.mAccessorizedDrawable);
    }

    public void setBatteryEstimateFetcher(BatteryEstimateFetcher batteryEstimateFetcher) {
        this.mBatteryEstimateFetcher = batteryEstimateFetcher;
    }

    public void setColorsFromContext(Context context) {
        if (context == null) {
            return;
        }
        this.mDualToneHandler.setColorsFromContext(context);
    }

    public void setDisplayShieldEnabled(boolean z) {
        this.mDisplayShieldEnabled = z;
    }

    public void setForceShowPercent(boolean z) {
        setPercentShowMode(z ? 1 : 0);
    }

    public void setPercentShowMode(int i) {
        if (i == this.mShowPercentMode) {
            return;
        }
        this.mShowPercentMode = i;
        updateShowPercent();
        updatePercentText();
    }

    public final void setPercentTextAtCurrentLevel() {
        if (this.mBatteryPercentView != null) {
            this.mEstimateText = null;
            String format = NumberFormat.getPercentInstance().format(this.mLevel / 100.0f);
            if (!TextUtils.equals(this.mBatteryPercentView.getText(), format)) {
                this.mBatteryPercentView.setText(format);
            }
        }
        updateContentDescription();
    }

    public final void setupLayoutTransition() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.setDuration(200L);
        layoutTransition.setAnimator(2, ObjectAnimator.ofFloat((Object) null, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f));
        layoutTransition.setInterpolator(2, Interpolators.ALPHA_IN);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) null, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW);
        layoutTransition.setInterpolator(3, Interpolators.ALPHA_OUT);
        layoutTransition.setAnimator(3, ofFloat);
        layoutTransition.setAnimator(0, null);
        layoutTransition.setAnimator(1, null);
        layoutTransition.setAnimator(4, null);
        setLayoutTransition(layoutTransition);
    }

    public void updateBatteryStyle() {
        updateDrawable();
        scaleBatteryMeterViews();
        updatePercentView();
    }

    public void updateColors(int i, int i2, int i3) {
        this.mAccessorizedDrawable.setColors(i, i2, i3);
        this.mCircleDrawable.setColors(i, i2, i3);
        this.mTextColor = i3;
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            textView.setTextColor(i3);
        }
        Drawable drawable = this.mUnknownStateDrawable;
        if (drawable != null) {
            drawable.setTint(i3);
        }
    }

    public final void updateContentDescription() {
        String string;
        Context context = getContext();
        if (this.mBatteryStateUnknown) {
            string = context.getString(R$string.accessibility_battery_unknown);
        } else if (this.mShowPercentMode != 3 || TextUtils.isEmpty(this.mEstimateText)) {
            string = this.mIsOverheated ? context.getString(R$string.accessibility_battery_level_charging_paused, Integer.valueOf(this.mLevel)) : this.mCharging ? context.getString(R$string.accessibility_battery_level_charging, Integer.valueOf(this.mLevel)) : context.getString(R$string.accessibility_battery_level, Integer.valueOf(this.mLevel));
        } else {
            string = context.getString(this.mIsOverheated ? R$string.accessibility_battery_level_charging_paused_with_estimate : R$string.accessibility_battery_level_with_estimate, Integer.valueOf(this.mLevel), this.mEstimateText);
        }
        setContentDescription(string);
    }

    public final void updateDrawable() {
        int batteryStyle = getBatteryStyle();
        if (batteryStyle == 0) {
            this.mBatteryIconView.setImageDrawable(this.mAccessorizedDrawable);
            this.mBatteryIconView.setVisibility(0);
        } else if (batteryStyle == 1) {
            this.mBatteryIconView.setImageDrawable(this.mCircleDrawable);
            this.mBatteryIconView.setVisibility(0);
        } else if (batteryStyle != 2) {
        } else {
            this.mBatteryIconView.setVisibility(8);
            this.mBatteryIconView.setImageDrawable(null);
        }
    }

    public void updatePercentText() {
        if (this.mBatteryStateUnknown) {
            return;
        }
        BatteryEstimateFetcher batteryEstimateFetcher = this.mBatteryEstimateFetcher;
        if (batteryEstimateFetcher == null) {
            setPercentTextAtCurrentLevel();
        } else if (this.mBatteryPercentView == null) {
            updateContentDescription();
        } else if (this.mShowPercentMode != 3 || this.mCharging) {
            setPercentTextAtCurrentLevel();
        } else {
            batteryEstimateFetcher.fetchBatteryTimeRemainingEstimate(new BatteryController.EstimateFetchCompletion() { // from class: com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0
                public final void onBatteryRemainingEstimateRetrieved(String str) {
                    BatteryMeterView.m1490$r8$lambda$Fr7tCs3K1JnPKi_am1jBOSiCe0(BatteryMeterView.this, str);
                }
            });
        }
    }

    public void updatePercentView() {
        TextView textView = this.mBatteryPercentView;
        if (textView != null) {
            removeView(textView);
            this.mBatteryPercentView = null;
        }
        updateShowPercent();
    }

    public void updateShowPercent() {
        boolean z = this.mBatteryPercentView != null;
        int intForUser = Settings.System.getIntForUser(getContext().getContentResolver(), "status_bar_show_battery_percent", 0, -2);
        int i = this.mShowPercentMode;
        boolean z2 = i == 0 && intForUser == 1;
        if (!((((i == 3 || intForUser == 2) && (!z2 || this.mCharging)) || getBatteryStyle() == 2) && !this.mBatteryStateUnknown)) {
            this.mAccessorizedDrawable.showPercent(z2);
            this.mCircleDrawable.setShowPercent(z2);
            if (z) {
                removeView(this.mBatteryPercentView);
                this.mBatteryPercentView = null;
                return;
            }
            return;
        }
        this.mAccessorizedDrawable.showPercent(false);
        this.mCircleDrawable.setShowPercent(false);
        if (!z) {
            TextView loadPercentView = loadPercentView();
            this.mBatteryPercentView = loadPercentView;
            int i2 = this.mPercentageStyleId;
            if (i2 != 0) {
                loadPercentView.setTextAppearance(i2);
            }
            int i3 = this.mTextColor;
            if (i3 != 0) {
                this.mBatteryPercentView.setTextColor(i3);
            }
            updatePercentText();
            addView(this.mBatteryPercentView, new ViewGroup.LayoutParams(-2, -1));
        }
        if (getBatteryStyle() == 2) {
            this.mBatteryPercentView.setPaddingRelative(0, 0, 0, 0);
        } else {
            this.mBatteryPercentView.setPaddingRelative(getContext().getResources().getDimensionPixelSize(R$dimen.battery_level_padding_start), 0, 0, 0);
        }
    }
}