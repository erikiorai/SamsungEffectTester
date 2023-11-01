package com.android.systemui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import com.android.systemui.util.leak.RotationUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/MultiListLayout.class */
public abstract class MultiListLayout extends LinearLayout {
    public MultiListAdapter mAdapter;
    public int mRotation;
    public RotationListener mRotationListener;

    /* loaded from: mainsysui33.jar:com/android/systemui/MultiListLayout$MultiListAdapter.class */
    public static abstract class MultiListAdapter extends BaseAdapter {
        public abstract int countListItems();

        public abstract int countSeparatedItems();

        public boolean hasSeparatedItems() {
            return countSeparatedItems() > 0;
        }

        public abstract boolean shouldBeSeparated(int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/MultiListLayout$RotationListener.class */
    public interface RotationListener {
        void onRotate(int i, int i2);
    }

    public MultiListLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mRotation = RotationUtils.getRotation(context);
    }

    public abstract ViewGroup getListView();

    public abstract ViewGroup getSeparatedView();

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int rotation = RotationUtils.getRotation(((LinearLayout) this).mContext);
        int i = this.mRotation;
        if (rotation != i) {
            rotate(i, rotation);
            this.mRotation = rotation;
        }
    }

    public void onUpdateList() {
        removeAllItems();
        setSeparatedViewVisibility(this.mAdapter.hasSeparatedItems());
    }

    public void removeAllItems() {
        removeAllListViews();
        removeAllSeparatedViews();
    }

    public void removeAllListViews() {
        ViewGroup listView = getListView();
        if (listView != null) {
            listView.removeAllViews();
        }
    }

    public void removeAllSeparatedViews() {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            separatedView.removeAllViews();
        }
    }

    public void rotate(int i, int i2) {
        RotationListener rotationListener = this.mRotationListener;
        if (rotationListener != null) {
            rotationListener.onRotate(i, i2);
        }
    }

    public void setAdapter(MultiListAdapter multiListAdapter) {
        this.mAdapter = multiListAdapter;
    }

    public void setListViewAccessibilityDelegate(View.AccessibilityDelegate accessibilityDelegate) {
        getListView().setAccessibilityDelegate(accessibilityDelegate);
    }

    public void setRotationListener(RotationListener rotationListener) {
        this.mRotationListener = rotationListener;
    }

    public void setSeparatedViewVisibility(boolean z) {
        ViewGroup separatedView = getSeparatedView();
        if (separatedView != null) {
            separatedView.setVisibility(z ? 0 : 8);
        }
    }

    public void updateList() {
        if (this.mAdapter == null) {
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }
        onUpdateList();
    }
}