package com.android.systemui.globalactions;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsColumnLayout.class */
public class GlobalActionsColumnLayout extends GlobalActionsLayout {
    public boolean mLastSnap;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsColumnLayout$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$p3Lb-zb_F4A6w4l4zT0kWyeHhFc */
    public static /* synthetic */ void m2727$r8$lambda$p3Lbzb_F4A6w4l4zT0kWyeHhFc(GlobalActionsColumnLayout globalActionsColumnLayout) {
        globalActionsColumnLayout.lambda$onLayout$0();
    }

    public GlobalActionsColumnLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @VisibleForTesting
    public void centerAlongEdge() {
        int currentRotation = getCurrentRotation();
        if (currentRotation == 1) {
            setPadding(0, 0, 0, 0);
            setGravity(49);
        } else if (currentRotation != 3) {
            setPadding(0, 0, 0, 0);
            setGravity(21);
        } else {
            setPadding(0, 0, 0, 0);
            setGravity(81);
        }
    }

    @VisibleForTesting
    public float getAnimationDistance() {
        return getGridItemSize() / 2.0f;
    }

    @VisibleForTesting
    public float getGridItemSize() {
        return getContext().getResources().getDimension(R$dimen.global_actions_grid_item_height);
    }

    @VisibleForTesting
    public int getPowerButtonOffsetDistance() {
        return Math.round(getContext().getResources().getDimension(R$dimen.global_actions_top_padding));
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        post(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsColumnLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GlobalActionsColumnLayout.m2727$r8$lambda$p3Lbzb_F4A6w4l4zT0kWyeHhFc(GlobalActionsColumnLayout.this);
            }
        });
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    @Override // com.android.systemui.globalactions.GlobalActionsLayout, com.android.systemui.MultiListLayout
    public void onUpdateList() {
        super.onUpdateList();
        updateChildOrdering();
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

    @VisibleForTesting
    public boolean shouldSnapToPowerButton() {
        int measuredWidth;
        int measuredWidth2;
        int powerButtonOffsetDistance = getPowerButtonOffsetDistance();
        View wrapper = getWrapper();
        if (getCurrentRotation() == 0) {
            measuredWidth = wrapper.getMeasuredHeight();
            measuredWidth2 = getMeasuredHeight();
        } else {
            measuredWidth = wrapper.getMeasuredWidth();
            measuredWidth2 = getMeasuredWidth();
        }
        return measuredWidth + powerButtonOffsetDistance < measuredWidth2;
    }

    @VisibleForTesting
    public void snapToPowerButton() {
        int powerButtonOffsetDistance = getPowerButtonOffsetDistance();
        int currentRotation = getCurrentRotation();
        if (currentRotation == 1) {
            setPadding(powerButtonOffsetDistance, 0, 0, 0);
            setGravity(51);
        } else if (currentRotation != 3) {
            setPadding(0, powerButtonOffsetDistance, 0, 0);
            setGravity(53);
        } else {
            setPadding(0, 0, powerButtonOffsetDistance, 0);
            setGravity(85);
        }
    }

    public final void updateChildOrdering() {
        if (shouldReverseListItems()) {
            getListView().bringToFront();
        } else {
            getSeparatedView().bringToFront();
        }
    }

    @VisibleForTesting
    /* renamed from: updateSnap */
    public void lambda$onLayout$0() {
        boolean shouldSnapToPowerButton = shouldSnapToPowerButton();
        if (shouldSnapToPowerButton != this.mLastSnap) {
            if (shouldSnapToPowerButton) {
                snapToPowerButton();
            } else {
                centerAlongEdge();
            }
        }
        this.mLastSnap = shouldSnapToPowerButton;
    }
}