package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsFlatLayout.class */
public class GlobalActionsFlatLayout extends GlobalActionsLayout {
    public GlobalActionsFlatLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        View overflowButton = getOverflowButton();
        if (overflowButton != null) {
            getListView().removeView(overflowButton);
            super.addToListView(overflowButton, z);
        }
    }

    @VisibleForTesting
    public float getAnimationDistance() {
        return getGridItemSize() / 2.0f;
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public HardwareBgDrawable getBackgroundDrawable(int i) {
        return null;
    }

    @VisibleForTesting
    public float getGridItemSize() {
        return getContext().getResources().getDimension(R$dimen.global_actions_grid_item_height);
    }

    public final View getOverflowButton() {
        return findViewById(R$id.global_actions_overflow_button);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        boolean z2;
        super.onLayout(z, i, i2, i3, i4);
        ViewGroup listView = getListView();
        int i5 = 0;
        boolean z3 = false;
        while (true) {
            z2 = z3;
            if (i5 >= listView.getChildCount()) {
                break;
            }
            View childAt = listView.getChildAt(i5);
            boolean z4 = z2;
            if (childAt instanceof GlobalActionsItem) {
                z4 = z2 || ((GlobalActionsItem) childAt).isTruncated();
            }
            i5++;
            z3 = z4;
        }
        if (z2) {
            for (int i6 = 0; i6 < listView.getChildCount(); i6++) {
                View childAt2 = listView.getChildAt(i6);
                if (childAt2 instanceof GlobalActionsItem) {
                    ((GlobalActionsItem) childAt2).setMarquee(true);
                }
            }
        }
    }

    @Override // com.android.systemui.MultiListLayout
    public void removeAllListViews() {
        View overflowButton = getOverflowButton();
        super.removeAllListViews();
        if (overflowButton != null) {
            super.addToListView(overflowButton, false);
        }
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    @VisibleForTesting
    public boolean shouldReverseListItems() {
        int currentRotation = getCurrentRotation();
        boolean z = false;
        if (currentRotation == 0) {
            return false;
        }
        if (getCurrentLayoutDirection() == 1) {
            if (currentRotation == 1) {
                z = true;
            }
            return z;
        }
        boolean z2 = false;
        if (currentRotation == 3) {
            z2 = true;
        }
        return z2;
    }
}