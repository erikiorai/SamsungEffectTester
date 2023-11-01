package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.R$dimen;
import com.android.systemui.R$integer;
import com.android.systemui.plugins.qs.QSTileView;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelControllerBase;
import com.android.systemui.qs.tileimpl.HeightOverrideable;
import com.android.systemui.qs.tileimpl.QSTileViewImplKt;
import com.android.systemui.util.Utils;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/TileLayout.class */
public class TileLayout extends ViewGroup implements QSPanel.QSTileLayout {
    public int mCellHeight;
    public int mCellHeightResId;
    public int mCellMarginHorizontal;
    public int mCellMarginVertical;
    public int mCellWidth;
    public int mColumns;
    public int mLastTileBottom;
    public final boolean mLessRows;
    public boolean mListening;
    public int mMaxAllowedRows;
    public int mMaxCellHeight;
    public int mMaxColumns;
    public int mMinRows;
    public final ArrayList<QSPanelControllerBase.TileRecord> mRecords;
    public int mResourceColumns;
    public int mRows;
    public int mSidePadding;
    public float mSquishinessFraction;

    public TileLayout(Context context) {
        this(context, null);
    }

    public TileLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCellHeightResId = R$dimen.qs_tile_height;
        this.mRows = 1;
        this.mRecords = new ArrayList<>();
        this.mMaxAllowedRows = 3;
        this.mMinRows = 1;
        this.mMaxColumns = 100;
        this.mSquishinessFraction = 1.0f;
        setFocusableInTouchMode(true);
        this.mLessRows = Settings.System.getInt(context.getContentResolver(), "qs_less_rows", 0) == 0 ? Utils.useQsMediaPlayer(context) : true;
        updateResources();
    }

    public static int exactly(int i) {
        return View.MeasureSpec.makeMeasureSpec(i, 1073741824);
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void addTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.add(tileRecord);
        tileRecord.tile.setListening(this, this.mListening);
        addTileView(tileRecord);
    }

    public void addTileView(QSPanelControllerBase.TileRecord tileRecord) {
        addView(tileRecord.tileView);
    }

    public int getCellHeight() {
        return this.mMaxCellHeight;
    }

    public int getColumnStart(int i) {
        return getPaddingStart() + this.mSidePadding + (i * (this.mCellWidth + this.mCellMarginHorizontal));
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getNumVisibleTiles() {
        return this.mRecords.size();
    }

    public int getRowTop(int i) {
        return (int) (i * ((this.mCellHeight * QSTileViewImplKt.constrainSquishiness(this.mSquishinessFraction)) + this.mCellMarginVertical));
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public int getTilesHeight() {
        return this.mLastTileBottom + getPaddingBottom();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    public final void layoutTileRecords(int i, boolean z) {
        boolean z2 = getLayoutDirection() == 1;
        this.mLastTileBottom = 0;
        int min = Math.min(i, this.mRows * this.mColumns);
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i2 >= min) {
                return;
            }
            int i6 = i3;
            int i7 = i5;
            if (i3 == this.mColumns) {
                i7 = i5 + 1;
                i6 = 0;
            }
            QSPanelControllerBase.TileRecord tileRecord = this.mRecords.get(i2);
            int rowTop = getRowTop(i7);
            int columnStart = getColumnStart(z2 ? (this.mColumns - i6) - 1 : i6);
            int i8 = this.mCellWidth + columnStart;
            int measuredHeight = tileRecord.tileView.getMeasuredHeight() + rowTop;
            if (z) {
                tileRecord.tileView.layout(columnStart, rowTop, i8, measuredHeight);
            } else {
                tileRecord.tileView.setLeftTopRightBottom(columnStart, rowTop, i8, measuredHeight);
            }
            tileRecord.tileView.setPosition(i2);
            this.mLastTileBottom = rowTop + ((int) (tileRecord.tileView.getMeasuredHeight() * QSTileViewImplKt.constrainSquishiness(this.mSquishinessFraction)));
            i2++;
            i3 = i6 + 1;
            i4 = i7;
        }
    }

    public int maxTiles() {
        return Math.max(this.mColumns * this.mRows, 1);
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo(new AccessibilityNodeInfo.CollectionInfo(this.mRecords.size(), 1, false));
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        layoutTileRecords(this.mRecords.size(), true);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = this.mRecords.size();
        int size2 = View.MeasureSpec.getSize(i);
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        if (View.MeasureSpec.getMode(i2) == 0) {
            int i3 = this.mColumns;
            this.mRows = ((size + i3) - 1) / i3;
        }
        int i4 = this.mColumns;
        this.mCellWidth = ((((size2 - paddingStart) - paddingEnd) - (this.mCellMarginHorizontal * (i4 - 1))) - (this.mSidePadding * 2)) / i4;
        int exactly = exactly(getCellHeight());
        Iterator<QSPanelControllerBase.TileRecord> it = this.mRecords.iterator();
        TileLayout tileLayout = this;
        while (it.hasNext()) {
            QSPanelControllerBase.TileRecord next = it.next();
            if (next.tileView.getVisibility() != 8) {
                next.tileView.measure(exactly(this.mCellWidth), exactly);
                tileLayout = next.tileView.updateAccessibilityOrder(tileLayout);
                this.mCellHeight = next.tileView.getMeasuredHeight();
            }
        }
        int i5 = this.mCellHeight;
        int i6 = this.mCellMarginVertical;
        int i7 = ((i5 + i6) * this.mRows) - i6;
        int i8 = i7;
        if (i7 < 0) {
            i8 = 0;
        }
        setMeasuredDimension(size2, i8);
    }

    @Override // android.view.ViewGroup
    public void removeAllViews() {
        Iterator<QSPanelControllerBase.TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            it.next().tile.setListening(this, false);
        }
        this.mRecords.clear();
        super.removeAllViews();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void removeTile(QSPanelControllerBase.TileRecord tileRecord) {
        this.mRecords.remove(tileRecord);
        tileRecord.tile.setListening(this, false);
        removeView(tileRecord.tileView);
    }

    public void setListening(boolean z) {
        setListening(z, null);
    }

    public void setListening(boolean z, UiEventLogger uiEventLogger) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        Iterator<QSPanelControllerBase.TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            it.next().tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMaxColumns(int i) {
        this.mMaxColumns = i;
        return updateColumns();
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public boolean setMinRows(int i) {
        if (this.mMinRows != i) {
            this.mMinRows = i;
            updateResources();
            return true;
        }
        return false;
    }

    @Override // com.android.systemui.qs.QSPanel.QSTileLayout
    public void setSquishinessFraction(float f) {
        if (Float.compare(this.mSquishinessFraction, f) == 0) {
            return;
        }
        this.mSquishinessFraction = f;
        layoutTileRecords(this.mRecords.size(), false);
        Iterator<QSPanelControllerBase.TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            QSTileView qSTileView = it.next().tileView;
            if (qSTileView instanceof HeightOverrideable) {
                ((HeightOverrideable) qSTileView).setSquishinessFraction(this.mSquishinessFraction);
            }
        }
    }

    public final boolean updateColumns() {
        int i = this.mColumns;
        int min = Math.min(this.mResourceColumns, this.mMaxColumns);
        this.mColumns = min;
        return i != min;
    }

    public boolean updateMaxRows(int i, int i2) {
        int i3 = this.mCellMarginVertical;
        int i4 = this.mRows;
        int cellHeight = (i + i3) / (getCellHeight() + this.mCellMarginVertical);
        this.mRows = cellHeight;
        int i5 = this.mMinRows;
        if (cellHeight < i5) {
            this.mRows = i5;
        } else {
            int i6 = this.mMaxAllowedRows;
            if (cellHeight >= i6) {
                this.mRows = i6;
            }
        }
        int i7 = this.mRows;
        int i8 = this.mColumns;
        boolean z = true;
        if (i7 > ((i2 + i8) - 1) / i8) {
            this.mRows = ((i2 + i8) - 1) / i8;
        }
        if (i4 == this.mRows) {
            z = false;
        }
        return z;
    }

    public boolean updateResources() {
        Resources resources = ((ViewGroup) this).mContext.getResources();
        this.mResourceColumns = Math.max(1, resources.getInteger(R$integer.quick_settings_num_columns));
        this.mMaxCellHeight = ((ViewGroup) this).mContext.getResources().getDimensionPixelSize(this.mCellHeightResId);
        this.mCellMarginHorizontal = resources.getDimensionPixelSize(R$dimen.qs_tile_margin_horizontal);
        this.mSidePadding = useSidePadding() ? this.mCellMarginHorizontal / 2 : 0;
        this.mCellMarginVertical = resources.getDimensionPixelSize(R$dimen.qs_tile_margin_vertical);
        int max = Math.max(1, getResources().getInteger(R$integer.quick_settings_max_rows));
        this.mMaxAllowedRows = max;
        if (this.mLessRows) {
            this.mMaxAllowedRows = Math.max(this.mMinRows, max - 1);
        }
        if (updateColumns()) {
            requestLayout();
            return true;
        }
        return false;
    }

    public boolean useSidePadding() {
        return true;
    }
}