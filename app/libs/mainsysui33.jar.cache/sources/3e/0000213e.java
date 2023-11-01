package com.android.systemui.qs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import com.android.internal.policy.SystemBarUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.R$bool;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.TouchAnimator;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.VariableDateView;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.LargeScreenUtils;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickStatusBarHeader.class */
public class QuickStatusBarHeader extends FrameLayout implements TunerService.Tunable {
    public final ActivityStarter mActivityStarter;
    public TouchAnimator mAlphaAnimator;
    public BatteryMeterView mBatteryRemainingIcon;
    public ViewGroup mClockContainer;
    public VariableDateView mClockDateView;
    public View mClockIconsSeparator;
    public Clock mClockView;
    public boolean mConfigShowBatteryEstimate;
    public View mContainer;
    public int mCutOutPaddingLeft;
    public int mCutOutPaddingRight;
    public View mDateContainer;
    public Space mDatePrivacySeparator;
    public View mDatePrivacyView;
    public View mDateView;
    public boolean mExpanded;
    public boolean mHasCenterCutout;
    public QuickQSPanel mHeaderQsPanel;
    public StatusIconContainer mIconContainer;
    public TouchAnimator mIconsAlphaAnimator;
    public TouchAnimator mIconsAlphaAnimatorFixed;
    public StatusBarContentInsetsProvider mInsetsProvider;
    public boolean mIsSingleCarrier;
    public float mKeyguardExpansionFraction;
    public View mPrivacyChip;
    public View mPrivacyContainer;
    public View mQSCarriers;
    public QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    public boolean mQsDisabled;
    public View mRightLayout;
    public int mRoundedCornerPadding;
    public List<String> mRssiIgnoredSlots;
    public boolean mShowClockIconsSeparator;
    public int mStatusBarPaddingTop;
    public View mStatusIconsView;
    public int mTextColorPrimary;
    public StatusBarIconController.TintedIconManager mTintedIconManager;
    public int mTopViewMeasureHeight;
    public TouchAnimator mTranslationAnimator;
    public boolean mUseCombinedQSHeader;
    public int mWaterfallTopInset;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$5iY7OvdvqQZzZKLQ956waMXDU1M(QuickStatusBarHeader quickStatusBarHeader) {
        quickStatusBarHeader.updateAnimators();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$PXMdyxp9ErVeXLNv1CCVwhVh6ws(QuickStatusBarHeader quickStatusBarHeader, View view) {
        quickStatusBarHeader.lambda$onFinishInflate$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda3.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$ZLXnNMx8KR_BoShRsrntyGNo_hg(QuickStatusBarHeader quickStatusBarHeader, View view) {
        quickStatusBarHeader.lambda$setBatteryRemainingOnClick$2(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$mdRTvmaIqXq8W4LTSVO_mcCmO7g(QuickStatusBarHeader quickStatusBarHeader) {
        quickStatusBarHeader.lambda$updateEverything$1();
    }

    public QuickStatusBarHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRoundedCornerPadding = 0;
        this.mTextColorPrimary = 0;
        this.mRssiIgnoredSlots = List.of();
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
    }

    public /* synthetic */ void lambda$onFinishInflate$0(View view) {
        this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.intent.action.SHOW_ALARMS"), 0);
    }

    public /* synthetic */ void lambda$setBatteryRemainingOnClick$2(View view) {
        this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.intent.action.POWER_USAGE_SUMMARY"), 0);
    }

    public /* synthetic */ void lambda$updateEverything$1() {
        setClickable(!this.mExpanded);
    }

    public void disable(int i, int i2, boolean z) {
        boolean z2 = true;
        int i3 = 0;
        if ((i2 & 1) == 0) {
            z2 = false;
        }
        if (z2 == this.mQsDisabled) {
            return;
        }
        this.mQsDisabled = z2;
        this.mHeaderQsPanel.setDisabledByPolicy(z2);
        View view = this.mStatusIconsView;
        if (this.mQsDisabled) {
            i3 = 8;
        }
        view.setVisibility(i3);
        updateResources();
    }

    public int getOffsetTranslation() {
        return this.mTopViewMeasureHeight;
    }

    @Override // android.view.View
    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        Pair statusBarContentInsetsForCurrentRotation = this.mInsetsProvider.getStatusBarContentInsetsForCurrentRotation();
        boolean currentRotationHasCornerCutout = this.mInsetsProvider.currentRotationHasCornerCutout();
        int i = 0;
        this.mDatePrivacyView.setPadding(((Integer) statusBarContentInsetsForCurrentRotation.first).intValue(), 0, ((Integer) statusBarContentInsetsForCurrentRotation.second).intValue(), 0);
        this.mStatusIconsView.setPadding(((Integer) statusBarContentInsetsForCurrentRotation.first).intValue(), 0, ((Integer) statusBarContentInsetsForCurrentRotation.second).intValue(), 0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDatePrivacySeparator.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mClockIconsSeparator.getLayoutParams();
        if (displayCutout != null) {
            Rect boundingRectTop = displayCutout.getBoundingRectTop();
            if (boundingRectTop.isEmpty() || currentRotationHasCornerCutout) {
                layoutParams.width = 0;
                this.mDatePrivacySeparator.setVisibility(8);
                layoutParams2.width = 0;
                setSeparatorVisibility(false);
                this.mShowClockIconsSeparator = false;
                this.mHasCenterCutout = false;
            } else {
                layoutParams.width = boundingRectTop.width();
                this.mDatePrivacySeparator.setVisibility(0);
                layoutParams2.width = boundingRectTop.width();
                this.mShowClockIconsSeparator = true;
                setSeparatorVisibility(this.mKeyguardExpansionFraction == ActionBarShadowController.ELEVATION_LOW);
                this.mHasCenterCutout = true;
            }
        }
        this.mDatePrivacySeparator.setLayoutParams(layoutParams);
        this.mClockIconsSeparator.setLayoutParams(layoutParams2);
        this.mCutOutPaddingLeft = ((Integer) statusBarContentInsetsForCurrentRotation.first).intValue();
        this.mCutOutPaddingRight = ((Integer) statusBarContentInsetsForCurrentRotation.second).intValue();
        if (displayCutout != null) {
            i = displayCutout.getWaterfallInsets().top;
        }
        this.mWaterfallTopInset = i;
        updateBatteryMode();
        updateHeadersPadding();
        return super.onApplyWindowInsets(windowInsets);
    }

    public void onAttach(StatusBarIconController.TintedIconManager tintedIconManager, QSExpansionPathInterpolator qSExpansionPathInterpolator, List<String> list, StatusBarContentInsetsProvider statusBarContentInsetsProvider, boolean z) {
        this.mUseCombinedQSHeader = z;
        this.mTintedIconManager = tintedIconManager;
        this.mRssiIgnoredSlots = list;
        this.mInsetsProvider = statusBarContentInsetsProvider;
        tintedIconManager.setTint(Utils.getColorAttrDefaultColor(getContext(), 16842806));
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        updateAnimators();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
        setDatePrivacyContainersWidth(configuration.orientation == 2);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderQsPanel = (QuickQSPanel) findViewById(R$id.quick_qs_panel);
        this.mDatePrivacyView = findViewById(R$id.quick_status_bar_date_privacy);
        this.mStatusIconsView = findViewById(R$id.quick_qs_status_icons);
        this.mQSCarriers = findViewById(R$id.carrier_group);
        this.mContainer = findViewById(R$id.qs_container);
        this.mIconContainer = findViewById(R$id.statusIcons);
        this.mPrivacyChip = findViewById(R$id.privacy_chip);
        this.mDateView = findViewById(R$id.date);
        this.mClockDateView = findViewById(R$id.date_clock);
        this.mClockIconsSeparator = findViewById(R$id.separator);
        this.mRightLayout = findViewById(R$id.rightLayout);
        this.mDateContainer = findViewById(R$id.date_container);
        this.mPrivacyContainer = findViewById(R$id.privacy_container);
        this.mClockContainer = (ViewGroup) findViewById(R$id.clock_container);
        Clock findViewById = findViewById(R$id.clock);
        this.mClockView = findViewById;
        findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QuickStatusBarHeader.$r8$lambda$PXMdyxp9ErVeXLNv1CCVwhVh6ws(QuickStatusBarHeader.this, view);
            }
        });
        this.mDatePrivacySeparator = (Space) findViewById(R$id.space);
        this.mBatteryRemainingIcon = (BatteryMeterView) findViewById(R$id.batteryRemainingIcon);
        updateResources();
        setDatePrivacyContainersWidth(((FrameLayout) this).mContext.getResources().getConfiguration().orientation == 2);
        this.mBatteryRemainingIcon.setPercentShowMode(3);
        this.mIconsAlphaAnimatorFixed = new TouchAnimator.Builder().addFloat(this.mIconContainer, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).addFloat(this.mBatteryRemainingIcon, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).build();
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"icon_blacklist"});
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mDatePrivacyView.getMeasuredHeight() != this.mTopViewMeasureHeight) {
            this.mTopViewMeasureHeight = this.mDatePrivacyView.getMeasuredHeight();
            post(new Runnable() { // from class: com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    QuickStatusBarHeader.$r8$lambda$5iY7OvdvqQZzZKLQ956waMXDU1M(QuickStatusBarHeader.this);
                }
            });
        }
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateResources();
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mUseCombinedQSHeader || motionEvent.getY() > this.mHeaderQsPanel.getTop()) {
            return super.onTouchEvent(motionEvent);
        }
        return false;
    }

    public void onTuningChanged(String str, String str2) {
        this.mClockView.setClockVisibleByUser(!StatusBarIconController.getIconHideList(((FrameLayout) this).mContext, str2).contains("clock"));
    }

    public final void setBatteryRemainingOnClick(boolean z) {
        if (z) {
            this.mBatteryRemainingIcon.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda3
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    QuickStatusBarHeader.$r8$lambda$ZLXnNMx8KR_BoShRsrntyGNo_hg(QuickStatusBarHeader.this, view);
                }
            });
            this.mBatteryRemainingIcon.setClickable(true);
            return;
        }
        this.mBatteryRemainingIcon.setOnClickListener(null);
        this.mBatteryRemainingIcon.setClickable(false);
    }

    public void setChipVisibility(boolean z) {
        if (z) {
            TouchAnimator touchAnimator = this.mIconsAlphaAnimatorFixed;
            this.mIconsAlphaAnimator = touchAnimator;
            touchAnimator.setPosition(this.mKeyguardExpansionFraction);
            setBatteryRemainingOnClick(false);
            return;
        }
        this.mIconsAlphaAnimator = null;
        this.mIconContainer.setAlpha(1.0f);
        this.mBatteryRemainingIcon.setAlpha(1.0f);
        setBatteryRemainingOnClick(true);
    }

    public final void setContentMargins(View view, int i, int i2) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        marginLayoutParams.setMarginStart(i);
        marginLayoutParams.setMarginEnd(i2);
        view.setLayoutParams(marginLayoutParams);
    }

    public final void setDatePrivacyContainersWidth(boolean z) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDateContainer.getLayoutParams();
        layoutParams.width = z ? -2 : 0;
        layoutParams.weight = z ? 0.0f : 1.0f;
        this.mDateContainer.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mPrivacyContainer.getLayoutParams();
        layoutParams2.width = z ? -2 : 0;
        layoutParams2.weight = z ? 0.0f : 1.0f;
        this.mPrivacyContainer.setLayoutParams(layoutParams2);
    }

    public void setExpanded(boolean z, QuickQSPanelController quickQSPanelController) {
        if (this.mExpanded == z) {
            return;
        }
        this.mExpanded = z;
        quickQSPanelController.setExpanded(z);
        updateEverything();
    }

    public void setExpandedScrollAmount(int i) {
        this.mStatusIconsView.setScrollY(i);
        this.mDatePrivacyView.setScrollY(i);
    }

    public void setExpansion(boolean z, float f, float f2) {
        float f3 = z ? 1.0f : f;
        TouchAnimator touchAnimator = this.mAlphaAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f3);
        }
        TouchAnimator touchAnimator2 = this.mTranslationAnimator;
        if (touchAnimator2 != null) {
            touchAnimator2.setPosition(f3);
        }
        TouchAnimator touchAnimator3 = this.mIconsAlphaAnimator;
        if (touchAnimator3 != null) {
            touchAnimator3.setPosition(f3);
        }
        if (z) {
            setAlpha(f);
        } else {
            setAlpha(1.0f);
        }
        this.mKeyguardExpansionFraction = f3;
    }

    public void setIsSingleCarrier(boolean z) {
        this.mIsSingleCarrier = z;
        updateAlphaAnimator();
    }

    public final void setSeparatorVisibility(boolean z) {
        if (this.mClockIconsSeparator.getVisibility() == (z ? 0 : 8)) {
            return;
        }
        this.mClockIconsSeparator.setVisibility(z ? 0 : 8);
        this.mQSCarriers.setVisibility(z ? 8 : 0);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        layoutParams.width = z ? 0 : -2;
        layoutParams.weight = z ? 1.0f : 0.0f;
        this.mClockContainer.setLayoutParams(layoutParams);
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mRightLayout.getLayoutParams();
        layoutParams2.width = z ? 0 : -2;
        layoutParams2.weight = z ? 1.0f : 0.0f;
        this.mRightLayout.setLayoutParams(layoutParams2);
    }

    public final void updateAlphaAnimator() {
        if (this.mUseCombinedQSHeader) {
            this.mAlphaAnimator = null;
        } else {
            this.mAlphaAnimator = new TouchAnimator.Builder().addFloat(this.mDateView, "alpha", ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f).addFloat(this.mClockDateView, "alpha", 1.0f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW).addFloat(this.mQSCarriers, "alpha", ActionBarShadowController.ELEVATION_LOW, 1.0f).setListener(new TouchAnimator.ListenerAdapter() { // from class: com.android.systemui.qs.QuickStatusBarHeader.1
                {
                    QuickStatusBarHeader.this = this;
                }

                @Override // com.android.systemui.qs.TouchAnimator.ListenerAdapter, com.android.systemui.qs.TouchAnimator.Listener
                public void onAnimationAtEnd() {
                    super.onAnimationAtEnd();
                    if (!QuickStatusBarHeader.this.mIsSingleCarrier) {
                        QuickStatusBarHeader.this.mIconContainer.addIgnoredSlots(QuickStatusBarHeader.this.mRssiIgnoredSlots);
                    }
                    QuickStatusBarHeader.this.mClockDateView.setVisibility(8);
                }

                @Override // com.android.systemui.qs.TouchAnimator.ListenerAdapter, com.android.systemui.qs.TouchAnimator.Listener
                public void onAnimationAtStart() {
                    super.onAnimationAtStart();
                    QuickStatusBarHeader.this.mClockDateView.setFreezeSwitching(false);
                    QuickStatusBarHeader.this.mClockDateView.setVisibility(0);
                    QuickStatusBarHeader quickStatusBarHeader = QuickStatusBarHeader.this;
                    quickStatusBarHeader.setSeparatorVisibility(quickStatusBarHeader.mShowClockIconsSeparator);
                    QuickStatusBarHeader.this.mIconContainer.removeIgnoredSlots(QuickStatusBarHeader.this.mRssiIgnoredSlots);
                }

                @Override // com.android.systemui.qs.TouchAnimator.Listener
                public void onAnimationStarted() {
                    QuickStatusBarHeader.this.mClockDateView.setVisibility(0);
                    QuickStatusBarHeader.this.mClockDateView.setFreezeSwitching(true);
                    QuickStatusBarHeader.this.setSeparatorVisibility(false);
                    if (QuickStatusBarHeader.this.mIsSingleCarrier) {
                        return;
                    }
                    QuickStatusBarHeader.this.mIconContainer.addIgnoredSlots(QuickStatusBarHeader.this.mRssiIgnoredSlots);
                }
            }).build();
        }
    }

    public final void updateAnimators() {
        Interpolator interpolator = null;
        if (this.mUseCombinedQSHeader) {
            this.mTranslationAnimator = null;
            return;
        }
        updateAlphaAnimator();
        TouchAnimator.Builder addFloat = new TouchAnimator.Builder().addFloat(this.mContainer, "translationY", ActionBarShadowController.ELEVATION_LOW, this.mTopViewMeasureHeight);
        QSExpansionPathInterpolator qSExpansionPathInterpolator = this.mQSExpansionPathInterpolator;
        if (qSExpansionPathInterpolator != null) {
            interpolator = qSExpansionPathInterpolator.getYInterpolator();
        }
        this.mTranslationAnimator = addFloat.setInterpolator(interpolator).build();
    }

    public final void updateBatteryMode() {
        if (this.mConfigShowBatteryEstimate) {
            this.mBatteryRemainingIcon.setPercentShowMode(3);
        } else {
            this.mBatteryRemainingIcon.setPercentShowMode(1);
        }
    }

    public final void updateClockDatePadding() {
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.status_bar_left_clock_starting_padding);
        int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.status_bar_left_clock_end_padding);
        Clock clock = this.mClockView;
        clock.setPaddingRelative(dimensionPixelSize, clock.getPaddingTop(), dimensionPixelSize2, this.mClockView.getPaddingBottom());
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mClockDateView.getLayoutParams();
        marginLayoutParams.setMarginStart(dimensionPixelSize2);
        this.mClockDateView.setLayoutParams(marginLayoutParams);
    }

    public void updateEverything() {
        post(new Runnable() { // from class: com.android.systemui.qs.QuickStatusBarHeader$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                QuickStatusBarHeader.$r8$lambda$mdRTvmaIqXq8W4LTSVO_mcCmO7g(QuickStatusBarHeader.this);
            }
        });
        if (this.mExpanded) {
            setBatteryRemainingOnClick(true);
        }
    }

    public final void updateHeadersPadding() {
        setContentMargins(this.mDatePrivacyView, 0, 0);
        setContentMargins(this.mStatusIconsView, 0, 0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        int i = layoutParams.leftMargin;
        int i2 = layoutParams.rightMargin;
        int i3 = this.mCutOutPaddingLeft;
        int max = i3 > 0 ? Math.max(Math.max(i3, this.mRoundedCornerPadding) - i, 0) : 0;
        int i4 = this.mCutOutPaddingRight;
        int max2 = i4 > 0 ? Math.max(Math.max(i4, this.mRoundedCornerPadding) - i2, 0) : 0;
        this.mDatePrivacyView.setPadding(max, this.mStatusBarPaddingTop, max2, 0);
        this.mStatusIconsView.setPadding(max, this.mStatusBarPaddingTop, max2, 0);
    }

    public void updateResources() {
        Resources resources = ((FrameLayout) this).mContext.getResources();
        boolean shouldUseLargeScreenShadeHeader = LargeScreenUtils.shouldUseLargeScreenShadeHeader(resources);
        boolean z = shouldUseLargeScreenShadeHeader || this.mUseCombinedQSHeader || this.mQsDisabled;
        this.mStatusIconsView.setVisibility(z ? 8 : 0);
        this.mDatePrivacyView.setVisibility(z ? 8 : 0);
        this.mConfigShowBatteryEstimate = resources.getBoolean(R$bool.config_showBatteryEstimateQSBH);
        this.mRoundedCornerPadding = resources.getDimensionPixelSize(R$dimen.rounded_corner_content_padding);
        int statusBarHeight = SystemBarUtils.getStatusBarHeight(((FrameLayout) this).mContext);
        this.mStatusBarPaddingTop = resources.getDimensionPixelSize(R$dimen.status_bar_padding_top);
        this.mDatePrivacyView.getLayoutParams().height = statusBarHeight;
        View view = this.mDatePrivacyView;
        view.setLayoutParams(view.getLayoutParams());
        this.mStatusIconsView.getLayoutParams().height = statusBarHeight;
        View view2 = this.mStatusIconsView;
        view2.setLayoutParams(view2.getLayoutParams());
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (this.mQsDisabled) {
            layoutParams.height = this.mStatusIconsView.getLayoutParams().height - this.mWaterfallTopInset;
        } else {
            layoutParams.height = -2;
        }
        setLayoutParams(layoutParams);
        int colorAttrDefaultColor = Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, 16842806);
        if (colorAttrDefaultColor != this.mTextColorPrimary) {
            int colorAttrDefaultColor2 = Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, Settings.System.getIntForUser(((FrameLayout) this).mContext.getContentResolver(), "status_bar_battery_style", 0, -2) == 1 ? 16842906 : 16842808);
            this.mTextColorPrimary = colorAttrDefaultColor;
            this.mClockView.setTextColor(colorAttrDefaultColor);
            StatusBarIconController.TintedIconManager tintedIconManager = this.mTintedIconManager;
            if (tintedIconManager != null) {
                tintedIconManager.setTint(colorAttrDefaultColor);
            }
            BatteryMeterView batteryMeterView = this.mBatteryRemainingIcon;
            int i = this.mTextColorPrimary;
            batteryMeterView.updateColors(i, colorAttrDefaultColor2, i);
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mHeaderQsPanel.getLayoutParams();
        if (shouldUseLargeScreenShadeHeader) {
            marginLayoutParams.topMargin = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.qqs_layout_margin_top);
        } else if (this.mUseCombinedQSHeader) {
            marginLayoutParams.topMargin = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R$dimen.large_screen_shade_header_min_height);
        } else {
            marginLayoutParams.topMargin = SystemBarUtils.getQuickQsOffsetHeight(((FrameLayout) this).mContext);
        }
        this.mHeaderQsPanel.setLayoutParams(marginLayoutParams);
        updateBatteryMode();
        updateHeadersPadding();
        updateAnimators();
        updateClockDatePadding();
    }
}