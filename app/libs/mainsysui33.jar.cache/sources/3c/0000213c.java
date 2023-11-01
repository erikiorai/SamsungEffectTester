package com.android.systemui.qs;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$integer;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.leak.RotationUtils;
import java.util.ArrayList;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickQSPanelController.class */
public class QuickQSPanelController extends QSPanelControllerBase<QuickQSPanel> {
    public final Provider<Boolean> mUsingCollapsedLandscapeMediaProvider;

    public QuickQSPanelController(QuickQSPanel quickQSPanel, QSTileHost qSTileHost, QSCustomizerController qSCustomizerController, boolean z, MediaHost mediaHost, Provider<Boolean> provider, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager) {
        super(quickQSPanel, qSTileHost, qSCustomizerController, z, mediaHost, metricsLogger, uiEventLogger, qSLogger, dumpManager);
        this.mUsingCollapsedLandscapeMediaProvider = provider;
    }

    public int getRotation() {
        return RotationUtils.getRotation(getContext());
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onConfigurationChanged() {
        int integer = getResources().getInteger(R$integer.quick_qs_panel_max_tiles);
        if (integer != ((QuickQSPanel) ((ViewController) this).mView).getNumQuickTiles()) {
            setMaxTiles(integer);
        }
        updateMediaExpansion();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onInit() {
        super.onInit();
        updateMediaExpansion();
        this.mMediaHost.setShowsOnlyActiveMedia(true);
        this.mMediaHost.init(1);
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onViewAttached() {
        super.onViewAttached();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void onViewDetached() {
        super.onViewDetached();
    }

    public void setContentMargins(int i, int i2) {
        ((QuickQSPanel) ((ViewController) this).mView).setContentMargins(i, i2, this.mMediaHost.getHostView());
    }

    public final void setMaxTiles(int i) {
        ((QuickQSPanel) ((ViewController) this).mView).setMaxTiles(i);
        setTiles();
    }

    @Override // com.android.systemui.qs.QSPanelControllerBase
    public void setTiles() {
        ArrayList arrayList = new ArrayList();
        for (QSTile qSTile : this.mHost.getTiles()) {
            arrayList.add(qSTile);
            if (arrayList.size() == ((QuickQSPanel) ((ViewController) this).mView).getNumQuickTiles()) {
                break;
            }
        }
        super.setTiles(arrayList, true);
    }

    public final void updateMediaExpansion() {
        int rotation = getRotation();
        boolean z = true;
        if (rotation != 1) {
            z = rotation == 3;
        }
        if (((Boolean) this.mUsingCollapsedLandscapeMediaProvider.get()).booleanValue() && z) {
            this.mMediaHost.setExpansion(ActionBarShadowController.ELEVATION_LOW);
        } else {
            this.mMediaHost.setExpansion(1.0f);
        }
    }
}