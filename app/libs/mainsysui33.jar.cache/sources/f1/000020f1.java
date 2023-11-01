package com.android.systemui.qs;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.qs.PagedTileLayout;
import com.android.systemui.qs.QSTileRevealController;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessMirrorHandler;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.BrightnessMirrorController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSPanelController.class */
public class QSPanelController extends QSPanelControllerBase<QSPanel> {
    public final BrightnessController mBrightnessController;
    public final BrightnessMirrorHandler mBrightnessMirrorHandler;
    public final BrightnessSliderController mBrightnessSliderController;
    public final FalsingManager mFalsingManager;
    public final QSCustomizerController mQsCustomizerController;
    public final QSTileRevealController.Factory mQsTileRevealControllerFactory;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public View.OnTouchListener mTileLayoutTouchListener;
    public final TunerService mTunerService;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.QSPanelController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$pWrm8hTd7tMymTXvMd1IrptMZgU(QSPanelController qSPanelController, View view) {
        qSPanelController.lambda$showEdit$0(view);
    }

    public QSPanelController(QSPanel qSPanel, TunerService tunerService, QSTileHost qSTileHost, QSCustomizerController qSCustomizerController, boolean z, MediaHost mediaHost, QSTileRevealController.Factory factory, DumpManager dumpManager, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, BrightnessController.Factory factory2, BrightnessSliderController.Factory factory3, FalsingManager falsingManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, FeatureFlags featureFlags) {
        super(qSPanel, qSTileHost, qSCustomizerController, z, mediaHost, metricsLogger, uiEventLogger, qSLogger, dumpManager);
        this.mTileLayoutTouchListener = new View.OnTouchListener() { // from class: com.android.systemui.qs.QSPanelController.1
            {
                QSPanelController.this = this;
            }

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getActionMasked() == 1) {
                    QSPanelController.this.mFalsingManager.isFalseTouch(15);
                    return false;
                }
                return false;
            }
        };
        this.mTunerService = tunerService;
        this.mQsCustomizerController = qSCustomizerController;
        this.mQsTileRevealControllerFactory = factory;
        this.mFalsingManager = falsingManager;
        BrightnessSliderController create = factory3.create(getContext(), (ViewGroup) ((ViewController) this).mView);
        this.mBrightnessSliderController = create;
        ((QSPanel) ((ViewController) this).mView).setBrightnessView(create.getRootView());
        BrightnessController create2 = factory2.create(create);
        this.mBrightnessController = create2;
        this.mBrightnessMirrorHandler = new BrightnessMirrorHandler(create2);
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        ((QSPanel) ((ViewController) this).mView).setUsingCombinedHeaders(featureFlags.isEnabled(Flags.COMBINED_QS_HEADERS));
    }

    public /* synthetic */ void lambda$showEdit$0(View view) {
        if (this.mQsCustomizerController.isCustomizing()) {
            return;
        }
        int[] locationOnScreen = view.getLocationOnScreen();
        this.mQsCustomizerController.show(locationOnScreen[0] + (view.getWidth() / 2), locationOnScreen[1] + (view.getHeight() / 2), false);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public QSTileRevealController createTileRevealController() {
        return this.mQsTileRevealControllerFactory.create(this, (PagedTileLayout) ((QSPanel) ((ViewController) this).mView).getOrCreateTileLayout());
    }

    public QSTileHost getHost() {
        return this.mHost;
    }

    public int getPaddingBottom() {
        return ((QSPanel) ((ViewController) this).mView).getPaddingBottom();
    }

    public boolean isBouncerInTransit() {
        return this.mStatusBarKeyguardViewManager.isPrimaryBouncerInTransit();
    }

    public boolean isExpanded() {
        return ((QSPanel) ((ViewController) this).mView).isExpanded();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onConfigurationChanged() {
        ((QSPanel) ((ViewController) this).mView).updateResources();
        if (((QSPanel) ((ViewController) this).mView).isListening()) {
            refreshAllTiles();
        }
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onInit() {
        super.onInit();
        this.mMediaHost.setExpansion(1.0f);
        this.mMediaHost.setShowsOnlyActiveMedia(false);
        this.mMediaHost.init(0);
        this.mQsCustomizerController.init();
        this.mBrightnessSliderController.init();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onSplitShadeChanged() {
        ((PagedTileLayout) ((QSPanel) ((ViewController) this).mView).getOrCreateTileLayout()).forceTilesRedistribution("Split shade state changed");
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onViewAttached() {
        super.onViewAttached();
        updateMediaDisappearParameters();
        this.mTunerService.addTunable(((ViewController) this).mView, new String[]{"qs_show_brightness"});
        this.mTunerService.addTunable(((ViewController) this).mView, new String[]{"customsecure:qs_show_auto_brightness"});
        this.mTunerService.addTunable(((ViewController) this).mView, new String[]{"customsecure:qs_show_brightness_slider"});
        ((QSPanel) ((ViewController) this).mView).updateResources();
        if (((QSPanel) ((ViewController) this).mView).isListening()) {
            refreshAllTiles();
        }
        switchTileLayout(true);
        this.mBrightnessMirrorHandler.onQsPanelAttached();
        ((PagedTileLayout) ((QSPanel) ((ViewController) this).mView).getOrCreateTileLayout()).setOnTouchListener(this.mTileLayoutTouchListener);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onViewDetached() {
        this.mTunerService.removeTunable(((ViewController) this).mView);
        this.mBrightnessMirrorHandler.onQsPanelDettached();
        super.onViewDetached();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void refreshAllTiles() {
        this.mBrightnessController.checkRestrictionAndSetEnabled();
        super.refreshAllTiles();
    }

    public void setBrightnessMirror(BrightnessMirrorController brightnessMirrorController) {
        this.mBrightnessMirrorHandler.setController(brightnessMirrorController);
    }

    public void setContentMargins(int i, int i2) {
        ((QSPanel) ((ViewController) this).mView).setContentMargins(i, i2, this.mMediaHost.getHostView());
    }

    public void setFooterPageIndicator(PageIndicator pageIndicator) {
        ((QSPanel) ((ViewController) this).mView).setFooterPageIndicator(pageIndicator);
    }

    public void setListening(boolean z, boolean z2) {
        setListening(z && z2);
        if (z) {
            this.mBrightnessController.registerCallbacks();
        } else {
            this.mBrightnessController.unregisterCallbacks();
        }
    }

    public void setPageListener(PagedTileLayout.PageListener pageListener) {
        ((QSPanel) ((ViewController) this).mView).setPageListener(pageListener);
    }

    public void setPageMargin(int i) {
        ((QSPanel) ((ViewController) this).mView).setPageMargin(i);
    }

    public void setVisibility(int i) {
        ((QSPanel) ((ViewController) this).mView).setVisibility(i);
    }

    public void showEdit(final View view) {
        view.post(new Runnable() { // from class: com.android.systemui.qs.QSPanelController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                QSPanelController.$r8$lambda$pWrm8hTd7tMymTXvMd1IrptMZgU(QSPanelController.this, view);
            }
        });
    }

    public void updateResources() {
        ((QSPanel) ((ViewController) this).mView).updateResources();
    }
}