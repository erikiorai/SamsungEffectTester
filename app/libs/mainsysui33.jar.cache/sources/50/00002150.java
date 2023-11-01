package com.android.systemui.qs;

import android.content.Context;
import android.util.AttributeSet;
import com.android.systemui.R$integer;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/SideLabelTileLayout.class */
public class SideLabelTileLayout extends TileLayout {
    public SideLabelTileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final int getPhantomTopPosition(int i) {
        return getRowTop(i / this.mColumns);
    }

    @Override // com.android.systemui.qs.TileLayout
    public boolean updateMaxRows(int i, int i2) {
        int i3 = this.mRows;
        int i4 = this.mMaxAllowedRows;
        this.mRows = i4;
        int i5 = this.mColumns;
        boolean z = true;
        if (i4 > ((i2 + i5) - 1) / i5) {
            this.mRows = ((i2 + i5) - 1) / i5;
        }
        if (i3 == this.mRows) {
            z = false;
        }
        return z;
    }

    @Override // com.android.systemui.qs.TileLayout, com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean updateResources() {
        boolean updateResources = super.updateResources();
        this.mMaxAllowedRows = getContext().getResources().getInteger(R$integer.quick_settings_max_rows);
        return updateResources;
    }

    @Override // com.android.systemui.qs.TileLayout
    public boolean useSidePadding() {
        return false;
    }
}