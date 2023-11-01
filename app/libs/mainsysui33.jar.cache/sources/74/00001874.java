package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsLayoutLite.class */
public class GlobalActionsLayoutLite extends GlobalActionsLayout {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsLayoutLite$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$vpSx5EMkYSSZADcTyloyjRsUc8E(View view) {
        lambda$new$0(view);
    }

    public GlobalActionsLayoutLite(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsLayoutLite$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                GlobalActionsLayoutLite.$r8$lambda$vpSx5EMkYSSZADcTyloyjRsUc8E(view);
            }
        });
    }

    public static /* synthetic */ void lambda$new$0(View view) {
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    public void addToListView(View view, boolean z) {
        super.addToListView(view, z);
        ((Flow) findViewById(R$id.list_flow)).addView(view);
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

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public void onUpdateList() {
        super.onUpdateList();
        int integer = getResources().getInteger(R$integer.power_menu_lite_max_columns);
        int i = integer;
        if (getListView().getChildCount() - 1 == integer + 1) {
            i = integer;
            if (integer > 2) {
                i = integer - 1;
            }
        }
        ((Flow) findViewById(R$id.list_flow)).setMaxElementsWrap(i);
    }

    @Override // com.android.systemui.MultiListLayout
    public void removeAllListViews() {
        View findViewById = findViewById(R$id.list_flow);
        super.removeAllListViews();
        super.addToListView(findViewById, false);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout
    @VisibleForTesting
    public boolean shouldReverseListItems() {
        return false;
    }
}