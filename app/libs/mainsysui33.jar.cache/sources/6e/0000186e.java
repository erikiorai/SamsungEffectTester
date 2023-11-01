package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsGridLayout.class */
public class GlobalActionsGridLayout extends GlobalActionsLayout {
    public GlobalActionsGridLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public void addToListView(View view, boolean z) {
        ListGridLayout listView = getListView();
        if (listView != null) {
            listView.addItem(view);
        }
    }

    @VisibleForTesting
    public float getAnimationDistance() {
        int rowCount = getListView().getRowCount();
        return (rowCount * getContext().getResources().getDimension(R$dimen.global_actions_grid_item_height)) / 2.0f;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public ListGridLayout getListView() {
        return (ListGridLayout) super.getListView();
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public void onUpdateList() {
        setupListView();
        super.onUpdateList();
        updateSeparatedItemSize();
    }

    @Override // com.android.systemui.MultiListLayout
    public void removeAllItems() {
        ViewGroup separatedView = getSeparatedView();
        ListGridLayout listView = getListView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
        if (listView != null) {
            listView.removeAllItems();
        }
    }

    @Override // com.android.systemui.MultiListLayout
    public void removeAllListViews() {
        ListGridLayout listView = getListView();
        if (listView != null) {
            listView.removeAllItems();
        }
    }

    @VisibleForTesting
    public void setupListView() {
        ListGridLayout listView = getListView();
        listView.setExpectedCount(this.mAdapter.countListItems());
        listView.setReverseSublists(shouldReverseSublists());
        listView.setReverseItems(shouldReverseListItems());
        listView.setSwapRowsAndColumns(shouldSwapRowsAndColumns());
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        boolean z = currentRotation == 0 || currentRotation == 3;
        boolean z2 = z;
        if (getCurrentLayoutDirection() == 1) {
            z2 = !z;
        }
        return z2;
    }

    @VisibleForTesting
    public boolean shouldReverseSublists() {
        return getCurrentRotation() == 3;
    }

    @VisibleForTesting
    public boolean shouldSwapRowsAndColumns() {
        return getCurrentRotation() != 0;
    }

    @VisibleForTesting
    public void updateSeparatedItemSize() {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView.getChildCount() == 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = separatedView.getChildAt(0).getLayoutParams();
        if (separatedView.getChildCount() == 1) {
            layoutParams.width = -1;
            layoutParams.height = -1;
            return;
        }
        layoutParams.width = -2;
        layoutParams.height = -2;
    }
}