package com.android.systemui.qs;

import android.os.Bundle;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.systemui.R$id;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoMode;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.qs.carrier.QSCarrierGroup;
import com.android.systemui.qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarLocation;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import com.android.systemui.util.ViewController;
import java.util.List;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickStatusBarHeaderController.class */
public class QuickStatusBarHeaderController extends ViewController<QuickStatusBarHeader> implements ChipVisibilityListener {
    public final BatteryMeterViewController mBatteryMeterViewController;
    public final Clock mClockView;
    public SysuiColorExtractor mColorExtractor;
    public final DemoModeController mDemoModeController;
    public final DemoMode mDemoModeReceiver;
    public final FeatureFlags mFeatureFlags;
    public final StatusIconContainer mIconContainer;
    public final StatusBarIconController.TintedIconManager mIconManager;
    public final StatusBarContentInsetsProvider mInsetsProvider;
    public boolean mListening;
    public ColorExtractor.OnColorsChangedListener mOnColorsChangedListener;
    public final HeaderPrivacyIconsController mPrivacyIconsController;
    public final QSCarrierGroupController mQSCarrierGroupController;
    public final QSExpansionPathInterpolator mQSExpansionPathInterpolator;
    public final QuickQSPanelController mQuickQSPanelController;
    public final StatusBarIconController mStatusBarIconController;
    public final VariableDateViewController mVariableDateViewControllerClockDateView;
    public final VariableDateViewController mVariableDateViewControllerDateView;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickStatusBarHeaderController$ClockDemoModeReceiver.class */
    public static class ClockDemoModeReceiver implements DemoMode {
        public Clock mClockView;

        public ClockDemoModeReceiver(Clock clock) {
            this.mClockView = clock;
        }

