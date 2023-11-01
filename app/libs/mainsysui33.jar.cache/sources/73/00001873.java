package com.android.systemui.globalactions;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.HardwareBgDrawable;
import com.android.systemui.MultiListLayout;
import com.android.systemui.R$color;
import com.android.systemui.R$id;
import com.android.systemui.util.leak.RotationUtils;
import java.util.Locale;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsLayout.class */
public abstract class GlobalActionsLayout extends MultiListLayout {
    public boolean mBackgroundsSet;

    public GlobalActionsLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void addToListView(View view, boolean z) {
        if (z) {
            getListView().addView(view, 0);
        } else {
            getListView().addView(view);
        }
    }

    public void addToSeparatedView(View view, boolean z) {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView == null) {
            addToListView(view, z);
        } else if (z) {
            separatedView.addView(view, 0);
        } else {
            separatedView.addView(view);
        }
    }

    public HardwareBgDrawable getBackgroundDrawable(int i) {
        HardwareBgDrawable hardwareBgDrawable = new HardwareBgDrawable(true, true, getContext());
        hardwareBgDrawable.setTint(i);
        return hardwareBgDrawable;
    }

    @VisibleForTesting
    public int getCurrentLayoutDirection() {
        return TextUtils.getLayoutDirectionFromLocale(Locale.getDefault());
    }

    @VisibleForTesting
    public int getCurrentRotation() {
        return RotationUtils.getRotation(((LinearLayout) this).mContext);
    }

    @Override // com.android.systemui.MultiListLayout
    public ViewGroup getListView() {
        return (ViewGroup) findViewById(16908298);
    }

    @Override // com.android.systemui.MultiListLayout
    public ViewGroup getSeparatedView() {
        return (ViewGroup) findViewById(R$id.separated_button);
    }

    public View getWrapper() {
        return getChildAt(0);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (getListView() == null || this.mBackgroundsSet) {
            return;
        }
        setBackgrounds();
        this.mBackgroundsSet = true;
    }

    @Override // com.android.systemui.MultiListLayout
    public void onUpdateList() {
        super.onUpdateList();
        ViewGroup separatedView = getSeparatedView();
        ViewGroup listView = getListView();
        for (int i = 0; i < this.mAdapter.getCount(); i++) {
            boolean shouldBeSeparated = this.mAdapter.shouldBeSeparated(i);
            View view = shouldBeSeparated ? this.mAdapter.getView(i, null, separatedView) : this.mAdapter.getView(i, null, listView);
            if (shouldBeSeparated) {
                addToSeparatedView(view, false);
            } else {
                addToListView(view, shouldReverseListItems());
            }
        }
    }

    public final void setBackgrounds() {
        HardwareBgDrawable backgroundDrawable;
        ViewGroup listView = getListView();
        HardwareBgDrawable backgroundDrawable2 = getBackgroundDrawable(getResources().getColor(R$color.global_actions_grid_background, null));
        if (backgroundDrawable2 != null) {
            listView.setBackground(backgroundDrawable2);
        }
        if (getSeparatedView() == null || (backgroundDrawable = getBackgroundDrawable(getResources().getColor(R$color.global_actions_separated_background, null))) == null) {
            return;
        }
        getSeparatedView().setBackground(backgroundDrawable);
    }

    public abstract boolean shouldReverseListItems();
}