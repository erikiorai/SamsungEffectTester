package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.qs.QSPanelControllerBase;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickQSPanel.class */
public class QuickQSPanel extends QSPanel {
    public boolean mDisabledByPolicy;
    public int mMaxTiles;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickQSPanel$QQSSideLabelTileLayout.class */
    public static class QQSSideLabelTileLayout extends SideLabelTileLayout {
        public boolean mLastSelected;

        public QQSSideLabelTileLayout(Context context) {
            super(context, null);
            setClipChildren(false);
            setClipToPadding(false);
            setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            setMaxColumns(4);
        }

        @Override // android.view.View
        public void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            updateResources();
        }

        @Override // com.android.systemui.qs.TileLayout, android.view.View
        public void onMeasure(int i, int i2) {
            updateMaxRows(10000, this.mRecords.size());
            super.onMeasure(i, i2);
        }

        @Override // com.android.systemui.qs.QSPanel.QSTileLayout
        public void setExpansion(float f, float f2) {
            if (f <= ActionBarShadowController.ELEVATION_LOW || f >= 1.0f) {
                boolean z = f == 1.0f || f2 < ActionBarShadowController.ELEVATION_LOW;
                if (this.mLastSelected == z) {
                    return;
                }
                setImportantForAccessibility(4);
                for (int i = 0; i < getChildCount(); i++) {
                    getChildAt(i).setSelected(z);
                }
                setImportantForAccessibility(0);
                this.mLastSelected = z;
            }
        }

        @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.QSPanel.QSTileLayout
        public void setListening(boolean z, UiEventLogger uiEventLogger) {
            boolean z2 = !this.mListening && z;
            super.setListening(z, uiEventLogger);
            if (z2) {
                for (int i = 0; i < getNumVisibleTiles(); i++) {
                    QSTile qSTile = this.mRecords.get(i).tile;
                    uiEventLogger.logWithInstanceId(QSEvent.QQS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
                }
            }
        }

        @Override // com.android.systemui.qs.SideLabelTileLayout, com.android.systemui.qs.TileLayout, com.android.systemui.qs.QSPanel.QSTileLayout
        public boolean updateResources() {
            this.mCellHeightResId = R$dimen.qs_quick_tile_size;
            boolean updateResources = super.updateResources();
            this.mMaxAllowedRows = getResources().getInteger(R$integer.quick_qs_panel_max_rows);
            return updateResources;
        }
    }

    public QuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mMaxTiles = getResources().getInteger(R$integer.quick_qs_panel_max_tiles);
    }

    @Override // com.android.systemui.qs.QSPanel
    public QSEvent closePanelEvent() {
        return QSEvent.QQS_PANEL_COLLAPSED;
    }

    @Override // com.android.systemui.qs.QSPanel
    public boolean displayMediaMarginsOnMedia() {
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: com.android.systemui.plugins.qs.QSTile$State */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.android.systemui.qs.QSPanel
    public void drawTile(QSPanelControllerBase.TileRecord tileRecord, QSTile.State state) {
        QSTile.SignalState signalState = state;
        if (state instanceof QSTile.SignalState) {
            signalState = new QSTile.SignalState();
            state.copyTo(signalState);
            signalState.activityIn = false;
            signalState.activityOut = false;
        }
        super.drawTile(tileRecord, signalState);
    }

    @Override // com.android.systemui.qs.QSPanel
    public String getDumpableTag() {
        return "QuickQSPanel";
    }

    public int getNumQuickTiles() {
        return this.mMaxTiles;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.QSPanel
    public TileLayout getOrCreateTileLayout() {
        QQSSideLabelTileLayout qQSSideLabelTileLayout = new QQSSideLabelTileLayout(this.mContext);
        qQSSideLabelTileLayout.setId(R$id.qqs_tile_layout);
        return qQSSideLabelTileLayout;
    }

    @Override // com.android.systemui.qs.QSPanel
    public boolean mediaNeedsTopMargin() {
        return true;
    }

    @Override // com.android.systemui.qs.QSPanel, android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_COLLAPSE);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_EXPAND);
    }

    @Override // com.android.systemui.qs.QSPanel
    public void onTuningChanged(String str, String str2) {
        if ("customsecure:qs_show_brightness_slider".equals(str)) {
            super.onTuningChanged(str, "0");
        }
    }

    @Override // com.android.systemui.qs.QSPanel
    public QSEvent openPanelEvent() {
        return QSEvent.QQS_PANEL_EXPANDED;
    }

    public void setDisabledByPolicy(boolean z) {
        if (z != this.mDisabledByPolicy) {
            this.mDisabledByPolicy = z;
            setVisibility(z ? 8 : 0);
        }
    }

    @Override // com.android.systemui.qs.QSPanel
    public void setHorizontalContentContainerClipping() {
        this.mHorizontalContentContainer.setClipToPadding(false);
        this.mHorizontalContentContainer.setClipChildren(false);
    }

    public void setMaxTiles(int i) {
        this.mMaxTiles = i;
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        if (this.mDisabledByPolicy) {
            if (getVisibility() == 8) {
                return;
            }
            i = 8;
        }
        super.setVisibility(i);
    }

    @Override // com.android.systemui.qs.QSPanel
    public void updatePadding() {
        setPaddingRelative(getPaddingStart(), getPaddingTop(), getPaddingEnd(), getResources().getDimensionPixelSize(R$dimen.qqs_layout_padding_bottom));
    }
}