        @Override // com.android.systemui.demomode.DemoMode
        public List<String> demoCommands() {
            return List.of("clock");
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void dispatchDemoCommand(String str, Bundle bundle) {
            this.mClockView.dispatchDemoCommand(str, bundle);
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void onDemoModeFinished() {
            this.mClockView.onDemoModeFinished();
        }

        @Override // com.android.systemui.demomode.DemoModeCommandReceiver
        public void onDemoModeStarted() {
            this.mClockView.onDemoModeStarted();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QuickStatusBarHeaderController$$ExternalSyntheticLambda0.onColorsChanged(com.android.internal.colorextraction.ColorExtractor, int):void] */
    public static /* synthetic */ void $r8$lambda$cFqUFu8nFqfUEvSWLdscueAdKd4(QuickStatusBarHeaderController quickStatusBarHeaderController, ColorExtractor colorExtractor, int i) {
        quickStatusBarHeaderController.lambda$new$0(colorExtractor, i);
    }

    public QuickStatusBarHeaderController(QuickStatusBarHeader quickStatusBarHeader, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarIconController statusBarIconController, DemoModeController demoModeController, QuickQSPanelController quickQSPanelController, QSCarrierGroupController.Builder builder, SysuiColorExtractor sysuiColorExtractor, QSExpansionPathInterpolator qSExpansionPathInterpolator, FeatureFlags featureFlags, VariableDateViewController.Factory factory, BatteryMeterViewController batteryMeterViewController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, StatusBarIconController.TintedIconManager.Factory factory2) {
        super(quickStatusBarHeader);
        this.mPrivacyIconsController = headerPrivacyIconsController;
        this.mStatusBarIconController = statusBarIconController;
        this.mDemoModeController = demoModeController;
        this.mQuickQSPanelController = quickQSPanelController;
        this.mQSExpansionPathInterpolator = qSExpansionPathInterpolator;
        this.mFeatureFlags = featureFlags;
        this.mBatteryMeterViewController = batteryMeterViewController;
        this.mInsetsProvider = statusBarContentInsetsProvider;
        this.mQSCarrierGroupController = builder.setQSCarrierGroup((QSCarrierGroup) ((QuickStatusBarHeader) ((ViewController) this).mView).findViewById(R$id.carrier_group)).build();
        Clock findViewById = ((QuickStatusBarHeader) ((ViewController) this).mView).findViewById(R$id.clock);
        this.mClockView = findViewById;
        StatusIconContainer findViewById2 = ((QuickStatusBarHeader) ((ViewController) this).mView).findViewById(R$id.statusIcons);
        this.mIconContainer = findViewById2;
        this.mVariableDateViewControllerDateView = factory.create(((QuickStatusBarHeader) ((ViewController) this).mView).requireViewById(R$id.date));
        this.mVariableDateViewControllerClockDateView = factory.create(((QuickStatusBarHeader) ((ViewController) this).mView).requireViewById(R$id.date_clock));
        this.mIconManager = factory2.create(findViewById2, StatusBarLocation.QS);
        this.mDemoModeReceiver = new ClockDemoModeReceiver(findViewById);
        this.mColorExtractor = sysuiColorExtractor;
        ColorExtractor.OnColorsChangedListener onColorsChangedListener = new ColorExtractor.OnColorsChangedListener() { // from class: com.android.systemui.qs.QuickStatusBarHeaderController$$ExternalSyntheticLambda0
            public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
                QuickStatusBarHeaderController.$r8$lambda$cFqUFu8nFqfUEvSWLdscueAdKd4(QuickStatusBarHeaderController.this, colorExtractor, i);
            }
        };
        this.mOnColorsChangedListener = onColorsChangedListener;
        this.mColorExtractor.addOnColorsChangedListener(onColorsChangedListener);
        batteryMeterViewController.ignoreTunerUpdates();
    }

    public /* synthetic */ void lambda$new$0(ColorExtractor colorExtractor, int i) {
        this.mClockView.onColorsChanged(this.mColorExtractor.getNeutralColors().supportsDarkText());
    }

    @Override // com.android.systemui.qs.ChipVisibilityListener
    public void onChipVisibilityRefreshed(boolean z) {
        ((QuickStatusBarHeader) ((ViewController) this).mView).setChipVisibility(z);
    }

    public void onInit() {
        this.mBatteryMeterViewController.init();
    }

    public void onViewAttached() {
        this.mPrivacyIconsController.onParentVisible();
        this.mPrivacyIconsController.setChipVisibilityListener(this);
        this.mIconContainer.addIgnoredSlot(getResources().getString(17041603));
        this.mIconContainer.setShouldRestrictIcons(false);
        this.mStatusBarIconController.addIconGroup(this.mIconManager);
        ((QuickStatusBarHeader) ((ViewController) this).mView).setIsSingleCarrier(this.mQSCarrierGroupController.isSingleCarrier());
        QSCarrierGroupController qSCarrierGroupController = this.mQSCarrierGroupController;
        final QuickStatusBarHeader quickStatusBarHeader = (QuickStatusBarHeader) ((ViewController) this).mView;
        Objects.requireNonNull(quickStatusBarHeader);
        qSCarrierGroupController.setOnSingleCarrierChangedListener(new QSCarrierGroupController.OnSingleCarrierChangedListener() { // from class: com.android.systemui.qs.QuickStatusBarHeaderController$$ExternalSyntheticLambda1
            @Override // com.android.systemui.qs.carrier.QSCarrierGroupController.OnSingleCarrierChangedListener
            public final void onSingleCarrierChanged(boolean z) {
                QuickStatusBarHeader.this.setIsSingleCarrier(z);
            }
        });
        ((QuickStatusBarHeader) ((ViewController) this).mView).onAttach(this.mIconManager, this.mQSExpansionPathInterpolator, List.of(getResources().getString(17041605)), this.mInsetsProvider, this.mFeatureFlags.isEnabled(Flags.COMBINED_QS_HEADERS));
        this.mDemoModeController.addCallback(this.mDemoModeReceiver);
        this.mVariableDateViewControllerDateView.init();
        this.mVariableDateViewControllerClockDateView.init();
    }

    public void onViewDetached() {
        this.mColorExtractor.removeOnColorsChangedListener(this.mOnColorsChangedListener);
        this.mPrivacyIconsController.onParentInvisible();
        this.mStatusBarIconController.removeIconGroup(this.mIconManager);
        this.mQSCarrierGroupController.setOnSingleCarrierChangedListener(null);
        this.mDemoModeController.removeCallback(this.mDemoModeReceiver);
        setListening(false);
    }

    public void setContentMargins(int i, int i2) {
        this.mQuickQSPanelController.setContentMargins(i, i2);
    }

    public void setListening(boolean z) {
        this.mQSCarrierGroupController.setListening(z);
        if (z == this.mListening) {
            return;
        }
        this.mListening = z;
        this.mQuickQSPanelController.setListening(z);
        if (this.mQuickQSPanelController.switchTileLayout(false)) {
            ((QuickStatusBarHeader) ((ViewController) this).mView).updateResources();
        }
        if (z) {
            this.mPrivacyIconsController.startListening();
        } else {
            this.mPrivacyIconsController.stopListening();
        }
    }
}